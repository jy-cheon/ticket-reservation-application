package io.jeeyeon.app.ticketReserve.controller.res;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
public class BalanceResponse {
    private String userId;
    private int balance;
}
