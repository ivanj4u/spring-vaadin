/*
 * Copyright (c) 2017.
 */

package com.aribanilia.vaadin.framework.constants;

import com.vaadin.ui.Notification;

public class Constants {

    public static final String APPLICATION_NAME = "Vaading Spring";

    public interface STATUS_USER {
        String NON_ACTIVE = "0";
        String ACTIVE = "1";
        String BLOKIR = "2";
    }

    public interface APP_CONFIG{
        String ACCOUNTING_IA_CLASSNAME="accounting-ia-classname";
        String ACCOUNTING_JURNAL_CLASSNAME="accounting-jurnal-classname";
        String ACCOUNTING_UNAUTH_CLASSNAME="accounting-unauth-classname";
        String ACCOUNTING_REVERSE_CLASSNAME="accounting-reverse-classname";

        String CACHE_ENABLE = "cache-enable";
        String SESSION_FACTORY_CLASS_NAME="session-factory-classname";
        String QUERY_TO="query-to";
        String SESSION_FACTORY_CACHE_CLASS_NAME = "session-factory-cache-classname";
    }
    public interface APP_CONFIG_VALUE{
        String QUERY_TO_DB_ONLY="db";
        String QUERY_TO_CACHE_ONLY="cache";
        String QUERY_TO_BOTH="both";
    }

    public interface CAPTION_MESSAGE {
        String INFO = "Informasi";
        String WARN = "Peringatan";
        String ERROR = "Kesalahan";
        String CONFIRM = "Konfirmasi";

        String QUESTION_SAVE = "Apakah Anda yakin untuk menyimpan data ini ?";
        String QUESTION_DELETE = "Apakah Anda yakin untuk menghapus data ini ?";
    }

    public interface APP_MODE {
        int MODE_NEW = 0;
        int MODE_UPDATE = 1;
        int MODE_VIEW = 2;
    }

    public enum APP_MESSAGE {
        INFO_DATA_NOT_EXIST(CAPTION_MESSAGE.INFO, "Data tidak ditemukan", Notification.Type.HUMANIZED_MESSAGE),
        INFO_DATA_SAVED(CAPTION_MESSAGE.INFO, "Data berhasil disimpan", Notification.Type.HUMANIZED_MESSAGE),
        INFO_DATA_UPDATED(CAPTION_MESSAGE.INFO, "Data berhasil diubah", Notification.Type.HUMANIZED_MESSAGE),
        INFO_DATA_DELETED(CAPTION_MESSAGE.INFO, "Data berhasil dihapus", Notification.Type.HUMANIZED_MESSAGE),

        WARN_DATA_MANDATORY(CAPTION_MESSAGE.WARN, "Mohon mengisi data terlebih dahulu", Notification.Type.WARNING_MESSAGE),
        WARN_DATA_IS_EXIST(CAPTION_MESSAGE.WARN, "Data sudah ada", Notification.Type.WARNING_MESSAGE),
        WARN_DATA_MUST_BE_SELECTED(CAPTION_MESSAGE.WARN, "Mohon memilih data terlebih dahulu", Notification.Type.WARNING_MESSAGE),
        WARN_DATA_EXPIRED(CAPTION_MESSAGE.WARN, "Data sudah diupdate oleh user lain, lakukan pencarian ulang", Notification.Type.WARNING_MESSAGE),
        WARN_DATA_IS_ACTIVE(CAPTION_MESSAGE.WARN, "Data telah aktif dan tidak dapat dihapus", Notification.Type.WARNING_MESSAGE),

        ERR_DATA_NOT_SAVE(CAPTION_MESSAGE.ERROR, "Data gagal disimpan", Notification.Type.ERROR_MESSAGE),
        ERR_DATA_NOT_DELETE(CAPTION_MESSAGE.ERROR, "Data gagal dihapus", Notification.Type.ERROR_MESSAGE),
        ERR_DATA_IN_VALID(CAPTION_MESSAGE.ERROR, "Data tidak valid, lakukan pencarian ulang", Notification.Type.ERROR_MESSAGE),
        ERR_DATA_SEARCH(CAPTION_MESSAGE.ERROR, "Pencarian data gagal", Notification.Type.ERROR_MESSAGE),
        ERR_DATA_GET_DETAIL(CAPTION_MESSAGE.ERROR, "Pengambilan data gagal", Notification.Type.ERROR_MESSAGE),
        ERR_DATA_USED_BY_ANOTHER_USER(CAPTION_MESSAGE.ERROR, "Data sedang dipakai oleh user lain, cobalah beberapa saat lagi", Notification.Type.ERROR_MESSAGE),
        ERR_DATA_PRINT(CAPTION_MESSAGE.ERROR, "Cetak dokumen gagal", Notification.Type.ERROR_MESSAGE);

        private String caption;
        private String message;
        private Notification.Type type;
        APP_MESSAGE(String caption, String message, Notification.Type type) {
            setCaption(caption);
            setMessage(message);
            setType(type);

        }
        public String getCaption() {
            return caption;
        }
        public void setCaption(String caption) {
            this.caption = caption;
        }
        public String getMessage() {
            return message;
        }
        public void setMessage(String message) {
            this.message = message;
        }
        public Notification.Type getType() {
            return type;
        }
        public void setType(Notification.Type type) {
            this.type = type;
        }
    }
}
