package pers.zhang;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

/**
 * @Author: acton_zhang
 * @Date: 2024/1/30 6:20 下午
 * @Version 1.0
 */
//指定@SpringBootTest的Web Environment为RANDOM_PORT
//此时，将会加载ApplicationContext，并启动Server，Server监听在随机端口上。
//在测试类中通过@LocalServerPort获取该端口值
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DemoTest {

    @LocalServerPort
    private Integer port;

    @Test
    @DisplayName("should access application")
    public void shouldAccessApplication() {
        Assertions.assertThat(port).isGreaterThan(1024);
    }
}
