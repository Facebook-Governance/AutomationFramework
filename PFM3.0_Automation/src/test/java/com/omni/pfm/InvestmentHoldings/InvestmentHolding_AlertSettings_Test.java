/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author anair
*/

package com.omni.pfm.InvestmentHoldings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.InvestmentHoldings.InvestmentHoldings_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class InvestmentHolding_AlertSettings_Test extends TestBase {
	static Logger logger = LoggerFactory.getLogger(InvestmentHolding_AlertSettings_Test.class);

	InvestmentHoldings_Loc investmentHoldings;
	String expectedClassificationValues[];

	@BeforeClass()
	public void init() {
		doInitialization("Investment Holdings");
		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);
		investmentHoldings = new InvestmentHoldings_Loc(d);

	}

	@Test(description = "INV_03_01:Verify login happenes successfully.", priority = 1, groups = { "Desktop", "Smoke" })
	public void login() throws Exception {
		PageParser.forceNavigate("InvestmentHoldings", d);
	}

	@Test(description = "Open Alert Popup", priority = 2, enabled = true)
	public void openAlertPopup() {

		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			investmentHoldings.moreOption().get(0).click();
			SeleniumUtil.waitForPageToLoad();
			investmentHoldings.menuAlert().get(0).click();
			SeleniumUtil.waitForPageToLoad();
		} else {
			investmentHoldings.moreOption().get(0).click();
			SeleniumUtil.waitForPageToLoad();
			SeleniumUtil.click(investmentHoldings.alertsettings());
			SeleniumUtil.waitForPageToLoad();
		}
	}

	@Test(description = "AT-81071,AT-81074,AT-81075:INV_01_26:Verify Alert settings popup text", priority = 3, enabled = true, dependsOnMethods = {
			"login" })
	public void verifyAlertSettings() {

		logger.info("==>Verify Alert Settings popup text");

		String alertHeaderText = investmentHoldings.alertHeader().getText();
		Assert.assertEquals(alertHeaderText, PropsUtil.getDataPropertyValue("IH_alertHeader"));

		String alertBalIncrease = investmentHoldings.alertBalance().get(0).getText();
		Assert.assertEquals(alertBalIncrease, PropsUtil.getDataPropertyValue("IH_alertBalIncrease"));

		SeleniumUtil.waitForPageToLoad();
		String alertBalIncreaseText = investmentHoldings.alertBalanceText(0);
		Assert.assertEquals(alertBalIncreaseText, PropsUtil.getDataPropertyValue("IH_alertBalIncreaseText"));

		String alertBalDecrease = investmentHoldings.alertBalance().get(2).getText();
		Assert.assertEquals(alertBalDecrease, PropsUtil.getDataPropertyValue("IH_alertBalDecrease"));

		String alertBalDecreasetext = investmentHoldings.alertBalanceText(1);
		Assert.assertEquals(alertBalDecreasetext, PropsUtil.getDataPropertyValue("IH_alertBalDecreaseText"));

		Assert.assertEquals(investmentHoldings.alertBalDrpdwnAmt().get(0).getText(),
				PropsUtil.getDataPropertyValue("IH_alertBalDropdwnAmount"));
		Assert.assertEquals(investmentHoldings.alertBalanceValue().get(0).getText(),
				PropsUtil.getDataPropertyValue("IH_alertBalAmountValue"));

	}

	@Test(description = "AT-81076,AT-81077,AT-81078:INV_01_27:Verify Amount dropdown in Alert settings popup", priority = 4, enabled = true, dependsOnMethods = {
			"login" })
	public void verifyAmountDropdown() {
		logger.info("==>Verify Amount dropdown in Alert settings popup");
		investmentHoldings.alertBalDrpdwnAmt().get(0).click();
		Assert.assertTrue(investmentHoldings.alertBalDrpdwnValue(0).isDisplayed());
		Assert.assertTrue(investmentHoldings.alertBalDrpdwnValue(1).isDisplayed());
		Assert.assertTrue(investmentHoldings.alertBalDrpdwnTickmark(0).get(1).isDisplayed());
		Assert.assertEquals(investmentHoldings.alertBalanceToggle().get(1).getCssValue("background-color"),
				PropsUtil.getDataPropertyValue("IH_alertBalToggleOff"));

	}

	@Test(description = "AT-81079:INV_01_28:Verify Amount field in Alert settings popup", priority = 5, enabled = true, dependsOnMethods = {
			"login" })
	public void verifyAmountField() {
		logger.info("==>Verify Amount field in Alert settings popup");
		investmentHoldings.alertBalDrpdwnValue(1).click();
		Assert.assertEquals(investmentHoldings.alertBalDrpdwnAmt().get(0).getText(),
				PropsUtil.getDataPropertyValue("IH_alertBalDropdwnPercentage"));
		Assert.assertEquals(investmentHoldings.alertBalanceToggle().get(0).getCssValue("background-color"),
				PropsUtil.getDataPropertyValue("IH_alertBalToggleOn"));
		Assert.assertEquals(investmentHoldings.alertBalanceValue().get(0).getText(),
				PropsUtil.getDataPropertyValue("IH_alertBalPercentageValue"));
	}

	@Test(description = "AT-81080:INV_01_28:Verify Amount field in Alert settings popup", priority = 6, enabled = true, dependsOnMethods = {
			"login" })
	public void verifyAmountValue() {
		logger.info("==>Verify Amount Value in Alert settings popup");
		investmentHoldings.alertBalanceValue().get(0).click();
		Assert.assertTrue(investmentHoldings.alertInfo().get(0).isDisplayed());
		Assert.assertEquals(investmentHoldings.alertInfo().get(0).getText(),
				PropsUtil.getDataPropertyValue("IH_alertInfo"));
	}

	@Test(description = "AT-81081:INV_01_28:Verify Amount field in Alert settings popup", priority = 7, enabled = true, dependsOnMethods = {
			"login" })
	public void verifyBalanceField() {
		logger.info("==>Verify Balance Field in Alert settings popup");
		investmentHoldings.alertBalDrpdwnAmt().get(0).click();
		investmentHoldings.waitFor(1);
		investmentHoldings.alertBalDrpdwnValue(0).click();
		Assert.assertEquals(investmentHoldings.alertBalanceValue().get(0).getText(),
				PropsUtil.getDataPropertyValue("IH_alertBalAmountValue"));
	}

	@Test(description = "AT-81085:INV_01_28:Verify Amount field in Alert settings popup", priority = 8, enabled = true, dependsOnMethods = {
			"login" })
	public void verifyBalanceValue() {
		logger.info("==>Verify Balance Value in Alert settings popup");
		investmentHoldings.alertBalanceValue().get(0).click();
		investmentHoldings.alertBalanceField().get(0)
				.sendKeys(PropsUtil.getDataPropertyValue("IH_alertBalanceMaxValue"));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(investmentHoldings.alertBalanceField().get(0).getAttribute("value"),
				PropsUtil.getDataPropertyValue("IH_alertBalanceValue"));
	}

	@Test(description = "AT-81087:INV_01_29:Verify buttons in Alert settings popup", priority = 9, enabled = true, dependsOnMethods = {
			"login" })
	public void verifyCancelButtonValidations() {
		logger.info("==>Verify Cancel buttons and close buttons in Alert settings popup");
		if (appFlag.equals("emulator") || appFlag.equals("app")) {
			SeleniumUtil.click(investmentHoldings.alertCrossicon());
			Assert.assertTrue(investmentHoldings.moreOption().get(0).isDisplayed());
		} else {
			Assert.assertTrue(investmentHoldings.alertCancelButton().isDisplayed());
			investmentHoldings.alertCancelButton().click();
			SeleniumUtil.waitForPageToLoad();
			Assert.assertTrue(investmentHoldings.moreOption().get(0).isDisplayed());
		}
	}

	@Test(description = "AT-81088,AT-81089:INV_01_29:Verify buttons in Alert settings popup", priority = 10, enabled = true, dependsOnMethods = {
			"login" })
	public void verifySaveButtonValidations() {
		logger.info("==>Verify Save buttons in Alert settings popup");
		openAlertPopup();
		Assert.assertTrue(investmentHoldings.alertSaveButton().get(0).getAttribute("class").contains("disabled"));
		investmentHoldings.alertBalDrpdwnAmt().get(0).click();
		investmentHoldings.alertBalDrpdwnValue(1).click();
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			Assert.assertFalse(investmentHoldings.alertSaveButton().get(1).getAttribute("class").contains("disabled"));
			investmentHoldings.alertSaveButton().get(1).click();
			SeleniumUtil.waitForPageToLoad();
		} else {
			Assert.assertFalse(investmentHoldings.alertSaveButton().get(0).getAttribute("class").contains("disabled"));
			investmentHoldings.alertSaveButton().get(0).click();
			SeleniumUtil.waitForPageToLoad();
			Assert.assertTrue(investmentHoldings.moreOption().get(0).isDisplayed());
		}
	}

	@Test(description = "AT-81090:INV_01_29:Verify buttons in Alert settings popup", priority = 11, enabled = true, dependsOnMethods = {
			"login" })
	public void verifyButtonValidations() {
		logger.info("==>Verify buttons in Alert settings popup");
		openAlertPopup();
		logger.info("========================================== dropdown amount"
				+ investmentHoldings.alertBalDrpdwnAmt().get(0).getText());
		Assert.assertEquals(investmentHoldings.alertBalDrpdwnAmt().get(0).getText(),
				PropsUtil.getDataPropertyValue("IH_alertBalDropdwnPercentage"));
	}

	@Test(description = "AT-80976:INV_01_30:Verify chart data validations", priority = 12, enabled = true, dependsOnMethods = {
			"login" })
	public void verifyChartDataValidations() {
		investmentHoldings.alertCrossicon().click();
		SeleniumUtil.waitForPageToLoad(500);
		logger.info("==>Verify chart data validations");
		logger.info("==>Verify chart data validations " + investmentHoldings.chartData().get(0).getText());
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			Assert.assertEquals(investmentHoldings.chartData().get(0).getText(),
					PropsUtil.getDataPropertyValue("IH_assetClass1"));
			Assert.assertEquals(investmentHoldings.chartDataMobile().get(0).getText(),
					PropsUtil.getDataPropertyValue("IH_assetClass1Amt"));
			Assert.assertEquals(investmentHoldings.chartDataMobile().get(1).getText(),
					PropsUtil.getDataPropertyValue("IH_assetClass1Change"));
		} else {
			Assert.assertEquals(investmentHoldings.chartData().get(0).getText(),
					PropsUtil.getDataPropertyValue("IH_assetClass1"));
			Assert.assertEquals(investmentHoldings.chartData().get(1).getText(),
					PropsUtil.getDataPropertyValue("IH_assetClass1Amt"));
			Assert.assertEquals(investmentHoldings.chartData().get(2).getText(),
					PropsUtil.getDataPropertyValue("IH_assetClass1Change"));
		}
	}

	@Test(description = "AT-80989,AT-80984:INV_01_31:Verify expand and collapse icons", priority = 13, enabled = true, dependsOnMethods = {
			"login" })
	public void verifyExpandCollapseIcons() {
		logger.info("==>Verify expand and collapse icons");
		if (!appFlag.equals("app") || appFlag.equals("emulator")) {
			Assert.assertEquals(investmentHoldings.expandCollapseButton().getText(),
					PropsUtil.getDataPropertyValue("IH_ExpendALL"));
			investmentHoldings.expandCollapseButton().click();
			Assert.assertEquals(investmentHoldings.expandCollapseButton().getText(),
					PropsUtil.getDataPropertyValue("IH_Collapse_All"));
			for (int i = 0; i < investmentHoldings.expandCollapseIcons().size(); i++) {
				Assert.assertTrue(investmentHoldings.expandCollapseIcons().get(i).isDisplayed());
			}
		}
	}

	@Test(description = "AT-81052,AT-81064,AT-80977,AT-81051:INV_01_32:Verify Change and change% value calculation", priority = 14, enabled = false, dependsOnMethods = {
			"login" })
	public void verifyChangeCalculation() {
		logger.info("==>Verify Change and change% value calculation");
		String marketValue = investmentHoldings.marketValue().get(0).getText().replaceAll("[$,]", "");
		String costBasisValue = investmentHoldings.costBasisValue().get(0).getText().replaceAll("[$,]", "");
		double change = Double.parseDouble(marketValue) - Double.parseDouble(costBasisValue);
		logger.info("change ==========================> "
				+ Double.parseDouble(investmentHoldings.changeValue().get(0).getText().replaceAll("[$,]", "")) + "  "
				+ change);
		Assert.assertEquals(
				Double.parseDouble(investmentHoldings.changeValue().get(0).getText().replaceAll("[$,]", "")), change);
		double changePercent = (change / Double.parseDouble(costBasisValue)) * 100;
		Assert.assertEquals(
				Double.parseDouble(investmentHoldings.changePercentageValue().get(0).getText().replaceAll("[$,]", "")),
				changePercent);
	}

	@Test(description = "AT-80948,AT-80952:INV_01_33:Verify Account Filter Dropdown", priority = 15, enabled = true, dependsOnMethods = {
			"login" })
	public void verifyAccDropdown() {
		logger.info("==>Verify Account Filter Dropdown");
		SeleniumUtil.click(investmentHoldings.accountDropDown());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(investmentHoldings.accountDropDown().getText(),
				PropsUtil.getDataPropertyValue("IH_AccDropdown"));// TODO
		List<WebElement> accNames = investmentHoldings.accsDetails();
		List<WebElement> accNumbers = investmentHoldings.accsNumber();
		List<String> expectedBankNames = Arrays.asList(new String[] { PropsUtil.getDataPropertyValue("IH_AccDetails_1"),
				PropsUtil.getDataPropertyValue("IH_AccDetails_2"), PropsUtil.getDataPropertyValue("IH_AccDetails_3") });
		List<String> expectedAccountNumbers = Arrays.asList(new String[] { PropsUtil.getDataPropertyValue("IH_AccNumber_1"),
				PropsUtil.getDataPropertyValue("IH_AccNumber_2"), PropsUtil.getDataPropertyValue("IH_AccNumber_3") });
		for(int i=0;i<accNames.size();i++) {
			String actualAccountName = accNames.get(i).getText();
			String actualAccountNumber = accNumbers.get(i).getText();
			if(expectedBankNames.contains(actualAccountName)) {
				int indexOfBankName = expectedBankNames.indexOf(actualAccountName);
				if(!expectedAccountNumbers.get(indexOfBankName).equals(actualAccountNumber)) {
					Assert.fail("Account number displayed in the dropdown for account name :: " + actualAccountName +" is not as expected. Expected :: " + expectedAccountNumbers.get(indexOfBankName) + " and Actual :: " + actualAccountNumber);
				}
			} else {
				Assert.fail("Account with name :: \"" + actualAccountName + "\" is not expected in the investment account dropdown. Expected Accounts in dropdown are :: " + expectedBankNames);
			}
		}
	}

	@Test(description = "AT-80952:INV_01_33:Verify Single Account Filter Dropdown", priority = 16, enabled = true, dependsOnMethods = {
			"login" })
	public void verifySingleAccDropdown() {
		logger.info("==>Verify Account Filter Dropdown");
		SeleniumUtil.click(investmentHoldings.accsDetails(0));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(investmentHoldings.accsDetails(1));
		SeleniumUtil.waitForPageToLoad();
		if (investmentHoldings.isAcntSelectDoneMobile()) {
			SeleniumUtil.click(investmentHoldings.AcntSelectDoneMobile());
			SeleniumUtil.waitForPageToLoad(500);
		}
		Assert.assertEquals(investmentHoldings.accountDropDown().getText(),
				PropsUtil.getDataPropertyValue("IH_AccDetails_2"));
	}

	@Test(description = "AT-80985,AT-80996,AT-80999:INV_01_34:Verify data inside the holdings table", priority = 17, enabled = true, dependsOnMethods = {
			"login" })
	public void verifyHoldingsData() {
		SeleniumUtil.click(investmentHoldings.accDrpdwnInvAcc().get(0));
		SeleniumUtil.click(investmentHoldings.accountDropDown());
		SeleniumUtil.waitForPageToLoad();
		logger.info("==>Verify data inside the holdings table");
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			SeleniumUtil.click(investmentHoldings.clickChartCategoryMobile());
		} else {
			SeleniumUtil.click(investmentHoldings.clickDetailsDropdown());
			SeleniumUtil.waitForPageToLoad();
			Assert.assertEquals(investmentHoldings.changePerValue().get(0).getText(), "N/A");
		}
		for (int i = 0; i < investmentHoldings.holdingName().size(); i++) {
			Assert.assertTrue(investmentHoldings.holdingName().get(0).isDisplayed());
			Assert.assertTrue(investmentHoldings.companyName().get(0).isDisplayed());
		}
	}
}