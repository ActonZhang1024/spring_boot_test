package pers.zhang.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pers.zhang.entity.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;



@SpringBootTest
class UserMapperTest {

    /**
     * 数据库user表内容如下：
     *
     *  id  |  name  |  age  |  height  |
     *  1      tom      18       1.77
     *  2      jerry    22       1.83
     *  3      mike     24       1.79
     */

    @Autowired
    private UserMapper userMapper;

    @Test
    void list() {
        List<User> list = userMapper.list();
        assertThat(list.size()).isEqualTo(3);
        assertThat(list).extracting("id", "name", "age", "height")
                .contains(
                        tuple(1L, "tom", 18, 1.77),
                        tuple(2L, "jerry", 22, 1.83),
                        tuple(3L, "mike", 24, 1.79)
                );
    }

    @Test
    void add() {
        User user = new User();
        user.setName("zhangsan");
        user.setAge(30);
        user.setHeight(1.66);
        Integer effectRows = userMapper.add(user);
        assertThat(effectRows).isEqualTo(1);
    }

    @Test
    void update() {
        User user = new User();
        user.setName("zhangsan");
        user.setAge(33);
        user.setHeight(1.88);
        user.setId(7L);
        Integer effectRows = userMapper.update(user);
        assertThat(effectRows).isEqualTo(1);
    }

    @Test
    void deleteById() {
        Integer effectRows = userMapper.deleteById(7L);
        assertThat(effectRows).isEqualTo(1);
    }

    @Test
    void getById() {
        User expect = new User();
        expect.setId(1L);
        expect.setName("tom");
        expect.setAge(18);
        expect.setHeight(1.77);
        User user = userMapper.getById(1L);
        assertThat(user).isEqualTo(expect);
    }
}
