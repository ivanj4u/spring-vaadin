package com.aribanilia.vaadin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tbl_menu", schema = "spring_vaadin")
public class TblMenu implements Serializable {

    @Id
    @Column(name = "menu_id", length = 100, nullable = false)
    private String menuId;
    @Column(name = "menu_name")
    private String menuName;
    @Column(name = "menu_class")
    private String menuClass;
    @Column(name = "position", length = 5)
    private Long position;
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

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
