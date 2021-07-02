/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
package com.omni.pfm.pages;

import static com.omni.pfm.utility.SeleniumUtil.click;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Reporter;

import com.omni.pfm.Exceptions.LabelNotFoundException;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.config.Config;
import com.omni.pfm.listeners.EReporter;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.FunctionUtil;
import com.omni.pfm.utility.GenericUtil;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;
import com.relevantcodes.extentreports.LogStatus;

/**
 * @author Suhaib
 *
 */
public class AccountGroupsPage {
    private static final Logger logger = LoggerFactory.getLogger(AccountGroupsPage.class);
    private static String chkbxExpression = ".//ancestor::td/preceding-sibling::td/input";
    // private static final String page = "AccountGroupsPage";

    /*
     * public static boolean verifyDefaultDataWithoutData(WebDriver d, String
     * defaultTextObjectLabel, String defaultTextStringLabel) { logger.info(
     * "==> Entering AccountGroupsPage.verifyDefaultDataWithoutData method");
     * WebElement elementToVerify = SeleniumUtil.getWebElement(d,
     * defaultTextObjectLabel, page, null); boolean result =
     * FunctionUtil.verifyDefaultData(d, elementToVerify, page,
     * defaultTextStringLabel); logger.info(
     * "<== Exiting AccountGroupsPage.verifyDefaultDataWithoutData method");
     * return result; }
     */
    /**
     * Create account groups
     * 
     * @param d
     * @param pageName
     * @param frameName
     * @param createGroupLinkLabel
     *            - Create group label when no groups are present
     * @param createNewGroupLinkLabel
     *            - Create group label when one or more groups are present
     * @param grpNameObjectLabel
     *            - Label of the group name text box element defined in the page xml
     * @param grpNameToAddLabel
     *            - Name of the group defined in the property file
     * @param accountNamesToAddLabel
     *            - Account Names that need to be added. Defined in the property file
     * @param accntNameChkbxLabel
     *            - xpath expression for checkbox for corresponding account names. Defined in the property file. Default value is
     *            <b>".//ancestor::td/preceding-sibling::td/input"</b>
     * @param createGroupBtnLabel
     *            - Create Button label defined in page xml
     * @param successMsgObjectLabel
     *            - Success Object label defined in page xml
     * @param successMsgLabel
     *            - Success Message defined in the property file
     * @param verifyGroupPageLabel
     *            - Name of the page where Group needs to be verified.
     * @param groupDDFrame
     *            - Frame name if the Group DropDown in within a frame
     * @param groupDDLabel
     *            - Account Groups DropDown label that is present with the 'verifyGroupPageLabel' page
     * @return
     */
    public static boolean createAccountGroup(WebDriver d, String pageName, String frameName, String createGroupLinkLabel,
	    String createNewGroupLinkLabel, String grpNameObjectLabel, String grpNameToAddLabel, String accountNamesToAddLabel,
	    String accntNameChkbxLabel, String createGroupBtnLabel, String successMsgObjectLabel, String successMsgLabel,
	    String verifyGroupPageLabel, String groupDDFrame, String groupDDLabel) {
	logger.info("==> Entering AccountGroupsPage.createAccountGroup method...");
	boolean status = false;
	// check all parameters are not null
	if (GenericUtil.checkParamsNotNull(d, pageName, createGroupLinkLabel, createNewGroupLinkLabel, grpNameObjectLabel,
		grpNameToAddLabel, accountNamesToAddLabel, createGroupBtnLabel, successMsgObjectLabel, successMsgLabel)) {
	    PageParser.forceNavigate(pageName, d);
	    TestBase.imageprocess.captureScreenshot(d, "WithoutGroup", TestBase.screenShotEnabledFlag, false, 5000);
	    SeleniumUtil.waitForElement("citi", 4000);
	    
	    // TODO Auto-generated catch block
	    WebElement createGrpLink = SeleniumUtil.getVisibileWebElement(d, createGroupLinkLabel, pageName, frameName);
	    if (createGrpLink == null) {
		createGrpLink = SeleniumUtil.getVisibileWebElement(d, createNewGroupLinkLabel, pageName, frameName);
	    }
	    if (createGrpLink != null) {
		SeleniumUtil.click(createGrpLink);// .click();
		SeleniumUtil.waitForElement("citi", 3000);
		// TODO Auto-generated catch block
		String grpName = PropsUtil.getDataPropertyValue(grpNameToAddLabel);
		WebElement grpNameEl = SeleniumUtil.getWebElement(d, grpNameObjectLabel, pageName, frameName);
		if (grpNameEl != null) {
		    grpNameEl.clear();
		    grpNameEl.sendKeys(grpName);
		    String[] acntsToAdd = FunctionUtil.splitLabelValue(accountNamesToAddLabel, ";");
		    if (acntsToAdd != null) {
			for (String s : acntsToAdd) {
			    logger.info("Selecting [{}] account", s);
			    WebElement acntLink = null;
			    try {
				acntLink = d.findElement(By.partialLinkText(s.trim()));
			    } catch (NoSuchElementException ex) {
				logger.error("Could not find the [{}] account. Group will be created without this account.", s);
				logger.error("EXCEPTION is : {}", ex);
				EReporter.log(LogStatus.INFO, "Could not find the [" + s + "] account. Creating group skipping this account.");
				Reporter.log("Could not find the [" + s + "] account. Creating group skipping this account.");
			    }
			    if (acntLink != null) {
				String chkbxExp = null;
				try {
				    chkbxExp = PropsUtil.getDataPropertyValue(accntNameChkbxLabel);
				} catch (LabelNotFoundException e) {
				}
				if (!GenericUtil.isNull(chkbxExp)) {
				    chkbxExpression = chkbxExp;
				}
				WebElement chkbx = acntLink.findElement(By.xpath(chkbxExpression));
				if (chkbx != null) {
				    click(chkbx);
				} else
				    logger.error("Could not find the checkbox");
			    } else
				logger.error("Could not find the Account [{}].", s);
			} // for loops ends here
			SeleniumUtil.waitForElement("citi", 2000);
			// TODO Auto-generated catch block
			SeleniumUtil.getWebElement(d, createGroupBtnLabel, pageName, frameName).click();
			SeleniumUtil.waitForElement("citi", 2000);
			// TODO Auto-generated catch block
			WebElement successObject = SeleniumUtil.findElementWithText(d, null,
				PropsUtil.getDataPropertyValue(successMsgLabel), 4);
			if (successObject != null) {
			    status = true;
			} else {
			    status = false;
			    logger.error("Could not find the success message with text = {}",
				    PropsUtil.getDataPropertyValue(successMsgLabel));
			    EReporter.log(LogStatus.INFO, "Could not find the success message with text = " + PropsUtil.getDataPropertyValue(successMsgLabel));
			    Reporter.log(
				    "Could not find the success message with text = " + PropsUtil.getDataPropertyValue(successMsgLabel));
			}
			// status = FunctionUtil.verifyDefaultData(d, pageName, frameName, successMsgObjectLabel, successMsgLabel);
			if (status) {
			    /*  if (verifyGroupPageLabel != null && groupDDLabel != null) {
			    PageParser.forceNavigate(verifyGroupPageLabel, d);
			    if (!GenericUtil.isNull(groupDDFrame)) {
			        PageParser.loadFrame(d, verifyGroupPageLabel, groupDDFrame);
			    }
			    status = SeleniumUtil.selectCustomDDValue(d, groupDDLabel, grpName, verifyGroupPageLabel, groupDDFrame);
			    // screenshot code here
			    d.switchTo().defaultContent();
			    }*/
			}
		    } else
			logger.error("Could not find the account names to add from the property file...");
		} else
		    logger.error("Could not find the Group Name text box...");
	    } else
		logger.error("Could not find the Create Group Link");
	} else {
	    logger.error("One or more parameters are null or empty...");
	    EReporter.log(LogStatus.INFO, "One or more parameters are null or empty...");	
	    Reporter.log("One or more parameters are null or empty...");
	}
	logger.info("<== Exiting AccountGroupsPage.createAccountGroup method...");
	return status;
    }

