package com.globalLogic.challenge.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiErrorResponse {

        public ApiErrorResponse(){
        }

        public ApiErrorResponse(Integer code, String message) {
            this.code = code;
            this.message = message;
            this.timestamp = LocalDateTime.now();
        }

        private Integer code;
        private String message;
        private LocalDateTime timestamp;

}
