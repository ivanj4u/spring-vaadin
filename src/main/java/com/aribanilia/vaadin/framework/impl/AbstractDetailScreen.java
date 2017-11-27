/*
 * Copyright (c) 2017.
 */

package com.aribanilia.vaadin.framework.impl;

import com.aribanilia.vaadin.framework.constants.Constants;
import com.aribanilia.vaadin.framework.listener.DetailCallbackListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import org.vaadin.dialogs.ConfirmDialog;

public abstract class AbstractDetailScreen extends AbstractScreen {

    protected DetailCallbackListener listener;
    protected Button btnSave, btnCancel;
    protected HorizontalLayout buttonBar;

    public AbstractDetailScreen(DetailCallbackListener listener) {
        this.listener = listener;
    }

    public AbstractDetailScreen() {
    }

    public void setListener(DetailCallbackListener listener) {
        this.listener = listener;
    }

    protected void initButton() {
        buttonBar = new HorizontalLayout();
        buttonBar.setSpacing(true);
        buttonBar.addComponent(btnSave = new Button("Simpan"));
        buttonBar.addComponent(btnCancel = new Button("Batal"));

        btnSave.addListener(event -> {
            ConfirmDialog.show(getUI(), Constants.CAPTION_MESSAGE.CONFIRM,
                    Constants.CAPTION_MESSAGE.QUESTION_SAVE, "Ya", "Tidak", confirmDialog -> {
                        if (confirmDialog.isConfirmed()) {
                            doSave();
                        }
                    });
        });

        btnCancel.addListener(event -> {
            doCancel();
        });
    }

    protected void doCancel() {
        if (listener != null)
            listener.onCancel();
    }

    @Override
    protected void initComponents() {

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

    protected abstract void doSave();
    protected abstract void setContentById(Object pojo);

}
