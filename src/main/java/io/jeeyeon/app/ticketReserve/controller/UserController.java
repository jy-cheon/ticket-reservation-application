package io.jeeyeon.app.ticketReserve.controller;

import io.jeeyeon.app.ticketReserve.controller.req.ChargeRequest;
import io.jeeyeon.app.ticketReserve.controller.res.BalanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/users")
@RequiredArgsConstructor
@RestController
public class UserController {

    // 잔액 조회 API
    @GetMapping("/{userId}/balance")
    public BalanceResponse balance(@PathVariable String userId) {
        int balance = 50000;
        return new BalanceResponse(userId, balance);
    }

    // 잔액 충전 API
    @PostMapping("/charge")
    public BalanceResponse charge(@RequestBody ChargeRequest request) {
        try {
            BalanceResponse response = new BalanceResponse("user", 500000);
            return response;
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }

}
