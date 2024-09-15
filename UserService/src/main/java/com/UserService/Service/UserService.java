package com.UserService.Service;

import java.util.List;

import com.UserService.dto.UserOrderResponse;
import com.UserService.dto.UserResponse;

public interface UserService {
    List<UserResponse> findAllUsers();
    UserResponse findUserById(Long id);
    UserOrderResponse findUserByEmail(String Email);
}
