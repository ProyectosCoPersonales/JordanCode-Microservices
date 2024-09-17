package com.PaymentServicePayPal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long orderId;
    private String userName;
    private String userEmail;
    private String userAddress;
    private String userPhone;
    private List<OrderItemDTO> items;  
    private Double totalAmount;
    private String orderStatus;
    private LocalDateTime orderDate;
}

