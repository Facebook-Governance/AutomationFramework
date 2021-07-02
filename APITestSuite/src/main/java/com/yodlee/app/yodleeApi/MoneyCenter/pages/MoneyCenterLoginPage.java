/*******************************************************************************
 *
 * * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * *
 *
 * * This software is the confidential and proprietary information of Yodlee, Inc.
 *
 * * Use is subject to license terms.
 *
 *******************************************************************************/
package com.yodlee.app.yodleeApi.MoneyCenter.pages;

import com.yodlee.app.yodleeApi.webdriver.*;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.DBHelper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

/**
 * Created by smishra8 on 06-04-2017.
 */
public class MoneyCenterLoginPage {

    WebDriver driver;
    protected Logger logger = LoggerFactory.getLogger(MoneyCenterLoginPage.class);
    public MoneyCenterLoginPage(WebDriver driver) {
        this.driver=driver;
        logger.info("*********** Initializing "+ this.getClass().getSimpleName() + "***********");;
        PageFactory.initElements(driver, this);
    }

    boolean isFirstTimeLogin=false;

    String mfaLoginFlowUrl = "mfaloginflow";

    @FindBy(id = "loginName")
    public WebElement txtLoginId;

    @FindBy(name = "Submit")
    public WebElement btnNext;

    @FindBy(id = "password")
    public WebElement txtPassword;

    @FindBy(xpath = "//input[@type='submit']")
    public WebElement btnLogin;

    @FindBy(xpath = "//input[@value='Continue']")
    WebElement btnContinue;

    @FindBy(linkText = "Cancel")
    WebElement cancelLink;

    @FindBy(id = "pma0")
    WebElement mfaLoginFlowAAns1;

    @FindBy(id = "pma1")
    WebElement mfaLoginFlowAAns2;

    @FindBy(id = "pma2")
    WebElement mfaLoginFlowAAns3;

    @FindBy(xpath = "//div[@class='body']/descendant::input[@type='radio']")
    public List<WebElement> siteVerifyImages;

    @FindBy(id = "name_image")
    WebElement txtSecretPhrase;

    @FindBy(xpath = "//h1[@class='help']")
    WebElement txtfileNotFound;

    @FindBy(id="answer")
    WebElement txtSecurityAnswer;

    @FindBy(id = "remember_computer")
    WebElement chkbxRememberMe;

    @FindBy(xpath = "//input[@value='I Accept']")
    WebElement btnAccept;

    @FindBy(id = "emailConfirmationCode")
    WebElement txtEmailConfirmationCode;

    @FindBy(xpath = "//input[@value='Confirm']")
    WebElement btnConfirm;

    @FindBy(xpath = "//span[@class='succeeded']")
    WebElement confirmedText;

    @FindBy(id="loadingIndicator")
    WebElement loadingIndicator;

    public void login(String userId, String password,boolean isFirstTimeLogin) {
        try {
            DriverFactory.waitForPageToLoad();
            txtLoginId.sendKeys(userId);
            btnNext.click();

            if (isFirstTimeLogin) {
               // DriverFactory.getWait().until(ExpectedConditions.elementToBeClickable(btnContinue));

                if(!DriverFactory.waitForElement(btnContinue)){
                    Assert.fail("Not able to proceed with ");
                }
                btnContinue.click();
                DriverFactory.getWait().until(ExpectedConditions.presenceOfElementLocated(DriverFactory.getByFromWebElement(txtPassword)));
                txtPassword.sendKeys(password);
                btnContinue.click();
                DriverFactory.waitForPageToLoad();
                mfaLoginFlowAAns1.sendKeys("1");
                mfaLoginFlowAAns2.sendKeys("1");
                mfaLoginFlowAAns3.sendKeys("1");
                siteVerifyImages.get(0).click();
                txtSecretPhrase.sendKeys("1");
                txtSecretPhrase.submit();
                if (driver.getCurrentUrl().contains("yodlee_file_not_found")) {
                   // isFirstTimeLogin=false;
                    this.isFirstTimeLogin=isFirstTimeLogin;
                    //driver.get(InitialLoader.moneyCenterUrl);
                    open();
                    reLogin(userId, password);
                }
            }else {

                existingUserLogin(userId,password);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void reLogin(String userName, String password) {


        if(isFirstTimeLogin) {

            txtLoginId.sendKeys(userName);
            btnLogin.click();
            DriverFactory.waitForPageToLoad();
            enterSecurityAnswerIfDisplayedAndContinue();
            DriverFactory.waitForPageToLoad();
            txtPassword.sendKeys(password);
            btnLogin.click();
            DriverFactory.waitForPageToLoad();
            btnAccept.click();
            String confirmMationCode=getConfirmationCodeFromDb(userName);
            txtEmailConfirmationCode.sendKeys(confirmMationCode);
            btnConfirm.click();
            try {
                DriverFactory.getWait().
                        until(ExpectedConditions.presenceOfElementLocated(
                                By.xpath("//span[@class='succeeded']")));
            }catch (Exception e){
               // e.printStackTrace();
            }
            btnContinue.click();

        }
    }



    private void existingUserLogin(String userName,String password){
       // txtLoginId.sendKeys(userName);
       // btnLogin.click();
       // DriverFactory.waitUntilLoads();
        enterSecurityAnswerIfDisplayedAndContinue();
        txtPassword.sendKeys(password);
        btnLogin.click();

    }


    private void enterSecurityAnswerIfDisplayedAndContinue(){
        try{
            //DriverFactory.waitForElementToVanish();
            if(txtSecurityAnswer.isDisplayed()|| chkbxRememberMe.isDisplayed()){
                txtSecurityAnswer.sendKeys("1");
                chkbxRememberMe.click();
                btnContinue.click();

            }

        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    private String getConfirmationCodeFromDb(String loginName){
        String confirmationCode=null;
        ResultSet rs=null;
        DBHelper dbHelper=new DBHelper();
        Connection conn=dbHelper.getDBConnection();
        String query="select * from mem_alert_Dest where mem_id in (Select mem_id from mem where LOGIN_NAME_ASUCASE='"+loginName.toUpperCase()+"')";
        try {
        	rs=dbHelper.getResultSet(conn, query);
            Thread.sleep(1000);
            
            if (rs.next()) {
            	confirmationCode = rs.getString("VERIFICATION_CODE");
			} else {
				throw new Exception("no value found.");
			}
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
        	dbHelper.closeConnectionStatementResultSet(conn, null, rs);
        }
        return  confirmationCode;
    }

    public void open(){
        driver.get(Configuration.getInstance().getMoneyCenterUrl());
    }
}
