package com.hrms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 存储员工基本信息
 * </p>
 *
 * @author tra
 * @since 2024-12-14
 */
@Getter
@Setter
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键，自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String gender;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话
     */
    private String phone;

    /**
     * 入职时间
     */
    private Timestamp entryDate;

    /**
     * 员工级别，关联level表
     */
    private Byte levelId;

    /**
     * 所在部门ID，关联department表（总经理为空）
     */
    private Long departmentId;

    /**
     * 是否在职（1是，0否）
     */
    private Byte isActive;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 更新时间
     */
    private Timestamp updateTime;
}
