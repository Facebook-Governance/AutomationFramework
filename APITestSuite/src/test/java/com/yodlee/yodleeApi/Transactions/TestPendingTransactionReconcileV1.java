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

package com.yodlee.yodleeApi.Transactions;

import org.testng.annotations.*;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


import org.databene.benerator.anno.Source;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;
import com.yodlee.yodleeApi.helper.SessionHelper;
import com.yodlee.yodleeApi.helper.UserHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.pojo.User;

import com.yodlee.yodleeApi.helper.ProviderAccountsHelper;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.common.Configuration;


import io.restassured.response.Response;
import com.yodlee.yodleeApi.constants.Constants;
import com.yodlee.yodleeApi.constants.JSONPaths;
import com.yodlee.DBHelper;
import com.yodlee.yodleeApi.database.DbQueries;
import com.yodlee.app.yodleeApi.DagPages.DagHome;


/**
 * @author namitha
 * B-36571 - Reconciliation of Pending Transaction
 */
public class TestPendingTransactionReconcileV1 extends Base{
	public static final String testPendTransReconcile = "\\TestData\\CSVFiles\\PendingTransactions\\ReconcilePendTransV1.csv";
	
	public final String currentDir = System.getProperty("user.dir");
	public Path chromedriverfilePath = Paths.get(currentDir, "driver", "chromedriver.exe");
	String dagSiteUserName = "PendingTrx_RecNonSdg.site16441.1", dagSitePassword = "site16441.1", catalogUserName = "PendingTrx_RecNonSdg", catalogPassword = "santaclaus", 
			add_xmlUpload_path = "\\TestData\\XML\\pendingTransactions\\add_account", update_xmlUpload_path = "\\TestData\\XML\\pendingTransactions\\update_account", transactionDesc="AUTOZONE 1295 Complete", transactionAmount="131",
			dagUrl = "https://dag2.yodlee.com/dag/dhaction.do";
	long providerId = 16441;
	CommonUtils commonUtils = new CommonUtils();
	SessionHelper sessionHelper = new SessionHelper();
	DBHelper dbHelper = new DBHelper();
	ProviderAccountsHelper providerAccountsHelper = new ProviderAccountsHelper();
	Configuration configurationObj = Configuration.getInstance();
	UserHelper userHelper = new UserHelper();
	UserUtils userUtils = new UserUtils();
	Long providerAccountId;
	Response getProvidersResponse;
	List<String> list1 = new ArrayList<>();
	
	@BeforeClass
	public void testSetUp() throws Exception {
		System.out.println(" ==== DB Check for COM.YODLEE.DBFILER.UPDATE_PENDING_TXNS.ENABLED key is enabled or not ==== ");	
		String cobrandId = Configuration.getInstance().getCobrandObj().getCobrandId();

		String res = dbHelper.getValueFromDB(DbQueries.GET_PARAM_VALUE + cobrandId, "PARAM_VALUE").toString();
		if (res.equalsIgnoreCase("TRUE")) {
			System.out.println(
					"COM.YODLEE.DBFILER.UPDATE_PENDING_TXNS.ENABLED key is enabled for the cobrandId " + cobrandId);
		} else if (res.equalsIgnoreCase("FALSE") || res.isEmpty()) {
			Assert.fail("The required key is not enabled for the cobrand");
		}
		
		
	}
	
