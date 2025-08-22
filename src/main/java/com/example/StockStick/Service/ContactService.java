package com.example.StockStick.Service;

import com.example.StockStick.Model.Contact;
import com.example.StockStick.Repositery.ContactRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ContactRepo contactRepository;

    public void sendEmail(Contact contact) {
        try {
            Contact savedContact = contactRepository.save(contact);
            System.out.println(contact.getEmail());
            // Send email
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo("shobhitsrivastava2004@gmail.com");
            message.setSubject("New Contact Form Submission - " + contact.getSubject());
            message.setText(buildEmailContent(contact));
            message.setFrom(contact.getEmail());

            mailSender.send(message);
            sendConfirmationEmail(contact);

        } catch (Exception e) {
            throw new RuntimeException("Failed to send email: " + e.getMessage());
        }
    }

    private void sendConfirmationEmail(Contact contact) {
        try {
            SimpleMailMessage confirmationMessage = new SimpleMailMessage();
            confirmationMessage.setTo(contact.getEmail());
            confirmationMessage.setSubject("Thank you for contacting us");
            confirmationMessage.setText(buildConfirmationEmailContent(contact));
            confirmationMessage.setFrom("no-reply@yourcompany.com");

            mailSender.send(confirmationMessage);
        } catch (Exception e) {
            // Log the error but don't throw - main email was sent
            System.err.println("Failed to send confirmation email: " + e.getMessage());
        }
    }

    private String buildEmailContent(Contact contact) {
        return "New Contact Form Submission\n\n" +
                "Name: " + contact.getFirstName() + " " + contact.getLastName() + "\n" +
                "Email: " + contact.getEmail() + "\n" +
                "Phone: " + (contact.getPhone() != null ? contact.getPhone() : "Not provided") + "\n" +
                "Subject: " + contact.getSubject() + "\n" +
                "Submitted: " + contact.getTimestamp() + " (IST)\n\n" +
                "Message:\n" + contact.getMessage();
    }

    private String buildConfirmationEmailContent(Contact contact) {
        return "Dear " + contact.getFirstName() + ",\n\n" +
                "Thank you for contacting us. We have received your message and will get back to you soon.\n\n" +
                "Your submission details:\n" +
                "Subject: " + contact.getSubject() + "\n" +
                "Submitted: " + contact.getTimestamp() + " (IST)\n\n" +
                "Best regards,\n" +
                "Your Company Team";
    }

    // Additional service methods
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    public Optional<Contact> getContactById(Long id) {
        return contactRepository.findById(id);
    }

    public List<Contact> getContactsByEmail(String email) {
        return contactRepository.findByEmail(email);
    }
}