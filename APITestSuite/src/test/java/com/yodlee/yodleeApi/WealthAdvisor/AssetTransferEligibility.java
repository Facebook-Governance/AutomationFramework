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

package com.yodlee.yodleeApi.WealthAdvisor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.databene.benerator.anno.Source;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.yodlee.DBHelper;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.Constant_Wealth;
import com.yodlee.yodleeApi.constants.Constants;
import com.yodlee.yodleeApi.helper.UserHelper;
import com.yodlee.yodleeApi.interfaces.Session;
import com.yodlee.yodleeApi.pojo.AdditionalDataSet;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.pojo.User;
import com.yodlee.yodleeApi.sdg.ProcessSdg;
import com.yodlee.yodleeApi.sdg.RequestSequence;
import com.yodlee.yodleeApi.sdg.SdgHelper;
import com.yodlee.yodleeApi.utils.apiUtils.AccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.AssetTransferUtils;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;
import io.restassured.response.Response;

public class AssetTransferEligibility extends Base {

	public Session sessionObj;
	public String userName;
	public String responseStatus;
	public Connection conn_oltp, conn_wealth;

	/*
	 * creating user and adding investment accounts
	 */
	@SuppressWarnings("unused")
	@BeforeClass(alwaysRun = true)
	private void createAndAddUser() {
		try {
			System.out.println("createAndAddUser");
			sessionObj = EnvSession.builder().build();
			sessionObj.setCobSession(Configuration.getInstance().getCobrandSessionObj().getCobSession());
			sessionObj.setPath(Configuration.getInstance().getCobrandSessionObj().getPath());
			System.out.println("Cob Login Successful.........");
			userName = Constant_Wealth.YSL_USER + System.currentTimeMillis();
			UserUtils userUtils = new UserUtils();
			UserHelper userHelper = new UserHelper();
			User userInfo = User.builder().build();
			userInfo.setUsername(userName);
			userInfo.setPassword(Constant_Wealth.USER_PASSWORD);
			userInfo.setLocale(Constant_Wealth.LOCALE_US);
			HttpMethodParameters httpMethodParameters = userHelper.createInputForUserReg(userInfo);
			System.out.println("User Login Sucessful.................");
			String userSessionToken = userUtils.userRegistration(sessionObj, httpMethodParameters);
			sessionObj.setUserSession(userSessionToken);
			long providerAccountId = addSDGAccount();
			DBHelper dbHelper = new DBHelper();
			/*
			 * Connection to new schema wealth
			 */
			conn_wealth = dbHelper.getWealthDBConnection();
			/*
			 * Connection to OLTP
			 */
			conn_oltp = dbHelper.getDBConnection();
			/*
			 * getting item_account_id for added investment account
			 */

			assertKeyStatus();
		} catch (Exception e) {
			e.getStackTrace()[0].getLineNumber();
			Assert.fail(e.getMessage());
		}
	}

	public void assertKeyStatus() throws SQLException {
		String query = "select * from PAL.COBRAND_ACL_VALUE  where param_acl_id= 7475 and COBRAND_ID = 10000004";
		Statement stmt = conn_oltp.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet vs = stmt.executeQuery(query);
		while (vs.next()) {
			Assert.assertTrue(String.valueOf(vs.getBoolean("PARAM_ACL_ID")).equalsIgnoreCase("TRUE"),
					"PARAM_ACL_KEY not enabled for Param key 7475");
		}
		vs.close();
	}

	/*
	 * Test Methods
	 */
	@Test(enabled = true, dataProvider = "feeder", alwaysRun = false)
	@Source("\\TestData\\CSVFiles\\Wealth\\AssetTransfer.csv")
	public void init(String testCaseId, String testCasenum, String testCase, String parameter, String successScenario,
			String enabled) throws JSONException, SQLException, InterruptedException, Exception {
		System.out.println("@ AssetTransferEligibilty");
		/*
		 * getting item_account_id for added investment account
		 */
		String invItemAccount = getItemAccountId();
		boolean isReconciableCheckDone = false;
		if (!isReconciableCheckDone)
			checkAccountIsReconciled(invItemAccount);
		String successfulRes = successScenario;
		CommonUtils commonUtils = new CommonUtils();
		commonUtils.isTCEnabled(enabled, testCaseId);
		System.out.println("Executing testcase => " + testCaseId);
		HashMap<String, String> expected_keyValue = new HashMap<>();
		System.out.println("parameter: " + parameter);
		expected_keyValue = getParameterMap(parameter);
		String requestId = String.valueOf(assertTransferPOST(invItemAccount, expected_keyValue, successfulRes));
		if (successfulRes.equalsIgnoreCase("TRUE")) {
			Response getAssetTransfer = assertTransferGET(requestId, expected_keyValue);
			if (!expected_keyValue.containsKey("invalidRequestId")) {
				checkRequestStatus(requestId);
				Assert.assertTrue(
						expected_keyValue.get("isAccountEligible")
								.equalsIgnoreCase(validateAccountEligibilty(getAssetTransfer)),
						" AccountEligible not generated as expected for investment account: " + invItemAccount);
				checkAccountData(requestId, getAssetTransfer);
				String expected_cusip = expected_keyValue.get("CUSIP");
				getHoldingData(expected_cusip, getAssetTransfer, expected_keyValue);
			}
		}
	}

