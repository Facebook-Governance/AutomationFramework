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

import java.util.List;

public class CustomizationToolFeaturesPage extends Page {

    private Logger log = LoggerFactory.getLogger(CustomizationToolFeaturesPage.class);

    public String pageName = "CUSTOMIZATION_TOOL_FEATURES_PAGE";

    public CustomizationToolFeaturesPage(WebDriver driver) {
        super(driver);
    }

    public WebElement featuresTab() { return getWebElement(pageName, "featuresTab"); }

    public WebElement applyToAllLink() { return getWebElement(pageName, "applyToAllLnk"); }

    public WebElement applyToAllSSOToggleButton() { return getWebElement(pageName, "ataSSOToggleBtn"); }

    public WebElement applyToAllPrivateLink() { return getWebElement(pageName, "ataSSOPrivateLnk"); }

    public WebElement applyToAllSSOPrivateDefaultDomainNameTextBox() { return getWebElement(pageName, "ataSSOPrivateDefaultDomainNameTxtBx"); }

    public WebElement applyToAllSSOPrivateLoginUrlTextBox() { return getWebElement(pageName, "ataSSOPrivateLoginURLTxtBx"); }

    public WebElement applyToAllSSOPrivateLogoutUrlTextBox() { return getWebElement(pageName, "ataSSOPrivateLogoutURLTxtBx"); }

    public WebElement applyToAllSSOPrivateStaleSessionUrlTextBox() { return getWebElement(pageName, "ataSSOPrivateStaleSessionURLTxtBx"); }

    public WebElement applyToAllSSOPrivateKeepAliveReferralUrlTextBox() { return getWebElement(pageName, "ataSSOPrivateKeepAliveReferralURLTxtBx"); }

    public WebElement applyToAllSSOPrivateSessionTimeOutUrlTextBox() { return getWebElement(pageName, "ataSSOPrivateSessionTimeOutURLTxtBx"); }

    public WebElement applyToAllSSOPrivateTechnicalDifficultiesUrlTextBox() { return getWebElement(pageName, "ataSSOPrivateTechnicalDifficultiesURLTxtBx"); }

    public WebElement applyToAllSSOPrivateFallbackUrlTextBox() { return getWebElement(pageName, "ataSSOPrivateFallbackURLTxtBx"); }

    public WebElement applyToAllPublicLink() { return getWebElement(pageName, "ataSSOPublicLnk"); }

    public WebElement applyToAllSSOPublicDefaultDomainNameTextBox() { return getWebElement(pageName, "ataSSOPublicDefaultDomainNameTxtBx"); }

    public WebElement applyToAllSSOPublicLoginUrlTextBox() { return getWebElement(pageName, "ataSSOPublicLoginURLTxtBx"); }

    public WebElement applyToAllSSOPublicLogoutUrlTextBox() { return getWebElement(pageName, "ataSSOPublicLogoutURLTxtBx"); }

    public WebElement applyToAllSSOPublicStaleSessionUrlTextBox() { return getWebElement(pageName, "ataSSOPublicStaleSessionURLTxtBx"); }

    public WebElement applyToAllSSOPublicKeepAliveReferralUrlTextBox() { return getWebElement(pageName, "ataSSOPublicKeepAliveReferralURLTxtBx"); }

    public WebElement applyToAllSSOPublicSessionTimeOutUrlTextBox() { return getWebElement(pageName, "ataSSOPublicSessionTimeOutURLTxtBx"); }

    public WebElement applyToAllSSOPublicTechnicalDifficultiesUrlTextBox() { return getWebElement(pageName, "ataSSOPublicTechnicalDifficultiesURLTxtBx"); }

    public WebElement applyToAllSSOPublicFallbackUrlTextBox() { return getWebElement(pageName, "ataSSOPublicFallbackURLTxtBx"); }

    public WebElement applyToAllSessionManagerToggleButton() { return getWebElement(pageName, "ataSessionManagerToggleBtn"); }

    public WebElement applyToAllSessionManagerSessionTimeOutClickableTextBox() { return getWebElement(pageName, "ataSessionManagerSessionTimeOutMinutesClickableTxtBx"); }

    public WebElement applyToAllSessionManagerSessionTimeOutTextBox() { return getWebElement(pageName, "ataSessionManagerSessionTimeOutMinutesTxtBx"); }

    public WebElement applyToAllSessionManagerKeepAliveReferralUrlTextBox() { return getWebElement(pageName, "ataSessionManagerKeepAliveURLTxtBx"); }

    public WebElement applyToAllSessionManagerRedirectUrlTextBox() { return getWebElement(pageName, "ataSessionManagerRedirectURLTxtBx"); }

    public WebElement applyToAllSessionManagerShowTimeNotificationToggleButton() { return getWebElement(pageName, "ataSessionManagerShowTimeNotificationToggleBtn"); }

    public WebElement applyToAllSessionManagerSessionAutoRenewToggleButton() { return getWebElement(pageName, "ataSessionManagerSessionAutoRenewToggleBtn"); }

