/*
 * @(#) NetworkException.java 2014年11月21日
 * 
 * Copyright 2010 NetEase.com, Inc. All rights reserved.
 */
package httpclient;

/**
 *
 * @author hzdingyong
 * @version 2014年11月21日
 */
public class ApiNotAvailableException extends Exception {
    private static final long serialVersionUID = -1214339681014242774L;

    public ApiNotAvailableException() {
        super();
    }

    public ApiNotAvailableException(String msg) {
        super(msg);
    }

    public ApiNotAvailableException(Throwable cause) {
        super(cause);
    }

    public ApiNotAvailableException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
