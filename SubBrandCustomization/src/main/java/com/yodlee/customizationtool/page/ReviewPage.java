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

import java.util.HashMap;
import java.util.List;

public class ReviewPage extends Page {

	private Logger log = LoggerFactory.getLogger(ReviewPage.class);

	public String pageName = "REVIEW_PAGE";

	public ReviewPage(WebDriver driver) {
		super(driver);
	}

	public WebElement headerLabel() {
		return getWebElement(pageName, "reviewPageLbl");
	}

	public List<WebElement> stepHeaderLabels() {
		return getElementsFromPage(pageName, "rpStepHeader");
	}

	public WebElement activeStep() {
		return getWebElement(pageName, "activeStep");
	}

	public WebElement backToPreviousStepButton() {
		return getWebElement(pageName, "backToPreviousStepBtn");
	}

	public WebElement backToSubBrandListLink() {
		return getWebElement(pageName, "backToSubBrandsListLnk");
	}

	public WebElement publishToPrivate() {
		return getWebElement(pageName, "publishToPrivateBtn");
	}

	public WebElement configureAppsButton() {
		return getWebElement(pageName, "configureAppsBtn");
	}

	public WebElement reviewGenConfigSubBrandNameLbl() {
		return getWebElement(pageName, "reviewGenConfigSubBrandNameLbl");
	}

	public WebElement reviewGenConfigSubBrandNameValueLbl() {
		return getWebElement(pageName, "reviewGenConfigSubBrandNameValueLbl");
	}

	public WebElement reviewGenConfigDomainNameSubStringLbl() {
		return getWebElement(pageName, "reviewGenConfigDomainNameSubStringLbl");
	}

	public WebElement reviewGenConfigDomainNameSubStringValueLbl() {
		return getWebElement(pageName, "reviewGenConfigDomainNameSubStringValueLbl");
	}

	public WebElement reviewGenConfigSourceIpAddressLbl() {
		return getWebElement(pageName, "reviewGenConfigSourceIpAddressLbl");
	}

	public List<WebElement> reviewGenConfigSourceIpAddressValueLbl() {
		return getElementsFromPage(pageName, "reviewGenConfigSourceIpAddressValueLbl");
	}

	public WebElement reviewGenConfigTier1CustomerCareLbl() {
		return getWebElement(pageName, "reviewGenConfigTier1CustomerCareLbl");
	}

	public WebElement reviewGenConfigTier1CustomerCareValueLbl() {
		return getWebElement(pageName, "reviewGenConfigTier1CustomerCareValueLbl");
	}

	public WebElement reviewPrivateSetupLoginMechanismLbl() {
		return getWebElement(pageName, "reviewPrivateSetupLoginMechanismLbl");
	}

	public List<WebElement> reviewPrivateSetupLoginMechanismValueLbl() {
		return getElementsFromPage(pageName, "reviewPrivateSetupLoginMechanismValueLbl");
	}

	public WebElement reviewPrivateSetupCredentialsLbl() {
		return getWebElement(pageName, "reviewPrivateSetupCredentialsLbl");
	}

	public WebElement reviewPrivateSetupCredentialsValueLbl() {
		return getWebElement(pageName, "reviewPrivateSetupCredentialsValueLbl");
	}

	public WebElement reviewPrivateSetupSamlLbl() {
		return getWebElement(pageName, "reviewPrivateSetupSamlLbl");
	}

	public List<WebElement> reviewPrivateSetupSamlValueLbl() {
		return getElementsFromPage(pageName, "reviewPrivateSetupSamlValueLbl");
	}

	public WebElement reviewPrivateSetupJwtOneLbl() {
		return getWebElement(pageName, "reviewPrivateSetupJwtOneLbl");
	}

	public List<WebElement> reviewPrivateSetupJwtOneValueLbl() {
		return getElementsFromPage(pageName, "reviewPrivateSetupJwtOneValueLbl");
	}

	public WebElement reviewPrivateSetupJwtOneIssuerIdLbl() {
		return getWebElement(pageName, "reviewPrivateSetupJwtOneIssuerIdLbl");
	}

	public WebElement reviewPrivateSetupJwtTwoLbl() {
		return getWebElement(pageName, "reviewPrivateSetupJwtTwoLbl");
	}

	public List<WebElement> reviewPrivateSetupJwtTwoValueLbl() {
		return getElementsFromPage(pageName, "reviewPrivateSetupJwtTwoValueLbl");
	}

	public WebElement reviewPrivateSetupJwtTwoIssuerIdLbl() {
		return getWebElement(pageName, "reviewPrivateSetupJwtTwoIssuerIdLbl");
	}

