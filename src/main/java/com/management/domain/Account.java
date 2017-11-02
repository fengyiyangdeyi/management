package com.management.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(uniqueConstraints={
        @UniqueConstraint(
                columnNames = {"vipAccount"}
        )}
)
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    @Column(columnDefinition=("varchar(50)  default null comment '登录名'"))
    private String vipAccount;

    @Column(columnDefinition=("varchar(50)  default null comment '密码'"))
    private String pwd;

    @Column(columnDefinition=("datetime  default null comment '过期时间'"))
    private Date endDate;

    @Column(columnDefinition=("datetime  default null comment '导入时间'"))
    private Date importDate;

    @Column(columnDefinition=("int(5)  default 1 comment '状态：0-不可用、1-可用'"))
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getImportDate() {
        return importDate;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    public Long getId() {
        return id;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVipAccount() {
        return vipAccount;
    }

    public void setVipAccount(String vipAccount) {
        this.vipAccount = vipAccount;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}