package io.jeeyeon.app.ticketReserve.domain.user;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository {

    Optional<User> findByUserId(Long userId);

    User save(User user);

}
