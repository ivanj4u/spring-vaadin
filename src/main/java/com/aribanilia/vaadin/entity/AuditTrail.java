package com.aribanilia.vaadin.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public class AuditTrail implements Serializable {

    @Column(name = "create_by", length = 20)
    private String createBy;
    @Column(name = "update_by", length = 20)
    private String updateBy;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", length = 29)
    private Date createDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date", length = 29)
    private Date updateDate;
    @Column(name = "versi", length = 20)
    private Long versi;

    public AuditTrail(){

    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Long getVersi() {
        return versi;
    }

    public void setVersi(Long versi) {
        this.versi = versi;
    }
}
