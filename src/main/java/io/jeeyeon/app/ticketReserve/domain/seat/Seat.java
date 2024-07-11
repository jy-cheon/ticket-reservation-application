package io.jeeyeon.app.ticketReserve.domain.seat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Seat {
    private Long seatId;
    private Long concertScheduleId;
    private String seatNumber;
    private Long ticketPrice;
    private SeatStatus status;

    public Seat(Long scheduleId, String seatNumber, SeatStatus reserved) {
        this.concertScheduleId = scheduleId;
        this.seatNumber = seatNumber;
        this.status = reserved;
        this.ticketPrice = 100000L;
    }

    public boolean isAvailable() {
        return this.status == SeatStatus.AVAILABLE;
    }
}
