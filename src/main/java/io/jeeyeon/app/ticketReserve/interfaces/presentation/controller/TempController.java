package io.jeeyeon.app.ticketReserve.interfaces.presentation.controller;


import io.jeeyeon.app.ticketReserve.application.ReservationManagerFacade;
import io.jeeyeon.app.ticketReserve.application.TokenManagerFacade;
import io.jeeyeon.app.ticketReserve.interfaces.presentation.res.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "임시 API(스케줄러에 붙일 기능)")
@RequestMapping("/api/temp")
@RequiredArgsConstructor
@RestController
public class TempController {
    private final TokenManagerFacade tokenManagerFacade;
    private final ReservationManagerFacade reservationManagerFacade;

    @Operation(summary = "토큰 활성화 API", description = "-")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @GetMapping("/activateTokens")
    public ResponseEntity<ResponseDto<?>> activateTokens() {
        tokenManagerFacade.activateQueueTokens();
        return ResponseEntity.ok(ResponseDto.success("성공"));
    }

    @Operation(summary = "토큰 만료 API", description = "-")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @GetMapping("/expireQueueTokens")
    public ResponseEntity<ResponseDto<?>> expireQueueTokens() {
        tokenManagerFacade.expireQueueTokens();
        return ResponseEntity.ok(ResponseDto.success("성공"));
    }

    @Operation(summary = "좌석 임시 배정 해제 API", description = "-")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @GetMapping("/cancelReservations")
    public void cancelReservations() {
        reservationManagerFacade.cancelReservations();
    }

}

