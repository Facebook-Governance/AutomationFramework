/**
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 *
 * @author Rajeev Anantharaman Iyer
 */
package com.yodlee.customizationtool;

import com.yodlee.customizationtool.basetest.BaseTest;
import com.yodlee.customizationtool.constants.Constants;
import com.yodlee.customizationtool.page.LoginPage;
import com.yodlee.customizationtool.page.ViewListOfSubBrandsPage;
import org.databene.benerator.anno.Source;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


public class Login extends BaseTest {

    private Logger log = LoggerFactory.getLogger(Login.class);

    LoginPage loginPage;
    ViewListOfSubBrandsPage viewListOfSubBrandsPage;

    @BeforeTest
    public void setup() {
        loginPage = PageFactory.initElements(getWebdriver(), LoginPage.class);
        viewListOfSubBrandsPage = PageFactory.initElements(getWebdriver(), ViewListOfSubBrandsPage.class);
    }

    @AfterClass
    public  void logout(){
        viewListOfSubBrandsPage.logout();
    }

    @Test(priority = 0, enabled = true)
    public void testValidateLoginPageContents(){

        SoftAssert softAssert = new SoftAssert();

        /*Headee Was removed as per Product Feedback
         * softAssert.assertTrue(loginPage.isHeaderLabelPresent(),
                "Login Page: Header Sub-Brand Management Not Present. Please Check!");
        log.info("Login Page - Header Label Validated Successfully");

        softAssert.assertEquals(loginPage.headerLabel().getText(), Constants.PAGE_HEADER_SUB_BRAND_MANAGEMENT,
                "Login Page: The Actual And Expected Page Headers Do Not Match. "+
                        "Actual Is "+loginPage.headerLabel().getText()+" Expected Is: "+Constants.PAGE_HEADER_SUB_BRAND_MANAGEMENT);*/

        softAssert.assertTrue(loginPage.isYodleeIDTextBoxPresent(),
                "Login Page: Yodlee ID Login Text Box Not Present. Please Check!");
        log.info("Login Page - Yodlee ID Text Box Is Present.");
        softAssert.assertTrue(loginPage.isPasswordTextBoxPresent(),
                "Login Page: Password Text Box Not Present. Please Check!");
        log.info("Login Page - Password Text Box Is Present.");
        softAssert.assertTrue(loginPage.isLoginButtonPresent(),
                "Login Page: Login Button Not Present. Please Check!");
        log.info("Login Page - Login Button Is Present.");
        softAssert.assertTrue(loginPage.isLogoImgPresent(),
                "Login Page: Envestnet Yodlee Logo Not Present. Please Check!");
        log.info("Login Page - Envestnet Yodlee Logo Is Present.");
        softAssert.assertTrue(loginPage.isForgotPasswordLinkPresent(),
                "Login Page: Forgot Password? Link Not Present. Please Check!");
        log.info("Login Page - Forgot Password Link Is Present.");

        softAssert.assertEquals(loginPage.getGhostTextUsernameTextBox(),
                dataProperty.getLoginPageUserIdGhostText(),
                "Login Page: Actual And Expected User Id Ghost Text Text Is Not Matching. Actual is: "+
                        loginPage.getGhostTextUsernameTextBox()+" Expected Is: "
                        +dataProperty.getLoginPageUserIdGhostText());

        softAssert.assertEquals(loginPage.getGhostTextPasswordTextBox(),
                dataProperty.getLoginPagePasswordGhostText(),
                "Login Page: Actual And Expected Password Ghost Text Is Not Matching. Actual is: "+
                        loginPage.getGhostTextPasswordTextBox()+" Expected Is: "
                        +dataProperty.getLoginPagePasswordGhostText());

        log.info("Login Page - User Id Format Default Text Is Present.");
        softAssert.assertTrue(loginPage.isLoginButtonEnabled(),
                "Login Page: Login Button Not Enabled. Please Check!");
        log.info("Login Page - Login Button Is Enabled By Default.");
        softAssert.assertAll();
    }

