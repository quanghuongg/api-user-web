package com.api.user.controller;

import com.api.user.define.Constant;
import com.api.user.entity.Skill;
import com.api.user.entity.User;
import com.api.user.entity.model.RequestInfo;
import com.api.user.entity.model.Response;
import com.api.user.exception.ApiServiceException;
import com.api.user.service.ManagerService;
import com.api.user.service.UserService;
import com.api.user.uitls.ServiceUtils;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = {"/manager"})
@Api(tags = {"ManagerController API"})
public class ManagerController {

    private final ManagerService managerService;

    private final UserService userService;


    public ManagerController(ManagerService managerService, UserService userService) {
        this.managerService = managerService;
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = {"/add-admin"}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> registerUser(@RequestBody User user) throws ApiServiceException {
        if (user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
            throw new ApiServiceException(Constant.OBJECT_EMPTY_FIELD);
        }

        User existedUser = userService.findByUsername(user.getUsername());
        if (ServiceUtils.isNotEmpty(existedUser)) {
            throw new ApiServiceException(Constant.USER_CREATE_EXISTING);
        }
        user.setRole_id(3);
        userService.save(user);
        Response response = Response.builder()
                .code(Constant.SUCCESS_CODE)
                .message(Constant.SUCCESSFUL_MESSAGE)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = {"/list-user"}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> getAllUser(@RequestBody RequestInfo requestInfo) {
        List<User> list = managerService.getAllUser(requestInfo.getType());
        list = ServiceUtils.paging(list, requestInfo.getPage(), requestInfo.getSize());
        Response responseObject = Response.builder()
                .code(0)
                .data(list)
                .message("success")
                .build();
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = {"/view-detail"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> viewInfoDetailUser(@RequestParam int userId) throws ApiServiceException {
        if (ServiceUtils.isEmpty(userId)) {
            throw new ApiServiceException("empty field");
        }
        Response responseObject = Response.builder()
                .code(0)
                .data(userService.findById(userId))
                .message("success")
                .build();
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = {"/create-skill"}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> createSkill(@RequestBody Skill skill) throws ApiServiceException {
        if (ServiceUtils.isEmpty(skill) || ServiceUtils.isEmpty(skill.getName())) {
            throw new ApiServiceException("empty field");
        }
        skill.setStatus(1);
        Response responseObject = Response.builder()
                .code(0)
                .data(managerService.addSkill(skill))
                .message("success")
                .build();
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = {"/delete-skill"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> deleteSkill(@RequestParam int id) throws ApiServiceException {
        if (id <= 0) {
            throw new ApiServiceException("empty field");
        }
        Skill skill = managerService.findSkillById(id);
        if (ServiceUtils.isEmpty(skill)) {
            throw new ApiServiceException("skill not existed");
        }
        skill.setStatus(0);
        managerService.deleteSkill(skill);
        Response responseObject = Response.builder()
                .code(0)
                .message("success")
                .build();
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = {"/list-skill"}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> listSkill(@RequestBody RequestInfo requestInfo) {
        List<Skill> skillList = managerService.listSkill();
        skillList = ServiceUtils.paging(skillList, requestInfo.getPage(), requestInfo.getSize());
        Response responseObject = Response.builder()
                .code(0)
                .data(skillList)
                .message("success")
                .build();
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }
}
