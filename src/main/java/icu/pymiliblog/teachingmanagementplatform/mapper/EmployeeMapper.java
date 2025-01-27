package icu.pymiliblog.teachingmanagementplatform.mapper;

import icu.pymiliblog.teachingmanagementplatform.pojo.employee.EmployeePojo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    EmployeePojo findById(Integer id);

    List<EmployeePojo> findRange(Integer start, Integer number);

    @Select("SELECT COUNT(*) FROM employees;")
    Integer total();

    boolean insertEmployee(EmployeePojo employeePojo);

    @Delete("DELETE FROM employees WHERE id = #{id}")
    boolean removeEmployeeById(Integer id);

    List<EmployeePojo> findAllEmployees();
}
