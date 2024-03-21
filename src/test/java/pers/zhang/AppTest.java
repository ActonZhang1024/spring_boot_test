package pers.zhang;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pers.zhang.entity.User;
import pers.zhang.mapper.UserMapper;

/**
 * @Author: acton_zhang
 * @Date: 2024/1/21 9:24 下午
 * @Version 1.0
 */
@SpringBootTest
public class AppTest {

    @Autowired
    UserMapper userMapper;

    @Test
    public void test() {
        User user = new User();
        user.setName("tom");
        user.setAge(18);
        user.setHeight(1.88);
        Assertions.assertThat(userMapper.add(user)).isEqualTo(1);
    }
}
