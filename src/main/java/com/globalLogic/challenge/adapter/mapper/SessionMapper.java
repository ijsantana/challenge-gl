package com.globalLogic.challenge.adapter.mapper;

import com.globalLogic.challenge.core.dto.LoginResponse;
import com.globalLogic.challenge.core.dto.SessionDto;
import com.globalLogic.challenge.core.model.Session;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class SessionMapper {

    public static SessionDto mapSessionToDto(Session session){
        return SessionDto.builder()
                .email(session.getUser().getMail())
                .name(session.getUser().getName())
                .isActive(session.isActive())
                .build();
    }

    public static LoginResponse mapSessionToLoginResponse(Session session, LocalDateTime lastSession) {
        return LoginResponse.builder()
                .id(session.getId())
                .token(session.getSessionToken())
                .created(session.getCreated())
                .name(session.getUser().getName())
                .mail(session.getUser().getMail())
                .password("xxxxxxxxx")
                .isActive(session.isActive())
                .phones(session.getUser().getPhones()
                        .stream()
                        .map(PhoneMapper::mapPhoneToDto)
                        .collect(Collectors.toList()))
                .build();
    }

}
