package org.example.bankproject.identityCard.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class IdentityCardDto {

    @Pattern(regexp = "^[A-Za-z]{3}\\d{6}$", message = "Niepoprawny format")
    private String identityCardNumber;

    @Size(min = 3, max = 30)
    private String issuePlace;

    @Size(min = 5, max = 100)
    private String issuingAuthority;

    @Future(message = "Niepoprawna data")
    private LocalDate validUntil;
}
