package com.hrms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hrms.entity.Employee;
import com.hrms.dto.EmployeeDetailsDTO;
import com.hrms.entity.Level;
import com.hrms.entity.Performance;
import com.hrms.mapper.EmployeeMapper;
import com.hrms.mapper.LevelMapper;
import com.hrms.service.IEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hrms.service.IPerformanceService;
import com.hrms.service.ISalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * <p>
 * 存储员工基本信息 服务实现类
 * </p>
 *
 * @author tra
 * @since 2024-12-14
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {
    @Autowired
    private ISalaryService salaryService;

    @Autowired
    private IPerformanceService performanceService;

    @Override
    public List<Employee> getActiveEmployees() {
        return baseMapper.selectActiveEmployees();
    }

    @Override
    public List<Employee> getInactiveEmployees() {
        return baseMapper.selectInactiveEmployees();
    }

    @Override
    public IPage<Employee> getActiveEmployeesByCondition(Page<Employee> page, Long id, String name, Byte levelId, Long departmentId) {
        // 构建查询条件
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<Employee> queryWrapper = 
            new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<Employee>()
                .eq(id != null, "id", id)
                .like(name != null && !name.trim().isEmpty(), "name", name)
                .eq(levelId != null, "level_id", levelId)
                .eq(departmentId != null, "department_id", departmentId)
                .eq("is_active", 1);
        
        // 先查询总记录数
        Long total = baseMapper.selectCount(queryWrapper);
        
        // 设置分页信息
        page.setTotal(total);
        page.setPages((long) Math.ceil((double) total / page.getSize()));
        
        // 手动实现分页查询，确保只返回当前页数据
        long offset = (page.getCurrent() - 1) * page.getSize();
        queryWrapper.last("LIMIT " + page.getSize() + " OFFSET " + offset);
        
        // 查询当前页数据
        List<Employee> records = baseMapper.selectList(queryWrapper);
        
        // 创建分页结果
        page.setRecords(records);
        
        return page;
    }


    @Override
    public IPage<Employee> getInactiveEmployeesByCondition(Page<Employee> page, Long id, String name, Byte levelId, Long departmentId) {
        // 构建查询条件
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<Employee> queryWrapper = 
            new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<Employee>()
                .eq(id != null, "id", id)
                .like(name != null, "name", name)
                .eq(levelId != null, "level_id", levelId)
                .eq(departmentId != null, "department_id", departmentId)
                .eq("is_active", 0);
        
        // 先查询总记录数
        Long total = baseMapper.selectCount(queryWrapper);
        
        // 设置分页信息
        page.setTotal(total);
        page.setPages((long) Math.ceil((double) total / page.getSize()));
        
        // 手动实现分页查询，确保只返回当前页数据
        long offset = (page.getCurrent() - 1) * page.getSize();
        queryWrapper.last("LIMIT " + page.getSize() + " OFFSET " + offset);
        
        // 查询当前页数据
        List<Employee> records = baseMapper.selectList(queryWrapper);
        
        // 创建分页结果
        page.setRecords(records);
        
        return page;
    }

    @Override
    public boolean markEmployeeAsInactive(Long id) {
        return baseMapper.markEmployeeAsInactive(id) > 0;
    }

    @Autowired
    private LevelMapper levelMapper;
    @Override
    public BigDecimal getBaseSalaryByLevel(Byte levelId) {
        Level level = levelMapper.selectById(levelId);
        if (level == null) {
            throw new IllegalArgumentException("级别不存在！");
        }
        return level.getBaseSalary();
    }

    @Override
    public EmployeeDetailsDTO getEmployeeDetailsById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("员工ID不能为空！");
        }
        return baseMapper.selectEmployeeDetailsById(id);
    }

    @Override
    public boolean createEmployeeAndInitialize(Employee employee) {
        if (employee.getEntryDate() == null) {
            employee.setEntryDate(new Timestamp(System.currentTimeMillis())); // 设置默认入职日期
        }
        // 保存员工信息
        boolean saved = this.save(employee);
        if (!saved) {
            throw new RuntimeException("创建员工失败！");
        }

        // 调用 SalaryService 中的已存在方法来初始化工资表
        salaryService.createDefaultSalaryRecord(employee.getId());

        // 初始化绩效表
        Performance performance = new Performance();
        performance.setEmployeeId(employee.getId());
        performance.setScore((byte) 0);
        performance.setRemark("新员工初始绩效");
        performance.setCreateTime(new Timestamp(System.currentTimeMillis()));
        performance.setUpdateTime(new Timestamp(System.currentTimeMillis()));

        boolean performanceCreated = performanceService.createPerformanceRecord(performance);

        if (!performanceCreated) {
            throw new RuntimeException("初始化绩效表失败！");
        }

        return true;
    }

    @Override
    public boolean updateEmployeeById(Employee employee) {
        // 确保更新时包含更新时间戳
        employee.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return baseMapper.updateById(employee) > 0;
    }


}
