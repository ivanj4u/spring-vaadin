package com.aribanilia.vaadin.service;

import com.aribanilia.vaadin.dao.UserDao;
import com.aribanilia.vaadin.entity.TblUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServices {
    @Autowired
    private UserDao dao;

    private static final Logger logger = LoggerFactory.getLogger(UserServices.class);

    public TblUser login(String username, String password) {
        logger.info("Start Services login : " + username + " - " + password);
        TblUser user = null;
        try {
            user = dao.findByUsernameAndPassword(username, password);
            if (user != null)
                logger.info("User Found : " + user.getUsername() + " - " + user.getName());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        logger.info("End Services login : " + username + " - " + password);
        return user;
    }

}
