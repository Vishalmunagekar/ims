package com.app.ims.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(value = "api/test")
public class TestController {

    @GetMapping(value = "/welcome")
    public ResponseEntity<?> welcome(){
        return ResponseEntity.ok().body("Welcome User to spring boot application");
    }

}
