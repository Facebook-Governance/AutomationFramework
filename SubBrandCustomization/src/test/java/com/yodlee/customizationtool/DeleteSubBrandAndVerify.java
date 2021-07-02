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
import com.yodlee.customizationtool.core.SeleniumWrapper;
import com.yodlee.customizationtool.page.*;
import com.yodlee.customizationtool.util.GenerateJWTKeys;
import com.yodlee.customizationtool.util.GenericUtils;
import org.databene.benerator.anno.Source;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import java.util.*;
import java.util.stream.Collectors;

public class DeleteSubBrandAndVerify extends BaseTest {

	private Logger log = LoggerFactory.getLogger(DeleteSubBrandAndVerify.class);

	LoginPage loginPage;
	ViewListOfSubBrandsPage viewListOfSubBrandsPage;
	CreateSubBrandPage createSubBrandPage;
	ContactDetailsPage contactDetailsPage;
	GeneralConfigurationPage generalConfigurationPage;
	PublicAuthenticationSetupPage publicAuthenticationSetupPage;
	PrivateAuthenticationSetupPage privateAuthenticationSetupPage;
	ProductsAndAppsSelectionPage productsAndAppsSelectionPage;
	SubBrandOverViewPage subBrandOverViewPage;
	ReviewPage reviewPage;
	CustomizationToolFeaturesPage customizationToolFeaturesPage;
	CustomizationToolDesignPage customizationToolDesignPage;
	CustomizationToolTextPage customizationToolTextPage;
	CustomizationToolCommonPage customizationToolCommonPage;

	String subBrandName;
	String domainNameSubString;

	@BeforeClass
	public void init() throws InterruptedException {

		loginPage.loginToSubBrandManagementPortal(dataProperty.getLoginUsername(), dataProperty.getLoginPassword());
		Thread.sleep(2000);

	}

	@BeforeTest
	public void setup() {

		loginPage = PageFactory.initElements(getWebdriver(), LoginPage.class);
		viewListOfSubBrandsPage = PageFactory.initElements(getWebdriver(), ViewListOfSubBrandsPage.class);
		createSubBrandPage = PageFactory.initElements(getWebdriver(), CreateSubBrandPage.class);
		generalConfigurationPage = PageFactory.initElements(getWebdriver(), GeneralConfigurationPage.class);
		privateAuthenticationSetupPage = PageFactory.initElements(getWebdriver(), PrivateAuthenticationSetupPage.class);
		publicAuthenticationSetupPage = PageFactory.initElements(getWebdriver(), PublicAuthenticationSetupPage.class);
		productsAndAppsSelectionPage = PageFactory.initElements(getWebdriver(), ProductsAndAppsSelectionPage.class);
		contactDetailsPage = PageFactory.initElements(getWebdriver(), ContactDetailsPage.class);
		reviewPage = PageFactory.initElements(getWebdriver(), ReviewPage.class);
		subBrandOverViewPage = PageFactory.initElements(getWebdriver(), SubBrandOverViewPage.class);
		customizationToolFeaturesPage = PageFactory.initElements(getWebdriver(), CustomizationToolFeaturesPage.class);
		customizationToolDesignPage = PageFactory.initElements(getWebdriver(), CustomizationToolDesignPage.class);
		customizationToolTextPage = PageFactory.initElements(getWebdriver(), CustomizationToolTextPage.class);
		customizationToolCommonPage = PageFactory.initElements(getWebdriver(), CustomizationToolCommonPage.class);
	}

	@AfterClass
	public void logout() {
		viewListOfSubBrandsPage.logout();
	}

	public void testGeneralConfigurationNegativeValidations(String testId, String enabled, String testName)
			throws InterruptedException {

		getWebdriver().navigate().refresh();

		SoftAssert softAssert = new SoftAssert();

		subBrandName = dataProperty.getSubBrandName() + GenericUtils.getTimStamp();
		domainNameSubString = dataProperty.getDomainNameSubString() + GenericUtils.getTimStamp();

		Assert.assertTrue(viewListOfSubBrandsPage.isPresentCreateSubBrandButton(),
				"View List Of Sub Brand Page Not Displayed/Create Sub-Brand Button Not Found. Please Check!");
		log.info("Create Sub-Brand Button Found.");

		if (enabled.equalsIgnoreCase("True")) {

			viewListOfSubBrandsPage.clickCreateSubBrandButton();
			Thread.sleep(2000);

			Assert.assertEquals(generalConfigurationPage.stepHeaderGenConfigLabel().getText().trim(),
					Constants.GENERAL_CONFIGURATION,
					"Create Sub-Brand: General Configuration Page: Actual And Expected Sub Headers Do Not Match."
							+ "Actual Is: " + generalConfigurationPage.stepHeaderGenConfigLabel().getText().trim()
							+ " Expected Is: " + Constants.GENERAL_CONFIGURATION);

			Assert.assertTrue(generalConfigurationPage.subBrandNameTextBox().isDisplayed(),
					"Create Sub-Brand: General Configuration Page: Sub Brand Name Text Box Not Displayed.");

			if (!generalConfigurationPage.channelPartnerRadioButton().isSelected())
				Assert.assertTrue(generalConfigurationPage.channelPartnerRadioButton().isSelected(),
						"Create Sub-Brand: General Configuration Page: Channel Partner Radio Button Not Selected By Default.");

			if (testName.equalsIgnoreCase("EmptySubBrandDomainNameIPAddress")) {
				generalConfigurationPage.enterGeneralConfigDetails(dataProperty.getEmptySubBrandName(),
						dataProperty.getEmptyDomainNameSubString(), dataProperty.getEmptySourceIpAddress(),
						dataProperty.getCustomerType());
				generalConfigurationPage.saveAndContinueButton().click();
				Thread.sleep(2000);

				softAssert.assertEquals(generalConfigurationPage.subBrandNameErrorText().getText().trim(),
						dataProperty.getEmptySubBrandNameErrorText(),
						"Create Sub-Brand: General Configuration Page: When Blank Sub Brand Name Is Specified, Actual & Expected Errors Are Not Matching. Actual Is: "
								+ generalConfigurationPage.subBrandNameErrorText().getText() + " Expected Is: "
								+ dataProperty.getEmptySubBrandNameErrorText());
				softAssert.assertEquals(generalConfigurationPage.domainNameSubStringErrorText().getText().trim(),
						dataProperty.getEmptyDomainNameSubStringErrorText(),
						"Create Sub-Brand: General Configuration Page: When Blank Domain Name Sub String Is Specified, Actual & Expected Errors Are Not Matching. Actual Is: "
								+ generalConfigurationPage.domainNameSubStringErrorText().getText() + " Expected Is: "
								+ dataProperty.getEmptyDomainNameSubStringErrorText());

			} else if (testName.equalsIgnoreCase("DuplicateIPSubBrandNameAndDomainNameGreaterThan50Characters")) {
				generalConfigurationPage.enterGeneralConfigDetails(dataProperty.getSubBrandNameExceed50Characters(),
						dataProperty.getDomainNameSubStringExceeds50Characters(),
						dataProperty.getDuplicateSourceIPAddress(), dataProperty.getCustomerType());
				// commenting this because when we click on saveAndContinue Button the IP
				// Address
				// duplicate error is disappearing. -15-Sep-2019 - Rajeev
				// generalConfigurationPage.saveAndContinueButton().click();
				Thread.sleep(2000);

				softAssert.assertEquals(generalConfigurationPage.subBrandNameErrorText().getText().trim(),
						dataProperty.getSubBrandNameExceed50CharactersErrorText(),
						"Create Sub-Brand: General Configuration Page: When Sub Brand Name More Than 50 Characters Specified, "
								+ "Actual & Expected Errors Are Not Matching. Actual Is: "
								+ generalConfigurationPage.subBrandNameErrorText().getText() + " Expected Is: "
								+ dataProperty.getSubBrandNameExceed50CharactersErrorText());
				softAssert.assertEquals(generalConfigurationPage.domainNameSubStringErrorText().getText().trim(),
						dataProperty.getDomainNameSubStringExceeds50CharactersErrorText(),
						"Create Sub-Brand: General Configuration Page: When Domain Name Sub String More Than 50 Characters Specified, "
								+ "Actual & Expected Errors Are Not Matching. Actual Is: "
								+ generalConfigurationPage.domainNameSubStringErrorText().getText() + " Expected Is: "
								+ dataProperty.getDomainNameSubStringExceeds50CharactersErrorText());
				softAssert.assertEquals(generalConfigurationPage.sourceIPAddressErrorText().getText().trim(),
						dataProperty.getDuplicateSourceIPAddressErrorText(),
						"Create Sub-Brand: General Configuration Page: When Duplicate Source IP Address Is Specified, "
								+ "Actual & Expected Errors Are Not Matching. Actual Is: "
								+ generalConfigurationPage.sourceIPAddressErrorText().getText() + " Expected Is: "
								+ dataProperty.getDuplicateSourceIPAddressErrorText());
				// Clean Up
				generalConfigurationPage.removeIPAddress(dataProperty.getDuplicateSourceIPAddress());

			} else if (testName.equalsIgnoreCase("MoreThan5IPAddressesAndSubBrandDomainNameLessThan2Characters")) {

				generalConfigurationPage.enterGeneralConfigDetails(dataProperty.getSubBrandNameLessThan2Characters(),
						dataProperty.getDomainNameSubStringLessThan2Characters(),
						dataProperty.getSourceIPAddressLimitExceed(), dataProperty.getCustomerType());
				generalConfigurationPage.saveAndContinueButton().click();

				softAssert.assertEquals(generalConfigurationPage.subBrandNameErrorText().getText().trim(),
						dataProperty.getSubBrandNameLessThan2CharactersErrorText(),
						"Create Sub-Brand: General Configuration Page: When Sub Brand Name Is Less Than 2 Characters Specified, "
								+ "Actual & Expected Errors Are Not Matching. Actual Is: "
								+ generalConfigurationPage.subBrandNameErrorText().getText() + " Expected Is: "
								+ dataProperty.getSubBrandNameLessThan2CharactersErrorText());
				softAssert.assertEquals(generalConfigurationPage.domainNameSubStringErrorText().getText().trim(),
						dataProperty.getDomainNameSubStringLessThan2CharactersErrorText(),
						"Create Sub-Brand: General Configuration Page: When Domain Name Sub String Is Less Than 2 Characters Specified, "
								+ "Actual & Expected Errors Are Not Matching. Actual Is: "
								+ generalConfigurationPage.domainNameSubStringErrorText().getText() + " Expected Is: "
								+ dataProperty.getDomainNameSubStringLessThan2CharactersErrorText());
				softAssert.assertEquals(generalConfigurationPage.sourceIPAddressErrorText().getText().trim(),
						dataProperty.getSourceIPAddressLimitExceedGreyedOutText(),
						"Create Sub-Brand: General Configuration Page: When 5 IP Addresses are Specified, "
								+ "Actual & Expected Errors Are Not Matching. Actual Is: "
								+ generalConfigurationPage.sourceIPAddressErrorText().getText() + " Expected Is: "
								+ dataProperty.getSourceIPAddressLimitExceedGreyedOutText());
				// Clean Up
				generalConfigurationPage.removeIPAddress(dataProperty.getSourceIPAddressLimitExceed());
			} else if (testName.equalsIgnoreCase("InvalidIPAddressAndSubBrandDomainNameNoSpecialCharactersAllowed")) {

				generalConfigurationPage.enterGeneralConfigDetails(
						dataProperty.getSubBrandNameNoSpecialCharactersAllowed(),
						dataProperty.getDomainNameSubStringNoSpecialCharactersAllowed(),
						dataProperty.getInvalidSourceIpAddress(), dataProperty.getCustomerType());
				generalConfigurationPage.saveAndContinueButton().click();

				softAssert.assertEquals(generalConfigurationPage.subBrandNameErrorText().getText().trim(),
						dataProperty.getSubBrandNameNoSpecialCharactersAllowedErrorText(),
						"Create Sub-Brand: General Configuration Page: When Sub Brand Name With Special Characters Is Specified, "
								+ "Actual & Expected Errors Are Not Matching. Actual Is: "
								+ generalConfigurationPage.subBrandNameErrorText().getText() + " Expected Is: "
								+ dataProperty.getSubBrandNameNoSpecialCharactersAllowedErrorText());
				softAssert.assertEquals(generalConfigurationPage.domainNameSubStringErrorText().getText().trim(),
						dataProperty.getDomainNameSubStringNoSpecialCharactersAllowedErrorText(),
						"Create Sub-Brand: General Configuration Page: When Domain Name Sub String With Special Characters Is Specified, "
								+ "Actual & Expected Errors Are Not Matching. Actual Is: "
								+ generalConfigurationPage.domainNameSubStringErrorText().getText() + " Expected Is: "
								+ dataProperty.getDomainNameSubStringNoSpecialCharactersAllowedErrorText());
				softAssert.assertEquals(generalConfigurationPage.sourceIPAddressErrorText().getText().trim(),
						dataProperty.getInvalidSourceIpAddressErrorText(),
						"Create Sub-Brand: General Configuration Page: When Invalid Source IP Address Is Specified, "
								+ "Actual & Expected Errors Are Not Matching. Actual Is: "
								+ generalConfigurationPage.sourceIPAddressErrorText().getText() + " Expected Is: "
								+ dataProperty.getInvalidSourceIpAddressErrorText());
			}
			softAssert.assertTrue(generalConfigurationPage.backToSubBrandListLink().isDisplayed(),
					"Create Sub-Brand: General Configuration Page: Back To Sub-Brand List Link Not Displayed "
							+ "In General Configuration Page.");
			softAssert.assertTrue(generalConfigurationPage.saveAndContinueButton().isDisplayed(),
					"Create Sub-Brand: General Configuration Page: Save And Continue Button Not Displayed "
							+ "In General Configuration Page.");
			softAssert.assertAll();

		} else
			log.info("TEST CASE - " + testId + " - " + testName + " NOT ENABLED. HENCE SKIPPING");

	}

