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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import javax.print.attribute.standard.MediaSize.JIS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yodlee.DBHelper;
import com.yodlee.yodleeApi.constants.Container;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Map;
import com.yodlee.yodleeApi.interfaces.Session;
import com.yodlee.yodleeApi.utils.apiUtils.AccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.GroupUtils;
import com.yodlee.yodleeApi.utils.apiUtils.BudgetUtils;
import io.restassured.response.Response;

public class BudgetHelper {

	/**
	 * Method to prepare crete group JSON body request
	 * 
	 */
	protected static Logger logger = LoggerFactory.getLogger(BudgetHelper.class);
	JsonParser jsonParser = new JsonParser();
	static DBHelper dbHelper = new DBHelper();
	Map<String, Integer> budgetAmountMap = new HashMap<String, Integer>();
	static String[] transactionsValuesMap = { "yearMonth", "creditTotal", "debitTotal" };
	int[] categoryIds = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26,
			27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 92, 94, 96, 225, 98, 227, 100,
			102, 104, 106, 108, 110, 112, 114 };
	String[] categoryTypes = { "INCOME", "EXPENSE", "TRANSFER" };
	HashMap<String, HashMap<String, Integer>> budgetCategoryTypeDetailsMap;
	HashMap<String, HashMap<String, Integer>> budgetCategoryDetailsMap;
	BudgetUtils budgetUtils = new BudgetUtils();	

	public HttpMethodParameters createGroupJson(String group) throws JSONException {
		System.out.println("createGroupJson called for group:" + group);
		JSONObject grpObj = new JSONObject();
		JSONArray grpObj1 = new JSONArray();
		JSONObject grpObj2 = new JSONObject();
		grpObj.put("name", group);
		grpObj1.put(grpObj);
		grpObj2.put("group", grpObj1);
		String userRegisterObjJSON = grpObj2.toString();
		System.out.println("createGroupJson is : " + userRegisterObjJSON);
		HttpMethodParameters param = HttpMethodParameters.builder().build();
		param.setBodyParams(userRegisterObjJSON);
		return param;
	}

	/**
	 * Method to generate random budget amount
	 **/
	public String BudgetAmount() {
		Random rand = new Random();
		int rand_int = rand.nextInt(1000);

		return (String.valueOf(rand_int));
	}

	/**
	 *
	 * Method to fetch random category Id's from listed category Data
	 **/
	public String FetchRandomInput(File f) throws FileNotFoundException {
		String FilePath = new java.io.File("").getAbsolutePath() + "\\" + f;
		String RandomCatId = null;
		Random rand = new Random();
		int n = 0;
		for (Scanner sc = new Scanner(new File(FilePath)); sc.hasNext();) {
			++n;
			String line = sc.nextLine();
			if (rand.nextInt(n) == 0)
				RandomCatId = line;
		}
		return (RandomCatId);
	}

	/**
	 * Method to fetch two random category Id's to pass as array of category Ids in
	 * request param
	 * 
	 */
	public ArrayList<String> FetchTwoRandomInput(File f) throws FileNotFoundException {
		String FilePath = new java.io.File("").getAbsolutePath() + "\\" + f;
		String RandomCatId = null;
		ArrayList<String> RandomCatId1 = new ArrayList<String>();
		Random rand = new Random();
		int n = 0;
		for (Scanner sc = new Scanner(new File(FilePath)); sc.hasNext();) {
			++n;
			String line = sc.nextLine();
			if (rand.nextInt(n) == 0) {
				RandomCatId = line;
				RandomCatId1.add(RandomCatId);
				if (RandomCatId1.size() == 2)
					break;
			}
		}
		return (RandomCatId1);
	}

	// Method to prepare JSON request for Budget at category data
	public String CreateBudgetAtCatData(String groupId, List<Integer> categoryId, String budgetAmount, String currency)
			throws JSONException {

		JSONObject reqObject = new JSONObject();
		JSONArray budgetArray = new JSONArray();

		JSONObject budgetObject = new JSONObject();
		budgetObject.put("groupId", groupId);

		// Category Data array
		JSONArray categoryDataArray = new JSONArray();
		for (int i = 0; i < categoryId.size(); i++) {
			JSONObject catTypeObject = new JSONObject();
			catTypeObject.put("categoryId", categoryId.get(i));

			JSONObject budgetTotalObj = new JSONObject();
			budgetTotalObj.put("amount", budgetAmount);
			budgetTotalObj.put("currency", currency);
			catTypeObject.put("budgetAmount", budgetTotalObj);

			categoryDataArray.put(catTypeObject);
		}
		budgetObject.put("categoryData", categoryDataArray);

		budgetArray.put(budgetObject);

		reqObject.put("budget", budgetArray);
		// String test = reqObject.toJSONString();
		return reqObject.toString();
	}

	

	// Method to prepare JSON request for Budget at category Type
	public String CreateBudgetAtCatType(String groupId, List<String> categoryType, String budgetAmount, String currency)
			throws JSONException {

		JSONObject reqObject = new JSONObject();
		JSONObject budgetObject = new JSONObject();
		JSONArray budgetArray = new JSONArray();
		budgetObject.put("groupId", groupId);
		// Category Type array
		JSONArray categoryTypeArray = new JSONArray();
		for (int i = 0; i < categoryType.size(); i++) {
			JSONObject catObject = new JSONObject();

			catObject.put("categoryType", categoryType.get(i));

			JSONObject budgetTotalObj = new JSONObject();
			budgetTotalObj.put("amount", budgetAmount);
			budgetTotalObj.put("currency", currency);
			catObject.put("budgetAmount", budgetTotalObj);

			categoryTypeArray.put(catObject);
		}
		budgetObject.put("categoryTypeData", categoryTypeArray);
		budgetArray.put(budgetObject);

		reqObject.put("budget", budgetArray);

		return reqObject.toString();

	}

	// Method to prepare JSON request for Budget at category data
	public String CreateCatDataAndCatType(String groupId, List<String> categoryType, List<Integer> categoryId,
			String budgetAmount, String currency) throws JSONException {

		JSONObject reqObject = new JSONObject();
		JSONObject budgetObject = new JSONObject();
		JSONArray budgetArray = new JSONArray();
		budgetObject.put("groupId", groupId);
		// Category Type array
		JSONArray categoryTypeArray = new JSONArray();
		for (int i = 0; i < categoryType.size(); i++) {
			JSONObject catObject = new JSONObject();
			catObject.put("categoryType", categoryType.get(i));

			JSONObject budgetTotalObj = new JSONObject();
			budgetTotalObj.put("amount", budgetAmount);
			budgetTotalObj.put("currency", currency);
			catObject.put("budgetAmount", budgetTotalObj);

			categoryTypeArray.put(catObject);
		}
		budgetObject.put("categoryTypeData", categoryTypeArray);
		budgetArray.put(budgetObject);

		// Category Data array
		JSONArray categoryDataArray = new JSONArray();
		for (int i = 0; i < categoryId.size(); i++) {
			JSONObject catTypeObject = new JSONObject();
			catTypeObject.put("categoryId", categoryId.get(i));

			JSONObject budgetTotalObj = new JSONObject();
			budgetTotalObj.put("amount", budgetAmount);
			budgetTotalObj.put("currency", currency);
			catTypeObject.put("budgetAmount", budgetTotalObj);

			categoryDataArray.put(catTypeObject);
		}
		budgetObject.put("categoryData", categoryDataArray);

		reqObject.put("budget", budgetArray);

		return reqObject.toString();
	}

	// Populating user details in to text file
	public void createFile(ArrayList<String> arrData) throws IOException {
		FileWriter writer = new FileWriter("C://UserDetails" + ".txt");
		BufferedWriter Bwriter = new BufferedWriter(writer);
		int size = arrData.size();
		for (int i = 0; i < size; i++) {
			String str = arrData.get(i).toString();
			Bwriter.write(str);
			Bwriter.newLine();
			if (i < size - 1)// **//This prevent creating a blank like at the
								// end of the file**
				Bwriter.write("\n");
		}
		Bwriter.close();
	}

	// Method to prepare JSON request for Budget at category data
	public String UpdateBudgetAtCatData(String groupId, ArrayList<Integer> categoryId, String budgetAmount,
			String currency) throws JSONException {

		JSONObject reqObject = new JSONObject();
		JSONArray budgetArray = new JSONArray();

		JSONObject budgetObject = new JSONObject();
		budgetObject.put("groupId", groupId);

		// Category Data array
		JSONArray categoryDataArray = new JSONArray();
		for (int i = 0; i < categoryId.size(); i++) {
			JSONObject catTypeObject = new JSONObject();
			catTypeObject.put("categoryId", categoryId.get(i));

			JSONObject budgetTotalObj = new JSONObject();
			budgetTotalObj.put("amount", budgetAmount);
			budgetTotalObj.put("currency", currency);
			catTypeObject.put("budgetAmount", budgetTotalObj);

			categoryDataArray.put(catTypeObject);
		}
		budgetObject.put("categoryData", categoryDataArray);

		budgetArray.put(budgetObject);

		reqObject.put("budget", budgetArray);

		return reqObject.toString();
	}

	// Method to prepare JSON request for Budget at category Type
	public String UpdateBudgetAtCatType(String groupId, ArrayList<String> categoryType, String budgetAmount,
			String currency) throws JSONException {

		JSONObject reqObject = new JSONObject();
		JSONObject budgetObject = new JSONObject();
		JSONArray budgetArray = new JSONArray();
		budgetObject.put("groupId", groupId);
		// Category Type array
		JSONArray categoryTypeArray = new JSONArray();
		for (int i = 0; i < categoryType.size(); i++) {
			JSONObject catObject = new JSONObject();
			catObject.put("categoryType", categoryType.get(i));

			JSONObject budgetTotalObj = new JSONObject();
			budgetTotalObj.put("amount", budgetAmount);
			budgetTotalObj.put("currency", currency);
			catObject.put("budgetAmount", budgetTotalObj);

			categoryTypeArray.put(catObject);
		}
		budgetObject.put("categoryTypeData", categoryTypeArray);
		budgetArray.put(budgetObject);

		reqObject.put("budget", budgetArray);

		return reqObject.toString();
	}

	public String getClassName() {
		String className = "";
		Class<?> enclosingClass = getClass().getEnclosingClass();
		if (enclosingClass != null) {
			className = enclosingClass.getName();
		} else {
			className = getClass().getName();
		}
		return className;
	}

	@SuppressWarnings("resource")
	public static ArrayList<String> verifyBudgeatAtDB(String userName, String currency, String budgetType,
			String budgetAmount, boolean newCatTYpe) {
		SoftAssert softAssert = new SoftAssert();
		String Mem_Id = null;
		ArrayList<String> MEM_HOUSE_HOLDING_GROUP_ID = new ArrayList<String>();
		ArrayList<String> BUDGET_BY_CATEGORY_ID = new ArrayList<String>();
		ArrayList<String> BUDGET_BY_CATEGORY_IS_DELETED = new ArrayList<String>();
		ArrayList<String> BUDGET_SUMMARY_ID = new ArrayList<String>();
		ArrayList<String> BUDGET_SUMMARY_ID_IS_DELETED = new ArrayList<String>();
		ArrayList<String> BUDGET_GOAL_ID = new ArrayList<String>();
		ArrayList<String> GOAL_AMOUNT_CURR_ID = new ArrayList<String>();
		ArrayList<String> MONTHLY_GOAL_AMOUNT_CURR_ID = new ArrayList<String>();
		ArrayList<String> CURRENCY_ID = new ArrayList<String>();
		ArrayList<String> GOAL_AMOUNT = new ArrayList<String>();
		ArrayList<String> MONTHLY_GOAL_AMOUNT = new ArrayList<String>();
		ArrayList<String> BUDGET_GROUP_ID = new ArrayList<String>();
		ArrayList<String> BUDGET_GROUP_IS_DELETED = new ArrayList<String>();
		ArrayList<String> BUDGET_GROUPS = new ArrayList<String>();
		ArrayList<String> TRANSACTION_CATEGORY_TYPE_ID = new ArrayList<String>();
		ArrayList<String> BUDGET_GROUPS_IS_DELETED = new ArrayList<String>();
		Statement statement = null;
		ResultSet rs = null;
		DBHelper dbHelper = new DBHelper();

		Connection conn = dbHelper.getDBConnection();
		try {
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
			if (budgetType.equals("catData")) {
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
					softAssert.assertTrue(BUDGET_BY_CATEGORY_IS_DELETED.get(i).equals("0")
							&& BUDGET_GROUP_IS_DELETED.get(i).equals("0") && !(BUDGET_GROUP_ID.get(i).equals("null"))
							&& !(BUDGET_BY_CATEGORY_ID.get(i).equals("null")),
							"Budget data has successfully populated in Budget_group,budget_by_category and budget_goal tables and Is_Deleted column set to zero in all tables");
					String query5 = "select * from budget_goal where budget_goal_id ='" + BUDGET_GOAL_ID.get(i) + "'";
					System.out.println("QUERY IS :::: " + query5);
					rs = statement.executeQuery(query5);
					while (rs.next()) {
						GOAL_AMOUNT_CURR_ID.add(rs.getString("GOAL_AMOUNT_CURR_ID"));
						GOAL_AMOUNT.add(rs.getString("GOAL_AMOUNT"));
					}

					String currencyQuery = "select * from currency where CURRENCY_DESC ='" + currency + "'";
					rs = statement.executeQuery(currencyQuery);
					while (rs.next()) {
						CURRENCY_ID.add(rs.getString("CURRENCY_ID"));
					}
					softAssert.assertFalse(GOAL_AMOUNT_CURR_ID.get(i).equals(CURRENCY_ID.get(i)),
							"Budget data has populated in budget_goal table");
					softAssert.assertFalse(GOAL_AMOUNT.get(i).equals(budgetAmount),
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
					softAssert.assertFalse(MONTHLY_GOAL_AMOUNT.get(i).equals("null"),
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
			} else if (budgetType.equals("catType")) {
				String query3 = "select * from budget_group where group_id='" + MEM_HOUSE_HOLDING_GROUP_ID.get(0) + "'";
				System.out.println("QUERY IS :::: " + query3);
				rs = statement.executeQuery(query3);
				while (rs.next()) {
					BUDGET_SUMMARY_ID.add(rs.getString("BUDGET_SUMMARY_ID"));
					BUDGET_SUMMARY_ID_IS_DELETED.add(rs.getString("IS_DELETED"));
					BUDGET_GROUP_ID.add(rs.getString("BUDGET_GROUP_ID"));
					BUDGET_GROUP_IS_DELETED.add(rs.getString("IS_DELETED"));
				}
				try {
					for (int i = 0; i < BUDGET_SUMMARY_ID.size(); i++) {
						String query4 = "select * from budget_summary where budget_summary_id='"
								+ BUDGET_SUMMARY_ID.get(i) + "'";
						System.out.println("QUERY IS :::: " + query4);
						rs = statement.executeQuery(query4);
						softAssert.assertTrue(
								BUDGET_SUMMARY_ID_IS_DELETED.get(i).equals("0")
										&& BUDGET_GROUP_IS_DELETED.get(i).equals("0")
										&& !(BUDGET_GROUP_ID.get(i).equals("null"))
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
				} catch (Exception ex) {
					System.out.println("Budget has not created at summary level........" + ex);
				}
			} else if (budgetType.equals("catDT")) {
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
					softAssert.assertTrue(BUDGET_BY_CATEGORY_IS_DELETED.get(i).equals("0")
							&& BUDGET_GROUP_IS_DELETED.get(i).equals("0") && !(BUDGET_GROUP_ID.get(i).equals("null"))
							&& !(BUDGET_BY_CATEGORY_ID.get(i).equals("null")),
							"Budget data has successfully populated in Budget_group,budget_by_category and budget_goal tables and Is_Deleted column set to zero in all tables");
					String query5 = "select * from budget_goal where budget_goal_id ='" + BUDGET_GOAL_ID.get(i) + "'";
					System.out.println("QUERY IS :::: " + query5);
					rs = statement.executeQuery(query5);
					while (rs.next()) {
						GOAL_AMOUNT_CURR_ID.add(rs.getString("GOAL_AMOUNT_CURR_ID"));
						GOAL_AMOUNT.add(rs.getString("GOAL_AMOUNT"));
					}

					String currencyQuery = "select * from currency where CURRENCY_DESC ='" + currency + "'";
					rs = statement.executeQuery(currencyQuery);
					while (rs.next()) {
						CURRENCY_ID.add(rs.getString("CURRENCY_ID"));
					}
					softAssert.assertFalse(GOAL_AMOUNT_CURR_ID.get(i).equals(CURRENCY_ID.get(i)),
							"Budget data has populated in budget_goal table");
					softAssert.assertFalse(GOAL_AMOUNT.get(i).equals(budgetAmount),
							"Budget data has populated in budget_goal table");
					String query6 = "select * from monthly_budget_goal_history where budget_goal_id ='"
							+ BUDGET_GOAL_ID.get(i) + "'";
					System.out.println("QUERY IS :::: " + query6);
					rs = statement.executeQuery(query6);
					while (rs.next()) {
						MONTHLY_GOAL_AMOUNT_CURR_ID.add(rs.getString("CURRENCY_ID"));
						MONTHLY_GOAL_AMOUNT.add(rs.getString("GOAL_AMOUNT"));
					}
					softAssert.assertFalse(MONTHLY_GOAL_AMOUNT.get(i).equals("null"),
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
				String mem_house = "select * from budget_group where group_id='" + MEM_HOUSE_HOLDING_GROUP_ID.get(0)
						+ "'";
				System.out.println("QUERY IS :::: " + mem_house);
				rs = statement.executeQuery(mem_house);
				while (rs.next()) {
					BUDGET_SUMMARY_ID.add(rs.getString("BUDGET_SUMMARY_ID"));
					BUDGET_SUMMARY_ID_IS_DELETED.add(rs.getString("IS_DELETED"));
					BUDGET_GROUP_ID.add(rs.getString("BUDGET_GROUP_ID"));
					BUDGET_GROUP_IS_DELETED.add(rs.getString("IS_DELETED"));
				}
				try {
					for (int i = 0; i < BUDGET_SUMMARY_ID.size(); i++) {
						String query4 = "select * from budget_summary where budget_summary_id='"
								+ BUDGET_SUMMARY_ID.get(i) + "'";
						System.out.println("QUERY IS :::: " + query4);
						rs = statement.executeQuery(query4);
						softAssert.assertTrue(
								BUDGET_SUMMARY_ID_IS_DELETED.get(i).equals("0")
										&& BUDGET_GROUP_IS_DELETED.get(i).equals("0")
										&& !(BUDGET_GROUP_ID.get(i).equals("null"))
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

				} catch (SQLException ex) {
					ex.printStackTrace();
				} finally {
					dbHelper.closeConnectionStatementResultSet(conn, statement, rs);
				}

			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		// return (GroupDetails);
		return CURRENCY_ID;
	}

	public static String parseDate(String testDate) {
		String[] dateStringArray = testDate.split("-");
		String temp = dateStringArray[0]; // swapping to change the format to yyyy-MM-dd
		dateStringArray[0] = dateStringArray[2];
		dateStringArray[2] = temp;
		String formattedTestDate = String.join("-", dateStringArray[0], dateStringArray[1], dateStringArray[2]); // formatted
																													// to
																													// yyyy-MM-dd
																													// format
		System.out.println("Formatted Date " + formattedTestDate);
		return formattedTestDate;
	}

	public void validateGetBudgetHistoryResponse(Response response, int httpStatusCode, String[] validateIncludeParams,
			HashMap<String, ArrayList<String>> negativeExpectedValuesMap) {
		// String[] validateAttributes=
		// {testCaseId,fromDate,toDate,includeValues,categoryIds,categoryType};
		// transactionsValuesMap =
		// Filters.getTransactionsByFromDateToDate(validateIncludeParams[1],
		// validateIncludeParams[2],transactionsList);
		JsonObject responseObject = (JsonObject) jsonParser.parse(response.asString());
		if (httpStatusCode == response.getStatusCode()) { // 1 if

			if (response.getStatusCode() == 200) { // 2 -if
				JsonObject budgetResponseObj = (JsonObject) jsonParser.parse(response.asString());
				JsonArray budgetHistoryArray = budgetResponseObj.getAsJsonArray("budgetHistory");
				for (int i = 0; i < budgetHistoryArray.size(); i++) {
					JsonObject budgetHistoryObj = (JsonObject) budgetHistoryArray.get(i);
					if (validateIncludeParams[3].equals("categoryTypeData,categoryData")) {
						try {
							budgetHistoryObj.get("groupId");
							budgetHistoryObj.get("groupName");
						} catch (Exception e) {
							Assert.fail("either groupId OR groupName NOT FOUND in the budgetHistory");
						}
						validateCategoryTypeData(budgetHistoryObj, transactionsValuesMap);
						validateCategoryData(budgetHistoryObj);
					}

					if (validateIncludeParams.equals("categoryTypeData")) {
						validateCategoryTypeData(budgetHistoryObj, transactionsValuesMap);

					}
					if (validateIncludeParams.equals("categoryData")) {
						// validateCategoryData(budgetHistoryObj);
					}

				}
			}

			// Negative tests assertion
			String testCaseId = validateIncludeParams[0];
			if (response.getStatusCode() == 400) {
				if (Integer.parseInt(negativeExpectedValuesMap.get(testCaseId).get(0)) == httpStatusCode) {
					if (!responseObject.get("errorCode").getAsString()
							.equals(negativeExpectedValuesMap.get(testCaseId).get(1))) {
						Assert.fail(testCaseId + "  Failed errorCode doesn't match... Expected errorCode : "
								+ negativeExpectedValuesMap.get(testCaseId).get(1) + " Actual errorCode : "
								+ responseObject.get("errorCode").getAsString());
					}
					if (!responseObject.get("errorMessage").getAsString()
							.equals(negativeExpectedValuesMap.get(testCaseId).get(2))) {
						Assert.fail(testCaseId + "  Failed errorMessage doesn't match... Expected errorMessage :  "
								+ negativeExpectedValuesMap.get(testCaseId).get(2) + " Actual errorMessage  : "
								+ responseObject.get("errorMessage").getAsString());
					}
				}
			}

		}

	}

	public HashMap<String, ArrayList<String>> readNegativeExpectedValues() {
		HashMap<String, ArrayList<String>> negativeTestValuesMap = new HashMap<String, ArrayList<String>>();
		ArrayList<String> negativeTestList;
		try {

			JsonArray negativeTestsArr = (JsonArray) jsonParser.parse(new FileReader(System.getProperty("user.dir")
					+ "\\src\\main\\java\\com\\yodlee\\insights\\yodleeApi\\utils\\ExpenseNegativeExpectedVals.json"));
			for (Object negativeTest : negativeTestsArr) {
				negativeTestList = new ArrayList<>();
				JsonObject negativeExpense = (JsonObject) negativeTest;
				String testCaseId = negativeExpense.get("testCaseId").getAsString();
				String statusCode = negativeExpense.get("httpStatuCode").getAsString();
				String yslErrorCode = negativeExpense.get("yslErrorCode").getAsString();
				String errorMessage = negativeExpense.get("errorMessage").getAsString();
				negativeTestList.add(statusCode);
				negativeTestList.add(yslErrorCode);
				negativeTestList.add(errorMessage);
				negativeTestValuesMap.put(testCaseId, negativeTestList);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return negativeTestValuesMap;
	}

	public void getBudgetTrendsQueryDB(String userName) throws SQLException {
		DBHelper dbHelper = new DBHelper();
		Connection connection = dbHelper.getDBConnection();
		try {
			String getCategoryTypeData = "select mbh.budget_goal_id,mbh.goal_amount,bbc.category_level_id,bbc.transaction_category_id ,bbc.row_created from monthly_budget_goal_history mbh inner join budget_by_category bbc on mbh.budget_goal_id=bbc.budget_goal_id where bbc.budget_goal_id in(select budget_goal_id from budget_by_category where budget_by_category_id in (select budget_by_category_id from budget_group where group_id in (select  mem_house_holding_group_id from mem_house_holding_group where mem_id=(select mem_id from mem where login_name='budgettest104'))))";
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(getCategoryTypeData);
			while (resultSet.next()) {
				String budgetGoalId = resultSet.getString("budget_goal_id");
				int goal_Amount = resultSet.getInt("goal_amount");
				int categoryLevelId = resultSet.getInt("category_level_id");
				int transactionCatId = resultSet.getInt("transaction_category_id");
				Date rowCreated = resultSet.getTimestamp("row_created");

			}
		} finally {
			connection.close();

		}

	}

	public void validateCategoryTypeData(JsonObject budgetHistoryObj, String[] transactionsValuesMap) {
		JsonArray categoryTypeDataArr = budgetHistoryObj.getAsJsonArray("categoryTypeData");
		String categoryType = null;
		for (int j = 0; j < categoryTypeDataArr.size(); j++) {
			JsonObject categoryTypeDataObj = (JsonObject) categoryTypeDataArr.get(j);
			categoryType = categoryTypeDataObj.get("categoryType").getAsString();
			if (categoryType.equals("INCOME") || categoryType.equals("EXPENSE") || categoryType.equals("TRANSFER")) {
				JsonArray historyArr = categoryTypeDataObj.getAsJsonArray("history");
				for (int k = 0; k < historyArr.size(); k++) {
					JsonObject historyObj = (JsonObject) historyArr.get(k);
					String budgetedDate = historyObj.get("date").getAsString();
					int budgetedAmount = ((JsonObject) historyObj.get("budgetAmount")).get("amount").getAsInt();
					if (budgetCategoryTypeDetailsMap.get(categoryType).containsKey(budgetedDate)) {
						if (!(budgetCategoryTypeDetailsMap.get(categoryType).get(budgetedDate)
								.intValue() == budgetedAmount)) {
							Assert.fail("Budget amount doesn't match");
						}
						System.out.println("Verifying Budget Amount for category types.......SUCCESS");
					}
					String budgetedAmountCurrency = ((JsonObject) historyObj.get("budgetAmount")).get("currency")
							.getAsString();
					int creditTotalAmount = ((JsonObject) historyObj.get("creditTotal")).get("amount").getAsInt();
					String creditTotalAmountCurrency = ((JsonObject) historyObj.get("creditTotal")).get("currency")
							.getAsString();
					int debitTotalAmount = ((JsonObject) historyObj.get("debitTotal")).get("amount").getAsInt();
					String debitTotalAmountCurrency = ((JsonObject) historyObj.get("debitTotal")).get("currency")
							.getAsString();
				}
			} else
				Assert.fail("Incorrect category Type.....");

		}

	}

	public void validateCategoryData(JsonObject budgetHistoryObj) {

		JsonArray categoryDataArr = budgetHistoryObj.getAsJsonArray("categoryData");
		for (int j = 0; j < categoryDataArr.size(); j++) {
			JsonObject categoryDataObj = (JsonObject) categoryDataArr.get(j);
			int categoryId = categoryDataObj.get("categoryId").getAsInt();
			String categoryName = categoryDataObj.get("categoryName").getAsString();
			String categoryType = categoryDataObj.get("categoryType").getAsString();
			if (budgetCategoryTypeDetailsMap.containsKey(Integer.toString(categoryId))) {
				JsonArray historyArr = categoryDataObj.getAsJsonArray("history");
				for (int k = 0; k < historyArr.size(); k++) {
					JsonObject historyObj = (JsonObject) historyArr.get(k);
					String budgetedDate = historyObj.get("date").getAsString();
					int budgetedAmount = ((JsonObject) historyObj.get("budgetAmount")).get("amount").getAsInt();
					if (budgetCategoryTypeDetailsMap.get(Integer.toString(categoryId)).containsKey(budgetedDate)) {
						if (!(budgetCategoryTypeDetailsMap.get(Integer.toString(categoryId)).get(budgetedDate)
								.intValue() == budgetedAmount)) {
							Assert.fail("Budget amount doesn't match");
						}
						System.out.println("Verifying Budget Amount for categories.......SUCCESS");
					}
					String budgetedAmountCurrency = ((JsonObject) historyObj.get("budgetAmount")).get("currency")
							.getAsString();
					int creditTotalAmount = ((JsonObject) historyObj.get("creditTotal")).get("amount").getAsInt();
					String creditTotalAmountCurrency = ((JsonObject) historyObj.get("creditTotal")).get("currency")
							.getAsString();
					int debitTotalAmount = ((JsonObject) historyObj.get("debitTotal")).get("amount").getAsInt();
					String debitTotalAmountCurrency = ((JsonObject) historyObj.get("debitTotal")).get("currency")
							.getAsString();
				}
			} else
				Assert.fail("Incorrect category Id.....");
		}
	}

	public static ArrayList<Long> getItemAccountsOfContainers(Session sessionObj) {
		logger.info("Fetching Added accounts Information.....");
		AccountUtils accountUtils = new AccountUtils();
		Response getAccountsResponse = null;
		HashMap<String, ArrayList<Long>> containerAccountsMap = new HashMap<String, ArrayList<Long>>();
		/*
		 * ArrayList<Long> bankItemAccountsList = new ArrayList<Long>(); ArrayList<Long>
		 * cardItemAccountsList = new ArrayList<Long>(); ArrayList<Long>
		 * stocksItemAccountsList = new ArrayList<Long>();
		 */
		ArrayList<Long> itemAccountsList = new ArrayList<Long>();
		JSONObject containersObj = null, bankObject = null, creditCardObject = null, investmentObject = null;
		JSONArray containersObjArray = null;
		String[] containersArray = { "Bank", "Card", "Stocks" };
		getAccountsResponse = accountUtils.getAllAccounts(sessionObj);
		getAccountsResponse.then().log().all();
		Container containers = new Container();
		try {
			containersObj = new JSONObject(getAccountsResponse.asString());
			System.out.println("Bank Object : " + containersObj.toString());
			containersObjArray = containersObj.getJSONArray(containers.JSON_PATH_ACCOUNT);
			for (int count = 0; count < containersObjArray.length(); count++) {
				String container = containersObjArray.getJSONObject(count).getString("CONTAINER");
				Long accountId = containersObjArray.getJSONObject(count).getLong(containers.ID);
				if (container.equalsIgnoreCase("bank"))
					itemAccountsList.add(accountId);
				if (container.equalsIgnoreCase("creditCard"))
					itemAccountsList.add(accountId);
				if (container.equalsIgnoreCase("investment"))
					itemAccountsList.add(accountId);
			}
			/*
			 * containerAccountsMap.put(containersArray[0], bankItemAccountsList);
			 * containerAccountsMap.put(containersArray[1], cardItemAccountsList);
			 * containerAccountsMap.put(containersArray[2], stocksItemAccountsList);
			 */

		} catch (JSONException e) {
			logger.error("Failed to convert get accounts response to JSONObject : " + e);
			Assert.fail("Failed to create JSONObject of account response.");
		}
		return itemAccountsList;
	}

	public String prepDataToGetBudgetGoalHistory(Session sessionObj, String userName,
			ArrayList<Long> itemAccountsList) {

		String accountGroupId = null;
		Connection conn = dbHelper.getDBConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		ArrayList<Long> budgetGoalIdList = new ArrayList<Long>();
		// String getItemAccountQuery="SELECT item_account_id FROM item_account WHERE
		// cache_item_id IN (SELECT cache_item_id FROM mem_item WHERE mem_id=(SELECT
		// mem_id FROM mem WHERE login_name ='"+userName+"'))";
		String getBudgetDetailsCategory = "select mbh.budget_goal_id,mbh.goal_amount,bbc.category_level_id,bbc.transaction_category_id ,bbc.row_created from monthly_budget_goal_history mbh inner join budget_by_category bbc on mbh.budget_goal_id=bbc.budget_goal_id where bbc.budget_goal_id in(select budget_goal_id from budget_by_category where budget_by_category_id in (select budget_by_category_id from budget_group where group_id in (select  mem_house_holding_group_id from mem_house_holding_group where mem_id=(select mem_id from mem where login_name='"
				+ userName + "'))))";
		String getBudgetDetailsCategoryType = "select mbh.budget_goal_id,mbh.goal_amount,bs.transaction_category_type_id,mbh.row_created,bs.row_last_updated from monthly_budget_goal_history mbh inner join budget_summary bs on mbh.budget_goal_id=bs.budget_goal_id where bs.budget_goal_id in(select budget_goal_id from budget_summary where budget_groups_id in (select budget_groups_id from budget_groups where mem_house_holding_group_Id  in (select  mem_house_holding_group_id from mem_house_holding_group where mem_id=(select mem_id from mem where login_name='"
				+ userName + "'))))";
		try {

			// create account group
			String addAccountGroupBodyParam = addAccountGroupJsonAccounts(itemAccountsList.get(0));
			HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
			httpMethodParameters.setBodyParams(addAccountGroupBodyParam);
			Response response = new GroupUtils().createGroup(httpMethodParameters, sessionObj);
			JsonObject obj = (JsonObject) jsonParser.parse(response.asString());
			JsonObject grpObj = obj.getAsJsonObject("group");
			accountGroupId = grpObj.get("id").getAsString();

			// create budget
			String createbudgetAccountBodyParam = createBudgetForCatTypeAndCategories(accountGroupId, categoryIds,
					categoryTypes);
			HttpMethodParameters httpMethodParams = HttpMethodParameters.builder().build();
			httpMethodParams.setBodyParams(createbudgetAccountBodyParam);
			Response createBudgetResponse = new BudgetUtils().createBudget(httpMethodParams, sessionObj);
			System.out.println(" create budget response " + createBudgetResponse.asString());
			System.out.println("Account Group Id " + accountGroupId);

			// CategoryType budget details
			// int index=2;
			statement = conn.createStatement();
			resultSet = statement.executeQuery(getBudgetDetailsCategoryType);
			while (resultSet.next()) {
				Long budget_goal_id = resultSet.getLong("BUDGET_GOAL_ID");
				budgetGoalIdList.add(budget_goal_id);
			}

			resultSet.close();

			// Category budget details

			resultSet = statement.executeQuery(getBudgetDetailsCategory);
			while (resultSet.next()) {
				Long budget_goal_id = resultSet.getLong("BUDGET_GOAL_ID");
				budgetGoalIdList.add(budget_goal_id);
			}

			// update created budgets[Today] to the past dates - 62 [59 category Ids and 3
			// categoryTypes]
			int count = 1;
			for (int i = 0; i < 62; i += 5) {
				int numberOfPastDays = 50 * count;
				for (int j = 0; j < 5; j++) {
					if ((i + j) < 62) {
						String updateBudgetGoalsToPast = "update monthly_budget_goal_history set row_created=row_created-"
								+ numberOfPastDays + ",row_last_updated=row_last_updated-" + numberOfPastDays
								+ " where budget_goal_id in (" + budgetGoalIdList.get(i + j).toString() + ")";
						statement.executeQuery(updateBudgetGoalsToPast);
						conn.commit();
						numberOfPastDays += 2;
					} else {
						break;
					}
				}
				count++;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbHelper.closeConnectionStatementResultSet(conn, statement, resultSet);
		}

		getLatestBudgetDetailsForCategoryType(userName);

		return accountGroupId;

	}

	public void getLatestBudgetDetailsForCategoryType(String userName) {
		// Income,Expense,Transfer month,goal_amount
		// Map<ArrayList, HashMap<String,Integer>> budgetCategoryTypeDetailsMap;
		// userName="BudgetYSL1557631433275";
		budgetCategoryTypeDetailsMap = new HashMap<String, HashMap<String, Integer>>();
		HashMap<String, Integer> budgetMonthGoalAmountMap = null;
		Connection conn = dbHelper.getDBConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		String getBudgetDetailsCategoryType = "select mbh.budget_goal_id,mbh.goal_amount,bs.transaction_category_type_id,mbh.row_created,bs.row_last_updated,mbh.is_deleted from monthly_budget_goal_history mbh inner join budget_summary bs on mbh.budget_goal_id=bs.budget_goal_id where bs.budget_goal_id in(select budget_goal_id from budget_summary where budget_groups_id in (select budget_groups_id from budget_groups where mem_house_holding_group_Id  in (select  mem_house_holding_group_id from mem_house_holding_group where mem_id=(select mem_id from mem where login_name='"
				+ userName + "')))) order by mbh.row_created asc";
		try {
			// CategoryType budget details
			statement = conn.createStatement();
			resultSet = statement.executeQuery(getBudgetDetailsCategoryType);
			while (resultSet.next()) {
				int transaction_category_type_id = resultSet.getInt("TRANSACTION_CATEGORY_TYPE_ID");
				Date row_created = resultSet.getDate("ROW_CREATED");
				String rowCreated = row_created.toString().substring(0, 7);
				int is_deleted = resultSet.getInt("IS_DELETED");
				int budget_goal_amount = resultSet.getInt("GOAL_AMOUNT");
				budgetMonthGoalAmountMap = new HashMap<String, Integer>();
				budgetMonthGoalAmountMap.put(rowCreated, budget_goal_amount);
				if (transaction_category_type_id == 2) {
					if (!budgetCategoryTypeDetailsMap.containsKey("INCOME")) {
						budgetCategoryTypeDetailsMap.put("INCOME", budgetMonthGoalAmountMap);
					}
					budgetCategoryTypeDetailsMap.get("INCOME").put(rowCreated, budget_goal_amount);
				}

				if (transaction_category_type_id == 3) {
					if (!budgetCategoryTypeDetailsMap.containsKey("EXPENSE")) {
						budgetCategoryTypeDetailsMap.put("EXPENSE", budgetMonthGoalAmountMap);
					}
					budgetCategoryTypeDetailsMap.get("EXPENSE").put(rowCreated, budget_goal_amount);
				}
				if (transaction_category_type_id == 4) {
					if (!budgetCategoryTypeDetailsMap.containsKey("TRANSFER")) {
						budgetCategoryTypeDetailsMap.put("TRANSFER", budgetMonthGoalAmountMap);
					}
					budgetCategoryTypeDetailsMap.get("TRANSFER").put(rowCreated, budget_goal_amount);
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// CategoryData

		String getBudgetDetailsCategory = "select mbh.budget_goal_id,mbh.goal_amount,bbc.category_level_id,bbc.transaction_category_id ,bbc.row_created , mbh.is_deleted from monthly_budget_goal_history mbh inner join budget_by_category bbc on mbh.budget_goal_id=bbc.budget_goal_id where bbc.budget_goal_id in(select budget_goal_id from budget_by_category where budget_by_category_id in (select budget_by_category_id from budget_group where group_id in (select  mem_house_holding_group_id from mem_house_holding_group where mem_id=(select mem_id from mem where login_name='"
				+ userName + "')))) order by mbh.row_created asc";
		try {
			// CategoryType budget details
			resultSet = statement.executeQuery(getBudgetDetailsCategory);
			while (resultSet.next()) {
				int transaction_category_id = resultSet.getInt("TRANSACTION_CATEGORY_ID");
				Date row_created = resultSet.getDate("ROW_CREATED");
				String rowCreated = row_created.toString().substring(0, 7);
				int is_deleted = resultSet.getInt("IS_DELETED");
				int budget_goal_amount = resultSet.getInt("GOAL_AMOUNT");
				budgetMonthGoalAmountMap = new HashMap<String, Integer>();
				budgetMonthGoalAmountMap.put(rowCreated, budget_goal_amount);

				if (!budgetCategoryTypeDetailsMap.containsKey(Integer.toString(transaction_category_id))) {
					budgetCategoryTypeDetailsMap.put(Integer.toString(transaction_category_id),
							budgetMonthGoalAmountMap);
				}
				budgetCategoryTypeDetailsMap.get(Integer.toString(transaction_category_id)).put(rowCreated,
						budget_goal_amount);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			dbHelper.closeConnectionStatementResultSet(conn, statement, resultSet);
		}

	}

	public String addAccountGroupJsonAccounts(Long accountID) throws JSONException {
		JSONObject grpObjid = new JSONObject();
		JSONObject grpObjaccount = new JSONObject();
		JSONObject grpObjgroup = new JSONObject();
		JSONArray grpObjAccArray = new JSONArray();
		JSONArray grpObjGroupArray = new JSONArray();

		grpObjid.put("id", accountID);
		grpObjAccArray.put(grpObjid);

		grpObjaccount.put("account", grpObjAccArray);
		grpObjaccount.put("name", "KempBudgetGroup1");
		grpObjGroupArray.put(grpObjaccount);

		grpObjgroup.put("group", grpObjGroupArray);

		String addGroupObjson = grpObjgroup.toString();
		System.out.println("grpObjgroup 1 is : " + addGroupObjson);
		return addGroupObjson;
	}

	public String createBudgetForCatTypeAndCategories(String groupId, int[] categoryIdsList, String[] categoryTypeList)
			throws JSONException {
		double catBudgetAmt = 10000.3735373537;
		JSONObject reqObject = new JSONObject();
		JSONObject budgetObject = new JSONObject();
		JSONArray budgetArray = new JSONArray();
		budgetObject.put("groupId", groupId);
		// Category Type array
		JSONArray categoryTypeArray = new JSONArray();
		double catTypeBudgetAmt = 5999.121432325452;
		for (int i = 0; i < categoryTypeList.length; i++) {
			JSONObject catObject = new JSONObject();
			catObject.put("categoryType", categoryTypeList[i]);
			JSONObject budgetTotalObj = new JSONObject();
			budgetTotalObj.put("amount", catTypeBudgetAmt);
			budgetTotalObj.put("currency", "USD");
			catObject.put("budgetAmount", budgetTotalObj);
			categoryTypeArray.put(catObject);
			catTypeBudgetAmt -= 500;
		}
		budgetObject.put("categoryTypeData", categoryTypeArray);
		budgetArray.put(budgetObject);

		// Category Data array
		JSONArray categoryDataArray = new JSONArray();
		for (int i = 0; i < categoryIdsList.length; i++) {
			JSONObject catTypeObject = new JSONObject();
			catTypeObject.put("categoryId", categoryIdsList[i]);

			JSONObject budgetTotalObj = new JSONObject();
			budgetTotalObj.put("amount", Double.toString(catBudgetAmt + categoryIdsList[i]));
			budgetTotalObj.put("currency", "USD");
			catTypeObject.put("budgetAmount", budgetTotalObj);

			categoryDataArray.put(catTypeObject);
		}
		budgetObject.put("categoryData", categoryDataArray);

		reqObject.put("budget", budgetArray);

		return reqObject.toString();
	}

	public static HttpMethodParameters createGroupBodyParam(String groupName, List<Integer> listname) {
		JSONObject parentObj = new JSONObject();
		JSONObject groupObj = new JSONObject();
		JSONArray groupArrObj = new JSONArray();
		JSONArray accountArrObj = new JSONArray();

		for (int i = 0; i < listname.size(); i++) {
			JSONObject accountObj = new JSONObject();
			accountObj.put("id", listname.get(i));
			accountArrObj.put(accountObj);
		}

		groupObj.put("name", groupName);
		groupObj.put("account", accountArrObj);
		groupArrObj.put(groupObj);
		parentObj.put("group", groupArrObj);

		String createGroupBodyParam = parentObj.toString();
		HttpMethodParameters param = HttpMethodParameters.builder().build();
		param.setBodyParams(createGroupBodyParam);
		return param;
	}

	public List<Integer> getItemAccountIds(String UserName, String containerTypeId) {
		List<Integer> itemAccountIdList = new ArrayList<>();
		String containerIds = containerTypeId;

		DBHelper dbHelper = new DBHelper();
		Connection conn = dbHelper.getDBConnection();
		Statement st = null;
		ResultSet rs = null;

		String getItemAccountIdQuery = "select item_account_id from item_account where cache_item_id in (select cache_item_id from mem_item where mem_id in (select mem_id from mem where login_name='"
				+ UserName + "')) and item_data_table_id in(" + containerIds + ") order by item_account_id asc";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(getItemAccountIdQuery);
			while (rs.next()) {
				itemAccountIdList.add(rs.getInt("item_account_id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			dbHelper.closeConnectionStatementResultSet(conn, st, rs);
		}
		return itemAccountIdList;
	}

	@SuppressWarnings("resource")
	public static ArrayList<String> verifyBudgeatAtDBGoal(String userName, String currency, String budgetType,
			List<String> budgetAmount, boolean newCatTYpe) {
		SoftAssert softAssert = new SoftAssert();
		String Mem_Id = null;
		ArrayList<String> MEM_HOUSE_HOLDING_GROUP_ID = new ArrayList<String>();
		ArrayList<String> BUDGET_BY_CATEGORY_ID = new ArrayList<String>();
		ArrayList<String> BUDGET_BY_CATEGORY_IS_DELETED = new ArrayList<String>();
		ArrayList<String> BUDGET_SUMMARY_ID = new ArrayList<String>();
		ArrayList<String> BUDGET_SUMMARY_ID_IS_DELETED = new ArrayList<String>();
		ArrayList<String> BUDGET_GOAL_ID = new ArrayList<String>();
		ArrayList<String> GOAL_AMOUNT_CURR_ID = new ArrayList<String>();
		ArrayList<String> MONTHLY_GOAL_AMOUNT_CURR_ID = new ArrayList<String>();
		ArrayList<String> CURRENCY_ID = new ArrayList<String>();
		ArrayList<String> GOAL_AMOUNT = new ArrayList<String>();
		ArrayList<String> MONTHLY_GOAL_AMOUNT = new ArrayList<String>();
		ArrayList<String> BUDGET_GROUP_ID = new ArrayList<String>();
		ArrayList<String> BUDGET_GROUP_IS_DELETED = new ArrayList<String>();
		ArrayList<String> BUDGET_GROUPS = new ArrayList<String>();
		ArrayList<String> BUDGET_GROUPS1 = new ArrayList<String>();
		ArrayList<String> TRANSACTION_CATEGORY_TYPE_ID = new ArrayList<String>();
		ArrayList<String> BUDGET_GROUPS_IS_DELETED = new ArrayList<String>();
		Statement statement = null;
		ResultSet rs = null;
		DBHelper dbHelper = new DBHelper();

		Connection conn = dbHelper.getDBConnection();
		try {
			String query1 = "select * from MEM where Login_name='" + userName + "'";
			statement = conn.createStatement();
			rs = statement.executeQuery(query1);
			while (rs.next()) {
				Mem_Id = rs.getString("MEM_ID");
			}
			String query2 = "select * from budget_groups where mem_id='" + Mem_Id + "'";
			System.out.println("QUERY IS :::: " + query2);
			rs = statement.executeQuery(query2);
			while (rs.next()) {
				MEM_HOUSE_HOLDING_GROUP_ID.add(rs.getString("MEM_HOUSE_HOLDING_GROUP_ID"));
			}
			if (budgetType.equals("catData")) {
				String query3 = "select * from budget_groups where mem_house_holding_group_id='"
						+ MEM_HOUSE_HOLDING_GROUP_ID.get(0) + "'";
				System.out.println("QUERY IS :::: " + query3);
				rs = statement.executeQuery(query3);
				while (rs.next()) {
					BUDGET_GROUPS1.add(rs.getString("budget_groups_id"));
				}
				String query33 = "select * from budget_by_category  where budget_groups_id='" + BUDGET_GROUPS1.get(0)
						+ "'";
				System.out.println("QUERY IS :::: " + query33);
				rs = statement.executeQuery(query33);
				while (rs.next()) {
					BUDGET_BY_CATEGORY_ID.add(rs.getString("BUDGET_BY_CATEGORY_ID"));
					BUDGET_BY_CATEGORY_IS_DELETED.add(rs.getString("IS_DELETED"));
					BUDGET_GROUP_ID.add(rs.getString("BUDGET_GOAL_ID"));
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
					softAssert.assertTrue(BUDGET_BY_CATEGORY_IS_DELETED.get(i).equals("0")
							&& BUDGET_GROUP_IS_DELETED.get(i).equals("0") && !(BUDGET_GROUP_ID.get(i).equals("null"))
							&& !(BUDGET_BY_CATEGORY_ID.get(i).equals("null")),
							"Budget data has successfully populated in Budget_group,budget_by_category and budget_goal tables and Is_Deleted column set to zero in all tables");
					String query5 = "select * from budget_goal where budget_goal_id ='" + BUDGET_GOAL_ID.get(i) + "'";
					System.out.println("QUERY IS :::: " + query5);
					rs = statement.executeQuery(query5);
					while (rs.next()) {
						GOAL_AMOUNT_CURR_ID.add(rs.getString("GOAL_AMOUNT_CURR_ID"));
						GOAL_AMOUNT.add(rs.getString("GOAL_AMOUNT"));
					}

					String currencyQuery = "select * from currency where CURRENCY_DESC ='" + currency + "'";
					rs = statement.executeQuery(currencyQuery);
					while (rs.next()) {
						CURRENCY_ID.add(rs.getString("CURRENCY_ID"));
					}
					softAssert.assertFalse(GOAL_AMOUNT_CURR_ID.get(i).equals(CURRENCY_ID.get(i)),
							"Budget data has populated in budget_goal table");
					softAssert.assertFalse(GOAL_AMOUNT.get(i).equals(budgetAmount.get(i)),
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
					softAssert.assertFalse(MONTHLY_GOAL_AMOUNT.get(i).equals("null"),
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
			} else if (budgetType.equals("catType")) {
				String query3 = "select * from budget_groups where mem_house_holding_group_id='"
						+ MEM_HOUSE_HOLDING_GROUP_ID.get(0) + "'";
				System.out.println("QUERY IS :::: " + query3);
				rs = statement.executeQuery(query3);
				while (rs.next()) {
					BUDGET_GROUPS1.add(rs.getString("budget_groups_id"));
				}
				String query33 = "select * from budget_summary  where budget_groups_id='" + BUDGET_GROUPS1.get(0) + "'";
				System.out.println("QUERY IS :::: " + query33);
				rs = statement.executeQuery(query33);
				while (rs.next()) {
					BUDGET_SUMMARY_ID.add(rs.getString("BUDGET_SUMMARY_ID"));
					BUDGET_SUMMARY_ID_IS_DELETED.add(rs.getString("IS_DELETED"));
					BUDGET_GOAL_ID.add(rs.getString("BUDGET_GOAL_ID"));
					BUDGET_GROUP_IS_DELETED.add(rs.getString("IS_DELETED"));
				}
				try {
					for (int i = 0; i < BUDGET_SUMMARY_ID.size(); i++) {
						String query4 = "select * from budget_summary where budget_summary_id='"
								+ BUDGET_SUMMARY_ID.get(i) + "'";
						System.out.println("QUERY IS :::: " + query4);
						rs = statement.executeQuery(query4);
						softAssert.assertTrue(BUDGET_SUMMARY_ID_IS_DELETED.get(i).equals("0")
								&& BUDGET_GROUP_IS_DELETED.get(i).equals("0") && !(BUDGET_GOAL_ID.get(i).equals("null"))
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
						softAssert.assertFalse(MONTHLY_GOAL_AMOUNT_CURR_ID.get(i).equals(budgetAmount.get(i)),
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
				} catch (Exception ex) {
					System.out.println("Budget has not created at summary level........" + ex);
				}
			} else if (budgetType.equals("catDT")) {
				String query3 = "select * from budget_groups where group_id='" + MEM_HOUSE_HOLDING_GROUP_ID.get(0)
						+ "'";
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
					softAssert.assertTrue(BUDGET_BY_CATEGORY_IS_DELETED.get(i).equals("0")
							&& BUDGET_GROUP_IS_DELETED.get(i).equals("0") && !(BUDGET_GROUP_ID.get(i).equals("null"))
							&& !(BUDGET_BY_CATEGORY_ID.get(i).equals("null")),
							"Budget data has successfully populated in Budget_group,budget_by_category and budget_goal tables and Is_Deleted column set to zero in all tables");
					String query5 = "select * from budget_goal where budget_goal_id ='" + BUDGET_GOAL_ID.get(i) + "'";
					System.out.println("QUERY IS :::: " + query5);
					rs = statement.executeQuery(query5);
					while (rs.next()) {
						GOAL_AMOUNT_CURR_ID.add(rs.getString("GOAL_AMOUNT_CURR_ID"));
						GOAL_AMOUNT.add(rs.getString("GOAL_AMOUNT"));
					}

					String currencyQuery = "select * from currency where CURRENCY_DESC ='" + currency + "'";
					rs = statement.executeQuery(currencyQuery);
					while (rs.next()) {
						CURRENCY_ID.add(rs.getString("CURRENCY_ID"));
					}
					softAssert.assertFalse(GOAL_AMOUNT_CURR_ID.get(i).equals(CURRENCY_ID.get(i)),
							"Budget data has populated in budget_goal table");
					softAssert.assertFalse(GOAL_AMOUNT.get(i).equals(budgetAmount.get(i)),
							"Budget data has populated in budget_goal table");
					String query6 = "select * from monthly_budget_goal_history where budget_goal_id ='"
							+ BUDGET_GOAL_ID.get(i) + "'";
					System.out.println("QUERY IS :::: " + query6);
					rs = statement.executeQuery(query6);
					while (rs.next()) {
						MONTHLY_GOAL_AMOUNT_CURR_ID.add(rs.getString("CURRENCY_ID"));
						MONTHLY_GOAL_AMOUNT.add(rs.getString("GOAL_AMOUNT"));
					}
					softAssert.assertFalse(MONTHLY_GOAL_AMOUNT.get(i).equals("null"),
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
				String mem_house = "select * from budget_group where group_id='" + MEM_HOUSE_HOLDING_GROUP_ID.get(0)
						+ "'";
				System.out.println("QUERY IS :::: " + mem_house);
				rs = statement.executeQuery(mem_house);
				while (rs.next()) {
					BUDGET_SUMMARY_ID.add(rs.getString("BUDGET_SUMMARY_ID"));
					BUDGET_SUMMARY_ID_IS_DELETED.add(rs.getString("IS_DELETED"));
					BUDGET_GROUP_ID.add(rs.getString("BUDGET_GROUP_ID"));
					BUDGET_GROUP_IS_DELETED.add(rs.getString("IS_DELETED"));
				}
				try {
					for (int i = 0; i < BUDGET_SUMMARY_ID.size(); i++) {
						String query4 = "select * from budget_summary where budget_summary_id='"
								+ BUDGET_SUMMARY_ID.get(i) + "'";
						System.out.println("QUERY IS :::: " + query4);
						rs = statement.executeQuery(query4);
						softAssert.assertTrue(
								BUDGET_SUMMARY_ID_IS_DELETED.get(i).equals("0")
										&& BUDGET_GROUP_IS_DELETED.get(i).equals("0")
										&& !(BUDGET_GROUP_ID.get(i).equals("null"))
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

				} catch (SQLException ex) {
					ex.printStackTrace();
				} finally {
					dbHelper.closeConnectionStatementResultSet(conn, statement, rs);
				}

			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		// return (GroupDetails);
		return CURRENCY_ID;
	}

	public void verifyDBData(String userName, String categoryType, String currency, List<String> categoryTypeAmt,
			List<String> goalId) {
		SoftAssert softAssert = new SoftAssert();
		String Mem_Id = null;
		Statement statement = null;
		ResultSet rs = null;
		DBHelper dbHelper = new DBHelper();

		Connection conn = dbHelper.getDBConnection();

		ArrayList<String> budget_groups_id = new ArrayList<String>();
		ArrayList<String> BUDGET_BY_CATEGORY_ID = new ArrayList<String>();
		ArrayList<String> BUDGET_BY_CATEGORY_IS_DELETED = new ArrayList<String>();
		ArrayList<String> BUDGET_GROUP_ID = new ArrayList<String>();
		ArrayList<String> BUDGET_GROUP_IS_DELETED = new ArrayList<String>();
		ArrayList<String> BUDGET_GOAL_ID = new ArrayList<String>();
		ArrayList<String> GOAL_AMOUNT_CURR_ID = new ArrayList<String>();
		ArrayList<String> GOAL_AMOUNT = new ArrayList<String>();
		ArrayList<String> CURRENCY_ID = new ArrayList<String>();
		ArrayList<String> MONTHLY_GOAL_AMOUNT_CURR_ID = new ArrayList<String>();
		ArrayList<String> MONTHLY_GOAL_AMOUNT = new ArrayList<String>();
		ArrayList<String> BUDGET_GROUPS = new ArrayList<String>();
		ArrayList<String> BUDGET_GROUPS_IS_DELETED = new ArrayList<String>();
		ArrayList<String> BUDGET_SUMMARY_ID = new ArrayList<String>();
		ArrayList<String> BUDGET_SUMMARY_ID_IS_DELETED = new ArrayList<String>();
		ArrayList<String> TRANSACTION_CATEGORY_TYPE_ID = new ArrayList<String>();
		ArrayList<String> USER_GOAL_ID = new ArrayList<String>();

		try {
			String query1 = "select * from MEM where Login_name='" + userName + "'";
			statement = conn.createStatement();
			rs = statement.executeQuery(query1);
			while (rs.next()) {
				Mem_Id = rs.getString("MEM_ID");
			}
			String query2 = "select * from budget_groups where mem_id='" + Mem_Id + "'";
			System.out.println("QUERY IS :::: " + query2);
			rs = statement.executeQuery(query2);
			while (rs.next()) {
				budget_groups_id.add(rs.getString("budget_groups_id"));
			}

			if (categoryType.equals("catData")) {

				String query33 = "select * from budget_by_category  where budget_groups_id='" + budget_groups_id.get(0)
						+ "'";
				System.out.println("QUERY IS :::: " + query33);
				rs = statement.executeQuery(query33);
				while (rs.next()) {
					BUDGET_BY_CATEGORY_ID.add(rs.getString("BUDGET_BY_CATEGORY_ID"));
					BUDGET_BY_CATEGORY_IS_DELETED.add(rs.getString("IS_DELETED"));
					BUDGET_GROUP_ID.add(rs.getString("BUDGET_GOAL_ID"));
					BUDGET_GROUP_IS_DELETED.add(rs.getString("IS_DELETED"));
				}
				for (int i = 0; i < BUDGET_BY_CATEGORY_ID.size(); i++) {
					String query22 = "select * from budget_grps_user_goal_map where budget_groups_id='"
							+ budget_groups_id.get(0) + "'";
					System.out.println("QUERY IS :::: " + query22);
					rs = statement.executeQuery(query22);
					while (rs.next()) {
						USER_GOAL_ID.add(rs.getString("user_goal_id"));
					}
					String query4 = "select * from budget_by_category where budget_by_category_id='"
							+ BUDGET_BY_CATEGORY_ID.get(i) + "'";
					System.out.println("QUERY IS :::: " + query4);
					rs = statement.executeQuery(query4);
					while (rs.next()) {
						BUDGET_GOAL_ID.add(rs.getString("BUDGET_GOAL_ID"));
					}
					softAssert.assertTrue(BUDGET_BY_CATEGORY_IS_DELETED.get(i).equals("0")
							&& BUDGET_GROUP_IS_DELETED.get(i).equals("0") && !(BUDGET_GROUP_ID.get(i).equals("null"))
							&& !(BUDGET_BY_CATEGORY_ID.get(i).equals("null")),
							"Budget data has successfully populated in Budget_group,budget_by_category and budget_goal tables and Is_Deleted column set to zero in all tables");
					softAssert.assertTrue(USER_GOAL_ID.contains(goalId.get(0)));
					softAssert.assertAll();
					String query5 = "select * from budget_goal where budget_goal_id ='" + BUDGET_GOAL_ID.get(i) + "'";
					System.out.println("QUERY IS :::: " + query5);
					rs = statement.executeQuery(query5);
					while (rs.next()) {
						GOAL_AMOUNT_CURR_ID.add(rs.getString("GOAL_AMOUNT_CURR_ID"));
						GOAL_AMOUNT.add(rs.getString("GOAL_AMOUNT"));
					}

					String currencyQuery = "select * from currency where CURRENCY_DESC ='" + currency + "'";
					rs = statement.executeQuery(currencyQuery);
					while (rs.next()) {
						CURRENCY_ID.add(rs.getString("CURRENCY_ID"));
					}
					softAssert.assertTrue(GOAL_AMOUNT_CURR_ID.get(i).equals(CURRENCY_ID.get(i)),
							"Budget data has populated in budget_goal table");
					softAssert.assertTrue(GOAL_AMOUNT.get(i).equals(categoryTypeAmt.get(i)),
							"Budget data has populated in budget_goal table");
					softAssert.assertAll();
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

					softAssert.assertAll();
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
						softAssert.assertAll();
					}
				}
			} else if (categoryType == "catType") {
				String query3 = "select * from budget_summary  where budget_groups_id='" + budget_groups_id.get(0)
						+ "'";
				System.out.println("QUERY IS :::: " + query3);
				rs = statement.executeQuery(query3);
				while (rs.next()) {
					BUDGET_SUMMARY_ID.add(rs.getString("BUDGET_SUMMARY_ID"));
					BUDGET_SUMMARY_ID_IS_DELETED.add(rs.getString("IS_DELETED"));
					BUDGET_GOAL_ID.add(rs.getString("BUDGET_GOAL_ID"));
					BUDGET_GROUP_IS_DELETED.add(rs.getString("IS_DELETED"));
				}

				try {
					for (int i = 0; i < BUDGET_SUMMARY_ID.size(); i++) {
						String query22 = "select * from budget_grps_user_goal_map where budget_groups_id='"
								+ budget_groups_id.get(0) + "'";
						System.out.println("QUERY IS :::: " + query22);
						rs = statement.executeQuery(query22);
						while (rs.next()) {
							USER_GOAL_ID.add(rs.getString("user_goal_id"));
						}
						softAssert.assertTrue(USER_GOAL_ID.contains(goalId.get(0)));

						String query4 = "select * from budget_summary where budget_summary_id='"
								+ BUDGET_SUMMARY_ID.get(i) + "'";
						System.out.println("QUERY IS :::: " + query4);
						rs = statement.executeQuery(query4);
						softAssert.assertTrue(BUDGET_SUMMARY_ID_IS_DELETED.get(i).equals("0")
								&& BUDGET_GROUP_IS_DELETED.get(i).equals("0") && !(BUDGET_GOAL_ID.get(i).equals("null"))
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
						softAssert.assertFalse(MONTHLY_GOAL_AMOUNT_CURR_ID.get(i).equals(categoryTypeAmt.get(i)),
								"Budget data has populated in monthly_budget_goal_history table");
						softAssert.assertAll();
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
				} catch (Exception ex) {
					System.out.println("Budget has not created at summary level........" + ex);
				}
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			dbHelper.closeConnectionStatementResultSet(conn, statement, rs);
		}
		softAssert.assertAll();
	}

	public ArrayList<String> verifyCategoryIdMultpleGoalId(String userName, String categoryType, String currency,
			List<String> categoryTypeAmt, List<String> goalId) {
		SoftAssert softAssert = new SoftAssert();
		String Mem_Id = null;
		Statement statement = null;
		ResultSet rs = null;
		DBHelper dbHelper = new DBHelper();

		Connection conn = dbHelper.getDBConnection();

		ArrayList<String> budget_groups_id = new ArrayList<String>();
		ArrayList<String> BUDGET_BY_CATEGORY_ID = new ArrayList<String>();
		ArrayList<String> BUDGET_BY_CATEGORY_IS_DELETED = new ArrayList<String>();
		ArrayList<String> BUDGET_GROUP_ID = new ArrayList<String>();
		ArrayList<String> BUDGET_GROUP_IS_DELETED = new ArrayList<String>();
		ArrayList<String> BUDGET_GOAL_ID = new ArrayList<String>();
		ArrayList<String> GOAL_AMOUNT_CURR_ID = new ArrayList<String>();
		ArrayList<String> GOAL_AMOUNT = new ArrayList<String>();
		ArrayList<String> CURRENCY_ID = new ArrayList<String>();
		ArrayList<String> MONTHLY_GOAL_AMOUNT_CURR_ID = new ArrayList<String>();
		ArrayList<String> MONTHLY_GOAL_AMOUNT = new ArrayList<String>();
		ArrayList<String> BUDGET_GROUPS = new ArrayList<String>();
		ArrayList<String> BUDGET_GROUPS_IS_DELETED = new ArrayList<String>();
		ArrayList<String> BUDGET_SUMMARY_ID = new ArrayList<String>();
		ArrayList<String> BUDGET_SUMMARY_ID_IS_DELETED = new ArrayList<String>();
		ArrayList<String> TRANSACTION_CATEGORY_TYPE_ID = new ArrayList<String>();
		ArrayList<String> USER_GOAL_ID = new ArrayList<String>();

		try {
			String query1 = "select * from MEM where Login_name='" + userName + "'";
			statement = conn.createStatement();
			rs = statement.executeQuery(query1);
			while (rs.next()) {
				Mem_Id = rs.getString("MEM_ID");
			}
			String query2 = "select * from budget_groups where mem_id='" + Mem_Id + "'";
			System.out.println("QUERY IS :::: " + query2);
			rs = statement.executeQuery(query2);
			while (rs.next()) {
				budget_groups_id.add(rs.getString("budget_groups_id"));
			}

			if (categoryType.equals("catData")) {

				String query33 = "select * from budget_by_category  where budget_groups_id='" + budget_groups_id.get(0)
						+ "'";
				System.out.println("QUERY IS :::: " + query33);
				rs = statement.executeQuery(query33);
				while (rs.next()) {
					BUDGET_BY_CATEGORY_ID.add(rs.getString("BUDGET_BY_CATEGORY_ID"));
					BUDGET_BY_CATEGORY_IS_DELETED.add(rs.getString("IS_DELETED"));
					BUDGET_GROUP_ID.add(rs.getString("BUDGET_GOAL_ID"));
					BUDGET_GROUP_IS_DELETED.add(rs.getString("IS_DELETED"));
				}
				String query22 = "select * from budget_grps_user_goal_map where budget_groups_id='"
						+ budget_groups_id.get(0) + "'";
				System.out.println("QUERY IS :::: " + query22);
				rs = statement.executeQuery(query22);
				while (rs.next()) {
					USER_GOAL_ID.add(rs.getString("user_goal_id"));
				}
				for (int i = 0; i < USER_GOAL_ID.size(); i++) {
					softAssert.assertTrue(USER_GOAL_ID.get(i).equals(goalId.get(i)));

				}
			}

			else if (categoryType.equals("catType")) {

				String query33 = "select * from budget_by_category  where budget_groups_id='" + budget_groups_id.get(0)
						+ "'";
				System.out.println("QUERY IS :::: " + query33);
				rs = statement.executeQuery(query33);
				while (rs.next()) {
					BUDGET_BY_CATEGORY_ID.add(rs.getString("BUDGET_BY_CATEGORY_ID"));
					BUDGET_BY_CATEGORY_IS_DELETED.add(rs.getString("IS_DELETED"));
					BUDGET_GROUP_ID.add(rs.getString("BUDGET_GOAL_ID"));
					BUDGET_GROUP_IS_DELETED.add(rs.getString("IS_DELETED"));
				}
				String query22 = "select * from budget_grps_user_goal_map where budget_groups_id='"
						+ budget_groups_id.get(0) + "'";
				System.out.println("QUERY IS :::: " + query22);
				rs = statement.executeQuery(query22);
				while (rs.next()) {
					USER_GOAL_ID.add(rs.getString("user_goal_id"));
				}
				for (int i = 0; i < USER_GOAL_ID.size(); i++) {
					softAssert.assertTrue(USER_GOAL_ID.get(i).equals(goalId.get(i)));
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			dbHelper.closeConnectionStatementResultSet(conn, statement, rs);
		}
		softAssert.assertAll();
		return USER_GOAL_ID;

	}

	public String CreateBudgetAtCatTypeGoal(String groupId, List<String> categoryType, List<String> budgetAmount,
			String currency, String goalId) throws JSONException {

		JSONObject reqObject = new JSONObject();
		JSONObject budgetObject = new JSONObject();
		JSONArray budgetArray = new JSONArray();
		budgetObject.put("groupId", groupId);
		// Category Type array
		JSONArray categoryTypeArray = new JSONArray();
		for (int i = 0; i < categoryType.size(); i++) {
			JSONObject catObject = new JSONObject();

			catObject.put("categoryType", categoryType.get(i));

			JSONObject budgetTotalObj = new JSONObject();
			budgetTotalObj.put("amount", budgetAmount.get(i));
			budgetTotalObj.put("currency", currency);
			catObject.put("budgetAmount", budgetTotalObj);

			categoryTypeArray.put(catObject);
		}

		JSONArray goalArray = new JSONArray();
		goalArray.put(goalId);

		JSONObject goalObject = new JSONObject();
		goalObject.put("id", goalArray);
		budgetObject.put("categoryTypeData", categoryTypeArray);
		budgetObject.put("goal", goalObject);
		budgetArray.put(budgetObject);

		reqObject.put("budget", budgetArray);
		System.out.println(reqObject);

		return reqObject.toString();

	}

	public String CreateBudgetAtCatDataGoal(String groupId, List<Integer> categoryId, List<String> budgetAmount,
			String currency, List<String> goalId) throws JSONException {

		JSONObject reqObject = new JSONObject();
		JSONArray budgetArray = new JSONArray();

		JSONObject budgetObject = new JSONObject();
		budgetObject.put("groupId", groupId);

		// Category Data array
		JSONArray categoryDataArray = new JSONArray();
		for (int i = 0; i < categoryId.size(); i++) {
			JSONObject catTypeObject = new JSONObject();
			catTypeObject.put("categoryId", categoryId.get(i));

			JSONObject budgetTotalObj = new JSONObject();
			budgetTotalObj.put("amount", budgetAmount.get(i));
			budgetTotalObj.put("currency", currency);
			catTypeObject.put("budgetAmount", budgetTotalObj);

			categoryDataArray.put(catTypeObject);
		}
		JSONArray goalArray = new JSONArray();
		for (int i = 0; i < goalId.size(); i++) {
			goalArray.put(goalId.get(i));
		}
		JSONObject goalObject = new JSONObject();
		goalObject.put("id", goalArray);
		budgetObject.put("categoryData", categoryDataArray);
		budgetObject.put("goal", goalObject);
		budgetArray.put(budgetObject);

		reqObject.put("budget", budgetArray);
		// String test = reqObject.toJSONString();
		return reqObject.toString();
	}

	public String CreateBudgetWithOnlyGOalId(String groupId, List<String> goalId) throws JSONException {

		JSONObject reqObject = new JSONObject();
		JSONArray budgetArray = new JSONArray();

		JSONObject budgetObject = new JSONObject();
		budgetObject.put("groupId", groupId);

		JSONArray goalArray = new JSONArray();
		for (int i = 0; i < goalId.size(); i++) {
			goalArray.put(goalId.get(i));
		}
		JSONObject goalObject = new JSONObject();
		goalObject.put("id", goalArray);
		budgetObject.put("goal", goalObject);
		budgetArray.put(budgetObject);

		reqObject.put("budget", budgetArray);
		// String test = reqObject.toJSONString();
		return reqObject.toString();
	}

	public String CreateCatDataAndCatTypeGoal(String groupId, List<String> categoryType, List<Integer> categoryId,
			String budgetAmount, String currency, List<String> goalId) throws JSONException {

		JSONObject reqObject = new JSONObject();
		JSONObject budgetObject = new JSONObject();
		JSONArray budgetArray = new JSONArray();
		budgetObject.put("groupId", groupId);
		// Category Type array
		JSONArray categoryTypeArray = new JSONArray();
		for (int i = 0; i < categoryType.size(); i++) {
			JSONObject catObject = new JSONObject();
			catObject.put("categoryType", categoryType.get(i));

			JSONObject budgetTotalObj = new JSONObject();
			budgetTotalObj.put("amount", budgetAmount);
			budgetTotalObj.put("currency", currency);
			catObject.put("budgetAmount", budgetTotalObj);

			categoryTypeArray.put(catObject);
		}
		budgetObject.put("categoryTypeData", categoryTypeArray);
		budgetArray.put(budgetObject);

		// Category Data array
		JSONArray categoryDataArray = new JSONArray();
		for (int i = 0; i < categoryId.size(); i++) {
			JSONObject catTypeObject = new JSONObject();
			catTypeObject.put("categoryId", categoryId.get(i));

			JSONObject budgetTotalObj = new JSONObject();
			budgetTotalObj.put("amount", budgetAmount);
			budgetTotalObj.put("currency", currency);
			catTypeObject.put("budgetAmount", budgetTotalObj);

			categoryDataArray.put(catTypeObject);
		}
		JSONArray goalArray = new JSONArray();
		for (int i = 0; i < goalId.size(); i++) {
			goalArray.put(goalId.get(i));
		}
		JSONObject goalObject = new JSONObject();
		goalObject.put("id", goalArray);
		budgetObject.put("goal", goalObject);
		budgetObject.put("categoryData", categoryDataArray);

		reqObject.put("budget", budgetArray);

		return reqObject.toString();
	}

	public String UpdateBudgetAtCatDataGoal(String groupId, List<Integer> categoryId, List<String> budgetAmount,
			String currency, List<String> goalId) throws JSONException {

		JSONObject reqObject = new JSONObject();
		JSONArray budgetArray = new JSONArray();

		JSONObject budgetObject = new JSONObject();
		budgetObject.put("groupId", groupId);

		// Category Data array
		JSONArray categoryDataArray = new JSONArray();
		for (int i = 0; i < categoryId.size(); i++) {
			JSONObject catTypeObject = new JSONObject();
			catTypeObject.put("categoryId", categoryId.get(i));

			JSONObject budgetTotalObj = new JSONObject();
			budgetTotalObj.put("amount", budgetAmount.get(i));
			budgetTotalObj.put("currency", currency);
			catTypeObject.put("budgetAmount", budgetTotalObj);

			categoryDataArray.put(catTypeObject);
		}
		JSONArray goalArray = new JSONArray();
		for (int i = 0; i < goalId.size(); i++) {
			goalArray.put(goalId.get(i));
		}
		JSONObject goalObject = new JSONObject();
		goalObject.put("id", goalArray);
		budgetObject.put("goal", goalObject);
		budgetObject.put("categoryData", categoryDataArray);

		budgetArray.put(budgetObject);

		reqObject.put("budget", budgetArray);

		return reqObject.toString();
	}

	public String UpdateBudgetAtCatTypeGoal(String groupId, List<String> categoryType, List<String> budgetAmount,
			String currency, List<String> goalId) throws JSONException {

		JSONObject reqObject = new JSONObject();
		JSONObject budgetObject = new JSONObject();
		JSONArray budgetArray = new JSONArray();
		budgetObject.put("groupId", groupId);
		// Category Type array
		JSONArray categoryTypeArray = new JSONArray();
		for (int i = 0; i < categoryType.size(); i++) {
			JSONObject catObject = new JSONObject();
			catObject.put("categoryType", categoryType.get(i));

			JSONObject budgetTotalObj = new JSONObject();
			budgetTotalObj.put("amount", budgetAmount.get(i));
			budgetTotalObj.put("currency", currency);
			catObject.put("budgetAmount", budgetTotalObj);

			categoryTypeArray.put(catObject);
		}
		JSONArray goalArray = new JSONArray();
		for (int i = 0; i < goalId.size(); i++) {
			goalArray.put(goalId.get(i));
		}
		JSONObject goalObject = new JSONObject();
		goalObject.put("id", goalArray);
		budgetObject.put("goal", goalObject);
		budgetObject.put("categoryTypeData", categoryTypeArray);
		budgetArray.put(budgetObject);

		reqObject.put("budget", budgetArray);

		return reqObject.toString();
	}

	public String updateCatDataAndCatTypeGoal(String groupId, List<String> categoryType, List<Integer> categoryId,
			String budgetAmount, List<String> listCategoryTypeAmt, String currency, List<String> goalId)
			throws JSONException {

		JSONObject reqObject = new JSONObject();
		JSONObject budgetObject = new JSONObject();
		JSONArray budgetArray = new JSONArray();
		budgetObject.put("groupId", groupId);
		// Category Type array
		JSONArray categoryTypeArray = new JSONArray();
		for (int i = 0; i < categoryType.size(); i++) {
			JSONObject catObject = new JSONObject();
			catObject.put("categoryType", categoryType.get(i));

			JSONObject budgetTotalObj = new JSONObject();
			budgetTotalObj.put("amount", listCategoryTypeAmt.get(i));
			budgetTotalObj.put("currency", currency);
			catObject.put("budgetAmount", budgetTotalObj);

			categoryTypeArray.put(catObject);
		}
		budgetObject.put("categoryTypeData", categoryTypeArray);
		budgetArray.put(budgetObject);

		// Category Data array
		JSONArray categoryDataArray = new JSONArray();
		for (int i = 0; i < categoryId.size(); i++) {
			JSONObject catTypeObject = new JSONObject();
			catTypeObject.put("categoryId", categoryId.get(i));

			JSONObject budgetTotalObj = new JSONObject();
			budgetTotalObj.put("amount", budgetAmount);
			budgetTotalObj.put("currency", currency);
			catTypeObject.put("budgetAmount", budgetTotalObj);

			categoryDataArray.put(catTypeObject);
		}
		JSONArray goalArray = new JSONArray();
		for (int i = 0; i < goalId.size(); i++) {
			goalArray.put(goalId.get(i));
		}
		JSONObject goalObject = new JSONObject();
		goalObject.put("id", goalArray);
		budgetObject.put("goal", goalObject);
		budgetObject.put("categoryData", categoryDataArray);

		reqObject.put("budget", budgetArray);

		return reqObject.toString();
	}

	public String updateBudgetWithOnlyGOalId(String groupId, List<String> goalId) throws JSONException {

		JSONObject reqObject = new JSONObject();
		JSONArray budgetArray = new JSONArray();

		JSONObject budgetObject = new JSONObject();
		if (!groupId.isEmpty()) {
			budgetObject.put("groupId", groupId);
		}

		JSONArray goalArray = new JSONArray();
		for (int i = 0; i < goalId.size(); i++) {
			goalArray.put(goalId.get(i));
		}
		JSONObject goalObject = new JSONObject();
		goalObject.put("id", goalArray);
		budgetObject.put("goal", goalObject);
		budgetArray.put(budgetObject);

		reqObject.put("budget", budgetArray);
		// String test = reqObject.toJSONString();
		return reqObject.toString();
	}

	public void deleteBudgetDbVerification(String userName, int budgetId, String CategoryTypeQueryParam,
			String CategoryIdQueryParam, String goalId, boolean groupDeleted) {
		String Mem_Id = null;
		Statement statement = null;
		ResultSet rs = null;
		DBHelper dbHelper = new DBHelper();
		String isGroupDeleted = null;
		String mem_house_holding_group_id = null;
		List<String> isGoalDeleted = new ArrayList<String>();
		String isMemHouseHoldingDeleted = null;
		// String groupsId=null;
		List<String> isCategoryDeleted = new ArrayList<String>();
		List<String> isCategoryTypeDeleted = new ArrayList<String>();

		Connection conn = dbHelper.getDBConnection();
		String query1 = "select * from MEM where Login_name='" + userName + "'";
		try {
			statement = conn.createStatement();

			rs = statement.executeQuery(query1);
			while (rs.next()) {
				Mem_Id = rs.getString("MEM_ID");
			}
			String query2 = "select * from budget_groups where MEM_ID='" + Mem_Id + "'";
			System.out.println("QUERY IS :::: " + query2);
			rs = statement.executeQuery(query2);
			while (rs.next()) {
				isGroupDeleted = rs.getString("is_deleted");
				mem_house_holding_group_id = rs.getString("mem_house_holding_group_id");
				// groupsId=rs.getString("budget_groups_id");
			}
			String query3 = "select * from budget_grps_user_goal_map where budget_groups_id='" + budgetId + "'";
			System.out.println("QUERY IS :::: " + query3);
			rs = statement.executeQuery(query3);
			while (rs.next()) {
				isGoalDeleted.add(rs.getString("is_deleted"));

			}
			if (!groupDeleted) {
				if (CategoryTypeQueryParam.isEmpty() && !CategoryIdQueryParam.isEmpty()) {
					String query4 = "select * from budget_by_category where budget_groups_id='" + budgetId + "'";
					System.out.println("QUERY IS :::: " + query4);
					rs = statement.executeQuery(query4);
					while (rs.next()) {
						isCategoryDeleted.add(rs.getString("is_deleted"));

					}
					// Assert.assertEquals(isGroupDeleted, "1");
					// Assert.assertEquals(isGoalDeleted, "1");
					// Assert.assertEquals(isMemHouseHoldingDeleted, "1");

					for (int i = 0; i < isCategoryDeleted.size(); i++) {
						Assert.assertEquals(isCategoryDeleted.get(i), "1");
					}
				} else if (CategoryIdQueryParam.isEmpty() && !(CategoryTypeQueryParam.isEmpty())) {
					String query5 = "select * from budget_summary where budget_groups_id='" + budgetId + "'";
					System.out.println("QUERY IS :::: " + query5);
					rs = statement.executeQuery(query5);
					while (rs.next()) {
						isCategoryTypeDeleted.add(rs.getString("is_deleted"));

					}
					for (int i = 0; i < isCategoryTypeDeleted.size(); i++) {
						Assert.assertEquals(isCategoryTypeDeleted.get(i), "1");

					}
				} else {
					// String expectedGoalId[]=goalId.split("");
					for (int i = 0; i < isGoalDeleted.size(); i++) {
						Assert.assertEquals(isGoalDeleted.get(i), "1");

					}
				}
			} else {
				if (CategoryTypeQueryParam.isEmpty()) {
					String query4 = "select * from budget_by_category where budget_groups_id='" + budgetId + "'";
					System.out.println("QUERY IS :::: " + query4);
					rs = statement.executeQuery(query4);
					while (rs.next()) {
						isCategoryDeleted.add(rs.getString("is_deleted"));

					}

					for (int i = 0; i < isCategoryDeleted.size(); i++) {
						Assert.assertEquals(isCategoryDeleted.get(i), "1");
					}
				}
				if (CategoryIdQueryParam.isEmpty()) {
					String query5 = "select * from budget_summary where budget_groups_id='" + budgetId + "'";
					System.out.println("QUERY IS :::: " + query5);
					rs = statement.executeQuery(query5);
					while (rs.next()) {
						isCategoryTypeDeleted.add(rs.getString("is_deleted"));

					}
					for (int i = 0; i < isCategoryTypeDeleted.size(); i++) {
						Assert.assertEquals(isCategoryTypeDeleted.get(i), "1");

					}
				}

				String expectedGoalId[] = goalId.split("");
				for (int i = 0; i < isGoalDeleted.size(); i++) {
					Assert.assertEquals(isGoalDeleted.get(i), "1");

				}
				Assert.assertEquals(isGroupDeleted, "1");

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			dbHelper.closeConnectionStatementResultSet(conn, statement, rs);
		}
	}

	public void createBudgetDbVerification(String userName, int budgetId, List<String> CategoryType,
			List<Integer> CategoryId, List<String> goalId) {
		String Mem_Id = null;
		Statement statement = null;
		ResultSet rs = null;
		DBHelper dbHelper = new DBHelper();
		String isGroupDeleted = null;
		String mem_house_holding_group_id = null;
		List<String> isGoalDeleted = new ArrayList<String>();
		List<String> userGoalId = new ArrayList<String>();
		String isMemHouseHoldingDeleted = null;
		// String groupsId=null;
		List<String> isCategoryDeleted = new ArrayList<String>();
		List<String> isCategoryTypeDeleted = new ArrayList<String>();

		Connection conn = dbHelper.getDBConnection();
		String query1 = "select * from MEM where Login_name='" + userName + "'";
		try {
			statement = conn.createStatement();

			rs = statement.executeQuery(query1);
			while (rs.next()) {
				Mem_Id = rs.getString("MEM_ID");
			}
			String query2 = "select * from budget_groups where MEM_ID='" + Mem_Id + "'";
			System.out.println("QUERY IS :::: " + query2);
			rs = statement.executeQuery(query2);
			while (rs.next()) {
				isGroupDeleted = rs.getString("is_deleted");
				mem_house_holding_group_id = rs.getString("mem_house_holding_group_id");
				// groupsId=rs.getString("budget_groups_id");
			}

			String query3 = "select * from budget_grps_user_goal_map where budget_groups_id='" + budgetId + "'";
			System.out.println("QUERY IS :::: " + query3);
			rs = statement.executeQuery(query3);
			if (goalId != null) {
				while (rs.next()) {
					isGoalDeleted.add(rs.getString("is_deleted"));
					userGoalId.add(rs.getString("user_goal_id"));
				}

				for (int i = 0; i < isGoalDeleted.size(); i++) {
					Assert.assertEquals(isGoalDeleted.get(i), "0");
					Assert.assertTrue(userGoalId.contains(goalId.get(i)));

				}

			} else {
				Assert.assertTrue(rs == null);
			}

			if (CategoryType.isEmpty() && !CategoryId.isEmpty()) {
				String query4 = "select * from budget_by_category where budget_groups_id='" + budgetId + "'";
				System.out.println("QUERY IS :::: " + query4);
				rs = statement.executeQuery(query4);
				while (rs.next()) {
					isCategoryDeleted.add(rs.getString("is_deleted"));

				}

				for (int i = 0; i < isCategoryDeleted.size(); i++) {
					Assert.assertEquals(isCategoryDeleted.get(i), "0");
					Assert.assertTrue(mem_house_holding_group_id != null);
				}
			} else if (CategoryId.isEmpty() && !(CategoryType.isEmpty())) {
				String query5 = "select * from budget_summary where budget_groups_id='" + budgetId + "'";
				System.out.println("QUERY IS :::: " + query5);
				rs = statement.executeQuery(query5);
				while (rs.next()) {
					isCategoryTypeDeleted.add(rs.getString("is_deleted"));

				}
				for (int i = 0; i < isCategoryTypeDeleted.size(); i++) {
					Assert.assertEquals(isCategoryTypeDeleted.get(i), "0");

				}
				Assert.assertTrue(mem_house_holding_group_id != null);
			} else {
				String query4 = "select * from budget_by_category where budget_groups_id='" + budgetId + "'";
				System.out.println("QUERY IS :::: " + query4);
				rs = statement.executeQuery(query4);
				while (rs.next()) {
					isCategoryDeleted.add(rs.getString("is_deleted"));

				}
				for (int i = 0; i < isCategoryDeleted.size(); i++) {
					Assert.assertEquals(isCategoryDeleted.get(i), "0");

					String query5 = "select * from budget_summary where budget_groups_id='" + budgetId + "'";
					System.out.println("QUERY IS :::: " + query5);
					rs = statement.executeQuery(query5);
					while (rs.next()) {
						isCategoryTypeDeleted.add(rs.getString("is_deleted"));

					}
					for (int j = 0; j < isCategoryTypeDeleted.size(); j++) {
						Assert.assertEquals(isCategoryTypeDeleted.get(j), "0");

					}
					Assert.assertTrue(mem_house_holding_group_id != null);
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			dbHelper.closeConnectionStatementResultSet(conn, statement, rs);
		}
	}

	public void updateBudgetDbVerification(String userName, int budgetId, List<String> CategoryType,
			List<Integer> CategoryId, List<String> goalId, List<String> catTypeAmtUpdate,
			List<String> catidAmountUpdate, String currency) {
		String Mem_Id = null;
		Statement statement = null;
		ResultSet rs = null;
		DBHelper dbHelper = new DBHelper();
		String isGroupDeleted = null;
		String mem_house_holding_group_id = null;
		List<String> isGoalDeleted = new ArrayList<String>();
		List<String> userGoalId = new ArrayList<String>();
		String isMemHouseHoldingDeleted = null;
		// String groupsId=null;
		List<String> isCategoryDeleted = new ArrayList<String>();
		List<String> isCategoryTypeDeleted = new ArrayList<String>();
		List<String> BUDGET_GOAL_ID = new ArrayList<String>();
		List<String> BUDGET_GOAL_IDtype = new ArrayList<String>();
		String CURRENCY_ID = null;

		Connection conn = dbHelper.getDBConnection();
		String query1 = "select * from MEM where Login_name='" + userName + "'";
		try {
			statement = conn.createStatement();

			rs = statement.executeQuery(query1);
			while (rs.next()) {
				Mem_Id = rs.getString("MEM_ID");
			}
			String query2 = "select * from budget_groups where MEM_ID='" + Mem_Id + "'";
			System.out.println("QUERY IS :::: " + query2);
			rs = statement.executeQuery(query2);
			while (rs.next()) {
				isGroupDeleted = rs.getString("is_deleted");
				mem_house_holding_group_id = rs.getString("mem_house_holding_group_id");
				// groupsId=rs.getString("budget_groups_id");
			}
			if (goalId != null) {

				String query3 = "select * from budget_grps_user_goal_map where budget_groups_id='" + budgetId + "'";
				System.out.println("QUERY IS :::: " + query3);
				rs = statement.executeQuery(query3);
				while (rs.next()) {
					isGoalDeleted.add(rs.getString("is_deleted"));
					userGoalId.add(rs.getString("user_goal_id"));
				}

				for (int i = 0; i < isGoalDeleted.size(); i++) {
					Assert.assertEquals(isGoalDeleted.get(i), "0");
					Assert.assertTrue(userGoalId.contains(goalId.get(i)));

				}
			}
			if (CategoryType.isEmpty() && !CategoryId.isEmpty()) {

				String query4 = "select * from budget_by_category where budget_groups_id='" + budgetId + "'";
				System.out.println("QUERY IS :::: " + query4);
				rs = statement.executeQuery(query4);
				while (rs.next()) {
					isCategoryDeleted.add(rs.getString("is_deleted"));
					BUDGET_GOAL_ID.add(rs.getString("budget_goal_id"));
				}
				String currencyQuery = "select * from currency where CURRENCY_DESC ='" + currency + "'";
				rs = statement.executeQuery(currencyQuery);
				while (rs.next()) {
					CURRENCY_ID = rs.getString("CURRENCY_ID");
				}
				for (int i = 0; i < catidAmountUpdate.size(); i++) {

					String query5 = "select * from budget_goal where budget_goal_id ='" + BUDGET_GOAL_ID.get(i) + "'";
					System.out.println("QUERY IS :::: " + query5);
					rs = statement.executeQuery(query5);
					while (rs.next()) {
						Assert.assertEquals(rs.getString("GOAL_AMOUNT_CURR_ID"), CURRENCY_ID);
						Assert.assertTrue(catidAmountUpdate.contains(rs.getString("GOAL_AMOUNT")));

					}
					Assert.assertEquals(isCategoryDeleted.get(i), "0");
				}
				Assert.assertTrue(mem_house_holding_group_id != null);

			} else if (CategoryId.isEmpty() && !(CategoryType.isEmpty())) {
				String query5 = "select * from budget_summary where budget_groups_id='" + budgetId + "'";
				System.out.println("QUERY IS :::: " + query5);
				rs = statement.executeQuery(query5);
				while (rs.next()) {
					isCategoryTypeDeleted.add(rs.getString("is_deleted"));
					BUDGET_GOAL_IDtype.add(rs.getString("budget_goal_id"));

				}
				String currencyQuery = "select * from currency where CURRENCY_DESC ='" + currency + "'";
				rs = statement.executeQuery(currencyQuery);
				while (rs.next()) {
					CURRENCY_ID = rs.getString("CURRENCY_ID");
				}
				for (int j = 0; j < catTypeAmtUpdate.size(); j++) {
					Assert.assertEquals(isCategoryTypeDeleted.get(j), "0");
					String query6 = "select * from budget_goal where budget_goal_id ='" + BUDGET_GOAL_IDtype.get(j)
							+ "'";
					System.out.println("QUERY IS :::: " + query6);
					rs = statement.executeQuery(query6);
					while (rs.next()) {
						Assert.assertEquals(rs.getString("GOAL_AMOUNT_CURR_ID"), CURRENCY_ID);
						if (!catTypeAmtUpdate.isEmpty()) {
							Assert.assertTrue(catTypeAmtUpdate.contains(rs.getString("GOAL_AMOUNT")));
						}
					}

				}

				Assert.assertTrue(mem_house_holding_group_id != null);
			} else {
				String query4 = "select * from budget_by_category where budget_groups_id='" + budgetId + "'";
				System.out.println("QUERY IS :::: " + query4);
				rs = statement.executeQuery(query4);
				while (rs.next()) {
					isCategoryDeleted.add(rs.getString("is_deleted"));
					BUDGET_GOAL_ID.add(rs.getString("budget_goal_id"));
				}
				String currencyQuery = "select * from currency where CURRENCY_DESC ='" + currency + "'";
				rs = statement.executeQuery(currencyQuery);
				while (rs.next()) {
					CURRENCY_ID = rs.getString("CURRENCY_ID");
				}
				for (int i = 0; i < CategoryId.size(); i++) {

					String query5 = "select * from budget_goal where budget_goal_id ='" + BUDGET_GOAL_ID.get(i) + "'";
					System.out.println("QUERY IS :::: " + query5);
					rs = statement.executeQuery(query5);
					while (rs.next()) {
						Assert.assertEquals(rs.getString("GOAL_AMOUNT_CURR_ID"), CURRENCY_ID);
						Assert.assertEquals(rs.getString("GOAL_AMOUNT"), catidAmountUpdate.get(i));

					}
					Assert.assertEquals(isCategoryDeleted.get(i), "0");
				}
				String query5 = "select * from budget_summary where budget_groups_id='" + budgetId + "'";
				System.out.println("QUERY IS :::: " + query5);
				rs = statement.executeQuery(query5);
				while (rs.next()) {
					isCategoryTypeDeleted.add(rs.getString("is_deleted"));
					BUDGET_GOAL_IDtype.add(rs.getString("budget_goal_id"));
				}

				for (int j = 0; j < CategoryType.size(); j++) {
					Assert.assertEquals(isCategoryTypeDeleted.get(j), "0");
					String query6 = "select * from budget_goal where budget_goal_id ='" + BUDGET_GOAL_IDtype.get(j)
							+ "'";
					System.out.println("QUERY IS :::: " + query6);
					rs = statement.executeQuery(query6);
					while (rs.next()) {
						Assert.assertEquals(rs.getString("GOAL_AMOUNT_CURR_ID"), CURRENCY_ID);
						Assert.assertEquals(rs.getString("GOAL_AMOUNT"), catTypeAmtUpdate.get(j));

					}

				}
				Assert.assertTrue(mem_house_holding_group_id != null);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			dbHelper.closeConnectionStatementResultSet(conn, statement, rs);
		}
	}

	public void updateBudgetDbVerification(String userName, int budgetId, List<String> goalId) {
		String Mem_Id = null;
		Statement statement = null;
		ResultSet rs = null;
		DBHelper dbHelper = new DBHelper();
		String isGroupDeleted = null;
		String mem_house_holding_group_id = null;
		List<String> isGoalDeleted = new ArrayList<String>();
		List<String> userGoalId = new ArrayList<String>();
		String isMemHouseHoldingDeleted = null;
		// String groupsId=null;

		Connection conn = dbHelper.getDBConnection();
		String query1 = "select * from MEM where Login_name='" + userName + "'";
		try {
			statement = conn.createStatement();

			rs = statement.executeQuery(query1);
			while (rs.next()) {
				Mem_Id = rs.getString("MEM_ID");
			}
			String query2 = "select * from budget_groups where MEM_ID='" + Mem_Id + "'";
			System.out.println("QUERY IS :::: " + query2);
			rs = statement.executeQuery(query2);
			while (rs.next()) {
				isGroupDeleted = rs.getString("is_deleted");
				mem_house_holding_group_id = rs.getString("mem_house_holding_group_id");
				// groupsId=rs.getString("budget_groups_id");
			}
			if (goalId != null) {

				String query3 = "select * from budget_grps_user_goal_map where budget_groups_id='" + budgetId + "'";
				System.out.println("QUERY IS :::: " + query3);
				rs = statement.executeQuery(query3);
				while (rs.next()) {
					isGoalDeleted.add(rs.getString("is_deleted"));
					userGoalId.add(rs.getString("user_goal_id"));
				}

				for (int i = 0; i < isGoalDeleted.size(); i++) {
					Assert.assertEquals(isGoalDeleted.get(i), "0");
					Assert.assertTrue(userGoalId.contains(goalId.get(i)));

				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			dbHelper.closeConnectionStatementResultSet(conn, statement, rs);
		}
	}

	public  HttpMethodParameters createGroupBodyParamRequestPayload(String groupNames, String numberOfAccounts,String container,String accountIdsByNames ,HashMap<String,HashMap<String,Integer>> itemAccountsByContainersMap ,String groupName ){
		JSONObject groupPayload=new JSONObject();	
		JSONObject groupObj = new JSONObject();
		JSONArray groupArrObj = new JSONArray();
		JSONArray accountArrObj = new JSONArray();
		HashMap<String,HashMap<String,ArrayList>> groupAndAccountInfoMap = new HashMap<String,HashMap<String,ArrayList>>();
		HashMap<String,ArrayList> noOfAccountsContainerMap = new HashMap<String,ArrayList>();
		ArrayList<String> containerList = new ArrayList<String>();
		String[] groupsNamesArray = new String[]{"","","",""};
		String[] totalNoAccountsArray = new String[] {"","","",""};
		String[] containersInfoArray =new String[] {"","","",""};
		String accountIdsByNamesArray = null;
		
		String[] groupNameNoOfAccounts = groupName.split(",");	
		String[] containers = container.split(",");
		String[] accountIds = accountIdsByNames.split(",");
		
		
		for(int i=0;i<groupNameNoOfAccounts.length;i++) {			
			groupsNamesArray[i] = groupNameNoOfAccounts[i].split("_")[0];
			totalNoAccountsArray[i] = groupNameNoOfAccounts[i].split("_")[1];			
			for(int j=0;j<containers[i].split("_").length;j++) {
				containerList.add(containers[i].split("_")[j]);
			}
			//noOfAccountsContainerMap.put(key, value)
		//groupAndAccountInfoMap.pu
		}
		
		for(int i =0 ;i<groupNameNoOfAccounts.length;i++) {
			groupsNamesArray[i] = groupNameNoOfAccounts[i].split("_")[0];
			totalNoAccountsArray[i] = groupNameNoOfAccounts[i].split("_")[1];
			containersInfoArray[i] = (containers[i].split("_"))[0];			
		}		
			
		
		for(int i = 0;i<groupsNamesArray.length;i++) {		
			
			for(int j =0 ;j<totalNoAccountsArray.length;j++) {				
				String[] addContainerAccounts = containersInfoArray[i].split("_");
				JSONObject accountObj = new JSONObject();
				accountObj.put("id",itemAccountsByContainersMap.get(addContainerAccounts[j]).get(accountIdsByNames));
				accountArrObj.put(accountObj);
			}			
			groupObj.put("name", groupsNamesArray[i]);
		    groupObj.put("account",accountArrObj);
		    groupArrObj.put(groupObj);
		    groupPayload.put("group",groupArrObj);
		}
			   
	    String createGroupBodyParam=groupPayload.toString();
	    HttpMethodParameters param = HttpMethodParameters.builder().build();
		param.setBodyParams(createGroupBodyParam);
		return param;
	}
	
	public String createBudgetByCategoryRequestPayload(String groupId, List<Integer> categoryId, String budgetAmount,
			String currency) throws JSONException {

		JSONObject reqObject = new JSONObject();
		JSONArray budgetArray = new JSONArray();

		JSONObject budgetObject = new JSONObject();
		budgetObject.put("groupId", groupId);

		// Category Data array
		JSONArray categoryDataArray = new JSONArray();
		for (int i = 0; i < categoryId.size(); i++) {
			JSONObject catTypeObject = new JSONObject();
			catTypeObject.put("categoryId", categoryId.get(i));

			JSONObject budgetTotalObj = new JSONObject();
			budgetTotalObj.put("amount", budgetAmount);
			budgetTotalObj.put("currency", currency);
			catTypeObject.put("budgetAmount", budgetTotalObj);

			categoryDataArray.put(catTypeObject);
		}
		budgetObject.put("categoryData", categoryDataArray);

		budgetArray.put(budgetObject);

		reqObject.put("budget", budgetArray);
		// String test = reqObject.toJSONString();
		return reqObject.toString();
	}
	
	
	public String createBudgetForCatTypeDataAndCatData(String groupId, String categoryIdsList,String categoriesBudgtAmount, 
			String categoryTypeList,String categoryTypesBudgetAmount,EnvSession sessionObj)
			throws JSONException {
	    String msg="Budget Created";
		JSONObject reqObject = new JSONObject();
		JSONObject budgetObject = new JSONObject();
		JSONArray budgetArray = new JSONArray();
		budgetObject.put("groupId", groupId);
		// Category Type array
		
		if(!categoryTypeList.isEmpty()) {
			JSONArray categoryTypeArray = new JSONArray();
			String[] catTypeList = categoryTypeList.split(",");
			String[] catTypesBudgetAmt = categoryTypesBudgetAmount.split(",");
			for (int i = 0; i < catTypeList.length; i++) {
				JSONObject catObject = new JSONObject();
				catObject.put("categoryType", catTypeList[i]);
				JSONObject budgetTotalObj = new JSONObject();
				budgetTotalObj.put("amount", catTypesBudgetAmt[i]);
				budgetTotalObj.put("currency", "USD");
				catObject.put("budgetAmount", budgetTotalObj);
				categoryTypeArray.put(catObject);			
			}
			budgetObject.put("categoryTypeData", categoryTypeArray);
		}
		// Category Data array
		if(!categoryIdsList.isEmpty()) {
			String[] catIdsList = categoryIdsList.split(",");
			String[] catIdsBudgetAmount = categoriesBudgtAmount.split(",");
			JSONArray categoryDataArray = new JSONArray();
			for (int i = 0; i < catIdsList.length; i++) {
				JSONObject catTypeObject = new JSONObject();
				catTypeObject.put("categoryId", catIdsList[i]);
				JSONObject budgetTotalObj = new JSONObject();
				budgetTotalObj.put("amount", catIdsBudgetAmount[i]);
				budgetTotalObj.put("currency", "USD");
				catTypeObject.put("budgetAmount", budgetTotalObj);
				categoryDataArray.put(catTypeObject);
			}
			budgetObject.put("categoryData", categoryDataArray);
		}		
		budgetArray.put(budgetObject);
		reqObject.put("budget", budgetArray);
		
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(reqObject.toString());
		Response createBudgetResponse = budgetUtils.createBudget(httpMethodParameters, sessionObj);
		
		if(createBudgetResponse.getStatusCode()!=201)
			msg="Budget NOT Created";
		
		return msg;
	}

}
