package org.example.bankproject.exceptions.handling;

import org.example.bankproject.exceptions.AccountNotActiveException;
import org.example.bankproject.exceptions.InsufficientFundsException;
import org.example.bankproject.exceptions.InvalidAccountNumberException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({AccountNotActiveException.class, InsufficientFundsException.class, InvalidAccountNumberException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleException(RuntimeException exception){
        return new ExceptionResponse(exception.getMessage());
    }
}
