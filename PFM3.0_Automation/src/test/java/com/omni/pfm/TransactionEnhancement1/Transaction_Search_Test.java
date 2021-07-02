/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.TransactionEnhancement1;


import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.accessibility.examples.testng.webdriver.test.RunAccessibilityTest;
import com.omni.pfm.PageProcessor.PageParser;

import com.omni.pfm.pages.TransactionEnhancement1.AddToCalendar_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Aggregated_Transaction_Details_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Search_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Transaction_Search_Test extends TestBase {
	
	Transaction_Search_Loc search;
	AddToCalendar_Transaction_Loc addCal;
	Add_Manual_Transaction_Loc manual;
	Aggregated_Transaction_Details_Loc agge;
	private static final Logger logger = LoggerFactory.getLogger(Transaction_Search_Test.class);
	public static String userName="";
	@BeforeClass(alwaysRun = true)

	public void testInit() throws InterruptedException {
		doInitialization("Transaction Search");
		
		search = new Transaction_Search_Loc(d);
		addCal = new AddToCalendar_Transaction_Loc(d);
		manual = new Add_Manual_Transaction_Loc(d);
		d.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		PageParser.forceNavigate("Transaction", d);
		  
	    SeleniumUtil.waitForPageToLoad(5000);
	    PageParser.forceNavigate("Transaction", d);
	    SeleniumUtil.waitForPageToLoad(5000);
	
	}

	@Test(description = "verify search transaction field goast text", priority = 2)

	public void searchGoastText() throws Exception {
		Assert.assertEquals(search.allSearch().getAttribute("placeholder"),PropsUtil.getDataPropertyValue("SearchGoastMark") );
	}
	@Test(description = "verify all search icon", priority = 3,dependsOnMethods={"searchGoastText"})

	public void allSearchIcon() throws Exception {
		search.allSearch().sendKeys(PropsUtil.getDataPropertyValue("SearchAll"));
		search.allSearch().sendKeys(Keys.TAB);
		Assert.assertTrue(search.allSearchIcon().isDisplayed());
	}
@Test(description = "verify all search lable", priority = 4,dependsOnMethods={"allSearchIcon"})

	public void allSearchLable() throws Exception {
	//Verify once user enters 3 characters inside  the search field, it should initiate auto  searching 
		//Verify the "Search Icon" displayed inside the search field
		//Verify the Ghost Text "Search Transactions" displayed in the Search field.
	
		
		
		Assert.assertEquals(addCal.getAtributeVAlue(search.allSearch().getAttribute("id")),PropsUtil.getDataPropertyValue("SearchAll") );
		
	}
@Test(description = "verify all search result icon", priority = 5, dependsOnMethods={"allSearchIcon"})

public void allSearchResultIcon() throws Exception {
	SeleniumUtil.waitForPageToLoad();
	Assert.assertTrue(search.allSearchResultIcon().isDisplayed());
}
	@Test(description = "search transaction with two char", priority = 6,dependsOnMethods="allSearchResultIcon")

	public void allSearch2char() throws Exception {
		//Verify Auto Searching should not happen if user enters 1 or 2 characters
		
		search.allSearch().clear();
		SeleniumUtil.click(search.closeIcon());
		SeleniumUtil.waitForPageToLoad();
		search.allSearch().sendKeys(PropsUtil.getDataPropertyValue("SearchAll1"));
		boolean searchpresent=false;
		if(search.allSearchResultIconList().get(0).getAttribute("class").contains("hide"))
		{
			searchpresent=true;	
		}
				Assert.assertTrue(searchpresent);
	}

	@Test(description = "verify search result text", priority = 7,dependsOnMethods="allSearch2char")

	public void SearhcResultText() throws Exception {
		//Verify the text "Search results for and "Searched String" displayed next to the search field after searching in the Medium and Large Screen
		//Verify the "Close Icon" displayed next to the searched string after searching
        //Verify the searched string got closed after click on the close icon
		search.allSearch().sendKeys(PropsUtil.getDataPropertyValue( "SearchAll"));
		search.allSearch().sendKeys(Keys.TAB);
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(search.searchLabel().getText(), PropsUtil.getDataPropertyValue("SearchLabel"));
		
		
		
		
	}
	@Test(description = "verify search result close icon", priority = 8, dependsOnMethods={"SearhcResultText"})

	public void SearhcResultclose() throws Exception {
		Assert.assertTrue(search.closeIcon().isDisplayed());
	}
	@Test(description = "seacrh text should cleared when click o close icon", priority = 9, dependsOnMethods={"SearhcResultclose"} )

	public void SearhcResultcloseClick() throws Exception {
      SeleniumUtil.click(search.closeIcon());
		
		boolean expected =false; 
			if(search.allSearchResultIconList().get(0).getAttribute("class").contains("hide"))
			{
				expected=true;
			}
		Assert.assertTrue(expected);
    
	}
	@Test(description = "verify search transaction with description", priority = 10,dependsOnMethods="SearhcResultcloseClick")

	public void searchDescription() throws Exception {
		//Verify all the transactions should reflect in the landing screen based on the user searched 

		
		SeleniumUtil.waitForPageToLoad();
if(manual.isMoreBtnPresent())
		{ manual.clickAddManualTransaction(); }
		else { 
		SeleniumUtil.click(manual.addManualLink());
		}
		SeleniumUtil.waitForPageToLoad();
		manual.createTransactionWithAllDetails(PropsUtil.getDataPropertyValue("AttachmentTransactionAmount"), PropsUtil.getDataPropertyValue("AttachmentTransactionDescription"), 1, 0, 1, 1, PropsUtil.getDataPropertyValue("AttchmentTransactionTag"), PropsUtil.getDataPropertyValue("AttachmentTransactionNote"), PropsUtil.getDataPropertyValue("AttachmentTransactioncheck"));
		SeleniumUtil.waitForPageToLoad();
		/*addCal.PendingStranctionList.get(0).click();
		search.uploadImage("src/test/resources/Attachments/networth.png");*/
		search.allSearch().clear();
		search.allSearch().sendKeys(PropsUtil.getDataPropertyValue("AttachmentTransactionDescription"));
		
		SeleniumUtil.waitForPageToLoad(5000);
		if(search.listOfTransaction().size()!=1)
		{
			Assert.assertTrue(false);
		}
		
	}
	@Test(description = "verify search transaction with note", priority = 11,  dependsOnMethods={"searchDescription"})

	public void searchNote() throws Exception {
		SeleniumUtil.click(search.closeIcon());
		SeleniumUtil.waitForPageToLoad(2000);
		search.allSearch().sendKeys(PropsUtil.getDataPropertyValue("AttachmentTransactionNote"));
		SeleniumUtil.waitForPageToLoad(5000);
		if(search.listOfTransaction().size()!=1)
		{
			Assert.assertTrue(false);
		}
	}
	@Test(description = "verify search transaction with check", priority = 12, dependsOnMethods={"searchDescription"})

	public void searchCheck() throws Exception {
		SeleniumUtil.click(search.closeIcon());
		SeleniumUtil.waitForPageToLoad(2000);
		search.allSearch().sendKeys(PropsUtil.getDataPropertyValue("AttachmentTransactioncheck"));
		SeleniumUtil.waitForPageToLoad(5000);
		if(search.listOfTransaction().size()!=1)
		{
			Assert.assertTrue(false);
		}
	}
	@Test(description = "verify search transaction with tag", priority = 13,dependsOnMethods={"searchDescription"})

	public void searchTag() throws Exception {
		SeleniumUtil.click(search.closeIcon());
		SeleniumUtil.waitForPageToLoad(2000);
		search.allSearch().sendKeys(PropsUtil.getDataPropertyValue("AttchmentTransactionTag"));
		SeleniumUtil.waitForPageToLoad(5000);
		if(search.listOfTransaction().size()!=1)
		{
			Assert.assertTrue(false);
		}

	}
	@Test(description = "refresh transaction search", priority = 14,dependsOnMethods="searchDescription")

	public void searchrefresh() throws Exception {
		SeleniumUtil.click(search.closeIcon());
		SeleniumUtil.waitForPageToLoad(2000);
		if(search.listOfTransaction().size()==1)
		{
			Assert.assertTrue(false);
		}
	}
	@AfterClass
    public void checkAccessibility() {
           try {
                  RunAccessibilityTest rat = new RunAccessibilityTest(this.getClass().getName());
                  rat.testAccessibility(d);
                  
           } catch (Exception e) {

           }
    }
}
