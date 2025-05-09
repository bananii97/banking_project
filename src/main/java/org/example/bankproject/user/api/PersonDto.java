package org.example.bankproject.user.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import org.example.bankproject.user.model.Address;
import org.example.bankproject.user.model.PersonalData;

@Builder
@Getter
public class PersonDto {

    @Size(min = 3, max = 20)
    private String name;

    @Size(min = 3, max = 30)
    private String lastName;

    @Email
    private String email;

    @NotNull
    @Valid
    private PersonalData personalData;

    @NotNull
    @Valid
    private Address address;

    private int phoneNumber;
}
