/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
package com.omni.pfm.DemoSite;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.omni.pfm.pages.DemoSite.CreateServiceRequest_Loc;
import com.omni.pfm.pages.DemoSite.DeleteMyAccount_Loc;
import com.omni.pfm.pages.DemoSite.LandingScreen_Loc;
import com.omni.pfm.pages.DemoSite.SignUp_Loc;
import com.omni.pfm.pages.DemoSite.SuccessPage_Loc;
import com.omni.pfm.pages.DemoSite.UserProfilePersonalInfo_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.DemoSiteDBUtil;
import com.omni.pfm.utility.DemoSiteUtil;
import com.omni.pfm.utility.Email;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;





public class CreateServiceRequest_Test extends TestBase{

	CreateServiceRequest_Loc createServiceRequest;
	SuccessPage_Loc SuccessPage;
    DeleteMyAccount_Loc DeleteAccount;	
	SignUp_Loc SignUp;
	LandingScreen_Loc LandingScreen;
    UserProfilePersonalInfo_Loc PersonalInfo;
	CreateServiceRequest_Loc CreateServiceRequest;
	DemoSiteDBUtil DBUtil;
	DemoSiteUtil DSUtil;
	DemoSiteUtil demoUtil;
	Email mail;


	String gmailId = "";
	String gmailPassword = "";
	
    @BeforeClass(alwaysRun = true)

	public void testInit() throws Exception {
		
		doInitialization("Create Service Request Test");
		
		CreateServiceRequest = new CreateServiceRequest_Loc(d);
		LandingScreen = new LandingScreen_Loc(d);
		PersonalInfo = new UserProfilePersonalInfo_Loc(d);

		DBUtil = new DemoSiteDBUtil(gmailId, gmailId, gmailId);
		DSUtil = new DemoSiteUtil();
		demoUtil = new DemoSiteUtil();
		mail=new Email();

		
		gmailId = PropsUtil.getDataPropertyValue("LoginEn_email");
		gmailPassword = PropsUtil.getDataPropertyValue( "LoginEn_Password");

}
	
	

	@Test(priority = 1, description = "Sign Up into the service request finapp")

	public void signup() {

		DemoSiteUtil.launchNodeURL();

		SeleniumUtil.waitForPageToLoad();


		CreateServiceRequest.typeTextField(LandingScreen.userID(), SignUp_Test.userid);

		CreateServiceRequest.typeTextField(LandingScreen.password(), PropsUtil.getDataPropertyValue("UserProfile_Password1"));

		CreateServiceRequest.typeTextField(LandingScreen.finappID(), "10003700");


	
		SeleniumUtil.click(PersonalInfo.loginButton());
		
		SeleniumUtil.waitForPageToLoad(10000);

		CreateServiceRequest.navigateToServiceRequests();

		SeleniumUtil.click(LandingScreen.createNewButton());
		SeleniumUtil.waitForPageToLoad();

	}

	@Test(priority = 2, description = "AT-14199,AT-14186,AT-14187,AT-14190:Verifying Text and label values in the page")

