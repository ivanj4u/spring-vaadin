package com.aribanilia.vaadin.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class TblUserGroupId implements Serializable {

    private String username;
    private long groupId;

    public TblUserGroupId() {
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
