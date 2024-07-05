package io.jeeyeon.app.ticketReserve.controller;

import io.jeeyeon.app.ticketReserve.controller.req.ReserveSeatRequest;
import io.jeeyeon.app.ticketReserve.controller.res.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RequestMapping
@RequiredArgsConstructor
@RestController
public class ReservationController {
    // 좌석 예약 요청 API
    @PostMapping("/concerts/reservation")
    public ReservationResponse reservation(@RequestBody ReserveSeatRequest reserveSeatRequest,
                                           @RequestHeader("Authorization") String token) {
        String reservationId = "ABC123";
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(5);

        return new ReservationResponse(reservationId, expirationTime);
    }
}
