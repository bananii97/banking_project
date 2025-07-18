package org.example.bankproject.transfer;

import lombok.experimental.UtilityClass;
import org.example.bankproject.transfer.api.TransferDto;
import org.example.bankproject.transfer.jpa.Transfer;

@UtilityClass
public class TransferMapper {

    public static Transfer fromDto(TransferDto transferDto) {
        return Transfer.builder()
                .fromAccountNumber(transferDto.getFromAccountNumber())
                .toAccountNumber(transferDto.getToAccountNumber())
                .amount(transferDto.getAmount())
                .dateTransfer(transferDto.getDateTransfer())
                .build();
    }

    public static TransferDto toDto(Transfer transfer) {
        return TransferDto.builder()
                .fromAccountNumber(transfer.getFromAccountNumber())
                .toAccountNumber(transfer.getToAccountNumber())
                .amount(transfer.getAmount())
                .title(transfer.getTitle())
                .dateTransfer(transfer.getDateTransfer())
                .build();
    }
}
