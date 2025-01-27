package icu.pymiliblog.teachingmanagementplatform.controller;

import icu.pymiliblog.teachingmanagementplatform.common.ApiResponse;
import icu.pymiliblog.teachingmanagementplatform.pojo.notification.NotificationPojo;
import icu.pymiliblog.teachingmanagementplatform.service.NoticeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notice")
public class NoticeController {
    private static final Logger logger = LoggerFactory.getLogger(NoticeService.class);

    @Autowired
    private NoticeService noticeService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse<Object>> upload(@RequestBody NotificationPojo notificationPojo) {
        logger.info(notificationPojo.toString());
        if (notificationPojo.getTitle() == null ||
                notificationPojo.getTitle().isEmpty() ||
                notificationPojo.getContent() == null ||
                notificationPojo.getContent().isEmpty()) {
            return new ResponseEntity<>(
                    ApiResponse.not_found("参数残缺！"), HttpStatus.NOT_FOUND);
        }
        return noticeService.upload(notificationPojo);
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<Object>> find(Integer id) {
        logger.info("/find id = {}", id);
        if (id == 0) {
            return new ResponseEntity<>(
                    ApiResponse.not_found("参数不合法！"), HttpStatus.NOT_FOUND);
        }
        NotificationPojo notificationPojo = new NotificationPojo();
        notificationPojo.setId(id);
        return new ResponseEntity<>(
                ApiResponse.ok(noticeService.findByPojo(notificationPojo)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/find-range", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<Object>> findRange(
            @RequestParam(value = "start", required = false) Integer start,
            @RequestParam(value = "number", required = false) Integer number) {
        NotificationPojo notificationPojo = new NotificationPojo();
        if (start == null || number == null) {
            return new ResponseEntity<>(
                    ApiResponse.not_found("参数错误！"), HttpStatus.NOT_FOUND
            );
        }
        if (start == -1 && number == -1) {
            return new ResponseEntity<>(
                    ApiResponse.ok(noticeService.findALl()), HttpStatus.OK
            );
        }
        return new ResponseEntity<>(
                ApiResponse.ok(noticeService.findRange(start, number)),
                HttpStatus.OK);
    }
}
