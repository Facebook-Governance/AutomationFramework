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
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.databene.benerator.anno.Source;
import org.junit.AfterClass;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.yodlee.DBHelper;
import com.yodlee.taas.annote.TestGroup;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.common.FileCopyFromRemote;
import com.yodlee.yodleeApi.constants.Constants;
import com.yodlee.yodleeApi.helper.AccountsHelper;
import com.yodlee.yodleeApi.helper.DbHelper;
import com.yodlee.yodleeApi.helper.SessionHelper;
import com.yodlee.yodleeApi.helper.YslLogAnalysisHelper;
import com.yodlee.yodleeApi.pojo.AdditionalDataSet;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.User;
import com.yodlee.yodleeApi.sdg.SdgHelper;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;
import com.yodlee.yodleeApi.utils.commonUtils.PropertyFileUtil;

public class TestStopScreenScraping extends Base {

	public final String testDataFile = "\\TestData\\CSVFiles\\ScreenScraping\\StopScraping.csv";
	//String filePath = "..\\src\\test\\resources\\TestData\\XML\\pendingTransactions\\";
	public final String userDirPath = System.getProperty("user.dir");
	String dataSetTemplateFile = "..\\src\\test\\resources\\TestData\\XML\\loginform\\bank_screenscraping\\bank1.xml";
	
	
	String processLogFileLocation = "/opt/ctier/logs/";
	String localProcessLogFileLoc = "\\test\\logs\\";
	String Server_name="sdp",Server_password="sdp";
	String Site_ID = "24016";
	public  String itemActionDetailsNode = "sumInfoId",
	requestXmlFilePath = userDirPath+ "\\src\\test\\resources\\TestData\\YslRequest\\";
	
	
	List<String> Param_values= new ArrayList<>();
	String	params[];
	
	DBHelper dbHelper = new DBHelper();
	
	CommonUtils commonUtils = new CommonUtils();
	
	SdgHelper sdgHelper = new SdgHelper();
	SessionHelper sessionHelper = new SessionHelper();
	AccountsHelper accountsHelper = new AccountsHelper();
	
	Configuration configurationObj = Configuration.getInstance(); 
	
	Properties configProperties = new Properties();
	PropertyFileUtil propertyfileUtil = new PropertyFileUtil();
	
	

	public  String xyz = System.getProperty("configFile");
	Connection conn =null;
	
