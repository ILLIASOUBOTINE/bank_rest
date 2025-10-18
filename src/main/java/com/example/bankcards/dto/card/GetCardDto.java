package com.example.bankcards.dto.card;


import com.example.bankcards.entity.enums.CardStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GetCardDto(
        Long id,
        String maskedNumber,
        LocalDate expiry,
        CardStatus status,
        BigDecimal balance,
        Long userId
) {
}
