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

import java.util.List;
import org.databene.benerator.anno.Source;

import org.testng.annotations.Test;
import com.yodlee.yodleeApi.helper.GroupsHelper;
import com.yodlee.yodleeApi.helper.SessionHelper;
import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.Constants;
import com.yodlee.yodleeApi.helper.AccountsHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.utils.apiUtils.GroupUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

/**
 * @author mallikan
 *
 */
public class TestCreateGroup extends Base {
	public List<Long> accountIdList;
	public AccountsHelper accountsHelper;

	public String dagUN = "qe_YSTRefreshAPI.site16441.2";
	public String dagPwd = "site16441.2";
	String json;

	public static final String CREATE_GROUP = "\\TestData\\CSVFiles\\Groups\\CreateGroupData\\createGrooupCases.csv";
	CommonUtils commonUtils = new CommonUtils();
	GroupUtils groupUtils = new GroupUtils();	
	Configuration configurationObj = Configuration.getInstance();
	GroupsHelper groupsHelper = new GroupsHelper();
	JsonAssertionUtil jsonPath = new JsonAssertionUtil();



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
	@Source(CREATE_GROUP)
	public void createGroupTest(String TestCaseId, String TestCaseName, String containerKey, String container,
			String groupKey, String group, String resFile, String filePath, String cobsessionTokenKey,
			String cobsessionToken, String userSessionTokenKey, String userSessionToken, int status, String enabledTest,
			String defectID) throws Exception {
		System.out.println("**************Testing to create Group >>" + TestCaseId);
		commonUtils.isTCEnabled(enabledTest, TestCaseId);
		System.out.println("User Registration file is ready " + json);

		String bodyParam = groupsHelper.createGroupJson(group);
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(bodyParam);
			
		String cobSessionToken = SessionHelper.getSessionToken(cobsessionToken, "cob"); 
		String userSession = SessionHelper.getSessionToken(userSessionToken, "user");
					
//		EnvSession session = new EnvSession(cobSessionToken, userSession, configurationObj.getCobrandSessionObj().getPath()); 
		EnvSession session= EnvSession.builder().cobSession(cobSessionToken).userSession(userSession).path(configurationObj.getCobrandSessionObj().getPath()).build();

		Response response = groupUtils.createGroup(httpMethodParameters, session);
		
		if(TestCaseId.equalsIgnoreCase("AT-67617")) {
			response = groupUtils.createGroup(httpMethodParameters, session);
			System.out.println("Response is : "+response);
		}	
		jsonPath.assertResponse(response, status, resFile, filePath);

	}
}
