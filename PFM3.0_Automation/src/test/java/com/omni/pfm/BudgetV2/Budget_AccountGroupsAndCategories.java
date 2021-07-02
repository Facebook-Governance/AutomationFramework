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

import org.openqa.selenium.Keys;
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
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Budget_AccountGroupsAndCategories extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(Budget_AccountGroupsAndCategories.class);
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

	// Account group in step1

	@Test(description = "AT-108810,AT-108811,AT-108870,AT-108871:verify account group drop down", priority = 100)
	public void verifyAccountGroupDDFTUE() throws Exception {
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.click(budgetCreateBudgetLoc.budget_Getstart_GetStartButton());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps1_AccountGroupDropDown());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_GrpDDCancel());
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
			String dropDownClose = budgetCreateBudgetLoc.budget_Steps1_GrpDDOpen().getAttribute("class");
			SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps1_AccountGroupDropDown());
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
			Assert.assertTrue(
					dropDownClose.contains(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategoryHide")),
					"dropdwon is not closed");
		}
		Assert.assertTrue(budgetCreateBudgetLoc.budget_Steps1_GrpDDEmptIcon().isDisplayed(),
				"dropdwon icon is not displayed");
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps1_GrpDDEmptHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Step1_EmptyGroupMessageHeader"),
				"empty group message is not displayed");
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps1_GrpDDEmptDesc().getText().trim().replace("\n", " "),
				PropsUtil.getDataPropertyValue("Budget_Step1_EmptyGroupMessage"), "empoty message not displayed");
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps1_GrpDDCreate().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Step1_CreateGroupButton"),
				"create group button is not displayed");
	}

	@Test(description = "AT-108820,AT-108818:verify account is selected", priority = 101, dependsOnMethods = "verifyAccountGroupDDFTUE")
	public void verifyAccountselected() {
		SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps1_GrpDDCreate());
		String defaultDroDownvalue = budgetCreateBudgetLoc.budget_Steps1_AccountGroupDropDown().getText().trim();
		Assert.assertEquals(defaultDroDownvalue, PropsUtil.getDataPropertyValue("Budget_Step1_AccountGroupDDValue"),
				"account group drodown is not displayed with create group text");
		Assert.assertTrue(budgetCreateBudgetLoc.budget_Steps1_BudgetNameField().isDisplayed());
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
				PropsUtil.getDataPropertyValue("Budget_Step1CardAccountNo"), "Cash account number is not dispalyed");

		util.assertExpectedActualList(
				util.getWebElementsValue(budgetCreateBudgetLoc.budget_Steps1_AccountNumber(
						PropsUtil.getDataPropertyValue("Budget_Step1InvestmentContainerInput"))),
				PropsUtil.getDataPropertyValue("Budget_Step1InvestmentAccountNo"),
				"Invetsment account number is not dispalyed");
	}

	@Test(description = "AT-108820,AT-108818:verify create button", priority = 102, dependsOnMethods = "verifyAccountselected")
	public void verifyCreateButton() {
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		PageParser.forceNavigate("AccountsPage", d);
		groupView.clikcCreatgroup();
		groupView.createGroup(PropsUtil.getDataPropertyValue("Budget_Step1_CreateEducationAccGroup"),
				PropsUtil.getDataPropertyValue("Budget_Step1_CreateEducationAcc").split(","));
		groupView.createGroupMore(PropsUtil.getDataPropertyValue("Budget_Step1_CreateAccGroup").split(","),
				PropsUtil.getDataPropertyValue("Budget_Step1_CreateAccGroupAccList").split(","));
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.click(budgetCreateBudgetLoc.budget_Getstart_GetStartButton());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		String defaultDroDownvalue = budgetCreateBudgetLoc.budget_Steps1_AccountGroupDropDown().getText().trim();
		Assert.assertEquals(defaultDroDownvalue, PropsUtil.getDataPropertyValue("Budget_Step1_CreateDailyAccGroup"),
				"account group drodown is not displayed with create group text");
	}

	@Test(description = "AT-108820:verify account group name", priority = 103, dependsOnMethods = "verifyCreateButton")
	public void verifygroupnameForAcccgrp() {
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps1_BudgetGroupName().getText(),
				PropsUtil.getDataPropertyValue("Budget_Step1_CreateDailyAccGroup"),
				"created budget group name not displayed");
	}

	@Test(description = "AT-108821,AT-108819:verify eligible group ", priority = 104, dependsOnMethods = "verifyCreateButton")
	public void verifyEligibleGroup() {
		SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps1_AccountGroupDropDown()); // group name should be
																						// displayed in apabetical order
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps1_GrpDDCreategroup().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Step1_CreateGroupButton1"), "");
		util.assertExpectedActualList(util.getWebElementsValue(budgetCreateBudgetLoc.budget_Steps1_GrpDDName()),
				PropsUtil.getDataPropertyValue("Budget_Step1_GroupDDGroups"), "all eligible group is not displayed");
	}

	@Test(description = "AT-108894:verify 25 txn", priority = 105, dependsOnMethods = "verifyEligibleGroup")
	public void createMedicalBudget() {
		SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps1_GrpDDCreategroup());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		budgetCreateBudgetLoc.sendKeys(budgetCreateBudgetLoc.budget_Steps1_BudgetNameField(),
				PropsUtil.getDataPropertyValue("Budget_Step1_CreateMedicalBudget"));
		SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps1_NextButton());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.click(budgetCreateBudgetLoc
				.budget_Steps2_CategoryDetailsExpand(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))
				.get(0));
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
	}

	@Test(description = "AT-108893:verify transaction edited", priority = 106)
	public void verifyTxnEdited() {
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Transaction", d);
		String IA_MTCategory8 = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {

			IA_MTCategory8 = PropsUtil.getDataPropertyValue("Budget_Step2_MTCatgeory_43");

		} else {

			IA_MTCategory8 = PropsUtil.getDataPropertyValue("Budget_Step2_MTCatgeory");

		}
		if (addManualTransaction.isMoreBtnPresent()) {
			SeleniumUtil.click(addManualTransaction.MobileMore());
			SeleniumUtil.click(addManualTransaction.MobileaddManualIcon_AMT());
		} else {
			addManualTransaction.clickMTLink();
		}
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		addManualTransaction.createOneTimeTransaction(PropsUtil.getDataPropertyValue("Budget_Step2_MTAmount"),
				PropsUtil.getDataPropertyValue("Budget_Step2_MTDescription"),
				PropsUtil.getDataPropertyValue("Budget_Step2_MTType"),
				PropsUtil.getDataPropertyValue("Budget_Step2_MTAccount"),
				PropsUtil.getDataPropertyValue("Budget_Step2_MTFrequency"), budgetCreateBudgetLoc.getNoDaysOfMonth(-1),
				IA_MTCategory8, PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummerydebitTxnNote"),
				PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummerydebitTxnCheck"));
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.click(budgetCreateBudgetLoc.budget_Getstart_GetStartButton());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps1_NextButton());
		SeleniumUtil.waitFor(3);
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.click(budgetCreateBudgetLoc
				.budget_Steps2_CategoryDetailsExpand(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))
				.get(1));
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		budgetCreateBudgetLoc.updateTxn(PropsUtil.getDataPropertyValue("Budget_Step2_MTAmountList"),
				PropsUtil.getDataPropertyValue("Budget_Step2_MTAmountUpdated"));
		if (Config.appFlag.equalsIgnoreCase("false") || Config.appFlag.equalsIgnoreCase("web")) {
			Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_legend3monthAvg().getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeMonthlyAvgUpdated"),
					"all catgeory 3 month avg amount is not dispalyed");
		}
		Assert.assertEquals(
				budgetCreateBudgetLoc
						.budget_Steps2_CategoryMonthlyAgAmount(
								PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))
						.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexOne"))).getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_IncomeTxnEditeAvgAmt"),
				"all catgeory 3 month  amount is not dispalyed");
		Assert.assertEquals(
				budgetCreateBudgetLoc
						.budget_Steps2_CategoryMonthlyBudgetAmt(
								PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))
						.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexOne"))).getAttribute("value"),
				PropsUtil.getDataPropertyValue("Budget_Step2_IncomeTxnEditeBdgAmt"),
				"all catgeory Budgeted amount is not dispalyed");

	}

	@Test(description = "AT-108822:verify group is deleted", priority = 107, dependsOnMethods = "verifyTxnEdited")
	public void verifydeletedBudgetGroup() {
		SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_DoneButton());
		SeleniumUtil.waitFor(2);
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		PageParser.forceNavigate("AccountGroups", d);
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.click(accountGroupSetting
				.AccGroupSetting_GroupListName(PropsUtil.getDataPropertyValue("Budget_Step1_CreateMedicalBudget")));
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		accountGroupSetting.deleteAccountGroup();
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps1_AccountGroupDropDown());

		Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps1_GrpDDCreategroup().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Step1_CreateGroupButton1"), "");
		util.assertExpectedActualList(util.getWebElementsValue(budgetCreateBudgetLoc.budget_Steps1_GrpDDName()),
				PropsUtil.getDataPropertyValue("Budget_Step1_GroupDDGroups"), "");

	}

	@Test(description = "AT-108717:verify no account message", priority = 108)
	public void verifyNoAccountMessage() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		Assert.assertEquals(budgetCreateBudgetLoc.budget_NoDataHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_NoDataHeader"), " no data headre is not displayed");
		Assert.assertEquals(budgetCreateBudgetLoc.budget_NoDataDescription().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_NoDescription"), "no data description");
	}

	@Test(description = "AT-108905:verify no catgeory popup", priority = 109, dependsOnMethods = "verifyNoAccountMessage")
	public void verifyNoCatgeoryDataPopUp() throws Exception {

		accountAddition.linkAccount();
		accountAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("Budget_BudgetSummery_Data9_Site"),
				PropsUtil.getDataPropertyValue("Budget_BudgetSummery_Data9_Password"));

		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		budgetCreateBudgetLoc.createBudgetGroup(PropsUtil.getDataPropertyValue("Budget_HouseHoldingBudget"));
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_PopupHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_NoCategorytHeader"),
				"no category header is not displayed");
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_PopupBody().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_NoCategoryBodymsg"),
				"no category popup body message is not displayed");
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_PopupButton().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_NoCategoryOkButton"), "ok button is not displayed");
		Assert.assertTrue(budgetCreateBudgetLoc.budget_Steps2_PopupCloseIcon().isDisplayed(),
				"close icon is not displayed");
	}

	@Test(description = "AT-108804:verify link account in step1", priority = 110, dependsOnMethods = "verifyNoCatgeoryDataPopUp")
	public void verifyLinkAccount() throws Exception {
		SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_PopupButton());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		if (budgetCreateBudgetLoc.isbudget_Steps2_BackButtonMobile()) {
			SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_BackButtonMobile());
		} else {
			SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_BackButton());
		}
		SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps1_AccountGroupDropDown());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps1_GrpDDCreategroup());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();

		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_linkAccountMobile());
		} else {
			SeleniumUtil.click(budgetCreateBudgetLoc.budget_Getstart_LinkAccountButton());
		}
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		accountAddition.addAggregatedAccount2(PropsUtil.getDataPropertyValue("Budget_BudgetSummery_Data14_Site"),
				PropsUtil.getDataPropertyValue("Budget_BudgetSummery_Data14_Password"));
		SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps1_LinAccountClose());
		SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps1_LinAccountDone());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		String[] cashAccount = PropsUtil.getDataPropertyValue("Budget_Step1_CreateGroupUpdatedCashAccount").split(",");
		List<String> cashActualValue = new ArrayList<String>();
		cashActualValue = util.getWebElementsValue(budgetCreateBudgetLoc
				.budget_Steps1_AccountNumber(PropsUtil.getDataPropertyValue("Budget_Step1BankContainerInput")));
		for (int i = 0; i < cashAccount.length; i++) {
			util.isAnyMatchInList(cashActualValue, cashAccount[i]);
		}
		String[] cardAccount = PropsUtil.getDataPropertyValue("Budget_Step1_CreateGroupUpdatedCardAccount").split(",");
		List<String> cardActualValue = new ArrayList<String>();
		cardActualValue = util.getWebElementsValue(budgetCreateBudgetLoc
				.budget_Steps1_AccountNumber(PropsUtil.getDataPropertyValue("Budget_Step1CardContainerInput")));
		for (int i = 0; i < cardAccount.length; i++) {
			util.isAnyMatchInList(cardActualValue, cardAccount[i]);
		}
		String[] InvestmentAccount = PropsUtil.getDataPropertyValue("Budget_Step1_CreateGroupUpdatedInvstAccount")
				.split(",");
		List<String> investmentActualValue = new ArrayList<String>();
		investmentActualValue = util.getWebElementsValue(budgetCreateBudgetLoc
				.budget_Steps1_AccountNumber(PropsUtil.getDataPropertyValue("Budget_Step1InvestmentContainerInput")));
		for (int i = 0; i < InvestmentAccount.length; i++) {
			util.isAnyMatchInList(investmentActualValue, InvestmentAccount[i]);
		}
	}

	@Test(description = "AT-108904:verify save details in summary", priority = 111, dependsOnMethods = "verifyLinkAccount")
	public void verifySavedDetails() throws Exception {

		budgetCreateBudgetLoc.createGroup(PropsUtil.getDataPropertyValue("Budget_Step2_SummaryValidationGroup"));
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.waitUntilToastMessageIsDisappeared();
		budgetCreateBudgetLoc.editBudgetedAmount(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"),
				PropsUtil.getDataPropertyValue("Budget_Step2_CategorymonthlybdgIncomeAmountlist").split(","),
				PropsUtil.getDataPropertyValue("Budget_Step2_CategorymonthlybdgIncomeAmounAddCat"));
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.click(budgetCreateBudgetLoc
				.budget_Steps2_CategoryTypeExpand(PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput")));
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		budgetCreateBudgetLoc.editBudgetedAmount(PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"),
				PropsUtil.getDataPropertyValue("Budget_Step2_CategorymonthlybdgFlexibleAmountlist").split(","),
				PropsUtil.getDataPropertyValue("Budget_Step2_CategorymonthlybdgFlexibleAmounAddCat"));
		SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_DoneButton());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			SeleniumUtil.click(budgetSummary.budget_Summery_Viewdetails());
		} else {
			util.assertExpectedActualAmountList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryBudgetedAmt(
							PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))),
					PropsUtil.getDataPropertyValue("Budget_Summary_EditedIncomeCategoryBudgetedAmount"),
					"income catgeory name is displayed");
			util.assertExpectedActualAmountList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeorySpentAmt(
							PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))),
					PropsUtil.getDataPropertyValue("Budget_Summary_EditedIncomeCategorySpentAmount"),
					"income catgeory name is displayed");

		}

		util.assertExpectedActualList(
				util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryName(
						PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))),
				PropsUtil.getDataPropertyValue("Budget_Summary_EditedIncomeCategoryName"),
				"income catgeory name is displayed");
		util.assertExpectedActualAmountList(
				util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryLeftAmt(
						PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))),
				PropsUtil.getDataPropertyValue("Budget_Summary_EditedIncomeCategoryleftAmount"),
				"income catgeory name is displayed");

	}

	@Test(description = "AT-110223:verify add income catgeory", priority = 112, dependsOnMethods = "verifySavedDetails")
	public void verifySummaryIncomeAddCatgeory() {
		budgetCreateBudgetLoc.scroolDown();
		Assert.assertTrue(
				budgetSummary.budget_Summary_CategorySectionAddCategoryIcon(
						PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput")).isDisplayed(),
				"add income catgory icon is not diplsayed");
		Assert.assertEquals(
				budgetSummary.budget_Summary_CategorySectionAddCategoryLabel(
						PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput")).getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategory"),
				"add income catgeory label is not displayed");
	}

	@Test(description = "AT-110224,AT-110247,AT-110252,AT-110253:verify add catgeory in summary", priority = 113, dependsOnMethods = "verifySummaryIncomeAddCatgeory")
	public void verifySummaryIncomeAddCategory() {
		SeleniumUtil.click(budgetSummary.budget_Summary_CategorySectionAddCategoryLabel(
				PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput")));
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			Assert.assertTrue(budgetCreateBudgetLoc.budget_Steps2_AddCategoryCloseIconMobile().isDisplayed());
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
				PropsUtil.getDataPropertyValue("Budget_Summery_IncomeAddCategorySelection"),
				"add category drodown is not selected value");
		Assert.assertTrue(budgetCreateBudgetLoc.budget_Steps2_AddCategoryAddButton().isDisplayed());
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_AddCategoryAmount().getAttribute("value").trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategoryCurrencyAmt"),
				"catgeory amount is not displayed");
		Assert.assertEquals(budgetCreateBudgetLoc.budget_NoDataDescription().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Summery_NoTxnDataMessage"),
				"no data description is ot displayed");

	}

	@Test(description = "AT-110234,AT-110236,AT-110239,AT-110255:verify add category trends", priority = 114, dependsOnMethods = "verifySummaryIncomeAddCategory")
	public void verifySummaryIncomeAddCategoryTrends() {
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			util.assertExpectedActualList(
					util.getWebElementsValueUperCase(budgetCreateBudgetLoc.budget_Steps2_legendMonthLinkMobile()),
					util.revereseList(budgetCreateBudgetLoc.xAixValue(4)), "legend months are not displayed");

		}
		// Assert.assertEquals(budgetSummary.budget_Summery_CatTrendsverticalBar().get(0).getCssValue("color"),
		// PropsUtil.getDataPropertyValue("Budget_Summery_IncomeBarColr"), "income bar
		// color");
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

	@Test(description = "AT-110229,AT-110230,AT-110231,AT-110233:verify add category amount field validation", priority = 115, dependsOnMethods = "verifySummaryIncomeAddCategory")
	public void verifySummaryCatAmountValidation() {
		String[] amountinput = PropsUtil.getDataPropertyValue("Budget_Summery_IncomeAmountInput").split(",");
		List<String> actualErrorMessage = new ArrayList<String>();
		for (int i = 0; i < amountinput.length; i++) {
			budgetCreateBudgetLoc.budget_Steps2_AddCategoryAmount().clear();
			budgetCreateBudgetLoc.budget_Steps2_AddCategoryAmount()
					.sendKeys(PropsUtil.getDataPropertyValue(amountinput[i]));
			budgetCreateBudgetLoc.budget_Steps2_AddCategoryAmount().sendKeys(Keys.TAB);
			actualErrorMessage.add(budgetSummary.budget_Summery_AddCategoryerorrMesg().getText());
		}

		util.assertExpectedActualAmountList(actualErrorMessage,
				PropsUtil.getDataPropertyValue("Budget_Summery_IncomeAddCategoryRemainError"),
				"all catgeory name is not dispalyed");
	}

	@Test(description = "AT-110245,AT-110246,AT-110254:verify add category close", priority = 116, dependsOnMethods = "verifySummaryIncomeAddCategory")
	public void verifySummaryAddCatClose() {
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_AddCategoryCloseIconMobile());
			Assert.assertTrue(budgetCreateBudgetLoc.budget_Steps2_AddCategoryCloseIconMobileList().size() == 0);

		} else {
			SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_AddCategoryCloseIcon());
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
			Assert.assertTrue(!SeleniumUtil.isDisplayed(budgetCreateBudgetLoc.addCategoryCloseIcon, 2));
			Assert.assertFalse(budgetSummary
					.budget_Summary_CategorySectionAddCategoryhide(
							PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))
					.getAttribute("class")
					.contains(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategoryHide")));
			Assert.assertTrue(
					budgetSummary.budget_Summary_CategorySectionAddCategoryIcon(
							PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput")).isDisplayed(),
					"add income catgory icon is not diplsayed");
			Assert.assertEquals(
					budgetSummary.budget_Summary_CategorySectionAddCategoryLabel(
							PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput")).getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategory"),
					"add income catgeory label is not displayed");

		}
	}

	@Test(description = "AT-110225:verify remaing income category", priority = 117, dependsOnMethods = "verifySummaryAddCatClose")
	public void verifySummaryIncomeremaing() {
		SeleniumUtil.click(budgetSummary.budget_Summary_CategorySectionAddCategoryLabel(
				PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput")));
		SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_AddCategoryDDIcon());
		util.assertExpectedActualList(
				util.getWebElementsValue(budgetCreateBudgetLoc.budget_Steps2_addCatDdCategoryList()),
				PropsUtil.getDataPropertyValue("Budget_Summery_IncomeAddCategoryRemain"),
				"all catgeory name is not dispalyed");

	}

	@Test(description = "AT-110226,AT-110228,AT-110232,AT-110244:verify added catgeory amount in summary", priority = 118, dependsOnMethods = "verifySummaryIncomeremaing")
	public void verifySummaryIncomeAddCategoryAmount() {

		SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_addCatDdCategoryName(
				PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategoryName")));
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		budgetCreateBudgetLoc.budget_Steps2_AddCategoryAmount().clear();
		budgetCreateBudgetLoc.budget_Steps2_AddCategoryAmount()
				.sendKeys(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategoryAmt"));
		SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_AddCategoryAddButton());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.waitFor(3);
		int catgeoryIndex = budgetSummary
				.budget_Summary_CategorySectionCatgeoryName(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))
				.size();
		Assert.assertEquals(
				budgetSummary
						.budget_Summary_CategorySectionCatgeoryName(
								PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))
						.get(catgeoryIndex - 1).getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategoryName"),
				"Selected catgeory name is not displayed");
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			Assert.assertEquals(
					budgetSummary
							.budget_Summary_CategorySectionCatgeoryLeftAmt(
									PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))
							.get(catgeoryIndex - 1).getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_Summery_Ammountadded"), "added amount is not displayed");
		} else {
			System.out.println(budgetSummary
					.budget_Summary_CategorySectionCatgeoryBudgetedAmt(
							PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))
					.get(catgeoryIndex - 1).getText());
			Assert.assertEquals(
					budgetSummary
							.budget_Summary_CategorySectionCatgeoryBudgetedAmt(
									PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))
							.get(catgeoryIndex - 1).getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategoryAmtadded"),
					"added amount is not displayed");
		}
	}

	@Test(description = "AT-110227:verify catgeory search in drop down", priority = 119, dependsOnMethods = "verifySummaryIncomeAddCategoryAmount")
	public void verifyIncomeAddCategorySearch() {
		SeleniumUtil.click(budgetSummary.budget_Summary_CategorySectionAddCategoryIcon(
				PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput")));
		SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_AddCategoryDDIcon());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		budgetCreateBudgetLoc.sendKeys(budgetCreateBudgetLoc.budget_Steps2_addCatDdCategoryNameSearchField(),
				PropsUtil.getDataPropertyValue("Budget_Summery_SearchCat"));
		util.assertExpectedActualList(
				util.getWebElementsValue(budgetCreateBudgetLoc.budget_Steps2_addCatDdCategoryNameSearch()),
				PropsUtil.getDataPropertyValue("Budget_Summery_SearchCatExpected"), "searched values are displayed");
	}

	@Test(description = "AT-110234,AT-110236,AT-110250:verify add acteory txn", priority = 120, dependsOnMethods = "verifyIncomeAddCategorySearch")
	public void verifySummaryIncomeCategoryLegends() {
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			SeleniumUtil.click(budgetSummary.budget_Summery_CloseCatgeoryDDMObile());
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
			SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_AddCategoryCloseIconMobile());
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
			SeleniumUtil.click(budgetSummary.budget_Summary_CategorySectionCatgeoryLeftAmt(
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput")).get(1));
		} else {
			SeleniumUtil.click(budgetSummary.budget_Summary_CategorySectionCatgeoryBudgetedAmt(
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput")).get(1));
		}
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		budgetSummary.deleteCategory();
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.click(budgetSummary.budget_Summary_CategorySectionAddCategoryLabel(
				PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput")));
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();

		SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_AddCategoryDDIcon());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_addCatDdCategoryName(
				PropsUtil.getDataPropertyValue("Budget_Summery_IncomeDeletedCategoryAdd")));
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_legend3monthAvg().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Summery_AddCatIncomeMonthlyAvg"),
				" 3 months avg is not displayed");
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_legendcatTypeLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_IncomeAddCategoryLegendCategory"),
				"legends category section is not displayed");
		Assert.assertTrue(budgetCreateBudgetLoc.budget_Steps2_legendcatTypeLabelcolor().isDisplayed(),
				"catgeory section color is not dipslayed");
		util.assertExpectedActualList(util.getWebElementsValue(budgetCreateBudgetLoc.budget_Steps2_legendXaxis()),
				util.revereseList(budgetCreateBudgetLoc.xAixValue(4)), "X axix months is not displayed");
		util.assertExpectedActualAmountList(util.getWebElementsValue(budgetCreateBudgetLoc.budget_Steps2_legendYaxis()),
				PropsUtil.getDataPropertyValue("Budget_Summery_IncomeMonthlyYaxix"), "y axix amount is not displayed");

	}

	@Test(description = "AT-110240,AT-110241,AT-110250,AT-110256,AT-110257:verify previous month txn", priority = 121, dependsOnMethods = "verifySummaryIncomeCategoryLegends")
	public void verifyIncomeCategoryprevioumthLegends() {

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
		String expected = budgetCreateBudgetLoc.DateInMMMMFormate(
				budgetCreateBudgetLoc.getNoDaysOfMonth(-1) + budgetCreateBudgetLoc.getNoDaysOfMonth(-2)).trim();
		Assert.assertEquals(
				budgetCreateBudgetLoc.budget_Steps2_TxnDateHeader()
						.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).getText().trim(),
				expected, " transaction date header not displayed ");
		Assert.assertEquals(
				budgetCreateBudgetLoc.budget_Steps2_TxnCategory()
						.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Summery_IncomeMonthlytxnCategory"),
				"transaction description is not displayed");
		Assert.assertEquals(
				budgetCreateBudgetLoc.budget_Steps2_TxnAmount()
						.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Summery_IncomeMonthlytxnBankAmount"),
				"transaction amount is  not displayed");
	}

	@Test(description = "AT-110222:verify bill section add category", priority = 124, dependsOnMethods = "verifyIncomeCategoryprevioumthLegends")
	public void verifySummaryBillsAddCatgeory() {

		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_AddCategoryCloseIconMobile());
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		}
		Assert.assertTrue(
				budgetSummary.budget_Summary_CategorySectionAddCategoryIcon(
						PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput")).isDisplayed(),
				"add income catgory icon is not diplsayed");
		Assert.assertEquals(
				budgetSummary.budget_Summary_CategorySectionAddCategoryLabel(
						PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput")).getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_BillsAddCategory"),
				"add income catgeory label is not displayed");
	}

	@Test(description = "AT-110224,AT-110234,AT-110236,AT-110237:verify biil section add category legends", priority = 125, dependsOnMethods = "verifySummaryBillsAddCatgeory")
	public void verifySummaryBillsAddCategoryLegends() {
		SeleniumUtil.click(budgetSummary.budget_Summary_CategorySectionAddCategoryIcon(
				PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput")));
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

	}

	@Test(description = "AT-110221:verify flexble section add category", priority = 126, dependsOnMethods = "verifySummaryBillsAddCategoryLegends")
	public void verifySummaryFlexibleAddCatgeory() {
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_AddCategoryCloseIconMobile());
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		}
		Assert.assertTrue(
				budgetSummary.budget_Summary_CategorySectionAddCategoryIcon(
						PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput")).isDisplayed(),
				"add income catgory icon is not diplsayed");
		Assert.assertEquals(
				budgetSummary.budget_Summary_CategorySectionAddCategoryLabel(
						PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput")).getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleAddCategory"),
				"add income catgeory label is not displayed");
	}

	@Test(description = "AT-110224,AT-110234,AT-110236,AT-110237:verify flexible section add category gegends", priority = 127, dependsOnMethods = "verifySummaryFlexibleAddCatgeory")
	public void verifySummaryFlexibleAddCategoryLegends() {
		SeleniumUtil.click(budgetSummary.budget_Summary_CategorySectionAddCategoryIcon(
				PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput")));
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

	}

	@Test(description = "AT-110248,AT-110282,AT-110283,AT-110284:verify 25 txn and load more", priority = 128, dependsOnMethods = "verifySummaryFlexibleAddCategoryLegends")
	public void verifySummary25TXn() {
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_AddCategoryCloseIconMobile());
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		}

		SeleniumUtil.click(budgetSummary
				.budget_Summary_CategorySectionAccordian(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))
				.get(0));
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		Assert.assertTrue(budgetCreateBudgetLoc.budget_Steps2_TxnCategory().size() == 25,
				"transaction description should be displayed");
		Assert.assertEquals(
				budgetCreateBudgetLoc.budget_Steps2_TxnAmount()
						.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).getText(),
				PropsUtil.getDataPropertyValue("Budget_Summary_IncomeMonthlytxnBankAmount"),
				"transactin amount should be displayed");
		if (Config.appFlag.equalsIgnoreCase("false") || Config.appFlag.equalsIgnoreCase("web")) {
			Assert.assertEquals(
					budgetCreateBudgetLoc.budget_Steps2_TxnAccountName()
							.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).getText(),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeMonthlytxnBankName"),
					"transaction account name should be displayed");
			Assert.assertEquals(
					budgetCreateBudgetLoc.budget_Steps2_TxnAccountName()
							.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexOne"))).getText(),
					PropsUtil.getDataPropertyValue("Budget_Step2_IncomeMonthlytxnBankNumber"),
					"transaction account number should be displayed");
		}
		Assert.assertEquals(
				budgetCreateBudgetLoc.budget_Steps2_TxnCategory()
						.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))).getText(),
				PropsUtil.getDataPropertyValue("Budget_Step2_IncomeMonthlytxnCategory"),
				"transaction account number should be displayed");
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_LoadMoreTxn().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_LoanMoreTxnText"),
				"load more transaction is not displayed");

	}

	@Test(description = "AT-110249:veify more than 25 txn", priority = 129, dependsOnMethods = "verifySummary25TXn")
	public void verifySummary25MoreTXn() {

		SeleniumUtil.click(budgetCreateBudgetLoc.budget_Steps2_LoadMoreTxn());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		Assert.assertTrue(budgetCreateBudgetLoc.budget_Steps2_TxnCategory().size() > 25, "");
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_LoadMoreTxn().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Step2_LoanMoreTxnText"),
				"load more transaction is not displayed");

	}

	@Test(description = "AT-110242,AT-110243,AT-110258:verify edit txn", priority = 130, dependsOnMethods = "verifySummary25MoreTXn")
	public void verifySummaryTxnEdited() {
		d.navigate().refresh();
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		String IA_MTCategory8 = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {

			IA_MTCategory8 = PropsUtil.getDataPropertyValue("Budget_Step2_MTCatgeory_43");

		} else {

			IA_MTCategory8 = PropsUtil.getDataPropertyValue("Budget_Summery_MTCatgeory");

		}
		if (addManualTransaction.isMoreBtnPresent()) {
			SeleniumUtil.click(addManualTransaction.MobileMore());
			SeleniumUtil.click(addManualTransaction.MobileaddManualIcon_AMT());
		} else {
			addManualTransaction.clickMTLink();
		}
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		addManualTransaction.createOneTimeTransaction(PropsUtil.getDataPropertyValue("Budget_Step2_MTAmount"),
				PropsUtil.getDataPropertyValue("Budget_Step2_MTDescription"),
				PropsUtil.getDataPropertyValue("Budget_Step2_MTType"),
				PropsUtil.getDataPropertyValue("Budget_Step2_MTAccount"),
				PropsUtil.getDataPropertyValue("Budget_Step2_MTFrequency"), budgetCreateBudgetLoc.getNoDaysOfMonth(-1),
				IA_MTCategory8, PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummerydebitTxnNote"),
				PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummerydebitTxnCheck"));
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			SeleniumUtil.click(budgetSummary.budget_Summery_Viewdetails());
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		}
		SeleniumUtil.click(budgetSummary.budget_Summary_CategorySectionAddCategoryIcon(
				PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput")));
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		budgetCreateBudgetLoc.updateTxn(PropsUtil.getDataPropertyValue("Budget_Step2_MTAmountList"),
				PropsUtil.getDataPropertyValue("Budget_Step2_MTAmountUpdated"));
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_legend3monthAvg().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Summery_EditedIncomeMonthlyAvg"),
				"all catgeory 3 month avg amount is not dispalyed");

	}
}
