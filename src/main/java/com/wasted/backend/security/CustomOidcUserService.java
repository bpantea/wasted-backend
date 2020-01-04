package com.wasted.backend.security;

import com.wasted.backend.core.user.api.dtos.GoogleUserDto;
import com.wasted.backend.core.user.domain.User;
import com.wasted.backend.core.user.repository.UserRepository;
import com.wasted.backend.core.user.service.converter.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CustomOidcUserService extends OidcUserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Autowired
    public CustomOidcUserService(final UserRepository userRepository,
                                 final UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        Map<String, Object> attributes = oidcUser.getAttributes();
        GoogleUserDto userDto = GoogleUserDto.builder()
                .email((String) attributes.get("email"))
                .displayName((String) attributes.get("name"))
                .id((String) attributes.get("sub"))
                .profilePicture((String) attributes.get("picture"))
                .build();
        updateUser(userDto);
        return oidcUser;
    }

    private void updateUser(GoogleUserDto userInfo) {
        User user = userRepository.findByEmail(userInfo.getEmail());
        if(user == null) {
            user = new User();
        }
        user = userConverter.convert(userInfo, user);
        userRepository.save(user);
    }
}
