/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
package com.omni.pfm.pages.DemoSite;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.omni.pfm.utility.SeleniumUtil;

public class RegistrationLandingScreen_Loc {

	WebDriver d;
	String pageName="DemoSite";
	
	public RegistrationLandingScreen_Loc(WebDriver d) {
		this.d=d;
	}
	
	public WebElement yodleeLogo() {

		return SeleniumUtil.getVisibileWebElement(d, "YodleeLogo", pageName, null);

	}

	public WebElement loginLink() {

		return SeleniumUtil.getVisibileWebElement(d, "RegistrationLoginLink", pageName, null);

	}

	public WebElement support() {

		return SeleniumUtil.getVisibileWebElement(d, "RegistrationSupport", pageName, null);

	}

	public WebElement downloadApp() {

		return SeleniumUtil.getVisibileWebElement(d, "RegistrationDownloadApp", pageName, null);

	}

	public WebElement screen1_HeaderText() {

		return SeleniumUtil.getVisibileWebElement(d, "RegistrationScreen1_HeaderText", pageName, null);

	}

	public WebElement screen1_Text1() {

		return SeleniumUtil.getVisibileWebElement(d, "RegistrationScreen1_Text1", pageName, null);

	}

	public WebElement screen1_Text2() {

		return SeleniumUtil.getVisibileWebElement(d, "RegistrationScreen1_Text2", pageName, null);

	}

	public WebElement screen2_HeaderText() {

		return SeleniumUtil.getVisibileWebElement(d, "RegistrationScreen2_HeaderText", pageName, null);

	}

	public WebElement screen2_SubHeaderText() {

		return SeleniumUtil.getVisibileWebElement(d, "RegistrationScreen2_SubHeaderText", pageName, null);

	}

	public WebElement screen2_Para1() {

		return SeleniumUtil.getVisibileWebElement(d, "RegistrationScreen2_Para1", pageName, null);

	}

	public WebElement screen2_Para1_Text1() {

		return SeleniumUtil.getVisibileWebElement(d, "RegistrationScreen2_Para1_Text1", pageName, null);

	}

	public WebElement screen2_Para1_Text2() {

		return SeleniumUtil.getVisibileWebElement(d, "RegistrationScreen2_Para1_Text2", pageName, null);

	}

	public WebElement screen2_Para2() {

		return SeleniumUtil.getVisibileWebElement(d, "RegistrationScreen2_Para2", pageName, null);

	}

	public WebElement screen2_Para2_Text1() {

		return SeleniumUtil.getVisibileWebElement(d, "RegistrationScreen2_Para2_Text1", pageName, null);

	}

	public WebElement screen2_Para2_Text2() {

		return SeleniumUtil.getVisibileWebElement(d, "RegistrationScreen2_Para2_Text2", pageName, null);

	}

	public WebElement screen2_Para3() {

		return SeleniumUtil.getVisibileWebElement(d, "RegistrationScreen2_Para3", pageName, null);

	}

	public WebElement screen2_Para3_Text1() {

		return SeleniumUtil.getVisibileWebElement(d, "RegistartionScreen2_Para3_Text1", pageName, null);

	}

	public WebElement screen2_Para3_Text2() {

		return SeleniumUtil.getVisibileWebElement(d, "RegistartionScreen2_Para3_Text2", pageName, null);

	}

	public WebElement screen3_HeaderText() {

		return SeleniumUtil.getVisibileWebElement(d, "RegistartionScreen3_HeaderText", pageName, null);

	}

	public WebElement screen3_Text() {

		return SeleniumUtil.getVisibileWebElement(d, "RegistartionScreen3_Text", pageName, null);

	}

	public WebElement loginButton() {

		return SeleniumUtil.getVisibileWebElement(d, "RegistartionLoginButton", pageName, null);

	}

	public WebElement signUpButton() {

		return SeleniumUtil.getVisibileWebElement(d, "RegistartionSignUpButton", pageName, null);

	}

	public WebElement privacyNotice() {

		return SeleniumUtil.getVisibileWebElement(d, "RegistartionPrivacyNotice", pageName, null);

	}

	public WebElement securityPolicy() {

		return SeleniumUtil.getVisibileWebElement(d, "RegistartionSecurityPolicy", pageName, null);

	}

	public WebElement disclaimer() {

		return SeleniumUtil.getVisibileWebElement(d, "RegistartionDisclaimer", pageName, null);

	}

	public WebElement termsAndConditions() {

		return SeleniumUtil.getVisibileWebElement(d, "RegistartionTermsAndConditions", pageName, null);

	}

	public WebElement trusteCertified() {

		return SeleniumUtil.getVisibileWebElement(d, "RegistartionTrusteCertified", pageName, null);

	}
	public WebElement trusteApec() {

		return SeleniumUtil.getVisibileWebElement(d, "RegistartionTrusteApec", pageName, null);

	}

	public WebElement privacyNoticeWindow() {

		return SeleniumUtil.getVisibileWebElement(d, "RegistrationPrivacyNoticeWindow", pageName, null);

	}

	public WebElement securityPolicyWindow() {

		return SeleniumUtil.getVisibileWebElement(d, "RegistartionSecurityPolicyWindow", pageName, null);

	}


	public WebElement disclaimerWindow() {

		return SeleniumUtil.getVisibileWebElement(d, "RegistartionDisclaimerWindow", pageName, null);

	}
	
	public WebElement tNCWindow() {

		return SeleniumUtil.getVisibileWebElement(d, "RegistartionTNCWindow", pageName, null);

	}
	
	public WebElement trustedeCertifiedWindow() {

		return SeleniumUtil.getVisibileWebElement(d, "RegistartionTrustedeCertifiedWindow", pageName, null);

	}
	
	public WebElement trustedeApecWindow() {

		return SeleniumUtil.getVisibileWebElement(d, "RegistartionTrustedeApecWindow", pageName, null);

	}
}
