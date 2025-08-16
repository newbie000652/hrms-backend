package com.hrms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 存储员工账户信息
 * </p>
 *
 * @author tra
 * @since 2024-12-14
 */
@Getter
@Setter
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键，自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 账号
     */
    private String account;

    /**
     * 员工ID，关联employee表
     */
    private Long employeeId;

    /**
     * 密码
     */
    private String password;

    /**
     * 权限（员工、领导、管理员）
     */
    private String role;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 更新时间
     */
    private Timestamp updateTime;
}
