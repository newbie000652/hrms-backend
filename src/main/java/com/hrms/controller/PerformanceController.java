package com.hrms.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hrms.entity.Performance;
import com.hrms.service.IPerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 存储员工绩效信息 前端控制器
 * </p>
 *
 * @author tra
 * @since 2024-12-14
 */
@RestController
@RequestMapping("/api/performance")
public class PerformanceController {
    @Autowired
    private IPerformanceService performanceService;

    /**
     * 获取绩效记录（分页 + 搜索）
     */
    @GetMapping
    public IPage<Performance> getPerformanceRecords(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String searchBy,
            @RequestParam(required = false) String keyword) {

        Page<Performance> pageObj = new Page<>(page, size);
        return performanceService.getPerformancePage(pageObj, searchBy, keyword);
    }

    /**
     * 修改指定员工的绩效记录
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updatePerformanceRecord(@PathVariable Long id,
                                                          @RequestParam Byte score,
                                                          @RequestParam(required = false) String remark) {
        boolean result = performanceService.updatePerformanceRecord(id, score, remark);
        return result ? ResponseEntity.ok("绩效记录修改成功")
                : ResponseEntity.status(404).body("修改失败，指定记录不存在");
    }

    /**
     * 创建新的绩效记录
     */
    @PostMapping("/create")
    public String createPerformanceRecord(@RequestBody Performance performance) {
        boolean result = performanceService.createPerformanceRecord(performance);
        return result ? "绩效记录创建成功" : "绩效记录创建失败";
    }
}
