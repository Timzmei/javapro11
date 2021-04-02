package skillbox.javapro11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(String toEmail, String text, String subject) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("socialnetwok2021@gmail.com");
        message.setTo(toEmail);
        message.setText(text);
        message.setSubject(subject);

        emailSender.send(message);
    }
}