	public void verifyText() {

		SoftAssert s_assert = new SoftAssert();

		SeleniumUtil.waitForPageToLoad();

		s_assert.assertEquals(CreateServiceRequest.backText().getText().toLowerCase().trim(),

	    PropsUtil.getDataPropertyValue("CreateSR_BacktoLink").toLowerCase().trim(),"Back to Link Text");

		s_assert.assertEquals(CreateServiceRequest.headerText().getText(),

		PropsUtil.getDataPropertyValue("CreateSR_Header"), "Header Text");

		s_assert.assertEquals(CreateServiceRequest.text1().getText(),

		PropsUtil.getDataPropertyValue("CreateSR_Text1"), "Text Message 1");

		s_assert.assertEquals(CreateServiceRequest.text2().getText(),

		PropsUtil.getDataPropertyValue("CreateSR_Text2"), "Text Message 2");

		s_assert.assertEquals(CreateServiceRequest.labelSubject().getText(),

		PropsUtil.getDataPropertyValue("CreateSR_LabelSubject"), "Label Subject");

		s_assert.assertEquals(CreateServiceRequest.labelDescription().getText(),

		PropsUtil.getDataPropertyValue("CreateSR_LabelDescription"), "Label Description");

		s_assert.assertEquals(CreateServiceRequest.textBoxSubject().getAttribute("placeholder"),

		PropsUtil.getDataPropertyValue("CreateSR_PlaceholderSubject"), "Placeholder Subject");

		s_assert.assertEquals(CreateServiceRequest.textBoxDescription().getAttribute("placeholder"),

   	   PropsUtil.getDataPropertyValue("CreateSR_PlaceholderDescription"), "Placeholder Description");

		s_assert.assertEquals(CreateServiceRequest.labelSystemDetails().getText(),

		 
		PropsUtil.getDataPropertyValue("CreateSR_LabelSystemDetails"), "Label System Details");

		s_assert.assertEquals(CreateServiceRequest.dropdownSystemDetails().getText(),

		PropsUtil.getDataPropertyValue("CreateSR_DropdownSystemDetails"), "Dropdown System Details");

       SeleniumUtil.click(CreateServiceRequest.dropdownSystemDetails());
		
       s_assert.assertEquals(CreateServiceRequest.browserText().getText(),

		
       PropsUtil.getDataPropertyValue("CreateSR_BrowserText"), "Browser Text");

		s_assert.assertEquals(CreateServiceRequest.oSText().getText(),


	    PropsUtil.getDataPropertyValue("CreateSR_OSText"), "OS Text");

		SeleniumUtil.waitForPageToLoad();

		s_assert.assertAll();

	}

	@Test(priority = 3, description = "AT-15412,AT-85111,AT-85112:Verifying if Back link is working")

	public void verifyBackToLink() {

		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(CreateServiceRequest.backText());
		
		SeleniumUtil.waitForPageToLoad(40000);

		Assert.assertTrue(LandingScreen.createNewButton().isDisplayed(), "Back link is not working");

		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(LandingScreen.createNewButton());
		
		SeleniumUtil.waitForPageToLoad();

	}

	@Test(priority = 4, description = "AT-15413:Verifying blank fields shows appropriate error message")

	public void verifyEmptyErrorMessage() throws InterruptedException {

		SoftAssert s_assert = new SoftAssert();

		// add wait
		SeleniumUtil.waitForPageToLoad(10000);

		SeleniumUtil.click(CreateServiceRequest.submitButton());
		
		s_assert.assertEquals(CreateServiceRequest.errorEmptySubject().getText(),
	   
		PropsUtil.getDataPropertyValue("CreateSR_EmptyError"), "Empty Subject error");

		
		s_assert.assertEquals(CreateServiceRequest.errorEmptyDescription().getText(),

		PropsUtil.getDataPropertyValue("CreateSR_EmptyError"), "Empty Description error");

		s_assert.assertAll();


	}

	@Test(priority = 5, description = "AT-14191,AT-14189:Verifying after maximum allowed characters typing is restricted")

	public void verifyMaximumCharacters() {

		SoftAssert s_assert = new SoftAssert();

		
			SeleniumUtil.waitForPageToLoad(10000);
		
		CreateServiceRequest.typeTextField(CreateServiceRequest.textBoxSubject(),

		PropsUtil.getDataPropertyValue("CreateSR_LongSubject"));

		CreateServiceRequest.typeTextField(CreateServiceRequest.textBoxDescription(),
			
		PropsUtil.getDataPropertyValue("CreateSR_LongDescription"));
		
		SeleniumUtil.waitForPageToLoad();

		String Subject = CreateServiceRequest.textBoxSubject().getAttribute("value");

		s_assert.assertEquals(Subject.length(), 100, "Length of Subject");

		SeleniumUtil.waitForPageToLoad();

		String Description = CreateServiceRequest.textBoxDescription().getAttribute("value");

		s_assert.assertEquals(Description.length(), 1024, "Length of Description");

		s_assert.assertAll();

	}

