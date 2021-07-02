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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.databene.benerator.anno.Source;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.yodlee.DBHelper;
import com.yodlee.taas.annote.Feature;
import com.yodlee.taas.annote.FeatureName;
import com.yodlee.taas.annote.Info;
import com.yodlee.taas.annote.SubFeature;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.Constants;
import com.yodlee.yodleeApi.exceptions.UnexpectedFunctionalityException;
import com.yodlee.yodleeApi.helper.AccountsHelper;
import com.yodlee.yodleeApi.helper.DbHelper;
import com.yodlee.yodleeApi.helper.ProviderAccountsHelper;
import com.yodlee.yodleeApi.pojo.AdditionalDataSet;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.sdg.SdgHelper;
import com.yodlee.yodleeApi.utils.apiUtils.DataExtractUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;

/**
 * @author Siva Teja
 *
 */
@Feature(featureName = { FeatureName.ACCOUNTS })
public class TestPensionAccType extends Base {

	public int paramKeyId = 6187;
	public long cobrandID = 10000004;
	public int sdgParamAclId = 7466, SNParamAclId = 7421, DMParamKey = 6226;

	String fromDateForDEEnabled_Implicitly, toDateForDEEnabled_Implicitly;
	Connection conn = null;
	public static final String VALID_INVESTMENT_ACCOUNTS = "\\TestData\\CSVFiles\\Get Accounts_V1.1\\testGetPensionAcctypeForInvestment.csv";

	AccountsHelper accountsHelper = new AccountsHelper();
	ProviderAccountsHelper providerAccountsHelper = new ProviderAccountsHelper();
	Long providerAccountId;
	Response getProvidersResponse;
	// MorganStanley New Account Type for Loan is defined in this Catalog and
	// used as DagUN and DagPassword
	String dagSiteUserName = "jcbose_meerkat_Site.site16441.2";
	String dagSitePassword = "site16441.2";
	String dataSetTemplateFile = "\\TestData\\loginform\\template1.xml";