	private void checkAccountIsReconciled(String invItemAccount) {
		System.out.println("@getAccountIsReconciled");
		AccountUtils accountUtils = new AccountUtils();
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		Map<String, Object> queryParam = new HashMap<>();
		queryParam.put("accountId", invItemAccount);
		httpParams.setQueryParams(queryParam);
		Response getAccount = accountUtils.getAccounts(httpParams, sessionObj);
		List<Boolean> isReconciable = getAccount.jsonPath().getList(Constant_Wealth.AssetTransferAccountIsReconciable);
		System.out.println("isReconciable: " + isReconciable.get(0));
		Assert.assertTrue(isReconciable.get(0).equals(true), "isRecociable not true for account: " + invItemAccount);
	}

	/*
	 * getting account id
	 */
	String getItemAccountId() throws Exception {
		System.out.println("@getItemAccountId");
		Statement stmt = conn_oltp.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		String query = "select t.item_account_id , m.sum_info_id from item_account t , mem_item m where m.CACHE_ITEM_ID=t.CACHE_ITEM_ID and "
				+ "t.CACHE_ITEM_ID in ( select cache_item_id from mem_item where mem_id in (select mem_id from mem where login_name ="
				+ "'" + userName + "'" + " ))";
		System.out.println(query);
		String invItemAccount = "";
		int sumInfoItem = 0;
		ResultSet vs = stmt.executeQuery(query);
		while (vs.next()) {
			sumInfoItem = vs.getInt("sum_info_id");
			if (sumInfoItem == 20549) {
				invItemAccount = String.valueOf(vs.getInt("item_account_id"));
			}
		}
		System.out.println("investItemAccountId: " + invItemAccount);
		vs.close();
		stmt.close();
		return invItemAccount;

	}

	/*
	 * POST api for request generation
	 */
	String createAssetTransferPostRequest(String invItemAccount, HashMap<String, String> expected_keyValue) {
		System.out.println("@Creating POST request body........");
		JSONObject putObj = new JSONObject();
		JSONArray array = new JSONArray();
		JSONObject arrayElementOne = new JSONObject();

		if (expected_keyValue.keySet().contains("accountId")) {
			invItemAccount = (expected_keyValue.get("accountId")).toString();
		}
		if (expected_keyValue.keySet().contains("request")) {
			arrayElementOne.put("invalidKey", 1234);
		}
		arrayElementOne.put("id", invItemAccount);
		array.put(arrayElementOne);
		putObj.put("account", array);
		System.out.println("post body: " + putObj.toString());
		return putObj.toString();
	}

	/*
	 * triggering POST api
	 */
	String assertTransferPOST(String invItemAccount, HashMap<String, String> expected_keyValue, String successfulRes) {
		try {
			System.out.println("@assertTransferPOST");
			String request = createAssetTransferPostRequest(invItemAccount, expected_keyValue);
			AssetTransferUtils util = new AssetTransferUtils();
			HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
			httpParams.setBodyParams(request);
			Map<String, Object> queryParam = new HashMap<>();
			queryParam.put("container", "investment");
			httpParams.setQueryParams(queryParam);
			Response postAssetTransfer = util.addAssetTransferRequest(httpParams, sessionObj);
			String requestId = "";
			/*
			 * Asserting response from POST api
			 */
			if (successfulRes.equalsIgnoreCase("TRUE")) {
				/*
				 * Asserting response from POST api
				 */
				Assert.assertTrue(postAssetTransfer.statusCode() == 201,
						"Assert Transfer POST api not returning any response.");
				requestId = (postAssetTransfer.jsonPath().getString(Constant_Wealth.AssetTransferRequestId)).toString();
				responseStatus = postAssetTransfer.jsonPath().getString(Constant_Wealth.AssetTransferStatus);
			} else {
				String errorCode = postAssetTransfer.jsonPath().getString(Constant_Wealth.AssetTransferErrorCode);
				System.out.println("errorCode: " + errorCode);
				Assert.assertTrue(errorCode.equalsIgnoreCase(expected_keyValue.get("errorCode")),
						"ErrorCode not as expected.....");
				// proceedNext = false;
			}
			return requestId;
		} catch (Exception e) {
			e.printStackTrace();
			e.getStackTrace()[0].getLineNumber();
			Assert.fail(e.getMessage());
			return null;
		}
	}

