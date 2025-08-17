package org.example.bankproject.account;

import jakarta.persistence.EntityNotFoundException;
import org.example.bankproject.account.api.AccountDto;
import org.example.bankproject.account.jpa.Account;
import org.example.bankproject.account.jpa.AccountRepository;
import org.example.bankproject.exceptions.AccountInActiveException;
import org.example.bankproject.iban.IbanGenerator;
import org.example.bankproject.user.PersonService;
import org.example.bankproject.user.jpa.Person;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    private static final String bicNumber = "BREXPLPWXXX";
    private static final String iban = "PL12345678901234567890123456";
    private static final String bankBranchCode = "0050";
    private static final Long personId = 1L;
    private static final Long accountId = 2L;

    private static Account account;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private PersonService personService;

    @Mock
    private IbanGenerator ibanGenerator;

    @InjectMocks
    private AccountService accountService;

    @Captor
    private ArgumentCaptor<Account> accountArgumentCaptor;

    @BeforeAll
    static void setUp() {
        Person person = new Person();
        person.setId(personId);

        account = Account.builder()
                .id(accountId)
                .accountNumber(iban)
                .balance(new BigDecimal("100.00"))
                .accountOpenedAt(LocalDate.now().minusMonths(1))
                .person(person)
                .build();
    }

    @Test
    void accountCreateShouldReturnAccount() {
        //given
        Person person = new Person();
        person.setAccounts(new ArrayList<>());

        when(personService.findByPersonId(personId)).thenReturn(person);
        when(ibanGenerator.createAccountNumber(bankBranchCode)).thenReturn(iban);
        when(accountRepository.save(any(Account.class))).thenAnswer(inv -> inv.getArgument(0));

        //when
        AccountDto accountDto = accountService.createAccount(personId, bankBranchCode);

        //then
        assertThat(accountDto.getAccountNumber()).isEqualTo(iban);
        assertThat(accountDto.getAccountOpenedAt()).isEqualTo(LocalDate.now());
        assertThat(accountDto.isPrimaryAccount()).isTrue();
        assertThat(accountDto.getBalance()).isEqualTo(new BigDecimal("0"));
        assertThat(accountDto.getBicNumber()).isEqualTo(bicNumber);

        verify(personService, times(1)).findByPersonId(personId);
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void shouldCreateNewAccountWhenPrimaryFlagIsFalse() {
        //given
        Person person = new Person();
        Account account = new Account();
        person.setAccounts(new ArrayList<>());
        person.getAccounts().add(account);

        when(personService.findByPersonId(personId)).thenReturn(person);
        when(ibanGenerator.createAccountNumber(bankBranchCode)).thenReturn(iban);
        when(accountRepository.save(any(Account.class))).thenAnswer(inv -> inv.getArgument(0));

        //when
        AccountDto accountDto = accountService.createAccount(personId, bankBranchCode);

        //then
        assertThat(accountDto.getAccountNumber()).isEqualTo(iban);
        assertThat(accountDto.getAccountOpenedAt()).isEqualTo(LocalDate.now());
        assertThat(accountDto.isPrimaryAccount()).isFalse();
        assertThat(accountDto.getBalance()).isEqualTo(new BigDecimal("0"));
        assertThat(accountDto.getBicNumber()).isEqualTo(bicNumber);

        verify(personService, times(1)).findByPersonId(personId);
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void shouldThrowPersonNotFoundExceptionWhenCreatingAccountWithNonExistentPerson() {
        //given
        String message = "Person with id: " + personId + " not found";
        when(personService.findByPersonId(personId)).thenThrow(new EntityNotFoundException(message));

        //when then
        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> accountService.createAccount(personId, bankBranchCode))
                .withMessage(message);

        verify(personService, times(1)).findByPersonId(personId);
        verifyNoInteractions(accountRepository);
    }

    @Test
    void shouldSetSoftDeleteToTrueWhenAccountIsSoftDeleted() {
        //given
        when(accountRepository.findByIdAndPersonId(personId, accountId))
                .thenReturn(Optional.of(account));
        //when
        accountService.softDelete(personId, accountId);

        //then
        assertThat(account.isSoftDeleted()).isTrue();
        verify(accountRepository, times(1)).findByIdAndPersonId(personId, accountId);
        verify(accountRepository, times(1)).save(any(Account.class));
        verifyNoMoreInteractions(accountRepository);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenSoftDeletingNonExistentAccount() {
        //given
        String message = "Account with id " + accountId + " for person " + personId + " not found";
        when(accountRepository.findByIdAndPersonId(personId, accountId))
                .thenReturn(Optional.empty());

        //then
        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> accountService.softDelete(personId, accountId))
                .withMessage(message);

        verify(accountRepository, times(1)).findByIdAndPersonId(personId, accountId);
        verifyNoMoreInteractions(accountRepository);
    }

    @Test
    void shouldReturnAccountWhenFindingById() {
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        //when
        Account returned = accountService.findById(accountId);

        //then
        assertThat(returned).isEqualTo(account);
        verify(accountRepository, times(1)).findById(accountId);
        verifyNoMoreInteractions(accountRepository);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenAccountNotFoundById() {
        //given
        String message = "Account with id " + accountId + " not found";
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        //then
        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> accountService.findById(accountId))
                .withMessage(message);

        verify(accountRepository, times(1)).findById(accountId);
        verifyNoMoreInteractions(accountRepository);
    }

    @Test
    void shouldReturnAccountWhenAccountNumberIsCorrect() {
        //given
        when(accountRepository.findByAccountNumber(iban)).thenReturn(Optional.of(account));

        //when
        Account returned = accountService.findByAccountNumber(iban);

        //then
        assertThat(returned).isEqualTo(account);
        verify(accountRepository, times(1)).findByAccountNumber(iban);
        verifyNoMoreInteractions(accountRepository);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenAccountNumberIsIncorrect() {
        //given
        String message = "Account with account number " + iban + " not found";
        when(accountRepository.findByAccountNumber(iban)).thenReturn(Optional.empty());

        //when then
        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> accountService.findByAccountNumber(iban))
                .withMessage(message);

        verify(accountRepository, times(1)).findByAccountNumber(iban);
        verifyNoMoreInteractions(accountRepository);
    }

    @Test
    void shouldCheckAccountIsActive() {
        //given
        when(accountRepository.findByAccountNumber(account.getAccountNumber())).thenReturn(Optional.of(account));

        //when then
        assertDoesNotThrow(() -> accountService.checkAccountIsActive(account.getAccountNumber()));
        verify(accountRepository, times(1)).findByAccountNumber(account.getAccountNumber());
    }

    @Test
    void shouldThrowAccountInActiveException_whenAccountInActive() {
        //given
        Account account = new Account();
        account.setActive(false);
        when(accountRepository.findByAccountNumber(account.getAccountNumber())).thenReturn(Optional.of(account));
        String message = "Account with id " + account.getId() + " is not active";

        //when then
        assertThatExceptionOfType(AccountInActiveException.class)
                .isThrownBy(() -> accountService.checkAccountIsActive(account.getAccountNumber()))
                .withMessage(message);
        verify(accountRepository, times(1)).findByAccountNumber(account.getAccountNumber());
        verifyNoMoreInteractions(accountRepository);
    }

    @Test
    void shouldSaveAccount_whenIsCorrect() {
        //given
        when(accountRepository.save(any(Account.class))).thenAnswer(args -> {
            Account savedAccount = args.getArgument(0);
            return savedAccount;
        });

        //when
        Account returned = accountService.saveAccount(account);

        //then
        verify(accountRepository).save(accountArgumentCaptor.capture());
        Account savedAccount = accountArgumentCaptor.getValue();
        assertThat(savedAccount).isEqualTo(returned);
    }
}