	// to get total row count in datapoints table
	private int getrowCount(Long mem_site_acc) {

		DbHelper dbHelper = new DbHelper();
		String tablename = "mem_txn_datapoint";
		int rc = 0;
		String mem_query = "select mem_id from mem_site_acc where mem_site_acc_id = " + mem_site_acc.toString();
		try {
			String memid = dbHelper.getValueFromDB(mem_query, "mem_id").toString();
			String whereClause = " where mem_id = " + memid;

			rc = dbHelper.getRowCount(tablename, whereClause);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rc;

	}

	// to get param_key or param acl values
	private String getParamValue(int paramid, String type) {

		String query;
		String queryValue = null;
		DBHelper dbHelper = new DBHelper();

		if (type.equalsIgnoreCase("acl")) {
			query = "select * from cobrand_acl_value where param_acl_id= " + paramid + " and cobrand_id = "
					+ Configuration.getInstance().getCobrandObj().getCobrandId();
			try {
				queryValue = dbHelper.getValueFromDB(query, "acl_value").toString();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (type.equalsIgnoreCase("key")) {
			query = "select * from cob_param where param_key_id= " + paramid + " and cobrand_id = "
					+ Configuration.getInstance().getCobrandObj().getCobrandId();
			try {
				queryValue = dbHelper.getValueFromDB(query, "param_value").toString();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		System.out.println("value is " + queryValue);
		return queryValue;
	}

	@BeforeClass
	public void testSetUp() throws IOException {

		DBHelper dbHelper = new DBHelper();
		conn = dbHelper.getDBConnection();

		CommonUtils commonUtils = new CommonUtils();
		String currentPath = new File(".").getCanonicalPath() + "\\src\\test\\resources";
		// List<AdditionalDataSet> dataSet = getAdditionalDataSet(currentPath +
		// dataSetTemplateFile);
		List<AdditionalDataSet> dataSet = accountsHelper.getAdditionalDataSet(currentPath + dataSetTemplateFile);

		SdgHelper sdgHelper = new SdgHelper();
		providerAccountId = sdgHelper.addProviderAccountSdg(null, null, dagSiteUserName, dagSitePassword, "16441",
				dataSet, Configuration.getInstance().getCobrandSessionObj(), "fieldarray", "dataset");

		if (providerAccountId == 0) {
			throw new UnexpectedFunctionalityException("providerAccountId is not present after account addition");
		}
		System.out.println("provider acct id :" + providerAccountId);

		fromDateForDEEnabled_Implicitly = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
				.format(commonUtils.addSecondsToDate(commonUtils.getUTCtime(), -30));

	}

	@Test(dataProvider = Constants.FEEDER, alwaysRun = true, priority = 1)
	@Source(VALID_INVESTMENT_ACCOUNTS)
	@Info(subfeature = { SubFeature.GET_ACCOUNTS }, userStoryId = "")
	public void testAccountsTypeforInvestment(String tcId, String tcName, String container, String accountTypeExpected,
			String enabled) throws FileNotFoundException {

		logger.info("********* Priority 1 *****************");
		System.out.println("TestCase id is " + tcId + " TestCase Name is " + tcName);
		CommonUtils commonUtils = new CommonUtils();
		commonUtils.isTCEnabled(enabled, tcId);
		DbHelper dbHelper = new DbHelper();

		if (getParamValue(sdgParamAclId, "acl").equalsIgnoreCase("ON")) {

			System.out.println("Running the suite only if SDG key is enabled");

			if (dbHelper.getDataModelVer(paramKeyId) >= 14
					&& getParamValue(SNParamAclId, "acl").equalsIgnoreCase("false")) {

				try {
					Map<String, String> queryParam = new LinkedHashMap<>();
					queryParam.put("container", container);
					queryParam.put("providerAccountId", String.valueOf(providerAccountId));
					Response getAccountsResponse = null;
					HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
					httpMethodParameters.setQueryParams(queryParam);

					ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
					getAccountsResponse = providerAccountUtils.getProviderAcctDetails(httpMethodParameters,
							Configuration.getInstance().getCobrandSessionObj());

					// getAccountsResponse = yslMethods.getAccounts(queryParam,
					// InitialLoader.cobSessionObj);
					JSONObject obj = new JSONObject(getAccountsResponse.asString());
					JSONArray accountsArray = obj.getJSONArray("account");
					JSONObject accountObject = accountsArray.getJSONObject(0);
					String accountType = accountObject.getString("accountType");
					AssertJUnit.assertEquals(accountType, accountTypeExpected,
							"Wrong value for accountType in Get Accounts API Response");
					toDateForDEEnabled_Implicitly = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
							.format(commonUtils.getUTCtime());

					DataExtractUtils dataExtractUtils = new DataExtractUtils();
					Map<String, Object> queryParams = new LinkedHashMap<>();
					queryParams.put("eventName", "DATA_UPDATES");
					queryParams.put("loginName", Configuration.getInstance().getUserObj().getUsername());
					queryParams.put("fromDate", fromDateForDEEnabled_Implicitly);
					queryParams.put("toDate", toDateForDEEnabled_Implicitly);
					HttpMethodParameters httpMethodParameters1 = HttpMethodParameters.builder().build();
					httpMethodParameters1.setQueryParams(queryParams);

					Response userDataResponse = dataExtractUtils.getUserData(httpMethodParameters1,
							Configuration.getInstance().getCobrandSessionObj());

					String acctype = userDataResponse.jsonPath().get("userData[0].account[0].accountType").toString();
					AssertJUnit.assertEquals(acctype, accountTypeExpected,
							"Wrong value for accountType in data Extract response");
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				System.out.println("data model version is lower the 14 and pension acc type is not supported");

				return;

			}

		} else {

			throw new SkipException("Skipping the suite as SDG key is Enabled ....! ");
		}

		if (getParamValue(DMParamKey, "key").equalsIgnoreCase("true")) {
			Assert.assertTrue(getrowCount(providerAccountId) >= 1,
					"datapoints are not populated properly for pension account");

		}

	}

	@AfterClass
	public void shutDownHook() throws IOException {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
