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
import com.yodlee.customizationtool.page.*;
import com.yodlee.customizationtool.util.*;
import org.databene.benerator.anno.Source;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import sun.net.www.content.text.Generic;

import java.util.*;
import java.util.stream.Collectors;

public class CreateSubBrandAndCustomize extends BaseTest {

	private Logger log = LoggerFactory.getLogger(CreateSubBrandAndCustomize.class);

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
	SAMLPage samlPage;
	DashboardPage dashboardPage;
	NodeLoginPage nodeLoginPage;

	String subBrandName;
	String domainNameSubString;

	/*
	 * @BeforeClass public void init() throws InterruptedException {
	 * 
	 *//*
		 * loginPage.loginToSubBrandManagementPortal( dataProperty.getLoginUsername(),
		 * dataProperty.getLoginPassword()); Thread.sleep(2000);
		 *//*
			 * 
			 * }
			 */

	@BeforeTest
	public void setup() throws InterruptedException {

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
		samlPage = PageFactory.initElements(getWebdriver(), SAMLPage.class);
		dashboardPage = PageFactory.initElements(getWebdriver(), DashboardPage.class);
		nodeLoginPage = PageFactory.initElements(getWebdriver(), NodeLoginPage.class);

		loginPage.loginToSubBrandManagementPortal(dataProperty.getLoginUsername(), dataProperty.getLoginPassword());
		Thread.sleep(2000);
	}

	@AfterClass
	public void logout() {
		viewListOfSubBrandsPage.logout();
	}

