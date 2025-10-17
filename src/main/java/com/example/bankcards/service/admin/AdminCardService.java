package com.example.bankcards.service.admin;

import com.example.bankcards.dto.card.GetCardDto;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.enums.CardStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminCardService {
    Page<GetCardDto> findAllByUserId(Long userId, Pageable pageable);

    Card createCard(Card card);

    Card updateCard(Card card);

    void deleteCard(Long cardId);

    void changeStatus(Long cardId, CardStatus status);
}
