package com.wasted.backend.security;

import com.wasted.backend.core.user.repository.UserRepository;
import com.wasted.backend.core.user.service.UserService;
import com.wasted.backend.core.user.service.converter.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.filter.CompositeFilter;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableOAuth2Client
@EnableAuthorizationServer
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final UserService userService;
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Qualifier("oauth2ClientContext")
    @Autowired
    OAuth2ClientContext oauth2ClientContext;

    private final String[] unauthorizedUrls = new String[]{
            "/",
            "/login**",
            "/error**",
            "/oauth2/**",
            "/api/user/unauthenticated",
            "/webjars/**"
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
            .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            .and().authorizeRequests()
                .antMatchers(unauthorizedUrls)
                    .permitAll()
                .anyRequest()
                    .authenticated()
            .and().logout().logoutSuccessUrl("/").permitAll()
            .and().addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class)
            .exceptionHandling()
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/"));

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

    private Filter ssoFilter() {
        CompositeFilter filter = new CompositeFilter();
        List<Filter> filters = new ArrayList<>();
        filters.add(ssoFilter(facebook(), "/login/facebook"));
        filters.add(ssoFilter(google(), "/login/google"));
        filter.setFilters(filters);
        return filter;
    }

    private Filter ssoFilter(ClientResources client, String path) {
        OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter(path);
        OAuth2RestTemplate template = new OAuth2RestTemplate(client.getClient(), oauth2ClientContext);
        filter.setRestTemplate(template);
        UserInfoTokenServices tokenServices = new UserInfoTokenServices(
                client.getResource().getUserInfoUri(), client.getClient().getClientId());
        tokenServices.setRestTemplate(template);
        filter.setTokenServices(tokenServices);
        return filter;
    }

    @Bean
    @ConfigurationProperties("google")
    public ClientResources google() {
        return new ClientResources();
    }

    @Bean
    @ConfigurationProperties("facebook")
    public ClientResources facebook() {
        return new ClientResources();
    }

    @Bean
    public FilterRegistrationBean<OAuth2ClientContextFilter> oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
        FilterRegistrationBean<OAuth2ClientContextFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }
}

