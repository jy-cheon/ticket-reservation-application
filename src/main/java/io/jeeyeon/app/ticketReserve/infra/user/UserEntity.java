package io.jeeyeon.app.ticketReserve.infra.user;

import io.jeeyeon.app.ticketReserve.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "APP_USER")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private Long balance;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserEntity(Long userId) {
        this.userId = userId;
        this.balance = 0l;
    }

    public UserEntity(User user) {
        this.userId = user.getUserId();
        this.balance = user.getBalance();
    }

    public User toUser() {
        User user = new User();
        user.setUserId(this.userId);
        user.setBalance(this.balance);
        return user;
    }

}
