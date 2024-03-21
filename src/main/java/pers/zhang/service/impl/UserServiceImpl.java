package pers.zhang.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.zhang.entity.User;
import pers.zhang.mapper.UserMapper;
import pers.zhang.service.UserService;

import java.util.List;



@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> list() {
        System.out.println("Call userMapper.list...");
        return userMapper.list();
    }

    @Override
    public Integer add(User user) {
        System.out.println("Call userMapper.add...");
        return userMapper.add(user);
    }

    @Override
    public Integer update(User user) {
        System.out.println("Call userMapper.update...");
        return userMapper.update(user);
    }

    @Override
    public Integer deleteById(Long id) {
        System.out.println("Call userMapper.deleteById...");
        return userMapper.deleteById(id);
    }


    @Override
    public User getById(Long id) {
        System.out.println("Call userMapper.getById...");
        return userMapper.getById(id);
    }
}
