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

import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.databene.benerator.anno.Source;
import org.json.JSONException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.common.LoginFormFactory;
import com.yodlee.yodleeApi.constants.Constants;
import com.yodlee.yodleeApi.constants.HttpStatus;
import com.yodlee.yodleeApi.helper.GroupsHelper;
import com.yodlee.yodleeApi.helper.ProviderAccountsHelper;
import com.yodlee.yodleeApi.helper.ProvidersHelper;
import com.yodlee.yodleeApi.helper.SessionHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.utils.apiUtils.GroupUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

/**
 * @author bhuvaneshwari
 *
 */
public class TestUpdateGroup extends Base {
	public List<Long> accountIdList;
	String providerAccountId;
	public String dagUN = "qe_YSTRefreshAPI.site16441.2";
	public String dagPwd = "site16441.2";
	String json;
	long gropuID;
	public static final String UPDATE_GROUP = "\\TestData\\CSVFiles\\Groups\\UpdateGroupData\\UpdateGroupCases.csv";
	public static final String UPDATE_GROUPS = "\\TestData\\CSVFiles\\Groups\\UpdateGroupData\\UpdateGroupTestCases.csv";

	ProviderAccountsHelper providerAccountsHelper;
	Response getAllAccountResponse;
	public static Long providerAccountId1 = null;
	Configuration configurationObj = Configuration.getInstance();
	LoginFormFactory loginFormFactory = new LoginFormFactory();
	ProvidersHelper providersHelper = new ProvidersHelper();
	ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
	CommonUtils commonUtils = new CommonUtils();
	GroupUtils groupUtils = new GroupUtils();
	GroupsHelper groupsHelper = new GroupsHelper();
	Configuration configuration = Configuration.getInstance();
	JsonAssertionUtil jsonAssertionUtil = new JsonAssertionUtil();

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
	@Source(UPDATE_GROUP)
	public void updateGroupTest(String TestCaseId, String TestCaseName, String containerKey, String container,
			String groupKey, String group, String resFile, String filePath, String cobsessionTokenKey,
			String cobsessionToken, String userSessionTokenKey, String userSessionToken, int status, String enabledTest,
			String defectID) throws Exception {
		commonUtils.isTCEnabled(enabledTest, TestCaseId);
		System.out.println("enabledTest:::" + enabledTest);
		System.out.println("TestCaseName:::" + TestCaseName);
		json = groupsHelper.createGroupJson(group);

		System.out.println("json for CreateGroup " + json);

		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setBodyParams(json);

		String cobSession = SessionHelper.getSessionToken(cobsessionToken, "cob");
		String userSession = SessionHelper.getSessionToken(userSessionToken, "user");

		/*
		 * EnvSession envSession = new EnvSession(cobSession, userSession,
		 * configuration.getCobrandSessionObj().getPath());
		 */

		EnvSession envSession = EnvSession.builder().cobSession(cobSession).userSession(userSession)
				.path(configurationObj.getCobrandSessionObj().getPath()).build();

		Response response = groupUtils.createGroup(httpParams, envSession);
		if (response.statusCode() == HttpStatus.UNAUTHORISED) {
			System.out.println("response.statusCode()>>>>>>>>>>.::: ");
			System.out.println("Executing test >>>>>>>>>>>: ");
			jsonAssertionUtil.assertResponse(response, HttpStatus.UNAUTHORISED, resFile, filePath);
		}
	}

	@Test(description = "AT-67713") 
	public void updateGroupTest_67713() throws Exception {
		String TestCaseId = "AT-67713";
		String TestCaseName = "Invalid Group id";
		String containerKey = "containerKey";
		String container = "bank";
		String groupKey = "group";
		String group = "";
		String resFile = "invalidResponse.json";
		String filePath = "../src/test/resources/TestData/JSONFiles/AccountGroups/UpdateGroupJson/";
		String cobsessionTokenKey = "cobsession";
		String cobsessionToken = "VALID";
		String userSessionTokenKey = "userSession";
		String userSessionToken = "VALID";
		int status = 400;
		String enabledTest = "TRUE";
		String defectID = "0";

		commonUtils.isTCEnabled(enabledTest, TestCaseId);
		System.out.println("enabledTest:::" + enabledTest);
		System.out.println("TestCaseName:::" + TestCaseName);
		json = groupsHelper.createGroupJson(group);

		System.out.println("json for CreateGroup " + json);

		int gropuID1 = 13143453;

		HttpMethodParameters httpMethodParameters = groupsHelper.createPathParam("gropuID1", gropuID1);
		httpMethodParameters.setBodyParams(json);

		String cobSession = SessionHelper.getSessionToken(cobsessionToken, "cob");
		String userSession = SessionHelper.getSessionToken(userSessionToken, "user");

		EnvSession envSession = EnvSession.builder().cobSession(cobSession).userSession(userSession)
				.path(configurationObj.getCobrandSessionObj().getPath()).build();

		Response resUpdateGrp = groupUtils.updateGroup(httpMethodParameters, envSession);

		System.out.println("Response after the update user" + resUpdateGrp.prettyPrint());
		System.out.println("response.statusCode()::: " + resUpdateGrp.statusCode());
		jsonAssertionUtil.assertResponse(resUpdateGrp, status, resFile, filePath);
	}

