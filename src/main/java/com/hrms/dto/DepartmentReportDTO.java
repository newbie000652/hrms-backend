package com.hrms.dto;

import java.sql.Timestamp;

/**
 * Data Transfer Object (DTO) for department reports.
 * Used to transfer data between different layers of the application.
 *
 * @author tra
 * @since 2024-12-21
 */
public class DepartmentReportDTO {

    /**
     * 部门ID
     */
    private Long departmentId;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 部门经理姓名
     */
    private String managerName;

    /**
     * 部门员工总数
     */
    private Integer employeeCount;

    /**
     * 部门创建时间
     */
    private Timestamp createdTime;

    /**
     * 最后更新时间
     */
    private Timestamp lastUpdatedTime;

    // Getters and Setters

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public Integer getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(Integer employeeCount) {
        this.employeeCount = employeeCount;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public Timestamp getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(Timestamp lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    @Override
    public String toString() {
        return "DepartmentReportDTO{" +
                "departmentId=" + departmentId +
                ", departmentName='" + departmentName + '\'' +
                ", managerName='" + managerName + '\'' +
                ", employeeCount=" + employeeCount +
                ", createdTime=" + createdTime +
                ", lastUpdatedTime=" + lastUpdatedTime +
                '}';
    }
}
