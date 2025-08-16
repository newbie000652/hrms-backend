package com.hrms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 记录员工技能的多对多关系
 * </p>
 *
 * @author tra
 * @since 2024-12-14
 */
@Getter
@Setter
@TableName("employee_skill")
public class EmployeeSkill implements Serializable {

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
     * 技能ID，关联skill表
     */
    private Long skillId;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 更新时间
     */
    private Timestamp updateTime;
}
