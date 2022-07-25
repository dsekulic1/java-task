package com.example.task.response;

import com.example.task.model.User;

import java.util.List;

public class UserFileResponse {
    private User user;
    private List<FileResponse> userFiles;

    public UserFileResponse(User user, List<FileResponse> userFiles) {
        this.user = user;
        this.userFiles = userFiles;
    }
}
