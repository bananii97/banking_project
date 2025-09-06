package org.example.bankproject.transfer;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder(toBuilder = true)
@Getter
public class TransferRequest {

    private String fromAccountNumber;
    private String toAccountNumber;
    private BigDecimal amount;
    private String title;
}
