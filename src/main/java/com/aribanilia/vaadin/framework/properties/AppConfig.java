/*
 * Copyright (c) 2017.
 */

package com.aribanilia.vaadin.framework.properties;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class AppConfig {
    public final static Properties properties = new Properties();

    public static String getApplicationParameter(String key) throws Exception {
        if (properties.isEmpty()) {
            InputStream in = null;
            try {
                String path = System.getProperty("user.dir").replaceAll("\\\\", "\\/")
                        + "/cfg/appconfig.properties";
                in = new FileInputStream(path);
                properties.load(in);
            } finally {
                if (in != null)
                    in.close();
            }
        }
        return properties.getProperty(key);
    }
}
