package io.jeeyeon.app.ticketReserve.application;

import io.jeeyeon.app.ticketReserve.domain.concert.ConcertService;
import io.jeeyeon.app.ticketReserve.domain.queueToken.QueueToken;
import io.jeeyeon.app.ticketReserve.domain.queueToken.QueueTokenRepository;
import io.jeeyeon.app.ticketReserve.domain.queueToken.QueueTokenService;
import io.jeeyeon.app.ticketReserve.domain.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class TokenManagerFacadeTest {

    @Autowired
    private TokenManagerFacade tokenManagerFacade;

    @Autowired
    private QueueTokenRepository queueTokenRepository;

    @Autowired
    private QueueTokenService queueTokenService;

    @Autowired
    private UserService userService;

    @Autowired
    private ConcertService concertService;

    @Test
    @DisplayName("토큰 발급 테스트")
    public void testIssueToken() {
        // given
        Long userId = 1L;
        Long concertId = 1L;
        QueueToken mockToken = new QueueToken(1L, userId, concertId);

        // when
        Long tokenId = tokenManagerFacade.issueToken(userId, concertId);

        // then
        assertEquals(mockToken.getTokenId(), tokenId);
    }

    @Test
    @DisplayName("토큰 발급 시 사용자가 존재하지 않는 경우 예외 처리 테스트")
    public void testIssueTokenUserNotExist() {
        // given
        Long userId = 999L;
        Long concertId = 1L;

        // when, then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> tokenManagerFacade.issueToken(userId, concertId));

        assertEquals("User with userId " + userId + " does not exist", exception.getMessage());
    }

    @Test
    @DisplayName("토큰 조회 테스트")
    public void testGetTokenInfo() {
        // given
        long concertId = 1;
        long sequenceId = 3;
        long userId = 2;
        QueueToken token = queueTokenRepository.save(new QueueToken(userId, concertId, sequenceId));

        // when
        QueueToken tokenInfo = tokenManagerFacade.getTokenInfo(token.getTokenId());

        // then
        assertEquals(1l, tokenInfo.getConcertId());
        assertEquals(2l, tokenInfo.getUserId());
        assertEquals(3l, tokenInfo.getSequenceId());
        assertEquals(1l, tokenInfo.getTokenId());
    }


    @Test
    @DisplayName("토큰 활성화 테스트")
    public void testActivateQueueTokens() {
        // given
        List<Long> concertIds = Arrays.asList(1L, 2L, 3L);

        // when
        tokenManagerFacade.activateQueueTokens();

        // then
        verify(concertService, times(1)).getAvailableConcertIds();
        verify(queueTokenService, times(1)).activateQueueTokens(concertIds);
    }

    @Test
    @DisplayName("토큰 만료 테스트")
    public void testExpireQueueTokens() {
        // when
        tokenManagerFacade.expireQueueTokens();

        // then
        verify(queueTokenService, times(1)).expireQueueTokens();
    }
}
