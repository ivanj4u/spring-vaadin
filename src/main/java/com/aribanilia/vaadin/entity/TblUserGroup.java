package com.aribanilia.vaadin.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tbl_user_group", schema = "spring_vaadin")
@IdClass(TblUserGroupId.class)
public class TblUserGroup extends AuditTrail implements Serializable {

    @Id @Column(name = "username", length = 20, nullable = false)
    private String username;
    @Id @Column(name = "group_id", length = 20, nullable = false)
    private long groupId;

    public TblUserGroup(){

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }
}
