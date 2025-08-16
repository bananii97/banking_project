package org.example.bankproject.account.api;

import lombok.RequiredArgsConstructor;
import org.example.bankproject.account.AccountService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/create/{personId}/{bankBranchCode}")
    public AccountDto createAccount(@PathVariable("personId") long personId,
                                    @PathVariable("bankBranchCode")  String bankBranchCode) {
        return accountService.createAccount(personId,  bankBranchCode);
    }

    @DeleteMapping("/delete/{personId}/{accountId}")
    public void deleteAccount(@PathVariable("personId") long personId, @PathVariable("accountId") long accountId) {
        accountService.softDelete(personId, accountId);
    }
}
