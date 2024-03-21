package pers.zhang.service;

import pers.zhang.entity.User;

import java.util.List;

/**
 * @Author: acton_zhang
 * @Date: 2024/1/30 3:35 下午
 * @Version 1.0
 */
public interface UserService {
    List<User> list();

    Integer add(User user);

    Integer update(User user);

    Integer deleteById(Long id);

    User getById(Long id);
}
