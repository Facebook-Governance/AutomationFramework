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

package com.yodlee.yodleeApi.sdg.script;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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
import com.yodlee.yodleeApi.pojo.AdditionalDataSet;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.User;
import com.yodlee.yodleeApi.helper.AccountsHelper;
import com.yodlee.yodleeApi.sdg.SdgHelper;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.common.Configuration;

import io.restassured.response.Response;
import com.yodlee.yodleeApi.constants.Constants;
import com.yodlee.DBHelper;
import com.yodlee.yodleeApi.database.DbQueries;
import com.yodlee.app.yodleeApi.DagPages.DagHome;
import com.yodlee.taas.annote.TestGroup;

/**
 * @author namitha 
 * B-36571 - Reconciliation of Pending Transaction
 *
 */
public class TestPendingTransactionReconcile extends Base {

	public final String testPendTransReconcile = "\\TestData\\CSVFiles\\PendingTransactions\\ReconcilePendTrans.csv";
	public final String currentDir = System.getProperty("user.dir");
	public Path chromedriverfilePath = Paths.get(currentDir, "driver", "chromedriver.exe");
	public final String dagSiteUserName = "PendingTrx_ReconSdg.site16441.1", dagSitePassword = "site16441.1",
			catalogUserName = "PendingTrx_ReconSdg", catalogPassword = "santaclaus",
			add_xmlUpload_path = "\\TestData\\XML\\pendingTransactions\\add_account",
			update_xmlUpload_path = "\\TestData\\XML\\pendingTransactions\\update_account",
			dagUrl = "https://dag2.yodlee.com/dag/dhaction.do", dataSetTemplateFile2 = "withoutContainer.xml",
			transactionDesc = "AUTOZONE 1295 Complete", transactionAmount = "131";
	String filePath = "..\\src\\test\\resources\\TestData\\XML\\pendingTransactions\\";
	CommonUtils commonUtils = new CommonUtils();
	SessionHelper sessionHelper = new SessionHelper();
	AccountsHelper accountsHelper = new AccountsHelper();
	SdgHelper sdgHelper = new SdgHelper();
	DBHelper dbHelper = new DBHelper();
	List<String> list1 = new ArrayList<>();
	List<String> list2 = new ArrayList<>();

	Long providerAccountId;
	Response getProvidersResponse;
	
	@BeforeClass
	public void verifyKeyEnablement() throws Exception {
		System.out.println(
				" ==== DB Check for COM.YODLEE.DBFILER.UPDATE_PENDING_TXNS.ENABLED key is enabled or not ==== ");
		String cobrandId = Configuration.getInstance().getCobrandObj().getCobrandId();

		String res = dbHelper.getValueFromDB(DbQueries.GET_PARAM_VALUE + cobrandId, "PARAM_VALUE").toString();
		if (res.equalsIgnoreCase("TRUE")) {
			System.out.println(
					"COM.YODLEE.DBFILER.UPDATE_PENDING_TXNS.ENABLED key is enabled for the cobrandId " + cobrandId);
		} else if (res.equalsIgnoreCase("FALSE") || res.isEmpty()) {
			Assert.fail("The required key is not enabled for the cobrand");
		}

	}

