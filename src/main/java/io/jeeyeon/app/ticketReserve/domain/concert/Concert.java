package io.jeeyeon.app.ticketReserve.domain.concert;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class Concert {
    private Long concertId;
    private String concertName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ConcertSchedule> schedules;

    public Concert(Long concertId, String concertName) {
        this.concertId = concertId;
        this.concertName = concertName;
    }
    public Concert(String concertName) {
        this.concertName = concertName;
    }
}