	@Test(priority = 1, enabled = true, dataProvider = "feeder")
	@Source("src\\test\\resources\\testdata\\CreateAndCustomizeSubBrand.csv")
	public void testCreateSubBrandCustomizePublishAndViewSubBrandOverview(String testID, String enabled,
			String testName, String sourceIPAddress, String customerType, String loginMechanism,
			String credentialsLoginIdPrivate, String samlCertificateNamePrivate, String samlIssueIdPrivate,
			String ssoPostSourcePrivate, String jwtMaximumExpirationTimePrivate, String credentialsLoginIdPublic,
			String samlCertificateNamePublic, String samlIssueIdPublic, String ssoPostSourcePublic,
			String jwtMaximumExpirationTimePublic, String products, String subProductsAggregation,
			String subProductsVerification, String hostedApplications, String finapps_review, String finapps_overview, String contactOneFN,
			String contactOneLN, String contactOneEmail, String contactOneContactNum, String contactOneUserId,
			String contactTwoFN, String contactTwoLN, String contactTwoEmail, String contactTwoContactNum,
			String contactTwoUserId, String enableMultipleContacts, String enableMultipleJWT, String addAnotherContact,
			String addAnotherJWT) throws Exception {

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
			if (subProductsAggregation.equalsIgnoreCase(""))
				enabledDisabledSubProductsAggregation = "Document Download disabled";
			else
				enabledDisabledSubProductsAggregation = "Document Download enabled";

			if (products.contains("Aggregation"))
				softAssert.assertEquals(revProductDetails.get("aggregation"), enabledDisabledSubProductsAggregation,
						"Create Sub-Brand: Review Page: The Actual And Expected Aggregation Sub Products Does Not Match. Actual Is: "
								+ revProductDetails.get("aggregation") + " Expected Is: "
								+ enabledDisabledSubProductsAggregation);

			String enabledDisabledSubProductsVerification;
			if (subProductsVerification.equalsIgnoreCase(""))
				enabledDisabledSubProductsVerification = "Document Download disabled";
			else
				enabledDisabledSubProductsVerification = "Document Download enabled";
			if (products.contains("Verification"))
				softAssert.assertEquals(revProductDetails.get("verification"), enabledDisabledSubProductsVerification,
						"Create Sub-Brand: Review Page: The Actual And Expected Verification Sub Products Does Not Match. Actual Is: "
								+ revProductDetails.get("verification") + " Expected Is: "
								+ enabledDisabledSubProductsVerification);
			

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

			Assert.assertTrue(reviewPage.configureAppsButton().isDisplayed(),
					"Create Sub-Brand: Review Page: Configure Apps Button Not Found In Review Screen. Please Check!");
			reviewPage.configureAppsButton().click();

			Thread.sleep(1000);

			customizeSubBrand(subBrandName);
			Thread.sleep(1000);
			viewListOfSubBrandsPage.clickPublishToPrivate(subBrandName);

			Thread.sleep(1000);
			Assert.assertTrue(viewListOfSubBrandsPage.popUpPublishToPrivateBtn().isDisplayed(),
					"Create Sub-Brand: Review Page: Publish To Private Button Not Found "
							+ "In View List Of Sub-brand Page Pop Up. Please Check!");
			Thread.sleep(1000);
			viewListOfSubBrandsPage.popUpPublishToPrivateBtn().click();
			Thread.sleep(2000);

			Assert.assertTrue(viewListOfSubBrandsPage.createSubBrandButton().isDisplayed(),
					"Not in View List of Sub-Brands Page. Create Sub-Brand Button Not Found.");

			Thread.sleep(5000);
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
					Thread.sleep(3000);
					deploymentStatus = viewListOfSubBrandsPage.getDeploymentStatus(subBrandName);
					counter = counter + 1;
					Assert.assertTrue(
							!deploymentStatus.equalsIgnoreCase(Constants.DEPLOYMENT_STATUS_PUBLISH_FAILED_IN_PUBLIC),
							"View List of Sub-Brands Page: Published Failed In Public. Cannot Proceed Further.");
					if (counter > 60)
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

			String applicationPrivateURL = subBrandOverViewPage.sbOverviewGenConfigApplPrivateURLValueTextLabel()
					.getText();
			log.info("Sub Brand Overview Page: General Information Step Header: Application Private URL Is: "
					+ subBrandOverViewPage.sbOverviewGenConfigApplPrivateURLValueTextLabel().getText());

			Assert.assertTrue(subBrandOverViewPage.sbOverviewGenConfigApplPublicURLValueTextLabel().isDisplayed(),
					"Sub Brand Overview Page: General Information Application Public URL Is Not Displayed.");

			String applicationPublicURL = subBrandOverViewPage.sbOverviewGenConfigApplPublicURLValueTextLabel()
					.getText();
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

			/*
			 * subBrandName="QAAutoSub1574330426275";
			 * domainNameSubString="DNS1574330426275"; credentialsLoginIdPrivate =
			 * "testCredPrivate"; String applicationPrivateURL =
			 * "https://10.79.6.106/authenticate/DNS1574330426275/?channelAppName=pfml1stage";
			 * 
			 */

			Thread.sleep(2000);
			if (envDetails.getIsPrivateNonSSO().equalsIgnoreCase("true")) {

				String query = "select cob_password from SDP_SUBBRAND where name='" + subBrandName
						+ "' and Is_Private='1'";
				String toBeRegisteredUsername = dataProperty.getToBeRegisteredUserName()
						+ GenericUtils.getAlphaNumericString(5);
				String toBeRegisteredPassword = dataProperty.getToBeRegisteredUserPassword();
				String cobPassword = DBUtils.getDecryptedPassword(envDetails.getSdpServerName(),
						envDetails.getSdpServerPort(), envDetails.getSdpServiceName(), envDetails.getSdpDBUsername(),
						envDetails.getSdpDBPassword(), query);

				String newRegisteredUser = RestUtils.initCobrand(String.valueOf(envDetails.getRestURLEncoding()),
						envDetails.getRestVersion(), envDetails.getRestBaseURIPrivate() + domainNameSubString + "/",
						credentialsLoginIdPrivate, cobPassword, toBeRegisteredUsername, toBeRegisteredPassword);
				// System.out.print("\n The User is [" + user + "]");
				String nodeLoginUrl = applicationPrivateURL.replace("authenticate", "authtest");

				getWebdriver().navigate().to(nodeLoginUrl);
				Thread.sleep(2000);

				nodeLoginPage.loginToNode(newRegisteredUser, toBeRegisteredPassword, envDetails.getSamlFinAppID());
				Thread.sleep(5000);

				WebDriverWait wait = new WebDriverWait(getWebdriver(), 100);
				wait.until(ExpectedConditions.visibilityOf(dashboardPage.linkAccountButton()));

				Assert.assertTrue(dashboardPage.linkAccountButton().isDisplayed(),
						"Dashboard Page: Link Account Button Not Found. Please Check !");

				dashboardPage.addAccount(dataProperty.getDagSiteName(), dataProperty.getDagUsername(),
						dataProperty.getDagPassword());
				Thread.sleep(3000);

				wait = new WebDriverWait(getWebdriver(), 60);
				wait.until(ExpectedConditions.textToBePresentInElement(dashboardPage.toastMessageLabel(),
						"Congratulations! Your accounts have been linked."));

				Assert.assertTrue(dashboardPage.toastMessageLabel().isDisplayed(),
						"Toast Message Not Found. Account Addition May Have Failed. Please Check !");

				Assert.assertEquals(dashboardPage.toastMessageLabel().getText(),
						"Congratulations! Your accounts have been linked.",
						"The Actual And Expected Toast Message Does Not Match. Actual Is: "
								+ dashboardPage.toastMessageLabel().getText() + " Expected Is: "
								+ "Congratulations! Your accounts have been linked.");

				dashboardPage.closeButton().click();
				Thread.sleep(3000);

				getWebdriver().get(envDetails.getListSubbrandUrl());
				Thread.sleep(2000);

			} else if (envDetails.getIsPrivateNonSSO().equalsIgnoreCase("false")) {

				getWebdriver().navigate().to(envDetails.getSamlNodeURL());
				Thread.sleep(2000);

				// SAML Login
				String subjectNameUserID = subBrandName + "_" + GenericUtils.getAlphaNumericString(5);
				samlPage.enterSAMLDetails(samlIssueIdPrivate, subjectNameUserID, applicationPrivateURL,
						envDetails.getSamlFinAppID(), envDetails.getSamlExtraParams(),
						envDetails.getSamlRedirectRequest(), envDetails.getSamlAttributes(),
						envDetails.getSamlVersion(), envDetails.getSamlAssertionEncryption(),
						envDetails.getSamlMultipleKeys(), envDetails.getSamlAttributeEncryption(),
						envDetails.getSamlAttributeEncryptionMechanism(), envDetails.getSamlAttributeEncoding(),
						envDetails.getSamlSignResponse(), envDetails.getSamlSignAssertion(),
						envDetails.getSamlSigningAliasKey(), envDetails.getSamlSSOAttributeKey(),
						envDetails.getSamlLinkIntegrityToken());

				Thread.sleep(2000);

				Assert.assertEquals(samlPage.headerLabel().getText(), "SAML POST FORM",
						"The Actual And The Expected Header Does Not Match. Actual Is: "
								+ samlPage.headerLabel().getText() + " Expected Is: SAML POST FORM");

				samlPage.submitButton().click();
				Thread.sleep(2000);

				WebDriverWait wait = new WebDriverWait(getWebdriver(), 100);
				wait.until(ExpectedConditions.visibilityOf(dashboardPage.linkAccountButton()));

				Assert.assertTrue(dashboardPage.linkAccountButton().isDisplayed(),
						"Dashboard Page: Link Account Button Not Found. Please Check !");

				dashboardPage.addAccount(dataProperty.getDagSiteName(), dataProperty.getDagUsername(),
						dataProperty.getDagPassword());
				Thread.sleep(4000);

				wait = new WebDriverWait(getWebdriver(), 60);
				wait.until(ExpectedConditions.textToBePresentInElement(dashboardPage.toastMessageLabel(),
						"Congratulations! Your accounts have been linked."));

				Assert.assertTrue(dashboardPage.toastMessageLabel().isDisplayed(),
						"Toast Message Not Found. Account Addition May Have Failed. Please Check !");

				Assert.assertEquals(dashboardPage.toastMessageLabel().getText(),
						"Congratulations! Your accounts have been linked.",
						"The Actual And Expected Toast Message Does Not Match. Actual Is: "
								+ dashboardPage.toastMessageLabel().getText() + " Expected Is: "
								+ "Congratulations! Your accounts have been linked.");

				dashboardPage.closeButton().click();
				Thread.sleep(3000);

				getWebdriver().get(envDetails.getListSubbrandUrl());
				Thread.sleep(2000);

			}

			/*// Sub-brand Login and Sanity is being verified in Private Environment. This further needs enhancement to cover in Public Enviroment.
			 * 
			 * credentialsLoginIdPublic="testCredPublic"; String applicationPublicURL=
			 * "https://10.79.8.1/authenticate/DNS1574330426275/?channelAppName=pfml1stage";
			 */
			/*
			 * //Sanity of public Enrionment if
			 * (envDetails.getIsPublicNonSSO().equalsIgnoreCase("true")) {
			 * 
			 * String query = "select cob_password from SDP_SUBBRAND where name='" +
			 * subBrandName + "' and Is_Private='0'"; String toBeRegisteredUsername =
			 * dataProperty.getToBeRegisteredUserName() +
			 * GenericUtils.getAlphaNumericString(5); String toBeRegisteredPassword =
			 * dataProperty.getToBeRegisteredUserPassword(); String cobPassword =
			 * DBUtils.getDecryptedPassword(envDetails.getSdpServerName(),
			 * envDetails.getSdpServerPort(), envDetails.getSdpServiceName(),
			 * envDetails.getSdpDBUsername(), envDetails.getSdpDBPassword(), query);
			 * 
			 * String newRegisteredUser =
			 * RestUtils.initCobrand(String.valueOf(envDetails.getRestURLEncoding()),
			 * envDetails.getRestVersion(), envDetails.getRestBaseURIPrivate() +
			 * domainNameSubString + "/", credentialsLoginIdPublic, cobPassword,
			 * toBeRegisteredUsername, toBeRegisteredPassword); //
			 * System.out.print("\n The User is [" + user + "]"); String nodeLoginUrl =
			 * applicationPublicURL.replace("authenticate", "authtest");
			 * 
			 * getWebdriver().navigate().to(nodeLoginUrl); Thread.sleep(2000);
			 * 
			 * nodeLoginPage.loginToNode(newRegisteredUser, toBeRegisteredPassword,
			 * envDetails.getSamlFinAppID()); Thread.sleep(5000);
			 * 
			 * WebDriverWait wait = new WebDriverWait(getWebdriver(), 100);
			 * wait.until(ExpectedConditions.visibilityOf(dashboardPage.linkAccountButton())
			 * );
			 * 
			 * Assert.assertTrue(dashboardPage.linkAccountButton().isDisplayed(),
			 * "Dashboard Page: Link Account Button Not Found. Please Check !");
			 * 
			 * dashboardPage.addAccount(dataProperty.getDagSiteName(),
			 * dataProperty.getDagUsername(), dataProperty.getDagPassword());
			 * Thread.sleep(4000);
			 * 
			 * wait = new WebDriverWait(getWebdriver(), 60);
			 * wait.until(ExpectedConditions.textToBePresentInElement(dashboardPage.
			 * toastMessageLabel(), "Congratulations! Your accounts have been linked."));
			 * 
			 * Assert.assertTrue(dashboardPage.toastMessageLabel().isDisplayed(),
			 * "Toast Message Not Found. Account Addition May Have Failed. Please Check !");
			 * 
			 * Assert.assertEquals(dashboardPage.toastMessageLabel().getText(),
			 * "Congratulations! Your accounts have been linked.",
			 * "The Actual And Expected Toast Message Does Not Match. Actual Is: " +
			 * dashboardPage.toastMessageLabel().getText() + " Expected Is: " +
			 * "Congratulations! Your accounts have been linked.");
			 * 
			 * dashboardPage.closeButton().click(); Thread.sleep(3000);
			 * 
			 * getWebdriver().get(envDetails.getListSubbrandUrl()); Thread.sleep(2000);
			 * 
			 * } else if (envDetails.getIsPublicNonSSO().equalsIgnoreCase("false")) {
			 * 
			 * getWebdriver().navigate().to(envDetails.getSamlNodeURL());
			 * Thread.sleep(2000);
			 * 
			 * // SAML Login String subjectNameUserID = subBrandName + "_" +
			 * GenericUtils.getAlphaNumericString(5);
			 * samlPage.enterSAMLDetails(samlIssueIdPublic, subjectNameUserID,
			 * applicationPublicURL, envDetails.getSamlFinAppID(),
			 * envDetails.getSamlExtraParams(), envDetails.getSamlRedirectRequest(),
			 * envDetails.getSamlAttributes(), envDetails.getSamlVersion(),
			 * envDetails.getSamlAssertionEncryption(), envDetails.getSamlMultipleKeys(),
			 * envDetails.getSamlAttributeEncryption(),
			 * envDetails.getSamlAttributeEncryptionMechanism(),
			 * envDetails.getSamlAttributeEncoding(), envDetails.getSamlSignResponse(),
			 * envDetails.getSamlSignAssertion(), envDetails.getSamlSigningAliasKey(),
			 * envDetails.getSamlSSOAttributeKey(), envDetails.getSamlLinkIntegrityToken());
			 * 
			 * Thread.sleep(2000);
			 * 
			 * Assert.assertEquals(samlPage.headerLabel().getText(), "SAML POST FORM",
			 * "The Actual And The Expected Header Does Not Match. Actual Is: " +
			 * samlPage.headerLabel().getText() + " Expected Is: SAML POST FORM");
			 * 
			 * samlPage.submitButton().click(); Thread.sleep(2000);
			 * 
			 * WebDriverWait wait = new WebDriverWait(getWebdriver(), 100);
			 * wait.until(ExpectedConditions.visibilityOf(dashboardPage.linkAccountButton())
			 * );
			 * 
			 * Assert.assertTrue(dashboardPage.linkAccountButton().isDisplayed(),
			 * "Dashboard Page: Link Account Button Not Found. Please Check !");
			 * 
			 * dashboardPage.addAccount(dataProperty.getDagSiteName(),
			 * dataProperty.getDagUsername(), dataProperty.getDagPassword());
			 * Thread.sleep(4000);
			 * 
			 * wait = new WebDriverWait(getWebdriver(), 60);
			 * wait.until(ExpectedConditions.textToBePresentInElement(dashboardPage.
			 * toastMessageLabel(), "Congratulations! Your accounts have been linked."));
			 * 
			 * Assert.assertTrue(dashboardPage.toastMessageLabel().isDisplayed(),
			 * "Toast Message Not Found. Account Addition May Have Failed. Please Check !");
			 * 
			 * Assert.assertEquals(dashboardPage.toastMessageLabel().getText(),
			 * "Congratulations! Your accounts have been linked.",
			 * "The Actual And Expected Toast Message Does Not Match. Actual Is: " +
			 * dashboardPage.toastMessageLabel().getText() + " Expected Is: " +
			 * "Congratulations! Your accounts have been linked.");
			 * 
			 * dashboardPage.closeButton().click(); Thread.sleep(3000);
			 * 
			 * getWebdriver().get(envDetails.getListSubbrandUrl()); Thread.sleep(2000); }
			 */

			softAssert.assertAll();
		} else {
			log.info("TEST CASE - " + testID + " - " + testName + " IS NOT ENABLED. HENCE, SKIPPING");
		}
	}

	
	public void customizeSubBrand(String subBrandName) throws InterruptedException {

		CustomizationUtils.loadCustomizationParams();

		SoftAssert softAssert = new SoftAssert();
		/*
		 * subBrandName="QAAutoSub1572922393804";
		 * viewListOfSubBrandsPage.searchBySubBrand(subBrandName);
		 * viewListOfSubBrandsPage.clickConfigureApps(subBrandName);
		 */

		WebDriverWait wait = new WebDriverWait(getWebdriver(), 10);
		wait.until(ExpectedConditions.visibilityOf(customizationToolFeaturesPage.featuresTab()));

		softAssert.assertTrue(customizationToolFeaturesPage.featuresTab().isDisplayed(),
				"Features Tab Not Displayed In Customization Page.");
		softAssert.assertTrue(customizationToolDesignPage.designTab().isDisplayed(),
				"Design Tab Not Displayed In Customization Page.");
		softAssert.assertTrue(customizationToolTextPage.textTab().isDisplayed(),
				"Text Tab Not Displayed In Customization Page.");

		String subBrandNameValueCustomization = customizationToolCommonPage.subBrandNameLabel().getText().trim();

		softAssert.assertEquals(subBrandNameValueCustomization, subBrandName,
				"The Actual And Expected Sub-brand Names Are Not Matching. " + "Actual Is: "
						+ subBrandNameValueCustomization + " Expected Is: " + subBrandName);

		softAssert.assertTrue(
				customizationToolFeaturesPage.featuresTab().getAttribute("aria-selected").equalsIgnoreCase("true"),
				"Customization: Features Tab Is Not Selected By Default");

		if (customizationToolFeaturesPage.featuresTab().getAttribute("aria-selected").equalsIgnoreCase("false")) {
			/*
			 * Assert.assertFalse(customizationToolFeaturesPage.applyToAllLink().isDisplayed
			 * (), "Customization Features Tab: Apply To All Link Not Displayed.");
			 */

			customizationToolFeaturesPage.applyToAllLink().click();
			Thread.sleep(1000);

			// Check If Apply To All Link Is Active
			Assert.assertTrue(
					customizationToolFeaturesPage.applyToAllLink().getAttribute("class").contains("is-active"),
					"Customization: Features Tab: Apply To All Link Is Not Expanded.");

			// ***********************************************************************************************************************
			// Enable/Disable Apply To All SSO
			boolean applyToAllEnableDisableSSO = Boolean.valueOf(
					CustomizationUtils.getRandomItemFromList(CustomizationUtils.customizeFeaturesATLEnableDisableSSO));

			if (applyToAllEnableDisableSSO) {
				if (!customizationToolFeaturesPage.applyToAllSSOToggleButton().getAttribute("class")
						.contains("is-checked"))
					customizationToolFeaturesPage.applyToAllSSOToggleButton().click();

				Thread.sleep(1000);
				// Check If Private Link Is Active
				if (!customizationToolFeaturesPage.applyToAllPrivateLink().getAttribute("class").contains("is-active"))
					customizationToolFeaturesPage.applyToAllPrivateLink().click();

				Thread.sleep(1000);
				// Enter Private Details
				customizationToolFeaturesPage
						.clearText(customizationToolFeaturesPage.applyToAllSSOPrivateDefaultDomainNameTextBox());
				Thread.sleep(500);
				customizationToolFeaturesPage.applyToAllSSOPrivateDefaultDomainNameTextBox()
						.sendKeys("PrivateDefaultDomainName");
				Thread.sleep(500);
				customizationToolFeaturesPage
						.clearText(customizationToolFeaturesPage.applyToAllSSOPrivateLoginUrlTextBox());
				Thread.sleep(500);
				customizationToolFeaturesPage.applyToAllSSOPrivateLoginUrlTextBox().sendKeys("PrivateLoginUrl");
				Thread.sleep(500);
				customizationToolFeaturesPage
						.clearText(customizationToolFeaturesPage.applyToAllSSOPrivateLogoutUrlTextBox());
				Thread.sleep(500);
				customizationToolFeaturesPage.applyToAllSSOPrivateLogoutUrlTextBox().sendKeys("PrivateLogoutUrl");
				Thread.sleep(500);
				customizationToolFeaturesPage
						.clearText(customizationToolFeaturesPage.applyToAllSSOPrivateStaleSessionUrlTextBox());
				Thread.sleep(500);
				customizationToolFeaturesPage.applyToAllSSOPrivateStaleSessionUrlTextBox()
						.sendKeys("PrivateStaleSessionUrl");
				Thread.sleep(500);
				customizationToolFeaturesPage
						.clearText(customizationToolFeaturesPage.applyToAllSSOPrivateKeepAliveReferralUrlTextBox());
				Thread.sleep(500);
				customizationToolFeaturesPage.applyToAllSSOPrivateKeepAliveReferralUrlTextBox()
						.sendKeys("PrivateSSOKeepAliveReferralUrl");
				Thread.sleep(500);
				customizationToolFeaturesPage
						.clearText(customizationToolFeaturesPage.applyToAllSSOPrivateSessionTimeOutUrlTextBox());
				Thread.sleep(500);
				customizationToolFeaturesPage.applyToAllSSOPrivateSessionTimeOutUrlTextBox()
						.sendKeys("PrivateSessionTimeOutUrl");
				Thread.sleep(500);
				customizationToolFeaturesPage
						.clearText(customizationToolFeaturesPage.applyToAllSSOPrivateTechnicalDifficultiesUrlTextBox());
				Thread.sleep(500);
				customizationToolFeaturesPage.applyToAllSSOPrivateTechnicalDifficultiesUrlTextBox()
						.sendKeys("PrivateTechDiffUrl");
				Thread.sleep(500);
				customizationToolFeaturesPage
						.clearText(customizationToolFeaturesPage.applyToAllSSOPrivateFallbackUrlTextBox());
				Thread.sleep(500);
				customizationToolFeaturesPage.applyToAllSSOPrivateFallbackUrlTextBox().sendKeys("PrivateFallbackUrl");

				Thread.sleep(1000);
				// Check if Public Link Is Active
				if (!customizationToolFeaturesPage.applyToAllPublicLink().getAttribute("class").contains("is-active"))
					customizationToolFeaturesPage.applyToAllPublicLink().click();

				Thread.sleep(1000);
				// Enter Public Details
				customizationToolFeaturesPage
						.clearText(customizationToolFeaturesPage.applyToAllSSOPublicDefaultDomainNameTextBox());
				Thread.sleep(500);
				customizationToolFeaturesPage.applyToAllSSOPublicDefaultDomainNameTextBox()
						.sendKeys("PublicDefaultDomainName");
				Thread.sleep(500);
				customizationToolFeaturesPage
						.clearText(customizationToolFeaturesPage.applyToAllSSOPublicLoginUrlTextBox());
				Thread.sleep(500);
				customizationToolFeaturesPage.applyToAllSSOPublicLoginUrlTextBox().sendKeys("PublicLoginUrl");
				Thread.sleep(500);
				customizationToolFeaturesPage
						.clearText(customizationToolFeaturesPage.applyToAllSSOPublicLogoutUrlTextBox());
				Thread.sleep(500);
				customizationToolFeaturesPage.applyToAllSSOPublicLogoutUrlTextBox().sendKeys("PublicLogoutUrl");
				Thread.sleep(500);
				customizationToolFeaturesPage
						.clearText(customizationToolFeaturesPage.applyToAllSSOPublicStaleSessionUrlTextBox());
				Thread.sleep(500);
				customizationToolFeaturesPage.applyToAllSSOPublicStaleSessionUrlTextBox()
						.sendKeys("PublicStaleSessionUrl");
				Thread.sleep(500);
				customizationToolFeaturesPage
						.clearText(customizationToolFeaturesPage.applyToAllSSOPublicKeepAliveReferralUrlTextBox());
				Thread.sleep(500);
				customizationToolFeaturesPage.applyToAllSSOPublicKeepAliveReferralUrlTextBox()
						.sendKeys("PublicKeepAliveReferralUrl");
				Thread.sleep(500);
				customizationToolFeaturesPage
						.clearText(customizationToolFeaturesPage.applyToAllSSOPublicSessionTimeOutUrlTextBox());
				Thread.sleep(500);
				customizationToolFeaturesPage.applyToAllSSOPublicSessionTimeOutUrlTextBox()
						.sendKeys("PublicSessionTimeOutUrl");
				Thread.sleep(500);
				customizationToolFeaturesPage
						.clearText(customizationToolFeaturesPage.applyToAllSSOPublicTechnicalDifficultiesUrlTextBox());
				Thread.sleep(500);
				customizationToolFeaturesPage.applyToAllSSOPublicTechnicalDifficultiesUrlTextBox()
						.sendKeys("PublicTechDiffUrl");
				Thread.sleep(500);
				customizationToolFeaturesPage
						.clearText(customizationToolFeaturesPage.applyToAllSSOPublicFallbackUrlTextBox());
				Thread.sleep(500);
				customizationToolFeaturesPage.applyToAllSSOPublicFallbackUrlTextBox().sendKeys("PublicFallbackUrl");

			} else {
				if (customizationToolFeaturesPage.applyToAllSSOToggleButton().getAttribute("class")
						.contains("is-checked"))
					customizationToolFeaturesPage.applyToAllSSOToggleButton().click();
				Thread.sleep(1000);
			}

			// ***********************************************************************************************************************
			// Enable Disable Apply To All Session Manager
			boolean applyToAllEnableDisableSessionManager = Boolean.valueOf(CustomizationUtils
					.getRandomItemFromList(CustomizationUtils.customizeFeaturesATLEnableDisableSessionManager));

			if (applyToAllEnableDisableSessionManager) {
				// Check If Session Manager Toggle Button is Active.
				if (!customizationToolFeaturesPage.applyToAllSessionManagerToggleButton().getAttribute("class")
						.contains("is-checked"))
					customizationToolFeaturesPage.applyToAllSessionManagerToggleButton().click();

				Thread.sleep(1000);
				// Enter Session Time Out Minutes
				customizationToolFeaturesPage.applyToAllSessionManagerSessionTimeOutClickableTextBox().click();
				Thread.sleep(500);
				customizationToolFeaturesPage
						.clearText(customizationToolFeaturesPage.applyToAllSessionManagerSessionTimeOutTextBox());
				Thread.sleep(500);
				customizationToolFeaturesPage.applyToAllSessionManagerSessionTimeOutTextBox().sendKeys("20");

				Thread.sleep(500);
				// Enter Keep Alive URL
				customizationToolFeaturesPage.applyToAllSessionManagerKeepAliveReferralUrlTextBox()
						.sendKeys("SessionManagerKeepAliveReferralUrl");
				Thread.sleep(500);
				// Enter Redirect URL
				customizationToolFeaturesPage.applyToAllSessionManagerRedirectUrlTextBox()
						.sendKeys("SessionManagerRedirectUrl");

				// Enable Disable Show Time Out Notification / Session Auto Renew -> Either Of
				// the Two Will Be Active At Any Give Point.
				// Both Cannot Be Active
				boolean applyToAllEnableDisableShowTimeOutNotification = Boolean
						.valueOf(CustomizationUtils.getRandomItemFromList(
								CustomizationUtils.customizeFeaturesATLEnableDisableShowTimeOutNotification));

				if (applyToAllEnableDisableShowTimeOutNotification) {
					if (!customizationToolFeaturesPage.applyToAllSessionManagerShowTimeNotificationToggleButton()
							.getAttribute("class").contains("is-checked"))
						customizationToolFeaturesPage.applyToAllSessionManagerShowTimeNotificationToggleButton()
								.click();
					Thread.sleep(500);
					Assert.assertTrue(
							!customizationToolFeaturesPage.applyToAllSessionManagerSessionAutoRenewToggleButton()
									.getAttribute("class").contains("is-checked"),
							"Features Tab: Apply To All Section: Session Auto Renew Toggle Button Is Enabled Even When Show Time Out Notification Toggle Button Is Enabled. "
									+ "It Should Be Disabled.");
				} else {
					if (customizationToolFeaturesPage.applyToAllSessionManagerShowTimeNotificationToggleButton()
							.getAttribute("class").contains("is-checked"))
						customizationToolFeaturesPage.applyToAllSessionManagerShowTimeNotificationToggleButton()
								.click();
					Thread.sleep(500);
					Assert.assertTrue(
							customizationToolFeaturesPage.applyToAllSessionManagerSessionAutoRenewToggleButton()
									.getAttribute("class").contains("is-checked"),
							"Features Tab: Apply To All Section: Session Auto Renew Toggle Button Is Disabled Even When Show Time Out Notification Toggle Button Is Disabled. "
									+ "It Should Be Enabled.");
				}
			} else {
				if (customizationToolFeaturesPage.applyToAllSessionManagerToggleButton().getAttribute("class")
						.contains("is-checked"))
					customizationToolFeaturesPage.applyToAllSessionManagerToggleButton().click();
				Thread.sleep(500);
			}

			// ***********************************************************************************************************************
			Assert.assertTrue(customizationToolFeaturesPage.fastLinkLink().isDisplayed(),
					"Customization Features Tab: FastLink Link Not Displayed.");

			customizationToolFeaturesPage.fastLinkLink().click();
			Thread.sleep(1000);

			// Check If FastLink Link Is Active
			Assert.assertTrue(customizationToolFeaturesPage.fastLinkLink().getAttribute("class").contains("is-active"),
					"Customization: Features Tab: FastLink Link Is Not Expanded.");

			// ***********************************************************************************************************************
			// Enable Disable Fast Link Splash Page
			boolean fastLinkEnableDisableSplashPage = Boolean.valueOf(CustomizationUtils
					.getRandomItemFromList(CustomizationUtils.customizeFeaturesFLEnableDisableSplashPage));

			if (fastLinkEnableDisableSplashPage) {
				if (!customizationToolFeaturesPage.fastLinkSplashPageToggleButton().getAttribute("class")
						.contains("is-checked"))
					customizationToolFeaturesPage.fastLinkSplashPageToggleButton().click();
				Thread.sleep(500);
			} else {
				if (customizationToolFeaturesPage.fastLinkSplashPageToggleButton().getAttribute("class")
						.contains("is-checked"))
					customizationToolFeaturesPage.fastLinkSplashPageToggleButton().click();
				Thread.sleep(500);
			}

			// Enable Disable Fast Link Terms And Conditions Verify Credentials
			boolean fastLinkEnableDisableTNCVerifyCredentials = Boolean.valueOf(CustomizationUtils
					.getRandomItemFromList(CustomizationUtils.customizeFeaturesFLTNCEnableDisableVerifyCredentials));

			if (fastLinkEnableDisableTNCVerifyCredentials) {
				if (!customizationToolFeaturesPage.fastLinkTermsAndConditionsVerifyCredentialsToggleButton()
						.getAttribute("class").contains("is-checked"))
					customizationToolFeaturesPage.fastLinkTermsAndConditionsVerifyCredentialsToggleButton().click();

				Thread.sleep(500);
				// Get the Verify Credential Type Value To Be Selected In Drop Down.
				String verifyCredentialsType = CustomizationUtils
						.getRandomItemFromList(CustomizationUtils.customizeFeaturesFLTNCVerifyCredentialsType);

				customizationToolFeaturesPage.fastLinkTermsAndConditionsVerifyCredentialsDropDown().click();
				Thread.sleep(1000);
				customizationToolFeaturesPage
						.fastLinkTermsAndConditionsVerifyCredentialsItemsInDropDown(verifyCredentialsType).click();
				Thread.sleep(1000);
				if (verifyCredentialsType.equalsIgnoreCase("Link")) {
					customizationToolFeaturesPage.fastLinkTermsAndConditionsVerifyCredentialsLinkClickAbleTextBox()
							.click();
					customizationToolFeaturesPage.clearText(
							customizationToolFeaturesPage.fastLinkTermsAndConditionsVerifyCredentialsLinkTextBox());
					customizationToolFeaturesPage.fastLinkTermsAndConditionsVerifyCredentialsLinkTextBox()
							.sendKeys("www.VerifyCredentialsLink.com");
				} else if (verifyCredentialsType.equalsIgnoreCase("Inline")) {
					customizationToolFeaturesPage.clearTextArea(
							customizationToolFeaturesPage.fastLinkTermsAndConditionsVerifyCredentialsInlineTextArea());
					customizationToolFeaturesPage.fastLinkTermsAndConditionsVerifyCredentialsInlineTextArea()
							.sendKeys("VerifyCredentialsInline");
				}

			} else {
				if (customizationToolFeaturesPage.fastLinkTermsAndConditionsVerifyCredentialsToggleButton()
						.getAttribute("class").contains("is-checked"))
					customizationToolFeaturesPage.fastLinkTermsAndConditionsVerifyCredentialsToggleButton().click();
			}

			// ***********************************************************************************************************************
			// Enable Disable Fast Link Stepper
			boolean fastLinkEnableDisableStepper = Boolean.valueOf(CustomizationUtils
					.getRandomItemFromList(CustomizationUtils.customizeFeaturesFLEnableDisableStepper));

			if (fastLinkEnableDisableStepper) {
				// Select Stepper On Or Off
				if (!customizationToolFeaturesPage.fastLinkStepperToggleButton().getAttribute("class")
						.contains("is-checked"))
					customizationToolFeaturesPage.fastLinkStepperToggleButton().click();
				Thread.sleep(500);
			} else {
				// Select Stepper On Or Off
				if (customizationToolFeaturesPage.fastLinkStepperToggleButton().getAttribute("class")
						.contains("is-checked"))
					customizationToolFeaturesPage.fastLinkStepperToggleButton().click();
				Thread.sleep(500);
			}

			// ***********************************************************************************************************************
			// Select Search Bar Position - Top Or Bottom.
			String searchBarPositionType = CustomizationUtils
					.getRandomItemFromList(CustomizationUtils.customizeFeaturesFLSearchBarPositionType);

			customizationToolFeaturesPage.fastLinkSearchBarPositionDropDown().click();
			Thread.sleep(1000);
			customizationToolFeaturesPage.fastLinkSearchBarPositionItemsInDropDown(searchBarPositionType).click();
			Thread.sleep(1000);


				// Enable Disable Site Name On Logo Button
				boolean fastLinkEnableDisableSiteNameOnLogoButton = Boolean
						.valueOf(CustomizationUtils.getRandomItemFromList(
								CustomizationUtils.customizeFeaturesFLEnableDisableDisplaySiteNameOnLogoButton));

				if (fastLinkEnableDisableSiteNameOnLogoButton) {
					if (!customizationToolFeaturesPage.fastLinkPopularSiteDisplaySiteNameOnLogoButtonsToggleButton()
							.getAttribute("class").contains("is-checked"))
						customizationToolFeaturesPage.fastLinkPopularSiteDisplaySiteNameOnLogoButtonsToggleButton()
								.click();
					Thread.sleep(500);
				} else {
					if (customizationToolFeaturesPage.fastLinkPopularSiteDisplaySiteNameOnLogoButtonsToggleButton()
							.getAttribute("class").contains("is-checked"))
						customizationToolFeaturesPage.fastLinkPopularSiteDisplaySiteNameOnLogoButtonsToggleButton()
								.click();
					Thread.sleep(500);
				}
			} else {
				if (customizationToolFeaturesPage.fastLinkPopularSiteToggleButton().getAttribute("class")
						.contains("is-checked"))
					customizationToolFeaturesPage.fastLinkPopularSiteToggleButton().click();
				Thread.sleep(500);
			}

			// ***********************************************************************************************************************
			// Enable Disable Fast Link Footer
			boolean fastLinkEnableDisableFooter = Boolean.valueOf(CustomizationUtils
					.getRandomItemFromList(CustomizationUtils.customizeFeaturesFLEnableDisableFooter));

			if (fastLinkEnableDisableFooter) {
				if (!customizationToolFeaturesPage.fastLinkFooterToggleButton().getAttribute("class")
						.contains("is-checked"))
					customizationToolFeaturesPage.fastLinkFooterToggleButton().click();
				Thread.sleep(500);
			} else {
				if (customizationToolFeaturesPage.fastLinkFooterToggleButton().getAttribute("class")
						.contains("is-checked"))
					customizationToolFeaturesPage.fastLinkFooterToggleButton().click();
				Thread.sleep(500);
			}

			// ***********************************************************************************************************************
			// Enable Disable Success Page For Add Account
			boolean fastLinkEnableDisableSuccessPageForAddAccount = Boolean
					.valueOf(CustomizationUtils.getRandomItemFromList(
							CustomizationUtils.customizeFeaturesFLEnableDisableSuccessPageForAddAccount));

			if (fastLinkEnableDisableSuccessPageForAddAccount) {
				if (!customizationToolFeaturesPage.fastLinkSuccessPageForAddAccountAggregationToggleButton()
						.getAttribute("class").contains("is-checked"))
					customizationToolFeaturesPage.fastLinkSuccessPageForAddAccountAggregationToggleButton().click();
				Thread.sleep(500);
			} else {
				if (customizationToolFeaturesPage.fastLinkSuccessPageForAddAccountAggregationToggleButton()
						.getAttribute("class").contains("is-checked"))
					customizationToolFeaturesPage.fastLinkSuccessPageForAddAccountAggregationToggleButton().click();
				Thread.sleep(500);

			}
			// ***********************************************************************************************************************
		

		customizationToolDesignPage.designTab().click();

		wait = new WebDriverWait(getWebdriver(), 10);
		wait.until(ExpectedConditions.visibilityOf(customizationToolDesignPage.designTabApplyToAllLink()));

		if (customizationToolDesignPage.designTab().getAttribute("aria-selected").equalsIgnoreCase("true")) {
			Assert.assertTrue(customizationToolDesignPage.designTabApplyToAllLink().isDisplayed(),
					"Customization: Design Tab: Apply To All Link Not Displayed");

			customizationToolDesignPage.designTabApplyToAllLink().click();

			Thread.sleep(500);

			// Design Apply To All Upload Font Link
			List<String> lstFonts = CustomizationUtils.getListOfFonts();
			String font = CustomizationUtils.getRandomItemFromList(lstFonts);
			Thread.sleep(500);
			customizationToolDesignPage.designApplyToAllFontFamilyUploadLink().click();
			Thread.sleep(500);
			customizationToolDesignPage.designApplyToAllFontUploadDropFileTextBox()
					.sendKeys(CustomizationUtils.getFontAbsolutePath(font));
			Thread.sleep(500);
			customizationToolDesignPage.designApplyToAllFontUploadUploadFontButton().click();

			// Select Design Apply To All Text Color
			Thread.sleep(500);
			String designApplyToAllTextColor = CustomizationUtils
					.getRandomItemFromList(CustomizationUtils.hexColorCode);
			customizationToolDesignPage.designApplyToAllTextColorButton().click();
			Thread.sleep(200);
			customizationToolDesignPage.clearText(customizationToolDesignPage.designTabColorPickerTextBox());
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerTextBox().sendKeys(designApplyToAllTextColor);
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerOkButton().click();

			Assert.assertTrue(customizationToolDesignPage.designApplyToAllPrimaryButtonLink().isDisplayed(),
					"Customization: Design Tab: Apply To All -> Primary Button Link Not Displayed");
			Thread.sleep(500);
			customizationToolDesignPage.designApplyToAllPrimaryButtonLink().click();

			// Select Design Apply To All Primary Button Default Text Color
			Thread.sleep(500);
			String designATAPrimaryButtonDefaultTextColor = CustomizationUtils
					.getRandomItemFromList(CustomizationUtils.hexColorCode);
			customizationToolDesignPage.designApplyToAllPrimaryButtonDefaultTextColorButton().click();
			Thread.sleep(200);
			customizationToolDesignPage.clearText(customizationToolDesignPage.designTabColorPickerTextBox());
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerTextBox().sendKeys(designATAPrimaryButtonDefaultTextColor);
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerOkButton().click();

			// Select Design Apply To All Primary Button Hover Text Color
			Thread.sleep(500);
			String designATAPrimaryButtonHoverTextColor = CustomizationUtils
					.getRandomItemFromList(CustomizationUtils.hexColorCode);
			customizationToolDesignPage.designApplyToAllPrimaryButtonHoverTextColorButton().click();
			Thread.sleep(200);
			customizationToolDesignPage.clearText(customizationToolDesignPage.designTabColorPickerTextBox());
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerTextBox().sendKeys(designATAPrimaryButtonHoverTextColor);
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerOkButton().click();

			// Select Design Apply To All Primary Button Hover Text Color
			Thread.sleep(500);
			String designATAPrimaryButtonDefaultBackgroundColor = CustomizationUtils
					.getRandomItemFromList(CustomizationUtils.hexColorCode);
			customizationToolDesignPage.designApplyToAllPrimaryButtonDefaultBackgroundColorButton().click();
			Thread.sleep(200);
			customizationToolDesignPage.clearText(customizationToolDesignPage.designTabColorPickerTextBox());
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerTextBox()
					.sendKeys(designATAPrimaryButtonDefaultBackgroundColor);
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerOkButton().click();

			// Select Design Apply To All Primary Button Hover Text Color
			Thread.sleep(500);
			String designATAPrimaryButtonHoverBackgroundColor = CustomizationUtils
					.getRandomItemFromList(CustomizationUtils.hexColorCode);
			customizationToolDesignPage.designApplyToAllPrimaryButtonHoverBackgroundColorButton().click();
			Thread.sleep(200);
			customizationToolDesignPage.clearText(customizationToolDesignPage.designTabColorPickerTextBox());
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerTextBox()
					.sendKeys(designATAPrimaryButtonHoverBackgroundColor);
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerOkButton().click();

			// Select Design Apply To All Primary Button Hover Text Color
			Thread.sleep(500);
			String designATAPrimaryButtonBorderColor = CustomizationUtils
					.getRandomItemFromList(CustomizationUtils.hexColorCode);
			customizationToolDesignPage.designApplyToAllPrimaryButtonBorderColorButton().click();
			Thread.sleep(200);
			customizationToolDesignPage.clearText(customizationToolDesignPage.designTabColorPickerTextBox());
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerTextBox().sendKeys(designATAPrimaryButtonBorderColor);
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerOkButton().click();

			Assert.assertTrue(customizationToolDesignPage.designApplyToAllSecondaryButtonLink().isDisplayed(),
					"Customization: Design Tab: Apply To All -> Secondary Button Link Not Displayed");
			Thread.sleep(500);
			customizationToolDesignPage.designApplyToAllSecondaryButtonLink().click();

			// Select Design Apply To All Secondary Button Default Text Color
			Thread.sleep(500);
			String designATASecondaryButtonDefaultTextColor = CustomizationUtils
					.getRandomItemFromList(CustomizationUtils.hexColorCode);
			customizationToolDesignPage.designApplyToAllSecondaryButtonDefaultTextColorButton().click();
			Thread.sleep(200);
			customizationToolDesignPage.clearText(customizationToolDesignPage.designTabColorPickerTextBox());
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerTextBox()
					.sendKeys(designATASecondaryButtonDefaultTextColor);
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerOkButton().click();

			// Select Design Apply To All Secondary Button Hover Text Color
			Thread.sleep(500);
			String designATASecondaryButtonHoverTextColor = CustomizationUtils
					.getRandomItemFromList(CustomizationUtils.hexColorCode);
			customizationToolDesignPage.designApplyToAllSecondaryButtonHoverTextColorButton().click();
			Thread.sleep(200);
			customizationToolDesignPage.clearText(customizationToolDesignPage.designTabColorPickerTextBox());
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerTextBox().sendKeys(designATASecondaryButtonHoverTextColor);
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerOkButton().click();

			// Select Design Apply To All Secondary Button Hover Text Color
			Thread.sleep(500);
			String designATASecondaryButtonDefaultBackgroundColor = CustomizationUtils
					.getRandomItemFromList(CustomizationUtils.hexColorCode);
			customizationToolDesignPage.designApplyToAllSecondaryButtonDefaultBackgroundColorButton().click();
			Thread.sleep(200);
			customizationToolDesignPage.clearText(customizationToolDesignPage.designTabColorPickerTextBox());
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerTextBox()
					.sendKeys(designATASecondaryButtonDefaultBackgroundColor);
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerOkButton().click();

			// Select Design Apply To All Secondary Button Hover Text Color
			Thread.sleep(500);
			String designATASecondaryButtonHoverBackgroundColor = CustomizationUtils
					.getRandomItemFromList(CustomizationUtils.hexColorCode);
			customizationToolDesignPage.designApplyToAllSecondaryButtonHoverBackgroundColorButton().click();
			Thread.sleep(200);
			customizationToolDesignPage.clearText(customizationToolDesignPage.designTabColorPickerTextBox());
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerTextBox()
					.sendKeys(designATASecondaryButtonHoverBackgroundColor);
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerOkButton().click();

			// Select Design Apply To All Secondary Button Hover Text Color
			Thread.sleep(500);
			String designATASecondaryButtonBorderColor = CustomizationUtils
					.getRandomItemFromList(CustomizationUtils.hexColorCode);
			customizationToolDesignPage.designApplyToAllSecondaryButtonBorderColorButton().click();
			Thread.sleep(200);
			customizationToolDesignPage.clearText(customizationToolDesignPage.designTabColorPickerTextBox());
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerTextBox().sendKeys(designATASecondaryButtonBorderColor);
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerOkButton().click();

			Assert.assertTrue(customizationToolDesignPage.designApplyToAllDeleteButtonLink().isDisplayed(),
					"Customization: Design Tab: Apply To All -> Delete Button Link Not Displayed");
			Thread.sleep(500);
			customizationToolDesignPage.designApplyToAllDeleteButtonLink().click();

			// Select Design Apply To All Delete Button Default Text Color
			Thread.sleep(500);
			String designATADeleteButtonDefaultTextColor = CustomizationUtils
					.getRandomItemFromList(CustomizationUtils.hexColorCode);
			customizationToolDesignPage.designApplyToAllDeleteButtonDefaultTextColorButton().click();
			Thread.sleep(200);
			customizationToolDesignPage.clearText(customizationToolDesignPage.designTabColorPickerTextBox());
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerTextBox().sendKeys(designATADeleteButtonDefaultTextColor);
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerOkButton().click();

			// Select Design Apply To All Delete Button Hover Text Color
			Thread.sleep(500);
			String designATADeleteButtonHoverTextColor = CustomizationUtils
					.getRandomItemFromList(CustomizationUtils.hexColorCode);
			customizationToolDesignPage.designApplyToAllDeleteButtonHoverTextColorButton().click();
			Thread.sleep(200);
			customizationToolDesignPage.clearText(customizationToolDesignPage.designTabColorPickerTextBox());
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerTextBox().sendKeys(designATADeleteButtonHoverTextColor);
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerOkButton().click();

			// Select Design Apply To All Delete Button Hover Text Color
			Thread.sleep(500);
			String designATADeleteButtonDefaultBackgroundColor = CustomizationUtils
					.getRandomItemFromList(CustomizationUtils.hexColorCode);
			customizationToolDesignPage.designApplyToAllDeleteButtonDefaultBackgroundColorButton().click();
			Thread.sleep(200);
			customizationToolDesignPage.clearText(customizationToolDesignPage.designTabColorPickerTextBox());
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerTextBox()
					.sendKeys(designATADeleteButtonDefaultBackgroundColor);
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerOkButton().click();

			// Select Design Apply To All Delete Button Hover Text Color
			Thread.sleep(500);
			String designATADeleteButtonHoverBackgroundColor = CustomizationUtils
					.getRandomItemFromList(CustomizationUtils.hexColorCode);
			customizationToolDesignPage.designApplyToAllDeleteButtonHoverBackgroundColorButton().click();
			Thread.sleep(200);
			customizationToolDesignPage.clearText(customizationToolDesignPage.designTabColorPickerTextBox());
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerTextBox()
					.sendKeys(designATADeleteButtonHoverBackgroundColor);
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerOkButton().click();

			// Select Design Apply To All Delete Button Hover Text Color
			Thread.sleep(500);
			String designATADeleteButtonBorderColor = CustomizationUtils
					.getRandomItemFromList(CustomizationUtils.hexColorCode);
			customizationToolDesignPage.designApplyToAllDeleteButtonBorderColorButton().click();
			Thread.sleep(200);
			customizationToolDesignPage.clearText(customizationToolDesignPage.designTabColorPickerTextBox());
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerTextBox().sendKeys(designATADeleteButtonBorderColor);
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerOkButton().click();

			Thread.sleep(1000);
			Assert.assertTrue(customizationToolDesignPage.designTabFastLinkLink().isDisplayed(),
					"Customization: Design Tab: Fastlink Link Not Displayed");

			customizationToolDesignPage.designTabFastLinkLink().click();

			// Select Design Fastlink Background Color
			Thread.sleep(500);
			String designFLBackgroundColor = CustomizationUtils.getRandomItemFromList(CustomizationUtils.hexColorCode);
			customizationToolDesignPage.designFastLinkBackgroundColorButton().click();
			Thread.sleep(200);
			customizationToolDesignPage.clearText(customizationToolDesignPage.designTabColorPickerTextBox());
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerTextBox().sendKeys(designFLBackgroundColor);
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerOkButton().click();

			Assert.assertTrue(customizationToolDesignPage.designFastLinkHeadersLink().isDisplayed(),
					"Customization: Design Tab: Fastlink -> Headers Link Not Displayed");
			// Click On Design Page Fastlink -> Headers Link
			Thread.sleep(500);
			customizationToolDesignPage.designFastLinkHeadersLink().click();
			Assert.assertTrue(
					customizationToolDesignPage.designFastLinkHeadersLink().getAttribute("class").contains("is-active"),
					"Customization: Design Tab: Fastlink -> Headers Link Is Not Expanded.");

			// Select Design Fastlink -> Headers Text Color
			Thread.sleep(500);
			String designFLHeadersTextColor = CustomizationUtils.getRandomItemFromList(CustomizationUtils.hexColorCode);
			customizationToolDesignPage.designFastLinkHeadersTextColorButton().click();
			Thread.sleep(200);
			customizationToolDesignPage.clearText(customizationToolDesignPage.designTabColorPickerTextBox());
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerTextBox().sendKeys(designFLHeadersTextColor);
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerOkButton().click();

			// Select Design Fastlink -> Headers Background Color
			Thread.sleep(500);
			String designFLHeadersBackgroundColor = CustomizationUtils
					.getRandomItemFromList(CustomizationUtils.hexColorCode);
			customizationToolDesignPage.designFastLinkBackgroundColorButton().click();
			Thread.sleep(200);
			customizationToolDesignPage.clearText(customizationToolDesignPage.designTabColorPickerTextBox());
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerTextBox().sendKeys(designFLHeadersBackgroundColor);
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerOkButton().click();

			Assert.assertTrue(customizationToolDesignPage.designFastLinkTipsLink().isDisplayed(),
					"Customization: Design Tab: Fastlink -> Tips Link Not Displayed");
			// Click Design Page Fastlink -> Tips Link
			Thread.sleep(1000);
			customizationToolDesignPage.designFastLinkTipsLink().click();
			Assert.assertTrue(
					customizationToolDesignPage.designFastLinkTipsLink().getAttribute("class").contains("is-active"),
					"Customization: Design Tab: Fastlink -> Tips Link Is Not Expanded.");

			// Select Design Fastlink -> Tips Text Color
			Thread.sleep(500);
			String designFLTipsTextColor = CustomizationUtils.getRandomItemFromList(CustomizationUtils.hexColorCode);
			customizationToolDesignPage.designFastLinkTipsTextColorButton().click();
			Thread.sleep(200);
			customizationToolDesignPage.clearText(customizationToolDesignPage.designTabColorPickerTextBox());
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerTextBox().sendKeys(designFLTipsTextColor);
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerOkButton().click();

			// Select Design Fastlink -> Tips Background Color
			Thread.sleep(500);
			String designFLTipsBackgroundColor = CustomizationUtils
					.getRandomItemFromList(CustomizationUtils.hexColorCode);
			customizationToolDesignPage.designFastLinkTipsBackgroundColorButton().click();
			Thread.sleep(200);
			customizationToolDesignPage.clearText(customizationToolDesignPage.designTabColorPickerTextBox());
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerTextBox().sendKeys(designFLTipsBackgroundColor);
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerOkButton().click();

			Assert.assertTrue(customizationToolDesignPage.designFastLinkSteppersLink().isDisplayed(),
					"Customization: Design Tab: Fastlink -> Steppers Link Not Displayed");
			// Click Design Page Fastlink -> Steppers Link
			Thread.sleep(1000);
			customizationToolDesignPage.designFastLinkSteppersLink().click();
			Assert.assertTrue(customizationToolDesignPage.designFastLinkSteppersLink().getAttribute("class")
					.contains("is-active"), "Customization: Design Tab: Fastlink -> Steppers Link Is Not Expanded.");

			// Select Design Tab Fastlink -> Steppers Active Text Color
			Thread.sleep(500);
			String designFLSteppersActiveTextColor = CustomizationUtils
					.getRandomItemFromList(CustomizationUtils.hexColorCode);
			customizationToolDesignPage.designFastLinkSteppersActiveTextColorButton().click();
			Thread.sleep(200);
			customizationToolDesignPage.clearText(customizationToolDesignPage.designTabColorPickerTextBox());
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerTextBox().sendKeys(designFLSteppersActiveTextColor);
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerOkButton().click();

			// Select Design Tab Fastlink -> Steppers Active Border Color
			Thread.sleep(500);
			String designFLSteppersActiveBorderColor = CustomizationUtils
					.getRandomItemFromList(CustomizationUtils.hexColorCode);
			customizationToolDesignPage.designFastLinkSteppersActiveBorderColorButton().click();
			Thread.sleep(200);
			customizationToolDesignPage.clearText(customizationToolDesignPage.designTabColorPickerTextBox());
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerTextBox().sendKeys(designFLSteppersActiveBorderColor);
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerOkButton().click();

			// Select Design Tab Fastlink -> Steppers Inactive Text Color
			Thread.sleep(500);
			String designFLSteppersInactiveTextColor = CustomizationUtils
					.getRandomItemFromList(CustomizationUtils.hexColorCode);
			customizationToolDesignPage.designFastLinkSteppersInactiveTextColorButton().click();
			Thread.sleep(200);
			customizationToolDesignPage.clearText(customizationToolDesignPage.designTabColorPickerTextBox());
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerTextBox().sendKeys(designFLSteppersInactiveTextColor);
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerOkButton().click();

			// Select Design Tab -> Fastlink -> Steppers Inactive Border Color
			Thread.sleep(500);
			String designFLSteppersInactiveBorderColor = CustomizationUtils
					.getRandomItemFromList(CustomizationUtils.hexColorCode);
			customizationToolDesignPage.designFastLinkSteppersInactiveBorderColorButton().click();
			Thread.sleep(200);
			customizationToolDesignPage.clearText(customizationToolDesignPage.designTabColorPickerTextBox());
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerTextBox().sendKeys(designFLSteppersInactiveBorderColor);
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerOkButton().click();

			Assert.assertTrue(customizationToolDesignPage.designFastLinkSelectInstitutionsButtonsLink().isDisplayed(),
					"Customization: Design Tab: Fastlink -> Select Institutions Link Not Displayed");
			// Click Design Page -> Buttons -> Select Institutions Buttons Link
			Thread.sleep(1000);
			customizationToolDesignPage.designFastLinkSelectInstitutionsButtonsLink().click();

			Assert.assertTrue(
					customizationToolDesignPage.designFastLinkSelectInstitutionsButtonsLink().getAttribute("class")
							.contains("is-active"),
					"Customization: Design Tab: Fastlink -> Select Institutions Buttons Link Is Not Expanded.");

			// Select Design Tab -> Fastlink -> Select Institutions Buttons -> Background
			// Color
			Thread.sleep(500);
			String designFLSelectInstitutionsButtonsBackgroundColor = CustomizationUtils
					.getRandomItemFromList(CustomizationUtils.hexColorCode);
			customizationToolDesignPage.designFastLinkSelectInstitutionsButtonsBackgroundColorButton().click();
			Thread.sleep(200);
			customizationToolDesignPage.clearText(customizationToolDesignPage.designTabColorPickerTextBox());
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerTextBox()
					.sendKeys(designFLSelectInstitutionsButtonsBackgroundColor);
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerOkButton().click();

			// Select Design Tab -> Fastlink -> Select Institutions Buttons -> Label Text
			// Color
			Thread.sleep(500);
			String designFLSelectInstitutionsButtonsLabelTextColor = CustomizationUtils
					.getRandomItemFromList(CustomizationUtils.hexColorCode);
			customizationToolDesignPage.designFastLinkSelectInstitutionsButtonsLabelTextColorButton().click();
			Thread.sleep(200);
			customizationToolDesignPage.clearText(customizationToolDesignPage.designTabColorPickerTextBox());
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerTextBox()
					.sendKeys(designFLSelectInstitutionsButtonsLabelTextColor);
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerOkButton().click();

			Assert.assertTrue(customizationToolDesignPage.designTabWellnessLink().isDisplayed(),
					"Customization: Design Tab: Wellness Link Not Displayed");
			// Click Design Page Buttons Link
			Thread.sleep(1000);
			customizationToolDesignPage.designTabWellnessLink().click();

			Assert.assertTrue(
					customizationToolDesignPage.designTabWellnessLink().getAttribute("class").contains("is-active"),
					"Customization: Design Tab: Wellness Link Is Not Expanded.");

			// Enter Button Radius
			Thread.sleep(500);
			customizationToolDesignPage.designWellnessButtonRadiusClickableTextBox().click();
			Thread.sleep(200);
			customizationToolDesignPage.clearText(customizationToolDesignPage.designWellnessButtonRadiusTextBox());
			Thread.sleep(200);
			customizationToolDesignPage.designWellnessButtonRadiusTextBox().sendKeys("10px");

			Assert.assertTrue(customizationToolDesignPage.designWellnessMainMenuLink().isDisplayed(),
					"Customization: Design Tab: Wellness -> Main Menu Link Not Displayed");

			// Click Design Tab -> Wellness -> Main Menu Link
			Thread.sleep(1000);
			customizationToolDesignPage.designWellnessMainMenuLink().click();

			Assert.assertTrue(customizationToolDesignPage.designWellnessMainMenuLink().getAttribute("class")
					.contains("is-active"), "Customization: Features Tab: Menus -> Main Menu Link Is Not Expanded.");

			// Select Design Tab -> Wellness -> Main Menu -> Default Text Color
			Thread.sleep(500);
			String designWellnessMainMenuDefaultTextColor = CustomizationUtils
					.getRandomItemFromList(CustomizationUtils.hexColorCode);
			customizationToolDesignPage.designWellnessMainMenuDefaultTextColorButton().click();
			Thread.sleep(200);
			customizationToolDesignPage.clearText(customizationToolDesignPage.designTabColorPickerTextBox());
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerTextBox().sendKeys(designWellnessMainMenuDefaultTextColor);
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerOkButton().click();

			// Select Design Tab -> Wellness -> Main Menu -> Hover Text Color
			Thread.sleep(500);
			String designWellnessMainMenuHoverTextColor = CustomizationUtils
					.getRandomItemFromList(CustomizationUtils.hexColorCode);
			customizationToolDesignPage.designWellnessMainMenuHoverTextColorButton().click();
			Thread.sleep(200);
			customizationToolDesignPage.clearText(customizationToolDesignPage.designTabColorPickerTextBox());
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerTextBox().sendKeys(designWellnessMainMenuHoverTextColor);
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerOkButton().click();

			// Select Design Tab -> Wellness -> Main Menu -> Active Text Color
			Thread.sleep(500);
			String designWellnessMainMenuActiveTextColor = CustomizationUtils
					.getRandomItemFromList(CustomizationUtils.hexColorCode);
			customizationToolDesignPage.designWellnessMainMenuActiveTextColorButton().click();
			Thread.sleep(200);
			customizationToolDesignPage.clearText(customizationToolDesignPage.designTabColorPickerTextBox());
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerTextBox().sendKeys(designWellnessMainMenuActiveTextColor);
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerOkButton().click();

			// Select Design Tab -> Wellness -> Main Menu -> Background Color
			Thread.sleep(500);
			String designWellnessMainMenuBackgroundColor = CustomizationUtils
					.getRandomItemFromList(CustomizationUtils.hexColorCode);
			customizationToolDesignPage.designWellnessMainMenuBackgroundColorButton().click();
			Thread.sleep(200);
			customizationToolDesignPage.clearText(customizationToolDesignPage.designTabColorPickerTextBox());
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerTextBox().sendKeys(designWellnessMainMenuBackgroundColor);
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerOkButton().click();

			Assert.assertTrue(customizationToolDesignPage.designWellnessDropdownSubmenuLink().isDisplayed(),
					"Customization: Design Tab: Wellness -> Dropdown Submenu Link Not Displayed");
			// Design Tab -> Wellness -> Dropdown Submenu Link
			Thread.sleep(1000);
			customizationToolDesignPage.designWellnessDropdownSubmenuLink().click();

			Assert.assertTrue(
					customizationToolDesignPage.designWellnessDropdownSubmenuLink().getAttribute("class")
							.contains("is-active"),
					"Customization: Design Tab: Menus -> Dropdown Sub Menu Link Is Not Expanded.");

			// Select Design Tab -> Wellness -> Dropdown Submenu -> Default Text Color
			Thread.sleep(500);
			String designWellnessDropdownSubMenuDefaultTextColor = CustomizationUtils
					.getRandomItemFromList(CustomizationUtils.hexColorCode);
			customizationToolDesignPage.designWellnessDropdownSubmenuDefaultTextColorButton().click();
			Thread.sleep(200);
			customizationToolDesignPage.clearText(customizationToolDesignPage.designTabColorPickerTextBox());
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerTextBox()
					.sendKeys(designWellnessDropdownSubMenuDefaultTextColor);
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerOkButton().click();

			// Select Design Tab -> Wellness -> Dropdown Submenu -> Hover Text Color
			Thread.sleep(500);
			String designWellnessDropdownSubMenuHoverTextColor = CustomizationUtils
					.getRandomItemFromList(CustomizationUtils.hexColorCode);
			customizationToolDesignPage.designWellnessDropdownSubmenuHoverTextColorButton().click();
			Thread.sleep(200);
			customizationToolDesignPage.clearText(customizationToolDesignPage.designTabColorPickerTextBox());
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerTextBox()
					.sendKeys(designWellnessDropdownSubMenuHoverTextColor);
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerOkButton().click();

			// Select Design Tab -> Wellness -> Dropdown Submenu -> Active Text Color
			Thread.sleep(500);
			String designWellnessDropdownSubMenuActiveTextColor = CustomizationUtils
					.getRandomItemFromList(CustomizationUtils.hexColorCode);
			customizationToolDesignPage.designWellnessDropdownSubmenuActiveTextColorButton().click();
			Thread.sleep(200);
			customizationToolDesignPage.clearText(customizationToolDesignPage.designTabColorPickerTextBox());
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerTextBox()
					.sendKeys(designWellnessDropdownSubMenuActiveTextColor);
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerOkButton().click();

			Assert.assertTrue(customizationToolDesignPage.designWellnessSidebarMenuLink().isDisplayed(),
					"Customization: Design Tab: Wellness -> Sidebar Menu Link Not Displayed");
			// Click Design Tab -> Wellness -> -> Sidebar Menu Link
			Thread.sleep(1000);
			customizationToolDesignPage.designWellnessSidebarMenuLink().click();

			Assert.assertTrue(customizationToolDesignPage.designWellnessSidebarMenuLink().getAttribute("class")
					.contains("is-active"), "Customization: Design Tab: Menus -> Side Bar Menu Link Is Not Expanded.");

			// Select Design Tab -> Wellness -> Sidebar Menu -> Default Text Color
			Thread.sleep(500);
			String designWellnessSidebarMenuDefaultTextColor = CustomizationUtils
					.getRandomItemFromList(CustomizationUtils.hexColorCode);
			customizationToolDesignPage.designWellnessSidebarMenuDefaultTextColorButton().click();
			Thread.sleep(200);
			customizationToolDesignPage.clearText(customizationToolDesignPage.designTabColorPickerTextBox());
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerTextBox()
					.sendKeys(designWellnessSidebarMenuDefaultTextColor);
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerOkButton().click();

			// Select Design Tab -> Wellness -> Sidebar Menu -> Hover Text Color
			Thread.sleep(500);
			String designWellnessSidebarMenuHoverTextColor = CustomizationUtils
					.getRandomItemFromList(CustomizationUtils.hexColorCode);
			customizationToolDesignPage.designWellnessSidebarMenuHoverTextColorButton().click();
			Thread.sleep(200);
			customizationToolDesignPage.clearText(customizationToolDesignPage.designTabColorPickerTextBox());
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerTextBox().sendKeys(designWellnessSidebarMenuHoverTextColor);
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerOkButton().click();

			// Select Design Tab -> Wellness -> Sidebar Menu -> Active Text Color
			Thread.sleep(500);
			String designWellnessSidebarMenuActiveTextColor = CustomizationUtils
					.getRandomItemFromList(CustomizationUtils.hexColorCode);
			customizationToolDesignPage.designWellnessSidebarMenuActiveTextColorButton().click();
			Thread.sleep(200);
			customizationToolDesignPage.clearText(customizationToolDesignPage.designTabColorPickerTextBox());
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerTextBox()
					.sendKeys(designWellnessSidebarMenuActiveTextColor);
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerOkButton().click();

			// Select Design Tab -> Wellness -> Sidebar Menu -> Background Color
			Thread.sleep(500);
			String designWellnessSidebarMenuBackgroundColor = CustomizationUtils
					.getRandomItemFromList(CustomizationUtils.hexColorCode);
			customizationToolDesignPage.designWellnessSidebarMenuBackgroundColorButton().click();
			Thread.sleep(200);
			customizationToolDesignPage.clearText(customizationToolDesignPage.designTabColorPickerTextBox());
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerTextBox()
					.sendKeys(designWellnessSidebarMenuBackgroundColor);
			Thread.sleep(200);
			customizationToolDesignPage.designTabColorPickerOkButton().click();
		}

		// Click Text Tab In Customization Page.
		Thread.sleep(100);
		customizationToolTextPage.textTab().click();

		wait = new WebDriverWait(getWebdriver(), 10);
		wait.until(ExpectedConditions.visibilityOf(customizationToolTextPage.fastLinkLink()));

		if (customizationToolTextPage.textTab().getAttribute("aria-selected").equalsIgnoreCase("true")) {
			Assert.assertTrue(customizationToolTextPage.fastLinkLink().isDisplayed(),
					"Customization: Design Tab: FastLink Link Not Displayed");

			// Click Text Page -> Fastlink Link
			customizationToolTextPage.fastLinkLink().click();
			Thread.sleep(1000);

			Assert.assertTrue(customizationToolTextPage.fastLinkLink().getAttribute("class").contains("is-active"),
					"Customization: Text Tab: Fastlink Link Is Not Expanded.");

			Thread.sleep(200);
			// Enter value in Select A Site Text Box
			customizationToolTextPage.clearText(customizationToolTextPage.fastLinkSelectASiteTextBox());
			customizationToolTextPage.fastLinkSelectASiteTextBox().sendKeys("Select A Site");

			Thread.sleep(200);
			// Enter value in Verify Credentials Text Box
			customizationToolTextPage.clearText(customizationToolTextPage.fastLinkVerifyCredentialsTextBox());
			customizationToolTextPage.fastLinkVerifyCredentialsTextBox().sendKeys("Verify Credentials");

			Thread.sleep(200);
			// Enter value in View Accounts Text Box
			customizationToolTextPage.clearText(customizationToolTextPage.fastLinkViewAccountsTextBox());
			customizationToolTextPage.fastLinkViewAccountsTextBox().sendKeys("View Accounts");

			Thread.sleep(200);
			// Enter value in Tips Account Types Text Area
			customizationToolTextPage.clearTextArea(customizationToolTextPage.fastLinkTipAccountTypesTextArea());
			customizationToolTextPage.fastLinkTipAccountTypesTextArea().sendKeys("Tips Account Types");

			Thread.sleep(200);
			// Enter value in Linking Accounts Security Text Area
			customizationToolTextPage
					.clearTextArea(customizationToolTextPage.fastLinkLinkingAccountsSecurityTextArea());
			customizationToolTextPage.fastLinkLinkingAccountsSecurityTextArea().sendKeys("Linking Account Security");

			Thread.sleep(200);
			// Click Text Page -> Fastlink -> Buttons Link
			Thread.sleep(1000);
			customizationToolTextPage.fastLinkButtonsLink().click();

			Assert.assertTrue(
					customizationToolTextPage.fastLinkButtonsLink().getAttribute("class").contains("is-active"),
					"Customization: Text Tab: Fast Link Buttons Link Is Not Expanded.");

			// Enter value for Get Started Button Text Box
			customizationToolTextPage.clearText(customizationToolTextPage.fastLinkButtonsGetStartedTextBox());
			customizationToolTextPage.fastLinkButtonsGetStartedTextBox().sendKeys("Get Started");
			Thread.sleep(200);

			// Enter value for Submit Button Text Box
			customizationToolTextPage.clearText(customizationToolTextPage.fastLinkButtonsSubmitTextBox());
			customizationToolTextPage.fastLinkButtonsSubmitTextBox().sendKeys("Submit");
			Thread.sleep(200);

			// Enter value for Back Button Text Box
			customizationToolTextPage.clearText(customizationToolTextPage.fastLinkButtonsBackTextBox());
			customizationToolTextPage.fastLinkButtonsBackTextBox().sendKeys("Back");
			Thread.sleep(200);

			// Enter value for Close Button Text Box
			customizationToolTextPage.clearText(customizationToolTextPage.fastLinkButtonsCloseTextBox());
			customizationToolTextPage.fastLinkButtonsCloseTextBox().sendKeys("Close");
			Thread.sleep(200);

			// Enter value for Link Another Site Button Text Box
			customizationToolTextPage.clearText(customizationToolTextPage.fastLinkButtonsLinkAnotherSiteTextBox());
			customizationToolTextPage.fastLinkButtonsLinkAnotherSiteTextBox().sendKeys("Link Another Site");
			Thread.sleep(200);

			// Enter value for Delete Button Text Box
			customizationToolTextPage.clearText(customizationToolTextPage.fastLinkButtonsDeleteTextBox());
			customizationToolTextPage.fastLinkButtonsDeleteTextBox().sendKeys("Delete");
			Thread.sleep(200);
		}

		softAssert.assertTrue(customizationToolCommonPage.backToSubBrandListLink().isDisplayed(),
				"Customization: Back To Sub-brand List Link Not Displayed.");

		/*
		 * softAssert.assertTrue(customizationToolCommonPage.previewButton().isDisplayed
		 * (), "Customization: Preview Button Not Displayed.");
		 */

		softAssert.assertTrue(customizationToolCommonPage.saveButton().isDisplayed(),
				"Customization: Save Button Not Displayed.");

		Thread.sleep(2000);

		if (customizationToolCommonPage.saveButton().isDisplayed())
			customizationToolCommonPage.saveButton().click();

		Thread.sleep(2000);
		Assert.assertEquals(customizationToolCommonPage.alertToolTipOnSave().getText().trim(),
				"Changes have been saved successfully.",
				"The Actual And The Expected Message Upon Saving Is Not Matching. Actual Is "
						+ customizationToolCommonPage.alertToolTipOnSave().getText().trim()
						+ " Expected Is Changes have been saved successfully.");

		if (customizationToolCommonPage.backToSubBrandListLink().isDisplayed()) {
			customizationToolCommonPage.backToSubBrandListLink().click();			
			Assert.assertTrue(viewListOfSubBrandsPage.createSubBrandButton().isDisplayed(),
					"View List To Sub-brand Page: Clicking on Back To Sub-brand List In "
							+ "Customization Screen Did Not Return to View List Of Sub-brands Page. Please Check.");
		}

		softAssert.assertAll();
	}

	/*
	 * Test - Sample Code for Sanity in Sub-brand - Local Code
	 * 
	 * @Test public void sample() throws InterruptedException {
	 * 
	 * String url = envDetails.getSamlNodeURL();
	 * 
	 * getWebdriver().get(url); Thread.sleep(2000);
	 * 
	 * String subjectNameUserID = "QAAutoSub1572920546641_Rajeev19Nov201911"; String
	 * issuerID = "testSAMLIssuerIdPublic"; String nodeUrl =
	 * "https://10.79.8.1/authenticate/DNS1572920546641/?channelAppName=pfml1stage";
	 * samlPage.enterSAMLDetails(issuerID, subjectNameUserID, nodeUrl,
	 * envDetails.getSamlFinAppID(), envDetails.getSamlExtraParams(),
	 * envDetails.getSamlRedirectRequest(), envDetails.getSamlAttributes(),
	 * envDetails.getSamlVersion(), envDetails.getSamlAssertionEncryption(),
	 * envDetails.getSamlMultipleKeys(), envDetails.getSamlAttributeEncryption(),
	 * envDetails.getSamlAttributeEncryptionMechanism(),
	 * envDetails.getSamlAttributeEncoding(), envDetails.getSamlSignResponse(),
	 * envDetails.getSamlSignAssertion(), envDetails.getSamlSigningAliasKey(),
	 * envDetails.getSamlSSOAttributeKey(), envDetails.getSamlLinkIntegrityToken());
	 * 
	 * Thread.sleep(2000);
	 * 
	 * Assert.assertEquals(samlPage.headerLabel().getText(),"SAML POST FORM",
	 * "The Actual And The Expected Header Does Not Match. Actual Is: "
	 * +samlPage.headerLabel().getText()+ " Expected Is: SAML POST FORM");
	 * 
	 * samlPage.submitButton().click(); Thread.sleep(2000);
	 * 
	 * WebDriverWait wait=new WebDriverWait(getWebdriver(), 30);
	 * wait.until(ExpectedConditions.visibilityOf(dashboardPage.linkAccountButton())
	 * );
	 * 
	 * Assert.assertTrue(dashboardPage.linkAccountButton().isDisplayed(),
	 * "Dashboard Page: Link Account Button Not Found. Please Check !");
	 * 
	 * dashboardPage.addAccount(dataProperty.getDagSiteName(),
	 * dataProperty.getDagUsername(), dataProperty.getDagPassword());
	 * Thread.sleep(7000);
	 * 
	 * wait=new WebDriverWait(getWebdriver(), 60);
	 * wait.until(ExpectedConditions.textToBePresentInElement(dashboardPage.
	 * toastMessageLabel(), "Congratulations! Your accounts have been linked."));
	 * 
	 * Assert.assertTrue(dashboardPage.toastMessageLabel().isDisplayed(),
	 * "Toast Message Not Found. Account Addition May Have Failed. Please Check !");
	 * 
	 * Assert.assertEquals(dashboardPage.toastMessageLabel().getText(),
	 * "Congratulations! Your accounts have been linked.",
	 * "The Actual And Expected Toast Message Does Not Match. Actual Is: "
	 * +dashboardPage.toastMessageLabel().getText() + " Expected Is: "
	 * +"Congratulations! Your accounts have been linked.");
	 * 
	 * dashboardPage.closeButton().click(); Thread.sleep(3000);
	 * 
	 * getWebdriver().get(envDetails.getBaseUrl()); }
	 */ }