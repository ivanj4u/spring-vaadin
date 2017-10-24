package com.aribanilia.vaadin.dao;

import com.aribanilia.vaadin.entity.TblMenu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MenuDao extends CrudRepository<TblMenu, String> {

    @Override
    @Query(value = "select menu from TblMenu menu order by menu.parentId asc , menu.position asc")
    List<TblMenu> findAll();
}
