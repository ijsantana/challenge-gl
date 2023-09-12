package com.globalLogic.challenge.adapter.controller;

import com.globalLogic.challenge.adapter.dto.ApiResponse;
import com.globalLogic.challenge.core.dto.UserDto;
import com.globalLogic.challenge.core.model.User;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserController {

    public ResponseEntity<Object> createNewUser(@RequestBody UserDto userDto, @RequestHeader("Authorization") String apiKey);

    public ResponseEntity<ApiResponse<Page<User>>> getAllUsers(@RequestParam Integer page);



    }
