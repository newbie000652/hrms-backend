package com.hrms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hrms.dto.PersonalReportDTO;
import com.hrms.entity.Salary;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 存储员工工资信息 服务类
 * </p>
 *
 * @author tra
 * @since 2024-12-14
 */
public interface ISalaryService extends IService<Salary> {
    /**
     * 分页获取工资记录（支持模糊搜索）
     */
    IPage<Salary> getSalariesByCondition(int page, int size, String searchBy, String keyword);

    /**
     * 计算并保存某员工工资
     */
    Salary calculateAndSaveSalary(Long employeeId);

    /**
     * 为指定员工创建工资记录（初始化工资表）
     */
    Salary createDefaultSalaryRecord(Long employeeId);

    /**
     * 获取指定员工的工资报表
     */
    List<PersonalReportDTO> getSalaryReportByEmployeeId(Long employeeId);

    /**
     * 通过员工id计算并更新工资记录
     */
    Salary updateSalaryByEmployeeId(Long employeeId);
}
