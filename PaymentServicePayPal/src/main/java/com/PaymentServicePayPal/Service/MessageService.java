package com.PaymentServicePayPal.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PaymentServicePayPal.Message.Message;
import com.PaymentServicePayPal.Repository.MessageRepository;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public Message findMessagebyOrderId(Long OrderId){
        return messageRepository.findByOrderId(OrderId);
    }
    public String deleteMessageById(Long id){
        messageRepository.deleteById(id);
        return "delete success";
    }
}
