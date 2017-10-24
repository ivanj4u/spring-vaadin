package com.aribanilia.vaadin.dao;

import com.aribanilia.vaadin.entity.TblUserGroup;
import com.aribanilia.vaadin.entity.TblUserGroupId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserGroupDao extends CrudRepository<TblUserGroup, TblUserGroupId> {

    List<TblUserGroup> findByUsername(String username);
}
