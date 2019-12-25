package com.api.user.mapper;

import com.api.user.entity.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user")
    List<User> findUserAll();

    @Select("SELECT user.* FROM user,user_role WHERE user.id = user_role.user_id AND user_role.role_id =2")
    List<User> findTutorAll();

    @Select("SELECT * FROM user WHERE username = #{username} AND status=1 ")
    User findUserByName(String username);


    @Update("UPDATE user SET  password = #{password}, display_name =#{display_name}, phone =#{phone}, status = #{status} " +
            ", email =#{email}, address =#{address}, avatar =#{avatar} , hourly_wage =#{hourly_wage}, description =#{description} ,updated = #{updated} WHERE id = #{id}")
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

    @Select("SELECT  role.* FROM user_role , role WHERE user_id = #{user_id} and user_role.role_id = role.id limit 1 ")
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

    @Select("SELECT skill.* FROM user_skill, skill WHERE user_skill.user_id =#{userId} AND user_skill.skill_id = skill.id ")
    List<Skill> listSkillByUser(int userId);


    @Select("<script>" +
            "SELECT  distinct user.* FROM user, user_skill WHERE user.updated >=0 AND ( user.username LIKE '%' #{username} '%' OR user.display_name LIKE '%' #{username} '%')" +
//            "<if test=\"skillId >0\"> AND user_skill.user_id = user.id</if>" +
            "<if test=\"username != null\"> AND ( user.username LIKE '%' #{username} '%' OR user.display_name LIKE '%' #{username} '%')</if>" +
//            "<if test=\"address != null\"> AND  user.address LIKE '%' #{address} '%'</if>" +
            "</script>")
//    @Select("<select id=\"findUserByFilter\"\n" +
//            "     resultType=\"User\">\n" +
//            "  SELECT * FROM user, user_skill\n" +
//            "  WHERE user.updated >=0" +
//            "  <if test=\"skillId != null\">\n" +
//            "    user_skill.user_id = user.id\n" +
//            "  </if>\n" +
//            "  <if test=\"username != null\">\n" +
//            "    AND (user.username LIKE '%' #{username} '%' OR user.display_name LIKE '%' #{username} '%')\n" +
//            "  </if>\n" +
//            "  <if test=\"address != null and author.name != null\">\n" +
//            "    AND  user.address LIKE '%' #{address} '%'\n" +
//            "  </if>\n" +
//            "</select>")
    List<User> findUserByFilter(@Param("username") String username, @Param("oderBy") String oderBy, @Param("address") String address, @Param("skillId") Integer skillId);

}
