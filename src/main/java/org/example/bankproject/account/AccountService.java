package org.example.bankproject.account;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.bankproject.account.api.AccountDto;
import org.example.bankproject.account.jpa.Account;
import org.example.bankproject.account.jpa.AccountRepository;
import org.example.bankproject.iban.IbanGenerator;
import org.example.bankproject.user.PersonService;
import org.example.bankproject.user.jpa.Person;

import java.math.BigDecimal;
import java.time.LocalDate;

@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final IbanGenerator ibanGenerator;
    private final PersonService personService;

    public AccountDto accountCreate(Long personId) {

        Person person = personService.findByPersonId(personId);

        boolean isFirstAccount = person.getAccounts().isEmpty();

        Account account = Account.builder()
                .accountNumber(ibanGenerator.createAccountNumber())
                .dateOpenAccount(LocalDate.now())
                .person(person)
                .balance(BigDecimal.ZERO)
                .softDeleted(false)
                .isActive(true)
                .bicNumber("sa")
                .primaryAccount(isFirstAccount)
                .build();

        person.getAccounts().add(account);
        return AccountMapper.toDto(accountRepository.save(account));
    }


    public void softDelete(Long personId,Long accountId) {
        Account account = accountRepository.findByIdAndPersonId(personId, accountId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Account with id " + accountId + " for person " + personId + " not found"));

        account.setSoftDeleted(true);
        accountRepository.save(account);
    }

    public Account findById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account with id " + accountId + " not found"));
    }
}