    /**
     * Delete an account group
     * 
     * @param d
     *            - WebDriver instance
     * @param pageName
     *            - Account Groups page name
     * @param deleteLinkLabel
     *            - Delete link label
     * @param delContLabel
     *            - Delete account confirmation button label
     * @param successMsgObject
     *            - Success Message object
     * @param succMsgTextLabel
     *            - Success message text
     * @return Returns true if account is deleted and expected success message equals actual success msg otherwise false.
     * @throws InterruptedException
     */
    public static boolean deleteGroup(WebDriver d, String pageName, String deleteLinkLabel, String delContLabel, String successMsgObject,
	    String succMsgTextLabel) throws InterruptedException {
	logger.info("==> Entering AccountGroupsPage.deleteGroup method..");
	boolean status = false;
	if (GenericUtil.checkParamsNotNull(d, pageName, deleteLinkLabel, delContLabel, successMsgObject, succMsgTextLabel)) {
	    PageParser.forceNavigate(pageName, d);
	    SeleniumUtil.waitForElement("citi", 3000);
	    WebElement dltLink = SeleniumUtil.getVisibileWebElement(d, deleteLinkLabel, pageName, null);
	    if (dltLink != null) {
		click(dltLink);
		SeleniumUtil.waitForElement("citi", 2000);
		dltLink = SeleniumUtil.getVisibileWebElement(d, delContLabel, pageName, null);
		if (dltLink != null) {
		    click(dltLink);
		    // status = FunctionUtil.verifyDefaultData(d, pageName, null, successMsgObject, succMsgTextLabel);
		    SeleniumUtil.waitForElement("citi", 2000);
		    status = SeleniumUtil.findElementWithText(d, null, PropsUtil.getDataPropertyValue(succMsgTextLabel), 5) != null ? true
			    : false;
		} else {
		    logger.error("Could not find the delete continue button..");
		}
	    } else
		logger.error("Could not find the Delete Link.");
	} else
	    logger.error("One or more parameters are null or empty.. Check and try again");
	logger.info("<== Entering AccountGroupsPage.deleteGroup method..");
	return status;
    }

