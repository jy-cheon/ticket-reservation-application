package io.jeeyeon.app.ticketReserve.infra.concert;

import io.jeeyeon.app.ticketReserve.domain.concert.Concert;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "CONCERT")
public class ConcertEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long concertId;
    private String concertName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Concert toConcert() {
        Concert concert = new Concert();
        concert.setConcertId(concertId);
        concert.setConcertName(concertName);
        return concert;

    }
}
