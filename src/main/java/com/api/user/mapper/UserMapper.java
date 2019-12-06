package com.api.user.mapper;

import com.api.user.entity.Role;
import com.api.user.entity.User;
import com.api.user.entity.UserRole;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from user")
    List<User> findUserAll();


    @Select("select * from user WHERE username = #{username} AND status=1 ")
    User findUserByName(String username);


    @Update("UPDATE user SET  password = #{password}, display_name =#{display_name}, status = #{status} WHERE id = #{id}")
    void update(User user);


    @Insert("insert into user(username,display_name,password,status,phone,email,address,avatar,expired,created,updated) " +
            "values(#{username},#{display_name},#{password},#{status},#{phone},#{email},#{address},#{avatar},#{expired},#{created},#{updated})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id",
            before = false, resultType = Integer.class)
    void insertUser(User user);

    @Select("select * from role WHERE name = #{name}")
    Role findRoleByName(String name);

    @Select("select * from role WHERE id = #{id}")
    Role findRoleById(Integer id);

    @Delete("delete from user_role WHERE user_id = #{user_id}")
    void deleteUserRole(Integer user_id);

    @Select("select  role.id, role.name from user_role , role WHERE user_id = #{user_id} and user_role.role_id = role.id limit 1 ")
    Role findRoleByUserId(Integer user_id);

    @Insert("insert into user_role(user_id,role_id) values(#{user_id},#{role_id})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id",
            before = false, resultType = Integer.class)
    void insertUserRole(UserRole userRole);

    @Select("select * from user_role WHERE user_id = #{user_id} limit 1")
    UserRole getUserRole(Integer user_id);

    @Select("select * from user WHERE id = #{userId}")
    User findByUserId(int userId);

    @Select("select * from user WHERE email = #{email}")
    User findByEmail(String email);

    @Select("select email from user")
    List<String> listEmail();
}
