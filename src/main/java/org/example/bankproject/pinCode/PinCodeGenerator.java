package org.example.bankproject.pinCode;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class PinCodeGenerator {

    private static final Random random = new Random();

    public String generatePinCode() {
        return String.valueOf(random.nextInt(1000,9999));
    }
}
