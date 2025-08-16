package com.hrms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hrms.entity.Account;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 存储员工账户信息 服务类
 * </p>
 *
 * @author tra
 * @since 2024-12-14
 */
public interface IAccountService extends IService<Account> {
    /**
     * 登录验证
     */
    Account login(String account, String password);

    /**
     * 分页获取账户列表
     */
    IPage<Account> getAccountPage(Page<Account> page, String accountId);

    /**
     * 修改账户信息
     */
    boolean updateAccountInfo(Long id, String password, String role);

    /**
     * 删除账户
     */
    boolean deleteAccount(Long id);

    /**
     * 根据 ID 查询账户信息
     */
    Account getAccountById(Long id);

    /**
     * 添加新账户
     *
     * @param account 账户信息
     * @return 是否添加成功
     */
    boolean addAccount(Account account);

}
