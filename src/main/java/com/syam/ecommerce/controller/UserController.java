package com.syam.ecommerce.controller;

import com.syam.ecommerce.dto.UserDto;
import com.syam.ecommerce.exception.UserNotFoundException;
import com.syam.ecommerce.exception.UsernameAlreadyExistsException;
import com.syam.ecommerce.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/users")
public class UserController {
    //Handles user registration, login, profile management

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto userDto){
        try {
            userService.registerUser(userDto);
            return ResponseEntity.ok("User registered successfully");
        } catch (UsernameAlreadyExistsException ex) {
            logger.error("Registration failed: {}", ex.getMessage());
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            logger.error("Error during registration: {}", ex.getMessage());
            return ResponseEntity.internalServerError().body("Internal Server Error");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody UserDto userDto) {
        try {
            String token = userService.loginUser(userDto);
            return ResponseEntity.ok().body(token);
        } catch (UserNotFoundException ex) {
            logger.error("Login failed: {}", ex.getMessage());
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            logger.error("Error during login: {}", ex.getMessage());
            return ResponseEntity.internalServerError().body("Internal Server Error");
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser (@PathVariable Long userId, @Valid @RequestBody UserDto userDto) {
        try {
            userService.updateUser(userId, userDto);
            return ResponseEntity.ok("User updated successfully");
        } catch (UserNotFoundException ex) {
            logger.error("Update failed: {}", ex.getMessage());
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            logger.error("Error during user update: {}", ex.getMessage());
            return ResponseEntity.internalServerError().body("Internal Server Error");
        }
    }

}
