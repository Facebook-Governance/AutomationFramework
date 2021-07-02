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

public class PublicAuthenticationSetupPage extends Page {

    private Logger log = LoggerFactory.getLogger(PublicAuthenticationSetupPage.class);

    public String pageName = "PUBLIC_AUTHENTICATION_SETUP_PAGE";

    public PublicAuthenticationSetupPage(WebDriver driver) {
        super(driver);
    }

    public WebElement headerLabel() {
        return getWebElement(pageName, "publicAuthSetupPageLbl");
    }

    public WebElement stepHeaderPublicAuthSetupLabel() {
        return getWebElement(pageName, "paspStepHeader");
    }

    public List<WebElement> stepHeaderLabels() {
        return getElementsFromPage(pageName, "paspStepHeader");
    }

    public WebElement backToPreviousStepButton() { return getWebElement(pageName, "backToPreviousStepBtn"); }

    public WebElement backToSubBrandListLink() { return getWebElement(pageName, "backToSubBrandsListLnk"); }

    public WebElement saveAndContinueButton() { return getWebElement(pageName, "saveAndContinueBtn"); }

    public WebElement credentialsLoginIdPublicErrorText() { return getWebElement(pageName, "credentialsLoginIdPublicErrorText"); }

    public WebElement samlIssuerIDPublicErrorText() { return getWebElement(pageName, "samlIssuerIdPublicErrorText"); }

    public WebElement samlSSOPostSourcePublicErrorText() { return getWebElement(pageName, "samlSSOPostSourcePublicErrorText"); }

    public WebElement jwtMaximumExpirationTimePublicOneErrorText() { return getWebElement(pageName, "jwtMaximumExpirationTimePublicOneErrorText"); }

    public WebElement jwtRSAPublicKeyPublicOneErrorText() { return getWebElement(pageName, "jwtRSAPublicKeyPublicOneErrorText"); }

    public WebElement jwtMaximumExpirationTimePublicTwoErrorText() { return getWebElement(pageName, "jwtMaximumExpirationTimePublicTwoErrorText"); }

    public WebElement jwtRSAPublicKeyPublicTwoErrorText() { return getWebElement(pageName, "jwtRSAPublicKeyPublicTwoErrorText"); }

    public WebElement loginMechanismCredentialsPublicCheckBox() { return getWebElement(pageName, "loginMechanismCredentialsPublicChkBx"); }

    public WebElement loginMechanismCredentialsPublicResetPasswordButton() { return getWebElement(pageName, "loginMechanismCredentialsPublicResetPasswordBtn"); }

    public WebElement loginMechanismSAMLPublicCheckBox() { return getWebElement(pageName, "loginMechanismSAMLPublicChkBx"); }

    public WebElement loginMechanismSAMLPublicClickableCheckBox() { return getWebElement(pageName, "loginMechanismSAMLPublicClickableChkBx"); }

    public WebElement loginMechanismSAMLPublicExistsCheckBox() { return getWebElement(pageName, "loginMechanismSAMLPublicExistsChkBx"); }

    public WebElement loginMechanismJWTPublicCheckBox() { return getWebElement(pageName, "loginMechanismJWTPublicChkBx"); }

    public WebElement loginMechanismJWTPublicClickableCheckBox() { return getWebElement(pageName, "loginMechanismJWTPublicClickableChkBx"); }

    public WebElement loginMechanismJWTPublicExistsCheckBox() { return getWebElement(pageName, "loginMechanismJWTPublicExistsChkBx"); }

    public WebElement credentialsLoginIdPublicTextBox() { return getWebElement(pageName, "credentialsLoginIdPublicTxtBx"); }

    public WebElement samlUploadCertificatePublicTextBox() { return getWebElement(pageName, "samlUploadCertificatePublicTxtBx"); }

    public WebElement samlUploadCertificatePublicErrorText() { return getWebElement(pageName, "samlUploadCertificatePublicErrorText"); }

    public WebElement samlUploadCertificatePublicButton() { return getWebElement(pageName, "samlUploadCertificatePublicBtn"); }

    public WebElement samlIssuerIdPublicTextBox() { return getWebElement(pageName, "samlIssuerIdPublicTxtBx"); }

    public WebElement samlSSOPostSourcePublicTextBox() { return getWebElement(pageName, "samlSSOPostSourcePublicTxtBx"); }

    public WebElement samlSSOKeepLiveReferralUrlPublicTextBox() { return getWebElement(pageName, "samlSSOKeepLiveReferralUrlPublicTxtBx"); }

