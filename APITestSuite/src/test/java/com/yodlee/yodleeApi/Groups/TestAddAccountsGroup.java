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

package com.yodlee.yodleeApi.Groups;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.databene.benerator.anno.Source;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.common.LoginFormFactory;
import com.yodlee.yodleeApi.constants.*;
import com.yodlee.yodleeApi.constants.HttpStatus;
import com.yodlee.yodleeApi.constants.JSONPaths;
import com.yodlee.yodleeApi.helper.AccountsHelper;
import com.yodlee.yodleeApi.helper.GroupsHelper;
import com.yodlee.yodleeApi.helper.ProvidersHelper;
import com.yodlee.yodleeApi.helper.SessionHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.utils.apiUtils.AccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.GroupUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;

/**
 * @author bhuvaneshwari
 *
 */
public class TestAddAccountsGroup extends Base {
	public ArrayList<Long> accountIdList;
	public String dagUN = "qe_YSTRefreshAPI.site16441.2";
	public String dagPwd = "site16441.2";
	String json;
	String jsonadd;
	public static final String ADD_ACCOUNTS_GROUP = "\\TestData\\CSVFiles\\Groups\\AddAccountGroup\\AddAccounts.csv";
	public static Long providerAccountId1 = null;

	LoginFormFactory loginFormFactory = new LoginFormFactory();
	ProvidersHelper providersHelper = new ProvidersHelper();
	ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
	CommonUtils commonUtils = new CommonUtils();
	AccountUtils accountUtils = new AccountUtils();
	AccountsHelper accountsHelper = new AccountsHelper();
	Configuration configurationObj = Configuration.getInstance();
	GroupUtils groupUtils = new GroupUtils();
	GroupsHelper groupsHelper = new GroupsHelper();
	JsonAssertionUtil jsonAssertionUtil = new JsonAssertionUtil();

	Long gropuIDUpdate;

