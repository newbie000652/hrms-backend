package com.hrms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hrms.entity.Attendance;
import com.hrms.mapper.AttendanceMapper;
import com.hrms.service.IAttendanceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * <p>
 * 记录员工考勤情况 服务实现类
 * </p>
 *
 * @author tra
 * @since 2024-12-14
 */
@Service
public class AttendanceServiceImpl extends ServiceImpl<AttendanceMapper, Attendance> implements IAttendanceService {
    @Override
    public IPage<Attendance> getAttendanceRecords(Page<Attendance> page, String searchBy, String keyword) {
        QueryWrapper<Attendance> queryWrapper = new QueryWrapper<>();

        if ("id".equalsIgnoreCase(searchBy) && keyword != null) {
            queryWrapper.eq("employee_id", keyword);
        } else if ("name".equalsIgnoreCase(searchBy) && keyword != null) {
            queryWrapper.like("action", keyword); // 假设 "action" 字段包含员工操作描述
        }

        return baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    @Transactional
    public boolean signIn(Long employeeId) {
        // 获取当天的签到记录
        QueryWrapper<Attendance> query = new QueryWrapper<>();
        query.eq("employee_id", employeeId)
                .eq("DATE(time)", LocalDate.now())
                .eq("action", "签到");

        Attendance attendance = this.getOne(query);

        // 判断签到时间
        String status = LocalTime.now().isBefore(LocalTime.of(9, 0)) ? "正常" : "迟到";

        if (attendance != null) {
            // 覆盖签到
            attendance.setTime(Timestamp.valueOf(LocalDateTime.now()));
            attendance.setStatus(status);
            attendance.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            return this.updateById(attendance);
        } else {
            // 新增签到
            Attendance newRecord = new Attendance();
            newRecord.setEmployeeId(employeeId);
            newRecord.setTime(Timestamp.valueOf(LocalDateTime.now()));
            newRecord.setAction("签到");
            newRecord.setStatus(status);
            newRecord.setCreateTime(new Timestamp(System.currentTimeMillis()));
            newRecord.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            return this.save(newRecord);
        }
    }

    @Override
    public boolean signOut(Long employeeId) {
        Attendance signOutRecord = new Attendance();
        signOutRecord.setEmployeeId(employeeId);
        signOutRecord.setTime(Timestamp.valueOf(LocalDateTime.now()));
        signOutRecord.setAction("签离");
        signOutRecord.setStatus("正常");
        signOutRecord.setCreateTime(new Timestamp(System.currentTimeMillis()));
        signOutRecord.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return this.save(signOutRecord);
    }

    @Override
    public boolean requestLeave(Long employeeId) {
        Attendance leaveRecord = new Attendance();
        leaveRecord.setEmployeeId(employeeId);
        leaveRecord.setTime(Timestamp.valueOf(LocalDateTime.now()));
        leaveRecord.setAction("请假");
        leaveRecord.setStatus("请假待审批");
        leaveRecord.setCreateTime(new Timestamp(System.currentTimeMillis()));
        leaveRecord.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return this.save(leaveRecord);
    }

    @Override
    public boolean approveLeave(Long id) {
        Attendance leaveRecord = this.getById(id);
        if (leaveRecord != null && "请假待审批".equals(leaveRecord.getStatus())) {
            leaveRecord.setStatus("请假已审批");
            leaveRecord.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            return this.updateById(leaveRecord);
        }
        return false;
    }

    @Override
    public List<Attendance> getPendingLeaveRequests() {
        QueryWrapper<Attendance> query = new QueryWrapper<>();
        query.eq("status", "请假待审批");
        return this.list(query);
    }

    @Override
    public List<Attendance> getAttendanceByMonth(Long employeeId) {
        // 获取当前月份的开始和结束时间
        LocalDate now = LocalDate.now();
        LocalDate firstDay = now.withDayOfMonth(1);
        LocalDate lastDay = now.withDayOfMonth(now.lengthOfMonth());

        QueryWrapper<Attendance> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("employee_id", employeeId)
                .between("time", firstDay.atStartOfDay(), lastDay.atTime(23, 59, 59));
        return this.list(queryWrapper);
    }

}
