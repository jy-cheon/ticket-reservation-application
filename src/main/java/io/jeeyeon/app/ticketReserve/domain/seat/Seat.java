package io.jeeyeon.app.ticketReserve.domain.seat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Seat {
    private Long seatId;
    private Long concertScheduleId;
    private String seatNumber;
    private Integer ticketPrice;
    private SeatStatus status;
    private int version;

    public Seat(Long scheduleId, String seatNumber, SeatStatus reserved) {
        this.concertScheduleId = scheduleId;
        this.seatNumber = seatNumber;
        this.status = reserved;
    }

    public boolean isAvailable() {
        return this.status == SeatStatus.AVAILABLE;
    }
}
