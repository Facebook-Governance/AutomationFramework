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
import org.testng.Reporter;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.XmlBeans.Element;
import com.omni.pfm.annotations.Find;
import com.omni.pfm.config.Config;
import com.omni.pfm.constants.PageConstants;
import com.omni.pfm.factory.PageFactory;
import com.omni.pfm.listeners.EReporter;
import com.omni.pfm.utility.GenericUtil;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;
import com.omni.pfm.webdriver.WebDriverFactory;
import com.relevantcodes.extentreports.LogStatus;

public class LoginPage_SAML3 {
	private static final Logger logger = LoggerFactory.getLogger(LoginPage_SAML3.class);
	private static final String p = "SAML_LOGIN";
	@Find(label = "SAML_LOGIN.issuer", page = p)
	static WebElement issuer;
	@Find(label = "SAML_LOGIN.SubjectName", page = p)
	static WebElement subjectName;
	@Find(label = "SAML_LOGIN.ConsumerUrl", page = p)
	static WebElement consumerUrl;
	/*
	 * @Find(label = "SAML_LOGIN.TargetUrl", page = p) static WebElement targetUrl;
	 * 
	 * @Find(label = "SAML_LOGIN.ProxyUrl", page = p) static WebElement proxyUrl;
	 */
	@Find(label = "SAML_LOGIN.AttributeString1", page = p)
	static WebElement attributeString;
	@Find(label = "SAML_LOGIN.AttributeString2", page = p)
	static WebElement attributeString2;
	@Find(label = "SAML_LOGIN.AttributeString3", page = p)
	static WebElement attributeString3;
	@Find(label = "Signing_Key", page = p)
	static WebElement signingKey;
	@Find(label = "SSO_KEY", page = p)
	static WebElement ssoKey;
	static String fileName = null;
	@Find(label = "SAML_LOGIN.Submit1", page = p)
	static WebElement submit;
	String adviserName = null;
	@Find(label = "SAML_LOGIN.FINAPPID", page = p)
	static WebElement finAppId;
	@Find(label = "SAML_LOGIN.EXTRParams", page = p)
	static WebElement extrParams;
	@Find(label = "SAML_LOGIN.EXTRParams2", page = p)
	static WebElement extrParams2;

	String InvesterName = "";

