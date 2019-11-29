package com.api.user.service.impl;


import com.api.user.service.MailSendingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.UnsupportedEncodingException;
@Service
@Slf4j
public class MailSendingServiceImpl implements MailSendingService {
    @Autowired
    public JavaMailSender emailSender;


    public void demoSendMail() throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
        InternetHeaders headers = new InternetHeaders();
        headers.addHeader("Content-type", "text/html; charset=UTF-8");


        String invitedMail = "mail content";
        MimeBodyPart body = null;
        try {
            body = new MimeBodyPart(headers, invitedMail.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        body.setText(invitedMail, "UTF-8", "html");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(body);

        message.setContent(multipart);
        helper.setFrom("quanghuongitus@gmail.com");
        helper.setTo("thelove25051996@gmail.com");
        helper.setSubject("TEST SEND MAIL");
        this.emailSender.send(message);
        log.info("Sent!!!!");

    }
}
