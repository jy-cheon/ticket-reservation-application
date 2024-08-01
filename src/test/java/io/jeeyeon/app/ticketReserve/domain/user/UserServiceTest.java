package io.jeeyeon.app.ticketReserve.domain.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void init() {
        User user = new User();
        user.setUserId(1l);
        user.setBalance(1000);
        user.setVersion(1);
        userRepository.save(user);
    }

    @Test
    @DisplayName("유저 잔액 충전 동시성 테스트 - 비관적락")
    void chargeBalance() throws InterruptedException {
        // given : user 1000원

        // when
        int THREAD_COUNT = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            executorService.submit(() -> {
                try {
                    userService.chargeBalancePessimistic(1l, 1);
                } catch (Exception e) {

                } finally {
                  latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        // then
        int balance = userService.checkBalance(1l);
        Assertions.assertEquals(2000, balance);
    }

    @Test
    @DisplayName("유저 잔액 충전 동시성 테스트 - 낙관적락")
    void chargeBalance2() throws InterruptedException {
        // given : user 1000원

        // when
        int THREAD_COUNT = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

        System.out.println("1 : " + Thread.currentThread().getName());
        for (int i = 0; i < THREAD_COUNT; i++) {
            executorService.submit(() -> {
                try {
                    userService.chargeBalanceOptimistic(1l, 200);
                    System.out.println("2 : " + Thread.currentThread().getName());
                } catch (Exception e) {

                } finally {
                    latch.countDown();
                }
            });
        }

        System.out.println("3 : " + Thread.currentThread().getName());
        latch.await();
        executorService.shutdown();
        System.out.println("4 : " + Thread.currentThread().getName());

        // then
        int balance = userService.checkBalance(1l);
        Assertions.assertEquals(1200, balance);
    }

    @Test
    void deductBalance() {
    }
}