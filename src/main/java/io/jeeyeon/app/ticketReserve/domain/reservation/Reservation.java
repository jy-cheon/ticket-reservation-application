package io.jeeyeon.app.ticketReserve.domain.reservation;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Reservation {
    private Long reservationId;
    private Long seatId;
    private Long tokenId;
    private ReservationStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Reservation(Long seatId, Long tokenId) {
        this.seatId = seatId;
        this.tokenId = tokenId;
        this.status = ReservationStatus.RESERVED;
    }

    public Reservation cancel() {
        this.status = ReservationStatus.CANCELLED;
        return this;
    }

    public Reservation confirm() {
        this.status = ReservationStatus.CONFIRMED;
        return this;
    }
}
