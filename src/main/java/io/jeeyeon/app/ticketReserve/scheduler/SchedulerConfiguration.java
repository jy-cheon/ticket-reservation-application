package io.jeeyeon.app.ticketReserve.scheduler;

import io.jeeyeon.app.ticketReserve.application.TokenManagerFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@EnableScheduling
public class SchedulerConfiguration {
    private final TokenManagerFacade tokenManagerFacade;

    @Scheduled(cron = "0 * * * * *")
    public void activateTokens() {
        tokenManagerFacade.activateQueueTokens();
    }

    @Scheduled(cron = "0 * * * * *")
    public void expireTokens() {
        tokenManagerFacade.expireQueueTokens();
    }
}
