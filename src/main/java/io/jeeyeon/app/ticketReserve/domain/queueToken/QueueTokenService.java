package io.jeeyeon.app.ticketReserve.domain.queueToken;

import io.jeeyeon.app.ticketReserve.domain.common.exception.BaseException;
import io.jeeyeon.app.ticketReserve.domain.common.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class QueueTokenService {
    private final QueueTokenRepository queueTokenRepository;
    private static final int MAX_ACTIVE_TOKENS = 100; // 최대 활성화 가능한 토큰 수

    public QueueToken createToken(Long userId, Long concertId) {
        // 콘서트별 순서 id 조회
        Long nextSequenceId = queueTokenRepository.getNextSequenceIdForConcert(concertId);

        // 대기 토큰 생성
        QueueToken token = new QueueToken(userId, concertId, nextSequenceId);

        QueueToken newToken = queueTokenRepository.save(token);
        return newToken;
    }

    // 대기 토큰 활성화
    public QueueToken activateQueueToken(QueueToken queueToken) {
        QueueToken activatedToken = queueToken.activateQueueToken();
        return queueTokenRepository.save(activatedToken);
    }


    public long activateQueueTokens(List<Long> concertIds) {
        long total = 0;
        for (Long concertId : concertIds) {
            total += activateQueueTokens(concertId);
        }
        return total;
    }

    // 대기 토큰 활성화
    private long activateQueueTokens(Long concertId) {
        List<QueueToken> currentActiveTokens = getActiveTokens(concertId);
        long activatableTokenCount = MAX_ACTIVE_TOKENS - currentActiveTokens.size();

        if (activatableTokenCount == 0) {
            return 0;
        }

        List<QueueToken> waitingTokens = findWaitingTokens(concertId, TokenStatus.WAITING, activatableTokenCount);
        for (QueueToken token : waitingTokens) {
            activateQueueToken(token);
        }
        return waitingTokens.size();
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

    public QueueToken getTokenInfo(Long tokenId) {
        QueueToken token = findByTokenId(tokenId);
        if (token.isWaiting()) {
            // 대기 번호 조회
            long aheadCount = queueTokenRepository.findWaitingAheadCount(token.getConcertId(), token.getSequenceId(), token.getStatus());
            log.info("대기 번호 조회 : {}",aheadCount);
            // 대기 번호 설정
            token.setAheadCount(aheadCount);
        } else if (token.isActive()) {
            token.setAheadCount(0l);
        }
        return token;
    }

    public void expireQueueToken(Long tokenId) {
        QueueToken token = this.findByTokenId(tokenId);
        this.expireQueueToken(token);
    }
}
