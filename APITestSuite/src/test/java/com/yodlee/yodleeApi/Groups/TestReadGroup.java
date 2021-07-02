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

import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
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
import com.yodlee.yodleeApi.constants.JSONPaths;
import com.yodlee.yodleeApi.constants.*;
import com.yodlee.yodleeApi.helper.AccountsHelper;
import com.yodlee.yodleeApi.helper.CobrandHelper;
import com.yodlee.yodleeApi.helper.GroupsHelper;
import com.yodlee.yodleeApi.helper.ProvidersHelper;
import com.yodlee.yodleeApi.helper.SessionHelper;
import com.yodlee.yodleeApi.helper.UserHelper;
import com.yodlee.yodleeApi.interfaces.Session;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.utils.apiUtils.AccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.CobrandUtils;
import com.yodlee.yodleeApi.utils.apiUtils.GroupUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

/**
 * @author bhuvaneshwari
 *
 */
public class TestReadGroup extends Base {
	public List<Long> accountIdList;
	
	public String dagUN = "qe_YSTRefreshAPI.site16441.2";
	public String dagPwd = "site16441.2";
	String json;
	String jsonadd;
	Long gropuIDUpdate;
	public  Long providerAccountId1 = null;
	public static final String READ_GROUP = "\\TestData\\CSVFiles\\Groups\\ReadGroup\\readGroup.csv";
	
	Configuration configurationObj = Configuration.getInstance();
	LoginFormFactory loginFormFactory = new LoginFormFactory();
	ProvidersHelper providersHelper = new ProvidersHelper();
	ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
	CommonUtils commonUtils = new CommonUtils();	
	GroupsHelper groupsHelper = new GroupsHelper();
	GroupUtils groupUtils = new GroupUtils();
	AccountsHelper accountsHelper = new AccountsHelper();
	AccountUtils accountUtils = new AccountUtils();
	JsonAssertionUtil jsonAssertionUtil= new JsonAssertionUtil();
	
	CobrandUtils cobrandUtils=new CobrandUtils();
	CobrandHelper cobrandHelper=new CobrandHelper();
	UserUtils userUtils=new UserUtils();
	UserHelper userHelper=new UserHelper();



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
					.getUpdatedLoginFormWithCredentials(providerId, Constants.SIMPLIFIED_FORM, Constants.TWO_COUNT, credentialMap);
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
	 *            user session token value taken from CSV file.
	 * @param status
	 *            TestCaseName filter value taken from CSV file.
	 * @param enabledTest
	 *            file path where JSON file is stored.
	 * @param defectID
	 *            response JSON file name.
	 */
	@Test(alwaysRun = true, enabled = true, dataProvider = Constants.FEEDER)
	@Source(READ_GROUP)
	public void readGroupTest(String TestCaseId, String TestCaseName, String containerKey, String container,
			String groupKey, String group, String resFile, String filePath, String cobsessionTokenKey,
			String cobsessionToken, String userSessionTokenKey, String userSessionToken, int status, String enabledTest,
			String defectID) throws Exception {
		commonUtils.isTCEnabled(enabledTest, TestCaseId);

		createGroupAndAddAccountJsons(TestCaseId, container, group);

			
			
			String cobSession = SessionHelper.getSessionToken(cobsessionToken, "cob");
			String userSession = SessionHelper.getSessionToken(userSessionToken, "user");
			EnvSession envSession = EnvSession.builder().cobSession(cobSession).userSession(userSession)
					.path(configurationObj.getCobrandSessionObj().getPath()).build();
			
			HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
			httpParams.setBodyParams(json);
			
			Response response = groupUtils.createGroup(httpParams, envSession);
			System.out.println("response.statusCode().::: " + response.statusCode());
			
			jsonAssertionUtil.assertResponse(response, HttpStatus.UNAUTHORISED, resFile, filePath);

	}
	
