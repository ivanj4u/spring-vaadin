package com.aribanilia.vaadin.framework;

import com.aribanilia.vaadin.entity.TblSession;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class LoginUtil {

    private static final Logger logger = LoggerFactory.getLogger(LoginUtil.class);

    public static void sessionLogin(Session session, String username, String sessionId, String ip) {
        logger.info("Start Services sessionLogin : " + username);
        TblSession obj = session.get(TblSession.class, username);
        if (obj == null) {
            obj = new TblSession();
            obj.setUsername(username);
            obj.setIp(ip);
            obj.setSessionId(sessionId);
            obj.setAuditCreate(username);
        } else {
            obj.setIp(ip);
            obj.setSessionId(sessionId);
            obj.setAuditUpdate(username);
        }
        session.saveOrUpdate(obj);
        logger.info("End Services sessionLogin : " + username);
    }

    public static boolean sessionCheck(String username, String sessionId) {
        logger.info("Start Services sessionCheck : " + username);
        boolean valid = true;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            TblSession tblSession = session.get(TblSession.class, username);
            if (!tblSession.getSessionId().equals(sessionId))
                valid = false;
        } catch (Exception e) {
            if (session != null && session.isOpen())
                session.close();
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        logger.info("End Services sessionCheck : " + username);
        return valid;
    }
}
