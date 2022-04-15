package com.app.ims.controller;


import com.app.ims.repository.UserRepository;
import com.app.ims.security.filter.JwtAuthenticationFilter;
import com.app.ims.security.jwt.JwtUtil;
import com.app.ims.service.InitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController()
@RequestMapping(value = "api/")
public class InitController {

    private final Logger LOGGER = LoggerFactory.getLogger(InitController.class);
    @Autowired
    private InitService initService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping(value = "/init")
    public ResponseEntity<String> init(){
        URI selfLink = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
        if(initService.init1() && initService.init2()){
            return ResponseEntity.created(selfLink).body("Success!");
        }
        return ResponseEntity.notFound().build();
    }
}
