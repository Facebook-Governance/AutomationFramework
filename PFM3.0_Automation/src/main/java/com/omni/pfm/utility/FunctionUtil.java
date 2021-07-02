/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.utility;

import static com.omni.pfm.utility.SeleniumUtil.click;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Reporter;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.config.Config;
import com.omni.pfm.constants.DataConstants;
import com.omni.pfm.listeners.CustomListener;
import com.omni.pfm.listeners.EReporter;
import com.omni.pfm.testBase.TestBase;
import com.relevantcodes.extentreports.LogStatus;

/**
 * @author Suhaib
 *
 */
/**
 * @author msuhaib
 *
 */
public class FunctionUtil {
	static final Logger logger = LoggerFactory.getLogger(FunctionUtil.class);

	/**
	 * Checks if FinApp is present and if yes, then returns it otherwise returns
	 * null.
	 *
	 * @param d        - WebDriver Instance
	 * @param finappId - String FinApp Id
	 * @return Returns FinApp WebElement if found otherwise returns null
	 */
	public static WebElement isFinappVisible(WebDriver d, String finappId) {
		WebElement finApp = null;
		logger.info("==> Entering FunctionUtil.isFinappVisible method.");
		if (d == null) {
			logger.error("WebDriver instance is null : {}", d);
			logger.error("Returning from method with null");
			return null;
		} else if (finappId == null || "".equals(finappId.trim())) {
			logger.error("FinApp Id is null or empty : {}", finappId);
			logger.error("Returning from method with null");
			return null;
		}
		try {
			finApp = d.findElement(By.xpath("//li[starts-with(@id,'" + finappId + "')]"));
			logger.info("Found finApp with id [{}] = {}", finappId, finApp);
		} catch (NoSuchElementException e) {
			logger.error("Could not find the FinApp with id [{}]", finappId);
			logger.error("Returning from method with null");
			logger.error("ERROR - {}", e);
		}
		logger.info("<== Exiting FunctionUtil.isFinappVisible method.");
		return finApp;
	}

	/**
	 * Verify the text of an element.
	 * 
	 * @param d                    - Web Driver instance
	 * @param pageName             - PageName as defined in xml where element is to
	 *                             be located
	 * @param frameName            - Frame name if element to be located is in the
	 *                             frame. Frame Name should be same as that defined
	 *                             in the xml under the pageName, else it should be
	 *                             null.
	 * @param elementToVerifyLabel - Label of the element of which text is to be
	 *                             verified. Defined in the page xml file.
	 * @param textToVerifyLabel    - Label of the text to be verified. Defined in
	 *                             the data property file.
	 * @return <b>true</b> if element contains the text, else <b>false</b>.
	 */
	public static boolean verifyDefaultData(WebDriver d, String pageName, String frameName, String elementToVerifyLabel,
			String textToVerifyLabel) {
		logger.info("==>Entering FunctionUtil.verifyDefaultData()");
		// PageParser.navigateToPage(pageName, d);
		PageParser.navigateToPage(pageName, d);
		String parentWindowHandle = "";
		if (!GenericUtil.isNull(frameName)) {
			logger.info("Frame is not null : {}", frameName);
			logger.info("Loading Frame - {} ...", frameName);
			parentWindowHandle = d.getWindowHandle();
			PageParser.loadFrame(d, pageName, frameName);
		}
		WebElement elementToVerify = SeleniumUtil.getVisibileWebElement(d, elementToVerifyLabel, pageName, frameName);
		boolean result = false;
		if (elementToVerify != null) {
			// scroll to the element if element is not in the view port
			((JavascriptExecutor) d).executeScript("arguments[0].scrollIntoView(true);", elementToVerify);
			result = verifyText(d, elementToVerify, textToVerifyLabel);
		} else {
			logger.error("Could not find the element to verify text - label = [{}]", elementToVerifyLabel);
		}
		if (!"".equals(parentWindowHandle)) {
			logger.info("Switching back to parent handle - {} ...", frameName);
			d.switchTo().window(parentWindowHandle);
		}
		logger.info("<== Exiting FunctionUtil.verifyDefaultData()");
		return result;
	}

