package pers.zhang.mapper;

import org.apache.ibatis.annotations.Mapper;
import pers.zhang.entity.User;

import java.util.List;

/**
 * @Author: acton_zhang
 * @Date: 2024/1/30 2:59 下午
 * @Version 1.0
 */
@Mapper
public interface UserMapper {

    List<User> list();

    Integer add(User user);

    Integer update(User user);

    Integer deleteById(Long id);

    User getById(Long id);
}
