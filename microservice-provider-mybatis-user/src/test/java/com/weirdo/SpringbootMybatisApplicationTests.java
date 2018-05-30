/*
package com.weirdo;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.weirdo.dao.UserDao;
import com.weirdo.model.UserDomain;
import com.weirdo.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootMybatisApplicationTests {

    @Autowired
    UserDao userDao;
    @Autowired
    UserService userService;


    @Test
    public void contextLoads() {
        for (int i = 0; i <= 10; i++) {
            UserDomain userDomain = new UserDomain();
            userDomain.setPassword("ddd" + i);
            userDomain.setUserId(2);
            userDomain.setUserName("张三" + i);
            userDomain.setPhone("12356444" + i);
            userDao.insert(userDomain);
        }
    }

    @Test
    public void delete() {
        userService.delete(1000);
    }

    @Test
    public void update() {
        UserDomain userDomain = new UserDomain();
        userDomain.setPassword("dd2d");
        userDomain.setUserId(1002);
        userDomain.setUserName("张三44");
        userDomain.setPhone("12356444");
        userService.update(userDomain);
    }


    @Test
    public void findAllUser() {
        PageInfo<UserDomain> allUser = userService.findAllUser(1, 5);
        System.out.println("========================");
        System.out.println(allUser.toString());
    }

    @Test
    public void findPageJsonAllUser() {
        PageInfo jsonAllUser = userService.findJsonAllUser(1, 5);
        System.out.println("========================");
        System.out.println(jsonAllUser.toString());
    }

    @Test
    public void findJsonUser() {
        JSONObject oneUser = userService.findJSONUser(1002);
        System.out.printf("====" + oneUser.toString());
    }


    @Test
    public void findOneUser() {
        List<UserDomain> oneUser = userService.findOneUser(1002);
        System.out.printf("====" + oneUser.get(0).toString());
    }


    @Autowired
    private JdbcTemplate jdbcTemplate;//创建jdbctemplate对象，并使用spring的自动注入完成实例化

    @Test
    public void selectTest1() {
        UserDomain userDomain;
        String sql = " SELECT t.user_id AS userId,t.user_name AS userName,t.password,t.phone   FROM t_user t WHERE t.user_id = ?  ";//sql语句
        Object[] params = new Object[]{1000};//设置参数
        //使用queryForObject方法查询，当查询结果为空或者结果size大于2时，会抛出异常
        userDomain = (UserDomain) jdbcTemplate.queryForObject(sql, params, new RowMapper() {
            //重写mapRow方法，resultSet为查询的结果集
            @Override
            public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                UserDomain user = new UserDomain();
                user.setUserId(resultSet.getInt("userId"));
                user.setUserName(resultSet.getString("userName"));
                user.setPhone(resultSet.getString("phone"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        });
        System.out.println(userDomain.getUserId());
        System.out.println(userDomain.getUserName());
    }


}
*/
