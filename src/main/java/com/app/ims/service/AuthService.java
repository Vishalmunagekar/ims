package com.app.ims.service;


import com.app.ims.common.ApplicationRoleNotFoundException;
import com.app.ims.model.Role;
import com.app.ims.model.User;
import com.app.ims.repository.RoleRepository;
import com.app.ims.repository.UserRepository;
import com.app.ims.security.jwt.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {

    private final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private JwtUtil jwtUtil;

    public String login(User user){
        LOGGER.debug("login method User : {}", user.toString());

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return String.format("Bearer %s", jwtUtil.generateToken(authentication));
    }

    public User signup(User user){
        Set<Role> roles = new HashSet<>();

        user.getRoles().forEach((role) -> {
            Role role1 = roleRepository.findByRoleName(role.getRoleName().toUpperCase()).orElseThrow(
                    () -> new ApplicationRoleNotFoundException("Role " + role.getRoleName().toUpperCase() + " not found!")
            );
            LOGGER.debug("found role : {}", role.getRoleName());
            roles.add(role1);
        });

        User new_user = new User();
        new_user.setUsername(user.getUsername());
        new_user.setPassword(user.getPassword());
        new_user.setFirstName(user.getFirstName());
        new_user.setLastName(user.getLastName());
        new_user.setContact(user.getContact());
        new_user.setRoles(roles);
        return userRepository.save(new_user);
    }

    public String refreshToken(String token){
        LOGGER.debug("refreshToken method token : {}", token);
        // TO DO
        return "";
    }

}
