/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.TransactionEnhancement1;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.accessibility.examples.testng.webdriver.test.RunAccessibilityTest;
import com.gargoylesoftware.htmlunit.Page;

import com.omni.pfm.PageProcessor.PageParser;

import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Login.L1NodeLogin;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.TransactionEnhancement1.AddToCalendar_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Aggregated_Transaction_Details_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Layout_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Tag_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;



public class Transaction_Tag_Test extends TestBase {


	Transaction_Tag_Loc Tag;
	Add_Manual_Transaction_Loc manual;
	Aggregated_Transaction_Details_Loc agg;
	AddToCalendar_Transaction_Loc AddToCalendar;
	private static final Logger logger = LoggerFactory.getLogger(Transaction_Tag_Test.class);
	public static String userName="";
	LoginPage login;
	AccountAddition accountAdd;
	Transaction_Layout_Loc layout;
	SeleniumUtil util;
	@BeforeClass(alwaysRun = true)
	public void testInit() throws InterruptedException {
		 doInitialization("Transaction Tag");
		 TestBase.tc = TestBase.extent.startTest("Trans tags", "Login");
	       TestBase.test.appendChild(TestBase.tc);
		
	       layout=new Transaction_Layout_Loc(d);
		Tag = new Transaction_Tag_Loc(d);

		
		manual = new Add_Manual_Transaction_Loc(d);

	

		AddToCalendar = new AddToCalendar_Transaction_Loc(d);

		agg=new  Aggregated_Transaction_Details_Loc(d);
		login=new LoginPage();
        accountAdd=new AccountAddition();
        util=new SeleniumUtil();
		d.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		d.navigate().refresh();
	    SeleniumUtil.waitForPageToLoad(5000);
	    PageParser.forceNavigate("Transaction", d);
	    SeleniumUtil.waitForPageToLoad(10000);
	}

	
	@Test(description = "AT-27407,AT-68623::Verify the > icon displayed in the Tag Filter Field", priority = 2)
	public void tagTab() throws Exception {
	if(layout.isMobileFilterIconPresent())
		{ SeleniumUtil.click(layout.MobileFilterIcon());
		SeleniumUtil.waitForPageToLoad(2000);  }
       Assert.assertTrue(Tag.tagTab().isDisplayed());
	}
	
	@Test(description = "AT-27407::Verify the > icon displayed in the Tag Filter Field", priority = 3)
	public void tagIcon() throws Exception {
		Assert.assertTrue(Tag.tagIcon().isDisplayed());
	}
	@Test(description = "AT-27408,AT-68666::Verify the Tag Filter Dropdown opened after click on the > icon.", priority = 4)
	public void tagFilterTitle() throws Exception {
		SeleniumUtil.click(Tag.tagIcon());
		Assert.assertEquals(Tag.tagTitle().getText().trim(),PropsUtil.getDataPropertyValue("TagTitle"));
	}
	@Test(description = "AT-27409::Verify the Text Tag and Close icon displayed in tag dropdown header", priority = 5)
	public void tagClose() throws Exception {
		Assert.assertTrue(Tag.tagClose().isDisplayed());
	}
	@Test(description = "Verify login happenes successfully.", priority = 6, groups = { "Smoke" })
	public void tagSearchGostText() throws Exception {
		Assert.assertEquals(Tag.searchField().getAttribute("placeholder"),
				PropsUtil.getDataPropertyValue( "TagSeachTagGoast"));
	}
	@Test(description = "AT-27416,AT-68671::Verify the text No Tags Available displayed below the tag search field if user not added any tags to the transactions", priority = 7)
	public void notagValidation() throws Exception {
		Assert.assertEquals(Tag.noTagValidation().getText(), PropsUtil.getDataPropertyValue("TagNoTagMessage"));
	}
	
