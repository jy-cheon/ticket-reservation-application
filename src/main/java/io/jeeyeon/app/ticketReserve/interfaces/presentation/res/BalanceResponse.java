package io.jeeyeon.app.ticketReserve.interfaces.presentation.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class BalanceResponse {
    private Long userId;
    private Integer balance;
}
