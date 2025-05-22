package org.example.bankproject.address.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class AddressDto {

    @Size(min = 3, max = 50)
    private String city;

    @Pattern(regexp = "^[0-9]{2}-[0-9]{3}$", message = "Incorrect postal code")
    private String postalCode;

    @Size(min = 3, max = 50)
    private String street;

    @Size(min = 1, max = 50)
    private String apartmentNumber;
}
