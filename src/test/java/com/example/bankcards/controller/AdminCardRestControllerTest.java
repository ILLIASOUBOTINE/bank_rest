package com.example.bankcards.controller;

import com.example.bankcards.controller.admin.AdminCardRestController;
import com.example.bankcards.dto.card.CreateCartDto;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.enums.CardStatus;
import com.example.bankcards.service.admin.AdminCardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = AdminCardRestController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class AdminCardRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminCardService adminCardService;

    @MockBean
    private com.example.bankcards.security.JwtAuthFilter jwtAuthFilter;

    @MockBean
    private com.example.bankcards.security.JwtTokenProvider jwtTokenProvider;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createCard_success() throws Exception {
        CreateCartDto dto = new CreateCartDto(
                "1234567812345678",
                LocalDate.now().plusYears(1),
                CardStatus.ACTIVE,
                BigDecimal.valueOf(1000),
                1L
        );

        Card card = new Card();
        card.setId(1L);

        Mockito.when(adminCardService.createCard(Mockito.any(CreateCartDto.class))).thenReturn(card);

        mockMvc.perform(post("/api/admin/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }
}
