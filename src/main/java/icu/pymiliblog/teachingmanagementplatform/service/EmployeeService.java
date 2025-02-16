package icu.pymiliblog.teachingmanagementplatform.service;

import com.alibaba.excel.EasyExcel;
import icu.pymiliblog.teachingmanagementplatform.common.ApiResponse;
import icu.pymiliblog.teachingmanagementplatform.mapper.EmployeeMapper;
import icu.pymiliblog.teachingmanagementplatform.pojo.employee.EmployeeExcel;
import icu.pymiliblog.teachingmanagementplatform.pojo.employee.EmployeePojo;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 员工 Service
 * @author PYmili
 */
@Slf4j
@Service
public class EmployeeService {

    // 操作员工的mapper
    private final EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeMapper employeeMapper) {
        this.employeeMapper = employeeMapper;
    }

    /**
     * 通过id操作Mapper查找
     * @param id {@link Integer}
     * @return {@link ResponseEntity}
     */
    public ResponseEntity<ApiResponse<Object>> findById(Integer id) {
        EmployeePojo resultPojo = employeeMapper.findById(id);
        if (resultPojo == null) {
            return ApiResponse.not_found("未查找到！");
        }
        return ApiResponse.ok(resultPojo);
    }

    /**
     * 通过start和number操作Mapper查找
     * @param start {@link Integer}
     * @param number {@link Integer}
     * @return {@link ResponseEntity}
     */
    public ResponseEntity<ApiResponse<Object>> findByRange(Integer start, Integer number) {
        List<EmployeePojo> findResult = employeeMapper.findByRange(start, number);
        if (findResult.isEmpty()) {
            return ApiResponse.not_found("未查找到！");
        }
        return ApiResponse.ok(findResult);
    }

    /**
     * 员工总数
     * @return {@link ResponseEntity}
     */
    public ResponseEntity<ApiResponse<Object>> total() {
        return ApiResponse.ok(employeeMapper.total());
    }

    /**
     * 通过{@link EmployeePojo}操作Mapper查找
     * @param employeePojo {@link EmployeePojo}
     * @return {@link ResponseEntity}
     */
    public ResponseEntity<ApiResponse<Object>> insertByEmployee(EmployeePojo employeePojo) {
        boolean inserted = employeeMapper.insertByEmployee(employeePojo);
        if (!inserted) {
            return ApiResponse.not_found("创建用户失败！");
        }
        return ApiResponse.ok("创建成功！");
    }

    /**
     * 通过Id操作Mapper删除用户
     * @param id {@link Integer}
     * @return {@link ResponseEntity}
     */
    public ResponseEntity<ApiResponse<Object>> deleteById(Integer id) {
        boolean removeResult = employeeMapper.deleteById(id);
        if (!removeResult) {
            return ApiResponse.not_found("删除失败！");
        }
        return ApiResponse.ok("删除成功！");
    }

    /**
     * 导出用户数据为Excel
     * @param response {@link HttpServletResponse}
     * @throws IOException
     */
    public void exportEmployeesToExcel(HttpServletResponse response) throws IOException {
        // 1. 查询数据
        List<EmployeePojo> employees = employeeMapper.list();
        log.info("exportEmployeesToExcel 查询到数据: {}", employees.size());

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
