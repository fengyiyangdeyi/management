package com.management.service;

import com.alibaba.fastjson.JSON;
import com.management.dao.*;
import com.management.domain.*;
import com.management.utils.DateUtil;
import com.management.utils.LayuiResultJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.*;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TelepalyRepository telepalyRepository;
    @Autowired
    private AccountTelepalyRepository accountTelepalyRepository;
    @Autowired
    private AccountImportLogRepository accountImportLogRepository;
    @Autowired
    private PeriodRepository periodRepository;
    @Autowired
    private PeriodInfoRepository periodInfoRepository;


    public String updateStatus(AccountTeleplay ac) {
        //当0-1时：新建多对多关系表数据
        //当1-2时：更改watchStatus为2
        AccountTeleplay at = accountTelepalyRepository.findAT(ac.getAccount(), ac.getName());
        if (at != null) {
            at.setWatchStatus(2);
            at.setEndDate(new Date());
            accountTelepalyRepository.saveAndFlush(at);
            return "{\"code\":1}";
        } else {
            return "{\"code\":0}";
        }

    }

    public String updateStatusDisableAccount(AccountTeleplay ac) {
        //当0-1时：新建多对多关系表数据
        //当1-2时：更改watchStatus为2
        AccountTeleplay at = accountTelepalyRepository.findAT(ac.getAccount(), ac.getName());
        if (at != null) {
            at.setWatchStatus(4);
            at.setEndDate(new Date());
            accountTelepalyRepository.saveAndFlush(at);
            return "{\"code\":1}";
        } else {
            return "{\"code\":0}";
        }

    }

    public String importAccount(List<Account> acs) {
        StringBuffer sb = new StringBuffer();
        int sucess = 0;
        int faiure = 0;
        for (Account ac : acs) {
            try {
                ac.setImportDate(new Date());
                accountRepository.saveAndFlush(ac);
                sb.append(ac.getVipAccount() + ":1");
                sb.append("<br/>");
                sucess++;
            } catch (Exception e) {
                sb.append(ac.getVipAccount() + ":0");
                sb.append("<br/>");
                faiure++;
            }
        }
        String info = "成功" + sucess + "条，失败" + faiure + "条。" + "<br/>";
        return info + sb.toString();
    }

    public Map importTeleplay(List<Teleplay> teleplays) {
        Map map = new HashMap();
        for (Teleplay tp : teleplays) {
            try {
                telepalyRepository.saveAndFlush(tp);
                map.put(tp.getName(), 1);
            } catch (Exception e) {
                map.put(tp.getName(), 0);
            }
        }
        return map;
    }

    public String findNotWatchAccount(AccountTeleplay atf) {
        //只返回一系数据
        List<Account> as = accountRepository.findA();
        for (Account a : as) {
            //System.out.println(a.getVipAccount()+"---------"+atf.getName());
            AccountTeleplay at = accountTelepalyRepository.findAT(a.getVipAccount(), atf.getName());
            if (at == null) {
                AccountTeleplay accountTeleplay = new AccountTeleplay();
                accountTeleplay.setAccount(a.getVipAccount());
                accountTeleplay.setName(atf.getName());
                accountTeleplay.setWatchStatus(1);
                accountTeleplay.setAccountId(new Integer(a.getId().toString()));
                accountTeleplay.setBeginDate(new Date());
                accountTelepalyRepository.saveAndFlush(accountTeleplay);
                return getString(a);
            }
        }
        return "{}";
    }

    private String getString(Account a) {
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        sb.append("\"account\":\"");
        sb.append(a.getVipAccount());
        sb.append("----");
        sb.append(a.getPwd());
        sb.append("\"}");
        return sb.toString();
    }

    public void importLog(String accountJson) {
        ImportAccountLog importAccountLog = new ImportAccountLog();
        importAccountLog.setContext(accountJson);
        importAccountLog.setImportDate(new Date());
        accountImportLogRepository.saveAndFlush(importAccountLog);
    }

    @Transactional
    public String disableAccount(AccountTeleplay at) {
        //当0-1时：新建多对多关系表数据
        //当1-2时：更改watchStatus为2
        Account a = accountRepository.getByAccount(at.getAccount());
        a.setEndDate(new Date());
        a.setStatus(0);
        accountRepository.saveAndFlush(a);
        updateStatusDisableAccount(at);
        return "{\"code\":1}";
    }

    public String importPeriodInfo(PeriodInfo info) {
        info.setAddTime(new Date());
        periodInfoRepository.saveAndFlush(info);
        Period byKeyword = periodRepository.findByKeyword(info.getKeyword());
        Long l=periodInfoRepository.countByKeyword(byKeyword.getKeyword());
        byKeyword.setCount(l);
        periodRepository.saveAndFlush(byKeyword);
        return "{\"code\":1}";
    }

    public String findPeriodInfo(PeriodInfo info) {
        List<PeriodInfo> dbinfos = periodInfoRepository.findByKeywordAndIp(info.getKeyword(), info.getIp());
        if (dbinfos.size()==0) {
            return "{\"code\":1}";
        } else {
            Period dbp = periodRepository.findByKeyword(info.getKeyword());
            if(dbp==null){
                return info.getKeyword()+"有效期未维护！";
            }
            Calendar c = Calendar.getInstance();
            c.setTime(dbinfos.get(0).getAddTime());
            c.add(Calendar.HOUR, dbp.getPerod().intValue()); // 目前時間加3小時
            Date perodTime = c.getTime();
            if (perodTime.before(new Date())) {
                return "{\"code\":1}";
            } else {
                return "{\"code\":0}";
            }
        }
    }

    public String keywords() {
        StringBuffer sb =new StringBuffer();
        sb.append("<tbody>");
        sb.append("<tr>\n" +
                "\t\t\t\t\t<th>关键字</th>\n" +
                "\t\t\t\t\t<th>有效期（小时）</th>\n" +
                "\t\t\t\t\t<th>已使用次数</th>\n" +
                "\t\t\t\t\t<th>创建时间</th>\n" +
                "\t\t\t\t\t<th></th>\n" +
                "\t\t\t\t\t<th></th>\n" +
                "\t\t\t\t</tr>");
        List<Period> all = periodRepository.findAll();
        for (Period p:all) {
            String format = DateUtil.format(p.getAddTime(), "yyyy年MM月dd日");
            sb.append("<tr id=\""+p.getId()+"\">\n" +
                    "\t\t\t\t\t<td>"+p.getKeyword()+"</td>\n" +
                    "\t\t\t\t\t<td>"+p.getPerod()+"</td>\n" +
                    "\t\t\t\t\t<td>"+p.getCount()+"</td>\n" +
                    "\t\t\t\t\t<td>"+format+"</td>\n" +
                    "\t\t\t\t\t<td><a href=\"javascript:del("+p.getId()+",'"+p.getCount()+"');\" name=\"del\">删除</a></td>\n" +
                    "\t\t\t\t\t<td><a href=\"/web/findip?keyword="+p.getKeyword()+"\" title=\"脚本之家\" target=\"_blank\">查看ip</a></td>\n" +
                    "\t\t\t\t</tr>");
        }

                sb.append("</tbody>");
        return sb.toString();
    }

    @Transactional
    public String del(Period p,String pwd) {
        if(!"123456".equals(pwd)){
            return "密码有误！";
        }
        periodRepository.delete(p);
        return keywords();
    }

    @Transactional
    public String add(Period p,String pwd) {
        if(!"123456".equals(pwd)){
            return "密码有误！";
        }
        Period byKeyword = periodRepository.findByKeyword(p.getKeyword());
        if(byKeyword!=null){
            return "数据重复！";
        }
        p.setAddTime(new Date());
        periodRepository.saveAndFlush(p);
        return keywords();
    }

    public String findipjson(Period p, Integer page, Integer limit) {

        Integer pageIndex = (page - 1) * limit;
        Integer pageSize = limit;
//        long count=periodInfoRepository.count((root, query, cb) -> {
//            query.where(cb.equal(root.get("keyword"), p.getKeyword())); //这里可以设置任意条查询条件
//            return null;
//        });
//
//        PageRequest pageRequest = new PageRequest(page - 1, pageSize, Sort.Direction.DESC,"addTime");
//        Page<PeriodInfo> list = periodInfoRepository.findAll((root, query, cb) -> {
//            query.where(cb.equal(root.get("keyword"), p.getKeyword())); //这里可以设置任意条查询条件
//            return null;
//        },pageRequest);
        long count=periodInfoRepository.countGroupId(p.getKeyword());
        List<Object[]> list = periodInfoRepository.findGroupId(p.getKeyword(),pageIndex,pageSize);

        List relist = new ArrayList();
        for (Object[] f : list) {
            Map map = new HashMap();
            map.put("ip", f[0]);
            map.put("keyword", f[1]);
            map.put("num",f[2]);
            relist.add(map);
        }
        return new LayuiResultJson(0, "sucesssful", count, JSON.toJSONString(relist)).toString();
    }

    public String findipinfojson(final PeriodInfo p, Integer page, Integer limit) {
        long count=periodInfoRepository.count(new Specification<PeriodInfo>(){
            @Override
            public Predicate toPredicate(Root<PeriodInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.where(cb.equal(root.get("keyword"), p.getKeyword())
                        ,cb.equal(root.get("ip"), p.getIp())); //这里可以设置任意条查询条件
                return null;
            }

        });
        PageRequest pageRequest = new PageRequest(page - 1, limit, Sort.Direction.DESC,"addTime");
        Page<PeriodInfo> list = periodInfoRepository.findAll(new Specification<PeriodInfo>(){
            @Override
            public Predicate toPredicate(Root<PeriodInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.where(cb.equal(root.get("keyword"), p.getKeyword())
                        ,cb.equal(root.get("ip"), p.getIp())); //这里可以设置任意条查询条件
                return null;
            }

        },pageRequest);



        List relist = new ArrayList();
        for (PeriodInfo f : list) {
            Map map = new HashMap();
            map.put("ip", f.getIp());
            map.put("keyword", f.getKeyword());
            map.put("addTime",DateUtil.format(f.getAddTime()));
            relist.add(map);
        }
        return new LayuiResultJson(0, "sucesssful", count, JSON.toJSONString(relist)).toString();
    }
}
