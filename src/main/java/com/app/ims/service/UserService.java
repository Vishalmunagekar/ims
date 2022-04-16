package com.app.ims.service;

import com.app.ims.dto.UpdateUserRequest;
import com.app.ims.dto.UpdateUserResponse;
import com.app.ims.model.User;
import com.app.ims.repository.RoleRepository;
import com.app.ims.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class UserService {

    private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getUserById(Long id){
        LOGGER.debug("getUserById : {}",id);
        return userRepository.findById(id).get();
    }

    public User getUserByUsername(String username){
        LOGGER.debug("getUserByUsername : {}",username);
        return userRepository.findByUsername(username);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public UpdateUserResponse updateUserById(Long id,UpdateUserRequest updateUserRequest){
        LOGGER.debug("updateUserById id : {}", id);
        LOGGER.debug("updateUserById User : {}",updateUserRequest.toString());

        User optionalUser = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id : " + id));
        optionalUser.setUsername(updateUserRequest.getUsername());
        optionalUser.setPassword(passwordEncoder.encode(updateUserRequest.getPassword()));
        optionalUser.setContact(updateUserRequest.getContact());
        optionalUser.setFirstName(updateUserRequest.getFirstName());
        optionalUser.setLastName(updateUserRequest.getLastName());
        User update = userRepository.save(optionalUser);
        return new UpdateUserResponse(update.getId(),
                update.getUsername(),
                update.getFirstName(),
                update.getLastName(),
                update.getContact(),
                update.getRoles());
    }

    public Boolean deleteUserById(Long id){
        userRepository.deleteById(id);
        return Boolean.TRUE;
    }

}
