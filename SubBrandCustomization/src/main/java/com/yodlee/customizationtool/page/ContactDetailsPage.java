/**
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 *
 * @author Rajeev Anantharaman Iyer
 */
package com.yodlee.customizationtool.page;

import com.yodlee.customizationtool.constants.Constants;
import com.yodlee.customizationtool.util.GenericUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.util.List;

public class ContactDetailsPage extends Page {

    private Logger log = LoggerFactory.getLogger(ContactDetailsPage.class);

    public String pageName = "CONTACT_DETAILS_PAGE";

    public ContactDetailsPage(WebDriver driver) {
        super(driver);
    }

    public WebElement headerLabel() {
        return getWebElement(pageName, "contactDetailsPageLbl");
    }

    public WebElement stepHeaderContactDetailsLabel() {
        return getWebElement(pageName, "cdpStepHeader");
    }

    public List<WebElement> stepHeaderLabels() {
        return getElementsFromPage(pageName, "cdpStepHeader");
    }

    public WebElement backToPreviousStepButton() { return getWebElement(pageName, "backToPreviousStepBtn"); }

    public WebElement backToSubBrandListLink() { return getWebElement(pageName, "backToSubBrandsListLnk"); }

    public WebElement contactOneFirstNameTextBox() { return getWebElement(pageName, "contactOneFirstNameTxtBx"); }

    public WebElement contactOneFirstNameErrorText() { return getWebElement(pageName, "contactOneFirstNameErrorText"); }

    public WebElement contactOneLastNameTextBox() { return getWebElement(pageName, "contactOneLastNameTxtBx"); }

    public WebElement contactOneLastNameErrorText() { return getWebElement(pageName, "contactOneLastNameErrorText"); }

    public WebElement contactOneContactNumberTextBox() { return getWebElement(pageName, "contactOneContactNumberTxtBx"); }

    public WebElement contactOneContactNumberErrorText() { return getWebElement(pageName, "contactOneContactNumberErrorText"); }

    public WebElement contactOneEmailIdTextBox() { return getWebElement(pageName, "contactOneEmailIdTxtBx"); }

    public WebElement contactOneEmailIdErrorText() { return getWebElement(pageName, "contactOneEmailIdErrorText"); }

    public WebElement contactOneUserIdTextBox() { return getWebElement(pageName, "contactOneUserIdTxtBx"); }

    public WebElement contactOneUserIdErrorText() { return getWebElement(pageName, "contactOneUserIdErrorText"); }

    public WebElement addAnotherContactBtn() { return getWebElement(pageName, "addAnotherContactBtn"); }

    public WebElement addAnotherContactEnableDisableStatusBtn() { return getWebElement(pageName, "addAnotherContactEnableDisabledStatusBtn"); }

    public WebElement removeContactBtn() { return getWebElement(pageName, "deleteContactBtn"); }

    public WebElement contactTwoFirstNameTextBox() { return getWebElement(pageName, "contactTwoFirstNameTxtBx"); }

    public WebElement contactTwoFirstNameErrorText() { return getWebElement(pageName, "contactTwoFirstNameErrorText"); }

    public WebElement contactTwoLastNameTextBox() { return getWebElement(pageName, "contactTwoLastNameTxtBx"); }

    public WebElement contactTwoLastNameErrorText() { return getWebElement(pageName, "contactTwoLastNameErrorText"); }

    public WebElement contactTwoContactNumberTextBox() { return getWebElement(pageName, "contactTwoContactNumberTxtBx"); }

    public WebElement contactTwoContactNumberErrorText() { return getWebElement(pageName, "contactTwoContactNumberErrorText"); }

    public WebElement contactTwoEmailIdTextBox() { return getWebElement(pageName, "contactTwoEmailIdTxtBx"); }

    public WebElement contactTwoEmailIdErrorText() { return getWebElement(pageName, "contactTwoEmailIdErrorText"); }

    public WebElement contactTwoUserIdTextBox() { return getWebElement(pageName, "contactTwoUserIdTxtBx"); }

    public WebElement contactTwoUserIdErrorText() { return getWebElement(pageName, "contactTwoUserIdErrorText"); }

    public WebElement saveAndContinueButton() { return getWebElement(pageName, "saveAndContinueBtn"); }

