package io.jeeyeon.app.ticketReserve.infra.queueToken;

import io.jeeyeon.app.ticketReserve.domain.queueToken.TokenStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class QueueTokenJpaRepositoryTest {
    @Autowired
    private QueueTokenJpaRepository queueTokenRepository;

    @Test
    @Sql("/data-test.sql") // 필요한 경우 초기 데이터 셋업을 위한 SQL 파일을 지정할 수 있습니다.
    public void findByConcertIdAndStatus_WhenMatchingTokensExist_ShouldReturnMatchingTokens() {
        // Given
        Long concertId = 1L;
        TokenStatus status = TokenStatus.ACTIVE;

        // When
        List<QueueTokenEntity> tokens = queueTokenRepository.findByConcertIdAndStatus(concertId, status);

        // Then
        assertThat(tokens).isNotEmpty();
        assertThat(tokens.size()).isEqualTo(2); // 예상하는 결과 수와 일치하는지 확인
        QueueTokenEntity token = tokens.get(0);
        assertThat(token.getConcertId()).isEqualTo(concertId);
        assertThat(token.getStatus()).isEqualTo(status);
    }

    @Test
    public void findByConcertIdAndStatus_WhenNoMatchingTokens_ShouldReturnEmptyList() {
        // Given
        Long concertId = 999L; // 존재하지 않는 concertId
        TokenStatus status = TokenStatus.ACTIVE;

        // When
        List<QueueTokenEntity> tokens = queueTokenRepository.findByConcertIdAndStatus(concertId, status);

        // Then
        assertThat(tokens).isEmpty();
    }

}