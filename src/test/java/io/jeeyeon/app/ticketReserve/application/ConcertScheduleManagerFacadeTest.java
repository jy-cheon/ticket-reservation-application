package io.jeeyeon.app.ticketReserve.application;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
@SpringBootTest
@Transactional
public class ConcertScheduleManagerFacadeTest {

    @Autowired
    private ConcertScheduleManagerFacade facade;

    @Test
    @DisplayName("예약 가능 날짜 조회 테스트")
    @Sql("/concert.sql")
    public void testGetAvailableDatesForReservation() {
        // given
        Long concertId = 1L;
        Long tokenId = 1L;

        // when
        List<LocalDateTime> result = facade.getAvailableDatesForReservation(concertId, tokenId);

        // then
        // LocalDateTime 객체로 변환하여 비교
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime expectedDateTime = LocalDateTime.parse("2025-07-15 19:00:00", formatter);
        assertEquals(expectedDateTime, result.get(0));
    }

    @Test
    @DisplayName("예약 가능 좌석 조회 테스트시, 해당 날짜에 좌석이 없으면 실패한다.")
    @Sql("/concert.sql")
    void testGetAvailableSeatsForReservation_NoSeatsAvailable() {
        // given
        Long concertId = 1L;
        Long tokenId = 1L;
        String date = "2024-07-15 01:00:00";

        // when / then
        assertThrows(Exception.class, () -> {
            facade.getAvailableSeatsForReservation(concertId, date, tokenId);
        });
    }

    @Test
    @DisplayName("예약 가능 좌석 조회 테스트")
    @Sql("/concert.sql")
    public void testGetAvailableSeatsForReservation() {
        // given
        Long concertId = 1L;
        Long tokenId = 1L;
        String date = "2024-07-15";

        // when / then
        assertThrows(DateTimeParseException.class, () -> {
            facade.getAvailableSeatsForReservation(concertId, date, tokenId);
        });

    }
}

