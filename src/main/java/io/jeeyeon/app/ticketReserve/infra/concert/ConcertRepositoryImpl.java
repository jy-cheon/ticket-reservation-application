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
    private final ConcertRedisRepository concertRedisRepository;

    @Override
    public Optional<Concert> findByConcertId(Long concertId) {
        Optional<ConcertEntity> concertEntity = concertJpaRepository.findById(concertId);
        return concertEntity.stream()
                .map(ConcertEntity::toConcert)
                .findAny();
    }

    @Override
    public List<Concert> findAll() {
        List<ConcertEntity> concertEntities = concertJpaRepository.findAll();
        return concertEntities.stream()
                .map(ConcertEntity::toConcert)
                .collect(Collectors.toList());
    }

    @Override
    public List<Concert> findConcertsBeforeCurrentDate() {
        var cache = concertRedisRepository.findConcertsBeforeCurrentDate();
        boolean hasCache = cache.map(list -> !list.isEmpty()).orElse(false);

        if (hasCache) {
            return cache.get();
        } else {
            List<ConcertEntity> concertEntities = concertJpaRepository.findConcertsBeforeCurrentDate();
            return concertEntities.stream()
                    .map(ConcertEntity::toConcert)
                    .collect(Collectors.toList());
        }

    }
}
