package io.jeeyeon.app.ticketReserve.presentation.controller;

import io.jeeyeon.app.ticketReserve.presentation.req.ReserveSeatRequest;
import io.jeeyeon.app.ticketReserve.presentation.res.ReservationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@Tag(name = "좌석 예약 API")
@RequiredArgsConstructor
@RestController
public class ReservationController {

    @Operation(summary = "좌석 예약 요청 API", description = "좌석 예약 요청한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @PostMapping("/concerts/{concertId}/reservation")
    public ReservationResponse reservation(
            @RequestBody ReserveSeatRequest reserveSeatRequest,
            @Parameter(description = "콘서트 아이디", required = true, example = "123")
            @RequestParam("concertId") String concertId,
            @Parameter(description = "토큰 값 입력", required = true)
            @RequestHeader("Authorization") String token) {
        String reservationId = "ABC123";
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(5);

        return new ReservationResponse(reservationId, expirationTime);
    }
}
