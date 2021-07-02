/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.Exceptions;

public class FrameAttributeNotFoundExecption extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1853795931833131782L;

    public FrameAttributeNotFoundExecption() {
	super();
    }

    public FrameAttributeNotFoundExecption(String message) {
	super(message);
    }

}
