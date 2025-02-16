package icu.pymiliblog.teachingmanagementplatform.service;

import icu.pymiliblog.teachingmanagementplatform.common.ApiResponse;
import icu.pymiliblog.teachingmanagementplatform.mapper.NotificationMapper;
import icu.pymiliblog.teachingmanagementplatform.pojo.notification.NotificationPojo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 通知 Service
 * @author PYmili
 */
@Slf4j
@Service
public class NoticeService {

    // 操作通知的Mapper
    private final NotificationMapper notificationMapper;

    public NoticeService(NotificationMapper notificationMapper) {
        this.notificationMapper = notificationMapper;
    }

    /**
     * 上传通知信息
     * @param notificationPojo {@link NotificationPojo}
     * @return {@link ResponseEntity}
     */
    public ResponseEntity<ApiResponse<Object>> upload(NotificationPojo notificationPojo) {
        log.info(notificationPojo.toString());
        boolean uploaded = notificationMapper.upload(notificationPojo);
        if (!uploaded) {
            return ApiResponse.not_found("添加失败！");
        }
        return ApiResponse.ok(notificationPojo);
    }

    /**
     * 通过{@link NotificationPojo}操作Mapper查找
     * @param notificationPojo {@link NotificationPojo}
     * @return {@link List}
     */
    public List<NotificationPojo> findByPojo(NotificationPojo notificationPojo) {
        return notificationMapper.findByPojo(notificationPojo);
    }

    /**
     * 通过start和number操作mapper查找
     * @param start {@link Integer} 起始位置
     * @param number {@link Integer} 需要查找的数量
     * @return {@link List}
     */
    public List<NotificationPojo> findByRange(Integer start, Integer number) {
        return notificationMapper.findByRange(start, number);
    }

    /**
     * 返回所有通知
     * @return {@link List}
     */
    public List<NotificationPojo> list() {
        return notificationMapper.list();
    }
}