	@Test(description = "AT-27413,AT-68667,AT-68675::Verify the close icon displayed inside the tag search field after entering the string.", priority = 8)
	public void tagSearchClear() throws Exception {
		Tag.searchField().sendKeys(PropsUtil.getDataPropertyValue("TagTag1"));

		Assert.assertTrue(Tag.searchClear().isDisplayed());
	}
	@Test(description = "AT-27415,AT-68670::Verify the Text No Matching Tag displayed below the tag search field, if user entered tag inside the search is not matches.", priority = 9)
	public void noTagMachesMessage() throws Exception {
		Assert.assertEquals(Tag.noTagMachesValidation().getText(), PropsUtil.getDataPropertyValue("TagNoTagMessage"));
	}
	@Test(description = "AT-27414::Verify the User entered string terminates after click on the Close icon in the Tag search field.", priority = 11)
	public void clearClick() throws Exception {
		SeleniumUtil.click(Tag.searchClear());
		 Assert.assertTrue(Tag.searchField().getAttribute("value").equals(""));
	}
	
	
	

	@Test(description = "AT-27410::Verify the Tag Filter dropdown terminates after click on the close icon", priority = 12)
	public void closetag() throws Exception {
		
	if(Tag.isMobileTagBackBtnPresent()) { SeleniumUtil.click(Tag.MobiletagBack()); }
		else {
		SeleniumUtil.click(Tag.catTab());
		SeleniumUtil.click(Tag.tagIcon());
		Tag.tagClose().click();
}
        Assert.assertTrue(AddToCalendar.isElementPresent(Tag.tagTitle()));
		
	}
	
	@Test(description = "AT-58015:Verify that if there are no recently used tags then for the tag dropdown populated tags displayed should be Business, Vacation, Travel", priority = 13)
	public void verifydeafaultTagInAggregatedtransaction() throws Exception {
	if(agg.isCollapseIconPresent())
		{ SeleniumUtil.click(agg.collapsIcon().get(0)); }
		else {
		SeleniumUtil.click(agg.PendingStranctionList().get(0));
		}
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(agg.TransactionTagLink());
		SeleniumUtil.click(agg.TransactionTagField());
		SeleniumUtil.waitForPageToLoad(2000);
		String expectedTag[] = PropsUtil.getDataPropertyValue(
				"TagPredefinedTag").split(",");
		int defaultTagLize= agg.TransactionDefualtTagList().size();
		for (int i = 0; i<defaultTagLize; i++) {
			
			Assert.assertEquals((agg.TransactionDefualtTagList().get(i).getText()),
					expectedTag[i]);
		}
	}
	
	@Test(description = "AT-58015:Verify that if there are no recently used tags then for the tag dropdown populated tags displayed should be Business, Vacation, Travel", priority = 14)
	public void verifydeafaultTagInManualTRansaction() throws Exception {
		PageParser.forceNavigate("Transaction", d);
	PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad(3000);
		if(manual.isMoreBtnPresent())
		{ manual.clickAddManualTransaction(); }
		else { 
		SeleniumUtil.click(manual.addManualLink());
		}
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(manual.AddManulTransactionTag());
		SeleniumUtil.click(manual.AddManualTransactTag());
		SeleniumUtil.waitForPageToLoad(1000);
		String expectedTag[] = PropsUtil.getDataPropertyValue(
				"TagPredefinedTag").split(",");
		
		for (int i = 0; i < manual.tagList().size(); i++) {
			
			Assert.assertEquals((manual.tagList().get(i).getText()),
					expectedTag[i]);
		}
	}
	
	@Test(description = "AT-58015:Verify that if there are no recently used tags then for the tag dropdown populated tags displayed should be Business, Vacation, Travel", priority = 15)
	public void verifydeafaultTagInEnvent() throws Exception {
		SeleniumUtil.click(manual.close());
		SeleniumUtil.waitForPageToLoad(2000);
	if(agg.isCollapseIconPresent())
		{ SeleniumUtil.click(agg.collapsIcon().get(2)); }
		else {
		SeleniumUtil.click(agg.PendingStranctionList().get(2));
}
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(AddToCalendar.addcalIcon());
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(manual.AddManulTransactionTag());
		SeleniumUtil.click(manual.AddManualTransactTag());
		SeleniumUtil.waitForPageToLoad(2000);
		String expectedTag[] = PropsUtil.getDataPropertyValue(
				"TagPredefinedTag").split(",");
		
		for (int i = 0; i < manual.AddToCaltagList().size(); i++) {
			
			Assert.assertEquals((manual.AddToCaltagList().get(i).getText()),
					expectedTag[i]);
		}
	}
	
