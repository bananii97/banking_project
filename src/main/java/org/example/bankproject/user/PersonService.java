package org.example.bankproject.user;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.bankproject.address.AddressMapper;
import org.example.bankproject.identityCard.IdentityCardMapper;
import org.example.bankproject.user.api.PersonDto;
import org.example.bankproject.user.jpa.Person;
import org.example.bankproject.user.jpa.PersonRepository;
import org.springframework.stereotype.Service;

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
                .orElseThrow(()-> new EntityNotFoundException("Person with id: " + personId + " not found"));
    }

    public PersonDto updatePersonalData(Long personId, PersonDto personDto) {
        Person person = findByPersonId(personId);
        if (personDto.getEmail() != null) {
            person.setEmail(personDto.getEmail());
        }
        if (personDto.getIdentityCardDto() != null) {
            person.setIdentityCard(IdentityCardMapper.fromDto(personDto.getIdentityCardDto()));
        }
        if (personDto.getPhoneNumber() != null) {
            person.setPhoneNumber(personDto.getPhoneNumber());
        }
        if (personDto.getAddressDto() != null) {
            person.setAddress(AddressMapper.fromDto(personDto.getAddressDto()));
        }

        return PersonMapper.toDto(personRepository.save(person));
    }

    public PersonDto getPersonalData(Long personId) {
        Person person = findByPersonId(personId);
        return PersonMapper.toDto(person);
    }
}
