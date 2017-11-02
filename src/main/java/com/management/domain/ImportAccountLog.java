package com.management.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ImportAccountLog {

    @Id
    @GeneratedValue
    private Long id;

    @Lob
    @Basic(fetch=FetchType.LAZY)
    @Column(columnDefinition=("longtext  default null comment '内容'"))
    private String context;

    @Column(columnDefinition=("datetime  default null comment '时间'"))
    private Date importDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Date getImportDate() {
        return importDate;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }
}