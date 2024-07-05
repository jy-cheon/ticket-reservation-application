package io.jeeyeon.app.ticketReserve.controller;


import io.jeeyeon.app.ticketReserve.controller.req.PaymentRequest;
import io.jeeyeon.app.ticketReserve.controller.res.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequestMapping
@RequiredArgsConstructor
@RestController
public class PaymentController {

    // 결제 API
    @PostMapping("/payment")
    public PaymentResponse payment(@RequestBody PaymentRequest request, @RequestHeader("Authorization") String token) {
        String paymentId = "PAY123456";
        LocalDateTime paymentTime = LocalDateTime.now();

        PaymentResponse response = new PaymentResponse(paymentId, paymentTime);
        return response;
    }

}
