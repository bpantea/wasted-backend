package com.wasted.backend.core.user.api;

import com.wasted.backend.core.user.domain.User;
import com.wasted.backend.core.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
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
}
