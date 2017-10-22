package com.aribanilia.vaadin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_user", schema = "vaadin")
public class TblUser {

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

    public TblUser(String username) {
        this.username = username;
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
}
