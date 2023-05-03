package com.example.ticket.concurrency.domain.applier.controller;

import com.example.ticket.concurrency.domain.applier.entity.request.ReqApply;
import com.example.ticket.concurrency.domain.applier.service.ApplierService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.profiles.active:local")
@AutoConfigureMockMvc
@DisplayName("지원 절차 테스트")
class ApplierControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("지원하기")
    void applyTest() throws Exception {

        // given
        Random random = new Random();
        Long campaignId = 1L;
        Long memberPid = random.nextLong(1000000);
        String name = randomName();
        ReqApply reqApply = ReqApply.builder()
                .campaignId(campaignId)
                .memberId(memberPid)
                .name(name)
                .build();
        String content = objectMapper.writeValueAsString(reqApply);

        // when
        ResultActions resultActions = mockMvc.perform(post("/applier/apply")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        resultActions
                .andExpect(status().isOk())
                .andDo(print());

    }

    private String randomName() {
        Random random = new Random();
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

}