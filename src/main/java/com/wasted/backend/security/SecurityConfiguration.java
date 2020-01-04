package com.wasted.backend.security;

import com.wasted.backend.core.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final UserService userService;

    private final String[] unauthorizedUrls = new String[]{
            "/",
            "/login**",
            "/error**",
            "/oauth2/**",
            "/api/user/ unauthenticated"
    };

    @Autowired
    public SecurityConfiguration(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.cors().disable();
        http.csrf().disable();

        // add oauth2 check for every request
        http.authorizeRequests()
                    .antMatchers(unauthorizedUrls)
                    .permitAll()
                .anyRequest()
                    .authenticated()
                .and().logout().logoutSuccessUrl("/").permitAll()
                .and().oauth2Login().successHandler(loginSuccessHandler()).defaultSuccessUrl("/");

        // disable redirects after access failed
        http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
            if (authException != null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().print("Unauthorizated....");
            }
        });
    }

    private LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler(userService);
    }
}