	/**
	 * 
	 * @param d                        - WebDriver instance
	 * @param pageName                 - Page Name where finapp is located
	 * @param finAppFrameLabel         - Finapp Frame label as defined in xml within
	 *                                 the corresponding pageName tag.
	 * @param finappID                 - finapp id
	 * @param finappTrayLabel
	 * @param defaultObjectLabel
	 * @param defaultTextToVerifyLabel
	 * @param finappDropArea           - Drop Area label where to drag and drop
	 *                                 finapp
	 * @return
	 */
	/*
	 * public static boolean verifyDefaultDataFinapps(WebDriver d, String pageName,
	 * String finAppFrameLabel, String finappID, String finappTrayLabel, String
	 * defaultObjectLabel, String defaultTextToVerifyLabel, String finappDropArea) {
	 * logger.info("==>Entering verifyDefaultDataFinapps method.");
	 * FinappTray.addFinappToPage(d, pageName, finappID, finappTrayLabel,
	 * finappDropArea); String parentWidow = d.getWindowHandle(); boolean result =
	 * false; if (PageParser.loadFrame(d, pageName, finAppFrameLabel)) { WebElement
	 * elementToVerify = SeleniumUtil.getVisibileWebElement(d, defaultObjectLabel,
	 * pageName, finAppFrameLabel); if (elementToVerify != null) {
	 * ((JavascriptExecutor) d).executeScript("arguments[0].scrollIntoView(true);",
	 * elementToVerify); result = verifyText(d, elementToVerify,
	 * defaultTextToVerifyLabel); } else {
	 * logger.error("Could not find the element to verify text - label = [{}]",
	 * defaultObjectLabel); } logger.info("Switching back to main window..");
	 * d.switchTo().window(parentWidow); }
	 * logger.info("<==Exiting verifyDefaultDataFinapps method."); return result; }
	 */
	/**
	 * Verify text of a web element
	 *
	 * @param d
	 * @param objectToVerify - Label of the element whose text we have to verify
	 * @param textToVerify   - Label of the text we have to verify
	 */
	public static boolean verifyText(WebDriver d, String objectToVerify, String textToVerify) {
		logger.info("==> Entering verifyText method.");
		boolean status = false;
		if (d == null) {
			logger.error("WebDriver instance is null.");
		} else if (objectToVerify == null || "".equals(objectToVerify.trim())) {
			logger.error("Object to verify label is null or empty : {}", objectToVerify);
		} else if (textToVerify == null || textToVerify.trim().equals("")) {
			logger.error("TextToVerify Label is null or empty : {}", textToVerify);
		} else {
			String expectedText = null;
			if (Config.getInstance().dataMap.containsKey(DataConstants.DATA_LABELS_MAP)) {
				if (Config.getInstance().dataMap.get(DataConstants.DATA_LABELS_MAP).containsKey(textToVerify)) {
					expectedText = Config.getInstance().dataMap.get(DataConstants.DATA_LABELS_MAP).get(textToVerify);
					logger.info("Expected Text is : {}", expectedText);
					WebElement object = PropsUtil.getWebElement(d, objectToVerify);
					if (object != null) {
						String actualText = object.getText();
						logger.info("Actual text from object is : {}", actualText);
						System.out.println("------------->" + object.getAttribute("class"));
						status = actualText.contains(expectedText);
					}
				} else {
					logger.error("Data Labels map does not contain [{}] label.", textToVerify);
				}
			} else {
				logger.error("Data Map does not contain Data Labels Map : {}");
			}
		}
		logger.info("<== Exiting VerifyText method.");
		return status;
	}

	/**
	 * Verify the text of an element.
	 * 
	 * @param d                 - WebDriver instance
	 * @param objectToVerify    - WebElement object of which the text is to be
	 *                          verified.
	 * @param textToVerifyLabel - Label of the text to be verified. Refers to data
	 *                          property file.
	 * @return <b>true</b> if the text is matched, else <b>false</b>.
	 */
	public static boolean verifyText(WebDriver d, WebElement objectToVerify, String textToVerifyLabel) {
		logger.info("==> Entering verifyText method.");
		boolean status = false;
		if (d == null) {
			logger.error("WebDriver instance is null.");
		} else if (objectToVerify == null) {
			logger.error("WebElement object to verify is null : {}", objectToVerify);
		} else if (textToVerifyLabel == null || textToVerifyLabel.trim().equals("")) {
			logger.error("TextToVerify Label is null or empty : {}", textToVerifyLabel);
		} else {
			String expectedText = null;
			if (Config.getInstance().dataMap.containsKey(DataConstants.DATA_LABELS_MAP)) {
				if (Config.getInstance().dataMap.get(DataConstants.DATA_LABELS_MAP).containsKey(textToVerifyLabel)) {
					expectedText = Config.getInstance().dataMap.get(DataConstants.DATA_LABELS_MAP)
							.get(textToVerifyLabel);
					logger.info("Expected Text is : {}", expectedText);
					// WebElement object = LabelUtility.getWebElement(d,
					// objectToVerify);
					String actualText = (String) ((JavascriptExecutor) d)
							.executeScript("return arguments[0].textContent", objectToVerify);
					logger.info("Actual text is : {}", actualText);
					System.out.println("------------->" + objectToVerify.getAttribute("class"));
					// Assert.assertTrue(actualText.contains(expectedText));
					status = actualText.trim().contains(expectedText.trim());
					if (status == false) {
						logger.warn("EXPECTED AND ACTUAL TEXT DO NOT MATCH...");
						Reporter.log("Expected Text is : " + expectedText + "\nActual text is : " + actualText);
					}
				} else {
					logger.error("Data Labels map does not contain [{}] label.", textToVerifyLabel);
				}
			} else {
				logger.error("Data Map does not contain Data Labels Map : {}");
			}
		}
		logger.info("<== Exiting VerifyText method.");
		return status;
	}

