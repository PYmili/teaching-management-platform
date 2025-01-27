package icu.pymiliblog.teachingmanagementplatform.controller;

import icu.pymiliblog.teachingmanagementplatform.common.ApiResponse;
import icu.pymiliblog.teachingmanagementplatform.pojo.department.DepartmentPojo;
import icu.pymiliblog.teachingmanagementplatform.service.DepartmentService;
import icu.pymiliblog.teachingmanagementplatform.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<Object>> findById(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam(value = "id", required = false) Integer id) {
        logger.info("/api/department /find values: " +
                "authorization-header: {}, id: {}", authorizationHeader, id);
        if (JwtUtil.extractJwt(authorizationHeader) == null)
            return new ResponseEntity<>(
                ApiResponse.illegal(), HttpStatus.NOT_FOUND);
        if (id == null || id == 0) {
            return new ResponseEntity<>(
                    ApiResponse.not_found("参数错误！"), HttpStatus.NOT_FOUND);
        }
        return departmentService.findById(id);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<Object>> list(
            @RequestHeader("Authorization") String authorizationHeader) {
        logger.info("/api/department /list values: " +
                "authorization-header: {}", authorizationHeader);
        if (JwtUtil.extractJwt(authorizationHeader) == null)
            return new ResponseEntity<>(
                    ApiResponse.illegal(), HttpStatus.NOT_FOUND);
        return departmentService.list();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse<Object>> insert(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody DepartmentPojo departmentPojo) {
        if (JwtUtil.extractJwt(authorizationHeader) == null) {
            return new ResponseEntity<>(
                    ApiResponse.illegal(), HttpStatus.NOT_FOUND);
        }
        if (departmentPojo == null
                || departmentPojo.getDepartmentName() == null
                || departmentPojo.getDepartmentDescription() == null
                || departmentPojo.getParentDepartmentId() == null) {
            return new ResponseEntity<>(
                    ApiResponse.not_found("参数残缺！"), HttpStatus.NOT_FOUND);
        }
        logger.info("/api/department /insert values: " +
                "departmentPojo: {}", departmentPojo);
        return departmentService.insert(departmentPojo);
    }
}
