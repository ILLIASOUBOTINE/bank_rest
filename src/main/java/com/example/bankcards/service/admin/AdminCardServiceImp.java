package com.example.bankcards.service.admin;


import com.example.bankcards.dto.card.GetCardDto;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.enums.CardStatus;
import com.example.bankcards.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;




@Service
@RequiredArgsConstructor
public class AdminCardServiceImp implements AdminCardService {

    private final CardRepository cardRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<GetCardDto> findAllByUserId(Long userId, Pageable pageable) {
        Page<Card> cards = this.cardRepository.findByUserId(userId, pageable);

        return   cards.map(card ->
            new GetCardDto(
                    card.getId(),
                    card.getMaskedNumber(),
                    card.getExpiry(),
                    card.getStatus(),
                    card.getBalance(),
                    userId
            )
        );
    }

    @Override
    public Card createCard(Card card) {
        return null;
    }

    @Override
    public Card updateCard(Card card) {
        return null;
    }

    @Override
    public void deleteCard(Long cardId) {

    }

    @Override
    @Transactional
    public void changeStatus(Long cardId, CardStatus status) {
       Card card = this.cardRepository.findById(cardId)
               .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Card not found"));
        System.out.println(card.getStatus());
        System.out.println(status);
       card.setStatus(status);
    }


}
