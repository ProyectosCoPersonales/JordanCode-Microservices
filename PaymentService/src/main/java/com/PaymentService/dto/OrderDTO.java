package com.PaymentService.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long id;
    private String userName;
    private String userEmail;
    private String userAddress;
    private String userPhone;
    private List<OrderItemDTO> items;
    private Double totalAmount;
    private String orderStatus;
}

