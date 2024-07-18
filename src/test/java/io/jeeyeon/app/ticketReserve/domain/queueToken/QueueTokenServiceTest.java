package io.jeeyeon.app.ticketReserve.domain.queueToken;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class QueueTokenServiceTest {

    @Mock
    private QueueTokenRepository queueTokenRepository;

    @InjectMocks
    private QueueTokenService queueTokenService;

    private static final int MAX_ACTIVE_TOKENS = 100;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("유효한 토큰으로 검증 성공")
    public void testIsValidToken_validToken() {
        // given
        Long concertId = 1L;
        Long tokenId = 1L;
        Long sequenceId = 1L;
        QueueToken validToken = new QueueToken(concertId, tokenId, sequenceId);
        validToken.activateQueueToken();

        // 모의 객체 설정
        when(queueTokenRepository.findByTokenIdAndConcertId(concertId, tokenId))
                .thenReturn(Optional.of(validToken));

        // when
        QueueToken result = queueTokenService.validateActiveToken(concertId, tokenId);

        // then
        assertEquals(validToken, result);
    }

    @Test
    @DisplayName("유효하지 않은 토큰 ID로 검증 실패")
    public void testIsValidToken_invalidTokenId() {
        // given
        Long concertId = 1L;
        Long invalidTokenId = 2L;

        // 모의 객체 설정
        when(queueTokenRepository.findByTokenIdAndConcertId(concertId, invalidTokenId))
                .thenReturn(Optional.empty());

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            queueTokenService.validateActiveToken(concertId, invalidTokenId);
        });
    }

    @Test
    @DisplayName("활성화되지 않은 토큰으로 검증 실패")
    public void testIsValidToken_inactiveToken() {
        // given
        Long concertId = 1L;
        Long inactiveTokenId = 3L;
        Long sequenceId = 1L;
        QueueToken inactiveToken = new QueueToken(concertId, inactiveTokenId, sequenceId);

        // 모의 객체 설정
        when(queueTokenRepository.findByTokenIdAndConcertId(concertId, inactiveTokenId))
                .thenReturn(Optional.of(inactiveToken));

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            queueTokenService.validateActiveToken(concertId, inactiveTokenId);
        });
    }

    @Test
    @DisplayName("활성화할 토큰이 없을 때")
    public void testActivateQueueTokens_NoWaitingTokens() {
        // given
        Long concertId = 1L;
        List<QueueToken> emptyList = new ArrayList<>();
        when(queueTokenRepository.findByConcertIdAndStatus(anyLong(), eq(TokenStatus.ACTIVE)))
                .thenReturn(emptyList);
        when(queueTokenRepository.findTopNByConcertIdAndStatusOrderByCreatedAtAsc(anyLong(), eq(TokenStatus.WAITING), anyLong()))
                .thenReturn(emptyList);

        // when
        queueTokenService.activateQueueTokens(Collections.singletonList(concertId));

        // then
        verify(queueTokenRepository, never()).save(any(QueueToken.class));
    }

    private List<QueueToken> createTokenList(int count, TokenStatus status) {
        List<QueueToken> tokens = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            QueueToken token = new QueueToken(1L, 1L, 1L); // userId, concertId, sequenceId는 임의로 설정
            token.setStatus(status);
            tokens.add(token);
        }
        return tokens;
    }

    @Test
    @DisplayName("대기 토큰 활성화 테스트")
    public void testActivateQueueTokens() {
        // given
        List<Long> concertIds = Arrays.asList(1L, 2L, 3L);
        when(queueTokenRepository.findByConcertIdAndStatus(anyLong(), eq(TokenStatus.ACTIVE)))
                .thenReturn(new ArrayList<>()); // 활성화된 토큰 없음
        when(queueTokenRepository.findTopNByConcertIdAndStatusOrderByCreatedAtAsc(anyLong(), eq(TokenStatus.WAITING), anyLong()))
                .thenReturn(createTokenList(2)); // 대기 중인 토큰 2개 반환

        // when
        long totalActivatedTokens = queueTokenService.activateQueueTokens(concertIds);

        // then
        verify(queueTokenRepository, times(6)).save(any(QueueToken.class)); // 총 6번 저장되어야 함
        assertEquals(6, totalActivatedTokens); // 대기 중인 토큰 총 6개가 활성화되어야 함 (각 콘서트당 2개씩)
    }

    private List<QueueToken> createTokenList(int count) {
        List<QueueToken> tokens = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            QueueToken token = new QueueToken(1L, 1L, 1L); // userId, concertId, sequenceId는 임의로 설정
            tokens.add(token);
        }
        return tokens;
    }
}
