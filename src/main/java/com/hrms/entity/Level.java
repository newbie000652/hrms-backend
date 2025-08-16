package com.hrms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 存储员工级别和对应的基本工资
 * </p>
 *
 * @author tra
 * @since 2024-12-14
 */
@Getter
@Setter
public class Level implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键，级别编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Byte id;

    /**
     * 级别名称（如试用员工、总经理）
     */
    private String name;

    /**
     * 基本工资
     */
    private BigDecimal baseSalary;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 更新时间
     */
    private Timestamp updateTime;
}
