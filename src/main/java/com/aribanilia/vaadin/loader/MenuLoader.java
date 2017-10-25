package com.aribanilia.vaadin.loader;

import com.aribanilia.vaadin.entity.TblMenu;
import com.aribanilia.vaadin.entity.TblPriviledge;
import com.aribanilia.vaadin.entity.TblUser;
import com.aribanilia.vaadin.entity.TblUserGroup;
import com.aribanilia.vaadin.framework.HibernateUtil;
import com.aribanilia.vaadin.framework.LoginUtil;
import com.aribanilia.vaadin.model.AbstractScreen;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Notification;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MenuLoader {
    private static Vector<TblMenu> v = new Vector<>();
    private Vector<TblMenu> vSessionedPerUser = new Vector<>();
    private ConcurrentHashMap<String, AbstractScreen> cacheClass = new ConcurrentHashMap<>();

    public static void load() {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Criteria criteria = session.createCriteria(TblMenu.class);
            List<TblMenu> list = criteria.addOrder(Order.asc("parentId")).addOrder(Order.asc("position")).list();
            v.clear();
            v.addAll(list);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public AbstractScreen getScreen(String menuId) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        for (TblMenu menu : v) {
            if (menu.getMenuId().equals(menuId)) {
                if (menu.getMenuClass() == null || menu.getHaveChild().equals("1"))
                    return null;
                TblUser user = VaadinSession.getCurrent().getAttribute(TblUser.class);
                String sessionId = VaadinSession.getCurrent().getSession().getId();
                if (!LoginUtil.sessionCheck(user.getUsername(), sessionId)) {
                    Notification.show("Anda telah keluar", "Anda Telah Keluar/Login dari Komputer Lain!", Notification.Type.HUMANIZED_MESSAGE);
                    VaadinSession.getCurrent().close();
                    return null;
                }
                AbstractScreen obj = cacheClass.get(menu.getMenuId());
                if (obj != null)
                    return obj;
                obj = (AbstractScreen) Class.forName(menu.getMenuClass()).newInstance();
                cacheClass.put(menu.getMenuId(), obj);
                obj.setParam(menu.getParam());
                return obj;
            }
        }
        return null;
    }

    public void resetScreen(String className) {
        List<String> listOfRemoved = new ArrayList<>();
        for (Iterator<String> it = cacheClass.keySet().iterator(); it.hasNext(); ) {
            String key = it.next();
            AbstractScreen screen = cacheClass.get(key);
            if (screen.getClass().getName().equals(className)) {
                listOfRemoved.add(key);
            }
        }
        for (String key : listOfRemoved)
            cacheClass.remove(key);
    }

    private Hashtable<String, TblPriviledge> hSessionedMenuperUser = new Hashtable<>();
    private Hashtable<String, TblPriviledge> hSessionedMenuperUser2 = new Hashtable<>();

    public void setAuthorizedMenu(Session session, TblUser user) {
        hSessionedMenuperUser.clear();
        vSessionedPerUser.removeAllElements();
        vSessionedPerUser.addAll(v);
        Vector<TblMenu> vTemp = new Vector<>();

        Criteria criteria = session.createCriteria(TblUserGroup.class);
        criteria.add(Restrictions.eq("username", user.getUsername()));
        List<TblUserGroup> userGroups = criteria.list();
        for (TblUserGroup userGroup : userGroups) {
            criteria = session.createCriteria(TblPriviledge.class);
            criteria.add(Restrictions.eq("groupId", userGroup.getGroupId()));
            List<TblPriviledge> priviledges = criteria.list();
            for (TblPriviledge p : priviledges) {
                TblPriviledge privPrev = hSessionedMenuperUser.get(p.getMenuId());
                if (privPrev != null) {
                    if (privPrev.getIsAdd() == '0')
                        privPrev.setIsAdd(p.getIsAdd());
                    if (privPrev.getIsDelete() == '0')
                        privPrev.setIsDelete(p.getIsDelete());
                    if (privPrev.getIsUpdate() == '0')
                        privPrev.setIsUpdate(p.getIsUpdate());
                    if (privPrev.getIsView() == '0')
                        privPrev.setIsView(p.getIsView());
                } else {
                    hSessionedMenuperUser.put(p.getMenuId(), p);
                    TblMenu menu = session.get(TblMenu.class, p.getMenuId());
                    if (menu != null && menu.getMenuClass() != null) {
                        hSessionedMenuperUser2.put(menu.getMenuClass(), p);
                    }
                    vTemp.add(menu);
                }
            }
        }
        for (TblMenu menu : v) {
            boolean isShowed = false;
            for (TblMenu menu_ : vTemp) {
                if (menu_.getMenuId().equals(menu.getMenuId())) {
                    isShowed = true;
                    break;
                }
            }
            if (!isShowed) {
                vSessionedPerUser.remove(menu);
            }
        }
    }

    public TblPriviledge getPriviledge(String screenClassName) {
        return hSessionedMenuperUser2.get(screenClassName);
    }

    public Vector<TblMenu> getAuthorizedMenu() {
        return vSessionedPerUser;
    }

    public void clear() {
        hSessionedMenuperUser.clear();
        hSessionedMenuperUser2.clear();
        vSessionedPerUser.removeAllElements();
        cacheClass.clear();
    }
}
