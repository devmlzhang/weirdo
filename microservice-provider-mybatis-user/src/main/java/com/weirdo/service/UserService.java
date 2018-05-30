package com.weirdo.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.weirdo.model.UserDomain;

import java.util.List;

public interface UserService {
    int addUser(UserDomain user);

    PageInfo<UserDomain> findAllUser(int pageNum, int pageSize);

    List<UserDomain> findOneUser(Integer id);

    JSONObject findJSONUser(Integer id);

    void update(UserDomain userDomain);

    void delete(Integer id);

    PageInfo findJsonAllUser(int pageNum, int pageSize);

}