	@Test(priority = 1, enabled = true, dataProvider = "feeder")
	@Source("src\\test\\resources\\testdata\\NegativeScenariosPrivateAuthSetup.csv")
	public void testPrivateAuthSetupNegativeValidations(String testId, String enabled, String testName,
			String loginMechanism) throws InterruptedException {

		getWebdriver().navigate().refresh();
		Thread.sleep(2000);

		SoftAssert softAssert = new SoftAssert();

		Assert.assertTrue(viewListOfSubBrandsPage.isPresentCreateSubBrandButton(),
				"View List Of Sub Brand Page Not Displayed/Create Sub-Brand Button Not Found. Please Check!");
		log.info("View List Of Sub Brand Page: Create Sub-Brand Button Found.");

		if (enabled.equalsIgnoreCase("True")) {

			boolean blnSubBrandExists = false;

			String existingSubBrandName = viewListOfSubBrandsPage.getTopThreeSubBrandFromHomePage();
			String[] arrExistingSubBrandNames = existingSubBrandName.split(";");
			for (String s : arrExistingSubBrandNames) {
				if (s.equalsIgnoreCase(subBrandName)) {
					viewListOfSubBrandsPage.clickEditSubBrand(subBrandName);
					blnSubBrandExists = true;
					break;
				}
			}

			if (!blnSubBrandExists)
				viewListOfSubBrandsPage.clickCreateSubBrandButton();

			Thread.sleep(3000);

			Assert.assertEquals(generalConfigurationPage.stepHeaderGenConfigLabel().getText().trim(),
					Constants.GENERAL_CONFIGURATION,
					"Create Sub-Brand: General Configuration Page: Actual And Expected Sub Headers Do Not Match."
							+ "Actual Is: " + generalConfigurationPage.stepHeaderGenConfigLabel().getText().trim()
							+ " Expected Is: " + Constants.GENERAL_CONFIGURATION);
			Assert.assertTrue(generalConfigurationPage.subBrandNameTextBox().isDisplayed(),
					"Create Sub-Brand: General Configuration Page: Sub Brand Name Text Box Not Displayed.");

			if (!blnSubBrandExists)
				generalConfigurationPage.enterGeneralConfigDetails(subBrandName, domainNameSubString, "1.1.1.1",
						dataProperty.getCustomerType());
			generalConfigurationPage.saveAndContinueButton().click();
			Thread.sleep(3000);

			Assert.assertEquals(privateAuthenticationSetupPage.stepHeaderPrivateAuthSetupLabel().getText().trim(),
					Constants.PRIVATE_AUTHENTICATION_SETUP,
					"Create Sub-Brand: Private Authentication Setup Page: Actual And Expected Sub Headers Do Not Match. "
							+ "Actual Is: "
							+ privateAuthenticationSetupPage.stepHeaderPrivateAuthSetupLabel().getText().trim()
							+ " Expected Is: " + Constants.PRIVATE_AUTHENTICATION_SETUP);

			if (testName.equalsIgnoreCase("NoLoginMechanism")) {

				if (privateAuthenticationSetupPage.loginMechanismSAMLPrivateCheckBox().isSelected())
					privateAuthenticationSetupPage.loginMechanismSAMLPrivateCheckBox().click();
				if (privateAuthenticationSetupPage.loginMechanismJWTPrivateCheckBox().isSelected())
					privateAuthenticationSetupPage.loginMechanismJWTPrivateCheckBox().click();

				privateAuthenticationSetupPage.saveAndContinueButton().click();
				Thread.sleep(2000);

				softAssert.assertEquals(privateAuthenticationSetupPage.noLoginMechanismErrorText().getText(),
						dataProperty.getNoLoginMechanismErrorText(),
						"Create Sub Brand: Private Authentication Setup Page: When No Login Mechanism is Selected The Actual And Expected "
								+ "Errors Do Not Match. Actual Is: "
								+ privateAuthenticationSetupPage.noLoginMechanismErrorText().getText() + " "
								+ "Expected Is: " + dataProperty.getNoLoginMechanismErrorText());

			} else if (testName.equalsIgnoreCase("EmptyDetails")) {
				privateAuthenticationSetupPage.enterPrivateAuthSetupDetails(loginMechanism,
						dataProperty.getEmptyCredentialsLoginId(), dataProperty.getEmptySAMLCertificate(),
						dataProperty.getEmptySAMLIssuerID(), dataProperty.getEmptySSOPostSource(),
						dataProperty.getEmptyJWTMaxExpTime(), dataProperty.getEmptyJWTRSAPublicKey(),
						dataProperty.isAddMultipleJWT(), dataProperty.isAddAnotherJWT(),
						dataProperty.isEditSAMLDetails());
				privateAuthenticationSetupPage.saveAndContinueButton().click();
				Thread.sleep(3000);

				if (loginMechanism.contains("SAML")) {
					softAssert.assertEquals(
							privateAuthenticationSetupPage.samlUploadCertificatePrivateErrorText().getText().trim(),
							dataProperty.getEmptySAMLCertificateErrorText(),
							"Create Sub-Brand: Private Authentication Setup Page: When No SAML Certificate Is Specified. "
									+ "Actual & Expected Errors Are Not Matching. Actual Is: "
									+ privateAuthenticationSetupPage.samlUploadCertificatePrivateErrorText().getText()
									+ " Expected Is: " + dataProperty.getEmptySAMLCertificateErrorText());
					softAssert.assertEquals(
							privateAuthenticationSetupPage.credentialsLoginIdPrivateErrorText().getText().trim(),
							dataProperty.getEmptyCredentialsLoginIdErrorText(),
							"Create Sub-Brand: Private Authentication Setup Page: When Blank Credential Is Specified. "
									+ "Actual & Expected Errors Are Not Matching. Actual Is: "
									+ privateAuthenticationSetupPage.credentialsLoginIdPrivateErrorText().getText()
									+ " Expected Is: " + dataProperty.getEmptyCredentialsLoginIdErrorText());

				}
				if (loginMechanism.contains("JWT")) {
					softAssert.assertEquals(
							privateAuthenticationSetupPage
									.jwtMaximumExpirationTimePrivateOneErrorText().getText().trim(),
							dataProperty.getEmptyJWTMaxExpTimeErrorText(),
							"Create Sub-Brand: Private Authentication Setup Page: When Blank JWT Maximum Expiration Time Is Specified, "
									+ "Actual & Expected Errors Are Not Matching. Actual Is: "
									+ privateAuthenticationSetupPage.jwtMaximumExpirationTimePrivateOneErrorText()
											.getText()
									+ " Expected Is: " + dataProperty.getEmptyJWTMaxExpTimeErrorText());
					softAssert.assertEquals(
							privateAuthenticationSetupPage.jwtRSAPublicKeyPrivateOneErrorText().getText(),
							dataProperty.getEmptyJWTRSAPublicKeyErrorText(),
							"Create Sub-Brand: Private Authentication Setup Page: When Blank JWT RSA Public Key Is Specified, "
									+ "Actual & Expected Errors Are Not Matching. Actual Is: "
									+ privateAuthenticationSetupPage.jwtRSAPublicKeyPrivateOneErrorText().getText()
									+ " Expected Is: " + dataProperty.getEmptyJWTRSAPublicKeyErrorText());
					if (dataProperty.isAddMultipleJWT()) {
						softAssert.assertEquals(
								privateAuthenticationSetupPage
										.jwtMaximumExpirationTimePrivateTwoErrorText().getText().trim(),
								dataProperty.getEmptyJWTMaxExpTimeErrorText(),
								"Create Sub-Brand: Private Authentication Setup Page: When Blank JWT Maximum Expiration Time Is Specified, "
										+ "Actual & Expected Errors Are Not Matching. Actual Is: "
										+ privateAuthenticationSetupPage.jwtMaximumExpirationTimePrivateTwoErrorText()
												.getText()
										+ " Expected Is: " + dataProperty.getEmptyJWTMaxExpTimeErrorText());
						softAssert.assertEquals(
								privateAuthenticationSetupPage.jwtRSAPublicKeyPrivateTwoErrorText().getText(),
								dataProperty.getEmptyJWTRSAPublicKeyErrorText(),
								"Create Sub-Brand: Private Authentication Setup Page: When Blank JWT RSA Public Key Is Specified, "
										+ "Actual & Expected Errors Are Not Matching. Actual Is: "
										+ privateAuthenticationSetupPage.jwtRSAPublicKeyPrivateTwoErrorText().getText()
										+ " Expected Is: " + dataProperty.getEmptyJWTRSAPublicKeyErrorText());
					}
				}

			} else if (testName.equalsIgnoreCase("MaxExpTimeMoreThan1800AndOtherFieldsExceedCharacterLimit")) {
				privateAuthenticationSetupPage.enterPrivateAuthSetupDetails(loginMechanism,
						dataProperty.getCredentialsLoginIdExceeds50Characters(), dataProperty.getEmptySAMLCertificate(),
						dataProperty.getSamlIssuerIdExceeds50Characters(),
						dataProperty.getSsoPostSourceExceeds50Characters(), dataProperty.getJwtMaxExpTimeMoreThan1800(),
						dataProperty.getJwtRSAPublicKeyExceeds800Characters(), dataProperty.isAddMultipleJWT(),
						dataProperty.isAddAnotherJWT(), dataProperty.isEditSAMLDetails());
				privateAuthenticationSetupPage.saveAndContinueButton().click();
				Thread.sleep(2000);

				if (loginMechanism.contains("SAML")) {
					softAssert.assertEquals(
							privateAuthenticationSetupPage.credentialsLoginIdPrivateErrorText().getText().trim(),
							dataProperty.getCredentialsLoginIdExceeds50CharactersErrorText(),
							"Create Sub-Brand: Private Authentication Setup Page: When Login Credential Length More Than 50 Characters Is Specified. "
									+ "Actual & Expected Errors Are Not Matching. Actual Is: "
									+ privateAuthenticationSetupPage.credentialsLoginIdPrivateErrorText().getText()
									+ " Expected Is: "
									+ dataProperty.getCredentialsLoginIdExceeds50CharactersErrorText());

				}
				if (loginMechanism.contains("JWT")) {
					softAssert.assertEquals(
							privateAuthenticationSetupPage
									.jwtMaximumExpirationTimePrivateOneErrorText().getText().trim(),
							dataProperty.getJwtMaxExpTimeMoreThan1800ErrorText(),
							"Create Sub-Brand: Private Authentication Setup Page: When JWT Maximum Expiration Time Specified Is More Than 1800, "
									+ "Actual & Expected Errors Are Not Matching. Actual Is: "
									+ privateAuthenticationSetupPage.jwtMaximumExpirationTimePrivateOneErrorText()
											.getText()
									+ " Expected Is: " + dataProperty.getJwtMaxExpTimeMoreThan1800ErrorText());

					if (dataProperty.isAddMultipleJWT()) {
						softAssert.assertEquals(
								privateAuthenticationSetupPage
										.jwtMaximumExpirationTimePrivateTwoErrorText().getText().trim(),
								dataProperty.getJwtMaxExpTimeMoreThan1800ErrorText(),
								"Create Sub-Brand: Private Authentication Setup Page: When JWT Maximum Expiration Time Specified Is More Than 1800, "
										+ "Actual & Expected Errors Are Not Matching. Actual Is: "
										+ privateAuthenticationSetupPage.jwtMaximumExpirationTimePrivateTwoErrorText()
												.getText()
										+ " Expected Is: " + dataProperty.getJwtMaxExpTimeMoreThan1800ErrorText());

					}
				}
			} else if (testName.equalsIgnoreCase("MaxExpTimeAlphaNumericAndInvalidCredentialsLoginIdAndJWTKey")) {

				privateAuthenticationSetupPage.enterPrivateAuthSetupDetails(loginMechanism,
						dataProperty.getCredentialsLoginIdInvalid(), dataProperty.getEmptySAMLCertificate(),
						dataProperty.getEmptySAMLIssuerID(), dataProperty.getEmptySSOPostSource(),
						dataProperty.getJwtMaxExpTimeAlphaNumericCharacter(), dataProperty.getInvalidJWTRSAPublicKey(),
						dataProperty.isAddMultipleJWT(), dataProperty.isAddAnotherJWT(),
						dataProperty.isEditSAMLDetails());
				privateAuthenticationSetupPage.saveAndContinueButton().click();
				Thread.sleep(2000);

				softAssert.assertEquals(
						privateAuthenticationSetupPage.credentialsLoginIdPrivateErrorText().getText().trim(),
						dataProperty.getCredentialsLoginIdInvalidErrorText(),
						"Create Sub-Brand: Private Authentication Setup Page: When Invalid Credentials Login ID Is Specified, "
								+ "Actual & Expected Errors Are Not Matching. Actual Is: "
								+ privateAuthenticationSetupPage.credentialsLoginIdPrivateErrorText().getText()
								+ " Expected Is: " + dataProperty.getCredentialsLoginIdInvalidErrorText());

				softAssert.assertEquals(
						privateAuthenticationSetupPage.jwtMaximumExpirationTimePrivateOneErrorText().getText().trim(),
						dataProperty.getJwtMaxExpTimeAlphaNumericCharacterErrorText(),
						"Create Sub-Brand: Private Authentication Setup Page: When JWT Maximum Expiration Time Specified Is Alpha Numeric, "
								+ "Actual & Expected Errors Are Not Matching. Actual Is: "
								+ privateAuthenticationSetupPage.jwtMaximumExpirationTimePrivateOneErrorText().getText()
								+ " Expected Is: " + dataProperty.getJwtMaxExpTimeAlphaNumericCharacterErrorText());

				softAssert.assertEquals(
						privateAuthenticationSetupPage.jwtRSAPublicKeyPrivateOneErrorText().getText().trim(),
						dataProperty.getInvalidJWTRSAPublicKeyErrorText(),
						"Create Sub-Brand: Private Authentication Setup Page: When Invalid JWT RSA Token Is Specified, "
								+ "Actual & Expected Errors Are Not Matching. Actual Is: "
								+ privateAuthenticationSetupPage.jwtRSAPublicKeyPrivateOneErrorText().getText()
								+ " Expected Is: " + dataProperty.getInvalidJWTRSAPublicKeyErrorText());

				if (dataProperty.isAddMultipleJWT()) {
					softAssert.assertEquals(
							privateAuthenticationSetupPage
									.jwtMaximumExpirationTimePrivateTwoErrorText().getText().trim(),
							dataProperty.getJwtMaxExpTimeAlphaNumericCharacterErrorText(),
							"Create Sub-Brand: Private Authentication Setup Page: When JWT Maximum Expiration Time Specified Is Alpha Numeric, "
									+ "Actual & Expected Errors Are Not Matching. Actual Is: "
									+ privateAuthenticationSetupPage.jwtMaximumExpirationTimePrivateTwoErrorText()
											.getText()
									+ " Expected Is: " + dataProperty.getJwtMaxExpTimeAlphaNumericCharacterErrorText());

					softAssert.assertEquals(
							privateAuthenticationSetupPage.jwtRSAPublicKeyPrivateTwoErrorText().getText().trim(),
							dataProperty.getInvalidJWTRSAPublicKeyErrorText(),
							"Create Sub-Brand: Private Authentication Setup Page: When Invalid JWT RSA Token Is Specified, "
									+ "Actual & Expected Errors Are Not Matching. Actual Is: "
									+ privateAuthenticationSetupPage.jwtRSAPublicKeyPrivateTwoErrorText().getText()
									+ " Expected Is: " + dataProperty.getInvalidJWTRSAPublicKeyErrorText());

				}
			}
			softAssert.assertTrue(privateAuthenticationSetupPage.backToSubBrandListLink().isDisplayed(),
					"Create Sub-Brand: Private Authentication Setup Page: Back To Sub-Brand List Link Not Displayed in Private Authentication Setup Page.");
			softAssert.assertTrue(privateAuthenticationSetupPage.saveAndContinueButton().isDisplayed(),
					"Create Sub-Brand: Private Authentication Setup Page: Save And Continue Button Not Displayed in Private Authentication Setup Page.");
			softAssert.assertTrue(privateAuthenticationSetupPage.backToPreviousStepButton().isDisplayed(),
					"Create Sub-Brand: Private Authentication Setup Page: Back To Previous Step Button Not Displayed in Private Authentication Setup Page.");
			softAssert.assertAll();

		} else
			log.info("TEST CASE - " + testId + " - " + testName + " NOT ENABLED. HENCE SKIPPING");
	}