	@Source(testPendTransReconcile)
	@Test(enabled = true, groups = { TestGroup.REGRESSION }, dataProvider = Constants.FEEDER)
	public void testPendingTransactionReconcile(String testCaseId, String testCaseName, String add_dagXmlFileName,
			String update_dagXmlFileName, String container, String dataSetTemplateFile, String dataSetTemplateFile1,
			String isDeleted0_Pend_AfterAdd, String isDeleted0_Posted_AfterAdd, String isDeleted0_Pend_AfterEdit,
			String isDeleted0_Posted_AfterEdit, String isDeleted1_Pend_AfterEdit, String isDeleted1_Posted_AfterEdit,
			String verifyAmount, String verifyDesc, String priority, String enabled) throws Exception {

		commonUtils.isTCEnabled(enabled, testCaseName);
		System.out.println("*******TestCase : " + testCaseName + " & testCaseId : " + testCaseId + "*******");
		// Creating a new user.
		System.out.println("User Registration Started");
		String pendingTrxUserName = "TestYSLSdg"+"pendingTrx" + System.currentTimeMillis();
		String pendingTrxPassword = "TEST@123";

		User userInfo = User.builder().build();
		userInfo.setUsername(pendingTrxUserName);
		userInfo.setPassword(pendingTrxPassword);
		// User Registration
		EnvSession commonEnvObj = sessionHelper.getSessionObjectByUserRegistration(userInfo);
		System.out.println("New userSession created is::" + commonEnvObj.getUserSession());
		try {
			// launching chrome browser
			System.setProperty("webdriver.chrome.driver", chromedriverfilePath.toString());
			WebDriver driver = new ChromeDriver();
			DagHome dagHome = new DagHome(driver);
			driver.manage().window().maximize();
			
			// Upload xml in dag site and add an account and verify DB values
			dagHome.loginAndUploadDagXml(dagUrl, catalogUserName, catalogPassword, dagSiteUserName, add_xmlUpload_path,
					add_dagXmlFileName, container);

			List<AdditionalDataSet> dataSet = accountsHelper.getAdditionalDataSet(filePath + dataSetTemplateFile);
			sdgHelper.providerAccountId = sdgHelper.addProviderAccountSdg(null, null, dagSiteUserName, dagSitePassword,
					"16441", dataSet, commonEnvObj, "fieldarray", Constants.DATA_SET);

			list1.clear();
			if (container.equalsIgnoreCase("Bank")) {
				list1 = getIsDeletedCountBank(sdgHelper.providerAccountId);
				assertValuesAfterAdd(list1, isDeleted0_Pend_AfterAdd, isDeleted0_Posted_AfterAdd);
			} else {
				list1 = getIsDeletedCountCard(sdgHelper.providerAccountId);
				assertValuesAfterAdd(list1, isDeleted0_Pend_AfterAdd, isDeleted0_Posted_AfterAdd);
			}

			// Upload xml in dag site and Update an account, verify DB values
			WebDriver driver1 = new ChromeDriver();
			DagHome dagHome1 = new DagHome(driver1);
			driver1.manage().window().maximize();
			dagHome1.loginAndUploadDagXml(dagUrl, catalogUserName, catalogPassword, dagSiteUserName,
					update_xmlUpload_path, update_dagXmlFileName, container);

			List<AdditionalDataSet> dataSet1 = accountsHelper.getAdditionalDataSet(filePath + dataSetTemplateFile1);
			sdgHelper.updateProviderAccount(null, null, 65499, dagSiteUserName, 65500, "16441", dagSitePassword,
					dataSet1, commonEnvObj, "fieldarray", Constants.DATA_SET);
			list1.clear();
			if (container.equalsIgnoreCase("Bank")) {
				list1 = getIsDeletedCountBank(sdgHelper.providerAccountId);
				assertValuesAfterEdit(list1, isDeleted0_Pend_AfterEdit, isDeleted0_Posted_AfterEdit,
						isDeleted1_Pend_AfterEdit, isDeleted1_Posted_AfterEdit);
				if (verifyDesc.equalsIgnoreCase("TRUE")) {
					Assert.assertTrue(getDescriptionAmtForBank(sdgHelper.providerAccountId).get(0).equalsIgnoreCase(
							transactionDesc), "Transaction description is not updated properly in DB");
				}
				if (verifyAmount.equalsIgnoreCase("TRUE")) {
					Assert.assertTrue(getDescriptionAmtForBank(sdgHelper.providerAccountId).get(1)
							.equalsIgnoreCase(transactionAmount), "Transaction amount is not updated properly in DB");
				}
			} else {
				list1 = getIsDeletedCountCard(sdgHelper.providerAccountId);
				assertValuesAfterEdit(list1, isDeleted0_Pend_AfterEdit, isDeleted0_Posted_AfterEdit,
						isDeleted1_Pend_AfterEdit, isDeleted1_Posted_AfterEdit);
				if (verifyDesc.equalsIgnoreCase("TRUE")) {
					Assert.assertTrue(getDescriptionAmtForCard(sdgHelper.providerAccountId).get(0).equalsIgnoreCase(
							transactionDesc), "Transaction description is not updated properly in DB");
				}
				if (verifyAmount.equalsIgnoreCase("TRUE")) {
					Assert.assertTrue(getDescriptionAmtForCard(sdgHelper.providerAccountId).get(1)
							.equalsIgnoreCase(transactionAmount), "Transaction amount is not updated properly in DB");
				}
			}

			System.out.println("*********Completed TestPendingTransactionReconcile test class***********");

		}

		catch (Exception e) {
			e.printStackTrace();
			Assert.fail("failed to execute " + testCaseName + " test case due to " + e);
		}
		// Unregister user
		UserUtils userUtils = new UserUtils();
		Response res = userUtils.unRegisterUser(commonEnvObj);
		res.prettyPrint();

	}

