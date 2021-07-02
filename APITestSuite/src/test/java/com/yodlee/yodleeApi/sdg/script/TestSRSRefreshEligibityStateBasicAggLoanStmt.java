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
import java.util.List;

import org.databene.benerator.anno.Source;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.yodlee.DBHelper;
import com.yodlee.taas.annote.Info;
import com.yodlee.taas.annote.SubFeature;
import com.yodlee.taas.annote.TestGroup;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.helper.DbHelper;
import com.yodlee.yodleeApi.helper.SessionHelper;
import com.yodlee.yodleeApi.helper.ToMoveCommon;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.sdg.ProcessSdg;
import com.yodlee.yodleeApi.sdg.SdgHelper;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

/**
 * Test to ensure the new column SITE_REFRESH_STATS.REFRESH_ELIGIBILITY_STATE behavior is as expected
 * REFRESH_ELIGIBILITY_STATE behavior is derived based on the Config settings from the following tables
 *  (is_cache_refresh_suprt_en.attribute_cob_config and is_auto_refresh_enabled.mem_site_acc)
 * @author mkrishnasamy
 */
public class TestSRSRefreshEligibityStateBasicAggLoanStmt extends Base {

	public static final String testSRSRefreshEligibityStateBasicAggr = "\\TestData\\CSVFiles\\Sdg\\BASICAGG_HOLDER_SRS_LOAN.csv";
	public static final String GET_REFRESH_ELIGIBILITY_STATE_SRS = "select refresh_eligibility_state_id from site_refresh_stats where mem_site_acc_id=";
	public static final String GET_ENV_SPEC_CREFRESH_KEY_SETTING_LOAN_ST= "select is_cache_refresh_suprt_en, configs from attribute_cob_config where attribute_id in (7) and cobrand_id=10000004 and configs like '%bank%'";

    EnvSession envSessionObj = null;
	CommonUtils commonUtils = new CommonUtils();
	SessionHelper sessionHelper = new SessionHelper();
	Configuration configurationObj = Configuration.getInstance();
	double startTime = System.currentTimeMillis();

	@Source(testSRSRefreshEligibityStateBasicAggr)
	@Info(userStoryId = "B-32669", subfeature = {
			SubFeature.Account_addition}, userStoryName = "Support for offering Agg+IAV in SDG: Billing and service")
	@Test(enabled = true, groups = { TestGroup.REGRESSION }, dataProvider = "feeder")
	public void testSRSRefreshEligibityStateBasicAggrWithLoanStmts(String testCaseId, String tcId, String tcName, String sequence,
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
			String memSiteAccId = Long.toString(SdgHelper.providerAccountMap.get("1"));
			System.out.println("New mem_site_acc_id created is::" + memSiteAccId);
			//DB Assertion
			DbHelper dbHelper = new DbHelper();
			DBHelper dBHelper=new DBHelper(); 

			Connection con=dBHelper.getDBConnection();
			
			  int refresh_eligibility_state_id=0;
			  int default_refresh_state_id=0;
			 
			  //Get Default attribute cob config cache key setting for the attribute 7 (loan statement)
			  String defaultAttrquery = GET_ENV_SPEC_CREFRESH_KEY_SETTING_LOAN_ST;
			  String srsRefreshEligibilityCurrMSAQuery = GET_REFRESH_ELIGIBILITY_STATE_SRS+memSiteAccId;
			  
			  //new column refresh_eligibility_state_id value post account addition
			  ResultSet resultSet =dbHelper.getResultSet(con,srsRefreshEligibilityCurrMSAQuery);
			  while(resultSet.next()) {
				  refresh_eligibility_state_id = resultSet.getInt("refresh_eligibility_state_id");
			  }
              
			  //Iterate through the attribute_cob_config table
			  ResultSet resultSet1 =dbHelper.getResultSet(con,defaultAttrquery);
			  while(resultSet1.next()) {
				  default_refresh_state_id = resultSet1.getInt("is_cache_refresh_suprt_en"); 
				  //no need to iterate through all DB rows, even if one row found has cache enabled break for assertion
				  if(1==default_refresh_state_id) break; 
			  }
		      //Post account addition, ensure that the refresh_eligibility state id value is set as 6 or 3 based on the config parameters from the DB
			  if (1== default_refresh_state_id) {
				  Assert.assertTrue(refresh_eligibility_state_id==6, "refresh_eligibility_state_id value value is not 6 when cache is enabled for corresponding attributes");
			  } else {
				  Assert.assertTrue(refresh_eligibility_state_id==3, "refresh_eligibility_state_id value value is not 3 when cache is disabled for corresponding attributes");
			  }
				  
			  con.close();

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