	@Test(priority = 2, enabled = true, dataProvider = "feeder")
	@Source("src\\test\\resources\\testdata\\NegativeScenariosPublicAuthSetup.csv")
	public void testPublicAuthSetupNegativeValidations(String testId, String enabled, String testName,
			String loginMechanism, String credentialsLoginId, String samlCertificateName, String samlIssuerId,
			String ssoPostSource, String jwtMaxExpTime, String enableMultipleJWT, String addAnotherJWT)
			throws Exception {

		getWebdriver().navigate().refresh();
		Thread.sleep(2000);

		SoftAssert softAssert = new SoftAssert();

		Assert.assertTrue(viewListOfSubBrandsPage.isPresentCreateSubBrandButton(),
				"View List Of Sub Brand Page Not Displayed/Create Sub-Brand Button Not Found. Please Check!");
		log.info("Create Sub-Brand Button Found.");

		if (enabled.equalsIgnoreCase("True")) {

			String existingSubBrandName = viewListOfSubBrandsPage.getTopThreeSubBrandFromHomePage();
			String[] arrExistingSubBrandNames = existingSubBrandName.split(";");
			for (String s : arrExistingSubBrandNames) {
				if (s.equalsIgnoreCase(subBrandName)) {
					viewListOfSubBrandsPage.clickEditSubBrand(subBrandName);
					break;
				}
			}

			Thread.sleep(2000);

			Assert.assertEquals(generalConfigurationPage.stepHeaderGenConfigLabel().getText().trim(),
					Constants.GENERAL_CONFIGURATION,
					"Create Sub-Brand: General Configuration Page: " + "Actual And Expected Sub Headers Do Not Match."
							+ "Actual Is: " + generalConfigurationPage.stepHeaderGenConfigLabel().getText().trim() + " "
							+ "Expected Is: General Configuration");

			Assert.assertTrue(generalConfigurationPage.subBrandNameTextBox().isDisplayed(),
					"General Configuration Page: Sub Brand Name Text Box Not Displayed.");

			generalConfigurationPage.saveAndContinueButton().click();
			Thread.sleep(3000);

			WebDriverWait wait = new WebDriverWait(getWebdriver(), 10);
			wait.until(
					ExpectedConditions.visibilityOf(privateAuthenticationSetupPage.stepHeaderPrivateAuthSetupLabel()));

			Assert.assertEquals(privateAuthenticationSetupPage.stepHeaderPrivateAuthSetupLabel().getText().trim(),
					Constants.PRIVATE_AUTHENTICATION_SETUP,
					"Create Sub-Brand: Private Authentication Setup Page: Actual And Expected Sub Headers Do Not Match. "
							+ "Actual Is: "
							+ privateAuthenticationSetupPage.stepHeaderPrivateAuthSetupLabel().getText().trim() + " "
							+ "Expected Is: " + Constants.PRIVATE_AUTHENTICATION_SETUP);

			String jwtRSAPublicKey = "";
			if (Boolean.valueOf(enableMultipleJWT))
				jwtRSAPublicKey = GenerateJWTKeys.generateKeys().get("publicKey") + ";"
						+ GenerateJWTKeys.generateKeys().get("publicKey");
			else
				jwtRSAPublicKey = GenerateJWTKeys.generateKeys().get("publicKey");

			privateAuthenticationSetupPage.enterPrivateAuthSetupDetails(loginMechanism, credentialsLoginId,
					samlCertificateName, samlIssuerId, ssoPostSource, jwtMaxExpTime, jwtRSAPublicKey,
					Boolean.valueOf(enableMultipleJWT), Boolean.valueOf(addAnotherJWT),
					dataProperty.isEditSAMLDetails());
			privateAuthenticationSetupPage.saveAndContinueButton().click();
			Thread.sleep(3000);

			wait = new WebDriverWait(getWebdriver(), 10);
			wait.until(ExpectedConditions.visibilityOf(publicAuthenticationSetupPage.stepHeaderPublicAuthSetupLabel()));

			Assert.assertEquals(publicAuthenticationSetupPage.stepHeaderPublicAuthSetupLabel().getText().trim(),
					Constants.PUBLIC_AUTHENTICATION_SETUP,
					"Create Sub-Brand: Public Authentication Setup Page: Actual And Expected Sub Headers Do Not Match. "
							+ "Actual Is: "
							+ publicAuthenticationSetupPage.stepHeaderPublicAuthSetupLabel().getText().trim()
							+ " Expected Is: " + Constants.PUBLIC_AUTHENTICATION_SETUP);

			if (loginMechanism.equalsIgnoreCase("SAML")) {
				softAssert.assertTrue(!publicAuthenticationSetupPage.loginMechanismJWTPublicCheckBox().isEnabled(),
						"Create Sub-Brand: Public Authentication Setup: JWT Check Box Is Enabled, Instead It Should Be Disabled.");
			} else if (loginMechanism.equalsIgnoreCase("JWT")) {
				softAssert.assertTrue(!publicAuthenticationSetupPage.loginMechanismSAMLPublicCheckBox().isEnabled(),
						"Create Sub-Brand: Public Authentication Setup: SAML Check Box Is Enabled, Instead It Should Be Disabled.");
			}

			if (testName.equalsIgnoreCase("EmptyDetails")) {
				publicAuthenticationSetupPage.enterPublicAuthSetupDetails(loginMechanism,
						dataProperty.getEmptyCredentialsLoginId(), dataProperty.getEmptySAMLCertificate(),
						dataProperty.getEmptySAMLIssuerID(), dataProperty.getEmptySSOPostSource(),
						dataProperty.getEmptyJWTMaxExpTime(), dataProperty.getEmptyJWTRSAPublicKey(),
						dataProperty.isAddMultipleJWT(), dataProperty.isAddAnotherJWT(),
						dataProperty.isEditSAMLDetails());
				publicAuthenticationSetupPage.saveAndContinueButton().click();
				Thread.sleep(3000);

				if (loginMechanism.contains("SAML")) {
					softAssert.assertEquals(
							publicAuthenticationSetupPage.credentialsLoginIdPublicErrorText().getText().trim(),
							dataProperty.getEmptyCredentialsLoginIdErrorText(),
							"Create Sub-Brand: Public Authentication Setup Page: When Blank Credential Is Specified. "
									+ "Actual & Expected Errors Are Not Matching. Actual Is: "
									+ publicAuthenticationSetupPage.credentialsLoginIdPublicErrorText().getText()
									+ " Expected Is: " + dataProperty.getEmptyCredentialsLoginIdErrorText());

				}
				if (loginMechanism.contains("JWT")) {
					softAssert.assertEquals(
							publicAuthenticationSetupPage.jwtMaximumExpirationTimePublicOneErrorText().getText().trim(),
							dataProperty.getEmptyJWTMaxExpTimeErrorText(),
							"Create Sub-Brand: Public Authentication Setup Page: "
									+ "When Blank JWT Maximum Expiration Time Is Specified, "
									+ "Actual & Expected Errors Are Not Matching. Actual Is: "
									+ publicAuthenticationSetupPage.jwtMaximumExpirationTimePublicOneErrorText()
											.getText()
									+ " " + "Expected Is: " + dataProperty.getEmptyJWTMaxExpTimeErrorText());
					softAssert.assertEquals(publicAuthenticationSetupPage.jwtRSAPublicKeyPublicOneErrorText().getText(),
							dataProperty.getEmptyJWTRSAPublicKeyErrorText(),
							"Create Sub-Brand: Public Authentication Setup Page: "
									+ "When Blank JWT RSA Public Key Is Specified, "
									+ "Actual & Expected Errors Are Not Matching. Actual Is: "
									+ publicAuthenticationSetupPage.jwtRSAPublicKeyPublicOneErrorText().getText() + " "
									+ "Expected Is: " + dataProperty.getEmptyJWTRSAPublicKeyErrorText());
					if (Boolean.valueOf(enableMultipleJWT)) {
						softAssert.assertEquals(
								publicAuthenticationSetupPage
										.jwtMaximumExpirationTimePublicTwoErrorText().getText().trim(),
								dataProperty.getEmptyJWTMaxExpTimeErrorText(),
								"Create Sub-Brand: Public Authentication Setup Page: "
										+ "When Blank JWT Maximum Expiration Time Is Specified, "
										+ "Actual & Expected Errors Are Not Matching. Actual Is: "
										+ publicAuthenticationSetupPage.jwtMaximumExpirationTimePublicTwoErrorText()
												.getText()
										+ " " + "Expected Is: " + dataProperty.getEmptyJWTMaxExpTimeErrorText());
						softAssert.assertEquals(
								publicAuthenticationSetupPage.jwtRSAPublicKeyPublicTwoErrorText().getText(),
								dataProperty.getEmptyJWTRSAPublicKeyErrorText(),
								"Create Sub-Brand: Public Authentication Setup Page: "
										+ "When Blank JWT RSA Public Key Is Specified, "
										+ "Actual & Expected Errors Are Not Matching. Actual Is: "
										+ publicAuthenticationSetupPage.jwtRSAPublicKeyPublicTwoErrorText().getText()
										+ " " + "Expected Is: " + dataProperty.getEmptyJWTRSAPublicKeyErrorText());
					}
				}

			} else if (testName.equalsIgnoreCase("MaxExpTimeMoreThan1800AndOtherFieldsExceedCharacterLimit")) {
				publicAuthenticationSetupPage.enterPublicAuthSetupDetails(loginMechanism,
						dataProperty.getCredentialsLoginIdExceeds50Characters(), dataProperty.getEmptySAMLCertificate(),
						dataProperty.getSamlIssuerIdExceeds50Characters(),
						dataProperty.getSsoPostSourceExceeds50Characters(), dataProperty.getJwtMaxExpTimeMoreThan1800(),
						dataProperty.getJwtRSAPublicKeyExceeds800Characters(), dataProperty.isAddMultipleJWT(),
						dataProperty.isAddMultipleJWT(), dataProperty.isEditSAMLDetails());
				publicAuthenticationSetupPage.saveAndContinueButton().click();
				Thread.sleep(2000);

				if (loginMechanism.contains("SAML")) {
					softAssert
							.assertEquals(
									publicAuthenticationSetupPage.credentialsLoginIdPublicErrorText().getText().trim(),
									dataProperty.getCredentialsLoginIdExceeds50CharactersErrorText(),
									"Create Sub-Brand: Public Authentication Setup Page: "
											+ "When Login Credential Length More Than 50 Characters Is Specified. "
											+ "Actual & Expected Errors Are Not Matching. Actual Is: "
											+ publicAuthenticationSetupPage.credentialsLoginIdPublicErrorText()
													.getText()
											+ " Expected Is: "
											+ dataProperty.getCredentialsLoginIdExceeds50CharactersErrorText());

				}
				if (loginMechanism.contains("JWT")) {
					softAssert.assertEquals(
							publicAuthenticationSetupPage.jwtMaximumExpirationTimePublicOneErrorText().getText().trim(),
							dataProperty.getJwtMaxExpTimeMoreThan1800ErrorText(),
							"Create Sub-Brand: Public Authentication Setup Page: "
									+ "When JWT Maximum Expiration Time Specified Is More Than 1800, "
									+ "Actual & Expected Errors Are Not Matching. Actual Is: "
									+ publicAuthenticationSetupPage.jwtMaximumExpirationTimePublicOneErrorText()
											.getText()
									+ " Expected Is: " + dataProperty.getJwtMaxExpTimeMoreThan1800ErrorText());

					if (Boolean.valueOf(enableMultipleJWT)) {
						softAssert.assertEquals(
								publicAuthenticationSetupPage
										.jwtMaximumExpirationTimePublicTwoErrorText().getText().trim(),
								dataProperty.getJwtMaxExpTimeMoreThan1800ErrorText(),
								"Create Sub-Brand: Public Authentication Setup Page: "
										+ "When JWT Maximum Expiration Time Specified Is More Than 1800, "
										+ "Actual & Expected Errors Are Not Matching. Actual Is: "
										+ publicAuthenticationSetupPage.jwtMaximumExpirationTimePublicTwoErrorText()
												.getText()
										+ " Expected Is: " + dataProperty.getJwtMaxExpTimeMoreThan1800ErrorText());

					}
				}
			} else if (testName.equalsIgnoreCase("MaxExpTimeAlphaNumericAndInvalidCredentialsLoginIdAndJWTKey")) {

				publicAuthenticationSetupPage.enterPublicAuthSetupDetails(loginMechanism,
						dataProperty.getCredentialsLoginIdInvalid(), dataProperty.getEmptySAMLCertificate(),
						dataProperty.getEmptySAMLIssuerID(), dataProperty.getEmptySSOPostSource(),
						dataProperty.getJwtMaxExpTimeAlphaNumericCharacter(), dataProperty.getInvalidJWTRSAPublicKey(),
						dataProperty.isAddMultipleJWT(), dataProperty.isAddAnotherJWT(),
						dataProperty.isEditSAMLDetails());
				publicAuthenticationSetupPage.saveAndContinueButton().click();
				Thread.sleep(2000);

				softAssert.assertEquals(
						publicAuthenticationSetupPage.credentialsLoginIdPublicErrorText().getText().trim(),
						dataProperty.getCredentialsLoginIdInvalidErrorText(),
						"Create Sub-Brand: Public Authentication Setup Page: "
								+ "When Invalid Credentials Login ID Is Specified, "
								+ "Actual & Expected Errors Are Not Matching. Actual Is: "
								+ publicAuthenticationSetupPage.credentialsLoginIdPublicErrorText().getText()
								+ " Expected Is: " + dataProperty.getCredentialsLoginIdInvalidErrorText());

				softAssert.assertEquals(
						publicAuthenticationSetupPage.jwtMaximumExpirationTimePublicOneErrorText().getText().trim(),
						dataProperty.getJwtMaxExpTimeAlphaNumericCharacterErrorText(),
						"Create Sub-Brand: Public Authentication Setup Page: "
								+ "When JWT Maximum Expiration Time Specified Is Alpha Numeric, "
								+ "Actual & Expected Errors Are Not Matching. Actual Is: "
								+ publicAuthenticationSetupPage.jwtMaximumExpirationTimePublicOneErrorText().getText()
								+ " Expected Is: " + dataProperty.getJwtMaxExpTimeAlphaNumericCharacterErrorText());

				softAssert.assertEquals(
						publicAuthenticationSetupPage.jwtRSAPublicKeyPublicOneErrorText().getText().trim(),
						dataProperty.getInvalidJWTRSAPublicKeyErrorText(),
						"Create Sub-Brand: Public Authentication Setup Page: When Invalid JWT RSA Token Is Specified, "
								+ "Actual & Expected Errors Are Not Matching. Actual Is: "
								+ publicAuthenticationSetupPage.jwtRSAPublicKeyPublicOneErrorText().getText()
								+ " Expected Is: " + dataProperty.getInvalidJWTRSAPublicKeyErrorText());

				if (Boolean.valueOf(enableMultipleJWT)) {
					softAssert.assertEquals(
							publicAuthenticationSetupPage.jwtMaximumExpirationTimePublicTwoErrorText().getText().trim(),
							dataProperty.getJwtMaxExpTimeAlphaNumericCharacterErrorText(),
							"Create Sub-Brand: Public Authentication Setup Page: "
									+ "When JWT Maximum Expiration Time Specified Is Alpha Numeric, "
									+ "Actual & Expected Errors Are Not Matching. Actual Is: "
									+ publicAuthenticationSetupPage.jwtMaximumExpirationTimePublicTwoErrorText()
											.getText()
									+ " Expected Is: " + dataProperty.getJwtMaxExpTimeAlphaNumericCharacterErrorText());

					softAssert.assertEquals(
							publicAuthenticationSetupPage.jwtRSAPublicKeyPublicTwoErrorText().getText().trim(),
							dataProperty.getInvalidJWTRSAPublicKeyErrorText(),
							"Create Sub-Brand: Public Authentication Setup Page: When Invalid JWT RSA Token Is Specified, "
									+ "Actual & Expected Errors Are Not Matching. Actual Is: "
									+ publicAuthenticationSetupPage.jwtRSAPublicKeyPublicTwoErrorText().getText()
									+ " Expected Is: " + dataProperty.getInvalidJWTRSAPublicKeyErrorText());
				}
			}
			softAssert.assertTrue(publicAuthenticationSetupPage.backToSubBrandListLink().isDisplayed(),
					"Create Sub-Brand: Public Authentication Setup Page: Back To Sub-Brand List Link Not Displayed in Public Authentication Setup Page.");
			softAssert.assertTrue(publicAuthenticationSetupPage.saveAndContinueButton().isDisplayed(),
					"Create Sub-Brand: Public Authentication Setup Page: Save And Continue Button Not Displayed in Public Authentication Setup Page.");
			softAssert.assertTrue(publicAuthenticationSetupPage.backToPreviousStepButton().isDisplayed(),
					"Create Sub-Brand: Public Authentication Setup Page: Back To Previous Step Button Not Displayed in Public Authentication Setup Page.");
			softAssert.assertAll();
		} else
			log.info("TEST CASE - " + testId + " - " + testName + " NOT ENABLED. HENCE SKIPPING");
	}

