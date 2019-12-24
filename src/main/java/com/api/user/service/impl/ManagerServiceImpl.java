package com.api.user.service.impl;

import com.api.user.entity.Contract;
import com.api.user.entity.Role;
import com.api.user.entity.Skill;
import com.api.user.entity.User;
import com.api.user.entity.model.RequestInfo;
import com.api.user.mapper.ContractMapper;
import com.api.user.mapper.ManageMapper;
import com.api.user.mapper.UserMapper;
import com.api.user.service.ManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service(value = "managerService")
public class ManagerServiceImpl implements ManagerService {

    private UserMapper userMapper;
    private ManageMapper manageMapper;
    private ContractMapper contractMapper;

    @Autowired
    public ManagerServiceImpl(UserMapper userMapper, ManageMapper manageMapper, ContractMapper contractMapper) {
        this.userMapper = userMapper;
        this.manageMapper = manageMapper;
        this.contractMapper = contractMapper;
    }

    @Override
    public List<User> getAllUser(int roleId) {
        List<User> users = new ArrayList<>();
        List<User> list = userMapper.findUserAll();
        if (roleId == 0) {
            return list;
        }
        for (User user : list) {
            Role role = userMapper.findRoleByUserId(user.getId());
            if (role.getId() == roleId) {
                users.add(user);
            }
        }
        return users;
    }

    @Override
    public List<User> listTutor(RequestInfo requestInfo) {
        List<User> users = new ArrayList<>();
        List<User> list = userMapper.findUserByFilter(requestInfo.getName(), requestInfo.getOrderBy(), requestInfo.getAddress(), requestInfo.getSkillId());
        for (User user : list) {
            Role role = userMapper.findRoleByUserId(user.getId());
            if (role.getId() == 2) {
                users.add(user);
            }
        }
        return users;
    }

    @Override
    public int addSkill(Skill skill) {
        manageMapper.insertSkill(skill);
        return skill.getId();
    }

    @Override
    public List<Skill> listSkill() {
        return manageMapper.listAllSkill();
    }

    @Override
    public Skill findSkillById(int id) {
        return manageMapper.findSkillById(id);
    }


    @Override
    public void updateSkill(Skill skill) {
        skill.setUpdated(System.currentTimeMillis());
        manageMapper.updateSkill(skill);
    }

    @Override
    public List<Contract> getListContract(RequestInfo requestInfo) {
        if (requestInfo.getDateFrom() == 0 || requestInfo.getDateTo() == 0) {
            return contractMapper.getListContract();
        }
        return contractMapper.getListContractByFilter(requestInfo.getDateFrom(), requestInfo.getDateTo(), requestInfo.getStatusContract());
    }
}
