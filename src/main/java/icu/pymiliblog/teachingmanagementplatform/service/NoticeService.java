package icu.pymiliblog.teachingmanagementplatform.service;

import icu.pymiliblog.teachingmanagementplatform.common.ApiResponse;
import icu.pymiliblog.teachingmanagementplatform.mapper.NotificationMapper;
import icu.pymiliblog.teachingmanagementplatform.pojo.notification.NotificationPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService {
    private static final Logger logger = LoggerFactory.getLogger(NoticeService.class);
    @Autowired
    private NotificationMapper notificationMapper;

    public ResponseEntity<ApiResponse<Object>> upload(NotificationPojo notificationPojo) {
        logger.info(notificationPojo.toString());
        boolean uploaded = notificationMapper.upload(notificationPojo);
        if (!uploaded) return new ResponseEntity<>(
                ApiResponse.not_found("添加失败！"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(ApiResponse.ok(notificationPojo), HttpStatus.OK);
    }

    public List<NotificationPojo> findByPojo(NotificationPojo notificationPojo) {
        return notificationMapper.find(notificationPojo);
    }

    public List<NotificationPojo> findRange(Integer start, Integer number) {
        return notificationMapper.findRange(start, number);
    }

    public List<NotificationPojo> findALl() {
        return notificationMapper.findAll();
    }
}
