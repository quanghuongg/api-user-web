package com.api.user.service.impl;

import com.api.user.entity.Role;
import com.api.user.entity.Skill;
import com.api.user.entity.User;
import com.api.user.mapper.ManageMapper;
import com.api.user.mapper.UserMapper;
import com.api.user.service.ManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service(value = "managerService")
public class ManagerServiceImpl implements ManagerService {
    private UserMapper userMapper;

    private ManageMapper manageMapper;

    public ManagerServiceImpl(UserMapper userMapper, ManageMapper manageMapper) {
        this.userMapper = userMapper;
        this.manageMapper = manageMapper;
    }

    @Override
    public List<User> getAllUser(int type) {
        List<User> users = new ArrayList<>();
        List<User> list = userMapper.findUserAll();
        for (User user : list) {
            Role role = userMapper.findRoleByUserId(user.getId());
            if (role.getId() == type) {
                users.add(user);
            }
        }
        return users;
    }

    @Override
    public int addSkill(Skill skill) {
        manageMapper.indsertSkill(skill);
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
    public void deleteSkill(Skill skill) {
        manageMapper.updateSkill(skill);
    }
}
