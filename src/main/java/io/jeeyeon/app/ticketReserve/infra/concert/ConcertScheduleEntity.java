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
    @Column(name = "CONCERT_SCHEDULE_ID")
    private Long concertScheduleId;
    @Column(name = "CONCERT_ID")
    private Long concertId;
    @Column(name = "CONCERT_DATE")
    private LocalDateTime concertDate;
    @Column(name = "LOCATION")
    private String location;
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;
    @Column(name = "UPDATED_AT")
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
