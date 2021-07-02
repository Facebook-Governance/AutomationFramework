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

package com.yodlee.yodleeApi.Accounts;

import io.restassured.response.Response;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.databene.benerator.anno.Source;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.yodlee.taas.annote.Feature;
import com.yodlee.taas.annote.FeatureName;
import com.yodlee.taas.annote.Info;
import com.yodlee.taas.annote.SubFeature;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.common.LoginFormFactory;
import com.yodlee.yodleeApi.common.NonSdgLoginForm;
import com.yodlee.yodleeApi.constants.Constants;
import com.yodlee.yodleeApi.constants.JSONPaths;
import com.yodlee.yodleeApi.enumType.LoanAccountTypes;
import com.yodlee.yodleeApi.helper.AccountsHelper;
import com.yodlee.yodleeApi.helper.ProviderAccountsHelper;
import com.yodlee.yodleeApi.helper.TestTemplate;
import com.yodlee.yodleeApi.interfaces.Session;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.utils.apiUtils.AccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderUtils;
import com.yodlee.yodleeApi.utils.commonUtils.PropertyFileUtil;

/**
 * @author bhuvaneshwari
 *
 */
@Feature(featureName = { FeatureName.ACCOUNTS })
public class TestGetAccountSust extends TestTemplate {

	public List<Long> insuranceAccountIdList = new ArrayList<>();;
	public List<Long> loanAccountIdList = new ArrayList<>();;
	public static Long providerAccountId1;
	public String dagUN = "GetAccountsDag.site16441.1";
	public String dagPwd = "site16441.1";
	String providerObejct = "providerobject";
	String providerId = "16442";
	String includeDataSet = "ACCT_PROFILE.FULL_ACCT_NUMBER,ACCT_PROFILE.BANK_TRANSFER_CODE,ACCT_PROFILE.HOLDER_NAME";

	public Map<String, String> accNameAccNumberMap = new HashMap<String, String>();
	public AccountsHelper accountsHelper = new AccountsHelper();
	ProviderAccountsHelper providerAccountHelper = new ProviderAccountsHelper();
	Configuration configurationObj = Configuration.getInstance();
	NonSdgLoginForm nonSdgLoginForm = NonSdgLoginForm.getInstance();


	private static final Map<Integer, String> frequencyTypeMapforLoanContainer = new HashMap<Integer, String>() {
		{
			put(0, "WEEKLY");
			put(1, "SEMI_MONTHLY");
			put(2, "QUARTERLY");
			put(3, "ANNUALLY");
		}
	};

	// SustBug 969746 - In Stage, incorrect value for frequency: Qbroot
	@BeforeClass(enabled = true)

