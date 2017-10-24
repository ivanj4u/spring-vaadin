package com.aribanilia.vaadin.dao;

import com.aribanilia.vaadin.entity.TblSession;
import org.springframework.data.repository.CrudRepository;

public interface SessionDao extends CrudRepository<TblSession, String> {
}
