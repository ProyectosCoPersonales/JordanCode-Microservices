package com.Authentication.Config;

import com.Authentication.Model.User;
import com.Authentication.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException { 
        User credential = userRepository.findByName(name); 
        if (credential == null) {
            throw new UsernameNotFoundException("User not found with name: " + name);
        }
        return new CustomUserDetails(credential);
    }
}
