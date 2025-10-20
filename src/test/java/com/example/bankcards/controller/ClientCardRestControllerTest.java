package com.example.bankcards.controller;

import com.example.bankcards.controller.client.ClientCardRestController;
import com.example.bankcards.dto.card.TransferCardDto;
import com.example.bankcards.service.user.ClientCardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ClientCardRestController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class ClientCardRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientCardService clientCardService;

    @MockBean
    private com.example.bankcards.security.JwtAuthFilter jwtAuthFilter;

    @MockBean
    private com.example.bankcards.security.JwtTokenProvider jwtTokenProvider;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        clientCardService = Mockito.mock(ClientCardService.class);
        objectMapper = new ObjectMapper();

        // standaloneSetup — не поднимает Spring Security, фильтры и конфигурации
        mockMvc = MockMvcBuilders.standaloneSetup(new ClientCardRestController(clientCardService))
                .build();
    }

    @Test
    @WithMockUser(username = "user1", roles = {"USER"})
    void requestBlock_success() throws Exception {
        // Настроить мок, чтобы метод ничего не делал
        Mockito.doNothing().when(clientCardService).requestCardBlock(Mockito.anyString(), Mockito.anyLong());

        mockMvc.perform(patch("/api/client/cards/1/block/request")
                .principal(() -> "user1"))
                .andExpect(status().isNoContent()); // 204
    }

    @Test
    @WithMockUser(username = "user1", roles = {"USER"})
    void transfer_success() throws Exception {
        TransferCardDto dto = new TransferCardDto(1L, 2L, BigDecimal.valueOf(100));

        Mockito.doNothing().when(clientCardService).transferBetweenCardClient(Mockito.anyString(), Mockito.any(TransferCardDto.class));

        mockMvc.perform(patch("/api/client/cards/transfer")
                        .principal(() -> "user1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNoContent()); // 204
    }

}