	@Test(description = "AT-58018:Verify the changes in the tags dropdown should be applicable for both Posted/Projected transactions", priority = 16)
	public void AddTagInAggTransaction() throws Exception {
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad(5000);
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad(10000);
if(agg.isCollapseIconPresent())
		{ SeleniumUtil.click(agg.collapsIcon().get(0)); }
		else {
		SeleniumUtil.click(Tag.PendingStranctionList().get(0));
		}
		SeleniumUtil.waitForPageToLoad();
	    agg.createTag(PropsUtil.getDataPropertyValue("TagTag1"));
	    SeleniumUtil.waitForPageToLoad();
	    SeleniumUtil.click(Tag.update());
	    SeleniumUtil.waitForPageToLoad();
if(agg.isCollapseIconPresent())
		{ SeleniumUtil.click(agg.collapsIcon().get(0)); }
		else {
	    SeleniumUtil.click(Tag.PendingStranctionList().get(0));
	    }
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(agg.TransactionTagLink());
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertTrue((agg.TransactionDefualtTagList().get(0).getText()).equalsIgnoreCase(PropsUtil.getDataPropertyValue("TagTag1")));
		
	
	}
	@Test(description = "AT-58011:Verify that if user click on the tag dropdown while adding the transaction then for the tags dropdwon recently used tag should display", priority = 17)
	public void verifyRecntUsedTagInManualTRansaction() throws Exception {
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad(7000);
if(manual.isMoreBtnPresent())
		{ manual.clickAddManualTransaction(); }
		else { 
		SeleniumUtil.click(manual.addManualLink());
		}
		SeleniumUtil.waitForPageToLoad(7000);
		SeleniumUtil.click(manual.AddManulTransactionTag());
		SeleniumUtil.click(manual.AddManualTransactTag());
		SeleniumUtil.waitForPageToLoad(7000);
		
			
			Assert.assertTrue((manual.tagList().get(0).getText()).equalsIgnoreCase(PropsUtil.getDataPropertyValue("TagTag1")));
		}
	

	@Test(description = "AT-58012,AT-58017:Verify that if user click on the tag dropdown while editing the transaction then for the tags dropdwon recently used tag should display", priority = 18)
	public void verifyRecentTagInEnvent() throws Exception {
		logger.info("AT-58017:Verify the changes in the tags dropdown should be applicable for Add To Calendar popup");
		SeleniumUtil.click(manual.close());
		SeleniumUtil.waitForPageToLoad(2000);
if(agg.isCollapseIconPresent())
		{ SeleniumUtil.click(agg.collapsIcon().get(2)); }
		else {
		SeleniumUtil.click(agg.PendingStranctionList().get(2));
}
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(AddToCalendar.addcalIcon());
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(manual.AddManulTransactionTag());
		SeleniumUtil.click(manual.AddManualTransactTag());
		SeleniumUtil.waitForPageToLoad(2000);
		
			
			Assert.assertTrue((manual.tagList().get(0).getText()).equalsIgnoreCase(PropsUtil.getDataPropertyValue("TagTag1"))
					);
		}
	@Test(description = "AT-27417,AT-68672::Verify the user added tags to transactions should display in the Tag Filter dropdown", priority = 19)
	public void addedTag() throws Exception {
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad(5000);
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad(10000);
		SeleniumUtil.click(Tag.PendingStranctionList().get(0));
		SeleniumUtil.waitForPageToLoad();
		String expectedTagList[]=PropsUtil.getDataPropertyValue("TagTag5List").split(",");
		SeleniumUtil.click(agg.TransactionTagLink());
		for(int i=0;i<4;i++)
		{
			agg.createTag1(expectedTagList[i]);
		}
		
		SeleniumUtil.click(Tag.update());
		SeleniumUtil.waitForPageToLoad(3000);
	if(layout.isMobileFilterIconPresent())
		{ SeleniumUtil.click(layout.MobileFilterIcon());
		SeleniumUtil.waitForPageToLoad(2000);  }
		SeleniumUtil.click(Tag.tagIcon());
		
		SeleniumUtil.waitForPageToLoad(5000);
		Assert.assertTrue(Tag.TagList().get(0).isDisplayed());
	}

