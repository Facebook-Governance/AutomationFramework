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

import java.util.HashMap;
import java.util.LinkedHashMap;
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
import com.yodlee.yodleeApi.helper.ProvidersHelper;
import com.yodlee.yodleeApi.helper.SessionHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.utils.apiUtils.GroupUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;

/**
 * @author mallikan
 *
 */
public class TestDeleteGroup extends Base {
	String providerAccountId;
	public static Long providerAccountId1 = null;

	public String dagUN = "qe_YSTRefreshAPI.site16441.2";
	public String dagPwd = "site16441.2";
	public static final String ADD_ACCOUNTS_GROUP = "\\TestData\\CSVFiles\\Groups\\DeleteGroup\\DeleteGroupTestCases.csv";
	public static final String DELETE_ACCOUNTS_GROUP = "\\TestData\\CSVFiles\\Groups\\DeleteGroup\\DeleteAccountGroup.csv";

	Configuration configurationObj = Configuration.getInstance();
	LoginFormFactory loginFormFactory = new LoginFormFactory();
	ProvidersHelper providersHelper = new ProvidersHelper();
	ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
	CommonUtils commonUtils = new CommonUtils();
	JsonAssertionUtil jsonAssertionUtil = new JsonAssertionUtil();
	GroupUtils groupUtils = new GroupUtils();
	GroupsHelper groupsHelper = new GroupsHelper();


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

			if (configurationObj.isHybrid()) {
				providerAccountId1 = response.jsonPath().getLong("providerAccount[0].id");

			} else {
				providerAccountId1 = response.jsonPath().getLong("providerAccount.id");
			}
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
	public void DeleteGroupsFunction_user_session(String TestCaseId, String TestCaseName, String containerKey, String container,
			String groupKey, String group, String resFile, String filePath, String cobsessionTokenKey,
			String cobsessionToken, String userSessionTokenKey, String userSessionToken, int status, String enabledTest,
			String defectID) throws Exception {
		commonUtils.isTCEnabled(enabledTest, TestCaseId);

		System.out.println("enabledTest:::" + enabledTest);
		System.out.println("TestCaseName:::" + TestCaseName);
			String bodyParam = groupsHelper.createGroupJson(group);
			HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
			httpMethodParameters.setBodyParams(bodyParam);

			String cobSessionToken = SessionHelper.getSessionToken(cobsessionTokenKey, "cob");
			String userSession = SessionHelper.getSessionToken(userSessionTokenKey, "user");
			System.out.println("userSessionToken:::::::" + userSessionToken);
			/*EnvSession session = new EnvSession(cobSessionToken, userSession,
					configurationObj.getCobrandSessionObj().getPath());*/
			
			EnvSession session = EnvSession.builder().cobSession(cobSessionToken).userSession(userSession)
					.path(configurationObj.getCobrandSessionObj().getPath()).build();
			System.out.println("session iss:" + session);

			Response createGroupResponse = groupUtils.createGroup(httpMethodParameters,
					session);

			System.out.println("response.statusCode().::: " + createGroupResponse.statusCode());
			jsonAssertionUtil.assertResponse(createGroupResponse, HttpStatus.UNAUTHORISED, resFile, filePath);
		
	}
	
	@Test(description = "AT-67638") 
	public void DeleteGroups_67638() throws Exception {
		String TestCaseId = "AT-67638";
		String TestCaseName = "Delete Group with valid data";
		String containerKey = "containerKey";
		String container = "bank";
		String groupKey = "group";
		String group = "TestGroup";
		String resFile = "delete1.json";
		String filePath = "../src/test/resources/TestData/JSONFiles/AccountGroups/DeleteGroupJson/";
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
		String bodyParam = groupsHelper.createGroupJson(group);
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(bodyParam);
		Response createGroupResponse = groupUtils.createGroup(httpMethodParameters,
				Configuration.getInstance().getCobrandSessionObj());

		System.out.println("*****Inside TestcaseId-T-DelGrp-1******");

		Long groupIdUpdate = createGroupResponse.jsonPath().getLong("group.id");			
		LinkedHashMap<String, Object> pathParam =new LinkedHashMap<>(); 
		pathParam.put("groupIdUpdate", groupIdUpdate);
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setPathParams(pathParam);

		Response deleteResponse = groupUtils.deleteGroup(httpParams,
				Configuration.getInstance().getCobrandSessionObj());
		jsonAssertionUtil.assertResponse(deleteResponse, status, resFile, filePath);
	}
	
	@Test(description = "AT-67639") 
	public void DeleteGroups_67639() throws Exception {
		String TestCaseId = "AT-67639";
		String TestCaseName = "For Invalid Group ID";
		String containerKey = "containerKey";
		String container = "bank";
		String groupKey = "group";
		String group = "TestGroup";
		String resFile = "delete2.json";
		String filePath = "../src/test/resources/TestData/JSONFiles/AccountGroups/DeleteGroupJson/";
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
		Long gropuIdUpdate1 = 12345678910L;
		System.out.println("**********gropuIDUpdate1" + gropuIdUpdate1);
		String bodyParam = groupsHelper.createGroupJson(group);

		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		LinkedHashMap<String, Object> pathParam =new LinkedHashMap<>(); 
		pathParam.put("groupIdUpdate", gropuIdUpdate1);
		httpParams.setBodyParams(bodyParam);
		httpParams.setPathParams(pathParam);

		Response updateResponse = groupUtils.updateGroup(httpParams,
				Configuration.getInstance().getCobrandSessionObj());
		jsonAssertionUtil.assertResponse(updateResponse, status, resFile, filePath);
	}
	
	@Test(alwaysRun = true, enabled = true, dataProvider = Constants.FEEDER)
	@Source(DELETE_ACCOUNTS_GROUP)
	public void DeleteGroupsFunction(String TestCaseId, String TestCaseName, String containerKey, String container,
			String groupKey, String group, String resFile, String filePath, String cobsessionTokenKey,
			String cobsessionToken, String userSessionTokenKey, String userSessionToken, int status, String enabledTest,
			String defectID) throws Exception {
		commonUtils.isTCEnabled(enabledTest, TestCaseId);

		System.out.println("enabledTest:::" + enabledTest);
		System.out.println("TestCaseName:::" + TestCaseName);
		Long gropuIdUpdate1 = null;
		System.out.println("**********gropuIDUpdate1" + gropuIdUpdate1);
		String bodyParam = groupsHelper.createGroupJson(group);		
		LinkedHashMap<String, Object> pathParam =new LinkedHashMap<>(); 
		pathParam.put("groupIdUpdate", gropuIdUpdate1);
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setBodyParams(bodyParam);
		httpParams.setPathParams(pathParam);

		Response updateResponse = groupUtils.updateGroup(httpParams,
				Configuration.getInstance().getCobrandSessionObj());

		Response deleteResponse = groupUtils.deleteGroup(httpParams,
				Configuration.getInstance().getCobrandSessionObj());
		jsonAssertionUtil.assertResponse(deleteResponse, status, resFile, filePath);
	}
	
}