	@Test(priority = 3, enabled = true, dataProvider = "feeder")
	@Source("src\\test\\resources\\testdata\\NegativeScenariosProductAndAppsSelection.csv")
	public void testProductsAndAppSelectionNegativeValidations(String testId, String enabled, String testName,
			String loginMechanism, String credentialsLoginIdPublic, String samlCertificateNamePublic,
			String samlIssuerIdPublic, String ssoPostSourcePublic, String jwtMaxExpTimePublic, String enableMultipleJWT,
			String addAnotherJWT) throws Exception {

		getWebdriver().navigate().refresh();
		Thread.sleep(1000);

		SoftAssert softAssert = new SoftAssert();

		Assert.assertTrue(viewListOfSubBrandsPage.isPresentCreateSubBrandButton(),
				"View List Of Sub Brand Page Not Displayed/Create Sub-Brand Button Not Found. Please Check!");
		log.info("Create Sub-Brand Button Found.");

		if (enabled.equalsIgnoreCase("True")) {

			String existingSubBrandName = viewListOfSubBrandsPage.getTopThreeSubBrandFromHomePage();
			String[] arrExistingSubBrandNames = existingSubBrandName.split(";");
			for (String s : arrExistingSubBrandNames) {
				if (s.equalsIgnoreCase(subBrandName)) {
					viewListOfSubBrandsPage.clickEditSubBrand(subBrandName);
					break;
				}
			}

			Thread.sleep(3000);

			Assert.assertTrue(generalConfigurationPage.stepHeaderGenConfigLabel().isDisplayed(),
					"Create Sub-Brand: General Configuration Sub Header Not Displayed.");

			Assert.assertEquals(generalConfigurationPage.stepHeaderGenConfigLabel().getText().trim(),
					Constants.GENERAL_CONFIGURATION,
					"Create Sub-Brand: General Configuration Page: " + "Actual And Expected Sub Headers Do Not Match."
							+ "Actual Is: " + generalConfigurationPage.stepHeaderGenConfigLabel().getText().trim()
							+ " Expected Is: " + Constants.GENERAL_CONFIGURATION);

			generalConfigurationPage.saveAndContinueButton().click();
			Thread.sleep(3000);

			Assert.assertEquals(privateAuthenticationSetupPage.stepHeaderPrivateAuthSetupLabel().getText().trim(),
					Constants.PRIVATE_AUTHENTICATION_SETUP,
					"Create Sub-Brand: Private Authentication Setup Page: Actual And Expected Sub Headers Do Not Match. "
							+ "Actual Is: "
							+ privateAuthenticationSetupPage.stepHeaderPrivateAuthSetupLabel().getText().trim()
							+ " Expected Is: " + Constants.PRIVATE_AUTHENTICATION_SETUP);

			privateAuthenticationSetupPage.saveAndContinueButton().click();
			Thread.sleep(3000);

			Assert.assertEquals(publicAuthenticationSetupPage.stepHeaderPublicAuthSetupLabel().getText().trim(),
					Constants.PUBLIC_AUTHENTICATION_SETUP,
					"Create Sub-Brand: Public Authentication Setup Page: Actual And Expected Sub Headers Do Not Match. "
							+ "Actual Is: "
							+ publicAuthenticationSetupPage.stepHeaderPublicAuthSetupLabel().getText().trim()
							+ " Expected Is: " + Constants.PUBLIC_AUTHENTICATION_SETUP);

			String jwtRSAPublicKey = "";
			if (Boolean.valueOf(enableMultipleJWT))
				jwtRSAPublicKey = GenerateJWTKeys.generateKeys().get("publicKey") + ";"
						+ GenerateJWTKeys.generateKeys().get("publicKey");
			else
				jwtRSAPublicKey = GenerateJWTKeys.generateKeys().get("publicKey");

			publicAuthenticationSetupPage.enterPublicAuthSetupDetails(loginMechanism, credentialsLoginIdPublic,
					samlCertificateNamePublic, samlIssuerIdPublic, ssoPostSourcePublic, jwtMaxExpTimePublic,
					jwtRSAPublicKey, Boolean.valueOf(enableMultipleJWT), Boolean.valueOf(addAnotherJWT),
					dataProperty.isEditSAMLDetails());
			publicAuthenticationSetupPage.saveAndContinueButton().click();
			Thread.sleep(3000);

			Assert.assertEquals(productsAndAppsSelectionPage.stepHeaderProductsAndAppsLabel().getText(),
					Constants.PRODUCTS_AND_APPS_SELECTION + subBrandName,
					"Create Sub-Brand: Products And Apps Selection Page: Actual And Expected Sub Headers Do Not Match. "
							+ "Actual Is: " + productsAndAppsSelectionPage.stepHeaderProductsAndAppsLabel().getText()
							+ " Expected Is: " + Constants.PRODUCTS_AND_APPS_SELECTION + subBrandName);

			Assert.assertTrue(productsAndAppsSelectionPage.checkIfAggregationCheckBoxIsDisplayed().isDisplayed(),
					"Create Sub-Brand: Products & Apps Selection Page Not Loaded Successfully. Aggregation CheckBox Not Displayed.");

			if (testName.equalsIgnoreCase("DefaultSettings")) {

				softAssert.assertTrue(productsAndAppsSelectionPage.aggregationCheckBox().isSelected(),
						"Create Sub-Brand: Products And Apps Selection Page: "
								+ "Aggregation Is Not Enabled By Default");
				softAssert.assertTrue(
						productsAndAppsSelectionPage.aggregationDocumentDownloadToggleButton().isSelected(),
						"Create Sub-Brand: Products And Apps Selection Page: "
								+ "Aggregation Document Download Is Not Enabled By Default");
				softAssert.assertTrue(productsAndAppsSelectionPage.verificationCheckBox().isSelected(),
						"Create Sub-Brand: Products And Apps Selection Page: "
								+ "Verification Is Not Enabled By Default");
				softAssert.assertTrue(
						productsAndAppsSelectionPage.verificationDocumentDownloadToggleButton().isSelected(),
						"Create Sub-Brand: Products And Apps Selection Page: "
								+ "Verification Document Download Is Not Enabled By Default");
				softAssert.assertTrue(productsAndAppsSelectionPage.yodleeFastlinkCheckBox().isSelected(),
						"Create Sub-Brand: Products And Apps Selection Page: "
								+ "Yodlee FastLink App Is Not Selected By Default");
				softAssert.assertTrue(productsAndAppsSelectionPage.yodleeWellnessFinappsCheckBox().isSelected(),
						"Create Sub-Brand: Products And Apps Selection Page: "
								+ "Yodlee Financial Wellness FinApps Is Not Enabled By Default");
				softAssert.assertTrue(productsAndAppsSelectionPage.dashboardToggleButton().isSelected(),
						"Create Sub-Brand: Products And Apps Selection Page: "
								+ "Dashboard Toggle Button Is Not Selected By Default");
				softAssert.assertTrue(productsAndAppsSelectionPage.accountsToggleButton().isSelected(),
						"Create Sub-Brand: Products And Apps Selection Page: "
								+ "Accounts Toggle Button Is Not Selected By Default");
				softAssert.assertTrue(productsAndAppsSelectionPage.finCheckToggleButton().isSelected(),
						"Create Sub-Brand: Products And Apps Selection Page: "
								+ "Fincheck Toggle Button Is Not Selected By Default");
				softAssert.assertTrue(productsAndAppsSelectionPage.cashFlowAnalysisToggleButton().isSelected(),
						"Create Sub-Brand: Products And Apps Selection Page: "
								+ "Cash Flow Analysis Toggle Button Is Not Selected By Default");
				softAssert.assertTrue(productsAndAppsSelectionPage.investmentHoldingsToggleButton().isSelected(),
						"Create Sub-Brand: Products And Apps Selection Page: "
								+ "Investment Holdings Toggle Button Is Not Selected By Default");
				softAssert.assertTrue(productsAndAppsSelectionPage.okToSpendToggleButton().isSelected(),
						"Create Sub-Brand: Products And Apps Selection Page: "
								+ "OK To Spend Toggle Button Is Not Selected By Default");
				softAssert.assertTrue(productsAndAppsSelectionPage.transactionsToggleButton().isSelected(),
						"Create Sub-Brand: Products And Apps Selection Page: "
								+ "Transactions Toggle Button Is Not Selected By Default");
				softAssert.assertTrue(productsAndAppsSelectionPage.budgetToggleButton().isSelected(),
						"Create Sub-Brand: Products And Apps Selection Page: "
								+ "Budget Toggle Button Is Not Selected By Default");
				softAssert.assertTrue(productsAndAppsSelectionPage.incomeAndExpenseAnalysisToggleButton().isSelected(),
						"Create Sub-Brand: Products And Apps Selection Page: "
								+ "Income And Expense Analysis Toggle Button Is Not Selected By Default");
				softAssert.assertTrue(productsAndAppsSelectionPage.networthSummaryToggleButton().isSelected(),
						"Create Sub-Brand: Products And Apps Selection Page: "
								+ "Networth Summary Toggle Button Is Not Selected By Default");
				softAssert.assertTrue(productsAndAppsSelectionPage.saveForAGoalToggleButton().isSelected(),
						"Create Sub-Brand: Products And Apps Selection Page: "
								+ "Save For A Goal Toggle Button Is Not Selected By Default");
				softAssert.assertTrue(productsAndAppsSelectionPage.settingsToggleButton().isSelected(),
						"Create Sub-Brand: Products And Apps Selection Page: "
								+ "Settings Toggle Button Is Not Selected By Default");
			} else if (testName.equalsIgnoreCase("NothingEnabled")) {
				productsAndAppsSelectionPage.disableFinapps();
				productsAndAppsSelectionPage.disableHostedApps();
				productsAndAppsSelectionPage.disableProductsAndSubProducts();
				productsAndAppsSelectionPage.saveAndContinueButton().click();

				softAssert.assertEquals(
						productsAndAppsSelectionPage.noProductsSelectedErrorTextLabel().getText().trim(),
						dataProperty.getNoProductSelectedErrorText(),
						"Create Sub-Brand: Products and Apps Selection Page: "
								+ "When No Products Are Selected In Products And Apps Page, The Actual And Expected Errors Are Not Matching. "
								+ "Actual Is: "
								+ productsAndAppsSelectionPage.noProductsSelectedErrorTextLabel().getText().trim() + " "
								+ "Expected Is : " + dataProperty.getNoProductSelectedErrorText());
				// Assertions TO DO
			}
			softAssert.assertTrue(productsAndAppsSelectionPage.backToSubBrandListLink().isDisplayed(),
					"Create Sub-Brand: Products And Apps Selection Page: "
							+ "Back To Sub-Brand List Link Not Displayed in Products And Apps Selection Page.");
			softAssert.assertTrue(productsAndAppsSelectionPage.saveAndContinueButton().isDisplayed(),
					"Create Sub-Brand: Products And Apps Selection Page: "
							+ "Save And Continue Button Not Displayed in Products And Apps Selection Page.");
			softAssert.assertTrue(productsAndAppsSelectionPage.backToPreviousStepButton().isDisplayed(),
					"Create Sub-Brand: Products And Apps Selection Page: "
							+ "Back To Previous Step Button Not Displayed in Products And Apps Selection Page.");
			softAssert.assertAll();

		} else
			log.info("TEST CASE - " + testId + " - " + testName + " NOT ENABLED. HENCE SKIPPING");
	}

