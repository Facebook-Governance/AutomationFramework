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

package com.yodlee.yodleeApi.Providers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.sql.Statement;


import org.databene.benerator.anno.Source;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.Constants;
import com.yodlee.DBHelper;
import com.yodlee.yodleeApi.helper.ProvidersHelper;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;

public class GetProviderODataCompare extends Base {

	public static final String oData_CSV3 = "\\TestData\\CSVFiles\\oData\\CountAPI.csv";
	
	DBHelper dbHelper = new DBHelper();
	
	CommonUtils commonUtils=new CommonUtils();

	@Test(dataProvider = Constants.FEEDER, alwaysRun = true, priority = 45)
	@Source(oData_CSV3)
	public void validateCount(String tcID, String tcName, String priority, String name, String capability,
			String dataSet, String query, String top, String enabled) throws Exception {
		
		commonUtils.isTCEnabled(enabled, tcID);
		ProviderUtils providerUtils = new ProviderUtils();
		ProvidersHelper providersHelper = new ProvidersHelper();
		
		
		LinkedHashMap<String, Object> queryParam = providersHelper.createQueryParamsForGetProviders(priority, name, top, dataSet, capability);	
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setQueryParams(queryParam);
		
		Response getProviderResponse = providerUtils.getProvidersCount(httpMethodParameters, Configuration.getInstance().getCobrandSessionObj());
		System.out.println("My Json Response " + getProviderResponse.toString());
		if(Configuration.getInstance().isHybrid()) {
			System.out.println("count in JSON is " + getProviderResponse.jsonPath().getString("provider.TOTAL.count"));
		}else {
			System.out.println("count in JSON is " + getProviderResponse.jsonPath().getString("provider.count"));
		}
		AssertAPICount(getProviderResponse, query);
	}

	public void AssertAPICount(Response response, String query) throws Exception {
	
		JSONObject jsonObject = new JSONObject(response.asString());
		JSONObject provider = jsonObject.getJSONObject("provider");
		System.out.println("JSON OBJECT is" + jsonObject);
		DBHelper dbHelper = new DBHelper();
		
		int count = getCountValueFromDB(query);

		System.out.println("GetProviderAPICount From DataBase is " + count);
		String count1="";
		if(Configuration.getInstance().isHybrid()) {
			count1 = response.jsonPath().getString("provider.TOTAL.count");
		}else {
			count1 = response.jsonPath().getString("provider.count");
		}
		// Assert.assertTrue(c.equals(count+""));

		System.out.println("GetProviderAPICount From api response " + count1);

		int i = Integer.parseInt(count1);

		System.out.println("count is " + i + "and " + count);

		Assert.assertEquals(i, count, "values are not equal");

		System.out.println("GetProviderAPICount is " + count1);

	}
	
	public int getCountValueFromDB(String query) throws Exception {
		DBHelper dbHelper = new DBHelper();

		Connection conn = dbHelper.getDBConnection();
		int columnValue = 0;
		Statement statement = null;
		System.out.println("Query is : " + query);
		try {

			statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(query);

			if (rs.next()) {

				columnValue = rs.getInt(1);
			}

		} finally {

			conn.close();

			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
		return columnValue;
	}
}
