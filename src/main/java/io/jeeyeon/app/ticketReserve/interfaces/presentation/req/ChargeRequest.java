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
public class ChargeRequest {
    @Schema(description = "충전 금액", example = "413000")
    private int amount;
}
