/*
 * Copyright (c) 2017.
 */

package com.aribanilia.vaadin.framework.impl;

import com.aribanilia.vaadin.component.NotificationHelper;
import com.aribanilia.vaadin.framework.constants.Constants;
import com.aribanilia.vaadin.framework.listener.DetailCallbackListener;
import com.vaadin.server.Responsive;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public abstract class AbstractSearchScreen extends AbstractScreen implements DetailCallbackListener {

    protected DetailCallbackListener listener;
    protected GridLayout grid;
    protected Button btnAdd, btnEdit, btnDelete, btnView, btnSearch, btnReset, btnDetail;
    protected int row = 0;
    protected Window windowDetail = null;

    private Object rowId;

    protected GridLayout totalDetail = null;
    protected HorizontalLayout buttonDetail;

    public AbstractSearchScreen(DetailCallbackListener listener) {
        this.listener = listener;
    }

    public AbstractSearchScreen() {
    }

    @Override
    protected void initComponents() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.addStyleName(ValoTheme.PANEL_WELL);

        layout.addComponent(initPencarian());
        layout.addComponent(initTable());

        setContent(layout);
        Responsive.makeResponsive(this);
    }

    private Panel initPencarian() {
        Panel panel = new Panel("Kriteria Pencarian");
        panel.setStyleName(ValoTheme.PANEL_WELL);
        panel.setSizeFull();

        grid = new GridLayout(getGridColumn(), getGridRow());
        grid.setSpacing(false);

        HorizontalLayout buttonBar = new HorizontalLayout();
        buttonBar.addComponent(btnSearch = new Button("Cari"));
        buttonBar.addComponent(btnReset = new Button("Reset"));

        btnSearch.addListener(event -> {
            if (!validateSearchRequired()) {
                NotificationHelper.showNotification(Constants.APP_MESSAGE.WARN_DATA_MANDATORY);
                return;
            }
            if (!validateValue()) {
                return;
            }
            doSearch();

            if (getTableSize() == 0)
                NotificationHelper.showNotification(Constants.APP_MESSAGE.INFO_DATA_NOT_EXIST);
        });

        btnReset.addListener(event -> {
            doReset();
        });

        initGridComponent();
        grid.addComponent(buttonBar, 1, row++);
        grid.setComponentAlignment(buttonBar, Alignment.MIDDLE_LEFT);
        panel.setContent(grid);

        return panel;
    }

    protected Panel initTable() {
        Panel panel = new Panel("Hasil Pencarian");
        panel.addStyleName(ValoTheme.PANEL_WELL);
        panel.setSizeFull();

        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setMargin(true);

        initButtonDetailScreen();

        layout.addComponent(buttonDetail);
        layout.addComponent(initTableData());
        layout.setComponentAlignment(buttonDetail, Alignment.MIDDLE_RIGHT);
        layout.setExpandRatio(layout.getComponent(1), 1.0f);

        initTotalDetail();
        if (totalDetail != null)
            layout.addComponent(totalDetail);

        panel.setContent(layout);
        return panel;
    }

    protected void initSetAlignmentTable() {

    }

    protected void initTotalDetail() {

    }

    protected void initButtonDetailScreen() {
        buttonDetail = new HorizontalLayout();
        buttonDetail.setSpacing(false);
        buttonDetail.addComponent(btnAdd = new Button("Tambah"));
        buttonDetail.addComponent(btnEdit = new Button("Ubah"));
        buttonDetail.addComponent(btnView = new Button("Lihat"));
        buttonDetail.addComponent(btnDelete = new Button("Hapus"));
        buttonDetail.addComponent(btnDetail = new Button("Pilih"));
        btnDetail.setVisible(false);

        btnAdd.setVisible(this.isAuthorizedToAdd());
        btnEdit.setVisible(this.isAuthorizedToUpdate());
        btnView.setVisible(this.isAuthorizedToView());
        btnDelete.setVisible(this.isAuthorizedToDelete());

        btnAdd.addListener(event -> {
            doAdd();
        });

        btnEdit.addListener(event -> {
            doEdit();
        });

        btnView.addListener(event -> {
            doView();
        });

        btnDelete.addListener(event -> {
            doDelete();
        });

        btnDetail.addListener(event -> {
            doDetail();
        });

    }

    protected void showDetailScreen() {
        if (windowDetail == null) {
            windowDetail = new Window(getDetailScreenTitle());
            windowDetail.setContent(getDetailScreen());
            getDetailScreen().setSizeFull();
            windowDetail.setModal(getModality());
            windowDetail.setWidth(getDetailScreenWidth());
            windowDetail.setHeight(getDetailScreenHeight());
        }
        getUI().addWindow(windowDetail);
        windowDetail.center();
        getDetailScreen().show();
    }

    protected boolean getModality() {
        return false;
    }

    protected void doAdd() {
        getDetailScreen().setMode(Constants.APP_MODE.MODE_NEW);
        showDetailScreen();
    }

    protected void doEdit() {
        if (getRowId() == null) {
            NotificationHelper.showNotification(Constants.APP_MESSAGE.WARN_DATA_MUST_BE_SELECTED);
            return;
        }
        getDetailScreen().setMode(Constants.APP_MODE.MODE_UPDATE);
        showDetailScreen();
        getDetailScreen().setContentById(getRowId());
    }

    protected void doView() {
        if (getRowId() == null) {
            NotificationHelper.showNotification(Constants.APP_MESSAGE.WARN_DATA_MUST_BE_SELECTED);
            return;
        }
        getDetailScreen().setMode(Constants.APP_MODE.MODE_VIEW);
        showDetailScreen();
        getDetailScreen().setContentById(getRowId());
    }

    protected void doDelete() {
    }

    protected void doDetail() {
    }

    protected boolean validateValue() {
        return true;
    }

    @Override
    public void setModeNew() {

    }

    @Override
    public void setModeUpdate() {

    }

    @Override
    public void setModeView() {

    }

    @Override
    public void onAfterAdded(Object pojo) {
        getUI().removeWindow(windowDetail);
        NotificationHelper.showNotification(Constants.APP_MESSAGE.INFO_DATA_SAVED);
    }

    @Override
    public void onAfterUpdated(Object pojo) {
        getUI().removeWindow(windowDetail);
        NotificationHelper.showNotification(Constants.APP_MESSAGE.INFO_DATA_UPDATED);
    }

    @Override
    public void onAfterViewed() {
        onCancel();
    }

    @Override
    public void onCancel() {
        getUI().removeWindow(windowDetail);
        setRowId(null);
    }

    public Object getRowId() {
        return rowId;
    }

    public void setRowId(Object rowId) {
        this.rowId = rowId;
    }

    protected abstract int getGridColumn();
    protected abstract int getGridRow();
    protected abstract void initGridComponent();
    protected abstract Component initTableData();
    protected abstract int getTableSize();

    protected abstract AbstractDetailScreen getDetailScreen();
    protected abstract String getDetailScreenTitle();
    protected abstract String getDetailScreenWidth();
    protected abstract String getDetailScreenHeight();

    protected abstract void doSearch();
    protected abstract void doReset();

    protected abstract boolean validateSearchRequired();
}
