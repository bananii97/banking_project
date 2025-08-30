package org.example.bankproject.debitCard.api;

import lombok.Builder;
import lombok.Getter;
import org.example.bankproject.account.jpa.Account;
import org.example.bankproject.cardStatus.CardStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
public class DebitCardDto {

    private Long id;
    private Account account;
    private String debitCardNumber;
    private LocalDate expiryDate;
    private CardStatus cardStatus;
    private BigDecimal dailyTransactionLimit;
    private BigDecimal dailyWithdrawalLimit;
}