	@Test(description = "AT-28880::Verify the Clear link displayed in the tag drop down field after selecting the tags.", priority = 20)
	public void clearButton() throws Exception {
		
		
		SeleniumUtil.click(Tag.TagList().get(0));
		
		SeleniumUtil.click(Tag.tagIcon());
		
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertTrue(Tag.clearTagButton().isDisplayed());
		
	}
	
	@Test(description = "AT-27425,AT-68618::Verify the user entered string in search field(Tag and Category) get cleared after click on the RESET LINK", priority = 21)
	public void resetLink() throws Exception {
		Assert.assertTrue(Tag.reset().isDisplayed());
	}
	
	
	@Test(description = "AT-28881,AT-68676::Verify the user selected tags from dropdown get cleared after click on the clear link", priority = 22)
	public void clearButtonClick() throws Exception {
		
		
      SeleniumUtil.click(Tag.clearTagButton());
		
		SeleniumUtil.waitForPageToLoad();
		if( Tag.TagList().size()==1)
		{
			Assert.assertTrue(false);
		}
		
		Assert.assertFalse(Tag.ListtagTick().get(0).getAttribute("class").contains(PropsUtil.getDataPropertyValue("TransactionGroupSelectTick")),"tag slected tag should unselect");
		//need add tick mask should clreared after cleared 
	}
	
	@Test(description = "AT-28881::Verify the user selected tags from dropdown get cleared after click on the clear link", priority = 23)
	public void tagTabValueAfterClear() throws Exception {
			if(Tag.isMobileTagBackBtnPresent()){ SeleniumUtil.click(Tag.MobiletagBack()); }
		Assert.assertEquals(Tag.tagTab().getText(),
				PropsUtil.getDataPropertyValue("TagSelect1"));
	}
	
	@Test(description = "AT-28917::Validate the Transaction filter functionality remains as is for all the recently added tag filters", priority = 24)
	public void tagSorted() throws Exception {
       String tag[] = PropsUtil.getDataPropertyValue("TagList3").split(",");
		
		SeleniumUtil.click(Tag.tagIcon());
		for (int i = 0; i < Tag.TagList().size(); i++) {
			
			 Assert.assertEquals(Tag.TagList().get(i).getText().trim().toLowerCase(), tag[i].trim().toLowerCase());
			
		}
	}
	
