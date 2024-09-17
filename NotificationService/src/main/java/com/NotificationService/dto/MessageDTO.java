package com.NotificationService.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    private Long id;
    private Long orderId;
    private String userName;
    private String userEmail;
    private String userAddress;
    private String userPhone;
    private String orderStatus;
    private LocalDateTime orderDate;
    private Double totalAmount;
}