	/**
	 * Navigate to a component under the menu items
	 *
	 * @param d                  - WebDriver instance
	 * @param menuItemLabel      - Menu item label under which component is listed
	 * @param componentItemLabel - Label of the component we want to navigate to. If
	 *                           its value is null or empty, click would be
	 *                           performed on menuItemLabel element
	 */
	@Deprecated
	public static void navigateToListComponent(WebDriver d, String menuItemLabel, String componentItemLabel) {
		logger.info("==> Entering navigateToListComponent method.");
		if (d == null) {
			logger.error("WebDriver instance is null. Returning from method");
			return;
		}
		if (menuItemLabel == null || "".equals(menuItemLabel.trim())) {
			logger.error("MenuItemLabel is null. Returning from the method.");
			return;
		}
		/*
		 * if ("".equals(componentItemLabel.trim())) {
		 * logger.error("ComponentItemLabel is empty. Returning from the method." );
		 * return; }
		 */
		WebElement menu = PropsUtil.getWebElement(d, menuItemLabel);
		WebElement component;
		Actions action = new Actions(d);
		if (menu != null) {
			action.moveToElement(menu).click().build().perform();
			logger.info("Fetching component element..");
			if (componentItemLabel == null || componentItemLabel.trim().equals("")) {
				logger.warn("Component Item Label is empty or null - {}", componentItemLabel);
				logger.info("Clikcing on Menu Item");
				menu.click();
			} else {
				component = PropsUtil.getWebElement(d, componentItemLabel);
				if (component != null) {
					logger.info("Successfully fetched component element.");
					logger.info("Clicking on the component element - {}", componentItemLabel);
					component.click();
				} else {
					logger.error("Not able to find component with LABEL = [{}]", componentItemLabel);
				}
			}
		} else {
			logger.error("Not able to find Menu Item with LABEL = [{}]", menuItemLabel);
		}
		logger.info("<== Exiting navigateToListComponent method.");
	}

	public static boolean verifyTitle(WebDriver d, String titleLabel) {
		logger.info("==> Entering verify title method...");
		boolean status = false;
		if (d == null) {
			logger.info("Web Driver instance is null...");
		} else if (GenericUtil.isNull(titleLabel)) {
			logger.error("Title label is null or empty...");
		} else {
			String title = PropsUtil.getDataPropertyValue(titleLabel);
			if (GenericUtil.isNull(title)) {
				logger.error("Could not fetch the title value for the label - {}", title);
			} else {
				logger.info("Value of title from wedriver is : {}", d.getTitle());
				logger.info("Value of title from label file is : {}", title);
				status = d.getTitle().contains(title);
			}
		}
		logger.info("<== Exiting verifyTitle method...");
		return status;
	}

	/**
	 * - Returns true if the element is found in the page
	 * 
	 * @param d  - Web Driver instance
	 * @param by - By Object of the element to be verified
	 * @return true is element is visible otherwise false
	 */
	public static boolean verifyElementPresent(WebDriver d, By by) {
		logger.info("==> Entering verifyElementPresent method...");
		boolean status = false;
		return status;
	}

	public static void testMethod() {
		System.out.println("Test MEHTOD CALLED...");
	}

	/**
	 * Verifies the links present in the page
	 * 
	 * @param d                  - WebDriver instance
	 * @param linksToVerifyArray - Label of the links array that is defined in the
	 *                           Data Properties file. All the link values should be
	 *                           separated by a semi-colon(;)
	 * @return Returns <b>true</b> iff all the links are present and visible
	 *         otherwise <b>false</b>.
	 */
	public static boolean verifyPageLinks(WebDriver d, String linksToVerifyArray) {
		logger.info("==> Entering FunctionUtil.verifyPageLinks method...");
		boolean status = true;
		if (d == null) {
			logger.error("WebDriver instance is null");
		} else if (GenericUtil.isNull(linksToVerifyArray)) {
			logger.error("linksToVerifyArray Label is null or empty...");
		} else {
			logger.info("linksToVerifyArray Label : {}", linksToVerifyArray);
			String labelValue = PropsUtil.getDataPropertyValue(linksToVerifyArray).trim();
			if (GenericUtil.isNull(labelValue)) {
				logger.error("Could not find any value for the label : {}", linksToVerifyArray);
				status = false;
			} else {
				logger.info("Links to be verified are : {}", labelValue);
				String[] linkValues = labelValue.split(";");
				for (String link : linkValues) {
					logger.info("Verifying link - {}", link);
					try {
						WebElement we = d.findElement(By.linkText(link));
						if (we.isDisplayed()) {
							logger.info("Link [{}] is present and displayed...", link);
						} else {
							logger.error("Link [{}] is present but not visible...", link);
							status = false;
						}
					} catch (NoSuchElementException ex) {
						logger.error("Link [{}] not present", link);
						status = false;
					}
				}
			}
		}
		logger.info("<== Exiting FunctionUtil.verifyPageLinks method...");
		System.out.println(status);
		return status;
	}

	/**
	 * - Splits the value of the label defined in the data property file based on
	 * the given <b>regex</b>.
	 * 
	 * @param label - label that is defined in the data property file.
	 * @param regex - regex string based on which value needs to be splitted.
	 * @return - returns the String array of all the split values. Returns null if
	 *         any of the parameters in null or empty, or value of label is empty or
	 *         label is not present in the property file.
	 */
	public static String[] splitLabelValue(String label, String regex) {
		logger.info("==> Entering splitLabelValue method.");
		String split[] = null;
		if (GenericUtil.isNull(label)) {
			logger.error("Label is null or empty.");
		} else if (GenericUtil.isNull(regex)) {
			logger.error("Regex is null.");
		} else {
			logger.info("Label = {}", label);
			logger.info("Regex = {}", regex);
			String labelValue = PropsUtil.getDataPropertyValue(label);
			if (GenericUtil.isNull(labelValue)) {
				logger.error("Value of label from property file is null or empty.");
			} else {
				logger.info("Value of label from property file is : {}", labelValue);
				split = labelValue.split(regex);
			}
		}
		logger.info("<== Exiting splitLabelValue");
		return split;
	}

