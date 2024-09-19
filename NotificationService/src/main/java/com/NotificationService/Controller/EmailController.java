package com.NotificationService.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.NotificationService.Service.EmailService;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/api/email")
public class EmailController {
    
    @Autowired
    private EmailService emailService;

    @GetMapping("/send/{OrderId}")
    public ResponseEntity<?> sendMessage(@PathVariable Long OrderId) throws MessagingException{
        emailService.sendSimpleMessage(OrderId);
        return new ResponseEntity<>("Enviado",HttpStatus.OK);
    }
}
