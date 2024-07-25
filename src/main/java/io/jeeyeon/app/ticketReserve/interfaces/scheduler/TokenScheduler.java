package io.jeeyeon.app.ticketReserve.interfaces.scheduler;

import io.jeeyeon.app.ticketReserve.application.TokenManagerFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@EnableScheduling
public class TokenScheduler {
    private final TokenManagerFacade tokenManagerFacade;

    // 토큰 활성화
    //    @Scheduled(cron = "0 * * * * *")
    public void activateTokens() {
        tokenManagerFacade.activateQueueTokens();
    }

    // 토큰 만료
    //    @Scheduled(cron = "0 * * * * *")
    public void expireTokens() {
        tokenManagerFacade.expireQueueTokens();
    }

}
