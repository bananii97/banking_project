package org.example.bankproject.iban;

import lombok.RequiredArgsConstructor;
import org.example.bankproject.account.jpa.AccountRepository;
import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class IbanGenerator {

    private final AccountRepository accountRepository;

    public String createAccountNumber() {
        String iban;
        do {
            iban = IbanGenerator.generateIban().toString();

        } while (accountRepository.existsByIban(iban));
        return iban;
    }

    private static Iban generateIban() {
        return new Iban.Builder()
                .countryCode(CountryCode.PL)
                .bankCode("1140")
                .accountNumber(generateRandomDigits())
                .build();
    }

    private static String generateRandomDigits() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(16);
        for (int i = 0; i < 16; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
