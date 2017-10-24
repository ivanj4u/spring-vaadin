package com.aribanilia.vaadin.service;

import com.aribanilia.vaadin.dao.PriviledgeDao;
import com.aribanilia.vaadin.dao.SessionDao;
import com.aribanilia.vaadin.dao.UserDao;
import com.aribanilia.vaadin.dao.UserGroupDao;
import com.aribanilia.vaadin.entity.TblPriviledge;
import com.aribanilia.vaadin.entity.TblSession;
import com.aribanilia.vaadin.entity.TblUser;
import com.aribanilia.vaadin.entity.TblUserGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServices {
    @Autowired private SessionDao daoSession;
    @Autowired private UserDao daoUser;
    @Autowired private UserGroupDao daoUserGroup;
    @Autowired private PriviledgeDao daoPriviledge;

    private static final Logger logger = LoggerFactory.getLogger(UserServices.class);

    public TblUser login(String username, String password) {
        logger.info("Start Services login : " + username + " - " + password);
        TblUser user = null;
        try {
            user = daoUser.findByUsernameAndPassword(username, password);
            if (user != null)
                logger.info("User Found : " + user.getUsername() + " - " + user.getName());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        logger.info("End Services login : " + username + " - " + password);
        return user;
    }

    public void sessionLogin(String username, String sessionId, String ip) {
        logger.info("Start Services sessionLogin : " + username);
        TblSession session = null;
        try {
            session = daoSession.findOne(username);
            if (session == null) {
                session.setUsername(username);
                session.setIp(ip);
                session.setSessionId(sessionId);
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        logger.info("End Services sessionLogin : " + username);
    }

    public boolean sessionCheck(String username, String sessionId) {
        logger.info("Start Services sessionCheck : " + username);
        boolean valid = true;
        try {
            TblSession session = daoSession.findOne(username);
            if (!session.getSessionId().equals(sessionId))
                valid = false;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        logger.info("End Services sessionCheck : " + username);
        return valid;
    }

    public List<TblUserGroup> getUserGroup(String username) {
        logger.info("Start Services getUserGroup : " + username);
        List<TblUserGroup> list = null;
        try {
            list = daoUserGroup.findByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        logger.info("End Services getUserGroup : " + username);
        return list;
    }

    public List<TblPriviledge> getGroupPriviledge(Long groupId) {
        logger.info("Start Services getGroupPriviledge : " + groupId);
        List<TblPriviledge> list = null;
        try {
            list = daoPriviledge.findByGroupId(groupId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        logger.info("End Services getGroupPriviledge : " + groupId);
        return list;
    }

}
