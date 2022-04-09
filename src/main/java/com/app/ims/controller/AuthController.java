package com.app.ims.controller;

import com.app.ims.common.Constants;
import com.app.ims.model.User;
import com.app.ims.security.model.AuthenticationResponse;
import com.app.ims.security.model.LoginRequest;
import com.app.ims.security.model.RefreshTokenRequest;
import com.app.ims.service.AuthService;
import com.app.ims.service.RefreshTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/auth")
public class AuthController {

    private final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private AuthService authService;

    @PostMapping(value = "/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest loginRequest){
        LOGGER.debug("login method loginRequest : {}", loginRequest.toString());
        return ResponseEntity.ok().body(authService.login(loginRequest));
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<?> signup(@RequestBody User user){
        LOGGER.debug("signup method User : {}", user.toString());
        User signup = authService.signup(user);
        return new ResponseEntity<>(signup, HttpStatus.CREATED);
    }

    @PostMapping(value = "/refreshToken")
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){
        LOGGER.debug("refreshToken method refreshTokenRequest : {}", refreshTokenRequest.toString());
        return new ResponseEntity<>(authService.refreshToken(refreshTokenRequest), HttpStatus.OK);
    }
}
