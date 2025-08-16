package com.hrms.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 * 个人工资报表 DTO
 */
@Getter
@Setter
public class PersonalReportDTO {
    private Long employeeId;
    private String name;
    private BigDecimal baseSalary;
    private BigDecimal bonus;
    private BigDecimal penalty;
    private BigDecimal totalSalary;
}