	@Test(dataProvider = Constants.FEEDER, alwaysRun = true)
	@Source(testPendTransReconcile)
	public void testPendingTransactionReconcile(String testCaseId, String testCaseName, String loginNameKey, String loginName,
			String passwordKey, String password, String emailKey, String email, String firstKey, String first,
			String lastKey, String last, String address1Key, String address1, String stateKey, String state,
			String cityKey, String city, String zipKey, String zip, String countryKey, String country,
			String currencyKey, String currency, String timeZoneKey, String timeZone, String dateFormatKey,
			String dateFormat, String localeKey, String locale, String nameKey, String addressKey,
			String preferencesKey, String userKey, String add_dagXmlFileName,
			String update_dagXmlFileName, String container, String isDeleted0_Pend_AfterAdd, String isDeleted0_Posted_AfterAdd, String isDeleted0_Pend_AfterEdit,
			String isDeleted0_Posted_AfterEdit, String isDeleted1_Pend_AfterEdit, String isDeleted1_Posted_AfterEdit,
			String verifyAmount, String verifyDesc, String priority, String enabled)
			throws Exception {

		commonUtils.isTCEnabled(enabled, testCaseName);
		System.out.println("*******TestCase : " + testCaseName + " & testCaseId : " + testCaseId + "*******");
		String dynamicLoginName = loginName + System.currentTimeMillis();
		
		try {
		// Creating json
		String userRegisterObjJSON = userHelper.userRegisterJson(loginNameKey, dynamicLoginName, passwordKey,
							password, emailKey, email, firstKey, first, lastKey, last, address1Key, address1, stateKey, state,
							cityKey, city, zipKey, zip, countryKey, country, currencyKey, currency, timeZoneKey, timeZone,
							dateFormatKey, dateFormat, localeKey, locale, nameKey, addressKey, preferencesKey, userKey);

		System.out.println("currency:::::::" + currency + "dateFormat::::::" + dateFormat);

		// Set all to HttpParam
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(userRegisterObjJSON);

		EnvSession sessionObj = EnvSession.builder()
							.cobSession(configurationObj.getCobrandSessionObj().getCobSession())
							.path(configurationObj.getCobrandSessionObj().getPath()).build();
		Response newUserResponse = (Response) userUtils.userRegistrationResponse(sessionObj, httpMethodParameters);

		if (newUserResponse.getStatusCode() == 200) {
			String userSession = newUserResponse.jsonPath().getString(JSONPaths.USER_SESSION_TOKEN);

			sessionObj = EnvSession.builder()
					.cobSession(configurationObj.getCobrandSessionObj().getCobSession()).userSession(userSession)
					.path(configurationObj.getCobrandSessionObj().getPath()).build();

			System.out.println("New user Registered : " + dynamicLoginName);
		}
			
		// launching chrome browser
		System.setProperty("webdriver.chrome.driver", chromedriverfilePath.toString());
		WebDriver driver = new ChromeDriver();
		DagHome dagHome = new DagHome(driver);
		driver.manage().window().maximize();
		
		// Upload xml in dag site and add an account and verify DB values
		dagHome.loginAndUploadDagXml(dagUrl, catalogUserName, catalogPassword, dagSiteUserName, add_xmlUpload_path,
				add_dagXmlFileName, container);
		
		ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
		Response response = providerAccountUtils.addProviderAccountStrict(providerId, Constants.SIMPLIFIED_FORM, dagSiteUserName, dagSitePassword, sessionObj);
		providerAccountId = response.jsonPath().getLong(JSONPaths.PROVIDER_ACC_ID);

		list1.clear();
		if(container.equalsIgnoreCase("Bank")) {
		list1= getIsDeletedCountBank(providerAccountId);
		assertValuesAfterAdd(list1,isDeleted0_Pend_AfterAdd,isDeleted0_Posted_AfterAdd);
		}
		else {
			list1= getIsDeletedCountCard(providerAccountId);
			assertValuesAfterAdd(list1,isDeleted0_Pend_AfterAdd,isDeleted0_Posted_AfterAdd);
		}
		// Upload xml in dag site and Update an account, verify DB values
		WebDriver driver1 = new ChromeDriver();
		DagHome dagHome1 = new DagHome(driver1);
		driver1.manage().window().maximize();
		dagHome1.loginAndUploadDagXml(dagUrl, catalogUserName, catalogPassword, dagSiteUserName,
				update_xmlUpload_path, update_dagXmlFileName, container);
		
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams=providerAccountsHelper.createInputForAddProviderAcct(Long.toString(providerId), Constants.SIMPLIFIED_FORM, dagSiteUserName, dagSitePassword, Constants.TWO_COUNT);
		LinkedHashMap<String, Object> queryParam = new LinkedHashMap<>();
		queryParam.put("providerAccountIds", String.valueOf(providerAccountId));
		httpParams.setQueryParams(queryParam);
		
		providerAccountUtils.updateProviderAccount(httpParams, sessionObj);
		providerAccountUtils.checkRefreshStatusUntilActAddition(sessionObj, providerAccountId+"");

		
		list1.clear();
		if(container.equalsIgnoreCase("Bank")) {
			list1= getIsDeletedCountBank(providerAccountId);
			assertValuesAfterEdit(list1,isDeleted0_Pend_AfterEdit,isDeleted0_Posted_AfterEdit,isDeleted1_Pend_AfterEdit,isDeleted1_Posted_AfterEdit);
			if(verifyDesc.equalsIgnoreCase("TRUE")) {
				Assert.assertTrue(getDescriptionAmtForBank(providerAccountId).get(0).equalsIgnoreCase(transactionDesc),
						"Transaction description is not updated properly in DB");		
			}
			if(verifyAmount.equalsIgnoreCase("TRUE")) {
				Assert.assertTrue(getDescriptionAmtForBank(providerAccountId).get(1).equalsIgnoreCase(transactionAmount),
						"Transaction amount is not updated properly in DB");		
			}
		}
		else {
			list1= getIsDeletedCountCard(providerAccountId);
			assertValuesAfterEdit(list1,isDeleted0_Pend_AfterEdit,isDeleted0_Posted_AfterEdit,isDeleted1_Pend_AfterEdit,isDeleted1_Posted_AfterEdit);
			if(verifyDesc.equalsIgnoreCase("TRUE")) {
				Assert.assertTrue(getDescriptionAmtForCard(providerAccountId).get(0).equalsIgnoreCase(transactionDesc),
						"Transaction description is not updated properly in DB");		
			}
			if(verifyAmount.equalsIgnoreCase("TRUE")) {
				Assert.assertTrue(getDescriptionAmtForCard(providerAccountId).get(1).equalsIgnoreCase(transactionAmount),
						"Transaction amount is not updated properly in DB");		
			}
		}
		// Unregister user
		UserUtils userUtils = new UserUtils();
		Response res = userUtils.unRegisterUser(sessionObj);
		res.prettyPrint();
		}
		catch (Exception e) {
			e.printStackTrace();
			Assert.fail("failed to execute " + testCaseName + " test case due to " + e);
		}
	}
	
