package com.management.controller;

import com.alibaba.fastjson.JSON;
import com.management.dao.AccountRepository;
import com.management.dao.UserRepository;
import com.management.domain.*;
import com.management.service.AccountService;
import com.management.utils.LayuiResultJson;
import com.management.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/account")
public class AccountController {
    /**
     * Created by fangxiao on 2017/3/24.
     */
    @Autowired
    private AccountService accountService;

    /**
     * 更改状态接口
     *
     * @return
     */
    @RequestMapping("/updateStatus")
    public String updateStatus(AccountTeleplay at, String status) {
        if ("4".equals(status)) {
            String re = accountService.disableAccount(at);
            return re;
        } else {
            String re = accountService.updateStatus(at);
            return re;
        }
    }

    /**
     * 查找未看账号
     *
     * @return
     */
    @RequestMapping("/findNotWatchAccount")
    public String findNotWatchAccount(AccountTeleplay at) {
        String s = accountService.findNotWatchAccount(at);
        return s;
    }

    /**
     * 导入账号
     *
     * @return
     */
    @RequestMapping("/importAccount")
    public String importAccount(@RequestParam(required = true) String accountJson) {
        //13605088661----wykuokmm1&13820293836----jly009521
        accountService.importLog(accountJson);
        String[] split = accountJson.split(",");
        List<Account> acs = new ArrayList<>();
        for (String s : split) {
            String[] pojo = s.replaceAll(" ", "").split("----");
            if (pojo.length != 2) {
                return "格式有问题！";
            }
            String mobile = pojo[0];
            String pwd = pojo[1];
            Account account = new Account();
            account.setVipAccount(mobile);
            account.setPwd(pwd);
            acs.add(account);
        }
        //List<Account> acs = JSON.parseArray(accountJson, Account.class);
        String s = accountService.importAccount(acs);
        accountService.importLog(s);
        return s;
    }
    private final static Logger logger = LoggerFactory.getLogger(AccountController.class);
    /**
     * 导入
     *
     * @return
     */
    @RequestMapping("/importPeriodInfo")
    public String importPeriodInfo(PeriodInfo info) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ipAddr = getIpAddr(request);
        info.setIp(ipAddr);
        String s = accountService.importPeriodInfo(info);
        return s;
    }

    /**
     * 查看
     *
     * @retur
     */
    @RequestMapping("/findPeriodInfo")
    public String findPeriodInfo(PeriodInfo info) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ipAddr = getIpAddr(request);
        info.setIp(ipAddr);
        String s = accountService.findPeriodInfo(info);
        return s;
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if(ip.equals("127.0.0.1")){
                //根据网卡取本机配置的IP
                InetAddress inet=null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ip= inet.getHostAddress();
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if(ip != null && ip.length() > 15){
            if(ip.indexOf(",")>0){
                ip = ip.substring(0,ip.indexOf(","));
            }
        }
        return ip;
    }

    @RequestMapping(value = "/keywords", produces = "application/json; charset=utf-8")
    public String keywords() {
        String s = accountService.keywords();
        return s;
    }

    @RequestMapping(value = "/del", produces = "application/json; charset=utf-8")
    public String del(Period p,String pwd) {
        String s = accountService.del(p,pwd);
        return s;
    }

    @RequestMapping(value = "/add", produces = "application/json; charset=utf-8")
    public String add(Period p,String pwd) {
        String s = accountService.add(p,pwd);
        return s;
    }

    @RequestMapping(value = "/findipjson", produces = "application/json; charset=utf-8")
    public String findipjson(Period p,Integer page,Integer limit) {
        try {
            String ret = accountService.findipjson(p,page,limit);
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            return new LayuiResultJson(1,e.getMessage(),0, "").toString();
        }
    }

    @RequestMapping(value = "/findipinfojson", produces = "application/json; charset=utf-8")
    public String findipinfojson(PeriodInfo p,Integer page,Integer limit) {
        try {
            String ret = accountService.findipinfojson(p,page,limit);
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            return new LayuiResultJson(1,e.getMessage(),0, "").toString();
        }
    }


}
