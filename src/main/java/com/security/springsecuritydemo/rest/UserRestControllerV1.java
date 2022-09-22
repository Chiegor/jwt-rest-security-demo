package com.security.springsecuritydemo.rest;

import com.security.springsecuritydemo.dto.UserDto;
import com.security.springsecuritydemo.model.User;
import com.security.springsecuritydemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/users/")
public class UserRestControllerV1 {

    private final UserService service;

    @Autowired
    public UserRestControllerV1(UserService service) {
        this.service = service;
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") Long id) {
        User user = service.findById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        UserDto result = UserDto.toUserDto(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
