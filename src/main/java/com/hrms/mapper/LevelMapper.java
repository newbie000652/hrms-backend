package com.hrms.mapper;

import com.hrms.entity.Level;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 存储员工级别和对应的基本工资 Mapper 接口
 * </p>
 *
 * @author tra
 * @since 2024-12-14
 */
public interface LevelMapper extends BaseMapper<Level> {
    List<Level> selectByName(@Param("name") String name);
}
