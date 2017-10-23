/*
 * Copyright (c) 2017.
 */

package com.aribanilia.vaadin.config;

import com.aribanilia.vaadin.VaadinApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

public class VaadinServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(VaadinApplication.class);
    }
}
