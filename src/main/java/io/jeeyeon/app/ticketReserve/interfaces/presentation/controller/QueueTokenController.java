package io.jeeyeon.app.ticketReserve.interfaces.presentation.controller;


import io.jeeyeon.app.ticketReserve.application.TokenManagerFacade;
import io.jeeyeon.app.ticketReserve.domain.queueToken.QueueToken;
import io.jeeyeon.app.ticketReserve.interfaces.presentation.res.ResponseDto;
import io.jeeyeon.app.ticketReserve.interfaces.presentation.res.UserTokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "토큰 API")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@RestController
public class QueueTokenController {
    private final TokenManagerFacade tokenManagerFacade;

    @Operation(summary = "유저 토큰 발급 API", description = "유저 토큰을 발급한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @PostMapping("/concerts/{concertId}/token")
    public ResponseEntity<ResponseDto<?>> issueToken(@PathVariable Long concertId, @RequestParam Long userId) {
        tokenManagerFacade.issueToken(userId, concertId);
        return ResponseEntity.ok(ResponseDto.success("성공"));
    }

    @Operation(summary = "유저 토큰 조회 API", description = "유저 토큰을 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @GetMapping("/token")
    public ResponseEntity<ResponseDto<?>> getTokenInfo(@RequestParam Long concertId, @RequestParam Long userId) {
        QueueToken token = tokenManagerFacade.getTokenInfo(concertId, userId);
        return ResponseEntity.ok(ResponseDto.success(new UserTokenResponse(token)));
    }

}
