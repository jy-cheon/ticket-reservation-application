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
class ReservationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("좌석 예약 요청한다.")
    @Sql(value = {"/concert.sql", "/token.sql"})
    void 좌석예약요청() throws Exception {
        String requestBody = "{ \"userId\": 2, \"date\": \"2025-07-15 19:00:00\",\"seatNumber\": \"C01\"  }";
        Long tokenId = 2L;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/concerts/1/reservation")
                .header("Auth", tokenId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.reservationId", is(1)));
    }

    @Test
    @DisplayName("좌석 예약 요청한다. - 실패")
    @Sql(value = {"/concert.sql", "/token.sql"})
    void 좌석예약요청_실패() throws Exception {
        String requestBody = "{ \"userId\": 2, \"date\": \"2025-07-15 19:00:00\",\"seatNumber\": \"C02\"  }";
        Long tokenId = 2L;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/concerts/1/reservation")
                        .header("Auth", tokenId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.responseMsg", is("해당 좌석은 예약 불가능합니다.")));
    }

}