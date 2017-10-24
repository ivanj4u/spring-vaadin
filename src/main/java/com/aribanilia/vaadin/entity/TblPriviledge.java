package com.aribanilia.vaadin.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tbl_priviledge", schema = "vaadin")
@IdClass(TblPriviledgeId.class)
public class TblPriviledge extends AuditTrail implements Serializable {

    @Id
    @Column(name = "group_id", nullable = false)
    private long groupId;
    @Id
    @Column(name = "menu_id", nullable = false)
    private String menuId;
    @Column(name = "is_view", length = 1, nullable = false)
    private char isView;
    @Column(name = "is_update", length = 1, nullable = false)
    private char isUpdate;
    @Column(name = "is_add", length = 1, nullable = false)
    private char isAdd;
    @Column(name = "is_delete", length = 1, nullable = false)
    private char isDelete;

    public TblPriviledge() {

    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public char getIsView() {
        return isView;
    }

    public void setIsView(char isView) {
        this.isView = isView;
    }

    public char getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(char isUpdate) {
        this.isUpdate = isUpdate;
    }

    public char getIsAdd() {
        return isAdd;
    }

    public void setIsAdd(char isAdd) {
        this.isAdd = isAdd;
    }

    public char getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(char isDelete) {
        this.isDelete = isDelete;
    }


}
