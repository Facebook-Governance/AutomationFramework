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

package com.yodlee.yodleeApi.Statements;

import io.restassured.response.Response;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.databene.benerator.anno.Source;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.yodlee.app.yodleeApi.DagPages.DagHome;
import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.Constants;
import com.yodlee.yodleeApi.constants.HttpStatus;
import com.yodlee.yodleeApi.constants.JSONPaths;
import com.yodlee.yodleeApi.helper.AccountsHelper;
import com.yodlee.yodleeApi.helper.ProviderAccountsHelper;
import com.yodlee.yodleeApi.helper.SessionHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.utils.apiUtils.AccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.FastLinkAPI;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderUtils;
import com.yodlee.yodleeApi.utils.apiUtils.StatementUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

public class TestStatements extends Base {
	public List<Long> accountIdList;
	long providerAccountId;
	String providerId = "16441";
	

	private String dagUN = "AcntTest2.site16441.2";
	
	public Path currentDir = Paths.get(System.getProperty("user.dir")); 
	
	public Path chromedriverfilePath = Paths.get(currentDir.toString(), "driver", "chromedriver.exe");
	private String dagPwd = "site16441.2";

	public static final String GET_STATEMENTS = "\\TestData\\CSVFiles\\Statements\\GetStatements.csv";

	CommonUtils commonUtils = new CommonUtils();
	JsonAssertionUtil jsonAssertionUtil = new JsonAssertionUtil();
	ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
	AccountUtils accountUtils = new AccountUtils();
	AccountsHelper accountsHelper = new AccountsHelper();
	Configuration configurationObj= Configuration.getInstance();
	ProviderUtils providerUtils = new ProviderUtils();
	ProviderAccountsHelper providerAccountsHelper = new ProviderAccountsHelper();
	StatementUtils statementUtils= new StatementUtils();
	
	/**
	 * Before class that adds an account.
	 */
	@BeforeClass(alwaysRun = true)
	public void setUpTest() {
		Response getProviderAccountResponse = providerAccountUtils.addProviderAccountStrict(Long.parseLong(providerId),
				"simplified", dagUN, dagPwd, configurationObj.getCobrandSessionObj());
		providerAccountId = getProviderAccountResponse.jsonPath().getLong(JSONPaths.PROVIDER_ACC_ID);
		System.out.println("provider acct id before class:" + providerAccountId);
	}

