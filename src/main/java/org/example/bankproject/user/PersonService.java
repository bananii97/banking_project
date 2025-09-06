package org.example.bankproject.user;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.bankproject.address.AddressMapper;
import org.example.bankproject.identityCard.IdentityCardMapper;
import org.example.bankproject.user.api.PersonDto;
import org.example.bankproject.user.jpa.Person;
import org.example.bankproject.user.jpa.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public PersonDto createPerson(PersonDto personDto) {
        Person person = PersonMapper.fromDto(personDto);
        return PersonMapper.toDto(personRepository.save(person));
    }

    public Person findByPersonId(Long personId) {
        return personRepository.findById(personId)
                .orElseThrow(() -> new EntityNotFoundException("Person with id: " + personId + " not found"));
    }

    public PersonDto updatePersonalData(Long personId, PersonDto personDto) {
        Person person = findByPersonId(personId);

        Person updatedPerson = person.toBuilder()
                .email(Optional.ofNullable(personDto.getEmail()).orElse(person.getEmail()))
                .phoneNumber(Optional.ofNullable(personDto.getPhoneNumber()).orElse(person.getPhoneNumber()))
                .identityCard(Optional.ofNullable(personDto.getIdentityCardDto())
                        .map(IdentityCardMapper::fromDto)
                        .orElse(person.getIdentityCard()))
                .address(Optional.ofNullable(personDto.getAddressDto())
                        .map(AddressMapper::fromDto)
                        .orElse(person.getAddress()))
                .build();

        return PersonMapper.toDto(personRepository.save(updatedPerson));
    }

    public PersonDto getPersonalData(Long personId) {
        Person person = findByPersonId(personId);
        return PersonMapper.toDto(person);
    }
}
