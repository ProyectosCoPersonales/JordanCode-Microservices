package com.Authentication.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Authentication.Model.User;
import com.Authentication.Repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public String saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "user added to the system";
    }

    public String generateToken(String name) { 
        return jwtService.generateToken(name); 
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }
}