	public WebElement reviewPublicSetupLoginMechanismLbl() {
		return getWebElement(pageName, "reviewPublicSetupLoginMechanismLbl");
	}

	public List<WebElement> reviewPublicSetupLoginMechanismValueLbl() {
		return getElementsFromPage(pageName, "reviewPublicSetupLoginMechanismValueLbl");
	}

	public WebElement reviewPublicSetupCredentialsLbl() {
		return getWebElement(pageName, "reviewPublicSetupCredentialsLbl");
	}

	public WebElement reviewPublicSetupCredentialsValueLbl() {
		return getWebElement(pageName, "reviewPublicSetupCredentialsValueLbl");
	}

	public WebElement reviewPublicSetupSamlLbl() {
		return getWebElement(pageName, "reviewPublicSetupSamlLbl");
	}

	public List<WebElement> reviewPublicSetupSamlValueLbl() {
		return getElementsFromPage(pageName, "reviewPublicSetupSamlValueLbl");
	}

	public WebElement reviewPublicSetupJwtOneLbl() {
		return getWebElement(pageName, "reviewPublicSetupJwtOneLbl");
	}

	public List<WebElement> reviewPublicSetupJwtOneValueLbl() {
		return getElementsFromPage(pageName, "reviewPublicSetupJwtOneValueLbl");
	}

	public WebElement reviewPublicSetupJwtOneIssuerIdLbl() {
		return getWebElement(pageName, "reviewPublicSetupJwtOneIssuerIdLbl");
	}

	public WebElement reviewPublicSetupJwtTwoLbl() {
		return getWebElement(pageName, "reviewPublicSetupJwtTwoLbl");
	}

	public List<WebElement> reviewPublicSetupJwtTwoValueLbl() {
		return getElementsFromPage(pageName, "reviewPublicSetupJwtTwoValueLbl");
	}

	public WebElement reviewPublicSetupJwtTwoIssuerIdLbl() {
		return getWebElement(pageName, "reviewPublicSetupJwtTwoIssuerIdLbl");
	}

	public WebElement reviewSelectApiProductsLbl() {
		return getWebElement(pageName, "reviewSelectApiProductsLbl");
	}

	public List<WebElement> reviewSelectApiProductsValueLbl() {
		return getElementsFromPage(pageName, "reviewSelectApiProductsValueLbl");
	}

	public WebElement reviewSelectProductsAggregationLbl() {
		return getWebElement(pageName, "reviewSelectProductsAggregationLbl");
	}

	public WebElement reviewSelectProductsAggregationValueLbl() {
		return getWebElement(pageName, "reviewSelectProductsAggregationValueLbl");
	}

	public WebElement reviewSelectProductsVerificationLbl() {
		return getWebElement(pageName, "reviewSelectProductsVerificationLbl");
	}

	public WebElement reviewSelectProductsVerificationValueLbl() {
		return getWebElement(pageName, "reviewSelectProductsVerificationValueLbl");
	}

	public WebElement reviewSelectApplicationsToBeEnabledYodleeFL3Lbl() {
		return getWebElement(pageName, "reviewSelectApplicationsToBeEnabledYodleeFL3Lbl");
	}

	public WebElement reviewSelectApplicationsToBeEnabledYodleeFL3ValueLbl() {
		return getWebElement(pageName, "reviewSelectApplicationsToBeEnabledYodleeFL3ValueLbl");
	}

	public WebElement reviewSelectApplicationsToBeEnabledYodleePFM3Lbl() {
		return getWebElement(pageName, "reviewSelectApplicationsToBeEnabledYodleePFM3Lbl");
	}

	public List<WebElement> reviewSelectApplicationsToBeEnabledYodleePFM3ValueLbl() {
		return getElementsFromPage(pageName, "reviewSelectApplicationsToBeEnabledYodleePFM3ValueLbl");
	}

	public WebElement reviewContact1of2Lbl() {
		return getWebElement(pageName, "reviewContact1of2Lbl");
	}

	public List<WebElement> reviewContact1of2ValueLbl() {
		return getElementsFromPage(pageName, "reviewContact1of2ValueLbl");
	}

	public WebElement reviewContact2of2Lbl() {
		return getWebElement(pageName, "reviewContact2of2Lbl");
	}

	public List<WebElement> reviewContact2of2ValueLbl() {
		return getElementsFromPage(pageName, "reviewContact2of2ValueLbl");
	}