	@Test(priority = 6, description = "AT-15413:Checking only space is not taken")

    	public void verifyOnlySpaceNotValid() {

		SoftAssert s_assert = new SoftAssert();

	
		SeleniumUtil.waitForPageToLoad(10000);
		
		CreateServiceRequest.textBoxSubject().clear();
        
		CreateServiceRequest.textBoxDescription().clear();
      
		CreateServiceRequest.typeTextField(CreateServiceRequest.textBoxSubject(), "");

		CreateServiceRequest.typeTextField(CreateServiceRequest.textBoxDescription(), "");

		SeleniumUtil.click(CreateServiceRequest.submitButton());
		
		SeleniumUtil.waitForPageToLoad();

		String ErrorSubject = CreateServiceRequest.errorEmptySubject().getText();

		String ErrorDescription = CreateServiceRequest.errorEmptyDescription().getText();

		s_assert.assertEquals(ErrorSubject, PropsUtil.getDataPropertyValue("CreateSR_SpaceError"),"Subject only space error");

		s_assert.assertEquals(ErrorDescription, PropsUtil.getDataPropertyValue("CreateSR_SpaceError"),"Description only space error");

		s_assert.assertAll();

	}

	@Test(priority = 7, description = "AT-14200:Checking system details captured is valid")

    	public void verifySystemDetails() {
 
		SoftAssert s_assert = new SoftAssert();

		SeleniumUtil.waitForElementVisible(d, "backText", "DemoSite",null);

		SeleniumUtil.waitForPageToLoad(10000);
		
		SeleniumUtil.click(CreateServiceRequest.backText());
		
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(LandingScreen.createNewButton());
		
		SeleniumUtil.waitForPageToLoad();

		// till here

		SeleniumUtil.click(CreateServiceRequest.dropdownSystemDetails());
		// Get Browser name and version.

		s_assert.assertEquals(DemoSiteUtil.getBrowserVersion().toLowerCase(),
				
		CreateServiceRequest.browserVersion().getText().toLowerCase(),"Captured Browser Info");

		// Get OS name.

		s_assert.assertEquals(DemoSiteUtil.getOSType().toLowerCase(), CreateServiceRequest.oSType().getText().toLowerCase(),"Captured OS Info");

		SeleniumUtil.waitForPageToLoad();
		
		s_assert.assertAll();


	}

	@Test(priority = 8, description = "AT-14191:Checking if the character count is working properly")

	public void verifyCharacterCount() {

		SeleniumUtil.waitForElementVisible(d, "TextBoxDescription", "DemoSite",null);
		SeleniumUtil.waitForPageToLoad(10000);
		
		CreateServiceRequest.typeTextField(CreateServiceRequest.textBoxDescription(), 
				PropsUtil.getDataPropertyValue("CreateSR_Description"));

		String CharacterCount = PropsUtil.getDataPropertyValue("CreateSR_Description").length()
				+ PropsUtil.getDataPropertyValue("CreateSR_CharacterCount");

		Assert.assertEquals(CreateServiceRequest.characterCount().getText(), CharacterCount,
				"character count of description textbox");

	}

	@Test(priority = 9, description = " AT-85084 :verifying add attachment header")
	public void verifyAddAttachment() {
		
		String actualText = CreateServiceRequest.getAttachementTitle();

		String expectedUpdateText = PropsUtil.getDataPropertyValue("CreateSR_AddAttachmentHdr");

		Assert.assertEquals(actualText, expectedUpdateText);

	}

	@Test(priority = 10, description = " AT-85085 :verifying add attachment Link")
	public void verifyAddAttachmentLink() {
		
		String actualText = CreateServiceRequest.addAttachmentLink().getText().trim();

		String expectedUpdateText = PropsUtil.getDataPropertyValue("CreateSR_AddAttachmentLink");

		Assert.assertEquals(actualText, expectedUpdateText);

	}

