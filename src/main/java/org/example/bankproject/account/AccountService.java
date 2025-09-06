package org.example.bankproject.account;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.bankproject.account.api.AccountDto;
import org.example.bankproject.account.jpa.Account;
import org.example.bankproject.account.jpa.AccountRepository;
import org.example.bankproject.exceptions.AccountInActiveException;
import org.example.bankproject.iban.IbanGenerator;
import org.example.bankproject.user.PersonService;
import org.example.bankproject.user.jpa.Person;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final IbanGenerator ibanGenerator;
    private final PersonService personService;

    public AccountDto createAccount(Long personId, String bankBranchCode) {

        Person person = personService.findByPersonId(personId);

        boolean isFirstAccount = person.getAccounts().isEmpty();

        Account account = Account.builder()
                .accountNumber(ibanGenerator.createAccountNumber(bankBranchCode))
                .person(person)
                .primaryAccount(isFirstAccount)
                .build();

        person.getAccounts().add(account);
        return AccountMapper.toDto(accountRepository.save(account));
    }


    public void softDelete(Long personId, Long accountId) {
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

    public Account findByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new EntityNotFoundException("Account with account number " + accountNumber + " not found"));
    }

    public void throwIfAccountInactive(String accountNumber) {
        Account account = findByAccountNumber(accountNumber);
        if (!account.isActive()) {
            throw new AccountInActiveException("Account with id " + account.getId() + " is not active");
        }
    }

    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }
}
