package com.aribanilia.vaadin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tbl_menu", schema = "vaadin")
public class TblMenu implements Serializable {

    @Id
    @Column(name = "menu_id", length = 100, nullable = false)
    private String menuId;
    @Column(name = "menu_name")
    private String menuName;
    @Column(name = "menu_class")
    private String menuClass;
    @Column(name = "parent_id", nullable = false)
    private long parentId;
    @Column(name = "position", length = 5)
    private Long position;
    @Column(name = "have_child", length = 2)
    private String haveChild;
    @Column(name = "param", length = 200)
    private String param;

    public TblMenu() {
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuClass() {
        return menuClass;
    }

    public void setMenuClass(String menuClass) {
        this.menuClass = menuClass;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }

    public String getHaveChild() {
        return haveChild;
    }

    public void setHaveChild(String haveChild) {
        this.haveChild = haveChild;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
