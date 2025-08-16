package com.hrms.mapper;

import com.hrms.entity.Account;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 存储员工账户信息 Mapper 接口
 * </p>
 *
 * @author tra
 * @since 2024-12-14
 */
public interface AccountMapper extends BaseMapper<Account> {
    /**
     * 修改账户密码或角色
     */
    @Update("UPDATE account SET password = #{password}, role = #{role}, update_time = NOW() WHERE id = #{id}")
    int updateAccountInfo(@Param("id") Long id, @Param("password") String password, @Param("role") String role);
}
