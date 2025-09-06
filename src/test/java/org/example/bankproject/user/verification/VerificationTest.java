package org.example.bankproject.user.verification;

import org.example.bankproject.address.dto.AddressDto;
import org.example.bankproject.gender.Gender;
import org.example.bankproject.identityCard.dto.IdentityCardDto;
import org.example.bankproject.user.api.PersonDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class VerificationTest {

    private NationalNumberValidator nationalNumberValidator = new NationalNumberValidator();
    private static PersonDto personDto;

    @BeforeAll
    static void correctPersonDto() {
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
                .phoneNumber("456789789")
                .build();
    }


    @ParameterizedTest
    @CsvSource({"04221512319,2004-02-15,MALE",
                "95031712378,1995-03-17,MALE",
                "88112843209,1988-11-28,FEMALE"})
    public void passedTestAllValidationForNationalNumberShouldReturnTrue(String nationalIdentityNumber, String dateBirth, String gender) {
        //given
        LocalDate birthDate = LocalDate.parse(dateBirth);
        personDto = PersonDto.builder()
                .nationalIdentityNumber(nationalIdentityNumber)
                .dateOfBirth(birthDate)
                .gender(Gender.valueOf(gender))
                .build();

        //when
        boolean returned = nationalNumberValidator.passed(personDto);

        //then
        assertTrue(returned);
    }

    @ParameterizedTest
    @CsvSource({"04221512319,2004-02-15,FEMALE",
            "95031712378,1994-03-17,MALE",
            "88112843209,1981-11-28,MALE",
            "04211512319,2004-02-15,FEMALE",
            "95031712378,1994-03-17,FEMALE",
            "88112843219,1981-11-28,MALE",})
    public void testForAllValidationShouldReturnFalse(String nationalIdentityNumber, String dateBirth, String gender) {
        //given
        LocalDate birthDate = LocalDate.parse(dateBirth);
        personDto = PersonDto.builder()
                .nationalIdentityNumber(nationalIdentityNumber)
                .dateOfBirth(birthDate)
                .gender(Gender.valueOf(gender))
                .build();

        //when
        boolean returned = nationalNumberValidator.passed(personDto);

        //then
        assertFalse(returned);
    }
}
