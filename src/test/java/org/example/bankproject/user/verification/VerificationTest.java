package org.example.bankproject.user.verification;

import org.example.bankproject.address.dto.AddressDto;
import org.example.bankproject.gender.Gender;
import org.example.bankproject.identityCard.dto.IdentityCardDto;
import org.example.bankproject.user.api.PersonDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class VerificationTest {

    private NationalNumberValidator nationalNumberValidator = new NationalNumberValidator();
    private PersonDto personDto;

    @BeforeEach
    void correctPersonDto() {
        IdentityCardDto identityCardDto = IdentityCardDto.builder()
                .identityCardNumber("ABC123123")
                .issuePlace("Warszawa")
                .issuingAuthority("Prezydnet miasta stolecznego warszawa")
                .validUntil(LocalDate.of(2030,2,2))
                .build();

        AddressDto addressDto = AddressDto.builder()
                .city("Warszawa")
                .postalCode("12-300")
                .street("nowa")
                .apartmentNumber("17")
                .build();

        personDto = PersonDto.builder()
                .name("Krzysztof")
                .lastName("Nowy")
                .email("krzy@gmail.com")
                .identityCardDto(identityCardDto)
                .addressDto(addressDto)
                .phoneNumber(456789789)
                .build();
    }


    @ParameterizedTest
    @CsvSource({"04221512319,2004-02-15,MALE",
                "95031712378,1995-03-17,MALE",
                "88112843209,1988-11-28,FEMALE"})
    public void passedTest(String nationalIdentityNumber, String dateBirth, String gender) {
        //given
        LocalDate birthDate = LocalDate.parse(dateBirth);
        PersonDto variant = personDto.toBuilder()
                .nationalIdentityNumber(nationalIdentityNumber)
                .dateOfBirth(birthDate)
                .gender(Gender.valueOf(gender))
                .build();
        //when
        boolean returned = nationalNumberValidator.passed(variant);

        //then
        assertTrue(returned);
    }

    @ParameterizedTest
    @CsvSource({"03270556711,2003-07-05,MALE",
            "95031712378,1995-03-17,FEMALE",
            "88112843209,1988-11-27,FEMALE"})
    public void failedTestPassed(String nationalIdentityNumber, String dateBirth, String gender) {
        //given
        LocalDate birthDate = LocalDate.parse(dateBirth);
        PersonDto variant = personDto.toBuilder()
                .nationalIdentityNumber(nationalIdentityNumber)
                .dateOfBirth(birthDate)
                .gender(Gender.valueOf(gender))
                .build();
        //when
        boolean returned = nationalNumberValidator.passed(variant);
        System.out.println(variant);

        //then
        assertFalse(returned);
    }
}
