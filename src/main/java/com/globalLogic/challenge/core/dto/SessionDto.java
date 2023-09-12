package com.globalLogic.challenge.core.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SessionDto {

    private String name;
    private String email;
    private boolean isActive;

}
