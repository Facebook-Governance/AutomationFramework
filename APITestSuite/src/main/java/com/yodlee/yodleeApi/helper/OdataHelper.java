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

import java.util.LinkedHashMap;
import java.util.Map;

import com.yodlee.yodleeApi.common.Configuration;

public class OdataHelper {

	public Map<String, String> getProvider(String priority, String capability, String dataSet, String name,
			String skip, String top, String topSuggested) {
		Map<String, String> getProviderQueryParams = new LinkedHashMap<>();
		/* getProviderQueryParams.put("priority", priority); */

		Configuration configObj = Configuration.getInstance();
		if (priority != null && !priority.isEmpty())
			getProviderQueryParams.put("priority", priority);

		if (top != null && !top.isEmpty())
			getProviderQueryParams.put("top", top);

		if (capability != null && !capability.isEmpty())
			getProviderQueryParams.put("capability", capability);

		if (skip != null && !skip.isEmpty())
			getProviderQueryParams.put("skip", skip);

		if (topSuggested != null && !topSuggested.isEmpty())
			getProviderQueryParams.put("topSuggested", topSuggested);

		if (!configObj.isHybrid()) {
			getProviderQueryParams.put("dataSet.attribute", dataSet);
		} else {
			if (dataSet != null && !dataSet.isEmpty())
				getProviderQueryParams.put("dataset$filter", dataSet);
		}

	
		/*
		 * Response getProviderResponse =
		 * RestUtil.getWithQueryParamsAndSessions(getProviderQueryParams,
		 * commonEnvSession.getCobSession(), commonEnvSession.getUserSession(),
		 * commonEnvSession.getPath() + Constants.PROVIDERS_URL);
		 * getProviderResponse.then().log().all();
		 */
		// System.out.println("Get ProviderResponse:" +
		// getProviderResponse.asString());
		return getProviderQueryParams;
	}
}
