package com.example.bankcards.dto.card;


import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.enums.CardStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GetClientCardDto(
        Long id,
        String maskedNumber,
        LocalDate expiry,
        CardStatus status,
        BigDecimal balance,
        Long userId
) {
    public static GetClientCardDto mappedCard(Card card) {
        return new GetClientCardDto(
                card.getId(),
                card.getMaskedNumber(),
                card.getExpiry(),
                card.getStatus(),
                card.getBalance(),
                card.getUser().getId()
        );
    }
}
