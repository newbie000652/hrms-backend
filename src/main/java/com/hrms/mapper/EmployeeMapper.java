package com.hrms.mapper;

import com.hrms.entity.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hrms.dto.EmployeeDetailsDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 * 存储员工基本信息 Mapper 接口
 * </p>
 *
 * @author tra
 * @since 2024-12-14
 */
public interface EmployeeMapper extends BaseMapper<Employee> {
    /**
     * 查询所有在职员工
     */
    List<Employee> selectActiveEmployees();

    /**
     * 查询所有离职员工
     */
    List<Employee> selectInactiveEmployees();

    /**
     * 将员工设为离职状态 (is_active = 0)
     */
    @Update("UPDATE employee SET is_active = 0 WHERE id = #{id}")
    int markEmployeeAsInactive(@Param("id") Long id);

    /**
     * 根据员工编号查询在职员工
     */
    Employee selectActiveEmployeeById(@Param("id") Long id);

    /**
     * 根据姓名查询在职员工
     */
    List<Employee> selectActiveEmployeesByName(@Param("name") String name);

    /**
     * 根据级别查询在职员工
     */
    List<Employee> selectActiveEmployeesByLevel(@Param("levelId") Byte levelId);

    /**
     * 根据部门查询在职员工
     */
    List<Employee> selectActiveEmployeesByDepartment(@Param("departmentId") Long departmentId);

    /**
     * 根据员工编号查询离职员工
     */
    Employee selectInactiveEmployeeById(@Param("id") Long id);

    /**
     * 根据姓名查询离职员工
     */
    List<Employee> selectInactiveEmployeesByName(@Param("name") String name);

    /**
     * 根据级别查询离职员工
     */
    List<Employee> selectInactiveEmployeesByLevel(@Param("levelId") Byte levelId);

    /**
     * 根据部门查询离职员工
     */
    List<Employee> selectInactiveEmployeesByDepartment(@Param("departmentId") Long departmentId);

    /**
     * 查询员工详细信息，包括员工基本信息、部门、技能和级别
     * @param id 员工ID
     * @return 员工详细信息DTO
     */
    EmployeeDetailsDTO selectEmployeeDetailsById(@Param("id") Long id);

}
