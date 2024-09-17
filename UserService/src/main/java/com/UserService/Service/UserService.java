package com.UserService.Service;

import java.util.List;

import com.UserService.Model.User;
import com.UserService.dto.UserOrderResponse;
import com.UserService.dto.UserRequest;
import com.UserService.dto.UserResponse;


public interface UserService {
    List<UserResponse> findAllUsers();
    UserOrderResponse findUserById(Long idUser);
    User updateUser(Long idUser, UserRequest userRequest);
}