	public static List<String> splitLabelValueAsList(String label, String regex) {
		logger.info("==> Entering splitLabelValue method.");
		String split[] = null;
		List<String> list = null;
		if (GenericUtil.isNull(label)) {
			logger.error("Label is null or empty.");
		} else if (GenericUtil.isNull(regex)) {
			logger.error("Regex is null.");
		} else {
			logger.info("Label = {}", label);
			logger.info("Regex = {}", regex);
			String labelValue = PropsUtil.getDataPropertyValue(label);
			if (GenericUtil.isNull(labelValue)) {
				logger.error("Value of label from property file is null or empty.");
			} else {
				logger.info("Value of label from property file is : {}", labelValue);
				split = labelValue.split(regex);
				list = new ArrayList<>();
				for (String s : split) {
					list.add(s);
				}
			}
		}
		logger.info("<== Exiting splitLabelValue");
		return list;
	}

	/**
	 * Get the value of property as an array. Each value should be within double
	 * quotes ("") and each value should be separated by a comma(,)
	 * 
	 * @param label        - name of the label
	 * @param propertyType - 0 = Environment property, 1 = Data property
	 * @return String array of values if label is found else null
	 */
	public static String[] getPropertyValueAsArray(String label, int propertyType) {
		logger.info("==> Entering getPropertyValueArray method.");
		String[] value = null;
		String s = "";
		if (GenericUtil.isNull(label)) {
			logger.error("Label is null or empty..");
		} else {
			if (propertyType == 0) {
				s = PropsUtil.getEnvPropertyValue(label);
			} else
				s = PropsUtil.getDataPropertyValue(label);
			if ("".equals(s)) {
				logger.error("Label value is empty - {}", label);
			} else {
				int i = 0;
				String[] temp = s.split(",");
				for (String v : temp) {
					temp[i++] = v.substring(1, v.length() - 1);
				}
				value = temp;
			}
		}
		logger.info("<== Exiting getPropertyValueArray method.");
		return value;
	}

	public static String returnEllipsifiedText(String textToElipisy, String limitLabel) {
		logger.info("==> Entering returnEllipsifiedText method.");
		String elipsifiedText;
		int limit = 0;
		logger.debug("Text to Elipsify = {} ---- limitLabel = {}", textToElipisy, limitLabel);
		String s = PropsUtil.getDataPropertyValue(limitLabel);
		logger.debug("Limit value from property file = {}", s);
		if (!GenericUtil.isNull(s)) {
			try {
				limit = Integer.parseInt(s);
			} catch (NumberFormatException ex) {
				logger.error("Limit value should be an integer - {}", s);
			}
		}
		if (textToElipisy.length() > limit && limit != 0) {
			elipsifiedText = textToElipisy.substring(0, limit) + "...";
		} else
			elipsifiedText = textToElipisy;
		logger.info("==> Entering returnEllipsifiedText method.");
		return elipsifiedText;
	}

	/**
	 * Trim the Long acccount names upto number of characters defined by limit and
	 * '...' appended at the end. If limit is 0, value will taken from the
	 * Environment property file.
	 * 
	 * @param textToElipisy - Text to ellipsify.
	 * @param limit         - Number of characters after which text should be
	 *                      ellipsified.
	 * @return - Returns the ellipsified text.
	 */
	public static String returnEllipsifiedText(String textToElipisy, int limit) {
		logger.info("==> Entering returnEllipsifiedText method.");
		String elipsifiedText;
		if (limit == 0) {
			String s = PropsUtil.getEnvPropertyValue("cnf_Ellipsification_LimitForAccountNames");
			logger.debug("Limit value from env property file = {}", s);
			if (!GenericUtil.isNull(s)) {
				try {
					limit = Integer.parseInt(s);
				} catch (NumberFormatException ex) {
					logger.error("Limit value should be an integer - {}", s);
				}
			}
		}
		if (textToElipisy.length() > limit && limit != 0) {
			elipsifiedText = textToElipisy.substring(0, limit) + "...";
		} else
			elipsifiedText = textToElipisy;
		logger.info("==> Entering returnEllipsifiedText method.");
		return elipsifiedText;
	}

	public static boolean verifyObjectPresent(SearchContext d, String locator, String lValue) {
		logger.info("==> Entering verifyObjectPresent Method.");
		boolean status = false;
		List<WebElement> list = null;
		if (GenericUtil.checkParamsNotNull(d, locator, lValue)) {
			switch (locator.toLowerCase()) {
			case "xpath":
				list = d.findElements(By.xpath(lValue));
				break;
			case "id":
				list = d.findElements(By.id(lValue));
				break;
			case "css":
				list = d.findElements(By.cssSelector(lValue));
				break;
			case "link":
				list = d.findElements(By.linkText(lValue));
				break;
			case "partiallink":
				list = d.findElements(By.partialLinkText(lValue));
				break;
			default:
				logger.error("Locator [{}] not identified.", locator);
			}
			if (list != null && list.size() > 0) {
				for (WebElement e : list) {
					if (e.isDisplayed()) {
						logger.info("Found element with [{}] = [{}]", locator, lValue);
						status = true;
					}
				}
			}
		} else {
			logger.error("One or more param is null or empty...");
		}
		logger.info("Status of the method is : [{}]", status);
		logger.info("<== Exiting verifyObjectPresent Method.");
		return status;
	}