	/**
	 * This method is to get the list of transaction count based on IS_DELETED column value for pending and Posted trans from Bank transaction table
	 * @param mem_site_acc
	 * @return
	 * @throws Exception
	 */
	private List<String> getIsDeletedCountBank(Long mem_site_acc) throws Exception {
		list1.clear();
		String cacheItem = dbHelper.getValueFromDB(DbQueries.GET_CACHE_ITEM_ID_FOR_BANK+ mem_site_acc, "CACHE_ITEM_ID").toString();
		String itemAccountId = dbHelper.getValueFromDB(DbQueries.GET_ITEM_ACCOUNT_ID+ cacheItem, "ITEM_ACCOUNT_ID").toString();
		String isDeletedVal0Pend = dbHelper.getValueFromDB(DbQueries.GET_BANK_ISDELETEDVAL0_PEND_COUNT+ itemAccountId, "COUNT(*)").toString();
		String isDeletedVal0Posted = dbHelper.getValueFromDB(DbQueries.GET_BANK_ISDELETEDVAL0_POSTED_COUNT+ itemAccountId, "COUNT(*)").toString();
		String isDeletedVal1Pend = dbHelper.getValueFromDB(DbQueries.GET_BANK_ISDELETEDVAL1_PEND_COUNT+ itemAccountId, "COUNT(*)").toString();
		String isDeletedVal1Posted = dbHelper.getValueFromDB(DbQueries.GET_BANK_ISDELETEDVAL1_POSTED_COUNT+ itemAccountId, "COUNT(*)").toString();
		
		list1.add(isDeletedVal0Pend);
		list1.add(isDeletedVal0Posted);
		list1.add(isDeletedVal1Pend);
		list1.add(isDeletedVal1Posted);
		return list1;	
	}
	
	/**
	 * This method is to get the list of transaction count based on IS_DELETED column value for pending and Posted trans from Card transaction table
	 * @param mem_site_acc
	 * @return
	 * @throws Exception
	 */
	private List<String> getIsDeletedCountCard(Long mem_site_acc) throws Exception {
		list1.clear();
		String cacheItem = dbHelper.getValueFromDB(DbQueries.GET_CACHE_ITEM_ID_FOR_CARD+ mem_site_acc, "CACHE_ITEM_ID").toString();
		String itemAccountId = dbHelper.getValueFromDB(DbQueries.GET_ITEM_ACCOUNT_ID+ cacheItem, "ITEM_ACCOUNT_ID").toString();
		String isDeletedVal0Pend = dbHelper.getValueFromDB(DbQueries.GET_CARD_ISDELETEDVAL0_PEND_COUNT+ itemAccountId, "COUNT(*)").toString();
		String isDeletedVal0Posted = dbHelper.getValueFromDB(DbQueries.GET_CARD_ISDELETEDVAL0_POSTED_COUNT+ itemAccountId, "COUNT(*)").toString();
		String isDeletedVal1Pend = dbHelper.getValueFromDB(DbQueries.GET_CARD_ISDELETEDVAL1_PEND_COUNT+ itemAccountId, "COUNT(*)").toString();
		String isDeletedVal1Posted = dbHelper.getValueFromDB(DbQueries.GET_CARD_ISDELETEDVAL1_POSTED_COUNT+ itemAccountId, "COUNT(*)").toString();
		
		list1.add(isDeletedVal0Pend);
		list1.add(isDeletedVal0Posted);
		list1.add(isDeletedVal1Pend);
		list1.add(isDeletedVal1Posted);
		return list1;				
	}
	