	/**
	 * Before class that adds an account.
	 */
	@BeforeClass(alwaysRun = true)
	public void setUpTest() {
		long providerId = 16441;

		try {

			Map<String, String> credentialMap = new HashMap<String, String>();
			credentialMap.put(Constants.DAG_USERNAME, dagUN);
			credentialMap.put(Constants.DAG_PASSWORD, dagPwd);
			String updatedLoginFormWithCredentials = loginFormFactory.getLoginFormObject()
					.getUpdatedLoginFormWithCredentials(providerId, "simplified", Constants.TWO_COUNT, credentialMap);
			HttpMethodParameters httpParms = providersHelper.createInputParamforAcctAddition(providerId,
					updatedLoginFormWithCredentials);

			Response response = providerAccountUtils.addProviderAccount(httpParms,
					configurationObj.getCobrandSessionObj());
			
		

			providerAccountId1 = response.jsonPath().getLong(JSONPaths.PROVIDER_ACC_ID);
			System.out.println("providerAccountId1::::===" + providerAccountId1);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test case that validates the refresh status.
	 *
	 * @param TestCaseId
	 *            Test case id from CSV file. Test case name from CSV file.
	 * @param TestCaseName
	 *            TestCaseName filter value taken from CSV file.
	 * @param resFile
	 *            response JSON file name.
	 * @param container
	 *            response JSON file name.
	 * @param filePath
	 *            response JSON file name.
	 * @param cobSessionToken
	 *            cobsession token value taken from CSV file.
	 * @param userSessionToken
	 *            user sessoin token value taken from CSV file.
	 * @param status
	 *            TestCaseName filter value taken from CSV file.
	 * @param enabledTest
	 *            file path where JSON file is stored.
	 * @param defectID
	 *            response JSON file name.
	 */
	@Test(alwaysRun = true, enabled = true, dataProvider = Constants.FEEDER)
	@Source(ADD_ACCOUNTS_GROUP)
	public void addAccountsToGroupTest(String TestCaseId, String TestCaseName, String containerKey, String container,
			String groupKey, String group, String resFile, String filePath, String cobsessionTokenKey,
			String cobsessionToken, String userSessionTokenKey, String userSessionToken, int status, String enabledTest,
			String defectID) throws Exception {
		commonUtils.isTCEnabled(enabledTest, TestCaseId);
		System.out.println("enabledTest:::" + enabledTest);
		System.out.println("TestCaseName:::" + TestCaseName);
		System.out.println("TestCaseId:::" + TestCaseId);

		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		Map<String, Object> queryParams = accountsHelper.createQueryParamsForGetAccounts(null,
				accountsHelper.getFilterValue(container), String.valueOf(providerAccountId1), null, null, null, null);
		httpParams.setQueryParams(queryParams);

		Response getAccountsResponse = accountUtils.getAccounts(httpParams, configurationObj.getCobrandSessionObj());

		accountIdList = new ArrayList<>();
		json = groupsHelper.createGroupJson(group);
		System.out.println("json for CreateGroup " + json);

		if (TestCaseName.contains("user session")) {

			HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
			httpParams.setBodyParams(json);

			String cobSession = SessionHelper.getSessionToken(cobsessionToken, "cob");
			String userSession = SessionHelper.getSessionToken(userSessionToken, "user");

			/*EnvSession envSession = new EnvSession(cobSession, userSession,
					configurationObj.getCobrandSessionObj().getPath());*/
			EnvSession envSession = EnvSession.builder().cobSession(cobSession).userSession(userSession)
					.path(configurationObj.getCobrandSessionObj().getPath()).build();

			Response response = groupUtils.createGroup(httpParam, envSession);

			System.out.println("*************response string::" + response.asString());
			if (response.statusCode() == HttpStatus.UNAUTHORISED) {
				System.out.println("response.statusCode()>>>>>>>>>>.::: ");
				System.out.println("Executing test >>>>>>>>>>>: ");

				jsonAssertionUtil.assertResponse(response, HttpStatus.UNAUTHORISED, resFile, filePath);
			}
		}

		JSONObject bankObject = new JSONObject(getAccountsResponse.asString());
		JSONArray accountArray = bankObject.getJSONArray(JSONPaths.ACCOUNT);
		for (int count = 0; count < accountArray.length(); count++) {
			JSONObject accountObject = accountArray.getJSONObject(count);
			Long itemAccountId = accountObject.getLong(Constants.ID);
			accountIdList.add(itemAccountId);
			String accountID = String.valueOf(itemAccountId);
			System.out.println("**************Testing get transaction accountID" + accountID);
			jsonadd = groupsHelper.addAccountGroupJson(itemAccountId);
			if (TestCaseId.contains("T-ADDACCGRP-7")) {
				jsonadd = groupsHelper.addAccountGroupJsonAccounts(itemAccountId);
				System.out.println("T-ADDACCGRP-7 jsonadd:::::" + jsonadd);
			}
			if (TestCaseId.contains("T-ADDACCGRP-8")) {
				jsonadd = groupsHelper.createEmptyGroupJson(itemAccountId);
				System.out.println("T-ADDACCGRP-8 jsonadd:::::" + jsonadd);

			}
			if (TestCaseId.contains("T-ADDACCGRP-9")) {
				jsonadd = groupsHelper.emptyListAccountGroup(itemAccountId);
				System.out.println("T-ADDACCGRP-9 jsonadd:::::" + jsonadd);

			}
			System.out.println("jsonadd for add accounts " + jsonadd);
		}
		if (TestCaseId.equalsIgnoreCase("T-ADDACCGRP-1")
				&& TestCaseName.equalsIgnoreCase("Add Account with valid data")) {
			System.out.println("jsonadd forT-ADDACCGRP-1<>>>> " + jsonadd);
			json = groupsHelper.createGroupJson("Test My ATM Acc");
			System.out.println("json for CreateGroup " + json);

			HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
			httpParam.setBodyParams(json);

			String cobSession = SessionHelper.getSessionToken(cobsessionToken, "cob");
			String userSession = SessionHelper.getSessionToken(userSessionToken, "user");

			/*EnvSession envSession = new EnvSession(cobSession, userSession,
					configurationObj.getCobrandSessionObj().getPath());*/
			
			EnvSession envSession = EnvSession.builder().cobSession(cobSession).userSession(userSession)
					.path(configurationObj.getYslBasePath()).build();

			Response createResponse = groupUtils.createGroup(httpParam, envSession);

			System.out.println("jsonadd forT-ADDACCGRP-1responseCreate " + createResponse.asString());

			gropuIDUpdate = createResponse.jsonPath().getLong("group.id");
			String path = "" + gropuIDUpdate + Constants.PATH_SEPARATOR + URLConstants.ACCOUNT_ADD;

			System.out.println("jsonadd forT-ADDACCGRP-jsonaddjsonadd:: " + jsonadd);

			HttpMethodParameters httpMethodParameters = groupsHelper.createPathParam("gropuIDUpdate", path);
			httpMethodParameters.setBodyParams(json);

			Response resUpdateGrp = groupUtils.updateGroup(httpMethodParameters, envSession);

			System.out.println("**********response for add account groups::" + resUpdateGrp.asString());

			jsonAssertionUtil.assertResponse(resUpdateGrp, status, resFile, filePath);

		} else if (TestCaseId.equalsIgnoreCase("T-ADDACCGRP-10")) {

			String path = "" + gropuIDUpdate + Constants.PATH_SEPARATOR + URLConstants.ACCOUNT_ADD;

			String cobSession = SessionHelper.getSessionToken(cobsessionToken, "cob");
			String userSession = SessionHelper.getSessionToken(userSessionToken, "user");

			/*EnvSession envSession = new EnvSession(cobSession, userSession,
					configurationObj.getCobrandSessionObj().getPath());*/
			
			EnvSession envSession = EnvSession.builder().cobSession(cobSession).userSession(userSession)
					.path(configurationObj.getYslBasePath()).build();

			HttpMethodParameters httpMethodParameters = groupsHelper.createPathParam("gropuIDUpdate", path);
			httpMethodParameters.setBodyParams(jsonadd);

			Response resUpdateGrpAd = groupUtils.updateGroup(httpMethodParameters, envSession);

			System.out.println("**********response for add account groups::" + resUpdateGrpAd.asString());

			jsonAssertionUtil.assertResponse(resUpdateGrpAd, status, resFile, filePath);

		} else if (TestCaseId.equalsIgnoreCase("T-ADDACCGRP-2")) {
			long groupIDUpdateIn = 166677L;
			String path = "" + groupIDUpdateIn + Constants.PATH_SEPARATOR + URLConstants.ACCOUNT_ADD;

			String cobSession = SessionHelper.getSessionToken(cobsessionToken, "cob");
			String userSession = SessionHelper.getSessionToken(userSessionToken, "user");

		/*	EnvSession envSession = new EnvSession(cobSession, userSession,
					configurationObj.getCobrandSessionObj().getPath());*/
			
			EnvSession envSession = EnvSession.builder().cobSession(cobSession).userSession(userSession)
					.path(configurationObj.getYslBasePath()).build();

			HttpMethodParameters httpMethodParameters = groupsHelper.createPathParam("groupIDUpdateIn", path);
			httpMethodParameters.setBodyParams(jsonadd);

			Response resUpdateGrpIn = groupUtils.updateGroup(httpMethodParameters, envSession);

			System.out.println("**********response for add account groups::" + resUpdateGrpIn.asString());

			jsonAssertionUtil.assertResponse(resUpdateGrpIn, status, resFile, filePath);

		}

		else if (TestCaseId.equalsIgnoreCase("T-ADDACCGRP-3")) {
			long InvaliditemAccount = 12344;
			String path = "" + gropuIDUpdate + Constants.PATH_SEPARATOR + URLConstants.ACCOUNT_ADD;

			jsonadd = groupsHelper.addAccountGroupJsonInvalid(InvaliditemAccount);

			String cobSession = SessionHelper.getSessionToken(cobsessionToken, "cob");
			String userSession = SessionHelper.getSessionToken(userSessionToken, "user");

			/*EnvSession envSession = new EnvSession(cobSession, userSession,
					configurationObj.getCobrandSessionObj().getPath());*/
			
			EnvSession envSession = EnvSession.builder().cobSession(cobSession).userSession(userSession)
					.path(configurationObj.getYslBasePath()).build();

			HttpMethodParameters httpMethodParameters = groupsHelper.createPathParam("gropuIDUpdate", path);
			httpMethodParameters.setBodyParams(jsonadd);

			Response resUpdateGrpIn = groupUtils.updateGroup(httpMethodParameters, envSession);

			System.out.println("**********response for add account groups::" + resUpdateGrpIn.asString());

			jsonAssertionUtil.assertResponse(resUpdateGrpIn, status, resFile, filePath);

		} else if (TestCaseId.equalsIgnoreCase("T-ADDACCGRP-7")) {

			String path = "" + gropuIDUpdate + Constants.PATH_SEPARATOR + URLConstants.ACCOUNT_ADD;

			String cobSession = SessionHelper.getSessionToken(cobsessionToken, "cob");
			String userSession = SessionHelper.getSessionToken(userSessionToken, "user");

			/*EnvSession envSession = new EnvSession(cobSession, userSession,
					configurationObj.getCobrandSessionObj().getPath());*/
			
			EnvSession envSession = EnvSession.builder().cobSession(cobSession).userSession(userSession)
					.path(configurationObj.getYslBasePath()).build();

			HttpMethodParameters httpMethodParameters = groupsHelper.createPathParam("gropuIDUpdate", path);
			httpMethodParameters.setBodyParams(jsonadd);

			Response resUpdateGrpIn = groupUtils.updateGroup(httpMethodParameters, envSession);

			System.out.println("**********response for add account groups::" + resUpdateGrpIn.asString());

			jsonAssertionUtil.assertResponse(resUpdateGrpIn, status, resFile, filePath);

		} else if (TestCaseId.equalsIgnoreCase("T-ADDACCGRP-8")) {

			String path = Constants.PATH_SEPARATOR + URLConstants.ACCOUNT_ADD;

			String cobSession = SessionHelper.getSessionToken(cobsessionToken, "cob");
			String userSession = SessionHelper.getSessionToken(userSessionToken, "user");

		/*	EnvSession envSession = new EnvSession(cobSession, userSession,
					configurationObj.getCobrandSessionObj().getPath());*/
			
			EnvSession envSession = EnvSession.builder().cobSession(cobSession).userSession(userSession)
					.path(configurationObj.getYslBasePath()).build();

			HttpMethodParameters httpMethodParameters = groupsHelper.createPathParam("path", path);
			httpMethodParameters = HttpMethodParameters.builder().build();
			httpMethodParameters.setBodyParams(jsonadd);

			Response resUpdateGrpIn = groupUtils.updateGroup(httpMethodParameters, envSession);

			System.out.println("**********response for add account groups::" + resUpdateGrpIn.asString());

			jsonAssertionUtil.assertResponse(resUpdateGrpIn, status, resFile, filePath);

		}
		// Empty list of accounts
		else if (TestCaseId.equalsIgnoreCase("T-ADDACCGRP-9")) {

			String path = "" + gropuIDUpdate + Constants.PATH_SEPARATOR + URLConstants.ACCOUNT_ADD;

			String cobSession = SessionHelper.getSessionToken(cobsessionToken, "cob");
			String userSession = SessionHelper.getSessionToken(userSessionToken, "user");

			/*EnvSession envSession = new EnvSession(cobSession, userSession,
					configurationObj.getCobrandSessionObj().getPath());*/
			
			EnvSession envSession = EnvSession.builder().cobSession(cobSession).userSession(userSession)
					.path(configurationObj.getYslBasePath()).build();

			HttpMethodParameters httpMethodParameters = groupsHelper.createPathParam("gropuIDUpdate", path);
			httpMethodParameters.setBodyParams(jsonadd);

			Response resUpdateGrpIn = groupUtils.updateGroup(httpMethodParameters, envSession);

			System.out.println("**********response for add account groups::" + resUpdateGrpIn.asString());

			jsonAssertionUtil.assertResponse(resUpdateGrpIn, status, resFile, filePath);

		} else if (TestCaseId.equalsIgnoreCase("T-ADDACCGRP-11")
				&& TestCaseName.equalsIgnoreCase("Add Account with valid data")) {
			String invalidAccURL = "acco?action=add";

			String cobSession = SessionHelper.getSessionToken(cobsessionToken, "cob");
			String userSession = SessionHelper.getSessionToken(userSessionToken, "user");

		/*	EnvSession envSession = new EnvSession(cobSession, userSession,
					configurationObj.getCobrandSessionObj().getPath());*/
			EnvSession envSession = EnvSession.builder().cobSession(cobSession).userSession(userSession)
					.path(configurationObj.getYslBasePath()).build();

			HttpMethodParameters httpMethodParameters = groupsHelper.createPathParam("gropuIDUpdate", gropuIDUpdate);
			httpMethodParameters = groupsHelper.createPathParam("invalidAccURL", invalidAccURL);
			httpMethodParameters.setBodyParams(jsonadd);

			Response resUpdateGrpAd = groupUtils.updateGroup(httpMethodParameters, envSession);

			System.out.println("**********response for add account groups::" + resUpdateGrpAd.asString());

			jsonAssertionUtil.assertResponse(resUpdateGrpAd, status, resFile, filePath);

		}

	}

}
