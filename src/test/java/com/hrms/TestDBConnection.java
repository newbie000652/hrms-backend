package com.hrms;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestDBConnection {
    public static void main(String[] args) {
        try {
            String url = "jdbc:mysql://localhost:3306/hrms";
            String user = "root";
            String password = "traversal";
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("数据库连接成功！");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