	@Test(description = "AT-67681") 
	public void readGroups_67681() throws Exception {
		String TestCaseId = "AT-67681";
		String TestCaseName = "Multiple group id's for GET API request";
		String containerKey = "containerKey";
		String container = "bank";
		String groupKey = "group";
		String group = "SavingGroup1";
		String resFile = "readGroup_1.json";
		String filePath = "../src/test/resources/TestData/JSONFiles/AccountGroups/ReadGroupJson/";
		String cobsessionTokenKey = "cobsession";
		String cobsessionToken = "VALID";
		String userSessionTokenKey = "userSession";
		String userSessionToken = "VALID";
		int status = 200;
		String enabledTest = "TRUE";
		String defectID = "0";
		
		System.out.println("STARTED TestCaseId:"+TestCaseId);
		System.out.println("TestCaseName:"+TestCaseName);

		commonUtils.isTCEnabled(enabledTest, TestCaseId);

		createGroupAndAddAccountJsons(TestCaseId, container, group);
		System.out.println("jsonAdd is::::::::"+jsonadd);


		System.out.println("**************Testing AT-67681");
		
		
		String cobSession = SessionHelper.getSessionToken(cobsessionToken, "cob");
		String userSession = SessionHelper.getSessionToken(userSessionToken, "user");
		EnvSession envSession = EnvSession.builder().cobSession(cobSession).userSession(userSession)
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		
		HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
		httpParam.setBodyParams(json);
		Response response = groupUtils.createGroup(httpParam, envSession);


		Long gropuIDUpdate = response.jsonPath().getLong("group.id");
		
		String path="" + gropuIDUpdate + Constants.PATH_SEPARATOR +URLConstants.ACCOUNT_ADD ;
		System.out.println("*************response gropuIDUpdate::" + gropuIDUpdate);
		System.out.println("path::"+path);
		HttpMethodParameters httpMethodParameters = groupsHelper.createPathParam("gropuIDUpdate", path);
		httpMethodParameters.setBodyParams(jsonadd);

		Response resUpdateGrp = groupUtils.updateGroup(httpMethodParameters, envSession);
		
		String pathParam=URLConstants.ACCOUNT_READ+ gropuIDUpdate;
		
		HttpMethodParameters updateParam = groupsHelper.createPathParam("gropuIDUpdate", pathParam);
		
		/*LinkedHashMap<String, Object> queryParam = new LinkedHashMap<String, Object>();
		queryParam.put("include", "accounts");
		queryParam.put("groupId", gropuIDUpdate);
		
		HttpMethodParameters httpMethodParams = HttpMethodParameters.builder().build();
		httpMethodParams.setQueryParams(queryParam);*/
		
		Response resReadGroup = groupUtils.readGroup(updateParam, envSession);
		
		System.out.println("**********response for  AT-67681 groups::" + resReadGroup.asString());
		
		jsonAssertionUtil.assertResponse(resReadGroup, status, resFile, filePath);
		HttpMethodParameters deleteParam = groupsHelper.createPathParam("gropuIDUpdate", gropuIDUpdate);

		Response responseDelete = groupUtils.deleteGroup(deleteParam, configurationObj.getCobrandSessionObj());
		System.out.println("**********response for groups::" + responseDelete.asString());
	}
	
