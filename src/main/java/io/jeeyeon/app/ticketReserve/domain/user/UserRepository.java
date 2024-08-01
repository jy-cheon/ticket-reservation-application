package io.jeeyeon.app.ticketReserve.domain.user;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository {

    Optional<User> findByUserId(Long userId);

    Optional<User> findByUserIdWithPessimisticLock(Long userId);

    Optional<User> findByUserIdWithOptimisticLock(Long userId);

    User save(User user);

}