	@Test(description = "AT-105434:verify pending transaction reconcile without passing container name", enabled = true)
	public void testPendingTransactionReconcile1() throws Exception {
		String addXmlBank = "BankSce24Add.xml", addXmlCard = "Card1.xml", isDeletedVal0_pend = "3",
				isDeletedVal0_Posted = "0", isDeletedVal1 = "0";
		String pendingTrxUserName ="TestYSLSdg"+"pendingTrx" + System.currentTimeMillis();
		String pendingTrxPassword = "TEST@123";

		// Creating a new user.
		System.out.println("User Registration Started");

		User userInfo = User.builder().build();
		userInfo.setUsername(pendingTrxUserName);
		userInfo.setPassword(pendingTrxPassword);
		// User Registration
		EnvSession commonEnvObj = sessionHelper.getSessionObjectByUserRegistration(userInfo);
		System.out.println("New userSession created is::" + commonEnvObj.getUserSession());
		try {
			// launching chrome browser
			System.setProperty("webdriver.chrome.driver", chromedriverfilePath.toString());
			WebDriver driver = new ChromeDriver();
			DagHome dagHome = new DagHome(driver);
			driver.manage().window().maximize();
			// Upload xml in dag site for Bank, Card containers and add an account without
			// passing container and verify DB values
			dagHome.loginAndUploadDagXml(dagUrl, catalogUserName, catalogPassword, dagSiteUserName, add_xmlUpload_path,
					addXmlBank, "Bank");

			WebDriver driver1 = new ChromeDriver();
			DagHome dagHome1 = new DagHome(driver1);
			driver1.manage().window().maximize();
			dagHome1.loginAndUploadDagXml(dagUrl, catalogUserName, catalogPassword, dagSiteUserName, add_xmlUpload_path,
					addXmlCard, "Credits");

			List<AdditionalDataSet> dataSet = accountsHelper.getAdditionalDataSet(filePath + dataSetTemplateFile2);
			sdgHelper.providerAccountId = sdgHelper.addProviderAccountSdg(null, null, dagSiteUserName, dagSitePassword,
					"16441", dataSet, commonEnvObj, "fieldarray", Constants.DATA_SET);

			list1.clear();
			list2.clear();
			list1 = getIsDeletedCountBank(sdgHelper.providerAccountId);
			assertValuesAfterAdd(list1, isDeletedVal0_pend, isDeletedVal0_Posted);
			list2 = getIsDeletedCountCard(sdgHelper.providerAccountId);
			assertValuesAfterAdd(list2, isDeletedVal0_pend, isDeletedVal0_Posted);

			// update the account without any changes and verify DB values
			sdgHelper.updateProviderAccount(null, null, 65499, dagSiteUserName, 65500, "16441", dagSitePassword,
					dataSet, commonEnvObj, "fieldarray", Constants.DATA_SET);
			list1.clear();
			list2.clear();
			list1 = getIsDeletedCountBank(sdgHelper.providerAccountId);
			assertValuesAfterEdit(list1, isDeletedVal0_pend, isDeletedVal0_Posted, isDeletedVal1, isDeletedVal1);
			list2 = getIsDeletedCountCard(sdgHelper.providerAccountId);
			assertValuesAfterEdit(list2, isDeletedVal0_pend, isDeletedVal0_Posted, isDeletedVal1, isDeletedVal1);

			System.out.println("*********Completed TestPendingTransactionReconcile test class***********");

		}

		catch (Exception e) {
			e.printStackTrace();
		}
		// Unregister user
		UserUtils userUtils = new UserUtils();
		Response res = userUtils.unRegisterUser(commonEnvObj);
		res.prettyPrint();

	}

