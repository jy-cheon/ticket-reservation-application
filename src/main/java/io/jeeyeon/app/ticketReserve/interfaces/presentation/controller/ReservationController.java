package io.jeeyeon.app.ticketReserve.interfaces.presentation.controller;

import io.jeeyeon.app.ticketReserve.application.ReservationManagerFacade;
import io.jeeyeon.app.ticketReserve.domain.reservation.Reservation;
import io.jeeyeon.app.ticketReserve.interfaces.presentation.req.ReserveSeatRequest;
import io.jeeyeon.app.ticketReserve.interfaces.presentation.res.ReservationResponse;
import io.jeeyeon.app.ticketReserve.interfaces.presentation.res.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "좌석 예약 API")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@RestController
public class ReservationController {
    private final ReservationManagerFacade reservationManagerFacade;

    @Operation(summary = "좌석 예약 요청 API", description = "좌석 예약 요청한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @PostMapping("/concerts/{concertId}/reservation")
    public ResponseEntity<ResponseDto<?>> reservation(
            @RequestBody ReserveSeatRequest reserveSeatRequest,
            @Parameter(description = "콘서트 아이디", required = true, example = "123")
            @PathVariable("concertId") Long concertId,
            @Parameter(description = "토큰 값 입력", required = true)
            @RequestHeader("Auth") Long token) {

        Reservation reservation = reservationManagerFacade.applyForReservation(concertId, reserveSeatRequest.getDate(), reserveSeatRequest.getSeatNumber()
                , token);


        return ResponseEntity.ok(ResponseDto.success(new ReservationResponse(reservation.getReservationId())));
    }
}
