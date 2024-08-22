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
    @Column(name = "SEAT_ID")
    private Long seatId;
    @Column(name = "CONCERT_SCHEDULE_ID")
    private Long concertScheduleId;
    @Column(name = "SEAT_NUMBER")
    private String seatNumber;
    @Column(name = "TICKET_PRICE")
    private Integer ticketPrice;
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private SeatStatus status;
    @Version
    @Column(name = "VERSION")
    private int version;
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;
    @Column(name = "UPDATED_AT")
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
