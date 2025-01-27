package icu.pymiliblog.teachingmanagementplatform.service;

import com.alibaba.excel.EasyExcel;
import icu.pymiliblog.teachingmanagementplatform.common.ApiResponse;
import icu.pymiliblog.teachingmanagementplatform.mapper.EmployeeMapper;
import icu.pymiliblog.teachingmanagementplatform.pojo.employee.EmployeeExcel;
import icu.pymiliblog.teachingmanagementplatform.pojo.employee.EmployeePojo;
import icu.pymiliblog.teachingmanagementplatform.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);
    @Autowired
    private EmployeeMapper employeeMapper;

    public ResponseEntity<ApiResponse<Object>> findById(Integer id, String jwt) {
        if (JwtUtil.verifyJwt(jwt)) {
            return new ResponseEntity<>(
                    ApiResponse.illegal(), HttpStatus.NOT_FOUND);
        }
        EmployeePojo resultPojo = employeeMapper.findById(id);
        if (resultPojo == null) {
            return new ResponseEntity<>(
                    ApiResponse.not_found("未查找到！"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ApiResponse.ok(resultPojo), HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<Object>> findRange(Integer start, Integer number, String jwt) {
        if (JwtUtil.verifyJwt(jwt)) {
            return new ResponseEntity<>(
                    ApiResponse.illegal(), HttpStatus.NOT_FOUND);
        }
        List<EmployeePojo> findResult = employeeMapper.findRange(start, number);
        if (findResult.isEmpty()) {
            return new ResponseEntity<>(
                    ApiResponse.not_found("未查找到！"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ApiResponse.ok(findResult), HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<Object>> total() {
        return new ResponseEntity<>(ApiResponse.ok(employeeMapper.total()), HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<Object>> insertEmployee(EmployeePojo employeePojo) {
        boolean inserted = employeeMapper.insertEmployee(employeePojo);
        if (!inserted) {
            return new ResponseEntity<>(
                    ApiResponse.not_found("创建用户失败！"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(
                ApiResponse.ok("创建成功！"), HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<Object>> removeById(Integer id) {
        boolean removeResult = employeeMapper.removeEmployeeById(id);
        if (!removeResult) {
            return new ResponseEntity<>(
                    ApiResponse.not_found("删除失败！"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(
                ApiResponse.ok("删除成功！"), HttpStatus.OK);
    }

    public void exportEmployeesToExcel(HttpServletResponse response) throws IOException {
        // 1. 查询数据
        List<EmployeePojo> employees = employeeMapper.findAllEmployees();
        logger.info("exportEmployeesToExcel 查询到数据: {}", employees.size());

        // 2. 转换为 Excel 映射对象
        List<EmployeeExcel> excelData = employees.stream()
                .map(EmployeeExcel::new)
                .collect(Collectors.toList());

        // 3. 设置响应头
        String fileName = URLEncoder.encode("员工列表.xlsx", StandardCharsets.UTF_8);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        // 4. 导出 Excel
        EasyExcel.write(response.getOutputStream(), EmployeeExcel.class)
                .autoCloseStream(true)
                .sheet("员工数据")
                .doWrite(excelData);
    }
}
