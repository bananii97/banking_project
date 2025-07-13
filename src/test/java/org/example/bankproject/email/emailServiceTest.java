package org.example.bankproject.email;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class emailServiceTest {

    @Mock
    private EmailService emailService;

    @Test
    void shouldSendEmailWhenValidParameters() {
        // given
        String to = "test@example.com";
        String subject = "Test Subject";
        String text = "Text test";

        // when
        emailService.sendEmail(to, subject, text);

        // then
        verify(emailService).sendEmail(to, subject, text);
    }
}
