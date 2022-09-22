package com.security.springsecuritydemo.security.jwt;

import com.security.springsecuritydemo.model.Enums.Status;
import com.security.springsecuritydemo.model.Role;
import com.security.springsecuritydemo.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {
    public JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getLogin(),
                user.getFirstname(),
                user.getPassword(),
                mapToGrantedAuthority(new ArrayList<>(user.getRoles())),
                user.getStatus().equals(Status.ACTIVE),
                user.getUpdated());
    }

    // создает GrantedAuthority на оснвоании ролей юзера
    private static List<GrantedAuthority> mapToGrantedAuthority(List<Role> usersRole) {
        return usersRole.stream()
                .map(role ->  new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
