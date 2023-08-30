package com.globalLogic.challenge.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneDto {

    private Long number;
    private Integer cityCode;
    private String countryCode;

}
