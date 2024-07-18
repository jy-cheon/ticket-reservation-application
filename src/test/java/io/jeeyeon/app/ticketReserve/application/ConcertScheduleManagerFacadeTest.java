package io.jeeyeon.app.ticketReserve.application;

import io.jeeyeon.app.ticketReserve.domain.concert.ConcertService;
import io.jeeyeon.app.ticketReserve.domain.queueToken.QueueToken;
import io.jeeyeon.app.ticketReserve.domain.queueToken.QueueTokenService;
import io.jeeyeon.app.ticketReserve.domain.seat.Seat;
import io.jeeyeon.app.ticketReserve.domain.seat.SeatStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ConcertScheduleManagerFacadeTest {

    @Mock
    private ConcertService concertService;

    @Mock
    private QueueTokenService queueTokenService;

    @InjectMocks
    private ConcertScheduleManagerFacade facade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

//    @Test
//    @DisplayName("예약 가능 날짜 조회 테스트")
//    public void testGetAvailableDatesForReservation() {
//        // given
//        Long concertId = 1L;
//        Long tokenId = 1L;
//        Long sequenceId = 1L;
//        QueueToken mockToken = new QueueToken(tokenId, concertId, sequenceId);
//        Concert mockConcert = new Concert(concertId, "Mock Concert");
//
//        LocalDateTime date1 = LocalDateTime.of(2024, 7, 15, 19, 0);
//        LocalDateTime date2 = LocalDateTime.of(2024, 7, 16, 19, 0);
//        List<LocalDateTime> expectedDates = Arrays.asList(date1, date2);
//
//        // mockito stubbing
//        when(queueTokenService.isValidToken(concertId, tokenId)).thenReturn(mockToken);
//        when(concertService.getConcertSchedule(concertId)).thenReturn(mockConcert);
//        when(mockConcert.getSchedules()).thenReturn(Arrays.asList(
//                new ConcertSchedule(1L, concertId, date1, "Location 1"),
//                new ConcertSchedule(2L, concertId, date2, "Location 2")
//        ));
//
//        // when
//        List<LocalDateTime> result = facade.getAvailableDatesForReservation(concertId, tokenId);
//
//        // then
//        assertEquals(expectedDates.size(), result.size());
//        assertEquals(expectedDates.get(0), result.get(0));
//        assertEquals(expectedDates.get(1), result.get(1));
//        verify(queueTokenService, times(1)).isValidToken(concertId, tokenId);
//        verify(concertService, times(1)).getConcertSchedule(concertId);
//    }

    @Test
    @DisplayName("예약 가능 좌석 조회 테스트")
    public void testGetAvailableSeatsForReservation() {
        // given
        Long concertId = 1L;
        Long tokenId = 1L;
        String date = "2024-07-15";
        QueueToken mockToken = new QueueToken(tokenId, concertId, 1L);
        List<Seat> expectedSeats = Arrays.asList(
                new Seat(1L, "A1", SeatStatus.AVAILABLE),
                new Seat(2L, "A2", SeatStatus.AVAILABLE)
        );

        // mockito stubbing
        when(queueTokenService.isValidToken(concertId, tokenId)).thenReturn(mockToken);
        when(concertService.getConcertSeats(concertId, date)).thenReturn(expectedSeats);

        // when
        List<Seat> result = facade.getAvailableSeatsForReservation(concertId, date, tokenId);

        // then
        assertEquals(expectedSeats.size(), result.size());
        assertEquals(expectedSeats.get(0).getSeatNumber(), result.get(0).getSeatNumber());
        assertEquals(expectedSeats.get(1).getSeatNumber(), result.get(1).getSeatNumber());
        verify(queueTokenService, times(1)).isValidToken(concertId, tokenId);
        verify(concertService, times(1)).getConcertSeats(concertId, date);
    }
}

