package com.hrms.controller;

import com.hrms.entity.Account;
import com.hrms.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/")
public class AuthController {

    @Autowired
    private IAccountService accountService;

    /**
     * 登录验证
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        Account result = accountService.login(username, password);
        if (result != null) {
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "token", "some-token",  // 可以保持这个，或者生成真实的 token
                    "role", result.getRole(),
                    "employeeId", result.getEmployeeId()  // 返回 employeeId
            ));
        }
        return ResponseEntity.status(401).body(Map.of(
                "success", false,
                "message", "登录失败，账号或密码错误"
        ));
    }


}
