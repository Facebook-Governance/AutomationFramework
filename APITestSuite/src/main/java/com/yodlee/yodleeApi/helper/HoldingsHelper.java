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

/**
 * This is a helper class for Holdings related API's
 * 
 * @author Rahul Kumar
 *
 */
public class HoldingsHelper {

	/**
	 * This method is used by @API: GET /Holdings to create query params.
	 * 
	 * @param holdingType
	 * @param accountId
	 * @param providerAccountId
	 * @param include
	 * @param classificationType
	 * @param classificationValue
	 * @param accountReconType
	 * @return
	 */
	public Map<String, Object> createQueryParamsForGetHoldings(String holdingType, String accountId,
			String providerAccountId, String include, String classificationType, String classificationValue,
			String accountReconType) {

		Map<String, Object> holdingQueryParams = new LinkedHashMap<>();
		holdingQueryParams.put("holdingType", holdingType);
		holdingQueryParams.put("accountId", accountId);
		holdingQueryParams.put("include", include);
		holdingQueryParams.put("providerAccountId", providerAccountId);
		holdingQueryParams.put("assetClassification.classificationType", classificationType);
		holdingQueryParams.put("classificationValue", classificationValue);
		holdingQueryParams.put("accountReconType", accountReconType);

		return holdingQueryParams;
	}
}