    public void enterContactDetails(String contactOneFN, String contactOneLN,
                                    String contactOneEmail, String contactOneContactNum,
                                    String contactOneUserId, String contactTwoFN,
                                    String contactTwoLN, String contactTwoEmail,
                                    String contactTwoContactNum, String contactTwoUserId,
                                    String customerType, boolean enableMultipleContact,
                                    boolean addAnotherContact) throws InterruptedException {

        if(!headerLabel().getText().contains("Edit")) {

            if(enableMultipleContact){

                clearText(contactOneFirstNameTextBox());
                contactOneFirstNameTextBox().sendKeys(contactOneFN);
                Thread.sleep(1000);
                clearText(contactOneLastNameTextBox());
                contactOneLastNameTextBox().sendKeys(contactOneLN);
                Thread.sleep(1000);
                clearText(contactOneContactNumberTextBox());
                contactOneContactNumberTextBox().sendKeys(contactOneContactNum);
                Thread.sleep(1000);
                clearText(contactOneEmailIdTextBox());
                contactOneEmailIdTextBox().sendKeys(contactOneEmail);
                Thread.sleep(1000);
                if (customerType.equalsIgnoreCase(Constants.CUSTOMER_TYPE_SUB_BRAND)) {
                    clearText(contactOneUserIdTextBox());
                    contactOneUserIdTextBox().sendKeys(contactOneUserId);
                }

                if (addAnotherContactBtn().isEnabled() && addAnotherContactBtn().isDisplayed())
                    addAnotherContactBtn().click();

                clearText(contactTwoFirstNameTextBox());
                contactTwoFirstNameTextBox().sendKeys(contactTwoFN);
                Thread.sleep(1000);
                clearText(contactTwoLastNameTextBox());
                contactTwoLastNameTextBox().sendKeys(contactTwoLN);
                Thread.sleep(1000);
                clearText(contactTwoContactNumberTextBox());
                contactTwoContactNumberTextBox().sendKeys(contactTwoContactNum);
                Thread.sleep(1000);
                clearText(contactTwoEmailIdTextBox());
                contactTwoEmailIdTextBox().sendKeys(contactTwoEmail);
                Thread.sleep(1000);
                if (customerType.equalsIgnoreCase(Constants.CUSTOMER_TYPE_SUB_BRAND)) {
                        clearText(contactTwoUserIdTextBox());
                        contactTwoUserIdTextBox().sendKeys(contactTwoUserId);
                }
            }else{
                clearText(contactOneFirstNameTextBox());
                contactOneFirstNameTextBox().sendKeys(contactOneFN);
                Thread.sleep(1000);
                clearText(contactOneLastNameTextBox());
                contactOneLastNameTextBox().sendKeys(contactOneLN);
                Thread.sleep(1000);
                clearText(contactOneContactNumberTextBox());
                contactOneContactNumberTextBox().sendKeys(contactOneContactNum);
                Thread.sleep(1000);
                clearText(contactOneEmailIdTextBox());
                contactOneEmailIdTextBox().sendKeys(contactOneEmail);
                Thread.sleep(1000);
                if (customerType.equalsIgnoreCase(Constants.CUSTOMER_TYPE_SUB_BRAND)) {
                    clearText(contactOneUserIdTextBox());
                    contactOneUserIdTextBox().sendKeys(contactOneUserId);
                }
            }

        }else if(headerLabel().getText().contains("Edit")){

            if(addAnotherContact){

                try {
                    if(removeContactBtn().isDisplayed() && removeContactBtn().isEnabled())
                        removeContactBtn().click();
                }catch (Exception e) {
                    log.error("Edit Sub-Brand: Contact Details Page: Remove Contact Button Not Found. Only One Contact Is Added.");
                }

                if (addAnotherContactBtn().isEnabled() && addAnotherContactBtn().isDisplayed())
                    addAnotherContactBtn().click();

                clearText(contactTwoFirstNameTextBox());
                contactTwoFirstNameTextBox().sendKeys(contactTwoFN);
                Thread.sleep(1000);
                clearText(contactTwoLastNameTextBox());
                contactTwoLastNameTextBox().sendKeys(contactTwoLN);
                Thread.sleep(1000);
                clearText(contactTwoContactNumberTextBox());
                contactTwoContactNumberTextBox().sendKeys(contactTwoContactNum);
                Thread.sleep(1000);
                clearText(contactTwoEmailIdTextBox());
                contactTwoEmailIdTextBox().sendKeys(contactTwoEmail);
                Thread.sleep(1000);
                if (customerType.equalsIgnoreCase(Constants.CUSTOMER_TYPE_SUB_BRAND)) {
                    if(contactOneUserIdTextBox().isEnabled() && contactOneUserIdTextBox().getText().equalsIgnoreCase("")) {
                        clearText(contactOneUserIdTextBox());
                        contactOneUserIdTextBox().sendKeys(contactOneUserId);
                    }
                    if(contactTwoUserIdTextBox().isEnabled()) {
                        clearText(contactTwoUserIdTextBox());
                        contactTwoUserIdTextBox().sendKeys(contactTwoUserId);
                    }
                }
            }else{

                if(enableMultipleContact){

                    try {
                        if(removeContactBtn().isDisplayed() && removeContactBtn().isEnabled())
                            removeContactBtn().click();
                    }catch (Exception e) {
                        log.error("Edit Sub-Brand: Contact Details Page: Remove Contact Button Not Found. Only One Contact Is Added.");
                    }

                    clearText(contactOneFirstNameTextBox());
                    contactOneFirstNameTextBox().sendKeys(contactOneFN);
                    Thread.sleep(1000);
                    clearText(contactOneLastNameTextBox());
                    contactOneLastNameTextBox().sendKeys(contactOneLN);
                    Thread.sleep(1000);
                    clearText(contactOneContactNumberTextBox());
                    contactOneContactNumberTextBox().sendKeys(contactOneContactNum);
                    Thread.sleep(1000);
                    clearText(contactOneEmailIdTextBox());
                    contactOneEmailIdTextBox().sendKeys(contactOneEmail);
                    Thread.sleep(1000);
                    if (customerType.equalsIgnoreCase(Constants.CUSTOMER_TYPE_SUB_BRAND)) {
                        if(contactOneUserIdTextBox().isEnabled()) {
                            clearText(contactOneUserIdTextBox());
                            contactOneUserIdTextBox().sendKeys(contactOneUserId);
                        }else
                            log.info("Contact One User ID Text Box Is Disabled In Edit Mode.");
                    }

                    if (addAnotherContactBtn().isEnabled() && addAnotherContactBtn().isDisplayed())
                        addAnotherContactBtn().click();

                    clearText(contactTwoFirstNameTextBox());
                    contactTwoFirstNameTextBox().sendKeys(contactTwoFN);
                    Thread.sleep(1000);
                    clearText(contactTwoLastNameTextBox());
                    contactTwoLastNameTextBox().sendKeys(contactTwoLN);
                    Thread.sleep(1000);
                    clearText(contactTwoContactNumberTextBox());
                    contactTwoContactNumberTextBox().sendKeys(contactTwoContactNum);
                    Thread.sleep(1000);
                    clearText(contactTwoEmailIdTextBox());
                    contactTwoEmailIdTextBox().sendKeys(contactTwoEmail);
                    Thread.sleep(1000);
                    if (customerType.equalsIgnoreCase(Constants.CUSTOMER_TYPE_SUB_BRAND)) {
                        if(contactTwoUserIdTextBox().isEnabled()) {
                            clearText(contactTwoUserIdTextBox());
                            contactTwoUserIdTextBox().sendKeys(contactTwoUserId);
                        }else
                            log.info("Contact Two User ID Text Box Is Disabled");
                    }
                }else{
                    try {
                        if(removeContactBtn().isDisplayed() && removeContactBtn().isEnabled())
                            removeContactBtn().click();
                        log.info("Removing Contact 2 Since, Add Another Contact And Enable Multiple Contacts Is SET to FALSE");
                    }catch (Exception e) {
                        log.error("Edit Sub-Brand: Contact Details Page: Remove Contact Button Not Found. Only One Contact Is Added.");
                    }

                    if (customerType.equalsIgnoreCase(Constants.CUSTOMER_TYPE_SUB_BRAND)) {
                        if(contactOneUserIdTextBox().isEnabled() && contactOneUserIdTextBox().getText().equalsIgnoreCase("")) {
                            clearText(contactOneUserIdTextBox());
                            contactOneUserIdTextBox().sendKeys(contactOneUserId);
                        }
                    }
                }
            }
        }
    }
}