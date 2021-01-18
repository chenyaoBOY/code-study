package org.study.customer;

import org.study.database.DatabaseUtil;
import org.study.database.util.PropsUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author chenyao
 * @date 2021/1/15 13:59
 * @description
 */
public class CustomerService {

    /**
     * 优化后的查询
     * 1 将connect管理提出到 database-util 包
     * 2 将bean的set操作使用 工具类 queryRunner简化
     * 3 使用ThreadLocal管理Connection
     * @return
     */
    public List<Customer> getAllCustomerList(){
        String sql="SELECT * FROM customer";
        return DatabaseUtil.getList(Customer.class,sql);
    }

   static class oldCustomerService{
        private static final String driver;
        private static final String url;
        private static final String username;
        private static final String password;

        static {
            Properties properties = PropsUtil.loadFile("config.properties");
            driver = properties.getProperty("jdbc.driver");
            url = properties.getProperty("jdbc.url");
            username = properties.getProperty("jdbc.username");
            password = properties.getProperty("jdbc.password");
            try {
                Class.forName(driver);
            }catch (Exception e){
                throw new IllegalArgumentException("加载jdbc驱动失败"+driver);
            }
        }

        public List<Customer> getCustomerList(Customer customer){
            String sql="SELECT * FROM customer";
            Connection connection =null;
            try {
                connection = DriverManager.getConnection(url,username,password);
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet rs = statement.executeQuery();
                List<Customer> result = new ArrayList<>();
                while (rs.next()){
                    Customer c = new Customer();
                    c.setId(rs.getLong("id"));
                    c.setName(rs.getString("name"));
                    c.setContact(rs.getString("contact"));
                    c.setTelephone(rs.getString("telephone"));
                    c.setEmail(rs.getString("email"));
                    result.add(c);
                }
                return result;
            }catch(SQLException e){
                e.printStackTrace();
            }finally {
                if(connection!=null){
                    try {
                        connection.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
            return null;
        }
        public Customer getCustomer(Long id){
            return null;
        }
    }
}
