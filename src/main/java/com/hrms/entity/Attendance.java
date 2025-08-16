package com.hrms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 记录员工考勤情况
 * </p>
 *
 * @author tra
 * @since 2024-12-14
 */
@Getter
@Setter
public class Attendance implements Serializable {

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
     * 时间
     */
    private Timestamp time;

    /**
     * 操作
     */
    private String action;

    /**
     * 状态
     */
    private String status;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 更新时间
     */
    private Timestamp updateTime;
}
