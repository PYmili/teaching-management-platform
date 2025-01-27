package icu.pymiliblog.teachingmanagementplatform.controller;

import icu.pymiliblog.teachingmanagementplatform.common.ApiResponse;
import icu.pymiliblog.teachingmanagementplatform.pojo.employee.EmployeePojo;
import icu.pymiliblog.teachingmanagementplatform.service.EmployeeService;
import icu.pymiliblog.teachingmanagementplatform.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<Object>> find(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam(value = "id", required = false) Integer id) {
        logger.info("/api/employee/find values: " +
                "authorizationHeader: {}, id: {}", authorizationHeader, id);
        String jwt = JwtUtil.extractJwt(authorizationHeader);
        if (jwt == null) {
            return new ResponseEntity<>(ApiResponse.illegal(), HttpStatus.NOT_FOUND);
        }
        if (id == null || id == 0) {
            return new ResponseEntity<>(
                    ApiResponse.not_found("参数错误！"), HttpStatus.NOT_FOUND);
        }
        return employeeService.findById(id, jwt);
    }

    @RequestMapping(value = "/range", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<Object>> list(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam(value = "start", required = false) Integer start,
            @RequestParam(value = "number", required = false) Integer number) {
        logger.info("/api/employee/range values: " +
                "authorizationHeader: {}, start: {}, number: {}",
                authorizationHeader, start, number);
        String jwt = JwtUtil.extractJwt(authorizationHeader);
        if (jwt == null) {
            return new ResponseEntity<>(ApiResponse.illegal(), HttpStatus.NOT_FOUND);
        }
        if (start == null || number == null) {
            return new ResponseEntity<>(
                    ApiResponse.not_found("参数错误！"), HttpStatus.NOT_FOUND);
        }
        return employeeService.findRange(start, number, jwt);
    }

    @RequestMapping(value = "/total", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<Object>> total(
            @RequestHeader("Authorization") String authorizationHeader) {
        String jwt = JwtUtil.extractJwt(authorizationHeader);
        if (jwt == null) {
            return new ResponseEntity<>(ApiResponse.illegal(), HttpStatus.NOT_FOUND);
        }
        return employeeService.total();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse<Object>> insertEmployee(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody EmployeePojo employeePojo) {
        if (JwtUtil.extractJwt(authorizationHeader) == null) {
            return new ResponseEntity<>(
                    ApiResponse.illegal(), HttpStatus.NOT_FOUND);
        }
        if (employeePojo == null) {
            return new ResponseEntity<>(
                    ApiResponse.not_found("参数错误！"), HttpStatus.OK);
        }
        employeePojo.setId(null);
        employeePojo.setUpdatedAt(null);
        employeePojo.setCreatedAt(null);
        return employeeService.insertEmployee(employeePojo);
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse<Object>> remove(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody Integer id) {
        logger.info("/api/employee/remove values: " +
                "authorizationHeader: {}, id: {}", authorizationHeader, id);
        if (JwtUtil.extractJwt(authorizationHeader) == null) {
            return new ResponseEntity<>(
                    ApiResponse.illegal(), HttpStatus.NOT_FOUND);
        }
        if (id == null || id == 0) {
            return new ResponseEntity<>(
                    ApiResponse.not_found("参数残缺！"), HttpStatus.NOT_FOUND);
        }
        return employeeService.removeById(id);
    }

    @GetMapping("/export")
    public void exportEmployees(
            @RequestHeader("Authorization") String authorizationHeader,
            HttpServletResponse response) throws IOException {
        if (JwtUtil.extractJwt(authorizationHeader) == null) {
            return;
        }
        employeeService.exportEmployeesToExcel(response);
    }
}
