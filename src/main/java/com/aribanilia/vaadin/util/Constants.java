package com.aribanilia.vaadin.util;

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
}
