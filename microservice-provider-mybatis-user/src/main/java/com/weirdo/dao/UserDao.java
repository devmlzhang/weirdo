package com.weirdo.dao;

import com.weirdo.model.UserDomain;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @Author ML.Zhang
 * @Date 2018/5/25
 */

@Repository
public interface UserDao {
    int insert(UserDomain record);

    List<UserDomain> selectUsers();

    UserDomain findById(Integer id);

}
