package com.hrms;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
public class SpringContextTest {

    @Autowired
    private ApplicationContext context;

    @Test
    void testContextLoads() {
        // 验证 context 是否加载了 LevelMapper Bean
        boolean containsMapper = context.containsBean("levelMapper");
        assert containsMapper : "LevelMapper 未正确加载到 Spring 容器中！";
        System.out.println("Spring 容器加载成功！");
    }
}

