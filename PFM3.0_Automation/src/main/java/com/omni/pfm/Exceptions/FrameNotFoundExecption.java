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

public class FrameNotFoundExecption extends RuntimeException {

    /**
     * @serial
     */
    private static final long serialVersionUID = 5841322262737500964L;

    public FrameNotFoundExecption() {
	super();
    }

    public FrameNotFoundExecption(String message) {
	super(message);
    }

}
