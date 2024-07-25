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
class PaymentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("1. 결제를 처리한다.")
    @Sql(value = {"/member.sql","/data-test.sql"})
    void 결제테스트() throws Exception {
        Long tokenId = 5L;
        String requestBody = "{ \"reservationId\": 1, \"userId\": 6 }";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/concerts/3/payment")
                        .header("Auth", tokenId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("2. 결제를 처리한다. - 잔액부족")
    @Sql(value = {"/member.sql","/data-test.sql"})
    void 결제_잔액부족_테스트() throws Exception {
        Long tokenId = 5L;
        String requestBody = "{ \"reservationId\": 2, \"userId\": 4 }";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/concerts/3/payment")
                        .header("Auth", tokenId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.responseMsg", is("잔액이 충분하지 않습니다.")));
    }




}