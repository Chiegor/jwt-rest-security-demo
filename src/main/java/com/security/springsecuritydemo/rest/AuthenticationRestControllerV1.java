package com.security.springsecuritydemo.rest;

import com.security.springsecuritydemo.dto.AuthenticationRequestDto;
import com.security.springsecuritydemo.model.User;
import com.security.springsecuritydemo.security.jwt.JwtTokenProvider;
import com.security.springsecuritydemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthenticationRestControllerV1 {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider provider;

    @Autowired
    private UserService service;

    public AuthenticationRestControllerV1(AuthenticationManager authenticationManager, JwtTokenProvider provider, UserService service) {
        this.authenticationManager = authenticationManager;
        this.provider = provider;
        this.service = service;
    }

    public ResponseEntity login(@RequestBody AuthenticationRequestDto dto) {
        try {
            String login = dto.getLogin();
            //на основании данного логина и пароя дай нам аутентификацию
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, dto.getPassword()));
            User user = service.findByLogin(login);

            if (user == null) {
                throw new UsernameNotFoundException("User with login: " + login + " not found");
            }

            String token = provider.createToken(login, user.getRoles());

            // хотя можно создать отдельную сущность и зранить в ней эти данные authenticate response
            Map<Object, Object> response = new HashMap<>();
            response.put("login", login);
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid credentials");
        }

    }


}
