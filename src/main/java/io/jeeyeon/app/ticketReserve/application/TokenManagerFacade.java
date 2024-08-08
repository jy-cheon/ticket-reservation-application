package io.jeeyeon.app.ticketReserve.application;

import io.jeeyeon.app.ticketReserve.domain.common.exception.BaseException;
import io.jeeyeon.app.ticketReserve.domain.common.exception.ErrorType;
import io.jeeyeon.app.ticketReserve.domain.concert.ConcertService;
import io.jeeyeon.app.ticketReserve.domain.queueToken.QueueToken;
import io.jeeyeon.app.ticketReserve.domain.queueToken.QueueTokenService;
import io.jeeyeon.app.ticketReserve.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TokenManagerFacade {

    private final QueueTokenService queueTokenService;
    private final UserService userService;
    private final ConcertService concertService;

    // 토큰 발급
    public void issueToken(Long userId, Long concertId) {
        // 사용자 존재 여부 확인
        if (!userService.exists(userId)) {
            throw new BaseException(ErrorType.USER_ENTITY_NOT_FOUND);
        }
        // 콘서트 존재 여부 확인
        if (!concertService.exists(concertId)) {
            throw new BaseException(ErrorType.ENTITY_NOT_FOUND);
        }

        // 토큰 발급 및 반환
        queueTokenService.createToken(userId, concertId);
    }

    // 토큰 조회
    public QueueToken getTokenInfo(Long concertId, Long userId) {
        return queueTokenService.getTokenInfo(concertId, userId);
    }

    // 토큰 활성화(입장)
    public void activateQueueTokens() {
        // 콘서트 전체 아이디 조회
        List<Long> concertIds = concertService.getAvailableConcertIds();
        // 콘서트별 토큰 활성화
        queueTokenService.activateQueueTokens(concertIds);
    }

    // 토큰 만료(퇴장)
    public void expireQueueTokens() {
        List<Long> concertIds = concertService.getAvailableConcertIds();
        queueTokenService.removeExpiredTokens(concertIds);
    }

    // 토큰 활성화 유효성 검증
    public QueueToken validateActiveToken(Long concertId, Long tokenId) {
        return queueTokenService.validateActiveToken(concertId, tokenId);
    }


}
