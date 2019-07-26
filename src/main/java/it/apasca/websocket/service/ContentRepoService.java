package it.apasca.websocket.service;

import it.apasca.websocket.dto.DocumentDto;
import it.apasca.websocket.exception.ContentrepoFileNotFoundException;
import org.springframework.web.multipart.MultipartFile;

public interface ContentRepoService {
    String store(MultipartFile file) throws ContentrepoFileNotFoundException;
    DocumentDto load(String identifier) throws ContentrepoFileNotFoundException;
}
