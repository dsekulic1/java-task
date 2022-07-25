package com.example.task.service;

import com.example.task.exception.NotFoundException;
import com.example.task.model.FileEntity;
import com.example.task.model.UserFile;
import com.example.task.repositories.FileRepository;
import com.example.task.repositories.UserFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class FileService {

    private final FileRepository fileRepository;
    private final UserFileRepository userFileRepository;

    @Autowired
    public FileService(FileRepository fileRepository, UserFileRepository userFileRepository) {
        this.fileRepository = fileRepository;
        this.userFileRepository = userFileRepository;
    }

    public FileEntity save(MultipartFile file) throws IOException {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setName(StringUtils.cleanPath(file.getOriginalFilename()));
        fileEntity.setContentType(file.getContentType());
        fileEntity.setData(file.getBytes());
        fileEntity.setSize(file.getSize());

        return fileRepository.save(fileEntity);
    }

    public Optional<FileEntity> getFile(String id) {
        return fileRepository.findById(id);
    }

    public FileEntity deleteById(String id) {
        Optional<FileEntity> optionalFileEntity = getFile(id);

        if(optionalFileEntity.isEmpty()) {
            throw new NotFoundException("File does not exist.");
        } else {
            FileEntity fileEntity = optionalFileEntity.get();

            UserFile userFile = userFileRepository.findByFileEntity(fileEntity);
            userFileRepository.delete(userFile);
            fileRepository.deleteById(id);

            return fileEntity;
        }
    }
}
