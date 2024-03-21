package pers.zhang.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: acton_zhang
 * @Date: 2024/1/30 7:42 下午
 * @Version 1.0
 */
@SpringBootTest
@AutoConfigureMockMvc
class TestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void mock() throws Exception {
        //mockMvc.perform执行一个请求
        mockMvc.perform(MockMvcRequestBuilders
            //构造请求
            .get("/mock")
            //设置返回值类型
            .accept(MediaType.APPLICATION_JSON)
            //添加请求参数
            .param("name", "tom"))
        //添加执行完成后的断言
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string("Hello tom!"))
        //添加一个结果处理器，此处打印整个响应结果信息
        .andDo(MockMvcResultHandlers.print());
    }
}
