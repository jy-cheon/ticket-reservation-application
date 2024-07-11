package io.jeeyeon.app.ticketReserve.infra.queueToken;

import io.jeeyeon.app.ticketReserve.domain.queueToken.QueueToken;
import io.jeeyeon.app.ticketReserve.domain.queueToken.TokenStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "QUEUE_TOKEN")
public class QueueTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public QueueToken toQueueToken() {
        QueueToken token = new QueueToken();
        token.setTokenId(this.getTokenId());
        token.setUserId(this.userId);
        token.setConcertId(this.concertId);
        token.setSequenceId(this.sequenceId);
        token.setStatus(this.status);
        token.setActivatedAt(this.activatedAt);
        token.setExpiredAt(this.expiredAt);
        return token;
    }
}
