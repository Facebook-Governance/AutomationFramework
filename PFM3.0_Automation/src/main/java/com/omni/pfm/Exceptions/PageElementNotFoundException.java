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

public class PageElementNotFoundException extends RuntimeException {

    /**
     * @serial
     */
    private static final long serialVersionUID = 4511907503832230357L;

    public PageElementNotFoundException() {
	super();
    }

    public PageElementNotFoundException(String message) {
	super(message);
    }

}
