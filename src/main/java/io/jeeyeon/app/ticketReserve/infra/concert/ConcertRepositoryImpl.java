package io.jeeyeon.app.ticketReserve.infra.concert;

import io.jeeyeon.app.ticketReserve.domain.concert.Concert;
import io.jeeyeon.app.ticketReserve.domain.concert.ConcertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class ConcertRepositoryImpl implements ConcertRepository {
    private final ConcertJpaRepository concertJpaRepository;

    @Override
    public Optional<Concert> findByConcertId(Long concertId) {
        Optional<ConcertEntity> c = concertJpaRepository.findById(concertId);
        Optional<Concert> cc = c.stream().map(ConcertEntity::toConcert)
                .findAny();

        return cc;
    }

    @Override
    public List<Concert> findAll() {
        List<ConcertEntity> concertEntities = concertJpaRepository.findAll();
        return concertEntities.stream()
                .map(ConcertEntity::toConcert)
                .collect(Collectors.toList());
    }
}
