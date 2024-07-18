package io.jeeyeon.app.ticketReserve.infra.queueToken;

import io.jeeyeon.app.ticketReserve.domain.queueToken.QueueToken;
import io.jeeyeon.app.ticketReserve.domain.queueToken.QueueTokenRepository;
import io.jeeyeon.app.ticketReserve.domain.queueToken.TokenStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class QueueTokenRepositoryImpl implements QueueTokenRepository {
    private final QueueTokenJpaRepository queueTokenJpaRepository;
    @Override
    public List<QueueToken> findByConcertIdAndStatus(Long concertId, TokenStatus status) {
        List<QueueTokenEntity> entityList = queueTokenJpaRepository.findByConcertIdAndStatus(concertId, status);
        return entityList.stream()
                .map(QueueTokenEntity::toQueueToken)
                .collect(Collectors.toList());
    }

    @Override
    public QueueToken save(QueueToken queueToken) {
        QueueTokenEntity entity = new QueueTokenEntity(queueToken);
        return queueTokenJpaRepository.save(entity).toQueueToken();
    }

    @Override
    public List<QueueToken> findByUserIdAndConcertId(Long userId, Long concertId) {
        List<QueueTokenEntity> tokenEntities = queueTokenJpaRepository.findByUserIdAndConcertId(userId, concertId);

        return tokenEntities.stream()
                .map(QueueTokenEntity::toQueueToken)
                .collect(Collectors.toList());
    }

    @Override
    public Long getNextSequenceIdForConcert(Long concertId) {
        return queueTokenJpaRepository.getNextSequenceIdForConcert(concertId);
    }

    @Override
    public List<QueueToken> findByStatusAndExpiredAtBefore(TokenStatus active, LocalDateTime now) {
        List<QueueTokenEntity> tokenEntities = queueTokenJpaRepository.findByStatusAndExpiredAtBefore(active, now);
        return tokenEntities.stream()
                .map(QueueTokenEntity::toQueueToken)
                .collect(Collectors.toList());
    }

    @Override
    public List<QueueToken> findTopNByConcertIdAndStatusOrderByCreatedAtAsc(Long concertId, TokenStatus status, long remainTokensToActivate) {
        Pageable pageable = PageRequest.of(0, (int)remainTokensToActivate);
        List<QueueTokenEntity> tokenEntities = queueTokenJpaRepository.findTopNByConcertIdAndStatusOrderByCreatedAtAsc(concertId, status, pageable);
        return tokenEntities.stream()
                .map(QueueTokenEntity::toQueueToken)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<QueueToken> findByTokenIdAndConcertId(Long concertId, Long tokenId) {
        return queueTokenJpaRepository.findByTokenIdAndConcertId(concertId, tokenId).stream()
                .map(QueueTokenEntity::toQueueToken).findAny();
    }

    @Override
    public Optional<QueueToken> findByTokenId(Long tokenId) {
        return queueTokenJpaRepository.findById(tokenId).stream()
                .map(QueueTokenEntity::toQueueToken).findAny();
    }


    @Override
    public Long findWaitingAheadCount(Long concertId, Long sequenceId, TokenStatus status) {
        return queueTokenJpaRepository.findWaitingAheadCount(concertId, sequenceId, status);
    }
}
