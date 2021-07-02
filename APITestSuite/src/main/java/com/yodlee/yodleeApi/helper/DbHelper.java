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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.Instant;
import org.testng.asserts.SoftAssert;

import com.yodlee.DBHelper;
import com.yodlee.yodleeApi.common.Configuration;

public class DbHelper {
	public static final String GET_REFRESHNOTIFYID_ITEMACCT = "select refresh_notify_status_id from item_account where cache_item_id=";
	public static final String GET_REFRESHELIGIBILITYID_ITEMACCT = "select refresh_eligibility_state_id from item_account where cache_item_id=";
	public static final String GET_REFRESHELIGIBILITYTIME_ITEMACCT = "select refresh_eligibility_evl_time from item_account where cache_item_id=";
	public static final String GET_REFRESHNOTIFYID_REFRESHSTATS = "select refresh_notify_status_id from REFRESH_ALERT_STATS where item_account_id=";
	public static final String GET_REFRESHELIGIBILITYID_REFRESHSTATS = "select refresh_eligibility_state_id from REFRESH_ALERT_STATS where item_account_id=";
	public static final String GET_WEBHOOKSUB_COBRAND = "select * from webhook_subscriptions where event_group_master_id=5 and is_deleted=0 and cobrandid=";
	public static final String UADATE_ITEMACCT_INACTIVE = "update item_account set ITEM_ACCOUNT_STATUS_ID = 2 where item_account_id =";
	public static final String UADATE_ITEMACCT_CLOSED = "update item_account set ITEM_ACCOUNT_STATUS_ID = 6 where item_account_id =";
	public static final String UADATE_CACHEINFO_CUSTOM = "update cache_info set is_custom = 1 where cache_item_id =";
	public static final String GET_PARAMVALUE_MSFNLN = "select PARAM_VALUE from cob_param where param_key_id=6076 AND cobrand_id ="; 
	public static final String GET_PARAMVALUE_MSLN = "select PARAM_VALUE from cob_param where param_key_id=6341 AND cobrand_id =";
	
	public static final String GET_SITE_DETAILS = "select * from site where site_id = ";
	public static final String GET_SUMINFO_DETAILS_ACCTYPES = " select * from sum_info_param_value where sum_info_param_key_id=57 and sum_info_id=32021 ";
	public static final String GET_ACC_TYPES = "select * from acct_type where acct_type_id= ";
	

	/**
	 * Checking if TDE is enabled for Loan or not
	 * 
	 * @param cobrandId
	 * @return
	 * @throws SQLException
	 */
	public boolean isTdeEnabledForLoan(Connection conn, long cobrandId) throws SQLException {
		String accountTypes = getSupportedAccountTypes(conn, cobrandId);
		if (accountTypes == null || accountTypes.trim().length() == 0)
			return false;
		ResultSet rs = null;
		Statement st = null;
		try {
			st = conn.createStatement();
			rs = st.executeQuery("select LOAN_TYPE from LOAN_TYPE");
			List<String> accountTypesList = Arrays.asList(accountTypes.split(","));
			while (rs.next()) {
				String type = rs.getString("LOAN_TYPE");
				if (accountTypesList.contains(type)) {
					return true;
				}
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null)
				rs.close();
			st.close();

		}
		return false;
	}

	/**
	 * This method is used to get supported account types for a particular cobrand.
	 * 
	 * @param cobrandId
	 * @return
	 * @throws SQLException
	 */
	public String getSupportedAccountTypes(Connection conn, long cobrandId) throws SQLException {
		ResultSet rs = null;
		Statement st = null;
		try {
			rs = getResultSet(conn, "select * from param_acl where acl_name='TDE_SUPPORTED_ACCOUNT_TYPES'");
			if (rs.next()) {
				long paramId = rs.getLong("PARAM_ACL_ID");
				String paramAclValue = rs.getString("ACL_VALUE");
				String cobrandAclValue = null, cobrandOverrideVal = null;
				Date cobrandValidUntil = null;
				rs.close();
				rs = null;
				st = conn.createStatement();
				rs = st.executeQuery("select * from cobrand_acl_value where param_acl_id=" + paramId
						+ " and cobrand_id=" + cobrandId);
				if (rs.next()) {
					cobrandAclValue = rs.getString("ACL_VALUE");
					cobrandValidUntil = rs.getDate("VALID_UNTIL");
					cobrandOverrideVal = rs.getString("OVERRIDE_VALUE");
				}

				String derivedValue = null;
				if (cobrandAclValue != null) {
					Date sysdate = new Date();
					if ((cobrandValidUntil != null) && sysdate.before(cobrandValidUntil)) {
						derivedValue = cobrandOverrideVal;
					} else {
						derivedValue = cobrandAclValue;
					}
				}

				if (derivedValue == null) {
					derivedValue = paramAclValue;
				}
				return derivedValue;

			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null)
				rs.close();
			closeStatement(st);
		}
		return null;
	}

	/**
	 * Checking if TDE is enabled for Investment for a particular cobrand.
	 * 
	 * @param cobrandId
	 * @return
	 * @throws SQLException
	 */
	public boolean isTdeEnabledForInvestment(Connection conn, long cobrandId) throws SQLException {
		String accountTypes = getSupportedAccountTypes(conn, cobrandId);
		if (accountTypes == null || accountTypes.trim().length() == 0)
			return false;

		ResultSet rs = null;
		Statement st = null;
		try {
			st = conn.createStatement();
			rs = st.executeQuery("select ACCT_TYPE from ACCT_TYPE");
			List<String> accountTypesList = Arrays.asList(accountTypes.split(","));
			while (rs.next()) {
				String type = rs.getString("ACCT_TYPE");
				if (accountTypesList.contains(type)) {
					return true;
				}
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null)
				rs.close();
			closeStatement(st);
		}
		return false;
	}

