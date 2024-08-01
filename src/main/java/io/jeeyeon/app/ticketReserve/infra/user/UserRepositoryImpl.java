package io.jeeyeon.app.ticketReserve.infra.user;

import io.jeeyeon.app.ticketReserve.domain.user.User;
import io.jeeyeon.app.ticketReserve.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<User> findByUserId(Long userId) {
        Optional<UserEntity> userEntity = userJpaRepository.findById(userId);
        return userEntity.stream()
                .map(UserEntity::toUser)
                .findAny();
    }

    @Override
    public Optional<User> findByUserIdWithPessimisticLock(Long userId) {
        Optional<UserEntity> userEntity = userJpaRepository.findByIdWithPessimisticLock(userId);
        return userEntity.stream()
                .map(UserEntity::toUser)
                .findAny();
    }

    @Override
    public Optional<User> findByUserIdWithOptimisticLock(Long userId) {
        Optional<UserEntity> userEntity = userJpaRepository.findByIdWithOptimisticLock(userId);
        return userEntity.stream()
                .map(UserEntity::toUser)
                .findAny();
    }

    @Override
    public User save(User user) {
        UserEntity save = userJpaRepository.save(new UserEntity(user));
        User userModel = new User();
        userModel.setUserId(save.getUserId());
        userModel.setBalance(save.getBalance());
        userModel.setVersion(save.getVersion());
        return userModel;
    }


}
