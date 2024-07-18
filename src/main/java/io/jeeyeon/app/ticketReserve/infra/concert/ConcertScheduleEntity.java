package io.jeeyeon.app.ticketReserve.infra.concert;

import io.jeeyeon.app.ticketReserve.domain.concert.ConcertSchedule;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "CONCERT_SCHEDULE")
public class ConcertScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long concertScheduleId;
    private Long concertId;
    private LocalDateTime concertDate;
    private String location;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    public ConcertSchedule toConcertSchedule() {
        ConcertSchedule schedule = new ConcertSchedule();
        schedule.setConcertScheduleId(this.concertScheduleId);
        schedule.setConcertDate(this.concertDate);
        schedule.setLocation(this.location);
        schedule.setConcertId(this.concertId);
        return schedule;
    }
}
