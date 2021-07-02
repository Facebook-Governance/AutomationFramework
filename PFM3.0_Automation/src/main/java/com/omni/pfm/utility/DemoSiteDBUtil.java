/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
package com.omni.pfm.utility;

//To use db connect module we need to manually install mvn module ojdbc14

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DemoSiteDBUtil extends oracle.jdbc.driver.OracleDriver {

	private String dbURL;
	private String username;
	private String password;
	private int PKI_COBRAND_PARAM_KEY = 6166;
	
	

	public DemoSiteDBUtil(String dbURL, String username, String password) {

		this.dbURL = dbURL;
		this.username = username;
		this.password = password;

	}

	public static String getVerificationCode(String login_name) throws Exception {
		String dbUrl = "jdbc:oracle:thin:read/read@//blr-oda2-scan/indqa41";
		String verifyCode = null;
		Connection conn = DriverManager.getConnection(dbUrl, "read", "read");
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(
				"select verification_code from mem_alert_dest where mem_id in(select mem_id from mem where mem.login_name='"
						+ login_name + "') AND cob_alert_dest_type_id = 1");
		if (rs.next()) {
			verifyCode = rs.getString("verification_code");
		}

		return verifyCode;

	}

	public static String getMobileVerificationCode(String login_name) throws Exception {
		String dbUrl = "jdbc:oracle:thin:read/read@//blr-oda2-scan/indqa41";
		String verifyCode = null;
		Connection conn = DriverManager.getConnection(dbUrl, "read", "read");
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(
				"select verification_code from mem_alert_dest where mem_id in(select mem_id from mem where mem.login_name='"
						+ login_name + "') AND cob_alert_dest_type_id = 3");
		if (rs.next()) {
			verifyCode = rs.getString("verification_code");
		}

		return verifyCode;

	}

	// added by Ravi for pwd

	public static String getUserPassword(String login_name) throws Exception {
		String dbUrl = "jdbc:oracle:thin:read/read@//blr-oda2-scan/indqa41";
		String password = null;
		Connection conn = DriverManager.getConnection(dbUrl, "read", "read");
		Statement st = conn.createStatement();
		ResultSet rs = st
				.executeQuery("select passwd from mem where mem_id in(select mem_id from mem where mem.login_name='"
						+ login_name + "' and cobrand_id=10000004)");
		if (rs.next()) {
			password = rs.getString(1);
		}

		return password;

	}

	// by Amit Agarwal
	// Parametrised method to fetch single data from the db

	public static String exSelectQueryforSingleRecord(String query) throws Exception {
		// String dbUrl =
		// "jdbc:oracle:thin:read/read@//192.168.210.28:1521/indqa18";
		String dbUrl = "jdbc:oracle:thin:read/read@//blr-oda2-scan/indqa41";
		String outPutData = null;
		Connection conn = DriverManager.getConnection(dbUrl, "read", "read");
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		if (rs.next()) {
			outPutData = rs.getString(1);
		}

		return outPutData;

	}

	public static Object[] getMemMobileNum(String memID) throws Exception {
		// String dbUrl =
		// "jdbc:oracle:thin:read/read@//192.168.210.28:1521/indqa18";
		String dbUrl = "jdbc:oracle:thin:read/read@//blr-oda2-scan/indqa41";
		Connection conn = DriverManager.getConnection(dbUrl, "read", "read");
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(
				// "select pref_key_value from mem_pref where mem_pref_id
				// IN(10713555,10713556,10713557,10713558,10742616) order by
				// pref_key_name");
				"select pref_key_value from mem_pref where mem_pref_id IN("
						+ PropsUtil.getDataPropertyValue("mem_pref_ids")
						+ ") order by pref_key_name");

		Object[] dbData = new Object[5];
		int i = 0;
		while (rs.next()) {
			dbData[i++] = rs.getString(1);
		}

		return dbData;

	}

	public String getdbURL() {

		return dbURL;

	}

	public String getUsername() {

		return username;

	}

	public String getPassword() {

		return password;

	}

	public void deleteGroup(Connection con, String username) throws Exception {

		int i = getMEMID(con, username);

		Statement st = con.createStatement();

		String statement = "delete from mem_house_holding_group where MEM_ID='" + i + "'";

		int rs = st.executeUpdate(statement);

		if (rs > 0) {

			System.out.println("Group deleted");

			con.commit();

		} else {

			System.out.println("No group found to be deleted.");

		}

	}

	public Connection connectDB() throws Exception {

		try {

			Connection conn = DriverManager.getConnection(this.dbURL, this.username, this.password);

			return conn;

		}

		catch (SQLException ex) {

			throw new Exception("Unable to Connect. Please check the dbDetails"

					+ ex.getMessage());

		}

	}

	public int getMEMID(Connection conn, String username) throws Exception {

		// Connection conn = this.connectDB();

		try {

			Statement stmt = conn.createStatement(

					ResultSet.TYPE_SCROLL_INSENSITIVE,

					ResultSet.CONCUR_READ_ONLY);

			// Statement stmt = conn.createStatement();

			String statement = "select MEM_ID from mem where login_name='" + username + "'";

			ResultSet set = stmt.executeQuery(statement);

			set.last();

			return (set.getInt("mem_id"));

		}

		catch (SQLException ex) {

			throw new Exception("Unable to get the memid" + ex.getMessage());

		}

	}

	public void closeConnection(Connection conn) throws Exception {

		conn.close();

	}

	public String getConfirmationCode(Connection conn, String username)

			throws Exception {

		try {

			Statement stmt = conn.createStatement(

					ResultSet.TYPE_SCROLL_INSENSITIVE,

					ResultSet.CONCUR_READ_ONLY);

			String statement = "select verification_code from mem_alert_dest where mem_id IN (Select mem_id from mem where login_name like '"
					+ username + "')";

			ResultSet set = stmt.executeQuery(statement);

			set.last();

			String confirmationCode = set.getString("VERIFICATION_CODE");

			return confirmationCode;

		} catch (SQLException ex) {

			throw new Exception("Unable to get the Confirmation Code" + ex.getMessage());

		}

	}

	// Method to get PARAMVALUE from OLTP for specific COBRAND and APPID

	private String getParamValue(Connection conn, String PARAMKEYID,

			String COBRANDID, String APPID) throws Exception {

		Statement stmt = conn.createStatement(

				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

		String sql = "select * from COB_PARAM where param_key_id=" + PARAMKEYID

				+ " and cobrand_id=" + COBRANDID + " and app_id='" + APPID

				+ "'";

		ResultSet set = stmt.executeQuery(sql);

		set.last();

		String param_value = set.getString("PARAM_VALUE");

		return (param_value);

	}

	// Method to get list of configured popular site for specified param value

	@SuppressWarnings({ "rawtypes", "unchecked" })

	private List<HashMap> getSiteIds(Connection conn, String param_value)

			throws Exception {

		List<HashMap> list = new ArrayList();

		Statement stmt = conn.createStatement(

				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

		String sql = "select site_id, BASE_URL,mc_key from SITE where site_id in ("

				+ param_value + ")";

		System.out.println(sql);

		ResultSet set = stmt.executeQuery(sql);

		while (set.next()) {

			HashMap<String, String> siteData = new HashMap<String, String>();

			siteData.put("siteId", String.valueOf(set.getInt("site_id")));

			siteData.put("baseURL", set.getString("BASE_URL"));

			siteData.put("mcKey", set.getString("mc_key"));

			list.add(siteData);

		}

		return list;

	}

	// Method to get list of configured popular site with Name and Baseurl

	@SuppressWarnings({ "rawtypes", "unchecked" })

	private List<HashMap> getSiteNames(Connection conn, List<HashMap> sitelist)

			throws Exception {

		List<HashMap> popularlist = new ArrayList();

		for (int i = 0; i < sitelist.size(); i++) {

			HashMap<String, String> popularsites = new HashMap<String, String>();

			popularsites.put("siteId", sitelist.get(i).get("siteId").toString());

			popularsites.put("baseURL", sitelist.get(i).get("baseURL").toString());

			Statement stmt = conn.createStatement(

					ResultSet.TYPE_SCROLL_INSENSITIVE,

					ResultSet.CONCUR_READ_ONLY);

			String sql2 = "select value from DB_MESSAGE_CATALOG where mc_key='"

					+ sitelist.get(i).get("mcKey") + "' and ROWNUM <= 1";

			ResultSet set = stmt.executeQuery(sql2);

			set.last();

			popularsites.put("siteName", set.getString("value"));

			popularlist.add(popularsites);

			set.close();

			stmt.close();

		}

		return popularlist;

	}

	// Public Method to get list of popular sites

	@SuppressWarnings("rawtypes")

	public List<HashMap> getPopularSiteList(Connection conn, String PARAMKEYID,

			String COBRANDID, String APPID) throws Exception {

		List<HashMap> popularsitelist;

		String param_value = this.getParamValue(conn, PARAMKEYID, COBRANDID,

				APPID);

		List<HashMap> sitelist = this.getSiteIds(conn, param_value);

		popularsitelist = this.getSiteNames(conn, sitelist);

		return popularsitelist;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })

	private List getPopularSiteIDByCountry(Connection conn, String country,

			String COBRANDID, int count) throws Exception {

		List siteid = new ArrayList();

		Statement stmt = conn.createStatement(

				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

		String sql2 = "select site_id from POP_SITE_COUNTRY where cobrand_id='"

				+ COBRANDID + "' and country ='" + country

				+ "' order by popular_count desc";

		ResultSet set = stmt.executeQuery(sql2);

		for (int i = 0; i < count; i++) {

			set.next();

			siteid.add(set.getInt("site_id"));

		}

		set.close();

		stmt.close();

		return siteid;

	}

	// Public Method to get list of popular sites by country

	@SuppressWarnings("rawtypes")

	public List<HashMap> getPopularSiteListByCountry(Connection conn,

			String country, String COBRANDID, int count) throws Exception {

		List<HashMap> popularsitelistbyCountry = null;

		List siteidlist = this.getPopularSiteIDByCountry(conn, country,

				COBRANDID, count);

		String param_value = siteidlist.toString().replace("[", "")

				.replace("]", "");

		List<HashMap> sitelist = this.getSiteIds(conn, param_value);

		popularsitelistbyCountry = this.getSiteNames(conn, sitelist);

		return popularsitelistbyCountry;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })

	private List getSuggestedSites(Connection conn, int mem_id)

			throws Exception {

		List siteids = new ArrayList();

		Statement stmt = conn.createStatement(

				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

		String sql2 = "select site_id from SUGGESTED_SITES where mem_id='"

				+ String.valueOf(mem_id) + "'";

		ResultSet set = stmt.executeQuery(sql2);

		while (set.next()) {

			siteids.add(set.getInt("site_id"));

		}

		return siteids;

	}

	@SuppressWarnings("rawtypes")

	public List<HashMap> getSuggestedSiteList(Connection conn, String username)

			throws Exception {

		List<HashMap> suggestedsitelist;

		int memId = this.getMEMID(conn, username);

		List sitearray = this.getSuggestedSites(conn, memId);

		String param_value = sitearray.toString().replace("[", "")

				.replace("]", "");

		List<HashMap> sitelist = this.getSiteIds(conn, param_value);

		suggestedsitelist = this.getSiteNames(conn, sitelist);

		return suggestedsitelist;

	}

	public void updatePKIParamKey(Connection con, String cobrandid, String flag) throws SQLException {

		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

		String sql2 = "UPDATE COB_PARAM SET param_value = '" + flag
				+ "' WHERE cob_param_id IN (SELECT cob_param_id FROM COB_PARAM WHERE param_key_id = "
				+ PKI_COBRAND_PARAM_KEY + " AND cobrand_id = " + cobrandid + ")";

		stmt.executeQuery(sql2);

		stmt.executeQuery("commit");

	}

	public static void executeUpdateStatement(Connection con, String query) throws Exception

	{

		Statement st = con.createStatement();

		int rs = st.executeUpdate(query);

		if (rs > 0)

		{

			System.out.println("Query successfully executed.");

		}

	}

	// Ashwathi Nair - to update login attemots in the database

	public static void updateLoginAttempts(String login_name, Integer attempts) throws Exception {

		String dbUrl = "jdbc:oracle:thin:read/read@//blr-oda2-scan/indqa41";
		Connection conn = DriverManager.getConnection(dbUrl, "pal", "pal");
		String statement = "update mem set login_attempts = " + attempts + " where login_name = '" + login_name + "'";
		System.out.println(statement);
		DemoSiteDBUtil.executeUpdateStatement(conn, statement);

		statement = "update mem set login_cnt = " + attempts + " where login_name = '" + login_name + "'";
		System.out.println(statement);
		DemoSiteDBUtil.executeUpdateStatement(conn, statement);

		statement = "update mem set reset_attempts = " + attempts + " where login_name = '" + login_name + "'";
		System.out.println(statement);
		DemoSiteDBUtil.executeUpdateStatement(conn, statement);

		conn.commit();

	}

	// Ashwathi Nair - to fetch SR ID from the YCC database

	public static String getServiceRequestID(String login_name) throws Exception {
		String dbUrl = "jdbc:oracle:thin:read/read@//blr-oda2-scan/indqa41";
		String ServiceRequestID = null;
		Connection conn = DriverManager.getConnection(dbUrl, "ycc_app", "ycc_app");
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(
				"select max(service_req_id) from service_req where reporter_login_name = '" + login_name + "'");
		if (rs.next()) {
			ServiceRequestID = rs.getString("max(service_req_id)");
		}

		return ServiceRequestID;

	}

}
