package com.globalLogic.challenge.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationUser {

    private String name;
    private String email;
    private boolean isActive;

}
