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

package com.yodlee.yodleeApi.Budget;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.databene.benerator.anno.Source;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.JSONPaths;
import com.yodlee.yodleeApi.constants.JsonPath;
import com.yodlee.yodleeApi.helper.BudgetHelper;
import com.yodlee.yodleeApi.helper.UserHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.pojo.User;
import com.yodlee.yodleeApi.utils.apiUtils.BudgetUtils;
import com.yodlee.yodleeApi.utils.apiUtils.GroupUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;
import io.restassured.response.Response;

public class DeleteBudget extends Base {
	public static Long providerAccountId = null;
	String userSession = "";
	EnvSession sessionObj = null;
	String userName = "BUDGETYSL" + System.currentTimeMillis();
	Configuration configurationObj = Configuration.getInstance();
	UserHelper userHelper = new UserHelper();
	JsonAssertionUtil jsonAssertionutil = new JsonAssertionUtil();
	ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
	CommonUtils commonUtils = new CommonUtils();
	BudgetUtils budgetUtils = new BudgetUtils();
	GroupUtils groupUtils = new GroupUtils();
	BudgetHelper budgetHelper = new BudgetHelper();

	@BeforeClass(alwaysRun = true)
	public void createUser() throws ParseException, SQLException {
		System.out.println("******STARTING***********");
		User userInfo = User.builder().build();
		userInfo.setUsername(userName);
		userInfo.setPassword("Test@123");
		sessionObj = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		userHelper.getUserSession(userInfo, sessionObj);
		long providerId = 16441;
		providerAccountId = providerId;
		Response response = providerAccountUtils.addProviderAccountStrict(providerId, "fieldarray",
				"renuka21.site16441.2", "site16441.2", sessionObj);
		providerAccountId = response.jsonPath().getLong(JSONPaths.PROVIDER_ACC_ID);
		System.out.println("providerAccountId1::::===" + providerAccountId);
	}


