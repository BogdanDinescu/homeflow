package com.fmi.homeflow.service.notifications;


import com.fmi.homeflow.model.User;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Optional;

public class EmailNotifier implements Observer {

    private final MailSender mailSender;
    private final SimpleMailMessage templateMessage;
    private static final String TITLE = "HomeFlow";
    private static final String FROM_MAIL = "";

    public EmailNotifier() {
        templateMessage = new SimpleMailMessage();
        templateMessage.setSubject(TITLE);
        templateMessage.setFrom(FROM_MAIL);

        JavaMailSenderImpl javaMail = new JavaMailSenderImpl();
        javaMail.setHost("smtp-relay.sendinblue.com");
        javaMail.setPort(587);
        javaMail.setUsername("7fivwlats@relay.firefox.com");
        javaMail.setPassword("NKs8Mc1DQ52OyEUT");

        mailSender = javaMail;
    }

    @Override
    public void update(Notification notification) {
        Optional<String> emailToOptional = getEmailTo(notification);
        if (emailToOptional.isEmpty()) {
            return;
        }
        String emailTo = emailToOptional.get();

        Optional<String> messageOptional = textFromTask(notification.getTask());
        if (messageOptional.isEmpty()) {
            return;
        }
        String message = messageOptional.get();

        SimpleMailMessage msg = new SimpleMailMessage(templateMessage);
        msg.setTo(emailTo);
        msg.setText(message);
        try {
            mailSender.send(msg);
        } catch (MailException e) {
            e.printStackTrace();
        }
    }

    private Optional<String> getEmailTo(Notification notification) {
        if (notification == null) {
            return Optional.empty();
        }
        User user = notification.getUser();
        if (user == null) {
            return Optional.empty();
        }
        String emailTo = user.getEmail();
        if (emailTo == null) {
            return Optional.empty();
        }
        return Optional.of(emailTo);
    }
}
