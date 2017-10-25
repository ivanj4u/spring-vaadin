/*
 * Copyright (c) 2017.
 */

package com.aribanilia.vaadin.util;

import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Notification;

public class ValidationHelper {

    public static boolean validateRequired(AbstractField field) {
        if (field.getValue() == null || field.getValue().toString().equals("")) {
            Notification.show("Mohon mengisi field : " + field.getCaption(), Notification.Type.HUMANIZED_MESSAGE);
            return false;
        }
        return true;
    }
}
