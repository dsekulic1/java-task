package com.example.task.service;

import com.example.task.model.UserFile;
import com.example.task.repositories.UserFileRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserFileService {
    private final UserFileRepository userFileRepository;

    public UserFileService(UserFileRepository userFileRepository) {
        this.userFileRepository = userFileRepository;
    }

    public UserFile addUserFile(UserFile userFile) {
        return userFileRepository.save(userFile);
    }

    public List<UserFile> getAllUserFiles(UUID userId) {
        return userFileRepository.findAllByUserId(userId);
    }

}
