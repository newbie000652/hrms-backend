package com.hrms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hrms.entity.Log;
import com.hrms.mapper.LogMapper;
import com.hrms.service.ILogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 记录系统操作日志 服务实现类
 * </p>
 *
 * @author tra
 * @since 2024-12-14
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements ILogService {
    @Override
    public IPage<Log> getLogs(int page, int size) {
        // 使用 MyBatis-Plus 提供的分页查询
        Page<Log> logPage = new Page<>(page, size);
        return this.page(logPage);
    }
}
