package org.example.bankproject.transfer;

import org.example.bankproject.transfer.api.TransferDto;
import org.example.bankproject.transfer.jpa.Transfer;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferMapperTest {

    @ParameterizedTest
    @CsvSource({
            "PL50114020040000123412348822,PL58245874589632145825784512,100",
            "PL44556677885874515484856985,PL45678912345678913658741852,2500"
    })
    void shouldMapFromDtoToEntityCorrectly(String fromAccount, String toAccount, String amount) {
        // given
        TransferDto transferDto = TransferDto.builder()
                .fromAccountNumber(fromAccount)
                .toAccountNumber(toAccount)
                .amount(new BigDecimal(amount))
                .dateTransfer(LocalDateTime.now())
                .build();

        // when
        Transfer transfer = TransferMapper.fromDto(transferDto);

        // then
        assertEquals(transferDto.getFromAccountNumber(), transfer.getFromAccountNumber());
        assertEquals(transferDto.getToAccountNumber(), transfer.getToAccountNumber());
        assertEquals(transferDto.getAmount(), transfer.getAmount());
        assertEquals(transferDto.getDateTransfer(), transfer.getDateTransfer());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "NULL,PL50114020040000123412348822,100",
            "PL44556677885874515484856985,NULL,250",
            "PL58245874589632145825784512,PL50114020040000123412348822,NULL"
    }, nullValues = "NULL")
    void shouldMapFromDtoToEntityWithNulls(String fromAccount, String toAccount, String amount) {
        // given
        TransferDto transferDto = TransferDto.builder()
                .fromAccountNumber(fromAccount)
                .toAccountNumber(toAccount)
                .amount(new BigDecimal(amount))
                .dateTransfer(LocalDateTime.now())
                .build();

        // when
        Transfer transfer = TransferMapper.fromDto(transferDto);

        // then
        assertEquals(transferDto.getFromAccountNumber(), transfer.getFromAccountNumber());
        assertEquals(transferDto.getToAccountNumber(), transfer.getToAccountNumber());
        assertEquals(transferDto.getAmount(), transfer.getAmount());
        assertEquals(transferDto.getDateTransfer(), transfer.getDateTransfer());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "NULL,PL50114020040000123412348822,100",
            "PL50114020040000123412348822,NULL,250",
            "PL50114020040000123412348822,PL44556677885874515484856985,NULL"
    }, nullValues = "NULL")
    void shouldMapToDtoFromEntityWithNulls(String fromAccount, String toAccount, String amount) {
        // given
        Transfer transfer = Transfer.builder()
                .fromAccountNumber(fromAccount)
                .toAccountNumber(toAccount)
                .amount(new BigDecimal(amount))
                .dateTransfer(LocalDateTime.now())
                .build();

        // when
        TransferDto dto = TransferMapper.toDto(transfer);

        // then
        assertEquals(transfer.getFromAccountNumber(), dto.getFromAccountNumber());
        assertEquals(transfer.getToAccountNumber(), dto.getToAccountNumber());
        assertEquals(transfer.getAmount(), dto.getAmount());
        assertEquals(transfer.getDateTransfer(), dto.getDateTransfer());
    }

    @ParameterizedTest
    @CsvSource({
            "PL50114020040000123412348822,PL44556677885874515484856985,100",
            "PL44556677885874515484856985,PL50114020040000123412348822,1"
    })
    void shouldMapToDtoFromEntityCorrectly(String fromAccount, String toAccount, String amount) {
        // given
        Transfer transfer = Transfer.builder()
                .fromAccountNumber(fromAccount)
                .toAccountNumber(toAccount)
                .amount(new BigDecimal(amount))
                .dateTransfer(LocalDateTime.now())
                .build();

        // when
        TransferDto transferDto = TransferMapper.toDto(transfer);

        // then
        assertEquals(transfer.getFromAccountNumber(), transferDto.getFromAccountNumber());
        assertEquals(transfer.getToAccountNumber(), transferDto.getToAccountNumber());
        assertEquals(transfer.getAmount(), transferDto.getAmount());
        assertEquals(transfer.getDateTransfer(), transferDto.getDateTransfer());
    }
}
