package icu.pymiliblog.teachingmanagementplatform.service;

import icu.pymiliblog.teachingmanagementplatform.common.ApiResponse;
import icu.pymiliblog.teachingmanagementplatform.mapper.DepartmentMapper;
import icu.pymiliblog.teachingmanagementplatform.pojo.department.DepartmentPojo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * 部门 Service
 * @author PYmili
 */
@Slf4j
@Service
public class DepartmentService {

    // 操作部门的mapper
    private final DepartmentMapper departmentMapper;

    public DepartmentService(DepartmentMapper departmentMapper) {
        this.departmentMapper = departmentMapper;
    }

    /**
     * 通过id操作Mapper查找
     * @param id {@link Integer}
     * @return {@link ResponseEntity}
     */
    public ResponseEntity<ApiResponse<Object>> findById(Integer id) {
        DepartmentPojo findResult = departmentMapper.findById(id);
        if (findResult == null) {
            return ApiResponse.not_found("未找到！");
        }
        return ApiResponse.ok(findResult);
    }

    /**
     * 通过Mapper返回所有
     * @return {@link ResponseEntity}
     */
    public ResponseEntity<ApiResponse<Object>> list() {
        return ApiResponse.ok(departmentMapper.list());
    }

    /**
     * 根据{@link DepartmentPojo}在Mapper中查找
     * @param departmentPojo {@link DepartmentPojo}
     * @return {@link ResponseEntity}
     */
    public ResponseEntity<ApiResponse<Object>> insertByPojo(DepartmentPojo departmentPojo) {
        boolean insertResult = departmentMapper.insertByPojo(departmentPojo);
        if (!insertResult) {
            return ApiResponse.not_found("操作失败！");
        }
        return ApiResponse.ok("成功！");
    }
}