	public HashMap<String, String> getReviewScreenGeneralConfigurationDetails() {

		HashMap<String, String> revGenConfigDetails = new HashMap();
		String subBrandName = reviewGenConfigSubBrandNameValueLbl().getText();
		String domainNameSubString = reviewGenConfigDomainNameSubStringValueLbl().getText();

		String sourceIPAddress = "";
		for (int i = 0; i < reviewGenConfigSourceIpAddressValueLbl().size(); i++) {
			if (i == 0)
				sourceIPAddress = reviewGenConfigSourceIpAddressValueLbl().get(i).getText().toString().trim();
			else
				sourceIPAddress = sourceIPAddress + ";"
						+ reviewGenConfigSourceIpAddressValueLbl().get(i).getText().toString().trim();
		}

		revGenConfigDetails.put("subBrandName", subBrandName);
		revGenConfigDetails.put("domainNameSubString", domainNameSubString);
		revGenConfigDetails.put("sourceIPAddress", sourceIPAddress);

		return revGenConfigDetails;

	}

	public HashMap<String, String> getReviewScreenPrivateAuthenticationSetupDetails(String loginMechanism,
			boolean enableMultipleJWT, boolean addAnotherJWT) {

		HashMap<String, String> revPrivateAuthSetupDetails = new HashMap();
		String loginMech = "";
		for (int i = 0; i < reviewPrivateSetupLoginMechanismValueLbl().size(); i++) {
			if (i == 0)
				loginMech = reviewPrivateSetupLoginMechanismValueLbl().get(i).getText().toString().trim();
			else
				loginMech = loginMech + ";"
						+ reviewPrivateSetupLoginMechanismValueLbl().get(i).getText().toString().trim();
		}
		revPrivateAuthSetupDetails.put("loginMechanism", loginMech);

		if (loginMechanism.contains("JWT")) {
			String devJWTOneSetupDetails = "";
			for (int i = 0; i < reviewPrivateSetupJwtOneValueLbl().size(); i++) {
				if (i == 0)
					devJWTOneSetupDetails = reviewPrivateSetupJwtOneValueLbl().get(i).getText().toString().trim();
				else
					devJWTOneSetupDetails = devJWTOneSetupDetails + ";"
							+ reviewPrivateSetupJwtOneValueLbl().get(i).getText().toString().trim();
			}
			revPrivateAuthSetupDetails.put("JWT1", devJWTOneSetupDetails);

			if (enableMultipleJWT || addAnotherJWT) {
				String devJWTTwoSetupDetails = "";
				for (int i = 0; i < reviewPrivateSetupJwtTwoValueLbl().size(); i++) {
					if (i == 0)
						devJWTTwoSetupDetails = reviewPrivateSetupJwtTwoValueLbl().get(i).getText().toString().trim();
					else
						devJWTTwoSetupDetails = devJWTTwoSetupDetails + ";"
								+ reviewPrivateSetupJwtTwoValueLbl().get(i).getText().toString().trim();
				}
				revPrivateAuthSetupDetails.put("JWT2", devJWTTwoSetupDetails);
			}
		}
		if (loginMechanism.contains("SAML")) {
			String devSAMLSetupDetails = "";
			for (int i = 0; i < reviewPrivateSetupSamlValueLbl().size(); i++) {
				if (i == 0)
					devSAMLSetupDetails = reviewPrivateSetupSamlValueLbl().get(i).getText().toString().trim();
				else
					devSAMLSetupDetails = devSAMLSetupDetails + ";"
							+ reviewPrivateSetupSamlValueLbl().get(i).getText().toString().trim();
			}
			revPrivateAuthSetupDetails.put("SAML", devSAMLSetupDetails);
		}
		return revPrivateAuthSetupDetails;
	}

