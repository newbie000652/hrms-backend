package com.hrms;

import com.hrms.mapper.LevelMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
public class MapperInjectionTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void testLevelMapperBean() {
        LevelMapper levelMapper = (LevelMapper) applicationContext.getBean("levelMapper");
        assert levelMapper != null : "LevelMapper 无法通过 ApplicationContext 获取！";
        System.out.println("LevelMapper 成功加载！");
    }
}

