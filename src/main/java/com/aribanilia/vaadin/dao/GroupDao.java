package com.aribanilia.vaadin.dao;

import com.aribanilia.vaadin.entity.TblGroup;
import org.springframework.data.repository.CrudRepository;

public interface GroupDao extends CrudRepository<TblGroup, Long> {
}
