package com.globalLogic.challenge.adapter.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    public ApiResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public ApiResponse(Integer code, String message, T body) {
        this.code = code;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.body = body;
    }

    private Integer code;
    private String message;
    private LocalDateTime timestamp;
    private T body;

}