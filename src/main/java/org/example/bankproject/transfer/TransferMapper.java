package org.example.bankproject.transfer;

import lombok.experimental.UtilityClass;
import org.example.bankproject.transfer.api.TransferDto;
import org.example.bankproject.transfer.jpa.Transfer;

@UtilityClass
public class TransferMapper {

    public static Transfer fromDto(TransferDto transferDto) {
        return Transfer.builder()
                .fromAccount(transferDto.getFromAccount())
                .ToAccount(transferDto.getToAccount())
                .amount(transferDto.getAmount())
                .dateTransfer(transferDto.getDateTransfer())
                .build();
    }

    public static TransferDto toDto(Transfer transfer) {
        return TransferDto.builder()
                .fromAccount(transfer.getFromAccount())
                .ToAccount(transfer.getToAccount())
                .amount(transfer.getAmount())
                .dateTransfer(transfer.getDateTransfer())
                .build();
    }
}
