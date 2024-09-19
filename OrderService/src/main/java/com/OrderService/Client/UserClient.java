package com.OrderService.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.OrderService.dto.UserInfo;

@FeignClient(name = "userservice")
public interface UserClient {
    @GetMapping("/api/users/info/{idUser}")
    UserInfo getAllInformationById(@PathVariable Long idUser);
}
