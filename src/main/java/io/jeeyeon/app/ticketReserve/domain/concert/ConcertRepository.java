package io.jeeyeon.app.ticketReserve.domain.concert;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConcertRepository {
    Optional<Concert> findByConcertId(Long concertId);

    List<Concert> findAll();
}
