/*
 * Copyright (c) 2017.
 */

package com.aribanilia.vaadin.view.admin;

import com.aribanilia.vaadin.component.NotificationHelper;
import com.aribanilia.vaadin.entity.TblUser;
import com.aribanilia.vaadin.framework.constants.Constants;
import com.aribanilia.vaadin.framework.db.hibernate.Criteria;
import com.aribanilia.vaadin.framework.db.hibernate.Session;
import com.aribanilia.vaadin.framework.db.plugin.PersistentPlugin;
import com.aribanilia.vaadin.framework.impl.AbstractDetailScreen;
import com.aribanilia.vaadin.framework.impl.AbstractSearchScreen;
import com.aribanilia.vaadin.util.ValidationHelper;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.v7.data.Item;
import com.vaadin.v7.data.util.IndexedContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@SpringView
public class ListUserView extends AbstractSearchScreen implements View {
    private TextField txtUsername, txtName;

    private AbstractDetailScreen detailScreen;

    private final String USERNAME = "Id Pengguna";
    private final String NAME = "Nama";
    private final String EMAIL = "Email";
    private final String TELP = "No Telp";

    private static final Logger logger = LoggerFactory.getLogger(ListUserView.class);

    @Override
    protected int getGridColumn() {
        return 2;
    }

    @Override
    protected int getGridRow() {
        return 3;
    }

    @Override
    protected void initGridComponent() {
        grid.addComponent(new Label("Id Pengguna"), 0, row);
        grid.addComponent(txtUsername = new TextField(),1, row++);
        grid.addComponent(new Label("Nama"), 0, row);
        grid.addComponent(txtName = new TextField(),1, row++);
    }

    @Override
    protected void initIndexedContainer() {
        indexedContainer = new IndexedContainer();
        indexedContainer.addContainerProperty(USERNAME, String.class, null);
        indexedContainer.addContainerProperty(NAME, String.class, null);
        indexedContainer.addContainerProperty(EMAIL, String.class, null);
        indexedContainer.addContainerProperty(TELP, String.class, null);
    }

    @Override
    protected Object getTableId() {
        return USERNAME;
    }

    @Override
    protected AbstractDetailScreen getDetailScreen() {
        if (detailScreen == null) {
            try {
                detailScreen = (AbstractDetailScreen) Class.forName("com.aribanilia.vaadin.view.admin.UserView").newInstance();
                detailScreen.setListener(this);
            } catch (Exception e) {
                logger.error(e.getMessage());
                NotificationHelper.showNotification(Constants.APP_MESSAGE.ERR_DATA_GET_DETAIL);
            }
        }
        return detailScreen;
    }

    @Override
    protected String getDetailScreenTitle() {
        return "Pendaftaran Pengguna";
    }

    @Override
    protected String getDetailScreenWidth() {
        return "50%";
    }

    @Override
    protected String getDetailScreenHeight() {
        return "70%";
    }

    @Override
    protected void doSearch() {
        Session session = null;
        try {
            indexedContainer.removeAllItems();
            session = PersistentPlugin.getSessionFactory().openSession();
            Criteria criteria = session.createCriteria(TblUser.class);
            if (ValidationHelper.validateFieldWithoutWarn(txtUsername))
                criteria.addRestriction(criteria.getRestriction().eq("username", txtUsername.getValue()));
            if (ValidationHelper.validateFieldWithoutWarn(txtName))
                criteria.addRestriction(criteria.getRestriction().like("username", "%" + txtName.getValue() + "%"));
            List<TblUser> list = criteria.list();
            session.close();
            for (TblUser user : list) {
                parsToTable(indexedContainer.addItem(), user);
            }
            table.refreshRowCache();
            setRowId(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            NotificationHelper.showNotification(Constants.APP_MESSAGE.ERR_DATA_SEARCH);
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
    }

    private void parsToTable(Object itemId, TblUser user) {
        Item item = indexedContainer.getItem(itemId);
        item.getItemProperty(USERNAME).setValue(user.getUsername());
        item.getItemProperty(NAME).setValue(user.getName());
        item.getItemProperty(EMAIL).setValue(user.getEmail());
        item.getItemProperty(TELP).setValue(user.getPhone());
    }

    @Override
    protected void doReset() {
        txtUsername.setValue("");
    }

    @Override
    protected boolean validateSearchRequired() {
        return false;
    }
}
