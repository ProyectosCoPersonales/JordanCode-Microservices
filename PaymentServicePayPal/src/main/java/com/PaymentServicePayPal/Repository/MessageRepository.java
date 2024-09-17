package com.PaymentServicePayPal.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.PaymentServicePayPal.Message.Message;


public interface MessageRepository extends JpaRepository<Message,Long>{
    Message findByOrderId(Long orderId);
}
