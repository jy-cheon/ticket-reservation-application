package io.jeeyeon.app.ticketReserve.infra.concert;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ConcertScheduleJpaRepository extends JpaRepository<ConcertScheduleEntity, Long> {

    List<ConcertScheduleEntity> findByConcertId(Long concertIed);

    ConcertScheduleEntity findByConcertIdAndConcertDate(Long concertId, LocalDateTime date);
}
