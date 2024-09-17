package com.ShoppingCartService.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ShoppingCartService.dto.UserDTO;

@FeignClient(name="UserService", url="localhost:8090/api/users")
public interface UserClient {
    @GetMapping("/{id}")
    UserDTO getUserById(@PathVariable Long id);
}