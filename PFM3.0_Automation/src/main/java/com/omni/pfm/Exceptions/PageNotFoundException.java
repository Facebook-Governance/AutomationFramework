/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.Exceptions;

/**
 * 
 * @author msuhaib
 *
 */
public class PageNotFoundException extends RuntimeException {

    /**
     * @serial
     */
    private static final long serialVersionUID = -396199612179792632L;

    public PageNotFoundException() {
	super();
    }

    public PageNotFoundException(String message) {
	super(message);
    }

}
