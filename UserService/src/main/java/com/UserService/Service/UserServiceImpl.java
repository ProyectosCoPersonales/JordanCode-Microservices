package com.UserService.Service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

import com.UserService.Model.User;
import com.UserService.Repository.UserRepository;
import com.UserService.dto.UserOrderResponse;
import com.UserService.dto.UserRequest;
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
                        .email(user.getEmail())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public UserOrderResponse findUserById(Long idUser) {
        User user = userRepository.findById(idUser).orElseThrow();
        return UserOrderResponse.builder()
        .id(user.getId())
        .name(user.getName())
        .email(user.getEmail())
        .address(user.getAddress())
        .phone(user.getPhone()).build();
    }

    @Override
    public User updateUser(Long idUser, UserRequest userRequest){
        User user = userRepository.findById(idUser).orElseThrow();
        user.setAddress(userRequest.getAddress());
        user.setPhone(userRequest.getPhone());
        userRepository.save(user);
        return user;
    }
}