	/**
	 * Test case that validates statements.
	 *
	 * @param TestCaseId
	 *            Test case id from CSV file. Test case name from CSV file.
	 * @param status
	 *            Status filter taken from CSV file.
	 * @param container
	 *            Container filter taken from CSV file.
	 * @param fromDate
	 *            fromDate value taken from CSV file.
	 * @param TestCaseName
	 *            TestCaseName filter value taken from CSV file.
	 * @param cobSessionToken
	 *            cobsession token value taken from CSV file.
	 * @param userSessionToken
	 *            user sessoin token value taken from CSV file.
	 * @param filePath
	 *            file path where JSON file is stored.
	 * @param resFile
	 *            response JSON file name.
	 * @throws Exception
	 */
	@Test(alwaysRun = true, enabled = true, dataProvider = Constants.FEEDER)
	@Source(GET_STATEMENTS)
	public void testGetStatements(String TestCaseId, String TestCaseName, String containerKey, String container,
			String isLatestKey, String isLatest, String AccStatusKey, String AccStatus, String fromDateKey,
			String fromDate, String resFile, String filePath, String cobSessionTokenKey, String cobSessionToken,
			String userSessionTokenKey, String userSessionToken, int status, String enabledTest, String defectID)
			throws Exception {
		commonUtils.isTCEnabled(enabledTest, TestCaseId);
		System.out.println("AccStatus: " + AccStatus);
		if (AccStatus.contains("TO_BE_CLOSED")) {
			System.setProperty("webdriver.chrome.driver", chromedriverfilePath.toString()); 

			WebDriver driver = new ChromeDriver();
			
			DagHome dagHome = new DagHome(driver);
			dagHome.launchURL();
			dagHome.catlogLogin("YSLTestDummy", "YSLTestDummy@1");// Catlog Login
			dagHome.uploadXmlFile("Dag Site - Credits", "DeletedCardContainer.xml");
			driver.quit();
			
			HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
			httpParams=providerAccountsHelper.createInputForAddProviderAcct("16441", "loginform", dagUN, dagPwd, Constants.TWO_COUNT);
			LinkedHashMap<String, Object> queryParam = new LinkedHashMap<>();
			queryParam.put("providerAccountIds", String.valueOf(providerAccountId));
			httpParams.setQueryParams(queryParam);
			
			Response response_acc = providerAccountUtils.updateProviderAccount(httpParams, configurationObj.getCobrandSessionObj());
			providerAccountUtils.checkRefreshStatusUntilActAddition(configurationObj.getCobrandSessionObj(), providerAccountId+"");
		}
	
		HttpMethodParameters httpMethodParameters = accountsHelper.createInputForGetAccounts(Constants.EMPTY_STRING,
				accountsHelper.getFilterValue(container), accountsHelper.getProviderAccountId(providerAccountId+""), null, Constants.EMPTY_STRING, null, null);
		Response getAccountsResponse = accountUtils.getAccounts(httpMethodParameters,
				configurationObj.getCobrandSessionObj());

		accountIdList = new ArrayList<>();

		JSONObject bankObject = new JSONObject(getAccountsResponse.asString());
		JSONArray accountArray = bankObject.getJSONArray(JSONPaths.ACCOUNT);
		System.out.println("*********Number of  accountID" + accountArray.length());

		for (int count = 0; count < accountArray.length(); count++) {
			JSONObject accountObject = accountArray.getJSONObject(count);
			long itemAccountId = accountObject.getLong(Constants.ID);
			accountIdList.add(itemAccountId);
			String accountID = String.valueOf(itemAccountId);
			System.out.println("**************Testing get transaction accountID" + accountID);
			System.out.println("Executing test case: " + TestCaseName);
			LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
			params.put("container", container);
			params.put("isLatest", isLatest);
			params.put("status", AccStatus);
			params.put("fromDate", fromDate);
			params.put("accountId", accountID);
			System.out.println("cobSessionToken" + cobSessionToken);
			System.out.println("userSessionToken" + userSessionToken);
			Response response;
			if (cobSessionToken.equalsIgnoreCase("INVALID") || cobSessionToken.contains("")
					|| cobSessionToken.contains("EXPIRED") || userSessionToken.equalsIgnoreCase("INVALID")
					|| userSessionToken.contains("") || userSessionToken.contains("EXPIRED")) {
				
				String cobSession = SessionHelper.getSessionToken(cobSessionToken, "cob");
				String userSession = SessionHelper.getSessionToken(userSessionToken, "user");
				
				EnvSession envSession= EnvSession.builder().cobSession(cobSession).userSession(userSession).path(configurationObj.getCobrandSessionObj().getPath()).build();
				
				HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
				httpParams.setQueryParams(params);
				
				
				response = statementUtils.getStatements(httpParams, envSession);
			} else {				
				HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
				httpParams.setQueryParams(params);
					
				response = statementUtils.getStatements(httpParams, configurationObj.getCobrandSessionObj());
			}

			System.out.println("response.statusCode()::: " + response.statusCode());
			if (response.statusCode() == HttpStatus.UNAUTHORISED) {
				System.out.println("response.statusCode()>>>>>>>>>>.::: ");
				System.out.println("Executing test >>>>>>>>>>>: ");
				
				jsonAssertionUtil.assertLenientJSON(response, HttpStatus.UNAUTHORISED, resFile, filePath);
			} else {
				System.out.println("Executing test for Active ");
				
				jsonAssertionUtil.assertLenientJSON(response, status, resFile, filePath);
			}
		}
		// reverting back to normal xml's
		if (AccStatus.contains("TO_BE_CLOSED")) {
			System.setProperty("webdriver.chrome.driver", chromedriverfilePath.toString()); 
			WebDriver driver1 = new ChromeDriver();
			DagHome dagHome1 = new DagHome(driver1);
			dagHome1.launchURL();
			dagHome1.catlogLogin("YSLTestDummy", "YSLTestDummy@1");// Catlog Login
			dagHome1.uploadXmlFile("Dag Site - Credits", "CardContainerupdated.xml");
			driver1.quit();

			HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
			httpParams=providerAccountsHelper.createInputForAddProviderAcct("16441", "loginform", dagUN, dagPwd, Constants.TWO_COUNT);
			LinkedHashMap<String, Object> queryParam = new LinkedHashMap<>();
			queryParam.put("providerAccountIds", String.valueOf(providerAccountId));
			httpParams.setQueryParams(queryParam);
			
			Response response_acc = providerAccountUtils.updateProviderAccount(httpParams, configurationObj.getCobrandSessionObj());
			providerAccountUtils.checkRefreshStatusUntilActAddition(configurationObj.getCobrandSessionObj(), providerAccountId+"");

		}
	}
}
