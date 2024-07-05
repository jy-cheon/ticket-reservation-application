package io.jeeyeon.app.ticketReserve.controller.req;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class ReserveSeatRequest {
    private String userId;
    private String concertId;
    private String date;
    private String seatNumber;
}
