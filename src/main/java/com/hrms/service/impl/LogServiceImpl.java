package com.hrms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hrms.entity.Log;
import com.hrms.mapper.LogMapper;
import com.hrms.service.ILogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Page<Log> logPage = new Page<>(page, size);
        
        // 手动计算总数
        Long total = baseMapper.selectCount(null);
        logPage.setTotal(total);
        logPage.setPages((long) Math.ceil((double) total / size));

        // 手动应用LIMIT和OFFSET
        QueryWrapper<Log> queryWrapper = new QueryWrapper<>();
        long offset = (page - 1) * size;
        queryWrapper.last("LIMIT " + size + " OFFSET " + offset);

        List<Log> records = baseMapper.selectList(queryWrapper);
        logPage.setRecords(records);
        
        return logPage;
    }
}
