package io.jeeyeon.app.ticketReserve.interfaces.presentation.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    @Schema(description = "예약 ID", example = "")
    private Long reservationId;
    @Schema(description = "유저 ID", example = "23")
    private Long userId;

}
