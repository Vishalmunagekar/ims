package com.app.ims.controller;

import com.app.ims.model.Role;
import com.app.ims.model.User;
import com.app.ims.repository.RoleRepository;
import com.app.ims.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
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

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        LOGGER.debug("getUserById : {}",id);
        return new ResponseEntity<User>(userRepository.findById(id).get(), HttpStatus.OK);
    }

    @GetMapping(value = "/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username){
        LOGGER.debug("getUserByUsername : {}",username);
        return new ResponseEntity<>(userRepository.findByUsername(username), HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody User user){
        LOGGER.debug("createUser : {}",user.toString());
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        newUser.setContact(user.getContact());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());

        user.getRoles().forEach(role -> {
            Optional<Role> optionalRole = roleRepository.findByRoleName(role.getRoleName());
            newUser.addRoles(optionalRole.get());
        });
        return new ResponseEntity<>(userRepository.save(newUser), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateUserById(@PathVariable Long id, @RequestBody User user){
        LOGGER.debug("updateUserById id : {}", id);
        LOGGER.debug("updateUserById User : {}",user.toString());

        User optionalUser = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id : " + id));
        optionalUser.setUsername(user.getUsername());
        optionalUser.setPassword(user.getPassword());
        optionalUser.setContact(user.getContact());
        optionalUser.setFirstName(user.getFirstName());
        optionalUser.setLastName(user.getLastName());
        optionalUser.setRoles(user.getRoles());
        return new ResponseEntity<>(userRepository.save(optionalUser), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id){
        userRepository.deleteById(id);
        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }
}
