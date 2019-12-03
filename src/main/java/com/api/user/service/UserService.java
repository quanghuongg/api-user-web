package com.api.user.service;

import com.api.user.entity.Role;
import com.api.user.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAll();

    List<Role> getAllRole();

    int save(User user);

    User findByUsername(String username);

    Role findRoleByUserId(int userId);


}
