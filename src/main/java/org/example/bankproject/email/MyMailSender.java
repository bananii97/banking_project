package org.example.bankproject.email;

public interface MyMailSender {
    void sendEmail(String to, String subject, String text);
}
