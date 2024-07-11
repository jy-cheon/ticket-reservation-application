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
    public User chargeBalance(Long userId, Long amount) {
        return userService.chargeBalance(userId, amount);
    }

    // 잔액 조회
    public Long checkBalance(Long userId) {
        return userService.checkBalance(userId);
    }


}
