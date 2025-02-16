package icu.pymiliblog.teachingmanagementplatform.controller;

import icu.pymiliblog.teachingmanagementplatform.common.ApiResponse;
import icu.pymiliblog.teachingmanagementplatform.pojo.user.UserRequestPojo;
import icu.pymiliblog.teachingmanagementplatform.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse<Object>> register(@RequestBody UserRequestPojo requestPojo) throws NoSuchAlgorithmException {
        log.info("/user/reg request: {}", requestPojo.toString());
        if (requestPojo.getUsername() == null || requestPojo.getPassword() == null) {
            return ApiResponse.not_found("参数残缺！");
        }
        return userService.register(requestPojo);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse<Object>> login(@RequestBody UserRequestPojo requestPojo) throws NoSuchAlgorithmException {
        log.info("/user/login request: {}", requestPojo.toString());
        if (requestPojo.getUsername() == null || requestPojo.getPassword() == null) {
            return ApiResponse.not_found("参数残缺！");
        }
        return userService.login(requestPojo);
    }
}
