package com.app.ims.controller;


import com.app.ims.Constants;
import com.app.ims.model.User;
import com.app.ims.repository.UserRepository;
import com.app.ims.security.filter.JwtAuthenticationFilter;
import com.app.ims.security.jwt.JwtUtil;
import com.app.ims.service.ApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController()
@RequestMapping(value = "api/application")
public class ApplicationController {

    private final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping(value = "/init")
    public ResponseEntity<?> init(){
        URI selfLink = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
        if(applicationService.init1() && applicationService.init2()){
            return ResponseEntity.created(selfLink).body("Success!");
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody User user){
        LOGGER.debug("in login method User : {}", user.toString());
        URI selfLink = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtUtil.generateToken(authentication);
        String finalToken = "Bearer " + token;
        return ResponseEntity.ok().header(Constants.HEADER_STRING, "Bearer " + token).body(finalToken);
    }

    @GetMapping(value = "/users")
    public ResponseEntity<?> getUsers(){
        URI selfLink = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
        return ResponseEntity.ok().body(userRepository.findAll());
    }
}
