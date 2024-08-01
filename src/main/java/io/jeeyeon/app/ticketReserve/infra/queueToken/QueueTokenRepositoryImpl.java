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
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class QueueTokenRepositoryImpl implements QueueTokenRepository {
    private final QueueTokenJpaRepository queueTokenJpaRepository;
    private final QueueTokenRedisRepository queueTokenRedisRepository;
    @Override
    public List<QueueToken> findByConcertIdAndStatus(Long concertId, TokenStatus status) {
        List<QueueTokenEntity> entityList = queueTokenJpaRepository.findByConcertIdAndStatus(concertId, status);
        return entityList.stream()
                .map(QueueTokenEntity::toQueueToken)
                .collect(Collectors.toList());
    }

    @Override
    public void save(QueueToken queueToken) {
        QueueTokenEntity entity = new QueueTokenEntity(queueToken);
        queueTokenRedisRepository.createWaitingToken(queueToken.getConcertId(), queueToken.getUserId());
//        return queueTokenJpaRepository.save(entity).toQueueToken();
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

    public Long findWaitingAheadCount_redis(Long concertId, Long userId) {
        return  queueTokenRedisRepository.findWaitingAheadCount(concertId, userId);
    }

    @Override
    public boolean isWaitingToken(Long concertId, Long userId) {
        return queueTokenRedisRepository.isWaitingToken(concertId, userId);
    }

    @Override
    public boolean isActiveToken(Long concertId, Long userId) {
        return queueTokenRedisRepository.isActiveToken(concertId, userId);
    }

    @Override
    public boolean expireWaitingToken(Long concertId, Long userId) {
        return queueTokenRedisRepository.expireWaitingToken(concertId, userId);
    }

    @Override
    public Set<Long> getOldestWaitingTokens(Long concertId, int count) {
        return queueTokenRedisRepository.getOldestMembers(concertId, count);
    }
    @Override
    public void createActiveToken(Long concertId, Set<Long> userIds) {
        queueTokenRedisRepository.createActiveToken(concertId, userIds);
    }
    @Override
    public void removeWaitingToken(Long concertId, Set<Long> userIds) {
        queueTokenRedisRepository.removeWaitingToken(concertId, userIds);
    }
    @Override
    public long getWaitingTokensSize(Long concertId) {
        return queueTokenRedisRepository.getWaitingTokensSize(concertId);
    }
    @Override
    public void removeExpiredTokens(Long concertId, Long userId) {
        queueTokenRedisRepository.removeExpiredTokens(concertId, userId);
    }
    @Override
    public Set<Long> getActiveTokens(Long concertId) {
        return queueTokenRedisRepository.getActiveTokens(concertId);
    }

    @Override
    public boolean hasKey(Long concertId, Long userId) {
        return queueTokenRedisRepository.hasKey(concertId, userId);
    }
}
