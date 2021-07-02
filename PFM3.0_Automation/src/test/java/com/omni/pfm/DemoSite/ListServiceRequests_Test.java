/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
package com.omni.pfm.DemoSite;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.omni.pfm.pages.DemoSite.CreateServiceRequest_Loc;
import com.omni.pfm.pages.DemoSite.LandingScreen_Loc;
import com.omni.pfm.pages.DemoSite.ListServiceRequests_Loc;
import com.omni.pfm.pages.DemoSite.UserProfilePersonalInfo_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.DemoSiteDBUtil;
import com.omni.pfm.utility.DemoSiteUtil;
import com.omni.pfm.utility.Email;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;




public class ListServiceRequests_Test extends TestBase {
	
	private static final Logger logger = LoggerFactory.getLogger(ListServiceRequests_Test.class);

	UserProfilePersonalInfo_Loc PersonalInfo;
	LandingScreen_Loc LandingScreen;
	CreateServiceRequest_Loc CreateServiceRequest;
	ListServiceRequests_Loc ListServiceRequests;
	DemoSiteDBUtil DBUtil;
	DemoSiteUtil DSUtil;
	DemoSiteUtil demoUtil;
	Email mail;


	String gmailId = "";
	String gmailPassword = "";

    @BeforeClass(alwaysRun = true)

	public void testInit() throws Exception {
		

		
		doInitialization("List Service Requests Test");
		
		PersonalInfo = new UserProfilePersonalInfo_Loc(d);
		ListServiceRequests = new ListServiceRequests_Loc(d);
		DBUtil = new DemoSiteDBUtil(gmailId, gmailId, gmailId);
		DSUtil = new DemoSiteUtil();
		demoUtil = new DemoSiteUtil();
		mail=new Email();
		LandingScreen = new LandingScreen_Loc(d);
		CreateServiceRequest = new CreateServiceRequest_Loc(d);
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
		
		SeleniumUtil.waitForPageToLoad();
		
		CreateServiceRequest.navigateToServiceRequests();

	}

	@Test(priority = 2, description = "AT-15208:Verifying if requests are sorted")

	public void verifyRequestsSorted() {

		ArrayList<String> list = new ArrayList<>();

		List<WebElement> l = ListServiceRequests.dates();

		for (WebElement we : l) {

			list.add(we.getText());

		}

		ArrayList<String> sorted = new ArrayList<>();

		for (String s : list) {

			sorted.add(s);

		}

		Collections.reverse(sorted);

		Assert.assertEquals(list, sorted, "Data is sorted based on date or not");

	}

	@Test(priority = 3, description = "AT-14206:Verifying if the service request created is stored in db")

	public void verifyRequestinDb() throws Exception {

		String DB_ServiceRequestID = DemoSiteDBUtil.getServiceRequestID(SignUp_Test.userid);

		int code = 0;

		List<WebElement> l = ListServiceRequests.serviceRequestIDs();

		for (int i = 0; i < l.size(); i++) {

			SeleniumUtil.waitForPageToLoad();
			
			String reqID = l.get(i).getText();

			if (DB_ServiceRequestID.equalsIgnoreCase(reqID))

			{

				code = code + 1;

			}

		}

		Assert.assertEquals(code, 1, "Code in DB or not - ");

	}

	@Test(priority = 4, description = "Verifying if the labels, placeholder text and error message in comment textarea")

	public void verifyLabelValues() throws Exception {

		SoftAssert s_assert = new SoftAssert();
		
		List<WebElement> l = ListServiceRequests.serviceRequestIDs();

		l.get(0).click();

		SeleniumUtil.waitForPageToLoad();


		SeleniumUtil.click(ListServiceRequests.addCommentButton());
		
		ListServiceRequests.typeTextField(ListServiceRequests.commentTextArea(),PropsUtil.getDataPropertyValue("ListSR_CommentText") );
		s_assert.assertEquals(ListServiceRequests.commentTextArea().getAttribute("placeholder"),
		PropsUtil.getDataPropertyValue("ListSR_CommentPlaceHolder"), "Comment PlaceHolder Value");

		SeleniumUtil.waitForPageToLoad();
		String characterCount = PropsUtil.getDataPropertyValue("ListSR_CharacterCount");

		s_assert.assertEquals(ListServiceRequests.characterCount().getText(), characterCount, "character count match");
		
		SeleniumUtil.waitForPageToLoad();
		
		SeleniumUtil.click(ListServiceRequests.commentTextArea());
		
		ListServiceRequests.commentTextArea().clear();

		SeleniumUtil.click(ListServiceRequests.commentButton());
		
		SeleniumUtil.waitForPageToLoad();
		
		s_assert.assertEquals(ListServiceRequests.errorMsg().getText(),
		
		PropsUtil.getDataPropertyValue("ListSR_ErrorMessage"), "Empty Error Message");

		s_assert.assertAll();

	}

