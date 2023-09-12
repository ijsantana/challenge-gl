package com.globalLogic.challenge.infra.controller;

import com.globalLogic.challenge.adapter.controller.SessionController;
import com.globalLogic.challenge.adapter.dto.ApiErrorResponse;
import com.globalLogic.challenge.adapter.dto.ApiResponse;
import com.globalLogic.challenge.core.dto.LoginDto;
import com.globalLogic.challenge.core.exception.LoginException;
import com.globalLogic.challenge.core.exception.SessionException;
import com.globalLogic.challenge.core.exception.UserException;
import com.globalLogic.challenge.adapter.service.SessionService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

import static org.springframework.http.ResponseEntity.ok;


@RestController
@RequestMapping("api/v1/session")
@CrossOrigin
@Slf4j
public class SessionControllerImpl implements SessionController {

    private final SessionService sessionService;
    private final Bucket bucket;

    public SessionControllerImpl(SessionService sessionService) {
        this.sessionService = sessionService;
        Bandwidth limit = Bandwidth.classic(3, Refill.greedy(3, Duration.ofMinutes(1)));
        this.bucket = Bucket.builder()
                .addLimit(limit)
                .build();
    }
    @GetMapping(value = "/{token}")
    @ApiOperation(value = "Valida a través del session token si el Login continua activo o expiró.")
    public ResponseEntity<Object> isAuthenticated(@PathVariable String token){
        try {
            if (bucket.tryConsume(1)) {
                return ok(new ApiResponse<>(200, "OK", sessionService.isAuthenticated(token)));
            } else {
                final var response = new ApiErrorResponse(HttpStatus.TOO_MANY_REQUESTS.value(), "Too many requests (rate: 3 request/min)");
                return new ResponseEntity<>(response, HttpStatus.TOO_MANY_REQUESTS);
            }
        } catch(SessionException ex){
            log.error("Session Exception: {}", ex.getMessage(), ex);
            var response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        } catch(Exception ex){
            log.error("Fatal Exception: {}", ex.getMessage(), ex);
            var response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Logueo de un usuario nuevo - Requiere Mail y password")
    public ResponseEntity<Object> loginUser(@RequestBody LoginDto loginDto){
        try {
            if (bucket.tryConsume(1)) {
                return ok(new ApiResponse<>(200, "OK", sessionService.generateNewLogin(loginDto)));
            } else {
                final var response = new ApiErrorResponse(HttpStatus.TOO_MANY_REQUESTS.value(), "Too many requests (rate: 3 request/min)");
                return new ResponseEntity<>(response, HttpStatus.TOO_MANY_REQUESTS);
            }
        } catch(UserException ex){
            log.error("User Exception: {}", ex.getMessage(), ex);
            var response = new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
            return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
        } catch(LoginException ex){
            log.error("Login Exception: {}", ex.getMessage(), ex);
            var response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        } catch(Exception ex){
            log.error("Fatal Exception: {}", ex.getMessage(), ex);
            var response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
