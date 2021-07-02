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

package com.yodlee.yodleeApi.sdg.script;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.databene.benerator.anno.Source;
import org.junit.AfterClass;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.yodlee.DBHelper;
import com.yodlee.app.yodleeApi.DagPages.DagHome;
import com.yodlee.app.yodleeApi.webdriver.DriverFactory;
import com.yodlee.taas.annote.Feature;
import com.yodlee.taas.annote.FeatureName;
import com.yodlee.taas.annote.Info;
import com.yodlee.taas.annote.SubFeature;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.Constants;
import com.yodlee.yodleeApi.database.DbQueries;
import com.yodlee.yodleeApi.helper.AccountsHelper;
import com.yodlee.yodleeApi.helper.DbHelper;
import com.yodlee.yodleeApi.helper.ProviderAccountsHelper;
import com.yodlee.yodleeApi.helper.SessionHelper;
import com.yodlee.yodleeApi.pojo.AdditionalDataSet;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.User;
import com.yodlee.yodleeApi.sdg.SdgHelper;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;

/**
 * @author B sivateja -->Revised by Rahul Kumar
 *
 */
@Feature(featureName = { FeatureName.ACCOUNTS })
public class TestRowdelChangesForStudentLoanType extends Base {

	public static final String VALID_INVESTMENT_ACCOUNTS = "\\TestData\\CSVFiles\\Rowdel\\Rules_Loan\\testRowdelRulesforStudentLoan.csv";

	Connection conn = null;
	SdgHelper sdgHelper = new SdgHelper();
	AccountsHelper accountsHelper = new AccountsHelper();
	ProviderAccountsHelper providerAccountsHelper = new ProviderAccountsHelper();
	Long providerAccountId;
	Response getProvidersResponse;
	String dagSiteUserName = "rowdel_studentloan.site16441.2";
	String dagSitePassword = "site16441.2";
	String catalogUserName = "rowdel_studentloan";
	String catalogPassword = "yodlee@123";
	String paramValue;
	String dataSetTemplateFile = "\\TestData\\XML\\loginform\\Loan_RowdelRules\\loan1.xml";
	String add_xmlUpload_path = "\\dagXmlFiles\\rowdel_rules\\add_account";
	String update_xmlUpload_path = "\\dagXmlFiles\\rowdel_rules\\update_account";

	// to get total row count in datapoints table
	private int getRowCountforLoan(Long mem_site_acc) {

		String tablename = "loan";
		String whereClause = " where cache_item_id in (select cache_item_id from server_stats where mem_site_acc_id= "
				+ mem_site_acc + " and sum_info_id=20563 and rownum=1) ";

		int rc = 0;
		try {
			DbHelper dbHelper = new DbHelper();
			rc = dbHelper.getRowCount(tablename, whereClause);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rc;
	}

	private void isAccountClosed(Long mem_site_acc) throws Exception {

		DbHelper dbHelper = new DbHelper();
		String query = DbQueries.GET_ITEMACCOUNT_LOAN + mem_site_acc + " )";
		List<String> list = dbHelper.getCollectionValueFromDB(query, null, "item_account_status_id", 0);

		Assert.assertTrue(list.toString().contains("5"),
				" Failed as one of the item account is expected 'TO_BE_CLOSED' ");

	}

	@BeforeClass
	public void testSetUp() throws IOException {

		System.out.println(" ==== DB Check for latest rules ==== ");
		DBHelper dbHelper = new DBHelper();
		try {
			conn = dbHelper.getDBConnection();
			Object value = dbHelper.getValueFromDB(DbQueries.GET_ROWDEL_RULES_LOAN, "RULE");

			Assert.assertTrue(value.toString().contains("accountNumber + accountName + originalLoanAmount"),
					" Failed as new rowdel rules are not updated in DB ");
			Configuration configuration = Configuration.getInstance();

			String query = DbQueries.GET_NON_PREPOP_DBFILER + configuration.getCobrandObj().getCobrandId();
			paramValue = dbHelper.getValueFromDB(query, "param_value").toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(dataProvider = Constants.FEEDER, alwaysRun = true, priority = 1)
	@Source(VALID_INVESTMENT_ACCOUNTS)
	@Info(subfeature = { SubFeature.GET_ACCOUNTS }, userStoryId = "")
	public void testRowdelRules(String tcId, String tcName, String add_dagXmlFileName, String update_dagXmlFileName,
			String rowcount, String checkaccstatus, String enabled) throws Exception {

		logger.info("********* Priority 1 *****************");
		CommonUtils commonUtils = new CommonUtils();
		SessionHelper sessionHelper = new SessionHelper();
		System.out.println("TestCase id is " + tcId + " TestCase Name is " + tcName);
		commonUtils.isTCEnabled(enabled, tcName);

		String currentPath = new File(".").getCanonicalPath() + "\\src\\test\\resources";

		// Creating a new user.
		System.out.println("User Registration Started");
		String metricUserName = "TestYSLSdg" + "Metric" + System.currentTimeMillis();
		String metricPassword = "Metric@123";

		User userInfo = User.builder().build();
		userInfo.setUsername(metricUserName);
		userInfo.setPassword(metricPassword);
		userInfo.setLocale(Constants.LOCALE_US);

		// User Registration
		EnvSession envSessionObj = sessionHelper.getSessionObjectByUserRegistration(userInfo);

		WebDriver driver = DriverFactory.getDriver();
		DagHome dagHome = new DagHome(driver);

		dagHome.loginAndUploadDagXml(Configuration.getInstance().getDagUrl(), catalogUserName, catalogPassword,
				dagSiteUserName, add_xmlUpload_path, add_dagXmlFileName, "Loans");

		List<AdditionalDataSet> dataSet = accountsHelper.getAdditionalDataSet(currentPath + dataSetTemplateFile);

		// Adding account
		sdgHelper.providerAccountId = sdgHelper.addProviderAccountSdg(null, null, dagSiteUserName, dagSitePassword,
				"16441", dataSet, envSessionObj, "fieldarray", "dataset");

		dagHome.loginAndUploadDagXml(Configuration.getInstance().getDagUrl(), catalogUserName, catalogPassword,
				dagSiteUserName, update_xmlUpload_path, update_dagXmlFileName, "Loans");

		dagHome.close();

		// Updating Account
		sdgHelper.updateProviderAccount(null, null, 65499, dagSiteUserName, 65500, "16441", dagSitePassword, dataSet,
				envSessionObj, "fieldarray", "dataset");

		Thread.sleep(10000);

		int i = getRowCountforLoan(sdgHelper.providerAccountId);
		Assert.assertTrue(rowcount.equals(i + ""), " Mismatch in the row count of Loan table ");

		if (checkaccstatus.equalsIgnoreCase("true") && paramValue.equalsIgnoreCase("FALSE")) {
			isAccountClosed(sdgHelper.providerAccountId);
		}

		UserUtils userUtils = new UserUtils();
		userUtils.unRegisterUser(envSessionObj);

	}

	@AfterClass
	public void shutDownHook() throws IOException {
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
