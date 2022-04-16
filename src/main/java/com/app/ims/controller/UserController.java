package com.app.ims.controller;

import com.app.ims.dto.UpdateUserRequest;
import com.app.ims.dto.UpdateUserResponse;
import com.app.ims.model.Role;
import com.app.ims.model.User;
import com.app.ims.repository.RoleRepository;
import com.app.ims.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/user")
public class UserController {

    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getUserById(@NotNull @PathVariable Long id){
        LOGGER.debug("getUserById : {}",id);
        return new ResponseEntity<User>(userRepository.findById(id).get(), HttpStatus.OK);
    }

    @GetMapping(value = "/username/{username}")
    public ResponseEntity<User> getUserByUsername(@NotBlank @PathVariable String username){
        LOGGER.debug("getUserByUsername : {}",username);
        return new ResponseEntity<>(userRepository.findByUsername(username), HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UpdateUserResponse> updateUserById(@NotNull @PathVariable Long id, @Valid @RequestBody UpdateUserRequest updateUserRequest){
        LOGGER.debug("updateUserById id : {}", id);
        LOGGER.debug("updateUserById User : {}",updateUserRequest.toString());

        User optionalUser = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id : " + id));
        optionalUser.setUsername(updateUserRequest.getUsername());
        optionalUser.setPassword(passwordEncoder.encode(updateUserRequest.getPassword()));
        optionalUser.setContact(updateUserRequest.getContact());
        optionalUser.setFirstName(updateUserRequest.getFirstName());
        optionalUser.setLastName(updateUserRequest.getLastName());
        User update = userRepository.save(optionalUser);
        return new ResponseEntity<>(new UpdateUserResponse(update.getId(),
                update.getUsername(),
                update.getFirstName(),
                update.getLastName(),
                update.getContact(),
                update.getRoles()), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteUserById(@NotNull @PathVariable Long id){
        userRepository.deleteById(id);
        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }
}
