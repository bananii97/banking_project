package org.example.bankproject.transfer.api;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder(toBuilder = true)
@EqualsAndHashCode
@Getter
public class TransferDto {

    @NotBlank(message = "Sender account number must not be blank")
    private String fromAccountNumber;

    @NotBlank(message = "Recipient account number must not be blank")
    private String toAccountNumber;

    @NotNull(message = "Transfer amount is required")
    @DecimalMin(value = "0.01", inclusive = true, message = "Transfer amount must be greater than 0.01")
    private BigDecimal amount;

    @NotBlank(message = "Transfer title is required")
    @Size(max = 80, message = "Transfer title must not exceed 80 characters")
    private String title;

    private LocalDateTime dateTransfer;
}
