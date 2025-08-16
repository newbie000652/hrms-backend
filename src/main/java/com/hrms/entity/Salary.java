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
 * 存储员工工资信息
 * </p>
 *
 * @author tra
 * @since 2024-12-14
 */
@Getter
@Setter
public class Salary implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键，自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 员工ID，关联employee表
     */
    private Long employeeId;

    /**
     * 基本工资
     */
    private BigDecimal baseSalary;

    /**
     * 奖金
     */
    private BigDecimal bonus;

    /**
     * 罚款金额
     */
    private BigDecimal penalty;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 更新时间
     */
    private Timestamp updateTime;
}
