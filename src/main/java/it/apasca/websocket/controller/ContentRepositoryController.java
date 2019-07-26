package it.apasca.websocket.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.apasca.websocket.dto.DocumentDto;
import it.apasca.websocket.exception.ContentrepoFileNotFoundException;
import it.apasca.websocket.service.ContentRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/files")
@Api(value = "Repository controller")
public class ContentRepositoryController {

    @Autowired
    private ContentRepoService storageService;

    @GetMapping("/{identifier}")
    @ApiOperation("Download the file")
    public ResponseEntity<Resource> serveFile(
            @ApiParam(value = "File name identifier", required = true) @PathVariable("identifier") String identifier)
            throws ContentrepoFileNotFoundException {
        DocumentDto file = storageService.load(identifier);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file.getResource());
    }

    @PostMapping
    @ApiOperation("Upload the file")
    public String handleFileUpload(
            @ApiParam(value = "The file to upload", required = true) @RequestParam("file") MultipartFile file)
            throws ContentrepoFileNotFoundException {
        return storageService.store(file);
    }

    @ExceptionHandler(ContentrepoFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(ContentrepoFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }


}
