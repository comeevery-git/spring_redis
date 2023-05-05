package com.example.concu.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.profiles.active:local")
@AutoConfigureMockMvc
@DisplayName("캠페인 API 테스트")
class CampaignControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("캠페인 조회")
    @Order(2)
    void getCampaignByCampaignIdTest() throws Exception {
        // given
        Long campaignId = 1L;

        // when
        ResultActions resultActions = mockMvc.perform(get("/campaigns/{campaignId}", campaignId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(print());

        //then
        MvcResult requestResult = resultActions.andReturn();
        Assertions.assertNotNull(requestResult.getResponse());
    }

    @Test
    @DisplayName("캠페인 목록 조회")
    @Order(3)
    void getCampaignsTest() throws Exception {
        // when
        ResultActions resultActions = mockMvc.perform(get("/campaigns")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(4)));
    }

    @Test
    @DisplayName("테스트 캠페인 생성")
    @Order(1)
    void createCampaignsTest() throws Exception {
        // when
        ResultActions resultActions = mockMvc.perform(post("/campaigns")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().isOk());
    }


}