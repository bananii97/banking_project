package org.example.bankproject.user.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import org.example.bankproject.address.dto.AddressDto;
import org.example.bankproject.personal_data.dto.PersonalDataDto;

@Builder
@Getter
public class PersonDto {

    @Size(min = 3, max = 20)
    private String name;

    @Size(min = 3, max = 30)
    private String lastName;

    @Email
    private String email;

    @Valid
    private PersonalDataDto personalDataDto;

    @Valid
    private AddressDto addressDto;

    private int phoneNumber;
}
