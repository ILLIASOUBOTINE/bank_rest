package com.example.bankcards.controller.util;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
public class DocsController {

    @GetMapping("/docs/openapi.yaml")
    public ResponseEntity<Resource> getOpenApi() {
        File file = new File("docs/openapi.yaml"); // путь относительно корня проекта
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }
        Resource resource = new FileSystemResource(file);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("application/yaml"))
                .body(resource);
    }
}

