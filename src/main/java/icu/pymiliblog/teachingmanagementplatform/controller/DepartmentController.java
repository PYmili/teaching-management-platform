package icu.pymiliblog.teachingmanagementplatform.controller;

import icu.pymiliblog.teachingmanagementplatform.common.ApiResponse;
import icu.pymiliblog.teachingmanagementplatform.pojo.department.DepartmentPojo;
import icu.pymiliblog.teachingmanagementplatform.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<Object>> findById(@RequestParam(value = "id", required = false) Integer id) {
        log.info("/api/department /find values: id: {}", id);
        if (id == null || id == 0) {
            return ApiResponse.not_found("参数错误！");
        }
        return departmentService.findById(id);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<Object>> list() {
        log.info("/api/department /list");
        return departmentService.list();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse<Object>> insert(@RequestBody DepartmentPojo departmentPojo) {
        if (departmentPojo == null
                || departmentPojo.getDepartmentName() == null
                || departmentPojo.getDepartmentDescription() == null
                || departmentPojo.getParentDepartmentId() == null) {
            return ApiResponse.not_found("参数残缺！");
        }
        log.info("/api/department /insert values: " +
                "departmentPojo: {}", departmentPojo);
        return departmentService.insertByPojo(departmentPojo);
    }
}