	/**
	 * Calculate the size of the finapp
	 * 
	 * @author Mohit
	 * @param d        - WebDriver instance
	 * @param finappId - Finapp Id label of the finapp as defined in property file
	 * @return the MEDIUM and SMALL according to the size of the finapp. returns
	 *         null if the parameters are null.
	 */
	public static String finappStatus(WebDriver d, String finappId) {
		logger.info("Entring to finappStatus function");
		String status = null;
		if (d == null)
			logger.error("The web driver instance is null");
		else {
			String finappIdToCheck = PropsUtil.getDataPropertyValue(finappId);
			if (finappIdToCheck == null) {
				Reporter.log("The label " + finappId + " is not defined in the property file");
				logger.error("The label " + finappId
						+ " is not defined in the property file.\n The value for finappIdToCheck is null");
			} else {
				String finapp = "//iframe[contains(@id,'" + finappIdToCheck + "')]";
				WebElement fa = null;
				long start = System.currentTimeMillis();
				long end = start + 5000;
				while (start != end) {
					try {
						fa = d.findElement(By.xpath(finapp));
						break;
					} catch (Exception ex) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					start += 1000;
				}
				if (fa == null) {
					throw new NoSuchElementException(
							"Unable to locate element:{\"method\":\"xpath\",\"selector\":\"" + finapp + "\"}");
				}
				int finappWidth = fa.getSize().getWidth();
				if (finappWidth > 600)
					status = "MEDIUM";
				else
					status = "SMALL";
				SeleniumUtil.switchToDefaultContent(d);
				logger.info("The status of Finapp " + finappId + " is " + status);
			}
		}
		logger.info("Exiting from finappStatus function");
		return status;
	}

	public static String finappStatusCheck(WebDriver d, String elementName, String pageName, String frameName) {
		logger.info("Entring to finappStatus function");
		String status = null;
		if (d == null)
			logger.error("The web driver instance is null");
		else {
			SeleniumUtil.switchToDefaultContent(d);
			// PageParser.loadFrame(d, null, frameName);
			WebElement el = SeleniumUtil.getVisibileWebElement(d, elementName, pageName, null);
			int finappWidth = el.getSize().getWidth();
			if (finappWidth > 600) {
				status = "MEDIUM";
				logger.info("Finapp status is medium.");
			} else {
				status = "SMALL";
				logger.info("<== Finapp Status is small.");
			}
			// SeleniumUtil.switchToDefaultContent(d);
		}
		logger.info("Exiting from finappStatus function");
		return status;
	}

	// 24 MAY
	/**
	 * Verify the functional flow of remove finapp
	 * 
	 * @author Mohit
	 * @param d                 - WebDriver instance
	 * @param pageName          - Page Name where finapp is located
	 * @param finAppFrameLabels - Finapp Frame label as defined in xml within the
	 *                          corresponding pageName tag.
	 * @param assetsDropdown    -pined drop down for remove object as defined in xml
	 *                          within the corresponding frame tag.
	 * @param removeLink        -remove link inside the remove Dropdown as defined
	 *                          in xml within the corresponding frame tag.
	 * @param submitButton      -Submit button on conformation floater as defined in
	 *                          xml within the corresponding frame tag.
	 * @return true if the finapp is removed
	 */
	public static boolean removeFinapp(WebDriver d, String pageName, String finAppFrameLabels, String assetsDropdown,
			String removeLink, String submitButton) {
		PageParser.navigateToPage(pageName, d);
		if (SeleniumUtil.waitForElementVisible(d, assetsDropdown, pageName, finAppFrameLabels) != null) {
			click(SeleniumUtil.waitForElementVisible(d, assetsDropdown, pageName, finAppFrameLabels));
			logger.info("Drop down is clicked");
			if (SeleniumUtil.waitForElementVisible(d, removeLink, pageName, finAppFrameLabels) != null) {
				click(SeleniumUtil.waitForElementVisible(d, removeLink, pageName, finAppFrameLabels));
				logger.info("Help link is clicked");
			}
		} else
			logger.error("Remove link did not found");
		SeleniumUtil.switchToDefaultContent(d);
		if (SeleniumUtil.waitForElementVisible(d, submitButton, pageName, finAppFrameLabels) != null) {
			logger.info("Message floater is opening");
			SeleniumUtil.waitForElementVisible(d, submitButton, pageName, finAppFrameLabels).click();
			logger.info("Submit Button is clicked");
			SeleniumUtil.switchToDefaultContent(d);
			return true;
		}
		logger.info("Exiting from verifyRemoveFinapp");
		Reporter.log("The finapp is not removed");
		return false;
	}

