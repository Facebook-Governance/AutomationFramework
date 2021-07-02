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

public class CreateSubBrand extends BaseTest {

	private Logger log = LoggerFactory.getLogger(CreateSubBrand.class);

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

	@Test(priority = 0, enabled = true, dataProvider = "feeder")
	@Source("src\\test\\resources\\testdata\\NegativeScenariosGeneralConfiguration.csv")
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
	}

	@Test(priority = 5, enabled = true, dataProvider = "feeder")
	@Source("src\\test\\resources\\testdata\\CreateSubBrand.csv")
	public void testCreateAndViewSubBrandOverView(String testID, String enabled, String testName,
			String sourceIPAddress, String customerType, String loginMechanism, String credentialsLoginIdPrivate,
			String samlCertificateNamePrivate, String samlIssueIdPrivate, String ssoPostSourcePrivate,
			String jwtMaximumExpirationTimePrivate, String credentialsLoginIdPublic, String samlCertificateNamePublic,
			String samlIssueIdPublic, String ssoPostSourcePublic, String jwtMaximumExpirationTimePublic,
			String products, String subProductsAggregation, String subProductsVerification, String hostedApplications,
			String finapps_review, String finapps_overview, String contactOneFN, String contactOneLN,
			String contactOneEmail, String contactOneContactNum, String contactOneUserId, String contactTwoFN,
			String contactTwoLN, String contactTwoEmail, String contactTwoContactNum, String contactTwoUserId,
			String enableMultipleContacts, String enableMultipleJWT, String addAnotherContact, String addAnotherJWT)
			throws Exception {

		getWebdriver().navigate().refresh();
		Thread.sleep(2000);

		SoftAssert softAssert = new SoftAssert();

		Assert.assertTrue(viewListOfSubBrandsPage.isPresentCreateSubBrandButton(),
				"View List Of Sub Brand Page Not Displayed/Create Sub-Brand Button Not Found. Please Check!");
		log.info("Create Sub-Brand Button Found.");

		String jwtRSAPublicKeyPrivate = "";
		String jwtRSAPublicKeyPublic = "";
		if (enabled.equalsIgnoreCase("True")) {

			subBrandName = dataProperty.getSubBrandName() + GenericUtils.getTimStamp();
			domainNameSubString = dataProperty.getDomainNameSubString() + GenericUtils.getTimStamp();

			viewListOfSubBrandsPage.clickCreateSubBrandButton();

			log.info("Sub Brand Name: " + subBrandName);
			log.info("Domain Name Sub String: " + domainNameSubString);

			Assert.assertTrue(generalConfigurationPage.subBrandNameTextBox().isDisplayed(),
					"Create Sub-Brand: General Configuration Sub Brand Name Text Box Not Displayed.");

			Assert.assertEquals(generalConfigurationPage.stepHeaderGenConfigLabel().getText().trim(),
					Constants.GENERAL_CONFIGURATION,
					"Create Sub-Brand: General Configuration Page: " + "Actual And Expected Sub Headers Do Not Match."
							+ "Actual Is: " + generalConfigurationPage.stepHeaderGenConfigLabel().getText().trim()
							+ " Expected Is: " + Constants.GENERAL_CONFIGURATION);
			log.info("New Sub-Brand Name Is: " + subBrandName);

			generalConfigurationPage.enterGeneralConfigDetails(subBrandName, domainNameSubString, sourceIPAddress,
					customerType);
			generalConfigurationPage.saveAndContinueButton().click();
			Thread.sleep(2000);
			Assert.assertEquals(privateAuthenticationSetupPage.stepHeaderPrivateAuthSetupLabel().getText().trim(),
					Constants.PRIVATE_AUTHENTICATION_SETUP,
					"Create Sub-Brand: Private Authentication Setup Page: "
							+ "Actual And Expected Sub Headers Do Not Match. " + "Actual Is: "
							+ privateAuthenticationSetupPage.stepHeaderPrivateAuthSetupLabel().getText().trim()
							+ " Expected Is: " + Constants.PRIVATE_AUTHENTICATION_SETUP);

			Map<String, String> PrivateJWTKeys1;
			Map<String, String> PrivateJWTKeys2;
			if (loginMechanism.contains("JWT")) {
				if (Boolean.valueOf(enableMultipleJWT)) {
					PrivateJWTKeys1 = GenerateJWTKeys.generateKeys();
					PrivateJWTKeys2 = GenerateJWTKeys.generateKeys();
					jwtRSAPublicKeyPrivate = PrivateJWTKeys1.get("publicKey") + ";" + PrivateJWTKeys2.get("publicKey");
					log.info("Begin JWT 1 Private Key Private------------");
					log.info(PrivateJWTKeys1.get("privateKey"));
					log.info("End JWT 1 Private Key Private------------");
					log.info("Begin JWT 2 Private Key Private------------");
					log.info(PrivateJWTKeys2.get("privateKey"));
					log.info("Begin JWT 2 Private Key Private------------");
				} else {
					PrivateJWTKeys1 = GenerateJWTKeys.generateKeys();
					jwtRSAPublicKeyPrivate = PrivateJWTKeys1.get("publicKey");
					log.info("Begin JWT Private Key Private------------");
					log.info(PrivateJWTKeys1.get("privateKey"));
					log.info("End JWT Private Key Private------------");
				}
			}

			privateAuthenticationSetupPage.enterPrivateAuthSetupDetails(loginMechanism, credentialsLoginIdPrivate,
					samlCertificateNamePrivate, samlIssueIdPrivate, ssoPostSourcePrivate,
					jwtMaximumExpirationTimePrivate, jwtRSAPublicKeyPrivate, Boolean.valueOf(enableMultipleJWT),
					Boolean.valueOf(addAnotherJWT), dataProperty.isEditSAMLDetails());
			privateAuthenticationSetupPage.saveAndContinueButton().click();
			Thread.sleep(2000);
			Assert.assertEquals(publicAuthenticationSetupPage.stepHeaderPublicAuthSetupLabel().getText().trim(),
					Constants.PUBLIC_AUTHENTICATION_SETUP,
					"Create Sub-Brand: Public Authentication Setup Page: "
							+ "Actual And Expected Sub Headers Do Not Match. " + "Actual Is: "
							+ publicAuthenticationSetupPage.stepHeaderPublicAuthSetupLabel().getText().trim()
							+ " Expected Is: " + Constants.PUBLIC_AUTHENTICATION_SETUP);

			Map<String, String> PublicJWTKeys1;
			Map<String, String> PublicJWTKeys2;
			if (loginMechanism.contains("JWT")) {
				if (Boolean.valueOf(enableMultipleJWT)) {
					PublicJWTKeys1 = GenerateJWTKeys.generateKeys();
					PublicJWTKeys2 = GenerateJWTKeys.generateKeys();
					jwtRSAPublicKeyPublic = PublicJWTKeys1.get("publicKey") + ";" + PublicJWTKeys2.get("publicKey");
					log.info("Begin JWT 1 Private Key Public------------");
					log.info(PublicJWTKeys1.get("privateKey"));
					log.info("End JWT 1 Private Key Public------------");
					log.info("Begin JWT 2 Private Key Public------------");
					log.info(PublicJWTKeys1.get("privateKey"));
					log.info("Begin JWT 2 Private Key Public------------");
				} else {
					PublicJWTKeys1 = GenerateJWTKeys.generateKeys();
					jwtRSAPublicKeyPublic = PublicJWTKeys1.get("publicKey");
					log.info("Begin JWT Private Key Public------------");
					log.info(PublicJWTKeys1.get("privateKey"));
					log.info("End JWT Private Key Public------------");
				}
			}

			publicAuthenticationSetupPage.enterPublicAuthSetupDetails(loginMechanism, credentialsLoginIdPublic,
					samlCertificateNamePublic, samlIssueIdPublic, ssoPostSourcePublic, jwtMaximumExpirationTimePublic,
					jwtRSAPublicKeyPublic, Boolean.valueOf(enableMultipleJWT), Boolean.valueOf(addAnotherJWT),
					dataProperty.isEditSAMLDetails());
			publicAuthenticationSetupPage.saveAndContinueButton().click();
			Thread.sleep(2000);
			Assert.assertEquals(productsAndAppsSelectionPage.stepHeaderProductsAndAppsLabel().getText(),
					Constants.PRODUCTS_AND_APPS_SELECTION + subBrandName,
					"Create Sub-Brand: Products And Apps Selection Page: Actual And Expected Sub Headers Do Not Match. "
							+ "Actual Is: " + productsAndAppsSelectionPage.stepHeaderProductsAndAppsLabel().getText()
							+ " Expected Is: " + Constants.PRODUCTS_AND_APPS_SELECTION + subBrandName);

		

			productsAndAppsSelectionPage.selectProductsAndAppDetails(products, subProductsAggregation,
					subProductsVerification, hostedApplications, finapps_review);
			productsAndAppsSelectionPage.saveAndContinueButton().click();
			Thread.sleep(2000);
			Assert.assertTrue(contactDetailsPage.contactOneFirstNameTextBox().isDisplayed(),
					"Create Sub-Brand: Contact Details Page Not Loaded Successfully. Contact First Name Text Box Not Displayed.");

			Assert.assertEquals(contactDetailsPage.stepHeaderContactDetailsLabel().getText().trim(),
					Constants.CONTACT_DETAILS,
					"Create Sub-Brand: Contact Details Page: Actual And Expected Contact Details Sub Headers Do Not Match. "
							+ "Actual Is: " + contactDetailsPage.stepHeaderContactDetailsLabel().getText().trim()
							+ " Expected Is: " + Constants.CONTACT_DETAILS);

			if (!contactOneEmail.contains("@"))
				contactOneEmail = contactOneEmail + "_" + GenericUtils.getTimStamp() + "@mailinator.com";
			log.info("Contact 1 Email ID Is: " + contactOneEmail);

			if (Boolean.valueOf(enableMultipleContacts)) {
				if (!contactTwoEmail.contains("@"))
					contactTwoEmail = contactTwoEmail + "_" + GenericUtils.getTimStamp() + "@mailinator.com";
				log.info("Contact 2 Email ID Is: " + contactTwoEmail);
			}
			contactOneUserId = subBrandName + contactOneUserId;
			contactTwoUserId = subBrandName + contactTwoUserId;
			contactDetailsPage.enterContactDetails(contactOneFN, contactOneLN, contactOneEmail, contactOneContactNum,
					contactOneUserId, contactTwoFN, contactTwoLN, contactTwoEmail, contactTwoContactNum,
					contactTwoUserId, customerType, Boolean.valueOf(enableMultipleContacts),
					Boolean.valueOf(addAnotherContact));
			contactDetailsPage.saveAndContinueButton().click();
			Thread.sleep(2000);
			Assert.assertTrue(reviewPage.publishToPrivate().isDisplayed(),
					"Publish To Private Button Not Found In Review Screen. Please Check!");

			String activeStep = reviewPage.activeStep().getText();
			Assert.assertTrue(activeStep.equalsIgnoreCase("Review"),
					"Create Sub-Brand: Not In Review Screen. "
							+ "Cannot Proceed With Review Screen Validations. Please Check! " + "Active Screen Is "
							+ activeStep);

			HashMap<String, String> revGenConfig = reviewPage.getReviewScreenGeneralConfigurationDetails();
			softAssert.assertEquals(revGenConfig.get("subBrandName"), subBrandName,
					"Create Sub-Brand: Review Page: The Actual And Expected Sub Brand Name Does Not Match. Actual Is: "
							+ revGenConfig.get("subBrandName") + " Expected Is: " + subBrandName);

			softAssert.assertEquals(revGenConfig.get("domainNameSubString"), domainNameSubString,
					"Create Sub-Brand: Review Page: The Actual And Expected Domain Name Sub String Does Not Match. Actual Is: "
							+ revGenConfig.get("domainNameSubString") + " Expected Is: " + domainNameSubString);

			softAssert.assertEquals(revGenConfig.get("sourceIPAddress"), sourceIPAddress,
					"Create Sub-Brand: Review Page: The Actual And Expected Domain Name Sub String Does Not Match. Actual Is: "
							+ revGenConfig.get("sourceIPAddress") + " Expected Is: " + sourceIPAddress);

			HashMap<String, String> revPrivateAuthSetup = reviewPage.getReviewScreenPrivateAuthenticationSetupDetails(
					loginMechanism, Boolean.valueOf(enableMultipleJWT), Boolean.valueOf(addAnotherJWT));

			softAssert.assertEquals(revPrivateAuthSetup.get("loginMechanism"), loginMechanism,
					"Create Sub-Brand: Review Page: The Actual And Expected Private Login Mechanism Does Not Match. Actual Is: "
							+ revPrivateAuthSetup.get("loginMechanism") + " Expected Is: " + loginMechanism);

			if (loginMechanism.contains("JWT")) {
				if (Boolean.valueOf(enableMultipleJWT) || Boolean.valueOf(addAnotherJWT)) {
					softAssert.assertEquals(revPrivateAuthSetup.get("JWT1"),
							"Algorithm : RS512;Maximum Expiration Time : " + jwtMaximumExpirationTimePrivate
									+ ";RSA Public Key : " + jwtRSAPublicKeyPrivate.split(";")[0],
							"Create Sub-Brand: Review Page: The Actual And Expected Private JWT Details Does Not Match. Actual Is: "
									+ revPrivateAuthSetup.get("JWT1")
									+ " Expected Is: Algorithm : RS512;Maximum Expiration Time : "
									+ jwtMaximumExpirationTimePrivate + ";RSA Public Key : "
									+ jwtRSAPublicKeyPrivate.split(";")[0]);
					softAssert.assertEquals(revPrivateAuthSetup.get("JWT2"),
							"Algorithm : RS512;Maximum Expiration Time : " + jwtMaximumExpirationTimePrivate
									+ ";RSA Public Key : " + jwtRSAPublicKeyPrivate.split(";")[1],
							"Create Sub-Brand: Review Page: The Actual And Expected Private JWT Details Does Not Match. Actual Is: "
									+ revPrivateAuthSetup.get("JWT2")
									+ " Expected Is: Algorithm : RS512;Maximum Expiration Time : "
									+ jwtMaximumExpirationTimePrivate + ";RSA Public Key : "
									+ jwtRSAPublicKeyPrivate.split(";")[1]);
				} else {
					softAssert.assertEquals(revPrivateAuthSetup.get("JWT1"),
							"Algorithm : RS512;Maximum Expiration Time : " + jwtMaximumExpirationTimePrivate
									+ ";RSA Public Key : " + jwtRSAPublicKeyPrivate,
							"Create Sub-Brand: Review Page: The Actual And Expected Private JWT Details Does Not Match. Actual Is: "
									+ revPrivateAuthSetup.get("JWT1")
									+ " Expected Is: Algorithm : RS512;Maximum Expiration Time : "
									+ jwtMaximumExpirationTimePrivate + ";RSA Public Key : " + jwtRSAPublicKeyPrivate);
				}
			}

			if (loginMechanism.contains("SAML")) {
				softAssert.assertEquals(revPrivateAuthSetup.get("SAML"),
						"Login ID : " + credentialsLoginIdPrivate + ";" + "SAML Certificate : saml.cer;"
								+ "SAML Issuer ID : " + samlIssueIdPrivate + ";" + "SSO Post Source : "
								+ ssoPostSourcePrivate,
						"Create Sub-Brand: Review Page: The Actual And Expected Private SAML Details Does Not Match. "
								+ "Actual Is: " + revPrivateAuthSetup.get("SAML") + " " + "Expected Is: Login ID : "
								+ credentialsLoginIdPrivate + ";" + "SAML Certificate : saml.cer;SAML Issuer ID : "
								+ samlIssueIdPrivate + ";SSO Post Source : " + ssoPostSourcePrivate);

			}

			HashMap<String, String> revPublicAuthSetup = reviewPage.getReviewScreenPublicAuthenticationSetupDetails(
					loginMechanism, Boolean.valueOf(enableMultipleJWT), Boolean.valueOf(addAnotherJWT));

			softAssert.assertEquals(revPublicAuthSetup.get("loginMechanism"), loginMechanism,
					"Create Sub-Brand: Review Page: The Actual And Expected Public Login Mechanism Does Not Match. Actual Is: "
							+ revPublicAuthSetup.get("loginMechanism") + " Expected Is: " + loginMechanism);

			if (loginMechanism.contains("JWT")) {
				if (Boolean.valueOf(enableMultipleJWT) || Boolean.valueOf(addAnotherJWT)) {
					softAssert.assertEquals(revPublicAuthSetup.get("JWT1"),
							"Algorithm : RS512;Maximum Expiration Time : " + jwtMaximumExpirationTimePublic
									+ ";RSA Public Key : " + jwtRSAPublicKeyPublic.split(";")[0],
							"Create Sub-Brand: Review Page: The Actual And Expected Public JWT Details Does Not Match. Actual Is: "
									+ revPublicAuthSetup.get("JWT1")
									+ " Expected Is: Algorithm : RS512;Maximum Expiration Time : "
									+ jwtMaximumExpirationTimePublic + ";RSA Public Key : "
									+ jwtRSAPublicKeyPublic.split(";")[0]);
					softAssert.assertEquals(revPublicAuthSetup.get("JWT2"),
							"Algorithm : RS512;Maximum Expiration Time : " + jwtMaximumExpirationTimePublic
									+ ";RSA Public Key : " + jwtRSAPublicKeyPublic.split(";")[1],
							"Create Sub-Brand: Review Page: The Actual And Expected Public JWT Details Does Not Match. Actual Is: "
									+ revPublicAuthSetup.get("JWT2")
									+ " Expected Is: Algorithm : RS512;Maximum Expiration Time : "
									+ jwtMaximumExpirationTimePublic + ";RSA Public Key : "
									+ jwtRSAPublicKeyPublic.split(";")[1]);

				} else {
					softAssert.assertEquals(revPublicAuthSetup.get("JWT1"),
							"Algorithm : RS512;Maximum Expiration Time : " + jwtMaximumExpirationTimePublic
									+ ";RSA Public Key : " + jwtRSAPublicKeyPublic,
							"Create Sub-Brand: Review Page: The Actual And Expected Public JWT Details Does Not Match. Actual Is: "
									+ revPublicAuthSetup.get("JWT1")
									+ " Expected Is: Algorithm : RS512;Maximum Expiration Time : "
									+ jwtMaximumExpirationTimePublic + ";RSA Public Key : " + jwtRSAPublicKeyPublic);
				}
			}

			if (loginMechanism.contains("SAML")) {
				softAssert.assertEquals(revPublicAuthSetup.get("SAML"),
						"Login ID : " + credentialsLoginIdPublic + ";" + "SAML Certificate : saml.cer;SAML Issuer ID : "
								+ samlIssueIdPublic + ";" + "SSO Post Source : " + ssoPostSourcePublic,
						"Create Sub-Brand: Review Page: The Actual And Expected Public SAML Details Does Not Match. Actual Is: "
								+ revPublicAuthSetup.get("SAML") + " Expected Is: Login ID : "
								+ credentialsLoginIdPublic + ";" + "SAML Certificate : saml.cer;SAML Issuer ID : "
								+ samlIssueIdPublic + ";SSO Post Source : " + ssoPostSourcePublic);
			}

			HashMap<String, String> revProductDetails = reviewPage.getReviewScreenProductDetails(products);
			softAssert.assertEquals(revProductDetails.get("products"), products,
					"Create Sub-Brand: Review Page: The Actual And Expected Selected Products Does Not Match. Actual Is: "
							+ revProductDetails.get("products") + " Expected Is: " + products);

			String enabledDisabledSubProductsAggregation;
			/*
			 * if (subProductsAggregation.equalsIgnoreCase(""))
			 * enabledDisabledSubProductsAggregation = "Document Download disabled"; else
			 * enabledDisabledSubProductsAggregation = "Document Download enabled";
			 */

			/*
			 * if (products.contains("Aggregation"))
			 * softAssert.assertEquals(revProductDetails.get("aggregation"),
			 * enabledDisabledSubProductsAggregation,
			 * "Create Sub-Brand: Review Page: The Actual And Expected Aggregation Sub Products Does Not Match. Actual Is: "
			 * + revProductDetails.get("aggregation") + " Expected Is: " +
			 * enabledDisabledSubProductsAggregation);
			 */
			String enabledDisabledSubProductsVerification;
			/*
			 * if (subProductsVerification.equalsIgnoreCase(""))
			 * enabledDisabledSubProductsVerification = "Document Download disabled"; else
			 * enabledDisabledSubProductsVerification = "Document Download enabled";
			 */
			/*
			 * if (products.contains("Verification"))
			 * softAssert.assertEquals(revProductDetails.get("verification"),
			 * enabledDisabledSubProductsVerification,
			 * "Create Sub-Brand: Review Page: The Actual And Expected Verification Sub Products Does Not Match. Actual Is: "
			 * + revProductDetails.get("verification") + " Expected Is: " +
			 * enabledDisabledSubProductsVerification);
			 */

			HashMap<String, String> revHostedApps = reviewPage
					.getReviewScreenHostedApplicationDetails(hostedApplications);
			softAssert.assertEquals(revHostedApps.get("Yodlee FastLink"), "Enabled",
					"Create Sub-Brand: Review Page: The Actual And Expected Selected Yodlee FastLink Hosted Apps Does Not Match."
							+ " Actual Is: " + revHostedApps.get("Yodlee FastLink") + " Expected Is: Enabled");

			if (hostedApplications.contains("Yodlee Financial Wellness FinApps"))
				softAssert.assertEquals(revHostedApps.get("Yodlee Financial Wellness FinApps"), finapps_review,
						"Create Sub-Brand: Review Page: The Actual And Expected Selected Yodlee Financial Wellness FinApps Does Not Match."
								+ " Actual Is: " + revHostedApps.get("Yodlee Financial Wellness FinApps")
								+ " Expected Is: " + finapps_review);

			HashMap<String, String> revContactDetails = reviewPage.getReviewContactDetails(
					Boolean.valueOf(enableMultipleContacts), Boolean.valueOf(addAnotherContact));
			if (customerType.equalsIgnoreCase(Constants.CUSTOMER_TYPE_CHANNEL_PARTNER)) {
				softAssert.assertEquals(revContactDetails.get("ContactOneDetails"),
						"First Name : " + contactOneFN + ";Last Name : " + contactOneLN + ";" + "Contact Number : "
								+ contactOneContactNum + ";Email Address : " + contactOneEmail,
						"Create Sub-Brand: Review Page: The Actual And Expected Selected Contact 1 Details Does Not Match."
								+ " Actual Is: " + revContactDetails.get("ContactOneDetails") + " Expected Is: "
								+ "First Name : " + contactOneFN + ";Last Name : " + contactOneLN + ";"
								+ "Contact Number : " + contactOneContactNum + ";Email Address : " + contactOneEmail);
				if (Boolean.valueOf(enableMultipleContacts) || Boolean.valueOf(addAnotherContact)) {
					softAssert.assertEquals(revContactDetails.get("ContactTwoDetails"),
							"First Name : " + contactTwoFN + ";Last Name : " + contactTwoLN + ";" + "Contact Number : "
									+ contactTwoContactNum + ";Email Address : " + contactTwoEmail,
							"Create Sub-Brand: Review Page: The Actual And Expected Selected Contact 2 Details Does Not Match."
									+ " Actual Is: " + revContactDetails.get("ContactOneDetails") + " Expected Is: "
									+ "First Name : " + contactTwoFN + ";Last Name : " + contactTwoLN + ";"
									+ "Contact Number : " + contactTwoContactNum + ";Email Address : "
									+ contactTwoEmail);
				}
			} else {
				softAssert.assertEquals(revContactDetails.get("ContactOneDetails"),
						"First Name : " + contactOneFN + ";Last Name : " + contactOneLN + ";" + "Contact Number : "
								+ contactOneContactNum + ";Email Address : " + contactOneEmail + ";" + "User ID : "
								+ contactOneUserId,
						"Create Sub-Brand: Review Page: The Actual And Expected Selected Contact 1 Details Does Not Match."
								+ " Actual Is: " + revContactDetails.get("ContactOneDetails") + " Expected Is: "
								+ "First Name : " + contactOneFN + ";Last Name : " + contactOneLN + ";"
								+ "Contact Number : " + contactOneContactNum + ";Email Address : " + contactOneEmail
								+ ";" + "User ID : " + contactOneUserId);
				if (Boolean.valueOf(enableMultipleContacts) || Boolean.valueOf(addAnotherContact)) {
					softAssert.assertEquals(revContactDetails.get("ContactTwoDetails"),
							"First Name : " + contactTwoFN + ";Last Name : " + contactTwoLN + ";" + "Contact Number : "
									+ contactTwoContactNum + ";Email Address : " + contactTwoEmail + ";" + "User ID : "
									+ contactTwoUserId,
							"Create Sub-Brand: Review Page: The Actual And Expected Selected Contact 2 Details Does Not Match."
									+ " Actual Is: " + revContactDetails.get("ContactOneDetails") + " Expected Is: "
									+ "First Name : " + contactTwoFN + ";Last Name : " + contactTwoLN + ";"
									+ "Contact Number : " + contactTwoContactNum + ";Email Address : " + contactTwoEmail
									+ ";" + "User ID : " + contactTwoUserId);
				}

			}
			Assert.assertTrue(reviewPage.publishToPrivate().isDisplayed(),
					"Create Sub-Brand: Review Page: Publish To Private Button Not Found In Review Screen. Please Check!");
			reviewPage.publishToPrivate().click();

			Thread.sleep(3000);

			Assert.assertTrue(viewListOfSubBrandsPage.createSubBrandButton().isDisplayed(),
					"Not in View List of Sub-Brands Page. Create Sub-Brand Button Not Found.");

			Thread.sleep(2000);
			viewListOfSubBrandsPage.searchBySubBrand(subBrandName);

			String deploymentStatus = viewListOfSubBrandsPage.getDeploymentStatus(subBrandName);
			Assert.assertTrue(
					deploymentStatus.equalsIgnoreCase(Constants.DEPLOYMENT_STATUS_PUBLISH_INITIATED_IN_PRIVATE),
					"View List Of Sub Brand Page: Deployment Status Not Updated To Publish initiated in Private. Actual Is "
							+ deploymentStatus);

			List<WebElement> listOfSubBrands = viewListOfSubBrandsPage.getSubBrandNames();
			Assert.assertTrue(listOfSubBrands.size() == 1,
					"View List of Sub-Brands Page: More than One Sub-Brand Found With the Same Name. Which Is InCorrect. Please check. "
							+ "List Contains These Items" + Arrays.toString(listOfSubBrands.toArray())
							+ "Hence, Cannot Proceed With Publish Validations.");

			if (!deploymentStatus.equalsIgnoreCase(Constants.DEPLOYMENT_STATUS_PUBLISHED_IN_PRIVATE)) {
				int counter = 0;
				do {
					getWebdriver().navigate().refresh();
					Thread.sleep(3000);
					viewListOfSubBrandsPage.searchBySubBrand(subBrandName);
					Thread.sleep(3000);
					deploymentStatus = viewListOfSubBrandsPage.getDeploymentStatus(subBrandName);
					counter = counter + 1;
					Assert.assertTrue(
							!deploymentStatus.equalsIgnoreCase(Constants.DEPLOYMENT_STATUS_PUBLISH_FAILED_IN_PRIVATE),
							"View List of Sub-Brands Page: Publish Failed In Private. Cannot Proceed Further.");
					if (counter > 60)
						break;
				} while (!deploymentStatus.equalsIgnoreCase(Constants.DEPLOYMENT_STATUS_PUBLISHED_IN_PRIVATE));
			}
			deploymentStatus = viewListOfSubBrandsPage.getDeploymentStatus(subBrandName);
			Assert.assertTrue(deploymentStatus.equalsIgnoreCase(Constants.DEPLOYMENT_STATUS_PUBLISHED_IN_PRIVATE),
					"View List of Sub-Brands Page: Publish Not Completed Even After 5 Minutes. Deployment Status Still Shows:"
							+ " " + deploymentStatus + " For Sub Brand " + subBrandName);

			String environmentStatus = viewListOfSubBrandsPage.getEnvironmentStatus(subBrandName);
			Assert.assertTrue(environmentStatus.equalsIgnoreCase(Constants.ENVIRONMENT_STATUS_PRIVATE),
					"View List of Sub-Brands Page: Publish Not Completed Even After 5 Minutes. Environment Status Status Still Shows:"
							+ " " + environmentStatus + " For Sub Brand " + subBrandName);

			if (deploymentStatus.equalsIgnoreCase(Constants.DEPLOYMENT_STATUS_PUBLISHED_IN_PRIVATE))
				log.info("Sub Brand " + subBrandName + " Published To Private Successfully.");
			String lastPublished = viewListOfSubBrandsPage.getLastPublished(subBrandName);
			log.info("Sub Brand Details Are Mentioned Below");
			log.info("Sub Brand Name: " + subBrandName + "" + "\nDeployment Status: " + deploymentStatus + ""
					+ "\nEnvironment Status: " + environmentStatus + "" + "\nLast Published: " + lastPublished);

			// Refreshing the Page. To Initiating Publish To Public
			getWebdriver().navigate().refresh();

			listOfSubBrands.removeAll(listOfSubBrands);
			viewListOfSubBrandsPage.searchBySubBrand(subBrandName);
			listOfSubBrands = viewListOfSubBrandsPage.getSubBrandNames();
			Assert.assertTrue(listOfSubBrands.size() == 1,
					"View List of Sub-Brands Page: More than One Sub-Brand Found With the Same Name. Which Is InCorrect. Please check. "
							+ "List Contains These Items" + Arrays.toString(listOfSubBrands.toArray())
							+ "Hence, Cannot Proceed With Publish Validations.");

			String publishButtonName = viewListOfSubBrandsPage.getPublishBtnName(subBrandName);
			Assert.assertTrue(publishButtonName.equalsIgnoreCase("Publish to Public"),
					"View List Of Sub Brand Page: Button Name Is Not Publish To Production. Hence Cannot Continue to Publish To Public");

			viewListOfSubBrandsPage.clickPublishToPublic(subBrandName);
			Thread.sleep(2000);

			Assert.assertTrue(viewListOfSubBrandsPage.popUpPublishToPublicBtn().isDisplayed(),
					"View List Of Sub Brand Page: Publish to Public Button Not Displayed in Pop Up. "
							+ "Hence Cannot Continue with Publish Activity");

			viewListOfSubBrandsPage.popUpPublishToPublicBtn().click();

			Thread.sleep(5000);

			deploymentStatus = viewListOfSubBrandsPage.getDeploymentStatus(subBrandName);

			// Adding this additional dynamic check because at times, when publish to public
			// is clicked,
			// the time taken for the environment status to change to Publish initiated in
			// Public goes beyond 5 seconds.
			// Hence waiting for additional 5 seconds.
			// Rajeev 11-Oct-2019

			int depCounter = 0;
			while (deploymentStatus != Constants.DEPLOYMENT_STATUS_PUBLISH_INITIATED_IN_PUBLIC && depCounter < 5) {
				Thread.sleep(1000);
				deploymentStatus = viewListOfSubBrandsPage.getDeploymentStatus(subBrandName);
				if (depCounter > 4)
					break;
				depCounter++;
			}

			Assert.assertTrue(
					deploymentStatus.equalsIgnoreCase(Constants.DEPLOYMENT_STATUS_PUBLISH_INITIATED_IN_PUBLIC),
					"View List Of Sub Brand Page: Deployment Status Not Updated To Publish initiated in Public. Actual Is "
							+ deploymentStatus);
			if (!deploymentStatus.equalsIgnoreCase(Constants.DEPLOYMENT_STATUS_PUBLISHED_IN_PUBLIC)) {
				int counter = 0;
				do {
					getWebdriver().navigate().refresh();
					Thread.sleep(3000);
					viewListOfSubBrandsPage.searchBySubBrand(subBrandName);
					Thread.sleep(5000);
					deploymentStatus = viewListOfSubBrandsPage.getDeploymentStatus(subBrandName);
					counter = counter + 1;
					Assert.assertTrue(
							!deploymentStatus.equalsIgnoreCase(Constants.DEPLOYMENT_STATUS_PUBLISH_FAILED_IN_PUBLIC),
							"View List of Sub-Brands Page: Published Failed In Public. Cannot Proceed Further.");
					if (counter > 250)
						break;
				} while (!deploymentStatus.equalsIgnoreCase(Constants.DEPLOYMENT_STATUS_PUBLISHED_IN_PUBLIC));
			}
			deploymentStatus = viewListOfSubBrandsPage.getDeploymentStatus(subBrandName);
			Assert.assertTrue(deploymentStatus.equalsIgnoreCase(Constants.DEPLOYMENT_STATUS_PUBLISHED_IN_PUBLIC),
					"View List of Sub-Brands Page: Publish Not Completed Even After 5 Minutes. Deployment Status Still Shows:"
							+ " " + deploymentStatus + " For Sub Brand " + subBrandName);

			environmentStatus = viewListOfSubBrandsPage.getEnvironmentStatus(subBrandName);
			Assert.assertTrue(environmentStatus.equalsIgnoreCase(Constants.ENVIRONMENT_STATUS_PUBLIC),
					"View List of Sub-Brands Page: Publish Not Completed Even After 5 Minutes. Environment Status Status Still Shows:"
							+ " " + environmentStatus + " For Sub Brand " + subBrandName);

			if (deploymentStatus.equalsIgnoreCase(Constants.DEPLOYMENT_STATUS_PUBLISHED_IN_PUBLIC))
				log.info("Sub Brand " + subBrandName + " Published To Public Successfully.");
			lastPublished = viewListOfSubBrandsPage.getLastPublished(subBrandName);
			log.info("Sub Brand Details Are Mentioned Below");
			log.info("Sub Brand Name: " + subBrandName + "" + "\nDeployment Status: " + deploymentStatus + ""
					+ "\nEnvironment Status: " + environmentStatus + "" + "\nLast Published: " + lastPublished);

			// Subrand Overview
			getWebdriver().navigate().refresh();

			viewListOfSubBrandsPage.searchBySubBrand(subBrandName);
			viewListOfSubBrandsPage.subBrandNameLink(subBrandName).click();

			Assert.assertTrue(subBrandOverViewPage.subBrandOverViewEditButton().isDisplayed(),
					"Sub Brand Over View Page: Edit Button Not Displayed.");

			List<WebElement> listOfSubHeaders = subBrandOverViewPage.stepHeaderLabels();
			String[] expectedSubHeaders = { "General Information", "Selected Products", "Enabled Applications",
					"Support Team Details" };

			Assert.assertTrue(listOfSubHeaders.size() == expectedSubHeaders.length,
					"Sub Brand Overview Page: Actual Number Of Sub Headers Are More Than Expected. " + "Actual Is "
							+ listOfSubHeaders + " Expected Is " + expectedSubHeaders);

			int j = 0;
			for (WebElement e : listOfSubHeaders) {
				Assert.assertEquals(e.getText().trim(), expectedSubHeaders[j],
						"Sub Brand Over View Page: Actual And Expected Sub Headers In Sub Brand Over View Page Do Not Match."
								+ "Actual Is: " + e.getText().trim() + " Expected Is: " + expectedSubHeaders[j]);
				j++;
			}

			String customerSupportOwner = subBrandOverViewPage.sbOverviewGenConfigCustomerSupportOwnerValueTextLabel()
					.getText();
			Assert.assertEquals(customerSupportOwner, customerType,
					"Sub Brand Overview Page: Actual And Expected Customer Support Owner Are Not Matching."
							+ "Actual Is " + customerSupportOwner + " Expected Is " + customerType);

			Assert.assertTrue(subBrandOverViewPage.sbOverviewGenConfigApplPrivateURLValueTextLabel().isDisplayed(),
					"Sub Brand Overview Page: General Information Application Private URL Is Not Displayed.");

			log.info("Sub Brand Overview Page: General Information Step Header: Application Private URL Is: "
					+ subBrandOverViewPage.sbOverviewGenConfigApplPrivateURLValueTextLabel().getText());

			Assert.assertTrue(subBrandOverViewPage.sbOverviewGenConfigApplPublicURLValueTextLabel().isDisplayed(),
					"Sub Brand Overview Page: General Information Application Public URL Is Not Displayed.");

			log.info("Sub Brand Overview Page: General Information Step Header: Application Public URL Is: "
					+ subBrandOverViewPage.sbOverviewGenConfigApplPublicURLValueTextLabel().getText());

			Assert.assertTrue(subBrandOverViewPage.sbOverviewGenConfigYCCPrivateURLValueTextLabel().isDisplayed(),
					"Sub Brand Overview Page: General Information YCC Private URL Is Not Displayed.");

			log.info("Sub Brand Overview Page: General Information Step Header: YCC Private URL Is: "
					+ subBrandOverViewPage.sbOverviewGenConfigYCCPrivateURLValueTextLabel().getText());

			Assert.assertTrue(subBrandOverViewPage.sbOverviewGenConfigYCCPublicURLValueTextLabel().isDisplayed(),
					"Sub Brand Overview Page: General Information YCC Public URL Is Not Displayed.");

			log.info("Sub Brand Overview Page: General Information Step Header: YCC Public URL Is: "
					+ subBrandOverViewPage.sbOverviewGenConfigYCCPublicURLValueTextLabel().getText());

			Assert.assertTrue(subBrandOverViewPage.sbOverviewGenConfigYSLPrivateURLValueTextLabel().isDisplayed(),
					"Sub Brand Overview Page: General Information YSL Private URL Is Not Displayed.");

			log.info("Sub Brand Overview Page: General Information Step Header: YSL Private URL Is: "
					+ subBrandOverViewPage.sbOverviewGenConfigYSLPrivateURLValueTextLabel().getText());

			Assert.assertTrue(subBrandOverViewPage.sbOverviewGenConfigYSLPublicURLValueTextLabel().isDisplayed(),
					"Sub Brand Overview Page: General Information YSL Public URL Is Not Displayed.");

			log.info("Sub Brand Overview Page: General Information Step Header: YSL Public URL Is: "
					+ subBrandOverViewPage.sbOverviewGenConfigYSLPublicURLValueTextLabel().getText());

			if (products.contains("Aggregation")) {
				String aggregationProduct = subBrandOverViewPage.sbOverviewSelectedProductAggregationTextLabel()
						.getText();
				Assert.assertTrue(aggregationProduct.equalsIgnoreCase("Aggregation"),
						"Sub Brand Overview Page: "
								+ "Actual And Expected Aggregation Product Are Not Matching. Actual Is "
								+ aggregationProduct + " Expected Is Aggregation");
			}
			if (products.contains("Verification")) {
				String verificationProduct = subBrandOverViewPage.sbOverviewSelectedProductVerificationTextLabel()
						.getText();
				Assert.assertTrue(verificationProduct.equalsIgnoreCase("Verification"),
						"Sub Brand Overview Page: "
								+ "Actual And Expected Verification Product Are Not Matching. Actual Is "
								+ verificationProduct + " Expected Is Verification");
			}

			Assert.assertTrue(subBrandOverViewPage.sbOverviewEnabledApplicationFastLinkTextLabel().isDisplayed(),
					"Sub Brand Overview Page: Enabled Applications: Yodlee FastLink Not Displayed");

			if (hostedApplications.contains("Yodlee Financial Wellness FinApps")) {

				Assert.assertTrue(
						subBrandOverViewPage.sbOverviewEnabledApplicationsWellnessFinAppsTextLabel().isDisplayed(),
						"Sub Brand Overview Page: Enabled Applications: Yodlee Financial Wellness FinApps Not Displayed");
				Thread.sleep(10000);
				if (finapps_overview.contains(";")) {
					List<WebElement> listOfEnabledFinapps = subBrandOverViewPage
							.sbOverviewListOfEnabledApplicationsWellnessFinAppsValueTxtLbl();
					List<String> arrlistOfEnabledFinapps = new ArrayList();
					for (WebElement e : listOfEnabledFinapps) {
						arrlistOfEnabledFinapps.add(e.getText());
					}

					String listOfFinApps = arrlistOfEnabledFinapps.stream().collect(Collectors.joining(";"));
					System.out.println("Finapps are: " + listOfFinApps);

					softAssert.assertEquals(listOfFinApps, finapps_overview,
							"Sub Brand Overiew Page: Enabled Finapps: "
									+ "Actual And Expected Finapps Are Not Matching. Actual Is " + listOfFinApps
									+ " Expected " + "Is: " + finapps_overview);
				} else {
					List<WebElement> listOfEnabledFinapps = subBrandOverViewPage
							.sbOverviewListOfEnabledApplicationsWellnessFinAppsValueTxtLbl();
					String finApp = listOfEnabledFinapps.get(0).getText();
					Assert.assertEquals(finApp, finapps_overview,
							"Sub Brand Overiew Page: Enabled Finapps: "
									+ "Actual And Expected Finapps Are Not Matching. Actual Is " + finApp + " Expected "
									+ "Is: " + finapps_overview);
				}
			}

			String contactOneName = subBrandOverViewPage.sbOverviewContactOneNameValueTextLabel().getText();
			Assert.assertEquals(contactOneName, contactOneFN + " " + contactOneLN,
					"Sub Brand Overview Page: Support Team Details: "
							+ "Actual And Expected Contact One Name Does Not Match." + "Actual Is: " + contactOneName
							+ " Expected Is " + contactOneFN + " " + contactOneLN);

			String contactOneUserID;
			if (customerType.equalsIgnoreCase(Constants.CUSTOMER_TYPE_SUB_BRAND)) {
				contactOneUserID = subBrandOverViewPage.sbOverviewContactOneUserIdValueTextLabel().getText();
				Assert.assertEquals(contactOneUserID, contactOneUserId,
						"Sub Brand Overview Page: Support Team Details: "
								+ "Actual And Expected Contact One User Id Does Not Match." + "Actual Is: "
								+ contactOneUserID + " Expected Is " + contactOneUserId);
				String contactOneEmailID = subBrandOverViewPage.sbOverviewContactOneEmailValueTextLabel("3").getText();
				Assert.assertEquals(contactOneEmailID, contactOneEmail,
						"Sub Brand Overview Page: Support Team Details: "
								+ "Actual And Expected Contact One Email Address Does Not Match." + "Actual Is: "
								+ contactOneEmailID + " Expected Is " + contactOneEmail);

				String contactOneContactNumber = subBrandOverViewPage
						.sbOverviewContactOneContactNumberValueTextLabel("4").getText();
				Assert.assertEquals(contactOneContactNumber, contactOneContactNum,
						"Sub Brand Overview Page: Support Team Details: "
								+ "Actual And Expected Contact One Contact Number Does Not Match." + "Actual Is: "
								+ contactOneContactNumber + " Expected Is " + contactOneContactNum);
			} else {
				String contactOneEmailID = subBrandOverViewPage.sbOverviewContactOneEmailValueTextLabel("2").getText();
				Assert.assertEquals(contactOneEmailID, contactOneEmail,
						"Sub Brand Overview Page: Support Team Details: "
								+ "Actual And Expected Contact One Email Address Does Not Match." + "Actual Is: "
								+ contactOneEmailID + " Expected Is " + contactOneEmail);

				String contactOneContactNumber = subBrandOverViewPage
						.sbOverviewContactOneContactNumberValueTextLabel("3").getText();
				Assert.assertEquals(contactOneContactNumber, contactOneContactNum,
						"Sub Brand Overview Page: Support Team Details: "
								+ "Actual And Expected Contact One Contact Number Does Not Match." + "Actual Is: "
								+ contactOneContactNumber + " Expected Is " + contactOneContactNum);
			}

			if (Boolean.valueOf(enableMultipleContacts) || Boolean.valueOf(addAnotherContact)) {
				String contactTwoName = subBrandOverViewPage.sbOverviewContactTwoNameValueTextLabel().getText();
				Assert.assertEquals(contactTwoName, contactTwoFN + " " + contactTwoLN,
						"Sub Brand Overview Page: Support Team Details: "
								+ "Actual And Expected Contact Two Name Does Not Match." + "Actual Is: "
								+ contactTwoName + " Expected Is " + contactTwoFN + " " + contactTwoLN);

				String contactTwoUserID;
				if (customerType.equalsIgnoreCase(Constants.CUSTOMER_TYPE_SUB_BRAND)) {
					contactTwoUserID = subBrandOverViewPage.sbOverviewContactTwoUserIdValueTextLabel().getText();
					Assert.assertEquals(contactTwoUserID, contactTwoUserId,
							"Sub Brand Overview Page: Support Team Details: "
									+ "Actual And Expected Contact Two User Id Does Not Match." + "Actual Is: "
									+ contactTwoUserID + " Expected Is " + contactTwoUserId);

					String contactTwoEmailID = subBrandOverViewPage.sbOverviewContactTwoEmailValueTextLabel("3")
							.getText();
					Assert.assertEquals(contactTwoEmailID, contactTwoEmail,
							"Sub Brand Overview Page: Support Team Details: "
									+ "Actual And Expected Contact Two Email Address Does Not Match." + "Actual Is: "
									+ contactTwoEmailID + " Expected Is " + contactTwoEmail);

					String contactTwoContactNumber = subBrandOverViewPage
							.sbOverviewContactTwoContactNumberValueTextLabel("4").getText();
					Assert.assertEquals(contactTwoContactNumber, contactTwoContactNum,
							"Sub Brand Overview Page: Support Team Details: "
									+ "Actual And Expected Contact Two Contact Number Does Not Match." + "Actual Is: "
									+ contactTwoContactNumber + " Expected Is " + contactTwoContactNum);

				} else {
					String contactTwoEmailID = subBrandOverViewPage.sbOverviewContactTwoEmailValueTextLabel("2")
							.getText();
					Assert.assertEquals(contactTwoEmailID, contactTwoEmail,
							"Sub Brand Overview Page: Support Team Details: "
									+ "Actual And Expected Contact Two Email Address Does Not Match." + "Actual Is: "
									+ contactTwoEmailID + " Expected Is " + contactTwoEmail);

					String contactTwoContactNumber = subBrandOverViewPage
							.sbOverviewContactTwoContactNumberValueTextLabel("3").getText();
					Assert.assertEquals(contactTwoContactNumber, contactTwoContactNum,
							"Sub Brand Overview Page: Support Team Details: "
									+ "Actual And Expected Contact Two Contact Number Does Not Match." + "Actual Is: "
									+ contactTwoContactNumber + " Expected Is " + contactTwoContactNum);
				}
			}

			Assert.assertTrue(subBrandOverViewPage.backToSubBrandListLink().isDisplayed(),
					"Sub Brand Overview Page: Back To Sub Brand List Link Not Displayed.");
			subBrandOverViewPage.backToSubBrandListLink().click();

			Assert.assertTrue(viewListOfSubBrandsPage.searchFilterTxtBx().isDisplayed(),
					"View List Of Sub Brand Page: Search Filter Text Box Not Displayed.");

			Thread.sleep(1000);
			validateCustomization(subBrandName);

			softAssert.assertAll();
		} else {
			log.info("TEST CASE - " + testID + " - " + testName + " IS NOT ENABLED. HENCE, SKIPPING");
		}
	}

	public void validateCustomization(String subBrandName) throws InterruptedException {

		SoftAssert softAssert = new SoftAssert();
		// subBrandName="QAAutoSub1569327176345";
		viewListOfSubBrandsPage.clickConfigureApps(subBrandName);

		WebDriverWait wait = new WebDriverWait(getWebdriver(), 10);
		wait.until(ExpectedConditions.visibilityOf(customizationToolFeaturesPage.featuresTab()));

		softAssert.assertTrue(customizationToolFeaturesPage.featuresTab().isDisplayed(),
				"Features Tab Not Displayed In Customization Page.");
		softAssert.assertTrue(customizationToolDesignPage.designTab().isDisplayed(),
				"Design Tab Not Displayed In Customization Page.");
		softAssert.assertTrue(customizationToolTextPage.textTab().isDisplayed(),
				"" + "Text Tab Not Displayed In Customization Page.");

		String subBrandNameValueCustomization = customizationToolCommonPage.subBrandNameLabel().getText().trim();

		softAssert.assertEquals(subBrandNameValueCustomization, subBrandName,
				"The Actual And Expected Sub-brand Names Are Not Matching. " + "Actual Is: "
						+ subBrandNameValueCustomization + " Expected Is: " + subBrandName);

		softAssert.assertTrue(
				customizationToolFeaturesPage.featuresTab().getAttribute("aria-selected").equalsIgnoreCase("true"),
				"Customization: Features Tab Is Not Selected By Default");

		if (customizationToolFeaturesPage.featuresTab().getAttribute("aria-selected").equalsIgnoreCase("false")) {
			Assert.assertFalse(customizationToolFeaturesPage.applyToAllLink().isDisplayed(),
					"Customization Features Tab: Apply To All Link Not Displayed.");

			customizationToolFeaturesPage.applyToAllLink().click();
			Thread.sleep(1000);

			Assert.assertTrue(
					customizationToolFeaturesPage.applyToAllLink().getAttribute("class").contains("is-active"),
					"Customization: Features Tab: Apply To All Link Is Not Expanded.");
		}

		customizationToolDesignPage.designTab().click();

		wait = new WebDriverWait(getWebdriver(), 10);
		wait.until(ExpectedConditions.visibilityOf(customizationToolDesignPage.designTabApplyToAllLink()));

		if (customizationToolDesignPage.designTab().getAttribute("aria-selected").equalsIgnoreCase("true")) {
			Assert.assertTrue(customizationToolDesignPage.designTabApplyToAllLink().isDisplayed(),
					"Customization: Design Tab: Apply To All Link Not Displayed");

			customizationToolDesignPage.designTabApplyToAllLink().click();
			Thread.sleep(1000);

			Assert.assertTrue(
					customizationToolDesignPage.designTabApplyToAllLink().getAttribute("class").contains("is-active"),
					"Customization: Features Tab: Apply To All Link Is Not Expanded.");
		}
		customizationToolTextPage.textTab().click();

		wait = new WebDriverWait(getWebdriver(), 10);
		wait.until(ExpectedConditions.visibilityOf(customizationToolTextPage.fastLinkLink()));

		if (customizationToolTextPage.textTab().getAttribute("aria-selected").equalsIgnoreCase("true")) {
			Assert.assertTrue(customizationToolTextPage.fastLinkLink().isDisplayed(),
					"Customization: Design Tab: FastLink Link Not Displayed");

			customizationToolTextPage.fastLinkLink().click();
			Thread.sleep(1000);

			Assert.assertTrue(customizationToolTextPage.fastLinkLink().getAttribute("class").contains("is-active"),
					"Customization: Features Tab: Headers Link Is Not Expanded.");
		}

		softAssert.assertTrue(customizationToolCommonPage.backToSubBrandListLink().isDisplayed(),
				"Customization: Back To Sub-brand List Link Not Displayed.");

		/*
		Preview Functionality has been removed from Sub-branding Tool. Hence commenting the Preview use case until it is confirmed.
		*/
		/*
		 * softAssert.assertTrue(customizationToolCommonPage.previewButton().isDisplayed
		 * (), "Customization: Preview Button Not Displayed.");
		 * 
		 * softAssert.assertTrue(customizationToolCommonPage.saveButton().isDisplayed(),
		 * "Customization: Save Button Not Displayed.");
		 */

		if (customizationToolCommonPage.backToSubBrandListLink().isDisplayed()) {
			customizationToolCommonPage.backToSubBrandListLink().click();

			Assert.assertTrue(viewListOfSubBrandsPage.createSubBrandButton().isDisplayed(),
					"View List To Sub-brand Page: Clicking on Back To Sub-brand List In "
							+ "Customization Screen Did Not Return to View List Of Sub-brands Page. Please Check.");
		}

		softAssert.assertAll();
	}

}