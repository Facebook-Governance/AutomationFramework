package com.org.pages;

import org.openqa.selenium.WebDriver;

import com.org.Util.LocatorUtils;
import com.org.Util.SeleniumUtils;

public class ApplyFiltersToSelectProducts extends SeleniumUtils{

	public ApplyFiltersToSelectProducts(WebDriver driver) {
		super(driver);
	}
	
	public void applyStockFilter() {
		selectTheCheckBox(LocatorUtils.getLocator("LandingPage.Women.AvailabilityCheckBox"));
	}

	public void applyConditionFilter() {
		selectTheCheckBox(LocatorUtils.getLocator("LandingPage.Women.ConditionCheckBox"));
	}

	public void applyManufacturerFilter() {
		selectTheCheckBox(LocatorUtils.getLocator("LandingPage.Women.ManufacturerCheckBox"));
	}

}
