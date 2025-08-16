package com.hrms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hrms.entity.Performance;
import com.hrms.mapper.PerformanceMapper;
import com.hrms.service.IPerformanceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hrms.service.ISalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

/**
 * <p>
 * 存储员工绩效信息 服务实现类
 * </p>
 *
 * @author tra
 * @since 2024-12-14
 */
@Service
public class PerformanceServiceImpl extends ServiceImpl<PerformanceMapper, Performance> implements IPerformanceService {
    @Autowired
    @Lazy
    private ISalaryService salaryService;

    @Override
    public IPage<Performance> getPerformancePage(Page<Performance> page, String searchBy, String keyword) {
        QueryWrapper<Performance> queryWrapper = new QueryWrapper<>();

        if ("id".equalsIgnoreCase(searchBy) && keyword != null) {
            queryWrapper.eq("employee_id", keyword);
        } else if ("name".equalsIgnoreCase(searchBy) && keyword != null) {
            queryWrapper.like("remark", keyword); // 假设备注中可以搜索名字信息
        }

        return baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    @Transactional
    public boolean updatePerformanceRecord(Long id, Byte score, String remark) {
        Performance performance = baseMapper.selectById(id);
        if (performance == null) {
            return false;
        }

        // 修改绩效记录
        performance.setScore(score);
        performance.setRemark(remark);
        performance.setUpdateTime(new java.sql.Timestamp(System.currentTimeMillis()));

        // 更新绩效记录
        boolean updated = baseMapper.updateById(performance) > 0;

        // 如果绩效更新成功，更新对应员工的工资记录
        if (updated) {
            salaryService.updateSalaryByEmployeeId(performance.getEmployeeId());
        }

        return updated;
    }

    @Override
    public Performance getLatestPerformance(Long employeeId) {
        QueryWrapper<Performance> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("employee_id", employeeId)
                .orderByDesc("review_date")
                .last("LIMIT 1");
        return this.getOne(queryWrapper);
    }

    @Override
    public boolean createPerformanceRecord(Performance performance) {
        if (performance == null || performance.getEmployeeId() == null) {
            throw new IllegalArgumentException("绩效记录或员工ID不能为空！");
        }

        // 设置创建时间和更新时间
        Timestamp now = new Timestamp(System.currentTimeMillis());
        performance.setCreateTime(now);
        performance.setUpdateTime(now);
        if (performance.getReviewDate() == null) {
            performance.setReviewDate(now);
        }
        // 保存绩效记录
        return save(performance);
    }

}
