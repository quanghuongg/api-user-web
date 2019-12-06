package com.api.user.service;

import com.api.user.entity.Role;
import com.api.user.entity.User;
import com.api.user.entity.info.UserInfo;

import java.util.List;

public interface UserService {

    List<User> getAll();

    UserInfo findById(int userId);

    int save(User user);

    void update(User user);

    User findByUsername(String username);

    User findByUserId(int userId);

    Role findRoleByUserId(int userId);

    User findByEmail(String email);

    boolean checkEmailExisted(String email);
}
