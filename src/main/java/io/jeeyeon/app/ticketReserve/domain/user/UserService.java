package io.jeeyeon.app.ticketReserve.domain.user;

import io.jeeyeon.app.ticketReserve.domain.common.exception.BaseException;
import io.jeeyeon.app.ticketReserve.domain.common.exception.ErrorType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public boolean exists(Long userId) {
        Optional<User> user = userRepository.findByUserId(userId);
        return user.isPresent();
    }

    // 비관적락 테스트
    @Transactional
    public User chargeBalancePessimistic(Long userId, Integer amount) {
        User user = getUserWithPessimisticLock(userId);
        user = user.chargeBalance(amount);
        return userRepository.save(user);
    }

    @Transactional
    public User chargeBalanceOptimistic(Long userId, Integer amount) {
        User user = getUserWithOptimisticLock(userId);
        user = user.chargeBalance(amount);
        return userRepository.save(user);
    }

    public User deductBalance(Long userId, Integer price) throws Exception {
        User user = getUser(userId);
        user.deductBalance(price);
        userRepository.save(user);
        return user;
    }

    private User getUser(Long userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new BaseException(ErrorType.ENTITY_NOT_FOUND));
    }

    private User getUserWithPessimisticLock(Long userId) {
        return userRepository.findByUserIdWithPessimisticLock(userId)
                .orElseThrow(() -> new BaseException(ErrorType.ENTITY_NOT_FOUND));
    }

    private User getUserWithOptimisticLock(Long userId) {
        return userRepository.findByUserIdWithOptimisticLock(userId)
                .orElseThrow(() -> new BaseException(ErrorType.ENTITY_NOT_FOUND));
    }

    public Integer checkBalance(Long userId) {
        User user = getUser(userId);
        return user.getBalance();
    }
}
