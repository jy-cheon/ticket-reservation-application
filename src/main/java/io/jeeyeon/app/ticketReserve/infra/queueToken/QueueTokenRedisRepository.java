package io.jeeyeon.app.ticketReserve.infra.queueToken;

import io.jeeyeon.app.ticketReserve.infra.common.RedisCommonTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Repository
public class QueueTokenRedisRepository {

    private final RedisCommonTemplate redisTemplate;
    private static final String WAITING_TOKENS_KEY = "waiting_tokens";
    private static final String ACTIVE_TOKENS_KEY = "active_tokens";
    public void createWaitingToken(Long concertId, Long userId) {
        double score = Instant.now().toEpochMilli();
        String key = WAITING_TOKENS_KEY + ":" + concertId;
        Long value = userId;
        redisTemplate.addToZSet(key, value, score);

        String itemKey = key + ":" + value;
        redisTemplate.addValueWithTTl(itemKey, value, 24*60);
    }
    public void createActiveToken(Long concertId, Set<Long> userIds) {
        String key = ACTIVE_TOKENS_KEY + ":" + concertId;
        redisTemplate.addToSet(key, userIds);

        userIds.stream()
            .forEach(value -> {
                String itemKey = key + ":" + value;
                redisTemplate.addValueWithTTl(itemKey, value, 30);
            });
    }

    public Long findWaitingAheadCount(Long concertId, Long userId) {
        String key = WAITING_TOKENS_KEY + ":" + concertId;
        Long value = userId;
        Long no = redisTemplate.rankFromZSet(key, value);
        return no;
    }

    public boolean isWaitingToken(Long concertId, Long userId) {
        String key = WAITING_TOKENS_KEY + ":" + concertId + ":" + userId;
        return redisTemplate.isMemberInValue(key);
    }

    public boolean isActiveToken(Long concertId, Long userId) {
        String key = ACTIVE_TOKENS_KEY + ":" + concertId;
        Long value = userId;
        return redisTemplate.isMemberInSet(key, value);
    }

    public boolean expireWaitingToken(Long concertId, Long userId) {
        String key = WAITING_TOKENS_KEY + ":" + concertId;
        Long value = userId;
        return redisTemplate.deleteMemberInZset(key, value);
    }
}