	@Test(priority = 5, description = "Verifying if the Cancel button works as expected")

	public void verifyCancelButton() throws Exception {

		SeleniumUtil.click(ListServiceRequests.cancelButton());
		
		SeleniumUtil.waitForPageToLoad();
		
		Assert.assertFalse(ListServiceRequests.commentTextArea().isDisplayed(), "Cancel Button is clickable");

	}

	@Test(priority = 6, description = "AT-86412,AT-86411 :Verifying if the Comment button works as expected")

	public void verifyCommentButton() throws Exception {

		SoftAssert s_assert = new SoftAssert();

		SeleniumUtil.click(ListServiceRequests.addCommentButton());

		
		ListServiceRequests.typeTextField(ListServiceRequests.commentTextArea(),PropsUtil.getDataPropertyValue("ListSR_CommentText") );

		SeleniumUtil.waitForPageToLoad();
		
		SeleniumUtil.click(ListServiceRequests.charCount());

		SeleniumUtil.click(ListServiceRequests.commentButton());


		s_assert.assertEquals(ListServiceRequests.date().getText(), DemoSiteUtil.dateTimeSetter(),
				"Time at which the comment was added");

		String sentBy = PropsUtil.getDataPropertyValue("ListSR_SentBy") + " " + SignUp_Test.userid;

		s_assert.assertEquals(ListServiceRequests.sentBy().getText(), sentBy, "Sent By Text");

		s_assert.assertEquals(ListServiceRequests.commentText().getText(),
				PropsUtil.getDataPropertyValue("ListSR_CommentText"), "Comment Added");

		s_assert.assertAll();

	}
	
	@Test(priority = 7, description = " AT-86404,AT-86405,AT-86406 :verifying add attachment name & File type in Summary page")
	public void verifyAttachmentName() {
		
		
		String actualText =ListServiceRequests.summaryFileName().get(0).getText().trim();

		String expectedUpdateText = PropsUtil.getDataPropertyValue("ListSR_SummaryPageFileName");
		Assert.assertEquals(actualText, expectedUpdateText);
		

		String actualText1 =ListServiceRequests.summaryFileName().get(1).getText().trim();

		String expectedUpdateText1 = PropsUtil.getDataPropertyValue("ListSR_SummaryPageFileName1");
		Assert.assertEquals(actualText1, expectedUpdateText1);
		}

		
	@Test(priority = 8, description = " AT-86414 :verifying add attachment Link")
	public void verifyAddAttachmentLink() {
		
		ListServiceRequests.addCommentButton().click();
		
		String actualText = CreateServiceRequest.addAttachmentLink().getText().trim();

		String expectedUpdateText =PropsUtil.getDataPropertyValue("CreateSR_AddAttachmentLink");

		Assert.assertEquals(actualText, expectedUpdateText);

	}

	@Test(priority = 11, description = " AT-86415 :verifying add attachment Icon")
	public void verifyAddAttachmentIcon() {
		Assert.assertTrue(CreateServiceRequest.addAttachmentIconIsDisplyed());

	}

	@Test(priority = 12, description = "AT-86416 :verifying file format texts")
	public void verifyFileFormats() {
		String actualText = CreateServiceRequest.fileFormatText().getText().trim();

		String expectedUpdateText = PropsUtil.getDataPropertyValue("CreateSR_FileText");

		String actualText1 = CreateServiceRequest.fileFormatText2().getText().trim();

		String expectedUpdateText2 =PropsUtil.getDataPropertyValue("CreateSR_FileText1");

		String actualText2 = CreateServiceRequest.fileFormatText1().getText().trim();

		String expectedUpdateText1 = PropsUtil.getDataPropertyValue("CreateSR_FileFormat");
		Assert.assertEquals(actualText1, expectedUpdateText2);
		Assert.assertEquals(actualText, expectedUpdateText);
		Assert.assertEquals(actualText2, expectedUpdateText1);

	}

