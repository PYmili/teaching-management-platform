package icu.pymiliblog.teachingmanagementplatform.controller;

import icu.pymiliblog.teachingmanagementplatform.common.ApiResponse;
import icu.pymiliblog.teachingmanagementplatform.pojo.notification.NotificationPojo;
import icu.pymiliblog.teachingmanagementplatform.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/notice")
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse<Object>> upload(@RequestBody NotificationPojo notificationPojo) {
        log.info(notificationPojo.toString());
        if (notificationPojo.getTitle() == null ||
                notificationPojo.getTitle().isEmpty() ||
                notificationPojo.getContent() == null ||
                notificationPojo.getContent().isEmpty()) {
            return ApiResponse.not_found("参数残缺！");
        }
        return noticeService.upload(notificationPojo);
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<Object>> find(Integer id) {
        log.info("/find id = {}", id);
        if (id == 0) {
            return ApiResponse.not_found("参数不合法！");
        }
        NotificationPojo notificationPojo = new NotificationPojo();
        notificationPojo.setId(id);
        return ApiResponse.ok(noticeService.findByPojo(notificationPojo));
    }

    @RequestMapping(value = "/find-range", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<Object>> findRange(
            @RequestParam(value = "start", required = false) Integer start,
            @RequestParam(value = "number", required = false) Integer number) {
        // NotificationPojo notificationPojo = new NotificationPojo();
        if (start == null || number == null) {
            return ApiResponse.not_found("参数错误！");
        }
        if (start == -1 && number == -1) {
            return ApiResponse.ok(noticeService.list());
        }
        return ApiResponse.ok(noticeService.findByRange(start, number));
    }
}
