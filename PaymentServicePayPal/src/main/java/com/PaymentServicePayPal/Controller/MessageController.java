package com.PaymentServicePayPal.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PaymentServicePayPal.Message.Message;
import com.PaymentServicePayPal.Service.MessageService;

@RequestMapping("/api/messages")
@RestController
public class MessageController {
    
    @Autowired
    private MessageService messageService;

    @GetMapping("/{orderId}")
    public Message GetMessageByOrderId(@PathVariable Long orderId){
        return messageService.findMessagebyOrderId(orderId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMessage(@PathVariable Long id){
        return new ResponseEntity<>(messageService.deleteMessageById(id),HttpStatus.OK);
    }
}