	@Test(description = "AT-67682") 
	public void readGroups_67682() throws Exception {
		String TestCaseId = "AT-67682";
		String TestCaseName = "If user provides “include=accounts” as QueryParams in GET API request";
		String containerKey = "containerKey";
		String container = "bank";
		String groupKey = "group";
		String group = "SavingGroup2";
		String resFile = "readGroup_2.json";
		String filePath = "../src/test/resources/TestData/JSONFiles/AccountGroups/ReadGroupJson/";
		String cobsessionTokenKey = "cobsession";
		String cobsessionToken = "VALID";
		String userSessionTokenKey = "userSession";
		String userSessionToken = "VALID";
		int status = 200;
		String enabledTest = "TRUE";
		String defectID = "0";

		commonUtils.isTCEnabled(enabledTest, TestCaseId);

		createGroupAndAddAccountJsons(TestCaseId, container, group);

		System.out.println("**************Testing AT-67682");

		HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
		httpParam.setBodyParams(json);
		
		String cobSession = SessionHelper.getSessionToken(cobsessionToken, "cob");
		String userSession = SessionHelper.getSessionToken(userSessionToken, "user");
		
		/*EnvSession envSession = new EnvSession(cobSession, userSession, configurationObj.getCobrandSessionObj().getPath());*/
		
		EnvSession envSession = EnvSession.builder().cobSession(cobSession).userSession(userSession)
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		
		Response response = groupUtils.createGroup(httpParam, envSession);

		System.out.println("Response after the registering user" + response.prettyPrint());
		System.out.println("response.statusCode()::: " + response.statusCode());

		Long gropuIDUpdate = response.jsonPath().getLong("group.id");
		System.out.println("*************response gropuIDUpdate::" + gropuIDUpdate);

		String path=""+ gropuIDUpdate+Constants.PATH_SEPARATOR + URLConstants.ACCOUNT_ADD; 
		
		HttpMethodParameters updateParam = groupsHelper.createPathParam("gropuIDUpdate", path);
		updateParam.setBodyParams(jsonadd);

		Response resUpdateGrp = groupUtils.updateGroup(updateParam, envSession);

		LinkedHashMap<String, Object> queryParam = new LinkedHashMap<String, Object>();
		queryParam.put("include", "accounts");
		
		HttpMethodParameters httpMethodParams = HttpMethodParameters.builder().build();
		httpMethodParams.setQueryParams(queryParam);
		
		Response resReadGroup = groupUtils.readGroup(httpMethodParams, envSession);

		System.out.println("**********response for  AT-67682::" + resReadGroup.asString());

		jsonAssertionUtil.assertResponse(resReadGroup, status, resFile, filePath);
		HttpMethodParameters deleteParam = groupsHelper.createPathParam("gropuIDUpdate", gropuIDUpdate);

		Response responsedelete = groupUtils.deleteGroup(deleteParam, configurationObj.getCobrandSessionObj());
	}
	
	
	@Test(description = "AT-67683") 
	public void readGroups_67683() throws Exception {
		String TestCaseId = "AT-67683";
		String TestCaseName = "If user provides invalid groupId as QueryParam";
		String containerKey = "containerKey";
		String container = "bank";
		String groupKey = "group";
		String group = "SavingGroup3";
		String resFile = "readGroup_3.json";
		String filePath = "../src/test/resources/TestData/JSONFiles/AccountGroups/ReadGroupJson/";
		String cobsessionTokenKey = "cobsession";
		String cobsessionToken = "VALID";
		String userSessionTokenKey = "userSession";
		String userSessionToken = "VALID";
		int status = 400;
		String enabledTest = "TRUE";
		String defectID = "0";

		commonUtils.isTCEnabled(enabledTest, TestCaseId);

		createGroupAndAddAccountJsons(TestCaseId, container, group);

		System.out.println("**************Testing AT-67683");
		
		HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
		httpParam.setBodyParams(json);
		
		/*String cobSession = SessionHelper.getSessionToken(cobsessionToken, "cob");
		String userSession = SessionHelper.getSessionToken(userSessionToken, "user");*/
		
		
		
		HttpMethodParameters httpParms=cobrandHelper.createInputForCobrandLogin(configurationObj.getCobrandObj());
		String cobSession =cobrandUtils.getCobrandLoginToken(httpParms, configurationObj.getCobrandSessionObj().getPath());
		
		HttpMethodParameters httpMethodParameters=userHelper.createInputForUserReg(configurationObj.getUserObj());
		EnvSession sessionObj=EnvSession.builder().build();
		sessionObj.setCobSession(cobSession);
		sessionObj.setPath(configurationObj.getCobrandSessionObj().getPath());
		String userSession =userUtils.userLogin(httpMethodParameters, sessionObj);
				
		EnvSession envSession = EnvSession.builder().cobSession(cobSession).userSession(userSession)
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		
		Response response = groupUtils.createGroup(httpParam, envSession);

		System.out.println("Response after the registering user" + response.prettyPrint());
		System.out.println("response.statusCode()::: " + response.statusCode());

		Long gropuIDUpdate1 = response.jsonPath().getLong("group.id");
		System.out.println("*************response gropuIDUpdate::" + gropuIDUpdate);
		String path=""+ gropuIDUpdate+Constants.PATH_SEPARATOR + URLConstants.ACCOUNT_ADD; 
		
		HttpMethodParameters updateParam = groupsHelper.createPathParam("gropuIDUpdate", path);
		updateParam.setBodyParams(jsonadd);

		Response resUpdateGrp = groupUtils.updateGroup(updateParam, envSession);
								
		Long gropuIDUpdate = 7676762L;
		
		
	
	/*	String pathParam=""+ gropuIDUpdate+Constants.PATH_SEPARATOR + URLConstants.ACCOUNT_READ; 

		HttpMethodParameters updateParams = groupsHelper.createPathParam("gropuIDUpdate", pathParam);*/
		
		LinkedHashMap<String, Object> queryParam = new LinkedHashMap<String, Object>();
		queryParam.put("include", "accounts");
		queryParam.put("groupId", gropuIDUpdate);
		
		HttpMethodParameters httpMethodParams = HttpMethodParameters.builder().build();
		httpMethodParams.setQueryParams(queryParam);

		Response resReadGroup = groupUtils.readGroup(httpMethodParams, envSession);
		System.out.println("**********response for  AT-67683::" + resReadGroup.asString());
		
		jsonAssertionUtil.assertResponse(resReadGroup, status, resFile, filePath);
		HttpMethodParameters deleteParam = groupsHelper.createPathParam("gropuIDUpdate", gropuIDUpdate1);

		Response responsedelete = groupUtils.deleteGroup(deleteParam, configurationObj.getCobrandSessionObj());
	}
	
