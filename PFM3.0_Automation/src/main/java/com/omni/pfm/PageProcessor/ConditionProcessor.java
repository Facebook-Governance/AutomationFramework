/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.PageProcessor;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.Exceptions.ElementAttributeNotFoundExecption;
import com.omni.pfm.XmlBeans.Element;
import com.omni.pfm.XmlBeans.PreCondition;
import com.omni.pfm.config.Config;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.Login.LoginPage_DeepLink;
import com.omni.pfm.utility.GenericUtil;
import com.omni.pfm.webdriver.WebDriverFactory;

public class ConditionProcessor {

    private static final Logger logger = LoggerFactory.getLogger(ConditionProcessor.class);

    static void processPreconditions(WebDriver d, List<PreCondition> conditions) {
	logger.info("==> Entering processPreconditions method.");
	logger.info("Number of PreConditions found = {}", conditions.size());
	for (PreCondition pc : conditions) {
	    switch (pc.getType().toLowerCase()) {

	    case "deep_link":
		performDeepLinking(d, pc);
		break;

	    default:
		logger.error("Could not identify then PreCondition - {}", pc.getType());
	    }
	}
	logger.info("<== Exiting processPreconditions method.");
    }

    /**
     * 
     */
    private static void performDeepLinking(WebDriver d, PreCondition pc) {
	String samlPage = pc.getSamlPage();
	if (GenericUtil.isNull(samlPage)) {
	    throw new ElementAttributeNotFoundExecption(
		    "samlPage Attribute for the Precondition[" + pc.getType() + "] is null or not specified.");
	} else {
	    List<Element> elements = pc.getElements();
	    PageParser.getPageElement(samlPage, null, "SAML_LOGIN.SubjectName").setElementValue(Config.getInstance().getCurrentUser());
	    if (elements == null || elements.size() < 1) {
		// SamlForm.samlLogin(d, samlPage, "");
	    }

	    else {
		for (Element e : elements) {
		    Element samlElement = PageParser.getPageElement(samlPage, null, e.getName());
		    if (samlElement != null) {
			System.out.println("-----------Value before setting = " + samlElement.getElementValue());
			samlElement.setElementValue(e.getElementValue());
			new LoginPage_DeepLink().login(d);
		    } else
			throw new ElementAttributeNotFoundExecption("Element " + e.getName() + " not found in page " + samlPage);
		    System.out.println("-----------Value after setting = " + samlElement.getElementValue());
		}
	    }
	}
    }

   /* public static void main(String[] args) {
	Config.createInstance("BBT");
	WebDriver d = new FirefoxDriver();
	//WebDriverFactory.setDriver(d);
	// SamlForm.samlLogin(d, "SAML_LOGIN", "");
	// processPreconditions(d, "TransactionPage");
	// processPreconditions(d, "NetworthPage");
    }*/
}
