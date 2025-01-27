package icu.pymiliblog.teachingmanagementplatform.mapper;

import icu.pymiliblog.teachingmanagementplatform.pojo.user.UserPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper {
    boolean register(UserPojo userPojo);
    List<UserPojo> find(UserPojo userPojo);
    @Update("UPDATE users SET salt = #{newSalt} WHERE id = #{id}")
    boolean updateSalt(@Param("id") Integer id, @Param("newSalt") String newSalt);
    @Update("UPDATE users SET password_hash = #{newPasswordHash} WHERE id = #{id}")
    boolean updatePasswordHash(@Param("id") Integer id, @Param("newPasswordHash") String newPasswordHash);
}