	@Test(priority = 13, description = " AT-86417 ,AT-86418: click on add attachment and uploading file")
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

		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(CreateServiceRequest.screenShotIcon().get(1).isDisplayed());
		Assert.assertTrue(CreateServiceRequest.deleteIcon().get(1).isDisplayed());

		String actualText = CreateServiceRequest.fileName().get(1).getText().trim();

		String expectedUpdateText = PropsUtil.getDataPropertyValue("CreateSR_FileName1");

		Assert.assertEquals(actualText, expectedUpdateText);
	}

	@Test(priority = 15, description = "AT-86429  : Verifying delete icon ")
	public void verifyDeleteIcon() {
		Assert.assertTrue(CreateServiceRequest.deleteIcon().get(0).isDisplayed());
		Assert.assertTrue(CreateServiceRequest.deleteIcon().get(1).isDisplayed());

	}

	@Test(priority = 16, description = "AT-86430 : Verifying screenshot image icon ")
	public void verifyScreenshotIcon() {

		Assert.assertTrue(CreateServiceRequest.isScreenShotDisplayed(1));
		Assert.assertTrue(CreateServiceRequest.isScreenShotDisplayed(2));

	}

	@Test(priority = 17, description = " AT-86422, : Verifying screenshot Error icon ")
	public void verifyScreenshotErrorIcon() {
	
		String attachWordFile = System.getProperty("user.dir");
		
		String path = attachWordFile + "\\src\\main\\resources\\Attachments\\Attachments\\error.txt";
		
		CreateServiceRequest.fileAttachment().sendKeys(path);
		
		Assert.assertTrue(CreateServiceRequest.screenshotErrorIcon().isDisplayed());

	}

	@Test(priority = 18, description = " AT-86421 :verifying screenshot error text")
	public void verifyScreenshotErrorText() {
		String actualText = CreateServiceRequest.screenshotErrorText().getText().trim();

		String expectedUpdateText = PropsUtil.getDataPropertyValue("CreateSR_ErrorText");

		Assert.assertEquals(actualText, expectedUpdateText);

	}

	@Test(priority = 19, description = " AT-86433,AT-86429: click on delete icon file gets deleted & Adding same 3 files")
	public void verifyDeletingAttachment() {
		CreateServiceRequest.deleteAttachment();
		
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

	@Test(priority = 20, description = "AT-85288 : File name displayed same for all the three files")
	public void verifySameFileName() {

		String actualText = CreateServiceRequest.fileName().get(0).getText().trim();

		String expectedUpdateText = PropsUtil.getDataPropertyValue("CreateSR_FileName");

		Assert.assertEquals(actualText, expectedUpdateText);

		String actualText1 = CreateServiceRequest.fileName().get(1).getText().trim();

		String expectedUpdateText1 = PropsUtil.getDataPropertyValue("CreateSR_FileName");

		Assert.assertEquals(actualText1, expectedUpdateText1);
		
		
		SeleniumUtil.waitForPageToLoad(1000);

		String actualText2 = CreateServiceRequest.fileName().get(2).getText().trim();

		String expectedUpdateText2 = PropsUtil.getDataPropertyValue("CreateSR_FileName");;

		Assert.assertEquals(actualText2, expectedUpdateText2);

	}

	@Test(priority = 21, description = " AT-86407 : click on attachment file name it is previewed &  Verifying X icon in the pop up window")
	public void verifyViewingImageAndClosing() {
		
	

		CreateServiceRequest.fileName().get(1).click();
		
		SeleniumUtil.waitForPageToLoad(8000);
		
		Assert.assertTrue(CreateServiceRequest.windowCloseBtn().isDisplayed());

	}
	
	@Test(priority = 22, description = " AT-86409 : clicking X icon in the pop up window & Clicking on the Image icon")
	public void verifyClosingPreview() {
		
	

		CreateServiceRequest.windowCloseBtn().click();
		
		SeleniumUtil.waitForPageToLoad();
		
		CreateServiceRequest.screenShotIcon().get(1).click();
		
		CreateServiceRequest.windowCloseBtn().click();
		
		
	}
	
	@Test(priority = 23, description = " AT-86433 : click on delete icon file gets deleted")
	public void verifyDeletingAllAttachment() {
		
	
		SeleniumUtil.waitForPageToLoad(3000);


		CreateServiceRequest.deleteAttachment();


		Assert.assertTrue(CreateServiceRequest.deleteIcon().size() == 0);

	}

	@Test(priority = 24, description = "AT-86432 : File name is displayed and characters get ellipsis after 20 characters")
	public void verifyFileName() {

		String attachwordfile = System.getProperty("user.dir");
		String path = attachwordfile + "\\src\\main\\resources\\Attachments\\Attachments\\testcharactersabovetwenty.gif";
		CreateServiceRequest.fileAttachment().sendKeys(path);
		
		SeleniumUtil.waitForPageToLoad();

		String actualText = CreateServiceRequest.fileName().get(0).getText().trim();

		String expectedUpdateText = PropsUtil.getDataPropertyValue("CreateSR_FileCharacter");

		Assert.assertEquals(actualText, expectedUpdateText);

	}

	@Test(priority = 25, description = "AT-86419: verifying  text file format is not supported")
	public void verifyAddingUnsupportedFile() {
		String attachUnsupportedFile = System.getProperty("user.dir");
		String path = attachUnsupportedFile + "\\src\\main\\resources\\Attachments\\Attachments\\FDTTemplate.docx";
		CreateServiceRequest.fileAttachment().sendKeys(path);

		String actualText = CreateServiceRequest.screenshotErrorText().getText().trim();

		String expectedUpdateText = PropsUtil.getDataPropertyValue("CreateSR_FileNotSupported");

		Assert.assertEquals(actualText, expectedUpdateText);

		Assert.assertTrue(CreateServiceRequest.screenshotErrorIcon().isDisplayed());

	}

	@Test(priority = 26, description = "AT-86427: verifying add attachment is disabled after adding 3 files")
	public void verifyAddingPngFile() {
		String attachPngFile = System.getProperty("user.dir");
		String path1 = attachPngFile + "\\src\\main\\resources\\Attachments\\Attachments\\Alphabets.png";
		CreateServiceRequest.fileAttachment().sendKeys(path1);
	}

	@Test(priority = 27, description = " : verifying add attachment is disabled after adding 3 files")
	public void verifyAddingTxtFile() {
		

		String attachTxtFile = System.getProperty("user.dir");
		String path2 = attachTxtFile + "\\src\\main\\resources\\Attachments\\Attachments\\networth.png";
		CreateServiceRequest.fileAttachment().sendKeys(path2);
		
		SeleniumUtil.waitForPageToLoad(3000);

		Boolean status = CreateServiceRequest.addAttachmentLink().getAttribute("class").contains("disabled");

		Assert.assertTrue(status);

	}

	@Test(priority = 28, description = "AT-86428 : verifying add attachment is enabled after adding 3 files and delete one file")
	public void verifyAddAttachmentGettingEnabled() {
		CreateServiceRequest.deleteIcon1().click();

		Boolean status = CreateServiceRequest.addAttachmentLink().getAttribute("class").contains("disabled");

		Assert.assertFalse(status);

	}

	@Test(priority = 29, description = "AT-86435 : verify submit button enabled after adding errorfile and clicking on it & verifying Toast msg")
	public void verifyToastMsg() {

		String attachTxtFile = System.getProperty("user.dir");
		String path = attachTxtFile + "\\src\\main\\resources\\Attachments\\Attachments\\error.txt";

		SeleniumUtil.waitForPageToLoad();
		CreateServiceRequest.fileAttachment().sendKeys(path);

		ListServiceRequests.commentTextArea().clear();
		ListServiceRequests.commentTextArea().sendKeys("Test");
		ListServiceRequests.commentButton().click();

		
		SeleniumUtil.waitForPageToLoad(5000);

		String actualText = ListServiceRequests.toastMsg().getText().trim();

		String expectedUpdateText = PropsUtil.getDataPropertyValue("ListSR_SuccessToastMsg");

		Assert.assertEquals(actualText, expectedUpdateText);

		SeleniumUtil.waitForPageToLoad(5000);

	}

}
