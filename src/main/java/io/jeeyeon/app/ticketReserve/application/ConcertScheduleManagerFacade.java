package io.jeeyeon.app.ticketReserve.application;

import io.jeeyeon.app.ticketReserve.domain.concert.Concert;
import io.jeeyeon.app.ticketReserve.domain.concert.ConcertSchedule;
import io.jeeyeon.app.ticketReserve.domain.concert.ConcertService;
import io.jeeyeon.app.ticketReserve.domain.seat.Seat;
import io.jeeyeon.app.ticketReserve.domain.queueToken.QueueTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ConcertScheduleManagerFacade {
    private final ConcertService concertService;
    private final QueueTokenService queueTokenService;

    // 예약 가능 날짜 조회
    public List<LocalDateTime> getAvailableDatesForReservation(Long concertId) {
        // 날짜 조회
        Concert concert = concertService.getConcertSchedule(concertId);
        List<ConcertSchedule> scheduleList = concert.getSchedules();
        return scheduleList.stream().map(ConcertSchedule::getConcertDate).collect(Collectors.toList());
    }

    // 예약 가능 좌석 조회
    public List<Seat> getAvailableSeatsForReservation(Long concertId, String date) {
        // 좌석 조회
        List<Seat> seats = concertService.getConcertSeats(concertId, date);
        return seats;
    }

    // 콘서트 목록 조회
    public List<Concert> getConcertList() {
        return concertService.getAvailableConcerts();
    }

    public void registerConcert(String concertName) {
        concertService.registerConcert(concertName);
    }
}
