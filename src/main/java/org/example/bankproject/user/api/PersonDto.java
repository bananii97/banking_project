package org.example.bankproject.user.api;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public class PersonDto {

    @Size(min = 3, max = 20)
    private String name;

    @Size(min = 3, max = 30)
    private String lastName;

    @Email
    private String email;

    private String nationalIdentityNumber;
    private LocalDate birthDate;
    private int gender;
    private int phoneNumber;
}