    public WebElement samlSSOKeepLiveReferralUrlPublicErrorText() { return getWebElement(pageName, "samlSSOKeepLiveReferralUrlPublicErrorText"); }

    public WebElement jwtAlgorithmPublicOneTextBox() { return getWebElement(pageName, "jwtAlgorithmPublicOneTxtBx"); }

    public WebElement jwtMaximumExpirationTimePublicOneTextBox() { return getWebElement(pageName, "jwtMaximumExpirationTimePublicOneTxtBx"); }

    public WebElement jwtRSAPublicKeyPublicOneTextArea() { return getWebElement(pageName, "jwtRSAPublicKeyPublicOneTxtArea"); }

    public WebElement jwtAlgorithmPublicTwoTextBox() { return getWebElement(pageName, "jwtAlgorithmPublicTwoTxtBx"); }

    public WebElement jwtMaximumExpirationTimePublicTwoTextBox() { return getWebElement(pageName, "jwtMaximumExpirationTimePublicTwoTxtBx"); }

    public WebElement jwtRSAPublicKeyPublicTwoTextArea() { return getWebElement(pageName, "jwtRSAPublicKeyPublicTwoTxtArea"); }

    public WebElement addAnotherJWTPublicBtn() { return getWebElement(pageName, "addAnotherJWTPublicBtn"); }

    public WebElement addAnotherJWTEnableDisableStatusPublicBtn() { return getWebElement(pageName, "addAnotherJWTEnableDisabledStatusPublicBtn"); }

    public WebElement removeJWTPublicBtn() { return getWebElement(pageName, "deleteJWTPublicBtn"); }

