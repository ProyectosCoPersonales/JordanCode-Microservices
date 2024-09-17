package com.OrderService.Controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.OrderService.Model.Order;
import com.OrderService.Service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/createOrder/{userId}")
    public ResponseEntity<Order> CreateOrder(@PathVariable Long userId){
        return new ResponseEntity<>(orderService.createOrder(userId),HttpStatus.OK);
    }

    @GetMapping("/show/{orderId}")
    public ResponseEntity<?> GetOrderById(@PathVariable Long orderId){
        return new ResponseEntity<>(orderService.getOrderbyId(orderId),HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<List<Order>> GetOrdersByUser(@PathVariable String email){
        return new ResponseEntity<>(orderService.getOrdersByUser(email),HttpStatus.OK);
    }

    @PostMapping("/bring/{orderId}")
    public ResponseEntity<Order> BringOrder(@PathVariable Long orderId){
        return new ResponseEntity<>(orderService.bringOrder(orderId),HttpStatus.OK);
    }

    @PostMapping("/complete/{orderId}")
    public ResponseEntity<Order> CompleteOrder(@PathVariable Long orderId){
        return new ResponseEntity<>(orderService.completeOrder(orderId),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<?> DeleteOrder(@PathVariable Long orderId){
        orderService.DeleteOrder(orderId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
