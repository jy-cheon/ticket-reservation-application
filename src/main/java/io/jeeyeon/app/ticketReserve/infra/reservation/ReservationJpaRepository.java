package io.jeeyeon.app.ticketReserve.infra.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationJpaRepository extends JpaRepository<ReservationEntity, Long> {

    @Query(value = "SELECT * FROM Reservation r " +
            "LEFT JOIN Payment p on r.reservationId = p.reservationId " +
            "WHERE r.status = 'RESERVED' and p.paymentId IS NULL " +
            "and r.created_at < :fiveMinutesAgo", nativeQuery = true)
    List<ReservationEntity> findUnpaidReservations(LocalDateTime fiveMinutesAgo);

}
