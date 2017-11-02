package com.management.controller;

import com.management.domain.Account;
import com.management.domain.AccountTeleplay;
import com.management.domain.Period;
import com.management.domain.PeriodInfo;
import com.management.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/web")
public class WebController {
    /**
     * Created by fangxiao on 2017/3/24.
     */
    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/findip")
    public String findip(Map<String,Object> map,Period p) {
        map.put("keyword",p.getKeyword());
        return "findip";
    }

    @RequestMapping(value = "/findipinfo")
    public String findipinfo(Map<String,Object> map,PeriodInfo p) {
        map.put("keyword",p.getKeyword());
        map.put("ip",p.getIp());
        return "findipinfo";
    }

    @RequestMapping(value = "/testhello")
    public String testhello(Map map) {
//        request.setAttribute("name", "your valuecccccccccc");
//
//        HttpSession session = request.getSession();
//        session.setAttribute("isAll", "isAll");
        return "testhello";
    }

    @RequestMapping(value = "/testhello1")
    public String testhello1(Map map,HttpServletRequest request) {
        request.setAttribute("name", "your valuecccccccccc");

        HttpSession session = request.getSession();



        session.setAttribute("isAll", "isAll");
        return "testhello";
    }


}



































