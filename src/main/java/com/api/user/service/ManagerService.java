package com.api.user.service;

import com.api.user.entity.Skill;
import com.api.user.entity.User;
import com.api.user.entity.model.RequestInfo;

import java.util.List;

public interface ManagerService {

    List<User> getAllUser(int type);

    int addSkill(Skill skill);

    List<Skill> listSkill();

    Skill findSkillById(int id);

    void deleteSkill(Skill skill);

    List<User>listTutor(RequestInfo requestInfo);
}
