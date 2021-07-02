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

import static com.yodlee.yodleeApi.sdg.RequestSequence.requestTreadMap;

import java.util.LinkedHashMap;

/**
 * Helper class for Documents related API's
 * 
 * @author Rahul Kumar
 *
 */
public class DocumentsHelper {

	/**
	 * Used by @API GET/getDocuments to form query params
	 * 
	 * @param docType
	 * @param keyword
	 * @param providerAccountId
	 * @param accountId
	 * @param fromDate
	 * @param toDate
	 * @param requestID
	 * @return
	 */
	public LinkedHashMap<String, String> createQueryParamsForGetDocument(String docType, String keyword,
			String providerAccountId, String accountId, String fromDate, String toDate, String... requestID) {

		LinkedHashMap<String, String> documentQueryParams = new LinkedHashMap<>();
		documentQueryParams.put("docType", docType);
		documentQueryParams.put("keyword", keyword);
		documentQueryParams.put("providerAccountId", providerAccountId);
		documentQueryParams.put("accountId", accountId);
		documentQueryParams.put("fromDate", fromDate);
		documentQueryParams.put("toDate", toDate);

		if (null != requestID && requestID.length > 0 && null != requestID[0]
				&& requestID[0].toString().equalsIgnoreCase("true")) {
			String reqId = requestTreadMap.get(Thread.currentThread().getId() + "::" + Thread.currentThread().getName())
					.getRequestID();

			if (reqId != null) {
				documentQueryParams.put("requestId", reqId);
			} else
				System.out.println("RequestId is not obtained from requestThreadMap!");
		} else
			System.out.println("Null requestId or isRequestId is false; calling getDocuments without requestId");

		return documentQueryParams;

	}

}
