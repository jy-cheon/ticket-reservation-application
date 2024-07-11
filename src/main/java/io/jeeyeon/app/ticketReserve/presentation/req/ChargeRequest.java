package io.jeeyeon.app.ticketReserve.presentation.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChargeRequest {
    @Schema(description = "충전 금액", example = "413000")
    private int amount;
}