	@BeforeClass
	public void verifyKeyEnablement() throws Exception {
				
		// String cobrandId =
		// Configuration.getInstance().getCobrandObj().getCobrandId();
		
		try {
			String res1 = dbHelper.getValueFromDB(
					DbHelper.GET_SITE_DETAILS + Site_ID, "PARENT_SITE_ID")
					.toString();
			if (res1 != null && res1.equals("16441")) {
				System.out.println("Parent Site ID is mapped for the site_id  32021");
			} else {
				Assert.fail(" The required param value entry is not present ");
			}

			String res2 = dbHelper.getValueFromDB(
					DbHelper.GET_SUMINFO_DETAILS_ACCTYPES, "PARAM_VALUE")
					.toString();

				params = res2.split(",");

			if (params.length == 0 || res2 == null) {

				Assert.fail(" The required param value entry is not present ");
			}

			for (int i = 0; i < params.length; i++) {

				String acc = dbHelper.getValueFromDB(
						DbHelper.GET_ACC_TYPES + params[i],
						"acct_type").toString();

				Param_values.add(acc);
			}

		} catch (Exception e) {

			System.out.println(" failed due to -> " + e);
			e.printStackTrace();

		}

	}
	
	
	@Source(testDataFile)
	@Test(enabled = true, groups = { TestGroup.REGRESSION }, dataProvider = Constants.FEEDER)
	public void testStopScreenScraping(String testCaseId, String testCaseName, String enabled) throws Exception{

		commonUtils.isTCEnabled(enabled, testCaseName);
		System.out.println("*******TestCase : " + testCaseName + " & testCaseId : " + testCaseId + "*******");
		// Creating a new user.
		System.out.println("User Registration Started");
		String UserName = "StopScrscraping" + System.currentTimeMillis();
		String Password = "TEST@123";
		final String dagSiteUserName = "PendingTrx_Reconcile.site16441.1";
	    final String dagSitePassword = "site16441.1";

		User userInfo = User.builder().build();
		userInfo.setUsername(UserName);
		userInfo.setPassword(Password);
		// User Registration
		
		EnvSession commonEnvObj = null;
		try {

			commonEnvObj = sessionHelper
					.getSessionObjectByUserRegistration(userInfo);
			System.out.println("New userSession created is::"
					+ commonEnvObj.getUserSession());

			List<AdditionalDataSet> dataSet = accountsHelper
					.getAdditionalDataSet( dataSetTemplateFile);

			sdgHelper.providerAccountId = sdgHelper.addProviderAccountSdg(null,
					null, dagSiteUserName, dagSitePassword, "16441", dataSet,
					commonEnvObj, "fieldarray", Constants.DATA_SET);

			getProcessLogFile(UserName, sdgHelper.providerAccountId.toString());
			
			validateAccTypeforNonOB(sdgHelper.providerAccountId.toString(), params);

		}

		catch (Exception e) {
			e.printStackTrace();
			Assert.fail("failed to execute " + testCaseName
					+ " test case due to " + e);
		}
		// Unregister user

		/*Response res = userUtils.unRegisterUser(commonEnvObj);
		res.prettyPrint();*/
	}
	

