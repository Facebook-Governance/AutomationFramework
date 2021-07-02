/**
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 *
 * @author Rajeev Anantharaman Iyer
 */
package com.yodlee.customizationtool.page;

import com.yodlee.customizationtool.util.GenericUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class PrivateAuthenticationSetupPage extends Page {

    private Logger log = LoggerFactory.getLogger(PrivateAuthenticationSetupPage.class);

    public String pageName = "PRIVATE_AUTHENTICATION_SETUP_PAGE";

    public PrivateAuthenticationSetupPage(WebDriver driver) {
        super(driver);
    }

    public WebElement headerLabel() {
        return getWebElement(pageName, "privateAuthSetupPageLbl");
    }

    public WebElement stepHeaderPrivateAuthSetupLabel() {
        return getWebElement(pageName, "saspStepHeader");
    }

    public List<WebElement> stepHeaderLabels() {
        return getElementsFromPage(pageName, "saspStepHeader");
    }

    public WebElement backToPreviousStepButton() { return getWebElement(pageName, "backToPreviousStepBtn"); }

    public WebElement backToSubBrandListLink() { return getWebElement(pageName, "backToSubBrandsListLnk"); }

    public WebElement backToSubBrandListClickableLink() { return getWebElement(pageName, "backToSubBrandsListClickableLnk"); }

    public WebElement saveAndContinueButton() { return getWebElement(pageName, "saveAndContinueBtn"); }

    public WebElement noLoginMechanismErrorText() { return getWebElement(pageName, "noLoginMechanismErrorTxt"); }

    public WebElement loginMechanismCredentialsPrivateCheckBox() { return getWebElement(pageName, "loginMechanismCredentialsPrivateChkBx"); }

    public WebElement loginMechanismCredentialsPrivateResetPasswordButton() { return getWebElement(pageName, "loginMechanismCredentialsPrivateResetPasswordBtn"); }

    public WebElement loginMechanismSAMLPrivateCheckBox() { return getWebElement(pageName, "loginMechanismSAMLPrivateChkBx"); }

    public WebElement loginMechanismSAMLPrivateClickableCheckBox() { return getWebElement(pageName, "loginMechanismSAMLPrivateClickableChkBx"); }

    public WebElement loginMechanismSAMLPrivateExistsCheckBox() { return getWebElement(pageName, "loginMechanismSAMLPrivateExistsChkBx"); }

    public WebElement loginMechanismJWTPrivateCheckBox() { return getWebElement(pageName, "loginMechanismJWTPrivateChkBx"); }

    public WebElement loginMechanismJWTPrivateClickableCheckBox() { return getWebElement(pageName, "loginMechanismJWTPrivateClickableChkBx"); }

    public WebElement loginMechanismJWTPrivateExistsCheckBox() { return getWebElement(pageName, "loginMechanismJWTPrivateExistsChkBx"); }

    public WebElement credentialsLoginIdPrivateTextBox() { return getWebElement(pageName, "credentialsLoginIdPrivateTxtBx"); }

    public WebElement credentialsLoginIdPrivateErrorText() { return getWebElement(pageName, "credentialsLoginIdPrivateErrorText"); }

    public WebElement samlUploadCertificatePrivateTextBox() { return getWebElement(pageName, "samlUploadCertificatePrivateTxtBx"); }

    public WebElement samlUploadCertificatePrivateErrorText() { return getWebElement(pageName, "samlUploadCertificatePrivateErrorText"); }

    public WebElement samlUploadCertificatePrivateButton() { return getWebElement(pageName, "samlUploadCertificatePrivateBtn"); }

    public WebElement samlIssuerIdPrivateTextBox() { return getWebElement(pageName, "samlIssuerIdPrivateTxtBx"); }

    public WebElement samlIssuerIDPrivateErrorText() { return getWebElement(pageName, "samlIssuerIdPrivateErrorText"); }

    public WebElement samlSSOPostSourcePrivateTextBox() { return getWebElement(pageName, "samlSSOPostSourcePrivateTxtBx"); }

    public WebElement samlSSOPostSourcePrivateErrorText() { return getWebElement(pageName, "samlSSOPostSourcePrivateErrorText"); }

    public WebElement samlSSOKeepLiveReferralUrlPrivateTextBox() { return getWebElement(pageName, "samlSSOKeepLiveReferralUrlPrivateTxtBx"); }

    public WebElement samlSSOKeepLiveReferralUrlPrivateErrorText() { return getWebElement(pageName, "samlSSOKeepLiveReferralUrlPrivateErrorText"); }

    public WebElement jwtAlgorithmPrivateOneTextBox() { return getWebElement(pageName, "jwtAlgorithmPrivateOneTxtBx"); }

    public WebElement jwtMaximumExpirationTimePrivateOneTextBox() { return getWebElement(pageName, "jwtMaximumExpirationTimePrivateOneTxtBx"); }

    public WebElement jwtMaximumExpirationTimePrivateOneErrorText() { return getWebElement(pageName, "jwtMaximumExpirationTimePrivateOneErrorText"); }

    public WebElement jwtRSAPublicKeyPrivateOneTextArea() { return getWebElement(pageName, "jwtRSAPublicKeyPrivateOneTxtArea"); }

    public WebElement jwtRSAPublicKeyPrivateOneErrorText() { return getWebElement(pageName, "jwtRSAPublicKeyPrivateOneErrorText"); }

    public WebElement jwtAlgorithmPrivateTwoTextBox() { return getWebElement(pageName, "jwtAlgorithmPrivateTwoTxtBx"); }

    public WebElement jwtMaximumExpirationTimePrivateTwoTextBox() { return getWebElement(pageName, "jwtMaximumExpirationTimePrivateTwoTxtBx"); }

    public WebElement jwtMaximumExpirationTimePrivateTwoErrorText() { return getWebElement(pageName, "jwtMaximumExpirationTimePrivateTwoErrorText"); }

    public WebElement jwtRSAPublicKeyPrivateTwoTextArea() { return getWebElement(pageName, "jwtRSAPublicKeyPrivateTwoTxtArea"); }

    public WebElement jwtRSAPublicKeyPrivateTwoErrorText() { return getWebElement(pageName, "jwtRSAPublicKeyPrivateTwoErrorText"); }

    public WebElement addAnotherJWTPrivateBtn() { return getWebElement(pageName, "addAnotherJWTPrivateBtn"); }

    public WebElement addAnotherJWTEnableDisableStatusPrivateBtn() { return getWebElement(pageName, "addAnotherJWTEnableDisabledStatusPrivateBtn"); }

    public WebElement removeJWTPrivateBtn() { return getWebElement(pageName, "deleteJWTPrivateBtn"); }

    public void enterPrivateAuthSetupDetails(String loginMechanism, String credentialsLoginId,
                                             String samlCertificateName, String samlIssuerId,
                                             String ssoPostSource, String jwtMaximumExpirationTime,
                                             String jwtRSAPublicKey, boolean enableMultipleJWT,
                                             boolean addAnotherJWT, boolean editSAMLDetails) throws InterruptedException {

        log.info("**************************PRIVATE AUTHENTICATION SETUP**************************");

        if (loginMechanism.contains("SAML")) {

            if(!headerLabel().getText().contains("Edit")) {

                if(!loginMechanismSAMLPrivateCheckBox().isSelected())
                    loginMechanismSAMLPrivateCheckBox().click();
                Thread.sleep(1000);

                clearText(credentialsLoginIdPrivateTextBox());
                credentialsLoginIdPrivateTextBox().sendKeys(credentialsLoginId);
                Thread.sleep(1000);

                if(!samlCertificateName.equalsIgnoreCase("") && !samlCertificateName.equalsIgnoreCase(null))
                    if(!samlCertificateName.equalsIgnoreCase("EMPTY"))
                        samlUploadCertificatePrivateTextBox().sendKeys(GenericUtils.getSAMLCertificateAbsolutePath(samlCertificateName));

				/*
				 * clearText(samlIssuerIdPrivateTextBox());
				 * samlIssuerIdPrivateTextBox().sendKeys(samlIssuerId); Thread.sleep(1000);
				 * clearText(samlSSOPostSourcePrivateTextBox());
				 * samlSSOPostSourcePrivateTextBox().sendKeys(ssoPostSource);
				 */

            }else if(headerLabel().getText().contains("Edit")){

                if (!loginMechanismSAMLPrivateCheckBox().isSelected())
                    loginMechanismSAMLPrivateCheckBox().click();

                if(editSAMLDetails) {

                    if(credentialsLoginIdPrivateTextBox().isEnabled()) {
                        clearText(credentialsLoginIdPrivateTextBox());
                        credentialsLoginIdPrivateTextBox().sendKeys(credentialsLoginId);
                        Thread.sleep(1000);
                    }

                    Thread.sleep(1000);
                    if (!samlCertificateName.equalsIgnoreCase("") && !samlCertificateName.equalsIgnoreCase(null))
                        if (!samlCertificateName.equalsIgnoreCase("EMPTY"))
                            samlUploadCertificatePrivateTextBox().sendKeys(GenericUtils.getSAMLCertificateAbsolutePath(samlCertificateName));

					/*
					 * clearText(samlIssuerIdPrivateTextBox());
					 * samlIssuerIdPrivateTextBox().sendKeys(samlIssuerId); Thread.sleep(1000);
					 * clearText(samlSSOPostSourcePrivateTextBox());
					 * samlSSOPostSourcePrivateTextBox().sendKeys(ssoPostSource);
					 */
                }
            }

        }else{
            if(headerLabel().getText().contains("Edit")) {
                if (loginMechanismSAMLPrivateCheckBox().isSelected()) {
                    loginMechanismSAMLPrivateCheckBox().click();
                    log.info("Disabling SAML Since, Its Not Enabled In Edit Flow.");
                }
            }
        }

        if (loginMechanism.contains("JWT")) {

            if(!headerLabel().getText().contains("Edit")) {

                if(!loginMechanismJWTPrivateCheckBox().isSelected())
                    loginMechanismJWTPrivateCheckBox().click();

                if(enableMultipleJWT) {

                    String[] arrJWTRSAPublicKey = new String[2];
                    if (jwtRSAPublicKey.contains(";") || jwtRSAPublicKey=="" || jwtRSAPublicKey.contains("Loremipsum")) {
                        if(jwtRSAPublicKey==""){
                            arrJWTRSAPublicKey[0]="";
                            arrJWTRSAPublicKey[1]="";
                        }else if(jwtRSAPublicKey.contains("Loremipsum")){
                            arrJWTRSAPublicKey[0]=jwtRSAPublicKey;
                            arrJWTRSAPublicKey[1]=jwtRSAPublicKey;
                        }else
                            arrJWTRSAPublicKey = jwtRSAPublicKey.split(";");

                        clearText(jwtMaximumExpirationTimePrivateOneTextBox());
                        jwtMaximumExpirationTimePrivateOneTextBox().sendKeys(jwtMaximumExpirationTime);
                        Thread.sleep(1000);
                        clearTextArea(jwtRSAPublicKeyPrivateOneTextArea());
                        jwtRSAPublicKeyPrivateOneTextArea().sendKeys(arrJWTRSAPublicKey[0]);
                        Thread.sleep(1000);

                        if(addAnotherJWTPrivateBtn().isEnabled())
                            addAnotherJWTPrivateBtn().click();
                        Thread.sleep(1000);

                        clearText(jwtMaximumExpirationTimePrivateTwoTextBox());
                        jwtMaximumExpirationTimePrivateTwoTextBox().sendKeys(jwtMaximumExpirationTime);
                        Thread.sleep(1000);
                        clearTextArea(jwtRSAPublicKeyPrivateTwoTextArea());
                        jwtRSAPublicKeyPrivateTwoTextArea().sendKeys(arrJWTRSAPublicKey[1]);
                        Thread.sleep(1000);
                    }
                }
                else{
                    clearText(jwtMaximumExpirationTimePrivateOneTextBox());
                    jwtMaximumExpirationTimePrivateOneTextBox().sendKeys(jwtMaximumExpirationTime);
                    Thread.sleep(1000);
                    clearTextArea(jwtRSAPublicKeyPrivateOneTextArea());
                    jwtRSAPublicKeyPrivateOneTextArea().sendKeys(jwtRSAPublicKey);
                    Thread.sleep(1000);
                }
            }else{

                if(headerLabel().getText().contains("Edit")) {

                    if(loginMechanismJWTPrivateCheckBox().isSelected()){
                        if (addAnotherJWT) {
                            if (addAnotherJWTPrivateBtn().isEnabled()) {
                                addAnotherJWTPrivateBtn().click();
                                Thread.sleep(1000);
                                clearText(jwtMaximumExpirationTimePrivateTwoTextBox());
                                jwtMaximumExpirationTimePrivateTwoTextBox().sendKeys(jwtMaximumExpirationTime);
                                Thread.sleep(1000);
                                clearTextArea(jwtRSAPublicKeyPrivateTwoTextArea());
                                jwtRSAPublicKeyPrivateTwoTextArea().sendKeys(jwtRSAPublicKey);
                                Thread.sleep(1000);
                            } else {
                                log.info("Already Both The JWT Tokens Are Added. Hence, Deleting JWT Two. And Adding New JWT Two");
                                if (removeJWTPrivateBtn().isEnabled()) {
                                    removeJWTPrivateBtn().click();
                                    if(addAnotherJWTPrivateBtn().isEnabled())
                                        addAnotherJWTPrivateBtn().click();
                                    Thread.sleep(1000);
                                    clearText(jwtMaximumExpirationTimePrivateTwoTextBox());
                                    jwtMaximumExpirationTimePrivateTwoTextBox().sendKeys(jwtMaximumExpirationTime);
                                    Thread.sleep(1000);
                                    clearTextArea(jwtRSAPublicKeyPrivateTwoTextArea());
                                    jwtRSAPublicKeyPrivateTwoTextArea().sendKeys(jwtRSAPublicKey);
                                    Thread.sleep(1000);
                                }
                            }
                        }
                    }else if(!loginMechanismJWTPrivateCheckBox().isSelected()){
                        loginMechanismJWTPrivateCheckBox().click();
                        Thread.sleep(1000);
                        if(enableMultipleJWT){

                            String[] arrJWTRSAPublicKey = new String[2];
                            if (jwtRSAPublicKey.contains(";") || jwtRSAPublicKey=="" || jwtRSAPublicKey.contains("Loremipsum")) {
                                if(jwtRSAPublicKey==""){
                                    arrJWTRSAPublicKey[0]="";
                                    arrJWTRSAPublicKey[1]="";
                                }else if(jwtRSAPublicKey.contains("Loremipsum")){
                                    arrJWTRSAPublicKey[0]=jwtRSAPublicKey;
                                    arrJWTRSAPublicKey[1]=jwtRSAPublicKey;
                                }else
                                    arrJWTRSAPublicKey = jwtRSAPublicKey.split(";");

                                clearText(jwtMaximumExpirationTimePrivateOneTextBox());
                                jwtMaximumExpirationTimePrivateOneTextBox().sendKeys(jwtMaximumExpirationTime);
                                Thread.sleep(1000);
                                clearTextArea(jwtRSAPublicKeyPrivateOneTextArea());
                                jwtRSAPublicKeyPrivateOneTextArea().sendKeys(arrJWTRSAPublicKey[0]);
                                Thread.sleep(1000);

                                if(addAnotherJWTPrivateBtn().isEnabled())
                                    addAnotherJWTPrivateBtn().click();
                                Thread.sleep(1000);

                                clearText(jwtMaximumExpirationTimePrivateTwoTextBox());
                                jwtMaximumExpirationTimePrivateTwoTextBox().sendKeys(jwtMaximumExpirationTime);
                                Thread.sleep(1000);
                                clearTextArea(jwtRSAPublicKeyPrivateTwoTextArea());
                                jwtRSAPublicKeyPrivateTwoTextArea().sendKeys(arrJWTRSAPublicKey[1]);
                                Thread.sleep(1000);
                            }
                        }else{
                            clearText(jwtMaximumExpirationTimePrivateOneTextBox());
                            jwtMaximumExpirationTimePrivateOneTextBox().sendKeys(jwtMaximumExpirationTime);
                            Thread.sleep(1000);
                            clearTextArea(jwtRSAPublicKeyPrivateOneTextArea());
                            jwtRSAPublicKeyPrivateOneTextArea().sendKeys(jwtRSAPublicKey);
                            Thread.sleep(1000);
                        }
                    }
                }
            }
        }else if(headerLabel().getText().contains("Edit")) {
            if (loginMechanismJWTPrivateCheckBox().isSelected()) {
                loginMechanismJWTPrivateCheckBox().click();
                log.info("Disabling JWT Since, Its Not Enabled In Edit Flow.");
            }
        }
    }
}