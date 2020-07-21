package com.iot.platform.Service;

import java.io.File;
import java.util.ArrayList;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.iot.platform.Core.System.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Value("${spring.mail.username}")
    private String sendFromEmail;

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendActivationCode(String email, String activationCode) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject("Reset your password");
        msg.setText("Hi " + email + "\nPlease use this token to update your password: " + activationCode
                + "\nThanks and Best Regards,\nAdmin");
        javaMailSender.send(msg);
    }

    public void sendMail(Email email) throws MessagingException {
        if (email == null || email.getSendToEmail() == null || email.getSendToEmail().size() < 1) {
            throw new IllegalArgumentException("Params: email is empty || sendToEmail is empty");
        }

        if (email.getSendToCCs() == null) {
            email.setSendToCCs(new ArrayList<>());
        }

        if (email.getSendToBCCs() == null) {
            email.setSendToBCCs(new ArrayList<>());
        }

        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setFrom(this.sendFromEmail);
        helper.setSubject(email.getSubject());
        helper.setTo(email.getSendToEmail().stream().toArray(String[]::new));
        helper.setText(email.getBody(), email.isHtml());
        helper.setCc(email.getSendToCCs().stream().toArray(String[]::new));
        helper.setBcc(email.getSendToBCCs().stream().toArray(String[]::new));
        if (email.getFiles() != null) {
            for (File file : email.getFiles()) {
                helper.addAttachment(file.getName(), file);
            }
        }

        javaMailSender.send(msg);
    }

}