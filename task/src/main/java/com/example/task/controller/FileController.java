package com.example.task.controller;

import com.example.task.model.FileEntity;
import com.example.task.model.User;
import com.example.task.model.UserFile;
import com.example.task.response.FileResponse;
import com.example.task.response.UserFileResponse;
import com.example.task.service.FileService;
import com.example.task.service.UserFileService;
import com.example.task.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/file")
public class FileController {
    private final FileService fileService;
    private final UserService userService;
    private final UserFileService userFileService;

    public FileController(FileService fileService, UserService userService, UserFileService userFileService) {
        this.fileService = fileService;
        this.userService = userService;
        this.userFileService = userFileService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UserFile uploadFile(@RequestParam MultipartFile file, @RequestParam UUID userId) throws IOException {
        FileEntity fileEntity = fileService.save(file);
        UserFile userFile = new UserFile(fileEntity, userId);

        return userFileService.addUserFile(userFile);
    }

    @GetMapping(path = "/all/{id}")
    public UserFileResponse getUserFiles(@PathVariable UUID id) {
        List<FileResponse> fileResponseList = userFileService.getAllUserFiles(id)
                .stream()
                .map(file -> mapToFileResponse(file.getFileEntity()))
                .collect(Collectors.toList());

        User user = userService.getUserById(id);

        return new UserFileResponse(user, fileResponseList);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<FileEntity> deleteBusinessById(@PathVariable String id) {
        return ResponseEntity.ok(fileService.deleteById(id));
    }

    private FileResponse mapToFileResponse(FileEntity fileEntity) {
        String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/")
                .path(fileEntity.getId())
                .toUriString();
        FileResponse fileResponse = new FileResponse();
        fileResponse.setId(fileEntity.getId());
        fileResponse.setName(fileEntity.getName());
        fileResponse.setContentType(fileEntity.getContentType());
        fileResponse.setSize(fileEntity.getSize());
        fileResponse.setUrl(downloadURL);
        fileResponse.setBase64(Base64.getEncoder().encodeToString(fileEntity.getData()));

        return fileResponse;
    }
}
