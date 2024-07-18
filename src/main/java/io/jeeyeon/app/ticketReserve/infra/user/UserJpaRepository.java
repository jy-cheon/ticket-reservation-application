package io.jeeyeon.app.ticketReserve.infra.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUserId(long userId);
}