    /**
     * Validate the error messages in the Account Groups Page
     * 
     * @param d
     *            WebDriver instance
     * @param pageName
     *            - Page name
     * @param frameName
     *            - Frame name if page is loaded within a frame
     * @param createNewGrpLinkLabel
     *            - Create new Account group label
     * @param grpNameTextBx
     *            - Group name text box label
     * @param accountNamesToAddLabel
     *            - Accounts to add label
     * @param submitBtnLabel
     *            - submit button label
     * @param noGrpNameErrObject
     *            - Error object label when no name is given
     * @param noGrpNameErrMsg
     *            - Error message label when no name is given
     * @param existingGrpNameLabel
     *            - Group name label to be given
     * @param existingGrpNameErrObject
     *            - Error message when existing group name is given
     * @param existingGrpNameErrMsg
     *            - Error object label when existing group name is given
     * @return
     */
    public static boolean validateErrorMessages(WebDriver d, String pageName, String frameName, String createNewGrpLinkLabel,
	    String grpNameTextBx, String accountNamesToAddLabel, String submitBtnLabel, String noGrpNameErrObject, String noGrpNameErrMsg,
	    String existingGrpNameLabel, String existingGrpNameErrObject, String existingGrpNameErrMsg) {
	logger.info("==> Entering AccountGroupsPage.validateErrorMessgaes method..");
	boolean status = false;
	if (GenericUtil.checkParamsNotNull(d, pageName, createNewGrpLinkLabel, grpNameTextBx, submitBtnLabel, noGrpNameErrObject,
		noGrpNameErrMsg, existingGrpNameErrObject, existingGrpNameErrMsg)) {
	    PageParser.navigateToPage(pageName, d);
	    // Added by mohit for CITI
	    SeleniumUtil.waitForElementVisible(d, createNewGrpLinkLabel, pageName, frameName);
	    // END
	    status = createAccountGroup(d, pageName, frameName, "dumm", createNewGrpLinkLabel, grpNameTextBx, existingGrpNameLabel,
		    accountNamesToAddLabel, null, submitBtnLabel, existingGrpNameErrObject, existingGrpNameErrMsg, null, null, null);
	    if (!status) {
	    EReporter.log(LogStatus.INFO, "Actual and Expected text are not equal when already existing group name is provided");	
		Reporter.log("Actual and Expected text are not equal when already existing group name is provided");
		logger.error("Actual and Expected text are not equal when already existing group name is provided");
	    }
	    // SeleniumUtil.getWebElement(d, createNewGrpLinkLabel, pageName,
	    // frameName).click();
	    if (SeleniumUtil.findElementWithText(d, "a", "Create New Account Group", 4) != null)
		SeleniumUtil.click(SeleniumUtil.findElementWithText(d, "a", "Create New Account Group", 4));
	    SeleniumUtil.getWebElement(d, submitBtnLabel, pageName, frameName).click();
	    WebElement errEl = SeleniumUtil.waitUntilPresenceOfElement(d, noGrpNameErrObject, pageName, frameName);
	    status &= FunctionUtil.verifyText(d, errEl, noGrpNameErrMsg);
	    TestBase.imageprocess.captureScreenshot(d, "accGroupPageErrorMessage", TestBase.screenShotEnabledFlag, false, 5000);
	    if (!status) {
	    	EReporter.log(LogStatus.INFO, "Actual and Expected text are not equal when no group name is provided");
		Reporter.log("Actual and Expected text are not equal when no group name is provided");
		logger.error("Actual and Expected text are not equal when no group name is provided");
	    }
	    logger.info("Moving back to Account Groups Page..");
	    PageParser.forceNavigate(pageName, d);
	} else
	    logger.error("One or more parameters are null or empty..");
	logger.info("<== Exiting AccountGroupsPage.validateErrorMessgaes method..");
	return status;
    }

