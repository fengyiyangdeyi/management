package com.management.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(uniqueConstraints={
        @UniqueConstraint(
                columnNames = {"account","name"}
        )}
)
public class AccountTeleplay {

    @Id
    @GeneratedValue
    private Long id;

    @Column(columnDefinition=("int(5)  default null comment '状态：0-未看、1-浏览中、2-已看完'"))
    private Integer watchStatus;

    @Column(columnDefinition=("int(50)  default null comment 'AccountId'"))
    private Integer AccountId;

    @Column(columnDefinition=("int(50)  default null comment 'TeleplayId'"))
    private Integer TeleplayId;

    @Column(columnDefinition=("varchar(255)  default null comment '电影名'"))
    private String name;

    @Column(columnDefinition=("varchar(255)  default null comment '账号'"))
    private String account;

    @Column(columnDefinition=("datetime  default null comment '浏览时间'"))
    private Date beginDate;

    @Column(columnDefinition=("datetime  default null comment '浏览结束时间'"))
    private Date endDate;

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getWatchStatus() {
        return watchStatus;
    }

    public void setWatchStatus(Integer watchStatus) {
        this.watchStatus = watchStatus;
    }

    public Integer getAccountId() {
        return AccountId;
    }

    public void setAccountId(Integer accountId) {
        AccountId = accountId;
    }

    public Integer getTeleplayId() {
        return TeleplayId;
    }

    public void setTeleplayId(Integer teleplayId) {
        TeleplayId = teleplayId;
    }
}