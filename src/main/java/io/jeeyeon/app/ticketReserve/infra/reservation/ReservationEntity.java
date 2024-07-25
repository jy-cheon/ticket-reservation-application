package io.jeeyeon.app.ticketReserve.infra.reservation;

import io.jeeyeon.app.ticketReserve.domain.reservation.Reservation;
import io.jeeyeon.app.ticketReserve.domain.reservation.ReservationStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "RESERVATION")
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;
    private Long seatId;
    private Long tokenId;
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ReservationEntity(Reservation reservation) {
        this.reservationId = reservation.getReservationId();
        this.seatId = reservation.getSeatId();
        this.tokenId = reservation.getTokenId();
        this.status = reservation.getStatus();
    }

    public Reservation toReservation() {
        Reservation reservation = new Reservation();
        reservation.setReservationId(this.reservationId);
        reservation.setSeatId(this.seatId);
        reservation.setTokenId(this.tokenId);
        reservation.setStatus(this.status);
        return reservation;
    }
}
