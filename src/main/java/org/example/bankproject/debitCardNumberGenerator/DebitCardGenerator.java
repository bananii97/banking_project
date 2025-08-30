package org.example.bankproject.debitCardNumberGenerator;

import lombok.RequiredArgsConstructor;
import org.example.bankproject.debitCard.jpa.DebitCardRepository;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class DebitCardGenerator {

    private final DebitCardRepository debitCardRepository;

    private static final int bin = 4;
    private static final Random random = new Random();

    public String generateDebitCardNumber() {
        StringBuilder cardNumber = new StringBuilder();
        do {
            int[] cardNumbers = new int[16];

            cardNumbers[0] = bin;

            for (int i = 1; i < 15; i++) {
                cardNumbers[i] = random.nextInt(10);
            }

            cardNumbers[15] = calculateCheckDigit(cardNumbers);
            for (int i = 1; i < 16; i++) {
                cardNumber.append(cardNumbers[i]);
            }
        } while (debitCardRepository.existsByCardNumber(cardNumber.toString()));
        return cardNumber.toString();
    }

    private static int calculateCheckDigit(int[] digits) {
        int sum = 0;
        for (int i = 0; i < 15; i++) {
            int digit = digits[14 - i];
            if (i % 2 == 0) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
        }
        int checkDigit = (10 - (sum % 10)) % 10;
        return checkDigit;
    }
}
