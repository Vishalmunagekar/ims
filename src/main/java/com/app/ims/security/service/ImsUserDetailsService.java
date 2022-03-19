package com.app.ims.security.service;

import com.app.ims.model.User;
import com.app.ims.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ImsUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImsUserDetailsService.class);
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            String s = String.format("The username %s doesn't exist", username);
            UsernameNotFoundException exception = new UsernameNotFoundException(s);
            LOGGER.error(s,exception);
            throw exception;
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        });

        LOGGER.debug("WITH {} AUTHORITIES", grantedAuthorities.size());
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
