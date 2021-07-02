/*******************************************************************************
 *
 * * Copyright (c) 2020 Yodlee, Inc. All Rights Reserved.
 *
 * *
 *
 * * This software is the confidential and proprietary information of Yodlee, Inc.
 *
 * * Use is subject to license terms.
 *
 *******************************************************************************/
package com.yodlee.insights.yodleeApi.utils.v2;

import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.client.model.Filters;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.util.JSON;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.yodlee.DBHelper;
import com.yodlee.insights.persistence.dao.document.CustomerSubscription;
import com.yodlee.insights.persistence.dao.document.CustomerSubscriptions;
import com.yodlee.insights.yodleeApi.utils.InsightsDBUtils.dateOperations;
import com.yodlee.insights.yodleeApi.utils.InsightsDBUtils.dateParameters;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.utils.apiUtils.InsightsUtilV1;

import io.restassured.response.Response;

public class InsightsDBUtils {
	InsightsProperties prop = new InsightsProperties();
	int port = 27017;
	String db = "mongodb";
	String auto_db_name = "insights_qa_auto";
	String db_name = "insightsqa";
	MongoClient mongoClient = null;
	MongoClient autoMationMongoClient = null;
	MongoDatabase database = null;
	MongoDatabase automation_Database = null;
	MongoCredential credential = null;
	MongoClientOptions options = null;
	private boolean triggerCheckFlag = false;
	private boolean editGeneratedInsight = false;
	private String editInsightPastDate = "";
	BoardHelper boardHelper = new BoardHelper();
	private final String WELLNESS_COBRAND ="wellnessCobrand";
	private final String SENSE_COBRAND ="senseCobrand";

