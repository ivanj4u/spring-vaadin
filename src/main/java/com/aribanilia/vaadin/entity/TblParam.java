package com.aribanilia.vaadin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tbl_param", schema = "spring_vaadin")
public class TblParam extends AuditTrail implements Serializable {

    @Id
    @Column(name = "key_", nullable = false, length = 100)
    private String key;
    @Column(name = "value_", nullable = false, length = 600)
    private String value;
    @Column(name = "description_", nullable = false, length = 200)
    private String description;

    public TblParam() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
