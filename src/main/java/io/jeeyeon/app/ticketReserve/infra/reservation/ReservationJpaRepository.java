package io.jeeyeon.app.ticketReserve.infra.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationJpaRepository extends JpaRepository<ReservationEntity, Long> {

    @Query("SELECT r FROM ReservationEntity r LEFT JOIN PaymentEntity p ON r.reservationId = p.reservationId " +
            "WHERE r.status = 'RESERVED' AND p.paymentId IS NULL " +
            "AND r.createdAt < :fiveMinutesAgo")
    List<ReservationEntity> findUnpaidReservations(@Param("fiveMinutesAgo") LocalDateTime fiveMinutesAgo);



}
