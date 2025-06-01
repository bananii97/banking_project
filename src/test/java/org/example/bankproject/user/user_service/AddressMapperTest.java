package org.example.bankproject.user.user_service;

import org.example.bankproject.address.AddressMapper;
import org.example.bankproject.address.dto.AddressDto;
import org.example.bankproject.address.model.Address;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class AddressMapperTest {

    @ParameterizedTest
    @CsvSource({"Warsaw,nowa,15-800,120",
            "Glasgow,stara,22-300,1",
            "Lodz,2 pulku,12-12-,12b"})
    void shouldMapFromDtoToEntityCorrectly(String city, String street, String postalCode, String apartmentNumber) {
        //given
        AddressDto addressDto = AddressDto.builder()
                .city(city)
                .street(street)
                .postalCode(postalCode)
                .apartmentNumber(apartmentNumber)
                .build();

        //when
        Address address = AddressMapper.fromDto(addressDto);

        //then
        assertEquals(addressDto.getCity(), address.getCity());
        assertEquals(addressDto.getStreet(), address.getStreet());
        assertEquals(addressDto.getPostalCode(), address.getPostalCode());
        assertEquals(addressDto.getApartmentNumber(), address.getApartmentNumber());
    }

    @ParameterizedTest
    @CsvSource(value = {"NULL,nowa,15-800,120",
            "Glasgow,NULL,22-300,1",
            "Lodz,2 pulku,NULL,12b",
            "Glasgow,NULL,22-300,NULL",
    },nullValues = "NULL")
    void testMapFromDtoToEntityWithNullValues(String city, String street, String postalCode, String apartmentNumber) {
        //given
        AddressDto addressDto = AddressDto.builder()
                .city(city)
                .street(street)
                .postalCode(postalCode)
                .apartmentNumber(apartmentNumber)
                .build();

        //when
        Address address = AddressMapper.fromDto(addressDto);

        //then
        assertEquals(addressDto.getCity(), address.getCity());
        assertEquals(addressDto.getStreet(), address.getStreet());
        assertEquals(addressDto.getPostalCode(), address.getPostalCode());
        assertEquals(addressDto.getApartmentNumber(), address.getApartmentNumber());
    }

    @ParameterizedTest
    @CsvSource(value = {"NULL,nowa,15-800,120",
            "Glasgow,NULL,22-300,1",
            "Lodz,2 pulku,NULL,12b",
            "Glasgow,NULL,22-300,NULL",
    },nullValues = "NULL")
    void testMapToDtoFromEntityWithNullValues(String city, String street, String postalCode, String apartmentNumber) {
        //given
       Address address = Address.builder()
               .city(city)
               .street(street)
               .postalCode(postalCode)
               .apartmentNumber(apartmentNumber)
               .build();

        //when
        AddressDto addressDto = AddressMapper.toDto(address);

        //then
        assertEquals(addressDto.getCity(), address.getCity());
        assertEquals(addressDto.getStreet(), address.getStreet());
        assertEquals(addressDto.getPostalCode(), address.getPostalCode());
        assertEquals(addressDto.getApartmentNumber(), address.getApartmentNumber());
    }

    @ParameterizedTest
    @CsvSource(value = {"NULL,nowa,15-800,120",
            "Glasgow,NULL,22-300,1",
            "Lodz,2 pulku,NULL,12b",
            "Glasgow,NULL,22-300,NULL",
    },nullValues = "NULL")
    void shouldMapToDtoFromEntityCorrectly(String city, String street, String postalCode, String apartmentNumber) {
        //given
        Address address = Address.builder()
                .city(city)
                .street(street)
                .postalCode(postalCode)
                .apartmentNumber(apartmentNumber)
                .build();

        //when
        AddressDto addressDto = AddressMapper.toDto(address);

        //then
        assertEquals(addressDto.getCity(), address.getCity());
        assertEquals(addressDto.getStreet(), address.getStreet());
        assertEquals(addressDto.getPostalCode(), address.getPostalCode());
        assertEquals(addressDto.getApartmentNumber(), address.getApartmentNumber());
    }
}
