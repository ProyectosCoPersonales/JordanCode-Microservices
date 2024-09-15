package com.PaymentService.Config.Paypal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.PaymentService.Client.OrderClient;
import com.PaymentService.dto.OrderDTO;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PaypalController {
    
    @Autowired
    private final PaypalService paypalService;

    @Autowired
    private OrderClient orderClient;

    @GetMapping("/bring/{orderId}")
    public String bringOrder(@PathVariable Long orderId, Model model) {
        OrderDTO order = orderClient.BringOrder(orderId);
        model.addAttribute("order", order);
        return "index"; 
    }

    @PostMapping("/payment/create")
    public RedirectView createPayment(@RequestParam Long orderId) {
    try {
        OrderDTO order = orderClient.BringOrder(orderId);
        if (order == null || order.getTotalAmount() == null) {
            log.error("La orden no existe o no tiene un monto total.");
            return new RedirectView("/payment/error");
        }
        String cancelUrl = "https://localhost:8084/payment/cancel";  
        String successUrl = "https://localhost:8084/payment/success";  
        Payment payment = paypalService.createPayment(
            order.getTotalAmount(),  
            "USD",                 
            "paypal",              
            "sale",               
            "Payment for order #" + orderId,  
            cancelUrl,              
            successUrl               
        );

        for (Links links : payment.getLinks()) {
            if (links.getRel().equals("approval_url")) {
                return new RedirectView(links.getHref());
            }
        }

      
        log.error("No se encontró la URL de aprobación en la respuesta de PayPal.");
        return new RedirectView("/payment/error");

    } catch (PayPalRESTException e) {
        log.error("Ocurrió un error en la creación del pago: ", e);
        return new RedirectView("/payment/error");
    }
}

    @GetMapping("/payment/success")
    public String paymentSuccess(
        @RequestParam("paymentId") String paymentId,
        @RequestParam("payerId") String payerId
    ) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                return "success";
            }
        } catch (PayPalRESTException e) {
            log.error("Error occurred: ", e);
        }
        return "error";
    }

    @GetMapping("/payment/cancel")
    public String paymentCancel() {
        return "cancel";
    }

    @GetMapping("/payment/error")
    public String paymentError() {
        return "error";
    }
}
