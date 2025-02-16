package icu.pymiliblog.teachingmanagementplatform.mapper;

import icu.pymiliblog.teachingmanagementplatform.pojo.employee.EmployeePojo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 员工 mapper
 * @author PYmili
 */
@Mapper
public interface EmployeeMapper {

    /**
     * 通过Id查找
     * @param id {@link Integer}
     * @return {@link EmployeePojo}
     */
    EmployeePojo findById(Integer id);

    /**
     * 根据start和number查找
     * @param start {@link Integer} 起始位置
     * @param number {@link Integer} 需要获取的数量
     * @return {@link List}
     */
    List<EmployeePojo> findByRange(Integer start, Integer number);

    /**
     * 返回员工的总数
     * @return {@link Integer}
     */
    @Select("SELECT COUNT(id) FROM employees;")
    Integer total();

    /**
     * 通过{@link EmployeePojo}插入新员工
     * @param employeePojo {@link EmployeePojo}
     * @return boolean
     */
    boolean insertByEmployee(EmployeePojo employeePojo);

    /**
     * 通过id删除员工
     * @param id {@link Integer}
     * @return boolean
     */
    @Delete("DELETE FROM employees WHERE id = #{id}")
    boolean deleteById(Integer id);

    /**
     * 返回所有员工
     * @return {@link List}
     */
    List<EmployeePojo> list();
}
