package com.api.user.service.impl;

import com.api.user.entity.Role;
import com.api.user.entity.User;
import com.api.user.entity.UserRole;
import com.api.user.entity.info.UserInfo;
import com.api.user.mapper.UserMapper;
import com.api.user.service.UserService;
import com.api.user.uitls.ServiceUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service(value = "userService")
public class UserServiceImpl implements UserService, UserDetailsService {


    private UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findUserByName(username);

        if (user == null) {
            throw new UsernameNotFoundException("User [" + username + "] not found");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), getAuthorities(user));
    }

    private Set<SimpleGrantedAuthority> getAuthorities(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + userMapper.findRoleByUserId(user.getId()).getName()));
        return authorities;
    }

    @Override
    public List<User> getAll() {
        return userMapper.findUserAll();
    }

    @Override
    public UserInfo findById(int userId) {
        return null;
    }

    @Override
    public int save(User user) {
        user.setPassword(ServiceUtils.encodePassword(user.getPassword()));
        user.setStatus(0);
        user.setCreated(System.currentTimeMillis());
        userMapper.insertUser(user);
        user.setRole(userMapper.findRoleById(user.getRole_id()));
        UserRole userRole = new UserRole(user.getId(), user.getRole_id());
        userMapper.insertUserRole(userRole);
        log.info("Create user {} success!", user.getUsername());
        return user.getId();
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }

    @Override
    public User findByUsername(String username) {
        return userMapper.findUserByName(username);
    }

    @Override
    public User findByUserId(int userId) {
        return userMapper.findByUserId(userId);
    }

    @Override
    public Role findRoleByUserId(int userId) {
        UserRole userRole = userMapper.getUserRole(userId);
        return userMapper.findRoleById(userRole.getRole_id());
    }

    @Override
    public User findByEmail(String email) {
        return userMapper.findByEmail(email);
    }

    @Override
    public boolean checkEmailExisted(String email) {
        List<String> list = userMapper.listEmail();
        return list.contains(email);
    }
}
