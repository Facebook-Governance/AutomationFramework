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

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.databene.benerator.anno.Source;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.yodlee.DBHelper;
import com.yodlee.taas.annote.Info;
import com.yodlee.taas.annote.TestGroup;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.Constants;
import com.yodlee.yodleeApi.database.DbQueries;
import com.yodlee.yodleeApi.helper.AccountsHelper;
import com.yodlee.yodleeApi.helper.DbHelper;
import com.yodlee.yodleeApi.helper.SessionHelper;
import com.yodlee.yodleeApi.pojo.AdditionalDataSet;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.User;
import com.yodlee.yodleeApi.sdg.CacheUtil;
import com.yodlee.yodleeApi.sdg.SdgHelper;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;

/**
 * @author namitha
 *
 */
public class TestCacheRefreshStatusNotification extends Base{
	public final String testCacheRefreshStatusNotification = "\\TestData\\CSVFiles\\Sdg\\CacheRefreshStatusNotification.csv";
	Connection conn = null;
	ResultSet rs = null;
	CommonUtils commonUtils = new CommonUtils();
	SessionHelper sessionHelper = new SessionHelper();
	AccountsHelper accountsHelper = new AccountsHelper();
	SdgHelper sdgHelper = new SdgHelper();
	DbHelper dbHelper = new DbHelper();
	DBHelper dBHelper = new DBHelper();
	CacheUtil cache = new CacheUtil(); 
	
	@BeforeClass
	public void verifyCacheRefreshNotificationConfig() throws Exception {
		System.out.println(" ==== DB Check to verify webhook subscription is enabled for a particular cobrand id  ==== ");	
		String cobrandId = Configuration.getInstance().getCobrandObj().getCobrandId();
		conn =dBHelper.getDBConnection();
		rs = dbHelper.getResultSet(conn,DbHelper.GET_WEBHOOKSUB_COBRAND + cobrandId);
		Assert.assertTrue(rs.next(), "Webhook subscription is enabled for a particular cobrand id");
	}
	
