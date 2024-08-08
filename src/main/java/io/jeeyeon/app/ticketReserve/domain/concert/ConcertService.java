package io.jeeyeon.app.ticketReserve.domain.concert;

import io.jeeyeon.app.ticketReserve.domain.common.exception.BaseException;
import io.jeeyeon.app.ticketReserve.domain.common.exception.ErrorType;
import io.jeeyeon.app.ticketReserve.domain.seat.Seat;
import io.jeeyeon.app.ticketReserve.domain.seat.SeatRepository;
import io.jeeyeon.app.ticketReserve.domain.seat.SeatStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ConcertService {
    private final ConcertScheduleRepository concertScheduleRepository;
    private final ConcertRepository concertRepository;
    private final SeatRepository seatRepository;

    public Concert getConcertSchedule(Long concertId) {
        Concert concert = concertRepository.findByConcertId(concertId)
                .orElseThrow(() -> new BaseException(ErrorType.ENTITY_NOT_FOUND));

        // 현재 시간 이후의 콘서트 스케줄 조회
        LocalDateTime currentTime = LocalDateTime.now();
        List<ConcertSchedule> scheduleList = concertScheduleRepository.findByConcertId(concertId)
                .stream()
                .filter(schedule -> schedule.getConcertDate().isAfter(currentTime))
                .collect(Collectors.toList());

        if (scheduleList.size() < 1) throw new BaseException(ErrorType.ENTITY_NOT_FOUND);

        concert.setSchedules(scheduleList);

        return concert;
    }
    public ConcertSchedule getConcertSchedule(Long concertId, String dateTimeString) {
        final String pattern = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);

        // 해당 날짜의 스케줄 id 조회
        ConcertSchedule schedule = concertScheduleRepository.findByConcertIdAndConcertDate(concertId, dateTime)
                .orElseThrow(() -> new BaseException(ErrorType.ENTITY_NOT_FOUND));

        return schedule;
    }

    public List<Long> getAvailableConcertIds() {
        return this.getAvailableConcerts().stream()
                .map(Concert::getConcertId)
                .collect(Collectors.toList());
    }

    public List<Seat> getConcertSeats(Long concertId, String dateString) {
        // 스케줄 조회
        ConcertSchedule schedule = getConcertSchedule(concertId, dateString);

        // 좌석 조회
        List<Seat> seats = seatRepository.findByConcertScheduleIdAndStatus(schedule.getConcertScheduleId(), SeatStatus.AVAILABLE);
        return seats;
    }

    public boolean exists(Long concertId) {
        Optional<Concert> concert = concertRepository.findByConcertId(concertId);
        return concert.isPresent();
    }

    public List<Concert> getAvailableConcerts() {
        var list = concertRepository.findConcertsBeforeCurrentDate();
        return list;
    }

    public void registerConcert(String concertName) {
        Concert concert = new Concert(concertName);
        concertRepository.registerConcert(concert);
    }
}
