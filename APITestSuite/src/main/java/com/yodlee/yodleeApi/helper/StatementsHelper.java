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

/**
 * This is a helper class for statements API's
 * @author Rahul Kumar
 *
 */
public class StatementsHelper {

	/**
	 * This method is used by @API- GET /statements to create statements QueryParams
	 * 
	 * @param statAccountId
	 * @param statContainer
	 * @param isLatest
	 * @param status
	 * @param fromDate
	 * @return
	 */
	public LinkedHashMap<String, Object> createQueryParamsForGetStatements(String statAccountId, String statContainer,
			String isLatest, String status, String fromDate) {

		LinkedHashMap<String, Object> statementsQueryParams = new LinkedHashMap<>();
		statementsQueryParams.put("accountId", statAccountId);
		statementsQueryParams.put("container", statContainer);
		statementsQueryParams.put("isLatest", isLatest);
		statementsQueryParams.put("status", status);
		statementsQueryParams.put("fromDate", fromDate);

		return statementsQueryParams;

	}

}
