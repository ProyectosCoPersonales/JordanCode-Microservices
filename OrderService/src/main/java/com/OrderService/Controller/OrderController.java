package com.OrderService.Controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.OrderService.Http.Request.CreateOrderRequest;
import com.OrderService.Model.Order;
import com.OrderService.Service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> CreateOrder(@RequestBody CreateOrderRequest createOrderRequest){
        return new ResponseEntity<>(orderService.createOrder(
            createOrderRequest.getEmail(), createOrderRequest.getUsername()),HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> GetOrderById(@RequestParam Long orderId){
        return new ResponseEntity<>(orderService.getOrderbyId(orderId),HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<List<Order>> GetOrdersByUser(@RequestParam String email){
        return new ResponseEntity<>(orderService.getOrdersByUser(email),HttpStatus.OK);
    }

    @PostMapping("/bring/{orderId}")
    public ResponseEntity<Order> BringOrder(@RequestParam Long orderId){
        return new ResponseEntity<>(orderService.bringOrder(orderId),HttpStatus.OK);
    }

    @PostMapping("/complete/{orderId}")
    public ResponseEntity<Order> CompleteOrder(@RequestParam Long orderId){
        return new ResponseEntity<>(orderService.completeOrder(orderId),HttpStatus.OK);
    }
}
