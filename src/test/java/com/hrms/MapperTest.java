package com.hrms;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hrms.entity.Level;
import com.hrms.mapper.LevelMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MapperTest {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    private LevelMapper levelMapper;

    @BeforeAll
    void setUp() {
        Assertions.assertNotNull(sqlSessionFactory, "SqlSessionFactory 注入失败！");
        Assertions.assertNotNull(levelMapper, "LevelMapper 未正确注入！");
    }

    @Test
    @DisplayName("测试手动获取 LevelMapper")
    public void testManualMapper() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            LevelMapper manualMapper = sqlSession.getMapper(LevelMapper.class);
            printLevels("ManualMapper", manualMapper.selectList(new QueryWrapper<>()));
        }
    }

    @Test
    @DisplayName("测试 MyBatis-Plus 基础查询")
    void testMyBatisPlus() {
        List<Level> levels = levelMapper.selectList(null);
        Assertions.assertNotNull(levels, "Level 表数据未查询到");
        printLevels("MyBatis-Plus 查询结果", levels);
    }

    @Test
    @DisplayName("测试 LevelMapper 查询所有")
    void testLevelMapper() {
        List<Level> levels = levelMapper.selectList(null);
        Assertions.assertNotNull(levels, "Level 表数据未查询到");
        printLevels("LevelMapper 查询结果", levels);
    }

    @Test
    @DisplayName("测试 selectList 方法查询")
    void testSelectList() {
        List<Level> levels = levelMapper.selectList(null);
        Assertions.assertNotNull(levels, "Level 表数据未查询到");
        printLevels("SelectList 查询结果", levels);
    }

    @Test
    @DisplayName("测试通过名称查询 Level 表")
    public void testSelectByName() {
        String name = "试用员工";
        List<Level> levels = levelMapper.selectByName(name);
        Assertions.assertNotNull(levels, "按名称查询未返回数据");
        printLevels("按名称查询结果: " + name, levels);
    }

    /**
     * 工具方法：打印 Level 列表
     */
    private void printLevels(String title, List<Level> levels) {
        System.out.println("===== " + title + " =====");
        levels.forEach(System.out::println);
    }
}
