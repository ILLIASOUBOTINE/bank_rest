package com.example.bankcards.dto.card;


import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.enums.CardStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GetCardDto(
        Long id,
        String maskedNumber,
        LocalDate expiry,
        CardStatus status,
        Boolean blockRequested,
        BigDecimal balance,
        Long userId
) {
    public static GetCardDto mappedCard(Card card) {
        return new GetCardDto(
                card.getId(),
                card.getMaskedNumber(),
                card.getExpiry(),
                card.getStatus(),
                card.getBlockRequested(),
                card.getBalance(),
                card.getUser().getId()
        );
    }
}
