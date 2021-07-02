/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Rajeev Anantharaman Iyer
*/
package com.yodlee.customizationtool.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

public class SubBrandOverViewPage extends Page {

    private Logger log = LoggerFactory.getLogger(SubBrandOverViewPage.class);

    public String pageName = "SUB_BRAND_OVERVIEW_PAGE";

    public SubBrandOverViewPage(WebDriver driver) {
        super(driver);
    }

    public WebElement headerLabel() {
        return getWebElement(pageName, "sbOverviewPageLbl");
    }

    public List<WebElement> stepHeaderLabels() {
        return getElementsFromPage(pageName, "sbOverviewStepHeader");
    }

    public WebElement backToSubBrandListLink() { return getWebElement(pageName, "backToSubBrandsListLnk"); }

    public WebElement subBrandOverViewEditButton(){
        return getWebElement(pageName,"sbOverviewEditBtn");
    }

    public WebElement subBrandOverViewConfigureAppsButton() {
        return getWebElement(pageName, "sbOverviewConfigureAppsBtn");
    }

    public WebElement sbOverviewGenConfigCustomerSupportOwnerTextLabel() {
        return getWebElement(pageName, "sbOverviewGenConfigCustomerSupportOwnerTxtLbl");
    }

    public WebElement sbOverviewGenConfigCustomerSupportOwnerValueTextLabel() {
        return getWebElement(pageName, "sbOverviewGenConfigCustomerSupportOwnerValueTxtLbl");
    }

    public WebElement sbOverviewGenConfigApplPrivateURLTextLabel() {
        return getWebElement(pageName, "sbOverviewGenConfigApplPrivateURLTxtLbl");
    }

    public WebElement sbOverviewGenConfigApplPrivateURLValueTextLabel() {
        return getWebElement(pageName, "sbOverviewGenConfigApplPrivateURLValueTxtLbl");
    }

    public WebElement sbOverviewGenConfigApplPublicURLTextLabel() {
        return getWebElement(pageName, "sbOverviewGenConfigApplPublicURLTxtLbl");
    }

    public WebElement sbOverviewGenConfigApplPublicURLValueTextLabel() {
        return getWebElement(pageName, "sbOverviewGenConfigApplPublicURLValueTxtLbl");
    }

    public WebElement sbOverviewGenConfigYCCPrivateURLTextLabel() {
        return getWebElement(pageName, "sbOverviewGenConfigYCCPrivateURLTxtLbl");
    }

    public WebElement sbOverviewGenConfigYCCPrivateURLValueTextLabel() {
        return getWebElement(pageName, "sbOverviewGenConfigYCCPrivateURLValueTxtLbl");
    }

    public WebElement sbOverviewGenConfigYSLPrivateURLTextLabel() {
        return getWebElement(pageName, "sbOverviewGenConfigYSLPrivateURLTxtLbl");
    }

    public WebElement sbOverviewGenConfigYSLPrivateURLValueTextLabel() {
        return getWebElement(pageName, "sbOverviewGenConfigYSLPrivateURLValueTxtLbl");
    }

    public WebElement sbOverviewGenConfigYCCPublicURLTextLabel() {
        return getWebElement(pageName, "sbOverviewGenConfigYCCPublicURLTxtLbl");
    }

    public WebElement sbOverviewGenConfigYCCPublicURLValueTextLabel() {
        return getWebElement(pageName, "sbOverviewGenConfigYCCPublicURLValueTxtLbl");
    }

    public WebElement sbOverviewGenConfigYSLPublicURLTextLabel() {
        return getWebElement(pageName, "sbOverviewGenConfigYSLPublicURLTxtLbl");
    }

    public WebElement sbOverviewGenConfigYSLPublicURLValueTextLabel() {
        return getWebElement(pageName, "sbOverviewGenConfigYSLPublicURLValueTxtLbl");
    }

    public WebElement sbOverviewSelectedProductAggregationTextLabel() {
        return getWebElement(pageName, "sbOverviewSelectedProductAggregationTxtLbl");
    }

    public WebElement sbOverviewSelectedProductVerificationTextLabel() {
        return getWebElement(pageName, "sbOverviewSelectedProductVerificationTxtLbl");
    }

    public WebElement sbOverviewEnabledApplicationFastLinkTextLabel() {
        return getWebElement(pageName, "sbOverviewEnabledApplicationFastLinkTxtLbl");
    }

    public WebElement sbOverviewEnabledApplicationsWellnessFinAppsTextLabel() {
        return getWebElement(pageName, "sbOverviewEnabledApplicationsWellnessFinAppsTxtLbl");
    }

    public List<WebElement> sbOverviewListOfEnabledApplicationsWellnessFinAppsValueTxtLbl() {
        return getElementsFromPage(pageName, "sbOverviewListOfEnabledApplicationsWellnessFinAppsValueTxtLbl");
    }

    public WebElement sbOverviewContactNameColumnTextLabel() {
        return getWebElement(pageName, "sbOverviewContactNameColumnTxtLbl");
    }

    public WebElement sbOverviewUserIdColumnTextLabel() {
        return getWebElement(pageName, "sbOverviewUserIdColumnTxtLbl");
    }

    public WebElement sbOverviewEmailIdColumnTextLabel() {
        return getWebElement(pageName, "sbOverviewEmailIdColumnTxtLbl");
    }

    public WebElement sbOverviewContactNumberColumnTextLabel() {
        return getWebElement(pageName, "sbOverviewContactNumberColumnTxtLbl");
    }

    public WebElement sbOverviewContactOneNameValueTextLabel() {
        return getWebElement(pageName, "sbOverviewContactOneNameValueLbl");
    }

    public WebElement sbOverviewContactTwoNameValueTextLabel() {
        return getWebElement(pageName, "sbOverviewContactTwoNameValueLbl");
    }

    public WebElement sbOverviewContactOneUserIdValueTextLabel() {
        return getWebElement(pageName, "sbOverviewContactOneUserIdValueLbl");
    }

    public WebElement sbOverviewContactTwoUserIdValueTextLabel() {
        return getWebElement(pageName, "sbOverviewContactTwoUserIdValueLbl");
    }

    public WebElement sbOverviewContactOneEmailValueTextLabel(String columnValue) {
        return getWebElementByReplacingToken(pageName, "sbOverviewContactOneEmailValueLbl", columnValue);
    }

    public WebElement sbOverviewContactTwoEmailValueTextLabel(String columnValue) {
        return getWebElementByReplacingToken(pageName, "sbOverviewContactTwoEmailValueLbl", columnValue);
    }

    public WebElement sbOverviewContactOneContactNumberValueTextLabel(String columnValue) {
        return getWebElementByReplacingToken(pageName, "sbOverviewContactOneContactNumberValueLbl", columnValue);
    }

    public WebElement sbOverviewContactTwoContactNumberValueTextLabel(String columnValue) {
        return getWebElementByReplacingToken(pageName, "sbOverviewContactTwoContactNumberValueLbl", columnValue);
    }

}