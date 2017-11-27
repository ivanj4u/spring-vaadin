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
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@SpringView
public class ListUserView extends AbstractSearchScreen implements View {
    private TextField txtUsername, txtName;

    private Grid<TblUser> table;
    private List<TblUser> list = new ArrayList<>();

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
    protected Component initTableData() {
        table = new Grid<>();
        table.setItems(list);
        table.setSelectionMode(Grid.SelectionMode.SINGLE);
        table.addStyleName(ValoTheme.TABLE_COMPACT);
        table.setWidth("100%");

        table.addColumn(TblUser::getUsername).setCaption(USERNAME);
        table.addColumn(TblUser::getName).setCaption(NAME);
        table.addColumn(TblUser::getEmail).setCaption(EMAIL);
        table.addColumn(TblUser::getPhone).setCaption(TELP);

        return table;
    }

    @Override
    protected int getTableSize() {
        return list.size();
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
            session = PersistentPlugin.getSessionFactory().openSession();
            Criteria criteria = session.createCriteria(TblUser.class);
            if (ValidationHelper.validateFieldWithoutWarn(txtUsername))
                criteria.addRestriction(criteria.getRestriction().eq("username", txtUsername.getValue()));
            if (ValidationHelper.validateFieldWithoutWarn(txtName))
                criteria.addRestriction(criteria.getRestriction().like("username", "%" + txtName.getValue() + "%"));
            list = criteria.list();
            session.close();
            table.setItems(list);
            setRowId(null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            NotificationHelper.showNotification(Constants.APP_MESSAGE.ERR_DATA_SEARCH);
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
    }

    @Override
    protected void doReset() {
        txtUsername.setValue("");
        txtName.setValue("");
    }

    @Override
    protected boolean validateSearchRequired() {
        return true;
    }
}
