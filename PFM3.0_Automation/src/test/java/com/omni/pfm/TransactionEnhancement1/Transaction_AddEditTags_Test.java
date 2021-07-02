package com.omni.pfm.TransactionEnhancement1;
/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sjain10
*/
import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_AddAndEditTags_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Transaction_AddEditTags_Test extends TestBase {
	Logger logger=LoggerFactory.getLogger(Transaction_AddEditTags_Test.class);
	AccountAddition accountAdd;
	Transaction_AddAndEditTags_Loc addTag;
	Add_Manual_Transaction_Loc AddManualTransaction;

	@BeforeClass (alwaysRun=true)
	public void init() {
		doInitialization("Add and Edit transction tags");

		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);
		accountAdd=new AccountAddition();
		addTag=new Transaction_AddAndEditTags_Loc(d);
		AddManualTransaction=new Add_Manual_Transaction_Loc(d);
	}

	@Test(description="Trans-01_01:login and adding account.", groups = {
	"DesktopBrowsers,sanity" }, priority = 1, enabled = true)
	public void login() throws Exception
	{
		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad();
		accountAdd.linkAccount();
		accountAdd.addAggregatedAccount("renuka21.site16441.2", "site16441.2");
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();	
	}

	@Test(description="Trans-01_02,AT-62593,AT-62597,AT-62627,AT-62628:There should be a tag button on the Add transaction pop up.", groups = {
	"DesktopBrowsers,sanity" }, priority = 2, enabled = true)
	public void verifyTagBtnInAddTransaction() {
		addTag.goToAddTags();

		logger.info("AT-62627:The text on the button should be 'Tags'.");
		Assert.assertEquals(addTag.addTagButton().getText().trim(),PropsUtil.getDataPropertyValue("TagsButtonText").trim());

		logger.info("AT-62628:If the user adds a new tag, he should not see the default tag list.");
		Assert.assertTrue(addTag.addTagIcon().isDisplayed(), "Add Tag icon is not displayed.");

		logger.info("The user should also have a search field.");
		Assert.assertTrue(addTag.addSearchTagTxtBox().isDisplayed());
	}

	@Test(description="Trans-01_03,AT-62613: By default, the user should be shown these as his default selectable tags. 1.Business 2.Vacation 3.Travel", groups = {
	"DesktopBrowsers,sanity" }, priority = 3, enabled = true)
	public void verifyByDefaultTags() {

		logger.info("############### Verify Tags present in list when no tags were added to transactions##########");
		addTag.verifyByDefaultTags();	
	}

	@Test(description="Trans-01_04,AT-62600,AT-62598: Adding tags to the existing Transactions", groups = {
	"DesktopBrowsers,sanity" }, priority = 4, enabled = true)
	public void searchingUnavailableTags() {
		SeleniumUtil.click(addTag.addSearchTagTxtBox());

		logger.info("The user should be able to type in this search field to search for tags.");
		addTag.addSearchTagTxtBox().sendKeys(PropsUtil.getDataPropertyValue("AddedTag1").trim());

		String actual=addTag.getCreateTagBtnText();
		Assert.assertTrue(addTag.createNewTagBtn().isDisplayed());
		Assert.assertEquals(addTag.createNewTagBtn().getText().trim(),actual );
	}

	@Test(description="Trans-01_05,AT-62601: On clicking on the add tag button, the tag should get added.", groups = {
	"DesktopBrowsers,sanity" }, priority = 5, enabled = true)
	public void addingTags() {
		SeleniumUtil.click(addTag.createNewTagBtn());
		Assert.assertEquals(addTag.addedTagsList().get(0).getText().trim(), PropsUtil.getDataPropertyValue("AddedTag1").trim());	
	}

	@Test(description="Trans-01_06,AT-62616,AT-62630: On clicking on the 'X' button on a selected tag, it should get deleted from his tags list.", groups = {
	"DesktopBrowsers,sanity" }, priority = 6, enabled = true)
	public void addingAndremovingAddedTag() {
		addTag.addSearchTagTxtBox().sendKeys(PropsUtil.getDataPropertyValue("AddedTag2").trim());

		logger.info("AT-62630: If the user is shown the 'Add Tag' button, on clicking on the 'Enter' key, the tag should get added.");
		addTag.addSearchTagTxtBox().sendKeys(Keys.ENTER);
		SeleniumUtil.click(addTag.addedTagsCrossBtnList().get(1));	
		Assert.assertEquals(addTag.addedTagsList().size(), Integer.parseInt(PropsUtil.getDataPropertyValue("addedTagsSize1").trim()));	
	}

	@Test(description="Trans-01_07,AT-62605: The user should not be allowed to enter duplicate tags.", groups = {
	"DesktopBrowsers,sanity" }, priority = 7, enabled = true)
	public void duplicatingTagErrorMsg() {

		addTag.addSearchTagTxtBox().sendKeys(PropsUtil.getDataPropertyValue("AddedTag1").trim());
		addTag.addSearchTagTxtBox().sendKeys(Keys.ENTER);
		Assert.assertEquals(addTag.DuplicateTagErrorMsg().getText().trim(), PropsUtil.getDataPropertyValue("DuplicateTagErrorMsg").trim());		
	}


	@Test(description="Trans-01_08,AT-62607,AT-62606: On adding any illegal characters, he should be shown an error message.", groups = {
	"DesktopBrowsers,sanity" }, priority = 8, enabled = true)
	public void verifyingTagWithSpecialChar() {

		SeleniumUtil.click(addTag.addedTagsCrossBtnList().get(0));
		SeleniumUtil.waitForPageToLoad(2000);

		logger.info("AT-62606:The user should not be allowed to enter anything but numbers and alphabets in his tag field.");

		String[] specialChar=PropsUtil.getDataPropertyValue("SpecialCharacters").split(",");
		for(int i=0;i<specialChar.length;i++) {
			addTag.addSearchTagTxtBox().clear();
			addTag.addSearchTagTxtBox().sendKeys(specialChar[i]);	
			Assert.assertEquals(addTag.DuplicateTagErrorMsg().getText().trim(), PropsUtil.getDataPropertyValue("SpecialCharactersErrorMsg").trim());		
		}
	}

	@Test(description="Trans-01_09,AT-62608,AT-62612,AT-62611: The Ghost text before the user types anything in the search box is \"Add Tags…\"", groups = {
	"DesktopBrowsers,sanity" }, priority = 9, enabled = true)
	public void selectingAddedTag() {

		addTag.addSearchTagTxtBox().clear();
		addTag.addSearchTagTxtBox().sendKeys(PropsUtil.getDataPropertyValue("AddedTag1").trim());
		logger.info("AT-62611: As soon as the user starts typing anything, there should be an 'X' icon to clear whatever he has typed.");
		Assert.assertTrue(addTag.searchTxtBoxCrossIcon().isDisplayed());

		logger.info("AT-62612: On clicking on this 'X' button, the search box should be cleared of all text and the Ghost text should be shown again.");
		addTag.addSearchTagTxtBox().clear();
		Assert.assertEquals(addTag.addSearchTagTxtBox().getAttribute("placeholder"), PropsUtil.getDataPropertyValue("SearchBoxGhostText").trim());
	}

	@Test(description="Trans-01_10: Adding multiple transaction and saving", groups = {
	"DesktopBrowsers,sanity" }, priority = 10, enabled = true)
	public void addingMultipleTagsAndSaving() {
		addTag.createTransactionWithMultipleTags("1000", "Testing", 4, 30, 1, 1, 0);

		String expected[]=PropsUtil.getDataPropertyValue("AddedTagsSequence").split(",");
		for(int i=0;i<addTag.addedTagsList().size();i++) {
			String actual=addTag.addedTagsList().get(i).getText().trim();
			Assert.assertEquals(actual, expected[i]);
		}	
	}

	@Test(description="Trans-01_11,AT-62617: Every selected tag should have an 'X' button to delete it from his list.", groups = {
	"DesktopBrowsers,sanity" }, priority = 11, enabled = true)
	public void verifyingAddedTagsCrossBtn() {

		for(int i=0;i<addTag.addedTagsCrossBtnList().size();i++) {
			Assert.assertTrue(addTag.addedTagsCrossBtnList().get(i).isDisplayed());
		}
	}

	@Test(description="Trans-01_12,AT-62614,AT-62615,AT-62595,AT-62631,AT-62610:If the user adds and saves a tag, it should be populated into the dropdown the next time he searches for a tag. ", groups = {
	"DesktopBrowsers,sanity" }, priority = 12, enabled = true)
	public void verifyingAddedTagsListAfterSave() {

		logger.info("AT-62610:The finapp should not save any tags until the user clicks on Save/Add Transaction.");
		SeleniumUtil.click(addTag.add());
		SeleniumUtil.waitForPageToLoad(2000);
		addTag.goToAddTags();

		logger.info("AT-62615:The most recently created tags should be shown on top.");
		addTag.presentTagsList();
	}

	@Test(description="Trans-01_13,AT-62609: On clicking on a tag that has already been added, the user should be shown the 'Tag name already exists' error message and the existing tag should be highlighted in red.", groups = {
	"DesktopBrowsers,sanity" }, priority = 13, enabled = true)
	public void addingDuplicateTag() {
		SeleniumUtil.click(addTag.addSearchTagTxtBox());

		logger.info("The user should be able to type in this search field to search for tags.");
		addTag.addSearchTagTxtBox().sendKeys(PropsUtil.getDataPropertyValue("AddedTag2").trim());
		addTag.addSearchTagTxtBox().sendKeys(Keys.ENTER);

		SeleniumUtil.click(addTag.addedTags().get(0));
		Assert.assertEquals(addTag.DuplicateTagErrorMsg().getText().trim(), PropsUtil.getDataPropertyValue("DuplicateTagErrorMsg").trim());		
	}

	@Test(description="Trans-01_14,AT-62604: The user should be able to click on any tag in the searched list and it should be added to his list of added tags.", groups = {
	"DesktopBrowsers,sanity" }, priority = 14, enabled = true)
	public void addingTagsFromList() {

		for(int i=1;i<addTag.addedTags().size();i++) {
			SeleniumUtil.click(addTag.addedTags().get(i));
		}
		SeleniumUtil.waitForPageToLoad(3000);
		addTag.manuallyAddedTagList();
	}

	@Test(description="Trans-01_15:AT-62629,On clicking on the 'Escape' button or clicking anywhere outside the list, the list should close.", groups = {
	"DesktopBrowsers,sanity" }, priority = 15, enabled = false)
	public void verifyEscapeFunctionality() {
		addTag.addSearchTagTxtBox().sendKeys(Keys.ESCAPE);
		Assert.assertTrue(addTag.addSearchTagTxtBox1().size()==0);
	}

	@Test(description="Trans-01_16,AT-62594: All the added tags should be shown on the UI.", groups = {
	"DesktopBrowsers,sanity" }, priority = 16, enabled = true)
	public void verifyFilterByTagDD() {
		SeleniumUtil.click(addTag.closeAddTransacPopup());
		SeleniumUtil.click(addTag.FilterByTag());

		addTag.filterByTagList();
	}

	@Test(description="Trans-01_17,AT-62603: The finapp should show all tags that have the searched text somewhere in the text.", groups = {
	"DesktopBrowsers,sanity" }, priority = 17, enabled = true)
	public void VerifyingSelectedTag() {

		addTag.verifyingTagSelectionBehaviour();
	}

	@Test(description="Trans-01_18,verify user can add tags to the existing transaction and it should reflect in tags dropdown list", groups = {
	"DesktopBrowsers,sanity" }, priority = 18, enabled = true)
	public void addingTagsInExistingTransac() {
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		addTag.addingTagsToExistingTransaction();

		addTag.goToAddTags();
		SeleniumUtil.waitForPageToLoad(1000);
		addTag.newAddedTagsList();
	}

	@Test(description="Trans-01_19,AT-62602:Searching should start as soon as the user types the first character.", groups = {
	"DesktopBrowsers,sanity" }, priority = 19, enabled = true)
	public void verifySearchedResult() {
		addTag.addSearchTagTxtBox().sendKeys(PropsUtil.getDataPropertyValue("SearchingChar"));
		Assert.assertEquals(addTag.addedTags().get(1).getText().trim(), PropsUtil.getDataPropertyValue("SearchedResult").trim());
	}


	@Test(description="Trans-01_20,AT-62599:All the tags that match the search description should should show up as a list to the user.", groups = {
	"DesktopBrowsers,sanity" }, priority = 20, enabled = true)
	 public void verifySearchedList() {
		addTag.addSearchTagTxtBox().clear();
		addTag.addSearchTagTxtBox().sendKeys(PropsUtil.getDataPropertyValue("SearchingChar1"));

		addTag.SearchedResultList();
	}

	@Test(description="Trans-01_21,AT-62632:If the user adds a tag from the account settings, it should get populated in the tags list.", groups = {
	"DesktopBrowsers,sanity" }, priority = 21, enabled = true)
	public void verifyAcntSettingTag() {
		SeleniumUtil.click(addTag.closeAddTransacPopup());
		addTag.addTagFromAcntSettings();

		PageParser.forceNavigate("Transaction", d);

		addTag.goToAddTags();
		addTag.verifyAcntSettingAddedTag();
	}
	
	@Test(description="TC - Type : Withdrawal", groups = {
	"DesktopBrowsers,sanity" }, priority = 22, enabled = true)
	public void ClickAddTransactionWithdrawalType() throws Exception
	{	
		/*SeleniumUtil.click(AddManualTransaction.addManualIcon());*/
		SeleniumUtil.click(AddManualTransaction.TransactionType());
		SeleniumUtil.click(AddManualTransaction.TtransactionList().get(1));
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(AddManualTransaction.catgorie());
		boolean incomeFalse=false;

		for(int i=0;i<AddManualTransaction.HLCcatgoryList_AMT().size();i++)
		{
			if(AddManualTransaction.HLCcatgoryList_AMT().get(i).getText().trim().equals("Income"))
			{
				Assert.assertTrue(incomeFalse);
			}
		}

	}

	@Test(description="TC - Type : Deposit", groups = {
	"DesktopBrowsers,sanity" }, priority = 23, enabled = true)
	public void ClickAddTransactionDepositType() throws Exception
	{
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(AddManualTransaction.TtransactionList().get(0));
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(AddManualTransaction.catgorie());
		boolean expenseFalse=false;

		for(int i=0;i<AddManualTransaction.HLCcatgoryList_AMT().size();i++)
		{
			if(AddManualTransaction.HLCcatgoryList_AMT().get(i).getText().trim().equals("Expense"))
			{
				Assert.assertTrue(expenseFalse);
			}
		}

	}

	@Test(description="Changing transaction type after selecting expense", groups = {
	"DesktopBrowsers,sanity" }, priority = 24, enabled = true)
	public void ClickAddTransactionWithdrawalTypeChangeType() throws Exception

	{	
		SeleniumUtil.click(AddManualTransaction.TtransactionList().get(1));
		AddManualTransaction.selectingCategory(1,1);
		SeleniumUtil.click(AddManualTransaction.TtransactionList().get(0));

		Assert.assertEquals(PropsUtil.getDataPropertyValue("GetCategoryText").trim(), AddManualTransaction.categoryTextBox().getText().trim());

	}

	@Test(description="Changing transaction type after selecting income", groups = {
	"DesktopBrowsers,sanity" }, priority = 25, enabled = true)
	public void ClickAddTransactionDepositTypeChangeType() throws Exception

	{	
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(AddManualTransaction.TtransactionList().get(0));
		AddManualTransaction.selectingCategory(3,1);
		SeleniumUtil.click(AddManualTransaction.TtransactionList().get(0));

		Assert.assertEquals(PropsUtil.getDataPropertyValue("GetCategoryText").trim(), AddManualTransaction.categoryTextBox().getText().trim());

	}
	
}