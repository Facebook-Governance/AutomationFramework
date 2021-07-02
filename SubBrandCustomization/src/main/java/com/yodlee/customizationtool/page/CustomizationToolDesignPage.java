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

public class CustomizationToolDesignPage extends Page {

    private Logger log = LoggerFactory.getLogger(CustomizationToolDesignPage.class);

    public String pageName = "CUSTOMIZATION_TOOL_DESIGN_PAGE";

    public CustomizationToolDesignPage(WebDriver driver) {
        super(driver);
    }

    public WebElement designTab() { return getWebElement(pageName, "designTab"); }

    public WebElement designTabApplyToAllLink() { return getWebElement(pageName, "designTabApplyToAllLnk"); }

    public WebElement designApplyToAllFontFamilyUploadLink() { return getWebElement(pageName, "designATAFontFamilyUploadLnk"); }

    public WebElement designApplyToAllFontUploadDropFileTextBox() { return getWebElement(pageName, "designATAFontUploadDropFileTxtBx"); }

    public WebElement designApplyToAllFontUploadUploadFontButton() { return getWebElement(pageName, "designATAFontUploadUploadFontBtn"); }

    public WebElement designApplyToAllTextColorButton() { return getWebElement(pageName, "designATATextColorBtn"); }

    public WebElement designApplyToAllPrimaryButtonLink() { return getWebElement(pageName, "designATAPrimaryButtonLnk"); }

    public WebElement designApplyToAllPrimaryButtonDefaultTextColorButton() { return getWebElement(pageName, "designATAPrimaryButtonDefaultTextColorBtn"); }

    public WebElement designApplyToAllPrimaryButtonHoverTextColorButton() { return getWebElement(pageName, "designATAPrimaryButtonHoverTextColorBtn"); }

    public WebElement designApplyToAllPrimaryButtonDefaultBackgroundColorButton() { return getWebElement(pageName, "designATAPrimaryButtonDefaultBackgroundColorBtn"); }

    public WebElement designApplyToAllPrimaryButtonHoverBackgroundColorButton() { return getWebElement(pageName, "designATAPrimaryButtonHoverBackgroundColorBtn"); }

    public WebElement designApplyToAllPrimaryButtonBorderColorButton() { return getWebElement(pageName, "designATAPrimaryButtonBorderColorBtn"); }

    public WebElement designApplyToAllSecondaryButtonLink() { return getWebElement(pageName, "designATASecondaryButtonLnk"); }

    public WebElement designApplyToAllSecondaryButtonDefaultTextColorButton() { return getWebElement(pageName, "designATASecondaryButtonDefaultTextColorBtn"); }

    public WebElement designApplyToAllSecondaryButtonHoverTextColorButton() { return getWebElement(pageName, "designATASecondaryButtonHoverTextColorBtn"); }

    public WebElement designApplyToAllSecondaryButtonDefaultBackgroundColorButton() { return getWebElement(pageName, "designATASecondaryButtonDefaultBackgroundColorBtn"); }

    public WebElement designApplyToAllSecondaryButtonHoverBackgroundColorButton() { return getWebElement(pageName, "designATASecondaryButtonHoverBackgroundColorBtn"); }

    public WebElement designApplyToAllSecondaryButtonBorderColorButton() { return getWebElement(pageName, "designATASecondaryButtonBorderColorBtn"); }

    public WebElement designApplyToAllDeleteButtonLink() { return getWebElement(pageName, "designATADeleteButtonLnk"); }

    public WebElement designApplyToAllDeleteButtonDefaultTextColorButton() { return getWebElement(pageName, "designATADeleteButtonDefaultTextColorBtn"); }

    public WebElement designApplyToAllDeleteButtonHoverTextColorButton() { return getWebElement(pageName, "designATADeleteButtonHoverTextColorBtn"); }

    public WebElement designApplyToAllDeleteButtonDefaultBackgroundColorButton() { return getWebElement(pageName, "designATADeleteButtonDefaultBackgroundColorBtn"); }

    public WebElement designApplyToAllDeleteButtonHoverBackgroundColorButton() { return getWebElement(pageName, "designATADeleteButtonHoverBackgroundColorBtn"); }

    public WebElement designApplyToAllDeleteButtonBorderColorButton() { return getWebElement(pageName, "designATADeleteButtonBorderColorBtn"); }

    public WebElement designTabFastLinkLink() { return getWebElement(pageName, "designTabFastlinkLnk"); }

    public WebElement designFastLinkBackgroundColorButton() { return getWebElement(pageName, "designFLBackgroundColorBtn"); }

    public WebElement designFastLinkHeadersLink() { return getWebElement(pageName, "designFLHeadersLnk"); }

    public WebElement designFastLinkHeadersTextColorButton() { return getWebElement(pageName, "designFLHeadersTextColorBtn"); }

    public WebElement designFastLinkHeadersBackgroundColorButton() { return getWebElement(pageName, "designFLHeadersBackgroundColorBtn"); }

