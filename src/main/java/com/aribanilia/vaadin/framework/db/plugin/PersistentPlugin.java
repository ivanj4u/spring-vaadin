/*
 * Copyright (c) 2017.
 */

package com.aribanilia.vaadin.framework.db.plugin;

import com.aribanilia.vaadin.framework.db.hibernate.SessionFactory;
import com.aribanilia.vaadin.framework.properties.AppConfig;
import com.aribanilia.vaadin.framework.constants.Constants;

public class PersistentPlugin {

    private static SessionFactory sf = null;
    private static SessionFactory sfCache = null;
    private static final Object lock = new Object();

    public static SessionFactory getSessionFactory() throws Exception {
        if (sf == null) {
            synchronized (lock) {
                sf = (SessionFactory) Class.forName(AppConfig.getApplicationParameter(Constants.APP_CONFIG.SESSION_FACTORY_CLASS_NAME)).newInstance();
            }
        }
        return sf;
    }

    public static SessionFactory getSessionFactory(boolean cache) throws Exception {
        if (sfCache == null) {
            if ("1".equals(AppConfig.getApplicationParameter(Constants.APP_CONFIG.CACHE_ENABLE))) {
                synchronized (lock) {
                    sfCache = (SessionFactory) Class.forName(AppConfig.getApplicationParameter(Constants.APP_CONFIG.SESSION_FACTORY_CACHE_CLASS_NAME)).newInstance();
                }
            } else {
                sfCache = getSessionFactory();
            }
        }
        return sfCache;
    }

}