	public InsightsDBUtils() {
		try {
			mongoClient = new MongoClient(
					new MongoClientURI(db + "://" + prop.readPropertiesFile().getProperty("auth_user") + ":"
							+ prop.readPropertiesFile().getProperty("auth_pwd") + "@"
							+ prop.readPropertiesFile().getProperty("primaryHost") + "/?authSource="
							+ prop.readPropertiesFile().getProperty("authSource") + "&ssl="
							+ prop.readPropertiesFile().getProperty("ssl") + ","
							+ prop.readPropertiesFile().getProperty("secondaryHost1") + ","
							+ prop.readPropertiesFile().getProperty("secondaryHost2")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		database = mongoClient.getDatabase(db_name);
		try {
			autoMationMongoClient = new MongoClient(
					new MongoClientURI(db + "://" + prop.readPropertiesFile().getProperty("auth_user_1") + ":"
							+ prop.readPropertiesFile().getProperty("auth_pwd_1") + "@"
							+ prop.readPropertiesFile().getProperty("primaryHost") + "/?authSource="
							+ prop.readPropertiesFile().getProperty("authSource_1") + "&ssl="
							+ prop.readPropertiesFile().getProperty("ssl") + ","
							+ prop.readPropertiesFile().getProperty("secondaryHost1") + ","
							+ prop.readPropertiesFile().getProperty("secondaryHost2")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		automation_Database = autoMationMongoClient.getDatabase(auto_db_name);
	}

	public void getDocumentsOfNotifications() {
		MongoCollection<Document> coll = database.getCollection("user_notification");
		FindIterable<Document> fi = coll.find();
		MongoCursor<Document> cursor = null;
		try {
			cursor = fi.iterator();
			while (cursor.hasNext()) {
				System.out.println(cursor.next().toJson());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
		}
	}

	public void updateCobrandCollectionSubscriptionToInitialValuesIsSubscribedToFalse() {
		MongoCollection<Document> cobrandSubscriptionCollection = database.getCollection("cobrand_subscription");
		FindIterable<Document> findItr = cobrandSubscriptionCollection.find();
		MongoCursor<Document> cursor = null;
		try {
			cursor = findItr.iterator();
			while (cursor.hasNext()) {
				Bson filter = new Document("entityParameter", cursor.next().get("entityParameter"));
				Bson newValue = new Document("isSubscribed", "false");
				Bson updateOperationDocument = new Document("$set", newValue);
				cobrandSubscriptionCollection.updateOne(filter, updateOperationDocument);
				System.out.println(cursor.next().toJson());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
		}
	}

	public void deleteAllUserDetails(String envCobrand) throws IOException {
		MongoCollection<Document> userDetail = null;

		if(Boolean.parseBoolean(prop.readPropertiesFile().getProperty("isMultiCobrandEnv"))) {
			userDetail = automation_Database.getCollection("user_detail");
		}else {
			if (envCobrand.equalsIgnoreCase(WELLNESS_COBRAND)) 
				userDetail = automation_Database.getCollection("user_detail");
			if (envCobrand.equalsIgnoreCase(SENSE_COBRAND))
				userDetail = database.getCollection("user_detail");
		}
		BasicDBObject deleteAll = new BasicDBObject();
		DeleteResult result = userDetail.deleteMany(deleteAll);
		System.out.println("The Numbers of users deleted from user_details collection: " + result.getDeletedCount());
	}

	public void deleteAllJobDTO(String envCobrand) throws IOException, InterruptedException {
		MongoCollection<Document> userDetail = null;

		if(Boolean.parseBoolean(prop.readPropertiesFile().getProperty("isMultiCobrandEnv"))) {
			userDetail = automation_Database.getCollection("job");
		}else {
			if (envCobrand.equalsIgnoreCase(WELLNESS_COBRAND)) 
				userDetail = automation_Database.getCollection("job");
			if (envCobrand.equalsIgnoreCase(SENSE_COBRAND))
				userDetail = database.getCollection("job");
		}
		BasicDBObject deleteAll = new BasicDBObject();
		DeleteResult result = null;
		do
		{
			result = userDetail.deleteMany(deleteAll);
			Thread.sleep(4000);
			System.out.println("The Numbers of job dtos deleted from job collection: " + result.getDeletedCount());
		}while(result.getDeletedCount() != 0);
	}

	public void deleteUserDetail(Long memId, String envCobrand) throws IOException {
		MongoCollection<Document> userDetail = null;
		if(Boolean.parseBoolean(prop.readPropertiesFile().getProperty("isMultiCobrandEnv"))) {
			userDetail = automation_Database.getCollection("user_detail");
		}
		else {
			if (envCobrand.equalsIgnoreCase(WELLNESS_COBRAND)) 
				userDetail = automation_Database.getCollection("user_detail");
			if (envCobrand.equalsIgnoreCase(SENSE_COBRAND))
				userDetail = database.getCollection("user_detail");
		}

		BasicDBObject deleteOneQuery = new BasicDBObject();
		deleteOneQuery.put("memId", memId);
		DeleteResult result = userDetail.deleteOne(deleteOneQuery);
		System.out.println("The Numbers of users deleted from user_details collection: " + result.getDeletedCount());
	}

	public Long getNumberofInsights(Long memId, String envCobrand, String insightName) {
		MongoCollection<Document> userNotification = null;
		if (envCobrand.equalsIgnoreCase(WELLNESS_COBRAND))
			userNotification = automation_Database.getCollection("user_notification");
		else if (envCobrand.equalsIgnoreCase(SENSE_COBRAND))
			userNotification = database.getCollection("user_notification");

		BasicDBObject countQuery = new BasicDBObject();
		List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
		obj.add(new BasicDBObject("memId", memId));
		obj.add(new BasicDBObject("name", insightName));

		countQuery.put("$and", obj);
		Long count = userNotification.count(countQuery);
		System.out.println("The Numbers of Insights generated for given memId: " + count);
		return count;
	}

	public void deleteDocument(String notificationObjectId) throws IOException {
		MongoCollection<Document> userNotification = null;
		if (prop.readPropertiesFile().getProperty("envCobrand").equalsIgnoreCase(WELLNESS_COBRAND))
			userNotification = automation_Database.getCollection("user_notification");
		else if (prop.readPropertiesFile().getProperty("envCobrand").equalsIgnoreCase(SENSE_COBRAND))
			userNotification = database.getCollection("user_notification");
		BasicDBObject deleteOneQuery = new BasicDBObject();
		deleteOneQuery.put("_id", new ObjectId(notificationObjectId));
		if(!triggerCheckFlag) {
			DeleteResult result = userNotification.deleteOne(deleteOneQuery);
			System.err.println(	"The Numbers of Deleted Document(s) from notification collection : " + result.getDeletedCount());
		}else {
			System.err.println("TriggerCheck is Enabled hence Document is NOT Deleted");
		}
	}

	public void setTriggerCheckFlag(String triggerCheckValue) {

		if (triggerCheckValue.equalsIgnoreCase("TRUE") || triggerCheckValue.equalsIgnoreCase("FALSE")) {
			triggerCheckFlag = Boolean.parseBoolean(triggerCheckValue);
			editGeneratedInsight = false;
		}
		if (triggerCheckValue.contains("EDIT")) {
			triggerCheckFlag = true;
			editGeneratedInsight = true;
			editInsightPastDate = triggerCheckValue.split(",")[1].toString();
		}

	}

	public void deleteGeneratedInsights(Long userId, String envCobrand) throws IOException {

		MongoCollection<Document> userNotification = null;
		MongoCollection<Document> userSubscription = null;
		MongoCollection<Document> userBoard = null;
		MongoCollection<Document> userSegment = null;

		if (Boolean.parseBoolean(prop.readPropertiesFile().getProperty("isMultiCobrandEnv"))) {
			userNotification = automation_Database.getCollection("user_notification");
			userSubscription = automation_Database.getCollection("user_subscription");
			userBoard = automation_Database.getCollection("user_board");
			userSegment = automation_Database.getCollection("user_segment");
		} else {
			if (envCobrand.equalsIgnoreCase(WELLNESS_COBRAND)) {
				userNotification = automation_Database.getCollection("user_notification");
				userSubscription = automation_Database.getCollection("user_subscription");
				userBoard = automation_Database.getCollection("user_board");
			}
			if (envCobrand.equalsIgnoreCase(SENSE_COBRAND)) {
				userNotification = database.getCollection("user_notification");
				userSubscription = database.getCollection("user_subscription");
				userBoard = database.getCollection("user_board");
				userSegment = database.getCollection("user_segment");
			}
		}

		if (!triggerCheckFlag) {
			BasicDBObject findQuery = new BasicDBObject();
			findQuery.put("memId", userId);
			FindIterable<Document> doIterable = userNotification.find(findQuery);
			int count = 0;
			for (Document doc : doIterable) {
				try {
					userNotification.deleteMany(doc);
				}catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("Notification Document Deleted " + (++count));
			}
			// delete user subscriptions
			DeleteResult result = null;
			BasicDBObject userSubscriptionQuery = new BasicDBObject();
			userSubscriptionQuery.put("memId", userId);
			FindIterable<Document> doIterableUserSubscription = userSubscription.find(userSubscriptionQuery);
			for (Document doc1 : doIterableUserSubscription) {
				try {
					result = userSubscription.deleteMany(doc1);
				}catch(NullPointerException e) {
					System.out.println("All user subscriptions deleted for this user.");
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("User Details Document Deleted " + result.getDeletedCount());
			}

			// delete user board details
			BasicDBObject userBoardQuery = new BasicDBObject();
			userBoardQuery.put("memId", userId);
			FindIterable<Document> doIterableUserBoard = userBoard.find(userBoardQuery);
			for (Document doc2 : doIterableUserBoard) {
				try {
					result = userBoard.deleteMany(doc2);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("User Boards Document Deleted " + result.getDeletedCount());
			}
			
			//delete created user segment
			BasicDBObject userSegmentQuery = new BasicDBObject();
			userSegmentQuery.put("memId", userId);
			FindIterable<Document> doIterableUserSegment = userSegment.find(userSegmentQuery);
			for (Document doc3 : doIterableUserSegment) {
				try {
					result = userSegment.deleteMany(doc3);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("User Segment Document Deleted " + result.getDeletedCount());
			}
		}
		// coll.updateOne(Filters.eq("unit_tag_code", "payroll_1064"), new
		// Document("$set", new Document("age", newAge)));
		else {
			if (editGeneratedInsight) {
				Bson memIdFilter = Filters.eq("memId", userId);
				String processedCreatedDate = boardHelper.processDateCreatedDate(editInsightPastDate).concat("Z");
				Instant instant = Instant.parse(processedCreatedDate); // Pass your date.
				Date createdDate = Date.from(instant);
				Bson createdDateQuery = new Document("$set", new Document("createdDate", createdDate));
				UpdateResult result = userNotification.updateMany(memIdFilter, createdDateQuery);
				//UpdateResult result = userNotification.updateOne(memIdFilter, createdDateQuery);
				System.out.println("Number of records Updated: " + result.getModifiedCount());
			}
		}
	}

	public void deleteNotifocation() {
		MongoCollection<Document> userNotification = automation_Database.getCollection("user_notification");
		BasicDBObject deleteQuery = new BasicDBObject();
		deleteQuery.put("triggerType", "SCHEDULE");
		FindIterable<Document> doIterable = userNotification.find(deleteQuery);
		int count = 0;
		for (Document doc : doIterable) {
			try {
				userNotification.deleteMany(doc);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("Document Deleted " + (++count));
		}

	}
	
	public void deleteSpecificUserScheduleNotifocations(Long userId, String envCobrand) {
		MongoCollection<Document> userNotification = null;
		if (envCobrand.equalsIgnoreCase(WELLNESS_COBRAND)) {
			userNotification = automation_Database.getCollection("user_notification");
		}
		if (envCobrand.equalsIgnoreCase(SENSE_COBRAND)) {
			userNotification = database.getCollection("user_notification");
		}
		BasicDBObject deleteQuery = new BasicDBObject();
		deleteQuery.put("triggerType", "SCHEDULE");
		deleteQuery.put("memId", userId);
		FindIterable<Document> doIterable = userNotification.find(deleteQuery);
		int count = 0;
		for (Document doc : doIterable) {
			try {
				userNotification.deleteMany(doc);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("Document Deleted " + (++count));
		}

	}

	public void addTestResults(AutomationTestResults automationTestResults) {
		try {
			Gson gson = new Gson();
			String json = gson.toJson(automationTestResults);
			Document doc = Document.parse(json);
			database.getCollection("automation_results").insertOne(doc);
			System.out.println("Automation Results written to mongo");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// cursor.close();
		}
	}

	public List<Integer> getBillerAccountId(String UserName) {
		List<Integer> itemAccountIdList = new ArrayList<>();
		System.out.println("getBillerAccountId");
		DBHelper dbHelper = new DBHelper();
		Connection conn = dbHelper.getDBConnection();
		Statement st = null;
		ResultSet rs = null;

		String getItemAccountIdQuery = "select item_account_id from item_account where cache_item_id in (select cache_item_id from mem_item where mem_id in (select mem_id from mem where login_name='"
				+ UserName + "') and sum_info_id in (20549,\r\n" + "20559)) order by item_account_id asc";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(getItemAccountIdQuery);
			while (rs.next()) {
				itemAccountIdList.add(rs.getInt("item_account_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbHelper.closeConnectionStatementResultSet(conn, st, rs);
		}
		return itemAccountIdList;
	}
	
	public List<Integer> getLoanInvestmentAccountId(String UserName) {
		List<Integer> itemAccountIdList = new ArrayList<>();
		System.out.println("getting getBillerAccountId");
		DBHelper dbHelper = new DBHelper();
		Connection conn = dbHelper.getDBConnection();
		Statement st = null;
		ResultSet rs = null;

		String getItemAccountIdQuery = "select item_account_id from item_account where cache_item_id in (select cache_item_id from mem_item where mem_id in (select mem_id from mem where login_name='"
				+ UserName + "') and sum_info_id in (20563,20549)) order by item_account_id asc";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(getItemAccountIdQuery);
			while (rs.next()) {
				itemAccountIdList.add(rs.getInt("item_account_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbHelper.closeConnectionStatementResultSet(conn, st, rs);
		}
		return itemAccountIdList;
	}


	// updating the manual_transaction table to past date to generate the insight
	public void updateBillData(String itemAccountId,String noOfDays) {
		DBHelper dbHelper = new DBHelper();
		Connection conn = dbHelper.getDBConnection();
		Statement st = null;
		ResultSet rs = null;
		String updateDueDate = "update manual_transaction set trans_date=trans_date-"+noOfDays+" where item_account_id='"
				+ itemAccountId + "' and rownum<2";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(updateDueDate);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbHelper.closeConnectionStatementResultSet(conn, st, rs);
		}
	}
	
	// updating the maturity date for loan account id for different frequencies 
	public void updateDepositTransDate(String itemAccountId,String noOfDays) {
		DBHelper dbHelper = new DBHelper();
		Connection conn = dbHelper.getDBConnection();
		Statement st = null;
		ResultSet rs = null;
		String updateMaturityDate = "update manual_transaction set trans_date= (to_date((select current_date from DUAL)-"+noOfDays+")) where item_account_id='"+ itemAccountId + "' and rownum<2";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(updateMaturityDate);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbHelper.closeConnectionStatementResultSet(conn, st, rs);
		}
	}

	// reverting the the manual_transaction table to generate the insight
	public void ChangeDueDate(String itemAccountId,String noOfDays) {
		DBHelper dbHelper = new DBHelper();
		Connection conn = dbHelper.getDBConnection();
		Statement st = null;
		ResultSet rs = null;
		String updateDueDate = "update manual_transaction set trans_date=trans_date+"+noOfDays+" where item_account_id='"
				+ itemAccountId + "' and rownum<2" ;
		try {
			st = conn.createStatement();
			rs = st.executeQuery(updateDueDate);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbHelper.closeConnectionStatementResultSet(conn, st, rs);
		}
	}

	// updating the maturity date for loan account id for different frequencies 
	public void updateMaturityDate(String itemAccountId,String noOfDays) {
		System.out.println("executing the updateMaturityDate");
		DBHelper dbHelper = new DBHelper();
		Connection conn = dbHelper.getDBConnection();
		Statement st = null;
		ResultSet rs = null;
		String updateMaturityDate = "update LOAN set MATURITY_DATE = ((select current_date from DUAL)+"+noOfDays+") where item_account_id='"+ itemAccountId + "'";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(updateMaturityDate);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbHelper.closeConnectionStatementResultSet(conn, st, rs);
		}
	}
	
	// updating the maturity date for holdings for different frequencies 
		public void updateHoldingMaturityDate(String itemAccountId,String noOfDays) {
			DBHelper dbHelper = new DBHelper();
			Connection conn = dbHelper.getDBConnection();
			Statement st = null;
			ResultSet rs = null;
			String updateMaturityDate = "update holding set MATURITY_DATE = ((select current_date from DUAL)+"+noOfDays+") where item_account_id='"+ itemAccountId + "'";
			try {
				st = conn.createStatement();
				rs = st.executeQuery(updateMaturityDate);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				dbHelper.closeConnectionStatementResultSet(conn, st, rs);
			}
		}

	/**
	 * Add's or subtract's given number of days from given date object
	 * 
	 * @param Date
	 * @param dateOperations
	 * @param count
	 * @return
	 * @author kongara.sravan
	 */
	public static Date modifyTheGivenDate(Date Date, dateOperations dateOperations, dateParameters dateParameters,
			int count) {
		Calendar c = Calendar.getInstance();
		c.setTime(Date);
		count = dateOperations == dateOperations.ADDITION ? count : -count;
		switch (dateParameters) {
		case Days:
			c.add(Calendar.DATE, count);
			break;
		case Hours:
			c.add(Calendar.HOUR_OF_DAY, count);
			break;
		case Minutes:
			c.add(Calendar.MINUTE, count);
			break;
		case Months:
			c.add(Calendar.MONTH, count);
			break;
		case Years:
			c.add(Calendar.YEAR, count);
			break;
		default:
			break;
		}
		return c.getTime();
	}

	public enum dateOperations {
		ADDITION, SUBTRACTION;
	}

	public enum dateParameters {
		Days, Minutes, Months, Years, Hours;
	}

	// Reading Weekly Data Point Table for PropertyValueChange
	//To be updated - Weekly and Monthly to be paramterized
	public static void weeklyReadAndUpdateDataPointTable(String UserName, String balance, int normalizedWeekTime,
			String dateOfDataPoint) {
		DBHelper dbHelper = new DBHelper();
		Connection conn = dbHelper.getDBConnection();
		Statement statement = null;
		// Read from MonthlyDatapointTable
		String readQuery = "select * from weekly_account_data_point where item_account_id in (select item_account_id from item_account where cache_item_id in(select cache_item_id from mem_item where mem_id in (Select mem_id from mem where login_name='"
				+ UserName + "')))";

		String maxOfWeeklyDP_ID = "select MAX(weekly_account_data_point_id)+1 from weekly_account_data_point";

		try {
			statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(readQuery);
			ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
			ResultSetMetaData metaData = resultSet.getMetaData();
			int columnCount = metaData.getColumnCount();
			ArrayList<String> columnNames = new ArrayList<String>();
			for (int i = 1; i <= columnCount; i++) {
				columnNames.add(metaData.getColumnName(i));
			}
			while (resultSet.next()) {
				ArrayList<String> columndata = new ArrayList<String>();
				for (int i = 0; i < columnCount; i++) {
					columndata.add(resultSet.getString(i + 1));

				}
				table.add(columndata);

			}
			String insertQuery = "Insert into weekly_account_data_point ("
					+ columnNames.toString().replace("[", "").replace("]", "") + ") values (";
			for (int i = 0; i < columnCount; i++) {
				if (columnNames.get(i).equals("WEEKLY_ACCOUNT_DATA_POINT_ID")) {

					insertQuery += "(select MAX(weekly_account_data_point_id)+1 from weekly_account_data_point),";

				} else if (columnNames.get(i).equals("BALANCE")) {

					insertQuery += balance + ",";
				} else if (columnNames.get(i).equals("NORMALISED_WEEK_TIME")) {

					long s = modifyTheGivenDate(new Date(), dateOperations.SUBTRACTION, dateParameters.Days,
							normalizedWeekTime).getTime();

					insertQuery += s / 1000 + ",";
				} else if (columnNames.get(i).equals("ROW_CREATED") || columnNames.get(i).equals("ROW_LAST_UPDATED")) {

					insertQuery += "'" + dateOfDataPoint + "'" + ",";
				} else {
					insertQuery += table.get(0).get(i) + ",";

				}
			}

			insertQuery = insertQuery.substring(0, insertQuery.length() - 1) + ")";
			System.out.println(insertQuery);
			ResultSet testQuery = statement.executeQuery(insertQuery);
			System.out.println(testQuery.next());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// Insert

	}

	// Reading and Inserting into Monthly Data Point Table for PropertyValueChange
	// Reading Weekly Data Point Table for PropertyValueChange
	public static void monthlyReadAndUpdateDataPointTable(String UserName, String balance, int normalizedWeekTime,
			String dateOfDataPoint) {
		DBHelper dbHelper = new DBHelper();
		Connection conn = dbHelper.getDBConnection();
		Statement statement = null;
		// Read from MonthlyDatapointTable
		String readQuery = "select * from monthly_account_data_point where item_account_id in (select item_account_id from item_account where cache_item_id in(select cache_item_id from mem_item where mem_id in (Select mem_id from mem where login_name='"
				+ UserName + "')))";
		String maxOfWeeklyDP_ID = "select MAX(monthly_account_data_point_id)+1 from monthly_account_data_point";
		try {
			statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(readQuery);
			ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
			ResultSetMetaData metaData = resultSet.getMetaData();
			int columnCount = metaData.getColumnCount();
			ArrayList<String> columnNames = new ArrayList<String>();
			for (int i = 1; i <= columnCount; i++) {
				columnNames.add(metaData.getColumnName(i));
			}
			while (resultSet.next()) {
				ArrayList<String> columndata = new ArrayList<String>();
				for (int i = 0; i < columnCount; i++) {
					columndata.add(resultSet.getString(i + 1));
				}
				table.add(columndata);
			}
			String insertQuery = "Insert into monthly_account_data_point ("
					+ columnNames.toString().replace("[", "").replace("]", "") + ") values (";
			for (int i = 0; i < columnCount; i++) {
				if (columnNames.get(i).equals("MONTHLY_ACCOUNT_DATA_POINT_ID")) {

					insertQuery += "(select MAX(MONTHLY_account_data_point_id)+1 from MONTHLY_account_data_point),";

				} else if (columnNames.get(i).equals("BALANCE")) {
					insertQuery += balance + ",";
				} else if (columnNames.get(i).equals("NORMALISED_MONTH_TIME")) {
					long s = modifyTheGivenDate(new Date(), dateOperations.SUBTRACTION, dateParameters.Days,
							normalizedWeekTime).getTime();

					insertQuery += s / 1000 + ",";

				} else if (columnNames.get(i).equals("ROW_CREATED") || columnNames.get(i).equals("ROW_LAST_UPDATED")) {

					insertQuery += "'" + dateOfDataPoint + "'" + ",";
				} else {
					insertQuery += table.get(0).get(i) + ",";

				}
			}

			insertQuery = insertQuery.substring(0, insertQuery.length() - 1) + ")";
			System.out.println(insertQuery);
			statement.executeQuery(insertQuery);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}	
	
}
