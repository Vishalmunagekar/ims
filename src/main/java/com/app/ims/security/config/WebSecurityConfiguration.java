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
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
        return new BCryptPasswordEncoder(); //return NoOpPasswordEncoder.getInstance();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

//    @Bean
//    public AuthenticationEntryPoint authenticationEntryPoint(){
//        return new ApplicationAuthenticationEntryPoint();
//    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/swagger-ui.html?configUrl=/v3/api-docs/swagger-config",
                "/v3/api-docs/swagger-config",
                "/v3/api-docs/**",
                "/webjars/**");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        handleAuthorities(httpSecurity);
        httpSecurity.headers().frameOptions().disable(); //to access h2 database console
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    private void handleAuthorities(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().and()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers(Constants.ALLOWED_URL_PATHS).permitAll()
                // this below line can bypass your spring security.
                //.antMatchers("/v2/api-docs", "/swagger-resources/configuration/ui", "/swagger-ui/**", "/swagger-resources", "/swagger-resources/configuration/security", "/swagger-ui.html", "/webjars/**").permitAll()
                .antMatchers("/api/init","/api/auth/**").permitAll()
                .antMatchers("/api/user/**").hasAuthority(Constants.ADMIN_ROLE)
                .antMatchers("/api/order/**", "/api/product/**","/api/test/**").hasAnyAuthority(Constants.ADMIN_ROLE, Constants.USER_ROLE)
                .anyRequest()
                .authenticated();
    }
}
