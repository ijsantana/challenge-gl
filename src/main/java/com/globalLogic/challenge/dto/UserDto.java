package com.globalLogic.challenge.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserDto {

    private String name;
    private String email;
    private String password;
    private List<PhoneDto> phones;

}
