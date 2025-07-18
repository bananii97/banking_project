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

    private static final String iban = "1140";

    @Transactional
    public TransferDto makeTransfer(TransferDto transferDto) {
        int expectedLength = 28;
        Account fromAccount = accountService.findByAccountNumber(transferDto.getFromAccountNumber());
        BigDecimal amount = transferDto.getAmount();
        String title = transferDto.getTitle();

        if (!(transferDto.getToAccountNumber().length() == expectedLength && transferDto.getFromAccountNumber().length() == expectedLength)) {
            throw new InvalidAccountNumberException("Account number must have 26 characters");
        }

        if (fromAccount.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) <= 0) {
            throw new InsufficientFundsException("Insufficient Funds");
        }

        if (transferDto.getToAccountNumber().substring(4, 8).equals(iban)) {
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

        } else {

            fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
            accountService.saveAccount(fromAccount);

            Transfer transfer = Transfer.builder()
                    .fromAccountNumber(fromAccount.getAccountNumber())
                    .toAccountNumber(transferDto.getToAccountNumber())
                    .title(title)
                    .amount(amount)
                    .dateTransfer(LocalDateTime.now())
                    .build();

            return TransferMapper.toDto(transferRepository.save(transfer));
        }
    }
}
