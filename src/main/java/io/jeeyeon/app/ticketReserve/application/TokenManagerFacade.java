package io.jeeyeon.app.ticketReserve.application;

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
    public Long issueToken(Long userId, Long concertId) {
        // 사용자 존재 여부 확인
        if (!userService.exists(userId)) {
            throw new IllegalArgumentException("User with userId " + userId + " does not exist");
        }

        // 토큰 발급 및 반환
        QueueToken token = queueTokenService.createToken(userId, concertId);
        return token.getTokenId();
    }

    // 토큰 활성화(입장)
    public void activateQueueTokens() {
        // 콘서트 전체 아이디 조회
        List<Long> concertIds = concertService.getConcertIds();
        // 콘서트별 토큰 활성화
        queueTokenService.activateQueueTokens(concertIds);
    }

    // 토큰 만료(퇴장)
    public void expireQueueTokens() {
        queueTokenService.expireQueueTokens();
    }


}
