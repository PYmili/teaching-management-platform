package icu.pymiliblog.teachingmanagementplatform.mapper;

import icu.pymiliblog.teachingmanagementplatform.pojo.department.DepartmentPojo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 部门 mapper
 * @author PYmili
 */
@Mapper
public interface DepartmentMapper {
    /**
     * 通过Id查找
     * @param id {@link Integer}
     * @return {@link DepartmentPojo}
     */
    DepartmentPojo findById(Integer id);

    /**
     * 返回部门列表
     * @return {@link List}
     */
    List<DepartmentPojo> list();

    /**
     * 根据 {@link DepartmentPojo} 插入部门
     * @param departmentPojo {@link DepartmentPojo}
     * @return boolean
     */
    boolean insertByPojo(DepartmentPojo departmentPojo);
}
