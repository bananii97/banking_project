package org.example.bankproject.email;

public interface MailSenderImpl {
    void sendEmail(String to, String subject, String text);
}
