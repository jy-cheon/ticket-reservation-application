package io.jeeyeon.app.ticketReserve.application;

import io.jeeyeon.app.ticketReserve.domain.user.User;
import io.jeeyeon.app.ticketReserve.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserManagerFacade {

    private final UserService userService;

    // 잔액 충전
    public User chargeBalancePessimistic(Long userId, Integer amount) {
        return userService.chargeBalancePessimistic(userId, amount);
    }

    // 잔액 충전
    public User chargeBalanceOptimistic(Long userId, Integer amount) {
        return userService.chargeBalanceOptimistic(userId, amount);
    }

    // 잔액 조회
    public Integer checkBalance(Long userId) {
        return userService.checkBalance(userId);
    }


}
