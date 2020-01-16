package com.wasted.backend.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final String[] unauthorizedUrls = new String[]{
            "/",
            "/login**",
            "/error**",
            "/oauth2/**",
            "/api/user/unauthenticated"
    };

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.cors().disable()
            .csrf().disable()
            .authorizeRequests()
            .anyRequest()
            .permitAll();
    }
}
