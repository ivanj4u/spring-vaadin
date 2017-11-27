/*
 * Copyright (c) 2017.
 */

package com.aribanilia.vaadin.component;

import com.aribanilia.vaadin.framework.constants.Constants;
import com.vaadin.ui.Notification;

public class NotificationHelper {

    public static void showNotification(String msg) {
        Notification.show(msg);
    }

    public static void showNotification(String msg, Notification.Type type) {
        Notification.show(msg, type);
    }

    public static void showNotification(Constants.APP_MESSAGE appMessage) {
        Notification.show(appMessage.getCaption(), appMessage.getCaption(), appMessage.getType());
    }

}
