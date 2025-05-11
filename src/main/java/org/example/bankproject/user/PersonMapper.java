package org.example.bankproject.user;

import lombok.Builder;
import org.example.bankproject.address.AddressMapper;
import org.example.bankproject.personal_data.PersonalDataMapper;
import org.example.bankproject.user.api.PersonDto;
import org.example.bankproject.user.jpa.Person;

@Builder
public class PersonMapper {

    public static PersonDto toDto(Person person) {
        return PersonDto.builder()
                .build();
    }

    public static Person fromDto(PersonDto personDto) {
        return Person.builder()
                .name(personDto.getName())
                .lastName(personDto.getLastName())
                .email(personDto.getEmail())
                .personalData(PersonalDataMapper.fromDto(personDto.getPersonalDataDto()))
                .address(AddressMapper.fromDto(personDto.getAddressDto()))
                .phoneNumber(personDto.getPhoneNumber())
                .build();
    }
}