	/**
	 * Used to drag and drop the finapp
	 * 
	 * @author Mohit
	 * @param d                 - WebDriver instance
	 * @param pageName          - Page Name where finapp is located
	 * @param finAppFrameLabels - Finapp Frame label as defined in xml within the
	 *                          corresponding pageName tag.
	 * @param finapp            id
	 * @param finappTrayLabel   -Tray label object as defined in xml within the
	 *                          corresponding frame tag.
	 * @param finappDropArea    -pass as null.
	 * 
	 * @return true if the finapp is present on the page or in case of successful
	 *         darg insde the page
	 */
	/*
	 * public static boolean dragAndDropFinapp(WebDriver d, String pageName, String
	 * finAppFrameLabels, String finappID, String finappTrayLabel, String
	 * finappDropArea) {
	 * logger.info("==>Entering assetsAllocationDragAndDrop method.");
	 * logger.info("==>Dragging the finapp to the page");
	 * FinappTray.addFinappToPage(d, pageName, finappID, finappTrayLabel,
	 * finappDropArea); if (PageParser.loadFrame(d, pageName, finAppFrameLabels)) {
	 * logger.info("==>Success full darg and drop finapp " + finappID);
	 * SeleniumUtil.switchToDefaultContent(d); return true; } else {
	 * Reporter.log("Finapp " + finappID +
	 * "  did not found! Exiting from assetsAllocationDragAndDrop");
	 * SeleniumUtil.switchToDefaultContent(d); return false; } }
	 */

	/**
	 * Verify the remove finapp link
	 * 
	 * @author Mohit
	 * @param d                 - WebDriver instance
	 * @param pageName          - Page Name where finapp is located
	 * @param finAppFrameLabels - Finapp Frame label as defined in xml within the
	 *                          corresponding pageName tag.
	 * @param assetsDropdown    -pined drop down for remove object as defined in xml
	 *                          within the corresponding frame tag.
	 * @param removeLink        -remove link inside the remove Dropdown as defined
	 *                          in xml within the corresponding frame tag.
	 * @param submitButton      -Submit button on conformation floater as defined in
	 *                          xml within the corresponding frame tag.
	 * @return return true if the remove finapp functional flow is working fine
	 *         otherwise false
	 */
	public static boolean verifyRemoveFinapp(WebDriver d, String pageName, String finAppFrameLabels,
			String assetsDropdown, String removeLink, String submitButton) {
		PageParser.navigateToPage(pageName, d);
		if (SeleniumUtil.waitForElementVisible(d, assetsDropdown, pageName, finAppFrameLabels) != null) {
			SeleniumUtil.waitForElementVisible(d, assetsDropdown, pageName, finAppFrameLabels).click();
			logger.info("Drop down is clicked");
			if (SeleniumUtil.waitForElementVisible(d, removeLink, pageName, finAppFrameLabels) != null) {
				SeleniumUtil.waitForElementVisible(d, removeLink, pageName, finAppFrameLabels).click();
				logger.info("Help link is clicked");
			}
		} else
			logger.error("Remove link did not found");
		SeleniumUtil.switchToDefaultContent(d);
		if (SeleniumUtil.waitForElementVisible(d, submitButton, pageName, finAppFrameLabels) != null) {
			logger.info("Message floater is opening");
			SeleniumUtil.waitForElementVisible(d, submitButton, pageName, finAppFrameLabels).click();
			logger.info("Submit Button is clicked");
			SeleniumUtil.switchToDefaultContent(d);
			return true;
		}
		logger.info("Exiting from verifyRemoveFinapp");
		Reporter.log("The finapp is not removed");
		return false;
	}

	/**
	 * Verify the add account link for Assets Allocation Finapp
	 * 
	 * @author Mohit
	 * @param d                 - WebDriver instance
	 * @param pageName          - Page Name where finapp is located
	 * @param finAppFrameLabels - Finapp Frame label as defined in xml within the
	 *                          corresponding pageName tag.
	 * @param addAccountObj     -Add account link Object as defined in xml within
	 *                          the corresponding pageName tag.
	 * @param floaterCloseLink  -Add Account floater close link as defined in xml
	 *                          within the corresponding pageName tag.
	 * @return return true if the Add Account functional flow is working fine
	 *         otherwise false
	 */
	public static boolean verifyAddAccountLink(WebDriver d, String pageName, String finAppFrameLabels,
			String addAccountObj, String floaterCloseLink) {
		boolean status = false;
		if (PageParser.loadFrame(d, pageName, finAppFrameLabels)) {
			if (SeleniumUtil.waitForElementVisible(d, addAccountObj, pageName, finAppFrameLabels) != null) {
				SeleniumUtil.waitForElementVisible(d, addAccountObj, pageName, finAppFrameLabels).click();
				logger.info("Clicked on Add Account");
				SeleniumUtil.switchToDefaultContent(d);
				if (SeleniumUtil.waitForElementVisible(d, floaterCloseLink, pageName, finAppFrameLabels) != null) {
					logger.info("Clicking on close link on add account floater");
					WebElement closeLink = SeleniumUtil.waitForElementVisible(d, floaterCloseLink, pageName,
							finAppFrameLabels);// .click();
					if (closeLink != null) {
						closeLink.click();
						status = true;
						logger.info("Clicked on close link on add account floater");
					} else {
						logger.error("the close floater link is not visible");
						Reporter.log("the close floater link is not visible" + floaterCloseLink);
					}
					logger.info("Exiting from verifyAddAccountLink");
				} else {
					logger.error("Did not find close link on add account floater");
					logger.info("Exiting from verifyAddAccountLink");
					status = false;
				}
			}
		}
		SeleniumUtil.switchToDefaultContent(d);
		PageParser.forceNavigate(pageName, d);
		// logger.info("Did nfind Add account link");
		logger.info("Exiting from verifyAddAccountLink");
		return status;
	}

