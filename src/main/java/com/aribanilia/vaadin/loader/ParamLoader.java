package com.aribanilia.vaadin.loader;

import com.aribanilia.vaadin.entity.TblParam;
import com.aribanilia.vaadin.framework.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ParamLoader {

    private static ConcurrentHashMap<String, TblParam> h = new ConcurrentHashMap<String, TblParam>();

    @SuppressWarnings("unchecked")
    public static void load() throws Exception {
        h.clear();
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            List<TblParam> list = session.createCriteria(TblParam.class).list();
            for (TblParam param : list)
                h.put(param.getKey(), param);

            System.out.println("Loader Param : " + h);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if ((session != null) && (session.isOpen()))
                session.close();
        }
    }

    public static String getParam(String key) {
        TblParam param = h.get(key);
        if (param == null) {
            Session session = null;
            try {
                param = getTblParam(key);
                if (param != null) {
                    synchronized (h) {
                        h.put(key, param);
                    }
                    return param.getValue();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen())
                    session.close();
            }
        } else {
            return param.getValue();
        }
        return null;
    }

    public static String getParam(Session session, String key) {
        try {
            TblParam param = getTblParam(session, key);
            if (param != null)
                return param.getValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void replace(String key, String value) {
        Session session = null;
        Transaction trx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            trx = session.beginTransaction();
            TblParam param = session.get(TblParam.class, key);
            param.setValue(value);
            param.setUpdateDate(new Date());
            session.update(param);
            trx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (trx != null)
                trx.rollback();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
    }

    public static void replace(String key, String value, String description) {
        Session session = null;
        Transaction trx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            trx = session.beginTransaction();
            TblParam param = session.get(TblParam.class, key);
            param.setValue(value);
            param.setDescription(description);
            param.setUpdateDate(new Date());
            session.update(param);
            trx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (trx != null)
                trx.rollback();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
    }

    public static TblParam getTblParam(String key) {
        TblParam param = h.get(key);
        if (param != null)
            return param;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            param = (TblParam) session.get(TblParam.class, key);
            h.put(key, param);
            return param;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return null;
    }

    public static TblParam getTblParam(Session session, String key) {
        TblParam param = h.get(key);
        if (param != null)
            return param;
        try {
            param = (TblParam) session.get(TblParam.class, key);
            h.put(key, param);
            return param;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void replace(TblParam param) {
        replace(param.getKey(), param.getValue(), param.getDescription());
    }

    public static void setParam(TblParam param) {
        synchronized (h) {
            h.put(param.getKey(), param);
        }
    }

}
