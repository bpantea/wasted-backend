package com.wasted.backend.core.user.api;

import com.wasted.backend.core.user.api.dtos.ExtraFieldsUser;
import com.wasted.backend.core.user.api.dtos.GoogleUserDto;
import com.wasted.backend.core.user.domain.User;
import com.wasted.backend.core.user.repository.UserRepository;
import com.wasted.backend.core.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/test")
    public String getTest(Principal principal) {
        return "Hello, " + principal.getName();
    }

    @GetMapping("/principal")
    public Principal getPrincipal(Principal principal) {
        return principal;
    }

    @GetMapping("/unauthenticated")
    public ResponseEntity<Map<String, String>> getUnauthenticated() {
        Map<String, String> map = new HashMap();
        map.put("response", "Hello, World!");
        return ResponseEntity.ok(map);
    }

    @PostMapping("/full-user")
    public User saveUser(User user) {
        return userService.saveUser(user);
    }

    @PostMapping("/google-user")
    public User saveUser(@RequestBody GoogleUserDto user) {
        logger.info("google-user");
        return userService.saveUser(user);
    }

    // todo id token login maybe?

    @GetMapping("/get/{id}")
    public User getUser(@PathVariable("id") String id) {
        return userService.getUser(id);
    }

    @PutMapping("/extra-fields/{id}")
    public User putExtraFields(@PathVariable("id") String userId, @RequestBody ExtraFieldsUser user) {
        logger.info("extra fields");
        return userService.putExtraFields(userId, user);
    }
}
