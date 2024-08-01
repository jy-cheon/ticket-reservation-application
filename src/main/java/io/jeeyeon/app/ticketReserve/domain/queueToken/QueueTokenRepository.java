package io.jeeyeon.app.ticketReserve.domain.queueToken;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface QueueTokenRepository {
    List<QueueToken> findByConcertIdAndStatus(Long concertId, TokenStatus status);

    void save(QueueToken newToken);

    List<QueueToken> findByUserIdAndConcertId(Long userId, Long concertId);

    Long getNextSequenceIdForConcert(Long concertId);

    List<QueueToken> findByStatusAndExpiredAtBefore(TokenStatus active, LocalDateTime now);

    List<QueueToken> findTopNByConcertIdAndStatusOrderByCreatedAtAsc(Long concertId, TokenStatus status, long remainTokensToActivate);

    Optional<QueueToken> findByTokenIdAndConcertId(Long concertId, Long tokenId);

    Optional<QueueToken> findByTokenId(Long tokenId);

    Long findWaitingAheadCount(Long concertId, Long sequenceId, TokenStatus status);

    Long findWaitingAheadCount_redis(Long concertId, Long userId);

    boolean isWaitingToken(Long concertId, Long userId);

    boolean isActiveToken(Long concertId, Long userId);

    boolean expireWaitingToken(Long concertId, Long userId);

    Set<Long> getOldestWaitingTokens(Long concertId, int count);

    void createActiveToken(Long concertId, Set<Long> userIds);

    void removeWaitingToken(Long concertId, Set<Long> userIds);

    long getWaitingTokensSize(Long concertId);

    void removeExpiredTokens(Long concertId, Long userId);

    Set<Long> getActiveTokens(Long concertId);

    boolean hasKey(Long concertId, Long userId);
}