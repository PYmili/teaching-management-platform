package icu.pymiliblog.teachingmanagementplatform.mapper;

import icu.pymiliblog.teachingmanagementplatform.pojo.department.DepartmentPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DepartmentMapper {
    DepartmentPojo findById(Integer id);
    List<DepartmentPojo> list();
    boolean insert(DepartmentPojo departmentPojo);
}
