/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.BudgetV2;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.Accounts.Accounts_ViewByGroup_Loc;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.config.Config;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.AccountSettings.AccountGroup_Settings_Loc;
import com.omni.pfm.pages.BudgetV2.Budget_BudgetSummary_Loc;
import com.omni.pfm.pages.BudgetV2.Budget_CreateBudget_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.GenericUtil;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Budget_Setup_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(Budget_Setup_Test.class);
	AccountAddition accountAddition;
	Budget_CreateBudget_Loc budgetCreateBudgetLoc;
	SeleniumUtil util;
	Accounts_ViewByGroup_Loc groupView;
	AccountGroup_Settings_Loc accountGroupSetting;
	Add_Manual_Transaction_Loc addManualTransaction;
	Budget_BudgetSummary_Loc budgetSummary;

	@BeforeClass(alwaysRun = true)
	public void doInit() throws Exception {
		doInitialization("Budget Setup");
		accountAddition = new AccountAddition();
		budgetCreateBudgetLoc = new Budget_CreateBudget_Loc(d);
		util = new SeleniumUtil();
		groupView = new Accounts_ViewByGroup_Loc(d);
		accountGroupSetting = new AccountGroup_Settings_Loc(d);
		addManualTransaction = new Add_Manual_Transaction_Loc(d);
		budgetSummary = new Budget_BudgetSummary_Loc(d);
		LoginPage.loginMain(d, loginParameter);
		accountAddition.linkAccount();
		Assert.assertTrue(
				accountAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("Budget_BudgetSummery_Data11_Site"),
						PropsUtil.getDataPropertyValue("Budget_BudgetSummery_Data11_Password")),
				"Aggregated account addition is not working");
		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	}

	@Test(description = "AT-108802:verify finapp header", priority = 1)
	public void verifyFiappHeader() {
		By finappHeader = SeleniumUtil.getByObject("Budget", null, "budget_Step1_FinappHeader");
		SeleniumUtil.waitUntilElementIsVisible(finappHeader, 60);
		String finapHeader = budgetCreateBudgetLoc.budget_Step1_FinappHeader().getText().trim();
		logger.info("Finapp Header" + finapHeader);
		Assert.assertEquals(finapHeader, PropsUtil.getDataPropertyValue("Budget_Step1_FinappHeader"),
				"Finapp header Budget is not displayed as expected");
	}

	@Test(description = "AT-108802:verify Budget step2 name instruction", priority = 2)
	public void verifySteps() {
		if (Config.appFlag.equalsIgnoreCase("false") || Config.appFlag.equalsIgnoreCase("web")) {
			Assert.assertEquals(budgetCreateBudgetLoc.budget_Step1_Instruction().getText(),
					PropsUtil.getDataPropertyValue("Budget_Step1_Instruction"),
					"Step 1 instruction is not dipslayed as expected");
		}
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Step1_InstructionInfo().getText(),
				PropsUtil.getDataPropertyValue("Budget_Step1_InstructionInfo"),
				"Step 1 instruction info is not dipslayed as expected");
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Getstart_Step1Name().getText(),
				PropsUtil.getDataPropertyValue("Budget_Step1_Stepname1"), "Step 1 name is not dipslayed as expected");
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Getstart_Step2Name().getText(),
				PropsUtil.getDataPropertyValue("Budget_Step1_Stepname2"), "Step 2 name is not dipslayed as expected");
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Getstart_Step1Info().getText(),
				PropsUtil.getDataPropertyValue("Budget_Step1_Stepname1Info"),
				"Step 1 info is not dipslayed as expected");
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Getstart_Step2Info().getText(),
				PropsUtil.getDataPropertyValue("Budget_Step1_Stepname2Info"),
				"Step 2 info is not dipslayed as expected");
		Assert.assertTrue(budgetCreateBudgetLoc.budget_Getstart_Step1Icon().isDisplayed(),
				"Step 1 icon is not displayed");
		Assert.assertTrue(budgetCreateBudgetLoc.budget_Getstart_Step2Icon().isDisplayed(),
				"Step 2 icon is not displayed");
	}

	@Test(description = "AT-108803,AT-108805:Info message in getstart page", priority = 3)
	public void verifyGetstartPageInfoMessage() {
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Getstart_InfoMesssage().getText(),
				PropsUtil.getDataPropertyValue("Budget_Step1_FooterInfo"),
				"step 1 footer info message is not dipslayed as expected");
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Getstart_GetStartButton().getText(),
				PropsUtil.getDataPropertyValue("Budget_Step1_Getstarted"),
				"Get Started text is not displayed as expected");
	}

	@Test(description = "AT-108806,AT-108807:verify Steps names", priority = 4)
	public void verifyAllTwoStepName() {
		budgetCreateBudgetLoc.clickGetStartButton();
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		logger.info("verify 1.Select account and second step is Setup Budget");
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			Assert.assertEquals(budgetCreateBudgetLoc.budget_ActiveStepsNameMobile().getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_Step1_ActiveStepMobile"), "");
			util.assertExpectedActualList(util.getWebElementsValue(budgetCreateBudgetLoc.budget_StepsNameListMobile()),
					PropsUtil.getDataPropertyValue("Budget_Step1_StepnameListMobile"),
					"Steps name select account and setup budget is not displaying");

		} else {
			Assert.assertEquals(budgetCreateBudgetLoc.budget_ActiveStepsName().getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_Step1_ActiveStep"), "");
			util.assertExpectedActualList(util.getWebElementsValue(budgetCreateBudgetLoc.budget_StepsNameList()),
					PropsUtil.getDataPropertyValue("Budget_Step1_StepnameList"),
					"Steps name select account and setup budget is not displaying");

		}
	}

	@Test(description = "AT-108806,AT-108808:verify Account message name Info in step1", priority = 5, dependsOnMethods = "verifyAllTwoStepName")
	public void VerifyStep1AcountInfoMessage() {
		String infoMessage = budgetCreateBudgetLoc.budget_Steps1_AccountInfoMessage().getText().trim();
		logger.info("info message in step1" + infoMessage);
		Assert.assertEquals(infoMessage, PropsUtil.getDataPropertyValue("Budget_Step1_InfoMessage"),
				"account info message is not displyed in step1");
	}

	@Test(description = "AT-108809:verify Account group dropdown in step1", priority = 6, dependsOnMethods = "verifyAllTwoStepName")
	public void verifyAccountgroupDropDown() {
		String defaultDroDownvalue = budgetCreateBudgetLoc.budget_Steps1_AccountGroupDropDown().getText().trim();
		logger.info("group dropdown default value in step1" + defaultDroDownvalue);
		Assert.assertEquals(defaultDroDownvalue, PropsUtil.getDataPropertyValue("Budget_Step1_AccountGroupDDValue"),
				"account group drodown is not displayed with create group text");
	}

	@Test(description = "AT-108824:verify next button should be displayed", priority = 7, dependsOnMethods = "verifyAllTwoStepName")
	public void verifyNextButtonDisabledInStep1() {
		Assert.assertTrue(budgetCreateBudgetLoc.budget_Steps1_NextButton().isDisplayed(),
				"disabled attribute is not displayed");
	}

	@Test(description = "AT-108812:verify budget group input field", priority = 8, dependsOnMethods = "verifyAllTwoStepName")
	public void verifybudgetGrpInputField() {
		logger.info("input field allow enetre value ");
		SeleniumUtil.sendKeys(budgetCreateBudgetLoc.budget_Steps1_BudgetNameField(),
				PropsUtil.getDataPropertyValue("Budget_Step1_HouseHoldingBudget"));
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps1_BudgetNameField().getAttribute("value").trim(),
				PropsUtil.getDataPropertyValue("Budget_Step1_HouseHoldingBudget"),
				"budget name field is not allow to enter data");
	}

	@Test(description = "AT-108813:verify budget group input field", priority = 9, dependsOnMethods = "verifyAllTwoStepName")
	public void verifybudgetGrpErrorMessage() {
		logger.info("verify all input field error message validation");
		String errorMessageInput[] = PropsUtil.getDataPropertyValue("Budget_Step1_ErrormessageInput").split(",");
		List<String> actualErrorMessage = new ArrayList<String>();
		for (int i = 0; i < errorMessageInput.length; i++) {
			budgetCreateBudgetLoc.sendKeys(budgetCreateBudgetLoc.budget_Steps1_BudgetNameField(), errorMessageInput[i]);
			actualErrorMessage.add(budgetCreateBudgetLoc.budget_Steps1_BudgetNameFieldErrorMsg().getText().trim());
		}
		util.assertExpectedActualList(actualErrorMessage,
				PropsUtil.getDataPropertyValue("Budget_Step1_NameErrormessage"),
				"Error message is not dispalyed properlly");
	}

	@Test(description = "AT-108813:verify budget group input field max char validation", priority = 10, dependsOnMethods = "verifyAllTwoStepName")
	public void verifybudgetNameFieldMaxValidation() {
		budgetCreateBudgetLoc.sendKeys(budgetCreateBudgetLoc.budget_Steps1_BudgetNameField(),
				PropsUtil.getDataPropertyValue("Budget_Step1_NameMaxCharInput"));
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps1_BudgetNameField().getAttribute("value").length(), 40,
				"budget name field is not allow to enter data only 40 char");

	}

	@Test(description = "verify account container name", priority = 11, dependsOnMethods = "verifyAllTwoStepName")
	public void verifySupportedContainerType() {
		Assert.assertEquals(
				budgetCreateBudgetLoc.budget_Steps1_AccountContainerName(
						PropsUtil.getDataPropertyValue("Budget_Step1CardContainerInput")).getText(),
				PropsUtil.getDataPropertyValue("Budget_Step1CardContainer"), "Cash container is not dispalyed");
		Assert.assertEquals(
				budgetCreateBudgetLoc.budget_Steps1_AccountContainerName(
						PropsUtil.getDataPropertyValue("Budget_Step1BankContainerInput")).getText(),
				PropsUtil.getDataPropertyValue("Budget_Step1BankContainer"), "card container is not dispalyed");
		Assert.assertEquals(
				budgetCreateBudgetLoc.budget_Steps1_AccountContainerName(
						PropsUtil.getDataPropertyValue("Budget_Step1InvestmentContainerInput")).getText(),
				PropsUtil.getDataPropertyValue("Budget_Step1InvestmentContainer"),
				"Investment container is not dispalyed");
	}

	@Test(description = "verify account name and account number", priority = 12, dependsOnMethods = "verifyAllTwoStepName")
	public void verifyAccountNameAndInstitute() {
		logger.info("intitution name validation");
		util.assertExpectedActualList(
				util.getWebElementsValue(budgetCreateBudgetLoc.budget_Steps1_AccountInstitutionName(
						PropsUtil.getDataPropertyValue("Budget_Step1CardContainerInput"))),
				PropsUtil.getDataPropertyValue("Budget_Step1CardContainerInst"),
				"Cash container intitution is not dispalyed");
		util.assertExpectedActualList(
				util.getWebElementsValue(budgetCreateBudgetLoc.budget_Steps1_AccountInstitutionName(
						PropsUtil.getDataPropertyValue("Budget_Step1BankContainerInput"))),
				PropsUtil.getDataPropertyValue("Budget_Step1BankContainerInst"),
				"card container intitution is not dispalyed");
		util.assertExpectedActualList(
				util.getWebElementsValue(budgetCreateBudgetLoc.budget_Steps1_AccountInstitutionName(
						PropsUtil.getDataPropertyValue("Budget_Step1InvestmentContainerInput"))),
				PropsUtil.getDataPropertyValue("Budget_Step1InvestmentContainerInst"),
				"Invetsment container intitution is not dispalyed");
		logger.info("account number validation");
		util.assertExpectedActualList(
				util.getWebElementsValue(budgetCreateBudgetLoc
						.budget_Steps1_AccountNumber(PropsUtil.getDataPropertyValue("Budget_Step1CardContainerInput"))),
				PropsUtil.getDataPropertyValue("Budget_Step1CardAccountNo"), "card account number is not dispalyed");

		util.assertExpectedActualList(
				util.getWebElementsValue(budgetCreateBudgetLoc.budget_Steps1_AccountNumber(
						PropsUtil.getDataPropertyValue("Budget_Step1InvestmentContainerInput"))),
				PropsUtil.getDataPropertyValue("Budget_Step1InvestmentAccountNo"),
				"Invetsment account number is not dispalyed");
		String[] cashAccount = PropsUtil.getDataPropertyValue("Budget_Step1BankAccountNo1").split(",");
		List<String> cashActualValue = new ArrayList<String>();
		cashActualValue = util.getWebElementsValue(budgetCreateBudgetLoc
				.budget_Steps1_AccountNumber(PropsUtil.getDataPropertyValue("Budget_Step1BankContainerInput")));
		for (int i = 0; i < cashAccount.length; i++) {
			util.isAnyMatchInList(cashActualValue, cashAccount[i]);
		}
	}

	@Test(description = "AT-108816,AT-108823:verify card account is selected by dafault", priority = 13, dependsOnMethods = "verifyAllTwoStepName")
	public void verifyCardAccountSelected() {
		util.assertActualListContains(
				util.getWebElementsAttributeName(budgetCreateBudgetLoc.budget_Steps1_AccountCheckBox(
						PropsUtil.getDataPropertyValue("Budget_Step1CardContainerInput")), "class"),
				PropsUtil.getDataPropertyValue("Budget_Step1_AccountSelected"), "card account is not selected");
	}

	@Test(description = "AT-108816,AT-108823:verify Cash account is selected by dafault", priority = 14, dependsOnMethods = "verifyAllTwoStepName")
	public void verifyCashAccountSelected() {
		util.assertActualListContains(
				util.getWebElementsAttributeName(budgetCreateBudgetLoc.budget_Steps1_AccountCheckBox(
						PropsUtil.getDataPropertyValue("Budget_Step1BankContainerInput")), "class"),
				PropsUtil.getDataPropertyValue("Budget_Step1_AccountSelected"), "cash account is not selected");
	}

	@Test(description = "AT-108816,AT-108823:verify invetsment account is not selected by dafault", priority = 15, dependsOnMethods = "verifyAllTwoStepName")
	public void verifyInvestmentAccountUnSelected() {
		util.assertActualListContains(
				util.getWebElementsAttributeName(budgetCreateBudgetLoc.budget_Steps1_AccountCheckBox(
						PropsUtil.getDataPropertyValue("Budget_Step1InvestmentContainerInput")), "class"),
				PropsUtil.getDataPropertyValue("Budget_Step1_AccountUnSelected"), "investment account is selected");

	}

	@Test(description = "AT-108817:verify card account able to select and unselect", priority = 16, dependsOnMethods = "verifyCardAccountSelected")
	public void verifyCardAccountSelectAndUnselect() {
		SeleniumUtil.click(budgetCreateBudgetLoc
				.budget_Steps1_AccountCheckBox(PropsUtil.getDataPropertyValue("Budget_Step1CardContainerInput"))
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))));
		String unselected = budgetCreateBudgetLoc
				.budget_Steps1_AccountCheckBox(PropsUtil.getDataPropertyValue("Budget_Step1CardContainerInput"))
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).getAttribute("class");
		SeleniumUtil.click(budgetCreateBudgetLoc
				.budget_Steps1_AccountCheckBox(PropsUtil.getDataPropertyValue("Budget_Step1CardContainerInput"))
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))));
		String selected = budgetCreateBudgetLoc
				.budget_Steps1_AccountCheckBox(PropsUtil.getDataPropertyValue("Budget_Step1CardContainerInput"))
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).getAttribute("class");
		Assert.assertTrue(unselected.contains(PropsUtil.getDataPropertyValue("Budget_Step1_AccountUnSelected")),
				"card account is not unselected");
		Assert.assertTrue(selected.contains(PropsUtil.getDataPropertyValue("Budget_Step1_AccountSelected")),
				"card account is not selected");
	}

	@Test(description = "AT-108817:verify cash account able to select and unselect", priority = 17, dependsOnMethods = "verifyCashAccountSelected")
	public void verifyCashAccountSelectAndUnselect() {
		SeleniumUtil.click(budgetCreateBudgetLoc
				.budget_Steps1_AccountCheckBox(PropsUtil.getDataPropertyValue("Budget_Step1BankContainerInput"))
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))));
		String unselected = budgetCreateBudgetLoc
				.budget_Steps1_AccountCheckBox(PropsUtil.getDataPropertyValue("Budget_Step1BankContainerInput"))
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).getAttribute("class");
		SeleniumUtil.click(budgetCreateBudgetLoc
				.budget_Steps1_AccountCheckBox(PropsUtil.getDataPropertyValue("Budget_Step1BankContainerInput"))
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))));
		String selected = budgetCreateBudgetLoc
				.budget_Steps1_AccountCheckBox(PropsUtil.getDataPropertyValue("Budget_Step1BankContainerInput"))
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).getAttribute("class");
		Assert.assertTrue(unselected.contains(PropsUtil.getDataPropertyValue("Budget_Step1_AccountUnSelected")),
				"cash account is not unselected");
		Assert.assertTrue(selected.contains(PropsUtil.getDataPropertyValue("Budget_Step1_AccountSelected")),
				"cash account is not selected");
	}

	@Test(description = "AT-108817:verify investment account able to select", priority = 18, dependsOnMethods = "verifyInvestmentAccountUnSelected")
	public void verifyInvetsmenthAccountSelect() {
		SeleniumUtil.click(budgetCreateBudgetLoc
				.budget_Steps1_AccountCheckBox(PropsUtil.getDataPropertyValue("Budget_Step1InvestmentContainerInput"))
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))));
		String selected = budgetCreateBudgetLoc
				.budget_Steps1_AccountCheckBox(PropsUtil.getDataPropertyValue("Budget_Step1InvestmentContainerInput"))
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).getAttribute("class");
		Assert.assertTrue(selected.contains(PropsUtil.getDataPropertyValue("Budget_Step1_AccountSelected")),
				"investment account is not selected");

	}

	@Test(description = "AT-108814,AT-108815:verify account container can collpase", priority = 19, dependsOnMethods = "verifyAllTwoStepName")
	public void verifyCollapseAccountsSection() {

		String accountcontainer[] = PropsUtil.getDataPropertyValue("Budget_Step1ContainerInputList").split(",");
		for (int i = 0; i < accountcontainer.length; i++) {
			SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps1_AccountExpand(accountcontainer[i]));
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
			Assert.assertTrue(
					budgetCreateBudgetLoc.budget_Steps1_AccountExpand(accountcontainer[i]).getAttribute("class")
							.contains(PropsUtil.getDataPropertyValue("Budget_Step1ContainerCollapse")),
					"accounts containers are not collapsed");
		}

	}

	@Test(description = "AT-108814,AT-108815:verify account container can expande", priority = 20, dependsOnMethods = "verifyCollapseAccountsSection")
	public void verifyExpandAccountsSection() {
		String accountcontainer[] = PropsUtil.getDataPropertyValue("Budget_Step1ContainerInputList").split(",");
		for (int i = 0; i < accountcontainer.length; i++) {
			SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps1_AccountExpand(accountcontainer[i]));
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
			Assert.assertTrue(
					budgetCreateBudgetLoc.budget_Steps1_AccountExpand(accountcontainer[i]).getAttribute("class")
							.contains(PropsUtil.getDataPropertyValue("Budget_Step1ContainerExpand")),
					"accounts containers are not collapsed");
		}

	}

	@Test(description = "AT-108823,AT-108825,AT-108826,AT-108941,AT-108942:verify step2 budget group name field and steps", priority = 21, dependsOnMethods = "verifyAllTwoStepName")
	public void verifyGoToStep2() {
		budgetCreateBudgetLoc.sendKeys(budgetCreateBudgetLoc.budget_Steps1_BudgetNameField(),
				PropsUtil.getDataPropertyValue("Budget_Step1_HouseHoldingBudget"));
		budgetCreateBudgetLoc.clickNextbutton();
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps1_SuccessMessage().getText(),
				PropsUtil.getDataPropertyValue("Budget_Step1_GroupCreatedMessage"),
				"budgetUpdated sucess message is displayed");
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_InstructionMobile().getText(),
					PropsUtil.getDataPropertyValue("Budget_Step2_Instruction"),
					"budget group name not displaye in step2");

		} else {
			Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_BudgetName().getText(),
					PropsUtil.getDataPropertyValue("Budget_Step1_HouseHoldingBudget"),
					"budget group name not displaye in step2");
			Assert.assertEquals(budgetCreateBudgetLoc.budget_ActiveStepsName().getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_Step1_ActiveStep2"),
					"step2 is not avtive when navigated to step2");
		}

	}

	@Test(description = "AT-108827:back to step1 from step2", priority = 22, dependsOnMethods = "verifyGoToStep2")
	public void verifyBackToStep1() {
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_BackButtonMobile());
		} else {
			budgetCreateBudgetLoc.clickBackButton();
		}
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps1_BudgetGroupName().getText(),
				PropsUtil.getDataPropertyValue("Budget_Step1_HouseHoldingBudget"),
				"created budget group name not displayed");
		Assert.assertTrue(budgetCreateBudgetLoc.budget_Steps1_AccountGroupDropDown().isDisplayed(),
				"group drodown not displayed");
	}

	@Test(description = "AT-108827,AT-108828,AT-108831:verify budget group edit option", priority = 23, dependsOnMethods = "verifyBackToStep1")
	public void verifyeditBudgetOptionStep1() {
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_EditMobile().getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_Step1_GroupEditLabelMobile"),
					"edit group label is not dspalyed");
		} else {
			Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps1_EditLabel().getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_Step1_GroupEditLabel"), "edit group label is not dspalyed");
		}
		Assert.assertTrue(budgetCreateBudgetLoc.budget_Steps1_EditButton().isDisplayed(),
				"edit group icon not displayed");
		SeleniumUtil.waitFor(2);
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps1_EditButton());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_PopupCloseIcon());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.waitFor(5);
		Assert.assertTrue(!SeleniumUtil.isDisplayed(budgetCreateBudgetLoc.popupHeader, 2),
				"popup is not getting closed");
	}

	@Test(description = "AT-108827:verify budget account after navigated from step2", priority = 24, dependsOnMethods = "verifyBackToStep1")
	public void verifyAccountNameAndInstituteBack() {

		logger.info("intitution name validation");
		util.assertExpectedActualList(
				util.getWebElementsValue(budgetCreateBudgetLoc.budget_Steps1_AccountInstitutionName(
						PropsUtil.getDataPropertyValue("Budget_Step1CardContainerInput"))),
				PropsUtil.getDataPropertyValue("Budget_Step1CardContainerInst"),
				"card container intitution is not dispalyed");
		util.assertExpectedActualList(
				util.getWebElementsValue(budgetCreateBudgetLoc.budget_Steps1_AccountInstitutionName(
						PropsUtil.getDataPropertyValue("Budget_Step1BankContainerInput"))),
				PropsUtil.getDataPropertyValue("Budget_Step1BankContainerInst"),
				"cash container intitution is not dispalyed");
		util.assertExpectedActualList(
				util.getWebElementsValue(budgetCreateBudgetLoc.budget_Steps1_AccountInstitutionName(
						PropsUtil.getDataPropertyValue("Budget_Step1InvestmentContainerInput"))),
				PropsUtil.getDataPropertyValue("Budget_Step1InvestmentContainerInst"),
				"Invetsment container intitution is not dispalyed");
		logger.info("account number validation");
		util.assertExpectedActualList(
				util.getWebElementsValue(budgetCreateBudgetLoc
						.budget_Steps1_AccountNumber(PropsUtil.getDataPropertyValue("Budget_Step1CardContainerInput"))),
				PropsUtil.getDataPropertyValue("Budget_Step1CardAccountNo"), "card account number is not dispalyed");

		util.assertExpectedActualList(
				util.getWebElementsValue(budgetCreateBudgetLoc.budget_Steps1_AccountNumber(
						PropsUtil.getDataPropertyValue("Budget_Step1InvestmentContainerInput"))),
				PropsUtil.getDataPropertyValue("Budget_Step1InvestmentAccountNo"),
				"Invetsment account number is not dispalyed");
		String[] cashAccount = PropsUtil.getDataPropertyValue("Budget_Step1BankAccountNo1").split(",");
		List<String> cashActualValue = new ArrayList<String>();
		cashActualValue = util.getWebElementsValue(budgetCreateBudgetLoc
				.budget_Steps1_AccountNumber(PropsUtil.getDataPropertyValue("Budget_Step1BankContainerInput")));
		for (int i = 0; i < cashAccount.length; i++) {
			util.isAnyMatchInList(cashActualValue, cashAccount[i]);
		}
	}

	@Test(description = "AT-108833,AT-108827,AT-108829,AT-108830:verify budget group name upated message", priority = 25, dependsOnMethods = "verifyBackToStep1")
	public void verifyeditBudgetGroup() {
		budgetCreateBudgetLoc.clickEditButton();
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		groupView.createGroup(PropsUtil.getDataPropertyValue("Budget_Step1_HouseHoldingBudgetUpdated"),
				PropsUtil.getDataPropertyValue("Budget_Step1_HouseHoldingBudgetUpdatedAcc").split(","));
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps1_SuccessMessage().getText(),
				PropsUtil.getDataPropertyValue("Budget_Step1_HouseHoldingBudgetUpdatedSuccess"),
				"budgetUpdated sucess message is displayed");
		SeleniumUtil.waitUntilToastMessageIsDisappeared();
		SeleniumUtil.waitFor(3);
	}

	@Test(description = "AT-108832:verify budget account after edit budget group", priority = 26, dependsOnMethods = "verifyeditBudgetGroup")
	public void verifyeditedBudgetGroup() {
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps1_BudgetGroupName().getText(),
				PropsUtil.getDataPropertyValue("Budget_Step1_HouseHoldingBudgetUpdated"),
				"created budget group name not displayed");
		logger.info("intitution name validation");
		util.assertExpectedActualList(
				util.getWebElementsValue(budgetCreateBudgetLoc.budget_Steps1_AccountInstitutionName(
						PropsUtil.getDataPropertyValue("Budget_Step1CardContainerInput"))),
				PropsUtil.getDataPropertyValue("Budget_Step1CardContainerInst"),
				"card container intitution is not dispalyed");
		util.assertExpectedActualList(
				util.getWebElementsValue(budgetCreateBudgetLoc.budget_Steps1_AccountInstitutionName(
						PropsUtil.getDataPropertyValue("Budget_Step1BankContainerInput"))),
				PropsUtil.getDataPropertyValue("Budget_Step1BankContainerInst"),
				"bank container intitution is not dispalyed");

		logger.info("account number validation");
		util.assertExpectedActualList(
				util.getWebElementsValue(budgetCreateBudgetLoc
						.budget_Steps1_AccountNumber(PropsUtil.getDataPropertyValue("Budget_Step1CardContainerInput"))),
				PropsUtil.getDataPropertyValue("Budget_Step1CardAccountNo"), "Cash account number is not dispalyed");
		String[] cashAccount = PropsUtil.getDataPropertyValue("Budget_Step1BankAccountNo1").split(",");
		List<String> cashActualValue = new ArrayList<String>();
		cashActualValue = util.getWebElementsValue(budgetCreateBudgetLoc
				.budget_Steps1_AccountNumber(PropsUtil.getDataPropertyValue("Budget_Step1BankContainerInput")));
		for (int i = 0; i < cashAccount.length; i++) {
			util.isAnyMatchInList(cashActualValue, cashAccount[i]);
		}

	}

	@Test(description = "AT-108943:verify budget step2 info message", priority = 27, dependsOnMethods = "verifyeditBudgetGroup")
	public void verifyStep2BudgetInfoMessage() {
		budgetCreateBudgetLoc.clickNextbutton();
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {

			Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_InstructionInfoMobile().getText(),
					PropsUtil.getDataPropertyValue("Budget_Step2_InstructionInfo"),
					"budget step2 info message is not displayed");
		} else {
			Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_BudgetInfoMessage().getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_Step2_InfoMessage"),
					"budget step2 info message is not displayed");
		}
	}

	@Test(description = "AT-108881:verify budget lrft over amount", priority = 28, dependsOnMethods = "verifyStep2BudgetInfoMessage")
	public void verifyLeftOverAmount() {
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_LeftOverLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_LeftOverLabel"), "left over label is not displayed");
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_LeftOverAmount().getText().replace("$", "").trim(),
				budgetCreateBudgetLoc.calculateLeftOver().trim(), "left over amount is not displayed");
	}

	@Test(description = "AT-108881:verify category labels are  displayed", priority = 29, dependsOnMethods = "verifyStep2BudgetInfoMessage")
	public void verifyCategoryTypeLabel() {
		util.assertExpectedActualList(util.getWebElementsValue(budgetCreateBudgetLoc.budget_Steps2_CategoryTypeLabel()),
				PropsUtil.getDataPropertyValue("Budget_Step2_CategorySectionNameList"),
				"all category types are not displayed as expected");
	}

	/*
	 * Disabling this testcase becuase sub-categories under the income header is
	 * restricted to just one. So, checkbox will not be displayed
	 */
	@Test(description = "AT-108880:verify income category label header", priority = 30, dependsOnMethods = "verifyStep2BudgetInfoMessage", enabled = false)
	public void verifyIncomeCategoryTableHeader() {
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			Assert.assertTrue(
					budgetCreateBudgetLoc.budget_Steps2_CategorySelectAllCheckBox(
							PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput")).isDisplayed(),
					"select all check box is not displayed");
			Assert.assertEquals(
					budgetCreateBudgetLoc.budget_Steps2_CategorySelectAllLabelMobile(
							PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput")).getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_Step2_CategorySelectAllLblMobile"),
					"select all  label is not dsplayed");
			Assert.assertEquals(
					budgetCreateBudgetLoc.budget_Steps2_CategoryMonthyBudgetLabelMobile(
							PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput")).getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_Step2_CategorymonthlybdgLblMobile"),
					" budgeted label is not dsplayed");

		} else {
			Assert.assertTrue(
					budgetCreateBudgetLoc.budget_Steps2_CategorySelectAllCheckBox(
							PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput")).isDisplayed(),
					"select all check box is not displayed");
			Assert.assertEquals(budgetCreateBudgetLoc
					.budget_Steps2_CategorySelectAllLabel(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))
					.getText().trim(), PropsUtil.getDataPropertyValue("Budget_Step2_CategorySelectAllLbl"),
					"select  label is not dsplayed");
			Assert.assertEquals(budgetCreateBudgetLoc
					.budget_Steps2_CategoryMonthyAvgLabel(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))
					.getText().trim(), PropsUtil.getDataPropertyValue("Budget_Step2_Category3mthAvgLbl"),
					"3 month avg label is not dsplayed");
			Assert.assertEquals(budgetCreateBudgetLoc
					.budget_Steps2_CategoryMonthyBudgetLabel(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))
					.getText().trim(), PropsUtil.getDataPropertyValue("Budget_Step2_CategorymonthlybdgLbl"),
					" budgeted label is not dsplayed");
		}
	}

	@Test(description = "AT-108906:verify budget expand and collapse category section by deafault", priority = 30, dependsOnMethods = "verifyStep2BudgetInfoMessage")
	public void verifycategoryDefaultExpand() {
		Assert.assertTrue(
				budgetCreateBudgetLoc
						.budget_Steps2_CategoryTypeExpand(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))
						.getAttribute("class").contains(PropsUtil.getDataPropertyValue("Budget_Step1ContainerExpand")),
				"income section is not expanded");
		Assert.assertTrue(budgetCreateBudgetLoc
				.budget_Steps2_CategoryTypeExpand(PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))
				.getAttribute("class").contains(PropsUtil.getDataPropertyValue("Budget_Step1ContainerCollapse")),
				"bill section is expanded");
		Assert.assertTrue(budgetCreateBudgetLoc
				.budget_Steps2_CategoryTypeExpand(PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"))
				.getAttribute("class").contains(PropsUtil.getDataPropertyValue("Budget_Step1ContainerCollapse")),
				"flexible spending section is expanded");
	}

	@Test(description = "AT-108875:verify income section all categories check boxes are displayed", priority = 31, dependsOnMethods = "verifyStep2BudgetInfoMessage")
	public void verifyIncomeCategoryAllCheckBox() {
		util.assertIsDisplayed(budgetCreateBudgetLoc.budget_Steps2_CategorySelectCheckBox(
				PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput")), "check box is not dsplayed");
	}

	@Test(description = "AT-108875,AT-108877,AT-108876:verify income section all category", priority = 32, dependsOnMethods = "verifyStep2BudgetInfoMessage")
	public void verifyIncomeCategoryName() {
		if (Config.appFlag.equalsIgnoreCase("false") || Config.appFlag.equalsIgnoreCase("web")) {
			util.assertIsDisplayed(
					budgetCreateBudgetLoc
							.budget_Steps2_CategoryIcon(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput")),
					"category icon is not dsplayed");
		}
		util.assertExpectedActualList(
				util.getWebElementsValue(budgetCreateBudgetLoc
						.budget_Steps2_CategoryName(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))),
				PropsUtil.getDataPropertyValue("Budget_Step2_IncomeCategoryName"),
				"all catgeory name is not dispalyed");
	}

	@Test(description = "AT-108878:verify income category section 3 month avg amount ", priority = 33, dependsOnMethods = "verifyStep2BudgetInfoMessage")
	public void verifyIncomeCategory3monthAvg() {
		if (Config.appFlag.equalsIgnoreCase("web") || Config.appFlag.equalsIgnoreCase("false")) {
			util.assertExpectedActualAmountList(
					util.getWebElementsValue(budgetCreateBudgetLoc.budget_Steps2_CategoryMonthlyAgAmount(
							PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeCategoryAvgAmt"),
					"all catgeory 3 month avg amount is not dispalyed");
		}
	}

	@Test(description = "AT-108878,AT-108936:verify income category section all budgeted amount", priority = 34, dependsOnMethods = "verifyStep2BudgetInfoMessage")
	public void verifyIncomeCategoryBudgetedAmount() {
		util.assertExpectedActualAmountList(
				util.getWebElementsAttributeValue(budgetCreateBudgetLoc.budget_Steps2_CategoryMonthlyBudgetAmt(
						PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))),
				PropsUtil.getDataPropertyValue("Budget_Step2_IncomeCategoryBdgtAmt"),
				"all catgeory budgeted amount is not dispalyed");

	}

	@Test(description = "AT-108888:verify income category section total budgeted amount", priority = 35, dependsOnMethods = "verifyStep2BudgetInfoMessage")
	public void verifyIncomeCategoryTotalAmountAmount() {
		Assert.assertEquals(
				budgetCreateBudgetLoc
						.budget_Steps2_CategoryTypeAmount(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))
						.getText().trim(),
				"$" + budgetCreateBudgetLoc
						.calculateTotalCatAmount(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput")),
				"income category total budgeted amount is not displayed");
	}

	@Test(description = "AT-108895:verify income category section add category option", priority = 36, dependsOnMethods = "verifyStep2BudgetInfoMessage")
	public void verifyIncomeAddCategory() {
		Assert.assertTrue(budgetCreateBudgetLoc
				.budget_Steps2_AddCategoryIcon(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))
				.isDisplayed(), "add income category icon is not diplsayed");
		Assert.assertEquals(
				budgetCreateBudgetLoc
						.budget_Steps2_AddCategoryLbl(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))
						.getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategory"),
				"add income catgeory label is not displayed");
	}

	@Test(description = "AT-108897,AT-108901,AT-108940:verify add catgeory click and verify add catgeory fields", priority = 37, dependsOnMethods = "verifyIncomeAddCategory")
	public void verifyIncomeAddCategoryClick() {
		SeleniumUtil.click(budgetCreateBudgetLoc
				.budget_Steps2_AddCategoryIcon(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput")));
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			Assert.assertTrue(budgetCreateBudgetLoc.budget_Steps2_AddCategoryCloseIconMobile().isDisplayed());
			Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_LeftOverLabelMobile().getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_Step2_LeftOverLabel"), "left over label is not displayed");
			Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_LeftOverAmountMobile().getText(),
					PropsUtil.getDataPropertyValue("Budget_Step2_LeftOverMobileAmount"),
					"left over amount is not displayed");
			Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_AddCategoryCurrencyIconMobile().getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategoryCurrencyIcon"),
					"currency icon is not displayed");

		} else {
			Assert.assertTrue(budgetCreateBudgetLoc.budget_Steps2_AddCategoryCloseIcon().isDisplayed());
			Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_AddCategoryCurrencyIcon().getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategoryCurrencyIcon"),
					"currency icon is not displayed");

		}
		Assert.assertTrue(budgetCreateBudgetLoc.budget_Steps2_AddCategoryDDIcon().isDisplayed(),
				"add categroy dropdown icon is not displayed");
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_AddCategoryDD().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategorySelection"),
				"add category drodown is not selected value");
		Assert.assertTrue(budgetCreateBudgetLoc.budget_Steps2_AddCategoryAddButton().isDisplayed());
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_AddCategoryAmount().getAttribute("value").trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategoryCurrencyAmt"),
				"catgeory amount is not displayed");

	}

	@Test(description = "AT-108896:verify income section add catgeory legends", priority = 38, dependsOnMethods = "verifyIncomeAddCategoryClick")
	public void verifyIncomeAddCategoryLegends() {
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			util.assertExpectedActualList(
					util.getWebElementsValueUperCase(budgetCreateBudgetLoc.budget_Steps2_legendMonthLinkMobile()),
					util.revereseList(budgetCreateBudgetLoc.xAixValue(4)), "legend months are not displayed");

		}
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_legend3monthAvg().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategoryLegendsAvg"),
				"last 3 mnth avg is not displayed");
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_legendcatTypeLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategoryLegendCategory"),
				"legends catgeory sections are not displayed");
		Assert.assertTrue(budgetCreateBudgetLoc.budget_Steps2_legendcatTypeLabelcolor().isDisplayed(),
				"category section color is not displayed");
		util.assertExpectedActualList(util.getWebElementsValue(budgetCreateBudgetLoc.budget_Steps2_legendXaxis()),
				util.revereseList(budgetCreateBudgetLoc.xAixValue(4)), "x axix months are not displayed");
		util.assertExpectedActualList(util.getWebElementsValue(budgetCreateBudgetLoc.budget_Steps2_legendYaxis()),
				PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategoryLegendYaxix"),
				"y axix values are not displayed");

	}

	@Test(description = "AT-108898:verify income addcatgeory section id hided", priority = 39, dependsOnMethods = "verifyIncomeAddCategoryClick")
	public void verifyIncomeAddCategoryHide() {
		if (Config.appFlag.equalsIgnoreCase("web") || Config.appFlag.equalsIgnoreCase("false")) {
			Assert.assertTrue(
					budgetCreateBudgetLoc
							.budget_Steps2_AddCategoryHide(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))
							.getAttribute("class")
							.contains(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategoryHide")),
					"income add catgeory option is not hidden");
		}
	}

	@Test(description = "AT-108900:verify remaing income category name", priority = 40, dependsOnMethods = "verifyIncomeAddCategoryClick")
	public void verifyIncomeremaing() {
		SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_AddCategoryDDIcon());
		util.assertExpectedActualList(
				util.getWebElementsValue(budgetCreateBudgetLoc.budget_Steps2_addCatDdCategoryList()),
				PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategoryRemain"),
				"all catgeory name is not dispalyed");

	}

	@Test(description = "AT-108895:verify add one income category", priority = 41, dependsOnMethods = "verifyIncomeremaing")
	public void verifyIncomeAddCategoryAmount() {
		SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_addCatDdCategoryName(
				PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategoryName")));
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		budgetCreateBudgetLoc.budget_Steps2_AddCategoryAmount().clear();
		budgetCreateBudgetLoc.budget_Steps2_AddCategoryAmount()
				.sendKeys(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategoryAmt"));
		SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_AddCategoryAddButton());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		int catgeoryIndex = budgetCreateBudgetLoc
				.budget_Steps2_CategoryName(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput")).size();
		Assert.assertEquals(
				budgetCreateBudgetLoc
						.budget_Steps2_CategoryName(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))
						.get(catgeoryIndex - 1).getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategoryName"),
				"Selected catgeory name is not displayed");
		Assert.assertEquals(
				budgetCreateBudgetLoc
						.budget_Steps2_CategoryMonthlyBudgetAmt(
								PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))
						.get(catgeoryIndex - 1).getAttribute("value").trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategoryAmtadded"),
				"added amount is not displayed");
		Assert.assertTrue(
				budgetCreateBudgetLoc
						.budget_Steps2_CategorySelectCheckBox(
								PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))
						.get(catgeoryIndex - 1).getAttribute("class").trim()
						.contains(PropsUtil.getDataPropertyValue("Budget_Step1_AccountSelected")),
				"check box is not selected");
	}

	@Test(description = "AT-108895:verify budget left over amount after add inocme categoyy", priority = 42, dependsOnMethods = "verifyIncomeAddCategoryAmount")
	public void verifyCalculationAfterAddCategory() {
		Assert.assertEquals(
				budgetCreateBudgetLoc
						.budget_Steps2_CategoryTypeAmount(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))
						.getText().trim(),
				"$" + budgetCreateBudgetLoc
						.calculateTotalCatAmount(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput")),
				"income catgeory budgeted amount is correct");
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_LeftOverAmount().getText().replace("$", "").trim(),
				budgetCreateBudgetLoc.calculateLeftOver().trim(), "total left over amount is correct");

	}

	@Test(description = "AT-108890,AT-108938:verify income category legends", priority = 43, dependsOnMethods = "verifyStep2BudgetInfoMessage")
	public void verifyIncomeCategoryLegends() {
		SeleniumUtil.click(budgetCreateBudgetLoc
				.budget_Steps2_CategoryDetailsExpand(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))
				.get(0));
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_legend3monthAvg().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_IncomeMonthlyAvg"), " 3 months avg is not displayed");
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_legendcatTypeLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategoryLegendCategory"),
				"legends category section is not displayed");
		Assert.assertTrue(budgetCreateBudgetLoc.budget_Steps2_legendcatTypeLabelcolor().isDisplayed(),
				"catgeory section color is not dipslayed");
		util.assertExpectedActualList(util.getWebElementsValue(budgetCreateBudgetLoc.budget_Steps2_legendXaxis()),
				util.revereseList(budgetCreateBudgetLoc.xAixValue(4)), "X axix months is not displayed");
		util.assertExpectedActualAmountList(util.getWebElementsValue(budgetCreateBudgetLoc.budget_Steps2_legendYaxis()),
				PropsUtil.getDataPropertyValue("Budget_Step2_IncomeMonthlyYaxix"), "y axix amount is not displayed");

	}

	@Test(description = "AT-108891,AT-108902,AT-110285,AT-110286:verify income catgeoy legend transaction", priority = 44, dependsOnMethods = "verifyIncomeCategoryLegends")
	public void verifyIncomeCategoryTxn() {
		Assert.assertEquals(
				budgetCreateBudgetLoc.budget_Steps2_TxnDateHeader()
						.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).getText().trim(),
				budgetCreateBudgetLoc.DateInMMMMFormate(budgetCreateBudgetLoc.getNoDaysOfMonth(-1)).trim(), "");
		Assert.assertEquals(
				budgetCreateBudgetLoc.budget_Steps2_TxnCategory()
						.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_IncomeMonthlytxnCategory"),
				"transaction description not dsipalyed");
		if (Config.appFlag.equalsIgnoreCase("web") || Config.appFlag.equalsIgnoreCase("false")) {
			Assert.assertEquals(
					budgetCreateBudgetLoc.budget_Steps2_TxnAccountName()
							.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeMonthlytxnBankName"),
					"transaction bank name is not displayed");
			Assert.assertEquals(
					budgetCreateBudgetLoc.budget_Steps2_TxnAccountName()
							.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexOne"))).getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeMonthlytxnBankNumber"),
					"transaction bank number is not displayed");

		}
		Assert.assertEquals(
				budgetCreateBudgetLoc.budget_Steps2_TxnAmount()
						.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_IncomeMonthlytxnBankAmount"),
				"transaction amount is not displayed");

	}

	@Test(description = "AT-108892,AT-108902:verify previous months transactions details", priority = 45, dependsOnMethods = "verifyIncomeCategoryLegends")
	public void verifyIncomeCategoryTxnPreviousMonth() {
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_legendMonthLinkMobile()
					.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexOne"))));
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		} else {
			SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_legendBarClick()
					.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexOne"))));
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
			Assert.assertEquals(
					budgetCreateBudgetLoc.budget_Steps2_TxnAccountName()
							.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeMonthlytxnBankName"),
					"transaction bank name is not dispalyed");
			Assert.assertEquals(
					budgetCreateBudgetLoc.budget_Steps2_TxnAccountName()
							.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexOne"))).getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeMonthlytxnBankNumber"),
					"transaction bank number is not displayed");

		}
		Assert.assertEquals(
				budgetCreateBudgetLoc.budget_Steps2_TxnDateHeader()
						.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).getText().trim(),
				budgetCreateBudgetLoc.DateInMMMMFormate(-(GenericUtil.getNumberOfDaysInPreviousMonth(1)
						+ GenericUtil.getNumberOfDaysInPreviousMonth(2))).trim(),
				" transaction date header not displayed ");
		Assert.assertEquals(
				budgetCreateBudgetLoc.budget_Steps2_TxnCategory()
						.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_IncomeMonthlytxnCategory"),
				"transaction description is not displayed");
		Assert.assertEquals(
				budgetCreateBudgetLoc.budget_Steps2_TxnAmount()
						.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_IncomeMonthlytxnBankAmount"),
				"transaction amount is  not displayed");

	}

	@Test(description = "AT-108889:verify income catgeory section amount error message", priority = 46, dependsOnMethods = "verifyIncomeCategoryLegends")
	public void verifyIncomeCategoryAmtErrorMessage() {
		String aphanumaric = null;
		String Emptyamt = null;
		String morethan11char = null;
		String decimalErr = null;
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			budgetCreateBudgetLoc.sendKeys(budgetCreateBudgetLoc.budget_Steps2_CategoryMonthlyBudgetAmtMobile(),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeBdgAmtAlphaNumeric"));
			SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_AddCategoryAddButton());
			aphanumaric = budgetCreateBudgetLoc.budget_Steps2_BdgAmtErrMsgMobile().getText().trim();
			budgetCreateBudgetLoc.sendKeys(budgetCreateBudgetLoc.budget_Steps2_CategoryMonthlyBudgetAmtMobile(),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeBdgAmtEmpty"));
			SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_AddCategoryAddButton());
			Emptyamt = budgetCreateBudgetLoc.budget_Steps2_BdgAmtErrMsgMobile().getText().trim();
			budgetCreateBudgetLoc.sendKeys(budgetCreateBudgetLoc.budget_Steps2_CategoryMonthlyBudgetAmtMobile(),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeBdgAmtmorethan11char"));
			SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_AddCategoryAddButton());
			morethan11char = budgetCreateBudgetLoc.budget_Steps2_BdgAmtErrMsgMobile().getText().trim();
			budgetCreateBudgetLoc.sendKeys(budgetCreateBudgetLoc.budget_Steps2_CategoryMonthlyBudgetAmtMobile(),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeBdgAmtDecimal"));
			SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_AddCategoryAddButton());
			decimalErr = budgetCreateBudgetLoc.budget_Steps2_BdgAmtErrMsgMobile().getText().trim();

		} else {
			budgetCreateBudgetLoc.sendKeys(
					budgetCreateBudgetLoc
							.budget_Steps2_CategoryMonthlyBudgetAmt(
									PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))
							.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeBdgAmtAlphaNumeric"));
			aphanumaric = budgetCreateBudgetLoc
					.budget_Steps2_BdgAmtErrMsg(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))
					.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).getText().trim();
			budgetCreateBudgetLoc.sendKeys(
					budgetCreateBudgetLoc
							.budget_Steps2_CategoryMonthlyBudgetAmt(
									PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))
							.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeBdgAmtEmpty"));
			Emptyamt = budgetCreateBudgetLoc
					.budget_Steps2_BdgAmtErrMsg(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))
					.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).getText().trim();
			budgetCreateBudgetLoc.sendKeys(
					budgetCreateBudgetLoc
							.budget_Steps2_CategoryMonthlyBudgetAmt(
									PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))
							.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeBdgAmtmorethan11char"));
			morethan11char = budgetCreateBudgetLoc
					.budget_Steps2_BdgAmtErrMsg(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))
					.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).getText().trim();
			budgetCreateBudgetLoc.sendKeys(
					budgetCreateBudgetLoc
							.budget_Steps2_CategoryMonthlyBudgetAmt(
									PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))
							.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeBdgAmtDecimal"));
			decimalErr = budgetCreateBudgetLoc
					.budget_Steps2_BdgAmtErrMsg(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))
					.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).getText().trim();
		}
		Assert.assertEquals(aphanumaric, PropsUtil.getDataPropertyValue("Budget_Step2_IncomeBdgAmtAlphaNumericErr"),
				"alpha numeric messgae is not displayed");
		Assert.assertEquals(Emptyamt, PropsUtil.getDataPropertyValue("Budget_Step2_IncomeBdgAmtEmptyErr"),
				"empty error message is not displayed");
		Assert.assertEquals(morethan11char,
				PropsUtil.getDataPropertyValue("Budget_Step2_IncomeBdgAmtmorethan11charErr"),
				"11 char message is not dispalyed");
		Assert.assertEquals(decimalErr, PropsUtil.getDataPropertyValue("Budget_Step2_IncomeBdgAmtDecimalErr"),
				"4 decimal message is not displayed");

	}

	@Test(description = "AT-108889,AT-108939:verify income catgeory amount edited", priority = 47, dependsOnMethods = "verifyIncomeCategoryLegends")
	public void verifyIncomeCategoryAmtedit() {
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			budgetCreateBudgetLoc.sendKeys(budgetCreateBudgetLoc.budget_Steps2_CategoryMonthlyBudgetAmtMobile(),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeBdgAmtEdit"));
			SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_AddCategoryAddButton());
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		} else {
			budgetCreateBudgetLoc.sendKeys(
					budgetCreateBudgetLoc
							.budget_Steps2_CategoryMonthlyBudgetAmt(
									PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))
							.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeBdgAmtEdit"));
		}
		Assert.assertEquals(
				budgetCreateBudgetLoc
						.budget_Steps2_CategoryTypeAmount(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))
						.getText().trim(),
				"$" + budgetCreateBudgetLoc
						.calculateTotalCatAmount(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput")),
				"income catgeory total budgeted amount");
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_LeftOverAmount().getText(),
				"$" + budgetCreateBudgetLoc.calculateLeftOver(), "total left ovet amount");

	}

	@Test(description = "AT-108899:verify all income remaing category added", priority = 48, dependsOnMethods = "verifyIncomeCategoryAmtedit")
	public void verifyaddAllIncome() {
		String[] amountinput = PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAllAlcategoryInput").split(",");
		for (int i = 0; i < 5; i++) {
			SeleniumUtil.click(budgetCreateBudgetLoc
					.budget_Steps2_AddCategoryIcon(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput")));
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
			SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_AddCategoryDDIcon());
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
			SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_addCatDdCategoryList()
					.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))));
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
			budgetCreateBudgetLoc.budget_Steps2_AddCategoryAmount().clear();
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
			budgetCreateBudgetLoc.budget_Steps2_AddCategoryAmount().sendKeys(amountinput[i]);
			SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_AddCategoryAddButton());
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		}
		SeleniumUtil.waitFor(3);
		Assert.assertFalse(SeleniumUtil.isDisplayed(budgetCreateBudgetLoc.budget_Steps2_AddCategoryIcon(), 2),
				"Not all income categories are added, Add income category icon is still displayed");

	}

	@Test(description = "AT-108883:verify default income catgeory is empty", priority = 49, dependsOnMethods = "verifyaddAllIncome")
	public void verifyDafalutIncomeCategory() {
		if (Config.appFlag.equalsIgnoreCase("web") || Config.appFlag.equalsIgnoreCase("false")) {
			List<String> amount = new ArrayList<String>();
			int catgeoryIndex = budgetCreateBudgetLoc
					.budget_Steps2_CategoryName(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput")).size();
			for (int i = 0; i < catgeoryIndex; i++) {
				if (budgetCreateBudgetLoc
						.budget_Steps2_CategoryName(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput")).get(i)
						.getText().trim().equals(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeDefaultCategory1"))
						|| budgetCreateBudgetLoc
								.budget_Steps2_CategoryName(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))
								.get(i).getText().trim()
								.equals(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeDefaultCategory2"))) {
					Assert.assertEquals(
							budgetCreateBudgetLoc
									.budget_Steps2_CategoryMonthlyBudgetAmt(
											PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))
									.get(i).getAttribute("value").trim(),
							PropsUtil.getDataPropertyValue("Budget_Step2_IncomeDefaultBudgetAmount"), "");
					amount.add(
							budgetCreateBudgetLoc
									.budget_Steps2_CategoryMonthlyAgAmount(
											PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))
									.get(i).getText());
				}
			}
			util.assertExpectedActualAmountList(amount,
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeDefaultAvgAmount"),
					"income default catgeory is not displayed");
		}
	}

	@Test(description = "AT-108884:verify default catgeory error message", priority = 50, dependsOnMethods = "verifyDafalutIncomeCategory")
	public void verifyDafalutIncomeCategoryError() {

		int catgeoryIndex = budgetCreateBudgetLoc
				.budget_Steps2_CategoryName(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput")).size();
		for (int i = 0; i < catgeoryIndex; i++) {
			if (budgetCreateBudgetLoc
					.budget_Steps2_CategoryName(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput")).get(i)
					.getText().trim().equals(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeDefaultCategory1"))) {
				SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_CategorySelectCheckBox(
						PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput")).get(i));
				break;
			}

		}
		String decimalErr = budgetCreateBudgetLoc
				.budget_Steps2_BdgAmtErrMsg(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput")).get(4).getText()
				.trim();

		Assert.assertEquals(decimalErr, PropsUtil.getDataPropertyValue("Budget_Step2_IncomeBdgAmtGreaterThanZero"),
				"erorr message is not displayed");

	}

	@Test(description = "AT-108903:verify done button and back button", priority = 51, dependsOnMethods = "verifyStep2BudgetInfoMessage")
	public void verifyStep2DoneButton() {
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_DoneButton().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_DoneLabel"), "done button is not displayed");
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			Assert.assertTrue(budgetCreateBudgetLoc.budget_Steps2_BackButtonMobile().isDisplayed(),
					"back button is not displayed");
		} else {
			Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_BackButton().getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_Step2_BackLabel"), "back button is not displayed");
		}
	}

	@Test(description = "AT-108880:verify bill category table header is not displayed", priority = 52, dependsOnMethods = "verifyStep2BudgetInfoMessage")
	public void verifyBillsCategoryTableHeader() {
		SeleniumUtil.click(budgetCreateBudgetLoc
				.budget_Steps2_CategoryTypeExpand(PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput")));
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			Assert.assertTrue(
					budgetCreateBudgetLoc.budget_Steps2_CategorySelectAllCheckBox(
							PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput")).isDisplayed(),
					"select all check box is not displayed");
			Assert.assertEquals(
					budgetCreateBudgetLoc.budget_Steps2_CategorySelectAllLabelMobile(
							PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput")).getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_Step2_CategorySelectAllLblMobile"),
					"select all label is not dsplayed");
			Assert.assertEquals(
					budgetCreateBudgetLoc.budget_Steps2_CategoryMonthyBudgetLabelMobile(
							PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput")).getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_Step2_CategorymonthlybdgLblMobile"),
					" budgeted label is not dsplayed");

		} else {
			Assert.assertTrue(
					budgetCreateBudgetLoc.budget_Steps2_CategorySelectAllCheckBox(
							PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput")).isDisplayed(),
					"select all check box is not displayed");
			Assert.assertEquals(budgetCreateBudgetLoc
					.budget_Steps2_CategorySelectAllLabel(PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))
					.getText().trim(), PropsUtil.getDataPropertyValue("Budget_Step2_CategorySelectAllLbl"),
					"select  label is not dsplayed");
			Assert.assertEquals(budgetCreateBudgetLoc
					.budget_Steps2_CategoryMonthyAvgLabel(PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))
					.getText().trim(), PropsUtil.getDataPropertyValue("Budget_Step2_Category3mthAvgLbl"),
					"3 month avg label is not dsplayed");
			Assert.assertEquals(budgetCreateBudgetLoc
					.budget_Steps2_CategoryMonthyBudgetLabel(PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))
					.getText().trim(), PropsUtil.getDataPropertyValue("Budget_Step2_CategorymonthlybdgLbl"),
					" budgeted label is not dsplayed");
		}
	}

	@Test(description = "AT-108875:verify bill section all catgeory check box is displayed", priority = 53, dependsOnMethods = "verifyBillsCategoryTableHeader")
	public void verifyBillsCategoryAllCheckBox() {
		util.assertIsDisplayed(budgetCreateBudgetLoc.budget_Steps2_CategorySelectCheckBox(
				PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput")), "check box is not dsplayed");
	}

	@Test(description = "AT-108875,AT-108877:verify bills section catgeory name", priority = 54, dependsOnMethods = "verifyBillsCategoryTableHeader")
	public void verifyBillsCategoryName() {
		if (Config.appFlag.equalsIgnoreCase("false") || Config.appFlag.equalsIgnoreCase("web")) {
			util.assertIsDisplayed(
					budgetCreateBudgetLoc
							.budget_Steps2_CategoryIcon(PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput")),
					"category icon is not dsplayed");
		}
		util.assertExpectedActualList(
				util.getWebElementsValue(budgetCreateBudgetLoc
						.budget_Steps2_CategoryName(PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))),
				PropsUtil.getDataPropertyValue("Budget_Step2_BillsCategoryName"), "all catgeory name is not dispalyed");
	}

	@Test(description = "AT-108878:verify bills catgeory 3 month avg", priority = 55, dependsOnMethods = "verifyBillsCategoryTableHeader")
	public void verifyBillsCategory3monthAvg() {
		if (Config.appFlag.equalsIgnoreCase("false") || Config.appFlag.equalsIgnoreCase("web")) {
			util.assertExpectedActualAmountList(
					util.getWebElementsValue(budgetCreateBudgetLoc.budget_Steps2_CategoryMonthlyAgAmount(
							PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))),
					PropsUtil.getDataPropertyValue("Budget_Step2_BillsCategoryAvgAmt"),
					"all catgeory 3 month avg amount is not dispalyed");
		}
	}

	@Test(description = "AT-108878,AT-108879,AT-108936:verify bill section budgeted amount ", priority = 56, dependsOnMethods = "verifyBillsCategoryTableHeader")
	public void verifyBillsCategoryBudgetedAmount() {
		util.assertExpectedActualAmountList(
				util.getWebElementsAttributeValue(budgetCreateBudgetLoc.budget_Steps2_CategoryMonthlyBudgetAmt(
						PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))),
				PropsUtil.getDataPropertyValue("Budget_Step2_BillsCategoryBdgtAmt"),
				"all category budgeted amount is not dispalyed");

	}

	@Test(description = "AT-108888:verify bills section total amount is not displayed", priority = 57, dependsOnMethods = "verifyBillsCategoryTableHeader")
	public void verifyBillsCategoryTotalAmountAmount() {
		Assert.assertEquals(
				budgetCreateBudgetLoc
						.budget_Steps2_CategoryTypeAmount(PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))
						.getText().trim(),
				"$" + budgetCreateBudgetLoc
						.calculateTotalCatAmount(PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput")),
				"bills section total budgeted amount is not displayed");
	}

	@Test(description = "AT-108895:verify bills section add catgeory option", priority = 58, dependsOnMethods = "verifyBillsCategoryTableHeader")
	public void verifyBillsAddCategory() {
		Assert.assertTrue(budgetCreateBudgetLoc
				.budget_Steps2_AddCategoryIcon(PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))
				.isDisplayed());
		Assert.assertEquals(
				budgetCreateBudgetLoc
						.budget_Steps2_AddCategoryLbl(PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))
						.getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_BillsAddCategory"),
				"bill section add category option is not dispalyed");
	}

	@Test(description = "AT-108897,AT-108901:verify bill section category click and verify category fields", priority = 59, dependsOnMethods = "verifyBillsAddCategory")
	public void verifyBillsAddCategoryClick() {
		SeleniumUtil.click(budgetCreateBudgetLoc
				.budget_Steps2_AddCategoryIcon(PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput")));
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			Assert.assertTrue(budgetCreateBudgetLoc.budget_Steps2_AddCategoryCloseIconMobile().isDisplayed());
			Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_LeftOverLabelMobile().getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_Step2_LeftOverLabel"), "left over label is not displayed");
			Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_LeftOverAmountMobile().getText(),
					PropsUtil.getDataPropertyValue("Budget_Step2_LeftOverMobileAmountBills"));
			Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_AddCategoryCurrencyIconMobile().getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategoryCurrencyIcon"),
					"currency icon is not displayed");

		} else {
			Assert.assertTrue(budgetCreateBudgetLoc.budget_Steps2_AddCategoryCloseIcon().isDisplayed());
			Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_AddCategoryCurrencyIcon().getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategoryCurrencyIcon"),
					"currency icon is not displayed");
		}
		Assert.assertTrue(budgetCreateBudgetLoc.budget_Steps2_AddCategoryDDIcon().isDisplayed(),
				"dropdown icon is not dosplayed");
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_AddCategoryDD().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_BillsAddCategorySelection"),
				"dropdown value is not displayed");
		Assert.assertTrue(budgetCreateBudgetLoc.budget_Steps2_AddCategoryAddButton().isDisplayed(),
				"add button is not dsiplayed");
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_AddCategoryAmount().getAttribute("value").trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategoryCurrencyAmt"),
				"catgeory amount field is not displayed");

	}

	@Test(description = "AT-108896,AT-110285:verify bill section add catgeory legends", priority = 60, dependsOnMethods = "verifyBillsAddCategoryClick")
	public void verifyBillsAddCategoryLegends() {
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			util.assertExpectedActualList(
					util.getWebElementsValueUperCase(budgetCreateBudgetLoc.budget_Steps2_legendMonthLinkMobile()),
					util.revereseList(budgetCreateBudgetLoc.xAixValue(4)), "legend months are not dsipalyed");

		}
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_legend3monthAvg().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_BillsAddCategoryLegendsAvg"),
				"bills category 3 moth avg is not displayed");
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_legendcatTypeLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_BillsAddCategoryLegendCategory"),
				"bills section catgeory name");
		Assert.assertTrue(budgetCreateBudgetLoc.budget_Steps2_legendcatTypeLabelcolor().isDisplayed(),
				"bills section catgeory color");
		util.assertExpectedActualList(util.getWebElementsValue(budgetCreateBudgetLoc.budget_Steps2_legendXaxis()),
				util.revereseList(budgetCreateBudgetLoc.xAixValue(4)), "legends x axix value is not displayed");
		util.assertExpectedActualList(util.getWebElementsValue(budgetCreateBudgetLoc.budget_Steps2_legendYaxis()),
				PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategoryLegendYaxix"),
				"legends y axix value is not displayed");
		Assert.assertEquals(budgetCreateBudgetLoc.budget_NoDataDescription().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Summery_NoTxnDataMessage"),
				"no data description is not displayed");
	}

	@Test(description = "AT-108898:verify bills section add catgeory is hidden", priority = 61, dependsOnMethods = "verifyBillsAddCategoryClick")
	public void verifyBillsAddCategoryHide() {
		if (Config.appFlag.equalsIgnoreCase("web") || Config.appFlag.equalsIgnoreCase("false")) {
			Assert.assertTrue(
					budgetCreateBudgetLoc
							.budget_Steps2_AddCategoryHide(PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))
							.getAttribute("class")
							.contains(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategoryHide")),
					"add catgeory is not hidden");
		}
	}

	@Test(description = "AT-108900:verify bills section remaing catgeory name", priority = 62, dependsOnMethods = "verifyBillsAddCategoryClick")
	public void verifyBillsremaing() {
		SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_AddCategoryDDIcon());
		util.assertExpectedActualList(
				util.getWebElementsValue(budgetCreateBudgetLoc.budget_Steps2_addCatDdCategoryList()),
				PropsUtil.getDataPropertyValue("Budget_Step2_BillsAddCategoryRemain"),
				"all catgeory name is not dispalyed");

	}

	@Test(description = "AT-108895:verify bills section add category value", priority = 63, dependsOnMethods = "verifyBillsremaing")
	public void verifyBillsAddCategoryAmount() {
		SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_addCatDdCategoryName(
				PropsUtil.getDataPropertyValue("Budget_Step2_BillsAddCategoryName")));
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		budgetCreateBudgetLoc.budget_Steps2_AddCategoryAmount().clear();
		budgetCreateBudgetLoc.budget_Steps2_AddCategoryAmount()
				.sendKeys(PropsUtil.getDataPropertyValue("Budget_Step2_BillsAddCategoryAmt"));
		SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_AddCategoryAddButton());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			this.budgetCreateBudgetLoc.scroolDown();
		}
		int catgeoryIndex = budgetCreateBudgetLoc
				.budget_Steps2_CategoryName(PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput")).size();
		Assert.assertEquals(
				budgetCreateBudgetLoc
						.budget_Steps2_CategoryName(PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))
						.get(catgeoryIndex - 1).getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_BillsAddCategoryName"), "catgeory name is not displayed");
		Assert.assertEquals(
				budgetCreateBudgetLoc
						.budget_Steps2_CategoryMonthlyBudgetAmt(
								PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))
						.get(catgeoryIndex - 1).getAttribute("value").trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_BillseAddCategoryAmtadded"),
				"budgeted amount is not displayed");
		Assert.assertTrue(
				budgetCreateBudgetLoc
						.budget_Steps2_CategorySelectCheckBox(PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))
						.get(catgeoryIndex - 1).getAttribute("class").trim()
						.contains(PropsUtil.getDataPropertyValue("Budget_Step1_AccountSelected")),
				"check box is not selected");
	}

	@Test(description = ":verify total bill section budgeted amount", priority = 64, dependsOnMethods = "verifyBillsAddCategoryAmount")
	public void verifyBillsCalculationAfterAddCategory() {
		Assert.assertEquals(
				budgetCreateBudgetLoc
						.budget_Steps2_CategoryTypeAmount(PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))
						.getText().trim(),
				"$" + budgetCreateBudgetLoc
						.calculateTotalCatAmount(PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput")),
				"total budgeted amount is not correct");
		String leftOverAmount = null;
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			this.budgetCreateBudgetLoc.scroolUp();
			leftOverAmount = budgetCreateBudgetLoc.budget_Steps2_LeftOverAmount().getText();
		} else {
			leftOverAmount = budgetCreateBudgetLoc.budget_Steps2_LeftOverAmount().getText();
		}
		Assert.assertEquals(leftOverAmount, "$" + budgetCreateBudgetLoc.calculateLeftOver(),
				"left over amount is not correct");

	}

	@Test(description = "AT-108890:verify category section legends", priority = 65, dependsOnMethods = "verifyBillsAddCategoryAmount")
	public void verifyBillsCategoryLegends() {
		SeleniumUtil.click(budgetCreateBudgetLoc
				.budget_Steps2_CategoryDetailsExpand(PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))));
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_legend3monthAvg().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_BillsMonthlyAvg"), "3 month avg is not displayed");
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_legendcatTypeLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_BillsAddCategoryLegendCategory"),
				"legends catgeory type label is not displayed");
		Assert.assertTrue(budgetCreateBudgetLoc.budget_Steps2_legendcatTypeLabelcolor().isDisplayed(),
				"legends catgeory type color is not displayed");
		util.assertExpectedActualList(util.getWebElementsValue(budgetCreateBudgetLoc.budget_Steps2_legendXaxis()),
				util.revereseList(budgetCreateBudgetLoc.xAixValue(4)), "legend x axix is not displayed");
		util.assertExpectedActualAmountList(util.getWebElementsValue(budgetCreateBudgetLoc.budget_Steps2_legendYaxis()),
				PropsUtil.getDataPropertyValue("Budget_Step2_BillsMonthlyYaxix"), "legend y axix is not displayed");

	}

	@Test(description = "AT-108891,AT-108902:verify catgeory section transaction", priority = 66, dependsOnMethods = "verifyBillsCategoryLegends")
	public void verifyBillCategoryTxn() {
		Assert.assertEquals(
				budgetCreateBudgetLoc.budget_Steps2_TxnDateHeader()
						.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).getText().trim(),
				budgetCreateBudgetLoc.DateInMMMMFormate(budgetCreateBudgetLoc.getNoDaysOfMonth(-1)).trim(),
				"transaction date header is not displayed");
		Assert.assertEquals(
				budgetCreateBudgetLoc.budget_Steps2_TxnCategory()
						.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_BillsMonthlytxnCategory"),
				"transaction catgeory is not displayed");
		if (Config.appFlag.equalsIgnoreCase("web") || Config.appFlag.equalsIgnoreCase("false")) {
			Assert.assertEquals(
					budgetCreateBudgetLoc.budget_Steps2_TxnAccountName()
							.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeMonthlytxnBankName"),
					"transaction bank name is not displayed");
			Assert.assertEquals(
					budgetCreateBudgetLoc.budget_Steps2_TxnAccountName()
							.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexOne"))).getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeMonthlytxnBankNumber"),
					"transatcion bank number is not displayed");
		}
		Assert.assertEquals(
				budgetCreateBudgetLoc.budget_Steps2_TxnAmount()
						.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_BillsMonthlytxnBankAmount"),
				"transaction amount is not displayed");

	}

	@Test(description = "AT-108892,AT-108902:verify bprevious months transaction", priority = 67, dependsOnMethods = "verifyBillsCategoryLegends")
	public void verifyBillsCategoryTxnPreviousMonth() {
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_legendMonthLinkMobile()
					.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexOne"))));
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		} else {
			SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_legendBarClick()
					.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexOne"))));
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
			Assert.assertEquals(
					budgetCreateBudgetLoc.budget_Steps2_TxnAccountName()
							.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeMonthlytxnBankName"),
					"transaction bank name is not displayed");
			Assert.assertEquals(
					budgetCreateBudgetLoc.budget_Steps2_TxnAccountName()
							.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexOne"))).getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeMonthlytxnBankNumber"),
					"transaction bank number is nt displayed");

		}
		Assert.assertEquals(
				budgetCreateBudgetLoc.budget_Steps2_TxnDateHeader()
						.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).getText().trim(),
				budgetCreateBudgetLoc.DateInMMMMFormate(-(GenericUtil.getNumberOfDaysInPreviousMonth(1)
						+ GenericUtil.getNumberOfDaysInPreviousMonth(2))).trim(),
				"transaction date header is not displayed");
		Assert.assertEquals(
				budgetCreateBudgetLoc.budget_Steps2_TxnCategory()
						.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_BillsMonthlytxnCategory"),
				"transaction description is not displayed");
		Assert.assertEquals(
				budgetCreateBudgetLoc.budget_Steps2_TxnAmount()
						.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_BillsMonthlytxnBankAmount"),
				"transaction amount is not displayed");

	}

	@Test(description = "AT-108889:verify bills section error message", priority = 68, dependsOnMethods = "verifyBillsCategoryLegends")
	public void verifyBillsCategoryAmtErrorMessage() {
		String aphanumaric = null;
		String Emptyamt = null;
		String morethan11char = null;
		String decimalErr = null;
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			budgetCreateBudgetLoc.sendKeys(budgetCreateBudgetLoc.budget_Steps2_CategoryMonthlyBudgetAmtMobile(),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeBdgAmtAlphaNumeric"));
			SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_AddCategoryAddButton());
			aphanumaric = budgetCreateBudgetLoc.budget_Steps2_BdgAmtErrMsgMobile().getText().trim();
			budgetCreateBudgetLoc.sendKeys(budgetCreateBudgetLoc.budget_Steps2_CategoryMonthlyBudgetAmtMobile(),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeBdgAmtEmpty"));
			SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_AddCategoryAddButton());
			Emptyamt = budgetCreateBudgetLoc.budget_Steps2_BdgAmtErrMsgMobile().getText().trim();
			budgetCreateBudgetLoc.sendKeys(budgetCreateBudgetLoc.budget_Steps2_CategoryMonthlyBudgetAmtMobile(),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeBdgAmtmorethan11char"));
			SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_AddCategoryAddButton());
			morethan11char = budgetCreateBudgetLoc.budget_Steps2_BdgAmtErrMsgMobile().getText().trim();
			budgetCreateBudgetLoc.sendKeys(budgetCreateBudgetLoc.budget_Steps2_CategoryMonthlyBudgetAmtMobile(),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeBdgAmtDecimal"));
			SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_AddCategoryAddButton());
			decimalErr = budgetCreateBudgetLoc.budget_Steps2_BdgAmtErrMsgMobile().getText().trim();

		} else {
			budgetCreateBudgetLoc.sendKeys(
					budgetCreateBudgetLoc
							.budget_Steps2_CategoryMonthlyBudgetAmt(
									PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))
							.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeBdgAmtAlphaNumeric"));
			aphanumaric = budgetCreateBudgetLoc
					.budget_Steps2_BdgAmtErrMsg(PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))
					.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).getText().trim();
			budgetCreateBudgetLoc.sendKeys(
					budgetCreateBudgetLoc
							.budget_Steps2_CategoryMonthlyBudgetAmt(
									PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))
							.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeBdgAmtEmpty"));
			Emptyamt = budgetCreateBudgetLoc
					.budget_Steps2_BdgAmtErrMsg(PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))
					.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).getText().trim();
			budgetCreateBudgetLoc.sendKeys(
					budgetCreateBudgetLoc
							.budget_Steps2_CategoryMonthlyBudgetAmt(
									PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))
							.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeBdgAmtmorethan11char"));
			morethan11char = budgetCreateBudgetLoc
					.budget_Steps2_BdgAmtErrMsg(PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))
					.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).getText().trim();
			budgetCreateBudgetLoc.sendKeys(
					budgetCreateBudgetLoc
							.budget_Steps2_CategoryMonthlyBudgetAmt(
									PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))
							.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeBdgAmtDecimal"));
			decimalErr = budgetCreateBudgetLoc
					.budget_Steps2_BdgAmtErrMsg(PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))
					.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).getText().trim();
		}
		Assert.assertEquals(aphanumaric, PropsUtil.getDataPropertyValue("Budget_Step2_IncomeBdgAmtAlphaNumericErr"),
				"");
		Assert.assertEquals(Emptyamt, PropsUtil.getDataPropertyValue("Budget_Step2_IncomeBdgAmtEmptyErr"), "");
		Assert.assertEquals(morethan11char,
				PropsUtil.getDataPropertyValue("Budget_Step2_IncomeBdgAmtmorethan11charErr"), "");
		Assert.assertEquals(decimalErr, PropsUtil.getDataPropertyValue("Budget_Step2_IncomeBdgAmtDecimalErr"), "");

	}

	@Test(description = "AT-108889:verify bils catgeory edit category amount", priority = 69, dependsOnMethods = "verifyBillsCategoryLegends")
	public void verifyBillsCategoryAmtedit() {
		String leftOverAmount = null;
		String billstotal = null;
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			budgetCreateBudgetLoc.sendKeys(budgetCreateBudgetLoc.budget_Steps2_CategoryMonthlyBudgetAmtMobile(),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeBdgAmtEdit"));
			SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_AddCategoryAddButton());
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
			this.budgetCreateBudgetLoc.scroolDown();
			billstotal = budgetCreateBudgetLoc
					.budget_Steps2_CategoryTypeAmount(PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))
					.getText().trim();
			// this.budgetCreateBudgetLoc.scroolUp();
			leftOverAmount = budgetCreateBudgetLoc.budget_Steps2_LeftOverAmount().getText().replace("$", "");
		} else {

			budgetCreateBudgetLoc.sendKeys(
					budgetCreateBudgetLoc
							.budget_Steps2_CategoryMonthlyBudgetAmt(
									PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))
							.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeBdgAmtEdit"));
			billstotal = budgetCreateBudgetLoc
					.budget_Steps2_CategoryTypeAmount(PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))
					.getText().trim();
			leftOverAmount = budgetCreateBudgetLoc.budget_Steps2_LeftOverAmount().getText().replace("$", "");
		}

		Assert.assertEquals(billstotal,
				"$" + budgetCreateBudgetLoc
						.calculateTotalCatAmount(PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput")),
				"total budgeted amount is not correct");
		Assert.assertEquals(leftOverAmount, budgetCreateBudgetLoc.calculateLeftOver(),
				"left over amount is not correct");

	}

	@Test(description = "AT-108899:verify add all remaining bills catgeory", priority = 70, dependsOnMethods = "verifyBillsCategoryLegends")
	public void verifyaddAllBills() {
		String[] amountinput = PropsUtil.getDataPropertyValue("Budget_Step2_BillsAllAlcategoryInput").split(",");
		for (int i = 0; i < 3; i++) {
			SeleniumUtil.click(budgetCreateBudgetLoc
					.budget_Steps2_AddCategoryIcon(PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput")));
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
			SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_AddCategoryDDIcon());
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
			SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_addCatDdCategoryList()
					.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))));
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
			budgetCreateBudgetLoc.budget_Steps2_AddCategoryAmount().clear();

			budgetCreateBudgetLoc.budget_Steps2_AddCategoryAmount().sendKeys(amountinput[i]);
			SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_AddCategoryAddButton());
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		}
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {

			SeleniumUtil.click(budgetCreateBudgetLoc
					.budget_Steps2_CategoryTypeExpand(PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput")));
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		}
		SeleniumUtil.waitFor(2);
		Assert.assertTrue(budgetCreateBudgetLoc
				.budget_Steps2_AddCategoryDisapear1(PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))
				.getAttribute("class").contains(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategoryHide")),
				"all catgeories are not added");

	}

	@Test(description = "AT-108883:bills section default catgeory", priority = 71, dependsOnMethods = "verifyaddAllBills")
	public void verifyDafalutBillsCategory() {
		if (Config.appFlag.equalsIgnoreCase("web") || Config.appFlag.equalsIgnoreCase("false")) {
			List<String> amount = new ArrayList<String>();
			int catgeoryIndex = budgetCreateBudgetLoc
					.budget_Steps2_CategoryName(PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput")).size();
			for (int i = 0; i < catgeoryIndex; i++) {
				if (budgetCreateBudgetLoc
						.budget_Steps2_CategoryName(PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput")).get(i)
						.getText().trim()
						.equals(PropsUtil.getDataPropertyValue("Budget_Step2_BillsDefaultCategory1"))) {
					Assert.assertEquals(
							budgetCreateBudgetLoc
									.budget_Steps2_CategoryMonthlyBudgetAmt(
											PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))
									.get(i).getAttribute("value").trim(),
							PropsUtil.getDataPropertyValue("Budget_Step2_IncomeDefaultBudgetAmount"), "");
					amount.add(
							budgetCreateBudgetLoc
									.budget_Steps2_CategoryMonthlyAgAmount(
											PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))
									.get(i).getText());
				}
			}
			util.assertExpectedActualAmountList(amount,
					PropsUtil.getDataPropertyValue("Budget_Step2_BillsDefaultAvgAmount"),
					"default catgeory is not displayed");
		}
	}

	@Test(description = "AT-108884:verify default catgeory error message", priority = 72, dependsOnMethods = "verifyaddAllBills")
	public void verifyDafalutBiilsCategoryError() {

		int catgeoryIndex = budgetCreateBudgetLoc
				.budget_Steps2_CategoryName(PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput")).size();
		for (int i = 0; i < catgeoryIndex; i++) {
			if (budgetCreateBudgetLoc
					.budget_Steps2_CategoryName(PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput")).get(i)
					.getText().trim().equals(PropsUtil.getDataPropertyValue("Budget_Step2_BillsDefaultCategory1"))) {
				SeleniumUtil.click(budgetCreateBudgetLoc
						.budget_Steps2_CategorySelectCheckBox(PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))
						.get(i));
				break;
			}

		}
		String decimalErr = budgetCreateBudgetLoc
				.budget_Steps2_BdgAmtErrMsg(PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput")).get(5).getText()
				.trim();

		Assert.assertEquals(decimalErr, PropsUtil.getDataPropertyValue("Budget_Step2_IncomeBdgAmtGreaterThanZero"),
				"erorr message is not displayed");

	}

	@Test(description = "AT-108880:verify flexible category section table header", priority = 73, dependsOnMethods = "verifyStep2BudgetInfoMessage")
	public void verifyFlexibleCategoryTableHeader() {
		SeleniumUtil.click(budgetCreateBudgetLoc
				.budget_Steps2_CategoryTypeExpand(PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput")));
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			Assert.assertTrue(
					budgetCreateBudgetLoc.budget_Steps2_CategorySelectAllCheckBox(
							PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput")).isDisplayed(),
					"select all check box is not displayed");
			Assert.assertEquals(
					budgetCreateBudgetLoc.budget_Steps2_CategorySelectAllLabelMobile(
							PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput")).getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_Step2_CategorySelectAllLblMobile"),
					"select  label is not dsplayed");
			Assert.assertEquals(
					budgetCreateBudgetLoc.budget_Steps2_CategoryMonthyBudgetLabelMobile(
							PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput")).getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_Step2_CategorymonthlybdgLblMobile"),
					" budgeted label is not dsplayed");

		} else {
			Assert.assertTrue(
					budgetCreateBudgetLoc.budget_Steps2_CategorySelectAllCheckBox(
							PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput")).isDisplayed(),
					"select all check box is not displayed");
			Assert.assertEquals(budgetCreateBudgetLoc
					.budget_Steps2_CategorySelectAllLabel(PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"))
					.getText().trim(), PropsUtil.getDataPropertyValue("Budget_Step2_CategorySelectAllLbl"),
					"select  label is not dsplayed");
			Assert.assertEquals(budgetCreateBudgetLoc
					.budget_Steps2_CategoryMonthyAvgLabel(PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"))
					.getText().trim(), PropsUtil.getDataPropertyValue("Budget_Step2_Category3mthAvgLbl"),
					"3 month avg label is not dsplayed");
			Assert.assertEquals(
					budgetCreateBudgetLoc.budget_Steps2_CategoryMonthyBudgetLabel(
							PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput")).getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_Step2_CategorymonthlybdgLbl"),
					" budgeted label is not dsplayed");
		}
	}

	@Test(description = "AT-108875:verify flexible section check box", priority = 74, dependsOnMethods = "verifyFlexibleCategoryTableHeader")
	public void verifyFlexibleCategoryAllCheckBox() {
		util.assertIsDisplayed(budgetCreateBudgetLoc.budget_Steps2_CategorySelectCheckBox(
				PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput")), "check box is not dsplayed");
	}

	@Test(description = "AT-108875,AT-108877:verify flexible section category name", priority = 75, dependsOnMethods = "verifyFlexibleCategoryTableHeader")
	public void verifyFlexibleCategoryName() {
		if (Config.appFlag.equalsIgnoreCase("false") || Config.appFlag.equalsIgnoreCase("web")) {
			util.assertIsDisplayed(
					budgetCreateBudgetLoc
							.budget_Steps2_CategoryIcon(PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput")),
					"category icon is not dsplayed");
		}
		util.assertExpectedActualList(
				util.getWebElementsValue(budgetCreateBudgetLoc
						.budget_Steps2_CategoryName(PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"))),
				PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleCategoryName"),
				"All categories names are not dispalyed as expected");
	}

	@Test(description = "AT-108878:verify flexible section 3 month avg", priority = 76, dependsOnMethods = "verifyFlexibleCategoryTableHeader")
	public void verifyFlexibleCategory3monthAvg() {
		if (Config.appFlag.equalsIgnoreCase("web") || Config.appFlag.equalsIgnoreCase("false")) {
			util.assertExpectedActualAmountList(
					util.getWebElementsValue(budgetCreateBudgetLoc.budget_Steps2_CategoryMonthlyAgAmount(
							PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"))),
					PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleCategoryAvgAmt"),
					"all catgeory 3 month avg amount is not dispalyed");
		}
	}

	@Test(description = "AT-108878:verify flexible budgeted amount", priority = 77, dependsOnMethods = "verifyFlexibleCategoryTableHeader")
	public void verifyFlexibleCategoryBudgetedAmount() {
		util.assertExpectedActualAmountList(
				util.getWebElementsAttributeValue(budgetCreateBudgetLoc.budget_Steps2_CategoryMonthlyBudgetAmt(
						PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"))),
				PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleCategoryBdgtAmt"),
				"all catgeory budgeted amount is not dispalyed");

	}

	@Test(description = "AT-108888:verify flexible section total budgeted amount", priority = 78, dependsOnMethods = "verifyFlexibleCategoryTableHeader")
	public void verifyFlexibleCategoryTotalAmountAmount() {
		Assert.assertEquals(
				budgetCreateBudgetLoc
						.budget_Steps2_CategoryTypeAmount(PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"))
						.getText().trim(),
				"$" + budgetCreateBudgetLoc
						.calculateTotalCatAmount(PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput")),
				"total amount is not correct");
	}

	@Test(description = "AT-108895:verify flexible section add catgeory", priority = 79, dependsOnMethods = "verifyFlexibleCategoryTableHeader")
	public void verifyFlexibleAddCategory() {
		Assert.assertTrue(budgetCreateBudgetLoc
				.budget_Steps2_AddCategoryIcon(PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"))
				.isDisplayed(), "add categroy icon is not displayed");
		Assert.assertEquals(budgetCreateBudgetLoc
				.budget_Steps2_AddCategoryLbl(PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput")).getText()
				.trim(), PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleAddCategory"), "");
	}

	@Test(description = "AT-108897,AT-108901:verify flexible section add category legends", priority = 80, dependsOnMethods = "verifyFlexibleAddCategory")
	public void verifyFlexibleAddCategoryClick() {
		SeleniumUtil.click(budgetCreateBudgetLoc
				.budget_Steps2_AddCategoryIcon(PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput")));
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			Assert.assertTrue(budgetCreateBudgetLoc.budget_Steps2_AddCategoryCloseIconMobile().isDisplayed());
			Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_LeftOverLabelMobile().getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_Step2_LeftOverLabel"), "left over label is not displayed");
			Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_LeftOverAmountMobile().getText(),
					PropsUtil.getDataPropertyValue("Budget_Step2_LeftOverMobileAmountFlexible"));
			Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_AddCategoryCurrencyIconMobile().getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategoryCurrencyIcon"),
					"currency icon is not displayed");

		} else {
			Assert.assertTrue(budgetCreateBudgetLoc.budget_Steps2_AddCategoryCloseIcon().isDisplayed());
			Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_AddCategoryCurrencyIcon().getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategoryCurrencyIcon"),
					"currency icon is not displayed");
		}
		Assert.assertTrue(budgetCreateBudgetLoc.budget_Steps2_AddCategoryDDIcon().isDisplayed(),
				"dropdwon icon is not displayed");
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_AddCategoryDD().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleAddCategorySelection"), "dropdwon value");
		Assert.assertTrue(budgetCreateBudgetLoc.budget_Steps2_AddCategoryAddButton().isDisplayed(),
				"add button is not displayed");
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_AddCategoryAmount().getAttribute("value").trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategoryCurrencyAmt"), "catgeory amount");

	}

	@Test(description = "AT-108896:verify add categroy legends", priority = 81, dependsOnMethods = "verifyFlexibleAddCategoryClick")
	public void verifyFlexibleAddCategoryLegends() {
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			util.assertExpectedActualList(
					util.getWebElementsValueUperCase(budgetCreateBudgetLoc.budget_Steps2_legendMonthLinkMobile()),
					util.revereseList(budgetCreateBudgetLoc.xAixValue(4)), "legend months are not displayed");

		}
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_legend3monthAvg().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_BillsAddCategoryLegendsAvg"),
				"legends avg amount isnot displayed ");
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_legendcatTypeLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_BillsAddCategoryLegendCategory"),
				"catgeory section name is not displayed");
		Assert.assertTrue(budgetCreateBudgetLoc.budget_Steps2_legendcatTypeLabelcolor().isDisplayed(),
				"catgeory section color is displayed");
		util.assertExpectedActualList(util.getWebElementsValue(budgetCreateBudgetLoc.budget_Steps2_legendXaxis()),
				util.revereseList(budgetCreateBudgetLoc.xAixValue(4)), "x axix value is not displayed");
		util.assertExpectedActualList(util.getWebElementsValue(budgetCreateBudgetLoc.budget_Steps2_legendYaxis()),
				PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategoryLegendYaxix"),
				"y axix value is not displayed");

	}

	@Test(description = "AT-108898:verify bills section add category hidden", priority = 82, dependsOnMethods = "verifyFlexibleAddCategoryClick")
	public void verifyFlexibleAddCategoryHide() {
		if (Config.appFlag.equalsIgnoreCase("web") || Config.appFlag.equalsIgnoreCase("false")) {
			Assert.assertTrue(
					budgetCreateBudgetLoc
							.budget_Steps2_AddCategoryHide(PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"))
							.getAttribute("class")
							.contains(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategoryHide")),
					"add catgeory is not hidden");
		}
	}

	@Test(description = "AT-108900:verify flexible section remaing catgeory", priority = 83, dependsOnMethods = "verifyFlexibleAddCategoryClick")
	public void verifyFlexibleremaining() {
		if (Config.appFlag.equalsIgnoreCase("web") || Config.appFlag.equalsIgnoreCase("false")) {
			SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_AddCategoryDDIcon());
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
			util.assertExpectedActualList(
					util.getWebElementsValue(budgetCreateBudgetLoc.budget_Steps2_addCatDdCategoryList()),
					PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleAddCategoryRemain"),
					"all catgeory name is not dispalyed");
		}
	}

	@Test(description = "AT-108895:verify flexible add category amount", priority = 84, dependsOnMethods = "verifyFlexibleremaining")
	public void verifyFlexibleAddCategoryAmount() {
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_AddCategoryDDIcon());
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		}
		SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_addCatDdCategoryName(
				PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleAddCategoryName")));
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();

		budgetCreateBudgetLoc.budget_Steps2_AddCategoryAmount().clear();
		budgetCreateBudgetLoc.budget_Steps2_AddCategoryAmount()
				.sendKeys(PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleAddCategoryAmt"));
		SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_AddCategoryAddButton());
		int catgeoryIndex = budgetCreateBudgetLoc
				.budget_Steps2_CategoryName(PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput")).size();
		Assert.assertEquals(
				budgetCreateBudgetLoc
						.budget_Steps2_CategoryName(PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"))
						.get(catgeoryIndex - 1).getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleAddCategoryName"),
				"added category name is not displayed");
		Assert.assertEquals(
				budgetCreateBudgetLoc
						.budget_Steps2_CategoryMonthlyBudgetAmt(
								PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"))
						.get(catgeoryIndex - 1).getAttribute("value").trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleAddCategoryAmtadded"),
				"added category amount is not displayed");
		Assert.assertTrue(
				budgetCreateBudgetLoc
						.budget_Steps2_CategorySelectCheckBox(
								PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"))
						.get(catgeoryIndex - 1).getAttribute("class").trim()
						.contains(PropsUtil.getDataPropertyValue("Budget_Step1_AccountSelected")),
				"added catgeory is not selected");
	}

	@Test(description = "AT-108895:verify budgeted amount after addede category", priority = 85, dependsOnMethods = "verifyFlexibleAddCategoryAmount")
	public void verifyCalculationAfterAddFlexibleCategory() {
		Assert.assertEquals(
				budgetCreateBudgetLoc
						.budget_Steps2_CategoryTypeAmount(PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"))
						.getText().trim(),
				"$" + budgetCreateBudgetLoc
						.calculateTotalCatAmount(PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput")),
				"total budgeted amount is not correct");
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_LeftOverAmount().getText().replace("$", "").trim(),
				budgetCreateBudgetLoc.calculateLeftOver(), "left over amount is not correct");

	}

	@Test(description = "AT-108890:verify catgeory sectio legends", priority = 86, dependsOnMethods = "verifyFlexibleAddCategoryAmount")
	public void verifyFlexibleCategoryLegends() {
		SeleniumUtil.click(budgetCreateBudgetLoc
				.budget_Steps2_CategoryDetailsExpand(PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"))
				.get(0));
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_legend3monthAvg().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleMonthlyAvg"), "3 month avg is not displayed");
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_legendcatTypeLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_BillsAddCategoryLegendCategory"),
				"cateory section label is not displayed");
		Assert.assertTrue(budgetCreateBudgetLoc.budget_Steps2_legendcatTypeLabelcolor().isDisplayed(),
				"cateory section label color is not displayed");
		util.assertExpectedActualList(util.getWebElementsValue(budgetCreateBudgetLoc.budget_Steps2_legendXaxis()),
				util.revereseList(budgetCreateBudgetLoc.xAixValue(4)), "x axix value is not dipslayed");
		util.assertExpectedActualAmountList(util.getWebElementsValue(budgetCreateBudgetLoc.budget_Steps2_legendYaxis()),
				PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleMonthlyYaxix"), "y axix value is not dipslayed");

	}

	@Test(description = "AT-108891,AT-108902:verify legends transaction", priority = 87, dependsOnMethods = "verifyFlexibleCategoryLegends")
	public void verifyFlexibleCategoryTxn() {
		Assert.assertEquals(
				budgetCreateBudgetLoc.budget_Steps2_TxnDateHeader()
						.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).getText().trim(),
				budgetCreateBudgetLoc.DateInMMMMFormate(budgetCreateBudgetLoc.getNoDaysOfMonth(-1)).trim(),
				"transaction date header");
		Assert.assertEquals(
				budgetCreateBudgetLoc.budget_Steps2_TxnCategory()
						.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleMonthlytxnCategory"),
				"transaction description name is not displayed");
		if (Config.appFlag.equalsIgnoreCase("web") || Config.appFlag.equalsIgnoreCase("false")) {
			Assert.assertEquals(
					budgetCreateBudgetLoc.budget_Steps2_TxnAccountName()
							.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeMonthlytxnBankName"),
					"transaction bank name is not dipslayed");
			Assert.assertEquals(
					budgetCreateBudgetLoc.budget_Steps2_TxnAccountName()
							.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexOne"))).getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeMonthlytxnBankNumber"),
					"transaction bank number is not dipslayed");
		}
		Assert.assertEquals(
				budgetCreateBudgetLoc.budget_Steps2_TxnAmount()
						.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleMonthlytxnBankAmount"),
				"transaction amount is not dispayed");

	}

	@Test(description = "AT-108892,AT-108902:verify legends previous months transaction", priority = 88, dependsOnMethods = "verifyFlexibleCategoryLegends")
	public void verifyFlexibleCategoryTxnPreviousMonth() {
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_legendMonthLinkMobile()
					.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexOne"))));
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		} else {
			SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_legendBarClick()
					.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexOne"))));
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
			Assert.assertEquals(
					budgetCreateBudgetLoc.budget_Steps2_TxnAccountName()
							.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeMonthlytxnBankName"),
					"transaction bank name is not dipslayed");
			Assert.assertEquals(
					budgetCreateBudgetLoc.budget_Steps2_TxnAccountName()
							.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexOne"))).getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeMonthlytxnBankNumber"),
					"transaction bank number is not dipslayed");
		}
		String expectedDate = budgetCreateBudgetLoc.DateInMMMMFormate(
				-(GenericUtil.getNumberOfDaysInPreviousMonth(1) + GenericUtil.getNumberOfDaysInPreviousMonth(2)))
				.trim();
		Assert.assertEquals(
				budgetCreateBudgetLoc.budget_Steps2_TxnDateHeader()
						.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).getText().trim(),
				expectedDate, "transaction date header is not coming as expecetd");
		Assert.assertEquals(
				budgetCreateBudgetLoc.budget_Steps2_TxnCategory()
						.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleMonthlytxnCategory"),
				"transaction description name is not displayed");
		Assert.assertEquals(
				budgetCreateBudgetLoc.budget_Steps2_TxnAmount()
						.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleMonthlytxnBankAmount"),
				"transaction amount is not dispayed");

	}

	@Test(description = "AT-108889:verify budget group input field max char validation", priority = 89, dependsOnMethods = "verifyFlexibleCategoryLegends")
	public void verifyFlexibleCategoryAmtErrorMessage() {
		String aphanumaric = null;
		String Emptyamt = null;
		String morethan11char = null;
		String decimalErr = null;
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			budgetCreateBudgetLoc.sendKeys(budgetCreateBudgetLoc.budget_Steps2_CategoryMonthlyBudgetAmtMobile(),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeBdgAmtAlphaNumeric"));
			SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_AddCategoryAddButton());
			aphanumaric = budgetCreateBudgetLoc.budget_Steps2_BdgAmtErrMsgMobile().getText().trim();
			budgetCreateBudgetLoc.sendKeys(budgetCreateBudgetLoc.budget_Steps2_CategoryMonthlyBudgetAmtMobile(),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeBdgAmtEmpty"));
			SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_AddCategoryAddButton());
			Emptyamt = budgetCreateBudgetLoc.budget_Steps2_BdgAmtErrMsgMobile().getText().trim();
			budgetCreateBudgetLoc.sendKeys(budgetCreateBudgetLoc.budget_Steps2_CategoryMonthlyBudgetAmtMobile(),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeBdgAmtmorethan11char"));
			SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_AddCategoryAddButton());
			morethan11char = budgetCreateBudgetLoc.budget_Steps2_BdgAmtErrMsgMobile().getText().trim();
			budgetCreateBudgetLoc.sendKeys(budgetCreateBudgetLoc.budget_Steps2_CategoryMonthlyBudgetAmtMobile(),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeBdgAmtDecimal"));
			SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_AddCategoryAddButton());
			decimalErr = budgetCreateBudgetLoc.budget_Steps2_BdgAmtErrMsgMobile().getText().trim();

		} else {
			budgetCreateBudgetLoc.sendKeys(
					budgetCreateBudgetLoc
							.budget_Steps2_CategoryMonthlyBudgetAmt(
									PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"))
							.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeBdgAmtAlphaNumeric"));
			aphanumaric = budgetCreateBudgetLoc
					.budget_Steps2_BdgAmtErrMsg(PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"))
					.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).getText().trim();
			budgetCreateBudgetLoc.sendKeys(
					budgetCreateBudgetLoc
							.budget_Steps2_CategoryMonthlyBudgetAmt(
									PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"))
							.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeBdgAmtEmpty"));
			Emptyamt = budgetCreateBudgetLoc
					.budget_Steps2_BdgAmtErrMsg(PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"))
					.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).getText().trim();
			budgetCreateBudgetLoc.sendKeys(
					budgetCreateBudgetLoc
							.budget_Steps2_CategoryMonthlyBudgetAmt(
									PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"))
							.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeBdgAmtmorethan11char"));
			morethan11char = budgetCreateBudgetLoc
					.budget_Steps2_BdgAmtErrMsg(PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"))
					.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).getText().trim();
			budgetCreateBudgetLoc.sendKeys(
					budgetCreateBudgetLoc
							.budget_Steps2_CategoryMonthlyBudgetAmt(
									PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"))
							.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeBdgAmtDecimal"));
			decimalErr = budgetCreateBudgetLoc
					.budget_Steps2_BdgAmtErrMsg(PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"))
					.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).getText().trim();
		}
		Assert.assertEquals(aphanumaric, PropsUtil.getDataPropertyValue("Budget_Step2_IncomeBdgAmtAlphaNumericErr"),
				"alpha numberic error message is not displayed");
		Assert.assertEquals(Emptyamt, PropsUtil.getDataPropertyValue("Budget_Step2_IncomeBdgAmtEmptyErr"),
				"empty erro message is not displayed");
		Assert.assertEquals(morethan11char,
				PropsUtil.getDataPropertyValue("Budget_Step2_IncomeBdgAmtmorethan11charErr"),
				"11 char error message is not displayed");
		Assert.assertEquals(decimalErr, PropsUtil.getDataPropertyValue("Budget_Step2_IncomeBdgAmtDecimalErr"),
				"4 decimal char message is not displayed");

	}

	@Test(description = "AT-108889:verify flexible amount edited", priority = 90, dependsOnMethods = "verifyFlexibleCategoryLegends")
	public void verifyFlexibleCategoryAmtedit() {
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			budgetCreateBudgetLoc.sendKeys(budgetCreateBudgetLoc.budget_Steps2_CategoryMonthlyBudgetAmtMobile(),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeBdgAmtEdit"));
			SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_AddCategoryAddButton());
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		} else {
			budgetCreateBudgetLoc.sendKeys(
					budgetCreateBudgetLoc
							.budget_Steps2_CategoryMonthlyBudgetAmt(
									PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"))
							.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeBdgAmtEdit"));
		}
		Assert.assertEquals(
				budgetCreateBudgetLoc
						.budget_Steps2_CategoryTypeAmount(PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"))
						.getText().trim(),
				"$" + budgetCreateBudgetLoc
						.calculateTotalCatAmount(PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput")),
				"total budgeted amount is not correct");
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_LeftOverAmount().getText().replaceAll("-", "").trim(),
				"$" + budgetCreateBudgetLoc.calculateLeftOver().replace("-", "").trim(),
				"left over amount is not displayed");

	}

	@Test(description = "AT-108899:verify all flexible category is added", priority = 91, dependsOnMethods = "verifyFlexibleCategoryAmtedit")
	public void verifyaddAllFlexible() {
		String[] amountinput = PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleAllAlcategoryInput").split(",");
		for (int i = 0; i < 24; i++) {
			SeleniumUtil.click(budgetCreateBudgetLoc
					.budget_Steps2_AddCategoryIcon(PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput")));
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
			SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_AddCategoryDDIcon());
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
			SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_addCatDdCategoryList()
					.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))));
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
			budgetCreateBudgetLoc.budget_Steps2_AddCategoryAmount().clear();
			budgetCreateBudgetLoc.budget_Steps2_AddCategoryAmount().sendKeys(amountinput[i]);
			SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_AddCategoryAddButton());
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		}
		SeleniumUtil.waitFor(3);

		Assert.assertFalse(
				budgetCreateBudgetLoc.budget_Steps2_AddCategoryDisapear1(
						PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput")).isDisplayed(),
				"All flexible categories are not added");

	}

	@Test(description = "AT-108764:only one catgeory section is expanded", priority = 92)
	public void verifyOnlyoneCategoryDetailsExpanded() {
		SeleniumUtil.click(budgetCreateBudgetLoc
				.budget_Steps2_CategoryTypeExpand(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput")));
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.click(budgetCreateBudgetLoc
				.budget_Steps2_CategoryDetailsExpand(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))));
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		int categoryRow = budgetCreateBudgetLoc
				.budget_Steps2_CategoryDetailsExpand(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput")).size();
		int expandedCategorysize = 0;
		for (int i = 0; i < categoryRow; i++) {
			if (budgetCreateBudgetLoc
					.budget_Steps2_CategoryDetailsExpand(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))
					.get(i).getAttribute("class")
					.contains(PropsUtil.getDataPropertyValue("Budget_Step1ContainerExpand"))) {
				expandedCategorysize = expandedCategorysize + 1;
			}
		}
		Assert.assertTrue(expandedCategorysize == 1, "expanded size is not 1");
	}

	@Test(description = "AT-108765:only one catgeory section is expanded ", priority = 93, dependsOnMethods = "verifyOnlyoneCategoryDetailsExpanded")
	public void verifyClickOusideCategoryExpanded() {

		int categoryRow = budgetCreateBudgetLoc
				.budget_Steps2_CategoryDetailsExpand(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput")).size();
		int expandedCategorysize = 0;
		for (int i = 0; i < categoryRow; i++) {
			if (budgetCreateBudgetLoc
					.budget_Steps2_CategoryDetailsExpand(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))
					.get(i).getAttribute("class")
					.contains(PropsUtil.getDataPropertyValue("Budget_Step1ContainerExpand"))) {
				expandedCategorysize = expandedCategorysize + 1;
			}
		}
		Assert.assertTrue(expandedCategorysize == 1, "expanded size is not 1");
	}

	@Test(description = "AT-108763,AT-108767:verify Category section expanded", priority = 94, dependsOnMethods = "verifyClickOusideCategoryExpanded")
	public void verifycatgeoryDetailsExpanded() {
		SeleniumUtil.click(budgetCreateBudgetLoc
				.budget_Steps2_CategoryTypeExpand(PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput")));
		SeleniumUtil.click(budgetCreateBudgetLoc
				.budget_Steps2_CategorySelectCheckBox(PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))));
		String clicCheckbox = budgetCreateBudgetLoc
				.budget_Steps2_CategoryDetailsExpand(PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).getAttribute("class");
		SeleniumUtil.click(budgetCreateBudgetLoc
				.budget_Steps2_CategoryName(PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))));
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		String clicCategoryName = budgetCreateBudgetLoc
				.budget_Steps2_CategoryDetailsExpand(PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).getAttribute("class");
		SeleniumUtil.click(budgetCreateBudgetLoc
				.budget_Steps2_CategoryMonthlyAgAmount(PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexOne"))));
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		String clicAvgAmount = budgetCreateBudgetLoc
				.budget_Steps2_CategoryDetailsExpand(PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexOne"))).getAttribute("class");
		SeleniumUtil.click(budgetCreateBudgetLoc
				.budget_Steps2_CategoryDetailsExpand(PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexThree"))));
		String clicAccordion = budgetCreateBudgetLoc
				.budget_Steps2_CategoryDetailsExpand(PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexThree"))).getAttribute("class");
		Assert.assertTrue(clicCheckbox.contains(PropsUtil.getDataPropertyValue("Budget_Step1ContainerCollapse")),
				"category section expanded");
		Assert.assertTrue(clicCategoryName.contains(PropsUtil.getDataPropertyValue("Budget_Step1ContainerExpand")),
				"category section collapsed");
		Assert.assertTrue(clicAvgAmount.contains(PropsUtil.getDataPropertyValue("Budget_Step1ContainerExpand")),
				"category section collapsed");
		Assert.assertTrue(clicAccordion.contains(PropsUtil.getDataPropertyValue("Budget_Step1ContainerExpand")),
				"category section collapsed");

	}

//	Removed and moved to another class com.omni.pfm.BudgetV2.Budget_AccountGroupsAndCategories
	
	// Account group in step1

	/*
	 * @Test(description =
	 * "AT-108810,AT-108811,AT-108870,AT-108871:verify account group drop down",
	 * priority = 100) public void verifyAccountGroupDDFTUE() throws Exception {
	 * d.manage().deleteAllCookies(); d.navigate().refresh(); LoginPage.loginMain(d,
	 * loginParameter); SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	 * accountAddition.linkAccount();
	 * accountAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue(
	 * "Budget_BudgetSummery_Data10_Site"),
	 * PropsUtil.getDataPropertyValue("Budget_BudgetSummery_Data10_Password"));
	 * 
	 * PageParser.forceNavigate("Budget", d);
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	 * SeleniumUtil.click(budgetCreateBudgetLoc.budget_Getstart_GetStartButton());
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	 * SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps1_AccountGroupDropDown()
	 * ); SeleniumUtil.waitUntilSpinnerWheelIsInvisible(); if
	 * (Config.appFlag.equalsIgnoreCase("app") ||
	 * Config.appFlag.equalsIgnoreCase("emulator")) { // String cancelbtn = //
	 * budgetCreateBudgetLoc.budget_Steps2_GrpDDCancel().getText().trim(); // String
	 * donebtn = //
	 * budgetCreateBudgetLoc.budget_Steps2_GrpDDDone().getText().trim();
	 * SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_GrpDDCancel());
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible(); String dropDownClose =
	 * budgetCreateBudgetLoc.budget_Steps1_GrpDDOpen().getAttribute("class");
	 * SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps1_AccountGroupDropDown()
	 * ); SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	 * 
	 * // SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_GrpDDDone()); //
	 * String dropDownClose1 = //
	 * budgetCreateBudgetLoc.budget_Steps1_GrpDDOpen().getAttribute("class"); //
	 * SeleniumUtil.click(budgetCreateBudgetLoc.
	 * budget_Steps1_AccountGroupDropDownIcon()); // Assert.assertEquals(cancelbtn,
	 * // PropsUtil.getDataPropertyValue("Budget_Step1_CreateGroupMobileCancel"),
	 * ""); // Assert.assertEquals(donebtn, //
	 * PropsUtil.getDataPropertyValue("Budget_Step1_CreateGroupMobileDone"), "");
	 * Assert.assertTrue( dropDownClose.contains(PropsUtil.getDataPropertyValue(
	 * "Budget_Step2_IncomeAddCategoryHide")), "dropdwon is not closed"); //
	 * Assert.assertTrue( // dropDownClose1.contains(PropsUtil.getDataPropertyValue(
	 * "Budget_Step2_IncomeAddCategoryHide")), // "dropdwon is not closed"); }
	 * Assert.assertTrue(budgetCreateBudgetLoc.budget_Steps1_GrpDDEmptIcon().
	 * isDisplayed(), "dropdwon icon is not displayed");
	 * Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps1_GrpDDEmptHeader().
	 * getText().trim(),
	 * PropsUtil.getDataPropertyValue("Budget_Step1_EmptyGroupMessageHeader"),
	 * "empty group message is not displayed");
	 * Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps1_GrpDDEmptDesc().
	 * getText().trim().replace("\n", " "),
	 * PropsUtil.getDataPropertyValue("Budget_Step1_EmptyGroupMessage"),
	 * "empoty message not displayed");
	 * Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps1_GrpDDCreate().getText
	 * ().trim(), PropsUtil.getDataPropertyValue("Budget_Step1_CreateGroupButton"),
	 * "create group button is not displayed"); }
	 * 
	 * @Test(description = "AT-108820,AT-108818:verify account is selected",
	 * priority = 101, dependsOnMethods = "verifyAccountGroupDDFTUE") public void
	 * verifyAccountselected() {
	 * SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps1_GrpDDCreate()); String
	 * defaultDroDownvalue =
	 * budgetCreateBudgetLoc.budget_Steps1_AccountGroupDropDown().getText().trim();
	 * Assert.assertEquals(defaultDroDownvalue,
	 * PropsUtil.getDataPropertyValue("Budget_Step1_AccountGroupDDValue"),
	 * "account group drodown is not displayed with create group text");
	 * Assert.assertTrue(budgetCreateBudgetLoc.budget_Steps1_BudgetNameField().
	 * isDisplayed()); util.assertExpectedActualList(
	 * util.getWebElementsValue(budgetCreateBudgetLoc.
	 * budget_Steps1_AccountInstitutionName(
	 * PropsUtil.getDataPropertyValue("Budget_Step1CardContainerInput"))),
	 * PropsUtil.getDataPropertyValue("Budget_Step1CardContainerInst"),
	 * "Cash container intitution is not dispalyed"); util.assertExpectedActualList(
	 * util.getWebElementsValue(budgetCreateBudgetLoc.
	 * budget_Steps1_AccountInstitutionName(
	 * PropsUtil.getDataPropertyValue("Budget_Step1BankContainerInput"))),
	 * PropsUtil.getDataPropertyValue("Budget_Step1BankContainerInst"),
	 * "card container intitution is not dispalyed"); util.assertExpectedActualList(
	 * util.getWebElementsValue(budgetCreateBudgetLoc.
	 * budget_Steps1_AccountInstitutionName(
	 * PropsUtil.getDataPropertyValue("Budget_Step1InvestmentContainerInput"))),
	 * PropsUtil.getDataPropertyValue("Budget_Step1InvestmentContainerInst"),
	 * "Invetsment container intitution is not dispalyed");
	 * logger.info("account number validation"); util.assertExpectedActualList(
	 * util.getWebElementsValue(budgetCreateBudgetLoc
	 * .budget_Steps1_AccountNumber(PropsUtil.getDataPropertyValue(
	 * "Budget_Step1CardContainerInput"))),
	 * PropsUtil.getDataPropertyValue("Budget_Step1CardAccountNo"),
	 * "Cash account number is not dispalyed");
	 * 
	 * util.assertExpectedActualList(
	 * util.getWebElementsValue(budgetCreateBudgetLoc.budget_Steps1_AccountNumber(
	 * PropsUtil.getDataPropertyValue("Budget_Step1InvestmentContainerInput"))),
	 * PropsUtil.getDataPropertyValue("Budget_Step1InvestmentAccountNo"),
	 * "Invetsment account number is not dispalyed"); }
	 * 
	 * @Test(description = "AT-108820,AT-108818:verify create button", priority =
	 * 102, dependsOnMethods = "verifyAccountselected") public void
	 * verifyCreateButton() { SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	 * PageParser.forceNavigate("AccountsPage", d); groupView.clikcCreatgroup();
	 * groupView.createGroup(PropsUtil.getDataPropertyValue(
	 * "Budget_Step1_CreateEducationAccGroup"),
	 * PropsUtil.getDataPropertyValue("Budget_Step1_CreateEducationAcc").split(","))
	 * ; groupView.createGroupMore(PropsUtil.getDataPropertyValue(
	 * "Budget_Step1_CreateAccGroup").split(","),
	 * PropsUtil.getDataPropertyValue("Budget_Step1_CreateAccGroupAccList").split(
	 * ",")); SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	 * PageParser.forceNavigate("Budget", d);
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	 * SeleniumUtil.click(budgetCreateBudgetLoc.budget_Getstart_GetStartButton());
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible(); String defaultDroDownvalue =
	 * budgetCreateBudgetLoc.budget_Steps1_AccountGroupDropDown().getText().trim();
	 * Assert.assertEquals(defaultDroDownvalue,
	 * PropsUtil.getDataPropertyValue("Budget_Step1_CreateDailyAccGroup"),
	 * "account group drodown is not displayed with create group text"); }
	 * 
	 * @Test(description = "AT-108820:verify account group name", priority = 103,
	 * dependsOnMethods = "verifyCreateButton") public void
	 * verifygroupnameForAcccgrp() {
	 * Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps1_BudgetGroupName().
	 * getText(),
	 * PropsUtil.getDataPropertyValue("Budget_Step1_CreateDailyAccGroup"),
	 * "created budget group name not displayed"); }
	 * 
	 * @Test(description = "AT-108821,AT-108819:verify eligible group ", priority =
	 * 104, dependsOnMethods = "verifyCreateButton") public void
	 * verifyEligibleGroup() {
	 * SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps1_AccountGroupDropDown()
	 * ); // group name should be displayed in apabetical order
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	 * Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps1_GrpDDCreategroup().
	 * getText().trim(),
	 * PropsUtil.getDataPropertyValue("Budget_Step1_CreateGroupButton1"), "");
	 * util.assertExpectedActualList(util.getWebElementsValue(budgetCreateBudgetLoc.
	 * budget_Steps1_GrpDDName()),
	 * PropsUtil.getDataPropertyValue("Budget_Step1_GroupDDGroups"),
	 * "all eligible group is not displayed"); }
	 * 
	 * @Test(description = "AT-108894:verify 25 txn", priority = 105,
	 * dependsOnMethods = "verifyEligibleGroup") public void verify25TXn() { //
	 * budgetCreateBudgetLoc.createBudgetClickCreategroupButton(PropsUtil.
	 * getDataPropertyValue("Budget_Step1_CreateMedicalBudget"));
	 * SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps1_GrpDDCreategroup());
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	 * budgetCreateBudgetLoc.sendKeys(budgetCreateBudgetLoc.
	 * budget_Steps1_BudgetNameField(),
	 * PropsUtil.getDataPropertyValue("Budget_Step1_CreateMedicalBudget"));
	 * SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps1_NextButton());
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	 * SeleniumUtil.click(budgetCreateBudgetLoc
	 * .budget_Steps2_CategoryDetailsExpand(PropsUtil.getDataPropertyValue(
	 * "Budget_Step2_IncomeInput")) .get(0));
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	 * Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_TxnCategory().size(),
	 * 25, "Transaction category sizes are not matcing");
	 * Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_LoadMoreTxn().getText
	 * ().trim(), PropsUtil.getDataPropertyValue("Budget_Step2_LoanMoreTxnText"),
	 * "load more transaction is not displayed");
	 * 
	 * }
	 * 
	 * @Test(description = "AT-108893:verify transaction edited", priority = 106)
	 * public void verifyTxnEdited() { SeleniumUtil.refresh(d);
	 * PageParser.forceNavigate("Transaction", d);
	 * 
	 * if (!(Config.appFlag.equalsIgnoreCase("app") ||
	 * Config.appFlag.equalsIgnoreCase("emulator"))) {
	 * SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_Quite());
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	 * SeleniumUtil.waitForPageToLoad(2000); }
	 * 
	 * String IA_MTCategory8 = null; if
	 * (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
	 * 
	 * IA_MTCategory8 =
	 * PropsUtil.getDataPropertyValue("Budget_Step2_MTCatgeory_43");
	 * 
	 * } else {
	 * 
	 * IA_MTCategory8 = PropsUtil.getDataPropertyValue("Budget_Step2_MTCatgeory");
	 * 
	 * } if (addManualTransaction.isMoreBtnPresent()) {
	 * SeleniumUtil.click(addManualTransaction.MobileMore());
	 * SeleniumUtil.click(addManualTransaction.MobileaddManualIcon_AMT()); } else {
	 * addManualTransaction.clickMTLink(); }
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	 * addManualTransaction.createOneTimeTransaction(PropsUtil.getDataPropertyValue(
	 * "Budget_Step2_MTAmount"),
	 * PropsUtil.getDataPropertyValue("Budget_Step2_MTDescription"),
	 * PropsUtil.getDataPropertyValue("Budget_Step2_MTType"),
	 * PropsUtil.getDataPropertyValue("Budget_Step2_MTAccount"),
	 * PropsUtil.getDataPropertyValue("Budget_Step2_MTFrequency"),
	 * budgetCreateBudgetLoc.getNoDaysOfMonth(-1), IA_MTCategory8,
	 * PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummerydebitTxnNote"
	 * ), PropsUtil.getDataPropertyValue(
	 * "Budget_MonthlymaintaineceSummerydebitTxnCheck"));
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	 * PageParser.forceNavigate("Budget", d);
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	 * SeleniumUtil.click(budgetCreateBudgetLoc.budget_Getstart_GetStartButton());
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	 * SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps1_NextButton());
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	 * SeleniumUtil.click(budgetCreateBudgetLoc
	 * .budget_Steps2_CategoryDetailsExpand(PropsUtil.getDataPropertyValue(
	 * "Budget_Step2_IncomeInput")) .get(1));
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	 * budgetCreateBudgetLoc.updateTxn(PropsUtil.getDataPropertyValue(
	 * "Budget_Step2_MTAmountList"),
	 * PropsUtil.getDataPropertyValue("Budget_Step2_MTAmountUpdated")); if
	 * (Config.appFlag.equalsIgnoreCase("false") ||
	 * Config.appFlag.equalsIgnoreCase("web")) {
	 * Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_legend3monthAvg().
	 * getText().trim(),
	 * PropsUtil.getDataPropertyValue("Budget_Step2_IncomeMonthlyAvgUpdated"),
	 * "all catgeory 3 month avg amount is not dispalyed"); } Assert.assertEquals(
	 * budgetCreateBudgetLoc .budget_Steps2_CategoryMonthlyAgAmount(
	 * PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))
	 * .get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexOne"))).
	 * getText().trim(),
	 * PropsUtil.getDataPropertyValue("Budget_Step2_IncomeTxnEditeAvgAmt"),
	 * "all catgeory 3 month  amount is not dispalyed"); Assert.assertEquals(
	 * budgetCreateBudgetLoc .budget_Steps2_CategoryMonthlyBudgetAmt(
	 * PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))
	 * .get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexOne"))).
	 * getAttribute("value"),
	 * PropsUtil.getDataPropertyValue("Budget_Step2_IncomeTxnEditeBdgAmt"),
	 * "all catgeory Budgeted amount is not dispalyed");
	 * 
	 * }
	 * 
	 * @Test(description = "AT-108822:verify group is deleted", priority = 107,
	 * dependsOnMethods = "verifyTxnEdited") public void verifydeletedBudgetGroup()
	 * {//TODO SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_DoneButton());
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	 * PageParser.forceNavigate("AccountGroups", d);
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	 * SeleniumUtil.click(accountGroupSetting
	 * .AccGroupSetting_GroupListName(PropsUtil.getDataPropertyValue(
	 * "Budget_Step1_CreateMedicalBudget")));
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	 * accountGroupSetting.deleteAccountGroup();
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	 * PageParser.forceNavigate("Budget", d);
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible(); //
	 * SeleniumUtil.click(budgetCreateBudgetLoc.budget_Getstart_GetStartButton());
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	 * SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps1_AccountGroupDropDown()
	 * );
	 * 
	 * Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps1_GrpDDCreategroup().
	 * getText().trim(),
	 * PropsUtil.getDataPropertyValue("Budget_Step1_CreateGroupButton1"), "");
	 * util.assertExpectedActualList(util.getWebElementsValue(budgetCreateBudgetLoc.
	 * budget_Steps1_GrpDDName()),
	 * PropsUtil.getDataPropertyValue("Budget_Step1_GroupDDGroups"), "");
	 * 
	 * }
	 * 
	 * @Test(description = "AT-108717:verify no account message", priority = 108)
	 * public void verifyNoAccountMessage() throws Exception {
	 * LoginPage.loginMain(d, loginParameter); PageParser.forceNavigate("Budget",
	 * d); SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	 * Assert.assertEquals(budgetCreateBudgetLoc.budget_NoDataHeader().getText().
	 * trim(), PropsUtil.getDataPropertyValue("Budget_NoDataHeader"),
	 * " no data headre is not displayed");
	 * Assert.assertEquals(budgetCreateBudgetLoc.budget_NoDataDescription().getText(
	 * ).trim(), PropsUtil.getDataPropertyValue("Budget_NoDescription"),
	 * "no data description"); }
	 * 
	 * @Test(description = "AT-108905:verify no catgeory popup", priority = 109,
	 * dependsOnMethods = "verifyNoAccountMessage") public void
	 * verifyNoCatgeoryDataPopUp() throws Exception {
	 * 
	 * accountAddition.linkAccount();
	 * accountAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue(
	 * "Budget_BudgetSummery_Data9_Site"),
	 * PropsUtil.getDataPropertyValue("Budget_BudgetSummery_Data9_Password"));
	 * 
	 * PageParser.forceNavigate("Budget", d);
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	 * budgetCreateBudgetLoc.createBudgetGroup(PropsUtil.getDataPropertyValue(
	 * "Budget_HouseHoldingBudget"));
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	 * Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_PopupHeader().getText
	 * ().trim(), PropsUtil.getDataPropertyValue("Budget_Step2_NoCategorytHeader"),
	 * "no category header is not displayed");
	 * Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_PopupBody().getText()
	 * .trim(), PropsUtil.getDataPropertyValue("Budget_Step2_NoCategoryBodymsg"),
	 * "no category popup body message is not displayed");
	 * Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_PopupButton().getText
	 * ().trim(), PropsUtil.getDataPropertyValue("Budget_Step2_NoCategoryOkButton"),
	 * "ok button is not displayed");
	 * Assert.assertTrue(budgetCreateBudgetLoc.budget_Steps2_PopupCloseIcon().
	 * isDisplayed(), "close icon is not displayed"); }
	 * 
	 * @Test(description = "AT-108804:verify link account in step1", priority = 110,
	 * dependsOnMethods = "verifyNoCatgeoryDataPopUp") public void
	 * verifyLinkAccount() throws Exception {
	 * SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_PopupButton());
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible(); if
	 * (budgetCreateBudgetLoc.isbudget_Steps2_BackButtonMobile()) {
	 * SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_BackButtonMobile()); }
	 * else { SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_BackButton());
	 * }
	 * SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps1_AccountGroupDropDown()
	 * ); SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	 * SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps1_GrpDDCreategroup());
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	 * 
	 * if (Config.appFlag.equalsIgnoreCase("app") ||
	 * Config.appFlag.equalsIgnoreCase("emulator")) {
	 * SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_linkAccountMobile());
	 * } else {
	 * SeleniumUtil.click(budgetCreateBudgetLoc.budget_Getstart_LinkAccountButton())
	 * ; } SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	 * accountAddition.addAggregatedAccount2(PropsUtil.getDataPropertyValue(
	 * "Budget_BudgetSummery_Data14_Site"),
	 * PropsUtil.getDataPropertyValue("Budget_BudgetSummery_Data14_Password"));
	 * SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps1_LinAccountClose());
	 * SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps1_LinAccountDone());
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible(); String[] cashAccount =
	 * PropsUtil.getDataPropertyValue("Budget_Step1_CreateGroupUpdatedCashAccount").
	 * split(","); List<String> cashActualValue = new ArrayList<String>();
	 * cashActualValue = util.getWebElementsValue(budgetCreateBudgetLoc
	 * .budget_Steps1_AccountNumber(PropsUtil.getDataPropertyValue(
	 * "Budget_Step1BankContainerInput"))); for (int i = 0; i < cashAccount.length;
	 * i++) { util.isAnyMatchInList(cashActualValue, cashAccount[i]); } String[]
	 * cardAccount =
	 * PropsUtil.getDataPropertyValue("Budget_Step1_CreateGroupUpdatedCardAccount").
	 * split(","); List<String> cardActualValue = new ArrayList<String>();
	 * cardActualValue = util.getWebElementsValue(budgetCreateBudgetLoc
	 * .budget_Steps1_AccountNumber(PropsUtil.getDataPropertyValue(
	 * "Budget_Step1CardContainerInput"))); for (int i = 0; i < cardAccount.length;
	 * i++) { util.isAnyMatchInList(cardActualValue, cardAccount[i]); } String[]
	 * InvestmentAccount =
	 * PropsUtil.getDataPropertyValue("Budget_Step1_CreateGroupUpdatedInvstAccount")
	 * .split(","); List<String> investmentActualValue = new ArrayList<String>();
	 * investmentActualValue = util.getWebElementsValue(budgetCreateBudgetLoc
	 * .budget_Steps1_AccountNumber(PropsUtil.getDataPropertyValue(
	 * "Budget_Step1InvestmentContainerInput"))); for (int i = 0; i <
	 * InvestmentAccount.length; i++) { util.isAnyMatchInList(investmentActualValue,
	 * InvestmentAccount[i]); } }
	 * 
	 * @Test(description = "AT-108904:verify save details in summary", priority =
	 * 111, dependsOnMethods = "verifyLinkAccount") public void verifySavedDetails()
	 * throws Exception {
	 * 
	 * budgetCreateBudgetLoc.createGroup(PropsUtil.getDataPropertyValue(
	 * "Budget_Step2_SummaryValidationGroup"));
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	 * SeleniumUtil.waitUntilToastMessageIsDisappeared();
	 * budgetCreateBudgetLoc.editBudgetedAmount(PropsUtil.getDataPropertyValue(
	 * "Budget_Step2_IncomeInput"), PropsUtil.getDataPropertyValue(
	 * "Budget_Step2_CategorymonthlybdgIncomeAmountlist").split(","),
	 * PropsUtil.getDataPropertyValue(
	 * "Budget_Step2_CategorymonthlybdgIncomeAmounAddCat"));
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	 * SeleniumUtil.click(budgetCreateBudgetLoc
	 * .budget_Steps2_CategoryTypeExpand(PropsUtil.getDataPropertyValue(
	 * "Budget_Step2_FlexibleInput")));
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	 * budgetCreateBudgetLoc.editBudgetedAmount(PropsUtil.getDataPropertyValue(
	 * "Budget_Step2_FlexibleInput"), PropsUtil.getDataPropertyValue(
	 * "Budget_Step2_CategorymonthlybdgFlexibleAmountlist").split(","),
	 * PropsUtil.getDataPropertyValue(
	 * "Budget_Step2_CategorymonthlybdgFlexibleAmounAddCat"));
	 * SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_DoneButton());
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible(); if
	 * (Config.appFlag.equalsIgnoreCase("app") ||
	 * Config.appFlag.equalsIgnoreCase("emulator")) {
	 * SeleniumUtil.click(budgetSummary.budget_Summery_Viewdetails()); } else {
	 * util.assertExpectedActualAmountList( util.getWebElementsValue(budgetSummary.
	 * budget_Summary_CategorySectionCatgeoryBudgetedAmt(
	 * PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))),
	 * PropsUtil.getDataPropertyValue(
	 * "Budget_Summary_EditedIncomeCategoryBudgetedAmount"),
	 * "income catgeory name is displayed"); util.assertExpectedActualAmountList(
	 * util.getWebElementsValue(budgetSummary.
	 * budget_Summary_CategorySectionCatgeorySpentAmt(
	 * PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))),
	 * PropsUtil.getDataPropertyValue(
	 * "Budget_Summary_EditedIncomeCategorySpentAmount"),
	 * "income catgeory name is displayed");
	 * 
	 * }
	 * 
	 * util.assertExpectedActualList( util.getWebElementsValue(budgetSummary.
	 * budget_Summary_CategorySectionCatgeoryName(
	 * PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))),
	 * PropsUtil.getDataPropertyValue("Budget_Summary_EditedIncomeCategoryName"),
	 * "income catgeory name is displayed"); util.assertExpectedActualAmountList(
	 * util.getWebElementsValue(budgetSummary.
	 * budget_Summary_CategorySectionCatgeoryLeftAmt(
	 * PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))),
	 * PropsUtil.getDataPropertyValue(
	 * "Budget_Summary_EditedIncomeCategoryleftAmount"),
	 * "income catgeory name is displayed");
	 * 
	 * }
	 * 
	 * @Test(description = "AT-110223:verify add income catgeory", priority = 112,
	 * dependsOnMethods = "verifySavedDetails") public void
	 * verifySummaryIncomeAddCatgeory() { budgetCreateBudgetLoc.scroolDown();
	 * Assert.assertTrue(
	 * budgetSummary.budget_Summary_CategorySectionAddCategoryIcon(
	 * PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput")).isDisplayed(),
	 * "add income catgory icon is not diplsayed"); Assert.assertEquals(
	 * budgetSummary.budget_Summary_CategorySectionAddCategoryLabel(
	 * PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput")).getText().trim(),
	 * PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategory"),
	 * "add income catgeory label is not displayed"); }
	 * 
	 * @Test(description =
	 * "AT-110224,AT-110247,AT-110252,AT-110253:verify add catgeory in summary",
	 * priority = 113, dependsOnMethods = "verifySummaryIncomeAddCatgeory") public
	 * void verifySummaryIncomeAddCategory() { SeleniumUtil.click(budgetSummary.
	 * budget_Summary_CategorySectionAddCategoryLabel(
	 * PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput")));
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible(); if
	 * (Config.appFlag.equalsIgnoreCase("app") ||
	 * Config.appFlag.equalsIgnoreCase("emulator")) {
	 * Assert.assertTrue(budgetCreateBudgetLoc.
	 * budget_Steps2_AddCategoryCloseIconMobile().isDisplayed()); //
	 * Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_LeftOverLabelMobile()
	 * .getText().trim(), //
	 * PropsUtil.getDataPropertyValue("Budget_Step2_LeftOverLabel"), "left over //
	 * label is not displayed"); //
	 * Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_LeftOverAmountMobile(
	 * ).getText(), //
	 * PropsUtil.getDataPropertyValue("Budget_Step2_LeftOverMobileAmount"),"left //
	 * over amount is not displayed"); Assert.assertEquals(budgetCreateBudgetLoc.
	 * budget_Steps2_AddCategoryCurrencyIconMobile().getText().trim(),
	 * PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategoryCurrencyIcon"),
	 * "currency icon is not displayed");
	 * 
	 * } else {
	 * Assert.assertTrue(budgetCreateBudgetLoc.budget_Steps2_AddCategoryCloseIcon().
	 * isDisplayed()); Assert.assertEquals(budgetCreateBudgetLoc.
	 * budget_Steps2_AddCategoryCurrencyIcon().getText().trim(),
	 * PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategoryCurrencyIcon"),
	 * "currency icon is not displayed");
	 * 
	 * }
	 * 
	 * Assert.assertTrue(budgetCreateBudgetLoc.budget_Steps2_AddCategoryDDIcon().
	 * isDisplayed(), "add categroy dropdown icon is not displayed");
	 * Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_AddCategoryDD().
	 * getText().trim(),
	 * PropsUtil.getDataPropertyValue("Budget_Summery_IncomeAddCategorySelection"),
	 * "add category drodown is not selected value");
	 * Assert.assertTrue(budgetCreateBudgetLoc.budget_Steps2_AddCategoryAddButton().
	 * isDisplayed());
	 * Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_AddCategoryAmount().
	 * getAttribute("value").trim(),
	 * PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategoryCurrencyAmt"),
	 * "catgeory amount is not displayed");
	 * Assert.assertEquals(budgetCreateBudgetLoc.budget_NoDataDescription().getText(
	 * ).trim(), PropsUtil.getDataPropertyValue("Budget_Summery_NoTxnDataMessage"),
	 * "no data description is ot displayed");
	 * 
	 * }
	 * 
	 * @Test(description =
	 * "AT-110234,AT-110236,AT-110239,AT-110255:verify add category trends",
	 * priority = 114, dependsOnMethods = "verifySummaryIncomeAddCategory") public
	 * void verifySummaryIncomeAddCategoryTrends() { if
	 * (Config.appFlag.equalsIgnoreCase("app") ||
	 * Config.appFlag.equalsIgnoreCase("emulator")) { util.assertExpectedActualList(
	 * util.getWebElementsValueUperCase(budgetCreateBudgetLoc.
	 * budget_Steps2_legendMonthLinkMobile()),
	 * util.revereseList(budgetCreateBudgetLoc.xAixValue(4)),
	 * "legend months are not displayed");
	 * 
	 * } //
	 * Assert.assertEquals(budgetSummary.budget_Summery_CatTrendsverticalBar().get(0
	 * ).getCssValue("color"), //
	 * PropsUtil.getDataPropertyValue("Budget_Summery_IncomeBarColr"), "income bar
	 * // color");
	 * Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_legend3monthAvg().
	 * getText().trim(),
	 * PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategoryLegendsAvg"),
	 * "last 3 mnth avg is not displayed");
	 * Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_legendcatTypeLabel().
	 * getText().trim(),
	 * PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategoryLegendCategory"
	 * ), "legends catgeory sections are not displayed");
	 * Assert.assertTrue(budgetCreateBudgetLoc.budget_Steps2_legendcatTypeLabelcolor
	 * ().isDisplayed(), "category section color is not displayed");
	 * util.assertExpectedActualList(util.getWebElementsValue(budgetCreateBudgetLoc.
	 * budget_Steps2_legendXaxis()),
	 * util.revereseList(budgetCreateBudgetLoc.xAixValue(4)),
	 * "x axix months are not displayed");
	 * util.assertExpectedActualList(util.getWebElementsValue(budgetCreateBudgetLoc.
	 * budget_Steps2_legendYaxis()),
	 * PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategoryLegendYaxix"),
	 * "y axix values are not displayed");
	 * 
	 * }
	 * 
	 * @Test(description =
	 * "AT-110229,AT-110230,AT-110231,AT-110233:verify add category amount field validation"
	 * , priority = 115, dependsOnMethods = "verifySummaryIncomeAddCategory") public
	 * void verifySummaryCatAmountValidation() { String[] amountinput =
	 * PropsUtil.getDataPropertyValue("Budget_Summery_IncomeAmountInput").split(",")
	 * ; List<String> actualErrorMessage = new ArrayList<String>(); for (int i = 0;
	 * i < amountinput.length; i++) {
	 * budgetCreateBudgetLoc.budget_Steps2_AddCategoryAmount().clear();
	 * budgetCreateBudgetLoc.budget_Steps2_AddCategoryAmount()
	 * .sendKeys(PropsUtil.getDataPropertyValue(amountinput[i]));
	 * budgetCreateBudgetLoc.budget_Steps2_AddCategoryAmount().sendKeys(Keys.TAB);
	 * actualErrorMessage.add(budgetSummary.budget_Summery_AddCategoryerorrMesg().
	 * getText()); }
	 * 
	 * util.assertExpectedActualAmountList(actualErrorMessage,
	 * PropsUtil.getDataPropertyValue("Budget_Summery_IncomeAddCategoryRemainError")
	 * , "all catgeory name is not dispalyed"); }
	 * 
	 * @Test(description =
	 * "AT-110245,AT-110246,AT-110254:verify add category close", priority = 116,
	 * dependsOnMethods = "verifySummaryIncomeAddCategory") public void
	 * verifySummaryAddCatClose() { //
	 * SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_AddCategoryDDIcon());
	 * if (Config.appFlag.equalsIgnoreCase("app") ||
	 * Config.appFlag.equalsIgnoreCase("emulator")) {
	 * SeleniumUtil.click(budgetCreateBudgetLoc.
	 * budget_Steps2_AddCategoryCloseIconMobile());
	 * Assert.assertTrue(budgetCreateBudgetLoc.
	 * budget_Steps2_AddCategoryCloseIconMobileList().size() == 0);
	 * 
	 * } else {
	 * SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_AddCategoryCloseIcon()
	 * ); SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	 * Assert.assertTrue(!SeleniumUtil.isDisplayed(budgetCreateBudgetLoc.
	 * addCategoryCloseIcon, 2)); Assert.assertFalse(budgetSummary
	 * .budget_Summary_CategorySectionAddCategoryhide(
	 * PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))
	 * .getAttribute("class")
	 * .contains(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategoryHide"
	 * ))); Assert.assertTrue(
	 * budgetSummary.budget_Summary_CategorySectionAddCategoryIcon(
	 * PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput")).isDisplayed(),
	 * "add income catgory icon is not diplsayed"); Assert.assertEquals(
	 * budgetSummary.budget_Summary_CategorySectionAddCategoryLabel(
	 * PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput")).getText().trim(),
	 * PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategory"),
	 * "add income catgeory label is not displayed");
	 * 
	 * } }
	 * 
	 * @Test(description = "AT-110225:verify remaing income category", priority =
	 * 117, dependsOnMethods = "verifySummaryAddCatClose") public void
	 * verifySummaryIncomeremaing() { SeleniumUtil.click(budgetSummary.
	 * budget_Summary_CategorySectionAddCategoryLabel(
	 * PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput")));
	 * SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_AddCategoryDDIcon());
	 * util.assertExpectedActualList(
	 * util.getWebElementsValue(budgetCreateBudgetLoc.
	 * budget_Steps2_addCatDdCategoryList()),
	 * PropsUtil.getDataPropertyValue("Budget_Summery_IncomeAddCategoryRemain"),
	 * "all catgeory name is not dispalyed");
	 * 
	 * }
	 * 
	 * @Test(description =
	 * "AT-110226,AT-110228,AT-110232,AT-110244:verify added catgeory amount in summary"
	 * , priority = 118, dependsOnMethods = "verifySummaryIncomeremaing") public
	 * void verifySummaryIncomeAddCategoryAmount() {
	 * 
	 * SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_addCatDdCategoryName(
	 * PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategoryName")));
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	 * budgetCreateBudgetLoc.budget_Steps2_AddCategoryAmount().clear();
	 * budgetCreateBudgetLoc.budget_Steps2_AddCategoryAmount()
	 * .sendKeys(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategoryAmt")
	 * );
	 * SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_AddCategoryAddButton()
	 * ); SeleniumUtil.waitUntilSpinnerWheelIsInvisible(); SeleniumUtil.waitFor(3);
	 * int catgeoryIndex = budgetSummary
	 * .budget_Summary_CategorySectionCatgeoryName(PropsUtil.getDataPropertyValue(
	 * "Budget_Step2_IncomeInput")) .size(); Assert.assertEquals( budgetSummary
	 * .budget_Summary_CategorySectionCatgeoryName(
	 * PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))
	 * .get(catgeoryIndex - 1).getText().trim(),
	 * PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategoryName"),
	 * "Selected catgeory name is not displayed"); if
	 * (Config.appFlag.equalsIgnoreCase("app") ||
	 * Config.appFlag.equalsIgnoreCase("emulator")) { Assert.assertEquals(
	 * budgetSummary .budget_Summary_CategorySectionCatgeoryLeftAmt(
	 * PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))
	 * .get(catgeoryIndex - 1).getText().trim(),
	 * PropsUtil.getDataPropertyValue("Budget_Summery_Ammountadded"),
	 * "added amount is not displayed"); } else { System.out.println(budgetSummary
	 * .budget_Summary_CategorySectionCatgeoryBudgetedAmt(
	 * PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))
	 * .get(catgeoryIndex - 1).getText()); Assert.assertEquals( budgetSummary
	 * .budget_Summary_CategorySectionCatgeoryBudgetedAmt(
	 * PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))
	 * .get(catgeoryIndex - 1).getText().trim(),
	 * PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategoryAmtadded"),
	 * "added amount is not displayed"); } }
	 * 
	 * @Test(description = "AT-110227:verify catgeory search in drop down", priority
	 * = 119, dependsOnMethods = "verifySummaryIncomeAddCategoryAmount") public void
	 * verifyIncomeAddCategorySearch() { SeleniumUtil.click(budgetSummary.
	 * budget_Summary_CategorySectionAddCategoryIcon(
	 * PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput")));
	 * SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_AddCategoryDDIcon());
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	 * budgetCreateBudgetLoc.sendKeys(budgetCreateBudgetLoc.
	 * budget_Steps2_addCatDdCategoryNameSearchField(),
	 * PropsUtil.getDataPropertyValue("Budget_Summery_SearchCat"));
	 * util.assertExpectedActualList(
	 * util.getWebElementsValue(budgetCreateBudgetLoc.
	 * budget_Steps2_addCatDdCategoryNameSearch()),
	 * PropsUtil.getDataPropertyValue("Budget_Summery_SearchCatExpected"),
	 * "searched values are displayed"); }
	 * 
	 * @Test(description = "AT-110234,AT-110236,AT-110250:verify add acteory txn",
	 * priority = 120, dependsOnMethods = "verifyIncomeAddCategorySearch") public
	 * void verifySummaryIncomeCategoryLegends() { if
	 * (Config.appFlag.equalsIgnoreCase("app") ||
	 * Config.appFlag.equalsIgnoreCase("emulator")) {
	 * SeleniumUtil.click(budgetSummary.budget_Summery_CloseCatgeoryDDMObile());
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	 * SeleniumUtil.click(budgetCreateBudgetLoc.
	 * budget_Steps2_AddCategoryCloseIconMobile());
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	 * SeleniumUtil.click(budgetSummary.
	 * budget_Summary_CategorySectionCatgeoryLeftAmt(
	 * PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput")).get(1)); } else {
	 * SeleniumUtil.click(budgetSummary.
	 * budget_Summary_CategorySectionCatgeoryBudgetedAmt(
	 * PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput")).get(1)); }
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	 * budgetSummary.deleteCategory();
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	 * SeleniumUtil.click(budgetSummary.
	 * budget_Summary_CategorySectionAddCategoryLabel(
	 * PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput")));
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	 * 
	 * SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_AddCategoryDDIcon());
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	 * SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_addCatDdCategoryName(
	 * PropsUtil.getDataPropertyValue("Budget_Summery_IncomeDeletedCategoryAdd")));
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible(); //
	 * Assert.assertEquals(budgetSummary.budget_Summery_CatTrendsverticalBar().get(0
	 * ).getCssValue("color"), //
	 * PropsUtil.getDataPropertyValue("Budget_Summery_IncomeBarColr"), "income bar
	 * // color");
	 * Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_legend3monthAvg().
	 * getText().trim(),
	 * PropsUtil.getDataPropertyValue("Budget_Summery_AddCatIncomeMonthlyAvg"),
	 * " 3 months avg is not displayed");
	 * Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_legendcatTypeLabel().
	 * getText().trim(),
	 * PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategoryLegendCategory"
	 * ), "legends category section is not displayed");
	 * Assert.assertTrue(budgetCreateBudgetLoc.budget_Steps2_legendcatTypeLabelcolor
	 * ().isDisplayed(), "catgeory section color is not dipslayed");
	 * util.assertExpectedActualList(util.getWebElementsValue(budgetCreateBudgetLoc.
	 * budget_Steps2_legendXaxis()),
	 * util.revereseList(budgetCreateBudgetLoc.xAixValue(4)),
	 * "X axix months is not displayed");
	 * util.assertExpectedActualAmountList(util.getWebElementsValue(
	 * budgetCreateBudgetLoc.budget_Steps2_legendYaxis()),
	 * PropsUtil.getDataPropertyValue("Budget_Summery_IncomeMonthlyYaxix"),
	 * "y axix amount is not displayed");
	 * 
	 * }
	 * 
	 * @Test(description =
	 * "AT-110240,AT-110241,AT-110250,AT-110256,AT-110257:verify previous month txn"
	 * , priority = 121, dependsOnMethods = "verifySummaryIncomeCategoryLegends")
	 * public void verifyIncomeCategoryprevioumthLegends() {
	 * 
	 * if (Config.appFlag.equalsIgnoreCase("app") ||
	 * Config.appFlag.equalsIgnoreCase("emulator")) {
	 * SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_legendMonthLinkMobile(
	 * ) .get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexOne"))));
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible(); } else {
	 * SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_legendBarClick()
	 * .get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexOne"))));
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible(); Assert.assertEquals(
	 * budgetCreateBudgetLoc.budget_Steps2_TxnAccountName()
	 * .get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).
	 * getText().trim(),
	 * PropsUtil.getDataPropertyValue("Budget_Step2_IncomeMonthlytxnBankName"),
	 * "transaction bank name is not dispalyed"); Assert.assertEquals(
	 * budgetCreateBudgetLoc.budget_Steps2_TxnAccountName()
	 * .get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexOne"))).
	 * getText().trim(),
	 * PropsUtil.getDataPropertyValue("Budget_Step2_IncomeMonthlytxnBankNumber"),
	 * "transaction bank number is not displayed");
	 * 
	 * } String expected =
	 * budgetCreateBudgetLoc.DateInMMMMFormate(budgetCreateBudgetLoc.
	 * getNoDaysOfMonth(-1)).trim(); Assert.assertEquals(
	 * budgetCreateBudgetLoc.budget_Steps2_TxnDateHeader()
	 * .get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).
	 * getText().trim(), expected, " transaction date header not displayed ");
	 * Assert.assertEquals( budgetCreateBudgetLoc.budget_Steps2_TxnCategory()
	 * .get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).
	 * getText().trim(),
	 * PropsUtil.getDataPropertyValue("Budget_Summery_IncomeMonthlytxnCategory"),
	 * "transaction description is not displayed"); Assert.assertEquals(
	 * budgetCreateBudgetLoc.budget_Steps2_TxnAmount()
	 * .get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).
	 * getText().trim(),
	 * PropsUtil.getDataPropertyValue("Budget_Summery_IncomeMonthlytxnBankAmount"),
	 * "transaction amount is  not displayed"); }
	 * 
	 * @Test(description = "AT-110222:verify bill section add category", priority =
	 * 124, dependsOnMethods = "verifyIncomeCategoryprevioumthLegends") public void
	 * verifySummaryBillsAddCatgeory() {
	 * 
	 * if (Config.appFlag.equalsIgnoreCase("app") ||
	 * Config.appFlag.equalsIgnoreCase("emulator")) {
	 * SeleniumUtil.click(budgetCreateBudgetLoc.
	 * budget_Steps2_AddCategoryCloseIconMobile());
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible(); } Assert.assertTrue(
	 * budgetSummary.budget_Summary_CategorySectionAddCategoryIcon(
	 * PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput")).isDisplayed(),
	 * "add income catgory icon is not diplsayed"); Assert.assertEquals(
	 * budgetSummary.budget_Summary_CategorySectionAddCategoryLabel(
	 * PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput")).getText().trim(),
	 * PropsUtil.getDataPropertyValue("Budget_Step2_BillsAddCategory"),
	 * "add income catgeory label is not displayed"); }
	 * 
	 * @Test(description =
	 * "AT-110224,AT-110234,AT-110236,AT-110237:verify biil section add category legends"
	 * , priority = 125, dependsOnMethods = "verifySummaryBillsAddCatgeory") public
	 * void verifySummaryBillsAddCategoryLegends() {
	 * SeleniumUtil.click(budgetSummary.
	 * budget_Summary_CategorySectionAddCategoryIcon(
	 * PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))); if
	 * (Config.appFlag.equalsIgnoreCase("app") ||
	 * Config.appFlag.equalsIgnoreCase("emulator")) { util.assertExpectedActualList(
	 * util.getWebElementsValueUperCase(budgetCreateBudgetLoc.
	 * budget_Steps2_legendMonthLinkMobile()),
	 * util.revereseList(budgetCreateBudgetLoc.xAixValue(4)),
	 * "legend months are not dsipalyed");
	 * 
	 * } //
	 * Assert.assertEquals(budgetSummary.budget_Summery_CatTrendsverticalBar().get(0
	 * ).getCssValue("color"), //
	 * PropsUtil.getDataPropertyValue("Budget_Summery_SpendingBarColr"), "spending
	 * // bar color");
	 * Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_legend3monthAvg().
	 * getText().trim(),
	 * PropsUtil.getDataPropertyValue("Budget_Step2_BillsAddCategoryLegendsAvg"),
	 * "bills category 3 moth avg is not displayed");
	 * Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_legendcatTypeLabel().
	 * getText().trim(),
	 * PropsUtil.getDataPropertyValue("Budget_Step2_BillsAddCategoryLegendCategory")
	 * , "bills section catgeory name");
	 * Assert.assertTrue(budgetCreateBudgetLoc.budget_Steps2_legendcatTypeLabelcolor
	 * ().isDisplayed(), "bills section catgeory color");
	 * util.assertExpectedActualList(util.getWebElementsValue(budgetCreateBudgetLoc.
	 * budget_Steps2_legendXaxis()),
	 * util.revereseList(budgetCreateBudgetLoc.xAixValue(4)),
	 * "legends x axix value is not displayed");
	 * util.assertExpectedActualList(util.getWebElementsValue(budgetCreateBudgetLoc.
	 * budget_Steps2_legendYaxis()),
	 * PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategoryLegendYaxix"),
	 * "legends y axix value is not displayed");
	 * 
	 * }
	 * 
	 * @Test(description = "AT-110221:verify flexble section add category", priority
	 * = 126, dependsOnMethods = "verifySummaryBillsAddCategoryLegends") public void
	 * verifySummaryFlexibleAddCatgeory() { if
	 * (Config.appFlag.equalsIgnoreCase("app") ||
	 * Config.appFlag.equalsIgnoreCase("emulator")) {
	 * SeleniumUtil.click(budgetCreateBudgetLoc.
	 * budget_Steps2_AddCategoryCloseIconMobile());
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible(); } Assert.assertTrue(
	 * budgetSummary.budget_Summary_CategorySectionAddCategoryIcon(
	 * PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput")).isDisplayed(),
	 * "add income catgory icon is not diplsayed"); Assert.assertEquals(
	 * budgetSummary.budget_Summary_CategorySectionAddCategoryLabel(
	 * PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput")).getText().trim(
	 * ), PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleAddCategory"),
	 * "add income catgeory label is not displayed"); }
	 * 
	 * @Test(description =
	 * "AT-110224,AT-110234,AT-110236,AT-110237:verify flexible section add category gegends"
	 * , priority = 127, dependsOnMethods = "verifySummaryFlexibleAddCatgeory")
	 * public void verifySummaryFlexibleAddCategoryLegends() {
	 * SeleniumUtil.click(budgetSummary.
	 * budget_Summary_CategorySectionAddCategoryIcon(
	 * PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"))); if
	 * (Config.appFlag.equalsIgnoreCase("app") ||
	 * Config.appFlag.equalsIgnoreCase("emulator")) { util.assertExpectedActualList(
	 * util.getWebElementsValueUperCase(budgetCreateBudgetLoc.
	 * budget_Steps2_legendMonthLinkMobile()),
	 * util.revereseList(budgetCreateBudgetLoc.xAixValue(4)),
	 * "legend months are not dsipalyed");
	 * 
	 * } //
	 * Assert.assertEquals(budgetSummary.budget_Summery_CatTrendsverticalBar().get(0
	 * ).getCssValue("color"), //
	 * PropsUtil.getDataPropertyValue("Budget_Summery_SpendingBarColr"), "spending
	 * // bar color");
	 * Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_legend3monthAvg().
	 * getText().trim(),
	 * PropsUtil.getDataPropertyValue("Budget_Step2_BillsAddCategoryLegendsAvg"),
	 * "bills category 3 moth avg is not displayed");
	 * Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_legendcatTypeLabel().
	 * getText().trim(),
	 * PropsUtil.getDataPropertyValue("Budget_Step2_BillsAddCategoryLegendCategory")
	 * , "bills section catgeory name");
	 * Assert.assertTrue(budgetCreateBudgetLoc.budget_Steps2_legendcatTypeLabelcolor
	 * ().isDisplayed(), "bills section catgeory color");
	 * util.assertExpectedActualList(util.getWebElementsValue(budgetCreateBudgetLoc.
	 * budget_Steps2_legendXaxis()),
	 * util.revereseList(budgetCreateBudgetLoc.xAixValue(4)),
	 * "legends x axix value is not displayed");
	 * util.assertExpectedActualList(util.getWebElementsValue(budgetCreateBudgetLoc.
	 * budget_Steps2_legendYaxis()),
	 * PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategoryLegendYaxix"),
	 * "legends y axix value is not displayed");
	 * 
	 * }
	 * 
	 * @Test(description =
	 * "AT-110248,AT-110282,AT-110283,AT-110284:verify 25 txn and load more",
	 * priority = 128, dependsOnMethods = "verifySummaryFlexibleAddCategoryLegends")
	 * public void verifySummary25TXn() { if (Config.appFlag.equalsIgnoreCase("app")
	 * || Config.appFlag.equalsIgnoreCase("emulator")) {
	 * SeleniumUtil.click(budgetCreateBudgetLoc.
	 * budget_Steps2_AddCategoryCloseIconMobile());
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible(); }
	 * 
	 * SeleniumUtil.click(budgetSummary
	 * .budget_Summary_CategorySectionAccordian(PropsUtil.getDataPropertyValue(
	 * "Budget_Step2_IncomeInput")) .get(0));
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	 * Assert.assertTrue(budgetCreateBudgetLoc.budget_Steps2_TxnCategory().size() ==
	 * 25, "transaction description should be displayed"); Assert.assertEquals(
	 * budgetCreateBudgetLoc.budget_Steps2_TxnAmount()
	 * .get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).
	 * getText(),
	 * PropsUtil.getDataPropertyValue("Budget_Summary_IncomeMonthlytxnBankAmount"),
	 * "transactin amount should be displayed"); if
	 * (Config.appFlag.equalsIgnoreCase("false") ||
	 * Config.appFlag.equalsIgnoreCase("web")) { Assert.assertEquals(
	 * budgetCreateBudgetLoc.budget_Steps2_TxnAccountName()
	 * .get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).
	 * getText(),
	 * PropsUtil.getDataPropertyValue("Budget_Step2_IncomeMonthlytxnBankName"),
	 * "transaction account name should be displayed"); Assert.assertEquals(
	 * budgetCreateBudgetLoc.budget_Steps2_TxnAccountName()
	 * .get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexOne"))).
	 * getText(),
	 * PropsUtil.getDataPropertyValue("Budget_Step2_IncomeMonthlytxnBankNumber"),
	 * "transaction account number should be displayed"); } Assert.assertEquals(
	 * budgetCreateBudgetLoc.budget_Steps2_TxnCategory()
	 * .get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).
	 * getText(),
	 * PropsUtil.getDataPropertyValue("Budget_Step2_IncomeMonthlytxnCategory"),
	 * "transaction account number should be displayed");
	 * Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_LoadMoreTxn().getText
	 * ().trim(), PropsUtil.getDataPropertyValue("Budget_Step2_LoanMoreTxnText"),
	 * "load more transaction is not displayed");
	 * 
	 * }
	 * 
	 * @Test(description = "AT-110249:veify more than 25 txn", priority = 129,
	 * dependsOnMethods = "verifySummary25TXn") public void verifySummary25MoreTXn()
	 * {
	 * 
	 * SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_LoadMoreTxn());
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	 * Assert.assertTrue(budgetCreateBudgetLoc.budget_Steps2_TxnCategory().size() >
	 * 25, "");
	 * Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_LoadMoreTxn().getText
	 * ().trim(), PropsUtil.getDataPropertyValue("Budget_Step2_LoanMoreTxnText"),
	 * "load more transaction is not displayed");
	 * 
	 * }
	 * 
	 * @Test(description = "AT-110242,AT-110243,AT-110258:verify edit txn", priority
	 * = 130, dependsOnMethods = "verifySummary25MoreTXn") public void
	 * verifySummaryTxnEdited() { d.navigate().refresh();
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	 * PageParser.forceNavigate("Transaction", d);
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible(); String IA_MTCategory8 =
	 * null; if
	 * (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
	 * 
	 * IA_MTCategory8 =
	 * PropsUtil.getDataPropertyValue("Budget_Step2_MTCatgeory_43");
	 * 
	 * } else {
	 * 
	 * IA_MTCategory8 = PropsUtil.getDataPropertyValue("Budget_Summery_MTCatgeory");
	 * 
	 * } if (addManualTransaction.isMoreBtnPresent()) {
	 * SeleniumUtil.click(addManualTransaction.MobileMore());
	 * SeleniumUtil.click(addManualTransaction.MobileaddManualIcon_AMT()); } else {
	 * addManualTransaction.clickMTLink(); }
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	 * addManualTransaction.createOneTimeTransaction(PropsUtil.getDataPropertyValue(
	 * "Budget_Step2_MTAmount"),
	 * PropsUtil.getDataPropertyValue("Budget_Step2_MTDescription"),
	 * PropsUtil.getDataPropertyValue("Budget_Step2_MTType"),
	 * PropsUtil.getDataPropertyValue("Budget_Step2_MTAccount"),
	 * PropsUtil.getDataPropertyValue("Budget_Step2_MTFrequency"),
	 * budgetCreateBudgetLoc.getNoDaysOfMonth(-1), IA_MTCategory8,
	 * PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummerydebitTxnNote"
	 * ), PropsUtil.getDataPropertyValue(
	 * "Budget_MonthlymaintaineceSummerydebitTxnCheck"));
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	 * PageParser.forceNavigate("Budget", d);
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible(); if
	 * (Config.appFlag.equalsIgnoreCase("app") ||
	 * Config.appFlag.equalsIgnoreCase("emulator")) {
	 * SeleniumUtil.click(budgetSummary.budget_Summery_Viewdetails());
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible(); }
	 * SeleniumUtil.click(budgetSummary.
	 * budget_Summary_CategorySectionAddCategoryIcon(
	 * PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput")));
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	 * budgetCreateBudgetLoc.updateTxn(PropsUtil.getDataPropertyValue(
	 * "Budget_Step2_MTAmountList"),
	 * PropsUtil.getDataPropertyValue("Budget_Step2_MTAmountUpdated"));
	 * SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	 * Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_legend3monthAvg().
	 * getText().trim(),
	 * PropsUtil.getDataPropertyValue("Budget_Summery_EditedIncomeMonthlyAvg"),
	 * "all catgeory 3 month avg amount is not dispalyed");
	 * 
	 * }
	 */
}