	/*
	 * triggering get api
	 */
	Response assertTransferGET(String requestId, HashMap<String, String> expected_keyValue) {
		System.out.println("@assertTransferGET");
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		Map<String, Object> pathParams = new HashMap<>();
		AssetTransferUtils utils = new AssetTransferUtils();
		Response getAssetTransfer = null;
		if (!expected_keyValue.containsKey("invalidRequestId")) {
			pathParams.put("requestId", requestId);
			httpMethodParameters.setPathParams(pathParams);
			getAssetTransfer = utils.getAssetTransferDetails(httpMethodParameters, sessionObj);
			getAssetTransfer.then().log().all();
		} else {
			pathParams.put("requestId", expected_keyValue.get("invalidRequestId"));
			httpMethodParameters.setPathParams(pathParams);
			getAssetTransfer = utils.getAssetTransferDetails(httpMethodParameters, sessionObj);
			String actualErrorCode = getAssetTransfer.jsonPath().get(Constant_Wealth.AssetTransferGetErrorCode);
			System.out.println("actualErrorCode in get " + actualErrorCode);
			Assert.assertTrue(actualErrorCode.equalsIgnoreCase(expected_keyValue.get("errorCodeGet")),
					"Error Code not returned as expected....");
		}
		return getAssetTransfer;
	}

	/*
	 * validating account eligibility from response
	 */
	String validateAccountEligibilty(Response getAssetTransfer) {
		List<Boolean> isEligible = getAssetTransfer.jsonPath().getList(Constant_Wealth.AssetTransferIsEligible);
		/*
		 * As multiple account not supported for now.
		 */
		return String.valueOf(isEligible.get(0));
	}

	/*
	 * Holding data from response
	 */
	void getHoldingData(String expected_cusip, Response getAssetTransfer, HashMap<String, String> expected_keyValue) {
		System.out.println("@getHoldingData");
		List<String> holdingList = getAssetTransfer.jsonPath().getList(Constant_Wealth.AssetTransferHolding);
		String[] holdingListStr = holdingList.toString().split("\\}, \\{");
		for (String holding : holdingListStr) {
			String[] keyValue = (holding.replaceAll("\\[\\[\\{", "").replaceAll("\\}\\]\\]", "").replaceAll("\\}", "")
					.replaceAll("\\{", "").split(","));
			HashMap<String, String> responseHoldingValue = new HashMap<>();
			for (String pair : keyValue) {
				String[] split = pair.split("=");
				responseHoldingValue.put(split[0].trim(), split[1].trim());
			}
			if (expected_cusip.equalsIgnoreCase(responseHoldingValue.get("cusipNumber"))) {
				validateAssetTransferHoldingEligibility(expected_cusip, expected_keyValue, responseHoldingValue);
				if(expected_keyValue.get("isHoldingEligible").equalsIgnoreCase("true"))
				checkHoldingData(responseHoldingValue.get("id"), responseHoldingValue);
			}
		}
	}

	/*
	 * Validate Eligibility for holdings
	 */
	void validateAssetTransferHoldingEligibility(String expected_cusip, HashMap<String, String> expected_keyValue,
			HashMap<String, String> responseHoldingValue) {
		try {
			System.out.println("validateAssetTransferHoldingEligibility");
			String expected_HoldingEligiblity = expected_keyValue.get("isHoldingEligible");
			String actual_HoldingEligibility = responseHoldingValue.get("isAssetTransferEligible");
			Assert.assertTrue(expected_HoldingEligiblity.equalsIgnoreCase(actual_HoldingEligibility),
					"HoldingEligibility not as Expected for holding id: " + expected_cusip);
		} catch (Exception e) {
			e.getStackTrace()[0].getLineNumber();
			Assert.fail(e.getMessage());
			Assert.fail("Exception while validating holding data");
		}
	}

