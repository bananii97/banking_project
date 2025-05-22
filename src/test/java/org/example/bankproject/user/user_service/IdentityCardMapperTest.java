package org.example.bankproject.user.user_service;

import org.example.bankproject.identityCard.IdentityCardMapper;
import org.example.bankproject.identityCard.dto.IdentityCardDto;
import org.example.bankproject.identityCard.model.IdentityCard;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IdentityCardMapperTest {

    @ParameterizedTest
    @CsvSource({
            "ASD123123,Warszawa,Prezydent,2023-12-12",
            "AAA111222,Krakow,Burmistrz,2026-02-01",
            "BBB333444,Lodz,Wojewoda,2028-07-15",
            "XYZ999888,Gdansk,Starosta,2030-11-30"
    })
    void shouldMapIdentityCardDtoToIdentityCardCorrectly(String identityCardNumber, String issuePlace, String issuingAuthority, String validUntil) {
        //given
        LocalDate date = LocalDate.parse(validUntil);
        IdentityCardDto identityCardDto = IdentityCardDto.builder()
                .identityCardNumber(identityCardNumber)
                .issuePlace(issuePlace)
                .issuingAuthority(issuingAuthority)
                .validUntil(date)
                .build();

        //when
        IdentityCard identityCard = IdentityCardMapper.fromDto(identityCardDto);

        //then
        assertEquals(identityCardNumber, identityCard.getIdentityCardNumber());
        assertEquals(issuePlace, identityCard.getIssuePlace());
        assertEquals(issuingAuthority, identityCard.getIssuingAuthority());
        assertEquals(date, identityCard.getValidUntil());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "NULL,Warszawa,Prezydent,2023-12-12",
            "AAA111222,NULL,Burmistrz,2026-02-01",
            "BBB333444,Lodz,NULL,2028-07-15",
            "XYZ999888,Gdansk,Starosta,2028-07-15"
    }, nullValues = "NULL")
    void testMapIdentityCardDtoToIdentityCardWithNullValues(String identityCardNumber, String issuePlace, String issuingAuthority, String validUntil) {
        //given
        LocalDate date = LocalDate.parse(validUntil);
        IdentityCardDto identityCardDto = IdentityCardDto.builder()
                .identityCardNumber(identityCardNumber)
                .issuePlace(issuePlace)
                .issuingAuthority(issuingAuthority)
                .validUntil(date)
                .build();

        //when
        IdentityCard identityCard = IdentityCardMapper.fromDto(identityCardDto);

        //then
        assertEquals(identityCardNumber, identityCard.getIdentityCardNumber());
        assertEquals(issuePlace, identityCard.getIssuePlace());
        assertEquals(issuingAuthority, identityCard.getIssuingAuthority());
        assertEquals(date, identityCard.getValidUntil());
    }

    @ParameterizedTest
    @CsvSource({
            "ASD123123,Warszawa,Prezydent,2023-12-12",
            "AAA111222,Krakow,Burmistrz,2026-02-01",
            "BBB333444,Lodz,Wojewoda,2028-07-15",
            "XYZ999888,Gdansk,Starosta,2030-11-30"
    })
    void shouldMapToDtoWithEntityCorrectly(String identityCardNumber, String issuePlace, String issuingAuthority, String validUntil) {
        //given
        LocalDate date = LocalDate.parse(validUntil);
        IdentityCard identityCard = IdentityCard.builder()
                .identityCardNumber(identityCardNumber)
                .issuePlace(issuePlace)
                .issuingAuthority(issuingAuthority)
                .validUntil(date)
                .build();

        //when
        IdentityCardDto identityCardDto = IdentityCardMapper.toDto(identityCard);

        //then
        assertEquals(identityCardNumber, identityCardDto.getIdentityCardNumber());
        assertEquals(issuePlace, identityCardDto.getIssuePlace());
        assertEquals(issuingAuthority, identityCardDto.getIssuingAuthority());
        assertEquals(date, identityCardDto.getValidUntil());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "NULL,Warszawa,Prezydent,2023-12-12",
            "AAA111222,NULL,Burmistrz,2026-02-01",
            "BBB333444,Lodz,NULL,2028-07-15",
            "XYZ999888,Gdansk,Starosta,2028-07-15"
    }, nullValues = "NULL")
    void testMapToDtoWithEntityWithNull(String identityCardNumber, String issuePlace, String issuingAuthority, String validUntil) {
        //given
        LocalDate date = LocalDate.parse(validUntil);
        IdentityCard identityCard = IdentityCard.builder()
                .identityCardNumber(identityCardNumber)
                .issuePlace(issuePlace)
                .issuingAuthority(issuingAuthority)
                .validUntil(date)
                .build();

        //when
        IdentityCardDto identityCardDto = IdentityCardMapper.toDto(identityCard);

        //then
        assertEquals(identityCardNumber, identityCardDto.getIdentityCardNumber());
        assertEquals(issuePlace, identityCardDto.getIssuePlace());
        assertEquals(issuingAuthority, identityCardDto.getIssuingAuthority());
        assertEquals(date, identityCardDto.getValidUntil());
    }
}
