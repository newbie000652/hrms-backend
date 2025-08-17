package com.hrms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hrms.dto.PersonalReportDTO;
import com.hrms.entity.Attendance;
import com.hrms.entity.Employee;
import com.hrms.entity.Performance;
import com.hrms.entity.Salary;
import com.hrms.mapper.SalaryMapper;
import com.hrms.service.IAttendanceService;
import com.hrms.service.IEmployeeService;
import com.hrms.service.IPerformanceService;
import com.hrms.service.ISalaryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 存储员工工资信息 服务实现类
 * </p>
 *
 * @author tra
 * @since 2024-12-14
 */
@Service
public class SalaryServiceImpl extends ServiceImpl<SalaryMapper, Salary> implements ISalaryService {
    @Autowired
    @Lazy
    private IEmployeeService employeeService;

    @Autowired
    private IPerformanceService performanceService;

    @Autowired
    private IAttendanceService attendanceService;

    @Autowired
    private SalaryMapper salaryMapper;

    /**
     * 分页获取工资记录
     */
    @Override
    public IPage<Salary> getSalariesByCondition(int page, int size, String searchBy, String keyword) {
        Page<Salary> salaryPage = new Page<>(page, size);
        
        QueryWrapper<Salary> queryWrapper = new QueryWrapper<>();
        
        // 添加调试日志
        System.out.println("SalaryServiceImpl.getSalariesByCondition - page: " + page + ", size: " + size);
        System.out.println("SalaryServiceImpl.getSalariesByCondition - searchBy: " + searchBy + ", keyword: " + keyword);
        
        // 暂时简化查询逻辑，先确保分页能工作
        if (searchBy != null && keyword != null && !keyword.trim().isEmpty()) {
            if ("id".equals(searchBy)) {
                queryWrapper.eq("employee_id", keyword);
            } else if ("name".equals(searchBy)) {
                // 通过员工姓名搜索，需要关联查询
                queryWrapper.inSql("employee_id", 
                    "SELECT id FROM employee WHERE name LIKE '%" + keyword + "%'");
            }
        }
        
        // 手动计算总数
        Long total = baseMapper.selectCount(queryWrapper);
        System.out.println("SalaryServiceImpl.getSalariesByCondition - total records: " + total);
        
        // 手动应用LIMIT和OFFSET
        long offset = (page - 1) * size;
        queryWrapper.last("LIMIT " + size + " OFFSET " + offset);

        List<Salary> records = baseMapper.selectList(queryWrapper);
        System.out.println("SalaryServiceImpl.getSalariesByCondition - paginated records: " + records.size());
        
        // 设置分页信息
        salaryPage.setTotal(total);
        salaryPage.setPages((long) Math.ceil((double) total / size));
        salaryPage.setRecords(records);
        
        return salaryPage;
    }

    /**
     * 计算并保存某员工的工资
     */
    @Override
    public Salary calculateAndSaveSalary(Long employeeId) {
        Employee employee = employeeService.getById(employeeId);
        if (employee == null) {
            throw new IllegalArgumentException("员工不存在！");
        }

        // 基本工资
        BigDecimal baseSalary = employeeService.getBaseSalaryByLevel(employee.getLevelId());

        // 绩效奖金
        Performance performance = performanceService.getLatestPerformance(employeeId);
        BigDecimal bonus = performance != null
                ? baseSalary.multiply(BigDecimal.valueOf(performance.getScore())).divide(BigDecimal.TEN)
                : BigDecimal.ZERO;

        // 考勤扣款
        List<Attendance> attendances = attendanceService.getAttendanceByMonth(employeeId);
        long normalSignIn = attendances.stream().filter(a -> "正常".equals(a.getStatus())).count();
        long approvedLeave = attendances.stream().filter(a -> "已审批".equals(a.getStatus())).count();
        long late = attendances.stream().filter(a -> "迟到".equals(a.getStatus())).count();
        long abnormalDays = 20 - (normalSignIn + approvedLeave);

        BigDecimal penalty = baseSalary.multiply(BigDecimal.valueOf(abnormalDays)).divide(BigDecimal.valueOf(20));

        Salary salary = new Salary();
        salary.setEmployeeId(employeeId);
        salary.setBaseSalary(baseSalary);
        salary.setBonus(bonus);
        salary.setPenalty(penalty);
        salary.setCreateTime(new Timestamp(System.currentTimeMillis()));
        salary.setUpdateTime(new Timestamp(System.currentTimeMillis()));

        this.save(salary);
        return salary;
    }

