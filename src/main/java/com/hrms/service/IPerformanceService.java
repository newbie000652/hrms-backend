package com.hrms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hrms.entity.Performance;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 存储员工绩效信息 服务类
 * </p>
 *
 * @author tra
 * @since 2024-12-14
 */
public interface IPerformanceService extends IService<Performance> {
    /**
     * 分页获取绩效记录，支持条件搜索
     */
    IPage<Performance> getPerformancePage(Page<Performance> page, String searchBy, String keyword);

    /**
     * 修改指定员工的绩效记录
     */
    boolean updatePerformanceRecord(Long id, Byte score, String remark);

    /**
     * 获取指定员工的最新绩效记录
     *
     * @param employeeId 员工ID
     * @return 最新绩效记录
     */
    Performance getLatestPerformance(Long employeeId);

    /**
     * 创建新的绩效记录
     * @param performance 绩效信息对象
     * @return 是否创建成功
     */
    boolean createPerformanceRecord(Performance performance);

}
