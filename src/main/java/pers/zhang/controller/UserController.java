package pers.zhang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.zhang.entity.User;
import pers.zhang.service.UserService;

import java.util.List;

/**
 * @Author: acton_zhang
 * @Date: 2024/1/30 3:38 下午
 * @Version 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public List<User> list() {
        System.out.println("Call UserService.list");
        return userService.list();
    }

    @GetMapping("/info")
    public User getUserById(Long id) {
        System.out.println("Call UserService.getUserById");
        return userService.getById(id);
    }

    @PostMapping("/add")
    public Integer add(@RequestBody User user) {
        System.out.println("Call UserService.add");
        return userService.add(user);
    }

    @PostMapping("/update")
    public Integer update(@RequestBody User user) {
        System.out.println("Call UserService.update");
        return userService.update(user);
    }

    @PostMapping("/delete")
    public Integer delete(Long id) {
        System.out.println("Call UserService.delete");
        return userService.deleteById(id);
    }
}
