package io.jeeyeon.app.ticketReserve.infra.concert;

import io.jeeyeon.app.ticketReserve.domain.concert.ConcertSchedule;
import io.jeeyeon.app.ticketReserve.domain.concert.ConcertScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class ConcertScheduleRepositoryImpl implements ConcertScheduleRepository {
    private final ConcertScheduleJpaRepository concertScheduleJpaRepository;

    @Override
    public List<ConcertSchedule> findByConcertId(Long concertId) {
        List<ConcertScheduleEntity> list = concertScheduleJpaRepository.findByConcertId(concertId);
        return list.stream()
                .map(ConcertScheduleEntity::toConcertSchedule)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ConcertSchedule> findByConcertIdAndConcertDate(Long concertId, LocalDateTime date) {
        Optional<ConcertScheduleEntity> concertScheduleEntity = concertScheduleJpaRepository.findByConcertIdAndConcertDate(concertId, date);
        return concertScheduleEntity.stream()
                .map(ConcertScheduleEntity::toConcertSchedule)
                .findAny();
    }
}
