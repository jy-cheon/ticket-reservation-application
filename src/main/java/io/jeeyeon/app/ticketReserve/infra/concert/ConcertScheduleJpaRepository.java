package io.jeeyeon.app.ticketReserve.infra.concert;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ConcertScheduleJpaRepository extends JpaRepository<ConcertScheduleEntity, Long> {

    List<ConcertScheduleEntity> findByConcertId(Long concertIed);

    Optional<ConcertScheduleEntity> findByConcertIdAndConcertDate(Long concertId, LocalDateTime date);
}
