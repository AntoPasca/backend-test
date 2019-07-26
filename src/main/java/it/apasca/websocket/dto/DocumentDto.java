package it.apasca.websocket.dto;

import org.springframework.core.io.Resource;

public class DocumentDto {
    private static final long serialVersionUID = 1L;

    private String filename;
    private transient Resource resource;

    public DocumentDto(String filename, Resource resource) {
        this.filename = filename;
        this.resource = resource;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
}
