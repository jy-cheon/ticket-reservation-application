package io.jeeyeon.app.ticketReserve.domain.concert;

import io.jeeyeon.app.ticketReserve.domain.seat.Seat;
import io.jeeyeon.app.ticketReserve.domain.seat.SeatRepository;
import io.jeeyeon.app.ticketReserve.domain.seat.SeatStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ConcertService {
    private final ConcertScheduleRepository concertScheduleRepository;
    private final ConcertRepository concertRepository;
    private final SeatRepository seatRepository;

    public Concert getConcertSchedule(Long concertId) {
        Concert concert = concertRepository.findByConcertId(concertId)
                .orElseThrow(() -> new EntityNotFoundException("Concert not found with ID: " + concertId));

        // 현재 시간 이후의 콘서트 스케줄 조회
        LocalDateTime currentTime = LocalDateTime.now();
        List<ConcertSchedule> scheduleList = concertScheduleRepository.findByConcertId(concertId)
                .stream()
                .filter(schedule -> schedule.getConcertDate().isAfter(currentTime))
                .collect(Collectors.toList());

        concert.setSchedules(scheduleList);

        return concert;
    }
    public ConcertSchedule getConcertSchedule(Long concertId, String dateString) {
        LocalDateTime date = LocalDateTime.parse(dateString, DateTimeFormatter.ISO_DATE_TIME);

        // 해당 날짜의 스케줄 id 조회
        ConcertSchedule schedule = concertScheduleRepository.findByConcertIdAndConcertDate(concertId, date);

        if (schedule == null) {
            throw new EntityNotFoundException("Schedule not found for concertId: " + concertId + " and date: " + date);
        }
        return schedule;
    }


    public List<Long> getConcertIds() {
        return concertRepository.findAll()
                .stream()
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





}
