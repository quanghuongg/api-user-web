package com.api.user.controller;

import com.api.user.define.Constant;
import com.api.user.entity.Contract;
import com.api.user.entity.User;
import com.api.user.entity.info.ContractInfo;
import com.api.user.entity.info.FeedbackRequest;
import com.api.user.entity.model.Response;
import com.api.user.entity.request.ContractRequest;
import com.api.user.exception.ApiServiceException;
import com.api.user.service.ContractService;
import com.api.user.service.MailSendingService;
import com.api.user.service.ManagerService;
import com.api.user.service.UserService;
import com.api.user.uitls.ServiceUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = {"/user"})
public class StudentController {
    private final MailSendingService mailSendingService;
    private final UserService userService;
    private final ManagerService managerService;
    private final ContractService contractService;

    @Autowired
    public StudentController(MailSendingService mailSendingService, UserService userService, ContractService contractService
            , ManagerService managerService) {
        this.mailSendingService = mailSendingService;
        this.userService = userService;
        this.managerService = managerService;
        this.contractService = contractService;
    }

    @RequestMapping(value = {"/register-tutor"}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerCourse(@RequestBody ContractRequest request) throws ApiServiceException {
        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
        if (authUser.getName().isEmpty()) {
            throw new ApiServiceException("Token invalid");
        }
        User student = userService.findByUsername(authUser.getName());
        User tutor = userService.findByUserId(request.getTutorId());

        Contract contract = Contract.builder()
                .student_id(student.getId())
                .tutor_id(request.getTutorId())
                .number_hour(request.getNumberHour())
                .status(0)
                .number_hour(request.getNumberHour())
                .total(tutor.getHourly_wage() * request.getNumberHour())
                .created(System.currentTimeMillis())
                .date_from(request.getDateFrom())
                .date_to(request.getDateTo())
                .build();
        contractService.save(contract);
        Response response = Response.builder()
                .code(Constant.SUCCESS_CODE)
                .message(Constant.SUCCESSFUL_MESSAGE)
                .data(contract.getId())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = {"/oay"}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> studentPay(@RequestParam int contract_id) throws ApiServiceException {
        Contract contract = contractService.findById(contract_id);
        if (ServiceUtils.isNotEmpty(contract)) {
            contract.setStatus(1);
            contract.setUpdated(System.currentTimeMillis());
        }
        contractService.update(contract);
        Response response = Response.builder()
                .code(Constant.SUCCESS_CODE)
                .message(Constant.SUCCESSFUL_MESSAGE)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = {"/student-contract"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listContract() throws ApiServiceException {
        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
        if (authUser.getName().isEmpty()) {
            throw new ApiServiceException("Token invalid");
        }
        User user = userService.findByUsername(authUser.getName());
        List<Contract> contractList = contractService.listContractByStudentId(user.getId());
        Response response = Response.builder()
                .code(Constant.SUCCESS_CODE)
                .message(Constant.SUCCESSFUL_MESSAGE)
                .data(contractList)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = {"/detail-contract"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> detailContract(@RequestParam int id) throws ApiServiceException {
        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
        if (authUser.getName().isEmpty()) {
            throw new ApiServiceException("Token invalid");
        }
        ContractInfo contractInfo = contractService.detailContract(id);
        Response response = Response.builder()
                .code(Constant.SUCCESS_CODE)
                .message(Constant.SUCCESSFUL_MESSAGE)
                .data(contractInfo)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @RequestMapping(value = {"/add-feedback"}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> detailContract(@RequestBody FeedbackRequest request) throws ApiServiceException {
        if (ServiceUtils.isEmpty(request.getContractId()) || ServiceUtils.isEmpty(request.getType())) {
            throw new ApiServiceException("Object empty field");
        }

        Response response = Response.builder()
                .code(Constant.SUCCESS_CODE)
                .message(Constant.SUCCESSFUL_MESSAGE)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
