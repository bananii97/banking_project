package org.example.bankproject.user;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.bankproject.user.api.PersonDto;
import org.example.bankproject.user.jpa.Person;
import org.example.bankproject.user.jpa.PersonRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public Person createPerson(PersonDto personDto) {
        Person person = PersonMapper.fromDto(personDto);
        return personRepository.save(person);
    }

    public Person findByPersonId(Long personId) {
        return personRepository.findById(personId)
                .orElseThrow(()-> new EntityNotFoundException("Person with id: " + personId + " not found"));
    }
}
