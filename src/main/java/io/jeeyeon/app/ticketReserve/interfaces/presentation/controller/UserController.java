package io.jeeyeon.app.ticketReserve.interfaces.presentation.controller;

import io.jeeyeon.app.ticketReserve.application.UserManagerFacade;
import io.jeeyeon.app.ticketReserve.domain.user.User;
import io.jeeyeon.app.ticketReserve.interfaces.presentation.req.ChargeRequest;
import io.jeeyeon.app.ticketReserve.interfaces.presentation.res.BalanceResponse;
import io.jeeyeon.app.ticketReserve.interfaces.presentation.res.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "유저 잔액 API")
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserManagerFacade userManagerFacade;

    @Operation(summary = "잔액 조회 API", description = "잔액을 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @GetMapping("/{userId}/balance")
    public ResponseEntity<ResponseDto<?>> balance(
            @Parameter(description = "유저ID", required = true)
            @PathVariable Long userId) {
        Integer balance = userManagerFacade.checkBalance(userId);
        BalanceResponse balanceResponse = new BalanceResponse(userId, balance);
        return ResponseEntity.ok(ResponseDto.success(balanceResponse));
    }

    @Operation(summary = "잔액 충전 API", description = "잔액을 충전한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved available dates"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Concert not found")
    })
    @PatchMapping("/{userId}/charge")
    public ResponseEntity<ResponseDto<?>> charge(
            @Parameter(description = "유저 ID", required = true)
            @PathVariable Long userId,
            @RequestBody ChargeRequest request) {

        User user = userManagerFacade.chargeBalance(userId, request.getAmount());
        BalanceResponse response = new BalanceResponse(user.getUserId(), user.getBalance());
        return ResponseEntity.ok(ResponseDto.success(response));

    }

}
