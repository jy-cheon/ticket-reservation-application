package io.jeeyeon.app.ticketReserve.interfaces.presentation.controller;

import io.jeeyeon.app.ticketReserve.application.PaymentFacade;
import io.jeeyeon.app.ticketReserve.domain.payment.Payment;
import io.jeeyeon.app.ticketReserve.interfaces.presentation.req.PaymentRequest;
import io.jeeyeon.app.ticketReserve.interfaces.presentation.res.PaymentResponse;
import io.jeeyeon.app.ticketReserve.interfaces.presentation.res.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "결제 API")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@RestController
public class PaymentController {
    private final PaymentFacade paymentFacade;

    @Operation(summary = "결제 API", description = "결제를 처리한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @PostMapping("/concerts/{concertId}/payment")
    public ResponseEntity<ResponseDto<?>> payment(
            @RequestBody PaymentRequest request,
            @Parameter(description = "콘서트 아이디", required = true, example = "123")
            @PathVariable("concertId") Long concertId,
            @Parameter(description = "토큰 값 입력", required = true)
            @RequestHeader("Auth") String token) throws Exception {


        Payment payment = paymentFacade.processPayment(request.getReservationId(), request.getUserId());

        PaymentResponse response = new PaymentResponse(payment.getPaymentId(), payment.getCreatedAt());
        return ResponseEntity.ok(ResponseDto.success(response));
    }

}
