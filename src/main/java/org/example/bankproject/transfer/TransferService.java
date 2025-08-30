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
    private static final int polishIbanTotalLength = 28;
    private static final int accountNumberDigits = 26;
    private static final int bankCodeStart = 4;
    private static final int bankCodeEnd = 8;

    @Transactional
    public TransferDto makeTransfer(TransferDto transferDto) {
        Account fromAccount = accountService.findByAccountNumber(transferDto.getFromAccountNumber());

        TransferRequest transferRequest = TransferRequest.builder()
                .fromAccountNumber(transferDto.getFromAccountNumber())
                .toAccountNumber(transferDto.getToAccountNumber())
                .amount(transferDto.getAmount())
                .title(transferDto.getTitle())
                .build();

        validateTransferData(transferDto, fromAccount);

        return processTransfer(transferRequest);
    }

    public boolean belongsToOurBank(TransferRequest transferRequest) {
        return transferRequest.getToAccountNumber().substring(bankCodeStart, bankCodeEnd).equals(localBankCode);
    }

    public void validateTransferData(TransferDto transferDto, Account fromAccount) {
        String toAccountNumber = transferDto.getToAccountNumber();
        BigDecimal amount = transferDto.getAmount();

        if (toAccountNumber.length() != polishIbanTotalLength) {
            throw new InvalidAccountNumberException("Account number must have " + accountNumberDigits + " characters");
        }

        if (fromAccount.getBalance().compareTo(amount) < 0 || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InsufficientFundsException("Insufficient Funds");
        }
    }

    private void depositToAccount(Account account, BigDecimal amount) {
        account.setBalance(account.getBalance().add(amount));
        accountService.saveAccount(account);
    }

    private void withdrawFromAccount(Account account, BigDecimal amount) {
        if (account.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient funds on account " + account.getAccountNumber());
        }
        account.setBalance(account.getBalance().subtract(amount));
        accountService.saveAccount(account);
    }

    public TransferDto processTransfer(TransferRequest transferRequest) {
        Account fromAccount = accountService.findByAccountNumber(transferRequest.getFromAccountNumber());
        BigDecimal amount = transferRequest.getAmount();
        String title = transferRequest.getTitle();

        withdrawFromAccount(fromAccount, amount);

        if (belongsToOurBank(transferRequest)) {
            Account toAccount = accountService.findByAccountNumber(transferRequest.getToAccountNumber());
            depositToAccount(toAccount, amount);
        }

        Transfer transfer = Transfer.builder()
                .fromAccountNumber(fromAccount.getAccountNumber())
                .toAccountNumber(transferRequest.getToAccountNumber())
                .title(title)
                .amount(amount)
                .dateTransfer(LocalDateTime.now())
                .build();

        return TransferMapper.toDto(transferRepository.save(transfer));
    }
}
