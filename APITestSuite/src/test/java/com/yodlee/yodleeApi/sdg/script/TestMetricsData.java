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

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.databene.benerator.anno.Source;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.yodlee.DBHelper;
import com.yodlee.taas.annote.Info;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.constants.Constants;
import com.yodlee.yodleeApi.database.DbQueries;
import com.yodlee.yodleeApi.helper.SessionHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.User;
import com.yodlee.yodleeApi.sdg.ProcessSdg;
import com.yodlee.yodleeApi.sdg.SdgHelper;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

/**
 * @author JAgan Nath (TIPS)--> Revised by Rahul Kumar
 *
 */
public class TestMetricsData extends Base {

	Connection conn = null;

	@BeforeClass
	public void openHook() throws IOException {
		DBHelper dbHelper = new DBHelper();
		conn = dbHelper.getDBConnection();
	}

	@Test(dataProvider = "feeder", enabled = true)
	@Info(userStoryId = "B-32142")
	@Source("\\TestData\\CSVFiles\\Sdg\\MetricsData.csv")
	public void testMetrics(String usTestcaseId, String tcId, String tcName, String sequence, String sequenceFilePath,
			String sequenceResFile, String subrand, String cobrand, String enabled, String priority,
			String loginFormType) throws Exception {

		CommonUtils commonUtils = new CommonUtils();
		SessionHelper sessionHelper = new SessionHelper();
		System.out.println("################ Execution of Test case :" + tcName + "#####################");
		commonUtils.isTCEnabled(enabled, tcName);

		// Creating a new user.
		System.out.println("User Registration Started");
		String metricUserName = "TestYSLSdg" + "Metric" + System.currentTimeMillis();
		String metricPassword = "Metric@123";

		User userInfo = User.builder().build();
		userInfo.setUsername(metricUserName);
		userInfo.setPassword(metricPassword);
		userInfo.setLocale(Constants.LOCALE_US);

		try {

			// User Registration
			EnvSession envSessionObj = sessionHelper.getSessionObjectByUserRegistration(userInfo);

			// Executing sdg Test case
			String xmlFile = sequenceFilePath + sequenceResFile;
			ProcessSdg processSdg = new ProcessSdg(xmlFile);
			processSdg.processSdgXml(sequence, loginFormType, envSessionObj);
			String memSiteAccId = Long.toString(SdgHelper.providerAccountMap.get("1"));
			checkMetrics(memSiteAccId);

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to execute userRegistraion() case due to : " + e);
		}

	}

	/**
	 * This method is used internally to check metrics.
	 * 
	 * @param memSiteAccId
	 *            it is providerAccountId
	 * @throws SQLException
	 */
	private void checkMetrics(String memSiteAccId) throws SQLException {

		DBHelper dbHelper = new DBHelper();
		String query = DbQueries.GET_METRICS_TABLE_DATA + memSiteAccId + "))";

		ResultSet resultset = dbHelper.getResultSet(conn, query);

		int sumInfoId = 0;
		int errorCode = 0;

		while (resultset != null && resultset.next()) {

			String query2 = DbQueries.GET_SUM_INFO_ID + resultset.getString(17) + ")";
			ResultSet resultset2 = dbHelper.getResultSet(conn, query2);

			while (resultset2 != null && resultset2.next()) {
				sumInfoId = 0;
				if (resultset2.getString(1) != null)
					sumInfoId = Integer.parseInt(resultset2.getString(1));
				if (resultset2.getString(2) != null)
					errorCode = Integer.parseInt(resultset2.getString(2));
			}

			System.out.println(resultset.getString(1) + " " + resultset.getString(2));
			Assert.assertNotNull(resultset.getString(1));
			Assert.assertNotNull(resultset.getString(2));
			Assert.assertNotNull(resultset.getString(3));
			Assert.assertNotNull(resultset.getString(4));
			Assert.assertNotNull(resultset.getString(5));
			Assert.assertNotNull(resultset.getString(6));
			Assert.assertNotNull(resultset.getString(7));
			Assert.assertNotNull(resultset.getString(8));
			Assert.assertNotNull(resultset.getString(9));
			Assert.assertNotNull(resultset.getString(10));
			Assert.assertNotNull(resultset.getString(11));
			Assert.assertNotNull(resultset.getString(12));

			Long reqXMLPostTime = Long.parseLong(resultset.getString(2));
			Long dcsReqRecTime = Long.parseLong(resultset.getString(3));
			Long dcsReqGathSentTime = Long.parseLong(resultset.getString(4));
			Long gathReqRecTime = Long.parseLong(resultset.getString(5));
			Long gathSentResTime = Long.parseLong(resultset.getString(6));
			Long dcsResRecTime = Long.parseLong(resultset.getString(7));
			Long dcsResDbfSentTime = Long.parseLong(resultset.getString(8));
			Long dbfRecResTime = Long.parseLong(resultset.getString(9));
			Long dbfEndTime = Long.parseLong(resultset.getString(10));
			Long refeshStartTime = Long.parseLong(resultset.getString(13));
			Long txnProcessingTime = Long.parseLong(resultset.getString(14));
			String uniqueTrackingId = resultset.getString(16);

			Assert.assertTrue(dcsReqRecTime > reqXMLPostTime, "reqXMLPostTime is greater than dcsReqRecTime");
			Assert.assertTrue(gathReqRecTime > dcsReqGathSentTime, "dcsReqGathSentTime is greater than gathReqRecTime");
			Assert.assertTrue(dcsResRecTime > gathSentResTime, "gathSentResTime is greater than dcsResRecTime");
			Assert.assertTrue(dbfRecResTime > dcsResDbfSentTime, "dcsResDbfSentTime is greater than dbfRecResTime");

			// Validating computational columns REQ_XML_GENERATION, DBF_TIME,
			// END_TO_END_TIME
			Long dbfTime = dbfEndTime - dbfRecResTime;
			Assert.assertEquals(Long.parseLong(resultset.getString(11)), dbfTime.longValue());
			Long reqXmlGenTime = reqXMLPostTime - refeshStartTime;
			Assert.assertEquals(Long.parseLong(resultset.getString(1)), reqXmlGenTime.longValue());
			Assert.assertEquals(Long.parseLong(resultset.getString(11)), dbfTime.longValue());
			if (refeshStartTime != null) {
				Long endToEndTime = Long.parseLong(resultset.getString(10)) - Long.parseLong(resultset.getString(13));
				Assert.assertEquals(Long.parseLong(resultset.getString(12)), endToEndTime.longValue());
			}

			// validating newly added columns
			if (errorCode == 0) {
				Assert.assertNotNull(uniqueTrackingId);
			}
			if (sumInfoId == 20549) {
				Assert.assertNotNull(resultset.getString(15));
			}
			Assert.assertTrue(txnProcessingTime < dbfTime, "Txn_processing_time is greater than DBF Time");

		}

		System.out.println("Testcase Passed:Metrics data has been uploaded successfully");
	}

	@AfterClass
	public void shutDownHook() throws IOException {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
