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
package com.yodlee.insights.yodleeApi.utils.v2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.restassured.response.Response;
import junit.framework.Assert;

public class AccountEntityInsightsVerifications {

	protected Logger logger = LoggerFactory.getLogger(AccountEntityInsightsVerifications.class);
	InsightsGenerics insightsGenerics = new InsightsGenerics();
	boolean notificationInsightsStatus;
	JsonParser jsonParser = new JsonParser();
	
	
}
