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
    @Column(name = "RESERVATION_ID")
    private Long reservationId;
    @Column(name = "SEAT_ID")
    private Long seatId;
    @Column(name = "TOKEN_ID")
    private Long tokenId;
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private ReservationStatus status;
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

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
