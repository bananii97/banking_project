package org.example.bankproject.user.verification;

import org.example.bankproject.user.api.PersonDto;

public interface Verification {

    boolean passed(PersonDto person);
}
