package com.security.springsecuritydemo.service.impl;

import com.security.springsecuritydemo.model.Enums.Status;
import com.security.springsecuritydemo.model.Role;
import com.security.springsecuritydemo.model.User;
import com.security.springsecuritydemo.repository.RoleRepo;
import com.security.springsecuritydemo.repository.UserRepo;
import com.security.springsecuritydemo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;
    private RoleRepo roleRepo;

    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepo userRepo, RoleRepo roleRepo, @Lazy BCryptPasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user) {
        Role roleUser = roleRepo.findByName("ROLE_USER");
        List<Role> userRoles = new LinkedList<>();
        userRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword())); // в БД будет храниться зашифрованный пароль
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);

        User registeredUser = userRepo.save(user);

        log.info("IN register - user: {} successfully registered", registeredUser);
        return registeredUser;
    }

    @Override
    public List<User> getAll() {
        List<User> list = userRepo.findAll();
        log.info("IN getAll - {} users found", list.size());
        return list;
    }

    @Override
    public User findByLogin(String login) {
        User user = userRepo.findByLogin(login);
        log.info("IN findByLogin - user: {} found by login: {}", user, login);
        return user;
    }

    @Override
    public User findById(Long id) {
        User user = userRepo.findById(id).orElse(null);
        if (user == null) {
            log.warn("IN findById - user with id not found");
        }
        log.info("IN findById - user: {} found by id", user, id);
        return user;
    }

    @Override
    public void delete(Long id) {
        userRepo.deleteById(id);
        log.info("IN delete - delete user {} with id", id);
    }
}
