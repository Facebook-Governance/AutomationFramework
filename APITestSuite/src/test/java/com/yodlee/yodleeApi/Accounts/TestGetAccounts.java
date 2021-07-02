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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.databene.benerator.anno.Source;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yodlee.taas.annote.Feature;
import com.yodlee.taas.annote.FeatureName;
import com.yodlee.taas.annote.Info;
import com.yodlee.taas.annote.SubFeature;
import com.yodlee.yodleeApi.assertionPojo.Account;
import com.yodlee.yodleeApi.assertionPojo.GetAccounts;
import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.configs.AccountTypes;
import com.yodlee.yodleeApi.constants.Constants;
import com.yodlee.yodleeApi.constants.HttpStatus;
import com.yodlee.yodleeApi.constants.JSONPaths;
import com.yodlee.yodleeApi.helper.AccountsHelper;
import com.yodlee.yodleeApi.helper.PojoAssertionHelper;
import com.yodlee.yodleeApi.helper.PojoAssertionUtil;
import com.yodlee.yodleeApi.helper.ProviderAccountsHelper;
import com.yodlee.yodleeApi.helper.SessionHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.utils.apiUtils.AccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;
import com.yodlee.yodleeApi.utils.commonUtils.PropertyFileUtil;

import io.restassured.response.Response;

/**
 * Test Class that verifies Get Accounts functionality.
 *
 * @author Soujanya Kokala.
 */

@Feature(featureName = { FeatureName.ACCOUNTS })
public class TestGetAccounts extends Base {
	public static final String INVALID_GET_ACCOUNTS_CSV = "\\TestData\\CSVFiles\\Accounts\\GetAccounts\\testGetAccountsNegativeScenarios1.csv";

	public static final String VALID_GET_ACCOUNTS_CSV = "\\TestData\\CSVFiles\\Accounts\\GetAccounts\\testGetAccountsPositiveScenarios.csv";
	public static final String VALID_GET_ACCOUNTS_CSV_1 = "\\TestData\\CSVFiles\\Accounts\\GetAccounts\\testGetAccountsPositiveScenarios1.csv";
	public static final String VALID_GET_ACCOUNTS_CSV_2 = "\\TestData\\CSVFiles\\Accounts\\GetAccounts\\testGetAccountsPositiveScenarios2.csv";

	public static final String INVALID_GET_ACCOUNT_DETAILS_CSV = "\\TestData\\CSVFiles\\Accounts\\GetAccountDetails\\testGetAccountDetailsNegativeScenarios.csv";
	public static final String VALID_GET_ACCOUNTS_BANKCONTAINER = "\\TestData\\CSVFiles\\Accounts\\GetAccounts\\testGetAccountsBankContainer.csv";
	public static final String VALID_GET_ACCOUNTS_INVESTMENTCONTAINER = "\\TestData\\CSVFiles\\Accounts\\GetAccounts\\testGetAccountsForInvestmentContainer.csv";
	public static final String VALID_GET_ACCOUNTS_INSURANCECONTAINER = "\\TestData\\CSVFiles\\Accounts\\GetAccounts\\testGetAccountsForInsuranceContainer.csv";
	public static final String VALID_GET_ACCOUNTS_BILLSCONTAINER = "\\TestData\\CSVFiles\\Accounts\\GetAccounts\\testGetAccountsForBillsContainer.csv";
	public static final String VALID_GET_ACCOUNTS_CREDITCARDCONTAINER = "\\TestData\\CSVFiles\\Accounts\\GetAccounts\\testGetAccountsForCreditCardContainer.csv";
	public static final String VALID_GET_ACCOUNTS_LOANCONTAINER = "\\TestData\\CSVFiles\\Accounts\\GetAccounts\\testGetAccountsForLoanContainer.csv";
	public static final String VALID_GET_ACCOUNTS_REWARDCONTAINER = "\\TestData\\CSVFiles\\Accounts\\GetAccounts\\testGetAccountsForRewardContainer.csv";

	public List<Long> bankAccountIdList = new ArrayList<>();;
	public List<Long> investmentAccountIdList = new ArrayList<>();;
	public List<Long> insuranceAccountIdList = new ArrayList<>();;
	public List<Long> creditCardAccountIdList = new ArrayList<>();;
	public List<Long> loanAccountIdList = new ArrayList<>();;
	public List<Long> billAccountIdList = new ArrayList<>();
	public List<Long> inactiveAccountList = new ArrayList<>();;
	public List<Long> closedAccountList = new ArrayList<>();;
	public String dagUN = "GetAccountsDag.site16441.1";
	public String dagPwd = "site16441.1";
	public static Long providerAccountId = null;

	public static Long accountId = null;
	public Map<String, String> accNameAccNumberMap = new HashMap<String, String>();

	String updatedLoginFormWithCredentials = null;
	Configuration configurationObj = Configuration.getInstance();
	PropertyFileUtil propertyFileUtilObj = new PropertyFileUtil();
	ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
	ProviderAccountsHelper providerAccountsHelper = new ProviderAccountsHelper();
	CommonUtils commonUtils = new CommonUtils();
	AccountUtils accountUtils = new AccountUtils();
	AccountsHelper accountsHelper = new AccountsHelper();
	PojoAssertionUtil pojoAssertionUtil = new PojoAssertionUtil();
	PojoAssertionHelper pojoAssertionHelper = new PojoAssertionHelper();
	AccountTypes accountTypes = AccountTypes.getInstance();
	JsonAssertionUtil jsonAssertionUtil = new JsonAssertionUtil();
	SoftAssert softAssert = new SoftAssert();
	GetAccounts getAccountsPojo = new GetAccounts();

	/**
	 * Before class that adds an account.
	 */