	public void getProcessLogFile(String userName, String providerAccountId) {

		String memId = null;
		DbHelper dbHelp = new DbHelper();

		
	Configuration configurationObj=Configuration.getInstance();
		      String value=configurationObj.getExtYslLogs();
           System.out.println("YSL log details is :: "+value);
	
     String[] val= value.split(":");
		
     String logfile=val[1];
     
     int start=logfile.lastIndexOf("_");
     int last=logfile.lastIndexOf(".");
          
     String ysl_ip=logfile.substring(start+1, last);
     
		try {

			conn = dbHelper.getDBConnection();
			System.out.println("DBConnection in testStopScreenScraping:" + conn);
			
			// get gatherer ip and robot id  logfile.CORE_10_192.168.57.95.log
			memId = dbHelp.getMEMID(conn, userName);
			
      
			// get location for Core logs file from YSL
			String coreLogFolder = processLogFileLocation;
			System.out.println("core logs folder location on YSL: " + coreLogFolder);

			// get file path to store the YSL core log file
			String localProcessLogFilePath = userDirPath +localProcessLogFileLoc;
			System.out.println("process logs file location on local: " + localProcessLogFilePath);

			
			FileCopyFromRemote copyObj = new FileCopyFromRemote();
			copyObj.startFTP(ysl_ip, Server_name, Server_password, coreLogFolder,
					localProcessLogFilePath, logfile);
			
			
			if (YslLogAnalysisHelper.verifyRequestXMLExists(
					localProcessLogFilePath + logfile, memId)) {
				System.out.println("Request xml is present in the process logs");
				// getRequestXML(localProcessLogFilePath, memId);
				String requsetxml = requestXmlFilePath + "YSLrequest.xml";
				System.out.println("Request xml file location: " + requsetxml);
				String req = YslLogAnalysisHelper.getYslRequestxml(
						localProcessLogFilePath + logfile, memId);

				System.out.println("request xml is -> "+req );
				// create an xml file in the given path for the request xml retrieved from the YSL core log
				
				YslLogAnalysisHelper.createActXml(req, requsetxml);
				
				 checkOBInfoAccTypes(requsetxml,Param_values);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	void validateAccTypeforNonOB(String memsiteacc, String param_values[])
			throws Exception {

	
		String query = "select b.acct_type_id from bank_account b,acct_type a where b.acct_type_id=a.acct_type_id and cache_item_id "
				+ " in ( SELECT cache_item_id FROM cache_info where sum_info_id=20559 and mem_site_acc_id = "
				+ memsiteacc + " )";

		DbHelper DBhelp = new DbHelper();

		List<Object> list = DBhelp.getCollectionValueFromDB(query, null,
				"acct_type_id", 0);

		for (int i = 0; i < param_values.length; i++) {
	
			if (list.contains(param_values[i])){
				
				Assert.fail(" TC failed since Acc_type id is matched with the OB migrated container");
				
			}
			
			}
}
	
	
	public void  checkOBInfoAccTypes(String reqXmlFilePath, List<String> acc_types ) {

		

		File reqXmlFile = new File(reqXmlFilePath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {

			
			// traverse through xml to find necessary attributes
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(reqXmlFile);
			doc.getDocumentElement().normalize();
			System.out.print("Root element: ");
			System.out.println(doc.getDocumentElement().getNodeName());

			// get all the elements for a container
			NodeList nList = doc.getElementsByTagName("item");
			
			for (int items = 0; items < nList.getLength(); items++) {
				Node itemNode = nList.item(items);
				Node auxInfoNode = null;
				if (itemNode.hasChildNodes()) {

					NodeList itemNodeList = itemNode.getChildNodes();
					

					System.out.println(
							"------------------------- Parsing  XML for itemNodeList---------------------------------------------------------------------");

					if (itemNodeList != null && itemNodeList.getLength() > 0) {

						for (int i = 0; i < itemNodeList.getLength(); i++) {

							if (itemNodeList.item(i) != null
									&& itemNodeList.item(i).getNodeName().equalsIgnoreCase("auxInfo")) {
								auxInfoNode = itemNodeList.item(i);
								
								break;
							}
						}
					}
				}

				boolean isOBSumInfoId = false;
				if (auxInfoNode != null) {
					NodeList auxInfoChaildNodes = auxInfoNode.getChildNodes();
					if (auxInfoChaildNodes != null	&& auxInfoChaildNodes.getLength() > 0) {
					
						for (int i = 0; i < auxInfoChaildNodes.getLength(); i++) {

							if ( auxInfoChaildNodes.item(i).getNodeName().equalsIgnoreCase("sumInfoId") &&
								auxInfoChaildNodes.item(i).getTextContent().equals("20559")) {
									
								isOBSumInfoId = true;
								
								System.out.println("Sum Info value id : " + auxInfoChaildNodes.item(i).getNodeName());
								}
							
							if (isOBSumInfoId) {
								if (auxInfoChaildNodes.item(i) != null	
										&& auxInfoChaildNodes.item(i).getNodeName().equalsIgnoreCase("obInfo")) {
									System.out.println(
											"------------------------- Parsing  XML for obInfo---------------------------------------------------------------------");	
									
									
									Node obInfoNode = auxInfoChaildNodes.item(i);
									Node excludeOAuthAccountTypesNode = obInfoNode.getChildNodes().item(0);
									String excludeOAuthAccountTypes = excludeOAuthAccountTypesNode.getTextContent();

									for (String s : acc_types) {
										if (excludeOAuthAccountTypes.trim().contains(s)) {
											
 										 Assert.assertTrue(excludeOAuthAccountTypes.trim().contains(s));
											continue;
											
										} else {
											Assert.fail(" Entry not present in the Request for Acc type");
											break;
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (ParserConfigurationException e) {
			System.err.println(" Test case execution failed");
			e.printStackTrace();
		}  catch (IOException e) {
			System.err.println(" Test case execution failed");
			e.printStackTrace();
		}
		catch (Exception e) {
			System.err.println(" Test case execution failed");
			e.printStackTrace();
		}
	}

	@AfterClass
	public void shutDownHook() throws IOException {
		dbHelper.closeConnection(conn);

	}

}
