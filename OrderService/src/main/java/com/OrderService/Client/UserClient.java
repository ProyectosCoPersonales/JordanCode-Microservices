package com.OrderService.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.OrderService.dto.UserInfo;

@FeignClient(name="UserService", url="localhost:8080/api/users")
public interface UserClient {

    @GetMapping("/UserInfo/{email}")
    UserInfo getAllInformationByEmail(@PathVariable String email);
}
