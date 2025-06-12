package org.example.bankproject.user.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bankproject.user.PersonService;
import org.example.bankproject.user.jpa.Person;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @PostMapping("/create")
    public Person createPerson(@RequestBody @Valid PersonDto personDto) {
        return personService.createPerson(personDto);
    }
}
