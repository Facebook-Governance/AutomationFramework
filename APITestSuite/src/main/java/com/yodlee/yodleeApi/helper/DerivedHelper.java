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

import com.yodlee.yodleeApi.constants.Constants;

/**
 * Helper class for Derived related API's
 * 
 * @author Rahul Kumar
 *
 */
public class DerivedHelper {

	/**
	 * Used by @API-GET /derived/transactionSummary to form query params.
	 * 
	 * @param accountId
	 * @param groupBy
	 * @param categoryType
	 * @param categoryId
	 * @param fromDate
	 * @param toDate
	 * @param interval
	 * @param include
	 * @param includeUserCategory
	 * @return
	 */
	public LinkedHashMap<String, String> createQueryParamsForDerivedTransactionSummary(String accountId, String groupBy,
			String categoryType, String categoryId, String fromDate, String toDate, String interval, String include,
			String includeUserCategory) {

		LinkedHashMap<String, String> queryParams = new LinkedHashMap<String, String>();
		queryParams.put("accountId", accountId);
		queryParams.put("groupBy", groupBy);
		queryParams.put("categoryType", categoryType);
		queryParams.put("categoryId", categoryId);
		queryParams.put("fromDate", fromDate);
		queryParams.put("toDate", toDate);
		queryParams.put("interval", interval);
		queryParams.put("include", include);
		queryParams.put("includeUserCategory", includeUserCategory);

		return queryParams;
	}

	/**
	 * Used by @API-GET /derived/networth to form query params.
	 * 
	 * @param accountIds
	 * @param include
	 * @param fromDate
	 * @param toDate
	 * @param interval
	 * @param accountReconType
	 * @param skip
	 * @param top
	 * @return
	 */
	public LinkedHashMap<String, String> createQueryParamsForNetworthSummary(String accountIds, String include,
			String fromDate, String toDate, String interval, String accountReconType, String skip, String top) {

		LinkedHashMap<String, String> queryParams = new LinkedHashMap<>();
		queryParams.put(Constants.MULTIPLE_ACCOUNT_IDS_FILTER, accountIds);
		queryParams.put(Constants.INCLUDE_FILTER, include);
		queryParams.put(Constants.FROM_DATE_FILTER, fromDate);
		queryParams.put(Constants.TO_DATE_FILTER, toDate);
		queryParams.put(Constants.INTERVAL_FILTER, interval);
		queryParams.put(Constants.ACCOUNT_RECON_TYPE_FILTER, accountReconType);
		queryParams.put(Constants.SKIP_FILTER, skip);
		queryParams.put(Constants.TOP_FILTER, top);

		return queryParams;
	}

	/**
	 * Used by @API-GET /derived/holdingSummary to form query params.
	 * 
	 * @param include
	 * @param accountIds
	 * @param classificationType
	 * @param accountReconType
	 * @param filePath
	 * @param resFile
	 * @return
	 */
	public LinkedHashMap<String, Object> createQueryParamsForHoldingSummary(String include, String accountIds,
			String classificationType, String accountReconType) {

		LinkedHashMap<String, Object> queryParams = new LinkedHashMap<>();
		queryParams.put("include", include);
		queryParams.put("accountIds", accountIds);
		queryParams.put("classificationType", classificationType);
		queryParams.put("accountReconType", accountReconType);

		return queryParams;
	}

}
