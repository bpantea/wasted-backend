package com.wasted.backend.security;

import com.wasted.backend.core.user.api.dtos.GoogleUserDto;
import com.wasted.backend.core.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final UserService userService;

    @Autowired
    public LoginSuccessHandler(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;

            GoogleUserDto userDto = GoogleUserDto.builder()
                    .id(authentication.getName())
                    .displayName(token.getPrincipal().getAttribute("name"))
                    .email(token.getPrincipal().getAttribute("email"))
                    .profilePicture(token.getPrincipal().getAttribute("picture"))
                    .build();
            userService.saveUser(userDto);
        } else {
            throw new IllegalStateException("Authentication is not an instance of OAuth2AuthenticationToken");
        }
    }
}
