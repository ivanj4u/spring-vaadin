/*
 * Copyright (c) 2017.
 */

package com.aribanilia.vaadin.framework.exception;

public class UnsafeSessionCloseException extends  Exception {

    public UnsafeSessionCloseException() {
        super();
    }

    public UnsafeSessionCloseException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsafeSessionCloseException(String message) {
        super(message);
    }

    public UnsafeSessionCloseException(Throwable cause) {
        super(cause);
    }
}
