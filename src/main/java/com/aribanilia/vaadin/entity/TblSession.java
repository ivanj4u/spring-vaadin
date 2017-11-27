package com.aribanilia.vaadin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tbl_session", schema = "spring_vaadin")
public class TblSession extends AuditTrail implements Serializable {

    @Id @Column(name = "username", length = 20, nullable = false)
    private String username;
    @Column(name = "session_id", length = 32)
    private String sessionId;
    @Column(name = "ip", length = 20)
    private String ip;

    public TblSession() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
