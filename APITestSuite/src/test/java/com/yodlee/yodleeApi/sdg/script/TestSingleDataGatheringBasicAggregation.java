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

import java.util.List;

import org.databene.benerator.anno.Source;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.yodlee.taas.annote.ColumnDetails;
import com.yodlee.taas.annote.ConfigurationData;
import com.yodlee.taas.annote.ConfigurationParam;
import com.yodlee.taas.annote.ConfigurationParams;
import com.yodlee.taas.annote.Info;
import com.yodlee.taas.annote.ParamType;
import com.yodlee.taas.annote.SubFeature;
import com.yodlee.taas.annote.TableDetails;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.helper.SessionHelper;
import com.yodlee.yodleeApi.helper.ToMoveCommon;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.sdg.ProcessSdg;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

/**
 * @author Rahul Kumar
 *
 */
public class TestSingleDataGatheringBasicAggregation extends Base {

	public static final String testSingleDataGatheringBasicAggr = "\\TestData\\CSVFiles\\Sdg\\BASICAGG_HOLDER.csv";
	EnvSession envSessionObj = null;
	CommonUtils commonUtils = new CommonUtils();
	SessionHelper sessionHelper = new SessionHelper();
	Configuration configurationObj = Configuration.getInstance();
	double startTime = System.currentTimeMillis();

	@Source(testSingleDataGatheringBasicAggr)
	@Info(userStoryId = "B-07119", subfeature = {
			SubFeature.RTN_INVESTMENT }, userStoryName = "Routing Number for Investment container (scrape, store and return)")
	@Test(enabled = true, dataProvider = "feeder")
	@ConfigurationParams({
			@ConfigurationParam(keyId = "33", paramType = ParamType.SUM_INFO_PARAM, name = "COM.YODLEE.CORE.INSTANTACCOUNTVERIFICATION.END_SITE.RTN_SUPPORTED", value = "ON- for investment's sum_info"),
			@ConfigurationParam(keyId = "6330", paramType = ParamType.COB_PARAM, name = "COM.YODLEE.CORE.YSL.API_VERSION", value = "V2") })
	@ConfigurationData({ @TableDetails(name = "agent_info", columnData = {
			@ColumnDetails(name = "agent_info_id", value = "(select  max (agent_info_id) from agent_info)+1", dataType = "NUMBER(11)"),
			@ColumnDetails(name = "AGENT_NAME", value = "WellsfargoInvestments", dataType = "VARCHAR2(100)"),
			@ColumnDetails(name = "AGENT_IMPL_TYPE_ID", value = "5", dataType = "NUMBER(11)") }) })
	public void testSingleDataGatheringBasicAggr(String testCaseId, String tcId, String tcName, String sequence,
			String sequenceFilePath, String sequenceResFile, String Subrand, String Cobrand, String priority,
			String formType, String enabled) throws Exception {

		double startTimePerTestCase = System.currentTimeMillis();
		try {
			commonUtils.isTCEnabled(enabled, tcName);
			System.out.println("**********TestCase Id = " + testCaseId + "***********************");

			ToMoveCommon toMoveCommon = new ToMoveCommon();
			List<String> executionMode = toMoveCommon.getExecutionMode(Subrand, Cobrand);
			logger.info("Execution Mode in testSingleDataGatheringBasicAggr() : " + executionMode);

			// User Login to get newUserSession object
			envSessionObj = sessionHelper.getSessionObjByUserLogin(configurationObj.getUserObj().getUsername(),
					configurationObj.getUserObj().getPassword());
			System.out.println("New userSession created is::" + envSessionObj.getUserSession());

			// Calling sdg method to execute this Test class
			String xmlFile = sequenceFilePath + sequenceResFile;
			ProcessSdg processSdg = new ProcessSdg(xmlFile);
			processSdg.processSdgXml(sequence, formType, envSessionObj);

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to execute testSingleDataGatheringBasicAggr() case due to : " + e);

		} finally {
			long endTime = System.currentTimeMillis();
			System.out.println("Time taken for the execution of tcName " + tcName
					+ " of class TestSingleDataGatheringBasicAggregation is == " + (long) (endTime - startTimePerTestCase) / 60000
					+ " Minutes and " + ((int) ((((endTime - startTimePerTestCase) / 60000)
							- (long) ((endTime - startTimePerTestCase) / 60000)) * 60))
					+ " Seconds");
		}
	}

	@AfterClass
	public void totalRunTime() {
		long endTime = System.currentTimeMillis();
		System.out.println("Time taken for the execution for class TestSingleDataGatheringBasicAggregation is == "
				+ (long) (endTime - startTime) / 60000 + " Minutes and "
				+ ((int) ((((endTime - startTime) / 60000) - (long) ((endTime - startTime) / 60000)) * 60))
				+ " Seconds");
	}
}
