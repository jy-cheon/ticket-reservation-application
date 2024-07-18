package io.jeeyeon.app.ticketReserve.domain.reservation;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository {
    Reservation save(Reservation reservation);

    void saveAll(List<Reservation> reservations);

    List<Reservation> findUnpaidReservations(LocalDateTime fiveMinutesAgo);

    Optional<Reservation> findById(Long reservationId);

    List<Reservation> findAll();
}
