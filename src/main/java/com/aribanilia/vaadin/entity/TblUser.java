package com.aribanilia.vaadin.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tbl_user", schema = "vaadin")
public class TblUser extends AuditTrail implements Serializable {

    @Id @Column(name = "username", length = 20, nullable = false)
    private String username;
    @Column(name = "password", length = 50)
    private String password;
    @Column(name = "name", length = 200)
    private String name;
    @Column(name = "email", length = 100)
    private String email;
    @Column(name = "phone", length = 20)
    private String phone;
    /**
     * 0 = Tidak Aktif
     * 1 = Aktif
     * 2 = Blokir
     */
    @Column(name = "status", length = 2)
    private String status;
    @Temporal(TemporalType.DATE)
    @Column(name = "start_time")
    private Date startTime;
    @Temporal(TemporalType.DATE)
    @Column(name = "end_time")
    private Date endTime;
    @Column(name = "login_fail_count", length = 2)
    private Integer loginFailCount;
    @Temporal(TemporalType.DATE)
    @Column(name = "last_login")
    private Date lastLogin;

    public TblUser(){
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 0 = Tidak Aktif
     * 1 = Aktif
     * 2 = Blokir
     */
    public String getStatus() {
        return status;
    }

    /**
     * 0 = Tidak Aktif
     * 1 = Aktif
     * 2 = Blokir
     */
    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getLoginFailCount() {
        return loginFailCount;
    }

    public void setLoginFailCount(Integer loginFailCount) {
        this.loginFailCount = loginFailCount;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }
}
