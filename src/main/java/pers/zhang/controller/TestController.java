package pers.zhang.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: acton_zhang
 * @Date: 2024/1/30 7:41 下午
 * @Version 1.0
 */
@RestController
public class TestController {

    @RequestMapping("/mock")
    public String mock(String name) {
        return "Hello " + name + "!";
    }
}
