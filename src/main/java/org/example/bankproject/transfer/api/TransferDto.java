package org.example.bankproject.transfer.api;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@EqualsAndHashCode
@Getter
public class TransferDto {

    private Account fromAccount;
    private Account ToAccount;
    private BigDecimal amount;
    private LocalDateTime dateTransfer;
}