	@Test(description = "AT-27418,AT-68696::Verify by default 20 tags displayed in the tag filter dropdown", priority = 25)
	public void Max20TagValidation() throws Exception {
		//finapp.transactionemhancement();
		
if(agg.isCollapseIconPresent())
		{ SeleniumUtil.click(agg.collapsIcon().get(1)); }
		else {
		SeleniumUtil.click(Tag.PendingStranctionList().get(1));
		}
		SeleniumUtil.waitForPageToLoad();
		// Tag.PendingStranctionList.get(1).click();
	
		String expectedTagList[]=PropsUtil.getDataPropertyValue("TagTag6List").split(",");
		SeleniumUtil.click(agg.TransactionTagLink());
		for(int i=0;i<expectedTagList.length;i++)
		{
			agg.createTag1(expectedTagList[i]);
		}
		
		SeleniumUtil.click(Tag.update());
		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.waitForPageToLoad();
if(agg.isCollapseIconPresent())
		{ SeleniumUtil.click(agg.collapsIcon().get(2)); }
		else {
		SeleniumUtil.click(Tag.PendingStranctionList().get(2));
		}
		SeleniumUtil.waitForPageToLoad();
		// Tag.PendingStranctionList.get(2).click();
		String expectedTagList1[]=PropsUtil.getDataPropertyValue("TagTag11List").split(",");
		SeleniumUtil.click(agg.TransactionTagLink());
		for(int i=0;i<expectedTagList1.length;i++)
		{
			agg.createTag1(expectedTagList1[i]);
		}
		
		SeleniumUtil.click(Tag.update());
		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.waitForPageToLoad();
	if(agg.isCollapseIconPresent())
		{ SeleniumUtil.click(agg.collapsIcon().get(3)); }
		else {
		SeleniumUtil.click(Tag.PendingStranctionList().get(3));
}
		SeleniumUtil.waitForPageToLoad();
		// Tag.PendingStranctionList.get(3).click();
		String expectedTagList2[]=PropsUtil.getDataPropertyValue("TagTag16List").split(",");
		SeleniumUtil.click(agg.TransactionTagLink());
		for(int i=0;i<expectedTagList2.length;i++)
		{
			agg.createTag1(expectedTagList2[i]);
		}
		
		SeleniumUtil.click(Tag.update());
		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.waitForPageToLoad();
if(agg.isCollapseIconPresent())
		{ SeleniumUtil.click(agg.collapsIcon().get(4)); }
		else {
		SeleniumUtil.click(Tag.PendingStranctionList().get(4));
		}
		SeleniumUtil.waitForPageToLoad(5000);
		// Tag.PendingStranctionList.get(4).click();
		String expectedTagList3[]=PropsUtil.getDataPropertyValue("TagTag20List").split(",");
		SeleniumUtil.click(agg.TransactionTagLink());
		for(int i=0;i<expectedTagList3.length;i++)
		{
			agg.createTag1(expectedTagList3[i]);
		}
		
		SeleniumUtil.click(Tag.update());
		SeleniumUtil.waitForPageToLoad();
		if(layout.isMobileFilterIconPresent())
		{ SeleniumUtil.click(layout.MobileFilterIcon());
		SeleniumUtil.waitForPageToLoad(2000);  }
		SeleniumUtil.click(Tag.tagIcon());
		// Tag.tagIcon.click();
		SeleniumUtil.waitForPageToLoad(3000);
		boolean taglist=false;
		if (Tag.TagList1().size() == 51) {
			
			taglist=true;

		}
		Assert.assertTrue(taglist);
	}

	@Test(description = "AT-29717,AT-68679::Verify if user entered the same tags for different transactions it should display only once in tag filter dropdown", priority = 26)
	public void DuplicateDagVAlidation() throws Exception {
		int count = 0;
		for (int i = 0; i < Tag.TagList1().size(); i++) {
			if (Tag.TagList1().get(i).getText()
					.equals(PropsUtil.getDataPropertyValue("TagTag15")))
			{
				count = count + 1;
			}
		}

		if (count != 1) {
			Assert.assertTrue(false);
		}
	}
	
	@Test(description = "AT-27422,AT-68280::Verify after deleting the tags it should not reflect in tags filter dropdown", priority = 27)
	public void deletTag() throws Exception {
if(Tag.isMobileTagBackBtnPresent())
		{
			SeleniumUtil.click(Tag.MobiletagBack());
			SeleniumUtil.click(Tag.MobileFilterBackButton());
			SeleniumUtil.click(agg.collapsIcon().get(4));
		}
		else
		{
		SeleniumUtil.click(Tag.tagClose());
		// Tag.tagClose.click();

		SeleniumUtil.click(Tag.PendingStranctionList().get(0));
}
		SeleniumUtil.waitForPageToLoad(5000);
		// Tag.PendingStranctionList.get(0).click();
		//SeleniumUtil.click(agg.ShowMore);
		//SeleniumUtil.waitForPageToLoad();
		Tag.deletTag().get(0).click();
		SeleniumUtil.waitForPageToLoad(5000);
		Tag.update().click();
		SeleniumUtil.waitForPageToLoad(10000);
if(layout.isMobileFilterIconPresent())
		{ SeleniumUtil.click(layout.MobileFilterIcon());
		SeleniumUtil.waitForPageToLoad(2000);  }
		SeleniumUtil.click(Tag.tagIcon());
		SeleniumUtil.waitForPageToLoad();
		for(int i=0;i<Tag.TagList1().size();i++)
		{
		   if(Tag.TagList1().get(i).getText().toLowerCase().equals(PropsUtil.getDataPropertyValue("TagTag1").toLowerCase()))
		   {
		  SeleniumUtil.click(Tag.TagList1().get(i));
		   }
		}
		if(layout.isMobileApplyButtonPresent()) { SeleniumUtil.click(layout.MobileApplyButton());  } 
		Assert.assertEquals(Tag.noTransactionMessage().getText().trim(),PropsUtil.getDataPropertyValue("TagNoTransaction").trim());
		
		
	}
	
