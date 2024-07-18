package io.jeeyeon.app.ticketReserve.interfaces.presentation.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
