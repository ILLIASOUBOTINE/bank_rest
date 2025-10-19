package com.example.bankcards.controller.client;


import com.example.bankcards.dto.card.GetClientCardDto;
import com.example.bankcards.dto.card.TransferCardDto;
import com.example.bankcards.dto.general.PageResponse;
import com.example.bankcards.service.user.ClientCardService;
import com.example.bankcards.util.PageableUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/client/cards")
public class ClientCardRestController {

    private final ClientCardService clientCardService;

    @GetMapping
    public ResponseEntity<PageResponse<GetClientCardDto>> getClientCards(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "expiry,desc") String sort,
            Principal principal
    ) {

        Pageable pageable = PageableUtils.createPageable(page, size, sort);
        Page<GetClientCardDto> cardPage = this.clientCardService.findAllByUser(principal.getName(), pageable);

        return ResponseEntity.ok(PageResponse.fromPage(cardPage));
    }

    @PatchMapping("/{cardId}/block/request")
    public ResponseEntity<Void> blockCard(@PathVariable("cardId") Long cardId, Principal principal) {

        this.clientCardService.requestCardBlock(principal.getName(), cardId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/transfer")
    public ResponseEntity<Void> transferBetweenCard(@Valid @RequestBody TransferCardDto dto, Principal principal) {
        this.clientCardService.transferBetweenCardClient(principal.getName(), dto);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{cardId}/balance")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable("cardId") Long cardId, Principal principal) {

        BigDecimal balance = this.clientCardService.getCardBalance(principal.getName(), cardId);
        return ResponseEntity.ok(balance);
    }
}
