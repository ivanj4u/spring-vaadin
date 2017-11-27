package com.aribanilia.vaadin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tbl_group", schema = "spring_vaadin")
public class TblGroup extends AuditTrail implements Serializable {

    @Id @Column(name = "group_id", length = 20, nullable = false)
    private long groupId;
    @Column(name = "group_name", length = 200)
    private String groupName;

    public TblGroup(){

    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
