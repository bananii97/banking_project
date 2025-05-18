package org.example.bankproject.user.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.bankproject.address.dto.AddressDto;
import org.example.bankproject.gender.Gender;
import org.example.bankproject.identityCard.dto.IdentityCardDto;

import java.time.LocalDate;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
public class PersonDto {

    @Size(min = 3, max = 20)
    private String name;

    @Size(min = 3, max = 30)
    private String lastName;

    @Email
    private String email;

    private Gender gender;

    private String nationalIdentityNumber;

    @Past(message = "Niepoprawna data")
    private LocalDate dateOfBirth;

    @Valid
    private IdentityCardDto identityCardDto;

    @Valid
    private AddressDto addressDto;

    private int phoneNumber;
}