	@Test(priority = 11, description = " AT-85086 :verifying add attachment Icon")
	public void verifyAddAttachmentIcon() {
		
		Assert.assertTrue(CreateServiceRequest.addAttachmentIconIsDisplyed());

	}

	@Test(priority = 12, description = " AT-85087 :verifying file format texts")
	public void verifyFileFormats() {
		
		String actualText = CreateServiceRequest.fileFormatText().getText().trim();

		String expectedUpdateText = PropsUtil.getDataPropertyValue("CreateSR_FileText");

		String actualText1 = CreateServiceRequest.fileFormatText2().getText().trim();

		String expectedUpdateText2 = PropsUtil.getDataPropertyValue("CreateSR_FileText1");

		String actualText2 = CreateServiceRequest.fileFormatText1().getText().trim();
		
		String expectedUpdateText1 = PropsUtil.getDataPropertyValue("CreateSR_FileFormat");
		
		Assert.assertEquals(actualText1, expectedUpdateText2);
		
		Assert.assertEquals(actualText, expectedUpdateText);
		
		Assert.assertEquals(actualText2, expectedUpdateText1);

		

	}

	@Test(priority = 13, description = " AT-85088,AT-85095: click on add attachment and uploading file")
	public void verifyAddingAttachment() {
		
		String attachWordFile = System.getProperty("user.dir");
		
		String path = attachWordFile + "\\src\\main\\resources\\Attachments\\Demosite.doc";
		
		CreateServiceRequest.fileAttachment().sendKeys(path);
		
		SeleniumUtil.waitForPageToLoad(5000);

	}

	@Test(priority = 14, description = "  : Attaching same file and verifying")
	public void verifyAddingSameFile() {
		
		String attachWordFile = System.getProperty("user.dir");
		
		String path = attachWordFile + "\\src\\main\\resources\\Attachments\\Demosite.doc";
		
		CreateServiceRequest.fileAttachment().sendKeys(path);
		
		SeleniumUtil.waitForPageToLoad(5000);

		Assert.assertTrue(CreateServiceRequest.screenShotIcon().get(1).isDisplayed());
		
		Assert.assertTrue(CreateServiceRequest.deleteIcon().get(1).isDisplayed());

		String actualText = CreateServiceRequest.fileName().get(1).getText().trim();

		String expectedUpdateText = PropsUtil.getDataPropertyValue("CreateSR_FileName1");

		Assert.assertEquals(actualText, expectedUpdateText);
	}

	@Test(priority = 15, description = "AT-85103  : Verifying delete icon ")
	public void verifyDeleteIcon() {
		
		Assert.assertTrue(CreateServiceRequest.deleteIcon().get(0).isDisplayed());
		
		Assert.assertTrue(CreateServiceRequest.deleteIcon().get(1).isDisplayed());

	}

	@Test(priority = 16, description = "AT-85104 : Verifying screenshot image icon ")
	public void verifyScreenshotIcon() {

		Assert.assertTrue(CreateServiceRequest.isScreenShotDisplayed(1));
		
		Assert.assertTrue(CreateServiceRequest.isScreenShotDisplayed(2));

	}

	@Test(priority = 17, description = " AT-85094, : Verifying screenshot Error icon ")
	public void verifyScreenshotErrorIcon() {
		
		String attachWordFile = System.getProperty("user.dir");
		
		String path = attachWordFile + "\\src\\main\\resources\\Attachments\\Attachments\\error.txt";
		
		CreateServiceRequest.fileAttachment().sendKeys(path);
		
		Assert.assertTrue(CreateServiceRequest.screenshotErrorIcon().isDisplayed());

	}

	@Test(priority = 18, description = " AT-85093,AT-85091,AT-85100 :verifying screenshot error text")
	public void verifyScreenshotErrorText() {
		
		String actualText = CreateServiceRequest.screenshotErrorText().getText().trim();

		String expectedUpdateText = PropsUtil.getDataPropertyValue("CreateSR_ErrorText");

		Assert.assertEquals(actualText, expectedUpdateText);

	}

