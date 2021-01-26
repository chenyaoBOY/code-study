package org.study.database;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.study.database.util.PropsUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.dbutils.QueryRunner;

/**
 * @author chenyao
 * @date 2021/1/15 14:34
 * @description
 */
@Slf4j
public class DatabaseUtil {

    private static final String driver;
    private static final String url;
    private static final String username;
    private static final String password;

    private static final QueryRunner QUERY_RUNNER = new org.apache.commons.dbutils.QueryRunner();

    private static final ThreadLocal<Connection> CONNECTION_THREAD_LOCAL = new ThreadLocal<>();

    static {
        Properties properties = PropsUtil.loadFile("config.properties");
        driver = properties.getProperty("jdbc.driver");
        url = properties.getProperty("jdbc.url");
        username = properties.getProperty("jdbc.username");
        password = properties.getProperty("jdbc.password");
        try {
            Class.forName(driver);
        } catch (Exception e) {
            throw new IllegalArgumentException("加载jdbc驱动失败" + driver);
        }
    }


    public static <T> List<T> getList(Class<T> clazz, String sql, Object... params) {
        try {
            return QUERY_RUNNER.query(getConnection(), sql, new BeanListHandler<T>(clazz), params);
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables.getMessage());
        } finally {
            closeConnection();
        }
    }


    public static Connection getConnection() {
        Connection cnn = CONNECTION_THREAD_LOCAL.get();
        if (cnn == null) {
            try {
                cnn = DriverManager.getConnection(url, username, password);
            } catch (SQLException throwables) {
                throw new RuntimeException(throwables.getMessage());
            } finally {
                CONNECTION_THREAD_LOCAL.set(cnn);
            }
        }
        return cnn;
    }

    public static void closeConnection() {
        if (CONNECTION_THREAD_LOCAL.get() != null) {
            try {
                CONNECTION_THREAD_LOCAL.get().close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                CONNECTION_THREAD_LOCAL.remove();
            }
        }
    }

    public static void openTransaction(){
        Connection connection = getConnection();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            log.error("open transaction is failure",e);
            throw new RuntimeException("open transaction is failure:"+e.getMessage());
        }
    }

    public static void commitTransaction(){
        Connection connection = getConnection();
        try {
            connection.commit();
        } catch (SQLException e) {
            log.error("commit transaction is failure",e);
            throw new RuntimeException("commit transaction is failure:"+e.getMessage());
        }
    }
    public static void rollbackTransaction(){
        Connection connection = getConnection();
        try {
            connection.rollback();
        } catch (SQLException e) {
            log.error("rollback transaction is failure",e);
            throw new RuntimeException("rollback transaction is failure:"+e.getMessage());
        }
    }

}
