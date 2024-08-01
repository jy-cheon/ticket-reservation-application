package io.jeeyeon.app.ticketReserve.domain.queueToken;

import io.jeeyeon.app.ticketReserve.domain.common.exception.BaseException;
import io.jeeyeon.app.ticketReserve.domain.common.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class QueueTokenService {
    private final QueueTokenRepository queueTokenRepository;
    private static final int MAX_ACTIVATION_COUNT = 3; // 최대 활성화 가능한 토큰 수

    public void createToken(Long userId, Long concertId) {
        // 콘서트별 순서 id 조회
        Long nextSequenceId = queueTokenRepository.getNextSequenceIdForConcert(concertId);

        // 대기 토큰 생성
        QueueToken token = new QueueToken(userId, concertId, nextSequenceId);

        queueTokenRepository.save(token);
    }

    // 대기 토큰 활성화
    public void activateQueueToken(QueueToken queueToken) {
        QueueToken activatedToken = queueToken.activateQueueToken();
        queueTokenRepository.save(activatedToken);
    }

    /**
     * 대기열에서 활성 대기열로 토큰을 비율에 맞게 이동합니다.
     *
     * @param concertIds 활성화할 콘서트 ID 리스트
     * @return 총 활성화된 토큰 수
     */
    public long activateQueueTokens(List<Long> concertIds) {
        // 1. 총 대기열 사이즈 수집
        Map<Long, Long> concertQueueSizes = new HashMap<>();
        long totalQueueSize = 0;

        for (Long concertId : concertIds) {
            long queueSize = getWaitingTokensSize(concertId);
            concertQueueSizes.put(concertId, queueSize);
            totalQueueSize += queueSize;
        }

        // 2. 각 콘서트별 비율에 따라 활성화할 인원 수 계산
        Map<Long, Integer> activationCounts = new HashMap<>();
        int totalActivated = 0;

        for (Map.Entry<Long, Long> entry : concertQueueSizes.entrySet()) {
            Long concertId = entry.getKey();
            long queueSize = entry.getValue();
            // 비율에 따라 초기 활성화 수 계산
            int initialActivationCount = (int) Math.round((double) queueSize / totalQueueSize * MAX_ACTIVATION_COUNT);
            activationCounts.put(concertId, Math.max(initialActivationCount, 1)); // 최소 1명 보장
        }

        // 3. 토큰 이동 및 결과 집계
        for (Map.Entry<Long, Integer> entry : activationCounts.entrySet()) {
            Long concertId = entry.getKey();
            int count = entry.getValue();
            Set<Long> tokens = getOldestWaitingTokens(concertId, count);
            activateQueueToken(concertId, tokens);
            totalActivated += tokens.size();
        }

        return totalActivated;
    }

    public long getWaitingTokensSize(Long concertId) {
        return queueTokenRepository.getWaitingTokensSize(concertId);
    }

    // 대기 토큰 활성화
    private long activateQueueTokens(Long concertId) {
        List<QueueToken> currentActiveTokens = getActiveTokens(concertId);
        long activatableTokenCount = MAX_ACTIVATION_COUNT - currentActiveTokens.size();

        if (activatableTokenCount == 0) {
            return 0;
        }

        List<QueueToken> waitingTokens = findWaitingTokens(concertId, TokenStatus.WAITING, activatableTokenCount);
        for (QueueToken token : waitingTokens) {
            activateQueueToken(token);
        }
        return waitingTokens.size();
    }

    private Set<Long> getOldestWaitingTokens(Long concertId, int count) {
        Set<Long> tokens = queueTokenRepository.getOldestWaitingTokens(concertId, count);
        return tokens;
    }

    private void activateQueueToken(Long concertId, Set<Long> userIds) {
        if (ObjectUtils.isNotEmpty(userIds)) {
            queueTokenRepository.createActiveToken(concertId, userIds);
            queueTokenRepository.removeWaitingToken(concertId, userIds);
        }
        log.info("concertId : {}, userIds : {} is added to active token", concertId, userIds);
    }

    private List<QueueToken> findWaitingTokens(Long concertId, TokenStatus status, long remainTokensToActivate) {
        return queueTokenRepository.findTopNByConcertIdAndStatusOrderByCreatedAtAsc(concertId, status, remainTokensToActivate);
    }

    private List<QueueToken> getActiveTokens(Long concertId) {
        return findByConcertIdAndStatus(concertId, TokenStatus.ACTIVE);
    }

    // 대기 토큰 만료 처리
    public void expireQueueTokens() {
        // 활성화 상태인데, 만료시간이 이미 지난 토큰
        List<QueueToken> expiredTokens = queueTokenRepository.findByStatusAndExpiredAtBefore(TokenStatus.ACTIVE, LocalDateTime.now());
        for (QueueToken token : expiredTokens) {
            expireQueueToken(token);
        }
    }
    public void removeExpiredTokens(List<Long> concertIds) {
        for (Long concertId : concertIds) {
            Set<Long> activeTokens = queueTokenRepository.getActiveTokens(concertId);
            if (activeTokens != null) {
                activeTokens.forEach(token -> {
                    if (!queueTokenRepository.hasKey(concertId, token)) {
                        queueTokenRepository.removeExpiredTokens(concertId, token);
                    }
                });
            }
        }
    }
    // 토큰 단건 만료 처리
    private void expireQueueToken(QueueToken token) {
        token.expireQueueToken();
        queueTokenRepository.save(token);
    }

    private List<QueueToken> findByConcertIdAndStatus(Long concertId, TokenStatus status) {
        List<QueueToken> activeTokens = queueTokenRepository.findByConcertIdAndStatus(concertId, status);
        return activeTokens;
    }

    private List<QueueToken> findByUserIdAndConcertId(Long userId, Long concertId) {
        List<QueueToken> tokens = queueTokenRepository.findByUserIdAndConcertId(userId, concertId);
        return tokens;
    }


    public QueueToken validateActiveToken(Long concertId, Long tokenId) {
        // 토큰 유효성 검증
        QueueToken token = queueTokenRepository.findByTokenIdAndConcertId(concertId, tokenId)
                .orElseThrow(() -> new BaseException(ErrorType.INVALID_TOKEN));

        token.validateActiveToken();
        return token;
    }

    public QueueToken findByTokenId(Long tokenId) {
        return queueTokenRepository.findByTokenId(tokenId)
                .orElseThrow(() -> new BaseException(ErrorType.ENTITY_NOT_FOUND));
    }

    public void expireQueueToken(Long tokenId) {
        QueueToken token = this.findByTokenId(tokenId);
        this.expireQueueToken(token);
    }

    public void expireQueueToken(Long concertId, Long userId) {
        queueTokenRepository.removeExpiredTokens(concertId, userId);
    }

    public QueueToken getTokenInfo(Long concertId, Long userId) {
        QueueToken token = new QueueToken(concertId, userId);
        boolean isActive;
        boolean isWaiting;
        boolean isExpired;
        long aheadCount;

        isActive = queueTokenRepository.isActiveToken(concertId, userId);
        if (isActive) {
            token.setActive();
            return token;
        }

        isWaiting = queueTokenRepository.isWaitingToken(concertId, userId);
        if (isWaiting) {
            aheadCount =  queueTokenRepository.findWaitingAheadCount_redis(concertId, userId);
            token.setWaiting(aheadCount);
            return token;
        }

        isExpired = queueTokenRepository.expireWaitingToken(concertId, userId);
        if (isExpired) {
            token.setExpired();
            return token;
        }

        throw new BaseException(ErrorType.INVALID_TOKEN);
    }
}
