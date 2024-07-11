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
        UserEntity userEntity = userJpaRepository.findByUserId(userId);
        return Optional.ofNullable(userEntity.toUser());
    }

    @Override
    public User save(User user) {
        UserEntity save = userJpaRepository.save(new UserEntity(user));
        User userModel = new User();
        userModel.setUserId(save.getUserId());
        userModel.setBalance(save.getBalance());
        return userModel;
    }


}
