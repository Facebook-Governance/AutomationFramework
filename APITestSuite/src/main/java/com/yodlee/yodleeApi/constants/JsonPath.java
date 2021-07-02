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

package com.yodlee.yodleeApi.constants;

public class JsonPath {
	public static final String ACCOUNT = "account";
	public static final String PROVIDER_ACCOUNT_LOGIN_FORM = "providerAccount.loginForm";
	public static final String PROVIDER_ACCOUNT = "providerAccount";
	public static final String LOGIN_FORM = "loginForm";
	public static final String PROVIDER_ACCOUNT_ID = "providerAccount.id";
	public static final String PROVIDER_ID = "provider.id";
	public static final String PROVIDER_ID_ROW_ELEMENT = "provider[0].id";
	public static final String STATUS_MESSAGE = "providerAccount.refreshInfo.statusMessage";
	public static final String ADDITIONAL_STATUS = "providerAccount.refreshInfo.additionalStatus";
	public static final String REFRESH_INFO_STATUS = "providerAccount.refreshInfo.status";
	public static final String PROVIDER_ACCOUNT_STATUSCODE = "providerAccount.refreshInfo.statusCode";
	public static final String COB_SESSION_TOKEN = "session.cobSession";
	public static final String USER_SESSION_TOKEN = "user.session.userSession";
	public static final String LANGUAGE_ISO_CODE = "provider.languageISOCode";
	public static final String PRIMARY_LANGUAGE_ISO_CODE = "provider.primaryLanguageISOCode";
	public static final String ACCOUNT_TYPE = "accountType";
	public static final String ID = "id";
	
	
	
	//SaveForGoal
	public static final String GOALACCOUNTID="goal[0].fundingrule[0].goalAccountId";
	public static final String getGoalId="goal[0].id";	
	public static final String getFundingRuleId="fundingRule[0].id";
	
	public static final String getAccountName="account[0].accountName";	
	public static final String getGoalName="account[0].goals[0].name";
	
	public static final String groupName = "budget.groupName"; //Fetch group name from json resp 
	public static final String budgetGrpId = "budget.groupId"; //Fetch budget group Id from json resp
	public static final String budgetCatId = "budget.categoryId"; //Fetch budget cat Id from json resp
	public static final String budgetCatType = "budget.categoryName"; //Fetch budget catType from json resp
	public static final String budgetAmount = "budget.budgetAmount"; //Fetch budget amount from json resp
	public static final String budgetCategoryDataCatId = "budget.categoryData.categoryId";
	public static final String budgetCategoryDataCatName = "budget.categoryData.categoryName";
	public static final String budgetCategoryDataCatType = "budget.categoryData.categoryType";
	public static final String budgetCategoryDataBudAmt ="budget.categoryData.budgetAmount";
	public static final String budgetGroupId ="group.id";
	
}
