package io.jeeyeon.app.ticketReserve.application;

import io.jeeyeon.app.ticketReserve.domain.reservation.Reservation;
import io.jeeyeon.app.ticketReserve.domain.reservation.ReservationRepository;
import io.jeeyeon.app.ticketReserve.domain.reservation.ReservationStatus;
import io.jeeyeon.app.ticketReserve.domain.seat.Seat;
import io.jeeyeon.app.ticketReserve.domain.seat.SeatRepository;
import io.jeeyeon.app.ticketReserve.domain.seat.SeatStatus;
import io.jeeyeon.app.ticketReserve.infra.concert.ConcertEntity;
import io.jeeyeon.app.ticketReserve.infra.concert.ConcertJpaRepository;
import io.jeeyeon.app.ticketReserve.infra.concert.ConcertScheduleEntity;
import io.jeeyeon.app.ticketReserve.infra.concert.ConcertScheduleJpaRepository;
import io.jeeyeon.app.ticketReserve.infra.seat.SeatEntity;
import io.jeeyeon.app.ticketReserve.infra.seat.SeatJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@SpringBootTest
class ReservationManagerFacadeConcurrencyTest {
    @Autowired
    private ReservationManagerFacade reservationManagerFacade;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ConcertJpaRepository concertJpaRepository;
    @Autowired
    private ConcertScheduleJpaRepository scheduleJpaRepository;
    @Autowired
    private SeatJpaRepository seatJpaRepository;
    @Autowired
    private SeatRepository seatRepository;

    @BeforeEach
    void init() {
        // 콘서트 생성
        ConcertEntity concertEntity = new ConcertEntity();
        concertEntity.setConcertId(1l);
        concertEntity.setConcertName("뉴진스 콘서트");

        // 콘서트 스케줄 생성
        ConcertScheduleEntity scheduleEntity = new ConcertScheduleEntity();
        scheduleEntity.setConcertScheduleId(1l);
        scheduleEntity.setConcertId(1l);
        scheduleEntity.setConcertDate(LocalDateTime.of(2024, 7, 28, 12, 00));
        scheduleEntity.setLocation("파리");
        scheduleJpaRepository.save(scheduleEntity);

        // 좌석 생성
        SeatEntity seatEntity = new SeatEntity();
        seatEntity.setSeatId(10L);
        seatEntity.setConcertScheduleId(1L);
        seatEntity.setSeatNumber("S15");
        seatEntity.setTicketPrice(100_000);
        seatEntity.setStatus(SeatStatus.AVAILABLE);
        seatJpaRepository.save(seatEntity);
    }

    @Test
    @DisplayName("좌석 예약 동시성 테스트 - 비관적락")
    public void test_applyForReservation() throws InterruptedException {
        // Given: 예약 1건 준비
        Long concertId = 1l;
        String dateTimeString = "2024-07-28 12:00:00";
        String seatNumber = "S15";
        Long tokenId = 7l;

        // When: 좌석 예약 요청
        int THREAD_COUNT = 5;
        int TRY_COUNT = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(TRY_COUNT);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        for (int i = 0; i < TRY_COUNT; i++) {
            executorService.submit(() -> {
                try {
                    reservationManagerFacade.applyForReservation(concertId, dateTimeString, seatNumber, tokenId);
                } catch (Exception e) {

                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        stopWatch.stop();
        System.out.println("총 걸린시간(초)" + stopWatch.getTotalTimeSeconds());

        // Then: 좌석이 예약이 되었는지 확인
        List<Reservation> reservations = reservationRepository.findAll();


        List<Reservation> reservationList = reservations.stream()
                .filter(r -> r.getTokenId().equals(tokenId))
                        .collect(Collectors.toList());

        Optional<Seat> seat = seatRepository.findById(reservationList.get(0).getSeatId());

        Assertions.assertEquals(1, reservationList.size());
        Assertions.assertEquals(ReservationStatus.RESERVED, reservationList.get(0).getStatus());
        Assertions.assertEquals(seatNumber, seat.get().getSeatNumber());
    }

    @Test
    @DisplayName("좌석 예약 동시성 테스트 - 낙관락")
    public void test_applyForReservation2() throws InterruptedException {
        // Given: 예약 1건 준비
        Long concertId = 1l;
        String dateTimeString = "2024-07-28 12:00:00";
        String seatNumber = "S15";
        Long tokenId = 7l;

        // When: 좌석 예약 요청
        int THREAD_COUNT = 5;
        int TRY_COUNT = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(TRY_COUNT);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        for (int i = 0; i < TRY_COUNT; i++) {
            executorService.submit(() -> {
                try {
                    reservationManagerFacade.applyForReservation(concertId, dateTimeString, seatNumber, tokenId);
                } catch (Exception e) {

                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        stopWatch.stop();
        System.out.println("총 걸린시간(초)" + stopWatch.getTotalTimeSeconds());

        // Then: 좌석이 예약이 되었는지 확인
        List<Reservation> reservations = reservationRepository.findAll();


        List<Reservation> reservationList = reservations.stream()
                .filter(r -> r.getTokenId().equals(tokenId))
                .collect(Collectors.toList());

        Optional<Seat> seat = seatRepository.findById(reservationList.get(0).getSeatId());

        Assertions.assertEquals(1, reservationList.size());
        Assertions.assertEquals(ReservationStatus.RESERVED, reservationList.get(0).getStatus());
        Assertions.assertEquals(seatNumber, seat.get().getSeatNumber());
    }

}