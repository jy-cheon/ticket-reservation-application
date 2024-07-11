package io.jeeyeon.app.ticketReserve.presentation.controller;


import io.jeeyeon.app.ticketReserve.presentation.res.UserTokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "토큰발급 API")
@RequestMapping("")
@RequiredArgsConstructor
@RestController
public class QueueTokenController {

    @Operation(summary = "유저 토큰 발급 API", description = "유저 토큰을 발급한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @PostMapping("/concerts/{concertId}/token")
    public UserTokenResponse token(@RequestParam String userId) {
        return new UserTokenResponse(3576L);
    }
}
