package com.example.bankcards.dto.card;

import com.example.bankcards.entity.enums.CardStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;


import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateCardDto(

        @Future(message = "Expiry date must be in the future")
        LocalDate expiry,

        CardStatus status,

        @DecimalMin(value = "0.0", message = "Balance cannot be negative")
        BigDecimal balance

) {
}
