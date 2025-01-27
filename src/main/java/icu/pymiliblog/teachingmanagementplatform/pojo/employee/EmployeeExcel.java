package icu.pymiliblog.teachingmanagementplatform.pojo.employee;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;

import java.util.Date;

public class EmployeeExcel {

    @ExcelProperty("员工ID")
    private Integer id;

    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("部门ID")
    private Integer departmentId;

    @ExcelProperty("电话")
    private String phone;

    @ExcelProperty("邮箱")
    private String email;

    @ExcelProperty("QQ")
    private String qq;

    @ExcelProperty("微信")
    private String wechat;

    @ExcelProperty("是否可用")
    private Boolean available;

    @ExcelProperty(value = "创建时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @ColumnWidth(20)
    private Date createdAt;

    @ExcelProperty(value = "更新时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @ColumnWidth(20)
    private Date updatedAt;

    // 构造方法（用于数据转换）
    public EmployeeExcel(EmployeePojo employee) {
        this.id = employee.getId();
        this.name = employee.getName();
        this.departmentId = employee.getDepartmentId();
        this.phone = employee.getPhone();
        this.email = employee.getEmail();
        this.qq = employee.getQq();
        this.wechat = employee.getWechat();
        this.available = employee.getAvailable();
        this.createdAt = employee.getCreatedAt();
        this.updatedAt = employee.getUpdatedAt();
    }

    // 必须有无参构造方法（EasyExcel 反射需要）
    public EmployeeExcel() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "EmployeeExcel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", departmentId=" + departmentId +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", qq='" + qq + '\'' +
                ", wechat='" + wechat + '\'' +
                ", available=" + available +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}