    public WebElement designFastLinkTipsLink() { return getWebElement(pageName, "designFLTipsLnk"); }

    public WebElement designFastLinkTipsTextColorButton() { return getWebElement(pageName, "designFLTipsTextColorBtn"); }

    public WebElement designFastLinkTipsBackgroundColorButton() { return getWebElement(pageName, "designFLTipsBackgroundColorBtn"); }

    public WebElement designFastLinkSteppersLink() { return getWebElement(pageName, "designFLSteppersLnk"); }

    public WebElement designFastLinkSteppersActiveTextColorButton() { return getWebElement(pageName, "designFLSteppersActiveTextColorBtn"); }

    public WebElement designFastLinkSteppersActiveBorderColorButton() { return getWebElement(pageName, "designFLSteppersActiveBorderColorBtn"); }

    public WebElement designFastLinkSteppersInactiveTextColorButton() { return getWebElement(pageName, "designFLSteppersInactiveTextColorBtn"); }

    public WebElement designFastLinkSteppersInactiveBorderColorButton() { return getWebElement(pageName, "designFLSteppersInactiveBorderColorBtn"); }

    public WebElement designFastLinkSelectInstitutionsButtonsLink() { return getWebElement(pageName, "designFLSelectInstitutionsButtonsLnk"); }

    public WebElement designFastLinkSelectInstitutionsButtonsBackgroundColorButton() { return getWebElement(pageName, "designFLSelectInstitutionsButtonsBackgroundColorBtn"); }

    public WebElement designFastLinkSelectInstitutionsButtonsLabelTextColorButton() { return getWebElement(pageName, "designFLSelectInstitutionsButtonsLabelTextColorBtn"); }

    public WebElement designTabWellnessLink() { return getWebElement(pageName, "designTabWellnessLnk"); }

    public WebElement designWellnessButtonRadiusClickableTextBox() { return getWebElement(pageName, "designWellnessButtonRadiusClickableTxtBx"); }

    public WebElement designWellnessButtonRadiusTextBox() { return getWebElement(pageName, "designWellnessButtonRadiusTxtBx"); }

    public WebElement designWellnessMainMenuLink() { return getWebElement(pageName, "designWellnessMainMenuLnk"); }

    public WebElement designWellnessMainMenuDefaultTextColorButton() { return getWebElement(pageName, "designWellnessMainMenuDefaultTextColorBtn"); }

    public WebElement designWellnessMainMenuHoverTextColorButton() { return getWebElement(pageName, "designWellnessMainMenuHoverTextColorBtn"); }

    public WebElement designWellnessMainMenuActiveTextColorButton() { return getWebElement(pageName, "designWellnessMainMenuActiveTextColorBtn"); }

    public WebElement designWellnessMainMenuBackgroundColorButton() { return getWebElement(pageName, "designWellnessMainMenuBackgroundColorBtn"); }

    public WebElement designWellnessDropdownSubmenuLink() { return getWebElement(pageName, "designWellnessDropdownSubmenuLnk"); }

    public WebElement designWellnessDropdownSubmenuDefaultTextColorButton() { return getWebElement(pageName, "designWellnessDropdownSubmenuDefaultTextColorBtn"); }

    public WebElement designWellnessDropdownSubmenuHoverTextColorButton() { return getWebElement(pageName, "designWellnessDropdownSubmenuHoverTextColorBtn"); }

    public WebElement designWellnessDropdownSubmenuActiveTextColorButton() { return getWebElement(pageName, "designWellnessDropdownSubmenuActiveTextColorBtn"); }

    public WebElement designWellnessDropdownSubmenuBackgroundColorButton() { return getWebElement(pageName, "designWellnessDropdownSubmenuBackgroundColorBtn"); }

    public WebElement designWellnessSidebarMenuLink() { return getWebElement(pageName, "designWellnessSidebarMenuLnk"); }

    public WebElement designWellnessSidebarMenuDefaultTextColorButton() { return getWebElement(pageName, "designWellnessSidebarMenuDefaultTextColorBtn"); }

    public WebElement designWellnessSidebarMenuHoverTextColorButton() { return getWebElement(pageName, "designWellnessSidebarMenuHoverTextColorBtn"); }

    public WebElement designWellnessSidebarMenuActiveTextColorButton() { return getWebElement(pageName, "designWellnessSidebarMenuActiveTextColorBtn"); }

    public WebElement designWellnessSidebarMenuBackgroundColorButton() { return getWebElement(pageName, "designWellnessSidebarMenuBackgroundColorBtn"); }

    public WebElement designTabColorPickerTextBox() { return getWebElement(pageName, "designTabColorPickerTxtBx"); }

    public WebElement designTabColorPickerRevertToDefaultLink() { return getWebElement(pageName, "designTabColorPickerRevertToDefaultLnk"); }

    public WebElement designTabColorPickerOkButton() { return getWebElement(pageName, "designTabColorPickerOkBtn"); }
}