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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.databene.benerator.anno.Source;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.jayway.jsonpath.JsonPath;
import com.yodlee.taas.annote.TestGroup;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.FileCopyFromRemote;
import com.yodlee.yodleeApi.common.LogAnalyzer;
import com.yodlee.yodleeApi.common.Secureftp;
import com.yodlee.yodleeApi.constants.Constants;
import com.yodlee.DBHelper;
import com.yodlee.yodleeApi.helper.DbHelper;
import com.yodlee.yodleeApi.helper.SessionHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.User;
import com.yodlee.yodleeApi.sdg.ProcessSdg;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;

/**
 * @author asardeshpande
 *
 */
public class TestNumberOfTransactionDays extends Base {

	public final String itemActionDetailsNode = "itemActionDetails", auxinfoNode = "auxinfo",
			sumInfoIdNode = "sumInfoId", transaction_idNode = "transaction_id";
	public final String noOfTxnDaysTestCases = "\\TestData\\CSVFiles\\numOfTxnDays\\numOfTxnDays.csv";
	public final String userDirPath = System.getProperty("user.dir"),
			processLogFileLocation = "/opt/sdp/dapgathlogs/process_logs/",
			localProcessLogFileLoc = "/src/test/resources/logs/",
			requestXmlFilePath = userDirPath + "/src/test/resources/TestData/RestYslGathererResponse/",
			gatherer_userName = "sdp", gatherer_password = "sdp";
	final String jsonExp = "$..historicTransactionDuration", notApplicable = "NA", disabled = "FALSE";

	CommonUtils commonUtils = new CommonUtils();
	SessionHelper sessionHelper = new SessionHelper();
	Map<String, String> historicTxnDurValuesMap = new HashMap<String, String>();
	Map<String, String> csidTxnIdMap = new HashMap<String, String>();

	@Source(noOfTxnDaysTestCases)
	@Test(enabled = true, groups = { TestGroup.REGRESSION }, dataProvider = Constants.FEEDER)
	public void testNumOfTxnDays(String testCaseId, String testCaseName, String sequence, String seqFilePath,
			String valuesToAssert, String refreshSource, String enabled) throws Exception {

		commonUtils.isTCEnabled(enabled, testCaseName);
		System.out.println("*******TestCase : " + testCaseName + " & Sequence : " + sequence + "*******");

		// Creating a new user.
		System.out.println("User Registration Started");
		String userName = "YSL" + "numTxnDs" + System.currentTimeMillis();
		String password = "NOTD@123";

		User userInfo = User.builder().build();
		userInfo.setUsername(userName);
		userInfo.setPassword(password);

		try {

			// User Registration
			EnvSession envSession = sessionHelper.getSessionObjectByUserRegistration(userInfo);
			System.out.println("New userSession created is::" + envSession.getUserSession());

			// Test Case Execution
			ProcessSdg processSDG = new ProcessSdg(seqFilePath);
			processSDG.processSdgXml(sequence, Constants.SIMPLIFIED_FORM, envSession);
			ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();

			if (!valuesToAssert.equalsIgnoreCase(notApplicable)) {
				// for positive scenarios
				Response response = providerAccountUtils.getAllProviderAccounts(envSession);
				getProcessLogFile(userName, response.jsonPath().getString("providerAccount[0].id"), refreshSource);
				assertValues(valuesToAssert);
			}
			System.out.println("*********Completed TestNumberOfTransactionDays test class***********");

			// Unregister user
			UserUtils userUtils = new UserUtils();
			Response res = userUtils.getUserDetails(envSession);
			res.prettyPrint();

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("failed to execute " + testCaseName + " test case due to " + e);
		}

	}

	private void assertValues(String expectedValues) {

		String csid = null, transactionId = null, jsonString_itemActionDetails = null, numTxnDays_expected = null,
				numTxnDays_actual = null;
		Map<String, String> map_sumInfo_noOfTxnDays = new HashMap<>();
		String[] sumInfTxnDays = expectedValues.split(",");
		for (String dataToAssert : sumInfTxnDays) {
			String[] keyVal = dataToAssert.split(":");
			map_sumInfo_noOfTxnDays.put(keyVal[0], keyVal[1]);
		}
		for (Map.Entry<String, String> es : map_sumInfo_noOfTxnDays.entrySet()) {
			csid = es.getKey();
			numTxnDays_expected = es.getValue();
			transactionId = csidTxnIdMap.get(csid);
			jsonString_itemActionDetails = historicTxnDurValuesMap.get(transactionId);
			List<Object> valuesToAssert = JsonPath.read(jsonString_itemActionDetails, jsonExp);
			numTxnDays_actual = valuesToAssert.get(0).toString();
			System.out.println("SumInfoId: " + csid + " txnId: " + transactionId);
			System.out.println("expected value: " + numTxnDays_expected + " actual value: " + numTxnDays_actual);
			Assert.assertEquals(numTxnDays_actual, numTxnDays_expected);
		}
	}

