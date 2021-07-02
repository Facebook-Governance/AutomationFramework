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

/**
 * @author Guru Prasad
 * @Description To Test Update User Login for an Inactive User
 */

package com.yodlee.yodleeApi.DataExtracts;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.TimeZone;

import org.databene.benerator.anno.Source;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.Constants;
import com.yodlee.DBHelper;
import com.yodlee.yodleeApi.database.DbQueries;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.utils.apiUtils.DataExtractUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;

public class UpdateUserLogin extends Base {
	public final static String ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

	String userName; 
	CommonUtils commonUtils= new CommonUtils();
	Configuration configurationObj= Configuration.getInstance();
	DataExtractUtils dataExtractUtils= new DataExtractUtils();
	DBHelper dbHelper= new DBHelper();
	
	String fromDateAsString = new SimpleDateFormat(ISO_FORMAT).format(getUTCtime()); // To Convert time to Standard ISO_Format
	String toDateAsString = new SimpleDateFormat(ISO_FORMAT).format(getUTCtime()); // To Convert time to Standard ISO_Format
	Connection conn =null;
	@BeforeClass
	public void presetUp() {
		 conn = dbHelper.getDBConnection();
	}
	
	
	@Source("\\TestData\\CSVFiles\\DataExtracts\\updateUserLogin.csv")
	@Test(dataProvider = "feeder", alwaysRun = true)
	public void testAdd(String TcId, String TcName, String enabled) throws Exception {
		userName = configurationObj.getUserObj().getUsername();
		System.out.println(userName);
		commonUtils.isTCEnabled(enabled, TcId);
		System.out.println("*** Data Extract API ***");
		
		//dbHelper.getDBConnection();
		String query1 = "select MEM_ID from mem where login_name =" + "'" + userName + "'";
	//	String id = dbHelper.getValueFromDB(query1, "MEM_ID");
		String id = getValueFromDB(query1);

		System.out.print("mem_id = " + id + "\n");
		String query2 = "update mem set last_accessed = 1545580000 where mem_id = " + id;
		updateDb(query2);
		System.out.println("************updated*************");
		String query3 = "select last_accessed from mem where login_name = " + "'" + userName + "'";
	//	String last_accessed = dbHelper.getValueFromDB(query3, "last_accessed");
		
		String last_accessed = getValueFromDB(query3);

		System.out.println("Last accessed time is " + last_accessed + "\n");
		LinkedHashMap<String, String> queryParam = new LinkedHashMap<String, String>();
		System.out.println("data...::" + userName + "::" + fromDateAsString + "::" + toDateAsString);
		queryParam.put("loginName", userName);
		queryParam.put("fromDate", fromDateAsString);
		queryParam.put("toDate", toDateAsString);
		for (int count = 0; count < 2; count++) {				
					HttpMethodParameters httpParams= HttpMethodParameters.builder().build();
					httpParams.setQueryParams(queryParam);
					
			Response response = dataExtractUtils.getUserData(httpParams, configurationObj.getCobrandSessionObj());
				}
		String query4 = "select param_value from cob_param where param_key_id = 6354 and cobrand_id ="
				+ configurationObj.getCobrandObj().getCobrandId();
		
		
//		String param_value = dbHelper.getValueFromDB(query4, "param_value");
		
		String param_value = getValueFromDB(query4);

		System.out.println("The Param_Value is = " + param_value);

		if (Boolean.parseBoolean(param_value) == true) {
			String query5 = "Select channel_type_id from user_login_history where mem_id =" + id
					+ " and channel_type_id is not null order by channel_type_id desc";
	//		String channel_type_id = dbHelper.getValueFromDB(query5, "channel_type_id");
			
			String channel_type_id = getValueFromDB(query5);

			
			System.out.println("The Channel_Type_id is = " + channel_type_id);
			if (Integer.parseInt(channel_type_id) == 4) {
				//System.out.println("Entry made in User Login History with Channel_Type_id = 4 for an inactive user");
				Assert.assertTrue(true,"Test Case Passed");
			} else {
				Assert.fail("Test Case Failed");
			}
		} else {
			String query6 = "select extracts_login_history_id from extracts_login_history where mem_id =" + id;
	//		String extracts_login_history_id = dbHelper.getValueFromDB(query6, "extracts_login_history_id");
			
			String extracts_login_history_id = getValueFromDB(query6);

			System.out.println("The Entry is made in extracts login history table for Inactive User when PARAM KEY IS OFF and the Extracts_login_history_id is = "+extracts_login_history_id);
			if(extracts_login_history_id != null)
			Assert.assertTrue(true, "Entry made in Extracts login history");
			else
				Assert.fail("Test Case Failed!!!");
		}

	}
	
	public Date getUTCtime() {
		Date utcTime = null;
		SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
		// Local time zone
		SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
		// Time in GMT
		try {
			utcTime = dateFormatLocal.parse(dateFormatGmt.format(new Date()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return utcTime;
	}
	
	public void updateDb(String query) throws SQLException, ClassNotFoundException {
		 Statement stmt = null;
			if (conn != null) {
				stmt = conn.createStatement();
				stmt.executeUpdate(query);
				conn.commit();
			}
		
	}
	
	public String getValueFromDB(String query) throws Exception {
		String columnValue = "";
		Statement statement = null;
		ResultSet rs = null;
		System.out.println("Query is : " + query);
			statement = conn.createStatement();
			rs = statement.executeQuery(query);

			if (rs.next()) {

				columnValue = rs.getString(1);
			}
		
		return columnValue;
	}
	
	@AfterClass
	public void cleanUp() {
		
		if(conn!=null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

}
