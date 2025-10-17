package com.example.bankcards.service.user;


import com.example.bankcards.dto.card.GetCardDto;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.enums.CardStatus;
import com.example.bankcards.repository.CardRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserCardServiceImp implements UserCardService {

    private final CardRepository cardRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<GetCardDto> findAllByUserId(Long userId, Pageable pageable) {
        Page<Card> cards = cardRepository.findByUserId(userId, pageable);

        return   cards.map(card ->
            new GetCardDto(
                    card.getId(),
                    card.getMaskedNumber(),
                    card.getExpiry(),
                    card.getStatus(),
                    card.getBalance(),
                    userId
            )
        );
    }

    @Override
    public Card createCard(Card card) {
        return null;
    }

    @Override
    public Card updateCard(Card card) {
        return null;
    }

    @Override
    public void deleteCard(Long cardId) {

    }

    @Override
    public Card updateStatus(Long cardId, CardStatus status) {
        return null;
    }
}
