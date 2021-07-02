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

public class InsightsConstants {
	
	public static String[] INSIGHT_NAMES= {"LowBalanceWarning","LoanMaturityNotice","InvestmentMaturityNotice"};
	public static String[] STORY_IDS= {"B-51206","B-62586","B-62586"};
	public static String COB_SUBSCRIPTION_CONTEXT = "Cobrand";
	public static String USER_SUBSCRIPTION_CONTEXT = "User";
	public static String COB_SUB_REQUEST = "cobrandSubscriptionRequest";
	public static String USER_SUB_REQUEST = "userSubscriptionRequest";
	public static String GET_USER_SUBSCRIPTION_REQUEST="getUserSubscriptionRequest";
	public static String INVOKER_REQUEST = "invokerRequest";
	public static String USER_NOTIFICATION_REQUEST = "notificationsRequest";
	public static String ERROR_MESSAGE_801 = "Cobrand subscription Patch FAILED";
	public static String ERROR_MESSAGE_802 = "User subscription Patch FAILED";
	public static String USER = "userSubscription";
	public static String COBRAND = "cobrandSubscription";
	

}
