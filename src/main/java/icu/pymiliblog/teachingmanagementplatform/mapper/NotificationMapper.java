package icu.pymiliblog.teachingmanagementplatform.mapper;

import icu.pymiliblog.teachingmanagementplatform.pojo.notification.NotificationPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 通知信息 mapper
 * @author PYmili
 */
@Mapper
public interface NotificationMapper {
    /**
     * 上传通知信息
     * @param notificationPojo {@link NotificationPojo}
     * @return boolean
     */
    boolean upload(NotificationPojo notificationPojo);

    /**
     * 通过{@link NotificationPojo}查找
     * @param notificationPojo {@link NotificationPojo}
     * @return {@link List}
     */
    List<NotificationPojo> findByPojo(NotificationPojo notificationPojo);

    /**
     * 通过start和number查找
     * @param start {@link Integer} 起始位置
     * @param number {@link Integer} 需要获取的数量
     * @return {@link List}
     */
    List<NotificationPojo> findByRange(@Param("start") Integer start, @Param("number") Integer number);

    /**
     * 返回所有通知
     * @return {@link List}
     */
    List<NotificationPojo> list();
}
