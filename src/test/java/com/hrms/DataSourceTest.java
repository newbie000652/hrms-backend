package com.hrms;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
@SpringBootTest
public class DataSourceTest {

    @Autowired
    private DataSource dataSource;

    @Test
    void testConnection() throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            assert connection != null : "数据库连接失败！";
            System.out.println("数据库连接成功: " + connection.getCatalog());
        }
    }
}

