package icu.pymiliblog.teachingmanagementplatform.mapper;

import icu.pymiliblog.teachingmanagementplatform.pojo.notification.NotificationPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NotificationMapper {
    boolean upload(NotificationPojo notificationPojo);
    List<NotificationPojo> find(NotificationPojo notificationPojo);
    List<NotificationPojo> findRange(@Param("start") Integer start, @Param("number") Integer number);
    List<NotificationPojo> findAll();
}