	@Test(description = "AT-68678verift Max 40 char in Tag Serahc", priority = 28)
	public void tagMaxValidation() throws Exception {
		if(layout.isMobileFilterIconPresent())
		{ SeleniumUtil.click(layout.MobileFilterIcon());
		SeleniumUtil.waitForPageToLoad(2000);  }
		SeleniumUtil.click(Tag.tagIcon());
		SeleniumUtil.waitForPageToLoad();
		Tag.searchField().clear();
		Tag.searchField().sendKeys(PropsUtil.getDataPropertyValue("TagTag66"));
		SeleniumUtil.waitForPageToLoad();
		if (Tag.searchField().getAttribute("value").length() != 40) {
			Assert.assertTrue(false);
		} 
		SeleniumUtil.click(Tag.tagClose());
		SeleniumUtil.click(Tag.reset());
		SeleniumUtil.waitForPageToLoad(5000);
		
		// Tag.tagClose.click();
	}

	

	

	@Test(description = "AT-68674Verify the RestButton Disabled", priority = 29)
	public void resetButtonDesable() throws Exception {
		if(Tag.isMobileTagBackBtnPresent())
		{
			SeleniumUtil.click(Tag.MobiletagBack());
			SeleniumUtil.click(Tag.reset());
			SeleniumUtil.waitForPageToLoad();
		}
		
		SeleniumUtil.click(Tag.tagTab());
		
		SeleniumUtil.waitForPageToLoad();
		
		SeleniumUtil.click(Tag.TagList().get(0));
		
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(Tag.reset());
		Assert.assertEquals(Tag.tagTab().getText(), "Tag");
	}
	

	
	@Test(description = "verify the tag order ", priority = 30)
	public void verifyTheTagOrder() throws Exception {
		// Verify that when the user deletes any tag then all the other tags
		// shift to the previous position
		// Verify that when the user deletes any tag then all the other tags
		// shift to the previous position

		
	if(Tag.isMobileFilterBackButtonPresent()){ SeleniumUtil.click(Tag.MobileFilterBackButton());  }
		
		if(manual.isMoreBtnPresent())
		{ manual.clickAddManualTransaction(); }
		else {
		SeleniumUtil.click(manual.addManualLink());
		}
		SeleniumUtil.click(manual.AddManulTransactionTag());
		manual.createtagnew(PropsUtil.getDataPropertyValue("tag1"));
		manual.createtagnew(PropsUtil.getDataPropertyValue("tag2"));
		manual.createtagnew(PropsUtil.getDataPropertyValue("tag3"));
		manual.createtagnew(PropsUtil.getDataPropertyValue("tag4"));
		manual.createtagnew(PropsUtil.getDataPropertyValue("tag5"));
		manual.createtagnew(PropsUtil.getDataPropertyValue("tag6"));
		
		String expectedTag1[] = PropsUtil.getDataPropertyValue("TagList1")
				.split(",");
		
		for (int i = 0; i < manual.unlimittag().size(); i++) {
			// actualTag1.add(manual.ListofCustomeTag.get(i).getText());
			Assert.assertEquals(manual.unlimittag().get(i).getText(),
					expectedTag1[i]);
		}

		

		
	}
	@Test(description = "AT-68677verify the tag order after delete the tag", priority = 31)
	public void verifyTheDeleteTag() throws Exception {
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(Tag.deletTag().get(0));
		// Tag.deletTag.get(0).click();

		String expectedTag2[] = PropsUtil.getDataPropertyValue("TagList2")
				.split(",");
		
		for (int i = 0; i < manual.unlimittag().size(); i++) {
			// actualTag2.add(manual.ListofCustomeTag.get(i).getText());
			Assert.assertEquals(manual.unlimittag().get(i).getText(),
					expectedTag2[i]);
		}
	}
	
