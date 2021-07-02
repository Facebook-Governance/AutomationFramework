/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
package com.omni.pfm.utility;

import static org.testng.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.google.common.base.Function;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.XmlBeans.Element;
import com.omni.pfm.config.Config;
import com.omni.pfm.constants.SeleniumConstants;
import com.omni.pfm.listeners.EReporter;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.webdriver.WebDriverFactory;
import com.relevantcodes.extentreports.LogStatus;

/**
 * @author Suhaib
 *
 */
public class SeleniumUtil {
	private static final Logger logger = LoggerFactory.getLogger(SeleniumUtil.class);
	static Config config = Config.getInstance();
	private static int counter = 60;
	public static By loadingSymbol = getByObject("LinkAnAccount", null, "loadingSymbol");
	public static By spinnerWheel = getByObject("Transaction", null, "spinnerWheelWrap");
	public static By inlineSpinnerWheelWrap = getByObject("Transaction", null, "inlineSpinnerWheelWrap");
	public static By toastMessage = getByObject("Budget", null, "toastMessage");

	public static WebElement waitUntilElementVisible(WebDriver d, By by, int timeOuts) {
		logger.info("==> Entering waitUntilElementVisible method");
		WebElement ele = null;
		WebDriverWait wait = new WebDriverWait(d, timeOuts);
		if (d == null) {
			logger.error("WebDriver instance is null [{}]. Returning null", d);
		} else if (by == null) {
			logger.error("By value is null [{}]. Returning with null.", by);
		} else {
			try {
				logger.info("Waiting for element to be visible - {}", by.toString());
				ele = wait.until(ExpectedConditions.elementToBeClickable(by));
				logger.info("Element [{}] found", by.toString());
			} catch (TimeoutException e) {
				logger.error("Could not find the element [{}], returning null", by.toString());
			}
		}
		logger.info("<== Exiting waitUntilElementVisible method");
		return ele;
	}

	public static void waitForPageToLoad(long time) {
		waitUntilSpinnerWheelIsInvisible();
		waitUntilSpinnerWheelIsInvisible();
	}

	public static void waitForPageToLoad() {
		waitUntilSpinnerWheelIsInvisible();
		waitUntilSpinnerWheelIsInvisible();
	}

	public static boolean waitUntilElementInvisible(WebDriver d, By by, int timeOut) {
		logger.info("==> Entering waitUntilElementInvisible method");
		WebDriverWait wait;
		boolean isInvisible = false;
		if (d == null) {
			logger.error("WebDriver Instance is null : {}", d);
		} else if (by == null) {
			logger.error("By-Object is null : {}", by);
		} else {
			wait = new WebDriverWait(WebDriverFactory.getDriver(), timeOut);
			logger.info("Waiting for element to become invisible");
			isInvisible = wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
			logger.info("Finished waiting");
		}
		logger.info("<== Exiting waitUntilElementInvisible method");
		return isInvisible;
	}

	public static boolean waitUntilElementInvisible(WebDriver d, String elementName, String pageName,
			String frameName) {
		By by = getByObject(pageName, frameName, elementName);
		int timeOut = PageParser.getPageElement(pageName, frameName, elementName).getTimeOut();
		if (timeOut == 0) {
			timeOut = 40;
		}
		return waitUntilElementInvisible(d, by, timeOut);
	}