	@Test(priority = 19, description = " AT-85107,AT-85288 : click on delete icon file gets deleted & Adding same 3 files")
	public void verifyDeletingAttachment() {
		
		CreateServiceRequest.deleteAttachment();
        
		Assert.assertTrue(CreateServiceRequest.deleteIcon().size()==0);
		
	}
	
	@Test(priority = 20, description = " AT-85107,AT-85288 : click on delete icon file gets deleted & Adding same 3 files")
	public void verifyAddAttachments() {
		
		SeleniumUtil.waitForPageToLoad(5000);

		String attachwordfile = System.getProperty("user.dir");
		
		String path = attachwordfile + "\\src\\main\\resources\\Attachments\\Attachments\\networth.png";
		
		CreateServiceRequest.fileAttachment().sendKeys(path);

		SeleniumUtil.waitForPageToLoad();

		String attachwordfile1 = System.getProperty("user.dir");
		
		String path1 = attachwordfile1 + "\\src\\main\\resources\\Attachments\\Attachments\\networth.png";
		
		CreateServiceRequest.fileAttachment().sendKeys(path1);

		SeleniumUtil.waitForPageToLoad();

		String attachwordfile2 = System.getProperty("user.dir");
		
		String path2 = attachwordfile2 + "\\src\\main\\resources\\Attachments\\Attachments\\networth.png";
		
		CreateServiceRequest.fileAttachment().sendKeys(path2);

	}

	@Test(priority = 21, description = "AT-85288 : File name displayed same for all the three files")
	public void verifySameFileName() {

		String actualText = CreateServiceRequest.fileName().get(0).getText().trim();

		String expectedUpdateText = PropsUtil.getDataPropertyValue("CreateSR_FileName");

		Assert.assertEquals(actualText, expectedUpdateText);

		String actualText1 = CreateServiceRequest.fileName().get(1).getText().trim();

		String expectedUpdateText1 = PropsUtil.getDataPropertyValue("CreateSR_FileName");

		Assert.assertEquals(actualText1, expectedUpdateText1);
		
		SeleniumUtil.waitForPageToLoad(1000);

		String actualText2 = CreateServiceRequest.fileName().get(2).getText().trim();

		String expectedUpdateText2 = PropsUtil.getDataPropertyValue("CreateSR_FileName");

		Assert.assertEquals(actualText2, expectedUpdateText2);

	}

	@Test(priority = 22, description = " AT-86392,AT-86393,AT-86394,AT-86395 : click on attachment file name it is previewed &  Verifying X icon in the pop up window")
	public void verifyViewingImageAndClosing() {
		
	
	
	SeleniumUtil.click(CreateServiceRequest.fileName().get(1));
		SeleniumUtil.waitForPageToLoad(8000);
		
		Assert.assertTrue(CreateServiceRequest.windowCloseBtn().isDisplayed());

	}
	
	@Test(priority = 23, description = " AT-86394,AT-86396 : clicking X icon in the pop up window & Clicking on the Image icon")
	public void verifyClosingPreview() {
		
	

		SeleniumUtil.click(CreateServiceRequest.windowCloseBtn());
		SeleniumUtil.waitForPageToLoad();
		
		SeleniumUtil.click(CreateServiceRequest.screenShotIcon().get(1));

		SeleniumUtil.click(CreateServiceRequest.windowCloseBtn());

		
		
	}
	
	@Test(priority = 24, description = " AT-85107 : click on delete icon file gets deleted")
	public void verifyDeletingAllAttachment() {
		
	
		CreateServiceRequest.deleteAttachment();
        Assert.assertTrue(CreateServiceRequest.deleteIcon().size()==0);

	}

	@Test(priority = 25, description = "AT-85105,AT-85106,AT-85089 : File name is displayed and characters get ellipsis after 20 characters")
	public void verifyFileName() {

		String attachwordfile = System.getProperty("user.dir");
		String path = attachwordfile + "\\src\\main\\resources\\Attachments\\Attachments\\testcharactersabovetwenty.gif";
		CreateServiceRequest.fileAttachment().sendKeys(path);
		
		SeleniumUtil.waitForPageToLoad();

		String actualText = CreateServiceRequest.fileName().get(0).getText().trim();

		String expectedUpdateText = PropsUtil.getDataPropertyValue("CreateSR_FileCharacter");

		Assert.assertEquals(actualText, expectedUpdateText);

	}

