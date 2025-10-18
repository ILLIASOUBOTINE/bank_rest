package com.example.bankcards.controller.admin;


import com.example.bankcards.dto.card.CreateCartDto;
import com.example.bankcards.dto.card.GetCardDto;
import com.example.bankcards.dto.card.UpdateCardDto;
import com.example.bankcards.dto.card.UpdateStatusCardDto;
import com.example.bankcards.dto.general.PageResponse;
import com.example.bankcards.entity.Card;
import com.example.bankcards.service.admin.AdminCardService;
import com.example.bankcards.util.PageableUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/admin/cards")
public class AdminCardRestController {

    private final AdminCardService adminCardService;

    @GetMapping
    public ResponseEntity<PageResponse<GetCardDto>> getCards(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size,
                                                             @RequestParam(defaultValue = "expiry,desc") String sort) {

        Pageable pageable = PageableUtils.createPageable(page, size, sort);
        Page<GetCardDto> cardPage = this.adminCardService.findAll(pageable);

        return ResponseEntity.ok(PageResponse.fromPage(cardPage));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<PageResponse<GetCardDto>> getUserCards(@PathVariable("userId") Long userId,
                                                                 @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size,
                                                                 @RequestParam(defaultValue = "expiry,desc") String sort) {

        Pageable pageable = PageableUtils.createPageable(page, size, sort);
        Page<GetCardDto> cardPage = this.adminCardService.findAllByUserId(userId, pageable);

        return ResponseEntity.ok(PageResponse.fromPage(cardPage));
    }

    @PostMapping
    public ResponseEntity<Card> createCard(@Valid @RequestBody CreateCartDto cartDto) {
        Card newCard = this.adminCardService.createCard(cartDto);
        return ResponseEntity.ok(newCard);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateCard(@PathVariable("id") Long cardId,
                                           @Valid @RequestBody UpdateCardDto updateCardDto) {
        this.adminCardService.updateCard(cardId, updateCardDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable("id") Long cardId) {
        this.adminCardService.deleteCard(cardId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> changeStatus(@PathVariable("id") Long cardId,
                                             @RequestBody UpdateStatusCardDto dto) {
        this.adminCardService.changeStatus(cardId, dto.status());
        return ResponseEntity.noContent().build();
    }

}
