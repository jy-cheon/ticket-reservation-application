package io.jeeyeon.app.ticketReserve.controller.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChargeRequest {
    private String userId;
    private int amount;
}
