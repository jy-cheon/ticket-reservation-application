package io.jeeyeon.app.ticketReserve.controller;


import io.jeeyeon.app.ticketReserve.controller.res.UserTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping
@RequiredArgsConstructor
@RestController
public class QueueTokenController {

    // 유저 토큰 발급 API
    @PostMapping("/token")
    public UserTokenResponse token(@RequestBody String uuid) {
        return new UserTokenResponse("jeeyeon.iuconcert01.2024-07-04");
    }
}
