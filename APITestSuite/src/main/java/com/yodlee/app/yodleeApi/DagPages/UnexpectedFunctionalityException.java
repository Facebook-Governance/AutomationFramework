/*******************************************************************************
 *
 * * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * *
 *
 * * This software is the confidential and proprietary information of Yodlee, Inc.
 *
 * * Use is subject to license terms.
 *
 *******************************************************************************/
package com.yodlee.app.yodleeApi.DagPages;

public class UnexpectedFunctionalityException extends RuntimeException {

	public UnexpectedFunctionalityException(String message){
		super(message);
	}
	
	public UnexpectedFunctionalityException(){
		super();
	}
}
