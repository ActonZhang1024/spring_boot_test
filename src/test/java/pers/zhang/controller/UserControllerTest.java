package pers.zhang.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pers.zhang.entity.User;
import pers.zhang.service.UserService;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;


class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setup() {
        //开启Mockito注解
        MockitoAnnotations.openMocks(this);
        //初始化MockMvc，将UserController注入其中
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void list() throws Exception {
        //打桩
        when(userService.list()).thenReturn(
                Arrays.asList(
                        new User(1L, "tom", 18, 1.77),
                        new User(2L, "jerry", 22, 1.88)
                ));
        //调用
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/list")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
        //验证
        verify(userService, times(1)).list();
    }

    @Test
    void getUserById() throws Exception {
        //打桩
        User user = new User(1L, "tom", 18, 1.77);
        when(userService.getById(anyLong())).thenReturn(user);
        //调用
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/info")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
        //验证
        verify(userService, times(1)).getById(1L);
    }

    @Test
    void add() throws Exception {
        User user = new User();
        user.setName("jerry");
        user.setAge(22);
        user.setHeight(1.74);
        //打桩
        when(userService.add(isA(User.class))).thenReturn(1);
        //调用
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/add")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"jerry\", \"age\": 22, \"height\": 1.74}"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
        //验证
        verify(userService, times(1)).add(user);
    }

    @Test
    void update() throws Exception {
        User user = new User(1L, "tom", 18, 1.77);
        //打桩
        when(userService.update(isA(User.class))).thenReturn(1);
        //调用
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/update")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"name\": \"tom\", \"age\": 18, \"height\": 1.77}"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
        //验证
        verify(userService, times(1)).update(user);
    }

    @Test
    void delete() throws Exception {
        //打桩
        when(userService.deleteById(anyLong())).thenReturn(1);
        //调用
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/delete")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
        //验证
        verify(userService, times(1)).deleteById(1L);
    }
}
