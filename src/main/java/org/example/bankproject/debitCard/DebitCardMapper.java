package org.example.bankproject.debitCard;

import lombok.experimental.UtilityClass;
import org.example.bankproject.debitCard.api.DebitCardDto;
import org.example.bankproject.debitCard.jpa.DebitCard;

@UtilityClass
public class DebitCardMapper {

    public static DebitCardDto toDto(DebitCard debitCard) {
        return DebitCardDto.builder()
                .account(debitCard.getAccount())
                .debitCardNumber(debitCard.getDebitCardNumber())
                .expiryDate(debitCard.getExpiryDate())
                .cardStatus(debitCard.getCardStatus())
                .dailyTransactionLimit(debitCard.getDailyTransactionLimit())
                .dailyWithdrawalLimit(debitCard.getDailyWithdrawalLimit())
                .build();
    }
}
