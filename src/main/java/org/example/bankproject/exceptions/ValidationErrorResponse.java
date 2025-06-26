package org.example.bankproject.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorResponse extends ExceptionResponse {

    private static final String MESSAGE = "validation errors";

    private final List<ViolationInfo> violations = new ArrayList<>();

    public ValidationErrorResponse() {
        super(MESSAGE);
    }

    public void addViolation(String field, String message) {
        violations.add(new  ViolationInfo(field, message));
    }

    private record ViolationInfo(String field, String message) {
    }
}
