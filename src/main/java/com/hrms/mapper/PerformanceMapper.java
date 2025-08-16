package com.hrms.mapper;

import com.hrms.entity.Performance;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;

/**
 * <p>
 * 存储员工绩效信息 Mapper 接口
 * </p>
 *
 * @author tra
 * @since 2024-12-14
 */
public interface PerformanceMapper extends BaseMapper<Performance> {
    @Insert("INSERT INTO performance (employee_id, score, remark, create_time, update_time, review_date) " +
            "VALUES (#{employeeId}, #{score}, #{remark}, #{createTime}, #{updateTime}, #{reviewDate})")
    int insertPerformance(Performance performance);
}
