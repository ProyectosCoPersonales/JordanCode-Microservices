package com.OrderService.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.OrderService.Client.ProductClient;
import com.OrderService.Client.ShoppingClient;
import com.OrderService.Client.UserClient;
import com.OrderService.Model.Order;
import com.OrderService.Model.OrderItem;
import com.OrderService.Repository.OrderRepository;
import com.OrderService.dto.CartDTO;
import com.OrderService.dto.ProductDTO;
import com.OrderService.dto.UserInfo;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductClient productClient;
    @Autowired
    private ShoppingClient shoppingClient;
    @Autowired
    private UserClient userClient;

    public String ProductName(Long idProduct){
        ProductDTO product = productClient.getProductById(idProduct);
        return product.getName();
    }

    public Optional<Order> getOrderbyId(Long idOrder){
        return orderRepository.findById(idOrder);
    }

    public List<Order> getOrdersByUser(String email){
        return orderRepository.findByUserEmail(email);
    }

    public Order createOrder(String email, String username){
        CartDTO cart = shoppingClient.sendCartData(username);
        UserInfo user = userClient.getAllInformationByEmail(email);
        List<OrderItem> orderItems = cart.getCartItems().stream()
        .map(cartItem -> OrderItem.builder()
                .productId(cartItem.getProductId())
                .productName(ProductName(cartItem.getProductId()))
                .quantity(cartItem.getQuantity())
                .unitPrice(cartItem.getUnitPrice())
                .totalPrice(cartItem.getQuantity() * cartItem.getUnitPrice())
                .build())
        .collect(Collectors.toList());
        
        Order order = Order.builder()
        .userName(username)
        .userEmail(username)
        .userAddress(user.getAddress())
        .userPhone(user.getPhone())
        .items(orderItems)
        .totalAmount(cart.getTotal())
        .orderStatus("PENDIENTE")
        .build();
        return orderRepository.save(order);
    }
    public Order bringOrder(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setOrderStatus("PROCESSING");
            return orderRepository.save(order);
        } else {
            throw new RuntimeException("Orden no encontrada con ID: " + orderId);
        }
    }
    public Order completeOrder(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setOrderStatus("COMPLETED");
            return orderRepository.save(order);
        } else {
            throw new RuntimeException("Orden no encontrada con ID: " + orderId);
        }
    }
    
}