	@Test(description = "AT-68696verify tag search", priority = 32)
	public void tagSearch() throws Exception {
		SeleniumUtil.click(manual.close());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(Tag.tagTab());
		Tag.searchField().clear();
		Tag.searchField().sendKeys(PropsUtil.getDataPropertyValue("Tag50"));
		util.assertActualListIgnoreCase(util.getWebElementsValue(Tag.ListtagHide()), PropsUtil.getDataPropertyValue("Tag50"), "only one should be displayed");
		
	}
	
	@Test(description = "AT-58018:Verify the changes in the tags dropdown should be applicable for both Posted/Projected transactions", priority = 33)
	public void Vrify50TagInAggTransaction() throws Exception {
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad(5000);
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad(10000);
if(agg.isCollapseIconPresent())
		{ SeleniumUtil.click(agg.collapsIcon().get(0)); }
		else {
		SeleniumUtil.click(Tag.PendingStranctionList().get(0));
		}
		SeleniumUtil.waitForPageToLoad();
	    agg.createTag(PropsUtil.getDataPropertyValue("TagTag1"));
	    SeleniumUtil.waitForPageToLoad();
	    SeleniumUtil.click(Tag.update());
	    SeleniumUtil.waitForPageToLoad();
if(agg.isCollapseIconPresent())
		{ SeleniumUtil.click(agg.collapsIcon().get(0)); }
		else { 
	    SeleniumUtil.click(Tag.PendingStranctionList().get(0));
	    }
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(agg.TransactionTagLink());
		SeleniumUtil.waitForPageToLoad(2000);
		boolean taglist=false;
		if(agg.TransactionDefualtTagList().size()==51)
		{
			taglist=true;
		}
		
		Assert.assertTrue(taglist);

	
	}
	@Test(description = "AT-58011:Verify that if user click on the tag dropdown while adding the transaction then for the tags dropdwon recently used tag should display", priority = 34)
	public void verify50TagInManualTRansaction() throws Exception {
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad(7000);
if(manual.isMoreBtnPresent())
		{ manual.clickAddManualTransaction(); }
		else {
		SeleniumUtil.click(manual.addManualLink());
		}
		SeleniumUtil.waitForPageToLoad(7000);
		SeleniumUtil.click(manual.AddManulTransactionTag());
		SeleniumUtil.click(manual.AddManualTransactTag());
		SeleniumUtil.waitForPageToLoad(7000);
		
			
		boolean taglist=false;
		if(manual.tagList().size()==51)
		{
			taglist=true;
		}
		
		Assert.assertTrue(taglist);
		}
	

	@Test(description = "AT-58012,AT-58017:Verify that if user click on the tag dropdown while editing the transaction then for the tags dropdwon recently used tag should display", priority = 35)
	public void verify50TagInEnvent() throws Exception {
		logger.info("AT-58017:Verify the changes in the tags dropdown should be applicable for Add To Calendar popup");
		SeleniumUtil.click(manual.close());
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(agg.PendingStranctionList().get(2));
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(AddToCalendar.addcalIcon());
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(manual.AddManulTransactionTag());
		SeleniumUtil.click(manual.AddManualTransactTag());
		SeleniumUtil.waitForPageToLoad(2000);
		boolean taglist=false;
		if(manual.tagList().size()==51)
		{
			taglist=true;
		}
		
		Assert.assertTrue(taglist);
		}

	

	@AfterClass public void checkAccessibility() { try { 
		  RunAccessibilityTest rat = new RunAccessibilityTest(this.getClass().getName());
	       rat.testAccessibility(d);
	 
	 } catch (Exception e) {
	 
	  } }	
	
}