	@Test(priority = 4, enabled = true, dataProvider = "feeder")
	@Source("src\\test\\resources\\testdata\\NegativeScenariosContactDetails.csv")
	public void testContactDetailsNegativeValidations(String testId, String enabled, String testName)
			throws InterruptedException {

		getWebdriver().navigate().refresh();
		Thread.sleep(2000);

		SoftAssert softAssert = new SoftAssert();

		Assert.assertTrue(viewListOfSubBrandsPage.isPresentCreateSubBrandButton(),
				"View List Of Sub Brand Page Not Displayed/Create Sub-Brand Button Not Found. Please Check!");
		log.info("Create Sub-Brand Button Found.");

		if (enabled.equalsIgnoreCase("True")) {

			String existingSubBrandName = viewListOfSubBrandsPage.getTopThreeSubBrandFromHomePage();
			String[] arrExistingSubBrandNames = existingSubBrandName.split(";");
			for (String s : arrExistingSubBrandNames) {
				if (s.equalsIgnoreCase(subBrandName)) {
					viewListOfSubBrandsPage.clickEditSubBrand(subBrandName);
					break;
				}
			}
			Thread.sleep(3000);

			/*
			 * Assert.assertTrue(generalConfigurationPage.stepHeaderGenConfigLabel().
			 * isDisplayed(),
			 * "Create Sub-Brand: General Configuration Sub Header Not Displayed.");
			 */
			Assert.assertEquals(generalConfigurationPage.stepHeaderGenConfigLabel().getText().trim(),
					Constants.GENERAL_CONFIGURATION,
					"Create Sub-Brand: General Configuration Page: " + "Actual And Expected Sub Headers Do Not Match."
							+ "Actual Is: " + generalConfigurationPage.stepHeaderGenConfigLabel().getText().trim()
							+ " Expected Is: " + Constants.GENERAL_CONFIGURATION);

			generalConfigurationPage.saveAndContinueButton().click();
			Thread.sleep(3000);

			Assert.assertEquals(privateAuthenticationSetupPage.stepHeaderPrivateAuthSetupLabel().getText().trim(),
					Constants.PRIVATE_AUTHENTICATION_SETUP,
					"Create Sub-Brand: Private Authentication Setup Page: Actual And Expected Sub Headers Do Not Match. "
							+ "Actual Is: "
							+ privateAuthenticationSetupPage.stepHeaderPrivateAuthSetupLabel().getText().trim()
							+ " Expected Is: " + Constants.PRIVATE_AUTHENTICATION_SETUP);

			privateAuthenticationSetupPage.saveAndContinueButton().click();
			Thread.sleep(3000);

			Assert.assertEquals(publicAuthenticationSetupPage.stepHeaderPublicAuthSetupLabel().getText().trim(),
					Constants.PUBLIC_AUTHENTICATION_SETUP,
					"Create Sub-Brand: Public Authentication Setup Page: Actual And Expected Sub Headers Do Not Match. "
							+ "Actual Is: "
							+ publicAuthenticationSetupPage.stepHeaderPublicAuthSetupLabel().getText().trim()
							+ " Expected Is: " + Constants.PUBLIC_AUTHENTICATION_SETUP);

			publicAuthenticationSetupPage.saveAndContinueButton().click();
			Thread.sleep(3000);

			Assert.assertEquals(productsAndAppsSelectionPage.stepHeaderProductsAndAppsLabel().getText(),
					Constants.PRODUCTS_AND_APPS_SELECTION + subBrandName,
					"Create Sub-Brand: Products And Apps Selection Page: Actual And Expected Sub Headers Do Not Match. "
							+ "Actual Is: " + productsAndAppsSelectionPage.stepHeaderProductsAndAppsLabel().getText()
							+ " Expected Is: " + Constants.PRODUCTS_AND_APPS_SELECTION + subBrandName);

			Assert.assertTrue(productsAndAppsSelectionPage.checkIfAggregationCheckBoxIsDisplayed().isDisplayed(),
					"Create Sub-Brand: Products And Apps Selection Page Not Loaded Successfully. Aggregation CheckBox Not Displayed.");

			productsAndAppsSelectionPage.saveAndContinueButton().click();
			Thread.sleep(3000);

			Assert.assertEquals(contactDetailsPage.stepHeaderContactDetailsLabel().getText().trim(),
					Constants.CONTACT_DETAILS,
					"Create Sub-Brand: Contact Details Page: "
							+ "Actual And Expected Contact Details Sub Headers Do Not Match. " + "Actual Is: "
							+ contactDetailsPage.stepHeaderContactDetailsLabel().getText().trim() + " Expected Is: "
							+ Constants.CONTACT_DETAILS);

			Assert.assertTrue(contactDetailsPage.contactOneFirstNameTextBox().isDisplayed(),
					"Create Sub-Brand: Contact Details Page Not Loaded Successfully. Contact First Name Text Box Not Displayed.");

			if (testName.equalsIgnoreCase("EmptyContactDetails")) {

				Assert.assertTrue(contactDetailsPage.addAnotherContactBtn().isDisplayed(),
						"Create Sub-Brand: Contact Details Page: "
								+ "Add Contact Button Not Displayed In Contact Details Page.");
				contactDetailsPage.addAnotherContactBtn().click();
				Assert.assertTrue(contactDetailsPage.removeContactBtn().isDisplayed(),
						"Create Sub-Brand: Contact Details Page: "
								+ "Remove Contact Button Not Displayed In Contact Details Page.");
				Assert.assertTrue(!contactDetailsPage.addAnotherContactEnableDisableStatusBtn().isEnabled(),
						"Create Sub-Brand: Contact Details Page: "
								+ "Add Contact Button Enabled In Contact Details Page, Even After Adding Another Contact.");
				contactDetailsPage.removeContactBtn().click();
				Assert.assertTrue(contactDetailsPage.addAnotherContactEnableDisableStatusBtn().isEnabled(),
						"Create Sub-Brand: Contact Details Page: "
								+ "Add Contact Button Not Enabled In Contact Details Page, Even After Removing Second Contact.");

				contactDetailsPage.enterContactDetails(dataProperty.getEmptyContactFN(),
						dataProperty.getEmptyContactLN(), dataProperty.getEmptyContactEmailId(),
						dataProperty.getEmptyContactNum(), dataProperty.getEmptyContactUserId(),
						dataProperty.getEmptyContactFN(), dataProperty.getEmptyContactLN(),
						dataProperty.getEmptyContactEmailId(), dataProperty.getEmptyContactNum(),
						dataProperty.getEmptyContactUserId(), dataProperty.getCustomerType(),
						dataProperty.isAddMultipleContact(), dataProperty.isAddAnotherContact());
				contactDetailsPage.saveAndContinueButton().click();
				Thread.sleep(2000);

				softAssert.assertEquals(contactDetailsPage.contactOneFirstNameErrorText().getText().trim(),
						dataProperty.getEmptyContactFNErrorText(),
						"Create Sub-Brand: Contact Details Page: When Blank Contact One First Name Is Specified, "
								+ "Actual & Expected Errors Are Not Matching. " + "Actual Is: "
								+ contactDetailsPage.contactOneFirstNameErrorText().getText() + " Expected Is: "
								+ dataProperty.getEmptyContactFNErrorText());
				softAssert.assertEquals(contactDetailsPage.contactOneLastNameErrorText().getText().trim(),
						dataProperty.getEmptyContactLNErrorText(),
						"Create Sub-Brand: Contact Details Page: When Blank Contact One Last Name Is Specified, "
								+ "Actual & Expected Errors Are Not Matching. " + "Actual Is: "
								+ contactDetailsPage.contactOneLastNameErrorText().getText() + " Expected Is: "
								+ dataProperty.getEmptyContactLNErrorText());
				softAssert.assertEquals(contactDetailsPage.contactOneContactNumberErrorText().getText().trim(),
						dataProperty.getEmptyContactNumErrorText(),
						"Create Sub-Brand: Contact Details Page: When Blank Contact One Contact Number Is Specified, "
								+ "Actual & Expected Errors Are Not Matching. " + "Actual Is: "
								+ contactDetailsPage.contactOneContactNumberErrorText().getText() + " Expected Is: "
								+ dataProperty.getEmptyContactNumErrorText());
				softAssert.assertEquals(contactDetailsPage.contactOneEmailIdErrorText().getText().trim(),
						dataProperty.getEmptyContactEmailIdErrorText(),
						"Create Sub-Brand: Contact Details Page: When Blank Contact One Email Id Is Specified, "
								+ "Actual & Expected Errors Are Not Matching. " + "Actual Is: "
								+ contactDetailsPage.contactOneEmailIdErrorText().getText() + " Expected Is: "
								+ dataProperty.getEmptyContactEmailIdErrorText());
				if (dataProperty.getCustomerType().equalsIgnoreCase(Constants.CUSTOMER_TYPE_SUB_BRAND))
					Assert.assertEquals(contactDetailsPage.contactOneUserIdErrorText().getText().trim(),
							dataProperty.getEmptyContactUserIdErrorText(),
							"Create Sub-Brand: Contact Details Page: When Blank Contact One User Id Is Specified, "
									+ "Actual & Expected Errors Are Not Matching. Actual Is: "
									+ contactDetailsPage.contactOneUserIdErrorText().getText() + " " + "Expected Is: "
									+ dataProperty.getEmptyContactUserIdErrorText());
				softAssert.assertEquals(contactDetailsPage.contactTwoFirstNameErrorText().getText().trim(),
						dataProperty.getEmptyContactFNErrorText(),
						"Create Sub-Brand: Contact Details Page: When Blank Contact One First Name Is Specified, "
								+ "Actual & Expected Errors Are Not Matching. Actual Is: "
								+ contactDetailsPage.contactTwoFirstNameErrorText().getText() + " " + "Expected Is: "
								+ dataProperty.getEmptyContactFNErrorText());
				softAssert.assertEquals(contactDetailsPage.contactTwoLastNameErrorText().getText().trim(),
						dataProperty.getEmptyContactLNErrorText(),
						"Create Sub-Brand: Contact Details Page: When Blank Contact One Last Name Is Specified, "
								+ "Actual & Expected Errors Are Not Matching. Actual Is: "
								+ contactDetailsPage.contactTwoLastNameErrorText().getText() + " " + "Expected Is: "
								+ dataProperty.getEmptyContactLNErrorText());
				softAssert.assertEquals(contactDetailsPage.contactTwoContactNumberErrorText().getText().trim(),
						dataProperty.getEmptyContactNumErrorText(),
						"Create Sub-Brand: Contact Details Page: When Blank Contact One Contact Number Is Specified, "
								+ "Actual & Expected Errors Are Not Matching. Actual Is: "
								+ contactDetailsPage.contactTwoContactNumberErrorText().getText() + " "
								+ "Expected Is: " + dataProperty.getEmptyContactNumErrorText());
				softAssert.assertEquals(contactDetailsPage.contactTwoEmailIdErrorText().getText().trim(),
						dataProperty.getEmptyContactEmailIdErrorText(),
						"Create Sub-Brand: Contact Details Page: When Blank Contact One Email Id Is Specified, "
								+ "Actual & Expected Errors Are Not Matching. Actual Is: "
								+ contactDetailsPage.contactTwoEmailIdErrorText().getText() + " " + "Expected Is: "
								+ dataProperty.getEmptyContactEmailIdErrorText());
				if (dataProperty.getCustomerType().equalsIgnoreCase(Constants.CUSTOMER_TYPE_SUB_BRAND))
					Assert.assertEquals(contactDetailsPage.contactTwoUserIdErrorText().getText().trim(),
							dataProperty.getEmptyContactUserIdErrorText(),
							"Create Sub-Brand: Contact Details Page: When Blank Contact One User Id Is Specified, "
									+ "Actual & Expected Errors Are Not Matching. Actual Is: "
									+ contactDetailsPage.contactTwoUserIdErrorText().getText() + " " + "Expected Is: "
									+ dataProperty.getEmptyContactUserIdErrorText());

			} else if (testName.equalsIgnoreCase("ContactDetailsExceedCharacterLimit")) {

				contactDetailsPage.enterContactDetails(dataProperty.getContactFNExceeds200Characters(),
						dataProperty.getContactLNExceeds200Characters(),
						dataProperty.getContactEmailExceeds50Characters(),
						dataProperty.getContactNumberExceeds10Characters(),
						dataProperty.getContactUserIdExceeds50Characters(),
						dataProperty.getContactFNExceeds200Characters(),
						dataProperty.getContactLNExceeds200Characters(),
						dataProperty.getContactEmailExceeds50Characters(),
						dataProperty.getContactNumberExceeds10Characters(),
						dataProperty.getContactUserIdExceeds50Characters(), dataProperty.getCustomerType(),
						dataProperty.isAddMultipleContact(), dataProperty.isAddAnotherContact());
				contactDetailsPage.saveAndContinueButton().click();
				Thread.sleep(2000);

				softAssert.assertEquals(contactDetailsPage.contactOneFirstNameErrorText().getText().trim(),
						dataProperty.getContactFNExceeds200CharactersErrorText(),
						"Create Sub-Brand: Contact Details Page: When Contact One First Name More Than 200 Characters Is Specified, "
								+ "Actual & Expected Errors Are Not Matching. Actual Is: "
								+ contactDetailsPage.contactOneFirstNameErrorText().getText() + " Expected Is: "
								+ dataProperty.getContactFNExceeds200CharactersErrorText());
				softAssert.assertEquals(contactDetailsPage.contactOneLastNameErrorText().getText().trim(),
						dataProperty.getContactLNExceeds200CharactersErrorText(),
						"Create Sub-Brand: Contact Details Page: When Contact One Last Name More Than 200 Characters Is Specified, "
								+ "Actual & Expected Errors Are Not Matching. Actual Is: "
								+ contactDetailsPage.contactOneLastNameErrorText().getText() + " Expected Is: "
								+ dataProperty.getContactLNExceeds200CharactersErrorText());
				softAssert.assertEquals(contactDetailsPage.contactOneContactNumberErrorText().getText().trim(),
						dataProperty.getContactNumberExceeds10CharactersErrorText(),
						"Create Sub-Brand: Contact Details Page: When Contact One Contact Number More Than 10 Characters Is Specified, "
								+ "Actual & Expected Errors Are Not Matching. Actual Is: "
								+ contactDetailsPage.contactOneContactNumberErrorText().getText() + " Expected Is: "
								+ dataProperty.getContactNumberExceeds10CharactersErrorText());
				softAssert.assertEquals(contactDetailsPage.contactOneEmailIdErrorText().getText().trim(),
						dataProperty.getContactEmailExceeds50CharactersErrorText(),
						"Create Sub-Brand: Contact Details Page: When Contact One Email Id More Than 50 Characters Is Specified, "
								+ "Actual & Expected Errors Are Not Matching. Actual Is: "
								+ contactDetailsPage.contactOneEmailIdErrorText().getText() + " Expected Is: "
								+ dataProperty.getContactEmailExceeds50CharactersErrorText());
				if (dataProperty.getCustomerType().equalsIgnoreCase(Constants.CUSTOMER_TYPE_SUB_BRAND))
					softAssert.assertEquals(contactDetailsPage.contactOneUserIdErrorText().getText().trim(),
							dataProperty.getContactUserIdExceeds50CharactersErrorText(),
							"Create Sub-Brand: Contact Details Page: When Contact One User Id More Than 50 Characters Is Specified, "
									+ "Actual & Expected Errors Are Not Matching. Actual Is: "
									+ contactDetailsPage.contactOneUserIdErrorText().getText() + " Expected Is: "
									+ dataProperty.getContactUserIdExceeds50CharactersErrorText());
				softAssert.assertEquals(contactDetailsPage.contactTwoFirstNameErrorText().getText().trim(),
						dataProperty.getContactFNExceeds200CharactersErrorText(),
						"Create Sub-Brand: Contact Details Page: When Contact Two First Name More Than 200 Characters Is Specified, "
								+ "Actual & Expected Errors Are Not Matching. Actual Is: "
								+ contactDetailsPage.contactTwoFirstNameErrorText().getText() + " Expected Is: "
								+ dataProperty.getContactFNExceeds200CharactersErrorText());
				softAssert.assertEquals(contactDetailsPage.contactTwoLastNameErrorText().getText().trim(),
						dataProperty.getContactLNExceeds200CharactersErrorText(),
						"Create Sub-Brand: Contact Details Page: When Contact Two Last Name More Than 200 Characters Is Specified, "
								+ "Actual & Expected Errors Are Not Matching. Actual Is: "
								+ contactDetailsPage.contactTwoLastNameErrorText().getText() + " Expected Is: "
								+ dataProperty.getContactLNExceeds200CharactersErrorText());
				softAssert.assertEquals(contactDetailsPage.contactTwoContactNumberErrorText().getText().trim(),
						dataProperty.getContactNumberExceeds10CharactersErrorText(),
						"Create Sub-Brand: Contact Details Page: When Blank Contact Two Contact Number More Than 10 Characters Is Specified, "
								+ "Actual & Expected Errors Are Not Matching. Actual Is: "
								+ contactDetailsPage.contactTwoContactNumberErrorText().getText() + " Expected Is: "
								+ dataProperty.getContactNumberExceeds10CharactersErrorText());
				softAssert.assertEquals(contactDetailsPage.contactTwoEmailIdErrorText().getText().trim(),
						dataProperty.getContactEmailExceeds50CharactersErrorText(),
						"Create Sub-Brand: Contact Details Page: When Contact Two Email Id More Than 50 Characters Is Specified, "
								+ "Actual & Expected Errors Are Not Matching. Actual Is: "
								+ contactDetailsPage.contactTwoEmailIdErrorText().getText() + " Expected Is: "
								+ dataProperty.getContactEmailExceeds50CharactersErrorText());
				if (dataProperty.getCustomerType().equalsIgnoreCase(Constants.CUSTOMER_TYPE_SUB_BRAND))
					softAssert.assertEquals(contactDetailsPage.contactTwoUserIdErrorText().getText().trim(),
							dataProperty.getContactUserIdExceeds50CharactersErrorText(),
							"Create Sub-Brand: Contact Details Page: When Contact Two User Id More Than 50 Characters Is Specified, "
									+ "Actual & Expected Errors Are Not Matching. Actual Is: "
									+ contactDetailsPage.contactTwoUserIdErrorText().getText() + " Expected Is: "
									+ dataProperty.getContactUserIdExceeds50CharactersErrorText());

			} else if (testName.equalsIgnoreCase(
					"InvalidContactEmailAlphanumericContactNumberAndContactUserIdLessThan2Characters")) {
				contactDetailsPage.enterContactDetails(dataProperty.getEmptyContactFN(),
						dataProperty.getEmptyContactLN(), dataProperty.getInvalidContactEmail(),
						dataProperty.getContactNumberAlphaNumeric(), dataProperty.getContactUserIdLessThan6Characters(),
						dataProperty.getEmptyContactFN(), dataProperty.getEmptyContactLN(),
						dataProperty.getInvalidContactEmail(), dataProperty.getContactNumberAlphaNumeric(),
						dataProperty.getContactUserIdLessThan6Characters(), dataProperty.getCustomerType(),
						dataProperty.isAddMultipleContact(), dataProperty.isAddAnotherContact());
				contactDetailsPage.saveAndContinueButton().click();
				Thread.sleep(2000);

				softAssert.assertEquals(contactDetailsPage.contactOneEmailIdErrorText().getText().trim(),
						dataProperty.getInvalidContactEmailErrorText(),
						"Create Sub-Brand: Contact Details Page: When Invalid Contact One Email Id Is Specified, "
								+ "Actual & Expected Errors Are Not Matching. Actual Is: "
								+ contactDetailsPage.contactOneEmailIdErrorText().getText() + " Expected Is: "
								+ dataProperty.getInvalidContactEmailErrorText());
				softAssert.assertEquals(contactDetailsPage.contactOneContactNumberErrorText().getText().trim(),
						dataProperty.getContactNumberAlphaNumericErrorText(),
						"Create Sub-Brand: Contact Details Page: When Alpha Numeric Contact One Contact Number Is Specified, "
								+ "Actual & Expected Errors Are Not Matching. Actual Is: "
								+ contactDetailsPage.contactOneContactNumberErrorText().getText() + " Expected Is: "
								+ dataProperty.getContactNumberAlphaNumericErrorText());
				if (dataProperty.getCustomerType().equalsIgnoreCase(Constants.CUSTOMER_TYPE_SUB_BRAND))
					softAssert.assertEquals(contactDetailsPage.contactOneUserIdErrorText().getText().trim(),
							dataProperty.getContactUserIdLessThan6CharactersErrorText(),
							"Create Sub-Brand: Contact Details Page: When Contact One User Id Is Less Than 6 Characters Is Specified, "
									+ "Actual & Expected Errors Are Not Matching. Actual Is: "
									+ contactDetailsPage.contactOneUserIdErrorText().getText() + " Expected Is: "
									+ dataProperty.getContactUserIdLessThan6CharactersErrorText());

				if (dataProperty.isAddMultipleContact()) {
					softAssert.assertEquals(contactDetailsPage.contactTwoContactNumberErrorText().getText().trim(),
							dataProperty.getContactNumberAlphaNumericErrorText(),
							"Create Sub-Brand: Contact Details Page: When Alpha Numeric Contact Two Contact Number Is Specified, "
									+ "Actual & Expected Errors Are Not Matching. Actual Is: "
									+ contactDetailsPage.contactTwoContactNumberErrorText().getText() + " Expected Is: "
									+ dataProperty.getContactNumberAlphaNumericErrorText());
					softAssert.assertEquals(contactDetailsPage.contactTwoEmailIdErrorText().getText().trim(),
							dataProperty.getInvalidContactEmailErrorText(),
							"Create Sub-Brand: Contact Details Page: When Invalid Contact One Email Id Is Specified, "
									+ "Actual & Expected Errors Are Not Matching. Actual Is: "
									+ contactDetailsPage.contactTwoEmailIdErrorText().getText() + " Expected Is: "
									+ dataProperty.getInvalidContactEmailErrorText());
					if (dataProperty.getCustomerType().equalsIgnoreCase(Constants.CUSTOMER_TYPE_SUB_BRAND))
						softAssert.assertEquals(contactDetailsPage.contactTwoUserIdErrorText().getText().trim(),
								dataProperty.getContactUserIdLessThan6CharactersErrorText(),
								"Create Sub-Brand: Contact Details Page: When Contact Two User Id More Than 50 Characters Is Specified, "
										+ "Actual & Expected Errors Are Not Matching. Actual Is: "
										+ contactDetailsPage.contactTwoUserIdErrorText().getText() + " Expected Is: "
										+ dataProperty.getContactUserIdLessThan6CharactersErrorText());
				} else if (testName.equalsIgnoreCase("ContactUserIdNoSpecialCharacters")) {
					contactDetailsPage.enterContactDetails(dataProperty.getEmptyContactFN(),
							dataProperty.getEmptyContactLN(), dataProperty.getEmptyContactEmailId(),
							dataProperty.getEmptyContactNum(),
							dataProperty.getContactUserIdNoSpecialCharactersAllowed(), dataProperty.getEmptyContactFN(),
							dataProperty.getEmptyContactLN(), dataProperty.getInvalidContactEmail(),
							dataProperty.getEmptyContactNum(),
							dataProperty.getContactUserIdNoSpecialCharactersAllowed(), dataProperty.getCustomerType(),
							dataProperty.isAddMultipleContact(), dataProperty.isAddAnotherContact());
					contactDetailsPage.saveAndContinueButton().click();
					Thread.sleep(2000);

					if (dataProperty.getCustomerType().equalsIgnoreCase(Constants.CUSTOMER_TYPE_SUB_BRAND))
						softAssert.assertEquals(contactDetailsPage.contactOneUserIdErrorText().getText().trim(),
								dataProperty.getContactUserIdNoSpecialCharactersAllowedErrorText(),
								"Create Sub-Brand: Contact Details Page: When Contact One User Id With Special Characters Is Specified, "
										+ "Actual & Expected Errors Are Not Matching. Actual Is: "
										+ contactDetailsPage.contactOneUserIdErrorText().getText() + " Expected Is: "
										+ dataProperty.getContactUserIdNoSpecialCharactersAllowedErrorText());

					if (dataProperty.isAddMultipleContact())
						if (dataProperty.getCustomerType().equalsIgnoreCase(Constants.CUSTOMER_TYPE_SUB_BRAND))
							softAssert.assertEquals(contactDetailsPage.contactTwoUserIdErrorText().getText().trim(),
									dataProperty.getContactUserIdNoSpecialCharactersAllowedErrorText(),
									"Create Sub-Brand: Contact Details Page: When Contact Two User Id With Special Characters Is Specified, "
											+ "Actual & Expected Errors Are Not Matching. Actual Is: "
											+ contactDetailsPage.contactTwoUserIdErrorText().getText()
											+ " Expected Is: "
											+ dataProperty.getContactUserIdNoSpecialCharactersAllowedErrorText());

				}
			}
			softAssert.assertTrue(contactDetailsPage.backToSubBrandListLink().isDisplayed(),
					"Create Sub-Brand: Contact Details Page: Back To Sub-Brand List Link Not Displayed in Contact Details Page.");
			softAssert.assertTrue(contactDetailsPage.saveAndContinueButton().isDisplayed(),
					"Create Sub-Brand: Contact Details Page: Save And Continue Button Not Displayed in Contact Details Page.");
			softAssert.assertTrue(contactDetailsPage.backToPreviousStepButton().isDisplayed(),
					"Create Sub-Brand: Contact Details Page: Back To Previous Step Button Not Displayed in Contact Details Page.");
			softAssert.assertAll();

		} else
			log.info("TEST CASE - " + testId + " - " + testName + " NOT ENABLED. HENCE SKIPPING");
		getWebdriver().navigate().refresh();
		
		softAssert.assertTrue(viewListOfSubBrandsPage.deleteSubBrandButton().isEnabled());
		viewListOfSubBrandsPage.deleteSubBrandButton().click();
		viewListOfSubBrandsPage.confirmDelSubBrandBtn().click();
		/*
		 * getWebdriver().navigate().refresh();
		 */			
		softAssert.assertAll();
	}
	
