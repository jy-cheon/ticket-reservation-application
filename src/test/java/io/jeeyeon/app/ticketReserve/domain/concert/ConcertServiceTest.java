package io.jeeyeon.app.ticketReserve.domain.concert;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConcertServiceTest {

    @Mock
    private ConcertScheduleRepository concertScheduleRepository;

    @Mock
    private ConcertRepository concertRepository;

    @InjectMocks
    private ConcertService concertService;

    @Test
    public void testGetConcertSchedule() {
        // Mock 데이터 설정
        Long concertId = 1L;
        LocalDateTime currentTime = LocalDateTime.of(2024, 7, 7, 12, 0); // 현재 시간 설정

        ConcertSchedule schedule1 = new ConcertSchedule(1L, concertId, LocalDateTime.of(2024, 7, 8, 15, 0), "서울"); // 7월 8일 오후 3시
        ConcertSchedule schedule2 = new ConcertSchedule(2L, concertId, LocalDateTime.of(2024, 7, 9, 10, 30), "서울"); // 7월 9일 오전 10시 30분
        ConcertSchedule schedule3 = new ConcertSchedule(3L, concertId, LocalDateTime.of(2024, 7, 6, 18, 0), "서울"); // 7월 6일 오후 6시 (과거 날짜)

        List<ConcertSchedule> mockSchedules = new ArrayList<>();
        mockSchedules.add(schedule1);
        mockSchedules.add(schedule2);
        mockSchedules.add(schedule3);

        // Mock repository 동작 설정
        when(concertScheduleRepository.findByConcertId(concertId)).thenReturn(mockSchedules);

        // 테스트 메서드 호출
        List<ConcertSchedule> result = concertService.getConcertSchedule(concertId).getSchedules();

        // 검증
        assertEquals(2, result.size()); // 현재 시간 이후의 스케줄이 2개여야 함
        assertEquals(schedule1, result.get(0)); // 첫 번째 스케줄이 schedule1과 같아야 함
        assertEquals(schedule2, result.get(1)); // 두 번째 스케줄이 schedule2와 같아야 함
    }


}