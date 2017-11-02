package com.management.controller;

import com.management.dao.UserRepository;
import com.management.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class TestController {
    /**
     * Created by fangxiao on 2017/3/24.
     */

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/helloworld")
    public String helloWorld() {
        return "{\"helloworld\":\"helloworld\"}";
    }

    @RequestMapping(value = "/helloworld1", produces = "application/json; charset=utf-8")
    public String helloWorld1() {
        return "妈妈疲";
    }

    @RequestMapping("/findUser")

    public String findUser() {
        User user = userRepository.findUser("cc");
        return user.getAge().toString();
    }


    @RequestMapping("/index")
    public String helloHtml(ModelMap map){
        map.put("index","index");
        return"index";
    }

}
