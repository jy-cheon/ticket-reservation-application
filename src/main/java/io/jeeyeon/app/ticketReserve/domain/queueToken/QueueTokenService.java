package io.jeeyeon.app.ticketReserve.domain.queueToken;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QueueTokenService {
    private final QueueTokenRepository queueTokenRepository;
    private static final int MAX_ACTIVE_TOKENS = 100; // 최대 활성화 가능한 토큰 수


    public QueueToken createToken(Long userId, Long concertId) {
        // 콘서트별 순서 id 조회
        Long nextSequenceId = queueTokenRepository.getNextSequenceIdForConcert(concertId);

        // 대기 토큰 생성
        QueueToken newToken = new QueueToken(userId, concertId, nextSequenceId);

        queueTokenRepository.save(newToken);
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
        long waitingTokensSize = MAX_ACTIVE_TOKENS - currentActiveTokens.size();

        if (waitingTokensSize == 0) {
            return 0;
        }

        List<QueueToken> waitingTokens = findWaitingTokens(concertId, TokenStatus.WAITING, waitingTokensSize);
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
            token.expireQueueToken();
            queueTokenRepository.save(token);
        }
    }

    private List<QueueToken> findByConcertIdAndStatus(Long concertId, TokenStatus status) {
        List<QueueToken> activeTokens = queueTokenRepository.findByConcertIdAndStatus(concertId, status);
        return activeTokens;
    }

    private List<QueueToken> findByUserIdAndConcertId(Long userId, Long concertId) {
        List<QueueToken> tokens = queueTokenRepository.findByUserIdAndConcertId(userId, concertId);
        return tokens;
    }


    public QueueToken isValidToken(Long concertId, Long tokenId) {
        // 토큰 유효성 검증
         QueueToken token = queueTokenRepository.findByTokenIdAndConcertId(concertId, tokenId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid token"));

        // 토큰의 상태가 활성화인지 확인
        if(!token.isActive()) {
            throw new IllegalArgumentException("Token is not active");
        }
        return token;
    }

    public String getQueueNumber(Long concertId, Long tokenId) {
        // 대기열 번호 확인 로직 구현
        // 여기서는 간단하게 문자열로 반환
        return "Your queue number is 548"; // 예시로 숫자를 반환
    }

    public Optional<QueueToken> findByTokenId(Long tokenId) {
        return queueTokenRepository.findByTokenId(tokenId);
    }

    public void verifyUser(Long tokenId, Long userId) {
        Optional<QueueToken> token = findByTokenId(tokenId);
        if (token.isEmpty()) {
            throw new EntityNotFoundException("Token not found with ID: " + tokenId);
        }

        if (!token.get().getUserId().equals(userId)) {
//            throw new UnauthorizedException("Unauthorized user for token ID: " + tokenId);
        }
    }
}
