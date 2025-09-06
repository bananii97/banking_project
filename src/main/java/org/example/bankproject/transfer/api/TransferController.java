package org.example.bankproject.transfer.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bankproject.account.AccountService;
import org.example.bankproject.transfer.TransferService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transfer")
public class TransferController {

    private final TransferService transferService;
    private final AccountService accountService;

    @PostMapping("/makeTransfer")
    public TransferDto getTransfer(@RequestBody @Valid TransferDto transferDto) {
        accountService.throwIfAccountInactive(transferDto.getFromAccountNumber());
        return transferService.makeTransfer(transferDto);
    }
}
