package org.example.bankproject.user.verification;

import org.example.bankproject.user.api.PersonDto;

public class IdCardVerification implements Verification {

    @Override
    public boolean passed(PersonDto person) {
        return false;
    }
}