	public static WebElement waitUntilElementPresent(WebDriver d, String by, String expression, int timeOuts) {
		logger.info("==> Entering overloaded waitUntilElementVisible method");
		WebElement ele = null;
		WebDriverWait wait = new WebDriverWait(WebDriverFactory.getDriver(), timeOuts);
		if (d == null) {
			logger.error("WebDriver instance is null [{}]. Returning null", d);
		} else if (by == null || by.trim().equals("")) {
			logger.error("Loctor type value is null or empty [{}]. Returning with null.", by);
		} else if (expression == null || expression.trim().equals("")) {
			logger.error("Expression value is null or empty [{}]. Returning with null");
		} else {
			try {
				logger.info("Waiting for element to be visible - {} , {}", by, expression);
				switch (by.toLowerCase()) {
				case "id":
					ele = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(expression)));
					break;
				case "xpath":
					ele = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(expression)));
					break;
				case "css":
					ele = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(expression)));
					break;
				case "name":
					ele = wait.until(ExpectedConditions.presenceOfElementLocated(By.name(expression)));
					break;
				case "link":
					ele = wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(expression)));
					break;
				case "tag":
					ele = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName(expression)));
					break;
				case "partiallink":
					ele = wait.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText(expression)));
					break;
				default:
					logger.error("Invalid locator(by) value - [{}]", by);
					ele = null;
					break;
				}
				logger.info("Element [{} - {}] found", by, expression);
			} catch (Exception e) {
				logger.error("Could not find the element [{} - {}], returning null", by, expression);
				String msg = "Could not find the element [" + by + " - " + expression + "]";
				System.out.println("::::::::::::::::::::::::::::::" + msg);
				EReporter.log(LogStatus.INFO, msg);
			}
		}
		logger.info("<== Exiting waitUntilElementVisible method");
		return ele;
	}

	public static WebElement waitUntilElementVisible(WebDriver d, String by, String expression, int timeOuts) {
		logger.info("==> Entering overloaded waitUntilElementVisible method");
		WebElement ele = null;
		WebDriverWait wait = new WebDriverWait(WebDriverFactory.getDriver(), timeOuts);
		if (d == null) {
			logger.error("WebDriver instance is null [{}]. Returning null", d);
		} else if (by == null || by.trim().equals("")) {
			logger.error("Loctor type value is null or empty [{}]. Returning with null.", by);
		} else if (expression == null || expression.trim().equals("")) {
			logger.error("Expression value is null or empty [{}]. Returning with null");
		} else {
			try {
				logger.info("Waiting for element to be visible - {} , {}", by, expression);
				switch (by.toLowerCase()) {
				case "id":
					ele = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(expression)));
					break;
				case "xpath":
					ele = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(expression)));
					break;
				case "css":
					ele = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(expression)));
					break;
				case "name":
					ele = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(expression)));
					break;
				case "link":
					ele = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(expression)));
					break;
				case "tag":
					ele = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName(expression)));
					break;
				case "partiallink":
					ele = wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(expression)));
					break;
				default:
					logger.error("Invalid locator(by) value - [{}]", by);
					ele = null;
					break;
				}
				logger.info("Element [{} - {}] found", by, expression);
			} catch (Exception e) {
				logger.error("Could not find the element [{} - {}], returning null", by, expression);
				String msg = "Could not find the element [" + by + " - " + expression + "]";
				System.out.println("::::::::::::::::::::::::::::::" + msg);
				EReporter.log(LogStatus.INFO, msg);
			}
		}
		logger.info("<== Exiting waitUntilElementVisible method");
		return ele;
	}

	/**
	 * Waits for the presence of element represented by the label within the page
	 * 'pageName' and frame 'frameName'
	 * 
	 * @param d
	 * @param label     - label of the element
	 * @param pageName  - page name where element is defined
	 * @param frameName - frame name if element is present within the frame else
	 *                  null
	 * @return - returns the WebElement object if element is found else null
	 */
	public static WebElement waitUntilPresenceOfElement(WebDriver d, String label, String pageName, String frameName) {
		logger.info("==> Entering waitUntilPresenceOfElement method");
		WebElement ele = null;
		String by;
		String expression;
		boolean isFrameElement = (frameName == null) ? false : true;
		String v[] = PageParser.getElementValues(pageName, isFrameElement, frameName, label);
		int timeOuts;
		if (v != null) {
			String timeOut = v[2];
			try {
				timeOuts = Integer.parseInt(timeOut);
			} catch (NumberFormatException ex) {
				logger.error("Time out is not a valid number..");
				timeOuts = 30;
			}
			if (timeOuts == 0) {
				timeOuts = 30;
			}
			by = v[0];
			expression = v[1];
			WebDriverWait wait = new WebDriverWait(d, timeOuts);
			if (d == null) {
				logger.error("WebDriver instance is null [{}]. Returning null", d);
			} else if (by == null || by.trim().equals("")) {
				logger.error("Loctor type value is null or empty [{}]. Returning with null.", by);
			} else if (expression == null || expression.trim().equals("")) {
				logger.error("Expression value is null or empty [{}]. Returning with null");
			} else {
				try {
					logger.info("Waiting for element to be visible - {} , {}", by, expression);
					switch (by.toLowerCase()) {
					case "id":
						ele = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(expression)));
						break;
					case "xpath":
						ele = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(expression)));
						break;
					case "css":
						ele = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(expression)));
						break;
					case "name":
						ele = wait.until(ExpectedConditions.presenceOfElementLocated(By.name(expression)));
						break;
					case "link":
						ele = wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(expression)));
						break;
					case "tag":
						ele = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName(expression)));
						break;
					case "partiallink":
						ele = wait.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText(expression)));
						break;
					default:
						logger.error("Invalid locator(by) value - [{}]", by);
						ele = null;
						break;
					}
					logger.info("Element [{} - {}] found", by, expression);
				} catch (Exception e) {
					logger.error("Could not find the element [{} - {}], returning null", by, expression);
					String msg = "Could not find the element [" + by + " - " + expression + "]";
					System.out.println("::::::::::::::::::::::::::::::" + msg);
					EReporter.log(LogStatus.INFO, msg);
				}
			}
		}
		logger.info("<== Exiting waitUntilPresenceOfElement method");
		return ele;
	}

	public static List<WebElement> waitUntilElementsVisible(WebDriver d, String by, String expression, int timeOuts) {
		logger.info("==> Entering overloaded waitUntilElementVisible method");
		List<WebElement> elementList = new ArrayList<>();
		WebDriverWait wait = new WebDriverWait(WebDriverFactory.getDriver(), timeOuts);
		if (d == null) {
			logger.error("WebDriver instance is null [{}]. Returning null", d);
		} else if (by == null || by.trim().equals("")) {
			logger.error("By value is null or empty [{}]. Returning with null.", by);
		} else if (expression == null || expression.trim().equals("")) {
			logger.error("Expression value is null or empty [{}]. Returning with null");
		} else {
			try {
				logger.info("Waiting for element to be visible - {} , {}", by, expression);
				switch (by.toLowerCase()) {
				case "id":
					elementList = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id(expression)));
					break;
				case "xpath":
					elementList = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(expression)));
					break;
				case "css":
					elementList = wait
							.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(expression)));
					break;
				case "name":
					elementList = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.name(expression)));
					break;
				case "link":
					elementList = wait
							.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.linkText(expression)));
					break;
				case "tag":
					elementList = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName(expression)));
					break;
				case "partiallink":
					elementList = wait
							.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.partialLinkText(expression)));
					break;
				default:
					logger.error("Invalid locator(by) value - [{}]", by);
					elementList = null;
					break;
				}
				logger.info("Element [{} - {}] found", by, expression);
			} catch (Exception e) {
				logger.error("Could not find the elements [{} - {}], returning null", by, expression);
				String msg = "Could not find the element [" + by + " - " + expression + "]";
				System.out.println("::::::::::::::::::::::::::::::" + msg);
				EReporter.log(LogStatus.INFO, msg);
			}
		}
		logger.info("<== Exiting waitUntilElementVisible method");
		return elementList;
	}

	/**
	 * Gets the web element. This method will return the element even if it is not
	 * visible on the page. To get the Visible Element, use getVisibleWebElement.
	 * 
	 * @param d         - WebDriver instance
	 * @param label     - label of the element in xml
	 * @param pageName  - name of the page where element is to be located in the xml
	 * @param frameName - name of the frame is the element is located inside the
	 *                  frame other wise null or empty
	 * @return - WebElement object if the element is present else null.
	 */
	public static WebElement getWebElement(WebDriver d, String label, String pageName, String frameName) {
		WebElement we = null;
		boolean isFrameElement = true;
		if (d == null) {
			logger.error("WebDriver instance is null - {}", d);
			return null;
		} else if (GenericUtil.isNull(label)) {
			logger.error("Label is null or empty - {}", label);
			return null;
		} else if (GenericUtil.isNull(pageName)) {
			logger.error("Label is null or empty - {}", label);
			return null;
		} else if (GenericUtil.isNull(frameName)) {
			logger.info("Frame Name is null or empty. Element will be located inside the page");
			isFrameElement = false;
		}
		logger.info("label = {}, pageName = {}, frameName = {}", label, pageName, frameName);
		String v[] = PageParser.getElementValues(pageName, isFrameElement, frameName, label);
		if (v == null || !(v.length == 3)) {
			logger.error("Error while fetching the value of label from the XML");
		} else {
			int timeOut = Integer.parseInt(v[2]);
			we = PageParser.getWebElement(d, v[0], v[1], timeOut);
			if (we != null) {
				scrollToWebElementMiddle(d, we);
			} else
				EReporter.log(LogStatus.INFO, "Could not find the Visible WebElement with elementName = " + label
						+ ", pageName = " + pageName + " and frame = " + frameName);
		}
		logger.info("WebElement Value is : {}", we);
		return we;
	}

	public static List<WebElement> getWebElements(WebDriver d, String label, String pageName, String frameName) {
		List<WebElement> elementList = new ArrayList<WebElement>();
		boolean isFrameElement = true;
		if (d == null) {
			logger.error("WebDriver instance is null - {}", d);
			return null;
		} else if (GenericUtil.isNull(label)) {
			logger.error("Label is null or empty - {}", label);
			return null;
		} else if (GenericUtil.isNull(pageName)) {
			logger.error("Label is null or empty - {}", label);
			return null;
		} else if (GenericUtil.isNull(frameName)) {
			isFrameElement = false;
		}
		logger.info("label = {}, pageName = {}, frameName = {}", label, pageName, frameName);
		String v[] = PageParser.getElementValues(pageName, isFrameElement, frameName, label);
		if (v == null || !(v.length == 3)) {
			logger.error("Error while fetching the value of label from the XML");
		} else {
			int timeOut = v[2] == null ? Integer.parseInt(v[2]) : 10;
			elementList = waitUntilElementsVisible(d, v[0], v[1], timeOut);
		}
		if (elementList == null || elementList.size() < 1) {
			EReporter.log(LogStatus.INFO, "Could not find the Visible WebElements with elementName = " + label
					+ ", pageName = " + pageName + " and frame = " + frameName);
		}
		logger.info("WebElement Value is : {}", elementList);
		return elementList;
	}

	public static By getByObject(String pageName, String frameName, String elementName) {
		By by = null;
		boolean isFrameElement = false;
		if (GenericUtil.isNull(pageName)) {
			logger.info("Page name is null or empty : {}", pageName);
		} else if (GenericUtil.isNull(elementName)) {
			logger.error("Element Name is null or empty : {}", elementName);
		} else {
			String values[];
			if (GenericUtil.isNull(frameName)) {
			} else
				isFrameElement = true;
			values = PageParser.getElementValues(pageName, isFrameElement, frameName, elementName);
			if (values == null || values.length != 3) {
				logger.error("Element value is null or length is invalid");
				/*
				 * Assert.fail("Element :: '" + elementName +
				 * "' is not present under the Page ::'" + pageName + "' in the locator file");
				 */
			} else {
				logger.info("Element Values are : {} - {}", values[0], values[1]);
				switch (values[0]) {
				case "id":
					by = By.id(values[1]);
					break;
				case "xpath":
					by = By.xpath(values[1]);
					break;
				case "css":
					by = By.cssSelector(values[1]);
					break;
				case "name":
					by = By.name(values[1]);
					break;
				case "link":
					by = By.linkText(values[1]);
					break;
				case "tag":
					by = By.tagName(values[1]);
					break;
				case "partialLink":
					by = By.partialLinkText(values[1]);
				case "class":
				case "classname":
					by = By.className(values[1]);
					break;
				default:
					logger.error("Invalid locator(by) value - [{}]", values[0]);
					break;
				}
			}
		}
		logger.info("By value of element '{}' under page '{}' is : {}", elementName, pageName, by);
		return by;
	}

	public static WebElement getHTMLElement(String elementName, String pageName, String frameName, int timeOuts) {
		WebDriver d = WebDriverFactory.getDriver();
		By by = getByObject(pageName, frameName, elementName);
		return waitUntilElementVisible(d, by, timeOuts);
	}

	public static boolean populateElement(String elementName, String pageName, String frameName, String value,
			int timeOuts) {
		logger.info("==> Entering populateElement method...");
		WebElement we = getHTMLElement(elementName, pageName, frameName, timeOuts);
		boolean status = true;
		if (we == null) {
			logger.error("Could Not find the Element {} in page {}", elementName, pageName);
			status = false;
		} else {
			if (!GenericUtil.isNull(value)) {
				we.clear();
				we.sendKeys(value);
			} else {
				Element el = PageParser.getPageElement(pageName, elementName);
				String text = el.getElementValue();
				if (!GenericUtil.isNull(text)) {
					we.clear();
					we.sendKeys(text);
				}
			}
		}
		logger.info("<== Exiting populateElement");
		return status;
	}

	/**
	 * Used to click an element defined in the &ltclick&gt in the xml. Use
	 * getxxxElement() for getting the web element and perform the click if you are
	 * not using writing xml test scripts.
	 * 
	 * @param elementName
	 * @param pageName
	 * @param frameName
	 * @param timeOuts
	 */
	public static void clickeElement(String elementName, String pageName, String frameName, int timeOuts) {
		WebElement we = getHTMLElement(elementName, pageName, frameName, timeOuts);
		if (we != null) {
			we.click();
		} else {
			logger.error("Could not find element {} in page {}", elementName, pageName);
		}
	}

	/**
	 * - Used to get all the values of the custom dropdown.
	 * 
	 * @param d                  - WebDriver instance
	 * @param dropDownImageLabel - Label of the drop down image
	 * @param listLabel          - Label of the list of options
	 * @param pageName           - page name where the drop down is defined
	 * @param frameName          - frame name if present in the frame
	 * @return - List of String the contains all the values of the dropdown or null
	 *         dropdown is not found.
	 */
	public static List<String> getCustomDDValues(WebDriver d, String dropDownImageLabel, String listLabel,
			String pageName, String frameName) {
		FunctionUtil.logger.info("==> Entering getCustomDDValues method...");
		List<String> values = null;
		WebElement dropImage = getWebElement(d, dropDownImageLabel, pageName, frameName);
		if (dropImage == null) {
			FunctionUtil.logger.error("Could not find the drop Image - {}", dropDownImageLabel);
		} else {
			dropImage.click();
			List<WebElement> optionList = getWebElements(d, listLabel, pageName, frameName);
			if (optionList == null) {
				FunctionUtil.logger.error("Could not find the option values with label - {}", listLabel);
			} else {
				FunctionUtil.logger.info("Found the option list. Total number of options are - {}", optionList.size());
				values = new ArrayList<>();
				for (WebElement option : optionList) {
					FunctionUtil.logger.info("Value = {}", option.getText());
					values.add(option.getText().trim());
				}
			}
			dropImage.click();
		}
		FunctionUtil.logger.info("<== Entering getCustomDDValues method...");
		return values;
	}

	/**
	 * Select a value from the Custom DropDown or a normal select with the visible
	 * text within a Page
	 * 
	 * @param d              - WebDriver instance
	 * @param dropDownObject - Label of the DropDown element or the select element
	 * @param valueToSelect  - Value to be selected from the DropDown. <i>Note: It
	 *                       is the actual value and not the label of the value.</i>
	 * @param pageName       - Page Name where dropdown is defined.
	 * @param frameName      _ Frame name if the dropdown is defined in a frame.
	 * @return <b>true</b> if DropDown value is successfully selected, else
	 *         <b>false</b>.
	 */
	public static boolean selectCustomDDValue(WebDriver d, String dropDownObject, String valueToSelect, String pageName,
			String frameName) {
		logger.info("==> Entering selectCustomDDValue method...");
		boolean status = false;
		if (GenericUtil.checkParamsNotNull(d, dropDownObject, valueToSelect, pageName)) {
			WebElement drop = getVisibileWebElement(d, dropDownObject, pageName, frameName);
			if (drop != null) {
				if ("select".equalsIgnoreCase(drop.getTagName())) {
					new Select(drop).selectByVisibleText(valueToSelect);
				} else {
					click(drop);
					String valueExp = "//li[contains(text(),'" + valueToSelect + "')]";
					String valueExp2 = "//li//*[contains(text(),'" + valueToSelect + "')]";
					logger.info("Value to select expression is : {}", valueExp);
					logger.info("Value to select expression 2 is : {}", valueExp2);
					WebElement value = null;
					try {
						value = d.findElement(By.xpath(valueExp));
					} catch (Exception e) {
						logger.error(
								"Could not find the value [{}] by expression [{}] in the dropdown. Trying with other expression",
								valueToSelect, valueExp);
						try {
							value = d.findElement(By.xpath(valueExp2));
						} catch (Exception e2) {
							logger.error("Could not find the value [{}] by expression [{}] in the dropdown.",
									valueToSelect, valueExp2);
							EReporter.log(LogStatus.INFO,
									"Could not find the dropdown value by the given xpath expression. Exp1 = "
											+ valueExp + " and Exp2 = " + valueExp2);
						}
					}
					if (value != null && value.isDisplayed()) {
						// drop.click();
						click(value);
						status = true;
					} else {
						click(drop);
						// logger.error("Could not find the value [{}] by
						// expression [{}]", valueToSelect, valueExp);
					}
				}
			} else {
				logger.error("Could not find the drop down element - {}", dropDownObject);
				EReporter.log(LogStatus.INFO, "Could not find the drop down element - " + dropDownObject);
			}
		} else
			logger.error("One or more parameters in [d,dropImageLabel,valueToSelect] are null or empty...");
		logger.info("<== Exiting selectCustomDDValue method...");
		return status;
	}

	/**
	 * Select a value from the Custom DropDown or normal select within a Finapp
	 * 
	 * @param d                   - WebDriver instance
	 * @param dropDownObjectLabel - Label of the DropDown element or normal select
	 *                            element
	 * @param valueToSelect       - Value to be selected from the DropDown. <i>Note:
	 *                            It is the actual value and not the label of the
	 *                            value.</i>
	 * @param pageName            - Page Name where dropdown is defined.
	 * @param frameName           _ Frame name is the finapp frame defined inside
	 *                            the respective page.
	 * @return <b>true</b> if DropDown value is successfully selected, else
	 *         <b>false</b>.
	 */
	public static boolean selectCustomFinappDDValue(WebDriver d, String dropImageLabel, String valueToSelect,
			String pageName, String frameName) {
		logger.info("==> Entering selectCustomDDValue method...");
		boolean status = false;
		if (GenericUtil.checkParamsNotNull(d, dropImageLabel, valueToSelect, pageName)) {
			WebElement drop = getVisibileWebElement(d, dropImageLabel, pageName, frameName);
			if (drop != null) {
				if ("select".equalsIgnoreCase(drop.getTagName())) {
					new Select(drop).selectByVisibleText(valueToSelect);
				} else {
					click(drop);
					String valueExp = "//li//*[contains(text(),'" + valueToSelect + "')]";
					logger.info("Value to select expression is : {}", valueExp);
					List<WebElement> values = d.findElements(By.xpath(valueExp));
					if (values.size() > 0) {
						for (WebElement value : values)
							if (value.isDisplayed()) {
								// drop.click();
								click(value);
								status = true;
								break;
							}
					} else {
						logger.error("Could not find the value [{}] by expression [{}]", valueToSelect, valueExp);
						EReporter.log(LogStatus.INFO,
								"Could not find the value [" + valueToSelect + "] by expression [" + valueExp + "]");
					}
				}
			} else
				logger.error("Could not find the drop down element - {}", dropImageLabel);
			EReporter.log(LogStatus.INFO, "Could not find the drop down element - " + dropImageLabel);
		} else
			logger.error("One or more parameters in [d,dropImageLabel,valueToSelect] are null or empty...");
		logger.info("<== Exiting selectCustomDDValue method...");
		return status;
	}

	/**
	 * Check if the value is present in the custom dropdown
	 * 
	 * @param d              WebDriver instance
	 * @param dropImageLabel - DropDown image label
	 * @param valueToVerify  - Value to select
	 * @param pageName       -
	 * @param frameName
	 * @return True if value is present in the dropdown else false
	 */
	public static boolean verifyCustomDDValueIsPresent(WebDriver d, String dropImageLabel, String valueToVerify,
			String pageName, String frameName) {
		logger.info("==> Entering verifyCustomDDValueIsPresent method...");
		boolean status = false;
		if (GenericUtil.checkParamsNotNull(d, dropImageLabel, valueToVerify, pageName)) {
			WebElement drop = getWebElement(d, dropImageLabel, pageName, frameName);
			if (drop != null) {
				drop.click();
				String valueExp = "//li[contains(text(),'" + valueToVerify + "')]";
				logger.info("Value to select expression is : {}", valueExp);
				WebElement value = null;
				try {
					value = d.findElement(By.xpath(valueExp));
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				if (value != null) {
					status = true;
				} else {
					logger.error("Could not find the value [{}] by expression [{}]", valueToVerify, valueExp);
				}
				drop.click();
			} else
				logger.error("Could not find the drop down element - {}", dropImageLabel);
		} else
			logger.error("One or more parameters in [d,dropImageLabel,valueToSelect] are null or empty...");
		logger.info("<== Exiting verifyCustomDDValueIsPresent method...");
		return status;
	}

	public static List<String> getAllCustomDDValue(WebDriver d, String dropImageLabel, String pageName,
			String frameName) {
		logger.info("==> Entering getAllCustomDDValue method...");
		List<String> ddValues = null;
		if (GenericUtil.checkParamsNotNull(d, dropImageLabel, pageName)) {
			WebElement drop = getWebElement(d, dropImageLabel, pageName, frameName);
			// Element ddElement = PageParser.getPageElement(pageName,frameName,
			// dropImageLabel);
			if (drop != null) {
				drop.click();
				String script = "return arguments[0].nextElementSibling";
				JavascriptExecutor js = (JavascriptExecutor) d;
				WebElement listElement = (WebElement) js.executeScript(script, drop);
				List<WebElement> values;
				if (!"ul".equalsIgnoreCase(listElement.getTagName())) {
					values = listElement.findElements(By.xpath("ol/li"));
				} else {
					values = listElement.findElements(By.xpath("li"));
				}
				if (values != null) {
					logger.info("Number of li values found = {}", values.size());
					ddValues = new ArrayList<>();
					for (WebElement w : values) {
						if (!"".equals(w.getText().trim())) {
							ddValues.add(w.getText().trim());
						}
					}
					logger.info("Actual number of values = {}", ddValues.size());
				} else {
					logger.error("Could not find the DropDown values");
				}
			} else
				logger.error("Could not find the drop down element - {}", dropImageLabel);
		} else
			logger.error("One or more parameters in [d,dropImageLabel,valueToSelect] are null or empty...");
		logger.info("<== Exiting getAllCustomDDValue method...");
		return ddValues;
	}

	/**
	 * Click on the finapp drop down
	 * 
	 * @param d
	 * @param pageName    - page name
	 * @param frameName   - if finapp is inside a frame
	 * @param ddLabel     - Drop down label
	 * @param ddItemLabel - Drop down item to click
	 */
	public static void clickFinappDDItem(WebDriver d, String pageName, String frameName, String ddLabel,
			String ddItemLabel) {
		logger.info("==> Entering clickFinappDDItem method.");
		WebElement dd = getVisibileWebElement(d, ddLabel, pageName, frameName);
		if (dd != null) {
			new Actions(d).moveToElement(dd).click().build().perform();
		}
		WebElement item = getVisibileWebElement(d, ddItemLabel, pageName, frameName);
		if (item == null) {
			logger.error("Could not find the DropDown Item with label - {}", ddItemLabel);
		} else {
			item.click();
		}
		logger.info("<== Exiting clickFinappDDItem method.");
	}

	/**
	 * @param table
	 * @param cellText
	 * @param cellType
	 * @return
	 */
	public static boolean findTableCellWithText(WebDriver table, String cellText, String cellType) {
		logger.info("==> Entering FindTableCellWithText method.");
		boolean status = false;
		if (table == null) {
			logger.error("Table Element is null");
		} else if (GenericUtil.isNull(cellText)) {
			logger.error("Cell Text is null or empty");
		} else {
			logger.debug("Cell Text = [{}]", cellText);
			String cellExp = "//td";
			// "//td//*[contains(text(),'" + cellText + "')]"
			if (!GenericUtil.isNull(cellType)) {
				cellExp += "//" + cellType;
			}
			cellExp += "[contains(text(),'" + cellText + "')]";
			logger.info("Effective xpath = [{}]", cellExp);
			List<WebElement> list = table.findElements(By.xpath(cellExp));
			if (list == null || list.isEmpty()) {
				logger.error("Could not find the cell with text - [{}]", cellText);
			} else {
				status = true;
				logger.info("Found the cell with text - [{}]", cellText);
			}
		}
		logger.info("<== Exiting FindTableCellWithText method.");
		return status;
	}

	/**
	 * Finds an element with the given text
	 * 
	 * @param d           - WebDriver or WebElement instance
	 * @param elementType - Type of element like span, strong, div etc
	 * @param elementText - Text of the element
	 * @return WebElement object if an element is found with the given text and type
	 *         or else null.
	 */
	public static WebElement findElementWithText(WebDriver d, String elementType, String elementText) {
		logger.info("==> Entering findElementWithText method.");
		WebElement element = null;
		if (GenericUtil.isNull(elementText)) {
			logger.error("Cell Text is null or empty");
		} else {
			logger.debug("Cell Text = [{}]", elementText);
			String eleExp = "//";
			if (!GenericUtil.isNull(elementType)) {
				eleExp += elementType;
			} else {
				eleExp += "*";
			}
			eleExp += "[contains(.,'" + elementText + "')]";
			logger.info("Effective xpath = [{}]", eleExp);
			List<WebElement> list = d.findElements(By.xpath(eleExp));
			if (list == null || list.isEmpty()) {
				logger.error("Could not find the element with text - [{}] and elementType =  [{}]", elementText,
						elementType);
				EReporter.log(LogStatus.INFO, "Could not find the element with text - [" + elementText
						+ "] and elementType =  [" + elementType + "]");
			} else {
				element = list.get(0);
				logger.info("Found the cell with text - [{}] and elementType =  [{}]", elementText, elementType);
			}
		}
		logger.info("<== Exiting findElementWithText method.");
		return element;
	}

	/**
	 * @param d
	 * @param elementType      - Element Type
	 * @param elementText      - Text of the Element
	 * @param timeOutInSeconds - Time to wait in seconds
	 * @return
	 */
	public static WebElement findElementWithText(WebDriver d, String elementType, String elementText,
			int timeOutInSeconds) {
		logger.info("==> Entering findElementWithText method.");
		WebElement element = null;
		String exp2 = null;
		if (GenericUtil.isNull(elementText)) {
			logger.error("Element Text is null or empty");
		} else {
			logger.debug("Element Text = [{}]", elementText);
			String eleExp = "//";
			if (!GenericUtil.isNull(elementType)) {
				eleExp += elementType;
				logger.info("Element Type = {}", elementType);
			} else {
				eleExp += "*";
			}
			if (elementType != null && "input".equalsIgnoreCase(elementType)) {
				eleExp += "[contains(@value,\"" + elementText + "\")]";
				exp2 = eleExp;
			} else {
				exp2 = eleExp;
				eleExp += "[contains(text(),\"" + elementText + "\")]";
				exp2 += "[contains(.,\"" + elementText + "\")]";
			}
			logger.info("Effective xpath = [{}]", eleExp);
			long startTime = System.currentTimeMillis();
			long endTime = startTime + (timeOutInSeconds * 1000);
			List<WebElement> list = null;
			while (startTime <= endTime) {
				list = d.findElements(By.xpath(eleExp));
				if (list == null)
					list = d.findElements(By.xpath(exp2));
				if (list == null || list.size() < 1 || (getVisibleElementFromList(list) == null)) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {

						e.printStackTrace();
					}
				} else
					break;
				startTime = System.currentTimeMillis();
			}
			logger.info("Start_Time = {}", startTime);
			logger.info("End_Time = {}", endTime);
			if (list == null || list.isEmpty()) {
				logger.error("Could not find the element with text - [{}] and elementType =  [{}]", elementText,
						elementType);
				EReporter.log(LogStatus.INFO, "Could not find the element with text - [" + elementText
						+ "] and elementType =  [" + elementType + "]");
			} else {
				element = getVisibleElementFromList(list);
				logger.info("Found the cell with text - [{}] and elementType =  [{}]", elementText, elementType);
			}
		}
		logger.info("<== Exiting findElementWithText method.");
		return element;
	}

	/**
	 * Get the cell WebElement object within table that is to the left of or right
	 * of a cell with given text and type
	 * 
	 * @param table           - Can be table WebElement if known otherwise WebDriver
	 *                        instance
	 * @param cellToFindText  - Text of the cell to find. If the cell to find does
	 *                        not have text node, value should be <code>null</code>.
	 * 
	 * @param cellToFindType  - Cell type to find. Can be a
	 *                        <code>&ltspan&gt or &ltdiv&gt or &lta&gt</code> or any
	 *                        other within a <code>&lttd&gt</code> tag.It is
	 *                        <code>null</code> if the text is directly present
	 *                        within the <code>&lttd&gt</code> tag. Uses xpath
	 *                        expression, if type is div and there are 3 div's and
	 *                        we want to get 2nd div, then use like div[2].
	 * @param leftOfOrRightOf - Determines whether to find the cell towards the left
	 *                        or towards the right of the the near cell.Expected
	 *                        Values are <code>SeleniumConstants.LEFT_OF</code> and
	 *                        <code>SeleniumConstants.RIGHT_OF</code>. Default value
	 *                        is <code>SeleniumConstants.RIGHT_OF</code>.
	 * @param nearCelltext    - Text of the cell near which we have to find the
	 *                        given cell.If the cell to find does not have text
	 *                        node, value should be <code>null</code>.
	 * @param nearCellType    - Type of the cell.Can be a
	 *                        <code>&ltspan&gt or &ltdiv&gt or &lta&gt</code> within
	 *                        a <code>&lttd&gt</code> tag.Uses xpath expression, if
	 *                        type is div and there are 3 div's and we want to get
	 *                        2nd div, then use like div[2].
	 * @return Returns the cell WebElement object. If not such element is found,
	 *         <code>null</code> is returned.
	 */
	public static WebElement findTableCellNear(WebDriver table, String cellToFindText, String cellToFindType,
			String leftOfOrRightOf, String nearCelltext, String nearCellType) {
		logger.info("==> Entering findTableCellNear method.");
		WebElement cell = null;
		if (GenericUtil.checkParamsNotNull(table)) {
			StringBuilder xpathExp = new StringBuilder("//td");
			if (!GenericUtil.isNull(nearCellType)) {
				logger.debug("Near Cell type = [{}]", nearCellType);
				// commented by mohit
				// xpathExp.append("//" + nearCellType);
			}
			if (!GenericUtil.isNull(nearCelltext)) {
				logger.debug("Near Cell text = [{}]", nearCelltext);
				xpathExp.append("[contains(.,'" + nearCelltext + "')]");
			}
			String position;
			if (!GenericUtil.isNull(leftOfOrRightOf)) {
				position = leftOfOrRightOf;
			} else
				position = SeleniumConstants.RIGHT_OF;
			if (position.equals(SeleniumConstants.LEFT_OF)) {
				xpathExp.append("/ancestor-or-self::td/preceding-sibling::td");
			} else {
				xpathExp.append("/ancestor-or-self::td/following-sibling::td");
			}
			if (!GenericUtil.isNull(cellToFindType)) {
				logger.debug("Cell to find Type = [{}]", cellToFindType);
				xpathExp.append("//" + cellToFindType);
			}
			if (!GenericUtil.isNull(cellToFindText)) {
				logger.debug("Cell to find Text = [{}]", cellToFindText);
				xpathExp.append("[contains(text(),'" + cellToFindText + "')]");
			}
			logger.debug("Effective XPATH = [{}]", xpathExp);
			List<WebElement> l = table.findElements(By.xpath(xpathExp.toString()));
			if (l != null && !l.isEmpty()) {
				// Added by Mohit
				/*
				 * for (int i=0;i<l.size();i++) { if (l.get(i).isDisplayed()) { return l.get(i);
				 * } }
				 */
				cell = l.get(0);
				logger.info("Cell found inside the table");
			} else
				logger.error("Could not find the cell within the table.");
		} else {
			logger.error("Table instance is null...");
		}
		logger.info("<== Exiting findTableCellNear method.");
		return cell;
	}

	/**
	 * To get the visible web element on the page.
	 * 
	 * @param d           - WebDriver instance
	 * @param elementName - Element name as defined in the xml.
	 * @param pageName    - Page name as defined in the xml.
	 * @param frameName   - If element is present in the frame, then the frame name
	 *                    that is defined in the xml, else null or empty string
	 * @return - WebElement object is the object is present and visible, otherwise
	 *         null.
	 */
	public static WebElement getVisibileWebElement(WebDriver d, String elementName, String pageName, String frameName) {
		try {
			d.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		} catch (Exception e) {
			// TODO: handle exception
		}
		WebElement element = waitForElementVisible(d, elementName, pageName, frameName);
		if (element != null) {
			scrollToWebElementMiddle(d, element);
		} else {
			EReporter.log(LogStatus.INFO,
					"Could not find the Visible WebElement with elementName = " + elementName + ", pageName = "
							+ pageName + " and frame = " + frameName
							+ " Also If this element is not present in co-brand, then ignore this message ");
		}
		return element;
	}

	public static void scrollToWebElementMiddle(WebDriver d, WebElement element) {
		((JavascriptExecutor) d).executeScript(
				"window.scrollTo(0,arguments[0].getBoundingClientRect().top + window.pageYOffset - (window.innerHeight / 2))",
				element);
	}

	/**
	 * Verify the headers of a table with the expected headers text
	 * 
	 * @param d               - table web element object
	 * @param expectedHeaders - String array of all the headers to be verified
	 * @return - true if all the expected headers are found in the table, else false
	 */
	public static boolean verifyTableHeaders(WebDriver d, String[] expectedHeaders) {
		logger.info("==> Entering verifyTableHeaders method...");
		boolean status = false;
		if (GenericUtil.checkParamsNotNull(d, expectedHeaders)) {
			List<String> headerText = getTableHeaders(d);
			if (headerText.size() < 1) {
				logger.error("No Headers found...");
			} else {
				status = true;
				for (String s : expectedHeaders) {
					if (headerText.contains(s)) {
						logger.info("Found Expected Header [{}] in the Table Headers", s);
					} else {
						status = false;
						logger.error("Could not find the header text - [{}]", s);
						EReporter.log(LogStatus.INFO, "Could not find the header text - [" + s + "]");
					}
				}
			}
		} else {
			logger.error("One or more params are null");
		}
		logger.info("<== Exiting verifyTableHeaders method...");
		return status;
	}

	/**
	 * Retrieve the headers of a table denoted by the web element d into a list.
	 * 
	 * @param d - Table web element object
	 * @return a string list containing all the headers of a table if present
	 *         otherwise a list of size 0.
	 */
	public static List<String> getTableHeaders(WebDriver d) {
		logger.info("==> Entering getTableHeaders method..");
		List<String> headers = new ArrayList<>();
		if (d != null) {
			List<WebElement> hList = d.findElements(By.tagName("th"));
			long startTime = System.currentTimeMillis();
			long endTime = startTime * 10 * 20;
			while (startTime < endTime) {
				startTime = System.currentTimeMillis();
				try {
					Thread.sleep(500);
				} catch (Exception e) {
				}
				if (hList == null) {
					hList = d.findElements(By.tagName("th"));
				} else
					break;
			}
			if (hList == null || hList.size() < 1) {
				logger.error("Could not find the headers<th>...");
			} else {
				scrollToWebElementMiddle(d, hList.get(0));
				for (WebElement e : hList) {
					logger.info("HEADER = {}", e.getText());
					headers.add(e.getText());
				}
			}
		}
		logger.info("==> Entering getTableHeaders method..");
		return headers;
	}

	/**
	 * Select the check box in the Custom DropDown
	 * 
	 * @param d                  - WebDriver instance.
	 * @param drodDownObject     - DropDown Object.
	 * @param chkBxToSelectValue - Value whose corresponding checkbox is to be
	 *                           selected.
	 * @param checkboxType       - Type of checkbox, e.g., span, div, input. If null
	 *                           or empty string is passed, div is taken by default.
	 * @param pageName           - Name of the page
	 * @param frameName          - Frame name
	 * @return true if checkbox is clicked successfully, else false.
	 */
	public static boolean selectCustomDDCheckBox(WebDriver d, String drodDownObject, String chkBxToSelectValue,
			String checkboxType, String pageName, String frameName) {
		logger.info("==> Entering selectCustomDDValue method...");
		boolean status = false;
		if (GenericUtil.checkParamsNotNull(d, drodDownObject, chkBxToSelectValue, pageName)) {
			WebElement drop = getWebElement(d, drodDownObject, pageName, frameName);
			if (drop != null) {
				if ("select".equalsIgnoreCase(drop.getTagName())) {
					new Select(drop).selectByVisibleText(chkBxToSelectValue);
				} else {
					drop.click();
					if (GenericUtil.isNull(checkboxType)) {
						checkboxType = "div";
					}
					String valueExp = "//li[contains(text(),'" + chkBxToSelectValue + "')]/" + checkboxType;
					logger.info("Value to select expression is : {}", valueExp);
					WebElement value = null;
					try {
						value = d.findElement(By.xpath(valueExp));
					} catch (Exception e) {
						logger.error("Could not find the value [{}] by expression [{}] in the dropdown",
								chkBxToSelectValue, valueExp);
						EReporter.log(LogStatus.INFO, "Could not find the value [" + chkBxToSelectValue
								+ "] by expression [" + valueExp + "] in the dropdown");
					}
					if (value != null && value.isDisplayed()) {
						// drop.click();
						value.click();
						status = true;
					} else {
						logger.error("Could not find the value [{}] by expression [{}]", chkBxToSelectValue, valueExp);
					}
				}
			} else
				logger.error("Could not find the drop down element - {}", drodDownObject);
		} else
			logger.error("One or more parameters in [d,dropImageLabel,valueToSelect] are null or empty...");
		logger.info("<== Exiting selectCustomDDValue method...");
		return status;
	}

	public static WebElement getVisibleElementFromList(List<WebElement> list) {
		WebElement e = null;
		if (list == null || list.size() < 1) {
		} else {
			for (WebElement el : list) {
				if (el.isDisplayed()) {
					e = el;
					break;
				}
			}
		}
		return e;
	}

	public static WebElement waitForElementVisible(WebDriver d, String elementLabel, String pageName,
			String frameName) {
		WebElement element = null;
		Element xmlElement = PageParser.getPageElement(pageName, frameName, elementLabel);
		if (xmlElement == null) {
			EReporter.log(LogStatus.INFO, elementLabel + " not found in the page xml.");
			logger.error("{} not found in the page xml.", elementLabel);
			return null;
		}
		int timeOut = xmlElement.getTimeOut();
		if (timeOut == 0) {
			try {
				timeOut = Integer.parseInt(PropsUtil.getEnvPropertyValue("DefaultElementTimeOut"));
			} catch (Exception e) {
				logger.warn("Exception setting default TimeOut - {}", e);
				logger.warn("Default 10 secs will be used...");
				timeOut = 10;
			}
		}
		final By by = getByObject(pageName, frameName, elementLabel);
		int pollingTime = 1;
		long timeOutPeriod = System.currentTimeMillis() + (1000 * timeOut);
		while (System.currentTimeMillis() <= timeOutPeriod) {
			try {
				List<WebElement> list = d.findElements(by);
				if ((element = getVisibleElementFromList(list)) != null) {
					// element = d.findElement(by);
					break;
				} else {
					Thread.sleep(pollingTime * 1000);
				}
			} catch (Exception e) {
				try {
					Thread.sleep(pollingTime * 1000);
				} catch (Exception ex) {
				}
			}
		}
		if (element == null) {
			logger.error("Could not find the visible web element - [{}], [{}], [{}]", elementLabel, pageName,
					frameName);
		}
		return element;
	}

	public static WebElement getVisibleElement(WebDriver d, By by, int timeOut) {
		WebElement element = null;
		if (timeOut == 0) {
			try {
				timeOut = Integer.parseInt(PropsUtil.getEnvPropertyValue("DefaultElementTimeOut"));
			} catch (Exception e) {
				logger.warn("Exception setting default TimeOut - {}", e);
				logger.warn("Default 25 secs will be used...");
				timeOut = 25;
			}
		}
		int pollingTime = 1;
		long timeOutPeriod = System.currentTimeMillis() + (1000 * timeOut);
		while (System.currentTimeMillis() <= timeOutPeriod) {
			try {
				List<WebElement> list = d.findElements(by);
				if ((element = getVisibleElementFromList(list)) != null) {
					// element = d.findElement(by);
					break;
				} else {
					Thread.sleep(pollingTime * 1000);
				}
			} catch (Exception e) {
				try {
					Thread.sleep(pollingTime * 1000);
				} catch (Exception ex) {
				}
			}
		}
		if (element == null) {
			logger.error("Could not find the visible web element - By = {}", by);
		}
		return element;
	}

	public static WebElement waitForElementVisible(WebDriver d, By by, int timeOut) {
		WebElement element = null;
		if (timeOut == 0) {
			try {
				timeOut = Integer.parseInt(PropsUtil.getEnvPropertyValue("DefaultElementTimeOut"));
			} catch (Exception e) {
				logger.warn("Exception setting default TimeOut - {}", e);
				logger.warn("Default 40 secs will be used...");
				timeOut = 40;
			}
		}
		int pollingTime = 1;
		for (int i = 0; i < timeOut; i++) {
			try {
				WebElement w = d.findElement(by);
				if (w.isDisplayed()) {
					element = w;
					break;
				} else {
					Thread.sleep(pollingTime * 1000);
				}
			} catch (Exception e) {
			}
		}
		if (element == null) {
			logger.error("Could not find the visible web element with By object = [{}]", by);
		}
		return element;
	}
	/*
	 * public WebElement findElementNear(WebDriver d, String ){
	 * 
	 * }
	 */

	// for testing purpose

	/**
	 * get the default selected value for drop down
	 * 
	 * @author Mohit
	 * @param d           - WebDriver instance
	 * @param pageName    - Page Name where finapp is located as xml
	 * @param frameName   - Frame label as defined in xml within the corresponding
	 *                    pageName tag. Here value
	 * @param dropDownObj -drop down object as defined in xml within the
	 *                    corresponding pageName tag.
	 * 
	 * @return return the default selected value and if the dropdown is not found
	 *         then return null
	 */
	public static String getDefaultDDValue(WebDriver d, String pageName, String frameName, String dropDownObj) {
		logger.info("==> Entering getDefaultDDValue method.");
		String defaultValue = null;
		if (GenericUtil.isNull(pageName)) {
			logger.info("Page name is null or empty : {}", pageName);
		} else if (GenericUtil.isNull(dropDownObj)) {
			logger.error("Drop down Element Name is null or empty : {}", dropDownObj);
		} else {
			WebElement dropDownObject = getVisibileWebElement(d, dropDownObj, pageName, frameName);
			if (dropDownObject == null) {
				logger.error("Drop down Element object is null or empty : {}", dropDownObject);
				EReporter.log(LogStatus.INFO, "DropDown object " + dropDownObj + " not found on page or finapp");
			} else {
				defaultValue = dropDownObject.getText();
				logger.info("default value for the drodown {} is {}", dropDownObject, defaultValue);
			}
		}
		return defaultValue;
	}

	/*
	 * used
	 * 
	 * @author= Mohit
	 * 
	 * @param destDirectory -Directory Name from Data property file
	 * 
	 * @return -true if it takes screen shots else false
	 */
	public static boolean takeScreenshot(String destDirectory) {
		boolean status = false;
		WebDriver driver = TestBase.getDriver();
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		DateFormat dateFormat = new SimpleDateFormat("dd_MMM_yyyy__hh_mm_ssaa");
		String destDir = PropsUtil.getDataPropertyValue(destDirectory);// ex:
																		// "c:/ELPAM/screenshots";
		File dir = new File(destDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String destFile = "UICheck" + "_" + dateFormat.format(new Date()) + ".png";
		try {
			if ((new File(destDir + "/" + destFile)).exists()) {
				destFile = destFile + "_new";
			}
			FileUtils.copyFile(scrFile, new File(destDir + "/" + destFile));
			EReporter.log(LogStatus.INFO, "<a href=\"file://" + destDir + "/" + destFile + "\">Screenshot</a>");
			status = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * Get the Chart toolTip texts
	 * 
	 * @author Mohit
	 * @param d                - WebDriver instance
	 * @param pageName         - Page Name where finapp is located as xml
	 * @param frameName        - Frame label as defined in xml within the
	 *                         corresponding pageName tag. Here value
	 * @param containerToClick -Chart area to click as defined in xml within the
	 *                         corresponding frame tag. i.e. svg>g.highcharts-point
	 * @param elementToVerify  - text area located as xml. i.e
	 *                         div.highcharts-tooltip>span>span or
	 *                         div.highcharts-tooltip>span.customToolTip
	 * @return the Arrays of text and if did not find element then return null
	 */
	public static String[] getTooltipText(WebDriver d, String pageName, String frameName, String containerToClick,
			String elementToVerify) {
		List<WebElement> we = null;
		if (frameName == null)
			PageParser.navigateToPage(pageName, d);
		else
			PageParser.loadFrame(d, pageName, frameName);
		WebElement cl = getVisibileWebElement(d, containerToClick, pageName, frameName);
		cl.click();
		we = getWebElements(d, elementToVerify, pageName, frameName);
		String[] text = new String[we.size()];
		logger.info("size==" + we.size());
		if (we.size() != 0) {
			for (int i = 0; i < we.size(); i++) {
				text[i] = we.get(i).getText();
			}
			logger.info("The element found inside page {} and frame {} and element text {}", pageName, frameName,
					Arrays.toString(text));
		} else {
			logger.info("The element did not found inside page {} and frame {}", pageName, frameName);
			EReporter.log(LogStatus.INFO,
					"The element did not found inside page {" + pageName + "} and frame {" + frameName + "}");
		}
		if (frameName != null)
			d.switchTo().defaultContent();
		return text;
	}

	public static boolean verifyButtonorLink(WebDriver d, String pageName, String frameName, String objectToVerify) {
		boolean statusOfLink = false;
		d.switchTo().defaultContent();
		PageParser.loadFrame(d, null, frameName);
		logger.info(">>inside verifyButtonorLink");
		WebElement webObject = getVisibileWebElement(d, objectToVerify, pageName, frameName);
		if (webObject != null) {
			statusOfLink = true;
			logger.info("the element " + objectToVerify + " is present on page");
		} else {
			logger.error("the element " + objectToVerify + " is not present on page or locator has changed");
			EReporter.log(LogStatus.INFO,
					"the element " + objectToVerify + " is not present on page or locator has changed");
		}
		return statusOfLink;
	}

	public static boolean verifyFinappText(WebDriver d, String ElementLocator, String textToVerify, String pageName,
			String frameName) {
		boolean status = false;
		if (d == null || pageName == null || frameName == null || ElementLocator == null || textToVerify == null) {
			logger.error("Argument should not be null");
			return status;
		} else {
			d.switchTo().defaultContent();
			PageParser.loadFrame(d, null, frameName);
			WebElement ElementName = getVisibileWebElement(d, ElementLocator, pageName, frameName);
			if (ElementName != null) {
				String actualTextString = ElementName.getText();
				if (actualTextString.contains("\n"))
					actualTextString = actualTextString.replaceAll("\n", ",");
				logger.info("The value Actual Value is " + ElementName.getText());
				if (actualTextString.equals(textToVerify)) {
					status = true;
				} else {
					logger.error("The expected value {" + textToVerify + "} and actual value text {"
							+ ElementName.getText() + "} did not match in SFG finapp");
					EReporter.log(LogStatus.INFO,
							"The expected value {" + textToVerify + "} and the actual value text {"
									+ ElementName.getText() + "} did not match in SFG Finapp");
				}
			} else {
				logger.error("Correct account text object not found" + ElementLocator);
				EReporter.log(LogStatus.INFO, "Correct account text object not found IN SFG finapp " + ElementLocator);
			}
		}
		return status;
	}

	public static boolean clickOnFinappElement(WebDriver d, String elementToClick, String pageName, String frameName) {
		boolean status = false;
		if (d == null || elementToClick == null || pageName == null || frameName == null) {
			logger.error("The one of the parameter is null");
			status = false;
		} else {
			String callerMethodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			// PageParser.loadFrame(d, null, frameName);
			if (callerMethodName != null) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				WebElement eleToClick = getVisibileWebElement(d, elementToClick, pageName, frameName);
				if (eleToClick != null) {
					eleToClick.click();
					status = true;
					logger.info("The element is clicked by" + callerMethodName);
				} else {
					logger.error("The element " + elementToClick + " is not found or visible on finapp by method"
							+ callerMethodName);
					EReporter.log(LogStatus.INFO, "The element " + elementToClick
							+ " is not found or visible on finapp by method" + callerMethodName);
				}
			} else {
				status = false;
			}
		}
		return status;
	}

	public static void scrollElementIntoView(WebDriver d, WebElement element, boolean top) {
		if (element != null) {
			JavascriptExecutor jse = (JavascriptExecutor) d;
			String topView = "";
			if (top) {
				topView = "true";
			}
			try {
				jse.executeScript("arguments[0].scrollIntoView(" + topView + ")", element);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void click(WebElement we) {
		if (we != null) {
			logger.info("Clicking on element [{}]", we);
			try {
				we.click();
			} catch (Exception erp) {
				((JavascriptExecutor) WebDriverFactory.getDriver()).executeScript("arguments[0].click()", we);
			}
		} else {
			logger.error("WebElement to click is null.");
			throw new NullPointerException();
		}
	}

	public static void click(WebElement we, String message) {
		if (we != null) {
			logger.info("Clicking on element " + we);
			try {
				we.click();
			} catch (Exception ex) {
				Assert.fail("Unable to click on element :: \"" + we +"\" due to :: " + ex.getMessage());
			}
		} else {
			logger.error("WebElement to click is null.");
			EReporter.log(LogStatus.INFO, message);
		}
	}

	// Ramesh Method
	public static WebElement waitUntilPresenceOfElement(WebDriver d, String label, String pageName, String frameName,
			Integer TIMEOUT) throws InterruptedException {
		Boolean isFrameElement = true;
		if (frameName == null) {
			isFrameElement = false;
		}
		String[] values = PageParser.getElementValues(pageName, isFrameElement, frameName, label);
		WebElement element = null;
		int frequency = 5000;
		By by = null;
		switch (values[0]) {
		case "id":
			by = By.id(values[1]);
			break;
		case "xpath":
			by = By.xpath(values[1]);
			break;
		case "css":
			by = By.cssSelector(values[1]);
			break;
		case "name":
			by = By.name(values[1]);
			break;
		case "link":
			by = By.linkText(values[1]);
			break;
		case "tag":
			by = By.tagName(values[1]);
			break;
		case "partialLink":
			by = By.partialLinkText(values[1]);
		default:
			logger.error("Invalid locator(by) value - [{}]", values[0]);
			break;
		}
		while (TIMEOUT > 0) {
			Thread.sleep(frequency);
			TIMEOUT = TIMEOUT - frequency;
			try {
				element = d.findElement(by);
				break;
			} catch (NoSuchElementException ex) {
				continue;
			}
		}
		return element;
	}

	/*
	 * public static By getByObject(String type, String value) { By by = null;
	 * switch (type) { case "id": by = By.id(value); break; case "xpath": by =
	 * By.xpath(value); break; case "css": by = By.cssSelector(value); break; case
	 * "name": by = By.name(value); break; case "link": by = By.linkText(value);
	 * break; case "tag": by = By.tagName(value); break; case "partialLink": by =
	 * By.partialLinkText(value); default:
	 * logger.error("Invalid locator(by) value - [{}]", value); break; } return by;
	 * }
	 */

	/*
	 * public static WebElement getFrameWebElement(WebDriver d, String pageName,
	 * String frameName, int timeOut) { IFrame f = PageParser.getFrame(pageName,
	 * frameName); if (f != null) { if (!GenericUtil.isNull(f.getLocator()) &&
	 * !GenericUtil.isNull(f.getValue())) { return waitForElementVisible(d,
	 * getByObject(f.getLocator(), f.getValue()), timeOut); } } return null; }
	 */

	// added by Mohit
	public static boolean isVisible(WebElement element, String message) {
		if (element != null)
			return true;
		else {
			EReporter.log(LogStatus.INFO, message);
			logger.error(message);
			return false;
		}
	}

	public static boolean isVisible(boolean element, String message) {
		if (element)
			return true;
		else {
			EReporter.log(LogStatus.INFO, message);
			logger.error(message);
			return false;
		}
	}
	// END by Mohit

	public static void switchToDefaultContent(WebDriver d) {
		if (config.isIFrameEnabled()) {
			d.switchTo().defaultContent();
			d.switchTo().frame(d.findElement(By.id("YFrame")));
		} else
			d.switchTo().defaultContent();
	}

	public static void refresh(WebDriver d) {
		if (config.isIFrameEnabled()) {
			logger.info("Refreshing the page.");
			String currentPage = config.getCurrentPage();
			// PageParser.forceNavigate(PageConstants.TransactionPage, d);
			PageParser.forceNavigate(currentPage, d);
			logger.info("Refresh completed.");
		} else {
			logger.info("Refreshing the page.");
			d.navigate().refresh();
			logger.info("Refresh completed.");
		}
		waitUntilSpinnerWheelIsInvisible();
		waitUntilSpinnerWheelIsInvisible();
		waitUntilSpinnerWheelIsInvisible();
	}

	public static void waitForElement(String envName, int timeToWait) {
		String actualEnv = Config.getInstance().getEnvironment().toLowerCase();
		if (envName != null) {
			envName = envName.toLowerCase();
		}
		if (envName == null) {
			try {
				Thread.sleep(timeToWait);
			} catch (Exception e) {
				EReporter.log(LogStatus.INFO, "The waiting time is not a integer type");
			}
		} else if (actualEnv.contains(envName)) {
			try {
				Thread.sleep(timeToWait);
			} catch (Exception e) {
				EReporter.log(LogStatus.INFO, "The waiting time is not a integer type");
			}
		} else {
			// EReporter.log(LogStatus.INFO, "The Environment name does not
			// exist " + envName);
		}
	}

	/**
	 * Move cursor over the element passed as an argument
	 * 
	 * @param el - WebElement reference.Throws NPE if the WebElement reference is
	 *           null.
	 */
	public static void moveToElement(WebElement el) {
		try {
			WebDriver driver = WebDriverFactory.getDriver();
			Actions act = new Actions(driver);
			act.moveToElement(el).build().perform();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void SwitchToCurrentTab(WebDriver d) {
		if (getTabDataValue("TitleOfTabToClose") != null) {
			List<String> lblTitleOfTabToClose = FunctionUtil.splitLabelValueAsList("TitleOfTabToClose", ",");
			String base = d.getWindowHandle();
			Set<String> set = null;
			try {
				set = d.getWindowHandles();
			} catch (Exception er) {
				set.add(base);
			}
			if (set.size() != 1) {
				for (String a : set) {
					d.switchTo().window(a);
					if (lblTitleOfTabToClose.contains(d.getTitle())) {
						d.close();
					}
				}
			} else {
				d.switchTo().window(base);
			}

		}
	}

	public static String getVisibileElementXPath(WebDriver d, String pageName, String frameName, String elementName) {
		String value = null;
		boolean isFrameElement = false;
		if (GenericUtil.isNull(pageName)) {
			logger.info("Page name is null or empty : {}", pageName);
		} else if (GenericUtil.isNull(elementName)) {
			logger.error("Element Name is null or empty : {}", elementName);
		} else {
			String values[];
			if (GenericUtil.isNull(frameName)) {
			} else
				isFrameElement = true;
			values = PageParser.getElementValues(pageName, isFrameElement, frameName, elementName);
			if (values == null || values.length != 3) {
				logger.error("Element value is null or length is invalid");
			} else {
				logger.info("Element Values are : {} - {}", values[0], values[1]);
				value = values[1];
			}
		}
		logger.info("Value of Element is : {}", value);
		return value;
	}

	public static String getVisibileElementXPath(String pageName, String frameName, String elementName) {
		String value = null;
		boolean isFrameElement = false;
		if (GenericUtil.isNull(pageName)) {
			logger.info("Page name is null or empty : {}", pageName);
		} else if (GenericUtil.isNull(elementName)) {
			logger.error("Element Name is null or empty : {}", elementName);
		} else {
			String values[];
			if (GenericUtil.isNull(frameName)) {
				logger.warn("Frame Name is null or empty : {}", frameName);
				logger.warn("Element will be located within the page");
			} else
				isFrameElement = true;
			logger.info("PageName = [{}], FrameName = [{}], ElementName = [{}]", pageName, frameName, elementName);
			values = PageParser.getElementValues(pageName, isFrameElement, frameName, elementName);
			if (values == null || values.length != 3) {
				logger.error("Element value is null or length is invalid");
			} else {
				logger.info("Element Values are : {} - {}", values[0], values[1]);
				value = values[1];
			}
		}
		logger.info("Value of Element is : {}", value);
		return value;
	}

	public static void fluentWait(WebDriver d) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(d);
		// Specify the timout of the wait
		wait.withTimeout(8000, TimeUnit.MILLISECONDS);
		// Sepcify polling time
		wait.pollingEvery(1000, TimeUnit.MILLISECONDS);
		// This is how we specify the condition to wait on.
		// This is what we will explore more in this chapter
		// wait.until(ExpectedConditions.alertIsPresent());

	}

	public static void selectDate(Date date, WebElement calendarInstance) throws InterruptedException {

		Calendar cal = Calendar.getInstance();
		Calendar targetcal = Calendar.getInstance();
		targetcal.setTime(date);

		Integer yearCount = targetcal.get(Calendar.YEAR) - cal.get(Calendar.YEAR);
		Integer monthCount = targetcal.get(Calendar.MONTH) - cal.get(Calendar.MONTH);
		// Integer dayCount = targetcal.get(Calendar.DAY_OF_MONTH) -
		// cal.get(Calendar.DAY_OF_MONTH);

		if (yearCount < 0) {
			for (int i = yearCount; i < 0; i--) {
				calendarInstance.findElement(By.id("prev-year")).click();

			}
		} else {
			for (int i = 0; i < yearCount; i++) {
				calendarInstance.findElement(By.id("next-year")).click();
			}
		}

		Thread.sleep(2000);
		if (monthCount < 0) {
			for (int i = monthCount; i < 0; i--) {
				calendarInstance.findElement(By.id("prev-month")).click();

			}
		} else {
			for (int i = 0; i < monthCount; i++) {
				calendarInstance.findElement(By.id("next-month")).click();

			}
		}
		Thread.sleep(2000);
		String day = Integer.toString(targetcal.get(Calendar.DAY_OF_MONTH)).replace("0", "");
		String daylocator = "day" + day;
		System.out.println(daylocator);

		calendarInstance.findElement(By.id(daylocator)).click();

	}

	public static String getTabDataValue(String labelName) {
		String dataValue = null;
		try {
			dataValue = PropsUtil.getDataPropertyValue(labelName);
			logger.warn("Found the label [" + labelName
					+ "] in data file. Its act's as key. If it has any value in data file then it will go for tab close");
		} catch (Exception er) {
			logger.info(
					"The data value is null or the label is not defined in data file. So no tab closing operation will perform");
		}
		return dataValue;
	}

	public static Float parseAmount(String amount) {

		amount = amount.replace("$", "");
		amount = amount.replace("$", "");
		amount = amount.replace(",", "");
		amount = amount.replace("\n", "");
		amount = amount.replace("*", "");
		amount = amount.replace("N/A", "0");
		amount = amount.replace("₹", "");
		return Float.valueOf(amount);

	}

	public static boolean selectDropDownVisibility(WebDriver d, String accountName, String label) {
		boolean status = false;

		if (d == null) {
			logger.error("could not find webdriver" + d);
		} else if (label == null || label == "") {

			logger.error("label not found" + label);
		} else if (accountName == null) {
			logger.error("could not find the account Name" + accountName);
		} else {
			String settingLOc = "//a[contains(@title,'" + accountName + "')]";
			String loc = "//a[contains(text(),'" + label + "')]";
			try {
				WebElement setting = d.findElement(By.xpath(settingLOc));
				if (setting != null) {
					click(setting);
					Thread.sleep(1000);
					List<WebElement> lst = d.findElements(By.xpath(loc));
					for (int i = 0; i < lst.size(); i++) {
						if (lst.get(i) != null && lst.get(i).isDisplayed()) {
							status = true;
							click(setting);
							break;
						}
					}
				} else {

					logger.error("could not find setting for account" + accountName);

				}
			} catch (Exception er) {
			}

		}
		return status;
	}

	public static boolean selectDropDown(WebDriver d, String accountName, String label) {
		boolean status = false;
		if (d == null) {
			logger.error("could not find webdriver" + d);
		} else if (label == null || label == "") {

			logger.error("label not found" + label);
		} else if (accountName == null) {
			logger.error("could not find the account Name" + accountName);
		} else {
			String settingLOc = "//a[contains(@title,'" + accountName + "')]";
			String loc = "//a[contains(text(),'" + label + "')]";
			try {
				WebElement setting = d.findElement(By.xpath(settingLOc));
				if (setting != null) {
					click(setting);
					waitFor(2);
					List<WebElement> lst = d.findElements(By.xpath(loc));
					for (int i = 0; i < lst.size(); i++) {
						if (lst.get(i).isDisplayed() && lst.get(i) != null) {
							click(lst.get(i));
							status = true;
							break;
						}
					}
				} else {
					logger.error("could not find setting for account" + accountName);

				}
			} catch (Exception er) {
			}

		}
		waitUntilSpinnerWheelIsInvisible();
		return status;
	}

	public static String checkLabelExist(String labelName) {
		String label = null;
		try {
			label = PropsUtil.getDataPropertyValue(labelName);
		} catch (Exception er) {
			logger.info("Cobrand is not having this functionality");
			label = null;
		}
		return label;
	}

	public static boolean SwitchToTab(WebDriver d, String labelName) {
		boolean status = false;
		if (labelName == null || d == null) {
			logger.error("The argument value should not be null");
		} else {
			String tabToSwitch = checkLabelExist(labelName);
			if (tabToSwitch != null) {
				// List lblTitleOfTabToClose =
				// FunctionUtil.splitLabelValueAsList("TitleOfTabToClose", ",");
				String base = d.getWindowHandle();
				if (d.getTitle().contains(tabToSwitch)) {
					status = true;
					logger.info("The Tab is already switched [" + labelName + "]");
					return status;
				} else {
					Set<String> set = null;
					try {
						set = d.getWindowHandles();
					} catch (Exception er) {
						set.add(base);
					}
					if (set.size() != 1) {
						for (String a : set) {
							d.switchTo().window(a);
							System.out.println("Current tab: " + d.getTitle());
							if (d.getTitle().contains(tabToSwitch)) {
								d.switchTo().window(a);
								logger.info("Found the Tab with title [{}]", labelName);
								status = true;
								break;
							}
						}
					} else {
						d.switchTo().window(base);
						logger.info("There is only one tab open in the browser");
					}
				}
			}
		}
		return status;
	}

	public static Float parseAmountExpense(String amount) {

		if (amount.contains("-")) {

			amount = amount.replace("-", "");

		}

		amount = amount.replace("$", "");

		amount = amount.replace(",", "");

		amount = amount.replace("\n", "");

		amount = amount.replace("*", "");

		return Float.valueOf(amount);

	}

	public static boolean sendKeys(WebElement webElement, String value) {
		logger.info("Sending value [{}] to the textBox", value);
		if (webElement == null) {
			logger.error(">> Could not recognize the element on page");
			return false;
		} else if (GenericUtil.isNull(value)) {
			logger.error(">> The value to enter in text box is null");
			return false;
		}
		// if(Config.getAppFlag().equalsIgnoreCase("mobile"))
		if (MobileUtil.isMobileFlag()) {
			logger.info(" Going to send value [" + value + "] to mobile text box");
			webElement.sendKeys(value);
			try {
				TestBase.getDriver().hideKeyboard();

			} catch (Exception er) {
				logger.info("could not hide the keyboard for value[{}] and element [{}]", value, webElement);
				return false;
			}
			return true;
		} else {
			logger.info(" Going to send value [" + value + "] to Web text box");
			webElement.clear();
			webElement.sendKeys(value);
			return true;
		}
	}

	public static String getEnvironmetValue(String key) {
		String keyValue = "no";
		try {
			keyValue = PropsUtil.getDataPropertyValue(key).trim();
		} catch (Exception er) {
			logger.warn(
					"<<<<<<<==taking default value of [" + key + "] as no. Could not find the key name in env file");
		}
		return keyValue;
	}

	/**
	 * @author sswain1
	 * @param d
	 * @param elementLabel
	 * @param pageName
	 * @param frameName
	 * @return
	 */
	public static WebElement findElement(WebDriver driver, By locator, int timeoutSeconds) {
		logger.info("==> Entering findElement method.");
		try {
			FluentWait<WebDriver> wait = new FluentWait<>(driver).withTimeout(timeoutSeconds, TimeUnit.SECONDS)
					.withMessage("Unable to find element ::  " + locator).pollingEvery(500, TimeUnit.MILLISECONDS)
					.ignoring(NoSuchElementException.class);

			WebElement element = wait.until(new Function<WebDriver, WebElement>() {
				public WebElement apply(WebDriver webDriver) {
					WebElement element = null;
					List<WebElement> listOfElements = driver.findElements(locator);

					if (listOfElements != null && listOfElements.size() > 0) {
						for (WebElement webElement : listOfElements) {
							if (webElement.isDisplayed()) {
								logger.info("**************************Element found*****************************");
								element = webElement;
								break;
							}
						}
					}
					return element;
				}
			});
			logger.info("****************** Element Found [{}] **********************" + locator.toString());
			return element;
		} catch (Exception e) {
			logger.error("Could not find the element [{}], returning null", locator.toString());
			return null;
		}

	}

	/**
	 * 
	 * @param d
	 * @param elementName
	 * @param pageName
	 * @param frameName
	 * @param timeoutInSec
	 * @return
	 */
	public static WebElement findElement(WebDriver d, String elementName, String pageName, String frameName,
			int timeoutInSec) {
		logger.info("==> Entering findElement (.....) method ");

		final By by = getByObject(pageName, frameName, elementName);

		logger.info("************ Element to be found with Locator :: " + by.toString());
		WebElement element = findElement(d, by, timeoutInSec);

		if (element != null) {
			logger.info("************ Element found with Locator :: " + by.toString());
			scrollToWebElementMiddle(d, element);

		} else
			EReporter.log(LogStatus.INFO,
					"Could not find the Visible WebElement with elementName = " + elementName + ", pageName = "
							+ pageName + " and frame = " + frameName
							+ " Also If this element is not present in co-brand, then ignore this message ");
		logger.info("<== Exiting findElement(.....) method.");
		return element;
	}

	/**
	 * Utility method to check in a passed list of string if any key is matched.
	 * 
	 * @author ajain5
	 * 
	 * @param listtocheck -List of String to check
	 * @param keyToFind   -key to find in listtocheck
	 * @return true if listtocheck contains keyToFind, false otherwise
	 */
	public boolean isAnyMatchInList(List<String> listtocheck, String keyToFind) {
		return listtocheck.parallelStream().anyMatch(str -> str.trim().equals(keyToFind));
	}

	public static List<String> getIndexedStrings(String[] names) {

		List<String> evenIndexedNames = Arrays.asList(names);
		return evenIndexedNames;
	}

	public static void assertExpectedActualList(List<String> actualValue, String expected, String message) {
		List<String> expectedList = getIndexedStrings(expected.split(","));
		if (!expectedList.equals(actualValue)) {
			Assert.fail(message + ":: List values are not matching Expected :: " + expectedList + " &&&&&& "
					+ " Actual :: " + actualValue);
		}

	}

	public static void assertExpectedActualAmountList(List<String> actualValue, String expected, String message) {
		List<String> expectedList = getIndexedStrings(expected.split("/"));
		if (!expectedList.equals(actualValue)) {
			Assert.fail(message + ":: List values are not matching Expected :: " + expectedList + " &&&&&& "
					+ " Actual :: " + actualValue);
		}
	}

	public static void assertExpectedActualList(List<String> actualValue, List<String> expected, String message) {

		if (!expected.equals(actualValue)) {
			Assert.fail(message + ":: List values are not matching Expected :: " + expected + " &&&&&& " + " Actual :: "
					+ actualValue);
		}

	}

	public static void assertActualList(List<String> actualValue, String expectedValue, String message) {

		for (String actual : actualValue) {
			Assert.assertEquals(actual, expectedValue, message);
		}
	}

	public static void assertActualListContains(List<String> actualValue, String expectedValue, String message) {

		for (String actual : actualValue) {
			Assert.assertTrue(actual.contains(expectedValue), message);
		}
	}

	public void assertIsDisplayed(List<WebElement> actualValue, String message) {

		for (WebElement actual : actualValue) {
			Assert.assertTrue(actual.isDisplayed(), message);
		}
	}

	public static void assertActualListIgnoreCase(List<String> actualValue, String expectedValue, String message) {

		for (String actual : actualValue) {
			Assert.assertEquals(actual.toUpperCase(), expectedValue.toUpperCase(), message);
		}
	}

	public static List<String> getWebElementsValue(List<WebElement> webelements) {
		List<String> values = new ArrayList<>();
		List<WebElement> actualValues = webelements;
		for (WebElement element : actualValues) {
			values.add(element.getText().trim().replaceAll("\\n", " "));
		}
		return values;
	}

	public List<String> getWebElementsValueUperCase(List<WebElement> webelements) {
		List<String> values = new ArrayList<>();
		List<WebElement> actualValues = webelements;
		for (WebElement element : actualValues) {
			values.add(element.getText().trim().replaceAll("\\n", " ").toUpperCase());
		}
		return values;
	}

	public List<String> getWebElementsAttributeValue(List<WebElement> webelements) {
		List<String> values = new ArrayList<>();
		List<WebElement> actualValues = webelements;
		for (WebElement element : actualValues) {
			values.add(element.getAttribute("value").trim().replaceAll("\\n", " "));
		}
		return values;
	}

	public static List<String> getWebElementsAttributeName(List<WebElement> webelements, String attributeName) {
		waitUntilSpinnerWheelIsInvisible();
		List<String> values = new ArrayList<>();
		List<WebElement> actualValues = webelements;
		for (WebElement element : actualValues) {
			values.add(element.getAttribute(attributeName).trim().replaceAll("\\n", " "));
		}
		return values;
	}

	public boolean assertFalse(List<String> actualValue, String expected) {
		boolean elementFresentFalse = true;
		for (String value : actualValue) {
			if (value.equals(expected)) {
				elementFresentFalse = true;
				break;
			} else {
				elementFresentFalse = false;
			}
		}
		return elementFresentFalse;
	}

	public boolean assertContainsFalse(List<String> actualValue, String expected) {
		boolean elementFresentFalse = true;
		for (String value : actualValue) {
			if (value.contains(expected)) {
				elementFresentFalse = true;
				break;
			} else {
				elementFresentFalse = false;
			}
		}
		return elementFresentFalse;
	}

	public boolean assertTrue(List<String> actualValue, String expected) {
		boolean elementFresentFalse = false;
		for (String value : actualValue) {
			if (value.equals(expected)) {
				elementFresentFalse = true;

			} else {
				elementFresentFalse = false;
				break;
			}
		}
		return elementFresentFalse;
	}

	public List<String> revereseList(List<String> listvalue) {
		List<String> reversedValue = new ArrayList<String>();
		for (int i = listvalue.size() - 1; i >= 0; i--) {
			reversedValue.add(listvalue.get(i));
		}
		return reversedValue;
	}

	public List<String> getWebElementsIndexValue(List<WebElement> webelements, int StartIndex) {
		waitForPageToLoad();
		List<String> values = new ArrayList<>();
		List<WebElement> actualValues = webelements;
		for (int i = StartIndex; i < actualValues.size(); i++) {
			values.add(webelements.get(i).getText().trim().replaceAll("\\n", " "));
		}
		return values;
	}

	public void assertActualListIgnoreCase(List<String> actualValue, String[] expectedValue, String message) {

		for (int i = 0; i < actualValue.size(); i++) {
			Assert.assertEquals(actualValue.get(i).toLowerCase(), expectedValue[i].toLowerCase(), message);
		}
	}

	/**
	 * waits for web element to become invisible for given time and fails the test
	 * case if element is displayed after defined time
	 * 
	 * @param=By element locator in the form of By
	 */
	final static public void WaitUntilElementIsInVisible(By element, int counter) {
		WebDriver driver = WebDriverFactory.getDriver();
		try {
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			int i;
			for (i = 1; i < counter; i++) {
				try {
					boolean flag = driver.findElement(element).isDisplayed();
					if (!flag) {
						break;
					} else {
						logger.info("Waiting for element to be Invisible !!! " + i + " seconds " + element);
						waitFor(1);
					}
				} catch (StaleElementReferenceException r) {
					logger.info("Waiting for element to be Invisible !!! " + i + " seconds " + element);
					waitFor(1);
				} catch (NoSuchElementException e) {
					break;
				} catch (Exception e) {
					logger.error("Cannot Verify Whether element is displayed or not due to " + e.getMessage());
				}
			}
			if (i >= counter) {
				logger.error(element + " Element is  displayed after " + i + " seconds");
			}
		} catch (Exception e) {
		} finally {
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		}
	}

	/**
	 * waits for web element to become invisible for given time and fails the test
	 * case if element is displayed after defined time
	 * 
	 * @param=WebElement element locator in the form of By
	 */
	final static public void WaitUntilElementIsInVisible(WebDriver driver, WebElement element, int counter) {
		try {
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			int i;
			for (i = 0; i < counter; i++) {
				try {
					boolean flag = element.isDisplayed();
					if (!flag) {
						break;
					} else {
						logger.info("Waiting for element to be Invisible !!! " + i + " seconds " + element);
						waitFor(1);
					}
				} catch (StaleElementReferenceException r) {
					logger.info("Waiting for element to be Invisible !!! " + i + " seconds " + element);
					waitFor(1);
				} catch (NoSuchElementException e) {
					break;
				} catch (Exception e) {
					logger.error("Cannot Verify Whether element is displayed or not due to " + e.getMessage());
				}
			}
			if (i >= counter) {
				logger.error(element + " Element is  displayed after " + i + " seconds");
			}
		} catch (Exception e) {
		} finally {
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		}
	}

	/**
	 * To check whether element is displayed or not
	 * 
	 * @return=true if element is displayed
	 * @return=false if element is not displayed
	 * @param=By element locator in the form of By
	 */
	final public static boolean isDisplayed(By element, int waitTime) {
		boolean flag = false;
		WebDriver driver = WebDriverFactory.getDriver();
		try {
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			for (int i = 0; i <= waitTime; i++) {
				try {
					flag = driver.findElement(element).isDisplayed();
					if (flag) {
						flag = true;
						break;
					} else {
						Thread.sleep(1000);
					}
				} catch (Exception e) {
					Thread.sleep(1000);
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		}
		return flag;
	}

	public static String getAttribute(By by, String attribute) {
		WebDriver driver = WebDriverFactory.getDriver();
		String attributeValue = "";
		try {
			attributeValue = driver.findElement(by).getAttribute(attribute);
			logger.info(attribute + " attribute's value of element :: " + by + " is " + attributeValue);
		} catch (Exception e) {
			logger.error("Unable to get attribute value due to :: " + e.getMessage());
			return "";
		}
		return attributeValue;
	}

	public static String getAttribute(WebElement element, String attribute) {
		String attributeValue = "";
		try {
			attributeValue = element.getAttribute(attribute);
			logger.info(attribute + " attribute's value of element :: " + element + " is " + attributeValue);
		} catch (Exception e) {
			logger.error("Unable to get attribute value due to :: " + e.getMessage());
			return "";
		}
		return attributeValue;
	}

	/**
	 * wait until element is present in document object model (DOM)
	 * 
	 * @param=By element you want to wait for till that element loads in the DOM
	 * @author bharghav.kongara
	 * @param driver
	 */
	final public static void waitUntilElementIsPresent(By element) {
		WebDriver driver = WebDriverFactory.getDriver();
		try {
			boolean flag = false;
			implicitlyWait(0);
			for (int i = 0; i < counter; i++) {
				try {
					driver.findElement(element);
					flag = true;
					break;
				} catch (NoSuchElementException | StaleElementReferenceException e) {
					logger.info("Waiting for element to be present in DOM " + i + " seconds element:: " + element);
					waitFor(1);
				} catch (Exception e) {
					e.getStackTrace();
					break;
				}
			}
			if (flag) {
			} else {
				logger.error("Element is not present in DOM element:: " + element);
				Assert.fail("Element is not present in DOM element:: " + element);
			}
		} catch (Exception e) {
		} finally {
			implicitlyWait(60);
		}
	}

	final public static void implicitlyWait(int time) {
		WebDriver driver = WebDriverFactory.getDriver();
		try {
			driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * wait for particular amount of time
	 * 
	 * @param=float seconds you want to wait for
	 * @author bharghav.kongara
	 */
	final public static void waitFor(float timeToWait) {
		try {
			Thread.sleep((long) (timeToWait * 1000));
		} catch (InterruptedException e) {
		}
	}

	public static void waitUntilSpinnerWheelIsInvisible() {
		WaitUntilElementIsInVisible(spinnerWheel, 60);
		WaitUntilElementIsInVisible(inlineSpinnerWheelWrap, 60);
		WaitUntilElementIsInVisible(loadingSymbol, 60);
	}

	public static void waitUntilSpinnerWheelIsInvisible(int counter) {
		WaitUntilElementIsInVisible(spinnerWheel, counter);
		WaitUntilElementIsInVisible(inlineSpinnerWheelWrap, counter);
		WaitUntilElementIsInVisible(loadingSymbol, counter);
	}

	/**
	 * To check whether element is displayed or not
	 * 
	 * @return=true if element is displayed
	 * @return=false if element is not displayed
	 * @param=By element locator in the form of By
	 */
	final public static void waitUntilElementIsVisible(By element, int waitTime) {
		WebDriver driver = WebDriverFactory.getDriver();
		boolean flag = false;
		try {
			waitUntilElementIsPresent(element);
			scrollToWebElementMiddle(driver, driver.findElement(element));
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			for (int i = 0; i < waitTime; i++) {
				try {
					flag = driver.findElement(element).isDisplayed();
					if (flag) {
						return;
					} else {
						Thread.sleep(1000);
					}
				} catch (Exception e) {
					Thread.sleep(1000);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		}
	}

	/**
	 * To check whether element is displayed or not
	 * 
	 * @return=true if element is displayed
	 * @return=false if element is not displayed
	 * @param=By element locator in the form of By
	 */
	final public static void waitUntilElementIsPresent(WebDriver d, By element, int waitTime) {
		try {
			d.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			for (int i = 0; i < waitTime; i++) {
				try {
					d.findElement(element);
					return;
				} catch (Exception e) {
					Thread.sleep(1000);
				}
			}
			Assert.fail("Element :: '" + element + "' is not present in the DOM even after waiting for " + waitTime
					+ " seconds");
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			d.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		}
	}

	public static void click(By element) {
		WebDriver driver = WebDriverFactory.getDriver();
		logger.info("Clicking on element [{}]", element);
		try {
			waitUntilElementIsVisible(element, 60);
			scrollToWebElementMiddle(driver, driver.findElement(element));
			driver.findElement(element).click();
		} catch (Exception erp) {
			try {
				((JavascriptExecutor) driver).executeScript("arguments[0].click()", driver.findElement(element));
				// we.click();
			} catch (Exception ex) {
				logger.warn("Exception Thrown while Calling WebElement.click() method.");
				logger.warn("Trying with javascript click");
				// scrollElementIntoView(WebDriverFactory.getDriver(),
				// we, true);
				((JavascriptExecutor) driver).executeScript("arguments[0].click()", driver.findElement(element));
			}
		}
	}

	public static void waitUntilElementIsClickable(By element) {
		WebDriver driver = WebDriverFactory.getDriver();
		try {
			waitUntilElementIsVisible(element, 60);
			for (int i = 0; i < 60; i++) {
				try {
					if (!driver.findElement(element).isEnabled()) {
						waitFor(1);
					} else {
						return;
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		} catch (Exception erp) {

		}
	}

	public static int getElementCount(By value) {
		implicitlyWait(0);
		try {
			WebDriver driver = WebDriverFactory.getDriver();
			return driver.findElements(value).size();
		} catch (Exception e) {
			return 0;
		}
	}

	public static final void assertContains(String expected, String actual, String message) {
		if (!actual.contains(expected)) {
			fail(message + "Expected :: '" + expected + "' & Actual :: '" + actual + "'");
		}
	}

	public static String getText(By locator) {
		WebDriver driver = WebDriverFactory.getDriver();
		try {
			waitUntilElementIsVisible(locator, 45);
			return driver.findElement(locator).getText();
		} catch (Exception | AssertionError e) {
			return "No text is present for given locator :: " + locator;
		}
	}

	public String returnToastMessage() {
		try {
			return getText(toastMessage);
		} catch (Exception e) {
			logger.info("Unable to return toast message due to :: " + e.getMessage());
			return "No toast message is displayed";
		}
	}

	public static void waitUntilToastMessageIsDisappeared() {
		WaitUntilElementIsInVisible(toastMessage, 25);
	}

	public static void waitUntilToastMessageIsDisplayed() {
		waitUntilElementIsVisible(toastMessage, 30);
	}

	public static String duplicateTabAndReturnWindowhandle() {
		WebDriver driver = WebDriverFactory.getDriver();
		Set<String> presentWindowhandles = driver.getWindowHandles();
		String currentURL = driver.getCurrentUrl();
		String link = "window.open('" + currentURL + "','_blank');";
		((JavascriptExecutor) driver).executeScript(link);
		Set<String> currentWindowHandles = driver.getWindowHandles();
		currentWindowHandles.removeAll(presentWindowhandles);
		if (currentWindowHandles.size() == 0) {
			Assert.fail("Unable to open a new tab");
		}
		for (String windowHandle : currentWindowHandles) {
			driver.switchTo().window(windowHandle);
			return windowHandle;
		}
		return "";
	}

	public static void closeAllTheWindowsExceptTheGivenWindow(String window) {
		WebDriver driver = WebDriverFactory.getDriver();
		try {
			Set<String> windowHandles = driver.getWindowHandles();
			for (String presentWindow : windowHandles) {
				if (!presentWindow.equals(window)) {
					driver.switchTo().window(presentWindow);
					driver.close();
				}
			}
		} catch (Exception e) {

		} finally {
			driver.switchTo().window(window);
		}
	}
}