    public WebElement fastLinkLink() { return getWebElement(pageName, "fastLinkLnk"); }

    public WebElement fastLinkSplashPageToggleButton() { return getWebElement(pageName, "flSplashPageToggleBtn"); }

    public WebElement fastLinkTermsAndConditionsSplashToggleButton() { return getWebElement(pageName, "flTermsAndConditionsSplashToggleBtn"); }

    public WebElement fastLinkTermsAndConditionsVerifyCredentialsToggleButton() { return getWebElement(pageName, "flTermsAndConditionsVerifyCredentialsToggleBtn"); }

    public WebElement fastLinkTermsAndConditionsVerifyCredentialsDropDown() { return getWebElement(pageName, "flTermsAndConditionsVerifyCredentialsDropDwn"); }

    public WebElement fastLinkTermsAndConditionsVerifyCredentialsItemsInDropDown(String token) { return getWebElementByReplacingToken(pageName, "flTermsAndConditionsVerifyCredentialsItemsInDropDwn", token); }

    public WebElement fastLinkTermsAndConditionsVerifyCredentialsLinkTextBox() { return getWebElement(pageName, "flTermsAndConditionsVerifyCredentialsLinkTxtBx"); }

    public WebElement fastLinkTermsAndConditionsVerifyCredentialsLinkClickAbleTextBox() { return getWebElement(pageName, "flTermsAndConditionsVerifyCredentialsLinkClickableTxtBx"); }

    public WebElement fastLinkTermsAndConditionsVerifyCredentialsInlineTextArea() { return getWebElement(pageName, "flTermsAndConditionsVerifyCredentialsInlineTxtArea"); }

    public WebElement fastLinkStepperToggleButton() { return getWebElement(pageName, "flStepperToggleBtn"); }

    public WebElement fastLinkSearchBarPositionDropDown() { return getWebElement(pageName, "flSearchBarPositionDropDwn"); }

    public WebElement fastLinkSearchBarPositionItemsInDropDown(String token) { return getWebElementByReplacingToken(pageName, "flSearchBarPositionItemsInDropDwn", token); }

    public WebElement fastLinkPopularSiteToggleButton() { return getWebElement(pageName, "flPopularSiteToggleBtn"); }

    public WebElement flPopularSiteSearchTextBox() { return getWebElement(pageName, "flPopularSiteSearchTxtBx"); }

    public WebElement flPopularSiteSearchDropDown() { return getWebElement(pageName, "flPopularSiteSearchDrpDwn"); }

    public WebElement flPopularSiteSearchItemsInDropDown(String token) { return getWebElementByReplacingToken(pageName, "flPopularSiteSearchItemsInDrpDwn", token); }

    public WebElement fastLinkPopularSitesNumberOfSitesToDisplayLabel() { return getWebElement(pageName, "flPsNumberOfSitesToDisplayLbl"); }

    public WebElement fastLinkPopularSitesWebLabel() { return getWebElement(pageName, "flPsWebLbl"); }

    public WebElement fastLinkPopularSitesWebValueLabel() { return getWebElement(pageName, "flPsWebValueLbl"); }

    public WebElement fastLinkPopularSitesWebTextBox() { return getWebElement(pageName, "flPsWebTxtBx"); }

    public WebElement fastLinkPopularSitesWebClickableTextBox() { return getWebElement(pageName, "flPsWebClickableTxtBx"); }

    public WebElement fastLinkPopularSitesMobileLabel() { return getWebElement(pageName, "flPsMobileLbl"); }

    public WebElement fastLinkPopularSitesMobileValueLabel() { return getWebElement(pageName, "flPsMobileValueLbl"); }

    public WebElement fastLinkPopularSitesMobileTextBox() { return getWebElement(pageName, "flPsMobileTxtBx"); }

    public WebElement fastLinkPopularSitesMobileClickableTextBox() { return getWebElement(pageName, "flPsMobileClickableTxtBx"); }

    public WebElement fastLinkPopularSitesTabletLabel() { return getWebElement(pageName, "flPsTabletLbl"); }

    public WebElement fastLinkPopularSiteTabletValueLabel() { return getWebElement(pageName, "flPsTabletValueLbl"); }

    public WebElement fastLinkPopularSiteTabletTextBox() { return getWebElement(pageName, "flPsTabletTxtBx"); }

    public WebElement fastLinkPopularSiteTabletClickableTextBox() { return getWebElement(pageName, "flPsTabletClickableTxtBx"); }

    public WebElement fastLinkPopularSiteDisplaySiteNameOnLogoButtonsToggleButton() { return getWebElement(pageName, "flPsDisplaySiteNameOnLogoButtonsToggleBtn"); }

    public WebElement fastLinkSuccessPageForAddAccountAggregationToggleButton() { return getWebElement(pageName, "flSuccessPageForAddAccountAggregationToggleBtn"); }

    public WebElement fastLinkFooterToggleButton() { return getWebElement(pageName, "flFooterToggleBtn"); }
}