package com.hrms.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class EmployeeDetailsDTO {
    private Long id;
    private String name;
    private String gender;
    private String email;
    private String phone;
    private Timestamp entryDate;
    private Boolean isActive;

    // 部门信息
    private Long departmentId;
    private String departmentName;

    // 级别信息
    private Long levelId;
    private String levelName;
    private BigDecimal baseSalary;

    // 技能信息列表
    private List<String> skillNames;
}
