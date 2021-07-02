/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Basavaraj 
*/
package com.omni.pfm.BudgetV2;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.BudgetEnhancement.Budget_Enhancement_Loc;
import com.omni.pfm.pages.BudgetV2.New_Budget_App_Loc;
import com.omni.pfm.pages.BudgetV2.TestingBudgetLoc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.Networth.NetWorth_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Budget_DeleteBudget_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(Budget_DeleteBudget_Test.class);

	AccountAddition accAddition;

	NetWorth_Loc featureTour;
	Budget_Enhancement_Loc budget;
	TestingBudgetLoc checkpgt;
	New_Budget_App_Loc Bbudget;

	@BeforeClass(alwaysRun = true)
	public void inti() throws Exception {
		doInitialization("Budget");
		featureTour = new NetWorth_Loc(d);
		budget = new Budget_Enhancement_Loc(d);
		checkpgt = new TestingBudgetLoc(d);
		Bbudget = new New_Budget_App_Loc(d);
		accAddition=new AccountAddition();
		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad();
		accAddition.linkAccount();
		SeleniumUtil.waitForPageToLoad();

		Assert.assertTrue(accAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("DagSiteUserName"),PropsUtil.getDataPropertyValue("DatSitePassword")));
		 
		SeleniumUtil.waitForPageToLoad();

		logger.info(">>>>> Navigating to Budget..");
		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitForPageToLoad();
	}

	@Test (description = "AT-110182:check the landing page is New Budget", priority= 1, enabled = true)
	
	public void checkPageName() throws Exception{
		
		String ActualTitle = Bbudget.NewBudgetPageName().getText().trim();
		String ExpectedTitle = PropsUtil.getDataPropertyValue("NewBudgetPageTitle");
		Assert.assertEquals(ActualTitle, ExpectedTitle, "Not landed on the right page");
    }
	
	@Test (description = "AT-110182:Check if user landing to Budget Summary page and see the Budget Title", priority= 2, enabled = true)
	public void CheckPageTitle() throws Exception{
		 SeleniumUtil.click(Bbudget.GetStartedBtn());
		 SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(Bbudget.EnterName());
		SeleniumUtil.waitForPageToLoad();
		Bbudget.EnterName().sendKeys(PropsUtil.getDataPropertyValue("GroupName").trim());
		SeleniumUtil.click(Bbudget.NextBtn());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(Bbudget.DoneBtn());
		SeleniumUtil.waitForPageToLoad();
		String ActualLandingPage = Bbudget.BudgetSummaryPage().getText().trim();
		String ExpectedLandingPage = PropsUtil.getDataPropertyValue("BudgetPageTitle2");
		Assert.assertEquals(ActualLandingPage, ExpectedLandingPage, "Not landed on the Summary page");
	}
	
	@Test (description = "AT-110185:check Popup Header", priority= 3, enabled = true,dependsOnMethods="CheckPageTitle")
	public void PopupHeadertext() throws Exception {
		
		SeleniumUtil.click(Bbudget.MoreOption());
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(Bbudget.DeleteBudget());
		String ActualHeader = Bbudget.PopupHeader().getText().trim();
		String ExpectedHeader = PropsUtil.getDataPropertyValue("DeleteConfirmationHeader");
		Assert.assertEquals(ActualHeader, ExpectedHeader, "Delete popup header not shown");
	}
	
	@Test (description = "AT-110186:check popup message1", priority= 4, enabled = true,dependsOnMethods="PopupHeadertext")
	public void PopupTextMessage1() throws Exception {
		SeleniumUtil.waitForPageToLoad();
		String ActualMessage = Bbudget.PopupBodyMessage1().getText().trim();
		String ExpectedMessage = PropsUtil.getDataPropertyValue("PopupBodyMessage1");
		Assert.assertEquals(ActualMessage, ExpectedMessage, "Delete popup body message 1 is not correct");
	}
	
	@Test (description = "AT-110186:check popup message2", priority= 5, enabled = true,dependsOnMethods="PopupHeadertext")
	public void PopupTextMessage2() throws Exception {
		String ActualMessage2 = Bbudget.PopupBodyMessage2().getText().trim();
		String ExpectedMessage2 = PropsUtil.getDataPropertyValue("PopupBodyMessage2");
		Assert.assertEquals(ActualMessage2, ExpectedMessage2, "Delete popup body message 2 is not correct");
	}
	
	@Test (description = "AT-110187,AT-110188,AT-110189:check for Cancel and Delete Button", priority= 6, enabled = true,dependsOnMethods="PopupHeadertext")
	public void checkCancelbutton() throws Exception {
		Assert.assertEquals(Bbudget.checkDeleteText().getText().trim(), PropsUtil.getDataPropertyValue("DeleteTextValidation"), "Delete Button text is different");
		
		Assert.assertEquals(Bbudget.checkCancelText().getText().trim(), PropsUtil.getDataPropertyValue("CancelTextValidation"), "Cancel Button Text is different");
	}
	
	@Test (description = "AT-110187:Click Cancel", priority= 7, enabled = true,dependsOnMethods="PopupHeadertext")
	public void clickCancelbutton() throws Exception {
		if (Bbudget.CheckCancelButton().isDisplayed()) {
			SeleniumUtil.click(Bbudget.CheckCancelButton());
		}
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(Bbudget.PopupHeaderList().size()==0,"If user click on cancel, popup size must be zero");	
	}
		
	@Test (description = "AT-110182:Click on Close icon and then Verify the elements in the Group dropdown", priority = 8, enabled = true,dependsOnMethods="clickCancelbutton")
	public void verifyGroupName() throws Exception{
		SeleniumUtil.click(Bbudget.MoreOption());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(Bbudget.DeleteBudget());
		SeleniumUtil.waitForPageToLoad();
		Bbudget.closeIcone().click();
		Assert.assertTrue(Bbudget.PopupHeaderList().size()==0,"If user click on cancel, popup size must be zero");

		Assert.assertEquals(Bbudget.CreatedGroupName().getText().trim(), PropsUtil.getDataPropertyValue("GroupName"));
	}
	
	/*@Test(description = "AT-110182:Verify the names of the Groups", priority = 15, enabled = true)
	public void VerifyAllNames() throws Exception{
		
	}*/
	
	@Test (description = "AT-110182,AT-110190:click on the More options, Delete the Budget and check for Toast message", priority= 9, enabled = true,dependsOnMethods="verifyGroupName")
	public void DeleteCurrentGroup() throws Exception {
		SeleniumUtil.click(Bbudget.MoreOption());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(Bbudget.DeleteBudget());
		SeleniumUtil.waitForPageToLoad();
		if(Bbudget.CheckDeleteButton().isDisplayed()) {
			SeleniumUtil.click(Bbudget.CheckDeleteButton());
		}else {
			System.out.println("Delete Button not visiable or Available");
		}
		SeleniumUtil.waitForPageToLoad(500);
		Assert.assertEquals(Bbudget.BudgetDeletedTostMessage().getText().trim(),  PropsUtil.getDataPropertyValue("BudgetDeletedTostMessage"));
	}
	
	@Test(description = "AT-110192:check the landing page after the Budget is deleted successfully", priority = 10, enabled = true,dependsOnMethods="DeleteCurrentGroup")
	public void ValidationLandingPage() throws Exception{
		SeleniumUtil.waitForPageToLoad();
		//SeleniumUtil.click(Bbudget.GetStartedBtn());
		//Assert.assertEquals(Bbudget.Step1String().getText().trim(), PropsUtil.getDataPropertyValue("Step1Stng1"));
		//SeleniumUtil.waitForPageToLoad();
		
		Assert.assertEquals(Bbudget.CreateBudgetHeader().getText().trim(),PropsUtil.getDataPropertyValue("NewBudgetPageTitle"),"Could not delete the Budget Successfully");
		logger.info("Successfully Deleted the budget and now you can create new budget");
	}
	
	@Test(description = "AT-110192:check the Deleted Group name", priority = 11, enabled = true,dependsOnMethods="ValidationLandingPage")
	public void DeletedGroupName() throws Exception{
		String ActualName = Bbudget.GroupName().getText().trim();
		String ExpectedName = PropsUtil.getDataPropertyValue("GroupName");
		Assert.assertEquals(ActualName, ExpectedName, "Deleted Group name is not correct");
	}

	@Test (description = "AT-110193:check if the GroupName existing in Account Groups page", priority = 12, enabled = true,dependsOnMethods="DeletedGroupName")
	public void BudgetGroupName() throws Exception{
		SeleniumUtil.click(Bbudget.SettingsDropdown());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(Bbudget.AccountGroupName1());
		SeleniumUtil.waitForPageToLoad();
		String ActualGroupName = Bbudget.CheckGroupName().getText().trim();
		String ExpectedGroupName = PropsUtil.getDataPropertyValue("GroupName2");
		Assert.assertEquals(ActualGroupName, ExpectedGroupName, "Group Name shown is not the one which was deleted");
	}		
}