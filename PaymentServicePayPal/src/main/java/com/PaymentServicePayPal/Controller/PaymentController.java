package com.PaymentServicePayPal.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.PaymentServicePayPal.Message.Message;
import com.PaymentServicePayPal.Repository.MessageRepository;
import com.PaymentServicePayPal.Service.PaymentService;
import com.PaymentServicePayPal.dto.OrderDTO;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/{orderId}")
    private String ViewPayment(@PathVariable Long orderId, Model model) {
        OrderDTO orderDTO = paymentService.ViewOrderDetails(orderId);
        model.addAttribute("order", orderDTO);
        return "index";
    }

    @PostMapping("/payment/create")
    public RedirectView createPayment(
            @RequestParam("orderid") Long orderId,
            @RequestParam("method") String method,
            @RequestParam("amount") String amount,
            @RequestParam("currency") String currency,
            @RequestParam("description") String description) {
        try {
            String amounts = amount.replace(',', '.');
            String cancelUrl = "http://gateway-service/payment/cancel";
            String successUrl = String.format("http://gateway-service/payment/success/%d", orderId);
            Payment payment = paymentService.createPayment(
                    orderId,
                    Double.valueOf(amounts),
                    currency,
                    method,
                    "sale",
                    description,
                    cancelUrl,
                    successUrl);

            for (Links links : payment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    return new RedirectView(links.getHref());
                }
            }
        } catch (PayPalRESTException e) {
            log.error("Error occurred:: ", e);
        }
        return new RedirectView("/payment/error");
    }

    @GetMapping("/payment/success/{orderId}")
    private String ViewSuccess(@PathVariable Long orderId, Model model,
            @RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId) {
        OrderDTO orderDTO = paymentService.ViewPaymentSuccess(orderId);
        model.addAttribute("order", orderDTO);
        messageRepository.save(Message.builder().orderId(orderId)
                .userName(orderDTO.getUserName())
                .userEmail(orderDTO.getUserEmail())
                .userAddress(orderDTO.getUserAddress())
                .userPhone(orderDTO.getUserPhone())
                .orderStatus(orderDTO.getOrderStatus())
                .orderDate(orderDTO.getOrderDate())
                .totalAmount(orderDTO.getTotalAmount())
                .build());
        try {
            Payment payment = paymentService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                return "paymentSuccess";
            }
        } catch (PayPalRESTException e) {
            log.error("Error occurred:: ", e);
        }
        return "paymentSuccess";

    }

}
