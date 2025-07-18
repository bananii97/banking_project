package org.example.bankproject.account;

import org.example.bankproject.account.api.AccountDto;
import org.example.bankproject.account.jpa.Account;

public class AccountMapper {

    public static AccountDto toDto(Account account){
        return AccountDto.builder()
                .accountNumber(account.getAccountNumber())
                .balance(account.getBalance())
                .bicNumber("BREXPLPWXXX")
                .accountOpenedAt(account.getAccountOpenedAt())
                .person(account.getPerson())
                .primaryAccount(account.isPrimaryAccount())
                .build();
    }
}
