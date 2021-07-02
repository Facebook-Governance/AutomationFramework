/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
package com.omni.pfm.executor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.omni.pfm.config.Config;
import com.omni.pfm.functions.ExecutionCommands;
import com.omni.pfm.utility.GenericUtil;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;
import com.omni.pfm.webdriver.WebDriverFactory;

/**
 * @author Suhaib
 *
 */
public class Executor {

	private static final Logger logger = LoggerFactory.getLogger(Executor.class);

	private final Document doc;
	private static Executor executor;
	private static String parentWindowHandle;
	
	public static void setParenthandle(String handle){
		parentWindowHandle = handle;
	}
	
	public static String getParentWindowHandle(){
		return parentWindowHandle;
	}
	
	
	private Executor(String env) {
		logger.info("==> Entering Executor's private constructor");
		logger.info("Environment is : {}", env);
		if (!GenericUtil.isNull(env)) {
			String fileName = env.toLowerCase() + "_execution.xml";
			this.doc = loadExecutionXMl(fileName);
		} else
			throw new NullPointerException();
	}

	public static Executor getInstance(String env) {
		logger.info("==>Entering getInstance(env)");
		if (executor == null) {
			synchronized (Config.class) {
				if (executor == null) {
					logger.info("Creating new Instance of Executor class");
					executor = new Executor(env);
				}
			}
		}
		logger.info("Returning Executor instance");
		return executor;
	}

	public static Executor getInstance() {
		logger.info("==>Entering getInstance()");
		if (executor == null) {
			logger.error("Instance not created yet. Use getInstance(String env) to create instance first");
		}
		logger.info("Returning Executor instance");
		return executor;
	}

