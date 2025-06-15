package org.example.bankproject.email;

import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MyMailSenderImpl implements MyMailSender {

    private final JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();

        try {
            InternetAddress emailValidator = new InternetAddress(to);
            emailValidator.validate();

            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            mailSender.send(message);

        } catch (MailException x){
            throw new RuntimeException(x);
        } catch (AddressException e) {
            throw new RuntimeException(e);
        }
    }
}