	/**
	 * This method is to get the list of transaction count based on IS_DELETED column value for pending and Posted trans from Bank transaction table
	 * @param mem_site_acc
	 * @return
	 * @throws Exception
	 */
	private List<String> getIsDeletedCountBank(Long mem_site_acc) throws Exception {
		list1.clear();
		String cacheItem = dbHelper.getValueFromDB(DbQueries.GET_CACHE_ITEM_ID_FOR_BANK + mem_site_acc, "CACHE_ITEM_ID")
				.toString();
		String itemAccountId = dbHelper.getValueFromDB(DbQueries.GET_ITEM_ACCOUNT_ID + cacheItem, "ITEM_ACCOUNT_ID")
				.toString();
		String isDeletedVal0Pend = dbHelper
				.getValueFromDB(DbQueries.GET_BANK_ISDELETEDVAL0_PEND_COUNT + itemAccountId, "COUNT(*)").toString();
		String isDeletedVal0Posted = dbHelper
				.getValueFromDB(DbQueries.GET_BANK_ISDELETEDVAL0_POSTED_COUNT + itemAccountId, "COUNT(*)").toString();
		String isDeletedVal1Pend = dbHelper
				.getValueFromDB(DbQueries.GET_BANK_ISDELETEDVAL1_PEND_COUNT + itemAccountId, "COUNT(*)").toString();
		String isDeletedVal1Posted = dbHelper
				.getValueFromDB(DbQueries.GET_BANK_ISDELETEDVAL1_POSTED_COUNT + itemAccountId, "COUNT(*)").toString();

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
		String cacheItem = dbHelper.getValueFromDB(DbQueries.GET_CACHE_ITEM_ID_FOR_CARD + mem_site_acc, "CACHE_ITEM_ID")
				.toString();
		String itemAccountId = dbHelper.getValueFromDB(DbQueries.GET_ITEM_ACCOUNT_ID + cacheItem, "ITEM_ACCOUNT_ID")
				.toString();
		String isDeletedVal0Pend = dbHelper
				.getValueFromDB(DbQueries.GET_CARD_ISDELETEDVAL0_PEND_COUNT + itemAccountId, "COUNT(*)").toString();
		String isDeletedVal0Posted = dbHelper
				.getValueFromDB(DbQueries.GET_CARD_ISDELETEDVAL0_POSTED_COUNT + itemAccountId, "COUNT(*)").toString();
		String isDeletedVal1Pend = dbHelper
				.getValueFromDB(DbQueries.GET_CARD_ISDELETEDVAL1_PEND_COUNT + itemAccountId, "COUNT(*)").toString();
		String isDeletedVal1Posted = dbHelper
				.getValueFromDB(DbQueries.GET_CARD_ISDELETEDVAL1_POSTED_COUNT + itemAccountId, "COUNT(*)").toString();

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
		String cacheItem = dbHelper.getValueFromDB(DbQueries.GET_CACHE_ITEM_ID_FOR_BANK + mem_site_acc, "CACHE_ITEM_ID")
				.toString();
		String itemAccountId = dbHelper.getValueFromDB(DbQueries.GET_ITEM_ACCOUNT_ID + cacheItem, "ITEM_ACCOUNT_ID")
				.toString();
		String desc = dbHelper.getValueFromDB(DbQueries.GET_DESCRIPTION_BANK + itemAccountId, "plain_text_description")
				.toString();
		String amt = dbHelper.getValueFromDB(DbQueries.GET_AMOUNT_BANK + itemAccountId, "transaction_amount").toString();

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
		String cacheItem = dbHelper.getValueFromDB(DbQueries.GET_CACHE_ITEM_ID_FOR_CARD + mem_site_acc, "CACHE_ITEM_ID")
				.toString();
		String itemAccountId = dbHelper.getValueFromDB(DbQueries.GET_ITEM_ACCOUNT_ID + cacheItem, "ITEM_ACCOUNT_ID")
				.toString();
		String desc = dbHelper.getValueFromDB(DbQueries.GET_DESCRIPTION_CARD + itemAccountId, "plain_text_description")
				.toString();
		String amt = dbHelper.getValueFromDB(DbQueries.GET_AMOUNT_CARD + itemAccountId, "TRANS_AMOUNT").toString();

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
	private void assertValuesAfterAdd(List<String> list, String val1, String val2) {
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
	private void assertValuesAfterEdit(List<String> list, String val1, String val2, String val3, String val4) {
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