	private Map<String, String> gethistoricTxnDuration(String reqXmlFilePath) {

		String nodeName = null;
		String txnId = null, itemActionDetailsJson = null, csid = null;
		Map<String, String> historicTxnDurValues = new HashMap<String, String>();
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
			System.out.println(
					"-----------------------------------------------------------------------------------------------------------------");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node itemNode = nList.item(temp);
				System.out.print("\nCurrent Element :");
				System.out.println(itemNode.getNodeName());
				NodeList item_chList = itemNode.getChildNodes();
				for (int i = 0; i < item_chList.getLength(); i++) {
					nodeName = item_chList.item(i).getNodeName();

					// get item action details to retrieve the json that contains number of
					// transaction days
					if (nodeName.equalsIgnoreCase(itemActionDetailsNode)) {
						System.out.print("itemActionDetails : ");
						Node itemActionDetails = item_chList.item(i);
						itemActionDetailsJson = itemActionDetails.getTextContent();
						System.out.println(itemActionDetailsJson);
					}

					// get the sum info id and its relevant transaction id
					if (nodeName.equalsIgnoreCase(auxinfoNode)) {
						Node auxInfo = item_chList.item(i);
						NodeList auxInfo_chList = auxInfo.getChildNodes();
						for (int k = 0; k < auxInfo_chList.getLength(); k++) {
							Node newNode = auxInfo_chList.item(k);
							if (newNode.getNodeName().equalsIgnoreCase(sumInfoIdNode)) {
								csid = newNode.getTextContent();
								System.out.println("SumInfoId: " + csid);
							}
							if (newNode.getNodeName().equalsIgnoreCase(transaction_idNode)) {
								txnId = newNode.getTextContent();
								System.out.println("transaction_id: " + txnId);
							}
						}
					}
					if (txnId != null && itemActionDetailsJson != null && csid != null) {
						csidTxnIdMap.put(csid, txnId);
						historicTxnDurValues.put(txnId, itemActionDetailsJson);
						txnId = null;
						itemActionDetailsJson = null;
						csid = null;
					}
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return historicTxnDurValues;
	}

	private boolean verifyRequestXMLExists(String inputfile, String userid, String refreshValue) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(inputfile));
		boolean value = false;
		String xml = "";
		String line = null;
		while ((line = in.readLine()) != null) {

			if (line.startsWith("<request")) {
				xml = line;
				if (xml.contains(userid) && (refreshValue != null ? xml.contains(refreshValue) : false)) {
					value=true;
					break;
				} else {
					value=false;
				} 
			}

		}
		in.close();
		return value;
	}

	private void getProcessLogFile(String userName, String providerAccountId, String refreshSource) {

		String memId = null;
		DBHelper dbHelper1 = new DBHelper();
		Connection conn = dbHelper1.getDBConnection();
		// get trasaction id for better performance
		try {

			// get gatherer ip and robot id
			DbHelper dbHelper = new DbHelper();
			memId = dbHelper.getMEMID(conn, userName);
			String gathererIp = dbHelper.getGathererIp(conn, providerAccountId);
			int robotId = dbHelper.getRobotId(conn, providerAccountId);

			// get location for process.log file from gatherer
			String DapProcessLogFolder = processLogFileLocation + robotId + "/";
			System.out.println("process logs folder location on gatherer: " + DapProcessLogFolder);

			// get file path to store the process.log file
			String localProcessLogFilePath = userDirPath + localProcessLogFileLoc;
			System.out.println("process logs file location on local: " + localProcessLogFilePath);

			Secureftp ftp = new Secureftp(gathererIp, gatherer_userName, gatherer_password);
			List<String> filelist = ftp.getProcessLogFileList(processLogFileLocation, Integer.toString(robotId));
			String processLogFileName = ftp.getSecondRecentProcessLogFile(filelist);
			FileCopyFromRemote copyObj = new FileCopyFromRemote();
			copyObj.startFTP(gathererIp, gatherer_userName, gatherer_password, DapProcessLogFolder,
					localProcessLogFilePath, "process.log");
			if (verifyRequestXMLExists(localProcessLogFilePath + "process.log", memId, refreshSource)) {
				System.out.println("Request xml is present in the process logs");
				// getRequestXML(localProcessLogFilePath, memId);
				String requsetxml = requestXmlFilePath + "reqRest.xml";
				System.out.println("Request xml file location: " + requsetxml);
				String req = LogAnalyzer.getYslRequestxml(localProcessLogFilePath + "process.log", memId,
						refreshSource);

				// create an xml file in the given path for the request xml retrieved from the
				// process.log file.
				LogAnalyzer.createActXml(req, requsetxml);
				historicTxnDurValuesMap = gethistoricTxnDuration(requsetxml);
			} else {
				copyObj.startFTP(gathererIp, gatherer_userName, gatherer_password, DapProcessLogFolder,
						localProcessLogFilePath, processLogFileName);
				System.out.println("Request xml is present in the process archive logs");
				// getRequestXML(localProcessLogFilePath, memId);
				String requsetxml = requestXmlFilePath + "reqRest.xml";
				System.out.println("Request xml file location: " + requsetxml);
				String req1 = LogAnalyzer.getYslRequestxml(localProcessLogFilePath + processLogFileName, memId,
						refreshSource);
				LogAnalyzer.createActXml(req1, requsetxml);
				historicTxnDurValuesMap = gethistoricTxnDuration(requsetxml);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}