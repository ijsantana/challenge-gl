package com.globalLogic.challenge.dto;

import java.time.LocalDateTime;

public class UserResponse {

    private Long id;
    private LocalDateTime created;
    private LocalDateTime lastLogin;
    private String token;
    private boolean isActive;

}
