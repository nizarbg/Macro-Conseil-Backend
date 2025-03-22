package com.example.Macro_conseil_backend.Services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendEmail(String name, String email, String message) throws MessagingException {
        sendToAdmin(name, email, message);
        sendConfirmationToUser(name, email);
    }

    private void sendToAdmin(String name, String email, String message) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setFrom("conseilmacro@gmail.com");
        helper.setTo("conseilmacro@gmail.com");
        helper.setSubject("New Contact Form Submission");
        helper.setText("Name: " + name + "\nEmail: " + email + "\nMessage: " + message, false);

        mailSender.send(mimeMessage);
    }

    private void sendConfirmationToUser(String name, String userEmail) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setFrom("conseilmacro@gmail.com");
        helper.setTo(userEmail);
        helper.setSubject("Thank You for Contacting Us!");
        helper.setText(
                "Dear " + name + ",\n\n" +
                        "Thank you for reaching out to us. We have received your message and will get back to you as soon as possible.\n\n" +
                        "Best regards,\nMacro Conseil Team",
                false
        );

        mailSender.send(mimeMessage);
    }
}
