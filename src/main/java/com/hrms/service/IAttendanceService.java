package com.hrms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hrms.entity.Attendance;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 记录员工考勤情况 服务类
 * </p>
 *
 * @author tra
 * @since 2024-12-14
 */
public interface IAttendanceService extends IService<Attendance> {
    /**
     * 分页获取考勤记录，支持条件搜索
     */
    IPage<Attendance> getAttendanceRecords(Page<Attendance> page, Long employeeId, String date);

    /**
     * 签到操作
     */
    boolean signIn(Long employeeId);

    /**
     * 签离操作
     */
    boolean signOut(Long employeeId);

    /**
     * 请假操作
     */
    boolean requestLeave(Long employeeId);

    /**
     * 请假审批操作
     */
    boolean approveLeave(Long id);

    /**
     * 获取所有待审批的请假记录
     */
    IPage<Attendance> getPendingLeaveRequests(Page<Attendance> page);

    /**
     * 获取指定员工当月的考勤记录
     *
     * @param employeeId 员工ID
     * @return 当月考勤记录列表
     */
    List<Attendance> getAttendanceByMonth(Long employeeId);

}
