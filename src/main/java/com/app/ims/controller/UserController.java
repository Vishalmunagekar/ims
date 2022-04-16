package com.app.ims.controller;

import com.app.ims.dto.UpdateUserRequest;
import com.app.ims.dto.UpdateUserResponse;
import com.app.ims.model.User;
import com.app.ims.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(value = "api/user")
public class UserController {

    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getUserById(@NotNull @PathVariable Long id){
        LOGGER.debug("getUserById : {}",id);
        return new ResponseEntity<User>(userService.getUserById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/username/{username}")
    public ResponseEntity<User> getUserByUsername(@NotBlank @PathVariable String username){
        LOGGER.debug("getUserByUsername : {}",username);
        return new ResponseEntity<>(userService.getUserByUsername(username), HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UpdateUserResponse> updateUserById(@NotNull @PathVariable Long id, @Valid @RequestBody UpdateUserRequest updateUserRequest){
        LOGGER.debug("updateUserById id : {} and updateUserRequest : {}", id, updateUserRequest.toString());
        return new ResponseEntity<>(userService.updateUserById(id, updateUserRequest), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteUserById(@NotNull @PathVariable Long id){
        LOGGER.debug("deleteUserById id : {}", id);
        userService.deleteUserById(id);
        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }
}
