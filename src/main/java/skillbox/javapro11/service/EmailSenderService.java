package skillbox.javapro11.service;

public interface EmailSenderService {

    void sendSimpleMessage(String toEmail, String text, String subject);
}
