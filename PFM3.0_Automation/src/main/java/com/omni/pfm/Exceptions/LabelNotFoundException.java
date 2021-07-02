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

public class LabelNotFoundException extends RuntimeException {

    /**
     * @serial
     */
    private static final long serialVersionUID = 69687380279752881L;

    public LabelNotFoundException() {
	super();
    }

    public LabelNotFoundException(String message) {
	super(message);
    }

    /*
     * @Override public String toString() { return message; }
     */

}