	@Test(description = "AT-67684") 
	public void readGroups_67684() throws Exception {
		String TestCaseId = "AT-67684";
		String TestCaseName = "If user provides “include=accounts” as QueryParams ,without groupID's";
		String containerKey = "containerKey";
		String container = "bank";
		
		
		
		String groupKey = "group";
		String group = "SavingGroup4";
		String resFile = "readGroup_4.json";
		String filePath = "../src/test/resources/TestData/JSONFiles/AccountGroups/ReadGroupJson/";
		String cobsessionTokenKey = "cobsession";
		String cobsessionToken = "VALID";
		String userSessionTokenKey = "userSession";
		String userSessionToken = "VALID";
		int status = 400;
		String enabledTest = "TRUE";
		String defectID = "0";
				
		commonUtils.isTCEnabled(enabledTest, TestCaseId);

		createGroupAndAddAccountJsons(TestCaseId, container, group);

		System.out.println("**************Testing AT-67684");
		HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
		httpParam.setBodyParams(json);
		
		String cobSession = SessionHelper.getSessionToken(cobsessionToken, "cob");
		String userSession = SessionHelper.getSessionToken(userSessionToken, "user");
		
		EnvSession envSession = EnvSession.builder().cobSession(cobSession).userSession(userSession)
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		
		
		
		Response response = groupUtils.createGroup(httpParam, envSession);

		System.out.println("Response after the registering user" + response.prettyPrint());
		System.out.println("response.statusCode()::: " + response.statusCode());

		Long groupIDUpdate = response.jsonPath().getLong("group.id");
		System.out.println("*************response gropuIDUpdate::" + gropuIDUpdate);
		String path=""+ groupIDUpdate+Constants.PATH_SEPARATOR + URLConstants.ACCOUNT_ADD; 
		
		HttpMethodParameters updateParam = groupsHelper.createPathParam("groupIDUpdate", path);
		updateParam.setBodyParams(jsonadd);

		Response resUpdateGrp = groupUtils.updateGroup(updateParam, envSession);
	
		LinkedHashMap<String, Object> queryParam = new LinkedHashMap<String, Object>();
		queryParam.put("include", "accounts");
		
		HttpMethodParameters httpMethodParams = HttpMethodParameters.builder().build();
		httpMethodParams.setQueryParams(queryParam);
		
		Response resReadGroup = groupUtils.readGroup(httpMethodParams, envSession);
		
		
		
		System.out.println("**********response for  AT-67684::" + resReadGroup.asString());
	
		jsonAssertionUtil.assertResponse(resReadGroup, status, resFile, filePath);
		HttpMethodParameters deleteParam = groupsHelper.createPathParam("gropuIDUpdate", groupIDUpdate);

		Response responseDelete = groupUtils.deleteGroup(deleteParam, configurationObj.getCobrandSessionObj());
	}
	