	private Document loadExecutionXMl(String fileName) {
		logger.info("==>Entering loadExecutionXMl method...");
		logger.info("File Name is : " + fileName);

		DocumentBuilderFactory dbFactory = null;
		DocumentBuilder builder = null;
		Document doc = null;
		InputStream in = null;
		in = Executor.class.getClassLoader().getResourceAsStream(
				"executionXML" + File.separator + fileName);

		dbFactory = DocumentBuilderFactory.newInstance();
		try {
			builder = dbFactory.newDocumentBuilder();
			doc = builder.parse(in);
			doc.getDocumentElement().normalize();
		} catch (ParserConfigurationException e) {
			logger.error("Error Creating Document Builder object : {}", e);
			e.printStackTrace();
		} catch (SAXException | IOException ex) {
			logger.error("Error parisng the xml file : {}", ex);
			ex.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				logger.info("Error closing the input stream : {}", e);
				e.printStackTrace();
			}
		}
		logger.info("<== Exiting loadExecutionXML method...");
		return doc;
	}

	private static boolean executeTestCase(String testId, String... varArgs) {

		Executor executor = Executor.getInstance();
		Document doc = executor.doc;
		boolean actionStatus = true;
		try {
			Element root = doc.getDocumentElement();
			NodeList testCases = root.getElementsByTagName("test");
			System.out.println("Number of test found : " + testCases.getLength());
			for (int i = 0; i < testCases.getLength(); i++) {
				Node testCase = testCases.item(i);
				System.out.println(((Element) testCase).getAttribute("description"));
				logger.info("Test Case Description : {}",
						((Element) testCase).getAttribute("description"));
				Element ne = (Element) testCase;
				if (testId.equals(ne.getAttribute("id"))) {
					// String arguments[] = processTestCase(testCase, varArgs);
					NodeList testSteps = testCase.getChildNodes();
					for (int j = 0; j < testSteps.getLength(); j++) {
						Node testStep = testSteps.item(j);
						if (testStep.getNodeType() != Node.TEXT_NODE) {
							actionStatus = processTestStep(testStep, varArgs);
							logger.info("Test Step Passed = {}",actionStatus);
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("<== Exiting executeTestCase method...");
		return actionStatus;
	}

	static String[] processTestCase(Node testCase, String... args) {
		logger.info("==> Entering processTestCase method...");
		String[] arguments = null;
		if (testCase == null) {
			logger.error("Test case node is null");
		} else {
			Element e = (Element) testCase;
			logger.info("Test Case Id is : {}", e.getAttribute("id"));
			if (!GenericUtil.isNull(e.getAttribute("args"))) {
				logger.info("Argumenst for the test case are : {}", e.getAttribute("args"));
				arguments = e.getAttribute("args").split(";");
				logger.info("Number of Arguments = {}", arguments.length);
			}
		}
		// logger.info("Arguments array is : {}", arguments);
		logger.info("<== Exiting processTestCase method...");
		return arguments;
	}

	static boolean processTestStep(Node testStep, String[] varArgs) {
		logger.info("==> Entering processTestStep Method...");
		String pageName, frameName = "";
		String opType = testStep.getNodeName();
		Element testStepElement = (Element) testStep;
		pageName = testStepElement.getAttribute("pageName");
		frameName = testStepElement.getAttribute("frameName");
		NodeList elementList;
		boolean status = true;
		switch (opType) {

		case "populate":
			elementList = testStep.getChildNodes();
			for (int i = 0; i < elementList.getLength(); i++) {
				if (elementList.item(i).getNodeType() != Node.TEXT_NODE) {
					Element element = (Element) elementList.item(i);
					if (!element.getTagName().equalsIgnoreCase("element")) {
						System.out
						.println("Element is not proper. Can populate only <element> values");
					} else {
						String value = getValueOfElement(element, varArgs);
						logger.info("Value of Element is : {}",value);
						System.out.println("Populating Element : " + element.getAttribute("name"));
						status = SeleniumUtil.populateElement(element.getAttribute("name"), pageName,
								frameName, value, 5);
					}
				}

			}
			break;

		case "click":
			elementList = testStep.getChildNodes();
			for (int i = 0; i < elementList.getLength(); i++) {
				if (elementList.item(i).getNodeType() != Node.TEXT_NODE) {
					Element element = (Element) elementList.item(i);
					if (!element.getTagName().equalsIgnoreCase("element")) {
						System.out
						.println("Element is not proper. Can populate only <element> values");
					} else {
						System.out.println("Clicking Element : " + element.getAttribute("name"));
						SeleniumUtil.clickeElement(element.getAttribute("name"), pageName,
								frameName, 5);

					}
				}
			}
			break;

		case "assertEquals":
			elementList = testStep.getChildNodes();
			for (int i = 0; i < elementList.getLength(); i++) {
				if (elementList.item(i).getNodeType() != Node.TEXT_NODE) {
					Element element = (Element) elementList.item(i);
					if (element.getTagName().equalsIgnoreCase("element")) {
						System.out.println("Asserting value of element ["
								+ element.getAttribute("name") + "]...");
						String textToVerify = getValueOfElement(element, varArgs);
						status = ExecutionCommands.verifyElementText(element.getAttribute("name"), pageName, frameName, 30, textToVerify);
					} else if (element.getTagName().equalsIgnoreCase("title")) {
						String title = getValueOfElement(element, varArgs);
						logger.info("Expected Title is : {}",title); 
						status = ExecutionCommands.verifyTitle(title);
					} else {
						System.out
						.println("Assert equal Element : " + element.getAttribute("name"));
					}
				}
			}
			break;

		case "waitForElementInvisible":
			elementList = testStep.getChildNodes();
			for (int i = 0; i < elementList.getLength(); i++) {
				if (elementList.item(i).getNodeType() != Node.TEXT_NODE) {
					Element element = (Element) elementList.item(i);
					if (!element.getTagName().equalsIgnoreCase("element")) {
						System.out
						.println("Element is not proper. Can populate only <element> values");
					} else {
						System.out.println("Waiting for element to be invisible : "
								+ element.getAttribute("name"));
					}
				}
			}
			break;
			
		case "loadFrame":
			if(GenericUtil.isNull(pageName)){
				logger.error("Page Name not present for <loadFrame> action");
				status= false;
			}
			else if(GenericUtil.isNull(frameName)){
				logger.error("Frame Name not present for <loadFrame> element");;
				status = false;
			}
			else{
				status = ExecutionCommands.loadFrame(pageName, frameName);
			}
			break;
			
		case "unloadFrame" : 
			status = ExecutionCommands.unloadFrame();
			break;
			
		case "moveToPage" :
			if(GenericUtil.isNull(pageName)){
				logger.error("Page name is null or empty...");
				status = false;
			}
			else{
				status = ExecutionCommands.moveToPage(pageName);
			}
			break;
			
		case "call":
			System.out.println("Inside call command...");
			System.out.println("Node name is : "+testStep.getNodeName());
			String staticMethodName = testStepElement.getAttribute("method");
			String args = testStepElement.getAttribute("args");
			NodeList paramList = testStep.getChildNodes();
			System.out.println(testStep.getChildNodes().getLength()+" = number of clid");
			System.out.println("Param length is : "+paramList.getLength());
			for(int i=0;i < paramList.getLength(); i++){
				Node param = paramList.item(i);
				if(param.getNodeType()!=Node.TEXT_NODE)
				System.out.println("Param type is : "+param.getNodeName()+" : "+param.getNodeValue());
				
			}
			String className = null;
			String methodArguments[] = null;
			if(!GenericUtil.isNull(staticMethodName)){
				if(!GenericUtil.isNull(args)){
					methodArguments = args.split(",");
				}
				//status = ExecutionCommands.callMethod(className, staticMethodName,methodArguments);
			}
			else{
				logger.error("Method name not defined for the tag <call>");
				status = false;
			}
			break;
		default:
			logger.error("Invalid operation type defined : {}", opType);
			System.out.println("Invalid operation type defined : " + opType);
		}
		logger.info("<== Exiting processTestStep method...");
		return status;
		

	}
	
	private static String getValueOfElement(Element element,String[] varArgs){
		logger.info("==> Entering Executor.getValueOfElement method...");
		String value = "";
		if (!GenericUtil.isNull(element.getAttribute("value"))) {
			if (element.getAttribute("value").startsWith("arg")) {
				logger.info("Element contains argument : {}",
						element.getAttribute("value"));
				String a = element.getAttribute("value");
				String n = a.substring(3);
				logger.info("Value of argument index is : {}", n);
				if (varArgs == null || varArgs.length < 1) {
					logger.error("Element contains Arg type but no arguments passed");
				} else {
					try {
						logger.info("Setting element value to = {}",
								varArgs[Integer.parseInt(n)]);
						String val = PropsUtil.getDataPropertyValue(varArgs[Integer
						                                                    .parseInt(n)]);
						element.setAttribute("value", val);
					} catch (ArrayIndexOutOfBoundsException ex) {
						logger.error("Argument not found... Please check the number of arguments passed and the index of argument..");
						logger.error("Argument Passed in the XML = {}", a);
						logger.error("Length of arguments passed = {}",
								varArgs.length);
					}
				}
			}
			value = element.getAttribute("value");
			System.out.println("Value of Element is");
		} else if (!GenericUtil.isNull(element.getNodeValue())) {
			value = element.getNodeValue();
		}
		logger.info("<== Exiting Executor.getValueOfElement method...");
		return value;
	}

	/*public static void main(String[] args) {
		Config.createInstance("ALKAMI");
		Executor.getInstance("ALKAMI");
		//WebDriver d = WebDriverFactory.getInstance("chrome", "", "");
		//d.get("http://192.168.210.176:5080/opensaml/newopensaml/configAlkami.jsp");
		try {
			executeTestCase("call");
			executeTestCase("TC1");
			executeTestCase("TC2", "containerName", "containerLogin", "containerPwd",
					"containerPwd", "addAccountSuccessMsg");
		} catch (Exception e) {

		} finally {

			// d.close();
			// d.quit();
		}
	}*/
}
