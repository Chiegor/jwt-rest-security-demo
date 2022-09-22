package com.security.springsecuritydemo.security.jwt;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private JwtTokenProvider provider;

    public JwtConfigurer(JwtTokenProvider provider) {
        this.provider = provider;
    }

    // конфигурация секьюрити таким образом, чтобы каждый запрос, перед тем как быть переданным серверу должен пройти проверку
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        JwtTokenFilter filter = new JwtTokenFilter(provider);
        httpSecurity.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);


    }
}
