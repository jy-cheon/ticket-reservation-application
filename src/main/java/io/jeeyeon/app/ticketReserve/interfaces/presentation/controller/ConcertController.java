package io.jeeyeon.app.ticketReserve.interfaces.presentation.controller;

import io.jeeyeon.app.ticketReserve.application.ConcertScheduleManagerFacade;
import io.jeeyeon.app.ticketReserve.domain.seat.Seat;
import io.jeeyeon.app.ticketReserve.interfaces.presentation.res.ConcertResponse;
import io.jeeyeon.app.ticketReserve.interfaces.presentation.res.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "콘서트 API")
@RequestMapping("/api/v1/concerts")
@RequiredArgsConstructor
@RestController
public class ConcertController {
    private final ConcertScheduleManagerFacade concertScheduleManagerFacade;

    @Operation(summary = "콘서트 목록 조회 API", description = "예약 가능한 콘서트 목록을 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @GetMapping("")
    public ResponseEntity<ResponseDto<?>> getConcertList() {
        var list = concertScheduleManagerFacade.getConcertList();
        var concertResponse = ConcertResponse.fromConcertList(list);
        return ResponseEntity.ok(ResponseDto.success(concertResponse));
    }

    @Operation(summary = "예약 가능 날짜 API", description = "예약 가능 날짜를 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @GetMapping("/{concertId}/dates")
    public ResponseEntity<ResponseDto<?>> dates(
            @Parameter(description = "콘서트 ID 입력하세요.", required = true)
            @PathVariable Long concertId,
            @Parameter(in = ParameterIn.HEADER, description = "토큰 값 입력", required = true)
            @RequestHeader("Auth") Long token) {

        List<LocalDateTime> dateTimes = concertScheduleManagerFacade.getAvailableDatesForReservation(concertId);
        return ResponseEntity.ok(ResponseDto.success(dateTimes));
    }

    @Operation(summary = "예약 가능 좌석 API", description = "예약 가능 좌석을 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved available dates"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Concert not found")
    })
    @GetMapping("/{concertId}/seats")
    public ResponseEntity<ResponseDto<?>> seats(
            @Parameter(description = "콘서트 ID 입력하세요.", required = true)
            @PathVariable Long concertId,
            @Parameter(description = "날짜입력(yyyy-MM-dd HH:mm:ss)", required = true)
            @RequestParam String date,
            @Parameter(description = "토큰 값 입력", required = true)
            @RequestHeader("Auth") Long token) {

        List<Seat> list =  concertScheduleManagerFacade.getAvailableSeatsForReservation(concertId, date);
        return ResponseEntity.ok(ResponseDto.success(list));
    }
}