    public void enterPublicAuthSetupDetails(String loginMechanism, String credentialsLoginId,
                                            String samlCertificateName, String samlIssuerId,
                                            String ssoPostSource, String jwtMaximumExpirationTime,
                                            String jwtRSAPublicKey, boolean enableMultipleJWT,
                                            boolean addAnotherJWT, boolean editSAMLDetails) throws InterruptedException {

        log.info("**************************PUBLIC AUTHENTICATION SETUP**************************");

        if (loginMechanism.contains("SAML")) {

            if(!headerLabel().getText().contains("Edit")) {

                clearText(credentialsLoginIdPublicTextBox());
                credentialsLoginIdPublicTextBox().sendKeys(credentialsLoginId);
                Thread.sleep(1000);

                if(!samlCertificateName.equalsIgnoreCase("") && !samlCertificateName.equalsIgnoreCase(null))
                    if(!samlCertificateName.equalsIgnoreCase("EMPTY"))
                        samlUploadCertificatePublicTextBox().sendKeys(GenericUtils.getSAMLCertificateAbsolutePath(samlCertificateName));

				/*
				 * // clearText(samlIssuerIdPublicTextBox()); //
				 * samlIssuerIdPublicTextBox().sendKeys(samlIssuerId); Thread.sleep(1000); //
				 * clearText(samlSSOPostSourcePublicTextBox()); //
				 * samlSSOPostSourcePublicTextBox().sendKeys(ssoPostSource);
				 */
            }else if(headerLabel().getText().contains("Edit")){

                if(editSAMLDetails) {

                    if(credentialsLoginIdPublicTextBox().isEnabled()) {
                        clearText(credentialsLoginIdPublicTextBox());
                        credentialsLoginIdPublicTextBox().sendKeys(credentialsLoginId);
                        Thread.sleep(1000);
                    }

                    Thread.sleep(1000);
                    if (!samlCertificateName.equalsIgnoreCase("") && !samlCertificateName.equalsIgnoreCase(null))
                        if (!samlCertificateName.equalsIgnoreCase("EMPTY"))
                            samlUploadCertificatePublicTextBox().sendKeys(GenericUtils.getSAMLCertificateAbsolutePath(samlCertificateName));

					/*
					 * clearText(samlIssuerIdPublicTextBox());
					 * samlIssuerIdPublicTextBox().sendKeys(samlIssuerId); Thread.sleep(1000);
					 * clearText(samlSSOPostSourcePublicTextBox());
					 * samlSSOPostSourcePublicTextBox().sendKeys(ssoPostSource);
					 */
                }
            }

        }

        if (loginMechanism.contains("JWT")) {

            if(!headerLabel().getText().contains("Edit")) {

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

                        clearText(jwtMaximumExpirationTimePublicOneTextBox());
                        jwtMaximumExpirationTimePublicOneTextBox().sendKeys(jwtMaximumExpirationTime);
                        Thread.sleep(1000);
                        clearTextArea(jwtRSAPublicKeyPublicOneTextArea());
                        jwtRSAPublicKeyPublicOneTextArea().sendKeys(arrJWTRSAPublicKey[0]);
                        Thread.sleep(1000);

                        if(addAnotherJWTPublicBtn().isEnabled())
                            addAnotherJWTPublicBtn().click();
                        Thread.sleep(1000);

                        clearText(jwtMaximumExpirationTimePublicTwoTextBox());
                        jwtMaximumExpirationTimePublicTwoTextBox().sendKeys(jwtMaximumExpirationTime);
                        Thread.sleep(1000);
                        clearTextArea(jwtRSAPublicKeyPublicTwoTextArea());
                        jwtRSAPublicKeyPublicTwoTextArea().sendKeys(arrJWTRSAPublicKey[1]);
                        Thread.sleep(1000);
                    }
                }
                else{
                    clearText(jwtMaximumExpirationTimePublicOneTextBox());
                    jwtMaximumExpirationTimePublicOneTextBox().sendKeys(jwtMaximumExpirationTime);
                    Thread.sleep(1000);
                    clearTextArea(jwtRSAPublicKeyPublicOneTextArea());
                    jwtRSAPublicKeyPublicOneTextArea().sendKeys(jwtRSAPublicKey);
                    Thread.sleep(1000);
                }
            }else{

                if(headerLabel().getText().contains("Edit")) {

                    if (addAnotherJWT) {
                        if (addAnotherJWTPublicBtn().isEnabled()) {
                            addAnotherJWTPublicBtn().click();
                            Thread.sleep(1000);
                            clearText(jwtMaximumExpirationTimePublicTwoTextBox());
                            jwtMaximumExpirationTimePublicTwoTextBox().sendKeys(jwtMaximumExpirationTime);
                            Thread.sleep(1000);
                            clearTextArea(jwtRSAPublicKeyPublicTwoTextArea());
                            jwtRSAPublicKeyPublicTwoTextArea().sendKeys(jwtRSAPublicKey);
                            Thread.sleep(1000);
                        } else {
                            log.info("Already Both The JWT Tokens Are Added. Hence, Skipping.");
                            if (removeJWTPublicBtn().isEnabled()) {
                                removeJWTPublicBtn().click();
                                if(addAnotherJWTPublicBtn().isEnabled())
                                    addAnotherJWTPublicBtn().click();
                                Thread.sleep(1000);
                                clearText(jwtMaximumExpirationTimePublicTwoTextBox());
                                jwtMaximumExpirationTimePublicTwoTextBox().sendKeys(jwtMaximumExpirationTime);
                                Thread.sleep(1000);
                                clearTextArea(jwtRSAPublicKeyPublicTwoTextArea());
                                jwtRSAPublicKeyPublicTwoTextArea().sendKeys(jwtRSAPublicKey);
                                Thread.sleep(1000);
                            }
                        }
                    }else {
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

								/*
								 * clearText(jwtMaximumExpirationTimePublicOneTextBox());
								 * jwtMaximumExpirationTimePublicOneTextBox().sendKeys(jwtMaximumExpirationTime)
								 * ;
								 */
								/*
								 * Thread.sleep(1000); clearTextArea(jwtRSAPublicKeyPublicOneTextArea());
								 * jwtRSAPublicKeyPublicOneTextArea().sendKeys(arrJWTRSAPublicKey[0]);
								 */
                                Thread.sleep(1000);

                                if(addAnotherJWTPublicBtn().isEnabled())
                                    addAnotherJWTPublicBtn().click();
                                Thread.sleep(1000);

                                clearText(jwtMaximumExpirationTimePublicTwoTextBox());
                                jwtMaximumExpirationTimePublicTwoTextBox().sendKeys(jwtMaximumExpirationTime);
                                Thread.sleep(1000);
                                clearTextArea(jwtRSAPublicKeyPublicTwoTextArea());
                                jwtRSAPublicKeyPublicTwoTextArea().sendKeys(arrJWTRSAPublicKey[1]);
                                Thread.sleep(1000);
                            }
                        }else{
                            clearText(jwtMaximumExpirationTimePublicOneTextBox());
                            jwtMaximumExpirationTimePublicOneTextBox().sendKeys(jwtMaximumExpirationTime);
                            Thread.sleep(1000);
                            clearTextArea(jwtRSAPublicKeyPublicOneTextArea());
                            jwtRSAPublicKeyPublicOneTextArea().sendKeys(jwtRSAPublicKey);
                            Thread.sleep(1000);
                        }
                    }
                }
            }
        }
    }
}