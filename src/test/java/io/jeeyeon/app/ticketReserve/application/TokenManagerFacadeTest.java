package io.jeeyeon.app.ticketReserve.application;

import io.jeeyeon.app.ticketReserve.domain.queueToken.QueueToken;
import io.jeeyeon.app.ticketReserve.domain.queueToken.QueueTokenService;
import io.jeeyeon.app.ticketReserve.domain.user.UserService;
import io.jeeyeon.app.ticketReserve.domain.concert.ConcertService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TokenManagerFacadeTest {

    @Autowired
    private TokenManagerFacade facade;

    @MockBean
    private QueueTokenService queueTokenService;

    @MockBean
    private UserService userService;

    @MockBean
    private ConcertService concertService;

    @Test
    @DisplayName("토큰 발급 테스트")
    public void testIssueToken() {
        // given
        Long userId = 1L;
        Long concertId = 1L;
        QueueToken mockToken = new QueueToken(1L, userId, concertId);

        // mockito stubbing
        when(userService.exists(userId)).thenReturn(true);
        when(queueTokenService.createToken(userId, concertId)).thenReturn(mockToken);

        // when
        Long tokenId = facade.issueToken(userId, concertId);

        // then
        assertEquals(mockToken.getTokenId(), tokenId);
        verify(userService, times(1)).exists(userId);
        verify(queueTokenService, times(1)).createToken(userId, concertId);
    }

    @Test
    @DisplayName("토큰 발급 시 사용자가 존재하지 않는 경우 예외 처리 테스트")
    public void testIssueTokenUserNotExist() {
        // given
        Long userId = 999L;
        Long concertId = 1L;

        // mockito stubbing
        when(userService.exists(userId)).thenReturn(false);

        // when, then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> facade.issueToken(userId, concertId));

        assertEquals("User with userId " + userId + " does not exist", exception.getMessage());
        verify(userService, times(1)).exists(userId);
        verifyNoInteractions(queueTokenService); // queueTokenService는 호출되지 않아야 함
    }

    @Test
    @DisplayName("토큰 활성화 테스트")
    public void testActivateQueueTokens() {
        // given
        List<Long> concertIds = Arrays.asList(1L, 2L, 3L);

        // when
        facade.activateQueueTokens();

        // then
        verify(concertService, times(1)).getConcertIds();
        verify(queueTokenService, times(1)).activateQueueTokens(concertIds);
    }

    @Test
    @DisplayName("토큰 만료 테스트")
    public void testExpireQueueTokens() {
        // when
        facade.expireQueueTokens();

        // then
        verify(queueTokenService, times(1)).expireQueueTokens();
    }
}
