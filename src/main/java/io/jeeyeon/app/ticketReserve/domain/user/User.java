package io.jeeyeon.app.ticketReserve.domain.user;

import io.jeeyeon.app.ticketReserve.domain.common.exception.BaseException;
import io.jeeyeon.app.ticketReserve.domain.common.exception.ErrorType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
    private Long userId;
    private Integer balance;

    public User chargeBalance(Integer amount) {
        this.setBalance(this.balance + amount);
        return this;
    }

    public User deductBalance(Integer price) throws Exception {
        if (this.getBalance() < price) {
            throw new BaseException(ErrorType.INSUFFICIENT_BALANCE);
        }
        this.balance -= price;
        return this;
    }
}
