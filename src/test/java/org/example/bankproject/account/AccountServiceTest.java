package org.example.bankproject.account;

import jakarta.persistence.EntityNotFoundException;
import org.example.bankproject.account.api.AccountDto;
import org.example.bankproject.account.jpa.Account;
import org.example.bankproject.account.jpa.AccountRepository;
import org.example.bankproject.iban.IbanGenerator;
import org.example.bankproject.user.PersonService;
import org.example.bankproject.user.jpa.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private PersonService  personService;

    @Mock
    private IbanGenerator ibanGenerator;

    @InjectMocks
    private AccountService accountService;

    @Test
    void accountCreateTestShouldReturnAccount(){
        //given
        Person person = new Person();
        person.setAccounts(new ArrayList<>());
        Long personId = 1L;

        when(personService.findByPersonId(personId)).thenReturn(person);
        when(ibanGenerator.createAccountNumber()).thenReturn("12145478945");
        when(accountRepository.save(any(Account.class))).thenAnswer(inv -> inv.getArgument(0));

        //when
        AccountDto accountDto = accountService.accountCreate(personId);

        //then
        assertThat(accountDto.getAccountNumber()).isEqualTo("12145478945");
        assertThat(accountDto.getDateOpenAccount()).isEqualTo(LocalDate.now());
        assertThat(accountDto.isPrimaryAccount()).isTrue();
        assertThat(accountDto.getBalance()).isEqualTo(new BigDecimal("0"));
        assertThat(accountDto.getBicNumber()).isEqualTo("sa");

        verify(personService,times(1)).findByPersonId(personId);
        verify(ibanGenerator,times(1)).createAccountNumber();
        verify(accountRepository,times(1)).save(any(Account.class));
    }

    @Test
    void testCreateAccountShouldNewAccountWherePrimaryAccountIsFalse(){
        //given
        Person person = new Person();
        Account account = new Account();
        person.setAccounts(new ArrayList<>());
        person.getAccounts().add(account);
        Long personId = 1L;

        when(personService.findByPersonId(personId)).thenReturn(person);
        when(ibanGenerator.createAccountNumber()).thenReturn("12145478945");
        when(accountRepository.save(any(Account.class))).thenAnswer(inv -> inv.getArgument(0));

        //when
        AccountDto accountDto = accountService.accountCreate(personId);

        //then
        assertThat(accountDto.getAccountNumber()).isEqualTo("12145478945");
        assertThat(accountDto.getDateOpenAccount()).isEqualTo(LocalDate.now());
        assertThat(accountDto.isPrimaryAccount()).isFalse();
        assertThat(accountDto.getBalance()).isEqualTo(new BigDecimal("0"));
        assertThat(accountDto.getBicNumber()).isEqualTo("sa");

        verify(personService,times(1)).findByPersonId(personId);
        verify(ibanGenerator,times(1)).createAccountNumber();
        verify(accountRepository,times(1)).save(any(Account.class));
    }

    @Test
    void testAccountCreateWithPersonNotFoundException(){
        //given
        Long personId = 1L;
        String message = "Person with id: " + personId + " not found";
        when(personService.findByPersonId(personId)).thenThrow(new  EntityNotFoundException(message));

        //then
        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> accountService.accountCreate(personId))
        .withMessage(message);

        verify(personService,times(1)).findByPersonId(personId);
        verifyNoInteractions(accountRepository);
    }

    @Test
    void softDeleteAccountTestShouldChangeSoftDeleteOnTrue(){
        //given
        Long personId = 1L;
        Long accountId = 1L;
        Account account = new Account();

        when(accountRepository.findByIdAndPersonId(personId,accountId))
                .thenReturn(Optional.of(account));
        //when
        accountService.softDelete(personId,accountId);

        assertThat(account.isSoftDeleted()).isTrue();
        verify(accountRepository,times(1)).findByIdAndPersonId(personId,accountId);
        verify(accountRepository,times(1)).save(any(Account.class));
    }

    @Test
    void softDeleteTestShouldThrowEntityNotFoundException(){
        //given
        Long personId = 1L;
        Long accountId = 2L;
        String message = "Account with id " + accountId + " for person " + personId + " not found";
        when(accountRepository.findByIdAndPersonId(personId,accountId))
                .thenReturn(Optional.empty());

        //then
        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> accountService.softDelete(personId,accountId))
                .withMessage(message);

        verify(accountRepository,times(1)).findByIdAndPersonId(personId,accountId);
        verifyNoMoreInteractions(accountRepository);
    }

    @Test
    void testFindByIdShouldReturnAccount(){
        Long accountId = 1L;
        Account account = new Account();
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        //when
        Account returned = accountService.findById(accountId);

        //then
        assertThat(returned).isEqualTo(account);
        verify(accountRepository,times(1)).findById(accountId);
    }

    @Test
    void testFindByIdShouldThrowEntityNotFoundException(){
        //given
        Long accountId = 1L;
        String message = "Account with id " + accountId + " not found";
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        //then
        assertThatExceptionOfType(EntityNotFoundException.class)
        .isThrownBy(() -> accountService.findById(accountId))
                .withMessage(message);

        verify(accountRepository,times(1)).findById(accountId);
        verifyNoMoreInteractions(accountRepository);
    }
}
