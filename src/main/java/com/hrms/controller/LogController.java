package com.hrms.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hrms.entity.Log;
import com.hrms.service.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 记录系统操作日志 前端控制器
 * </p>
 *
 * @author tra
 * @since 2024-12-14
 */
@RestController
@RequestMapping("/logs")
public class LogController {
    @Autowired
    private ILogService logService;

    /**
     * 获取日志（支持分页）
     *
     * @param page 页码，默认 1
     * @param size 每页数量，默认 10
     * @return 分页日志数据
     */
    @GetMapping("/api/logs")
    public IPage<Log> getLogs(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return logService.getLogs(page, size);
    }
}
