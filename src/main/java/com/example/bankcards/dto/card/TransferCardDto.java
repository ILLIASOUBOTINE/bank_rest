package com.example.bankcards.dto.card;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransferCardDto(
        @NotNull
        Long fromCardId,
        @NotNull
        Long toCardId,
        @DecimalMin(value = "0.01", message = "Amount must be positive")
        BigDecimal amount

) {
}