	@Test(priority = 26, description = "AT-85090: verifying  text file format is not supported")
	public void verifyAddingUnsupportedFile() {
		String attachUnsupportedFile = System.getProperty("user.dir");
		String path = attachUnsupportedFile + "\\src\\main\\resources\\Attachments\\Attachments\\FDTTemplate.docx";

		CreateServiceRequest.fileAttachment().sendKeys(path);

		String actualText = CreateServiceRequest.screenshotErrorText().getText().trim();

		String expectedUpdateText = PropsUtil.getDataPropertyValue("CreateSR_FileNotSupported");

		Assert.assertEquals(actualText, expectedUpdateText);

		Assert.assertTrue(CreateServiceRequest.screenshotErrorIcon().isDisplayed());

	}

	@Test(priority = 27, description = "AT-85097,AT-85098,AT-85089: verifying add attachment is disabled after adding 3 files")
	public void verifyAddingPngFile() {
		String attachPngFile = System.getProperty("user.dir");
		String path1 = attachPngFile + "\\src\\main\\resources\\Attachments\\Attachments\\Alphabets.png";
		CreateServiceRequest.fileAttachment().sendKeys(path1);
	}

	@Test(priority = 28, description = "AT-85097,AT-85098,AT-85089 : verifying add attachment is disabled after adding 3 files")
	public void verifyAddingTxtFile() {

		
		SeleniumUtil.waitForPageToLoad(2000);
		String attachTxtFile = System.getProperty("user.dir");
		String path2 = attachTxtFile + "\\src\\main\\resources\\Attachments\\Attachments\\networth.png";

		SeleniumUtil.sendKeys(CreateServiceRequest.fileAttachment(), path2);

		SeleniumUtil.waitForPageToLoad();

		Boolean status = CreateServiceRequest.addAttachmentLink().getAttribute("class").contains("attachment disabled");

		Assert.assertTrue(status);

	}

	@Test(priority = 29, description = "AT-85099 : verifying add attachment is enabled after deleting 1 file")
	public void verifyAddAttachmentGettingEnabled() {

		SeleniumUtil.click(CreateServiceRequest.deleteIcon().get(2));
		Boolean status = CreateServiceRequest.addAttachmentLink().getAttribute("class").contains("disabled");

		Assert.assertFalse(status);

	}

	@Test(priority = 30, description = "AT-85109,AT-85102,AT-85113,AT-86397: verify submit button enabled after adding errorfile and clicking on it & verifying Toast msg")
	public void verifySubmitBtn() {

		String attachTxtFile = System.getProperty("user.dir");
		String path = attachTxtFile + "\\src\\main\\resources\\Attachments\\Attachments\\error.txt";

		SeleniumUtil.waitForPageToLoad();
		
		CreateServiceRequest.fileAttachment().sendKeys(path);
		
		CreateServiceRequest.issueSubTextBox().clear();
		
		CreateServiceRequest.issueSubTextBox().sendKeys("IssueSubject");

		CreateServiceRequest.descriptionTextBox().clear();
		
		SeleniumUtil.sendKeys(CreateServiceRequest.descriptionTextBox(), "description");

		
		SeleniumUtil.click(CreateServiceRequest.submitButton());
		SeleniumUtil.waitForPageToLoad(3000);

		String actualText = CreateServiceRequest.successToastMsg().getText().trim();

		String expectedUpdateText = PropsUtil.getDataPropertyValue("CreateSR_SuccessToastMsg");

		Assert.assertEquals(actualText, expectedUpdateText);

		SeleniumUtil.waitForPageToLoad(5000);
		
		Assert.assertTrue(CreateServiceRequest.indicatorIcon().isDisplayed());

	}
}