	public String createAdvisor(WebDriver d, String investerName, String refrenceId, String finappId) {
		String userName = "";
		logger.info("==> Entering SAML login");
		Element e;
		boolean result = true;
		if (d == null) {
			logger.error("WebDriver instance in null - {}", d);
		} else {
			d.get(PropsUtil.getEnvPropertyValue("SAML_BASE_URL"));
			try {
				JavascriptExecutor js = (JavascriptExecutor) WebDriverFactory.getDriver();
				js.executeScript("document.querySelector('#proceed-link').click();");
			} catch (Exception e1) {

			}
			try {
				if ("yes".equalsIgnoreCase(PropsUtil.getEnvPropertyValue("cnf_IFrameEnabled"))) {
					d.findElement(By.linkText("show LAW")).click();
					PageParser.switchToFrame(d, "SAML_LOGIN", "MAIN_FRAME");
				}
			} catch (Exception ex) {
			}
			By changeConfig = SeleniumUtil.getByObject("SAML_LOGIN", null, "ChangeConfiguration");
			if (SeleniumUtil.isDisplayed(changeConfig, 2)) {
				SeleniumUtil.click(changeConfig);
			}
			PageFactory.initalize(d, LoginPage_SAML3.class, "SAML_LOGIN");
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.issuer");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				issuer.clear();
				issuer.sendKeys(e.getElementValue().trim());
			}
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.SubjectName");
			subjectName.clear();
			if (PageConstants.NEW_USER_FOR_SAML.toUpperCase().trim().equals("YES")) {
				Config.getInstance().setCurrentUser(null);
			}

			if (!GenericUtil.isNull(Config.getInstance().getCurrentUser())) {
				userName = Config.getInstance().getCurrentUser();
			} else if (PropsUtil.getEnvPropertyValue("cnf_ReRun").trim().equalsIgnoreCase("yes")) {
				userName = e.getElementValue();
			} else {
				if (!GenericUtil.isNull(e.getElementValue())
						&& !PageConstants.NEW_USER_FOR_SAML.toUpperCase().trim().equals("YES")) {
					userName = e.getElementValue();
				} else {
					userName = getAdvisorUserName();

				}
			}
			if (PageConstants.NEW_USER_FOR_SAML.toUpperCase().trim().equals("YES")) {
				PageConstants.NEW_USER_FOR_SAML = "no";
			}
			logger.info("User Name is : {}", userName);
			subjectName.sendKeys(userName);
			Reporter.log("User Name : " + userName);
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.ConsumerUrl");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				logger.info("CONSUMER URL IS : {}", e.getElementValue());
				consumerUrl.clear();
				consumerUrl.sendKeys(e.getElementValue());
			}
			finAppId.clear();
			finAppId.sendKeys(finappId);
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.extrParams");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				logger.info("CONSUMER URL IS : {}", e.getElementValue());
				extrParams.clear();
				if (investerName == null) {
					String extparamvalue = e.getElementValue();
					extrParams.sendKeys(extparamvalue);
				} else {
					String extparamvalue = e.getElementValue() + "&IID=" + investerName;
					extrParams.sendKeys(extparamvalue);
				}
			}

			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.AttributeString1");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				attributeString.clear();
				logger.info(e.getElementValue());
				String adviserRefre = refrenceId;

				String atribute = e.getElementValue().replace("refrenceId1", adviserRefre);

				attributeString.sendKeys(atribute);
			}
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.SamlVersion");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				d.findElement(By.xpath("//input[@id='SAML_VERSION' and @value='" + e.getElementValue().trim() + "']"))
						.click();
			}
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.AssertionEncryptFlag");
			if (e != null && !GenericUtil.isNull(e.getElementValue())
					&& d.findElements(By.xpath("//input[@id='ASS_ENCRYPT_FLAG']")) != null
					&& d.findElements(By.xpath("//input[@id='ASS_ENCRYPT_FLAG']")).get(0).isDisplayed()) {
				if ("on".equalsIgnoreCase(e.getElementValue().trim())) {
					d.findElement(By.xpath("//input[@id='ASS_ENCRYPT_FLAG' and @value='true']")).click();
				} else
					d.findElement(By.xpath("//input[@id='ASS_ENCRYPT_FLAG' and @value='None']")).click();
			}
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.MultipleKey");
			if (e != null && !GenericUtil.isNull(e.getElementValue())
					&& d.findElements(By.xpath("//input[@id='MULTI_KEY_FLAG']")) != null
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
				} else if (e.getElementValue().trim().equalsIgnoreCase("ON")) {
					WebElement encryptFlag = d.findElement(By.xpath("//input[@id='ENCRYPT_FLAG' and @value='true']"));
					if (encryptFlag != null && encryptFlag.isDisplayed()) {
						d.findElement(By.xpath("//input[@id='ENCRYPT_FLAG' and @value='true']")).click();
					} else if (e.getElementValue().trim().equalsIgnoreCase("EBC")) {
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
				if (e.getElementValue().trim().equalsIgnoreCase("on")
						|| e.getElementValue().trim().equalsIgnoreCase("true")) {
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
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			}
			SeleniumUtil.scrollToWebElementMiddle(d, submit);
			logger.info("CLICKING SUBMIT");
			SeleniumUtil.click(submit);
			logger.info("FINISHED CLICKING SUBMIT");
			WebElement nxt = SeleniumUtil.getVisibileWebElement(d, "SAML_LOGIN.Submit2", "SAML_LOGIN", null);
			logger.info("The next button value=" + nxt);
			if (Config.getInstance().getEnvironment().equalsIgnoreCase("bac")) {
				if (!Config.getInstance().getEnvironment().equalsIgnoreCase("bacStage")) {
					d.findElement(By.cssSelector("input[type='submit']")).click();
				}
			} else {
				if (Config.getInstance().getEnvironment().equalsIgnoreCase("mgpStage")) {
					d.findElement(By.cssSelector("input[type='submit']")).click();
				}
				if (!Config.getInstance().getEnvironment().equalsIgnoreCase("mgpStage")) {
					d.findElement(By.cssSelector("input[type='submit']")).click();
				}
			}
			try {
				JavascriptExecutor js = (JavascriptExecutor) WebDriverFactory.getDriver();
				js.executeScript("document.querySelector('#proceed-link').click();");
			} catch (Exception e2) {
			}
			GenericUtil.writeUserToFile(Config.getInstance().getEnvironment(), userName);
			By loadingObject = SeleniumUtil.getByObject("SAML_LOGIN", null, "loadingPage");
			if (SeleniumUtil.isDisplayed(loadingObject, 10)) {
				try {
					SeleniumUtil.waitUntilPresenceOfElement(d, "AdvertisingFloater", "SAML_LOGIN", null, 180);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				By advFloater = SeleniumUtil.getByObject("SAML_LOGIN", null, "AdvertisingFloater");
				if (SeleniumUtil.isDisplayed(advFloater, 10)) {
					SeleniumUtil.click(advFloater);
				} else {
					EReporter.log(LogStatus.INFO, "The close button on Advertising is not Visible and clicked");
				}
			}

			By agreement = SeleniumUtil.getByObject("SAML_LOGIN", null, "AcceptAgreement");
			if (SeleniumUtil.isDisplayed(agreement, 2)) {
				SeleniumUtil.click(agreement);
			}
			EReporter.log(LogStatus.INFO, "USER = " + userName);
			By hideByObject = SeleniumUtil.getByObject("SAML_LOGIN", null, "hideGettingStarted");
			if (SeleniumUtil.isDisplayed(hideByObject, 2)) {
				try {
					JavascriptExecutor je = (JavascriptExecutor) d;
					je.executeScript("arguments[0].scrollIntoView(true);",
							SeleniumUtil.getVisibileWebElement(d, "hideGettingStarted", "SAML_LOGIN", null));
					SeleniumUtil.click(hideByObject);
				} catch (Exception ex) {
					logger.warn("Could not click on hide button... Trying again...");
					SeleniumUtil.click(SeleniumUtil.getWebElement(d, "hideGettingStarted", "SAML_LOGIN", null));
				}
			}
		}
		logger.info("Title matched - {}", result);
		logger.info("<== Exiting SAML Login...");
		SeleniumUtil.refresh(d);
		return userName;
	}

	public String createAdvisor2(WebDriver d, String investerName, String refrenceId, String FinappId) {
		Element e;
		String userName = "";
		if (d == null) {
			logger.error("WebDriver instance in null - {}", d);
		} else {
			d.get(PropsUtil.getEnvPropertyValue("SAML_BASE_URL"));
			try {
				JavascriptExecutor js = (JavascriptExecutor) WebDriverFactory.getDriver();
				js.executeScript("document.querySelector('#proceed-link').click();");
			} catch (Exception e1) {

			}
			By changeConfig = SeleniumUtil.getByObject("SAML_LOGIN", null, "ChangeConfiguration");
			if (SeleniumUtil.isDisplayed(changeConfig, 2)) {
				SeleniumUtil.click(changeConfig);
			}
			PageFactory.initalize(d, LoginPage_SAML3.class, "SAML_LOGIN");
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.issuer");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				issuer.clear();
				issuer.sendKeys(e.getElementValue().trim());
			}
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.SubjectName");
			if (e != null) {
				subjectName.clear();
				userName = getAdvisorUserName();
				subjectName.sendKeys(userName);
			}
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.ConsumerUrl");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				consumerUrl.clear();
				consumerUrl.sendKeys(e.getElementValue());
			}
			/*
			 * e=PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.finAppId"); if (e !=
			 * null && !GenericUtil.isNull(e.getElementValue())) { finAppId.clear();
			 * finAppId.sendKeys(e.getElementValue()); }
			 */

			finAppId.clear();
			finAppId.sendKeys(FinappId);

			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.extrParams");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				extrParams.clear();
				if (investerName == null) {
					String extparamvalue = e.getElementValue();
					extrParams.sendKeys(extparamvalue);
				} else {
					String extparamvalue = e.getElementValue() + "&IID=" + investerName;
					extrParams.sendKeys(extparamvalue);
				}
			}

			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.AttributeString1");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				attributeString.clear();
				logger.info(e.getElementValue());
				String adviserRefre = refrenceId;
				String atribute = e.getElementValue().replace("refrenceId1", adviserRefre);
				attributeString.sendKeys(atribute);
			}

			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.SamlVersion");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				SeleniumUtil.click(d.findElement(
						By.xpath("//input[@id='SAML_VERSION' and @value='" + e.getElementValue().trim() + "']")));
			}

			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.AssertionEncryptFlag");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				if ("on".equalsIgnoreCase(e.getElementValue().trim())) {
					SeleniumUtil.click(d.findElement(By.xpath("//input[@id='ASS_ENCRYPT_FLAG' and @value='true']")));
				} else
					SeleniumUtil.click(d.findElement(By.xpath("//input[@id='ASS_ENCRYPT_FLAG' and @value='None']")));
			}

			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.MultipleKey");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				if ("on".equalsIgnoreCase(e.getElementValue().trim())) {
					SeleniumUtil.click(d.findElement(By.xpath("//input[@id='MULTI_KEY_FLAG' and @value='true']")));
				} else
					SeleniumUtil.click(d.findElement(By.xpath("//input[@id='MULTI_KEY_FLAG' and @value='None']")));
			}

			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.EncryptFlag");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				if ("on".equalsIgnoreCase(e.getElementValue().trim())) {
					SeleniumUtil.click(d.findElement(By.xpath("//input[@id='ENCRYPT_FLAG' and @value='true']")));
				} else
					SeleniumUtil.click(d.findElement(By.xpath("//input[@id='ENCRYPT_FLAG' and @value='None']")));
			}

			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.ATTRIB_ENCRYPTION_MECHANISM");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				List<WebElement> list = d.findElements(By.id(e.getValue()));
				for (WebElement we : list) {
					if (we.getAttribute("value").contains(e.getElementValue().trim())) {
						SeleniumUtil.click(we);
						break;
					}
				}
			}
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.EncodeFlag");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				if (e.getElementValue().trim().equalsIgnoreCase("on")
						|| e.getElementValue().trim().equalsIgnoreCase("true")) {
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

		}
		e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.LinkIntegrityToken");
		if (e != null && !GenericUtil.isNull(e.getElementValue())) {
			if ("on".equalsIgnoreCase(e.getElementValue().trim())) {
				SeleniumUtil.click(d.findElement(By.xpath("//input[@id='LIT_FLAG' and @value='true']")));
			} else
				SeleniumUtil.click(d.findElement(By.xpath("//input[@id='ENCRYPT_FLAG' and @value='None']")));
		}

		SeleniumUtil.scrollToWebElementMiddle(d, submit);
		SeleniumUtil.click(submit);
		SeleniumUtil.waitForPageToLoad(3000);
		WebElement nxt = SeleniumUtil.getVisibileWebElement(d, "SAML_LOGIN.Submit2", "SAML_LOGIN", null);
		SeleniumUtil.click(nxt);
		logger.info("<== Exiting SAML Login...");
		return userName;
	}

	public String getAdvisorUserName() {
		logger.info("Entering getUserName method.");
		// String userConst = Config.getInstance().getEnvironment();
		return PropsUtil.getEnvPropertyValue("AdvisorName") + System.currentTimeMillis();
	}

	public String getInvestorUserName() {
		logger.info("Entering getUserName method.");
		// String userConst = Config.getInstance().getEnvironment();
		return PropsUtil.getEnvPropertyValue("InvestorName") + System.currentTimeMillis();
	}

	public String getRefrenceId() {
		logger.info("Entering getUserName method.");
		// String userConst = Config.getInstance().getEnvironment();
		return PropsUtil.getEnvPropertyValue("ReferenceId") + System.currentTimeMillis();
	}

	public String createInvestor2(WebDriver d, String advisor, String investor, String AccountName,
			String AccountPassword) {
		Element e;
		if (d == null) {
			logger.error("WebDriver instance in null - {}", d);
		} else {
			d.get(PropsUtil.getEnvPropertyValue("SAML_BASE_URL"));
			try {
				JavascriptExecutor js = (JavascriptExecutor) WebDriverFactory.getDriver();
				js.executeScript("document.querySelector('#proceed-link').click();");
			} catch (Exception e1) {

			}
			By changeConfig = SeleniumUtil.getByObject("SAML_LOGIN", null, "ChangeConfiguration");
			if (SeleniumUtil.isDisplayed(changeConfig, 2)) {
				SeleniumUtil.click(changeConfig);
			}
			PageFactory.initalize(d, LoginPage_SAML3.class, "SAML_LOGIN");
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.issuer");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				issuer.clear();
				issuer.sendKeys(e.getElementValue().trim());
			}
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.SubjectName");
			if (e != null) {
				subjectName.clear();
				subjectName.sendKeys(investor);
			}
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.ConsumerUrl");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				consumerUrl.clear();
				consumerUrl.sendKeys(e.getElementValue());
			}
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.finAppId");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				finAppId.clear();
				finAppId.sendKeys(e.getElementValue());
			}
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.EXTRParams2");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				extrParams2.clear();
				extrParams2.sendKeys("keyword=dag");
			}
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.AttributeString2");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				attributeString2.clear();
				logger.info(e.getElementValue());
				adviserName = getRefrenceId();
				String atribute = e.getElementValue().replace("refrenceId1", adviserName);
				logger.info(atribute);
				atribute = atribute.replace("advisorSaml1", advisor);
				logger.info(atribute);
				attributeString2.sendKeys(atribute);
			}

			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.SamlVersion");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				SeleniumUtil.click(d.findElement(
						By.xpath("//input[@id='SAML_VERSION' and @value='" + e.getElementValue().trim() + "']")));
			}

			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.AssertionEncryptFlag");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				if ("on".equalsIgnoreCase(e.getElementValue().trim())) {
					SeleniumUtil.click(d.findElement(By.xpath("//input[@id='ASS_ENCRYPT_FLAG' and @value='true']")));
				} else
					SeleniumUtil.click(d.findElement(By.xpath("//input[@id='ASS_ENCRYPT_FLAG' and @value='None']")));
			}

			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.MultipleKey");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				if ("on".equalsIgnoreCase(e.getElementValue().trim())) {
					SeleniumUtil.click(d.findElement(By.xpath("//input[@id='MULTI_KEY_FLAG' and @value='true']")));
				} else
					SeleniumUtil.click(d.findElement(By.xpath("//input[@id='MULTI_KEY_FLAG' and @value='None']")));
			}

			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.EncryptFlag");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				if ("on".equalsIgnoreCase(e.getElementValue().trim())) {
					SeleniumUtil.click(d.findElement(By.xpath("//input[@id='ENCRYPT_FLAG' and @value='true']")));
				} else
					SeleniumUtil.click(d.findElement(By.xpath("//input[@id='ENCRYPT_FLAG' and @value='None']")));
			}

			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.ATTRIB_ENCRYPTION_MECHANISM");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				List<WebElement> list = d.findElements(By.id(e.getValue()));
				for (WebElement we : list) {
					if (we.getAttribute("value").contains(e.getElementValue().trim())) {
						SeleniumUtil.click(we);
						break;
					}
				}
			}
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.EncodeFlag");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				if (e.getElementValue().trim().equalsIgnoreCase("on")
						|| e.getElementValue().trim().equalsIgnoreCase("true")) {
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

		}
		e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.LinkIntegrityToken");
		if (e != null && !GenericUtil.isNull(e.getElementValue())) {
			if ("on".equalsIgnoreCase(e.getElementValue().trim())) {
				SeleniumUtil.click(d.findElement(By.xpath("//input[@id='LIT_FLAG' and @value='true']")));
			} else
				SeleniumUtil.click(d.findElement(By.xpath("//input[@id='ENCRYPT_FLAG' and @value='None']")));
		}

		SeleniumUtil.scrollToWebElementMiddle(d, submit);
		SeleniumUtil.click(submit);
		SeleniumUtil.waitForPageToLoad(3000);
		WebElement nxt = SeleniumUtil.getVisibileWebElement(d, "SAML_LOGIN.Submit2", "SAML_LOGIN", null);
		SeleniumUtil.click(nxt);
		logger.info("<== Exiting SAML Login...");

		return InvesterName;
	}

	/*
	 * Create investor with Prepop Account Written By : Shivaprasad
	 */
	public String createInvestorWithPrepop(WebDriver d, String advisor, String investor) {
		Element e;
		if (d == null) {
			logger.error("WebDriver instance in null - {}", d);
		} else {
			d.get(PropsUtil.getEnvPropertyValue("SAML_BASE_URL"));
			try {
				JavascriptExecutor js = (JavascriptExecutor) WebDriverFactory.getDriver();
				js.executeScript("document.querySelector('#proceed-link').click();");
			} catch (Exception e1) {

			}
			By changeConfig = SeleniumUtil.getByObject("SAML_LOGIN", null, "ChangeConfiguration");
			if (SeleniumUtil.isDisplayed(changeConfig, 2)) {
				SeleniumUtil.click(changeConfig);
			}
			PageFactory.initalize(d, LoginPage_SAML3.class, "SAML_LOGIN");
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.issuer");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				issuer.clear();
				issuer.sendKeys(e.getElementValue().trim());
			}
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.SubjectName");
			if (e != null) {
				subjectName.clear();
				subjectName.sendKeys(investor);
			}
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.ConsumerUrl");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				consumerUrl.clear();
				consumerUrl.sendKeys(e.getElementValue());
			}
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.finAppId");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				finAppId.clear();
				finAppId.sendKeys(e.getElementValue());
			}
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.EXTRParams2");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				extrParams2.clear();
				extrParams2.sendKeys("keyword=dag");
			}
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.AttributeString4");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				attributeString2.clear();
				logger.info(e.getElementValue());
				adviserName = getRefrenceId();
				String atribute = e.getElementValue().replace("refrenceId1", adviserName);
				logger.info(atribute);
				atribute = atribute.replace("advisorSaml1", advisor);
				logger.info(atribute);
				attributeString2.sendKeys(atribute);
			}

			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.SamlVersion");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				SeleniumUtil.click(d.findElement(
						By.xpath("//input[@id='SAML_VERSION' and @value='" + e.getElementValue().trim() + "']")));
			}

			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.AssertionEncryptFlag");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				if ("on".equalsIgnoreCase(e.getElementValue().trim())) {
					SeleniumUtil.click(d.findElement(By.xpath("//input[@id='ASS_ENCRYPT_FLAG' and @value='true']")));
				} else
					SeleniumUtil.click(d.findElement(By.xpath("//input[@id='ASS_ENCRYPT_FLAG' and @value='None']")));
			}

			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.MultipleKey");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				if ("on".equalsIgnoreCase(e.getElementValue().trim())) {
					SeleniumUtil.click(d.findElement(By.xpath("//input[@id='MULTI_KEY_FLAG' and @value='true']")));
				} else
					SeleniumUtil.click(d.findElement(By.xpath("//input[@id='MULTI_KEY_FLAG' and @value='None']")));
			}

			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.EncryptFlag");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				if ("on".equalsIgnoreCase(e.getElementValue().trim())) {
					SeleniumUtil.click(d.findElement(By.xpath("//input[@id='ENCRYPT_FLAG' and @value='true']")));
				} else
					SeleniumUtil.click(d.findElement(By.xpath("//input[@id='ENCRYPT_FLAG' and @value='None']")));
			}

			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.ATTRIB_ENCRYPTION_MECHANISM");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				List<WebElement> list = d.findElements(By.id(e.getValue()));
				for (WebElement we : list) {
					if (we.getAttribute("value").contains(e.getElementValue().trim())) {
						SeleniumUtil.click(we);
						break;
					}
				}
			}
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.EncodeFlag");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				if (e.getElementValue().trim().equalsIgnoreCase("on")
						|| e.getElementValue().trim().equalsIgnoreCase("true")) {
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

		}
		e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.LinkIntegrityToken");
		if (e != null && !GenericUtil.isNull(e.getElementValue())) {
			if ("on".equalsIgnoreCase(e.getElementValue().trim())) {
				SeleniumUtil.click(d.findElement(By.xpath("//input[@id='LIT_FLAG' and @value='true']")));
			} else
				SeleniumUtil.click(d.findElement(By.xpath("//input[@id='ENCRYPT_FLAG' and @value='None']")));
		}

		SeleniumUtil.scrollToWebElementMiddle(d, submit);
		SeleniumUtil.click(submit);
		SeleniumUtil.waitForPageToLoad(3000);
		WebElement nxt = SeleniumUtil.getVisibileWebElement(d, "SAML_LOGIN.Submit2", "SAML_LOGIN", null);
		SeleniumUtil.click(nxt);
		logger.info("<== Exiting SAML Login...");

		return InvesterName;
	}

	public String createInvestor(WebDriver d, String advisor, String investor, String AccountName,
			String AccountPassword) {
		String userName = "";
		logger.info("==> Entering SAML login");
		Element e;
		boolean result = true;
		if (d == null) {
			logger.error("WebDriver instance in null - {}", d);
		} else {
			d.get(PropsUtil.getEnvPropertyValue("SAML_BASE_URL"));
			try {
				JavascriptExecutor js = (JavascriptExecutor) WebDriverFactory.getDriver();
				js.executeScript("document.querySelector('#proceed-link').click();");
			} catch (Exception e1) {

			}
			try {
				if ("yes".equalsIgnoreCase(PropsUtil.getEnvPropertyValue("cnf_IFrameEnabled"))) {
					d.findElement(By.linkText("show LAW")).click();
					PageParser.switchToFrame(d, "SAML_LOGIN", "MAIN_FRAME");
				}
			} catch (Exception ex) {
			}
			By changeConfig = SeleniumUtil.getByObject("SAML_LOGIN", null, "ChangeConfiguration");
			if (SeleniumUtil.isDisplayed(changeConfig, 2)) {
				SeleniumUtil.click(changeConfig);
			}
			PageFactory.initalize(d, LoginPage_SAML3.class, "SAML_LOGIN");
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.issuer");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				// Added for Safari
				// issuer.isDisplayed();
				// END for Safari
				issuer.clear();
				issuer.sendKeys(e.getElementValue().trim());
			}
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.SubjectName");
			subjectName.clear();
			if (PageConstants.NEW_USER_FOR_SAML.toUpperCase().trim().equals("YES")) {
				// PageConstants.NEsW_USER_FOR_SAML="no";
				// e.setElementValue(null);
				Config.getInstance().setCurrentUser(null);
			}

			if (!GenericUtil.isNull(Config.getInstance().getCurrentUser())) {
				userName = Config.getInstance().getCurrentUser();
			} else if (PropsUtil.getEnvPropertyValue("cnf_ReRun").trim().equalsIgnoreCase("yes")) {
				userName = e.getElementValue();
			} else {
				if (!GenericUtil.isNull(e.getElementValue())
						&& !PageConstants.NEW_USER_FOR_SAML.toUpperCase().trim().equals("YES")) {
					userName = e.getElementValue();
				} else {
					InvesterName = investor;

				}
			}
			if (PageConstants.NEW_USER_FOR_SAML.toUpperCase().trim().equals("YES")) {
				PageConstants.NEW_USER_FOR_SAML = "no";
			}
			logger.info("User Name is : {}", investor);
			subjectName.sendKeys(investor);
			Reporter.log("User Name : " + investor);
			Config.setUserName(investor);
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.ConsumerUrl");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				logger.info("CONSUMER URL IS : {}", e.getElementValue());
				consumerUrl.clear();
				consumerUrl.sendKeys(e.getElementValue());
			}
			/*
			 * e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.TargetUrl"); if (e !=
			 * null && !GenericUtil.isNull(e.getElementValue())) {
			 * logger.info("TARGET URL IS : {}", e.getElementValue()); targetUrl.clear();
			 * targetUrl.sendKeys(e.getElementValue()); } //added by mohit fore stage BBTL
			 * 
			 * e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.ProxyUrl"); if (e !=
			 * null && !GenericUtil.isNull(e.getElementValue())) {
			 * logger.info("Proxy URL IS : {}", e.getElementValue()); proxyUrl.clear();
			 * proxyUrl.sendKeys(e.getElementValue()); }
			 */

			// end stage

			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.finAppId");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				logger.info("CONSUMER URL IS : {}", e.getElementValue());
				finAppId.clear();
				finAppId.sendKeys(e.getElementValue());
			}
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.EXTRParams2");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				logger.info("CONSUMER URL IS : {}", e.getElementValue());
				extrParams2.clear();
				// extrParams2.sendKeys("keyword=dag");
				extrParams2.sendKeys(e.getElementValue());
			}

			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.AttributeString2");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				attributeString2.clear();
				logger.info(e.getElementValue());
				adviserName = getRefrenceId();
				String atribute = e.getElementValue().replace("refrenceId1", adviserName);
				logger.info(atribute);
				atribute = atribute.replace("advisorSaml1", advisor);
				/*
				 * if(AccountName!=null) { atribute=atribute.replace("sarv10.site16441.2",
				 * AccountName); atribute=atribute.replace("site16441.2", AccountPassword); }
				 */
				logger.info(atribute);
				attributeString2.sendKeys(atribute);
			}
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.SamlVersion");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				d.findElement(By.xpath("//input[@id='SAML_VERSION' and @value='" + e.getElementValue().trim() + "']"))
						.click();
			}
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.AssertionEncryptFlag");
			if (e != null && !GenericUtil.isNull(e.getElementValue())
					&& d.findElements(By.xpath("//input[@id='ASS_ENCRYPT_FLAG']")) != null
					&& d.findElements(By.xpath("//input[@id='ASS_ENCRYPT_FLAG']")).get(0).isDisplayed()) {
				if ("on".equalsIgnoreCase(e.getElementValue().trim())) {
					d.findElement(By.xpath("//input[@id='ASS_ENCRYPT_FLAG' and @value='true']")).click();
				} else
					d.findElement(By.xpath("//input[@id='ASS_ENCRYPT_FLAG' and @value='None']")).click();
			}
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.MultipleKey");
			if (e != null && !GenericUtil.isNull(e.getElementValue())
					&& d.findElements(By.xpath("//input[@id='MULTI_KEY_FLAG']")) != null
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
				} else if (e.getElementValue().trim().equalsIgnoreCase("ON")) {
					WebElement encryptFlag = d.findElement(By.xpath("//input[@id='ENCRYPT_FLAG' and @value='true']"));
					if (encryptFlag != null && encryptFlag.isDisplayed()) {
						d.findElement(By.xpath("//input[@id='ENCRYPT_FLAG' and @value='true']")).click();
					} else if (e.getElementValue().trim().equalsIgnoreCase("EBC")) {
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
				if (e.getElementValue().trim().equalsIgnoreCase("on")
						|| e.getElementValue().trim().equalsIgnoreCase("true")) {
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

				}
			}
			SeleniumUtil.scrollToWebElementMiddle(d, submit);
			logger.info("CLICKING SUBMIT");
			SeleniumUtil.click(submit);
			logger.info("FINISHED CLICKING SUBMIT");
			WebElement nxt = SeleniumUtil.getVisibileWebElement(d, "SAML_LOGIN.Submit2", "SAML_LOGIN", null);
			logger.info("The next button value=" + nxt);
			if (Config.getInstance().getEnvironment().equalsIgnoreCase("bac")) {
				if (!Config.getInstance().getEnvironment().equalsIgnoreCase("bacStage")) {
					d.findElement(By.cssSelector("input[type='submit']")).click();
				}
			} else {
				if (Config.getInstance().getEnvironment().equalsIgnoreCase("mgpStage")) {
					d.findElement(By.cssSelector("input[type='submit']")).click();
				}
				if (!Config.getInstance().getEnvironment().equalsIgnoreCase("mgpStage")) {
					// SeleniumUtil.click(nxt);
					d.findElement(By.cssSelector("input[type='submit']")).click();
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
			// for Wells Fargo
			WebElement agreement = SeleniumUtil.getVisibileWebElement(d, "AcceptAgreement", "SAML_LOGIN", null);
			if (agreement != null) {
				SeleniumUtil.click(agreement);
			}
			// Config.getInstance().setCurrentUser(userName);
			EReporter.log(LogStatus.INFO, "USER = " + userName);
			// String title = PropsUtil.getDataPropertyValue(titleLabel);
			// logger.info("Title is - {}", title);
			// result = d.getTitle().contains(title);
			By hideByObject = SeleniumUtil.getByObject("SAML_LOGIN", null, "hideGettingStarted");
			if (SeleniumUtil.isDisplayed(hideByObject, 3)) {
				try {
					SeleniumUtil.click(hideByObject);
				} catch (Exception ex) {
					logger.warn("Could not click on hide button... Trying again...");
					SeleniumUtil.click(SeleniumUtil.getWebElement(d, "hideGettingStarted", "SAML_LOGIN", null));
				}
			}
		}
		logger.info("Title matched - {}", result);
		logger.info("<== Exiting SAML Login...");
		return InvesterName;
	}

	public String login(WebDriver d, String loginusername, String loginAsInvester, String finapID) {
		if (loginusername.toLowerCase().contains("investor")) {
			return loginAsInvestor(d, loginusername, finapID);
		}
		String userName = "";
		logger.info("==> Entering SAML login");
		Element e;
		boolean result = true;
		d.get(PropsUtil.getEnvPropertyValue("SAML_BASE_URL"));
		try {
			JavascriptExecutor js = (JavascriptExecutor) WebDriverFactory.getDriver();
			js.executeScript("document.querySelector('#proceed-link').click();");
		} catch (Exception e1) {

		}
		try {
			if ("yes".equalsIgnoreCase(PropsUtil.getEnvPropertyValue("cnf_IFrameEnabled"))) {
				d.findElement(By.linkText("show LAW")).click();
				PageParser.switchToFrame(d, "SAML_LOGIN", "MAIN_FRAME");
			}
		} catch (Exception ex) {
		}
		By changeConfig = SeleniumUtil.getByObject("SAML_LOGIN", null, "ChangeConfiguration");
		if (SeleniumUtil.isDisplayed(changeConfig, 2)) {
			SeleniumUtil.click(changeConfig);
		}
		PageFactory.initalize(d, LoginPage_SAML3.class, "SAML_LOGIN");
		e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.issuer");
		if (e != null && !GenericUtil.isNull(e.getElementValue())) {
			// Added for Safari
			// issuer.isDisplayed();
			// END for Safari
			issuer.clear();
			issuer.sendKeys(e.getElementValue().trim());
		}
		e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.SubjectName");
		subjectName.clear();
		// String userName = "";
		if (PageConstants.NEW_USER_FOR_SAML.toUpperCase().trim().equals("YES")) {
			// PageConstants.NEW_USER_FOR_SAML="no";
			// e.setElementValue(null);
			Config.getInstance().setCurrentUser(null);
		}
		if (!GenericUtil.isNull(Config.getInstance().getCurrentUser())) {
			userName = Config.getInstance().getCurrentUser();
		} else if (PropsUtil.getEnvPropertyValue("cnf_ReRun").trim().equalsIgnoreCase("yes")) {
			userName = e.getElementValue();
		} else {
			if (!GenericUtil.isNull(e.getElementValue())
					&& !PageConstants.NEW_USER_FOR_SAML.toUpperCase().trim().equals("YES")) {
				userName = e.getElementValue();
			} else {
				userName = getAdvisorUserName();
			}
		}
		if (PageConstants.NEW_USER_FOR_SAML.toUpperCase().trim().equals("YES")) {
			PageConstants.NEW_USER_FOR_SAML = "no";
		}
		logger.info("User Name is : {}", loginusername);
		subjectName.sendKeys(loginusername);
		Reporter.log("User Name : " + loginusername);
		Config.setUserName(userName);
		e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.ConsumerUrl");
		if (e != null && !GenericUtil.isNull(e.getElementValue())) {
			logger.info("CONSUMER URL IS : {}", e.getElementValue());
			consumerUrl.clear();
			consumerUrl.sendKeys(e.getElementValue());
		}
		/*
		 * e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.TargetUrl"); if (e !=
		 * null && !GenericUtil.isNull(e.getElementValue())) {
		 * logger.info("TARGET URL IS : {}", e.getElementValue()); targetUrl.clear();
		 * targetUrl.sendKeys(e.getElementValue()); } //added by mohit fore stage BBTL
		 * 
		 * e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.ProxyUrl"); if (e !=
		 * null && !GenericUtil.isNull(e.getElementValue())) {
		 * logger.info("Proxy URL IS : {}", e.getElementValue()); proxyUrl.clear();
		 * proxyUrl.sendKeys(e.getElementValue()); }
		 */

		// end stage

		// e=PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.finAppId");
		// if (e != null && !GenericUtil.isNull(e.getElementValue())) {
		// logger.info("CONSUMER URL IS : {}", e.getElementValue());
		finAppId.clear();
		finAppId.sendKeys(finapID);
		// }
		e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.extrParams");
		if (e != null && !GenericUtil.isNull(e.getElementValue())) {
			logger.info("CONSUMER URL IS : {}", e.getElementValue());
			if (loginAsInvester != null) {
				extrParams.clear();
				String extparamvalue = e.getElementValue() + "&IID=" + loginAsInvester;
				extrParams.sendKeys(extparamvalue);
			}
			// extrParams.clear();
		}
		e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.AttributeString1");
		if (e != null && !GenericUtil.isNull(e.getElementValue())) {
			// attributeString.clear();
			// logger.info(e.getElementValue());
			// adviserName=getRefrenceId();
			// String atribute=e.getElementValue().replace("refrenceId1",adviserName );

			// attributeString.sendKeys(atribute);
		}
		e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.SamlVersion");
		if (e != null && !GenericUtil.isNull(e.getElementValue())) {
			d.findElement(By.xpath("//input[@id='SAML_VERSION' and @value='" + e.getElementValue().trim() + "']"))
					.click();
		}
		e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.AssertionEncryptFlag");
		if (e != null && !GenericUtil.isNull(e.getElementValue())
				&& d.findElements(By.xpath("//input[@id='ASS_ENCRYPT_FLAG']")) != null
				&& d.findElements(By.xpath("//input[@id='ASS_ENCRYPT_FLAG']")).get(0).isDisplayed()) {
			if ("on".equalsIgnoreCase(e.getElementValue().trim())) {
				d.findElement(By.xpath("//input[@id='ASS_ENCRYPT_FLAG' and @value='true']")).click();
			} else
				d.findElement(By.xpath("//input[@id='ASS_ENCRYPT_FLAG' and @value='None']")).click();
		}
		e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.MultipleKey");
		if (e != null && !GenericUtil.isNull(e.getElementValue())
				&& d.findElements(By.xpath("//input[@id='MULTI_KEY_FLAG']")) != null
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
			} else if (e.getElementValue().trim().equalsIgnoreCase("ON")) {
				WebElement encryptFlag = d.findElement(By.xpath("//input[@id='ENCRYPT_FLAG' and @value='true']"));
				if (encryptFlag != null && encryptFlag.isDisplayed()) {
					d.findElement(By.xpath("//input[@id='ENCRYPT_FLAG' and @value='true']")).click();
				} else if (e.getElementValue().trim().equalsIgnoreCase("EBC")) {
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
			if (e.getElementValue().trim().equalsIgnoreCase("on")
					|| e.getElementValue().trim().equalsIgnoreCase("true")) {
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
					// d.findElement(By.xpath("//input[@id='LIT_FLAG' and
					// @value='false']")).click();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
		SeleniumUtil.scrollToWebElementMiddle(d, submit);
		logger.info("CLICKING SUBMIT");
		SeleniumUtil.click(submit);
		logger.info("FINISHED CLICKING SUBMIT");
		WebElement nxt = SeleniumUtil.getVisibileWebElement(d, "SAML_LOGIN.Submit2", "SAML_LOGIN", null);
		logger.info("The next button value=" + nxt);
		if (Config.getInstance().getEnvironment().equalsIgnoreCase("bac")) {
			if (!Config.getInstance().getEnvironment().equalsIgnoreCase("bacStage")) {
				d.findElement(By.cssSelector("input[type='submit']")).click();
			}
		} else {
			if (Config.getInstance().getEnvironment().equalsIgnoreCase("mgpStage")) {
				d.findElement(By.cssSelector("input[type='submit']")).click();
			}
			if (!Config.getInstance().getEnvironment().equalsIgnoreCase("mgpStage")) {
				// SeleniumUtil.click(nxt);
				d.findElement(By.cssSelector("input[type='submit']")).click();
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
		// for Wells Fargo
		WebElement agreement = SeleniumUtil.getVisibileWebElement(d, "AcceptAgreement", "SAML_LOGIN", null);
		if (agreement != null) {
			SeleniumUtil.click(agreement);
		}
		// Config.getInstance().setCurrentUser(userName);
		EReporter.log(LogStatus.INFO, "USER = " + userName);
		// String title = PropsUtil.getDataPropertyValue(titleLabel);
		// logger.info("Title is - {}", title);
		// result = d.getTitle().contains(title);
		By hideByObject = SeleniumUtil.getByObject("SAML_LOGIN", null, "hideGettingStarted");
		if (SeleniumUtil.isDisplayed(hideByObject, 3)) {
			try {
				SeleniumUtil.click(hideByObject);
			} catch (Exception ex) {
				logger.warn("Could not click on hide button... Trying again...");
				SeleniumUtil.click(SeleniumUtil.getWebElement(d, "hideGettingStarted", "SAML_LOGIN", null));
			}
		}
		logger.info("Title matched - {}", result);
		logger.info("<== Exiting SAML Login...");
		return userName;
	}

	public String loginAsInvestor(WebDriver d, String loginusername, String finapID) {
		String userName = "";
		logger.info("==> Entering SAML login");
		Element e;
		boolean result = true;
		d.get(PropsUtil.getEnvPropertyValue("SAML_BASE_URL"));
		try {
			JavascriptExecutor js = (JavascriptExecutor) WebDriverFactory.getDriver();
			js.executeScript("document.querySelector('#proceed-link').click();");
		} catch (Exception e1) {

		}
		try {
			if ("yes".equalsIgnoreCase(PropsUtil.getEnvPropertyValue("cnf_IFrameEnabled"))) {
				d.findElement(By.linkText("show LAW")).click();
				PageParser.switchToFrame(d, "SAML_LOGIN", "MAIN_FRAME");
			}
		} catch (Exception ex) {
		}
		By changeConfig = SeleniumUtil.getByObject("SAML_LOGIN", null, "ChangeConfiguration");
		if (SeleniumUtil.isDisplayed(changeConfig, 2)) {
			SeleniumUtil.click(changeConfig);
		}
		PageFactory.initalize(d, LoginPage_SAML3.class, "SAML_LOGIN");
		e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.issuer");
		if (e != null && !GenericUtil.isNull(e.getElementValue())) {
			issuer.clear();
			issuer.sendKeys(e.getElementValue().trim());
		}
		e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.SubjectName");
		subjectName.clear();
		if (PageConstants.NEW_USER_FOR_SAML.toUpperCase().trim().equals("YES")) {
			Config.getInstance().setCurrentUser(null);
		}
		if (!GenericUtil.isNull(Config.getInstance().getCurrentUser())) {
			userName = Config.getInstance().getCurrentUser();
		} else if (PropsUtil.getEnvPropertyValue("cnf_ReRun").trim().equalsIgnoreCase("yes")) {
			userName = e.getElementValue();
		} else {
			if (!GenericUtil.isNull(e.getElementValue())
					&& !PageConstants.NEW_USER_FOR_SAML.toUpperCase().trim().equals("YES")) {
				userName = e.getElementValue();
			} else {
				userName = getAdvisorUserName();
			}
		}
		if (PageConstants.NEW_USER_FOR_SAML.toUpperCase().trim().equals("YES")) {
			PageConstants.NEW_USER_FOR_SAML = "no";
		}
		logger.info("User Name is : {}", loginusername);
		subjectName.clear();
		subjectName.sendKeys(loginusername);
		Reporter.log("User Name : " + loginusername);
		Config.setUserName(userName);
		e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.ConsumerUrl");
		if (e != null && !GenericUtil.isNull(e.getElementValue())) {
			logger.info("CONSUMER URL IS : {}", e.getElementValue());
			consumerUrl.clear();
			consumerUrl.sendKeys(e.getElementValue());
		}
		finAppId.clear();
		finAppId.sendKeys(finapID);
		e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.EXTRParams2");

		if (e != null && !GenericUtil.isNull(e.getElementValue())) {
			logger.info("CONSUMER URL IS : {}", e.getElementValue());
			extrParams.clear();
			extrParams.sendKeys(e.getElementValue());
		}
		e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.AttributeString2");
		if (e != null && !GenericUtil.isNull(e.getElementValue())) {
			attributeString2.clear();
			logger.info(e.getElementValue());
			adviserName = getRefrenceId();
			String atribute = e.getElementValue().replace("refrenceId1", adviserName);
			logger.info(atribute);
			atribute = atribute.replace("advisorSaml1", adviserName);
			/*
			 * if(AccountName!=null) { atribute=atribute.replace("sarv10.site16441.2",
			 * AccountName); atribute=atribute.replace("site16441.2", AccountPassword); }
			 */
			logger.info(atribute);
			attributeString2.sendKeys(atribute);

		}
		e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.SamlVersion");
		if (e != null && !GenericUtil.isNull(e.getElementValue())) {
			d.findElement(By.xpath("//input[@id='SAML_VERSION' and @value='" + e.getElementValue().trim() + "']"))
					.click();
		}
		e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.AssertionEncryptFlag");
		if (e != null && !GenericUtil.isNull(e.getElementValue())
				&& d.findElements(By.xpath("//input[@id='ASS_ENCRYPT_FLAG']")) != null
				&& d.findElements(By.xpath("//input[@id='ASS_ENCRYPT_FLAG']")).get(0).isDisplayed()) {
			if ("on".equalsIgnoreCase(e.getElementValue().trim())) {
				d.findElement(By.xpath("//input[@id='ASS_ENCRYPT_FLAG' and @value='true']")).click();
			} else
				d.findElement(By.xpath("//input[@id='ASS_ENCRYPT_FLAG' and @value='None']")).click();
		}
		e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.MultipleKey");
		if (e != null && !GenericUtil.isNull(e.getElementValue())
				&& d.findElements(By.xpath("//input[@id='MULTI_KEY_FLAG']")) != null
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
			} else if (e.getElementValue().trim().equalsIgnoreCase("ON")) {
				WebElement encryptFlag = d.findElement(By.xpath("//input[@id='ENCRYPT_FLAG' and @value='true']"));
				if (encryptFlag != null && encryptFlag.isDisplayed()) {
					d.findElement(By.xpath("//input[@id='ENCRYPT_FLAG' and @value='true']")).click();
				} else if (e.getElementValue().trim().equalsIgnoreCase("EBC")) {
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
			if (e.getElementValue().trim().equalsIgnoreCase("on")
					|| e.getElementValue().trim().equalsIgnoreCase("true")) {
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
			}
		}
		SeleniumUtil.scrollToWebElementMiddle(d, submit);
		logger.info("CLICKING SUBMIT");
		SeleniumUtil.click(submit);
		logger.info("FINISHED CLICKING SUBMIT");
		WebElement nxt = SeleniumUtil.getVisibileWebElement(d, "SAML_LOGIN.Submit2", "SAML_LOGIN", null);
		logger.info("The next button value=" + nxt);
		if (Config.getInstance().getEnvironment().equalsIgnoreCase("bac")) {
			if (!Config.getInstance().getEnvironment().equalsIgnoreCase("bacStage")) {
				d.findElement(By.cssSelector("input[type='submit']")).click();
			}
		} else {
			if (Config.getInstance().getEnvironment().equalsIgnoreCase("mgpStage")) {
				d.findElement(By.cssSelector("input[type='submit']")).click();
			}
			if (!Config.getInstance().getEnvironment().equalsIgnoreCase("mgpStage")) {
				// SeleniumUtil.click(nxt);
				d.findElement(By.cssSelector("input[type='submit']")).click();
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
		// for Wells Fargo
		By acceptAgreement = SeleniumUtil.getByObject("SAML_LOGIN", null, "AcceptAgreement");
		if (SeleniumUtil.isDisplayed(acceptAgreement, 3)) {
			SeleniumUtil.click(acceptAgreement);
		}
		EReporter.log(LogStatus.INFO, "USER = " + userName);
		By hideByObject = SeleniumUtil.getByObject("SAML_LOGIN", null, "hideGettingStarted");
		if (SeleniumUtil.isDisplayed(hideByObject, 3)) {
			SeleniumUtil.click(hideByObject);
		}
		logger.info("Title matched - {}", result);
		logger.info("<== Exiting SAML Login...");
		return userName;
	}

	public static String getUserName() {
		logger.info("Entering getUserName method.");
		String userConst = Config.getInstance().getEnvironment();
		return userConst + System.currentTimeMillis();
	}

	public String createInvestorWithMultipleAdvisor(WebDriver d, String advisor1, String advisor2, String investor) {
		String userName = "";
		logger.info("==> Entering SAML login");
		Element e;
		boolean result = true;
		if (d == null) {
			logger.error("WebDriver instance in null - {}", d);
		} else {
			d.get(PropsUtil.getEnvPropertyValue("SAML_BASE_URL"));
			try {
				JavascriptExecutor js = (JavascriptExecutor) WebDriverFactory.getDriver();
				js.executeScript("document.querySelector('#proceed-link').click();");
			} catch (Exception e1) {

			}
			try {
				if ("yes".equalsIgnoreCase(PropsUtil.getEnvPropertyValue("cnf_IFrameEnabled"))) {
					d.findElement(By.linkText("show LAW")).click();
					PageParser.switchToFrame(d, "SAML_LOGIN", "MAIN_FRAME");
				}
			} catch (Exception ex) {
			}
			By changeConfig = SeleniumUtil.getByObject("SAML_LOGIN", null, "ChangeConfiguration");
			if (SeleniumUtil.isDisplayed(changeConfig, 2)) {
				SeleniumUtil.click(changeConfig);
			}
			PageFactory.initalize(d, LoginPage_SAML3.class, "SAML_LOGIN");
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.issuer");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				// Added for Safari
				// issuer.isDisplayed();
				// END for Safari
				issuer.clear();
				issuer.sendKeys(e.getElementValue().trim());
			}
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.SubjectName");
			subjectName.clear();
			if (PageConstants.NEW_USER_FOR_SAML.toUpperCase().trim().equals("YES")) {
				// PageConstants.NEsW_USER_FOR_SAML="no";
				// e.setElementValue(null);
				Config.getInstance().setCurrentUser(null);
			}

			if (!GenericUtil.isNull(Config.getInstance().getCurrentUser())) {
				userName = Config.getInstance().getCurrentUser();
			} else if (PropsUtil.getEnvPropertyValue("cnf_ReRun").trim().equalsIgnoreCase("yes")) {
				userName = e.getElementValue();
			} else {
				if (!GenericUtil.isNull(e.getElementValue())
						&& !PageConstants.NEW_USER_FOR_SAML.toUpperCase().trim().equals("YES")) {
					userName = e.getElementValue();
				} else {
					InvesterName = investor;

				}
			}
			if (PageConstants.NEW_USER_FOR_SAML.toUpperCase().trim().equals("YES")) {
				PageConstants.NEW_USER_FOR_SAML = "no";
			}
			logger.info("User Name is : {}", investor);
			subjectName.sendKeys(investor);
			Reporter.log("User Name : " + investor);
			Config.setUserName(investor);
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.ConsumerUrl");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				logger.info("CONSUMER URL IS : {}", e.getElementValue());
				consumerUrl.clear();
				consumerUrl.sendKeys(e.getElementValue());
			}
			/*
			 * e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.TargetUrl"); if (e !=
			 * null && !GenericUtil.isNull(e.getElementValue())) {
			 * logger.info("TARGET URL IS : {}", e.getElementValue()); targetUrl.clear();
			 * targetUrl.sendKeys(e.getElementValue()); } //added by mohit fore stage BBTL
			 * 
			 * e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.ProxyUrl"); if (e !=
			 * null && !GenericUtil.isNull(e.getElementValue())) {
			 * logger.info("Proxy URL IS : {}", e.getElementValue()); proxyUrl.clear();
			 * proxyUrl.sendKeys(e.getElementValue()); }
			 */

			// end stage

			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.finAppId");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				logger.info("CONSUMER URL IS : {}", e.getElementValue());
				finAppId.clear();
				finAppId.sendKeys(e.getElementValue());
			}
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.EXTRParams2");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				logger.info("CONSUMER URL IS : {}", e.getElementValue());
				extrParams2.clear();
				extrParams2.sendKeys("keyword=dag");
			}

			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.AttributeString3");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				attributeString3.clear();
				logger.info(e.getElementValue());
				adviserName = getRefrenceId();
				String atribute = e.getElementValue().replace("refrenceId1", adviserName);
				logger.info(atribute);
				atribute = atribute.replace("advisorSaml1", advisor1);
				atribute = atribute.replace("advisorSaml2", advisor2);
				// atribute=atribute.replace("advisorSaml3", advisor3);
				logger.info(atribute);
				attributeString3.sendKeys(atribute);
			}
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.SamlVersion");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				d.findElement(By.xpath("//input[@id='SAML_VERSION' and @value='" + e.getElementValue().trim() + "']"))
						.click();
			}
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.AssertionEncryptFlag");
			if (e != null && !GenericUtil.isNull(e.getElementValue())
					&& d.findElements(By.xpath("//input[@id='ASS_ENCRYPT_FLAG']")) != null
					&& d.findElements(By.xpath("//input[@id='ASS_ENCRYPT_FLAG']")).get(0).isDisplayed()) {
				if ("on".equalsIgnoreCase(e.getElementValue().trim())) {
					d.findElement(By.xpath("//input[@id='ASS_ENCRYPT_FLAG' and @value='true']")).click();
				} else
					d.findElement(By.xpath("//input[@id='ASS_ENCRYPT_FLAG' and @value='None']")).click();
			}
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.MultipleKey");
			if (e != null && !GenericUtil.isNull(e.getElementValue())
					&& d.findElements(By.xpath("//input[@id='MULTI_KEY_FLAG']")) != null
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
				} else if (e.getElementValue().trim().equalsIgnoreCase("ON")) {
					WebElement encryptFlag = d.findElement(By.xpath("//input[@id='ENCRYPT_FLAG' and @value='true']"));
					if (encryptFlag != null && encryptFlag.isDisplayed()) {
						d.findElement(By.xpath("//input[@id='ENCRYPT_FLAG' and @value='true']")).click();
					} else if (e.getElementValue().trim().equalsIgnoreCase("EBC")) {
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
				if (e.getElementValue().trim().equalsIgnoreCase("on")
						|| e.getElementValue().trim().equalsIgnoreCase("true")) {
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
						// d.findElement(By.xpath("//input[@id='LIT_FLAG' and
						// @value='false']")).click();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			}
			SeleniumUtil.scrollToWebElementMiddle(d, submit);
			logger.info("CLICKING SUBMIT");
			SeleniumUtil.click(submit);
			logger.info("FINISHED CLICKING SUBMIT");
			WebElement nxt = SeleniumUtil.getVisibileWebElement(d, "SAML_LOGIN.Submit2", "SAML_LOGIN", null);
			logger.info("The next button value=" + nxt);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e2) {
				e2.printStackTrace();
			}
			if (Config.getInstance().getEnvironment().equalsIgnoreCase("bac")) {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e2) {
					e2.printStackTrace();
				}
				if (!Config.getInstance().getEnvironment().equalsIgnoreCase("bacStage")) {
					d.findElement(By.cssSelector("input[type='submit']")).click();
				}
			} else {
				if (Config.getInstance().getEnvironment().equalsIgnoreCase("mgpStage")) {
					d.findElement(By.cssSelector("input[type='submit']")).click();
				}
				if (!Config.getInstance().getEnvironment().equalsIgnoreCase("mgpStage")) {
					// SeleniumUtil.click(nxt);
					d.findElement(By.cssSelector("input[type='submit']")).click();
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
			// for Wells Fargo
			WebElement agreement = SeleniumUtil.getVisibileWebElement(d, "AcceptAgreement", "SAML_LOGIN", null);
			if (agreement != null) {
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
		return InvesterName;

	}

	public static String loginResponse(WebDriver d, String loginusername, String loginAsInvester) {
		String smalResponseForInvestorRegistration = null;

		logger.info("==> Entering SAML login");
		Element e;
		d.get(PropsUtil.getEnvPropertyValue("SAML_BASE_URL"));
		try {
			JavascriptExecutor js = (JavascriptExecutor) WebDriverFactory.getDriver();
			js.executeScript("document.querySelector('#proceed-link').click();");
		} catch (Exception e1) {

		}
		try {
			if ("yes".equalsIgnoreCase(PropsUtil.getEnvPropertyValue("cnf_IFrameEnabled"))) {
				d.findElement(By.linkText("show LAW")).click();
				PageParser.switchToFrame(d, "SAML_LOGIN", "MAIN_FRAME");
			}
		} catch (Exception ex) {
		}
		By changeConfig = SeleniumUtil.getByObject("SAML_LOGIN", null, "ChangeConfiguration");
		if (SeleniumUtil.isDisplayed(changeConfig, 2)) {
			SeleniumUtil.click(changeConfig);
		}
		PageFactory.initalize(d, LoginPage_SAML3.class, "SAML_LOGIN");
		e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.issuer");
		if (e != null && !GenericUtil.isNull(e.getElementValue())) {
			issuer.clear();
			issuer.sendKeys(e.getElementValue().trim());
		}
		e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.SubjectName");
		subjectName.clear();

		logger.info("User Name is : {}", loginusername);
		subjectName.sendKeys(loginusername);
		Reporter.log("User Name : " + loginusername);

		e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.ConsumerUrl");
		if (e != null && !GenericUtil.isNull(e.getElementValue())) {
			logger.info("CONSUMER URL IS : {}", e.getElementValue());
			consumerUrl.clear();
			consumerUrl.sendKeys(e.getElementValue());
		}

		e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.finAppId");
		if (e != null && !GenericUtil.isNull(e.getElementValue())) {
			logger.info("CONSUMER URL IS : {}", e.getElementValue());
			finAppId.clear();
			finAppId.sendKeys(e.getElementValue());
		}
		e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.extrParams");
		if (e != null && !GenericUtil.isNull(e.getElementValue())) {
			logger.info("CONSUMER URL IS : {}", e.getElementValue());
			if (loginAsInvester != null) {
				extrParams.clear();
				String extparamvalue = e.getElementValue() + "&IID=" + loginAsInvester;
				extrParams.sendKeys(extparamvalue);
			}

		}

		e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.AttributeString1");
		if (e != null && !GenericUtil.isNull(e.getElementValue())) {
			attributeString.clear();
			logger.info(e.getElementValue());

			attributeString.sendKeys(e.getElementValue());
		}
		e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.SamlVersion");
		if (e != null && !GenericUtil.isNull(e.getElementValue())) {
			d.findElement(By.xpath("//input[@id='SAML_VERSION' and @value='" + e.getElementValue().trim() + "']"))
					.click();
		}
		e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.AssertionEncryptFlag");
		if (e != null && !GenericUtil.isNull(e.getElementValue())
				&& d.findElements(By.xpath("//input[@id='ASS_ENCRYPT_FLAG']")) != null
				&& d.findElements(By.xpath("//input[@id='ASS_ENCRYPT_FLAG']")).get(0).isDisplayed()) {
			if ("on".equalsIgnoreCase(e.getElementValue().trim())) {
				d.findElement(By.xpath("//input[@id='ASS_ENCRYPT_FLAG' and @value='true']")).click();
			} else
				d.findElement(By.xpath("//input[@id='ASS_ENCRYPT_FLAG' and @value='None']")).click();
		}
		e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.MultipleKey");
		if (e != null && !GenericUtil.isNull(e.getElementValue())
				&& d.findElements(By.xpath("//input[@id='MULTI_KEY_FLAG']")) != null
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
			} else if (e.getElementValue().trim().equalsIgnoreCase("ON")) {
				WebElement encryptFlag = d.findElement(By.xpath("//input[@id='ENCRYPT_FLAG' and @value='true']"));
				if (encryptFlag != null && encryptFlag.isDisplayed()) {
					d.findElement(By.xpath("//input[@id='ENCRYPT_FLAG' and @value='true']")).click();
				} else if (e.getElementValue().trim().equalsIgnoreCase("EBC")) {
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
			if (e.getElementValue().trim().equalsIgnoreCase("on")
					|| e.getElementValue().trim().equalsIgnoreCase("true")) {
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
					// d.findElement(By.xpath("//input[@id='LIT_FLAG' and
					// @value='false']")).click();
				} catch (InterruptedException e1) {

					e1.printStackTrace();
				}

			}
		}
		SeleniumUtil.scrollToWebElementMiddle(d, submit);
		logger.info("CLICKING SUBMIT");
		SeleniumUtil.click(submit);
		smalResponseForInvestorRegistration = d.findElement(By.id("cleanResponse")).getText();

		logger.info("=================the saml response for resgsitration of a user===================\n\n");
		logger.info(smalResponseForInvestorRegistration + "\n");

		return smalResponseForInvestorRegistration;

	}

	public void login2(WebDriver d, String loginusername, String loginAsInvester, String finapp) {
		Element e;
		if (d == null) {
			logger.error("WebDriver instance in null - {}", d);
		} else {
			d.get(PropsUtil.getEnvPropertyValue("SAML_BASE_URL"));
			try {
				JavascriptExecutor js = (JavascriptExecutor) WebDriverFactory.getDriver();
				js.executeScript("document.querySelector('#proceed-link').click();");
			} catch (Exception e1) {

			}
			By changeConfig = SeleniumUtil.getByObject("SAML_LOGIN", null, "ChangeConfiguration");
			if (SeleniumUtil.isDisplayed(changeConfig, 2)) {
				SeleniumUtil.click(changeConfig);
			}
			PageFactory.initalize(d, LoginPage_SAML3.class, "SAML_LOGIN");
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.issuer");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				issuer.clear();
				issuer.sendKeys(e.getElementValue().trim());
			}
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.SubjectName");
			if (e != null) {
				subjectName.clear();
				subjectName.sendKeys(loginusername);
			}

			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.ConsumerUrl");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				consumerUrl.clear();
				consumerUrl.sendKeys(e.getElementValue());
			}
			/*
			 * e=PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.finAppId"); if (e !=
			 * null && !GenericUtil.isNull(e.getElementValue())) { finAppId.clear();
			 * finAppId.sendKeys(e.getElementValue()); }
			 */
			finAppId.clear();
			finAppId.sendKeys(finapp);

			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.extrParams");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				logger.info("CONSUMER URL IS : {}", e.getElementValue());
				if (loginAsInvester != null) {
					extrParams.clear();
					String extparamvalue = e.getElementValue() + "&IID=" + loginAsInvester;
					extrParams.sendKeys(extparamvalue);
				} else {
					extrParams2.clear();
					extrParams2.sendKeys("keyword=dag");
				}
			}

			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.SamlVersion");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				SeleniumUtil.click(d.findElement(
						By.xpath("//input[@id='SAML_VERSION' and @value='" + e.getElementValue().trim() + "']")));
			}

			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.AssertionEncryptFlag");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				if ("on".equalsIgnoreCase(e.getElementValue().trim())) {
					SeleniumUtil.click(d.findElement(By.xpath("//input[@id='ASS_ENCRYPT_FLAG' and @value='true']")));
				} else
					SeleniumUtil.click(d.findElement(By.xpath("//input[@id='ASS_ENCRYPT_FLAG' and @value='None']")));
			}

			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.MultipleKey");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				if ("on".equalsIgnoreCase(e.getElementValue().trim())) {
					SeleniumUtil.click(d.findElement(By.xpath("//input[@id='MULTI_KEY_FLAG' and @value='true']")));
				} else
					SeleniumUtil.click(d.findElement(By.xpath("//input[@id='MULTI_KEY_FLAG' and @value='None']")));
			}

			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.EncryptFlag");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				if ("on".equalsIgnoreCase(e.getElementValue().trim())) {
					SeleniumUtil.click(d.findElement(By.xpath("//input[@id='ENCRYPT_FLAG' and @value='true']")));
				} else
					SeleniumUtil.click(d.findElement(By.xpath("//input[@id='ENCRYPT_FLAG' and @value='None']")));
			}

			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.ATTRIB_ENCRYPTION_MECHANISM");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				List<WebElement> list = d.findElements(By.id(e.getValue()));
				for (WebElement we : list) {
					if (we.getAttribute("value").contains(e.getElementValue().trim())) {
						SeleniumUtil.click(we);
						break;
					}
				}
			}
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.EncodeFlag");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				if (e.getElementValue().trim().equalsIgnoreCase("on")
						|| e.getElementValue().trim().equalsIgnoreCase("true")) {
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

		}
		e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.LinkIntegrityToken");
		if (e != null && !GenericUtil.isNull(e.getElementValue())) {
			if ("on".equalsIgnoreCase(e.getElementValue().trim())) {
				SeleniumUtil.click(d.findElement(By.xpath("//input[@id='LIT_FLAG' and @value='true']")));
			} else
				SeleniumUtil.click(d.findElement(By.xpath("//input[@id='ENCRYPT_FLAG' and @value='None']")));
		}
		SeleniumUtil.scrollToWebElementMiddle(d, submit);
		SeleniumUtil.click(submit);
		SeleniumUtil.waitForPageToLoad(3000);
		WebElement nxt = SeleniumUtil.getVisibileWebElement(d, "SAML_LOGIN.Submit2", "SAML_LOGIN", null);
		SeleniumUtil.click(nxt);
		logger.info("<== Exiting SAML Login...");
	}
}
