package org.example.bankproject.debitCard;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.bankproject.account.AccountService;
import org.example.bankproject.account.jpa.Account;
import org.example.bankproject.cardStatus.CardStatus;
import org.example.bankproject.debitCard.api.DebitCardDto;
import org.example.bankproject.debitCard.jpa.DebitCard;
import org.example.bankproject.debitCard.jpa.DebitCardRepository;
import org.example.bankproject.debitCardNumberGenerator.DebitCardGenerator;
import org.example.bankproject.exceptions.DebitCardException;
import org.example.bankproject.pinCode.PinCodeGenerator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DebitCardService {

    private final DebitCardRepository debitCardRepository;
    private final DebitCardGenerator debitCardGenerator;
    private final PinCodeGenerator  pinCodeGenerator;
    private final AccountService  accountService;

    public DebitCard findById(Long id) {
        return debitCardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DebitCard Not Found"));
    }



    public DebitCardDto orderCard(Long accountId) {
        Account account = accountService.findById(accountId);
        if(!account.isActive()){
            //jak dobrze pamietam to wyjatek zostal stworzony na innym pull&request jak
            //zmerguje to zmienie
        }
        if(account.getDebitCard() != null){
            throw new DebitCardException("DebitCard already exists");
        }

        DebitCard debitCard = DebitCard.builder()
                .account(account)
                .debitCardNumber(debitCardGenerator.generateDebitCardNumber())
                .pinCode(pinCodeGenerator.generatePinCode())
                .expiryDate(LocalDate.now().plusYears(4))
                .ccvCode("as")//zastanawiam sie czy generowac losowo czy stworzyc jakis schemat
                .cardStatus(CardStatus.INACTIVE)
                .dailyTransactionLimit(new BigDecimal(1000))
                .dailyWithdrawalLimit(new BigDecimal(1000))
                .build();

        account.setDebitCard(debitCardRepository.save(debitCard));
        return DebitCardMapper.toDto(debitCardRepository.save(debitCard));
    }



    public DebitCardDto changeLimit(Long id, String dailyTransactionLimit, String dailyWithdrawalLimit) {
        DebitCard debitCard = findById(id);

        if (debitCard.getCardStatus().equals(CardStatus.INACTIVE)
                || debitCard.getCardStatus().equals(CardStatus.EXPIRED)
                || debitCard.getCardStatus().equals(CardStatus.BLOCKED)) {
            throw new DebitCardException("You cannot change card limits if it is not active");
        }

        if(dailyTransactionLimit != null){
            debitCard.setDailyTransactionLimit(new BigDecimal(dailyTransactionLimit));
        }
        if(dailyWithdrawalLimit != null){
            debitCard.setDailyWithdrawalLimit(new BigDecimal(dailyWithdrawalLimit));
        }

        return  DebitCardMapper.toDto(debitCardRepository.save(debitCard));
    }

    public void blockCard(Long cardId) {
        DebitCard debitCard = findById(cardId);
        debitCard.

    }
}
