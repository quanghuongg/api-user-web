package com.api.user.service.impl;


import com.api.user.service.MailSendingService;
import com.api.user.uitls.AESUtil;
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

    @Override
    public void mailConfirmRegister(String email, String fullName, int userId) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
        InternetHeaders headers = new InternetHeaders();
        headers.addHeader("Content-type", "text/html; charset=UTF-8");


        String content = "Link confirm </b>" + "<a href=\"http://localhost:8000/user/confirm-register?id=__USERID__\">Link</a>";
        content = content.replaceAll("__USERID__", AESUtil.encrypt(userId + ""));
        MimeBodyPart body = null;
        try {
            body = new MimeBodyPart(headers, content.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        body.setText(content, "UTF-8", "html");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(body);

        message.setContent(multipart);
        helper.setFrom("quanghuongitus@gmail.com");
        helper.setTo(email);
        helper.setSubject("CONFIRM REGISTER");
        this.emailSender.send(message);
        log.info("Sent mail confirm register to {} success!!!!",email);
    }

    @Override
    public void mailResetPassword(String email, String display_name, String newPassword) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
        InternetHeaders headers = new InternetHeaders();
        headers.addHeader("Content-type", "text/html; charset=UTF-8");

        String content = "New Password: " + newPassword;
        MimeBodyPart body = null;
        try {
            body = new MimeBodyPart(headers, content.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        body.setText(content, "UTF-8", "html");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(body);

        message.setContent(multipart);
        helper.setFrom("quanghuongitus@gmail.com");
        helper.setTo(email);
        helper.setSubject("RESET PASSWORD");
        this.emailSender.send(message);
        log.info("Sent mail reset password to {} success!", email);
    }
}
