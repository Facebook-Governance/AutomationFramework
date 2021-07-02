/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.pages.Login;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.Reporter;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.XmlBeans.Element;
import com.omni.pfm.annotations.Find;
import com.omni.pfm.config.Config;
import com.omni.pfm.constants.PageConstants;
import com.omni.pfm.factory.PageFactory;
import com.omni.pfm.listeners.EReporter;
import com.omni.pfm.rest.RegisterUser;
import com.omni.pfm.rest.rest;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.GenericUtil;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class LoginPage_DeepLink {
	 private static final Logger logger = LoggerFactory.getLogger(LoginPage_DeepLink.class);
	    private static final String p = "SAML_LOGIN";
	    @Find(label = "SAML_LOGIN.issuer", page = p)
	    static WebElement issuer;
	    @Find(label = "SAML_LOGIN.SubjectName", page = p)
	    static WebElement subjectName;
	    @Find(label = "SAML_LOGIN.ConsumerUrl", page = p)
	    static WebElement consumerUrl;
	    @Find(label = "SAML_LOGIN.TargetUrl", page = p)
	    static WebElement targetUrl;
	    @Find(label = "SAML_LOGIN.ProxyUrl", page = p)
	    static WebElement proxyUrl;
	    @Find(label = "SAML_LOGIN.AttributeString", page = p)
	    static WebElement attributeString;
	    @Find(label = "Signing_Key", page = p)
	    static WebElement signingKey;
	    @Find(label = "SSO_KEY", page = p)
	    static WebElement ssoKey;
	    static String fileName=null;
	    @Find(label = "SAML_LOGIN.Submit1", page = p)
	    static WebElement submit;
	
	
	 public  boolean login(WebDriver d) {
			logger.info("==> Entering SAML login");
			Element e;
			boolean result = true;
			if (d == null) {
			    logger.error("WebDriver instance in null - {}", d);
			} else {
			    d.get(PropsUtil.getEnvPropertyValue("SAML_BASE_URL"));
			    try {
				if ("yes".equalsIgnoreCase(PropsUtil.getEnvPropertyValue("cnf_IFrameEnabled"))) {
				    d.findElement(By.linkText("show LAW")).click();
				    PageParser.switchToFrame(d, "SAML_LOGIN", "MAIN_FRAME");
				}
			    } catch (Exception ex) {
			    }
			    WebElement changeConfig = SeleniumUtil.getWebElement(d, "ChangeConfiguration", "SAML_LOGIN", null);
			    if (changeConfig != null) {
				changeConfig.click();
			    }
			    PageFactory.initalize(d, LoginPage_DeepLink.class, "SAML_LOGIN");
			    e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.issuer");
			    if (e != null && !GenericUtil.isNull(e.getElementValue())) {
		//Added for Safari
			    	//issuer.isDisplayed();
		//END for Safari	    	
			    issuer.clear();
				issuer.sendKeys(e.getElementValue().trim());
			    }
			    e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.SubjectName");
			    subjectName.clear();
			    String userName = "";
			    if(PageConstants.NEW_USER_FOR_SAML.toUpperCase().trim().equals("YES"))
			    {
			    	//PageConstants.NEW_USER_FOR_SAML="no";
			    	//e.setElementValue(null);
			    	Config.getInstance().setCurrentUser(null);
			    }
			  
			    if (!GenericUtil.isNull(Config.getInstance().getCurrentUser())) {
				userName = Config.getInstance().getCurrentUser();
			    } else if(PropsUtil.getEnvPropertyValue("cnf_ReRun").trim().equalsIgnoreCase("yes")) {
			    	userName = e.getElementValue();
			}else{
				if (!GenericUtil.isNull(e.getElementValue()) && !PageConstants.NEW_USER_FOR_SAML.toUpperCase().trim().equals("YES") ) {
				    userName = e.getElementValue();
				} else {
					userName=getUserName();
					
				}
			    }
			    if(PageConstants.NEW_USER_FOR_SAML.toUpperCase().trim().equals("YES"))
			    {
			    	PageConstants.NEW_USER_FOR_SAML="no";
			    }
			    logger.info("User Name is : {}", userName);
			    subjectName.sendKeys(userName);
			    Reporter.log("User Name : " + userName);
			    Config.setUserName(userName);
			    e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.ConsumerUrl");
			    if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				logger.info("CONSUMER URL IS : {}", e.getElementValue());
				consumerUrl.clear();
				consumerUrl.sendKeys(e.getElementValue());
			    }
			    e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.TargetUrl");
			    if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				logger.info("TARGET URL IS : {}", e.getElementValue());
				targetUrl.clear();
				targetUrl.sendKeys(e.getElementValue());
			    }
			  //added by mohit fore stage BBTL
			   
			    e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.ProxyUrl");
			    if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				logger.info("Proxy URL IS : {}", e.getElementValue());
				proxyUrl.clear();
				proxyUrl.sendKeys(e.getElementValue());
			    }
			    
			   //end stage 
			    
			    
			    e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.AttributeString");
			    if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				attributeString.clear();
				//attributeString.sendKeys(e.getElementValue());
			    }
			    e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.SamlVersion");
			    if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				d.findElement(By.xpath("//input[@id='SAML_VERSION' and @value='" + e.getElementValue().trim() + "']")).click();
			    }
			    e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.AssertionEncryptFlag");
			    if (e != null && !GenericUtil.isNull(e.getElementValue()) && d.findElements(By.xpath("//input[@id='ASS_ENCRYPT_FLAG']")) != null
				    && d.findElements(By.xpath("//input[@id='ASS_ENCRYPT_FLAG']")).get(0).isDisplayed()) {
				if ("on".equalsIgnoreCase(e.getElementValue().trim())) {
				    d.findElement(By.xpath("//input[@id='ASS_ENCRYPT_FLAG' and @value='true']")).click();
				} else
				    d.findElement(By.xpath("//input[@id='ASS_ENCRYPT_FLAG' and @value='None']")).click();
			    }
			    e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.MultipleKey");
			    if (e != null && !GenericUtil.isNull(e.getElementValue()) && d.findElements(By.xpath("//input[@id='MULTI_KEY_FLAG']")) != null
				    && d.findElements(By.xpath("//input[@id='MULTI_KEY_FLAG']")).get(0).isDisplayed()) {
				if ("on".equalsIgnoreCase(e.getElementValue().trim())) {
				    d.findElement(By.xpath("//input[@id='MULTI_KEY_FLAG' and @value='true']")).click();
				} else
				    d.findElement(By.xpath("//input[@id='MULTI_KEY_FLAG' and @value='None']")).click();
			    }
			    e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.EncryptFlag");
			    if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				if (e.getElementValue().trim().equalsIgnoreCase("OFF")) {
				    WebElement encryptFlag = d.findElement(By.xpath("//input[@id='ENCRYPT_FLAG' and @value='None']"));
				    if (encryptFlag != null && encryptFlag.isDisplayed()) {
					d.findElement(By.xpath("//input[@id='ENCRYPT_FLAG' and @value='None']")).click();
				    }
				}else if(e.getElementValue().trim().equalsIgnoreCase("ON"))
				{
					 WebElement encryptFlag = d.findElement(By.xpath("//input[@id='ENCRYPT_FLAG' and @value='true']"));
					    if (encryptFlag != null && encryptFlag.isDisplayed()) {
						d.findElement(By.xpath("//input[@id='ENCRYPT_FLAG' and @value='true']")).click();
				}
					else if (e.getElementValue().trim().equalsIgnoreCase("EBC")) {
				    d.findElement(By.xpath("//input[@id='ENCRYPT_FLAG' and @value='EBC']")).click();
				} else if (e.getElementValue().trim().equalsIgnoreCase("CBC")) {
				    d.findElement(By.xpath("//input[@id='ENCRYPT_FLAG' and @value='CBC']")).click();
				}
				}
			    }
			    e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.ATTRIB_ENCRYPTION_MECHANISM");
			    if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				List<WebElement> list = d.findElements(By.id(e.getValue()));
				for (WebElement we : list) {
				    if (we.getAttribute("value").contains(e.getElementValue().trim())) {
					we.click();
					break;
				    }
				}
			    }
			    e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.EncodeFlag");
			    if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				if (e.getElementValue().trim().equalsIgnoreCase("on") || e.getElementValue().trim().equalsIgnoreCase("true")) {
				    d.findElement(By.xpath("//input[@id='ENCODE_ATTR_FLAG' and @value='true']")).click();
				} else {
				    d.findElement(By.xpath("//input[@id='ENCODE_ATTR_FLAG' and @value='false']")).click();
				}
			    }
			    e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.SignResponse");
			    if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				if (e.getElementValue().trim().equalsIgnoreCase("true")) {
				    d.findElement(By.xpath("//input[@id='SIGN_RES_FLAG' and @value='true']")).click();
				} else if (e.getElementValue().trim().equalsIgnoreCase("false")) {
				    d.findElement(By.xpath("//input[@id='SIGN_RES_FLAG' and @value='false']")).click();
				}
			    }
			    e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.SignAssertion");
			    if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				if (e.getElementValue().trim().equalsIgnoreCase("true")) {
				    d.findElement(By.xpath("//input[@id='SIGN_ASSER_FLAG' and @value='true']")).click();
				} else if (e.getElementValue().trim().equalsIgnoreCase("false")) {
				    d.findElement(By.xpath("//input[@id='SIGN_ASSER_FLAG' and @value='false']")).click();
				}
			    }
			    e = PageParser.getPageElement("SAML_LOGIN", "Signing_Key");
			    if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				logger.info("Signing Key Value is : {}", e.getElementValue());
				if (signingKey != null && signingKey.isDisplayed()) {
				    signingKey.clear();
				    signingKey.sendKeys(e.getElementValue());
				}
			    }
			    e = PageParser.getPageElement("SAML_LOGIN", "SSO_KEY");
			    if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				logger.info("SSO ATTRIB KEY Value is : {}", e.getElementValue());
				ssoKey.clear();
				ssoKey.sendKeys(e.getElementValue());
			    }
			    e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.LinkIntegrityToken");
			    if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				if (e.getElementValue().trim().equalsIgnoreCase("true")) {
				    d.findElement(By.xpath("//input[@id='LIT_FLAG' and @value='true']")).click();
				} else if (e.getElementValue().trim().equalsIgnoreCase("false")) {
					try {
						Thread.sleep(4000);
						//d.findElement(By.xpath("//input[@id='LIT_FLAG' and @value='false']")).click();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				    
				}
			    }
			    SeleniumUtil.scrollToWebElementMiddle(d, submit);
			    System.out.println("CLICKING SUBMIT");
			    SeleniumUtil.click(submit);
			    System.out.println("FINISHED CLICKING SUBMIT");
			   WebElement nxt=SeleniumUtil.getVisibileWebElement(d, "SAML_LOGIN.Submit2", "SAML_LOGIN", null);
			    logger.info("The next button value="+nxt);
			    try {
					Thread.sleep(3000);
				} catch (InterruptedException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			    if(Config.getInstance().getEnvironment().equalsIgnoreCase("bac"))
			    {
			    	try {
						Thread.sleep(3000);
					} catch (InterruptedException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
			    	
			    	 if(!Config.getInstance().getEnvironment().equalsIgnoreCase("bacStage")){
			    		 d.findElement(By.cssSelector("input[type='submit']")).click();
			    		    }
			    }else{
			    	if(Config.getInstance().getEnvironment().equalsIgnoreCase("mgpStage")){
			    		 d.findElement(By.cssSelector("input[type='submit']")).click();
			    		    }
			    	if(!Config.getInstance().getEnvironment().equalsIgnoreCase("mgpStage")){
			    		 SeleniumUtil.click(nxt);
			    		    }

			   
			    }
			    
			    GenericUtil.writeUserToFile(Config.getInstance().getEnvironment(), userName);
			    // added by mohit
			    WebElement loading = SeleniumUtil.getVisibileWebElement(d, "loadingPage", "SAML_LOGIN", null);
			    if (loading != null) {
				try { 
				    SeleniumUtil.waitUntilPresenceOfElement(d, "AdvertisingFloater", "SAML_LOGIN", null, 180);
				} catch (InterruptedException e1) {
				    e1.printStackTrace();
				}
				WebElement advFloater = SeleniumUtil.getVisibileWebElement(d, "AdvertisingFloater", "SAML_LOGIN", null);
				if (advFloater != null) {
				    SeleniumUtil.click(advFloater);
				} else {
				    EReporter.log(LogStatus.INFO, "The close button on Advertising is not Visible and clicked");
				}
			    }
			    // END Mohit
			 //for Wells Fargo   
			    WebElement agreement=SeleniumUtil.getVisibileWebElement(d, "AcceptAgreement", "SAML_LOGIN", null);
			    if(agreement!=null)
			    {
			    	SeleniumUtil.click(agreement);
			    }
			    Config.getInstance().setCurrentUser(userName);
			    EReporter.log(LogStatus.INFO, "USER = " + userName);
			    // String title = PropsUtil.getDataPropertyValue(titleLabel);
			    // logger.info("Title is - {}", title);
			    // result = d.getTitle().contains(title);
			    By hideByObject = SeleniumUtil.getByObject("SAML_LOGIN", null, "hideGettingStarted");
			    WebElement hideGS = SeleniumUtil.waitUntilElementVisible(d, hideByObject, 5);
			    if (hideGS != null) {
				try {
				    // scroll for chrome browser
				    JavascriptExecutor je = (JavascriptExecutor) d;
				    je.executeScript("arguments[0].scrollIntoView(true);", hideGS);
				    hideGS.click();
				} catch (Exception ex) {
				    logger.warn("Could not click on hide button... Trying again...");
				    SeleniumUtil.click(SeleniumUtil.getWebElement(d, "hideGettingStarted", "SAML_LOGIN", null));
				}
			    }
			}
			logger.info("Title matched - {}", result);
			logger.info("<== Exiting SAML Login...");
			return result;
			
		    }
    
	 
	 public static String getUserName() {
	    	logger.info("Entering getUserName method.");
	    	String userConst = Config.getInstance().getEnvironment();
	    	return userConst + System.currentTimeMillis();
	        }
}
