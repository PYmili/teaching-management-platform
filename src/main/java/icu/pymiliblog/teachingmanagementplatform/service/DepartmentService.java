package icu.pymiliblog.teachingmanagementplatform.service;

import icu.pymiliblog.teachingmanagementplatform.common.ApiResponse;
import icu.pymiliblog.teachingmanagementplatform.mapper.DepartmentMapper;
import icu.pymiliblog.teachingmanagementplatform.pojo.department.DepartmentPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    public ResponseEntity<ApiResponse<Object>> findById(Integer id) {
        DepartmentPojo findResult = departmentMapper.findById(id);
        if (findResult == null) {
            return new ResponseEntity<>(
                    ApiResponse.not_found("未找到！"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ApiResponse.ok(findResult), HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<Object>> list() {
        return new ResponseEntity<>(
                ApiResponse.ok(departmentMapper.list()), HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse<Object>> insert(DepartmentPojo departmentPojo) {
        boolean insertResult = departmentMapper.insert(departmentPojo);
        if (!insertResult) {
            return new ResponseEntity<>(
                    ApiResponse.not_found("操作失败！"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(
                ApiResponse.ok("成功！"), HttpStatus.OK);
    }
}
