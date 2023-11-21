package com.wx.manage.until;

import com.baomidou.mybatisplus.annotation.DbType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @Description JDBC工具类
 * @Date 2023/11/8 15:51 星期三
 * @Author Hu Wentao
 */
public class JdbcUtils {

    /**
     * 判断连接是否正确
     *
     * @param url      数据源连接
     * @param username 账号
     * @param password 密码
     * @return 是否正确
     */
    public static boolean isConnectionOK(String url, String username, String password) {
        try (Connection ignored = DriverManager.getConnection(url, username, password)) {
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * 获得 URL 对应的 DB 类型
     *
     * @param url URL
     * @return DB 类型
     */
    public static DbType getDbType(String url) {
        String name = com.alibaba.druid.util.JdbcUtils.getDbType(url, null);
        return DbType.getDbType(name);
    }

    /**
     * 执行sql文件
     * @param filePath
     * @param url
     * @param username
     * @param password
     * @return
     */
    public static boolean executeSqlFile(String filePath, String url, String username, String password) {
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            Statement statement = conn.createStatement();
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            StringBuilder sqlStatements = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                // 忽略注释行和空行
                if (!line.startsWith("--") && !line.isEmpty()) {
                    sqlStatements.append(line.trim());

                    // 如果语句以分号结尾，则执行该语句
                    if (line.trim().endsWith(";")) {
                        String sql = sqlStatements.toString();
                        statement.executeUpdate(sql);
                        sqlStatements.setLength(0);  // 清空语句缓冲区
                    }
                }
            }

            // 最后执行还没以分号结尾的语句
            if (sqlStatements.length() > 0) {
                String sql = sqlStatements.toString();
                statement.executeUpdate(sql);
            }

            // 关闭Statement和Connection对象
            statement.close();
            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 执行sql
     * @param url
     * @param username
     * @param password
     * @param sql
     * @return
     */
    public static boolean executeSql(String url, String username, String password, String sql) {

        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stmt = conn.createStatement();

            stmt.executeUpdate(sql);

            stmt.close();
            conn.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 创建数据库
     * @param url
     * @param username
     * @param password
     * @param dbName
     * @return
     */
    public static boolean createDatabase(String url, String username, String password, String dbName) {

        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stmt = conn.createStatement();

            String sql = "CREATE DATABASE `" + dbName + "`";
            stmt.executeUpdate(sql);

            stmt.close();
            conn.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

//    public static void main(String[] args) {
//        String url = "jdbc:mysql://127.0.0.1:3306?allowMultiQueries=true&useUnicode=true&useSSL=false&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&autoReconnect=true&nullCatalogMeansCurrent=true";
//        boolean root = executeSql(url, "root", "123456", "CREATE DATABASE test1111");
//        System.out.println(root);
//    }

}

