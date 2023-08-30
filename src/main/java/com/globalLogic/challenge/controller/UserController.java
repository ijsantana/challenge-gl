package com.globalLogic.challenge.controller;

import com.globalLogic.challenge.dto.ApiErrorResponse;
import com.globalLogic.challenge.dto.ApiResponse;
import com.globalLogic.challenge.dto.UserDto;
import com.globalLogic.challenge.exception.AuthorizationException;
import com.globalLogic.challenge.exception.UserException;
import com.globalLogic.challenge.model.User;
import com.globalLogic.challenge.service.UserService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

import static org.springframework.http.ResponseEntity.ok;


@RestController
@RequestMapping("api/v1/user")
@CrossOrigin
@Slf4j
public class UserController {

    private final UserService userService;

    private final Bucket bucket;

    @Value("${controller.authorization.apiKey}")
    private String apiKey;

    public UserController(UserService userService) {
        this.userService = userService;
        Bandwidth limit = Bandwidth.classic(3, Refill.greedy(3, Duration.ofMinutes(1)));
        this.bucket = Bucket.builder()
                .addLimit(limit)
                .build();
    }

    @PostMapping()
    public ResponseEntity<Object> createNewUser(@RequestBody UserDto userDto, @RequestHeader("Authorization") String apiKey){

        if (apiKey.equals(this.apiKey))
            throw new AuthorizationException("User Unauthorized");

        try {
            if (bucket.tryConsume(1)) {
                return ok(new ApiResponse<>(200, "OK", userService.createNewUser(userDto)));
            } else {
                final var response = new ApiErrorResponse(HttpStatus.TOO_MANY_REQUESTS.value(), "Too many requests (rate: 3 request/min)");
                return new ResponseEntity<>(response, HttpStatus.TOO_MANY_REQUESTS);
            }
        } catch(UserException ex){
            log.error("Number Exception: {}", ex.getMessage(), ex);
            var response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<Page<User>>> getAllUsers(@RequestParam Integer page){
        return ok(new ApiResponse<>(200, "OK", userService.getAllUsers(page)));
    }


    @GetMapping("/{token}")
    public ResponseEntity<Object> isAuthenticated(@PathVariable String token){
        try {
            if (bucket.tryConsume(1)) {
                return ok(new ApiResponse<>(200, "OK", userService.isAuthenticated(token)));
            } else {
                final var response = new ApiErrorResponse(HttpStatus.TOO_MANY_REQUESTS.value(), "Too many requests (rate: 3 request/min)");
                return new ResponseEntity<>(response, HttpStatus.TOO_MANY_REQUESTS);
            }
        } catch(UserException ex){
            log.error("Number Exception: {}", ex.getMessage(), ex);
            var response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping()
    public ResponseEntity<Object> loginUser(@RequestBody UserDto userDto){
        try {
            if (bucket.tryConsume(1)) {
                return ok(new ApiResponse<>(200, "OK", userService.generateNewLogin(userDto)));
            } else {
                final var response = new ApiErrorResponse(HttpStatus.TOO_MANY_REQUESTS.value(), "Too many requests (rate: 3 request/min)");
                return new ResponseEntity<>(response, HttpStatus.TOO_MANY_REQUESTS);
            }
        } catch(UserException ex){
            log.error("Number Exception: {}", ex.getMessage(), ex);
            var response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
    }


}