	public HashMap<String, String> getReviewScreenPublicAuthenticationSetupDetails(String loginMechanism,
			boolean enableMultipleJWT, boolean addAnotherJWT) {

		HashMap<String, String> revPublicAuthSetupDetails = new HashMap();
		String loginMech = "";
		for (int i = 0; i < reviewPublicSetupLoginMechanismValueLbl().size(); i++) {
			if (i == 0)
				loginMech = reviewPublicSetupLoginMechanismValueLbl().get(i).getText().toString().trim();
			else
				loginMech = loginMech + ";"
						+ reviewPublicSetupLoginMechanismValueLbl().get(i).getText().toString().trim();
		}
		revPublicAuthSetupDetails.put("loginMechanism", loginMech);

		if (loginMechanism.contains("JWT")) {
			String prodJWTOneSetupDetails = "";
			for (int i = 0; i < reviewPublicSetupJwtOneValueLbl().size(); i++) {
				if (i == 0)
					prodJWTOneSetupDetails = reviewPublicSetupJwtOneValueLbl().get(i).getText().toString().trim();
				else
					prodJWTOneSetupDetails = prodJWTOneSetupDetails + ";"
							+ reviewPublicSetupJwtOneValueLbl().get(i).getText().toString().trim();
			}
			revPublicAuthSetupDetails.put("JWT1", prodJWTOneSetupDetails);

			if (enableMultipleJWT || addAnotherJWT) {
				String prodJWTTwoSetupDetails = "";
				for (int i = 0; i < reviewPublicSetupJwtTwoValueLbl().size(); i++) {
					if (i == 0)
						prodJWTTwoSetupDetails = reviewPublicSetupJwtTwoValueLbl().get(i).getText().toString().trim();
					else
						prodJWTTwoSetupDetails = prodJWTTwoSetupDetails + ";"
								+ reviewPublicSetupJwtTwoValueLbl().get(i).getText().toString().trim();
				}
				revPublicAuthSetupDetails.put("JWT2", prodJWTTwoSetupDetails);
			}
		}

		if (loginMechanism.contains("SAML")) {
			String prodSAMLSetupDetails = "";
			for (int i = 0; i < reviewPublicSetupSamlValueLbl().size(); i++) {
				if (i == 0)
					prodSAMLSetupDetails = reviewPublicSetupSamlValueLbl().get(i).getText().toString().trim();
				else
					prodSAMLSetupDetails = prodSAMLSetupDetails + ";"
							+ reviewPublicSetupSamlValueLbl().get(i).getText().toString().trim();
			}
			revPublicAuthSetupDetails.put("SAML", prodSAMLSetupDetails);
		}
		return revPublicAuthSetupDetails;
	}

	public HashMap<String, String> getReviewScreenProductDetails(String selectedProducts) {

		HashMap<String, String> revProductDetails = new HashMap<String, String>();
		String products = "";
		for (int i = 0; i < reviewSelectApiProductsValueLbl().size(); i++) {
			if (i == 0)
				products = reviewSelectApiProductsValueLbl().get(i).getText().toString().trim();
			else
				products = products + ";" + reviewSelectApiProductsValueLbl().get(i).getText().toString().trim();
		}
		revProductDetails.put("products", products);

		if (selectedProducts.contains("Aggregation")) {
			String aggregation = reviewSelectProductsAggregationValueLbl().getText().replace("\n", " ");
			revProductDetails.put("aggregation", aggregation);
		}

		if (selectedProducts.contains("Verification")) {
			String verification = reviewSelectProductsVerificationValueLbl().getText().replace("\n", " ");
			revProductDetails.put("verification", verification);
		}

		return revProductDetails;
	}

	public HashMap<String, String> getReviewScreenHostedApplicationDetails(String hostedApps) {

		HashMap<String, String> revHostedAppsDetails = new HashMap();

		if (hostedApps.contains(Constants.SELECT_APPLICATIONS_YODLEE_FASTLINK)) {
			String appFL3 = reviewSelectApplicationsToBeEnabledYodleeFL3ValueLbl().getText();
			revHostedAppsDetails.put("Yodlee FastLink", appFL3);
		}

		if (hostedApps.contains(Constants.SELECT_APPLICATIONS_YODLEE_FINANCIAL_WELLNESS_FINAPPS)) {
			String finapps = "";
			for (int i = 0; i < reviewSelectApplicationsToBeEnabledYodleePFM3ValueLbl().size(); i++) {
				if (i == 0)
					finapps = reviewSelectApplicationsToBeEnabledYodleePFM3ValueLbl().get(i).getText().toString()
							.trim();
				else
					finapps = finapps + ";" + reviewSelectApplicationsToBeEnabledYodleePFM3ValueLbl().get(i).getText()
							.toString().trim();
			}
			revHostedAppsDetails.put("Yodlee Financial Wellness FinApps", finapps);
		}
		return revHostedAppsDetails;
	}

	public HashMap<String, String> getReviewContactDetails(boolean enableMultipleContact, boolean addAnotherContact) {

		HashMap<String, String> revContactDetails = new HashMap();
		String contactOne = "";
		for (int i = 0; i < reviewContact1of2ValueLbl().size(); i++) {
			if (i == 0)
				contactOne = reviewContact1of2ValueLbl().get(i).getText().toString().trim();
			else
				contactOne = contactOne + ";" + reviewContact1of2ValueLbl().get(i).getText().toString().trim();
		}
		revContactDetails.put("ContactOneDetails", contactOne);

		if (enableMultipleContact || addAnotherContact) {
			String contactTwo = "";
			for (int i = 0; i < reviewContact2of2ValueLbl().size(); i++) {
				if (i == 0)
					contactTwo = reviewContact2of2ValueLbl().get(i).getText().toString().trim();
				else
					contactTwo = contactTwo + ";" + reviewContact2of2ValueLbl().get(i).getText().toString().trim();
			}
			revContactDetails.put("ContactTwoDetails", contactTwo);
		}

		return revContactDetails;
	}

}