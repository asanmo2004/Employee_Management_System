package com.ems.controller;

import com.ems.model.User;
import com.ems.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        logger.info("Fetching user by username: {}", username);
        Optional<User> user = userService.getUserByUsername(username);
        if (user.isPresent()) {
            logger.debug("User found: {}", user.get().getUsername());
            return ResponseEntity.ok(user.get());
        } else {
            logger.warn("User not found with username: {}", username);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        logger.info("Adding new user: {}", user.getUsername());
        return userService.addUser(user);
    }
}
