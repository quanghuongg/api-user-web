package com.api.user.service;
import javax.mail.MessagingException;


public interface MailSendingService {
    void mailConfirmRegister(String email, String fullName, int userId) throws MessagingException;

    void mailResetPassword(String email, String display_name, String newPassword) throws MessagingException;

}
