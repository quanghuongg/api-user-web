package com.api.user.controller;

import com.api.user.entity.User;
import com.api.user.entity.model.RequestInfo;
import com.api.user.entity.model.Response;
import com.api.user.exception.ApiServiceException;
import com.api.user.service.ManagerService;
import com.api.user.uitls.ServiceUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class HomeController {
    private final ManagerService managerService;

    public HomeController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @RequestMapping(value = {"/list-tutor"}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> getListTutor(@RequestBody RequestInfo requestInfo) throws ApiServiceException {
        if (requestInfo.getPage() == 0) {
            requestInfo.setPage(1);
        }
        if (requestInfo.getSize() == 0) {
            requestInfo.setSize(10);
        }
        List<User> list = managerService.listTutor(requestInfo);
        list = ServiceUtils.paging(list, requestInfo.getPage(), requestInfo.getSize());
        Response responseObject = Response.builder()
                .code(0)
                .data(list)
                .message("success")
                .build();
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }
}
