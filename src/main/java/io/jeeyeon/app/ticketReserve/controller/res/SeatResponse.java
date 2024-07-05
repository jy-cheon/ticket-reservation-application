package io.jeeyeon.app.ticketReserve.controller.res;

import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
public class SeatResponse {
    private List<Seat> availableSeats;

    @Data
    public static class Seat {
        private String seatId;
        private String seatNumber;
        private Long price;

        public Seat(String seatId, String seatNumber, Long price) {
            this.seatId = seatId;
            this.seatNumber = seatNumber;
            this.price = price;
        }
    }
}
