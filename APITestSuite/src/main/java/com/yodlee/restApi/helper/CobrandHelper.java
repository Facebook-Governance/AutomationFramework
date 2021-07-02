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
package com.yodlee.restApi.helper;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yodlee.yodleeApi.pojo.HttpMethodParameters;

public class CobrandHelper {

	protected Logger logger = LoggerFactory.getLogger(AccountAdditionHelper.class);
	
	public HttpMethodParameters getCobrandRestInfo(String cobrandName,String cobrandPassword){
		
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("cobrandLogin", cobrandName);
		params.put("cobrandPassword", cobrandPassword);
		HttpMethodParameters methodParameters = HttpMethodParameters.builder().build();
		methodParameters.setRestformParams(params);
		
		return methodParameters;
	}
}
