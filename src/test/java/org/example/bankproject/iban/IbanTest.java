package org.example.bankproject.iban;

import org.example.bankproject.account.jpa.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IbanTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private IbanGenerator ibanGenerator;

    @Test
    void testCreateAccountNumber_generatesUniqueAccountNumber() {
        //given
        String branchCode = "0050";

        when(accountRepository.existsByAccountNumber(anyString()))
                .thenReturn(true)
                .thenReturn(false);

        //when
        String accountNumber = ibanGenerator.createAccountNumber(branchCode);

        //then
        assertNotNull(accountNumber);
        assertTrue(accountNumber.startsWith("PL"));
        assertTrue(accountNumber.contains(branchCode));
        verify(accountRepository, atLeastOnce()).existsByAccountNumber(anyString());
    }
}
