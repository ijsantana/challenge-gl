package com.globalLogic.challenge.core.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PhoneDto {

    private Long number;
    private Integer cityCode;
    private String countryCode;

}
