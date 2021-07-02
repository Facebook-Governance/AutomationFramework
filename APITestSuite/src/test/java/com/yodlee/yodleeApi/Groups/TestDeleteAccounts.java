/*******************************************************************************
 *
 * * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
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
import java.util.List;
import java.util.Map;

import org.databene.benerator.anno.Source;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.common.LoginFormFactory;
import com.yodlee.yodleeApi.constants.Constants;
import com.yodlee.yodleeApi.constants.HttpStatus;
import com.yodlee.yodleeApi.constants.JSONPaths;
import com.yodlee.yodleeApi.constants.URLConstants;
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
public class TestDeleteAccounts extends Base {
	public List<Long> accountIdList;
	public static Long providerAccountId1 = null;
	public String dagUN = "YSTRefreshAPI.site16441.2";
	public String dagPwd = "site16441.2";
	String json;
	String jsonadd;
	public static final String DELETE_ACCOUNTS_GROUP = "\\TestData\\CSVFiles\\Groups\\DeleteAccountGroup\\deleteAccounts.csv";
	public static final String DELETE_ACCOUNTS_GROUPS = "\\TestData\\CSVFiles\\Groups\\DeleteAccountGroup\\deleteAccountTestCases.csv";

	CommonUtils commonUtils = new CommonUtils();
	LoginFormFactory loginFormFactory = new LoginFormFactory();
	JsonAssertionUtil jsonAssertionUtil = new JsonAssertionUtil();
	AccountUtils accountUtils = new AccountUtils();
	Configuration configurationObj = Configuration.getInstance();
	AccountsHelper accountsHelper = new AccountsHelper();
	GroupsHelper groupsHelper = new GroupsHelper();
	GroupUtils groupUtils = new GroupUtils();
	ProvidersHelper providersHelper = new ProvidersHelper();
	ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();

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
	@Source(DELETE_ACCOUNTS_GROUP)
	public void deleteAccountTest(String TestCaseId, String TestCaseName, String containerKey, String container,
			String groupKey, String group, String resFile, String filePath, String cobsessionTokenKey,
			String cobsessionToken, String userSessionTokenKey, String userSessionToken, int status, String enabledTest,
			String defectID) throws Exception {

		System.out.println("enabledTest:::" + enabledTest);
		System.out.println("TestCaseName:::" + TestCaseName);
		commonUtils.isTCEnabled(enabledTest, TestCaseId);
		createGroupAndAddAccount(container, group);

		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(json);

		String cobSessionToken = SessionHelper.getSessionToken(cobsessionToken, "cob");
		String userSession = SessionHelper.getSessionToken(userSessionToken, "user");

		/*
		 * EnvSession session = new EnvSession(cobSessionToken, userSession,
		 * configurationObj.getCobrandSessionObj().getPath());
		 */

		EnvSession session = EnvSession.builder().cobSession(cobSessionToken).userSession(userSession)
				.path(configurationObj.getCobrandSessionObj().getPath()).build();

		Response response = groupUtils.createGroup(httpMethodParameters, session);

		System.out.println("response.statusCode().::: " + response.statusCode());
		System.out.println("response.statusCode()>>>>>>>>>>.::: ");
		System.out.println("Executing test >>>>>>>>>>>: ");
		jsonAssertionUtil.assertJSON(response, HttpStatus.UNAUTHORISED, resFile, filePath);

	}

	@Test(description = "AT-67658")
	public void deleteGroups_67658() throws Exception {
		String TestCaseId = "AT-67658";
		String TestCaseName = "Valid data to delete";
		String containerKey = "containerKey";
		String container = "bank";
		String groupKey = "group";
		String group = "CheckingGroup1";
		String resFile = "AddAddDeleteAcc.json";
		String filePath = "../src/test/resources/TestData/JSONFiles/AccountGroups/DeleteAccountsJson/";
		String cobsessionTokenKey = "cobsession";
		String cobsessionToken = "VALID";
		String userSessionTokenKey = "userSession";
		String userSessionToken = "VALID";
		int status = 204;
		String enabledTest = "TRUE";
		String defectID = "0";

		commonUtils.isTCEnabled(enabledTest, TestCaseId);

		createGroupAndAddAccount(container, group);
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(json);

		String cobSessionToken = SessionHelper.getSessionToken(cobsessionToken, "cob");
		String userSession = SessionHelper.getSessionToken(userSessionToken, "user");

		/*
		 * EnvSession session = new EnvSession(cobSessionToken, userSession,
		 * configurationObj.getCobrandSessionObj().getPath());
		 */

		EnvSession session = EnvSession.builder().cobSession(cobSessionToken).userSession(userSession)
				.path(configurationObj.getCobrandSessionObj().getPath()).build();

		Response response = groupUtils.createGroup(httpMethodParameters, session);
		System.out.println("*************response string::" + response.asString());

		Long gropuIDUpdate = response.jsonPath().getLong("group.id");

		// Add accounts
		String path=""+ gropuIDUpdate+Constants.PATH_SEPARATOR + URLConstants.ACCOUNT_ADD; 

		HttpMethodParameters httpParameters = groupsHelper.createPathParam("gropuIDUpdate", path);
		httpParameters.setBodyParams(jsonadd);

		Response resAddAccountsGrp = groupUtils.updateGroup(httpParameters, session);
		
		// Delete Accounts
		String paths = "" + gropuIDUpdate + Constants.PATH_SEPARATOR + URLConstants.ACCOUNT_DELETE;

		HttpMethodParameters httpParam = groupsHelper.createPathParam("gropuIDUpdate", paths);
		httpParam.setBodyParams(jsonadd);

		Response deleteAccGrp = groupUtils.updateGroup(httpParam, session);
		System.out.println("**********response for  AT-67658 groups::" + deleteAccGrp.asString());
		jsonAssertionUtil.assertJSON(deleteAccGrp, status, resFile, filePath);
	}

	@Test(description = "AT-67659")
	public void deleteGroups_67659() throws Exception {
		String TestCaseId = "AT-67659";
		String TestCaseName = "invalid group id";
		String containerKey = "containerKey";
		String container = "bank";
		String groupKey = "group";
		String group = "CheckingGroup2";
		String resFile = "InvalidpdateGroup.json";
		String filePath = "../src/test/resources/TestData/JSONFiles/AccountGroups/DeleteAccountsJson/";
		String cobsessionTokenKey = "cobsession";
		String cobsessionToken = "VALID";
		String userSessionTokenKey = "userSession";
		String userSessionToken = "VALID";
		int status = 400;
		String enabledTest = "TRUE";
		String defectID = "0";

		commonUtils.isTCEnabled(enabledTest, TestCaseId);

		createGroupAndAddAccount(container, group);
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(json);

		String cobSessionToken = SessionHelper.getSessionToken(cobsessionToken, "cob");
		String userSession = SessionHelper.getSessionToken(userSessionToken, "user");

		/*
		 * EnvSession session = new EnvSession(cobSessionToken, userSession,
		 * configurationObj.getCobrandSessionObj().getPath());
		 */

		EnvSession session = EnvSession.builder().cobSession(cobSessionToken).userSession(userSession)
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		Response response = groupUtils.createGroup(httpMethodParameters, session);
		System.out.println("*************response string::" + response.asString());

		Long gropuIDUpdate = response.jsonPath().getLong("group.id");
		gropuIDUpdate = 2324L;
		String paths = "" + gropuIDUpdate + Constants.PATH_SEPARATOR + URLConstants.ACCOUNT_DELETE;

		HttpMethodParameters httpParam = groupsHelper.createPathParam("gropuIDUpdate", paths);
		httpParam.setBodyParams(jsonadd);

		Response deleteAccinvalid = groupUtils.updateGroup(httpParam, session);

		System.out.println("**********response for AT-67659 groups::" + deleteAccinvalid.asString());
		jsonAssertionUtil.assertJSON(deleteAccinvalid, status, resFile, filePath);
	}

	@Test(description = "AT-67660")
	public void deleteGroups_67660() throws Exception {
		String TestCaseId = "AT-67660";
		String TestCaseName = "For null group id";
		String containerKey = "containerKey";
		String container = "bank";
		String groupKey = "group";
		String group = "CheckingGroup3";
		String resFile = "DeletedAddAddDeleteAcc.json";
		String filePath = "../src/test/resources/TestData/JSONFiles/AccountGroups/DeleteAccountsJson/";
		String cobsessionTokenKey = "cobsession";
		String cobsessionToken = "VALID";
		String userSessionTokenKey = "userSession";
		String userSessionToken = "VALID";
		int status = 400;
		String enabledTest = "TRUE";
		String defectID = "0";

		commonUtils.isTCEnabled(enabledTest, TestCaseId);

		createGroupAndAddAccount(container, group);
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(json);

		String cobSessionToken = SessionHelper.getSessionToken(cobsessionToken, "cob");
		String userSession = SessionHelper.getSessionToken(userSessionToken, "user");

		/*
		 * EnvSession session = new EnvSession(cobSessionToken, userSession,
		 * configurationObj.getCobrandSessionObj().getPath());
		 */

		EnvSession session = EnvSession.builder().cobSession(cobSessionToken).userSession(userSession)
				.path(configurationObj.getCobrandSessionObj().getPath()).build();

		Response response = groupUtils.createGroup(httpMethodParameters, session);

		System.out.println("*************response string::" + response.asString());
		Long gropuIDUpdate = null;
		String paths = "" + gropuIDUpdate + Constants.PATH_SEPARATOR + URLConstants.ACCOUNT_DELETE;

		HttpMethodParameters httpParam = groupsHelper.createPathParam("gropuIDUpdate", paths);
		httpParam.setBodyParams(jsonadd);

		Response deleteAccinvalid = groupUtils.updateGroup(httpParam, session);

		System.out.println("**********response for  AT-67660:" + deleteAccinvalid.asString());
		jsonAssertionUtil.assertJSON(deleteAccinvalid, status, resFile, filePath);
	}

	@Test(alwaysRun = true, enabled = true, dataProvider = Constants.FEEDER)
	@Source(DELETE_ACCOUNTS_GROUPS)
	public void deleteAccountTests(String TestCaseId, String TestCaseName, String containerKey, String container,
			String groupKey, String group, String resFile, String filePath, String cobsessionTokenKey,
			String cobsessionToken, String userSessionTokenKey, String userSessionToken, int status, String enabledTest,
			String defectID) throws Exception {

		commonUtils.isTCEnabled(enabledTest, TestCaseId);

		System.out.println("enabledTest:::" + enabledTest);
		System.out.println("TestCaseName:::" + TestCaseName);
		createGroupAndAddAccount(container, group);
		long itemAccountinvalid = 131431414L;
		jsonadd = groupsHelper.addAccountGroupJson(itemAccountinvalid);
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(json);

		String cobSessionToken = SessionHelper.getSessionToken(cobsessionToken, "cob");
		String userSession = SessionHelper.getSessionToken(userSessionToken, "user");

		/*
		 * EnvSession session = new EnvSession(cobSessionToken, userSession,
		 * configurationObj.getCobrandSessionObj().getPath());
		 */

		EnvSession session = EnvSession.builder().cobSession(cobSessionToken).userSession(userSession)
				.path(configurationObj.getCobrandSessionObj().getPath()).build();

		Response response = groupUtils.createGroup(httpMethodParameters, session);
		System.out.println("*************response string::" + response.asString());

		Long gropuIDUpdate = response.jsonPath().getLong("group.id");
		String paths = "" + gropuIDUpdate + Constants.PATH_SEPARATOR + URLConstants.ACCOUNT_DELETE;

		HttpMethodParameters httpParam = groupsHelper.createPathParam("gropuIDUpdate", paths);
		httpParam.setBodyParams(jsonadd);

		Response deleteAccinvalid = groupUtils.updateGroup(httpParam, session);
		System.out.println("**********response for AT-67661 and AT-67663 groups::" + deleteAccinvalid.asString());
		jsonAssertionUtil.assertJSON(deleteAccinvalid, status, resFile, filePath);

	}

	@Test(description = "AT-67662")
	public void deleteGroups_67662() throws Exception {
		String TestCaseId = "AT-67662";
		String TestCaseName = "For Empty group";
		String containerKey = "containerKey";
		String container = "bank";
		String groupKey = "group";
		String group = "CheckingGroup5";
		String resFile = "EmptyAddAddDeleteAcc.json";
		String filePath = "../src/test/resources/TestData/JSONFiles/AccountGroups/DeleteAccountsJson/";
		String cobsessionTokenKey = "cobsession";
		String cobsessionToken = "VALID";
		String userSessionTokenKey = "userSession";
		String userSessionToken = "VALID";
		int status = 204;
		String enabledTest = "TRUE";
		String defectID = "0";

		commonUtils.isTCEnabled(enabledTest, TestCaseId);

		createGroupAndAddAccount(container, group);
		json = groupsHelper.createGroupJson(group);
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(json);

		String cobSessionToken = SessionHelper.getSessionToken(cobsessionToken, "cob");
		String userSession = SessionHelper.getSessionToken(userSessionToken, "user");

		/*
		 * EnvSession session = new EnvSession(cobSessionToken, userSession,
		 * configurationObj.getCobrandSessionObj().getPath());
		 */

		EnvSession session = EnvSession.builder().cobSession(cobSessionToken).userSession(userSession)
				.path(configurationObj.getCobrandSessionObj().getPath()).build();

		Response response = groupUtils.createGroup(httpMethodParameters, session);

		System.out.println("*************response string::" + response.asString());

		Long gropuIDUpdate = response.jsonPath().getLong("group.id");

		gropuIDUpdate = response.jsonPath().getLong("group.id");

		HttpMethodParameters httpParam = groupsHelper.createPathParam("gropuIDUpdate", gropuIDUpdate);		
		

		Response deleteAccinvalid = groupUtils.deleteGroup(httpParam, session);

		System.out.println("**********response for AT-67662" + deleteAccinvalid.asString());
		Assert.assertEquals(deleteAccinvalid.statusCode(), status);
	}

	@Test(description = "AT-67664")
	public void deleteGroups_67664() throws Exception {
		String TestCaseId = "AT-67664";
		String TestCaseName = "Invalid value parameter to action";
		String containerKey = "containerKey";
		String container = "bank";
		String groupKey = "group";
		String group = "CheckingGroup7";
		String resFile = "DeleteAcc1.json";
		String filePath = "../src/test/resources/TestData/JSONFiles/AccountGroups/DeleteAccountsJson/";
		String cobsessionTokenKey = "cobsession";
		String cobsessionToken = "VALID";
		String userSessionTokenKey = "userSession";
		String userSessionToken = "VALID";
		int status = 404;
		String enabledTest = "TRUE";
		String defectID = "0";

		commonUtils.isTCEnabled(enabledTest, TestCaseId);

		createGroupAndAddAccount(container, group);
		//json = groupsHelper.createGroupJson(group);

		
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(json);

		String cobSessionToken = SessionHelper.getSessionToken(cobsessionToken, "cob");
		String userSession = SessionHelper.getSessionToken(userSessionToken, "user");


		EnvSession session = EnvSession.builder().cobSession(cobSessionToken).userSession(userSession)
				.path(configurationObj.getCobrandSessionObj().getPath()).build();

		Response response = groupUtils.createGroup(httpMethodParameters, session);

		System.out.println("*************response string::" + response.asString());
		Long gropuIDUpdate = response.jsonPath().getLong("group.id");

		String paths = "" + gropuIDUpdate + Constants.PATH_SEPARATOR + URLConstants.ACCOUNT_ADD;

		HttpMethodParameters httpParam = groupsHelper.createPathParam("gropuIDUpdate", paths);
		httpParam.setBodyParams(jsonadd);

		Response resAddAccountsGrp = groupUtils.updateGroup(httpParam, session);

		String accURL = "accous?action=rove";
		String path = "" + gropuIDUpdate + Constants.PATH_SEPARATOR + accURL;

		HttpMethodParameters httpParameter = groupsHelper.createPathParam("gropuIDUpdate", path);
		httpParameter.setBodyParams(json);

		Response deleteAccinvalid = groupUtils.updateGroup(httpParameter, session);
		// Deleting again to reproduce the issue
		String pathSeparator = "" + gropuIDUpdate + Constants.PATH_SEPARATOR + accURL;

		HttpMethodParameters httpParameters = groupsHelper.createPathParam("gropuIDUpdate", pathSeparator);
		httpParameters.setBodyParams(jsonadd);

		Response deleteAccinvalid1 = groupUtils.updateGroup(httpParameters, session);

		System.out.println("**********response for  T-T-DeleteAcc-7-1 groups::" + deleteAccinvalid1.asString());
		jsonAssertionUtil.assertJSON(deleteAccinvalid, status, resFile, filePath);
	}

	private void createGroupAndAddAccount(String container, String group) {
		accountIdList = new ArrayList<>();

		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		Map<String, Object> queryParams = accountsHelper.createQueryParamsForGetAccounts(null,
				accountsHelper.getFilterValue(container), String.valueOf(providerAccountId1), null, null, null, null);
		httpParams.setQueryParams(queryParams);

		Response getAccountsResponse = accountUtils.getAccounts(httpParams, configurationObj.getCobrandSessionObj());

		json = groupsHelper.createGroupJson(group);
		System.out.println("json for CreateGroup " + json);

		JSONObject bankObject = new JSONObject(getAccountsResponse.asString());
		JSONArray accountArray = bankObject.getJSONArray(JSONPaths.ACCOUNT);
		for (int count = 0; count < accountArray.length(); count++) {
			JSONObject accountObject = accountArray.getJSONObject(count);
			Long itemAccountId = accountObject.getLong(Constants.ID);
			accountIdList.add(itemAccountId);
			String accountID = String.valueOf(itemAccountId);
			System.out.println("**************Testing get transaction accountID" + accountID);
			jsonadd = groupsHelper.addAccountGroupJson(itemAccountId);
		}
	}

}
