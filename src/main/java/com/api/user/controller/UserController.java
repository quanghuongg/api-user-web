package com.api.user.controller;

import com.api.user.entity.model.Response;
import com.api.user.service.MailSendingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
@RequestMapping(value = {"/user"})

public class UserController {

    private final MailSendingService mailSendingService;

    @Autowired
    public UserController(MailSendingService mailSendingService) {
        this.mailSendingService = mailSendingService;
    }

    //Call service
    @RequestMapping(value = {"/mail"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> sendMail() throws MessagingException {

        mailSendingService.demoSendMail();
        Response responseObject = Response.builder()
                .code(0)
                .message("success")
                .build();
        return new ResponseEntity<>(responseObject, HttpStatus.OK);

    }

}

