package com.app.ims.controller;

import com.app.ims.dto.*;
import com.app.ims.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "api/auth")
public class AuthController {

    private final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private AuthService authService;

    @PostMapping(value = "/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody LoginRequest loginRequest){
        LOGGER.debug("login method loginRequest : {}", loginRequest.toString());
        return ResponseEntity.ok().body(authService.login(loginRequest));
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<SignupResponse> signup(@Valid @RequestBody SignupRequest signupRequest){
        LOGGER.debug("signup method User : {}", signupRequest.toString());
        return new ResponseEntity<>( authService.signup(signupRequest), HttpStatus.CREATED);
    }

    @PostMapping(value = "/refreshToken")
    public ResponseEntity<AuthenticationResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){
        LOGGER.debug("refreshToken method refreshTokenRequest : {}", refreshTokenRequest.toString());
        return new ResponseEntity<>(authService.refreshToken(refreshTokenRequest), HttpStatus.OK);
    }
}