    /**
     * Verify after clicking on help, Help Finapp is loaded
     * 
     * @param d
     *            - WebDriver instance
     * @param pageName
     *            - Account Groups page name as defined in the xml.
     * @param frameName
     *            - Frame name.
     * @param ddLabel
     *            - Custom DropDown Label.
     * @param ddHelpItemLabel
     *            - Help Option label in the DD.
     * @param helpHeaderObjectLabel
     *            - Header element label in the Help finapp as defined in the page xml.
     * @param textToVerifyLabel
     *            - Text to verify label as defined in the data properties.
     * @return True if help finapp is loaded and text verification is successful, otherwise false.
     */
    public static boolean verifyHelpFinapp(WebDriver d, String pageName, String frameName, String ddLabel, String ddHelpItemLabel,
	    String helpHeaderObjectLabel, String textToVerifyLabel) {
	logger.info("==> Entering AccountGroupsPage.verifyHelpFinapp method..");
	boolean status = false;
	if (GenericUtil.checkParamsNotNull(d, pageName, ddLabel, ddHelpItemLabel, helpHeaderObjectLabel, textToVerifyLabel)) {
	    PageParser.forceNavigate(pageName, d);
	    SeleniumUtil.clickFinappDDItem(d, pageName, frameName, ddLabel, ddHelpItemLabel);
	    WebElement el = SeleniumUtil.getVisibileWebElement(d, helpHeaderObjectLabel, pageName, frameName);
	    status = FunctionUtil.verifyText(d, el, textToVerifyLabel);
	    // removing the help finapp
	    SeleniumUtil.getVisibileWebElement(d, "RemoveHelpObj", pageName, null).click();
	    SeleniumUtil.getVisibileWebElement(d, "RemoveHlp", pageName, null).click();
	} else
	    logger.error("One or more parameters is null or empty.");
	logger.info("<== Exiting AccountGroupsPage.verifyHelpFinapp method..");
	return status;
    }

