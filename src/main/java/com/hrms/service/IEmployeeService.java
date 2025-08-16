package com.hrms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hrms.entity.Employee;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hrms.dto.EmployeeDetailsDTO;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 存储员工基本信息 服务类
 * </p>
 *
 * @author tra
 * @since 2024-12-14
 */
public interface IEmployeeService extends IService<Employee> {
    /**
     * 查询所有在职员工
     */
    List<Employee> getActiveEmployees();

    /**
     * 查询所有离职员工
     */
    List<Employee> getInactiveEmployees();

    /**
     * 根据条件查询在职员工（编号、姓名、级别、部门）
     */
    IPage<Employee> getActiveEmployeesByCondition(Page<Employee> page, Long id, String name, Byte levelId, Long departmentId);

    /**
     * 根据条件查询离职员工（编号、姓名、级别、部门）
     */
    IPage<Employee> getInactiveEmployeesByCondition(Page<Employee> page, Long id, String name, Byte levelId, Long departmentId);

    /**
     * 将员工标记为离职
     */
    boolean markEmployeeAsInactive(Long id);

    /**
     * 获取员工级别对应的基本工资
     *
     * @param levelId 员工级别ID
     * @return 基本工资
     */
    BigDecimal getBaseSalaryByLevel(Byte levelId);

    /**
     * 根据员工ID查询详细信息
     * @param id 员工ID
     * @return 员工详细信息DTO
     */
    EmployeeDetailsDTO getEmployeeDetailsById(Long id);

    /**
     * 创建新员工，并初始化工资表和绩效表
     *
     * @param employee 员工信息
     * @return 是否创建成功
     */
    boolean createEmployeeAndInitialize(Employee employee);

    /**
     * 根据员工ID更新员工信息
     *
     * @param employee 员工信息
     * @return 是否创建成功
     */
    boolean updateEmployeeById(Employee employee);

}
