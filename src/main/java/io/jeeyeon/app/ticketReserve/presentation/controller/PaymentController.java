package io.jeeyeon.app.ticketReserve.presentation.controller;

import io.jeeyeon.app.ticketReserve.presentation.req.PaymentRequest;
import io.jeeyeon.app.ticketReserve.presentation.res.PaymentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Tag(name = "결제 API")
@RequiredArgsConstructor
@RestController
public class PaymentController {

    @Operation(summary = "결제 API", description = "결제를 처리한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @PostMapping("/payment")
    public PaymentResponse payment(
            @RequestBody PaymentRequest request,
            @Parameter(description = "토큰 값 입력", required = true)
            @RequestHeader("Authorization") String token) {
        String paymentId = "PAY123456";
        LocalDateTime paymentTime = LocalDateTime.now();

        PaymentResponse response = new PaymentResponse(paymentId, paymentTime);
        return response;
    }

}
