package io.jeeyeon.app.ticketReserve.domain.user;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    public boolean exists(Long userId) {
        Optional<User> user = userRepository.findByUserId(userId);
        return userId != null && userId > 0;
    }

    public User chargeBalance(Long userId, Long amount) {
        User user = findByUserId(userId);
        user = user.chargeBalance(amount);
        return userRepository.save(user);
    }

    public User deductBalance(Long userId, Long price) throws Exception {
        User user = findByUserId(userId);
        user.deductBalance(price);
        userRepository.save(user);
        return user;
    }

    private User findByUserId(Long userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));
    }

    public Long checkBalance(Long userId) {
        User user = findByUserId(userId);
        return user.getBalance();
    }
}
