package icu.pymiliblog.teachingmanagementplatform.controller;

import icu.pymiliblog.teachingmanagementplatform.common.ApiResponse;
import icu.pymiliblog.teachingmanagementplatform.pojo.user.UserRequestPojo;
import icu.pymiliblog.teachingmanagementplatform.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse<Object>> register(@RequestBody UserRequestPojo requestPojo) throws NoSuchAlgorithmException {
        logger.info("/user/reg request: {}", requestPojo.toString());
        if (requestPojo.getUsername() == null || requestPojo.getPassword() == null) {
            return new ResponseEntity<>(
                    ApiResponse.not_found("参数残缺！"),
                    HttpStatus.NOT_FOUND);
        }
        return userService.register(requestPojo);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse<Object>> login(@RequestBody UserRequestPojo requestPojo) throws NoSuchAlgorithmException {
        logger.info("/user/login request: {}", requestPojo.toString());
        if (requestPojo.getUsername() == null || requestPojo.getPassword() == null) {
            return new ResponseEntity<>(
                    ApiResponse.not_found("参数残缺！"),
                    HttpStatus.NOT_FOUND);
        }
        return userService.login(requestPojo);
    }
}