	/**
	 * Checking if specific config is enabled for a particular cobrand or not.
	 * 
	 * @param cobrandId
	 * @param aclName
	 * @param expectedVal
	 * @return
	 * @throws SQLException
	 */
	
	public HashMap<String,ArrayList<Long>> getContainersCacheItemIds(String mem_id) {
		DBHelper dbUtils = new DBHelper();
		Connection conn = dbUtils.getDBConnection();
		HashMap<String,ArrayList<Long>> sumInfoCacheItemMap = new HashMap<String,ArrayList<Long>>();
		ArrayList<Long> bankCacheItemIdsList = new ArrayList<Long>();
		ArrayList<Long> cardCacheItemIdsList = new ArrayList<Long>();
		ArrayList<Long> investmentCacheItemIdsList = new ArrayList<Long>();
		ArrayList<Long> billsCacheItemIdsList = new ArrayList<Long>();
		ArrayList<Long> insuranceCacheItemIdsList = new ArrayList<Long>();
		ArrayList<Long> loansCacheItemIdsList = new ArrayList<Long>();
		ArrayList<Long> milesCacheItemIdsList = new ArrayList<Long>();
		ArrayList<Long> taxCacheItemIdsList = new ArrayList<Long>();
		ResultSet resultSet = null;
		Statement st = null;
		String query = "select *from mem_item where mem_id = "+mem_id;
		try {
			st = conn.createStatement();
			resultSet = st.executeQuery(query);
			while (resultSet.next()) {
				int sumInfoId = resultSet.getInt("SUM_INFO_ID");
				Long cacheItemId = resultSet.getLong("CACHE_ITEM_ID");
				if(sumInfoId == 20559) {
					bankCacheItemIdsList.add(cacheItemId);
					sumInfoCacheItemMap.put("BANK", bankCacheItemIdsList);
				}				
				if(sumInfoId == 20561) {
					cardCacheItemIdsList.add(cacheItemId);
					sumInfoCacheItemMap.put("CARD", cardCacheItemIdsList);
				}
				if(sumInfoId == 20549) {
					investmentCacheItemIdsList.add(cacheItemId);
					sumInfoCacheItemMap.put("INVESTMENT", investmentCacheItemIdsList);
				}				
				if(sumInfoId == 20560) {
					billsCacheItemIdsList.add(cacheItemId);
					sumInfoCacheItemMap.put("BILL", billsCacheItemIdsList);
				}
				if(sumInfoId == 20562) {
					insuranceCacheItemIdsList.add(cacheItemId);
					sumInfoCacheItemMap.put("INSURANCE", insuranceCacheItemIdsList);
				}					 
				if(sumInfoId == 20563) {
					loansCacheItemIdsList.add(cacheItemId);
					sumInfoCacheItemMap.put("LOAN", loansCacheItemIdsList);
				}					 
				if(sumInfoId == 20564) {
					milesCacheItemIdsList.add(cacheItemId);
					sumInfoCacheItemMap.put("MILES", milesCacheItemIdsList);
				}					
				if(sumInfoId == 20565) {
					taxCacheItemIdsList.add(cacheItemId);
					sumInfoCacheItemMap.put("TAX", taxCacheItemIdsList);
				}
					 
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dbUtils.closeConnectionStatementResultSet(conn, st, resultSet);
		}
		
		return sumInfoCacheItemMap;
	}
	
	public boolean isTdeSpecificConfigEnabled(Connection conn, long cobrandId, String aclName, String expectedVal)
			throws SQLException {

		boolean flag = false;
		ResultSet resultSet = null;
		Statement st = null;
		try {
			st = conn.createStatement();
			resultSet = st.executeQuery("SELECT * FROM cobrand_acl_value WHERE COBRAND_ID = " + cobrandId
					+ " AND param_acl_id in (select param_acl_id from param_acl where acl_name='" + aclName + "')");

			if (resultSet.next()) {
				String value = resultSet.getString("ACL_VALUE");
				if (value.equalsIgnoreCase(expectedVal)) {
					flag = true;
				}
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if (resultSet != null)
				resultSet.close();
			closeStatement(st);
		}
		return flag;

	}

	/**
	 * Used to get Transaction details.
	 * 
	 * @param tableName
	 * @param whereClause
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> getTransactionDetails(Connection conn, String tableName, String whereClause)
			throws SQLException {

		Map<String, String> returnMap = new HashMap<>();
		ResultSet resultSet = null, rs = null;
		try {
			resultSet = getResultSet(conn,
					"SELECT merchant_id,source_merchant_id,meerkat_simple_desc, granular_category_id,"
							+ "meerkat_txn_type_id,transaction_type_id,meerkat_txn_sub_type_id from " + tableName
							+ " WHERE " + whereClause);
			while (resultSet.next()) {
				Long mTxnTypeId = resultSet.getLong("meerkat_txn_type_id");
				if (mTxnTypeId != null && mTxnTypeId > 0) {

					rs = getResultSet(conn,
							"select TYPE_NAME from meerkat_txn_type where meerkat_txn_type_id = " + mTxnTypeId);
					if (rs.next()) {
						returnMap.put("type", rs.getString("TYPE_NAME"));
					}

					rs.close();
					rs = null;
				} else {
					Long typeId = resultSet.getLong("transaction_type_id");
					if (typeId != null) {
						rs = getResultSet(conn,
								"select transaction_type from transaction_type where transaction_type_id = " + typeId);
						if (rs.next()) {
							returnMap.put("type", rs.getString("transaction_type"));
						}
						rs.close();
						rs = null;
					}
				}

				Long mTxnSubTypeId = resultSet.getLong("meerkat_txn_sub_type_id");
				if (mTxnSubTypeId != null) {
					rs = getResultSet(conn,
							"select TYPE_NAME from meerkat_txn_sub_type where meerkat_txn_sub_type_id = "
									+ mTxnSubTypeId);
					if (rs.next()) {
						returnMap.put("subType", rs.getString("TYPE_NAME"));
					}
					rs.close();
					rs = null;
				}
				String simpleDesc = resultSet.getString("meerkat_simple_desc");
				returnMap.put("simple", simpleDesc);

				String granCatId = resultSet.getString("granular_category_id");
				returnMap.put("granCatId", granCatId);

				String srcMerchantId = resultSet.getString("source_merchant_id");
				if (srcMerchantId != null) {
					rs = getResultSet(conn,
							"select merchant_name,longitude,latitude from meerkat_merchant where source_merchant_id = '"
									+ srcMerchantId + "'");
					if (rs.next()) {
						returnMap.put("merchantName", rs.getString("merchant_name"));
						returnMap.put("longitude", rs.getString("longitude"));
						returnMap.put("latitude", rs.getString("latitude"));
					}
					rs.close();
					rs = null;
				} else {
					String merchantId = resultSet.getString("source_merchant_id");
					if (merchantId != null) {
						rs = getResultSet(conn,
								"select merchant_name from merchant where merchant_id = '" + merchantId + "'");
						if (rs.next()) {
							returnMap.put("merchantName", resultSet.getString("merchant_name"));
						}
						rs.close();
						rs = null;
					}
				}
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if (resultSet != null)
				resultSet.close();
			if (rs != null)
				rs.close();
		}
		return returnMap;
	}

	/**
	 * Used to get Meerkat types. This changes are for Morgan Stanley Transactions
	 * TDE Testing starts
	 * 
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public List<String> getMeerkatTypes(Connection conn) throws SQLException {
		ResultSet resultSet = null;
		List<String> list = new ArrayList<>();
		Statement st = null;
		try {
			st = conn.createStatement();
			resultSet = st.executeQuery("select TYPE_NAME from meerkat_txn_type");
			while (resultSet.next()) {
				String type = resultSet.getString("TYPE_NAME");
				type = type.replaceAll(" ", "_").replaceAll("/", "_").toUpperCase();
				list.add(type);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if (resultSet != null)
				resultSet.close();
			closeStatement(st);
		}
		return list;

	}

	/**
	 * This is generic method to execute select query and return the value for the
	 * columnName passed.
	 * 
	 * @param query
	 * @param columnName
	 * @return
	 */
	public List<Integer> getValueFromTable(String query, String columnName) {

		// Object columnValue = null;
		Statement stmt = null;
		ResultSet rs = null;
		DBHelper dbHelper = new DBHelper();
		Connection conn = dbHelper.getDBConnection();
		List<Integer> listOfAttributeValues = new ArrayList<>();
		try {
			System.out.println("QUERY IS :::: " + query);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				listOfAttributeValues.add(rs.getInt(columnName));
			}
			for (int i = 0; i < listOfAttributeValues.size(); i++) {
				System.out.println("list is::" + listOfAttributeValues.get(i));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
		
				try {
					if (rs != null)
						rs.close();
					if (stmt != null)
						stmt.close();
					if (conn != null)
						conn.close();

				} catch (SQLException e) {
					
				}
		}
		return listOfAttributeValues;
	}

	/**
	 * This method is used to check if cache refresh is enabled or not. This is
	 * generally used in SDG for GET_ACCOUNTS to check if extra attribute
	 * "nextUpdateScheduled" is there in the API response or not.
	 * 
	 * @param attributeName
	 *            it is pat
	 * @return
	 * @throws SQLException
	 */
	public boolean getIsCacheRefreshEnabled(String attributeName) throws SQLException {

		System.out.println("Attribiute Name in getIsCacheRefreshEnabled() is :" + attributeName);
		DBHelper dbHelper = new DBHelper();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		boolean cacheRefreshEnabledFlag = false;
		Connection conn = null;

		try {
			conn = dbHelper.getDBConnection();

			if (Configuration.getInstance().isDbReadable()) {

				String query = "Select is_cache_refresh_suprt from dataset_attributes where attribute_name like ?";
				System.out.println("QUERY ==> " + query);
				stmt = conn.prepareStatement(query);

				stmt.setString(1, "%" + attributeName + "%");
				rs = stmt.executeQuery();

				System.out.println("Rs. " + rs.toString());

				if (rs.next()) {
					int value = rs.getInt("is_cache_refresh_suprt");
					if (value == 1) {
						cacheRefreshEnabledFlag = true;
						return cacheRefreshEnabledFlag;
					}
				}
			}
		} catch (Exception e) {
			System.err.println("Exception Inside getIsCacheRefreshEnabled::");
			e.printStackTrace();
		} finally {
			if (conn != null)
				conn.close();
				
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();

				
		}

		return cacheRefreshEnabledFlag;
	}

	/**
	 * This method is used to check if a particular site id is enabled or not.
	 * 
	 * @param conn
	 * @param siteId
	 * @param cobId
	 * @return
	 * @throws SQLException
	 */
	public boolean isSiteEnable(Connection conn, String siteId, String cobId) throws SQLException {
		boolean flag = true;
		ResultSet ids = null;
		ResultSet rs = null;
		try {
			if (siteId != null) {
				String sum_info_id = "select sum_info_id from sum_info where site_id=" + siteId + "";
				ids = getResultSet(conn, sum_info_id);
				String info_id = "";
				if (ids != null) {
					while (ids.next()) {
						info_id += ids.getString("SUM_INFO_ID") + ",";
					}
				}
				if (!info_id.equalsIgnoreCase("")) {
					info_id = info_id.substring(0, info_id.length() - 1);
				}

				if (cobId.contains("_")) {
					cobId = cobId.substring(cobId.indexOf("_") + 1);
					System.out.println("cobrand ID:" + cobId);
				} else {
					System.out.println("cobrand ID getting from prop file:" + cobId);
				}

				String query = "select dt.is_ready,dc.is_ready,sc.is_ready,dt.name,sc.sum_info_id,dt.cobrand_id from def_tab dt, def_cat dc, sum_info_def_cat sc where dt.def_tab_id=dc.DEF_TAB_ID and dc.DEF_CAT_ID=sc.DEF_CAT_ID and sc.sum_info_id in ("
						+ info_id + ") and dt.cobrand_id=" + cobId + "";
				System.out.println("query:" + query);
				rs = getResultSet(conn, query);
				if (rs != null) {
					while (rs.next()) {
						String param_value = rs.getString("IS_READY");
						if (param_value.equalsIgnoreCase("1")) {

						} else {
							flag = false;
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ids != null) {
				ids.close();
			}
			if (rs != null) {
				rs.close();
			}

		}
		return flag;
	}

	/**
	 * @param conn
	 * @param username
	 *            This method is used to get the Mem_id from mem table using the
	 *            userName
	 * @return
	 * @throws Exception
	 */
	public String getMEMID(Connection conn, String username) throws Exception {
		String memid = null;
		ResultSet set = null;
		Statement stmt = null;
		try {
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			String statement = "select mem_id from mem where login_name='" + username + "'";
			System.out.println(statement);
			set = stmt.executeQuery(statement);
			if (set.next()) {
				System.out.println("Record available");
				memid = set.getString("mem_id");
				System.out.println(memid);
				return memid;
			}
		} catch (SQLException ex) {
			throw new Exception("Unable to get the memid" + ex.getMessage());
		} finally {
			closeResultSet(set);
			closeStatement(stmt);

		}

		return memid;

	}

	/**
	 * @param conn
	 * @param memsiteaccid
	 * 
	 * @return gatherer Ip is returned
	 * @throws SQLException
	 */
	public String getGathererIp(Connection conn, String memsiteaccid) throws SQLException {
		ResultSet set = null;
		Statement stmt = null;
		try {
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			// SQL Query Modified for Story# (R-23516)
			String sql2 = "select * from server_stats where mem_site_acc_id='" + memsiteaccid
					+ "' order by server_stats_id asc";
			set = stmt.executeQuery(sql2);
			set.last();
			return set.getString("gatherer_ip");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnectionStatementResultSet(null, stmt, set);
		}
		return null;

	}

	/**
	 * @param conn
	 * @param memsiteaccid
	 * 
	 * @return gatherer robot id is returned
	 * @throws SQLException
	 */
	public int getRobotId(Connection conn, String memsiteaccid) throws SQLException {

		ResultSet set = null;
		Statement stmt = null;
		try {
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			// SQL Query Modified for Story# (R-23516)
			String sql2 = "select * from server_stats where mem_site_acc_id='" + memsiteaccid
					+ "' order by server_stats_id asc";
			set = stmt.executeQuery(sql2);
			set.last();
			return set.getInt("gatherer_robot_id");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnectionStatementResultSet(null, stmt, set);
		}
		return (Integer) null;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getCollectionValueFromDB(String query, Class<T> clazz, String colName, int rowCount)
			throws Exception {
		T columnValue = null;
		List<T> list = new ArrayList<>();
		Statement statement = null;
		Connection conn = null;
		ResultSet rs = null;
		DBHelper dbHelper = new DBHelper();
		try {

			conn = dbHelper.getDBConnection();
			statement = conn.createStatement();
			rs = statement.executeQuery(query);

			while (rs.next()) {
				columnValue = (T) rs.getObject(colName);
				list.add(columnValue);
			}

		} finally {
			if(conn!=null) {
				conn.close();
			}if(rs!=null) {
				rs.close();
			}if(statement!=null) {
				statement.close();
			}
		}
		return list;
	}

	/**
	 * This method will return the count for the query executed.
	 * 
	 * @param table
	 * @param whereClause
	 * @return
	 * @throws Exception
	 */
	public int getRowCount(String table, String whereClause) throws Exception {

		int rowCount = 0;
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		DBHelper dbHelper = new DBHelper();
		try {

			conn = dbHelper.getDBConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT COUNT(*) FROM " + table + whereClause);
			while (rs.next()) {
				rowCount = rs.getInt(1);
			}
			System.out.println("Number of row:" + rowCount);

		} finally {

			closeStatement(stmt);
			closeConnection(conn);
			closeResultSet(rs);

		}

		return rowCount;
	}

	/**
	 * This method is used to execute a update query.
	 * 
	 * @param query
	 *            it is an update query.
	 */
	public void executeUpdate(String query) {

		Statement statement = null;
		Connection conn = null;
		try {
			System.out.println("QUERY IS :::: " + query);
			DBHelper dbHelper = new DBHelper();
			conn = dbHelper.getDBConnection();
			statement = conn.createStatement();
			statement.executeUpdate(query);

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeStatement(statement);
			closeConnection(conn);

		}
	}

	/**
	 * Used to fetch data model version for gatherer - Param_acl-6187
	 * 
	 * @param paramKeyId
	 * @return
	 */
	public int getDataModelVer(int paramKeyId) {
		int i = 0;

		try {

			String query = "select param_value from cob_param where param_key_id=" + paramKeyId + " and cobrand_id="
					+ Configuration.getInstance().getCobrandObj().getCobrandId();// cobrandID;

			i = (int) Float.parseFloat((getValueFromDB(query, "param_value").toString()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}

		return i;
	}

	public String getValueFromDB(String query) throws Exception {

		String columnValue = "";
		Statement statement = null;
		System.out.println("Query is : " + query);
		Connection conn = null;
		ResultSet rs = null;
		DBHelper dbHelper = new DBHelper();
		try {
			conn = dbHelper.getDBConnection();
			statement = conn.createStatement();
			rs = statement.executeQuery(query);

			if (rs.next()) {
				columnValue = rs.getString(1);
			}

		} finally {

			closeResultSet(rs);
			closeStatement(statement);
			closeConnection(conn);
		}
		return columnValue;
	}

	public ArrayList<String> verifyBudgetAtCatDataAndType(String userName, String currency, String budgetAmount,
			boolean newCatTYpe) {

		SoftAssert softAssert = new SoftAssert();
		DBHelper dbHelper = new DBHelper();
		ArrayList<String> CURRENCY_ID = new ArrayList<String>();
		String Mem_Id = null;
		ArrayList<String> MEM_HOUSE_HOLDING_GROUP_ID = new ArrayList<String>();
		ArrayList<String> BUDGET_BY_CATEGORY_ID = new ArrayList<String>();
		ArrayList<String> BUDGET_BY_CATEGORY_IS_DELETED = new ArrayList<String>();
		ArrayList<String> BUDGET_SUMMARY_ID = new ArrayList<String>();
		ArrayList<String> BUDGET_SUMMARY_ID_IS_DELETED = new ArrayList<String>();
		ArrayList<String> BUDGET_GOAL_ID = new ArrayList<String>();
		ArrayList<String> GOAL_AMOUNT_CURR_ID = new ArrayList<String>();
		ArrayList<String> MONTHLY_GOAL_AMOUNT_CURR_ID = new ArrayList<String>();
		ArrayList<String> BUDGET_GOAL_AMOUNT = new ArrayList<String>();
		ArrayList<String> MONTHLY_GOAL_AMOUNT = new ArrayList<String>();
		ArrayList<String> BUDGET_GROUP_ID = new ArrayList<String>();
		ArrayList<String> BUDGET_GROUP_IS_DELETED = new ArrayList<String>();
		ArrayList<String> BUDGET_GROUPS = new ArrayList<String>();
		ArrayList<String> TRANSACTION_CATEGORY_TYPE_ID = new ArrayList<String>();
		ArrayList<String> BUDGET_GROUPS_IS_DELETED = new ArrayList<String>();
		Statement statement = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = dbHelper.getDBConnection();

			String query1 = "select * from MEM where Login_name='" + userName + "'";
			statement = conn.createStatement();
			rs = statement.executeQuery(query1);
			while (rs.next()) {
				Mem_Id = rs.getString("MEM_ID");
			}
			String query2 = "select * from mem_house_holding_group where mem_id='" + Mem_Id + "'";
			System.out.println("QUERY IS :::: " + query2);
			rs = statement.executeQuery(query2);
			while (rs.next()) {
				MEM_HOUSE_HOLDING_GROUP_ID.add(rs.getString("MEM_HOUSE_HOLDING_GROUP_ID"));
			}
			String query3 = "select * from budget_group where group_id='" + MEM_HOUSE_HOLDING_GROUP_ID.get(0) + "'";
			System.out.println("QUERY IS :::: " + query3);
			rs = statement.executeQuery(query3);
			while (rs.next()) {
				BUDGET_BY_CATEGORY_ID.add(rs.getString("BUDGET_BY_CATEGORY_ID"));
				BUDGET_BY_CATEGORY_IS_DELETED.add(rs.getString("IS_DELETED"));
				BUDGET_GROUP_ID.add(rs.getString("BUDGET_GROUP_ID"));
				BUDGET_GROUP_IS_DELETED.add(rs.getString("IS_DELETED"));
			}
			for (int i = 0; i < BUDGET_BY_CATEGORY_ID.size(); i++) {
				String query4 = "select * from budget_by_category where budget_by_category_id='"
						+ BUDGET_BY_CATEGORY_ID.get(i) + "'";
				System.out.println("QUERY IS :::: " + query4);
				rs = statement.executeQuery(query4);
				while (rs.next()) {
					BUDGET_GOAL_ID.add(rs.getString("BUDGET_GOAL_ID"));
				}
				softAssert.assertTrue(
						BUDGET_BY_CATEGORY_IS_DELETED.get(i).equals("0") && BUDGET_GROUP_IS_DELETED.get(i).equals("0")
								&& !(BUDGET_GROUP_ID.get(i).equals("null"))
								&& !(BUDGET_BY_CATEGORY_ID.get(i).equals("null")),
						"Budget data has successfully populated in Budget_group,budget_by_category and budget_goal tables and Is_Deleted column set to zero in all tables");
				String query5 = "select * from budget_goal where budget_goal_id ='" + BUDGET_GOAL_ID.get(i) + "'";
				System.out.println("QUERY IS :::: " + query5);
				rs = statement.executeQuery(query5);
				while (rs.next()) {
					GOAL_AMOUNT_CURR_ID.add(rs.getString("GOAL_AMOUNT_CURR_ID"));
					BUDGET_GOAL_AMOUNT.add(rs.getString("GOAL_AMOUNT"));
				}

				String currencyQuery = "select * from currency where CURRENCY_DESC ='" + currency + "'";
				rs = statement.executeQuery(currencyQuery);
				while (rs.next()) {
					CURRENCY_ID.add(rs.getString("CURRENCY_ID"));
				}
				softAssert.assertFalse(GOAL_AMOUNT_CURR_ID.get(i).equals(CURRENCY_ID.get(i)),
						"Budget data has populated in budget_goal table");
				softAssert.assertFalse(BUDGET_GOAL_AMOUNT.get(i).equals(budgetAmount),
						"Budget data has populated in budget_goal table");
				String query6 = "select * from monthly_budget_goal_history where budget_goal_id ='"
						+ BUDGET_GOAL_ID.get(i) + "'";
				System.out.println("QUERY IS :::: " + query6);
				rs = statement.executeQuery(query6);
				while (rs.next()) {
					MONTHLY_GOAL_AMOUNT_CURR_ID.add(rs.getString("CURRENCY_ID"));
					MONTHLY_GOAL_AMOUNT.add(rs.getString("GOAL_AMOUNT"));
					// MONTHLY_GOAL_AMOUNT.add(rs.getString("BUDGET_GOAL_ID"));
				}
				softAssert.assertFalse(MONTHLY_GOAL_AMOUNT.get(i).equals(budgetAmount),
						"Budget data has populated in monthly_budget_goal_history table");
				String query7 = "select * from budget_groups where mem_id ='" + Mem_Id + "'";
				System.out.println("QUERY IS :::: " + query7);
				rs = statement.executeQuery(query7);
				while (rs.next()) {
					BUDGET_GROUPS.add(rs.getString("MEM_HOUSE_HOLDING_GROUP_ID"));
					BUDGET_GROUPS_IS_DELETED.add(rs.getString("IS_DELETED"));
				}
				for (int Budget_groups = 1; Budget_groups < BUDGET_GROUPS.size(); Budget_groups++) {
					softAssert.assertFalse(
							BUDGET_GROUPS.get(Budget_groups).equals("null")
									&& BUDGET_GROUPS_IS_DELETED.get(i).equals("0"),
							"Budget groups table has updated and is deleted coloumn is set to zero");

				}
			}

			// verifying budget at summary table
			String query4 = "select * from budget_group where group_id='" + MEM_HOUSE_HOLDING_GROUP_ID.get(0) + "'";
			System.out.println("QUERY IS :::: " + query4);
			rs = statement.executeQuery(query4);
			while (rs.next()) {
				BUDGET_SUMMARY_ID.add(rs.getString("BUDGET_SUMMARY_ID"));
				BUDGET_SUMMARY_ID_IS_DELETED.add(rs.getString("IS_DELETED"));
				BUDGET_GROUP_ID.add(rs.getString("BUDGET_GROUP_ID"));
				BUDGET_GROUP_IS_DELETED.add(rs.getString("IS_DELETED"));
			}
			try {
				for (int i = 0; i < BUDGET_SUMMARY_ID.size(); i++) {
					String query5 = "select * from budget_summary where budget_summary_id='" + BUDGET_SUMMARY_ID.get(i)
							+ "'";
					System.out.println("QUERY IS :::: " + query5);
					rs = statement.executeQuery(query5);
					softAssert.assertTrue(BUDGET_SUMMARY_ID_IS_DELETED.get(i).equals("0")
							&& BUDGET_GROUP_IS_DELETED.get(i).equals("0") && !(BUDGET_GROUP_ID.get(i).equals("null"))
							&& !(BUDGET_SUMMARY_ID.get(i).equals("null")),
							"Budget data has successfully populated in Budget_group,budget_summary and budget_goal tables and Is_Deleted column set to zero in all tables");
					while (rs.next()) {
						BUDGET_GOAL_ID.add(rs.getString("BUDGET_GOAL_ID"));
						TRANSACTION_CATEGORY_TYPE_ID.add(rs.getString("TRANSACTION_CATEGORY_TYPE_ID"));
					}
					String query6 = "select * from monthly_budget_goal_history where budget_goal_id ='"
							+ BUDGET_GOAL_ID.get(i) + "'";
					System.out.println("QUERY IS :::: " + query6);
					rs = statement.executeQuery(query6);
					while (rs.next()) {
						MONTHLY_GOAL_AMOUNT_CURR_ID.add(rs.getString("CURRENCY_ID"));
						MONTHLY_GOAL_AMOUNT.add(rs.getString("GOAL_AMOUNT"));
						// MONTHLY_GOAL_AMOUNT.add(rs.getString("BUDGET_GOAL_ID"));
					}
					softAssert.assertFalse(MONTHLY_GOAL_AMOUNT.get(i).equals("null"),
							"Budget data has populated in monthly_budget_goal_history table");
					softAssert.assertFalse(MONTHLY_GOAL_AMOUNT_CURR_ID.get(i).equals(budgetAmount),
							"Budget data has populated in monthly_budget_goal_history table");

					String query8 = "select * from budget_groups where mem_id ='" + Mem_Id + "'";
					System.out.println("QUERY IS :::: " + query8);
					rs = statement.executeQuery(query8);
					while (rs.next()) {
						BUDGET_GROUPS.add(rs.getString("MEM_HOUSE_HOLDING_GROUP_ID"));
						BUDGET_GROUPS_IS_DELETED.add(rs.getString("IS_DELETED"));
					}
					for (int Budget_groups = 1; Budget_groups < BUDGET_GROUPS.size(); Budget_groups++) {
						softAssert.assertFalse(
								BUDGET_GROUPS.get(Budget_groups).equals("null")
										&& BUDGET_GROUPS_IS_DELETED.get(i).equals("0"),
								"Budget groups table has updated and is deleted coloumn is set to zero");

					}
				}
			} catch (Exception ex) {
				System.out.println("Budget has not created at summary level........" + ex);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			closeConnectionStatementResultSet(conn, statement, rs);
		}
		// return (GroupDetails);
		return CURRENCY_ID;
	}

	public void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
	}

	public void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
	}

	public void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();

			}
		}
	}

	/**
	 * This method is to close Connection object, Statement Object and ResultSet
	 * object. This method will close only if any of these object are not closed and
	 * will not throw any exception if it is already closed.
	 * 
	 * @author Soujanya Kokala
	 * 
	 * @param connection
	 *            SQL Connection object that needs to be closed.
	 * @param statement
	 *            SQL Statement object that needs to be closed.
	 * @param resultSet
	 *            SQL ResultSet Object that needs to be closed.
	 */
	public void closeConnectionStatementResultSet(Connection connection, Statement statement, ResultSet resultSet) {
		try {
			if (resultSet != null)
				resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (statement != null)
				statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to create and execute query passed and return the
	 * Resultset for the query executed.
	 * 
	 * @param conn-
	 *            Connection object
	 * @param query-
	 *            Query to be executed
	 * @return
	 * @throws SQLException
	 */
	public ResultSet getResultSet(Connection conn, String query) throws SQLException {

		ResultSet rs = null;
		Statement st = conn.createStatement();
		rs = st.executeQuery(query);

		return rs;
	}

	public <T> T getValueFromDB(String query, String colName) throws Exception {
		T columnValue = null;
		Statement statement = null;
		Connection connection = null;
		ResultSet rs = null;
		System.out.println("Query is : " + query);
		DBHelper dbHelper = new DBHelper();
		try {
			connection = dbHelper.getDBConnection();
			statement = connection.createStatement();
			rs = statement.executeQuery(query);

			while (rs.next()) {
				columnValue = (T) rs.getObject(colName);
				System.out.println("Column value :" + columnValue);
				break;
			}
		} finally {
			closeConnectionStatementResultSet(connection, statement, rs);

		}
		return columnValue;
	}
	
	public void updateGoalsActualSavings(Connection conn, String actualSavingsAction, String goalId) throws SQLException {
		
		
		Statement stmt = null;
		ResultSet resultSet = null;	
		int actualSaveAmount=0;
		int expectedSaveAmount=0;
		try {
			if(actualSavingsAction.equalsIgnoreCase("less")) {
				actualSaveAmount = (int) (Math.random() * ((800 - 100) + 1)) + 100;
				expectedSaveAmount=actualSaveAmount+1895;
			}
			if(actualSavingsAction.equalsIgnoreCase("greater")) {
				actualSaveAmount = ((int) (Math.random() * ((800 - 100) + 1)) + 100)+1000;
				expectedSaveAmount=actualSaveAmount-100;
			}	
			if(actualSavingsAction.equalsIgnoreCase("equal")) {
				actualSaveAmount = 1000;
				expectedSaveAmount = actualSaveAmount;
			}	
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String updateQuery = "update user_goal set actual_save_amount=" + actualSaveAmount + "where user_goal_id="
					+ Long.parseLong(goalId);
			System.out.println("Update Goal Actual Save Amount Query " + updateQuery);
			stmt.executeQuery(updateQuery);
			String updateQuery1 = "update user_goal set expected_save_amount=" + expectedSaveAmount + "where user_goal_id="
					+ Long.parseLong(goalId);
			System.out.println("Update Goal Expected Save Amount Query " + updateQuery1);
			stmt.executeQuery(updateQuery1);
			String updateQuery2 = "update user_goal set expected_save_curr_id= 152 ,actual_save_curr_id=152 where user_goal_id="
					+ Long.parseLong(goalId);
			System.out.println("Update Goal Expected Save Amount Query " + updateQuery2);
			stmt.executeQuery(updateQuery2);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
  public void updateAccountStatus(Connection conn,String updateAccountStatus,String accountId) throws SQLException {
		Statement stmt = null;
		ResultSet resultSet = null;
		try {
			//Account Status :  1 - ACTIVE , 2 - INACTIVE , 3 - DELETED , 7 - UNRECONCILED , 5 - TO_BE_CLOSED , 6 - CLOSED
			String INACTIVE = "2";
			String DELETED = "3";
			int accountStatus=10;
			if(updateAccountStatus.equals("INACTIVE"))
				accountStatus=Integer.parseInt(INACTIVE);
			if(updateAccountStatus.equals("DELETED"))
				accountStatus=Integer.parseInt(DELETED);
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String updateQuery = "update item_account set item_account_status_id="+accountStatus + "where item_account_id="+Long.parseLong(accountId);
			System.out.println("Update Goal Actual Save Amount Query " + updateQuery);
			stmt.executeQuery(updateQuery);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.close();
			}
	}
  
  public void changeCreatedBudgetDateToPreviousMonth(Connection conn,String memId,String accountGroupId) throws SQLException {
	

 		Statement stmt =  null;
		ResultSet resultSet1 = null,resultSet2 = null,resultSet3 = null,resultSet4 = null,resultSet5 = null;	
		int actualSaveAmount=0;
		try {
			stmt = conn.createStatement();			
			String query1 = "update mem_house_holding_group set row_last_updated = row_last_updated -30, row_created = row_created -30 where mem_house_holding_group_id = "+ Long.parseLong(accountGroupId);
			stmt.executeQuery(query1);
			String query2 = "update budget_groups set row_last_updated = row_last_updated -30, row_created = row_created -30 where budget_Groups_id in (select budget_groups_id From budget_groups where mem_house_holding_group_id = "+ Long.parseLong(accountGroupId)+")";
			stmt.executeQuery(query2);
			String query3 = "update budget_group set row_last_updated = row_last_updated -30, row_created = row_created -30 where group_id in (select group_id From budget_group where group_id = "+ Long.parseLong(accountGroupId)+")";
			stmt.executeQuery(query3);
			String query4 = "update monthly_budget_goal_history set row_last_updated = row_last_updated -30, row_created = row_created -30, last_updated_time = "+(Instant.now().getEpochSecond()-2678400)+",normalized_time ="+(Instant.now().getEpochSecond()-2678400)+" where monthly_budget_goal_history_id in (SELECT monthly_budget_goal_history_id FROM monthly_budget_goal_history WHERE budget_goal_id IN (SELECT budget_goal_id FROM budget_by_category WHERE budget_groups_id in (SELECT budget_groups_id FROM budget_groups WHERE mem_house_holding_group_id = "+ Long.parseLong(accountGroupId)+")))";;
			stmt.executeQuery(query4);
			String query5 = "update monthly_budget_goal_history set row_last_updated = row_last_updated -30, row_created = row_created -30, last_updated_time = "+(Instant.now().getEpochSecond()-2678400)+",normalized_time = "+(Instant.now().getEpochSecond()-2678400)+" where monthly_budget_goal_history_id in (SELECT monthly_budget_goal_history_id FROM monthly_budget_goal_history WHERE budget_goal_id IN (SELECT budget_goal_id FROM budget_summary WHERE budget_groups_id in (SELECT budget_groups_id FROM budget_groups WHERE mem_house_holding_group_id ="+ Long.parseLong(accountGroupId)+")))";
			stmt.executeQuery(query5);
			String query6 = "update budget_by_category set row_last_updated = row_last_updated -30, row_created = row_created -30 where budget_by_category_id in (SELECT budget_by_category_id FROM budget_by_category WHERE budget_groups_id in (SELECT budget_groups_id FROM budget_groups WHERE mem_house_holding_group_id ="+ Long.parseLong(accountGroupId)+"))";
			stmt.executeQuery(query6);
			String query7 = "update budget_summary set row_last_updated = row_last_updated -30, row_created = row_created -30 where budget_summary_id in (SELECT budget_summary_id FROM budget_summary WHERE budget_groups_id in (SELECT budget_groups_id FROM budget_groups WHERE mem_house_holding_group_id ="+ Long.parseLong(accountGroupId)+"))";
			stmt.executeQuery(query7);
			String query8 = "update budget_goal set row_last_updated = row_last_updated -30, row_created = row_created -30 where budget_goal_id in (SELECT budget_goal_id FROM budget_goal WHERE budget_goal_id IN (SELECT budget_goal_id FROM budget_by_category WHERE budget_groups_id in (SELECT budget_groups_id FROM budget_groups WHERE mem_house_holding_group_id ="+ Long.parseLong(accountGroupId)+")))";
			stmt.executeQuery(query8);
			String query9 = "update budget_goal set row_last_updated = row_last_updated -30, row_created = row_created -30 where budget_goal_id in (SELECT budget_goal_id FROM budget_goal WHERE budget_goal_id IN (SELECT budget_goal_id FROM budget_summary WHERE budget_groups_id in (SELECT budget_groups_id FROM budget_groups WHERE mem_house_holding_group_id ="+ Long.parseLong(accountGroupId)+")))";
			stmt.executeQuery(query9);
			String query10 = "update budget_goal set row_last_updated = row_last_updated -30, row_created = row_created -30 where budget_goal_id in (SELECT budget_goal_id FROM budget_goal WHERE budget_goal_id IN (SELECT budget_summary_id FROM budget_summary WHERE budget_groups_id in (SELECT budget_groups_id FROM budget_groups WHERE mem_house_holding_group_id ="+ Long.parseLong(accountGroupId)+")))";
			stmt.executeQuery(query10);
			conn.commit();		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}

}
