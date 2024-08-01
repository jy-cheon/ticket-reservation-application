package io.jeeyeon.app.ticketReserve.application;

import io.jeeyeon.app.ticketReserve.domain.user.User;
import io.jeeyeon.app.ticketReserve.domain.user.UserRepository;
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
class UserManagerFacadeConcurrencyTest {

    @Autowired
    UserManagerFacade userManagerFacade;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void init() {
        User user = new User();
        user.setUserId(1l);
        user.setBalance(1000);
        user.setVersion(0);
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
                    userManagerFacade.chargeBalancePessimistic(1l, 1);
                } catch (Exception e) {

                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        // then
        int balance = userManagerFacade.checkBalance(1l);
        Assertions.assertEquals(2000, balance);
    }

    @Test
    @DisplayName("유저 잔액 충전 동시성 테스트 - 낙관적락")
    void chargeBalance2() throws InterruptedException {
        // given : user 1000원

        // when
        int THREAD_COUNT = 50;
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

        System.out.println("1 : " + Thread.currentThread().getName());
        for (int i = 0; i < THREAD_COUNT; i++) {
            executorService.submit(() -> {
                try {
                    userManagerFacade.chargeBalanceOptimistic(1l, 1);
                } catch (Exception e) {
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        // then
        int balance = userManagerFacade.checkBalance(1l);
        Assertions.assertEquals(1050, balance);
    }





}