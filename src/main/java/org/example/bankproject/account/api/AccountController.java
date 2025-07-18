package org.example.bankproject.account.api;

import lombok.RequiredArgsConstructor;
import org.example.bankproject.account.AccountService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/create/{personId}")
    public AccountDto createAccount(@PathVariable("personId") long personId) {
        return accountService.createAccount(personId);
    }

    @DeleteMapping("/delete/{personId}/{accountId}")
    public void deleteAccount(@PathVariable("personId") long personId, @PathVariable("accountId") long accountId) {
        accountService.softDelete(personId, accountId);
    }
}