	@BeforeClass(alwaysRun = true)
	public void setUpTest() {
		if (configurationObj.isUserStatic() && !configurationObj.isFirstRun()) {
			Properties properties = propertyFileUtilObj.loadParameters("providerAccountId.properties");
			providerAccountId = Long.parseLong(properties.getProperty(getClassName()));
			System.out.println("providerAccountId11:" + providerAccountId);
		} else {
			logger.info("********* EXECUTING BEFORE CLASS FOR ACTIVE STATUS*****************");
			long providerId = 16441;
			providerAccountId = providerId;

			try {
				HttpMethodParameters httpParms = providerAccountsHelper.createInputForAddProviderAcct(
						String.valueOf(providerId), Constants.LOGIN_FORM, dagUN, dagPwd, Constants.TWO_COUNT);

				Response response = providerAccountUtils.addProviderAccount(httpParms,
						configurationObj.getCobrandSessionObj());
				providerAccountId = response.jsonPath().getLong(JSONPaths.PROVIDER_ACC_ID);
				System.out.println("providerAccountId::::===" + providerAccountId);

				if (configurationObj.isUserStatic()) {
					propertyFileUtilObj.writePropertyFile(getClassName(), String.valueOf(providerAccountId));
				}
				logger.info("Provider Account Id : " + providerAccountId);
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * Test case that validates GET ACCOUNTS API with all negative scenarios.
	 *
	 * @param tcId
	 *            Test case id from CSV file. Test case name from CSV file.
	 * @param status
	 *            Status filter taken from CSV file.
	 * @param container
	 *            Container filter taken from CSV file.
	 * @param providerAccountId
	 *            provider account id value taken from CSV file.
	 * @param accountReconType
	 *            account RECON type filter value taken from CSV file.
	 * @param cobSessionToken
	 *            cobsession token value taken from CSV file.
	 * @param userSessionToken
	 *            user sessoin token value taken from CSV file.
	 * @param filePath
	 *            file path where JSON file is stored.
	 * @param resFile
	 *            response JSON file name.
	 */

	@Test(dataProvider = Constants.FEEDER, alwaysRun = true, priority = 1)
	@Source(VALID_GET_ACCOUNTS_CSV)
	@Info(subfeature = { SubFeature.GET_ACCOUNTS }, userStoryId = "")
	public void testGetAccountsPositiveScenariosForActiveStatus(String tcId, String tcName, String status,
			String container, String accountReconType, String filePath, String resFile, String defectId, String enabled,
			String ignoreKeys, String specific) throws FileNotFoundException {
		logger.info("********* Priority 1 *****************");
		commonUtils.isTCEnabled(enabled, tcId);

		// Fetching All Accounts for the new Provider Account ID
		System.out.println("TestCase id is " + tcId + " TestCase Name is " + tcName);
		Response getAccountsResponse = null;
		try {
			HttpMethodParameters httpMethodParameters = accountsHelper.createInputForGetAccounts(status, container,
					String.valueOf(providerAccountId), null, accountReconType, null, null);

			EnvSession session = EnvSession.builder()
					.cobSession(configurationObj.getCobrandSessionObj().getCobSession())
					.userSession(configurationObj.getCobrandSessionObj().getUserSession())
					.path(configurationObj.getCobrandSessionObj().getPath()).build();

			getAccountsResponse = accountUtils.getAccounts(httpMethodParameters,
					session);

		} catch (Exception e) {
			e.printStackTrace();
		}

		String accNum = "";
		resFile += ".json";
		commonUtils.saveJson(getAccountsResponse.prettyPrint(), filePath, resFile);
		GetAccounts getAccountsPojo = new GetAccounts();
		try {
			String jsonData = commonUtils.readJsonFile(commonUtils.getPath(filePath + File.separator + resFile));
			JSONObject json = new JSONObject(jsonData);
			JSONArray accountArray = json.getJSONArray("account");

			System.out.println("accountArray::" + accountArray);
			getAccountsPojo = pojoAssertionHelper.loadGetAccounts(container, getAccountsPojo, accountArray);
			if (configurationObj.getMaskingEnabled()) {
				System.out.println("Entered inside masking Enabled");
				List<Account> accountsList = getAccountsPojo.getAccount();
				for (Account acctObj : accountsList) {
					String accName = acctObj.getAccountName();
					if (!accName.equalsIgnoreCase("YODLEE")) {
						accNum = accountsHelper.accMasking(acctObj.getAccountNumber());
						acctObj.setAccountNumber(accNum);
					}
				}
				pojoAssertionUtil.assertResponseWithPojo(getAccountsResponse, getAccountsPojo, ignoreKeys);
			} else {
				System.out.println("Entered non masking else");
				pojoAssertionUtil.assertResponseWithPojo(getAccountsResponse, getAccountsPojo, ignoreKeys);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		JSONObject object = new JSONObject(getAccountsResponse.prettyPrint());
		JSONArray account = object.getJSONArray(JSONPaths.ACCOUNT);
		int count;
		for (count = 0; count < account.length(); count++) {

			String accountType = account.getJSONObject(count).getString(JSONPaths.ACCOUNT_TYPE);
			long accountId = account.getJSONObject(count).getLong(Constants.ID);
			String accountName = account.getJSONObject(count).getString("accountName");
			System.out.println(
					"Account Type :" + accountType + " account Id :" + accountId + " account Name :" + accountName);
			if (specific.equalsIgnoreCase("FIRST")) {
				if (container.equalsIgnoreCase("bank")) {
					bankAccountIdList.add(accountId);
				} else if (container.equalsIgnoreCase("bill")) {
					billAccountIdList.add(accountId);
				} else if (container.equalsIgnoreCase("insurance")) {
					insuranceAccountIdList.add(accountId);
				} else if (container.equalsIgnoreCase("investment")) {
					investmentAccountIdList.add(accountId);
				} else if (container.equalsIgnoreCase("loan")) {
					loanAccountIdList.add(accountId);
				} else if (container.equalsIgnoreCase("creditCard")) {
					creditCardAccountIdList.add(accountId);
				}
			}
		}
		System.out.println("Total arrays are " + count);

	}

	/**
	 * Test various accountTypes of Bank Container.
	 */
	@Test(alwaysRun = true, dataProvider = Constants.FEEDER, priority = 2)
	@Info(subfeature = { SubFeature.GET_ACCOUNTS }, userStoryId = "")
	@Source(VALID_GET_ACCOUNTS_BANKCONTAINER)
	public void testGetAccountsForBankContainer(String testcaseID, String steps, String input, String enabled,
			String defectID) {
		logger.info("********* Priority 2 *****************");
		HttpMethodParameters httpMethodParameters = accountsHelper.createInputForGetAccounts(Constants.ACTIVE_STATUS,
				Constants.BANK_CONTAINER, null, null, null, null, null);

		Response getAccountsResponse = accountUtils.getAccounts(httpMethodParameters,
				configurationObj.getCobrandSessionObj());

		try {
			JSONObject bankObject = new JSONObject(getAccountsResponse.asString());
			System.out.println("Bank Object : " + bankObject.toString());
			JSONArray accountArray = bankObject.getJSONArray(JSONPaths.ACCOUNT);

			List validBankAccountTypes = accountTypes.getValidBankAccountTypes();

			for (int count = 0; count < accountArray.length(); count++) {

				String accountType = accountArray.getJSONObject(count).getString(JSONPaths.ACCOUNT_TYPE);
				// System.out.println(" Array at Count" + count + " is :" +
				// accountArray.getJSONObject(count));
				System.out.println("accountType :" + accountType);

				System.out.println("validBankAccountTypes::" + validBankAccountTypes);
				Assert.assertTrue(validBankAccountTypes.contains(accountType));

			}

		} catch (JSONException e) {
			logger.error("Failed to convert get accounts response to JSONObject : " + e);
			Assert.fail("Failed to create JSONObject of account response.");
		}

	}

	/**
	 * Test various accountTypes of Investment Container.
	 * <p>
	 * TODO: DAG doesn't have correct account types.
	 */
	@Test(alwaysRun = true, dataProvider = Constants.FEEDER, priority = 3)
	@Info(subfeature = { SubFeature.GET_ACCOUNTS }, userStoryId = "")
	@Source(VALID_GET_ACCOUNTS_INVESTMENTCONTAINER)
	public void testGetAccountsForInvestmentContainer(String testcaseID, String steps, String input, String enabled,
			String defectID) {
		logger.info("********* Priority 3 *****************");

		HttpMethodParameters httpMethodParameters = accountsHelper.createInputForGetAccounts(Constants.ACTIVE_STATUS,
				Constants.INVESTMENT_CONTAINER, null, null, null, null, null);

		Response getAccountsResponse = accountUtils.getAccounts(httpMethodParameters,
				configurationObj.getCobrandSessionObj());

		try {
			JSONObject investmentObject = new JSONObject(getAccountsResponse.asString());
			System.out.println("Investment Object : " + investmentObject.toString());

			JSONArray accountArray = investmentObject.getJSONArray(JSONPaths.ACCOUNT);
			List accountTypeList = accountTypes.getValidAccountTypes();
			for (int count = 0; count < accountArray.length(); count++) {

				String accountType = accountArray.getJSONObject(count).getString(JSONPaths.ACCOUNT_TYPE);
				// long accountId = accountArray.getJSONObject(count).getLong(Constants.ID);
				// System.out.println(" Array at Count" + count + " is :" +
				// accountArray.getJSONObject(count));
				System.out.println("accountType :" + accountType);
				System.out.println("investment accountTypeList:::" + accountTypeList);
				Assert.assertTrue(accountTypeList.contains(accountType), "Account Types don't Match");

			}

		} catch (JSONException e) {
			logger.error("Failed to convert get accounts response to JSONObject : " + e);
			Assert.fail("Failed to create JSONObject of account response.");
		}

	}

	/**
	 * Test various accountTypes of Insurance Container.
	 */
	@Test(alwaysRun = true, dataProvider = Constants.FEEDER, priority = 4)
	@Info(subfeature = { SubFeature.GET_ACCOUNTS }, userStoryId = "")
	@Source(VALID_GET_ACCOUNTS_INSURANCECONTAINER)
	public void testGetAccountsForInsuranceContainer(String testcaseID, String steps, String input, String enabled,
			String defectID) {
		logger.info("********* Priority 4 *****************");
		HttpMethodParameters httpMethodParameters = accountsHelper.createInputForGetAccounts(Constants.ACTIVE_STATUS,
				Constants.INSURANCE_CONTAINER, null, null, null, null, null);
		Response getAccountsResponse = accountUtils.getAccounts(httpMethodParameters,
				configurationObj.getCobrandSessionObj());
		try {
			JSONObject insuranceObject = new JSONObject(getAccountsResponse.asString());
			System.out.println("Insurance Object : " + insuranceObject.toString());

			JSONArray accountArray = insuranceObject.getJSONArray(JSONPaths.ACCOUNT);
			for (int count = 0; count < accountArray.length(); count++) {

				String accountType = accountArray.getJSONObject(count).getString(JSONPaths.ACCOUNT_TYPE);
				// long accountId = accountArray.getJSONObject(count).getLong(Constants.ID);
				System.out.println(" Array at Count" + count + " is :" + accountArray.getJSONObject(count));
				System.out.println("Type :" + accountType);

				Assert.assertEquals(accountType, "INSURANCE", "Account Types don't Match");
			}

		} catch (JSONException e) {
			logger.error("Failed to convert get accounts response to JSONObject : " + e);
			Assert.fail("Failed to create JSONObject of account response.");
		}

	}

	/**
	 * Test various accountTypes of Bill Container.
	 */
	@Test(alwaysRun = true, dataProvider = Constants.FEEDER, priority = 5)
	@Info(subfeature = { SubFeature.GET_ACCOUNTS }, userStoryId = "")
	@Source(VALID_GET_ACCOUNTS_BILLSCONTAINER)
	public void testGetAccountsForBillsContainer(String testcaseID, String steps, String input, String enabled,
			String defectID) {
		logger.info("********* Priority 5 *****************");
		HttpMethodParameters httpMethodParameters = accountsHelper.createInputForGetAccounts(Constants.ACTIVE_STATUS,
				Constants.BILLS_CONTAINER, null, null, null, null, null);
		Response getAccountsResponse = accountUtils.getAccounts(httpMethodParameters,
				configurationObj.getCobrandSessionObj());

		try {
			JSONObject billObject = new JSONObject(getAccountsResponse.asString());
			System.out.println("Bill Object : " + billObject.toString());

			JSONArray accountArray = billObject.getJSONArray(JSONPaths.ACCOUNT);
			System.out.println("Account Array : " + accountArray.toString());
			List bankAccountTypes = accountTypes.getValidBankAccountTypes();
			for (int count = 0; count < accountArray.length(); count++) {

				String accountType = accountArray.getJSONObject(count).getString(JSONPaths.ACCOUNT_TYPE);
				System.out.println(" Array at Count" + count + " is :" + accountArray.getJSONObject(count));
				System.out.println("Type :" + accountType);

				Assert.assertTrue(bankAccountTypes.contains(accountType), "Account Types don't Match");
			}

		} catch (JSONException e) {
			logger.error("Failed to convert get accounts response to JSONObject : " + e);
			Assert.fail("Failed to create JSONObject of account response.");
		}

	}

	/**
	 * Test various accountTypes of Credit Card Container.
	 */
	@Test(alwaysRun = true, dataProvider = Constants.FEEDER, priority = 6)
	@Info(subfeature = { SubFeature.GET_ACCOUNTS }, userStoryId = "")
	@Source(VALID_GET_ACCOUNTS_CREDITCARDCONTAINER)
	public void testGetAccountsForCreditCardContainer(String testcaseID, String steps, String input, String enabled,
			String defectID) {
		logger.info("********* Priority 6 *****************");
		HttpMethodParameters httpMethodParameters = accountsHelper.createInputForGetAccounts(Constants.ACTIVE_STATUS,
				Constants.CREDIT_CARD_CONTAINER, null, null, null, null, null);

		Response getAccountsResponse = accountUtils.getAccounts(httpMethodParameters,
				configurationObj.getCobrandSessionObj());

		try {
			JSONObject creditObject = new JSONObject(getAccountsResponse.asString());
			System.out.println("Credit Object : " + creditObject.toString());

			JSONArray accountArray = creditObject.getJSONArray(JSONPaths.ACCOUNT);
			List creditCardAccountTypes = accountTypes.getValidCreditCardAccountTypes();
			for (int count = 0; count < accountArray.length(); count++) {

				String accountType = accountArray.getJSONObject(count).getString(JSONPaths.ACCOUNT_TYPE);
				long accountId = accountArray.getJSONObject(count).getLong(Constants.ID);
				System.out.println(" Array at Count" + count + " is :" + accountArray.getJSONObject(count));
				System.out.println("Type :" + accountType);
				Assert.assertTrue(creditCardAccountTypes.contains(accountType), "Account Types don't Match");
			}

		} catch (JSONException e) {
			logger.error("Failed to convert get accounts response to JSONObject : " + e);
			Assert.fail("Failed to create JSONObject of account response.");
		}

	}

	/**
	 * Test various accountTypes of Loan Container.
	 */
	@Test(alwaysRun = true, dataProvider = Constants.FEEDER, priority = 7)
	@Info(subfeature = { SubFeature.GET_ACCOUNTS }, userStoryId = "")
	@Source(VALID_GET_ACCOUNTS_LOANCONTAINER)
	public void testGetAccountsForLoanContainer(String testcaseID, String steps, String input, String enabled,
			String defectID) {
		logger.info("********* Priority 7 *****************");
		HttpMethodParameters httpMethodParameters = accountsHelper.createInputForGetAccounts(Constants.ACTIVE_STATUS,
				Constants.LOAN_CONTAINER, null, null, null, null, null);

		Response getAccountsResponse = accountUtils.getAccounts(httpMethodParameters,
				configurationObj.getCobrandSessionObj());

		try {
			JSONObject loanObject = new JSONObject(getAccountsResponse.asString());
			System.out.println("Loan Object : " + loanObject.toString());

			JSONArray accountArray = loanObject.getJSONArray(JSONPaths.ACCOUNT);
			List loanAccountTypes = accountTypes.getValidLoanAccountTypes();

			for (int count = 0; count < accountArray.length(); count++) {

				String accountType = accountArray.getJSONObject(count).getString(JSONPaths.ACCOUNT_TYPE);
				long accountId = accountArray.getJSONObject(count).getLong(Constants.ID);
				System.out.println(" Array at Count " + count + " is :" + accountArray.getJSONObject(count));
				System.out.println("Type :" + accountType);

				Assert.assertTrue(loanAccountTypes.contains(accountType), "Account Types don't Match");
			}
		} catch (JSONException e) {
			logger.error("Failed to convert get accounts response to JSONObject : " + e);
			Assert.fail("Failed to create JSONObject of account response.");
		}

	}

	/**
	 * Test various accountTypes of Reward Container.
	 * <p>
	 * TODO: No account type for reward.
	 */
	@Test(alwaysRun = true, dataProvider = Constants.FEEDER, priority = 8)
	@Info(subfeature = { SubFeature.GET_ACCOUNTS }, userStoryId = "")
	@Source(VALID_GET_ACCOUNTS_REWARDCONTAINER)
	public void testGetAccountsForRewardContainer(String testcaseID, String steps, String input, String enabled,
			String defectID) {
		logger.info("********* Priority 8 *****************");
		HttpMethodParameters httpMethodParameters = accountsHelper.createInputForGetAccounts(Constants.ACTIVE_STATUS,
				Constants.REWARD_CONTAINER, null, null, null, null, null);

		Response getAccountsResponse = accountUtils.getAccounts(httpMethodParameters,
				configurationObj.getCobrandSessionObj());

		try {
			JSONObject rewardObject = new JSONObject(getAccountsResponse.asString());
			System.out.println("Reward Object : " + rewardObject.toString());

			JSONArray accountArray = rewardObject.getJSONArray(JSONPaths.ACCOUNT);
			List bankAccountTypes = accountTypes.getValidBankAccountTypes();
			for (int count = 0; count < accountArray.length(); count++) {

				String accountType = accountArray.getJSONObject(count).getString(JSONPaths.ACCOUNT_TYPE);
				System.out.println(" Array at Count" + count + " is :" + accountArray.getJSONObject(count));
				System.out.println("Type :" + accountType);
				Assert.assertTrue(bankAccountTypes.contains(accountType), "Account Types don't Match");
			}

		} catch (JSONException e) {
			logger.error("Failed to convert get accounts response to JSONObject : " + e);
			Assert.fail("Failed to create JSONObject of account response.");
		}

	}

	/**
	 * Test case that validates GET ACCOUNT DETAILS API with all negative scenarios.
	 *
	 * @param tcId
	 *            Test case id taken from CSV File.
	 * @param tcName
	 *            Test case name taken from CSV file.
	 * @param accountId
	 *            Account id taken from CSV file.
	 * @param container
	 *            Container value taken from CSV file.
	 * @param cobSessionToken
	 *            cobSession value taken from CSV file. * @param userSessionToken
	 *            user session value taken from CSV file.
	 * @param filePath
	 *            filepath where JSON file is located.
	 * @param resFile
	 *            JSON file name.
	 */
	@Test(dataProvider = Constants.FEEDER, alwaysRun = true, priority = 9)
	@Source(INVALID_GET_ACCOUNT_DETAILS_CSV)
	@Info(subfeature = { SubFeature.GET_ACCOUNTS_DETAILS }, userStoryId = "")
	public void testGetAccountDetailsNegativeScenarios(String tcId, String tcName, String accountId, String container,
			String cobSessionToken, String userSessionToken, int status, String filePath, String resFile, Long defectId,
			String enabled) {
		logger.info("********* Priority 9 *****************");
		commonUtils.isTCEnabled(enabled, tcId);

		System.out.println("TestCase id is " + tcId + " TestCase Name is " + tcName);
		Response response;
		if (!tcName.equalsIgnoreCase("Multiple AccountId")) {

			HttpMethodParameters httpMethodParameters = accountsHelper.createInputForGetAccountDetails(null,
					accountsHelper.getFilterValue(container), null, accountsHelper.getFilterValue(accountId), null,
					null, null);

			String cobSession = SessionHelper.getSessionToken(cobSessionToken, "cob");
			String userSession = SessionHelper.getSessionToken(userSessionToken, "user");

			EnvSession session = EnvSession.builder().cobSession(cobSession).userSession(userSession)
					.path(configurationObj.getCobrandSessionObj().getPath()).build();

			response = accountUtils.getAccountDetailsForAccountId(httpMethodParameters, session);

		} else {

			String cobSession = SessionHelper.getSessionToken(cobSessionToken, "cob");
			String userSession = SessionHelper.getSessionToken(userSessionToken, "user");

			EnvSession session = EnvSession.builder().cobSession(cobSession).userSession(userSession)
					.path(configurationObj.getCobrandSessionObj().getPath()).build();

			Map<String, Object> queryParam = accountsHelper.createQueryParamsForGetAccounts(null, container, null, null,
					null, null, null);

			HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
			httpMethodParameters.setQueryParams(queryParam);

			Map<String, Object> pathParams = new HashMap<>();
			pathParams.put("accountId", accountsHelper.getFilterValue(accountId));
			httpMethodParameters.setPathParams(pathParams);
			response = accountUtils.getAccountDetailsForAccountId(httpMethodParameters, session);

		}
		if (response.statusCode() == status) {

			jsonAssertionUtil.assertResponse(response, status, resFile, filePath);
		} else {
			Assert.fail("Status is not Matched");

		}

	}

	@Test(dataProvider = Constants.FEEDER, alwaysRun = true, priority = 10)
	@Source(INVALID_GET_ACCOUNTS_CSV)
	@Info(subfeature = { SubFeature.GET_ACCOUNTS }, userStoryId = "")
	public void testGetAccountsNegativeScenarios(String tcId, String tcName, String status, String container,
			String providerAccountId, String accountReconType, String cobSessionToken, String userSessionToken,
			int statusCode, String filePath, String resFile, Long defectId, String enabled) {

		logger.info("********* Priority 10 *****************");
		commonUtils.isTCEnabled(enabled, tcId);

		System.out.println("TestCase id is " + tcId + " TestCase Name is " + tcName);
		Response getAccountsResponse = null;

		HttpMethodParameters httpMethodParameters = accountsHelper.createInputForGetAccounts(
				accountsHelper.getFilterValue(status), accountsHelper.getFilterValue(container),
				getProviderAccountId(providerAccountId), null, accountsHelper.getFilterValue(accountReconType), null,
				null);

		/*
		 * EnvSession sessionObj = new
		 * EnvSession(SessionHelper.getSessionToken(cobSessionToken, "cob"),
		 * SessionHelper.getSessionToken(userSessionToken, "user"),
		 * configurationObj.getCobrandSessionObj().getPath());
		 */

		EnvSession sessionObj = EnvSession.builder().cobSession(SessionHelper.getSessionToken(cobSessionToken, "cob"))
				.userSession(SessionHelper.getSessionToken(userSessionToken, "user"))
				.path(configurationObj.getCobrandSessionObj().getPath()).build();

		getAccountsResponse = accountUtils.getAccounts(httpMethodParameters, sessionObj);

		if (getAccountsResponse.statusCode() == statusCode) {
			commonUtils.saveJson(getAccountsResponse.prettyPrint(), filePath, resFile + ".json");
			jsonAssertionUtil.assertJSON(getAccountsResponse, statusCode, resFile + ".json", filePath);
		} else
			Assert.fail("Status code is not as Expected");
	}

	@Test(dataProvider = Constants.FEEDER, alwaysRun = true, priority = 11)
	@Source(VALID_GET_ACCOUNTS_CSV_1)
	@Info(subfeature = { SubFeature.GET_ACCOUNTS }, userStoryId = "")
	public void testGetAccountsPositiveScenariosForInactiveStatus(String tcId, String tcName, String oldStatus,
			String status, String accountName, String container, String providerAccID, String accountReconType,
			String filePath, String resFile, Long defectId, String enabled, String ignoreKeys, String specific)
			throws FileNotFoundException, JsonProcessingException {
		logger.info("********* Priority 11 *****************");
		commonUtils.isTCEnabled(enabled, tcId);
		int count = 0;
		JSONObject accountObject = null;
		System.out.println("TestCase id is " + tcId + " TestCase Name is " + tcName);

		if (specific.equalsIgnoreCase("TRUE")) {

			HttpMethodParameters httpMethodParameters = accountsHelper.createInputForGetAccounts(
					oldStatus, container,
					getProviderAccountId(providerAccID), null, accountReconType, null,
					null);

			Response getAccountsResponse = accountUtils.getAccounts(httpMethodParameters,
					configurationObj.getCobrandSessionObj());

			JSONObject object = new JSONObject(getAccountsResponse.asString());
			JSONArray accountArray = object.getJSONArray(JSONPaths.ACCOUNT);

			HttpMethodParameters httpMethodParameters1 = accountsHelper.createInputForGetAccounts(
					oldStatus, container,
					getProviderAccountId(providerAccID), null, accountReconType, null,
					null);

			Response getAccountsResponse1 = accountUtils.getAccounts(httpMethodParameters1,
					configurationObj.getCobrandSessionObj());

			System.out.println("Accounts With Inactive Status is Below");
			for (count = 0; count < accountArray.length(); count++) {
				accountObject = accountArray.getJSONObject(count);
				String accName = accountObject.getString("accountName");
				if (accName.equalsIgnoreCase(accountName))
					break;
			}
			Long accountId = accountObject.getLong(Constants.ID);

			if (status == null || status == "" || status.equalsIgnoreCase(""))
				status = "CLOSED";

			String bodyParams = accountsHelper.createBodyParamsForUpdateManualAccnt(null, status, null, null, null);
			Map<String, Object> pathParms = new HashMap<>();
			pathParms.put("accountId", accountId);
			HttpMethodParameters httpMethodParameters2 = HttpMethodParameters.builder().build();
			httpMethodParameters2.setBodyParams(bodyParams);
			httpMethodParameters2.setPathParams(pathParms);

			accountUtils.updateManualOrAggregateAccount(httpMethodParameters2, configurationObj.getCobrandSessionObj());
			inactiveAccountList.add(accountId);
		}

		HttpMethodParameters httpMethodParameters1 = accountsHelper.createInputForGetAccounts(
				accountsHelper.getFilterValue(status), accountsHelper.getFilterValue(container),
				getProviderAccountId(providerAccID), null, accountsHelper.getFilterValue(accountReconType), null, null);

		Response getAccountsResponse1 = accountUtils.getAccounts(httpMethodParameters1,
				configurationObj.getCobrandSessionObj());
		String accNum = "";
		resFile += ".json";
		commonUtils.saveJson(getAccountsResponse1.prettyPrint(), filePath, resFile);
		GetAccounts getAccountsPojo = new GetAccounts();

		String jsonData = commonUtils.readJsonFile(commonUtils.getPath(filePath + File.separator + resFile));
		JSONObject json = new JSONObject(jsonData);
		JSONArray accountArray = json.getJSONArray("account");
		PojoAssertionUtil pojoAssertionUtil = new PojoAssertionUtil();

		getAccountsPojo = pojoAssertionHelper.loadGetAccounts(container, getAccountsPojo, accountArray);

		if (configurationObj.getMaskingEnabled()) {
			System.out.println("Entered inside masking Enabled");
			List<Account> accountsList = getAccountsPojo.getAccount();
			for (Account acctObj : accountsList) {
				String accName = acctObj.getAccountName();
				if (!accName.equalsIgnoreCase("YODLEE")) {
					accNum = accountsHelper.accMasking(acctObj.getAccountNumber());
					acctObj.setAccountNumber(accNum);
				}
			}
			pojoAssertionUtil.assertResponseWithPojo(getAccountsResponse1, getAccountsPojo, ignoreKeys);
		} else {
			System.out.println("Entered non masking else");
			pojoAssertionUtil.assertResponseWithPojo(getAccountsResponse1, getAccountsPojo, ignoreKeys);
		}

		if (specific.equalsIgnoreCase("LAST")) {
			for (Long id : inactiveAccountList) {

				String bodyParams = accountsHelper.createBodyParamsForUpdateManualAccnt(null, "ACTIVE", null, null,
						null);
				Map<String, Object> pathParms = new HashMap<>();
				pathParms.put("accountId", id);
				HttpMethodParameters httpMethodParameters2 = HttpMethodParameters.builder().build();
				httpMethodParameters2.setBodyParams(bodyParams);
				httpMethodParameters2.setPathParams(pathParms);

				accountUtils.updateManualOrAggregateAccount(httpMethodParameters2,
						configurationObj.getCobrandSessionObj());

			}

		}
	}

	/*
	 * @Test(dataProvider = Constants.FEEDER, alwaysRun = true, priority = 12)
	 * 
	 * @Source(VALID_GET_ACCOUNTS_CSV_2)
	 * 
	 * @Info(subfeature = { SubFeature.GET_ACCOUNTS }, userStoryId = "")
	 */
	public void testGetAccountsPositiveScenariosForClosedStatus(String tcId, String tcName, String oldStatus,
			String status, String accountName, String container, String providerAcctId, String accountReconType,
			String filePath, String resFile, Long defectId, String specific, String ignoreKeys, String enabled)
			throws FileNotFoundException {
		logger.info("********* Priority 12 *****************");
		commonUtils.isTCEnabled(enabled, tcId);
		System.out.println("TestCase id is " + tcId + " TestCase Name is " + tcName);
		int count = 0;
		if (specific.equalsIgnoreCase("TRUE")) {

			HttpMethodParameters httpParams = accountsHelper.createInputForGetAccounts(
					accountsHelper.getFilterValue(oldStatus), accountsHelper.getFilterValue(container),
					getProviderAccountId(String.valueOf(providerAccountId)), null,
					accountsHelper.getFilterValue(accountReconType), null, null);

			Response getAccountsResponse = accountUtils.getAccounts(httpParams,
					configurationObj.getCobrandSessionObj());

			JSONObject accountObject = null;
			JSONObject object = new JSONObject(getAccountsResponse.asString());
			JSONArray accountArray = object.getJSONArray(JSONPaths.ACCOUNT);
			for (count = 0; count < accountArray.length(); count++) {
				accountObject = accountArray.getJSONObject(count);
				String accName = accountObject.getString("accountName");
				if (accName.equalsIgnoreCase(accountName))
					break;
			}

			Long accountId = accountObject.getLong(Constants.ID);

			if (status == null || status == "" || status.equalsIgnoreCase(""))
				status = "CLOSED";
			String bodyParams = accountsHelper.createBodyParamsForUpdateManualAccnt(null, status, null, null, null);
			Map<String, Object> pathParms = new HashMap<>();
			pathParms.put("accountId", accountId);
			HttpMethodParameters httpMethodParameters2 = HttpMethodParameters.builder().build();
			httpMethodParameters2.setBodyParams(bodyParams);
			httpMethodParameters2.setPathParams(pathParms);

			accountUtils.updateManualOrAggregateAccount(httpMethodParameters2, configurationObj.getCobrandSessionObj());

			closedAccountList.add(accountId);
		}

		HttpMethodParameters httpMethodParameters2 = accountsHelper.createInputForGetAccounts(
				accountsHelper.getFilterValue(status), accountsHelper.getFilterValue(container),
				String.valueOf(providerAccountId), null, accountsHelper.getFilterValue(accountReconType), null, null);

		Response getAccountsResponse1 = accountUtils.getAccounts(httpMethodParameters2,
				configurationObj.getCobrandSessionObj());

		commonUtils.saveJson(getAccountsResponse1.prettyPrint(), filePath, resFile);
		String accNum = "";
		resFile += ".json";

		try {
			String jsonData = commonUtils.readJsonFile(commonUtils.getPath(filePath + File.separator + resFile));
			JSONObject json = new JSONObject(jsonData);
			JSONArray accountArray = json.getJSONArray("account");

			System.out.println("accountArray::" + accountArray);
			getAccountsPojo = pojoAssertionHelper.loadGetAccounts(container, getAccountsPojo, accountArray);
			if (configurationObj.getMaskingEnabled()) {
				System.out.println("Entered inside masking Enabled");
				List<Account> accountsList = getAccountsPojo.getAccount();
				for (Account acctObj : accountsList) {
					String accName = acctObj.getAccountName();
					if (!accName.equalsIgnoreCase("YODLEE")) {
						accNum = accountsHelper.accMasking(acctObj.getAccountNumber());
						acctObj.setAccountNumber(accNum);
					}
				}
				pojoAssertionUtil.assertResponseWithPojo(getAccountsResponse1, getAccountsPojo, ignoreKeys);
			} else {
				System.out.println("Entered non masking else");
				pojoAssertionUtil.assertResponseWithPojo(getAccountsResponse1, getAccountsPojo, ignoreKeys);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (specific.equalsIgnoreCase("LAST")) {
			for (Long id : closedAccountList) {

				String bodyParams = accountsHelper.createBodyParamsForUpdateManualAccnt(null, "ACTIVE", null, null,
						null);
				Map<String, Object> pathParms = new HashMap<>();
				pathParms.put("accountId", id);
				HttpMethodParameters httpMethodParameters3 = HttpMethodParameters.builder().build();
				httpMethodParameters3.setBodyParams(bodyParams);
				httpMethodParameters3.setPathParams(pathParms);

				accountUtils.updateManualOrAggregateAccount(httpMethodParameters3,
						configurationObj.getCobrandSessionObj());

			}
		}

	}

	/**
	 * Test that deletes the account and invoke GET Accounts API.
	 */
	@Test(alwaysRun = true, priority = 13)
	@Info(subfeature = { SubFeature.DELETE_ACCOUNTS }, userStoryId = "")
	public void testDeleteAccountAndGetAccounts() {

		if (Configuration.getInstance().isUserStatic() && !Configuration.getInstance().isFirstRun()) {

			logger.info("********* Priority 13 *****************");
			// Delete all the account ids.
			if (bankAccountIdList != null) {
				for (long accountId : bankAccountIdList) {

					HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
					Map<String, Object> pathParams = new HashMap<>();
					pathParams.put("accountId", accountId);
					httpMethodParameters.setPathParams(pathParams);

					Response deleteAccountIdResponse = accountUtils.deleteAccount(httpMethodParameters,
							Configuration.getInstance().getCobrandSessionObj());

					HttpMethodParameters httpParamsGetAccounts = accountsHelper.createInputForGetAccounts(null,
							Constants.BANK_CONTAINER, null, String.valueOf(accountId), null, null, null);

					Response getAccountIdResponse = accountUtils.getAccounts(httpParamsGetAccounts,
							configurationObj.getCobrandSessionObj());

					if (deleteAccountIdResponse.statusCode() != HttpStatus.NO_CONTENT_204
							&& getAccountIdResponse.statusCode() != HttpStatus.BAD_REQUEST) {
						softAssert.fail("Failed to delete bank accounts and get account details ...");
					}
				}
			}
			// Delete all the account ids.
			if (investmentAccountIdList != null) {
				for (long accountId : investmentAccountIdList) {

					HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
					Map<String, Object> pathParams = new HashMap<>();
					pathParams.put("accountId", accountId);
					httpMethodParameters.setPathParams(pathParams);

					Response deleteAccountIdResponse = accountUtils.deleteAccount(httpMethodParameters,
							Configuration.getInstance().getCobrandSessionObj());

					HttpMethodParameters httpParamsGetAccounts = accountsHelper.createInputForGetAccounts(null,
							Constants.INVESTMENT_CONTAINER, null, String.valueOf(accountId), null, null, null);

					Response getAccountIdResponse = accountUtils.getAccounts(httpParamsGetAccounts,
							configurationObj.getCobrandSessionObj());

					if (deleteAccountIdResponse.statusCode() != HttpStatus.NO_CONTENT_204
							&& getAccountIdResponse.statusCode() != HttpStatus.BAD_REQUEST) {
						softAssert.fail("Failed to delete investment accounts and get account details ...");
					}
				}
			}

			// Delete all the account ids.
			if (insuranceAccountIdList != null) {
				for (long accountId : insuranceAccountIdList) {

					HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
					Map<String, Object> pathParams = new HashMap<>();
					pathParams.put("accountId", accountId);
					httpMethodParameters.setPathParams(pathParams);

					Response deleteAccountIdResponse = accountUtils.deleteAccount(httpMethodParameters,
							Configuration.getInstance().getCobrandSessionObj());

					HttpMethodParameters httpParamsGetAccounts = accountsHelper.createInputForGetAccounts(null,
							Constants.INSURANCE_CONTAINER, null, String.valueOf(accountId), null, null, null);

					Response getAccountIdResponse = accountUtils.getAccounts(httpParamsGetAccounts,
							configurationObj.getCobrandSessionObj());

					if (deleteAccountIdResponse.statusCode() != HttpStatus.NO_CONTENT_204
							&& getAccountIdResponse.statusCode() != HttpStatus.BAD_REQUEST) {
						softAssert.fail("Failed to delete insurance accounts and get account details ...");
					}
				}
			}
			// Delete all the account ids.
			if (creditCardAccountIdList != null) {
				for (long accountId : creditCardAccountIdList) {

					HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
					Map<String, Object> pathParams = new HashMap<>();
					pathParams.put("accountId", accountId);
					httpMethodParameters.setPathParams(pathParams);
					Response deleteAccountIdResponse = accountUtils.deleteAccount(httpMethodParameters,
							Configuration.getInstance().getCobrandSessionObj());

					HttpMethodParameters httpParamsGetAccounts = accountsHelper.createInputForGetAccounts(null,
							Constants.CREDIT_CARD_CONTAINER, null, String.valueOf(accountId), null, null, null);

					Response getAccountIdResponse = accountUtils.getAccounts(httpParamsGetAccounts,
							configurationObj.getCobrandSessionObj());

					if (deleteAccountIdResponse.statusCode() != HttpStatus.NO_CONTENT_204
							&& getAccountIdResponse.statusCode() != HttpStatus.BAD_REQUEST) {
						softAssert.fail("Failed to delete credit card accounts and get account details ...");
					}
				}
			}
			if (loanAccountIdList != null) {
				for (long accountId : loanAccountIdList) {

					HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
					Map<String, Object> pathParams = new HashMap<>();
					pathParams.put("accountId", accountId);
					httpMethodParameters.setPathParams(pathParams);
					Response deleteAccountIdResponse = accountUtils.deleteAccount(httpMethodParameters,
							Configuration.getInstance().getCobrandSessionObj());

					HttpMethodParameters httpParamsGetAccounts = accountsHelper.createInputForGetAccounts(null,
							Constants.LOAN_CONTAINER, null, String.valueOf(accountId), null, null, null);

					Response getAccountIdResponse = accountUtils.getAccounts(httpParamsGetAccounts,
							configurationObj.getCobrandSessionObj());

					if (deleteAccountIdResponse.statusCode() != HttpStatus.NO_CONTENT_204
							&& getAccountIdResponse.statusCode() != HttpStatus.BAD_REQUEST) {
						softAssert.fail("Failed to delete credit card accounts and get account details ...");
					}
				}
			}
			if (billAccountIdList != null) {
				for (long accountId : billAccountIdList) {
					HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
					Map<String, Object> pathParams = new HashMap<>();
					pathParams.put("accountId", accountId);
					httpMethodParameters.setPathParams(pathParams);
					Response deleteAccountIdResponse = accountUtils.deleteAccount(httpMethodParameters,
							Configuration.getInstance().getCobrandSessionObj());

					HttpMethodParameters httpParamsGetAccounts = accountsHelper.createInputForGetAccounts(null,
							Constants.BILLS_CONTAINER, null, String.valueOf(accountId), null, null, null);

					Response getAccountIdResponse = accountUtils.getAccounts(httpParamsGetAccounts,
							configurationObj.getCobrandSessionObj());

					if (deleteAccountIdResponse.statusCode() != HttpStatus.NO_CONTENT_204
							&& getAccountIdResponse.statusCode() != HttpStatus.BAD_REQUEST) {
						softAssert.fail("Failed to delete bank accounts and get account details ...");
					}
				}
			}

			softAssert.assertAll();

		}
	}

	/**
	 * After class that deletes the provider account id.
	 */
	@AfterClass(alwaysRun = true)
	public void deleteProviderAccount() {
		HttpMethodParameters httpMethodParameters = providerAccountsHelper
				.createInputParamsForDeleteProviderAccount(String.valueOf(providerAccountId));

		Response deleteResponse = providerAccountUtils.deleteProviderAccount(httpMethodParameters,
				configurationObj.getCobrandSessionObj());

	}

	private String getClassName() {
		String className = "";
		Class<?> enclosingClass = getClass().getEnclosingClass();
		if (enclosingClass != null) {
			className = enclosingClass.getName();
		} else {
			className = getClass().getName();
		}
		return className;
	}

	private String getProviderAccountId(String valueFromCSV) {

		if (Constants.VALID.equals(valueFromCSV)) {
			return String.valueOf(providerAccountId);
		} else if (Constants.MAX_COUNT.equals(valueFromCSV)) {
			return Constants.MAX_LENGTH_LONG_ID;
		}
		AccountsHelper accountsHelper = new AccountsHelper();
		return accountsHelper.getFilterValue(valueFromCSV);
	}

}
