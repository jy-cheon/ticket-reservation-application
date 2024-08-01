package io.jeeyeon.app.ticketReserve.infra.concert;

import io.jeeyeon.app.ticketReserve.domain.concert.Concert;
import io.jeeyeon.app.ticketReserve.infra.common.RedisCommonTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Repository
public class ConcertRedisRepository {

    private final RedisCommonTemplate redisTemplate;

    Optional<List<Concert>> findConcertsBeforeCurrentDate() {
        var concertMap = redisTemplate.getAllHashValues("concert", Concert.class);
        var list = concertMap.values().stream()
                        .collect(Collectors.toList());

        log.info(String.valueOf(list));
        return Optional.of(list);
    }
}
