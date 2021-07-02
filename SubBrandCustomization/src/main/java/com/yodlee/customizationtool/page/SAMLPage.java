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


public class SAMLPage extends Page {

    private Logger log = LoggerFactory.getLogger(SAMLPage.class);

    public String pageName = "SAML_PAGE";

    public SAMLPage(WebDriver driver) {
        super(driver);
    }

    public WebElement headerLabel() {
        return getWebElement(pageName, "headerLbl");
    }

    public WebElement issuerTextBox() {
        return getWebElement(pageName, "issuerTxtBx");
    }

    public WebElement subjectNameUserIdTextBox() { return getWebElement(pageName, "subjectNameUserIdTxtBx"); }

    public WebElement nodeURLTextBox() { return getWebElement(pageName, "nodeUrlTxtBx"); }

    public WebElement finappIdTextBox() { return getWebElement(pageName, "finappIdTxtBx"); }

    public WebElement extraParamsTextBox() { return getWebElement(pageName, "extraParamsTxtBx"); }

    public WebElement redirectRequestTextBox() { return getWebElement(pageName, "redirectRequestTxtBx"); }

    public WebElement attributeStringTextArea() { return getWebElement(pageName, "attributeStringTxtArea"); }

    public WebElement samlVersionRadioButton(String token) { return getWebElementByReplacingToken(pageName, "samlVersionRadioBtn", token); }

    public WebElement attributeEncryptionSAML_1_1_RadioButton(String token) { return getWebElementByReplacingToken(pageName, "attributeEncryptionSAML1.1RadioBtn", token); }

    public WebElement attributeEncryptionSAML_2_0_RadioButton(String token) { return getWebElementByReplacingToken(pageName, "attributeEncryptionSAML2.0RadioBtn", token); }

    public WebElement attributeEncryptionMechanismSAML_2_0_RadioButton(String token) { return getWebElementByReplacingToken(pageName, "attributeEncryptionMechanismSAML2.0RadioBtn", token); }

    public WebElement attributeEncodingRadioButton(String token) { return getWebElementByReplacingToken(pageName, "attributeEncodingRadioBtn", token); }

    public WebElement assertionEncryptionSAML_2_0_RadioButton(String token) { return getWebElementByReplacingToken(pageName, "assertionEncryptionSAML2.0RadioBtn", token); }

    public WebElement multipleKeysSAML_2_0_RadioButton(String token) { return getWebElementByReplacingToken(pageName, "multipleKeysSAML2.0RadioBtn", token); }

    public WebElement signResponseRadioButton(String token) { return getWebElementByReplacingToken(pageName, "signResponseRadioBtn", token); }

    public WebElement signAssertionRadioButton(String token) { return getWebElementByReplacingToken(pageName, "signAssertionRadioBtn", token); }

    public WebElement signingAliasKeyTextBox() { return getWebElement(pageName, "signingAliasKeyTxtBx"); }

    public WebElement ssoAttributeKeyTextBox() { return getWebElement(pageName, "ssoAttributeKeyTxtBx"); }

    public WebElement linkIntegrityTokenRadioButton(String token) { return getWebElementByReplacingToken(pageName, "linkIntegrityTokenRadioBtn", token); }

    public WebElement submitButton() { return getWebElement(pageName, "submitBtn"); }

    public void enterSAMLDetails(String issuerID, String subjectNameUserID, String nodeURL, String finappID,
                                 String extraParams, String redirectRequest, String attributes, String samlVersion,
                                 String assertionEncryption, String multipleKeys, String attributeEncryption,
                                 String attributeEncryptionMechanism, String attributeEncoding, String signResponse,
                                 String signAssertion, String signingAliasKey, String ssoAttributeKey, String linkIntegrityToken) throws InterruptedException {

        clearText(issuerTextBox());
        issuerTextBox().sendKeys(issuerID);
        Thread.sleep(500);
        clearText(subjectNameUserIdTextBox());
        subjectNameUserIdTextBox().sendKeys(subjectNameUserID);
        Thread.sleep(500);
        clearText(nodeURLTextBox());
        nodeURLTextBox().sendKeys(nodeURL);
        Thread.sleep(500);
        clearText(finappIdTextBox());
        finappIdTextBox().sendKeys(finappID);
        Thread.sleep(500);
        clearText(extraParamsTextBox());
        extraParamsTextBox().sendKeys(extraParams);
        Thread.sleep(500);
        clearText(redirectRequestTextBox());
        redirectRequestTextBox().sendKeys(redirectRequest);
        Thread.sleep(500);
        clearTextArea(attributeStringTextArea());
        attributeStringTextArea().sendKeys(attributes);
        Thread.sleep(500);
        samlVersionRadioButton(samlVersion).click();

        if(samlVersion.equalsIgnoreCase("1.1")){
            attributeEncryptionSAML_1_1_RadioButton(attributeEncryption).click();
            Thread.sleep(500);
            attributeEncodingRadioButton(attributeEncoding).click();
        }else{
            assertionEncryptionSAML_2_0_RadioButton(assertionEncryption).click();
            Thread.sleep(500);
            multipleKeysSAML_2_0_RadioButton(multipleKeys).click();
            Thread.sleep(500);
            attributeEncryptionSAML_2_0_RadioButton(attributeEncryption).click();
            Thread.sleep(500);
            attributeEncryptionMechanismSAML_2_0_RadioButton(attributeEncryptionMechanism).click();
            Thread.sleep(500);
            attributeEncodingRadioButton(attributeEncoding).click();
        }

        signResponseRadioButton(signResponse).click();
        Thread.sleep(500);
        signAssertionRadioButton(signAssertion).click();
        Thread.sleep(500);
        clearText(signingAliasKeyTextBox());
        signingAliasKeyTextBox().sendKeys(signingAliasKey);
        Thread.sleep(500);
        clearText(ssoAttributeKeyTextBox());
        ssoAttributeKeyTextBox().sendKeys(ssoAttributeKey);
        Thread.sleep(500);
        linkIntegrityTokenRadioButton(linkIntegrityToken).click();
        Thread.sleep(500);

        submitButton().click();
        Thread.sleep(1000);
    }
}