	/**
	 * This method is to get the list of Desc and Amount from Bank transaction table for a specific Item Account Id
	 * @param mem_site_acc
	 * @return
	 * @throws Exception
	 */
	private List<String> getDescriptionAmtForBank(Long mem_site_acc) throws Exception {
		list1.clear();
		String cacheItem = dbHelper.getValueFromDB(DbQueries.GET_CACHE_ITEM_ID_FOR_BANK+ mem_site_acc, "CACHE_ITEM_ID").toString();
		String itemAccountId = dbHelper.getValueFromDB(DbQueries.GET_ITEM_ACCOUNT_ID+ cacheItem, "ITEM_ACCOUNT_ID").toString();
		String desc = dbHelper.getValueFromDB(DbQueries.GET_DESCRIPTION_BANK+ itemAccountId, "plain_text_description").toString();
		String amt = dbHelper.getValueFromDB(DbQueries.GET_AMOUNT_BANK+ itemAccountId, "transaction_amount").toString();
		
		list1.add(desc);
		list1.add(amt);
		return list1;			
	}
	
	/**
	 * This method is to get the list of Desc and Amount from Card transaction table for a specific Item Account Id
	 * @param mem_site_acc
	 * @return
	 * @throws Exception
	 */
	private List<String> getDescriptionAmtForCard(Long mem_site_acc) throws Exception {
		list1.clear();
		String cacheItem = dbHelper.getValueFromDB(DbQueries.GET_CACHE_ITEM_ID_FOR_CARD+ mem_site_acc, "CACHE_ITEM_ID").toString();
		String itemAccountId = dbHelper.getValueFromDB(DbQueries.GET_ITEM_ACCOUNT_ID+ cacheItem, "ITEM_ACCOUNT_ID").toString();
		String desc = dbHelper.getValueFromDB(DbQueries.GET_DESCRIPTION_CARD+ itemAccountId, "plain_text_description").toString();
		String amt = dbHelper.getValueFromDB(DbQueries.GET_AMOUNT_CARD+ itemAccountId, "TRANS_AMOUNT").toString();
		
		list1.add(desc);
		list1.add(amt);
		return list1;			
	}	
	
	/**
	 * This method is to assert the values that we have got as a list from transaction table after add account
	 * @param list
	 * @param val1
	 * @param val2
	 */
	private void assertValuesAfterAdd(List<String> list, String val1, String val2 ) {
		System.out.println("verifying db after add account");
		Assert.assertTrue(list.get(0).equalsIgnoreCase(val1),
				"IsDeleted count for pending transactions is incorrect after add account");
		Assert.assertTrue(list.get(1).equalsIgnoreCase(val2),
				"IsDeleted count for posted transactions is incorrect after add account");
		
	}
	
	/**
	 * This method is to assert the values that we have got as a list from transaction table after Update/Edit account
	 * @param list
	 * @param val1
	 * @param val2
	 * @param val3
	 * @param val4
	 */
	private void assertValuesAfterEdit(List<String> list, String val1, String val2, String val3,String val4 ) {
		System.out.println("verifying db after update account");
		Assert.assertTrue(list.get(0).equalsIgnoreCase(val1),
				"IsDeleted count for pending transactions is incorrect after edit account");
		Assert.assertTrue(list.get(1).equalsIgnoreCase(val2),
				"IsDeleted count for posted transactions is incorrect after edit account");
		Assert.assertTrue(list.get(2).equalsIgnoreCase(val3),
				"IsDeleted count for soft deleted pending trasactions is incorrect after edit account");
		Assert.assertTrue(list.get(3).equalsIgnoreCase(val4),
				"IsDeleted count for soft deleted posted trasactions is incorrect after edit account");
			
	}
	}
