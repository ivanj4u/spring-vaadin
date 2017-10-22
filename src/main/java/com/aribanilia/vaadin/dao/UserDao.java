package com.aribanilia.vaadin.dao;

import com.aribanilia.vaadin.entity.TblUser;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<TblUser, String> {

    TblUser findByUsernameAndPassword(String username, String password);
}
