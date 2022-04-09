package com.app.ims.controller;

import com.app.ims.common.Constants;
import com.app.ims.model.User;
import com.app.ims.service.AuthService;
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
    public ResponseEntity<String> login(@RequestBody User user){
        LOGGER.debug("login method User : {}", user.toString());
        String bearer_token = authService.login(user);
        return ResponseEntity.ok().header(Constants.HEADER_STRING, bearer_token).body(bearer_token);
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<?> signup(@RequestBody User user){
        LOGGER.debug("signup method User : {}", user.toString());
        User signup = authService.signup(user);
        return new ResponseEntity<>(signup, HttpStatus.CREATED);
    }

    @PostMapping(value = "/refreshToken")
    public ResponseEntity<?> refreshToken(@PathVariable String token){
        LOGGER.debug("refreshToken method token : {}", token);
        // TO DO
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
