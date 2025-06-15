package org.example.bankproject.iban;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class IbanGenerator {

    private final AccountRepository accountRepository;

    private static final String bankCode = "1140";
    private static final String plCountryCodeNumeric = "002521";

    public String createAccountNumber(String branchCode) {
        String accountNumber = "";
        do {
            String clientNumber = generateRandomDigits();
            accountNumber = "PL" + checkControlSum(branchCode, generateRandomDigits())
                    + bankCode + branchCode + clientNumber;

        } while (accountRepository.existsByAccountNumber(accountNumber));
        return accountNumber;
    }

    private String checkControlSum(String branchCode, String randomDigits) {
        BigInteger sum = new BigInteger(bankCode + branchCode + randomDigits + plCountryCodeNumeric);
        BigInteger checkNumber = new BigInteger("98").subtract(sum.mod(new BigInteger("97")));

        return checkNumber.toString();
    }

    private static String generateRandomDigits() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(12);
        for (int i = 0; i < 12; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
