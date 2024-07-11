package io.jeeyeon.app.ticketReserve.presentation.controller;


import io.jeeyeon.app.ticketReserve.presentation.res.DatesResponse;
import io.jeeyeon.app.ticketReserve.presentation.res.SeatResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "콘서트 API")
@RequestMapping("/concerts")
@RequiredArgsConstructor
@RestController
public class ConcertController {

    @Operation(summary = "예약 가능 날짜 API", description = "예약 가능 날짜를 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @GetMapping("/{concertId}/dates")
    public DatesResponse dates(
            @Parameter(description = "콘서트ID 입력하세요.", required = true)
            @PathVariable String concertId,
            @Parameter(description = "토큰 값 입력", required = true)
            @RequestHeader("Authorization") String token) {
        return new DatesResponse(List.of("2024-07-05", "2024-07-06", "2024-07-07"));
    }

    @Operation(summary = "예약 가능 좌석 API", description = "예약 가능 좌석을 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved available dates"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Concert not found")
    })
    @GetMapping("/{concertId}/dates/{date}/seats")
    public SeatResponse seats(
            @Parameter(description = "콘서트ID 입력하세요.", required = true)
            @PathVariable String concertId,
            @Parameter(description = "날짜입력(YYYY-MM-DD)", required = true)
            @PathVariable String date,
            @Parameter(description = "토큰 값 입력", required = true)
            @RequestHeader("Authorization") String token) {

        List<SeatResponse.Seat> availableSeats = new ArrayList<>();
        availableSeats.add(new SeatResponse.Seat("1", "b48", 80000L));
        availableSeats.add(new SeatResponse.Seat("2", "c32", 60000L));
        availableSeats.add(new SeatResponse.Seat("3", "a17", 75000L));

        return new SeatResponse(availableSeats);
    }
}
