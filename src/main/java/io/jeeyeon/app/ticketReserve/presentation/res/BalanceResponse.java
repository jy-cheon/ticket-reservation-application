package io.jeeyeon.app.ticketReserve.presentation.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class BalanceResponse {
    private String userId;
    private int balance;
}