	@Test(description = "AT-67685") 
	public void readGroups_67685() throws Exception {
		String TestCaseId = "AT-67685";
		String TestCaseName = "If user not provide the include accounts and group id's ";
		String containerKey = "containerKey";
		String container = "bank";
		String groupKey = "group";
		String group = "SavingGroup5";
		String resFile = "readGroup_5.json";
		String filePath = "../src/test/resources/TestData/JSONFiles/AccountGroups/ReadGroupJson/";
		String cobsessionTokenKey = "cobsession";
		String cobsessionToken = "VALID";
		String userSessionTokenKey = "userSession";
		String userSessionToken = "VALID";
		int status = 200;
		String enabledTest = "TRUE";
		String defectID = "0";
			
		commonUtils.isTCEnabled(enabledTest, TestCaseId);
		
		createGroupAndAddAccountJsons(TestCaseId, container, group);

		System.out.println("**************Testing AT-67685");
		HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
		httpParam.setBodyParams(json);
		
		String cobSession = SessionHelper.getSessionToken(cobsessionToken, "cob");
		String userSession = SessionHelper.getSessionToken(userSessionToken, "user");
		
		/*EnvSession envSession = new EnvSession(cobSession, userSession, configurationObj.getCobrandSessionObj().getPath());*/
		
		EnvSession envSession = EnvSession.builder().cobSession(cobSession).userSession(userSession)
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		
		Response response = groupUtils.createGroup(httpParam, envSession);

		System.out.println("Response after the registering user" + response.prettyPrint());
		System.out.println("response.statusCode()::: " + response.statusCode());

		Long groupIDUpdate = response.jsonPath().getLong("group.id");
		System.out.println("*************response gropuIDUpdate::" + groupIDUpdate);
		String path=""+ groupIDUpdate+Constants.PATH_SEPARATOR + URLConstants.ACCOUNT_ADD; 

		HttpMethodParameters updateParam = groupsHelper.createPathParam("gropuIDUpdate", path);
		updateParam.setBodyParams(jsonadd);

		Response resUpdateGrp = groupUtils.updateGroup(updateParam, envSession);
		/*String readUrl = "";
	
		String pathParam=""+Constants.PATH_SEPARATOR+readUrl;
		HttpMethodParameters httpMethodParameters = groupsHelper.createPathParam("groupIDUpdate", pathParam);
		
		Response resReadGroup5 = groupUtils.updateGroup(httpMethodParameters, envSession);*/
		
		LinkedHashMap<String, Object> queryParam = new LinkedHashMap<String, Object>();
		queryParam.put("include", "accounts");
		
		HttpMethodParameters httpMethodParams = HttpMethodParameters.builder().build();
		/*httpMethodParams.setQueryParams(queryParam);*/
		
		Response resReadGroup = groupUtils.readGroup(httpMethodParams, envSession);

		
		System.out.println("**********response for  AT-67685::" + resReadGroup.asString());
	
		jsonAssertionUtil.assertResponse(resReadGroup, status, resFile, filePath);
		HttpMethodParameters deleteParam = groupsHelper.createPathParam("gropuIDUpdate", gropuIDUpdate);

		Response responseDelete = groupUtils.deleteGroup(deleteParam, configurationObj.getCobrandSessionObj());
	}
	
	@Test(description = "AT-67686") 
	public void readGroups_67686() throws Exception {
		String TestCaseId = "AT-67686";
		String TestCaseName = "if in URL include parameter contains incorrect word \"accounts\" for example include = accounts";
		String containerKey = "containerKey";
		String container = "bank";
		String groupKey = "group";
		String group = "SavingGroup7";
		String resFile = "readGroup_7.json";
		String filePath = "../src/test/resources/TestData/JSONFiles/AccountGroups/ReadGroupJson/";
		String cobsessionTokenKey = "cobsession";
		String cobsessionToken = "VALID";
		String userSessionTokenKey = "userSession";
		String userSessionToken = "VALID";
		int status = 400;
		String enabledTest = "TRUE";
		String defectID = "0";
			
		commonUtils.isTCEnabled(enabledTest, TestCaseId);

		createGroupAndAddAccountJsons(TestCaseId, container, group);

		System.out.println("**************Testing AT-67686");

		HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
		httpParam.setBodyParams(json);
		
		String cobSession = SessionHelper.getSessionToken(cobsessionToken, "cob");
		String userSession = SessionHelper.getSessionToken(userSessionToken, "user");
		
		/*EnvSession envSession = new EnvSession(cobSession, userSession, configurationObj.getCobrandSessionObj().getPath());*/
		
		EnvSession envSession = EnvSession.builder().cobSession(cobSession).userSession(userSession)
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		
		Response response = groupUtils.createGroup(httpParam, envSession);

		System.out.println("Response after the registering user" + response.prettyPrint());
		System.out.println("response.statusCode()::: " + response.statusCode());

		Long groupIDUpdate = response.jsonPath().getLong("group.id");
		System.out.println("*************response gropuIDUpdate::" + groupIDUpdate);

		String path=""+ groupIDUpdate+Constants.PATH_SEPARATOR + URLConstants.ACCOUNT_ADD; 
		
		HttpMethodParameters updateParam = groupsHelper.createPathParam("gropuIDUpdate", path);
		updateParam.setBodyParams(jsonadd);

		Response resUpdateGrp = groupUtils.updateGroup(updateParam, envSession);
		
		String readUrl = "?include=acunts&groupId=";
		
		String pathParam=""+Constants.PATH_SEPARATOR+readUrl;
		HttpMethodParameters httpMethodParameters = groupsHelper.createPathParam("groupIDUpdate", pathParam);
		
		Response resReadGroup6 = groupUtils.updateGroup(httpMethodParameters, envSession);

		System.out.println("**********response for  AT-67686::" + resReadGroup6.asString());
		
		LinkedHashMap<String, Object> queryParam = new LinkedHashMap<String, Object>();
		queryParam.put("include", "acunts");
		queryParam.put("groupId", groupIDUpdate);
		
		HttpMethodParameters httpMethodParams = HttpMethodParameters.builder().build();
		httpMethodParams.setQueryParams(queryParam);
		
		Response resReadGroup = groupUtils.readGroup(httpMethodParams, envSession);
		
		jsonAssertionUtil.assertResponse(resReadGroup, status, resFile, filePath);

		HttpMethodParameters deleteParam = groupsHelper.createPathParam("gropuIDUpdate", gropuIDUpdate);

		Response responseDelete = groupUtils.deleteGroup(deleteParam, configurationObj.getCobrandSessionObj());
	}
	
