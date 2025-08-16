package com.hrms;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
public class MyBatisPlusConfigurationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void testMapperBeanLoaded() {
        String[] mapperBeans = applicationContext.getBeanNamesForType(BaseMapper.class);
        for (String beanName : mapperBeans) {
            System.out.println("Mapper Bean: " + beanName);
        }
        assert mapperBeans.length > 0 : "没有发现任何 BaseMapper 的实现，MyBatis-Plus 未正确加载！";
    }
}

