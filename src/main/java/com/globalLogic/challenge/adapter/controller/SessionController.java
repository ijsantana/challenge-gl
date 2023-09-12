package com.globalLogic.challenge.adapter.controller;

import com.globalLogic.challenge.core.dto.LoginDto;
import com.globalLogic.challenge.core.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface SessionController {

    public ResponseEntity<Object> isAuthenticated(@PathVariable String token);

    public ResponseEntity<Object> loginUser(@RequestBody LoginDto loginDto);

}
