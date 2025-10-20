package com.example.bankcards.service;

import com.example.bankcards.dto.card.CreateCartDto;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.entity.enums.CardStatus;
import com.example.bankcards.exception.AlreadyExistsException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.service.admin.AdminCardServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AdminCardServiceTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AdminCardServiceImpl adminCardService;

    @Test
    void createCard_success() {
        User user = new User();
        user.setId(1L);

        CreateCartDto dto = new CreateCartDto(
                "1234567812345678",
                LocalDate.now().plusYears(1),
                CardStatus.ACTIVE,
                BigDecimal.valueOf(1000),
                1L
        );

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(cardRepository.existsByEncryptedNumber(Mockito.anyString())).thenReturn(false);
        Mockito.when(cardRepository.save(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);

        Card card = adminCardService.createCard(dto);

        assertNotNull(card);
        assertEquals("5678", card.getLast4());
        assertEquals(BigDecimal.valueOf(1000), card.getBalance());
        verify(cardRepository).save(Mockito.any());
    }

    @Test
    void createCard_duplicateNumber_throws() {
        User user = new User();
        user.setId(1L);

        CreateCartDto dto = new CreateCartDto(
                "1234567812345678",
                LocalDate.now().plusYears(1),
                CardStatus.ACTIVE,
                BigDecimal.valueOf(1000),
                1L
        );

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(cardRepository.existsByEncryptedNumber(Mockito.anyString())).thenReturn(true);

        assertThrows(AlreadyExistsException.class, () -> adminCardService.createCard(dto));
    }
}

