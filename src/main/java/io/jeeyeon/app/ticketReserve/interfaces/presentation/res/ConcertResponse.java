package io.jeeyeon.app.ticketReserve.interfaces.presentation.res;

import io.jeeyeon.app.ticketReserve.domain.concert.Concert;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@AllArgsConstructor
public class ConcertResponse {
    private Long concertId;
    private String concertName;

    public ConcertResponse(Concert concert) {
        this.concertId = concert.getConcertId();
        this.concertName = concert.getConcertName();
    }

    public static List<ConcertResponse> fromConcertList(List<Concert> concertList) {
        return concertList.stream()
                .map(ConcertResponse::new)
                .collect(Collectors.toList());
    }
}
