package icu.pymiliblog.teachingmanagementplatform.pojo.department;

import java.util.Date;

/**
 * 部门 POJO
 * @author PYmili
 */
public class DepartmentPojo {
    private Integer id;
    private String departmentName;
    private Integer parentDepartmentId;
    private String departmentDescription;
    private Date createdAt;
    private Date updatedAt;

    public DepartmentPojo() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Integer getParentDepartmentId() {
        return parentDepartmentId;
    }

    public void setParentDepartmentId(Integer parentDepartmentId) {
        this.parentDepartmentId = parentDepartmentId;
    }

    public String getDepartmentDescription() {
        return departmentDescription;
    }

    public void setDepartmentDescription(String departmentDescription) {
        this.departmentDescription = departmentDescription;
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
        return "DepartmentPojo{" +
                "id=" + id +
                ", departmentName='" + departmentName + '\'' +
                ", parentDepartmentId=" + parentDepartmentId +
                ", departmentDescription='" + departmentDescription + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
