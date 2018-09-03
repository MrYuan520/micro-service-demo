package com.yuanbo.user.service;
import com.yb.thrift.user.UserInfo;
import com.yb.thrift.user.UserService;
import com.yuanbo.user.mapper.UserMapper;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author boyuan
 * @create 2018-08-30 10:50
 */

@Service
public class UserServiceImpl implements UserService.Iface {

    @Autowired
    UserMapper userMapper;

    @Override
    public UserInfo getUserById(int id) throws TException {
        return userMapper.getUserById(id);
    }

    @Override
    public UserInfo getUserByName(String username) throws TException {
        return userMapper.getUserByName(username);
    }

    @Override
    public void registerUser(UserInfo userInfo) throws TException {
        userMapper.registerUser(userInfo);
    }

    @Override
    public UserInfo getTeacherById(int id) throws TException{
        return userMapper.getTeacherById(id);
    }
}
