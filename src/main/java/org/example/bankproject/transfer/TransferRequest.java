package org.example.bankproject.transfer;

import lombok.Builder;
import lombok.Getter;
import org.example.bankproject.account.jpa.Account;

import java.math.BigDecimal;

@Builder(toBuilder = true)
@Getter
public class TransferRequest {

    private Account fromAccountNumber;
    private String toAccountNumber;
    private BigDecimal amount;
    private String title;
}
