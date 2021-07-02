/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.xerces.util.SynchronizedSymbolTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import service.DBUtility;

import com.omni.pfm.config.Config;

public class DBUtil {
	private static final Logger logger = LoggerFactory.getLogger(DBUtil.class);

	public static String getConfirmationCode(String username, String conStringLabel, String dbDriverLabel,
			String userNameLabel, String passwordLabel) {
		logger.info("==> Entering DBUtil method.");
		String code = "";
		try {
			Connection conn = getDBConnection(conStringLabel, dbDriverLabel, userNameLabel, passwordLabel);
			Statement st = conn.createStatement();
			String query = "select verification_code from mem_alert_dest where mem_id in (select mem_id from mem where login_name = '"
					+ username + "')";
			logger.info("Query is : {}", query);
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				logger.info("Confirmation Code received from DB is : {}", rs.getString(1));
				code = rs.getString(1);
				System.out.println(rs.getString(1));
			}
		} catch (ClassNotFoundException e) {
			logger.error("Error loading DB Driver - {}", PropsUtil.getEnvPropertyValue(dbDriverLabel));
			logger.error("Stack Trace is : {}", e);
			e.printStackTrace();
		} catch (SQLException e) {
			logger.error("Error while creating DB Connection");
			logger.error("Stack Trace is : {}", e);
			e.printStackTrace();
		}
		logger.info("<== Exiting DBUtil method... Code = {}", code);
		return code;
	}

	public static Connection getDBConnection(String conStringLabel, String dbDriverLabel, String userNameLabel,
			String passwordLabel) throws ClassNotFoundException, SQLException {
		logger.info("==> Entering getDBConnection method.");
		Connection conn;
		String conString = PropsUtil.getEnvPropertyValue(conStringLabel);
		String dbDriver = PropsUtil.getEnvPropertyValue(dbDriverLabel);
		String usernName = PropsUtil.getEnvPropertyValue(userNameLabel);
		String password = PropsUtil.getEnvPropertyValue(passwordLabel);
		if (GenericUtil.isNull(conString)) {
			logger.error("DB Connection String not proper. Label : {}", conStringLabel);
			conn = null;
		} else if (GenericUtil.isNull(dbDriver)) {
			logger.error("DB Driver String not proper. Label : {}", dbDriverLabel);
			conn = null;
		} else if (GenericUtil.isNull(usernName)) {
			logger.error("DB Username not proper. Label : {}", userNameLabel);
			conn = null;
		} else if (GenericUtil.isNull(password)) {
			logger.error("DB Password not proper. Label : {}", passwordLabel);
			conn = null;
		} else {
			Class.forName(dbDriver);
			conn = DriverManager.getConnection(conString, usernName, password);

		}
		logger.info("<== Exiting getDBConnection method.");
		return conn;
	}

	public static Connection getDBConnections(String conStringLabel, String dbDriverLabel, String userNameLabel,
			String passwordLabel) throws ClassNotFoundException, SQLException {
		logger.info("==> Entering getDBConnection method.");
		Connection conn;
		String conString = conStringLabel;
		String dbDriver = dbDriverLabel;
		String usernName = userNameLabel;
		String password = passwordLabel;
		if (GenericUtil.isNull(conString)) {
			logger.error("DB Connection String not proper. Label : {}", conStringLabel);
			conn = null;
		} else if (GenericUtil.isNull(dbDriver)) {
			logger.error("DB Driver String not proper. Label : {}", dbDriverLabel);
			conn = null;
		} else if (GenericUtil.isNull(usernName)) {
			logger.error("DB Username not proper. Label : {}", userNameLabel);
			conn = null;
		} else if (GenericUtil.isNull(password)) {
			logger.error("DB Password not proper. Label : {}", passwordLabel);
			conn = null;
		} else {
			Class.forName(dbDriver);
			conn = DriverManager.getConnection(conString, usernName, password);

		}
		logger.info("<== Exiting getDBConnection method.");
		return conn;
	}

	public Connection connectDB(String dbHost, String dbPort, String dbServiceName, String dbType, String dbEnv)
			throws Exception {
		Connection conn;
		try {

			conn = DBUtility.getDBConnection(dbHost, Integer.parseInt(dbPort), dbServiceName, dbType, dbEnv);

		} catch (Exception ex) {
			throw new Exception("Unable to Connect to DB");
		}
		// Connection conn = DriverManager.getConnection(this.dbURL, this.username,
		// this.password);
		return conn;

	}

	public static Connection getDBConnections(String dbHost, String dbPort, String dbServiceName, String dbType,
			String dbEnv) throws Exception {
		logger.info("==> Entering getDBConnection method.");
		Connection conn;
		try {

			conn = DBUtility.getDBConnection(dbHost, Integer.parseInt(dbPort), dbServiceName, dbType, dbEnv);

		} catch (Exception ex) {
			throw new Exception("Unable to Connect to DB");
		}
		return conn;
	}

	/*
	 * public static void main(String[] args) throws Exception {
	 * 
	 * getDBConnection("cnf_JDBCConnectionStringOLTP", "cnf_JDBCDriverOLTP",
	 * "cnf_JDBCPasswordOLTP", "cnf_JDBCUserNameOLTP");
	 * 
	 * Config.createInstance("YcomMainline");
	 * System.out.println(getConfirmationCode("YcomMainline1470295386800",
	 * "cnf_JDBCConnectionStringOLTP", "cnf_JDBCDriverOLTP", "cnf_JDBCPasswordOLTP",
	 * "cnf_JDBCUserNameOLTP")); // System.out.println(con.getSchema());) }
	 */

	public static void goalHistoryUpdate(Connection con, String userName) throws ClassNotFoundException, SQLException {
		// Connection
		// con=DBUtil.getDBConnections(DBURL,jdbcDriver,dbusername,dbPassword);
		try {
			System.out.println(con);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from mem where login_name= '" + userName + "'");
			int memid = 0;
			while (rs.next()) {
				memid = rs.getInt("MEM_ID");
				System.out.println(memid);
			}
			ResultSet categoryId = stmt.executeQuery("select * from budget_by_category where mem_id='" + memid + "'");

			int budgetBycat = 0;
			String bugId = "";
			int budgetGoalId = 0;
			String bugGoalId = "";

			while (categoryId.next()) {
				budgetBycat = categoryId.getInt("budget_by_category_id");
				bugId = bugId + budgetBycat + ",";
				budgetGoalId = categoryId.getInt("budget_goal_id");
				bugGoalId = bugGoalId + budgetGoalId + ",";

			}
			bugId = bugId.substring(0, bugId.length() - 1);
			bugGoalId = bugGoalId.substring(0, bugGoalId.length() - 1);

			stmt.executeQuery(
					"update budget_by_category set row_last_updated = row_last_updated -31,row_created=row_created -31 where budget_by_category_id in("
							+ bugId + ")");
			ResultSet monthlubudgetGoalHistory = stmt.executeQuery(
					"select * from monthly_budget_goal_history where budget_goal_id in(" + bugGoalId + ")");

			int goalhistoryId = 0;
			String goalHisyoryId = "";
			while (monthlubudgetGoalHistory.next()) {
				goalhistoryId = monthlubudgetGoalHistory.getInt("monthly_budget_goal_history_id");
				goalHisyoryId = goalHisyoryId + goalhistoryId + ",";
			}
			goalHisyoryId = goalHisyoryId.substring(0, goalHisyoryId.length() - 1);
			stmt.executeQuery(
					"update monthly_budget_goal_history set row_last_updated=row_last_updated -31,row_created=row_created -31 where monthly_budget_goal_history_id in("
							+ goalHisyoryId + ")");
			con.commit();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			// throw new Exception(e.getLocalizedMessage());
			// TODO: handle exception
		}
	}

}
