package io.jeeyeon.app.ticketReserve.domain.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
    private Long userId;
    private Long balance;

    public User chargeBalance(Long amount) {
        this.setBalance(this.balance + amount);
        return this;
    }

    public User deductBalance(Long price) throws Exception {
        if (this.getBalance() < price) {
            throw new Exception("Insufficient balance for user ID: " + userId);
        }
        this.balance -= price;
        return this;
    }
}
