package io.jeeyeon.app.ticketReserve.application;

import io.jeeyeon.app.ticketReserve.domain.user.User;
import io.jeeyeon.app.ticketReserve.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
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
        int RETRY_MAX = Integer.MAX_VALUE;
        int tryCnt = 0;
        while (tryCnt < RETRY_MAX) {
            try {
                return userService.chargeBalanceOptimistic(userId, amount);
            } catch (ObjectOptimisticLockingFailureException e) {
                tryCnt += 1;
            }
        }
        throw new RuntimeException("완전실패했어요.....");
    }

    // 잔액 조회
    public Integer checkBalance(Long userId) {
        return userService.checkBalance(userId);
    }


}