    /**
     * Check expand and collapse is working for groups
     * 
     * @param d
     *            - WebDriver instance
     * @param pageName
     *            - Account Groups page name as defined in the page xml.
     * @param frameName
     *            - Frame name
     * @param collExpandGroup1
     *            - Collpase and expand element label for group1 as defined in the page xml.
     * @param collExpandGroup2
     *            - - Collpase and expand element label for group2 as defined in the page xml.
     * @return
     */
    public static boolean expandCollapseGroups(WebDriver d, String pageName, String frameName, String collExpandGroup1,
	    String collExpandGroup2) {
	logger.info("==> Entering AccountGroupsPage.expandCollapseGroups method");
	boolean status = false;
	if (GenericUtil.checkParamsNotNull(d, pageName, collExpandGroup1, collExpandGroup2)) {
	    PageParser.forceNavigate(pageName, d);
	    WebElement collEl1 = SeleniumUtil.getWebElement(d, collExpandGroup1, pageName, frameName);
	    WebElement collEl2 = SeleniumUtil.getWebElement(d, collExpandGroup2, pageName, frameName);
	    if (collEl1 == null) {
		logger.error("Could not find the element to click - {}", collExpandGroup1);
	    } else {
		collEl1.click();
		collEl1.click();
		status = true;
	    }
	    if (collEl2 == null) {
		logger.error("Could not find the element to click - {}", collExpandGroup2);
	    } else {
		collEl2.click();
		collEl2.click();
		status = true;
	    }
	} else
	    logger.error("One or more params are null or empty");
	logger.info("<== Exiting AccountGroupsPage.expandCollapseGroups method");
	return status;
    }

    /**
     * View the details of the account after clicking on the account name link in account groups page
     * 
     * @param d
     *            - WebDriver instance
     * @param pageName
     *            - Account Group page name as defined in the page xml.
     * @param frameName
     *            - Frame name
     * @param accountName
     *            - Account Name label to be clicked as defined in the data property file.
     * @param targetPageName
     *            - Name of the page where it would land after clicking on the account name(defined in the page xml).
     * @param targetFrameName
     *            - Frame name if it is contained inside a frame.
     * @param transactionSpanLabel
     *            - Transaction span label defined in the page xml under <i><b>targetPageName</b></i> and <i><b>targetFameName</b></i>
     * @param detailSpanLabel
     *            - Details span label defined in the page xml under <i><b>targetPageName</b></i> and <i><b>targetFameName</b></i>
     * @return True if both transaction and details span elements are found, else false.
     */
    public static boolean viewAccountDetails(WebDriver d, String pageName, String frameName, String accountName, String targetPageName,
	    String targetFrameName, String transactionSpanLabel, String detailSpanLabel) {
	logger.info("==> Entering AccountGroupsPage.viewAccountDetails method");
	boolean status = false;
	if (GenericUtil.checkParamsNotNull(d, pageName, accountName, targetPageName, transactionSpanLabel, detailSpanLabel)) {
	    PageParser.forceNavigate(pageName, d);
	    if (!GenericUtil.isNull(frameName)) {
		PageParser.loadFrame(d, pageName, frameName);
	    }
	    String acnt = PropsUtil.getDataPropertyValue(accountName);
	    WebElement account = null;
	    try {
		account = d.findElement(By.partialLinkText(acnt));
	    } catch (NoSuchElementException ex) {
		logger.error("Exception while trying to find the account : {}", ex);
	    }
	    if (account != null) {
		account.click();
		WebElement ele = SeleniumUtil.getWebElement(d, transactionSpanLabel, targetPageName, targetFrameName);
		if (ele != null) {
		    logger.info("Text from the [{}] is [{}]", transactionSpanLabel, ele.getText());
		    status = ele.getText().contains("Transaction");
		} else {
		    logger.error("Could not locate the element with label - {}", transactionSpanLabel);
		    EReporter.log(LogStatus.INFO, "Could not locate the element with label - " + transactionSpanLabel);	
		    Reporter.log("Could not locate the element with label - " + transactionSpanLabel);
		}
		ele = SeleniumUtil.getWebElement(d, detailSpanLabel, targetPageName, targetFrameName);
		if (ele != null) {
		    logger.info("Text from the [{}] is [{}]", detailSpanLabel, ele.getText());
		    status &= ele.getText().contains("Detail");
		} else {
		    logger.error("Could not locate the element with label - {}", detailSpanLabel);
		    EReporter.log(LogStatus.INFO, "Could not locate the element with label - "  + detailSpanLabel);	
		    Reporter.log("Could not locate the element with label - " + detailSpanLabel);
		}
	    } else {
		logger.error("Could not find the account - {}", acnt);
		EReporter.log(LogStatus.INFO, "Could not find the account - " + acnt);
		Reporter.log("Could not find the account - " + acnt);
	    }
	} else {
	    logger.error("One or more params is null or empty");
	    EReporter.log(LogStatus.INFO, "One or more params is null or empty");
	    Reporter.log("One or more params is null or empty");
	}
	logger.info("==> Exiting AccountGroupsPage.viewAccountDetails method");
	return status;
    }

