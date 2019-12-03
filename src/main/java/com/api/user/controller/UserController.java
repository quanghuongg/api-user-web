package com.api.user.controller;

import com.api.user.define.Constant;
import com.api.user.entity.Role;
import com.api.user.entity.User;
import com.api.user.entity.model.Response;
import com.api.user.exception.ApiServiceException;
import com.api.user.security.TokenProvider;
import com.api.user.service.MailSendingService;
import com.api.user.service.UserService;
import com.api.user.uitls.ServiceUtils;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.Collections;

@Slf4j
@RestController
@RequestMapping(value = {"/user"})
@Api(tags = {"UserController API"})
public class UserController {

    private final MailSendingService mailSendingService;
    private final UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenProvider;

    @Autowired

    public UserController(MailSendingService mailSendingService, UserService userService) {
        this.mailSendingService = mailSendingService;
        this.userService = userService;
    }

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

    @RequestMapping(value = {"/get-all"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> getAllUser() {
        Response responseObject = Response.builder()
                .code(0)
                .data(userService.getAll())
                .message("success")
                .build();
        return new ResponseEntity<>(responseObject, HttpStatus.OK);

    }

    @RequestMapping(value = {"/info"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> getInfo() throws ApiServiceException, HttpMessageNotReadableException {
        Response response;
        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
        if (authUser.getName().isEmpty()) {
            throw new ApiServiceException("Token invalid");
        }
        User user = userService.findByUsername(authUser.getName());
        if (ServiceUtils.isEmpty(user)) {
            throw new ApiServiceException("User not existed");
        }
        Role role = userService.findRoleByUserId(user.getId());
        user.setRole(userService.findRoleByUserId(user.getId()));
        user.setRole_id(role.getId());
        response = Response.builder()
                .code(Constant.SUCCESS_CODE)
                .message(Constant.SUCCESSFUL_MESSAGE)
                .data(user)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @RequestMapping(value = {"/register"}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> registerUser(@RequestBody User user) throws ApiServiceException {
        Response response;
        if (user.getUsername().isEmpty() || user.getPassword().isEmpty() || user.getRole_id() == 0) {
            throw new ApiServiceException(Constant.OBJECT_EMPTY_FIELD);
        }
        if (ServiceUtils.isNotEmpty(user.getEmail())) {
            if (!ServiceUtils.isValidMail(user.getEmail())) {
                throw new ApiServiceException("email invalid");
            }
        }
        User existedUser = userService.findByUsername(user.getUsername());
        if (existedUser != null) {
            throw new ApiServiceException(Constant.USER_CREATE_EXISTING);
        }
        userService.save(user);
        response = Response.builder()
                .code(Constant.SUCCESS_CODE)
                .message(Constant.SUCCESSFUL_MESSAGE)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = {"/get-token"})
    public ResponseEntity<?> getToken(@RequestBody User user) throws HttpMessageNotReadableException {
        Authentication socialAuthentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), Collections.emptyList()));
        String token = jwtTokenProvider.generateToken(socialAuthentication);
        Response response = Response.builder()
                .code(Constant.SUCCESS_CODE)
                .message(Constant.SUCCESSFUL_MESSAGE)
                .data(token)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

