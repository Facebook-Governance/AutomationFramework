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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GeneralConfigurationPage extends Page {

    private Logger log = LoggerFactory.getLogger(GeneralConfigurationPage.class);

    public String pageName = "GENERAL_CONFIGURATION_PAGE";

    public GeneralConfigurationPage(WebDriver driver) {
        super(driver);
    }

    public WebElement headerLabel() {
        return getWebElement(pageName, "genConfigPageLbl");
    }

    public WebElement stepHeaderGenConfigLabel() {
        return getWebElement(pageName, "gcpStepHeader");
    }

    public List<WebElement> stepHeaderLabels() {
        return getElementsFromPage(pageName, "gcpStepHeader");
    }

    public WebElement backToSubBrandListLink() { return getWebElement(pageName, "backToSubBrandsListLnk"); }

    public WebElement saveAndContinueButton() { return getWebElement(pageName, "saveAndContinueBtn"); }

    public WebElement subBrandNameTextBox() {
        return getWebElement(pageName, "subBrandNameTxtBx");
    }

    public WebElement subBrandNameErrorText() { return getWebElement(pageName, "subBrandNameErrorText"); }

    public WebElement domainNameSubStringTextBox() { return getWebElement(pageName, "domainNameSubStringTxtBx"); }

    public WebElement domainNameSubStringErrorText() { return getWebElement(pageName, "domainNameSubStringErrorText"); }

    public WebElement sourceIPAddressTextBox() { return getWebElement(pageName, "sourceIpAddressTxtBx"); }

    public WebElement sourceIPAddressErrorText() { return getWebElement(pageName, "sourceIpAddressErrorText"); }

    public WebElement addIPAddressButton() { return getWebElement(pageName, "addIpAddressBtn"); }

    public List<WebElement> listOfIPAddresses() { return getElementsFromPage(pageName, "listOfIpAddressesLbl"); }

    public WebElement removeIPAddressButton(String token) { return getWebElementByReplacingToken(pageName, "removeIpAddressBtn", token); }

    public WebElement subBrandRadioButton() { return getWebElement(pageName, "subBrandRadioBtn"); }

    public WebElement channelPartnerRadioButton() { return getWebElement(pageName, "channelPartnerRadioBtn"); }

    public List<WebElement> customerTypeRadioButtons() { return getElementsFromPage(pageName, "listOfCustomerTypeRadioBtns"); }

    public List <String> getListOfCustomerTypesRadioButtons(){
        List<String> listOfCustomerTypesRadioButtons = new ArrayList();
        for (WebElement e:customerTypeRadioButtons()) {
            listOfCustomerTypesRadioButtons.add(e.getText());
        }
        return listOfCustomerTypesRadioButtons;
    }

    public List<String> getListOfIPAddresses(){
        List<String> listOfIpAddresses = new ArrayList();
        for (WebElement e:listOfIPAddresses()) {
            listOfIpAddresses.add(e.getText());
        }
        return listOfIpAddresses;
    }

    public void enterGeneralConfigDetails(String subBrandName, String domainNameSubString,
                                          String sourceIPAddress, String customerType) throws InterruptedException {

        log.info("**************************GENERAL CONFIGURATION**************************");

        if(!headerLabel().getText().trim().contains("Edit")){

            selectCustomerType(customerType);
            Thread.sleep(1000);
            clearText(subBrandNameTextBox());
            subBrandNameTextBox().sendKeys(subBrandName);
            Thread.sleep(1000);
            clearText(domainNameSubStringTextBox());
            domainNameSubStringTextBox().sendKeys(domainNameSubString);
            Thread.sleep(1000);
            addIPAddress(sourceIPAddress);

        }else{

            if(customerType!=null && customerType!="")
                selectCustomerType(customerType);
            Thread.sleep(1000);

            if(sourceIPAddress!=null && sourceIPAddress!="") {
                List<String> listOfIpAddresses= getListOfIPAddresses();
                String stringOfIpAddresses = listOfIpAddresses.stream().collect(Collectors.joining(";"));
                removeIPAddress(stringOfIpAddresses);
                addIPAddress(sourceIPAddress);
            }
        }
    }

    public void selectCustomerType(String customerType){

        if(customerType.equalsIgnoreCase(Constants.CUSTOMER_TYPE_CHANNEL_PARTNER))
            if (!channelPartnerRadioButton().isSelected())
                channelPartnerRadioButton().click();

        if (customerType.equalsIgnoreCase(Constants.CUSTOMER_TYPE_SUB_BRAND))
            if (!subBrandRadioButton().isSelected())
                subBrandRadioButton().click();
    }

    public void addIPAddress(String ipAddress) throws InterruptedException {

        String [] arrIpAddresses ={};
        if(ipAddress.contains(";")) {
            arrIpAddresses = ipAddress.split(";");

            for (String s : arrIpAddresses) {
                Thread.sleep(300);
                clearText(sourceIPAddressTextBox());
                sourceIPAddressTextBox().sendKeys(s);
                Thread.sleep(300);
                addIPAddressButton().click();
            }
        }else{
            Thread.sleep(300);
            clearText(sourceIPAddressTextBox());
            sourceIPAddressTextBox().sendKeys(ipAddress);
            Thread.sleep(300);
            addIPAddressButton().click();
        }
    }

    public void removeIPAddress(String ipAddress) {

        String [] arrIpAddresses ={};
        if(ipAddress.contains(";")) {
            arrIpAddresses = ipAddress.split(";");

            for (String s : arrIpAddresses)
                try{
                    if (removeIPAddressButton(s).isDisplayed())
                        removeIPAddressButton(s).click();
                }catch (Exception e){
                    log.info("IP Address "+s+" Was Not In The List To Be Removed.");
                }
        }else
            removeIPAddressButton(ipAddress).click();
    }
}