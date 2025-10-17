package com.example.bankcards.controller.admin;


import com.example.bankcards.dto.card.GetCardDto;
import com.example.bankcards.dto.card.UpdateStatusCardDto;
import com.example.bankcards.dto.general.PageResponse;
import com.example.bankcards.entity.enums.CardStatus;
import com.example.bankcards.service.admin.AdminCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<PageResponse<GetCardDto>> getUserCards(@RequestParam Long userId,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size,
                                                         @RequestParam(defaultValue = "expiry,desc") String sort) {

        String[] sortParts = sort.split(",");
        String sortField = sortParts[0];
        Sort.Direction direction = (sortParts.length > 1 && sortParts[1].equalsIgnoreCase("desc"))
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page,size, Sort.by(direction, sortField));
        Page<GetCardDto> cardPage = this.adminCardService.findAllByUserId(userId, pageable);

        return ResponseEntity.ok(PageResponse.fromPage(cardPage));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> changeStatus(@PathVariable("id") Long cardId,
                                             @RequestBody UpdateStatusCardDto dto) {
        System.out.println(dto.status());
        this.adminCardService.changeStatus(cardId, dto.status());
        return ResponseEntity.noContent().build();
    }

}
