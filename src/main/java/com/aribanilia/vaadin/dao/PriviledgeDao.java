package com.aribanilia.vaadin.dao;

import com.aribanilia.vaadin.entity.TblPriviledge;
import com.aribanilia.vaadin.entity.TblPriviledgeId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PriviledgeDao extends CrudRepository<TblPriviledge, TblPriviledgeId> {

    List<TblPriviledge> findByGroupId(Long groupId);

}