	@Test(description = "AT-67687") 
	public void readGroups_67687() throws Exception {
		String TestCaseId = "AT-67687";
		String TestCaseName = "If user provides empty group ID";
		String containerKey = "containerKey";
		String container = "bank";
		String groupKey = "group";
		String group = "SavingGroup8";
		String resFile = "readGroup_8.json";
		String filePath = "../src/test/resources/TestData/JSONFiles/AccountGroups/ReadGroupJson/";
		String cobsessionTokenKey = "cobsession";
		String cobsessionToken = "VALID";
		String userSessionTokenKey = "userSession";
		String userSessionToken = "VALID";
		int status = 400;
		String enabledTest = "TRUE";
		String defectID = "0";
		
		commonUtils.isTCEnabled(enabledTest, TestCaseId);

		createGroupAndAddAccountJsons(TestCaseId, container, group);

		System.out.println("**************Testing AT-67687");

		HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
		httpParam.setBodyParams(json);
		
		String cobSession = SessionHelper.getSessionToken(cobsessionToken, "cob");
		String userSession = SessionHelper.getSessionToken(userSessionToken, "user");
		
		EnvSession envSession = EnvSession.builder().cobSession(cobSession).userSession(userSession)
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		
		Response response = groupUtils.createGroup(httpParam, envSession);

		System.out.println("Response after the registering user" + response.prettyPrint());
		System.out.println("response.statusCode()::: " + response.statusCode());

		Long groupIDUpdate = response.jsonPath().getLong("group.id");
		System.out.println("*************response gropuIDUpdate::" + groupIDUpdate);

		String path=""+ groupIDUpdate+Constants.PATH_SEPARATOR + URLConstants.ACCOUNT_ADD; 
		
		HttpMethodParameters updateParam = groupsHelper.createPathParam("groupIDUpdate", path);
		updateParam.setBodyParams(jsonadd);

		Response resUpdateGrp = groupUtils.updateGroup(updateParam, envSession);
		groupIDUpdate = null;
		

		
		LinkedHashMap<String, Object> queryParam = new LinkedHashMap<String, Object>();
		queryParam.put("include", "accounts");
		queryParam.put("groupId", "");		
		HttpMethodParameters httpMethodParams = HttpMethodParameters.builder().build();
		httpMethodParams.setQueryParams(queryParam);
		
		Response resReadGroup = groupUtils.readGroup(httpMethodParams, envSession);
					
	

		System.out.println("**********response for  AT-67687::" + resReadGroup.asString());
		
		jsonAssertionUtil.assertResponse(resReadGroup, status, resFile, filePath);

		HttpMethodParameters deleteParam = groupsHelper.createPathParam("gropuIDUpdate", gropuIDUpdate);

		Response responseDelete = groupUtils.deleteGroup(deleteParam, configurationObj.getCobrandSessionObj());
	}
	
