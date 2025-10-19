package com.example.bankcards.service.user;


import com.example.bankcards.dto.card.GetClientCardDto;
import com.example.bankcards.dto.card.TransferCardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface ClientCardService {

    Page<GetClientCardDto> findAllByUser(String username, Pageable pageable);

    void requestCardBlock(String username, Long cardId);

    void transferBetweenCardClient(String username, TransferCardDto dto);

    BigDecimal getCardBalance(String username, Long cardId);

}
