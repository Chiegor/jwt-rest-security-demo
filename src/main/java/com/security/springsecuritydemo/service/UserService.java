package com.security.springsecuritydemo.service;

import com.security.springsecuritydemo.model.User;

import java.util.List;

public interface UserService {
    User register(User user);

    List<User> getAll();

    User findByLogin(String login);

    User findById(Long id);

    void delete(Long id);
}
