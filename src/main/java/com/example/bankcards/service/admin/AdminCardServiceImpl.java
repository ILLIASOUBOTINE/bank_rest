package com.example.bankcards.service.admin;

import com.example.bankcards.dto.card.CreateCartDto;
import com.example.bankcards.dto.card.GetCardDto;
import com.example.bankcards.dto.card.UpdateCardDto;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.entity.enums.CardStatus;
import com.example.bankcards.exception.AlreadyExistsException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.util.AesEncryptor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
public class AdminCardServiceImpl implements AdminCardService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<GetCardDto> findAll(Pageable pageable) {
        Page<Card> cards = this.cardRepository.findAll(pageable);

        return cards.map(GetCardDto::mappedCard);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GetCardDto> findAllByUserId(Long userId, Pageable pageable) {
        Page<Card> cards = this.cardRepository.findByUserId(userId, pageable);

        return cards.map(GetCardDto::mappedCard);
    }

    @Override
    @Transactional
    public Card createCard(CreateCartDto cardDto) {
        User user = this.userRepository.findById(cardDto.userId())
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        String cardNumber = cardDto.cardNumber();
        String encryptedNumber = AesEncryptor.encrypt(cardNumber);

        if (this.cardRepository.existsByEncryptedNumber(encryptedNumber)) {
            throw new AlreadyExistsException("Card with encrypted number already exists");
        }

        Card card = new Card();
        card.setUser(user);
        card.setStatus(cardDto.status());
        card.setBalance(cardDto.balance());
        card.setExpiry(cardDto.expiry());
        card.setLast4(cardNumber.substring(cardNumber.length() -4));
        card.setEncryptedNumber(encryptedNumber);

        return this.cardRepository.save(card);
    }

    @Override
    @Transactional
    public void updateCard(Long cardId, UpdateCardDto cardDto) {
        this.cardRepository.findById(cardId).
                ifPresentOrElse(card -> {
                    if (cardDto.expiry() != null) card.setExpiry(cardDto.expiry());
                    if (cardDto.status() != null) card.setStatus(cardDto.status());
                    if (cardDto.balance() != null) card.setBalance(cardDto.balance());
                }, () -> {
                    throw new NoSuchElementException("Card not found");
                });
    }

    @Override
    @Transactional
    public void deleteCard(Long cardId) {
        Card card = this.cardRepository.findById(cardId)
                .orElseThrow(() -> new NoSuchElementException("Card not found"));

        this.cardRepository.delete(card);
    }

    @Override
    @Transactional
    public void changeStatus(Long cardId, CardStatus status) {
       Card card = this.cardRepository.findById(cardId)
               .orElseThrow(() -> new NoSuchElementException("Card not found"));
       card.setStatus(status);
    }


}
