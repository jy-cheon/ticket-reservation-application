package io.jeeyeon.app.ticketReserve.infra.seat;

import io.jeeyeon.app.ticketReserve.domain.seat.Seat;
import io.jeeyeon.app.ticketReserve.domain.seat.SeatStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "SEAT")
public class SeatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;
    private Long concertScheduleId;
    private String seatNumber;
    private Integer ticketPrice;
    @Enumerated(EnumType.STRING)
    private SeatStatus status;
    @Version
    private int version;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public SeatEntity(Seat seat) {
        this.seatId = seat.getSeatId();
        this.concertScheduleId = seat.getConcertScheduleId();
        this.seatNumber = seat.getSeatNumber();
        this.ticketPrice = seat.getTicketPrice();
        this.status = seat.getStatus();
        this.version = seat.getVersion();
    }
    public Seat toSeat() {
        Seat seat = new Seat();
        seat.setSeatId(this.seatId);
        seat.setConcertScheduleId(this.concertScheduleId);
        seat.setSeatNumber(this.seatNumber);
        seat.setTicketPrice(this.ticketPrice);
        seat.setStatus(this.status);
        seat.setVersion(this.version);
        return seat;
    }


}
