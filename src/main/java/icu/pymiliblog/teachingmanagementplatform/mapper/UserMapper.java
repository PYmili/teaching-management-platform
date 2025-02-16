package icu.pymiliblog.teachingmanagementplatform.mapper;

import icu.pymiliblog.teachingmanagementplatform.pojo.user.UserPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 用户 mapper
 * @author PYmili
 */
@Mapper
public interface UserMapper {
    /**
     * 注册用户
     * @param userPojo {@link  UserPojo}
     * @return boolean
     */
    boolean upload(UserPojo userPojo);

    /**
     * 通过{@link UserPojo}查找
     * @param userPojo {@link UserPojo}
     * @return {@link List}
     */
    List<UserPojo> findByPojo(UserPojo userPojo);

    /**
     * 更新hash盐
     * @param id {@link Integer}
     * @param newSalt {@link String}
     * @return boolean
     */
    @Update("UPDATE users SET salt = #{newSalt} WHERE id = #{id}")
    boolean updateSalt(@Param("id") Integer id, @Param("newSalt") String newSalt);

    /**
     * 更新hash密码
     * @param id {@link Integer}
     * @param newPasswordHash {@link String}
     * @return boolean
     */
    @Update("UPDATE users SET password_hash = #{newPasswordHash} WHERE id = #{id}")
    boolean updatePasswordHash(@Param("id") Integer id, @Param("newPasswordHash") String newPasswordHash);
}