	/*
	 * checking Request Status
	 */
	void checkRequestStatus(String requestId) {
		try {
			ResultSet rsRequest = getQueryResult("ASSET_TRANSFER_REQUEST", "ASSET_TRANSFER_REQUEST_ID", requestId);
			while (rsRequest.next()) {
				String statusFromDB = rsRequest.getString("VALIDATION_STATUS");
				Assert.assertTrue(responseStatus.equalsIgnoreCase(statusFromDB),
						"AssetTransfer Eligibity status doesnt match in DB and response for Holding id " + requestId);
			}
		} catch (Exception e) {
			e.getStackTrace()[0].getLineNumber();
			Assert.fail(e.getMessage());
			Assert.fail("Exception while validating assetTransferValidation status for requestId " + requestId);
		}
	}

	/*
	 * checking Account Status
	 */
	void checkAccountData(String requestId, Response getAssetTransfer) {
		try {
			System.out.println("checkAccountData");
			ResultSet rsAccount = getQueryResult("ASSET_TRANSFER_SRC_ACC", "ASSET_TRANSFER_REQUEST_ID", requestId);
			while (rsAccount.next()) {
				// validation
				String db_ACCOUNT_TYPE = rsAccount.getString("ACCOUNT_TYPE");
				String db_SRC_ITEM_ACCOUNT_ID = String.valueOf(rsAccount.getLong("SRC_ITEM_ACCOUNT_ID"));
				int db_IS_ELIGIBLE = rsAccount.getInt("IS_ELIGIBLE");
				String db_eligibilty = "false";
				if (db_IS_ELIGIBLE == 1)
					db_eligibilty = "true";

				List<String> accountId = null;
				accountId = getAssetTransfer.jsonPath().getList(Constant_Wealth.AssetTransferAccountId);
				Assert.assertTrue(String.valueOf(accountId.get(0)).equalsIgnoreCase(db_SRC_ITEM_ACCOUNT_ID),
						"AccountId not matching in DB and GET response for requestID: " + requestId);

				List<String> accountType = null;
				accountType = getAssetTransfer.jsonPath().getList(Constant_Wealth.AssetTransferAccountType);
				Assert.assertTrue(String.valueOf(accountType.get(0)).equalsIgnoreCase(db_ACCOUNT_TYPE),
						"AccountType not matching in DB and GET response for requestID: " + requestId);

				List<String> eligibilty = null;
				eligibilty = getAssetTransfer.jsonPath().getList(Constant_Wealth.AssetTransferIsEligible);
				Assert.assertTrue(String.valueOf(eligibilty.get(0)).equalsIgnoreCase(db_eligibilty),
						"AccountType not matching in DB and GET response for requestID: " + requestId);
			}
		} catch (SQLException e) {
			e.getStackTrace()[0].getLineNumber();
			Assert.fail(e.getMessage());
		}
	}

	/*
	 * checking holding data
	 */
	void checkHoldingData(String src_holding_id, HashMap<String, String> responseHoldingValue) {
		System.out.println("@checkHoldingData");
		try {
			ResultSet rsHolding = getQueryResult("ASSET_TRANSFER_SRC_HLD", "SRC_HOLDING_ID", src_holding_id);
			while (rsHolding.next()) {
//				System.out.println("price from DB: "+rsHolding.getString("PRICE"));
//				System.out.println("price from response: "+responseHoldingValue.get("price"));
//				Assert.assertTrue(rsHolding.getString("PRICE").equals(responseHoldingValue.get("price")),
//						"Price mismatch for Holding id: " + src_holding_id);
				Assert.assertTrue(rsHolding.getString("CUSIP").equals(responseHoldingValue.get("cusipNumber")),
						"Cusip Number mismatch for Holding id: " + src_holding_id);
				Assert.assertTrue(rsHolding.getString("ISIN").equals(responseHoldingValue.get("isin")),
						"ISIN Number mismatch for Holding id: " + src_holding_id);
				Assert.assertTrue(rsHolding.getString("SEDOL").equals(responseHoldingValue.get("sedol")),
						"SEDOL Number mismatch for Holding id: " + src_holding_id);
				Assert.assertTrue(rsHolding.getString("SYMBOL").equals(responseHoldingValue.get("symbol")),
						"SYMBOL Number mismatch for Holding id: " + src_holding_id);
				Assert.assertTrue(rsHolding.getString("SYMBOL").equals(responseHoldingValue.get("symbol")),
						"SYMBOL Number mismatch for Holding id: " + src_holding_id);
				int db_is_eligible = rsHolding.getInt("IS_ELIGIBLE");
				String db_eligiblity = "false";
				if (db_is_eligible == 1)
					db_eligiblity = "true";
				Assert.assertTrue(db_eligiblity.equalsIgnoreCase(responseHoldingValue.get("isAssetTransferEligible")),
						"Eligibilty mismatch for Holding id: " + src_holding_id);

			}
		} catch (Exception e) {
			e.getStackTrace()[0].getLineNumber();
			Assert.fail(e.getMessage());
		}

	}

