/*******************************************************************************
 *
 * * Copyright (c) 2019 Yodlee, Inc. All Rights Reserved.
 *
 * *
 *
 * * This software is the confidential and proprietary information of Yodlee, Inc.
 *
 * * Use is subject to license terms.
 *
 *******************************************************************************/

package com.yodlee.yodleeApi.helper;

import java.util.HashMap;
import java.util.Map;

public class FinCheckHelper {
	public Map<String, String> getSpendingsAccountIds(String accountId)
	{
		 Map<String, String> urlParams = new HashMap<String, String>();
		 urlParams.put("accountId@spending", accountId);
		 
		 return urlParams;
	}
	
}