	@Info(userStoryId = "B-36195")
	@Source(testCacheRefreshStatusNotification)
	@Test(enabled = true, groups = { TestGroup.REGRESSION }, dataProvider = Constants.FEEDER)
	public void testCacheRefreshStatusNotification(String testCaseId, String testCaseName, String isMFA, String key, String updateItemAcct, String triggerCacheRefresh, 
			String statsEntry, String status, String state, String statusUpdate, String StateUpdate, String datasetAttrId, String priority, String enabled, String filePath, String providerId,
			String dagSiteUserName, String dagSitePassword, String dataSetTemplateFile
) {
		commonUtils.isTCEnabled(enabled, testCaseName);
		System.out.println("*******TestCase : " + testCaseName + " & testCaseId : " + testCaseId + "*******");
		// Creating a new user.
		System.out.println("User Registration Started");
		String pendingTrxUserName = "TestYSLSdg"+"cacheref" + System.currentTimeMillis();
		String pendingTrxPassword = "TEST@123";

		User userInfo = User.builder().build();
		userInfo.setUsername(pendingTrxUserName);
		userInfo.setPassword(pendingTrxPassword);
		// User Registration
		EnvSession commonEnvObj = sessionHelper.getSessionObjectByUserRegistration(userInfo);
				
		
		System.out.println("New userSession created is::" + commonEnvObj.getUserSession());
		try {
			//Add account
			if(isMFA.equalsIgnoreCase("TRUE")) {
				System.out.println("Adding an MFA account for site ID " + providerId);
				List<AdditionalDataSet> dataSet = accountsHelper.getAdditionalDataSet(filePath + dataSetTemplateFile);
				sdgHelper.providerAccountId=sdgHelper.addMfaProviderAccountSdg(0, dagSiteUserName, 0, dagSitePassword, providerId, dataSet, commonEnvObj, Constants.DATA_SET, "123456", "Texas", "w3schools");
			}
			else {
				System.out.println("Adding a nonMFA account for site ID " + providerId);
			List<AdditionalDataSet> dataSet = accountsHelper.getAdditionalDataSet(filePath + dataSetTemplateFile);
			sdgHelper.providerAccountId = sdgHelper.addProviderAccountSdg(null, null, dagSiteUserName, dagSitePassword,
					providerId, dataSet, commonEnvObj, "fieldarray", Constants.DATA_SET);
			//Capture cacheItemId and itemAccountId
			String cacheItemId = dbHelper.getValueFromDB(DbQueries.GET_CACHE_ITEM_ID + Long.toString(sdgHelper.providerAccountId), "CACHE_ITEM_ID").toString();
			String itemAccountId= dbHelper.getValueFromDB(DbQueries.GET_ITEM_ACCOUNT_ID + cacheItemId, "ITEM_ACCOUNT_ID").toString();
			switch(key) {
			case "Inactive":
				System.out.println("Updating item account table with inactive status");
				String query=DbHelper.UADATE_ITEMACCT_INACTIVE + itemAccountId;
				dbHelper.executeUpdate(query);
				break;
			case "Closed":
				System.out.println("Updating item account table with closed status");
				String query1=DbHelper.UADATE_ITEMACCT_CLOSED + itemAccountId;
				dbHelper.executeUpdate(query1);
				break;
			case "Custom":
				System.out.println("Updating cacheinfo table with custom value");
				String query2=DbHelper.UADATE_CACHEINFO_CUSTOM + cacheItemId;
				dbHelper.executeUpdate(query2);
				break;
			default:
				System.out.println("Key didnot match" + key);
				break;
			}
			
			}
			
			//Capture cacheItemId and itemAccountId
			String cacheItemId = dbHelper.getValueFromDB(DbQueries.GET_CACHE_ITEM_ID + sdgHelper.providerAccountId, "CACHE_ITEM_ID").toString();
			String itemAccountId= dbHelper.getValueFromDB(DbQueries.GET_ITEM_ACCOUNT_ID + cacheItemId, "ITEM_ACCOUNT_ID").toString();
			//Get the attribute id and map to a list
			Map<String, List<String>> map1 = new HashMap<>();
			List<String> list = new ArrayList<>();
			list.add(datasetAttrId);
			map1.put(cacheItemId, list);
			
			//Update status and state as specified in the csv
			if(updateItemAcct.equalsIgnoreCase("TRUE")) {
				System.out.println("Updating item account table");
				updateItemAccountTable(cacheItemId,statusUpdate,StateUpdate);
			}
			//Trigger cache refresh
			if(triggerCacheRefresh.equalsIgnoreCase("TRUE")) {
				boolean istriggered= cache.triggerPFMCacheRefresh(Long.toString(sdgHelper.providerAccountId),  map1);
				if(istriggered) {
					System.out.println("Cache refresh triggered successfully");
				}
				else {
					System.out.println("Cache refresh is not triggered");
					//Assert.fail("cache refresh is not triggered");
				}
			}
			
			//Verify status and state in item account table
			System.out.println("Verifying itemaccount table");
			Thread.sleep(20000);
			assertItemAccountTable(cacheItemId,status,state);
			
			//Verify status and state in refresh stats table
			System.out.println("Verifying stats table");
			if(statsEntry.equalsIgnoreCase("TRUE")) {
				assertRefrStatsTableAfterUpdate(itemAccountId,status,state);
			}
			else {
				assertRefrStatsTblWithoutUpdate(itemAccountId);
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
			Assert.fail("failed to execute " + testCaseName + " test case due to " + e.getMessage()+"LineNumber:"+e.getStackTrace()[0].getLineNumber());
		}
		// Unregister user
		UserUtils userUtils = new UserUtils();
		Response res = userUtils.unRegisterUser(commonEnvObj);
	
	
		
	}
	
	/**
	 * Asserting values in Item Account table
	 * @param cacheItemId
	 * @param refreshNotifyId
	 * @param refreshEligibilityId
	 * @throws Exception
	 */
	private void assertItemAccountTable(String cacheItemId, String refreshNotifyId, String refreshEligibilityId) throws Exception {
		
		Assert.assertTrue(dbHelper.getValueFromDB(DbHelper.GET_REFRESHNOTIFYID_ITEMACCT + cacheItemId, "refresh_notify_status_id").toString().equalsIgnoreCase(refreshNotifyId), "refresh_notify_status_id is not matching in item account table");
		Assert.assertTrue(dbHelper.getValueFromDB(DbHelper.GET_REFRESHELIGIBILITYID_ITEMACCT + cacheItemId, "refresh_eligibility_state_id").toString().equalsIgnoreCase(refreshEligibilityId), "refresh_eligibility_state_id is not matching in item account table");
		Assert.assertTrue(!dbHelper.getValueFromDB(DbHelper.GET_REFRESHELIGIBILITYTIME_ITEMACCT + cacheItemId, "refresh_eligibility_evl_time").toString().equalsIgnoreCase("0"), "refresh_eligibility_evl_time is not matching in item account table");
		Assert.assertTrue(dbHelper.getValueFromDB(DbHelper.GET_REFRESHELIGIBILITYTIME_ITEMACCT + cacheItemId, "refresh_eligibility_evl_time").toString()!=null, "refresh_eligibility_evl_time is not matching in item account table");
	}
	
	/**
	 * Asserting values in Refresh Stats table
	 * @param itemAcctId
	 * @param refreshNotifyId
	 * @param refreshEligibilityId
	 * @throws Exception
	 */
	private void assertRefrStatsTableAfterUpdate(String itemAcctId, String refreshNotifyId, String refreshEligibilityId) throws Exception {
		
		Assert.assertTrue(dbHelper.getValueFromDB(DbHelper.GET_REFRESHNOTIFYID_REFRESHSTATS + itemAcctId, "refresh_notify_status_id").toString().equalsIgnoreCase(refreshNotifyId), "refresh_notify_status_id is not matching in Refresh stats table");
		Assert.assertTrue(dbHelper.getValueFromDB(DbHelper.GET_REFRESHELIGIBILITYID_REFRESHSTATS + itemAcctId, "refresh_eligibility_state_id").toString().equalsIgnoreCase(refreshEligibilityId), "refresh_eligibility_state_id is not matching in Refresh stats table");

	}
	
	/**
	 * Asserting values in Refresh Stats table when no entry exists
	 * @param itemAcctId
	 * @throws Exception
	 */
	private void assertRefrStatsTblWithoutUpdate(String itemAcctId) throws Exception {
		
		rs = dbHelper.getResultSet(conn,DbHelper.GET_REFRESHNOTIFYID_REFRESHSTATS + itemAcctId);
		Assert.assertTrue(rs.next()==false, "data present in Refresh Stats table");	
	}
	
	/**
	 * Updating item account table with specific status and state id for a specific cache Item Id
	 * @param cacheItemId
	 * @param refreshNotifyId
	 * @param refreshEligibilityId
	 */
	private void updateItemAccountTable(String cacheItemId, String refreshNotifyId, String refreshEligibilityId) {
		String query= "update item_account set refresh_notify_status_id ="+refreshNotifyId+" , refresh_eligibility_state_id ="+refreshEligibilityId+" where cache_item_id="+cacheItemId;
		dbHelper.executeUpdate(query);
		
	}
	
	@AfterClass
	public void closeConnection() throws Exception {	
		dbHelper.closeConnectionStatementResultSet(conn, null, rs);	
	}
	}
