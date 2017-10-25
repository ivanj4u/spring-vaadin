package com.aribanilia.vaadin.model;

import com.aribanilia.vaadin.loader.MenuLoader;
import com.vaadin.navigator.View;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Panel;

@SpringView
public abstract class AbstractScreen extends Panel implements PriviledgeModel, View {

    protected boolean isInit = false;
    protected int mode = -1;
    protected Object pojo;
    protected String param;

    public AbstractScreen() {
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public void show() {
        if (isInit) {
            onShow();
            return;
        }
        isInit = true;
        setContent(null);
        setWidth(100, Unit.PERCENTAGE);
        setSizeFull();
        beforeInitComponent();
        initComponents();
        afterInitComponent();
        onShow();
        Responsive.makeResponsive(this);
    }

    protected void beforeInitComponent() {

    }

    protected void afterInitComponent() {

    }

    protected void onShow() {

    }

    protected abstract void initComponents();

    public boolean setMode(int mode) {
        this.mode = mode;
        if (mode == MODE_NEW) {
            if (isAuthorizedToAdd()) {
                setModeNew();
                return true;
            }
        } else if (mode == MODE_UPDATE) {
            if (isAuthorizedToUpdate()) {
                setModeUpdate();
                return true;
            }
        } else if (mode == MODE_VIEW) {
            if (isAuthorizedToView()) {
                setModeView();
                return true;
            }
        }
        return false;
    }

    public int getMode() {
        return this.mode;
    }

    public abstract void setModeNew();

    public abstract void setModeUpdate();

    public abstract void setModeView();

    @Override
    public boolean isAuthorizedToView() {
        MenuLoader loader = VaadinSession.getCurrent().getAttribute(MenuLoader.class);
        return loader.getPriviledge(getClass().getName()) != null
                ? loader.getPriviledge(getClass().getName()).getIsView() == '1' : false;
    }

    @Override
    public boolean isAuthorizedToUpdate() {
        boolean b = false;
        MenuLoader loader = VaadinSession.getCurrent().getAttribute(MenuLoader.class);
        if (loader.getPriviledge(getClass().getName()) != null) {
            b = loader.getPriviledge(getClass().getName()).getIsUpdate() == '1';
        }
        return b;
    }

    @Override
    public boolean isAuthorizedToDelete() {
        MenuLoader loader = VaadinSession.getCurrent().getAttribute(MenuLoader.class);
        return loader.getPriviledge(getClass().getName()) != null
                ? loader.getPriviledge(getClass().getName()).getIsDelete() == '1' : false;
    }

    @Override
    public boolean isAuthorizedToAdd() {
        MenuLoader loader = VaadinSession.getCurrent().getAttribute(MenuLoader.class);
        return loader.getPriviledge(getClass().getName()) == null
                || (loader.getPriviledge(getClass().getName()) != null && loader.getPriviledge(getClass().getName())
                .getIsAdd() == '1');
    }
}
