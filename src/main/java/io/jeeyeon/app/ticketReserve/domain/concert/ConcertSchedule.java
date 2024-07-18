package io.jeeyeon.app.ticketReserve.domain.concert;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ConcertSchedule {

    private Long concertScheduleId;
    private Long concertId;
    private LocalDateTime concertDate;
    private String location;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ConcertSchedule(Long concertScheduleId, Long concertId, LocalDateTime concertDate, String location) {
        this.concertScheduleId = concertScheduleId;
        this.concertId = concertId;
        this.concertDate = concertDate;
        this.location = location;
    }
}
