package com.ShoppingCartService.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ShoppingCartService.dto.UserDTO;

@FeignClient(name="userservice")
public interface UserClient {
    @GetMapping("/api/users/info/{idUser}")
    UserDTO getAllInformationById(@PathVariable Long idUser);
}
