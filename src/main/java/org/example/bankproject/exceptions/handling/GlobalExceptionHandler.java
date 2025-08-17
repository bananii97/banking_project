package org.example.bankproject.exceptions.handling;

import org.example.bankproject.exceptions.AccountInActiveException;
import org.example.bankproject.exceptions.InsufficientFundsException;
import org.example.bankproject.exceptions.InvalidAccountNumberException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountInActiveException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse handleAccountInActive(AccountInActiveException exception) {
        return new ExceptionResponse(exception.getMessage());
    }

    @ExceptionHandler(InsufficientFundsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse handleInsufficientFunds(InsufficientFundsException exception) {
        return new ExceptionResponse(exception.getMessage());
    }

    @ExceptionHandler(InvalidAccountNumberException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleInvalidAccountNumber(InvalidAccountNumberException exception) {
        return new ExceptionResponse(exception.getMessage());
    }
}
