package io.jeeyeon.app.ticketReserve.presentation.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReserveSeatRequest {
    @Schema(description = "유저 아이디", example = "guppy4411")
    private String userId;
    @Schema(description = "날짜", example = "2024-04-01")
    private String date;
    @Schema(description = "좌석번호", example = "23")
    private String seatNumber;
}