	@Test(enabled = true, dataProvider = "feeder", priority = 1)
	@Source("\\TestData\\CSVFiles\\Budget\\DeleteBudget.csv")
	public void deleteBudget(String TestCaseId, String TestCase, String groupName, String CategoryType,
			String CategoryId, String Amount, String Currency, String CategoryIdQueryParam,String GroupIdQueryParam,
			String CategoryTypeQueryParam, String ContainerTypeId,String ErrorCode,String ExpectedErrorMsg,String ExpectedCategoryType,String ExpectedCategoryId,String Enabled) {
		commonUtils.isTCEnabled(Enabled, TestCaseId);	

		String []expectedCategoryTypes=ExpectedCategoryType.split(",");
		String []ExpectedCategoryIds=ExpectedCategoryId.split(",");

		List<Integer> categoryIds = null;
		if (CategoryId != null && !CategoryId.isEmpty()) {
			categoryIds = Stream.of(CategoryId.split(",")).map(Integer::parseInt).collect(Collectors.toList());
		}

		List<String> categoryTypes = null;
		if (CategoryType != null && !CategoryType.isEmpty()) {
			categoryTypes = Arrays.asList(CategoryType.split(","));
		}

		List<Integer> itemAccountId = budgetHelper.getItemAccountIds(userName, ContainerTypeId);

		HttpMethodParameters bodyParam = BudgetHelper.createGroupBodyParam(groupName, itemAccountId);
		Response groupResponse = groupUtils.createGroup(bodyParam, sessionObj);
		String groupId = groupResponse.jsonPath().getString(JsonPath.budgetGroupId);

		if(!GroupIdQueryParam.isEmpty()) {
			groupId=GroupIdQueryParam;	
		}
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();

		if (CategoryType.isEmpty()) {
			String catIdBodyReqParam = budgetHelper.CreateBudgetAtCatData(groupId, categoryIds, Amount, Currency);
			httpMethodParameters.setBodyParams(catIdBodyReqParam);
			budgetUtils.createBudget(httpMethodParameters, sessionObj);
		} else if (CategoryId.isEmpty()) {
			String catTypeBodyReqParam = budgetHelper.CreateBudgetAtCatType(groupId, categoryTypes, Amount, Currency);
			httpMethodParameters.setBodyParams(catTypeBodyReqParam);
			budgetUtils.createBudget(httpMethodParameters, sessionObj);
		} else {
			String catDataAndCatTypeBodyReqParam = budgetHelper.CreateCatDataAndCatType(groupId, categoryTypes,
					categoryIds, Amount, Currency);
			httpMethodParameters.setBodyParams(catDataAndCatTypeBodyReqParam);
			budgetUtils.createBudget(httpMethodParameters, sessionObj);
		}

		HttpMethodParameters httpMethodParameters1 = HttpMethodParameters.builder().build();
		HashMap<String, Object> getGoalQueryParam = new HashMap<>();
		getGoalQueryParam.put("groupId", groupId);

		if (CategoryIdQueryParam.isEmpty()) {
			getGoalQueryParam.put("categoryType", CategoryTypeQueryParam);
		} else if (CategoryTypeQueryParam.isEmpty()) {
			getGoalQueryParam.put("categoryId", CategoryIdQueryParam);
		} else {
			getGoalQueryParam.put("categoryType", CategoryTypeQueryParam);
			getGoalQueryParam.put("categoryId", CategoryIdQueryParam);
		}

		httpMethodParameters1.setQueryParams(getGoalQueryParam);
		Response deletBudgetResp=budgetUtils.deleteBudget(httpMethodParameters1, sessionObj);

		if(deletBudgetResp.getStatusCode()!=204) {
			JSONObject Obj = new JSONObject(deletBudgetResp.asString());
			Assert.assertEquals(ErrorCode, Obj.get("errorCode"));
			Assert.assertEquals(ExpectedErrorMsg, Obj.get("errorMessage"));	
		}else {
			assertEquals(deletBudgetResp.getStatusCode(),204);

			HttpMethodParameters httpMethodParameters2 = HttpMethodParameters.builder().build();
			HashMap<String, Object> getBudgetDetailQueryParam = new HashMap<>();
			getBudgetDetailQueryParam.put("groupId", groupId);
			getBudgetDetailQueryParam.put("include", "categoryTypeData,categoryData");

			httpMethodParameters2.setQueryParams(getBudgetDetailQueryParam);

			Response getBudgetDetails=budgetUtils.getBudget(httpMethodParameters2, sessionObj);	

			JSONObject responseObj = new JSONObject(getBudgetDetails.asString());
			JSONArray budgetArray = responseObj.getJSONArray("budget");

			if (CategoryId.isEmpty()) {	
				JSONObject budgetObject = budgetArray.getJSONObject(0);
				JSONArray categoryTypeDataObject = budgetObject.getJSONArray("categoryTypeData");
				for(int i=0;i<categoryTypeDataObject.length();i++) {
					JSONObject categoryTypeArray = categoryTypeDataObject.getJSONObject(i);
					assertEquals(categoryTypeArray.get("categoryType"), expectedCategoryTypes[i]);
				}	

			} else if (CategoryType.isEmpty()) {
				JSONObject budgetObject = budgetArray.getJSONObject(0);
				JSONArray categoryTypeDataObject = budgetObject.getJSONArray("categoryData");
				for(int i=0;i<categoryTypeDataObject.length();i++) {
					JSONObject categoryIdArray = categoryTypeDataObject.getJSONObject(i);
					assertTrue(categoryIdArray.get("categoryId").toString().contains(ExpectedCategoryIds[i]));
				}	
			} else {
				JSONObject budgetCategoryDataObject = budgetArray.getJSONObject(0);
				JSONArray categoryTypeDataObject = budgetCategoryDataObject.getJSONArray("categoryTypeData");
				for(int i=0;i<categoryTypeDataObject.length();i++) {
					JSONObject categoryTypeArray = categoryTypeDataObject.getJSONObject(i);
					assertEquals(categoryTypeArray.get("categoryType"), expectedCategoryTypes[i]);
				}	
				JSONArray categoryDataObject =  budgetCategoryDataObject.getJSONArray("categoryData");
				for(int i=0;i<categoryDataObject.length();i++) {
					JSONObject categoryIdArray = categoryDataObject.getJSONObject(i);
					assertTrue(categoryIdArray.get("categoryId").toString().contains(ExpectedCategoryIds[i]));
				}	

			}
		}
	}

}
