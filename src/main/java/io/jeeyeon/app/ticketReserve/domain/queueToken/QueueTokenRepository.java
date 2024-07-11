package io.jeeyeon.app.ticketReserve.domain.queueToken;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface QueueTokenRepository {
    List<QueueToken> findByConcertIdAndStatus(Long concertId, TokenStatus status);

    QueueToken save(QueueToken newToken);

    List<QueueToken> findByUserIdAndConcertId(Long userId, Long concertId);

    Long getNextSequenceIdForConcert(Long concertId);

    List<QueueToken> findByStatusAndExpiredAtBefore(TokenStatus active, LocalDateTime now);

    List<QueueToken> findTopNByConcertIdAndStatusOrderByCreatedAtAsc(Long concertId, TokenStatus status, long remainTokensToActivate);

    Optional<QueueToken> findByTokenIdAndConcertId(Long concertId, Long tokenId);

    Optional<QueueToken> findByTokenId(Long tokenId);
}