	@Test(description = "AT-67688") 
	public void readGroups_67688() throws Exception {
		String TestCaseId = "AT-67688";
		String TestCaseName = "no groups for the user";
		String containerKey = "containerKey";
		String container = "bank";
		String groupKey = "group";
		String group = "SavingGroup9";
		String resFile = "readGroup_9.json";
		String filePath = "../src/test/resources/TestData/JSONFiles/AccountGroups/ReadGroupJson/";
		String cobsessionTokenKey = "cobsession";
		String cobsessionToken = "VALID";
		String userSessionTokenKey = "userSession";
		String userSessionToken = "VALID";
		int status = 400;
		String enabledTest = "TRUE";
		String defectID = "0";
	
		commonUtils.isTCEnabled(enabledTest, TestCaseId);

		createGroupAndAddAccountJsons(TestCaseId, container, group);

		System.out.println("**************Testing AT-67688");

		HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
		httpParam.setBodyParams(json);
		
		String cobSession = SessionHelper.getSessionToken(cobsessionToken, "cob");
		String userSession = SessionHelper.getSessionToken(userSessionToken, "user");
		
	/*	EnvSession envSession = new EnvSession(cobSession, userSession, configurationObj.getCobrandSessionObj().getPath());*/
		
		EnvSession envSession = EnvSession.builder().cobSession(cobSession).userSession(userSession)
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		
		Response response = groupUtils.createGroup(httpParam, envSession);

		System.out.println("Response after the registering user" + response.prettyPrint());
		System.out.println("response.statusCode()::: " + response.statusCode());

		Long gropuIDUpdate = response.jsonPath().getLong("group.id");
		System.out.println("*************response gropuIDUpdate::" + gropuIDUpdate);

		HttpMethodParameters deleteParam = groupsHelper.createPathParam("gropuIDUpdate", gropuIDUpdate);

		Response responseDelete = groupUtils.deleteGroup(deleteParam, configurationObj.getCobrandSessionObj());

		String path=""+ gropuIDUpdate+Constants.PATH_SEPARATOR + URLConstants.ACCOUNT_ADD; 
		
		HttpMethodParameters updateParam = groupsHelper.createPathParam("gropuIDUpdate", path);
		updateParam.setBodyParams(jsonadd);

		Response resUpdateGrp = groupUtils.updateGroup(updateParam, envSession);

		
		
		// gropuIDUpdate ="" ;
		
		/*String pathParam=""+gropuIDUpdate+Constants.PATH_SEPARATOR + URLConstants.ACCOUNT_READ; 
		HttpMethodParameters updateParams = groupsHelper.createPathParam("groupIDUpdate", pathParam);
		
		Response resReadGroup8 = groupUtils.readGroup(updateParams, envSession);*/
		
		
		
		LinkedHashMap<String, Object> queryParam = new LinkedHashMap<String, Object>();
		queryParam.put("groupId", "");
		queryParam.put("include", "accounts");
		HttpMethodParameters httpMethodParams = HttpMethodParameters.builder().build();
		httpMethodParams.setQueryParams(queryParam);
		
		Response resReadGroup = groupUtils.readGroup(httpMethodParams, envSession);
		
		
		System.out.println("**********response for  AT-67688::" + resReadGroup.asString());
		
		jsonAssertionUtil.assertResponse(resReadGroup, status, resFile, filePath);
	}
	
	

	private void createGroupAndAddAccountJsons(String TestCaseId, String container, String group) {
		System.out.println("**************Testing to create Group >>" + TestCaseId);

		json = groupsHelper.createGroupJson(group);
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		Map<String, Object> queryParams = accountsHelper.createQueryParamsForGetAccounts(null,
				accountsHelper.getFilterValue(container), String.valueOf(providerAccountId1), null, null, null, null);
		httpParams.setQueryParams(queryParams);

		Response getAccountsResponse = accountUtils.getAccounts(httpParams, configurationObj.getCobrandSessionObj());

		accountIdList = new ArrayList<>();
		System.out.println("User Registration file is ready " + json);

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
		
		
		/*{
			"group": [{
				"account": [{
					"id": 10094365
				}]
			}]
		} */
		
	}
}