    @Override
    public Salary createDefaultSalaryRecord(Long employeeId) {
        // 检查员工是否存在
        Employee employee = employeeService.getById(employeeId);
        if (employee == null) {
            throw new IllegalArgumentException("指定的员工不存在！");
        }

        // 获取基本工资信息
        BigDecimal baseSalary = employeeService.getBaseSalaryByLevel(employee.getLevelId());
        if (baseSalary == null) {
            throw new IllegalArgumentException("未找到对应级别的基本工资！");
        }

        // 构建默认工资记录
        Salary salary = new Salary();
        salary.setEmployeeId(employeeId);
        salary.setBaseSalary(baseSalary);
        salary.setBonus(BigDecimal.ZERO); // 初始奖金设为 0
        salary.setPenalty(BigDecimal.ZERO); // 初始罚款设为 0
        salary.setCreateTime(new Timestamp(System.currentTimeMillis()));
        salary.setUpdateTime(new Timestamp(System.currentTimeMillis()));

        // 保存工资记录
        this.save(salary);
        return salary;
    }

    @Override
    public List<PersonalReportDTO> getSalaryReportByEmployeeId(Long employeeId) {
        // 检查员工是否存在
        Employee employee = employeeService.getById(employeeId);
        if (employee == null) {
            throw new IllegalArgumentException("员工不存在！");
        }

        // 获取工资报表
        return salaryMapper.getSalaryReportByEmployeeId(employeeId);
    }

    @Override
    public Salary updateSalaryByEmployeeId(Long employeeId) {
        // 检查员工是否存在
        Employee employee = employeeService.getById(employeeId);
        if (employee == null) {
            throw new IllegalArgumentException("员工不存在！");
        }

        // 获取基本工资
        BigDecimal baseSalary = employeeService.getBaseSalaryByLevel(employee.getLevelId());
        if (baseSalary == null) {
            throw new IllegalArgumentException("未找到对应级别的基本工资！");
        }

        // 计算绩效奖金
        Performance performance = performanceService.getLatestPerformance(employeeId);
        BigDecimal bonus = performance != null
                ? baseSalary.multiply(BigDecimal.valueOf(performance.getScore())).divide(BigDecimal.TEN)
                : BigDecimal.ZERO;

        // 计算考勤扣款
        List<Attendance> attendances = attendanceService.getAttendanceByMonth(employeeId);
        long normalSignIn = attendances.stream().filter(a -> "正常".equals(a.getStatus())).count();
        long approvedLeave = attendances.stream().filter(a -> "已审批".equals(a.getStatus())).count();
        long late = attendances.stream().filter(a -> "迟到".equals(a.getStatus())).count();
        long abnormalDays = 20 - (normalSignIn + approvedLeave);

        BigDecimal penalty = baseSalary.multiply(BigDecimal.valueOf(abnormalDays)).divide(BigDecimal.valueOf(20));

        // 查找现有工资记录
        Salary salary = this.getOne(new QueryWrapper<Salary>().eq("employee_id", employeeId));
        if (salary == null) {
            // 如果没有记录，创建新的记录
            salary = new Salary();
            salary.setEmployeeId(employeeId);
            salary.setCreateTime(new Timestamp(System.currentTimeMillis()));
        }

        // 更新工资信息
        salary.setBaseSalary(baseSalary);
        salary.setBonus(bonus);
        salary.setPenalty(penalty);
        salary.setUpdateTime(new Timestamp(System.currentTimeMillis()));

        // 保存或更新记录
        this.saveOrUpdate(salary);
        return salary;
    }


}
