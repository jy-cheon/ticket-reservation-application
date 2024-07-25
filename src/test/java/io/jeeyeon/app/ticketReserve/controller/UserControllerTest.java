package io.jeeyeon.app.ticketReserve.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("잔액조회")
    @Sql("/member.sql")
    void 잔액조회_테스트() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/1/balance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.userId", is(1)))
                .andExpect(jsonPath("$.result.balance", is(1000)));
    }

    @Test
    @DisplayName("잔액조회_실패_회원이 존재하지 않음.")
    @Sql("/member.sql")
    void 잔액조회_실패() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/10/balance"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.responseMsg", is("해당 엔터티를 찾을 수 없습니다.")));
    }

    @Test
    @DisplayName("잔액충전")
    @Sql("/member.sql")
    void 잔액충전_테스트() throws Exception {

        //given
        String requestBody = "{ \"amount\": 413000 }";

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/users/1/charge")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.userId", is(1)))
                .andExpect(jsonPath("$.result.balance", is(414000)));
    }

    @Test
    @DisplayName("잔액충전_실패_회원이 존재하지 않음.")
    @Sql("/member.sql")
    void 잔액충전_실패() throws Exception {

        //given
        String requestBody = "{ \"amount\": 413000 }";

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/users/13412/charge")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.responseMsg", is("해당 엔터티를 찾을 수 없습니다.")));
    }

}