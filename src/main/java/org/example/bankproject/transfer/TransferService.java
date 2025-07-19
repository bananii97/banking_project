package org.example.bankproject.transfer;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.bankproject.account.AccountService;
import org.example.bankproject.account.jpa.Account;
import org.example.bankproject.exceptions.InsufficientFundsException;
import org.example.bankproject.exceptions.InvalidAccountNumberException;
import org.example.bankproject.transfer.api.TransferDto;
import org.example.bankproject.transfer.jpa.Transfer;
import org.example.bankproject.transfer.jpa.TransferRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final TransferRepository transferRepository;
    private final AccountService accountService;

    private static final String localBankCode = "1140";

    @Transactional
    public TransferDto makeTransfer(TransferDto transferDto) {
        Account fromAccount = accountService.findByAccountNumber(transferDto.getFromAccountNumber());
        String toAccount = transferDto.getToAccountNumber();
        BigDecimal amount = transferDto.getAmount();
        String title = transferDto.getTitle();

        validateTransferData(fromAccount, toAccount, amount);

        if (transferDto.getToAccountNumber().substring(4, 8).equals(localBankCode)) {
            return processInternalTransfer(transferDto, fromAccount, title, amount);

        } else {
            return processExternalTransfer(fromAccount, toAccount, title, amount);
        }
    }

    public void validateTransferData(Account fromAccount, String toAccountNumber, BigDecimal amount) {
        int expectedLength = 28;
        if (!(toAccountNumber.length() == expectedLength && fromAccount.getAccountNumber().length() == expectedLength)) {
            throw new InvalidAccountNumberException("Account number must have 26 characters");
        }

        if (fromAccount.getBalance().compareTo(amount) < 0 || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InsufficientFundsException("Insufficient Funds");
        }
    }

    public TransferDto processInternalTransfer(TransferDto transferDto, Account fromAccount, String title, BigDecimal amount) {
        Account toAccount = accountService.findByAccountNumber(transferDto.getToAccountNumber());

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        accountService.saveAccount(fromAccount);
        accountService.saveAccount(toAccount);

        Transfer transfer = Transfer.builder()
                .fromAccountNumber(fromAccount.getAccountNumber())
                .toAccountNumber(toAccount.getAccountNumber())
                .title(title)
                .amount(amount)
                .dateTransfer(LocalDateTime.now())
                .build();

        return TransferMapper.toDto(transferRepository.save(transfer));
    }

    public TransferDto processExternalTransfer(Account fromAccount, String toAccountNumber, String title, BigDecimal amount) {
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        accountService.saveAccount(fromAccount);

        Transfer transfer = Transfer.builder()
                .fromAccountNumber(fromAccount.getAccountNumber())
                .toAccountNumber(toAccountNumber)
                .title(title)
                .amount(amount)
                .dateTransfer(LocalDateTime.now())
                .build();

        return TransferMapper.toDto(transferRepository.save(transfer));
    }
}