	public void setUpTest() {

		PropertyFileUtil propertyFileUtilObj = new PropertyFileUtil();

		if (configurationObj.isUserStatic() && !configurationObj.isFirstRun()) {

			Properties properties = propertyFileUtilObj.loadParameters("providerAccountId.properties");
			providerAccountId1 = Long.parseLong(properties.getProperty(getClassName()));
			System.out.println("providerAccountId111:" + providerAccountId1);
		} else {
			logger.info("********* EXECUTING BEFORE CLASS FOR ACTIVE STATUS*****************");
			long providerId = 16441;
			providerAccountId1 = providerId;

			try {
				Configuration configurationObj = Configuration.getInstance();
				ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
				Response response = providerAccountUtils.addProviderAccountStrict(providerId, Constants.SIMPLIFIED_FORM,
						"GetAccountsDag.site16441.1", "site16441.1", configurationObj.getCobrandSessionObj());
				providerAccountId1 = response.jsonPath().getLong(JSONPaths.PROVIDER_ACC_ID);
				System.out.println("providerAccountId1::::===" + providerAccountId1);

				if (configurationObj.isHybrid()) {
					providerAccountId1 = response.jsonPath().getLong("providerAccount[0].id");

				} else {
					providerAccountId1 = response.jsonPath().getLong("providerAccount.id");
				}
				System.out.println("providerAccountId1::::===" + providerAccountId1);

				if (configurationObj.isUserStatic()) {
					propertyFileUtilObj.writePropertyFile(getClassName(), String.valueOf(providerAccountId1));
				}
				logger.info("Provider Account Id : " + providerAccountId1);
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
		loadMap();
	}

	@Test(dataProvider = "feeder", enabled = true, priority = 1)
	@Source("\\TestData\\CSVFiles\\Accounts\\GetAccounts\\testGetAccountsPositiveScenarios.csv")

	public void testGetAccountsPositiveScenariosForActiveStatus(String tcId, String tcName, String status,
			String container, String accountReconType, String filePath, String resFile, String defectId, String enabled,
			String ignoreKeys, String specific) throws FileNotFoundException {
		logger.info("********* Get Account Frequency Sust *****************");
		commonUtils.isTCEnabled(enabled, tcId);

		System.out.println("TestCase id is " + tcId + " TestCase Name is " + tcName);
		Response getAccountsResponse = null;
		try {
			/*
			 * getAccountsResponse =
			 * ApiUtils.getAccountSummary(accountsHelper.getFilterValue(status),
			 * accountsHelper.getFilterValue(container), String.valueOf(providerAccountId1),
			 * accountsHelper.getFilterValue(accountReconType),
			 * InitialLoader.cobSessionObj);
			 */

			Map<String, Object> queryParam = accountsHelper.createQueryParamsForGetAccounts(
					accountsHelper.getFilterValue(status), accountsHelper.getFilterValue(container),
					String.valueOf(providerAccountId1), null, accountsHelper.getFilterValue(accountReconType), null,
					null);

			HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
			httpMethodParameters.setQueryParams(queryParam);

			getAccountsResponse = accountUtils.getAccounts(httpMethodParameters,
					configurationObj.getCobrandSessionObj());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test various accountTypes of Loan Container.
	 */
	@Test(enabled = true, dataProvider = "feeder", priority = 7)
	@Source("\\TestData\\CSVFiles\\Accounts\\GetAccounts\\testGetAccountsForLoanContainer.csv")

	public void testGetAccountsForLoanContainer(String testcaseID, String steps, String input, String enabled,
			String defectID) {
		logger.info("********* Priority 7 *****************");
		/*
		 * Response getAccountsResponse =
		 * ApiUtils.getAccountSummary(Constants.ACTIVE_STATUS, Constants.LOAN_CONTAINER,
		 * Constants.EMPTY_STRING, Constants.EMPTY_STRING, InitialLoader.cobSessionObj);
		 * getAccountsResponse.then().log().all();
		 */

		AccountUtils accountUtils = new AccountUtils();
		AccountsHelper accountsHelper = new AccountsHelper();
		Map<String, Object> queryParam = accountsHelper.createQueryParamsForGetAccounts(Constants.ACTIVE_STATUS,
				Constants.LOAN_CONTAINER, null, null, null, null, null);

		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setQueryParams(queryParam);
		Response getAccountsResponse = accountUtils.getAccounts(httpMethodParameters,
				configurationObj.getCobrandSessionObj());

		try {
			JSONObject loanObject = new JSONObject(getAccountsResponse.asString());
			System.out.println("Loan Object : " + loanObject.toString());
			System.out.println("JSONPaths.ACCOUNT"+JSONPaths.ACCOUNT);
			JSONArray accountArray = loanObject.getJSONArray(JSONPaths.ACCOUNT);
			for (int count = 0; count < accountArray.length(); count++) {

				String frequency = accountArray.getJSONObject(count).getString("frequency");
				System.out.println(" Array at Count " + count + " is :" + accountArray.getJSONObject(count));
				System.out.println("FrequencyType :" + frequency);
				// LoanAccountTypes[] loanAccountType = LoanAccountTypes.values();
				int c = 0;
				for (int cou = 0; cou < frequencyTypeMapforLoanContainer.size(); cou++) {
					if (frequency.equalsIgnoreCase(frequencyTypeMapforLoanContainer.get(Integer.valueOf(cou)))) {
						c = cou;
						break;
					}

				}
				System.out.println("Type for FrequencyEnumMap : "
						+ frequencyTypeMapforLoanContainer.get(Integer.valueOf(c)) + " and value of c is " + c);
				Assert.assertEquals(frequency, frequencyTypeMapforLoanContainer.get(Integer.valueOf(c)),
						"Frequency Types Match for Loan Container");
			}

		} catch (JSONException e) {
			logger.error("Failed to convert get accounts response to JSONObject : " + e);
			e.printStackTrace();
			Assert.fail("Failed to create JSONObject of account response.");
			
		}

	}

	@Test(priority = 4, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Accounts\\GetAccounts\\MFAUsingSimplifiedLoginForm.csv")
	public void testMFAUsingSimplifiedLoginForm(String testcaseID, String testcaseName, String defectID,
			String enabled) {
		commonUtils.isTCEnabled(enabled, testcaseID);
		Long providerId = 16442L;

		JSONObject loginFormMFA = NonSdgLoginForm.loginFormMap.get(Long.valueOf(providerId));
		System.out.println("Login Form MFA " + loginFormMFA);
		String updatedLoginForm = null;

		Map<String, String> credentialsMap = new HashMap<>();
		credentialsMap.put(Constants.LOGIN_NAME, dagUN);
		credentialsMap.put(Constants.PASSWORD, dagPwd);

		try {
			updatedLoginForm = nonSdgLoginForm.getUpdatedLoginFormWithCredentials(providerId, loginFormMFA.toString(),
					Constants.TWO_COUNT, credentialsMap);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		System.out.println("&&&&&& Updated Login Form Object " + updatedLoginForm.toString());

		Long providerAccountId = addMfaAccount(updatedLoginForm, "16442", configurationObj.getCobrandSessionObj());

		System.out.println("Site account id is : " + providerAccountId);

		// waitForTenSeconds();

		Map<String, String> queryParam = new LinkedHashMap<>();
		queryParam.put(Constants.CREDENTIALS, "credentials");

		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setQueryParams(queryParam);

	/*	Session session = new EnvSession(configurationObj.getCobrandSessionObj().getCobSession(),
				configurationObj.getCobrandSessionObj().getPath());*/
		
		EnvSession session = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();

		Response getProviderAccountDetails = providerAccountUtils.getProviderAcctDetails(httpParams, session);

		/*
		 * Response getProviderAccountDetails =
		 * ApiUtils.getProviderAccountDetails(providerAccountId, Constants.CREDENTIALS,
		 * InitialLoader.cobSessionObj);
		 */
		getProviderAccountDetails.then().log().all();
	}

	public String getClassName() {
		String className = "";
		Class<?> enclosingClass = getClass().getEnclosingClass();
		if (enclosingClass != null) {
			className = enclosingClass.getName();
		} else {
			className = getClass().getName();
		}
		return className;
	}

	public void loadMap() {
		accNameAccNumberMap.put("brokerageMargin", "989898139");
		accNameAccNumberMap.put("moneyMarket", "989898139");
		accNameAccNumberMap.put("401k", "989898139");
		accNameAccNumberMap.put("403b", "989898139");
		accNameAccNumberMap.put("trust", "989898139");
		accNameAccNumberMap.put("annuity", "989898139");
		accNameAccNumberMap.put("simple", "989898139");
		accNameAccNumberMap.put("custodial", "989898139");
		accNameAccNumberMap.put("brokerageCashOption", "989898139");
		accNameAccNumberMap.put("brokerageMarginOption", "989898139");
		accNameAccNumberMap.put("individual", "989898139");
		accNameAccNumberMap.put("corporate", "989898139");
		accNameAccNumberMap.put("jttic", "989898139");
		accNameAccNumberMap.put("jtwros", "989898139");
		accNameAccNumberMap.put("communityProperty", "989898139");
		accNameAccNumberMap.put("jointByEntirety", "989898139");
		accNameAccNumberMap.put("conservatorship", "989898139");
		accNameAccNumberMap.put("roth", "989898139");
		accNameAccNumberMap.put("rothConversion", "989898139");
		accNameAccNumberMap.put("rollover", "989898139");

		accNameAccNumberMap.put("TESTDATA", "503-1123xxx");
		accNameAccNumberMap.put("TESTDATA1", "503-1123xxx");
		accNameAccNumberMap.put("TESTDATA2", "503-1123xxx");

		accNameAccNumberMap.put("DAG_BILLING", "555555");
		accNameAccNumberMap.put("DAG_BILLING1", "555555");
		accNameAccNumberMap.put("DAG_BILLING2", "555555");
		accNameAccNumberMap.put("DAG_BILLING3", "555555");

		accNameAccNumberMap.put("Super CD Plus", "354-2334");
		accNameAccNumberMap.put("Super CD Plus1", "354-2334");
		accNameAccNumberMap.put("Super CD Plus2", "354-2334");

		accNameAccNumberMap.put("DAG INSURANCE", "5555556");
		accNameAccNumberMap.put("DAG INSURANCE1", "5555556");
		accNameAccNumberMap.put("DAG INSURANCE2", "5555556");
	}

	public long addMfaAccount(String updatedLoginForm, String providerId, Session obj) {
		Map<String, Object> param = accountsHelper.passOneQueryParam(Constants.PROVIDER_ID_FILTER, "16442");

		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setQueryParams(param);

		httpMethodParameters.setBodyParams(updatedLoginForm.toString());

		/*EnvSession session = new EnvSession(configurationObj.getCobrandSessionObj().getCobSession(),
				configurationObj.getCobrandSessionObj().getPath());*/
		
		EnvSession session = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();

		long response = providerAccountUtils.addProviderAccountMFA(httpMethodParameters, session);

		return response;
	}
}
