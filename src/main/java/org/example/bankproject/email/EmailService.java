package org.example.bankproject.email;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailSender mailSender;

    public void sendEmail(String to, String subject, String text) {
        mailSender.sendEmail(to, subject, text);
    }
}