    /**
     * Edit an account in group
     * 
     * @param d
     *            - WebDriver instance
     * @param pageName
     *            - Account Group page name as defined in the page xml.
     * @param frameName
     *            - Frame name
     * @param editLinkLabel
     *            - Edit link label as defined in the page xml.
     * @param updateBtnLabel
     *            - Update button label as defined in the page xml.
     * @param editSccuessMsgObjectLabel
     *            - Success message element label as defined in the page xml.
     * @param editSuccessMsgTxtLabel
     *            - Success message text label as defined in the data properties.
     * @return True if the edit is successful and success message from the page matches as in the property file else false.
     * @throws InterruptedException
     */
    public static boolean editAccountGroup(WebDriver d, String pageName, String frameName, String editLinkLabel, String updateBtnLabel,
	    String editSccuessMsgObjectLabel, String editSuccessMsgTxtLabel) throws InterruptedException {
	logger.info("==>Entering AccountGroupsPage.editAccountGroup method");
	boolean status = false, flag = true;
	if (GenericUtil.checkParamsNotNull(d, pageName, editLinkLabel, updateBtnLabel, editSccuessMsgObjectLabel, editSuccessMsgTxtLabel)) {
	    PageParser.forceNavigate(pageName, d);
	    SeleniumUtil.waitForElement("citi", 4000);
	    // added by Mohit
	    SeleniumUtil.waitForElementVisible(d, editLinkLabel, pageName, frameName);
	    String[] accountNameToSelect = FunctionUtil.splitLabelValue("AcntGrp.acntsToAdd1", ";");
	    for (int i = 0; i < 2; i++) {
		SeleniumUtil.click(SeleniumUtil.getWebElement(d, editLinkLabel, pageName, frameName), "The edit button is not clicked");
		// Added by Mohit
		SeleniumUtil.waitForElement(null, 3000);
		// TODO Auto-generated catch block
		SeleniumUtil.waitForElementVisible(d, By.partialLinkText(accountNameToSelect[0]), 6);
		WebElement dagBankAcntLink = d.findElement(By.partialLinkText(accountNameToSelect[0]));
		try {
		    Thread.sleep(3000);
		} catch (InterruptedException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		WebElement chkbx = dagBankAcntLink.findElement(By.xpath(chkbxExpression));
		if (chkbx != null) {
		    SeleniumUtil.click(chkbx);
		    SeleniumUtil.waitForElementVisible(d, updateBtnLabel, pageName, frameName);
		    SeleniumUtil.click(SeleniumUtil.getVisibileWebElement(d, updateBtnLabel, pageName, frameName));
		    SeleniumUtil.waitForElementVisible(d, editSccuessMsgObjectLabel, pageName, frameName);
		    WebElement sucsObj = SeleniumUtil.getWebElement(d, editSccuessMsgObjectLabel, pageName, frameName);
		    flag &= FunctionUtil.verifyText(d, sucsObj, editSuccessMsgTxtLabel);
		} else {
		    logger.error("Could not find the checkbox with xpath exp as : {}", chkbxExpression);
		}
	    }
	    status = flag;
	} else
	    logger.error("One or more params are null or empty.");
	logger.info("<==Exiting AccountGroupsPage.editAccountGroup method");
	return status;
    }

    /**
     * Validate the elements in delete account group page
     * 
     * @param d
     *            WebDriver
     * @param pageName
     *            - Account Group page name
     * @param frameName
     *            - Frame name
     * @param deleteLinkLabel
     *            - delete link label defined in page xml.
     * @param deleteConfirmObjectLabel
     *            - Delete confirmation message object label defined in page xml.
     * @param dltCnfrmMsgLabel
     *            - Delete confirmation message text label defined in data properties.
     * @param doNotDltLinkLabel
     *            - Do Not Delete Group link label defined in page xml.
     * @param dltContBtnLabel
     *            - Continue button link label defined in page xml.
     * @return True if all the elements are found and text matches, else false
     */
    public static boolean deleteGroupValidation(WebDriver d, String pageName, String frameName, String deleteLinkLabel,
	    String deleteConfirmObjectLabel, String dltCnfrmMsgLabel, String doNotDltLinkLabel, String dltContBtnLabel) {
	logger.info("==> Entering AccountGroupsPage.deleteGroupValidation method.");
	boolean status = false;
	if (GenericUtil.checkParamsNotNull(d, pageName, deleteLinkLabel, deleteConfirmObjectLabel, dltCnfrmMsgLabel, doNotDltLinkLabel,
		dltContBtnLabel)) {
	    PageParser.forceNavigate(pageName, d);
	    SeleniumUtil.click(SeleniumUtil.getVisibileWebElement(d, deleteLinkLabel, pageName, frameName),
		    "Could not click on delete link.");
	    WebElement confrmMsgEl = SeleniumUtil.getVisibileWebElement(d, deleteConfirmObjectLabel, pageName, frameName);
	    if (confrmMsgEl != null) {
		status = FunctionUtil.verifyText(d, confrmMsgEl, dltCnfrmMsgLabel);
		if (!status) {
		    logger.error("Delete confirmation message did not match...");
		}
	    } else
		logger.error("Could not find the Delete Confirmation message element by label - [{}]", deleteConfirmObjectLabel);
	    WebElement dnd = SeleniumUtil.getVisibileWebElement(d, doNotDltLinkLabel, pageName, frameName);
	    if (dnd == null) {
		status = false;
		logger.error("Could not find the link 'Do Not Delete Group' by label - [{}]", doNotDltLinkLabel);
	    } else {
		logger.info("Successfully found the 'Do Not Delete Group' link");
		EReporter.log(LogStatus.INFO, "Successfully found the 'Do Not Delete Group' link");
		Reporter.log("Successfully found the 'Do Not Delete Group' link");
	    }
	    if (SeleniumUtil.getWebElement(d, dltContBtnLabel, pageName, frameName) == null) {
		status = false;
		logger.error("Could not find the continue button by label - [{}]", dltContBtnLabel);
	    } else {
		logger.info("Successfully found the continue button");
		EReporter.log(LogStatus.INFO, "Successfully found the continue button");
		Reporter.log("Successfully found the continue button");
	    }
	} else
	    logger.error("One or more params is null or empty");
	d.navigate().back();
	logger.info("==> Exiting AccountGroupsPage.deleteGroupValidation method.");
	return status;
    }

 
}
