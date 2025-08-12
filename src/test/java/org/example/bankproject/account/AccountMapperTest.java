package org.example.bankproject.account;

import org.example.bankproject.account.api.AccountDto;
import org.example.bankproject.account.jpa.Account;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountMapperTest {

    @ParameterizedTest
    @CsvSource({
            "ASD123123,1000.50,2023-12-12,true",
            "AAA111222,250.00,2026-02-01,false",
            "BBB333444,0.00,2028-07-15,true",
            "XYZ999888,9999.99,2030-11-30,false"
    })
    void shouldMapAccountToAccountDto(String accountNumber, String balance,
                                               String DateOpenAccount, boolean primaryAccount) {
        //given
        LocalDate date = LocalDate.parse(DateOpenAccount);
        Account account = Account.builder()
                .accountNumber(accountNumber)
                .balance(new BigDecimal(balance))
                .accountOpenedAt(date)
                .primaryAccount(primaryAccount)
                .build();

        //when
        AccountDto accountDto = AccountMapper.toDto(account);

        //then
        assertEquals(accountNumber, accountDto.getAccountNumber());
        assertEquals(new BigDecimal(balance), accountDto.getBalance());
        assertEquals(date, accountDto.getAccountOpenedAt());
        assertEquals(primaryAccount, accountDto.isPrimaryAccount());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "NUll,1000.50,2023-12-12,true",
            "AAA111222,10,2026-02-01,false",
            "BBB333444,0.00,2028-07-15,true",
            "XYZ999888,9999.99,2026-02-01,false"
    }, nullValues = "NULL")
    void shouldHandleNullFieldsGracefullyWhenMappingAccountToDto(String accountNumber, String balance, String DateOpenAccount,
                                                         boolean primaryAccount) {
        //given
        LocalDate date = LocalDate.parse(DateOpenAccount);
        Account account = Account.builder()
                .accountNumber(accountNumber)
                .balance(new BigDecimal(balance))
                .accountOpenedAt(date)
                .primaryAccount(primaryAccount)
                .build();

        //when
        AccountDto accountDto = AccountMapper.toDto(account);

        //then
        assertEquals(accountNumber, accountDto.getAccountNumber());
        assertEquals(new BigDecimal(balance), accountDto.getBalance());
        assertEquals(date, accountDto.getAccountOpenedAt());
        assertEquals(primaryAccount, accountDto.isPrimaryAccount());
    }
}
