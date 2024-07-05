package io.jeeyeon.app.ticketReserve.controller;


import io.jeeyeon.app.ticketReserve.controller.res.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/concerts")
@RequiredArgsConstructor
@RestController
public class ConcertController {

    // 예약 가능 날짜 API
    @GetMapping("/{concertId}/dates")
    public DatesResponse dates(@PathVariable String concertId, @RequestHeader("Authorization") String token) {
        return new DatesResponse(List.of("2024-07-05", "2024-07-06", "2024-07-07"));
    }
    // 예약 가능 좌석 API
    @GetMapping("/{concertId}/dates/{date}/seats")
    public SeatResponse seats(@PathVariable String concertId, @PathVariable String date,
                              @RequestHeader("Authorization") String token) {

        List<SeatResponse.Seat> availableSeats = new ArrayList<>();
        availableSeats.add(new SeatResponse.Seat("1", "b48", 80000L));
        availableSeats.add(new SeatResponse.Seat("2", "c32", 60000L));
        availableSeats.add(new SeatResponse.Seat("3", "a17", 75000L));

        return new SeatResponse(availableSeats);
    }
}
