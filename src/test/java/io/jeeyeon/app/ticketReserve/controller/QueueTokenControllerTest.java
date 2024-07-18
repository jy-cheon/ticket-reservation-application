package io.jeeyeon.app.ticketReserve.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class QueueTokenControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("토큰 발급 테스트")
    @Sql(value = {"/member.sql","/concert.sql"})
    void 토큰발급테스트() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/concerts/3/token?userId=7"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("토큰 발급 테스트 - 사용자 없음")
    @Sql(value = {"/member.sql","/concert.sql"})
    void 토큰발급테스트_사용자_없음() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/concerts/3/token?userId=27"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("유저 토큰을 조회한다.")
    @Sql(value = {"/data-test.sql"})
    void 유저토큰조회() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/token?tokenId=3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.tokenId", is(3)));
    }

}