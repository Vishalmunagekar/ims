package com.app.ims.security.config;

import com.app.ims.common.Constants;
import com.app.ims.security.filter.JwtAuthenticationFilter;
import com.app.ims.security.service.ImsUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final ImsUserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtRequestFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); //return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/index.html/**", "/css/**", "/js/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/api/application/init", "/api/application/login").permitAll()
                .antMatchers("/api/user/**").hasAuthority(Constants.ADMIN_ROLE)
                .antMatchers("/api/order/**", "/api/product/**","/api/test/**").hasAnyAuthority(Constants.ADMIN_ROLE, Constants.USER_ROLE)
                .anyRequest()
                .authenticated();
        httpSecurity.headers().frameOptions().disable(); //to access h2 database console
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
