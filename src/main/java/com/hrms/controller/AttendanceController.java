package com.hrms.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hrms.entity.Attendance;
import com.hrms.service.IAttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 记录员工考勤情况 前端控制器
 * </p>
 *
 * @author tra
 * @since 2024-12-14
 */
@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {
    @Autowired
    private IAttendanceService attendanceService;

    /**
     * 获取考勤记录
     */
    @GetMapping
    public IPage<Attendance> getAttendanceRecords(@RequestParam(defaultValue = "1") int page,
                                                  @RequestParam(defaultValue = "10") int size,
                                                  @RequestParam(required = false) String searchBy,
                                                  @RequestParam(required = false) String keyword) {
        return attendanceService.getAttendanceRecords(new Page<>(page, size), searchBy, keyword);
    }

    /**
     * 签到操作
     */
    @PostMapping("/signIn")
    public ResponseEntity<String> signIn(@RequestParam Long employeeId) {
        boolean result = attendanceService.signIn(employeeId);
        return result ? ResponseEntity.ok("签到成功") : ResponseEntity.status(400).body("签到失败");
    }

    /**
     * 签离操作
     */
    @PostMapping("/signOut")
    public ResponseEntity<String> signOut(@RequestParam Long employeeId) {
        boolean result = attendanceService.signOut(employeeId);
        return result ? ResponseEntity.ok("签离成功") : ResponseEntity.status(400).body("签离失败");
    }

    /**
     * 请假操作
     */
    @PostMapping("/requestLeave")
    public ResponseEntity<String> requestLeave(@RequestParam Long employeeId) {
        boolean result = attendanceService.requestLeave(employeeId);
        return result ? ResponseEntity.ok("请假申请已提交") : ResponseEntity.status(400).body("请假申请失败");
    }

    /**
     * 请假审批
     */
    @PostMapping("/{id}/approve")
    public ResponseEntity<String> approveLeave(@PathVariable Long id) {
        boolean result = attendanceService.approveLeave(id);
        return result ? ResponseEntity.ok("请假已审批") : ResponseEntity.status(400).body("请假审批失败");
    }

    /**
     * 获取待审批的请假记录
     */
    @GetMapping("/pendingLeave")
    public List<Attendance> getPendingLeaveRequests() {
        return attendanceService.getPendingLeaveRequests();
    }
}
