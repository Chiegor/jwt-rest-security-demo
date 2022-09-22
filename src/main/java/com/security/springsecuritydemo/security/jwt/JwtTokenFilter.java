package com.security.springsecuritydemo.security.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


public class JwtTokenFilter extends GenericFilterBean {

    private JwtTokenProvider provider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.provider = jwtTokenProvider;
    }

    // анализирует наш запрос
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = provider.resolveToken((HttpServletRequest) servletRequest); // получаем токен из servletRequest и кастуем его

        if (token != null && provider.validateToken(token)) {
            Authentication authentication = provider.getAuthentication(token);

            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication); // аутентифицируем запрос
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);

    }
}
