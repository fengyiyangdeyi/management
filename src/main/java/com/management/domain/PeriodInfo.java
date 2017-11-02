package com.management.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
//@Table(uniqueConstraints={
//        @UniqueConstraint(
//                columnNames = {"keyword","ip"}
//        )}
//)
public class PeriodInfo {

    @Id
    @GeneratedValue
    private Long id;

    @Column(columnDefinition=("varchar(255) comment '电影名'"))
    private String keyword;

    @Column(columnDefinition=("varchar(255) comment 'ip'"))
    private String  ip;

    @Column(columnDefinition=("varchar(255) comment '备注'"))
    private String remark;

    @Column(columnDefinition=("datetime  default null comment '时间'"))
    private Date addTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}