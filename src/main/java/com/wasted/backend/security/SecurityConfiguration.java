package com.wasted.backend.security;

import com.wasted.backend.core.user.repository.UserRepository;
import com.wasted.backend.core.user.service.UserService;
import com.wasted.backend.core.user.service.converter.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final UserService userService;
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    private final String[] unauthorizedUrls = new String[]{
            "/",
            "/login**",
            "/error**",
            "/oauth2/**",
            "/api/user/unauthenticated"
    };

    @Autowired
    public SecurityConfiguration(final UserService userService,
                                 final UserRepository userRepository,
                                 final UserConverter userConverter) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.cors().disable()
            .csrf().disable()
            .authorizeRequests()
            .anyRequest()
            .permitAll();

        // disable redirects after access failed
//        http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
//            if (authException != null) {
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                response.getWriter().print("Unauthorizated....");
//            }
//        });
    }

//    @Bean
//    public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest>
//    accessTokenResponseClient() {
//        return new DefaultAuthorizationCodeTokenResponseClient();
//    }
//
//    private LoginSuccessHandler loginSuccessHandler() {
//        return new LoginSuccessHandler(userService);
//    }
//
//    @Bean
//    public OidcUserService oidcUserService() {
//        return new CustomOidcUserService(userRepository, userConverter);
//    }
}
