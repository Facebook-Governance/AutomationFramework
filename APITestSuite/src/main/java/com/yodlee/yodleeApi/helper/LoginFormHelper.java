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

package com.yodlee.yodleeApi.helper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.yodlee.DBHelper;


public class LoginFormHelper {

	private Map<Integer, String> localeIdValueMap = new HashMap<>();
	private Map<Long, Map<String, Integer>> siteDBMap = new HashMap<>();
	public Map<String, List<Long>> localeSiteIdMap = new HashMap<>();
	public Map<Long, Integer> localeSptdMap = new HashMap<>();
	public Map<String, List<Long>> siteListLocaleIdMap = new HashMap<>();
	private Map<Integer, Long> rowIdSiteIdMap = new HashMap<>();
	public Map<Integer, List<Map<String, String>>> mcKeyMap = new HashMap<>();
	public Map<String, List<Map<Long, List<Map<Integer, String>>>>> finalMap = new HashMap<>();

	public void createLocaleIdSiteIdMap() {

		try {

			long startTime = System.currentTimeMillis();
			
			localeIdValueMap = getLocaleValueMap("select LOCALE_ID, LOCALE_KEY from locale");
			siteDBMap =getValueFromDataBase2("select SITE_ID,PRIMARY_LOCALE_ID from SITE");

			// Map that has primary locale value for each site and is used to
			// validate PrimaryLanguageISOCode.
			siteDBMap.forEach((siteId, value) -> {
				String localeValue = null;
				List<Long> siteList;
				try {
					localeValue = localeIdValueMap.get(value.get("PRIMARY_LOCALE_ID"));
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (localeSiteIdMap.containsKey(localeValue)) {
					siteList = localeSiteIdMap.get(localeValue);
					siteList.add(siteId);

				} else {
					siteList = new ArrayList<>();
					siteList.add(siteId);
				}

				// Use this to validate PrimaryLanguageISOCode
				localeSiteIdMap.put(localeValue, siteList);

			});

			// Take locale info from SITE_SPTD_LOCALE table to validate
			// languageIsoCode. TODO: Optimize the db method.
			localeSptdMap = getValueFromDataBase5("select SITE_ID, LOCALE_ID from SITE_SPTD_LOCALE");
			localeSptdMap.forEach((siteId, localeId) -> {

				String localeValue = localeIdValueMap.get(localeId);

				List<Long> siteList;
				if (siteListLocaleIdMap.containsKey(localeValue)) {
					siteList = siteListLocaleIdMap.get(localeValue);
					siteList.add(siteId);
				} else {
					siteList = new ArrayList<>();
					siteList.add(siteId);
				}
				// map containing { localeId , List of siteids}. Use this to
				// validate languageIsoCode
				siteListLocaleIdMap.put(localeValue, siteList);
			});

			// {row id : siteId} . TODO: Optimize the db method.
			rowIdSiteIdMap = getValueFromDataBase3("select SITE_ID, LOGIN_FIELD_ROW_ID from LOGIN_FIELD_MAP");

			// Used for validating the field id and values. TODO: Hardcoded now
			// as some junk values are present in DB which causes exception
			// while splitting the row id
			// ('com.yodlee.db.description.login_field_row.Thu Apr 05 12:57:44
			// IST 2012';). TODO: Optimize the db method.
			/*
			 * mcKeyMap = DBHelper.getValueFromDataBase4(
			 * "select LOCALE_ID, VALUE, MC_KEY from DB_MESSAGE_CATALOG where MC_KEY in ('com.yodlee.db.description.login_field_row.164924','com.yodlee.db.description.login_field_row.164925','com.yodlee.db.description.login_field_row.150862', 'com.yodlee.db.description.login_field_row.150863', 'com.yodlee.db.description.login_field_row.121253','com.yodlee.db.description.login_field_row.121254')"
			 * );
			 */

			mcKeyMap = getValueFromDataBase4(
					"select LOCALE_ID, VALUE, MC_KEY from DB_MESSAGE_CATALOG where MC_KEY like '%com.yodlee.db.description.login_field_row%'");

			mcKeyMap.forEach((localeId, mcKeyValue) -> {

				String localeValue = localeIdValueMap.get(localeId);
				Map<Long, List<Map<Integer, String>>> siteIdRowIdValueMap = new HashMap<>();
				List<Map<Integer, String>> listOfRowIdValueMap = new ArrayList<>();
				List<Map<Long, List<Map<Integer, String>>>> listOfSiteIdRowIdValueMap = new ArrayList<>();

				for (int i = 0; i < mcKeyValue.size(); i++) {

					String toBeSplitString = mcKeyValue.get(i).get("MC_KEY");
					System.out.println("String to be split: " + toBeSplitString);
					String[] splitStrings = StringUtils.split(toBeSplitString, ".");

					Integer rowId = 0;

					try {
						rowId = Integer.parseInt(splitStrings[splitStrings.length - 1]);
					} catch (Exception e) {
						System.out.println("Invalid row id found ...");
					}

					System.out.println("Row id: " + rowId);
					Long siteId = rowIdSiteIdMap.get(rowId);

					Map<Integer, String> rowIdValueMap = new HashMap<>();
					rowIdValueMap.put(rowId, mcKeyValue.get(i).get("VALUE"));

					if (siteIdRowIdValueMap.containsKey(siteId)) {
						listOfRowIdValueMap = siteIdRowIdValueMap.get(siteId);
						listOfRowIdValueMap.add(rowIdValueMap);
					} else {
						listOfRowIdValueMap = new ArrayList<>();
						listOfRowIdValueMap.add(rowIdValueMap);
					}

					siteIdRowIdValueMap.put(siteId, listOfRowIdValueMap);
					listOfSiteIdRowIdValueMap.add(siteIdRowIdValueMap);

				}

				// 16441 :: [{150862=Catalog}, {150863=Password}]
				// 19488 :: [{164924=UserMalaysia}, {164925=Password}]
				siteIdRowIdValueMap.forEach((siteId, rowIdValueMap) -> {

					System.out.println(
							"********************** SITE_ID : ROW ID/VALUE MAP ******************************");
					System.out.println(" " + siteId + " :: " + rowIdValueMap);
					System.out.println(
							"**********************************************************************************");
				});

				if (finalMap.containsKey(localeValue)) {
					listOfSiteIdRowIdValueMap = finalMap.get(localeValue);
					listOfSiteIdRowIdValueMap.add(siteIdRowIdValueMap);
				} else {
					listOfSiteIdRowIdValueMap = new ArrayList<>();
					listOfSiteIdRowIdValueMap.add(siteIdRowIdValueMap);
				}

				// Map that is used to validate field id and value.
				// {en_MY=[{19488=[{164924=UserMalaysia}, {164925=Password}]}],
				// en_US=[{5=[{121254=Username}, {121253=Password}],
				// 16441=[{150862=Catalog}, {150863=Password}]}],
				// en_SG=[{19488=[{164924=UserSingapore}, {164925=Password}]}]}
				finalMap.put(localeValue, listOfSiteIdRowIdValueMap);

			});

			System.out.println("Final Map :" + finalMap.toString());

			long endTime = System.currentTimeMillis();
			long timeTaken = endTime - startTime;

			System.out.println("Time taken " + timeTaken);

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	
	public Map<Integer, String> getLocaleValueMap(String query) {

		DBHelper dbHelper=new DBHelper();
				
		Connection conn=null;
		Statement statement=null;
		ResultSet rs = null;
		Map<Integer, String> localeIdValueMap = new HashMap<>();

		try {
			conn=dbHelper.getDBConnection();
			statement = conn.createStatement();
			rs = statement.executeQuery(query);
			int localeId = 0;
			String localeValue = null;

			while (rs.next()) {
				localeId = rs.getInt("LOCALE_ID");
				localeValue = rs.getString("LOCALE_KEY");
				localeIdValueMap.put(localeId, localeValue);
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			
			closeConnectionStatementResultSet(conn, statement, rs);
				
		}

		return localeIdValueMap;
	}


	public void closeConnectionStatementResultSet(Connection conn, Statement statement, ResultSet rs) {
		try {
			if(rs!=null)
				rs.close();
			if (statement != null) 
				statement.close();
			if(conn!=null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public Map<Long, Map<String, Integer>> getValueFromDataBase2(String query) {

		DBHelper dbHelper=new DBHelper();
		
		Connection conn=null;
		Statement statement=null;
		ResultSet rs = null;

		Map<Long, Map<String, Integer>> siteMap = new HashMap<>();

		try {
			conn=dbHelper.getDBConnection();
			statement = conn.createStatement();
			rs = statement.executeQuery(query);

			long siteId = 0;
			int primaryLocaleId = 0;

			while (rs.next()) {
				siteId = rs.getLong("SITE_ID");
				primaryLocaleId = rs.getInt("PRIMARY_LOCALE_ID");
				Map<String, Integer> mckeyMap = new HashMap<>();
				mckeyMap.put("PRIMARY_LOCALE_ID", primaryLocaleId);
				siteMap.put(siteId, mckeyMap);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			dbHelper.closeConnectionStatementResultSet(conn, statement, rs);
		}

		return siteMap;
	}
	

	
	public Map<Integer, Long> getValueFromDataBase3(String query) {

		DBHelper dbHelper=new DBHelper();
		
		Connection conn=null;
		Statement statement=null;
		ResultSet rs = null;

		Map<Integer, Long> localeMap = new HashMap<>();

		try {
			conn=dbHelper.getDBConnection();
			statement = conn.createStatement();
			rs = statement.executeQuery(query);

			int rowId = 0;
			long siteId = 0;

			while (rs.next()) {
				rowId = rs.getInt("LOGIN_FIELD_ROW_ID");
				siteId = rs.getLong("SITE_ID");
				localeMap.put(rowId, siteId);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			dbHelper.closeConnectionStatementResultSet(conn, statement, rs);
		}

		return localeMap;
	}
	
	public Map<Integer, List<Map<String, String>>> getValueFromDataBase4(String query) {

		DBHelper dbHelper=new DBHelper();
		
		Connection conn=null;
		Statement statement=null;
		ResultSet rs = null;
		Map<Integer, List<Map<String, String>>> localeMap = new HashMap<>();
		List<Map<String, String>> listOfMckeyValue = new ArrayList<>();

		try {
			conn=dbHelper.getDBConnection();
			statement = conn.createStatement();

			System.out.println(" QUERY is : " + query);
			rs = statement.executeQuery(query);

			int localeId = 0;
			String value = null;
			String mcKey = null;

			while (rs.next()) {
				localeId = rs.getInt("LOCALE_ID");
				value = rs.getString("VALUE");
				mcKey = rs.getString("MC_KEY");

				Map<String, String> mckeyMap = new HashMap<>();
				mckeyMap.put("VALUE", value);
				mckeyMap.put("MC_KEY", mcKey);

				if (localeMap.containsKey(localeId)) {
					listOfMckeyValue = localeMap.get(localeId);
					listOfMckeyValue.add(mckeyMap);
				} else {

					listOfMckeyValue = new ArrayList<>();
					listOfMckeyValue.add(mckeyMap);
				}

				localeMap.put(localeId, listOfMckeyValue);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			dbHelper.closeConnectionStatementResultSet(conn, statement, rs);
		}

		return localeMap;
	}

	public Map<Long, Integer> getValueFromDataBase5(String query) {


		DBHelper dbHelper=new DBHelper();
		
		Connection conn=null;
		Statement statement=null;
		ResultSet rs = null;

		Map<Long, Integer> localeMap = new HashMap<>();

		try {
			conn=dbHelper.getDBConnection();
			statement = conn.createStatement();
			rs = statement.executeQuery(query);

			long siteId = 0;
			int primaryLocaleId = 0;

			while (rs.next()) {
				siteId = rs.getLong("SITE_ID");
				primaryLocaleId = rs.getInt("LOCALE_ID");
				localeMap.put(siteId, primaryLocaleId);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			dbHelper.closeConnectionStatementResultSet(conn, statement, rs);
		}

		return localeMap;
	}

}