	/*
	 * Running wealth table queries
	 */
	ResultSet getQueryResult(String tableName, String whereClausefield, Object whereClauseValue) {
		try {
			String query = "select * from " + tableName + " where " + whereClausefield + " = " + whereClauseValue;
			System.out.println("Executing Query: " + query);
			Statement stmt = conn_wealth.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rsQuery = stmt.executeQuery(query);
			return rsQuery;
		} catch (Exception e) {
			e.getStackTrace()[0].getLineNumber();
			Assert.fail(e.getMessage());
			return null;
		}
	}

	/*
	 * getting expected values as Map
	 */
	HashMap<String, String> getParameterMap(String parameter) {
		System.out.println("@getParameterMap");
		System.out.println(parameter);
		String[] parameters = parameter.split(",");
		HashMap<String, String> keyValue = new HashMap<String, String>();
		for (String pair : parameters) {
			System.out.println("pair: " + pair);
			String[] split = pair.split(":");
			keyValue.put(split[0], split[1]);
		}
		return keyValue;
	}

	/*
	 * Adding SDG account
	 */
	@SuppressWarnings("unchecked")
	public long addSDGAccount() {
		SdgHelper sdgHelper = new SdgHelper();
		System.out.println("adding sdg account...........");
		String filePath = "..\\src\\test\\resources\\TestData\\XML\\Accounts\\";
		String responseFile = "Wealth_AssetTransfer.xml";
		System.out.println("Response file path is::" + filePath + responseFile);
		String siteUserName = null;
		String sitePwd = null;
		String siteId = null;
		long providerAccountId = 0;
		// Loading XML and populating dataset values
		ProcessSdg processSdg = new ProcessSdg(filePath + responseFile);
		List<Object> listOfMaps = processSdg.list;
		List<AdditionalDataSet> additionalDataSets = null;
		if (!listOfMaps.isEmpty()) {
			Map<String, Map<String, Object>> xmlTagWithDetailsMap = (Map<String, Map<String, Object>>) listOfMaps
					.get(0);
			if (xmlTagWithDetailsMap.containsKey("add-provider-accounts1")) {
				Map<String, Object> requestMapDetails = xmlTagWithDetailsMap.get("add-provider-accounts1");
				if (requestMapDetails.containsKey("siteUser"))
					siteUserName = requestMapDetails.get("siteUser").toString();
				if (requestMapDetails.containsKey("sitePwd"))
					sitePwd = requestMapDetails.get("sitePwd").toString();
				if (requestMapDetails.containsKey("siteId"))
					siteId = requestMapDetails.get("siteId").toString();
				System.out.println("dataList.dataset value is==" + requestMapDetails.get("dataset"));
				if (requestMapDetails.get("dataset") != null
						&& !requestMapDetails.get("dataset").toString().isEmpty()) {
					RequestSequence requestSequence = RequestSequence.getInstance();
					additionalDataSets = requestSequence
							.getDataSet((List<HierarchicalConfiguration>) requestMapDetails.get("dataset"));
				}
				// Adding Sdg Account
				providerAccountId = sdgHelper.addProviderAccountSdg(null, null, siteUserName, sitePwd, siteId,
						additionalDataSets, sessionObj, "fieldarray", Constants.DATA_SET);
				System.out.println("providerAccountId value testclass is::" + providerAccountId);
			}

		}

		if (providerAccountId == 0) {
			throw new RuntimeException("providerAccountId is not present after account addition");

		}

		return providerAccountId;
	}

	@AfterClass
	void cleanUp() throws Exception {
		conn_oltp.close();
		conn_wealth.close();
		System.gc();
	}
}
