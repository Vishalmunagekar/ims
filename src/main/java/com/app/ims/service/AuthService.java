package com.app.ims.service;


import com.app.ims.common.ApplicationRoleNotFoundException;
import com.app.ims.common.Constants;
import com.app.ims.dto.*;
import com.app.ims.model.Role;
import com.app.ims.model.User;
import com.app.ims.repository.RoleRepository;
import com.app.ims.repository.UserRepository;
import com.app.ims.security.jwt.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

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
    private RefreshTokenService refreshTokenService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthenticationResponse login(LoginRequest loginRequest){
        LOGGER.debug("login method LoginRequest : {}", loginRequest.toString());
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        LOGGER.debug("user {} successfully logged in", loginRequest.getUsername());
        return AuthenticationResponse.builder()
                .authenticationToken(jwtUtil.generateToken(authentication, loginRequest.getUsername()))
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiration(new Date(System.currentTimeMillis() + Constants.TokenExpiration))
                .username(loginRequest.getUsername())
                .build();
    }

    public SignupResponse signup(SignupRequest signupRequest){
        LOGGER.debug("signup method signupRequest : {}", signupRequest.getUsername());
        Set<Role> roles = new HashSet<>();
        signupRequest.getRoles().forEach((role) -> {
            Role role1 = roleRepository.findByRoleName(role.getRoleName().toUpperCase()).orElseThrow(
                    () -> new ApplicationRoleNotFoundException("Role " + role.getRoleName().toUpperCase() + " not found!")
            );
            LOGGER.debug("found role : {}", role.getRoleName());
            roles.add(role1);
        });

        User new_user = new User();
        new_user.setUsername(signupRequest.getUsername());
        new_user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        new_user.setFirstName(signupRequest.getFirstName());
        new_user.setLastName(signupRequest.getLastName());
        new_user.setContact(signupRequest.getContact());
        new_user.setRoles(roles);
        User save = userRepository.save(new_user);
        LOGGER.debug("New user saved with id : {}", save.getId());
        return new SignupResponse(save.getId(),save.getUsername(),save.getFirstName(),save.getLastName(),save.getContact(),save.getRoles());
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
        LOGGER.debug("refreshToken method refreshTokenRequest : {}", refreshTokenRequest.toString());
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        User user = userRepository.findByUsername(refreshTokenRequest.getUsername());
        LOGGER.debug("findByUsername method user : {}", user.toString());
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        user.getRoles().forEach(role -> grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleName())));
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), null, grantedAuthorities);
        return AuthenticationResponse.builder()
                .authenticationToken(jwtUtil.generateToken(authentication, user.getUsername()))
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiration(new Date(System.currentTimeMillis() + Constants.TokenExpiration))
                .username(user.getUsername())
                .build();
    }

}
