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
            "ASD123123,1000.50,SA123456,2023-12-12,true",
            "AAA111222,250.00,PL654321,2026-02-01,false",
            "BBB333444,0.00,GB987654,2028-07-15,true",
            "XYZ999888,9999.99,DE456789,2030-11-30,false"
    })
    void mapperTestToDtoShouldReturnAccountDto(String accountNumber, String balance, String bicNumber,
                                               String DateOpenAccount, boolean primaryAccount) {
        //given
        LocalDate date = LocalDate.parse(DateOpenAccount);
        Account account = Account.builder()
                .accountNumber(accountNumber)
                .balance(new BigDecimal(balance))
                .bicNumber(bicNumber)
                .accountOpenedAt(date)
                .primaryAccount(primaryAccount)
                .build();

        //when
        AccountDto accountDto = AccountMapper.toDto(account);

        //then
        assertEquals(accountNumber, accountDto.getAccountNumber());
        assertEquals(new BigDecimal(balance), accountDto.getBalance());
        assertEquals(bicNumber, accountDto.getBicNumber());
        assertEquals(date, accountDto.getAccountOpenedAt());
        assertEquals(primaryAccount, accountDto.isPrimaryAccount());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "NUll,1000.50,SA123456,2023-12-12,true",
            "AAA111222,10,PL654321,2026-02-01,false",
            "BBB333444,0.00,NUll,2028-07-15,true",
            "XYZ999888,9999.99,DE456789,2026-02-01,false"
    }, nullValues = "NULL")
    void mapperTestToDtoShouldHandleNullFieldsGracefully(String accountNumber, String balance, String bicNumber, String DateOpenAccount,
                                                         boolean primaryAccount) {
        //given
        LocalDate date = LocalDate.parse(DateOpenAccount);
        Account account = Account.builder()
                .accountNumber(accountNumber)
                .balance(new BigDecimal(balance))
                .bicNumber(bicNumber)
                .accountOpenedAt(date)
                .primaryAccount(primaryAccount)
                .build();

        //when
        AccountDto accountDto = AccountMapper.toDto(account);

        //then
        assertEquals(accountNumber, accountDto.getAccountNumber());
        assertEquals(new BigDecimal(balance), accountDto.getBalance());
        assertEquals(bicNumber, accountDto.getBicNumber());
        assertEquals(date, accountDto.getAccountOpenedAt());
        assertEquals(primaryAccount, accountDto.isPrimaryAccount());
    }
}
