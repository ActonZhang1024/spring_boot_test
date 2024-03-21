package pers.zhang.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import pers.zhang.entity.User;
import pers.zhang.mapper.UserMapper;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;


class UserServiceImplTest {

    //Mock掉Dao层
    @Mock
    private UserMapper userMapper;

    //把Mock掉的Dao层注入Service
    @InjectMocks
    private UserServiceImpl userService;


    @BeforeEach
    void setup() {
        //开启Mockito注解
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void list() {
        List<User> users = new ArrayList<>();
        users.add(new User(10L, "zhangsan", 18, 1.77));
        users.add(new User(11L, "lisi", 22, 1.83));
        //打桩
        when(userMapper.list()).thenReturn(users);
        //调用
        List<User> list = userService.list();
        list.forEach(System.out::println);
        //验证
        verify(userMapper, times(1)).list();
    }

    @Test
    void add() {
        User user = new User(1L, "tom", 21, 1.80);
        //打桩
        when(userMapper.add(isA(User.class))).thenReturn(1);
        //调用
        Integer effectRows = userService.add(user);
        assertThat(effectRows).isEqualTo(1);
        //验证
        verify(userMapper, times(1)).add(user);
    }

    @Test
    void update() {
        User user = new User(2L, "tom", 21, 1.80);
        //打桩
        when(userMapper.update(argThat(u -> {
            return u != null && u.getId() != null;
        }))).thenReturn(1);
        //调用
        Integer effectRows = userService.update(user);
        assertThat(effectRows).isEqualTo(1);
        //验证
        verify(userMapper, times(1)).update(user);
    }

    @Test
    void deleteById() {
        //打桩
        when(userMapper.deleteById(anyLong())).thenReturn(1);
        //调用
        Integer effectRows = userService.deleteById(999L);
        assertThat(effectRows).isEqualTo(1);
        //验证
        verify(userMapper, times(1)).deleteById(999L);
    }

    @Test
    void getById() {
        User user = new User(1L, "xxx", 40, 1.92);
        //打桩
        when(userMapper.getById(1L)).thenReturn(user);
        //调用
        User actual = userService.getById(1L);
        assertThat(actual).isInstanceOf(User.class);
        //验证
        verify(userMapper, times(1)).getById(1L);
    }
}
