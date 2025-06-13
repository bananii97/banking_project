package org.example.bankproject.account;

import lombok.experimental.UtilityClass;
import org.example.bankproject.account.api.AccountDto;
import org.example.bankproject.account.jpa.Account;

@UtilityClass
public class AccountMapper {

    public static AccountDto toDto(Account account){
        return AccountDto.builder()
                .accountNumber(account.getAccountNumber())
                .balance(account.getBalance())
                .bicNumber(account.getBicNumber())
                .accountOpenedAt(account.getAccountOpenedAt())
                .person(account.getPerson())
                .primaryAccount(account.isPrimaryAccount())
                .build();
    }
}
