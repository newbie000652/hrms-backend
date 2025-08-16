package com.hrms.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hrms.entity.Employee;
import com.hrms.dto.EmployeeDetailsDTO;
import com.hrms.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resignations")
public class ResignationController {
    @Autowired
    private IEmployeeService employeeService;

    /**
     * 查询所有离职员工
     */
    @GetMapping
    public List<Employee> getInactiveEmployees() {
        return employeeService.getInactiveEmployees();
    }

    /**
     * 根据条件查询离职员工（分页）
     */
    @GetMapping("/search")
    public IPage<Employee> searchInactiveEmployees(@RequestParam(defaultValue = "1") int page,
                                                   @RequestParam(defaultValue = "10") int size,
                                                   @RequestParam(required = false) Long id,
                                                   @RequestParam(required = false) String name,
                                                   @RequestParam(required = false) Byte levelId,
                                                   @RequestParam(required = false) Long departmentId) {
        return employeeService.getInactiveEmployeesByCondition(new Page<>(page, size), id, name, levelId, departmentId);
    }

    /**
     * 根据员工ID查询详细信息
     */
    @GetMapping("/details/{id}")
    public EmployeeDetailsDTO getEmployeeDetails(@PathVariable Long id) {
        return employeeService.getEmployeeDetailsById(id);
    }
}
