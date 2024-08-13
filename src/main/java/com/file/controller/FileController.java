package com.file.controller;

import com.file.entity.FileEntity;
import com.file.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private FileService fileService;


//http://localhost:8080/api/files/upload
    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            FileEntity fileEntity = new FileEntity();
            fileEntity.setFileName(file.getOriginalFilename());
            fileEntity.setData(file.getBytes());
            fileService.saveFile(fileEntity);
            return ResponseEntity.ok("File uploaded successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file!");
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
        FileEntity fileEntity = fileService.getFileById(id);

        if (fileEntity != null && fileEntity.getData() != null) {
            return ResponseEntity.ok()
                    .header("Content-Disposition", "inline; filename=" + fileEntity.getFileName())
                    .body(fileEntity.getData());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
