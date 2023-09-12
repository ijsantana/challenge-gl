package com.globalLogic.challenge.adapter.mapper;

import com.globalLogic.challenge.core.dto.PhoneDto;
import com.globalLogic.challenge.core.model.Phone;

public class PhoneMapper {
    public static PhoneDto mapPhoneToDto(Phone phone){
        return PhoneDto.builder()
                .cityCode(phone.getCityCode())
                .number(phone.getNumber())
                .countryCode(phone.getCountryCode())
                .build();
    }

    public static Phone mapDtoToPhone(PhoneDto phoneDto){
        return new Phone()
                .withCityCode(phoneDto.getCityCode())
                .withNumber(phoneDto.getNumber())
                .withCountryCode(phoneDto.getCountryCode());
    }
}
