package com.OrderService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.OrderService.Model.Order;
import java.util.List;



public interface OrderRepository extends JpaRepository<Order, Long>{
    List<Order> findByUserEmail(String userEmail);
}
