package com.weirdo.service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weirdo.dao.UserDao;
import com.weirdo.model.UserDomain;
import com.weirdo.util.BaseJdbcDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "userService")
public class UserServiceImpl extends BaseJdbcDao implements UserService {
    @Autowired
    private UserDao userDao;//这里会报错，但是并不会影响

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int addUser(UserDomain user) {

        return userDao.insert(user);
    }

    /*
     * 这个方法中用到了我们开头配置依赖的分页插件pagehelper
     * 很简单，只需要在service层传入参数，然后将参数传递给一个插件的一个静态方法即可；
     * pageNum 开始页数
     * pageSize 每页显示的数据条数
     * */
    @Override
    public PageInfo<UserDomain> findAllUser(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<UserDomain> userDomains = userDao.selectUsers();
        PageInfo result = new PageInfo(userDomains);
        return result;
    }

    @Override
    public PageInfo findJsonAllUser(int pageNum, int pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        //  List<UserDomain> userDomains = userDao.selectUsers();


        String sql = "   SELECT t.user_id AS userId ,t.user_name AS userName ,t.password AS password,t.phone  AS phone  FROM t_user t WHERE 1 = ?  ";
        Object[] objs = new Object[1];
        objs[0] = 1;
        List<UserDomain> userDomains = jdbcQueryForList(sql, UserDomain.class, objs);

        //List<JSONObject> list = this.selectForJsonList(sql, objs);
        return new PageInfo(userDomains);
    }

    @Override
    public JSONObject findJSONUser(Integer id) {
        String sql = "   SELECT t.user_id ,t.user_name ,t.password AS password,t.phone  AS phone  FROM t_user t WHERE t.user_id = ?  ";
        Object[] objs = new Object[1];
        objs[0] = id;
        JSONObject jsonObject = this.selectForJsonObject(sql, objs);

        return jsonObject;
    }

    @Override
    public List<UserDomain> findOneUser(Integer id) {
        List<UserDomain> query = new ArrayList<UserDomain>();
        String sql = "  SELECT t.user_id ,t.user_name ,t.password AS password,t.phone  AS phone  FROM t_user t WHERE t.user_id = ?  ";
        Object[] objs = new Object[1];
        objs[0] = id;
        query = jdbcQueryForList(sql, UserDomain.class, objs);
        return query;
    }

    @Override
    public void delete(Integer id) {
        String sql = " DELETE FROM t_user WHERE user_id = ?  ";
        Object[] objs = new Object[1];
        objs[0] = id;
        jdbcTemplate.update(sql, objs);
    }

    @Override
    public void update(UserDomain userDomain) {
        String sql = "  UPDATE t_user SET user_name = ?, password = ? ,phone = ?  " +
                "  WHERE user_id = ? ";
        Object[] objs = new Object[4];
        objs[0] = userDomain.getUserName();
        objs[1] = userDomain.getPassword();
        objs[2] = userDomain.getPhone();
        objs[3] = userDomain.getUserId();
        jdbcTemplate.update(sql, objs);

    }

    /**
     * jdbc查询
     *
     * @param <T>
     * @param querySql
     * @param objs
     * @return
     */
    private <T> List<T> jdbcQueryForList(String querySql, Class<T> clazz, Object... objs) {
        List<T> resultList = Lists.newArrayList();
        try {
            resultList = jdbcTemplate.query(querySql, objs, BeanPropertyRowMapper.newInstance(clazz));
            return resultList;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return resultList;
    }


}