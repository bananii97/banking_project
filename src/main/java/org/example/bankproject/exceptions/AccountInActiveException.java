package org.example.bankproject.exceptions;

public class AccountInActiveException extends RuntimeException {

    public AccountInActiveException(String message) {
        super(message);
    }
}
