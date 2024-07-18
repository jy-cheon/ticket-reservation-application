package io.jeeyeon.app.ticketReserve.interfaces.scheduler;

import io.jeeyeon.app.ticketReserve.application.ReservationManagerFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@EnableScheduling
public class ReservationScheduler {
     private final ReservationManagerFacade reservationManagerFacade;

//    @Scheduled(cron = "0 * * * * *")
    // 좌석 임시 배정 해제
    public void cancelReservations() {
        reservationManagerFacade.cancelReservations();
    }
}