	@Test(description = "AT-67709")
	public void updateGroupTest_67709() throws Exception {
		String TestCaseId = "AT-67709";
		String TestCaseName = "Valid API parameters";
		String containerKey = "containerKey";
		String container = "bank";
		String groupKey = "group";
		String group = "SavingGroup1";
		String resFile = "updatevalidGroup.json";
		String filePath = "../src/test/resources/TestData/JSONFiles/AccountGroups/UpdateGroupJson/";
		String cobsessionTokenKey = "cobsession";
		String cobsessionToken = "VALID";
		String userSessionTokenKey = "userSession";
		String userSessionToken = "VALID";
		int status = 204;
		String enabledTest = "TRUE";
		String defectID = "0";

		commonUtils.isTCEnabled(enabledTest, TestCaseId);
		System.out.println("enabledTest:::" + enabledTest);
		System.out.println("TestCaseName:::" + TestCaseName);
		json = groupsHelper.createGroupJson(group);

		System.out.println("json for CreateGroup " + json);

		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setBodyParams(json);

		String cobSession = SessionHelper.getSessionToken(cobsessionToken, "cob");
		String userSession = SessionHelper.getSessionToken(userSessionToken, "user");

		/*
		 * EnvSession envSession = new EnvSession(cobSession, userSession,
		 * configuration.getCobrandSessionObj().getPath());
		 */

		EnvSession envSession = EnvSession.builder().cobSession(cobSession).userSession(userSession)
				.path(configurationObj.getCobrandSessionObj().getPath()).build();

		Response createResponse = groupUtils.createGroup(httpParams, envSession);

		System.out.println("Response after the AT-67709:: " + createResponse.asString());

		group = "MyBankAccounts";
		json = groupsHelper.updateGroupJson(group);
		System.out.println("json for AT-67709:: " + json);
		Long gropuIDUpdate = createResponse.jsonPath().getLong("group.id");
		HttpMethodParameters httpMethodParameters = groupsHelper.createPathParam("gropuIDUpdate", gropuIDUpdate);
		httpMethodParameters.setBodyParams(json);

		Response updateResponse = groupUtils.updateGroup(httpMethodParameters, envSession);

		System.out.println("Response after the update user" + updateResponse.prettyPrint());
		System.out.println("response.statusCode()::: " + updateResponse.statusCode());
		jsonAssertionUtil.assertResponse(updateResponse, status, resFile, filePath);
	}

