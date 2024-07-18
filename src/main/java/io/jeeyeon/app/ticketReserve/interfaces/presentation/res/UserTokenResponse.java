package io.jeeyeon.app.ticketReserve.interfaces.presentation.res;

import io.jeeyeon.app.ticketReserve.domain.queueToken.QueueToken;
import io.jeeyeon.app.ticketReserve.domain.queueToken.TokenStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserTokenResponse {
    private Long tokenId; // 3l
    private Long aheadCount;
    private TokenStatus status;// waiting, active, expired

    public UserTokenResponse(Long tokenId) {
        this.tokenId = tokenId;
    }

    public UserTokenResponse(QueueToken token) {
        this.tokenId = token.getTokenId();
        this.status = token.getStatus();
        this.aheadCount = token.getAheadCount();
    }
}
