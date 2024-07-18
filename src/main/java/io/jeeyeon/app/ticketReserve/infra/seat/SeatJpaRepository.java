package io.jeeyeon.app.ticketReserve.infra.seat;

import io.jeeyeon.app.ticketReserve.domain.seat.SeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SeatJpaRepository extends JpaRepository<SeatEntity, Long> {
    List<SeatEntity> findByConcertScheduleId(Long concertScheduleId);

    List<SeatEntity> findByConcertScheduleIdAndStatus(Long concertScheduleId, SeatStatus status);

    SeatEntity findByConcertScheduleIdAndSeatNumber(Long scheduleId, String seatNumber);

    @Transactional
    @Modifying
    @Query(value = "UPDATE SeatEntity s SET s.status = :status WHERE s.seatId IN :seatIds")
    void updateSeatStatusInBatch(@Param("seatIds") List<Long> seatIds, @Param("status") SeatStatus status);
}
