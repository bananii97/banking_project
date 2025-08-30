package org.example.bankproject.transfer;

import org.example.bankproject.account.AccountService;
import org.example.bankproject.account.jpa.Account;
import org.example.bankproject.exceptions.InsufficientFundsException;
import org.example.bankproject.exceptions.InvalidAccountNumberException;
import org.example.bankproject.transfer.api.TransferDto;
import org.example.bankproject.transfer.jpa.Transfer;
import org.example.bankproject.transfer.jpa.TransferRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransferServiceTest {

    private static final String fromAccountNumber = "PL12114020040000300201355387";
    private static final String toAccountNumber = "PL44114000997603123456789012";

    private static Account account;
    private static Account secondAccount;
    private static TransferDto transferDto;
    private static TransferRequest transferRequest;

    @Captor
    ArgumentCaptor<Transfer> transferCaptor;

    @Mock
    private AccountService accountService;

    @Mock
    private TransferRepository transferRepository;

    @InjectMocks
    private TransferService transferService;

    @BeforeAll
    static void setup() {
        String title = "transfer test";
        BigDecimal amount = new BigDecimal(100);

        account = Account.builder()
                .accountNumber(fromAccountNumber)
                .balance(new BigDecimal(100))
                .build();

        secondAccount = Account.builder()
                .accountNumber(toAccountNumber)
                .balance(new BigDecimal(100))
                .build();

        transferDto = TransferDto.builder()
                .fromAccountNumber(fromAccountNumber)
                .toAccountNumber(toAccountNumber)
                .title(title)
                .amount(amount)
                .build();

        transferRequest = TransferRequest.builder()
                .fromAccountNumber(fromAccountNumber)
                .toAccountNumber(toAccountNumber)
                .amount(amount)
                .title(title)
                .build();
    }

    @Test
    void shouldMakeTransfer_whenDataIsCorrect_returnTransferDto() {
        //given
        when(accountService.findByAccountNumber(fromAccountNumber)).thenReturn(account);
        when(accountService.findByAccountNumber(toAccountNumber)).thenReturn(secondAccount);
        when(transferRepository.save(any(Transfer.class))).thenAnswer( args -> {
            Transfer transfer = args.getArgument(0);
            return transfer;
        });

        //when
        TransferDto returned = transferService.makeTransfer(transferDto);

        //then
        verify(transferRepository).save(transferCaptor.capture());
        Transfer transfer = transferCaptor.getValue();

        verify(accountService, times(2)).findByAccountNumber(fromAccountNumber);

        assertThat(transfer.getFromAccountNumber()).isEqualTo(returned.getFromAccountNumber());
        assertThat(transfer.getToAccountNumber()).isEqualTo(returned.getToAccountNumber());
        assertThat(transfer.getAmount()).isEqualTo(returned.getAmount());
        assertThat(transfer.getTitle()).isEqualTo(returned.getTitle());
    }

    @Test
    void shouldInvalidAccountNumberException_whenAccountNumberIsInvalid() {
        //given
        String message = "Account number must have 26 characters";
        TransferDto transfer = transferDto.toBuilder()
                .toAccountNumber("PL4410500099760312345678902").build();
        when(accountService.findByAccountNumber(fromAccountNumber)).thenReturn(account);

        //when //then
        assertThatExceptionOfType(InvalidAccountNumberException.class)
                .isThrownBy(() -> transferService.makeTransfer(transfer))
                .withMessage(message);

        verify(accountService, times(1)).findByAccountNumber(fromAccountNumber);
        verifyNoInteractions(transferRepository);
        verifyNoMoreInteractions(accountService);
    }

    @Test
    void shouldInsufficientFundsException_whenAccountBalanceIsNegative() {
        //given
        when(accountService.findByAccountNumber(fromAccountNumber)).thenReturn(account);
        String message = "Insufficient Funds";
        TransferDto transfer = transferDto.toBuilder()
                .amount(new BigDecimal(110))
                .build();
        //when
        assertThatExceptionOfType(InsufficientFundsException.class)
                .isThrownBy(() -> transferService.makeTransfer(transfer))
                .withMessage(message);

        verify(accountService, times(1)).findByAccountNumber(fromAccountNumber);
        verifyNoInteractions(transferRepository);
        verifyNoMoreInteractions(accountService);
    }

    @Test
    void shouldReturnTrue_whenAccountIsOurBank(){
        //given

        //when
        boolean returned = transferService.belongsToOurBank(transferRequest);

        //then
        assertThat(returned).isTrue();
    }

    @Test
    void shouldReturnFalse_whenAccountIsNotOurBank(){
        //given
        String toAccountNumber = "PL44112000997603123456789012";
        TransferRequest request = transferRequest.toBuilder()
                .toAccountNumber(toAccountNumber)
                .build();
        //when
        boolean returned = transferService.belongsToOurBank(request);

        //then
        assertThat(returned).isFalse();
    }
}
