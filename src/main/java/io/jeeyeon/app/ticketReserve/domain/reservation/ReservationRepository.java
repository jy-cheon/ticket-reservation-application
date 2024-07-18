package io.jeeyeon.app.ticketReserve.domain.reservation;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository {
    void save(Reservation reservation);

    List<Reservation> findUnpaidReservations(LocalDateTime fiveMinutesAgo);

    Optional<Reservation> findById(Long reservationId);
}
