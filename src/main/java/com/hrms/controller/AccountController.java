package com.hrms.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hrms.entity.Account;
import com.hrms.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 存储员工账户信息 前端控制器
 * </p>
 *
 * @author tra
 * @since 2024-12-14
 */
@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private IAccountService accountService;

    /**
     * 获取账户分页列表
     */
    @GetMapping
    public IPage<Account> getAccounts(@RequestParam(defaultValue = "1") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(required = false) String accountId) {
        return accountService.getAccountPage(new Page<>(page, size), accountId);
    }

    /**
     * 根据账户ID获取账户信息
     */
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        Account account = accountService.getAccountById(id);
        if (account != null) {
            return ResponseEntity.ok(account);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    /**
     * 修改账户信息（密码或角色）
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateAccount(@PathVariable Long id,
                                                @RequestParam(required = false) String password,
                                                @RequestParam(required = false) String role) {
        boolean result = accountService.updateAccountInfo(id, password, role);
        return result ? ResponseEntity.ok("账户信息修改成功") : ResponseEntity.status(400).body("修改失败，账户不存在");
    }

    /**
     * 删除账户
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
        boolean result = accountService.deleteAccount(id);
        return result ? ResponseEntity.ok("账户删除成功") : ResponseEntity.status(400).body("删除失败，账户不存在");
    }

    /**
     * 添加账户
     */
    @PostMapping
    public ResponseEntity<String> addAccount(@RequestBody Account account) {
        boolean result = accountService.addAccount(account);
        return result ? ResponseEntity.ok("账户添加成功") : ResponseEntity.status(400).body("账户添加失败");
    }

}
