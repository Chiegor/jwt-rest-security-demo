package com.security.springsecuritydemo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.security.springsecuritydemo.model.User;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private Long id;
    private String login;
    private String firstname;

    public User toUser() {
        User user = new User();
        user.setId(id);
        user.setLogin(login);
        user.setFirstname(firstname);
        return user;
    }

    public static UserDto toUserDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setLogin(user.getLogin());
        dto.setFirstname(user.getFirstname());
        return dto;
    }
}
