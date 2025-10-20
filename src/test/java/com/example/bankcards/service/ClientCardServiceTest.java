package com.example.bankcards.service;

import com.example.bankcards.dto.card.TransferCardDto;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.entity.enums.CardStatus;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.service.user.ClientCardServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ClientCardServiceTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ClientCardServiceImp clientCardService;

    @Test
    void transferBetweenCardClient_success() {
        User user = new User();
        user.setId(1L);

        Card fromCard = new Card();
        fromCard.setId(1L);
        fromCard.setUser(user);
        fromCard.setBalance(BigDecimal.valueOf(1000));
        fromCard.setStatus(CardStatus.ACTIVE);

        Card toCard = new Card();
        toCard.setId(2L);
        toCard.setUser(user);
        toCard.setBalance(BigDecimal.valueOf(500));
        toCard.setStatus(CardStatus.ACTIVE);

        TransferCardDto dto = new TransferCardDto(1L, 2L, BigDecimal.valueOf(200));

        Mockito.when(userRepository.findByUsername("user1")).thenReturn(Optional.of(user));
        Mockito.when(cardRepository.findById(1L)).thenReturn(Optional.of(fromCard));
        Mockito.when(cardRepository.findById(2L)).thenReturn(Optional.of(toCard));

        clientCardService.transferBetweenCardClient("user1", dto);

        assertEquals(BigDecimal.valueOf(800), fromCard.getBalance());
        assertEquals(BigDecimal.valueOf(700), toCard.getBalance());
        verify(cardRepository, times(2)).save(Mockito.any(Card.class));
    }

    @Test
    void transferBetweenCardClient_insufficientFunds_throws() {
        User user = new User();
        user.setId(1L);

        Card fromCard = new Card();
        fromCard.setId(1L);
        fromCard.setUser(user);
        fromCard.setBalance(BigDecimal.valueOf(100));
        fromCard.setStatus(CardStatus.ACTIVE);

        Card toCard = new Card();
        toCard.setId(2L);
        toCard.setUser(user);
        toCard.setBalance(BigDecimal.valueOf(500));
        toCard.setStatus(CardStatus.ACTIVE);

        TransferCardDto dto = new TransferCardDto(1L, 2L, BigDecimal.valueOf(200));

        Mockito.when(userRepository.findByUsername("user1")).thenReturn(Optional.of(user));
        Mockito.when(cardRepository.findById(1L)).thenReturn(Optional.of(fromCard));
        Mockito.when(cardRepository.findById(2L)).thenReturn(Optional.of(toCard));

        assertThrows(IllegalArgumentException.class, () -> clientCardService.transferBetweenCardClient("user1", dto));
    }

    @Test
    void requestCardBlock_success() {
        User user = new User();
        user.setId(1L);

        Card card = new Card();
        card.setId(1L);
        card.setUser(user);
        card.setBlockRequested(false);

        Mockito.when(userRepository.findByUsername("user1")).thenReturn(Optional.of(user));
        Mockito.when(cardRepository.findById(1L)).thenReturn(Optional.of(card));

        clientCardService.requestCardBlock("user1", 1L);

        assertTrue(card.getBlockRequested());
        verify(cardRepository).save(card);
    }
}
