package icu.pymiliblog.teachingmanagementplatform.controller;

import icu.pymiliblog.teachingmanagementplatform.common.ApiResponse;
import icu.pymiliblog.teachingmanagementplatform.pojo.employee.EmployeePojo;
import icu.pymiliblog.teachingmanagementplatform.service.EmployeeService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<Object>> find(@RequestParam(value = "id", required = false) Integer id) {
        log.info("/api/employee/find values: id: {}", id);
        if (id == null || id == 0) {
            return ApiResponse.not_found("参数错误！");
        }
        return employeeService.findById(id);
    }

    @RequestMapping(value = "/range", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<Object>> list(
            @RequestParam(value = "start", required = false) Integer start,
            @RequestParam(value = "number", required = false) Integer number) {
        log.info("/api/employee/range values: " +
                "start: {}, number: {}", start, number);
        if (start == null || number == null) {
            return ApiResponse.not_found("参数错误！");
        }
        return employeeService.findByRange(start, number);
    }

    @RequestMapping(value = "/total", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<Object>> total() {
        return employeeService.total();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse<Object>> insertEmployee(@RequestBody EmployeePojo employeePojo) {
        if (employeePojo == null) {
            return ApiResponse.not_found("参数错误！");
        }
        employeePojo.setId(null);
        employeePojo.setUpdatedAt(null);
        employeePojo.setCreatedAt(null);
        return employeeService.insertByEmployee(employeePojo);
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse<Object>> remove(@RequestBody Integer id) {
        log.info("/api/employee/remove values: " + "id: {}", id);
        if (id == null || id == 0) {
            return ApiResponse.not_found("参数残缺！");
        }
        return employeeService.deleteById(id);
    }

    @GetMapping("/export")
    public void exportEmployees(HttpServletResponse response) throws IOException {
        employeeService.exportEmployeesToExcel(response);
    }
}
