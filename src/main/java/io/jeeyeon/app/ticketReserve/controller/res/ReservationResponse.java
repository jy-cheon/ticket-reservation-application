package io.jeeyeon.app.ticketReserve.controller.res;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
public class ReservationResponse {
    private String reservationId;
    private LocalDateTime expirationTime;
}
