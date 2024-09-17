package com.Authentication.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.Authentication.Model.User;
import com.Authentication.Service.AuthService;
import com.Authentication.dto.AuthRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authservice;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public String addNewUser(@RequestBody User user) {
        return authservice.saveUser(user);
    }

    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authRequest.getName(), authRequest.getPassword()) // Cambia de getUsername() a getName()
        );
        if (authenticate.isAuthenticated()) {
            return authservice.generateToken(authRequest.getName()); 
        } else {
            throw new RuntimeException("invalid access");
        }
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        authservice.validateToken(token);
        return "Token is valid";
    }
}