	/*
	 * @Test(priority = 0, enabled = true, dataProvider = "feeder") public void
	 * testDeleteSubBrand(String testId, String enabled, String testName, String
	 * loginMechanism, String credentialsLoginIdPublic, String
	 * samlCertificateNamePublic, String samlIssuerIdPublic, String
	 * ssoPostSourcePublic, String jwtMaxExpTimePublic, String enableMultipleJWT,
	 * String addAnotherJWT) throws Exception {
	 * 
	 * getWebdriver().navigate().refresh(); Thread.sleep(1000);
	 * 
	 * SoftAssert softAssert = new SoftAssert();
	 * 
	 * Assert.assertTrue(viewListOfSubBrandsPage.isPresentCreateSubBrandButton(),
	 * "View List Of Sub Brand Page Not Displayed/Create Sub-Brand Button Not Found. Please Check!"
	 * ); log.info("Create Sub-Brand Button Found.");
	 * 
	 * if (enabled.equalsIgnoreCase("True")) {
	 * 
	 * String existingSubBrandName =
	 * viewListOfSubBrandsPage.getTopThreeSubBrandFromHomePage(); String[]
	 * arrExistingSubBrandNames = existingSubBrandName.split(";"); for (String s :
	 * arrExistingSubBrandNames) { if (s.equalsIgnoreCase(subBrandName)) {
	 * viewListOfSubBrandsPage.clickEditSubBrand(subBrandName); break; } }
	 * 
	 * Thread.sleep(3000);
	 * 
	 * Assert.assertTrue(generalConfigurationPage.stepHeaderGenConfigLabel().
	 * isDisplayed(),
	 * "Create Sub-Brand: General Configuration Sub Header Not Displayed.");
	 * 
	 * Assert.assertEquals(generalConfigurationPage.stepHeaderGenConfigLabel().
	 * getText().trim(), Constants.GENERAL_CONFIGURATION,
	 * "Create Sub-Brand: General Configuration Page: " +
	 * "Actual And Expected Sub Headers Do Not Match." + "Actual Is: " +
	 * generalConfigurationPage.stepHeaderGenConfigLabel().getText().trim() +
	 * " Expected Is: " + Constants.GENERAL_CONFIGURATION);
	 * 
	 * generalConfigurationPage.saveAndContinueButton().click(); Thread.sleep(3000);
	 * 
	 * Assert.assertEquals(privateAuthenticationSetupPage.
	 * stepHeaderPrivateAuthSetupLabel().getText().trim(),
	 * Constants.PRIVATE_AUTHENTICATION_SETUP,
	 * "Create Sub-Brand: Private Authentication Setup Page: Actual And Expected Sub Headers Do Not Match. "
	 * + "Actual Is: " +
	 * privateAuthenticationSetupPage.stepHeaderPrivateAuthSetupLabel().getText().
	 * trim() + " Expected Is: " + Constants.PRIVATE_AUTHENTICATION_SETUP);
	 * 
	 * privateAuthenticationSetupPage.saveAndContinueButton().click();
	 * Thread.sleep(3000);
	 * 
	 * Assert.assertEquals(publicAuthenticationSetupPage.
	 * stepHeaderPublicAuthSetupLabel().getText().trim(),
	 * Constants.PUBLIC_AUTHENTICATION_SETUP,
	 * "Create Sub-Brand: Public Authentication Setup Page: Actual And Expected Sub Headers Do Not Match. "
	 * + "Actual Is: " +
	 * publicAuthenticationSetupPage.stepHeaderPublicAuthSetupLabel().getText().trim
	 * () + " Expected Is: " + Constants.PUBLIC_AUTHENTICATION_SETUP);
	 * 
	 * String jwtRSAPublicKey = ""; if (Boolean.valueOf(enableMultipleJWT))
	 * jwtRSAPublicKey = GenerateJWTKeys.generateKeys().get("publicKey") + ";" +
	 * GenerateJWTKeys.generateKeys().get("publicKey"); else jwtRSAPublicKey =
	 * GenerateJWTKeys.generateKeys().get("publicKey");
	 * 
	 * publicAuthenticationSetupPage.enterPublicAuthSetupDetails(loginMechanism,
	 * credentialsLoginIdPublic, samlCertificateNamePublic, samlIssuerIdPublic,
	 * ssoPostSourcePublic, jwtMaxExpTimePublic, jwtRSAPublicKey,
	 * Boolean.valueOf(enableMultipleJWT), Boolean.valueOf(addAnotherJWT),
	 * dataProperty.isEditSAMLDetails());
	 * publicAuthenticationSetupPage.saveAndContinueButton().click();
	 * Thread.sleep(3000);
	 * 
	 * Assert.assertEquals(productsAndAppsSelectionPage.
	 * stepHeaderProductsAndAppsLabel().getText(),
	 * Constants.PRODUCTS_AND_APPS_SELECTION + subBrandName,
	 * "Create Sub-Brand: Products And Apps Selection Page: Actual And Expected Sub Headers Do Not Match. "
	 * + "Actual Is: " +
	 * productsAndAppsSelectionPage.stepHeaderProductsAndAppsLabel().getText() +
	 * " Expected Is: " + Constants.PRODUCTS_AND_APPS_SELECTION + subBrandName);
	 * 
	 * Assert.assertTrue(productsAndAppsSelectionPage.
	 * checkIfAggregationCheckBoxIsDisplayed().isDisplayed(),
	 * "Create Sub-Brand: Products & Apps Selection Page Not Loaded Successfully. Aggregation CheckBox Not Displayed."
	 * );
	 * 
	 * if (testName.equalsIgnoreCase("DefaultSettings")) {
	 * 
	 * softAssert.assertTrue(productsAndAppsSelectionPage.aggregationCheckBox().
	 * isSelected(), "Create Sub-Brand: Products And Apps Selection Page: " +
	 * "Aggregation Is Not Enabled By Default"); softAssert.assertTrue(
	 * productsAndAppsSelectionPage.aggregationDocumentDownloadToggleButton().
	 * isSelected(), "Create Sub-Brand: Products And Apps Selection Page: " +
	 * "Aggregation Document Download Is Not Enabled By Default");
	 * softAssert.assertTrue(productsAndAppsSelectionPage.verificationCheckBox().
	 * isSelected(), "Create Sub-Brand: Products And Apps Selection Page: " +
	 * "Verification Is Not Enabled By Default"); softAssert.assertTrue(
	 * productsAndAppsSelectionPage.verificationDocumentDownloadToggleButton().
	 * isSelected(), "Create Sub-Brand: Products And Apps Selection Page: " +
	 * "Verification Document Download Is Not Enabled By Default");
	 * softAssert.assertTrue(productsAndAppsSelectionPage.yodleeFastlinkCheckBox().
	 * isSelected(), "Create Sub-Brand: Products And Apps Selection Page: " +
	 * "Yodlee FastLink App Is Not Selected By Default");
	 * softAssert.assertTrue(productsAndAppsSelectionPage.
	 * yodleeWellnessFinappsCheckBox().isSelected(),
	 * "Create Sub-Brand: Products And Apps Selection Page: " +
	 * "Yodlee Financial Wellness FinApps Is Not Enabled By Default");
	 * softAssert.assertTrue(productsAndAppsSelectionPage.dashboardToggleButton().
	 * isSelected(), "Create Sub-Brand: Products And Apps Selection Page: " +
	 * "Dashboard Toggle Button Is Not Selected By Default");
	 * softAssert.assertTrue(productsAndAppsSelectionPage.accountsToggleButton().
	 * isSelected(), "Create Sub-Brand: Products And Apps Selection Page: " +
	 * "Accounts Toggle Button Is Not Selected By Default");
	 * softAssert.assertTrue(productsAndAppsSelectionPage.finCheckToggleButton().
	 * isSelected(), "Create Sub-Brand: Products And Apps Selection Page: " +
	 * "Fincheck Toggle Button Is Not Selected By Default");
	 * softAssert.assertTrue(productsAndAppsSelectionPage.
	 * cashFlowAnalysisToggleButton().isSelected(),
	 * "Create Sub-Brand: Products And Apps Selection Page: " +
	 * "Cash Flow Analysis Toggle Button Is Not Selected By Default");
	 * softAssert.assertTrue(productsAndAppsSelectionPage.
	 * investmentHoldingsToggleButton().isSelected(),
	 * "Create Sub-Brand: Products And Apps Selection Page: " +
	 * "Investment Holdings Toggle Button Is Not Selected By Default");
	 * softAssert.assertTrue(productsAndAppsSelectionPage.okToSpendToggleButton().
	 * isSelected(), "Create Sub-Brand: Products And Apps Selection Page: " +
	 * "OK To Spend Toggle Button Is Not Selected By Default");
	 * softAssert.assertTrue(productsAndAppsSelectionPage.transactionsToggleButton()
	 * .isSelected(), "Create Sub-Brand: Products And Apps Selection Page: " +
	 * "Transactions Toggle Button Is Not Selected By Default");
	 * softAssert.assertTrue(productsAndAppsSelectionPage.budgetToggleButton().
	 * isSelected(), "Create Sub-Brand: Products And Apps Selection Page: " +
	 * "Budget Toggle Button Is Not Selected By Default");
	 * softAssert.assertTrue(productsAndAppsSelectionPage.
	 * incomeAndExpenseAnalysisToggleButton().isSelected(),
	 * "Create Sub-Brand: Products And Apps Selection Page: " +
	 * "Income And Expense Analysis Toggle Button Is Not Selected By Default");
	 * softAssert.assertTrue(productsAndAppsSelectionPage.
	 * networthSummaryToggleButton().isSelected(),
	 * "Create Sub-Brand: Products And Apps Selection Page: " +
	 * "Networth Summary Toggle Button Is Not Selected By Default");
	 * softAssert.assertTrue(productsAndAppsSelectionPage.saveForAGoalToggleButton()
	 * .isSelected(), "Create Sub-Brand: Products And Apps Selection Page: " +
	 * "Save For A Goal Toggle Button Is Not Selected By Default");
	 * softAssert.assertTrue(productsAndAppsSelectionPage.settingsToggleButton().
	 * isSelected(), "Create Sub-Brand: Products And Apps Selection Page: " +
	 * "Settings Toggle Button Is Not Selected By Default"); } else if
	 * (testName.equalsIgnoreCase("NothingEnabled")) {
	 * productsAndAppsSelectionPage.disableFinapps();
	 * productsAndAppsSelectionPage.disableHostedApps();
	 * productsAndAppsSelectionPage.disableProductsAndSubProducts();
	 * productsAndAppsSelectionPage.saveAndContinueButton().click();
	 * 
	 * softAssert.assertEquals(
	 * productsAndAppsSelectionPage.noProductsSelectedErrorTextLabel().getText().
	 * trim(), dataProperty.getNoProductSelectedErrorText(),
	 * "Create Sub-Brand: Products and Apps Selection Page: " +
	 * "When No Products Are Selected In Products And Apps Page, The Actual And Expected Errors Are Not Matching. "
	 * + "Actual Is: " +
	 * productsAndAppsSelectionPage.noProductsSelectedErrorTextLabel().getText().
	 * trim() + " " + "Expected Is : " +
	 * dataProperty.getNoProductSelectedErrorText()); // Assertions TO DO }
	 * softAssert.assertTrue(productsAndAppsSelectionPage.backToSubBrandListLink().
	 * isDisplayed(), "Create Sub-Brand: Products And Apps Selection Page: " +
	 * "Back To Sub-Brand List Link Not Displayed in Products And Apps Selection Page."
	 * );
	 * softAssert.assertTrue(productsAndAppsSelectionPage.saveAndContinueButton().
	 * isDisplayed(), "Create Sub-Brand: Products And Apps Selection Page: " +
	 * "Save And Continue Button Not Displayed in Products And Apps Selection Page."
	 * );
	 * softAssert.assertTrue(productsAndAppsSelectionPage.backToPreviousStepButton()
	 * .isDisplayed(), "Create Sub-Brand: Products And Apps Selection Page: " +
	 * "Back To Previous Step Button Not Displayed in Products And Apps Selection Page."
	 * );
	 * 
	 * 
	 * 
	 * } else log.info("TEST CASE - " + testId + " - " + testName +
	 * " NOT ENABLED. HENCE SKIPPING"); }
	 */
	
	
}