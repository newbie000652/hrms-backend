package com.hrms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hrms.entity.Log;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 记录系统操作日志 服务类
 * </p>
 *
 * @author tra
 * @since 2024-12-14
 */
public interface ILogService extends IService<Log> {
    /**
     * 分页获取日志
     *
     * @param page 页码
     * @param size 每页大小
     * @return 分页日志数据
     */
    IPage<Log> getLogs(int page, int size);
}
