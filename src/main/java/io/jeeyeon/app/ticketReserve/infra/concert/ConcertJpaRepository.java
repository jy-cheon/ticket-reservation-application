package io.jeeyeon.app.ticketReserve.infra.concert;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConcertJpaRepository extends JpaRepository<ConcertEntity, Long> {

    @Query("SELECT DISTINCT c " +
            "FROM ConcertEntity c, ConcertScheduleEntity s " + // Cartesian 조인으로 두 테이블을 조인
            "WHERE s.concertId = c.concertId " +
            "AND s.concertDate > CURRENT_TIMESTAMP")
    List<ConcertEntity> findConcertsBeforeCurrentDate();
}
