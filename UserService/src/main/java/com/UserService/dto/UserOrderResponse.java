package com.UserService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserOrderResponse {
    private Long id;
    private String name;
    private String email;
    private String address;
    private String phone;
}
