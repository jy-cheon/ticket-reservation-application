package io.jeeyeon.app.ticketReserve.domain.user;

import io.jeeyeon.app.ticketReserve.domain.common.exception.BaseException;
import io.jeeyeon.app.ticketReserve.domain.common.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    public boolean exists(Long userId) {
        Optional<User> user = userRepository.findByUserId(userId);
        return user.isPresent();
    }

    public User chargeBalance(Long userId, Integer amount) {
        User user = findByUserId(userId);
        user = user.chargeBalance(amount);
        return userRepository.save(user);
    }

    public User deductBalance(Long userId, Integer price) throws Exception {
        User user = findByUserId(userId);
        user.deductBalance(price);
        userRepository.save(user);
        return user;
    }

    private User findByUserId(Long userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new BaseException(ErrorType.ENTITY_NOT_FOUND));
    }

    public Integer checkBalance(Long userId) {
        User user = findByUserId(userId);
        return user.getBalance();
    }
}
