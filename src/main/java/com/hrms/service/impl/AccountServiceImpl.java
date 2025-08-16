package com.hrms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hrms.entity.Account;
import com.hrms.mapper.AccountMapper;
import com.hrms.service.IAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 存储员工账户信息 服务实现类
 * </p>
 *
 * @author tra
 * @since 2024-12-14
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements IAccountService {
    @Override
    public Account login(String account, String password) {
        // 通过账号和密码查询账户
        return baseMapper.selectOne(
                new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<Account>()
                        .eq("account", account)
                        .eq("password", password)
        );
    }

    @Override
    public IPage<Account> getAccountPage(Page<Account> page, String accountId) {
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        
        if (accountId != null && !accountId.trim().isEmpty()) {
            queryWrapper.eq("id", accountId);
        }
        
        return baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public boolean updateAccountInfo(Long id, String password, String role) {
        return baseMapper.updateAccountInfo(id, password, role) > 0;
    }

    @Override
    public boolean deleteAccount(Long id) {
        return baseMapper.deleteById(id) > 0;
    }

    @Override
    public Account getAccountById(Long id) {
        return baseMapper.selectById(id); // BaseMapper 提供的默认方法
    }

    @Override
    public boolean addAccount(Account account) {
        return baseMapper.insert(account) > 0;
    }

}
