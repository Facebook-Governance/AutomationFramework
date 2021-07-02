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
 *******************************************************************************//*
package com.yodlee.yodleeApi.sdg.script;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.databene.benerator.anno.Source;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.yodlee.taas.annote.Feature;
import com.yodlee.taas.annote.FeatureName;
import com.yodlee.taas.annote.Info;
import com.yodlee.taas.annote.SubFeature;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.helper.AccountsHelper;
import com.yodlee.yodleeApi.helper.ProviderAccountsHelper;

import io.restassured.response.Response;

*//**
 * @author Done by TIPS portfolio-->Revised by Rahul Kumar
 *
 *//*
@Feature(featureName = { FeatureName.ACCOUNTS })
public class TestDisableCacheRefresh extends Base {

	public int sdgParamAclId = 7466;
	public static final String CACHE_REFRESH = "\\CSV Files\\cacherefresh\\cacherefresh_v1.1\\testCacheRefresh.csv";

	Long providerAccountId;
	Response getProvidersResponse;

	String dagSiteUserName = "jcbose_meerkat_Site.site16441.2";
	String dagSitePassword = "site16441.2";
	String dataSetTemplateFile = "\\TestData\\loginform\\template1.xml";

	private void updateAutoRefresh(String memSiteAcc, String enable) {

		String query;

		if (enable.equalsIgnoreCase("true")) {
			query = "update mem_site_acc set is_auto_refresh_enabled=1 , next_update=0 where mem_site_acc_id = "
					+ memSiteAcc;
		} else if (enable.equalsIgnoreCase("false")) {

			query = "update mem_site_acc set is_auto_refresh_enabled=0 , next_update=0 where mem_site_acc_id = "
					+ memSiteAcc;
		} else {
			query = "update mem_site_acc set is_auto_refresh_enabled=null , next_update=0 where mem_site_acc_id = "
					+ memSiteAcc;
		}

		DBHelper.executeUpdate(query);
	}

	private String getParamAclValue(int paramAclId) {

		DBHelper.connectToDb();

		String query = "select * from cobrand_acl_value where param_acl_id= " + paramAclId + " and cobrand_id = "
				+ InitialLoader.cobrandId;

		String isEnabled = DBHelper.getValueFromTable(query, "acl_value").toString();
		System.out.println("value is " + isEnabled);
		return isEnabled;
	}

	private long getNextUpdate(String memSiteAcc) {

		ResultSet rs = null;
		long nextUpdate = -1L;
		DBHelper.connectToDb();
		String query = "select next_update from mem_site_acc where mem_site_acc_id = " + memSiteAcc;
		try {
			rs = DBHelper.getResultSet(query);

			rs.next();
			nextUpdate = rs.getLong(1);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return nextUpdate;
	}

	@BeforeClass
	public void testSetUp() throws IOException {
		yslMethods = new YslApiMethods();
		String currentPath = new File(".").getCanonicalPath() + "\\src\\test\\resources";
		List<AdditionalDataSet> dataSet = getAdditionalDataSet(currentPath + dataSetTemplateFile);

		providerAccountId = yslMethods.addProvidersAccount(null, null, dagSiteUserName, dagSitePassword, "16441",
				dataSet, InitialLoader.cobSessionObj, "fieldarray");

		if (providerAccountId == 0) {
			throw new UnexpectedFunctionalityException("providerAccountId is not present after account addition");
		}
		System.out.println("provider acct id :" + providerAccountId);

	}

	@Test(dataProvider = Constants.FEEDER, alwaysRun = true, priority = 1)
	@Source(CACHE_REFRESH)
	@Info(subfeature = { SubFeature.GET_ACCOUNTS }, userStoryId = "")
	public void testFlagBasedCacheRefresh(String tcId, String tcName, String refresh_enable, String enabled)
			throws Exception {
		logger.info("********* Priority 1 *****************");
		CommonUtils.isTCEnabled(enabled, tcId);
		System.setOut(new TracingPrintStream(System.out));
		System.setErr(new TracingPrintStream(System.err));
		System.out.println("TestCase id is " + tcId + " TestCase Name is " + tcName);

		if (getParamAclValue(sdgParamAclId).equalsIgnoreCase("ON")) {
			updateAutoRefresh(providerAccountId.toString(), refresh_enable);

			long starttime = System.currentTimeMillis();
			long endtime = starttime + 5 * 60 * 1000;
			long nextUpdateInDB = -1L;

			if ((refresh_enable.equalsIgnoreCase("true") || refresh_enable.equalsIgnoreCase("null"))) {

				while (endtime > System.currentTimeMillis()) {
					Thread.sleep(30000);
					nextUpdateInDB = getNextUpdate(providerAccountId.toString());

					if (nextUpdateInDB != 0L) {
						Assert.assertTrue(nextUpdateInDB > 0, "Next update value is : " + nextUpdateInDB);

						break;
					} else if (endtime < System.currentTimeMillis()) {
						Assert.fail("Waited for 5 min and Next_update field is not updated");

					}

				}

			}

			else {

				while (endtime > System.currentTimeMillis()) {
					Thread.sleep(60000);
					nextUpdateInDB = getNextUpdate(providerAccountId.toString());
				}

				Assert.assertTrue(nextUpdateInDB == 0, "Next update value is : " + nextUpdateInDB);

			}
		}

		else {

			throw new SkipException("Skipping Test Case As SDG is not enabled ");

		}

	}

	@AfterClass
	public void shutDownHook() throws IOException {
		DBHelper.closeConnection();
	}

}*/