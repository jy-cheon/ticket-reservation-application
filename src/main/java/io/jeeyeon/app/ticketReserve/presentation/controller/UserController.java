package io.jeeyeon.app.ticketReserve.presentation.controller;

import io.jeeyeon.app.ticketReserve.presentation.req.ChargeRequest;
import io.jeeyeon.app.ticketReserve.presentation.res.BalanceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "유저 잔액 API")
@RequestMapping("/users")
@RequiredArgsConstructor
@RestController
public class UserController {

    @Operation(summary = "잔액 조회 API", description = "잔액을 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @GetMapping("/{userId}/balance")
    public BalanceResponse balance(
            @Parameter(description = "유저ID", required = true)
            @PathVariable String userId) {
        int balance = 50000;
        return new BalanceResponse(userId, balance);
    }

    @Operation(summary = "잔액 충전 API", description = "잔액을 충전한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved available dates"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Concert not found")
    })
    @PatchMapping("/{userId}/charge")
    public BalanceResponse charge(
            @Parameter(description = "유저ID", required = true)
            @PathVariable String userId,
            @RequestBody ChargeRequest request) {
        try {
            BalanceResponse response = new BalanceResponse("user", 500000);
            return response;
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }

}
