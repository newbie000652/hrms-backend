package com.hrms.controller;

import com.hrms.dto.PersonalReportDTO;
import com.hrms.entity.Salary;
import com.hrms.service.ISalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 * <p>
 * 存储员工工资信息 前端控制器
 * </p>
 *
 * @author tra
 * @since 2024-12-17
 */
@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {
    @Autowired
    private ISalaryService salaryService;

    /**
     * 获取某员工的工资报表
     *
     * @param employeeId 员工ID
     * @return 工资报表
     */
    @GetMapping("/report/{employeeId}")
    public List<PersonalReportDTO> getSalaryReportByEmployeeId(@PathVariable Long employeeId) {
        return salaryService.getSalaryReportByEmployeeId(employeeId);
    }
}
