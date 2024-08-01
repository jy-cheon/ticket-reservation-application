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

    public void createWaitingToken(Long concertId, Long userId) {
        double score = Instant.now().toEpochMilli();
        String key = "waitingToken" + ":" + concertId;
        Long value = userId;
        redisTemplate.addToZSet(key, value, score);

        String itemKey = key + ":" + value;
        redisTemplate.addValueWithTTl(itemKey, value, 24);
    }
    public void createActiveToken(Long concertId, Set<Long> userIds) {
        String key = "activeToken" + ":" + concertId;
        redisTemplate.addToSet(key, userIds);
    }


    public Long findWaitingAheadCount(Long concertId, Long userId) {
        String key = "waitingToken" + ":" + concertId;
        Long value = userId;
        Long no = redisTemplate.rankFromZSet(key, value);
        return no;
    }

    public boolean isWaitingToken(Long concertId, Long userId) {
        String key = "waitingToken" + ":" + concertId + ":" + userId;
        return redisTemplate.isMemberInValue(key);
    }

    public boolean isActiveToken(Long concertId, Long userId) {
        String key = "activeToken" + ":" + concertId;
        Long value = userId;
        return redisTemplate.isMemberInSet(key, value);
    }

    public boolean expireWaitingToken(Long concertId, Long userId) {
        String key = "waitingToken" + ":" + concertId;
        Long value = userId;
        return redisTemplate.deleteMemberInZset(key, value);
    }

    public Set<Long> getOldestMembers(Long concertId, int count) {
        String key = "waitingToken" + ":" + concertId;
        Set<Object> range = redisTemplate.getOldestMembersFromZset(key, 0, count - 1);
        if (range != null) {
            return range.stream()
                    .map(o -> ((Integer) o).longValue())
                    .collect(Collectors.toSet());
        }
        return Set.of();
    }

    public void removeWaitingToken(Long concertId, Set<Long> userIds) {
        String key = "waitingToken" + ":" + concertId;
        redisTemplate.deleteMembersInZset(key,userIds);
    }

    public long getWaitingTokensSize(Long concertId) {
        String key = "waitingToken" + ":" + concertId;
        return redisTemplate.getZSetSize(key);
    }
}
