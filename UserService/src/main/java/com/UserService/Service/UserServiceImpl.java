package com.UserService.Service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

import com.UserService.Model.User;
import com.UserService.Repository.UserRepository;
import com.UserService.dto.UserOrderResponse;
import com.UserService.dto.UserResponse;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserResponse> findAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> UserResponse.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse findUserById(Long id) {
        return userRepository.findById(id)
                .map(user -> UserResponse.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .build())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id)); 
    }

    @Override
    public UserOrderResponse findUserByEmail(String Email) {
        User user = userRepository.findByEmail(Email);
        return UserOrderResponse.builder()
        .id(user.getId())
        .name(user.getName())
        .email(user.getEmail())
        .address(user.getAddress())
        .phone(user.getPhone()).build();
    }
}
