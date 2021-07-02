/*******************************************************************************
 *
 * * Copyright (c) 2020 Yodlee, Inc. All Rights Reserved.
 *
 * *
 *
 * * This software is the confidential and proprietary information of Yodlee, Inc.
 *
 * * Use is subject to license terms.
 *
 *******************************************************************************/

package com.yodlee.insights.yodleeApi.utils;

import com.yodlee.insights.views.pojo.CommonEntity;

public class VisibleAllOver {

	
	private static CommonEntity singleInstance = new CommonEntity();
	
	private VisibleAllOver() {
		
	}
	
	public static CommonEntity getInstance() {
		return singleInstance;
	}
	
	public static void setInstance(CommonEntity commonEntity) {
		singleInstance=commonEntity;
	}
	
	
}
