package io.jeeyeon.app.ticketReserve.presentation.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
public class ReservationResponse {
    private String reservationId;
    private LocalDateTime expirationTime;
}
