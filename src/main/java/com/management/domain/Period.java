package com.management.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(uniqueConstraints={
        @UniqueConstraint(
                columnNames = {"keyword"}
        )}
)
public class Period {

    @Id
    @GeneratedValue
    private Long id;

    @Column(columnDefinition=("varchar(255) comment '电影名'"))
    private String keyword;

    @Column//(columnDefinition=("varchar(255) comment '有效期'"))
    private Long  perod;

    @Column(columnDefinition=("varchar(255) comment '备注'"))
    private String remark;

    @Column(columnDefinition=("datetime  default null comment '时间'"))
    private Date addTime;

    @Column
    private Long count=0L;

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

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

    public Long getPerod() {
        return perod;
    }

    public void setPerod(Long perod) {
        this.perod = perod;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}