	// 23 MAY
	/**
	 * Verify the Alert Link Functionality
	 * 
	 * @author Mohit
	 * @param d                 - WebDriver instance
	 * @param pageName          - Page Name where finapp is located
	 * @param finAppFrameLabels - Finapp Frame label as defined in xml within the
	 *                          corresponding pageName tag.
	 * @param alertLinkObj      -Alert link Object as defined in xml within the
	 *                          corresponding pageName tag.
	 * @return return true if the Alert link functional flow is working fine
	 *         otherwise false
	 */
	public static boolean verifyAlertLink(WebDriver d, String pageName, String finAppFrameLabels, String alertLinkObj,
			String AlertPage, String alertHeader) {
		// PageParser.navigateToPage(pageName, d);
		if (PageParser.loadFrame(d, pageName, finAppFrameLabels)) {
			logger.info("in frame");
			if (SeleniumUtil.waitForElementVisible(d, alertLinkObj, pageName, finAppFrameLabels) != null) {
				SeleniumUtil.waitForElementVisible(d, alertLinkObj, pageName, finAppFrameLabels).click();
				logger.info("Clicked on alert link");
				SeleniumUtil.switchToDefaultContent(d);
				if (SeleniumUtil.waitForElementVisible(d, alertHeader, AlertPage, null) != null) {
					logger.info("The AlertLink is verified");
					logger.info("Exiting from verifyAlertLink");
					PageParser.forceNavigate(pageName, d);
					return true;
				}
			}
		}
		SeleniumUtil.switchToDefaultContent(d);
		Reporter.log("Alert Link is not visible");
		PageParser.navigateToPage(pageName, d);
		logger.info("Exiting from verifyAlertLink");
		return false;
	}

	/**
	 * Verify the help functionality is working fine
	 * 
	 * @author Mohit
	 * @param d                  - WebDriver instance
	 * @param pageName           - Page Name where finapp is located
	 * @param finAppFrameLabels  - Finapp Frame label as defined in xml within the
	 *                           corresponding pageName tag.
	 * @param assetsHelpDropdown -Dropdown label as defined in xml within the
	 *                           corresponding pageName tag.
	 * @param helpLink           - help link label as defined in xml within the
	 *                           corresponding pageName tag.
	 * @param verifyTextObjType  -verify text object type of help floater defined in
	 *                           property file.
	 * @param verifyTextForHelp  -verify text of help floater defined in property
	 *                           file.
	 * @param helpdropDown       -help dropdown label as defined in xml within the
	 *                           corresponding pageName tag.
	 * @param removelink         -remove link as defined in xml within the
	 *                           corresponding pageName tag.
	 * @return return true if the help functional flow is working fine otherwise
	 *         false
	 */
	public static boolean verifyHelpFin(WebDriver d, String pageName, String finAppFrameLabels,
			String assetsHelpDropdown, String helpLink, String helpdropDown, String removelink) {
		PageParser.navigateToPage(pageName, d);
		if (SeleniumUtil.waitForElementVisible(d, assetsHelpDropdown, pageName, finAppFrameLabels) != null) {
			SeleniumUtil.clickFinappDDItem(d, pageName, finAppFrameLabels, assetsHelpDropdown, helpLink);
			/*
			 * SeleniumUtil.waitForElementVisible(d, assetsHelpDropdown, pageName,
			 * finAppFrameLabels).click(); logger.info("Drop down is clicked"); if
			 * (SeleniumUtil.waitForElementVisible(d, helpLink, pageName, finAppFrameLabels)
			 * != null) { SeleniumUtil.waitForElementVisible(d, helpLink, pageName,
			 * finAppFrameLabels).click();
			 */
			logger.info("Help link is clicked");
			// }
		} else
			logger.error("Help link did not found");
		if (SeleniumUtil.waitForElementVisible(d, helpdropDown, pageName, finAppFrameLabels) != null) {
			logger.info("The help floater is open successfully");
			SeleniumUtil.clickFinappDDItem(d, pageName, finAppFrameLabels, helpdropDown, removelink);
			/*
			 * SeleniumUtil.waitForElementVisible(d, helpdropDown, pageName,
			 * finAppFrameLabels).click(); SeleniumUtil.waitForElementVisible(d, removelink,
			 * pageName, finAppFrameLabels).click();
			 */
			SeleniumUtil.switchToDefaultContent(d);
			PageParser.forceNavigate(pageName, d);
			return true;
		}
		PageParser.forceNavigate(pageName, d);
		logger.info("Exiting from verifyHelpFin");
		Reporter.log("The help floater did not open");
		return false;
	}

