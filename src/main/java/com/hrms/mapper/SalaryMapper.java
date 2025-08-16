package com.hrms.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hrms.dto.PersonalReportDTO;
import com.hrms.entity.Salary;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 存储员工工资信息 Mapper 接口
 * </p>
 *
 * @author tra
 * @since 2024-12-14
 */
public interface SalaryMapper extends BaseMapper<Salary> {
    /**
     * 分页获取工资记录，支持模糊搜索
     */
    IPage<Salary> getSalariesByCondition(
            IPage<Salary> page,
            @Param("searchBy") String searchBy,
            @Param("keyword") String keyword
    );

    /**
     * 根据员工ID获取个人工资信息
     */
    List<PersonalReportDTO> getSalaryReportByEmployeeId(@Param("employeeId") Long employeeId);

}
