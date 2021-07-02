/*******************************************************************************
 *
 * * Copyright (c) 201 Yodlee, Inc. All Rights Reserved.
 *
 * *
 *
 * * This software is the confidential and proprietary information of Yodlee, Inc.
 *
 * * Use is subject to license terms.
 *
 *******************************************************************************/
package com.yodlee.app.yodleeApi.SaveForGoal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.LinkedHashMap;

import org.databene.benerator.anno.Source;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.yodlee.DBHelper;
import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.JSONPaths;
import com.yodlee.yodleeApi.helper.SaveForGoalHelper;
import com.yodlee.yodleeApi.helper.UserHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.pojo.User;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.SFGUtils;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;

public class TestDeleteGoal extends Base{
	 public static Long providerAccountId = null;
	String loginName, password;
	String userSession="";
	EnvSession sessionObj=null;
	Configuration configurationObj= Configuration.getInstance();	
	UserUtils userUtils=new UserUtils();
	ProviderAccountUtils providerAccountUtils=new ProviderAccountUtils();
	CommonUtils commonUtils=new CommonUtils();
	SaveForGoalHelper saveForGoalHelper=new SaveForGoalHelper();
	UserHelper userHelper= new UserHelper();
	JsonAssertionUtil jsonAssertionutil=new JsonAssertionUtil();
	
	
	@BeforeClass(alwaysRun=true)
	public void addAccount()
	{
		System.out.println("initiated execution");
		User userInfo=User.builder().build();
		userInfo.setUsername("SFGYSL"+System.currentTimeMillis());
		userInfo.setPassword("Test@123");
		sessionObj = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		userHelper.getUserSession(userInfo, sessionObj);
		long providerId=16441;
		providerAccountId=providerId;
		Response response = providerAccountUtils.addProviderAccountStrict(providerId, "fieldarray",
				"getrecTrans.site16441.2", "site16441.2", sessionObj);
		providerAccountId = response.jsonPath().getLong(JSONPaths.PROVIDER_ACC_ID);
		System.out.println("providerAccountId1::::===" + providerAccountId);
		
	}
	@Test(enabled=true,dataProvider="feeder",priority=1)
	@Source("\\TestData\\CSVFiles\\SaveForGoal\\DeleteGoal.csv")
	public void deleteGoal(String TestCaseId,String TestCase,String Name,String CategoryID,String TargetAmount,
			String TargetAmountCurency,String RecurringAmount,String RecurringAmountCurrency,String Frequency,
			String StartDate,String TargetDate,String Status,String FundSetupStatus,String isFTSelected ,
			int httpStatus,String ResFile,String FilePath,String Enabled) throws Exception {
	    System.out.println(TestCaseId);
		commonUtils.isTCEnabled(Enabled, TestCaseId);
		SaveForGoalHelper sfg=new SaveForGoalHelper();
		 SFGUtils sfgUtils=new SFGUtils();
		
		String bodyParams= sfg.createGoalJson(Name, CategoryID, sfg.sfgDateFormate(StartDate), sfg.sfgDateFormate(TargetDate), RecurringAmount, RecurringAmountCurrency, TargetAmount, TargetAmountCurency, Frequency, Status, FundSetupStatus, isFTSelected,"");
        System.out.println(bodyParams);
       
        HttpMethodParameters httpMethodParameters=HttpMethodParameters.builder().build();
        httpMethodParameters.setBodyParams(bodyParams);
        Response response=sfgUtils.createGoal(httpMethodParameters, sessionObj);
		System.out.println("Response is : "+response);
		Long goalIdUpdate = response.jsonPath().getLong("goal[0].id") ;
		System.out.println(goalIdUpdate);
		LinkedHashMap<String, Object> pathParam =new LinkedHashMap<>(); 
		pathParam.put("goalIdUpdate", goalIdUpdate);
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setPathParams(pathParam);
		Response deleteResponse=sfgUtils.deleteGoal(httpParams, sessionObj);
		Assert.assertEquals(deleteResponse.getStatusCode(), httpStatus);
		
		  DBHelper dbHelper=new DBHelper(); 
		  Connection con=dbHelper.getDBConnection();
		
		  int is_deleted=0;
		  String query = "select user_goal_id,goal_name,goal_status_id,is_deleted from user_goal where user_goal_id="+goalIdUpdate;
		  ResultSet rs =dbHelper.getResultSet(con,query);
		  while(rs.next()) {
			   is_deleted = Integer.parseInt(rs.getString("is_deleted"));
			   
		  }
		  Assert.assertTrue(is_deleted==1);
		  con.close();
		 
		
}
	
	@Test(enabled=true,dataProvider="feeder",priority=2)
	@Source("\\TestData\\CSVFiles\\SaveForGoal\\DeleteGoalNegativeSenario.csv")
	public void deleteInvalidGoal(String TestCaseId,String TestCase,String GoalID,
			int httpStatus,String ResFile,String FilePath,String Enabled) throws Exception {
	    System.out.println(TestCaseId);
		commonUtils.isTCEnabled(Enabled, TestCaseId);
		SFGUtils sfgUtils=new SFGUtils();
		LinkedHashMap<String, Object> pathParam =new LinkedHashMap<>(); 
		pathParam.put("goalIdUpdate", GoalID);
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setPathParams(pathParam);
		Response deleteResponse=sfgUtils.deleteGoal(httpParams, sessionObj);
		JsonAssertionUtil jsonPath = new JsonAssertionUtil();
		jsonPath.assertResponse(deleteResponse, httpStatus, ResFile, FilePath);
}
}
