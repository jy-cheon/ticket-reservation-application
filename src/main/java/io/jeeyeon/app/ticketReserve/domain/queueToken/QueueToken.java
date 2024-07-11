package io.jeeyeon.app.ticketReserve.domain.queueToken;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class QueueToken {
    private Long tokenId;
    private Long userId;
    private Long concertId;
    private Long sequenceId; // 각 콘서트별 순서 ID
    @Enumerated(EnumType.STRING)
    private TokenStatus status;// waiting, active, expired
    private LocalDateTime activatedAt;
    private LocalDateTime expiredAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public QueueToken(Long userId, Long concertId, Long sequenceId) {
//        this.token = String.join(".", Long.toString(userId),Long.toString(concertId), LocalDateTime.now().toString());
        this.userId = userId;
        this.concertId = concertId;
        this.sequenceId = sequenceId;
        this.status = TokenStatus.WAITING;
        this.expiredAt = LocalDateTime.now().plusHours(24); // waiting 토큰은 24시간 만료
    }

    public boolean isActive() {
        if (this.status == null) {
            throw new IllegalStateException("Token is null");
        }

        if (this.status == TokenStatus.EXPIRED) {
            throw new IllegalStateException("Token is expired");
        }

        return this.status.equals(TokenStatus.ACTIVE);
    }

    // 대기 토큰 활성화
    public QueueToken activateQueueToken() {
        this.status = TokenStatus.ACTIVE;
        this.activatedAt = LocalDateTime.now(); // 활성화시간 저장
        this.expiredAt = LocalDateTime.now().minusMinutes(10); // 활성화 10분뒤 만료
        return this;
    }

    public QueueToken expireQueueToken() {
        this.status = TokenStatus.EXPIRED;
        return this;
    }
}
