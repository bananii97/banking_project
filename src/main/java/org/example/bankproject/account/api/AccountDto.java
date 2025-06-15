package org.example.bankproject.account.api;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.example.bankproject.user.jpa.Person;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
@EqualsAndHashCode
public class AccountDto {

    private Person person;
    private String accountNumber;
    private String bicNumber;
    private LocalDate dateOpenAccount;
    private BigDecimal balance;
    private boolean primaryAccount;
    private boolean isActive;
    private boolean softDeleted;
}
