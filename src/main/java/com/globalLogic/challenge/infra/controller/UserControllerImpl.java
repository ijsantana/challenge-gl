package com.globalLogic.challenge.infra.controller;

import com.globalLogic.challenge.adapter.controller.UserController;
import com.globalLogic.challenge.adapter.dto.ApiErrorResponse;
import com.globalLogic.challenge.adapter.dto.ApiResponse;
import com.globalLogic.challenge.core.dto.UserDto;
import com.globalLogic.challenge.core.exception.AttributeException;
import com.globalLogic.challenge.core.exception.UserException;
import com.globalLogic.challenge.core.model.User;
import com.globalLogic.challenge.adapter.service.UserService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

import static org.springframework.http.ResponseEntity.ok;


@RestController
@RequestMapping("api/v1/user")
@CrossOrigin
@Slf4j
public class UserControllerImpl implements UserController {

    private final UserService userService;

    private final Bucket bucket;

    @Value("${controller.authorization.apiKey}")
    private String apiKey;

    public UserControllerImpl(UserService userService) {
        this.userService = userService;
        Bandwidth limit = Bandwidth.classic(3, Refill.greedy(3, Duration.ofMinutes(1)));
        this.bucket = Bucket.builder()
                .addLimit(limit)
                .build();
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Creación de un nuevo usuario. Mail: nombre@Dominio.com - Password: 1 Mayuscula y 2 numeros minimos.")
    public ResponseEntity<Object> createNewUser(
            @RequestBody UserDto userDto,
            @RequestHeader("Authorization") String apiKey){

        try {
            if (bucket.tryConsume(1)) {
                return ok(new ApiResponse<>(200, "CREATED", userService.createNewUser(userDto)));
            } else {
                var response = new ApiErrorResponse(HttpStatus.TOO_MANY_REQUESTS.value(), "Too many requests (rate: 3 request/min)");
                return new ResponseEntity<>(response, HttpStatus.TOO_MANY_REQUESTS);
            }

        } catch(UserException ex) {
            log.error("User Exception: {}", ex.getMessage(), ex);
            var response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch(AttributeException ex){
                log.error("Format Exception: {}", ex.getMessage(), ex);
                var response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
                return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        } catch(Exception ex){
            log.error("Fatal Exception: {}", ex.getMessage(), ex);
            var response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping()
    @ApiOperation(value = "Obtención de todos los usuarios - Paginados")
    public ResponseEntity<ApiResponse<Page<User>>> getAllUsers(@RequestParam Integer page){
        return ok(new ApiResponse<>(200, "OK", userService.getAllUsers(page)));
    }




}
