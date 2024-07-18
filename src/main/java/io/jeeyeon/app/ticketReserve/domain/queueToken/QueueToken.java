package io.jeeyeon.app.ticketReserve.domain.queueToken;

import io.jeeyeon.app.ticketReserve.domain.common.exception.BaseException;
import io.jeeyeon.app.ticketReserve.domain.common.exception.ErrorType;
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
    private Long aheadCount;
    private TokenStatus status;// waiting, active, expired
    private LocalDateTime activatedAt;
    private LocalDateTime expiredAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public QueueToken(Long userId, Long concertId, Long sequenceId) {
        this.userId = userId;
        this.concertId = concertId;
        this.sequenceId = sequenceId;
        this.status = TokenStatus.WAITING;
        this.expiredAt = LocalDateTime.now().plusHours(24); // waiting 토큰은 24시간 만료
    }

    public boolean isActive() {
        if (this.status == null) {
            throw new BaseException(ErrorType.ENTITY_NOT_FOUND);
        }

        if (this.status == TokenStatus.EXPIRED) {
            throw new BaseException(ErrorType.INVALID_TOKEN);
        }

        return this.status.equals(TokenStatus.ACTIVE);
    }

    // 대기 토큰 활성화
    public QueueToken activateQueueToken() {
        this.status = TokenStatus.ACTIVE;
        this.activatedAt = LocalDateTime.now(); // 활성화시간 저장
        this.expiredAt = LocalDateTime.now().plusMinutes(30); // 활성화 30분뒤 만료
        this.aheadCount = 0l;
        return this;
    }

    public QueueToken expireQueueToken() {
        this.status = TokenStatus.EXPIRED;
        return this;
    }

    public boolean isExpired() {
        return this.status.equals(TokenStatus.EXPIRED);
    }

    public boolean isWaiting() {
        return this.status.equals(TokenStatus.WAITING);
    }

    public void validateActiveToken() {
        switch (this.status) {
            case WAITING -> throw new BaseException(ErrorType.NOT_ACTIVE_TOKEN);
            case EXPIRED -> throw new BaseException(ErrorType.EXPIRED_TOKEN);
        }
    }


}
