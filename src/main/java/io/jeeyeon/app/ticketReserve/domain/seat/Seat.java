package io.jeeyeon.app.ticketReserve.domain.seat;

import io.jeeyeon.app.ticketReserve.domain.common.exception.BaseException;
import io.jeeyeon.app.ticketReserve.domain.common.exception.ErrorType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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

    public void reserve() {
        if (this.isAvailable()) {
            this.setStatus(SeatStatus.RESERVED);
        } else {
            log.warn("해당 예약은 이미 예약되었습니다. {}", seatNumber);
            throw new BaseException(ErrorType.NOT_AVAILABLE_SEAT);
        }
    }
}
