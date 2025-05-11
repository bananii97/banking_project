package org.example.bankproject.personal_data.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import org.example.bankproject.personal_data.Gender;
import org.example.bankproject.user.verification.ValidNationalNumber;

import java.time.LocalDate;

@Builder
@Getter
@ValidNationalNumber
public class PersonalDataDto {

    @Size(min = 11, max = 11, message = "Numer pesel musi miec 11 znakow")
    private String nationalIdentityNumber;

    @NotNull
    private LocalDate dateOfBirth;

    @NotNull
    private Gender gender;

    @NotNull
    private int age;
}
