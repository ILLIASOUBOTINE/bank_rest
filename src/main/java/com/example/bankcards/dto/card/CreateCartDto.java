package com.example.bankcards.dto.card;

import com.example.bankcards.entity.enums.CardStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateCartDto(
        @NotBlank(message = "Card number is required")
        @Pattern(regexp = "\\d{16}", message = "Card number must be 16 digits")
        String cardNumber,

        @NotNull(message = "Expiry date is required")
        @Future(message = "Expiry date must be in the future")
        LocalDate expiry,

        @NotNull(message = "Card status is required")
        CardStatus status,

        @NotNull(message = "Balance is required")
        @DecimalMin(value = "0.0", message = "Balance cannot be negative")
        BigDecimal balance,

        @NotNull(message = "User ID is required")
        Long userId

) {
}
