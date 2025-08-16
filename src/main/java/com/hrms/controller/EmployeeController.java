package com.hrms.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hrms.entity.Employee;
import com.hrms.dto.EmployeeDetailsDTO;
import com.hrms.service.IEmployeeService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 存储员工基本信息 前端控制器
 * </p>
 *
 * @author tra
 * @since 2024-12-14
 */
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    /**
     * 查询所有在职员工
     */
    @GetMapping("/active")
    public ResponseEntity<?> getActiveEmployees() {
        List<Employee> employees = employeeService.getActiveEmployees();
        return ResponseEntity.ok(Map.of(
                "success", true,
                "data", employees
        ));
    }

    /**
     * 根据条件分页查询在职员工
     */
    @GetMapping("/active/search")
    public ResponseEntity<?> searchActiveEmployees(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Long id,          // 员工编号
            @RequestParam(required = false) String name,      // 员工姓名
            @Parameter(description = "员工级别", required = false, example = "1") Byte levelId,
            @RequestParam(required = false) Long departmentId // 部门ID
    ) {
        IPage<Employee> employeePage = employeeService.getActiveEmployeesByCondition(
                new Page<>(page, size), id, name, levelId, departmentId);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "data", employeePage
        ));
    }


    /**
     * 查询所有离职员工
     */
    @GetMapping("/inactive")
    public ResponseEntity<?> getInactiveEmployees() {
        List<Employee> employees = employeeService.getInactiveEmployees();
        return ResponseEntity.ok(Map.of(
                "success", true,
                "data", employees
        ));
    }

    /**
     * 根据条件分页查询离职员工
     */
    @GetMapping("/inactive/search")
    public ResponseEntity<?> searchInactiveEmployees(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Byte levelId,
            @RequestParam(required = false) Long departmentId) {

        IPage<Employee> employeePage = employeeService.getInactiveEmployeesByCondition(
                new Page<>(page, size), id, name, levelId, departmentId);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "data", employeePage
        ));
    }

    /**
     * 新增员工，同时初始化工资表和绩效表
     */
    @PostMapping
    public ResponseEntity<?> addEmployee(@RequestBody Employee employee) {
        try {
            employee.setIsActive((byte) 1); // 默认设置为在职
            boolean result = employeeService.createEmployeeAndInitialize(employee);
            if (result) {
                return ResponseEntity.ok(Map.of(
                        "success", true,
                        "message", "新增员工成功，已初始化相关信息"
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
        return ResponseEntity.status(400).body(Map.of(
                "success", false,
                "message", "新增员工失败"
        ));
    }

    /**
     * 将员工标记为离职
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> markEmployeeAsInactive(@PathVariable Long id) {
        boolean result = employeeService.markEmployeeAsInactive(id);
        if (result) {
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "员工已标记为离职"
            ));
        }
        return ResponseEntity.status(400).body(Map.of(
                "success", false,
                "message", "操作失败，员工不存在"
        ));
    }

    /**
     * 根据员工ID查询详细信息
     */
    @GetMapping("/details/{id}")
    public ResponseEntity<?> getEmployeeDetails(@PathVariable Long id) {
        try {
            EmployeeDetailsDTO details = employeeService.getEmployeeDetailsById(id);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "data", details
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }

    /**
     * 更新员工信息
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(
            @PathVariable Long id,
            @RequestBody Employee employee) {
        try {
            employee.setId(id); // 确保 ID 一致
            boolean result = employeeService.updateEmployeeById(employee);
            if (result) {
                return ResponseEntity.ok(Map.of(
                        "success", true,
                        "message", "员工信息更新成功"
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
        return ResponseEntity.status(400).body(Map.of(
                "success", false,
                "message", "更新员工信息失败"
        ));
    }

}