    @Test(priority = 1, enabled = true)
    public void testValidateMessageOnClickingForgotPasswordLink(){

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(loginPage.isYodleeIDTextBoxPresent(),
                "Login Page: Yodlee ID Login Text Box Not Present. Please Check!");
        log.info("Login Page - Yodlee ID Text Box Is Present.");

        loginPage.clickForgotPassword();
        String alert = loginPage.getAlertText();
        softAssert.assertEquals(alert,dataProperty.getForgotPasswordOnClickMessage(),
                "Login Page: Actual and Expected Errors Do not Match. Actual is "+alert+" " +
                        "Expected is "+dataProperty.getForgotPasswordOnClickMessage());
        log.info("Login Page: Forgot Password On Click, Message Verification Successful.");
        softAssert.assertAll();
    }

    @Test(dataProvider="feeder", enabled = true, priority = 2)
    @Source("src\\test\\resources\\testdata\\ValidateLogin.csv")
    public void testValidateLogin(String testId, String enabled, String testName,
                                  String loginUsername, String loginPassword,
                                  String errorMessage) throws InterruptedException {

        if(enabled.equalsIgnoreCase("True")) {
            SoftAssert softAssert = new SoftAssert();

            softAssert.assertTrue(loginPage.isYodleeIDTextBoxPresent(),
                    "Login Page: Yodlee ID Login Text Box Not Present. Please Check!");

            log.info("Successfully Loaded Login Page");
            loginPage.loginToSubBrandManagementPortal(loginUsername, loginPassword);

            if (testName.equalsIgnoreCase("ValidInput")) {
                softAssert.assertTrue(viewListOfSubBrandsPage.isPresentCreateSubBrandButton(),
                        "View List Of Sub Brand Page Not Displayed Post Login. Please Check!");
                log.info("Successfully Logged In To Sub-Brand Management Portal Page");

            } else {

                WebDriverWait wait = new WebDriverWait(getWebdriver(), 10);
                wait.until(ExpectedConditions.visibilityOf(loginPage.alertText()));

                String alert = loginPage.getAlertText();
                softAssert.assertEquals(alert, errorMessage,
                        "Login Page: Actual And Expected Errors Do Not Match. Actual is " + alert + " " +
                                "Expected Is " + errorMessage);
                if(loginPage.closeAlertToolTip().isDisplayed())
                    loginPage.closeAlertToolTip().click();
                softAssert.assertTrue(loginPage.isUsernameTextBoxEmpty(), "Login Page: Username Text Box Is Not Empty. Please Check!");
                softAssert.assertTrue(loginPage.isPasswordTextBoxEmpty(), "Login Page: Password Text Box Is Not Empty. Please Check!");
                if (testName.equalsIgnoreCase("NoInput")) {
                    softAssert.assertEquals(loginPage.userIdFormatDefaultText().getText(),dataProperty.getLoginPageUserIDBlankErrorText(),
                            "Login Page: Actual And the Expected Do Not Match When Blank User Id Is Mentioned, Actual is "+
                                    loginPage.userIdFormatDefaultText().getText()+" Expected is "+dataProperty.getLoginPageUserIDBlankErrorText());
                    softAssert.assertEquals(loginPage.passwordFormatDefaultText().getText(),dataProperty.getLoginPagePasswordBlankErrorText(),
                            "Login Page: Actual And the Expected Do Not Match When Blank User Id Is Mentioned, Actual is "+
                                    loginPage.passwordFormatDefaultText().getText()+" Expected is "+dataProperty.getLoginPagePasswordBlankErrorText());
                }
            }
            softAssert.assertAll();
        }else{
            log.info("TEST CASE - "+testId +" - "+ testName +" NOT ENABLED. HENCE, SKIPPING");
        }
    }
}