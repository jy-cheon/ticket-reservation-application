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
class ConcertControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("1. 예약 가능 날짜를 조회한다.")
    @Sql("/data-test.sql")
    void 예약가능날짜_조회_테스트() throws Exception {
        Long tokenId = 2L;
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/concerts/1/dates")
                        .header("Auth", tokenId))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("2. 예약 가능 좌석을 조회한다.")
    @Sql("/data-test.sql")
    void 예약_가능_좌석을_조회한다() throws Exception {
        Long tokenId = 5L;
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/concerts/3/seats?date=2025-07-19 18:30:00")
                        .header("Auth", tokenId))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("3. 예약 가능 좌석을 조회한다. - 콘서트 정보 없음")
    @Sql("/data-test.sql")
    void 예약_가능_좌석을_조회한다_콘서트_없음_테스트() throws Exception {
        Long tokenId = 5L;
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/concerts/3/seats?date=2025-08-19 18:30:00")
                        .header("Auth", tokenId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.responseMsg", is("해당 엔터티를 찾을 수 없습니다.")));
    }

}