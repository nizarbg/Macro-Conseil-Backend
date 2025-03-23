package com.example.Macro_conseil_backend.Services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;

@Service
@RequiredArgsConstructor
public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    public void sendEmail(String name, String email, String message) {
        try {
            sendToAdmin(name, email, message);
            sendConfirmationToUser(name, email);
        } catch (MessagingException e) {
            logger.error("❌ Error sending email: {}", e.getMessage(), e);
        }
    }

    private void sendToAdmin(String name, String email, String message) throws MessagingException {
        try {
            logger.info("📧 Preparing email to admin...");
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setFrom("conseilmacro@gmail.com");
            helper.setTo("conseilmacro@gmail.com");
            helper.setSubject("New Contact Form Submission");
            helper.setText("Name: " + name + "\nEmail: " + email + "\nMessage: " + message, false);

            mailSender.send(mimeMessage);
            logger.info("✅ Email successfully sent to admin!");
        } catch (MailException e) {
            logger.error("❌ Failed to send email to admin: {}", e.getMessage(), e);
        }
    }

    private void sendConfirmationToUser(String name, String userEmail) throws MessagingException {
        try {
            logger.info("📧 Preparing confirmation email to user: {}", userEmail);
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
            logger.info("✅ Confirmation email successfully sent to user: {}", userEmail);
        } catch (MailException e) {
            logger.error("❌ Failed to send confirmation email: {}", e.getMessage(), e);
        }
    }
}
