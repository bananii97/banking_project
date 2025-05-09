package org.example.bankproject.user.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
@Getter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, max = 50)
    private String city;

    @Pattern(regexp = "^[0-9]{2}-[0-9]{3}$", message = "Niepoprawny kod pocztowy")
    private String postalCode;

    @Size(min = 3, max = 50)
    private String street;

    @Size(min = 1, max = 50)
    private String apartmentNumber;

}
