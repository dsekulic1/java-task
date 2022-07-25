package com.example.task.repositories;

import com.example.task.model.FileEntity;
import com.example.task.model.UserFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserFileRepository extends JpaRepository<UserFile, UUID> {
    List<UserFile> findAllByUserId(UUID userId);

    UserFile findByFileEntity(FileEntity fileEntity);
}
