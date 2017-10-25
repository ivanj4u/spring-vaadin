package com.aribanilia.vaadin.loader;

public class Loader {

    public static void load() {
        try {
            ParamLoader.load();
            MenuLoader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
