package com.OrderService.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.OrderService.dto.UserInfo;

@FeignClient(name="UserService", url="localhost:8090/api/users")
public interface UserClient {
    @GetMapping("/info/{idUser}")
    UserInfo getAllInformationById(@PathVariable Long idUser);
}
