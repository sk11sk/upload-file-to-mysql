package com.file.service;

import com.file.entity.FileEntity;
import com.file.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    public void saveFile(FileEntity fileEntity) {
        fileRepository.save(fileEntity);
    }

    public FileEntity getFileById(Long id) {
        return fileRepository.findById(id).orElse(null);
    }
}
