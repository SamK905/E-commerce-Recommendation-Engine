package com.syam.ecommerce.service;

import com.syam.ecommerce.dto.UserDto;
import com.syam.ecommerce.exception.UserNotFoundException;
import com.syam.ecommerce.exception.UsernameAlreadyExistsException;
import com.syam.ecommerce.model.User;
import com.syam.ecommerce.repository.UserRepository;
import com.syam.ecommerce.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public void registerUser(UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException("Username already exists: " + userDto.getUsername());
        }

        User newUser = new User();
        newUser.setUsername(userDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        newUser.setEmail(userDto.getEmail());
        userRepository.save(newUser);
    }
    public String loginUser(UserDto userDto) {
        User user = userRepository.findByUsername(userDto.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + userDto.getUsername()));

        if (!passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            throw new UserNotFoundException("Invalid login credentials");
        }

        return jwtTokenProvider.createToken(user.getUsername());
    }
    public void updateUser(Long userId, UserDto userDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        // Update other fields as necessary

        userRepository.save(user);
    }
}