	/**
	 * Returns a double value from a String
	 * 
	 * @param str
	 * @return
	 */
	public static double returnDoubleFromString(String str) {
		StringBuilder sb = null;
		if (!GenericUtil.isNull(str)) {
			sb = new StringBuilder(str);
			logger.info("Original String = {}", str);
			int i = 0;
			while (i < sb.length()) {
				int charCode = sb.charAt(i);
				if (((charCode < 48 || charCode > 57) && sb.charAt(i) != '.') || sb.toString().startsWith(".")
						|| sb.toString().endsWith(".")) {
					int idx = sb.indexOf(String.valueOf(sb.charAt(i)));
					sb.deleteCharAt(idx);
					i = 0;
				} else
					i++;
			}
		}
		if (sb != null && !sb.toString().trim().equals("")) {
			logger.info("Number from string = " + sb.toString());
			return Double.valueOf(sb.toString().trim());
		}
		logger.info("No Decimal Number found in string");
		return -0.00;
	}

	public static void removeAllFinapp(WebDriver d, String pageName) {
		if (pageName != null) {
			try {
				PageParser.forceNavigate(pageName, d);
				if (SeleniumUtil.getVisibileWebElement(d, "finappToggleDD", pageName, null) != null) {
					List<WebElement> totalfinappOnPage = SeleniumUtil.getWebElements(d, "finappToggleDD", pageName,
							null);
					logger.info("size==" + totalfinappOnPage.size());
					if (totalfinappOnPage != null) {
						for (int i = 0; i < totalfinappOnPage.size(); i++) {
							Thread.sleep(4000);
							try {
								String title = totalfinappOnPage.get(i).getAttribute("title");
								String link = "a[title='" + title + "']";
								if (title.toLowerCase().contains("accounts")) {
									continue;
								} else {
									// By elementLoc=(By) d.findElement(By.xpath(link));
									WebElement toggleButton = SeleniumUtil.waitUntilElementVisible(d, "css", link, 7);
									if (toggleButton != null) {
										SeleniumUtil.click(toggleButton);
										Thread.sleep(1000);
									}
									WebElement removelink = SeleniumUtil.findElementWithText(d, "a", "Remove", 4);
									if (removelink != null) {
										SeleniumUtil.click(removelink);
										Thread.sleep(1000);
									}
									WebElement removeConf = SeleniumUtil.findElementWithText(d, "input", "Remove", 3);
									if (removeConf != null) {
										SeleniumUtil.click(removeConf);
										Thread.sleep(1000);
									}
								}

							} catch (Exception e) {
							}
						}
					}
				}
			} catch (Exception e) {
			}
		}

	}

	public static void mergeScreenShotToReport(String nameOfSS) {

		// String methodName=new ITestResult().getMethod().getMethodName();
		DateFormat dateFormat = new SimpleDateFormat("dd_MMM_yyyy__hh_mm_ssaa");
		String destDir = "./screenshots";
		String destDir2 = "c:/ELPAM/screenshots";
		// new File(destDir).mkdirs();
		String destFile = nameOfSS + "_" + dateFormat.format(new Date()) + ".png";
		try {
			CustomListener.captureScreenshot(TestBase.getDriver(), destDir + "/" + destFile, destDir2 + "/" + destFile);
		} catch (Exception e1) {
			logger.error("Error capturing the failure screenshot - {}", e1.getMessage());
			EReporter.log(LogStatus.INFO, "Error capturing the failure screenshot - " + e1.getMessage());
			e1.printStackTrace();
		}
		Reporter.setEscapeHtml(false);
		Reporter.log("<a target='_blank' href=\"file://" + destDir2 + "/" + destFile + "\">" + destFile + "</a>");
		TestBase.tc.addScreenCapture(destDir + "/" + destFile);
		TestBase.tc.log(LogStatus.INFO,
				"<a target='_blank' href=\"../" + "screenshots" + "/" + destFile + "\">" + destFile + "</a>");
	}

	public static void mergeScreenShotToReport(String nameOfSS, int postfix) {

		DateFormat dateFormat = new SimpleDateFormat("dd_MMM_yyyy__hh_mm_ssaa");
		String destDir = "./screenshots";
		String destDir2 = "c:/ELPAM/screenshots";
		// new File(destDir).mkdirs();
		String destFile = nameOfSS.concat(String.valueOf(postfix)) + "_" + dateFormat.format(new Date()) + ".png";
		try {
			CustomListener.captureScreenshot(TestBase.getDriver(), destDir + "/" + destFile, destDir2 + "/" + destFile);
		} catch (Exception e1) {
			logger.error("Error capturing the failure screenshot - {}", e1.getMessage());
			EReporter.log(LogStatus.INFO, "Error capturing the failure screenshot - " + e1.getMessage());
			e1.printStackTrace();
		}
		Reporter.setEscapeHtml(false);
		Reporter.log("<a target='_blank' href=\"file://" + destDir2 + "/" + destFile + "\">" + destFile + "</a>");
		TestBase.tc.addScreenCapture(destDir + "/" + destFile);
		TestBase.tc.log(LogStatus.INFO,
				"<a target='_blank' href=\"../" + "screenshots" + "/" + destFile + "\">" + destFile + "</a>");
	}

}
