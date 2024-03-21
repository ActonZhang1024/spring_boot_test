package pers.zhang.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;



@SpringBootTest
@AutoConfigureMockMvc
class JsonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * 数据库user表内容如下：
     *
     *  id  |  name  |  age  |  height  |
     *  1      tom      18       1.77
     *  2      jerry    22       1.83
     *  3      mike     24       1.79
     */

    @Test
    void list() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                    .get("/user/list")
                    .accept(MediaType.APPLICATION_JSON))
                //响应码200
                .andExpect(MockMvcResultMatchers.status().isOk())
                //json数组长度为3
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.equalTo(3)))
                //name包含指定值
                .andExpect(MockMvcResultMatchers.jsonPath("$..name", Matchers.contains("tom", "jerry", "mike")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void getUserById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                    .get("/user/info")
                    .accept(MediaType.APPLICATION_JSON)
                    .param("id", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.equalTo("tom")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age", Matchers.equalTo(18)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.height", Matchers.equalTo(1.77)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void add() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                    .post("/user/add")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content("{\"name\": \"zhangsan\", \"age\":  40, \"height\": 1.76}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.equalTo(1)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void update() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                    .post("/user/update")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content("{\"id\": 9, \"name\": \"lisi\", \"age\":  44, \"height\": 1.76}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.equalTo(1)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                    .post("/user/delete")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .param("id", "9"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.equalTo(1)))
                .andDo(MockMvcResultHandlers.print());
    }
}
