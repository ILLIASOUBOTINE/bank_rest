package com.example.bankcards.service.user;


import com.example.bankcards.dto.card.GetClientCardDto;
import com.example.bankcards.dto.card.TransferCardDto;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.entity.enums.CardStatus;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
public class ClientCardServiceImp implements ClientCardService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<GetClientCardDto> findAllByUser(String username, Pageable pageable) {
        User user = getUserByUsername(username);

        Page<Card> cards = this.cardRepository.findByUserId(user.getId(),pageable);

        return cards.map(GetClientCardDto::mappedCard);
    }

    @Override
    @Transactional
    public void requestCardBlock(String username, Long cardId) {
        User user = getUserByUsername(username);
        Card card = getCardById(cardId);

        if (!user.getId().equals(card.getUser().getId())) {
            throw new SecurityException("You do not have permission to block this card");
        }

        card.setBlockRequested(true);
        this.cardRepository.save(card);
    }

    @Override
    @Transactional
    public void transferBetweenCardClient(String username, TransferCardDto dto) {
        User user = getUserByUsername(username);

        Card fromCard = getCardById(dto.fromCardId(),"Source card not found");

        Card toCard =getCardById(dto.toCardId(),"Destination card not found");

        if (!fromCard.getUser().getId().equals(user.getId()) || !toCard.getUser().getId().equals(user.getId())) {
            throw new SecurityException("You can only transfer between your own cards");
        }

        if (!fromCard.getStatus().equals(CardStatus.ACTIVE)) {
            throw new IllegalStateException("Source card must be active");
        }

        if (!toCard.getStatus().equals(CardStatus.ACTIVE)) {
            throw new IllegalStateException("Target card must be active");
        }

        if (fromCard.getBalance().compareTo(dto.amount()) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        fromCard.setBalance(fromCard.getBalance().subtract(dto.amount()));
        toCard.setBalance(toCard.getBalance().add(dto.amount()));

        this.cardRepository.save(fromCard);
        this.cardRepository.save(toCard);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getCardBalance(String username, Long cardId) {
        User user = getUserByUsername(username);
        Card card = getCardById(cardId);

        if (!card.getUser().getId().equals(user.getId())) {
            throw new SecurityException("Access denied");
        }

        return card.getBalance();
    }

    private User getUserByUsername(String username) {
        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    private Card getCardById(Long cardId, String message) {
        return this.cardRepository.findById(cardId)
                .orElseThrow(() -> new NoSuchElementException(message));
    }

    private Card getCardById(Long cardId) {
       return this.cardRepository.findById(cardId)
                .orElseThrow(() -> new NoSuchElementException("Card not found"));
    }
}
