package org.example.bankproject.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorDto extends ExceptionDto {

    private static final String MESSAGE = "validation errors";

    private final List<ViolationInfo> violations = new ArrayList<>();

    public ValidationErrorDto() {
        super(MESSAGE);
    }

    public void addViolation(String field, String message) {
        violations.add(new  ViolationInfo(field, message));
    }

    private record ViolationInfo(String field, String message) {
    }
}
