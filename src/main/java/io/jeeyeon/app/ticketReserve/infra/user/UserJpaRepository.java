package io.jeeyeon.app.ticketReserve.infra.user;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT u FROM UserEntity u WHERE u.userId = :userId")
    Optional<UserEntity> findByIdWithPessimisticLock(@Param("userId") Long userId);

//    @Lock(LockModeType.OPTIMISTIC)
    @Query("SELECT u FROM UserEntity u WHERE u.userId = :id")
    Optional<UserEntity> findByIdWithOptimisticLock(@Param("id") Long id);
}