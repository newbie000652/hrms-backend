package com.hrms.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hrms.entity.Salary;
import com.hrms.service.ISalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 存储员工工资信息 前端控制器
 * </p>
 *
 * @author tra
 * @since 2024-12-14
 */
@RestController
@RequestMapping("/api/salary")
public class SalaryController {
    @Autowired
    private ISalaryService salaryService;

    /**
     * 分页获取工资记录（模糊搜索）
     */
    @GetMapping
    public IPage<Salary> getSalaries(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String searchBy,
            @RequestParam(required = false) String keyword) {
        return salaryService.getSalariesByCondition(page, size, searchBy, keyword);
    }

    /**
     * 计算并保存某员工的工资
     */
    @PostMapping("/calculate")
    public Salary calculateSalary(@RequestParam Long employeeId) {
        return salaryService.calculateAndSaveSalary(employeeId);
    }

    /**
     * 为员工创建初始工资记录
     */
    @PostMapping("/create")
    public Salary createDefaultSalary(@RequestParam Long employeeId) {
        return salaryService.createDefaultSalaryRecord(employeeId);
    }

    /**
     * 通过员工id计算并更新工资记录
     */
    @PutMapping("/update/{employeeId}")
    public ResponseEntity<Salary> updateSalary(@PathVariable Long employeeId) {
        Salary updatedSalary = salaryService.updateSalaryByEmployeeId(employeeId);
        return ResponseEntity.ok(updatedSalary);
    }
}
