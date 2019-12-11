package com.api.user.mapper;

import com.api.user.entity.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user")
    List<User> findUserAll();

    @Select("SELECT * FROM user WHERE username LIKE '%' #{keyword} '%' OR hourly_wage LIKE '%' #{keyword} '%'" +
            "OR address LIKE '%' #{keyword} '%' OR display_name LIKE '%' #{keyword} '%' ORDER BY user.username ${oderBy}")
    List<User> findUserByKeyword(String keyword, String oderBy);

    @Select("SELECT * FROM user WHERE username = #{username} AND status=1 ")
    User findUserByName(String username);


    @Update("UPDATE user SET  password = #{password}, display_name =#{display_name}, phone =#{phone}, status = #{status} " +
            ", email =#{email}, address =#{address}, avatar =#{avatar} , hourly_wage =#{hourly_wage}, description =#{description} WHERE id = #{id}")
    void update(User user);


    @Insert("insert into user(username,display_name,password,status,phone,email,address,avatar,expired,created,updated) " +
            "values(#{username},#{display_name},#{password},#{status},#{phone},#{email},#{address},#{avatar},#{expired},#{created},#{updated})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id",
            before = false, resultType = Integer.class)
    void insertUser(User user);

    @Select("SELECT * FROM role WHERE name = #{name}")
    Role findRoleByName(String name);

    @Select("SELECT * FROM role WHERE id = #{id}")
    Role findRoleById(Integer id);

    @Delete("delete FROM user_role WHERE user_id = #{user_id}")
    void deleteUserRole(Integer user_id);

    @Select("SELECT  role.id, role.name FROM user_role , role WHERE user_id = #{user_id} and user_role.role_id = role.id limit 1 ")
    Role findRoleByUserId(Integer user_id);

    @Insert("insert into user_role(user_id,role_id) values(#{user_id},#{role_id})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id",
            before = false, resultType = Integer.class)
    void insertUserRole(UserRole userRole);

    @Select("SELECT * FROM user_role WHERE user_id = #{user_id} limit 1")
    UserRole getUserRole(Integer user_id);

    @Select("SELECT * FROM user WHERE id = #{userId}")
    User findByUserId(int userId);

    @Select("SELECT * FROM user WHERE email = #{email}")
    User findByEmail(String email);

    @Select("SELECT email FROM user")
    List<String> listEmail();

    @Insert("insert into user_skill(user_id,skill_id) values(#{user_id},#{skill_id})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id",
            before = false, resultType = Integer.class)
    void insertUserSkill(UserSkill userSkill);

    @Select("SELECT skill.id, skill.name, skill.description FROM user_skill, skill WHERE user_skill.user_id =#{userId} AND user_skill.skill_id = skill.id")
    List<Skill> listSkillByUser(int  userId);
}
