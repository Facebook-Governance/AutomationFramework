/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.Exceptions;

public class PageAttributeNotFoundExecption extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 5621103230051248481L;

    public PageAttributeNotFoundExecption() {
	super();
    }

    public PageAttributeNotFoundExecption(String message) {
	super(message);
    }

}
