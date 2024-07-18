package io.jeeyeon.app.ticketReserve.presentation.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PaymentRequest {
    @Schema(description = "예약 ID", example = "")
    private String reservationId;
    @Schema(description = "결제 금액", example = "4000")
    private int amount;
}