	@Test(description = "AT-67710")
	public void updateGroupTest_67710() throws Exception {
		String TestCaseId = "AT-67710";
		String TestCaseName = "Valid Group Name";
		String containerKey = "containerKey";
		String container = "bank";
		String groupKey = "group";
		String group = "CheckingGroup";
		String resFile = "AccountGroupNameAlreadyExists.json";
		String filePath = "../src/test/resources/TestData/JSONFiles/AccountGroups/UpdateGroupJson/";
		String cobsessionTokenKey = "cobsession";
		String cobsessionToken = "VALID";
		String userSessionTokenKey = "userSession";
		String userSessionToken = "VALID";
		int status = 204;
		String enabledTest = "TRUE";
		String defectID = "0";

		commonUtils.isTCEnabled(enabledTest, TestCaseId);
		System.out.println("enabledTest:::" + enabledTest);
		System.out.println("TestCaseName:::" + TestCaseName);
		json = groupsHelper.createGroupJson(group);

		System.out.println("json for CreateGroup " + json);

		HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
		httpParam.setBodyParams(json);

		String cobSession = SessionHelper.getSessionToken(cobsessionToken, "cob");
		String userSession = SessionHelper.getSessionToken(userSessionToken, "user");

		/*
		 * EnvSession envSession = new EnvSession(cobSession, userSession,
		 * configuration.getCobrandSessionObj().getPath());
		 */

		EnvSession envSession = EnvSession.builder().cobSession(cobSession).userSession(userSession)
				.path(configurationObj.getCobrandSessionObj().getPath()).build();

		Response createResponse = groupUtils.createGroup(httpParam, envSession);

		group = "MyCardAccounts";
		json = groupsHelper.updateGroupJson(group);
		System.out.println("json AT-67710 for CreateGroup " + json);
		Long gropuIDUpdate = createResponse.jsonPath().getLong("group.id");
		System.out.println("GroupId:::::" + gropuIDUpdate);
		HttpMethodParameters httpMethodParameter = groupsHelper.createPathParam("gropuIDUpdate", gropuIDUpdate);
		httpMethodParameter.setBodyParams(json);

		Response updateResponse = groupUtils.updateGroup(httpMethodParameter, envSession);

		System.out.println("Response after the update user" + updateResponse.prettyPrint());
		System.out.println("response.statusCode()::: " + updateResponse.statusCode());
		jsonAssertionUtil.assertResponse(updateResponse, status, resFile, filePath);
	}

	@Test(alwaysRun = true, enabled = true, dataProvider = Constants.FEEDER)
	@Source(UPDATE_GROUPS)
	public void updateGroupTestCases(String TestCaseId, String TestCaseName, String containerKey, String container,
			String groupKey, String group, String resFile, String filePath, String cobsessionTokenKey,
			String cobsessionToken, String userSessionTokenKey, String userSessionToken, int status, String enabledTest,
			String defectID) throws Exception {
		commonUtils.isTCEnabled(enabledTest, TestCaseId);
		System.out.println("enabledTest:::" + enabledTest);
		System.out.println("TestCaseName:::" + TestCaseName);
		json = groupsHelper.createGroupJson(group);

		System.out.println("json for CreateGroup " + json);

		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setBodyParams(json);

		String cobSession = SessionHelper.getSessionToken(cobsessionToken, "cob");
		String userSession = SessionHelper.getSessionToken(userSessionToken, "user");

		/*
		 * EnvSession envSession = new EnvSession(cobSession, userSession,
		 * configuration.getCobrandSessionObj().getPath());
		 */

		EnvSession envSession = EnvSession.builder().cobSession(cobSession).userSession(userSession)
				.path(configurationObj.getCobrandSessionObj().getPath()).build();

		Response createResponse = groupUtils.createGroup(httpParams, envSession);

		System.out.println("response.statusCode()>>>>>>>>>>for session.::: " + createResponse.statusCode());

		if (createResponse.statusCode() == HttpStatus.UNAUTHORISED
				|| createResponse.statusCode() == HttpStatus.BAD_REQUEST) {
			System.out.println("response.statusCode()>>>>>>>>>>.::: ");
			System.out.println("Executing test >>>>>>>>>>>: ");
			jsonAssertionUtil.assertResponse(createResponse, HttpStatus.UNAUTHORISED, resFile, filePath);
		} else {
			Long gropuIDTest = createResponse.jsonPath().getLong("group.id");
			System.out.println("Response after the gropuIDElse" + gropuID);
			HttpMethodParameters httpMethodParameters = groupsHelper.createPathParam("gropuIDTest", gropuIDTest);
			httpMethodParameters.setBodyParams(json);

			Response updateResponse = groupUtils.updateGroup(httpMethodParameters, envSession);

			System.out.println("Response after the update user" + updateResponse.prettyPrint());
			System.out.println("response.statusCode()::: " + updateResponse.statusCode());

			if (updateResponse.statusCode() == HttpStatus.UNAUTHORISED) {
				System.out.println("response.statusCode()>>>>>>>>>>.::: ");
				System.out.println("Executing test >>>>>>>>>>>: ");
				jsonAssertionUtil.assertResponse(updateResponse, HttpStatus.UNAUTHORISED, resFile, filePath);
			} else {
				jsonAssertionUtil.assertResponse(updateResponse, status, resFile, filePath);
			}
		}
	}

}